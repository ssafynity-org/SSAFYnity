import React, { useState } from "react";
import { useLocation, Link } from "react-router-dom";

function Footer() {

  const location = useLocation();

  if (location.pathname === "/login" || location.pathname === "/signup" || location.pathname === "/newsignup") {
    return null;
  }

  return (
    <footer>
      <p>© 2024 SSAFYnity Project. All rights reserved.</p>
    </footer>
  );
}

export default Footer;
