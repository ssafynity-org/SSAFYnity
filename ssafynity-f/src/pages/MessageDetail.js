import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import axiosInstance from "../api/axiosInstance";

function MessageDetail() {
  const { id } = useParams(); // URL의 :id 파라미터
  const [message, setMessage] = useState(null);
  const [error, setError] = useState(null);

  // 특정 쪽지 조회
  const fetchMessage = async () => {
    try {
      const response = await axiosInstance.get(`/api/message/${id}`);
      setMessage(response.data);
    } catch (error) {
      setError("쪽지 상세 조회 중 오류가 발생했습니다.");
      console.error(error);
    }
  };

  useEffect(() => {
    fetchMessage();
  }, [id]);

  return (
    <div className="message-detail">
      <h1>쪽지 상세보기</h1>
      {error && <p style={{ color: "red" }}>{error}</p>}
      {message ? (
        <div>
          <p><strong>보낸 사람:</strong> {message.senderId}</p>
          <p><strong>내용:</strong> {message.content}</p>
          <p><strong>보낸 시간:</strong> {message.timestamp}</p>
        </div>
      ) : (
        <p>쪽지 데이터를 불러오는 중입니다...</p>
      )}
    </div>
  );
}

export default MessageDetail;
