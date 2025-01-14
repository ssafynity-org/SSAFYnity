import React from "react";
import { useSelector } from "react-redux"; // useSelector import
import { useNavigate } from "react-router-dom";
import axiosInstance from "../api/axiosInstance";
import "../styles/Main.css";

function Main() {
  const user = useSelector(state => state.user.userInfo); // Redux store에서 user 데이터 가져오기
  const navigate = useNavigate();

  const currentDate = new Date().toLocaleDateString('ko-KR', {
    year: 'numeric', month: 'long', day: 'numeric'
  });

  const handleCheckIn = async () => {
    try {
      const response = await axiosInstance.post(`/api/attendance/in`);
      const data = await response.data;
      if (response.status === 200) {
        alert(`입실체크 성공: ${data}`);
      } else {
        alert(`입실체크 실패: ${data}`);
      }
    } catch (error) {
      alert("입실체크 중 오류가 발생했습니다.");
      console.error(error);
    }
  };

  const handleCheckOut = async () => {
    try {
      const response = await axiosInstance.post(`/api/attendance/out`);
      const data = await response.data;
      if (response.status === 200) {
        alert(`퇴실체크 성공: ${data}`);
      } else {
        alert(`퇴실체크 실패: ${data}`);
      }
    } catch (error) {
      alert("퇴실체크 중 오류가 발생했습니다.");
      console.error(error);
    }
  };

  const handleNavigateToMessagePage = () => {
    navigate("/message");
  };

  return (
    <div className="profile-container">
      <div>
        <p>Today's date: {currentDate}</p>
        {user && (
          <>
            <div>
              <img src={`data:image/jpeg;base64,${user.profileImage}`} alt="User profile" style={{ width: 100, height: 100 }} />
              <h3>Welcome, {user.name}!</h3>
            </div>
          </>
        )}
        <button className="small-gray-button" onClick={handleCheckIn}>입실체크</button>
        <button className="small-gray-button" onClick={handleCheckOut}>퇴실체크</button>
        <button className="small-gray-button" onClick={handleNavigateToMessagePage}>메시지 페이지 이동</button>
      </div>
    </div>
  );
}

export default Main;
