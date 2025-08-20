import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux"; 
import { useNavigate } from "react-router-dom";
import axiosInstance from "../api/axiosInstance";
import "../styles/Main.css";

function Main() {
  const user = useSelector(state => state.user.userInfo); 
  const navigate = useNavigate();
  const [profileImageUrl, setProfileImageUrl] = useState(""); 

  const currentDate = new Date().toLocaleDateString('ko-KR', {
    year: 'numeric', month: 'long', day: 'numeric'
  });

  // 프로필 이미지 불러오기
  useEffect(() => {
    const fetchProfileImage = async () => {
      try {
        const res = await axiosInstance.get("/api/member/getProfileImage");
        console.log("들어오는 이미지 : ", res.data);
        setProfileImageUrl(res.data);
      } catch (error) {
        console.error("프로필 이미지 불러오기 실패:", error);
      }
    };

    fetchProfileImage();
  }, []);

  const handleCheckIn = async () => {
    try {
      const response = await axiosInstance.post(`/api/attendance/in`);
      const data = response.data;
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
      const data = response.data;
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

  // 🔹 로그아웃 처리 함수
  const handleLogout = () => {
    // 저장된 토큰 삭제
    localStorage.removeItem("jwtToken"); 
    
    // Redux user 정보 초기화 필요하다면 여기서 dispatch 추가 가능
    
    // login 페이지로 이동
    navigate("/login");
  };

  return (
    <div className="main">
      <div className="profile-container-wrap">
        <div className="profile-container">
          {user && (
            <div className="profile-section">
              <img
                src={profileImageUrl || "/images/default-profile.png"} 
                alt="User profile"
                style={{ width: 80, height: 80 }}
              />
              <div className="profile-info">
                <p className="profile-campus">🏫대전 캠퍼스 10기</p>
                <div className="profile-nameAndStatus">
                  <p className="user-name">{user.name} 님</p>
                  <div className="v-line"></div>
                  <p className="user-status">정회원</p>
                </div>  
                <div className="profile-email">
                  <p>dldnwls009@naver.com</p>
                </div>
              </div>
              {/* 🔹 로그아웃 버튼 */}
              <div className="profile-logout" onClick={handleLogout}>
                <p>로그아웃</p>
                <img src="/images/로그아웃.png" alt="로그아웃" />
              </div>
            </div>
          )}
        </div>
        <div className="profile-container-footer">
          <p>알림</p>
          <div className="v-line"></div>
          <p>메세지</p>
          <div className="v-line"></div>
          <p>구독</p>
          <div className="v-line"></div>
          <p>마이페이지</p>
        </div>
      </div>
      <div className="main-view">
        <p>안녕</p>
      </div>
    </div>    
  );
}

export default Main;
