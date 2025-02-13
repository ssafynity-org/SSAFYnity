import React, { useState, useEffect } from "react";
import axiosInstance from "../api/axiosInstance";
import { useNavigate } from "react-router-dom"; // useNavigate import
import SearchBar from "../components/SearchBar";
import "../styles/Conference.css";

function Conference() {

  const [menu, setMenu] = useState("전체 글");

  const handleMenu = (menu) => {
    setMenu(menu);
    if(menu === "기업 별"){
      setCompany("")
    }
  }

  const [company, setCompany] = useState("");
  const handleCompany = (company) => {
    setCompany(company);
  }
  
  return (
    <div className="conference-page">
      <SearchBar selectedMenu={handleMenu} selectedCompany={handleCompany} />
      <div className="conference-wrap"></div>    
    </div>
  );
}

export default Conference;
