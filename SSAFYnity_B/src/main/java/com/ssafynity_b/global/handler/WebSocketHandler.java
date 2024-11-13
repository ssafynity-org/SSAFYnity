package com.ssafynity_b.global.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafynity_b.domain.message.dto.MessageDto;
import com.ssafynity_b.domain.message.service.MessageService;
import com.ssafynity_b.domain.notification.dto.NotificationDto;
import com.ssafynity_b.domain.notification.service.NotificationService;
import com.ssafynity_b.global.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Map<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();

    private final MessageService messageService;
    private final NotificationService notificationService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String userId = (String) session.getAttributes().get("userId");
        if (userId != null) {
            sessions.put(Long.parseLong(userId), session);
            System.out.println("WebSocket 연결이 성립되었습니다: 사용자 ID = " + userId);
        } else {
            session.close(); // userId가 없으면 연결 종료
            System.out.println("WebSocket 연결에 userId가 없습니다.");
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 수신한 JSON 메시지를 MessageDto 객체로 변환
        String payload = message.getPayload();
        MessageDto receivedMessage = objectMapper.readValue(payload, MessageDto.class);

        try{
            // 쪽지와 알림을 DB에 저장
            messageService.save(receivedMessage);
            notificationService.save(receivedMessage);

            // 수신자 ID로 세션을 조회하여 알림 전송
            WebSocketSession receiverSession = sessions.get(receivedMessage.getReceiver());
            NotificationDto notificationDto = NotificationDto.builder()
                    .type(1)
                    .sender(receivedMessage.getSender())
                    .receiver(receivedMessage.getReceiver())
                    .message(receivedMessage.getMessage())
                    .time(receivedMessage.getTime())
                    .build();
            if (receiverSession != null && receiverSession.isOpen()) {
                receiverSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(notificationDto)));
            }

        } catch (MemberNotFoundException e){
            // DB에 저장하려고 하는데 회원을 찾지 못했을 때
            NotificationDto notificationDto = NotificationDto.builder()
                    .type(0)
                    .errorMessage("회원을 찾을 수 없습니다.")
                    .build();
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(notificationDto)));
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        // 연결이 종료되면 사용자 ID와 세션을 매핑에서 제거
        String userId = (String) session.getAttributes().get("userId");
        sessions.remove(Long.parseLong(userId));
    }
}
