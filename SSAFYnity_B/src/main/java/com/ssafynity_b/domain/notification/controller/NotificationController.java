package com.ssafynity_b.domain.notification.controller;

import com.ssafynity_b.domain.message.service.MessageService;
import com.ssafynity_b.domain.notification.dto.GetNotificationDto;
import com.ssafynity_b.domain.notification.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
@Tag(name = "Notification 컨트롤러")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "알림 전체 조회")
    @GetMapping("/{memberId}")
    public ResponseEntity<?> getAllNotification(@PathVariable Long memberId){
        List<GetNotificationDto> notificationList = notificationService.getAllNotification(memberId);
        return new ResponseEntity<List<GetNotificationDto>>(notificationList, HttpStatus.OK);
    }

}
