import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import './App.css';
import Header from './components/Header';
import Footer from './components/Footer';
import Login from "./pages/Login";
import Main from "./pages/Main";

function App() {
  return (
    <Router>
    <div className="App">
      <Header />
      <main>
        <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/main" element={<Main />} />
            <Route
              path="/"
              element={<h2>Welcome to the React App! Start here.</h2>}
            />
        </Routes>
      </main>
      <Footer />      
    </div>
    </Router>
  );
}

export default App;
