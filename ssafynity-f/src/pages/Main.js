import React from "react";
import { useLocation } from "react-router-dom"; // useLocation import
import axiosInstance from "../api/axiosInstance";
import { useNavigate } from "react-router-dom"; // useNavigate import

function Main() {
  const location = useLocation();
  const userData = location.state; // 전달된 데이터 수신
  const navigate = useNavigate(); // useNavigate 생성
  
  //입실체크 핸들러
  const handleCheckIn = async () => {
    try {
      const response = await axiosInstance.post(`/api/attendance/in`)
        const data = await response.data;
        console.log("Response Data:", response.data); // 서버 응답 데이터를 확인합니다.
        if (response.status==200) {
          alert(`입실체크 성공: ${data}`);
        } else {
          alert(`입실체크 실패: ${data}`);
        }
      } catch (error) {
        alert("입실체크 중 오류가 발생했습니다.");
        console.error(error);
      } 
  }

  //퇴실체크 핸들러
  const handleCheckOut = async () => {
    try {
      const response = await axiosInstance.post(`/api/attendance/out`)
        const data = await response.data;
        console.log("Response Data:", response.data); // 서버 응답 데이터를 확인합니다.
        if (response.status==200) {
          alert(`퇴실체크 성공: ${data}`);
        } else {
          alert(`퇴실체크 실패: ${data}`);
        }
      } catch (error) {
        alert("퇴실체크 중 오류가 발생했습니다.");
        console.error(error);
      } 
  }

  // message 이동 핸들러
  const handleNavigateToMessagePage = () => {
    navigate("/message"); // "/message" 경로로 이동
  };

  return (
    <div className="profile-container">
      <h1>User Profile</h1>
      {userData ? (
        <div>
          <p>JwtToken: {userData.jwtToken}</p>
          <button onClick={handleCheckIn}>입실체크</button>
          <button onClick={handleCheckOut}>퇴실체크</button>
          <button onClick={handleNavigateToMessagePage}>메시지 페이지 이동</button>
        </div>
      ) : (
        <p>No user data available.</p>
      )}
    </div>
  );
}

export default Main;