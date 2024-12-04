import React from "react";
import { useLocation } from "react-router-dom"; // useLocation import

function Main() {
  const location = useLocation();
  const userData = location.state; // 전달된 데이터 수신

  return (
    <div className="profile-container">
      <h1>User Profile</h1>
      {userData ? (
        <div>
          {<p>JwtToken: {userData.jwtToken}</p>}
        </div>
      ) : (
        <p>No user data available.</p>
      )}
    </div>
  );
}

export default Main;