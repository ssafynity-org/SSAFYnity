import React, { useState, useEffect } from "react";
import axiosInstance from "../api/axiosInstance";

function MessageWrite(){
    const [newMessage, setNewMessage] = useState({ content: "", receiverId: "" }); // 새 쪽지 데이터
    const [error, setError] = useState(null);

    // 쪽지 생성 핸들러
    const handleSendMessage = async (e) => {
    e.preventDefault(); // 폼 제출 기본 동작 방지
    try {
      const response = await axiosInstance.post("/api/message", newMessage);
      if (response.status === 200) {
        alert("쪽지가 성공적으로 전송되었습니다.");
        setNewMessage({ content: "", receiverId: "" }); // 입력 필드 초기화
      }
    } catch (error) {
      setError("쪽지 전송 중 오류가 발생했습니다.");
      console.error(error);
    }
  };

  return (
    <div className="message-page">
      <h1>쪽지 쓰기 페이지</h1>

      {/* 쪽지 전송 폼 */}
      <form onSubmit={handleSendMessage}>
        <h2>쪽지 쓰기</h2>
        <div>
          <label>쪽지 내용:</label>
          <textarea
            value={newMessage.content}
            onChange={(e) =>
              setNewMessage({ ...newMessage, content: e.target.value })
            }
            placeholder="쪽지 내용을 입력하세요."
            required
          />
        </div>
        <div>
          <label>받는 사람 ID:</label>
          <input
            type="number"
            value={newMessage.receiverId}
            onChange={(e) =>
              setNewMessage({ ...newMessage, receiverId: e.target.value })
            }
            placeholder="받는 사람의 ID를 입력하세요."
            required
          />
        </div>
        <button type="submit">쪽지 쓰기</button>
      </form>
      </div>
  );
}

export default MessageWrite;