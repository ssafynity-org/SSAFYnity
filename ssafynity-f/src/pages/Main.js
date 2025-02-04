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
    <div className="main">
      <div className="profile-container">
          {/* <p>Today's date: {currentDate}</p> */}
          {user && (
            <div className="profile-section">
              <img src={`data:image/jpeg;base64,${user.profileImage}`} alt="User profile" style={{ width: 80, height: 80 }} />
              <div className="profile-info">
                <div className="profile-nameAndStatus">
                  <p className="user-name">{user.name} 님</p>
                  <div class='v-line'></div>
                    <p className="user-status">정회원</p>
                </div>  
                <div className="profile-email">
                  <p>dldnwls009@naver.com</p>
                </div>
              </div>
            </div>
          )}
          {/* <div className="button-container">
            <button className="small-gray-button" onClick={handleCheckIn}>입실체크</button>
            <button className="small-gray-button" onClick={handleCheckOut}>퇴실체크</button>
          </div> */}
          <div className="profile-logout">
            <p>로그아웃</p>
            <img src="/images/로그아웃.png"/>
          </div>
      </div>
      <div className="main-view">
        <p>안녕</p>
      </div>
    </div>    
  );
}

export default Main;
