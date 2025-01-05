import React from "react";
import { useLocation } from "react-router-dom";

function Header() {
  const location = useLocation(); // 현재 위치 정보를 가져옵니다.

  // 로그인 페이지에서는 헤더를 숨깁니다.
  if (location.pathname === "/login") {
    return null;
  }
  
  return (
    <header>
      <h1>SSAFYnity Project</h1>
    </header>
  );
}

export default Header;