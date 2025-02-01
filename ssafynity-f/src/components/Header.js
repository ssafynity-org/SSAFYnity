import React from "react";
import { useLocation, Link } from "react-router-dom";
import "../styles/Header.css";

function Header() {
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
            <li><Link to="/mycampus">마이캠퍼스</Link></li>
            <li><Link to="/education">강의실</Link></li>
            <li><Link to="/community">커뮤니티</Link></li>
            <li><Link to="/helpdesk">Help Desk</Link></li>
            <li><Link to="/mentoring">멘토링 게시판</Link></li>
          </ul>
        </nav>
        <div className="header-bell">
          <img src="/images/bell.png" alt="bell" />
        </div>
      </div>
    </header>
  );
}

export default Header;