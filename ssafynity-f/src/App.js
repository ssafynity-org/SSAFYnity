import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import './App.css';
import Header from './components/Header';
import Footer from './components/Footer';
import { AuthProvider } from "./components/AuthContext";
import PrivateRoute from "./components/PrivateRoute";
import SignUp from "./pages/SignUp";
import Login from "./pages/Login";
import Main from "./pages/Main";
import Message from "./pages/Message";
import MessageWrite from "./pages/MessageWrite";
import MessageDetail from "./pages/MessageDetail";

function App() {
  return (
    <AuthProvider>
    <Router>
    <div className="App">
      <Header />
      <main>
        <Routes>
            <Route path="/signup" element={<SignUp />} />
            <Route path="/login" element={<Login />} />
            <Route
                        path="/main"
                        element={
                            <PrivateRoute>
                                <Main />
                            </PrivateRoute>
                        }
                    />
              {/* MessagePage를 위한 라우트 추가 */}
              <Route
                path="/message"
                element={
                  <PrivateRoute>
                    <Message />
                  </PrivateRoute>
                }
              />
              {/* MessageWrite을 위한 라우트 추가 */}
              <Route
                path="/message/write"
                element={
                  <PrivateRoute>
                    <MessageWrite />
                  </PrivateRoute>
                }
              />
              <Route
                path="/message/detail/:id"
                element={
                  <PrivateRoute>
                    <MessageDetail />
                  </PrivateRoute>
                }
              />
            <Route
              path="/"
              element={<h2>Welcome to the React App! Start here.</h2>}
            />
        </Routes>
      </main>
      <Footer />      
    </div>
    </Router>
    </AuthProvider>
  );
}

export default App;
