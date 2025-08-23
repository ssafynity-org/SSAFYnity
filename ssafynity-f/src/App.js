import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Provider } from 'react-redux';
import { PersistGate } from 'redux-persist/integration/react';
import { store, persistor } from '../src/app/store.js';
import './App.css';
import Header from './components/Header';
import Footer from './components/Footer';
import ProtectedRoute from "./components/ProtectedRoute.js";
import Login from "./pages/Login";
import Main from "./pages/Main";
import Message from "./pages/Message";
import MessageWrite from "./pages/MessageWrite";
import MessageDetail from "./pages/MessageDetail";
import Conference from "./pages/Conference.js";
import ConferenceVideo from "./pages/ConferenceVideo";
import Board from "./pages/Board.js";
import CreatePost from "./pages/CreatePost.js";
import NewSignup from "./pages/NewSignUp.js";


function App() {
  return (
    <Provider store={store}>
      <PersistGate loading={null} persistor={persistor}>
        <Router>
          <div className="App">
            <Header />
            <main>
              <Routes>
                {/* <Route path="/signup" element={<SignUp />} /> */}
                <Route path="/signup" element={<NewSignup />} />
                <Route path="/login" element={<Login />} />
                <Route path="/main" element={<ProtectedRoute><Main /></ProtectedRoute>} />
                <Route path="/message" element={<ProtectedRoute><Message /></ProtectedRoute>} />
                <Route path="/message/write" element={<ProtectedRoute><MessageWrite /></ProtectedRoute>} />
                <Route path="/message/detail/:id" element={<ProtectedRoute><MessageDetail /></ProtectedRoute>} />
                <Route path="/conference/external" element={<ProtectedRoute><Conference /></ProtectedRoute>} />
                <Route path="/conference/external/:videoId" element={<ProtectedRoute><ConferenceVideo /></ProtectedRoute>} />
                <Route path="/community/board" element={<ProtectedRoute><Board /></ProtectedRoute>} />
                <Route path="/create-post" element={<CreatePost />} />
              </Routes>
            </main>
            <Footer />
          </div>
        </Router>
      </PersistGate>
    </Provider>
  );
}

export default App;
