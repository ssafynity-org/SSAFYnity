import React, { useState, useRef } from "react";
import axios from "../api/axiosInstance"; // 가정된 axios 인스턴스 경로
import { useNavigate } from "react-router-dom";
import ImageCropper from "../components/ImageCropper"; // 크롭 컴포넌트 추가
import LottieAnimation from "../components/LottieAnimation"; // LottieAnimation 컴포넌트 불러오기
import "../styles/SignUp.css";

function Signup() {
  const [name, setName] = useState("");
  const [company, setCompany] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [profileImage, setProfileImage] = useState(null);
  const [userData, setUserData] = useState("");
  const [showModal, setShowModal] = useState(false); // 모달 표시 상태
  const [loading, setLoading] = useState(true); // 애니메이션 상태
  const [imageSrc, setImageSrc] = useState(null); // 원본 이미지 소스
  const [croppedImage, setCroppedImage] = useState(null); // 크롭된 이미지
  const [showCropper, setShowCropper] = useState(false); // 크롭 UI 표시 상태
  const imgInput = useRef(null);

  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    const formData = new FormData();
    // JSON 객체를 문자열로 변환한 뒤 Blob 객체로 변환하여 FormData에 추가
    const jsonBlob = new Blob([JSON.stringify({
      name: name,
      company: company,
      email: email,
      password: password
    })], { type: 'application/json' });

    formData.append("member", jsonBlob);

    if (croppedImage) {
      console.log("서버로 전송할 크롭된 이미지:", croppedImage);
      const croppedBlob = dataURLToBlob(croppedImage);
      formData.append("file", croppedBlob, "profile.jpg");
    }

    // if (profileImage) {
    //   formData.append("file", profileImage);
    // }


    try {
      const response = await axios.post("/api/member/signup/multipart", formData);
      setShowModal(true); // 모달 표시
      setTimeout(() => {
        setShowModal(false); // 3초 후 모달 숨김
        navigate("/login"); // 로그인 페이지로 이동
      }, 3000);
    } catch (error) {
      if (error.response && error.response.status === 401) {
        setUserData("Invalid credentials. Please try again.");
      } else {
        setUserData("An error occurred. Please try again later.");
      }
    }
  };

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        setImageSrc(reader.result); // 크롭할 원본 이미지 저장
        setShowCropper(true); // 크롭 UI 표시
        console.log("뭐지 왜 안뜸");  
      };
    }
  };

  const handleCropComplete = (croppedImg) => {
    setCroppedImage(croppedImg);
    setShowCropper(false);
  };

  const dataURLToBlob = (dataURL) => {
    const byteString = atob(dataURL.split(",")[1]); // Base64 데이터 디코딩
    const mimeString = dataURL.split(",")[0].split(":")[1].split(";")[0]; // MIME 타입 추출
  
    const arrayBuffer = new ArrayBuffer(byteString.length);
    const ia = new Uint8Array(arrayBuffer);
  
    for (let i = 0; i < byteString.length; i++) {
      ia[i] = byteString.charCodeAt(i);
    }
  
    return new Blob([arrayBuffer], { type: mimeString }); // Blob 객체 생성
  };

  return (
    <div className="signup-container">
      <img src="/images/bigLogo.png" alt="Logo" className="signup-logo" />
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="name">Name</label>
          <input
            type="text"
            id="name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="company">Company</label>
          <input
            type="text"
            id="company"
            value={company}
            onChange={(e) => setCompany(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="email">Email</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="file">Profile Image</label>
          <input
            type="file"
            id="file"
            ref={imgInput} // ✅ useRef 연결
            onChange={handleFileChange}
          />
        </div>
        {croppedImage && <img src={croppedImage} alt="Cropped Profile" className="cropped-preview" />}
        <button onClick={() => imgInput.current.click()}>Upload Image</button>
        {showCropper && (
          <ImageCropper
            imageSrc={imageSrc}
            onCropComplete={handleCropComplete}
            onCancel={() => setShowCropper(false)}
          />
        )}
        <button type="submit">SignUp</button>
        {userData && <p>{userData}</p>}
      </form>
      {showModal && (
        <div className="modal">
          <div className="modal-content">
            <h4>회원가입 성공!</h4>
            {loading ?
              <LottieAnimation lottiePath="/path/to/your/loading-animation.json" height={200} width={200} /> :
              <LottieAnimation lottiePath="/path/to/your/check-animation.json" height={200} width={200} />
            }
            <p>잠시 후 로그인 페이지로 이동합니다.</p>
          </div>
        </div>
      )}
    </div>
  );
}

export default Signup;
