import React from "react";
import { useSelector } from "react-redux"; // useSelector import
import { useLocation, Link } from "react-router-dom";
import "../styles/Header.css";

function Header() {
  const user = useSelector(state => state.user.userInfo); // Redux store에서 user 데이터 가져오기
  const location = useLocation(); // 현재 위치 정보를 가져옵니다.

  // 로그인 페이지에서는 헤더를 숨깁니다.
  if (location.pathname === "/login" || location.pathname === "/signup") {
    return null;
  }
  
  return (
    <header className="header">
      <div className="header-container">
        <div className="header-logo">
          <Link to="/main">
            <img src="/images/bigLogo.png" alt="Logo" />
          </Link>
        </div>
        <nav className="header-navigation">
          <ul>
            <li><Link to="/mycampus">싸피니티 소개</Link></li>
            <li><Link to="/education">컨퍼런스</Link></li>
            <li><Link to="/community">커뮤니티</Link></li>
            <li><Link to="/mentoring">멘토링</Link></li>
            <li><Link to="/helpdesk">Help Desk</Link></li>
          </ul>
        </nav>
        <div className="header-link">
          <div className="header-support">
            <img src="/images/아이들과미래재단후원하기.png" alt="아이들과미래재단후원하기" />
            </div>
          <div className="header-discord">
            <img src="/images/디스코드참여하기.png" alt="디스코드참여하기" />
          </div>
        </div>
      </div>
    </header>
  );
}

export default Header;