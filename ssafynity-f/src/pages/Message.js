import React, { useState, useEffect } from "react";
import axiosInstance from "../api/axiosInstance";
import { useNavigate } from "react-router-dom"; // useNavigate import

function Message() {
  const [messages, setMessages] = useState([]); // 전체 쪽지 상태
  const [error, setError] = useState(null);
  const navigate = useNavigate(); // useNavigate 생성

  
  // 전체 쪽지 조회
  const fetchMessages = async () => {
    try {
      const response = await axiosInstance.get("/api/message/all");
      setMessages(response.data);
    } catch (error) {
      setError("쪽지 조회 중 오류가 발생했습니다.");
      console.error(error);
    }
  };

  useEffect(() => {
    fetchMessages(); // 페이지 로드 시 쪽지 조회
  }, []);

  // 쪽지쓰기 페이지 이동 핸들러
  const handleNavigateToMessageWrite = () => {
    navigate("/message/write"); // "/message/write" 경로로 이동
  };

  // 쪽지 상세보기 페이지 이동
  const handleNavigateToMessageDetail = (messageId) => {
    navigate(`/message/detail/${messageId}`); // "/message/detail/:id" 경로로 이동
  };

  return (
    <div className="message-page">
      <h1>메시지 페이지</h1>
      <div>
        <button onClick={handleNavigateToMessageWrite}>쪽지 쓰러가기</button>
      </div>

      {/* 쪽지 목록 */}
      <div>
        <h2>받은 쪽지</h2>
        {error && <p style={{ color: "red" }}>{error}</p>}
        {messages.length > 0 ? (
          <ul>
            {messages.map((message) => (
              <li key={message.id} onClick={() => handleNavigateToMessageDetail(message.id)}>
                <p>
                  <strong>보낸 사람:</strong> {message.senderId}
                </p>
                <p>
                  <strong>내용:</strong> {message.content.length > 20
                    ? `${message.content.slice(0, 20)}...`
                    : message.content}
                </p>
                <p>
                  <strong>보낸 시간:</strong> {message.timestamp}
                </p>
              </li>
            ))}
          </ul>
        ) : (
          <p>쪽지가 없습니다.</p>
        )}
      </div>
    </div>
  );
}

export default Message;
