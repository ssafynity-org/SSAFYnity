import React, { useState, useRef } from "react";
import { useEffect } from "react";
import axios from "../api/axiosInstance"; // 가정된 axios 인스턴스 경로
import { useNavigate } from "react-router-dom";
import ImageCropper from "../components/ImageCropper"; // 크롭 컴포넌트 추가
import LottieAnimation from "../components/LottieAnimation"; // LottieAnimation 컴포넌트 불러오기
import styles from "../styles/NewSignUp.module.css";

function NewSignup() {

  const navigate = useNavigate(); // useNavigate 훅 사용

  const [profile, setProfile] = useState({
    email: "", // 백엔드에 api 요청 보낼때에는 emailDomain포함해서 전송하기
    password: "",
    name: "",
    cohort: 1,
    campus: "서울캠퍼스",
    jobSearch: true,
    company: "",
    companyBuild: true,
  });

  const [emailDomain, setEmailDomain] = useState("@gmail.com");

  const [passwordErrors, setPasswordErrors] = useState({
    length: true, // 8자 이상 20자 이하
    number: true, // 숫자 포함
    letter: true, // 영문 포함
    special: true, // 특수문자 포함
  });

  const handleChange = (event) => {
    const { name, value } = event.target;
    setProfile((prevProfile) => ({
      ...prevProfile,
      [name]: name === "companyBuild" ? JSON.parse(value) : value,
    }));

    if (name === "password") {
      setPasswordErrors({
        length: value.length < 8 || value.length > 20,
        number: !/\d/.test(value), // 숫자가 없는 경우
        letter: !/[a-zA-Z]/.test(value), // 영문자가 없는 경우
        special: !/[!@#$%^&*(),.?":{}|<>]/.test(value), // 특수문자가 없는 경우
      });
    }
  };

  const handleChangeEmailDomain = (option) => {
    setEmailDomain(option);
    setIsOpenDomain(false);
  }

  const handleChangeCohort = (option) => {
    setProfile((prevProfile) => ({
      ...prevProfile,
      cohort: option
    }));
    setIsOpenCohort(false);
  }

  const handleChangeCampus = (option) => {
    setProfile((prevProfile) => ({
      ...prevProfile,
      campus: option
    }));
    setIsOpenCampus(false);
  }

  const handleChangeJobSearch = (option) => {
    setProfile((prevProfile) => ({
      ...prevProfile,
      jobSearch: JSON.parse(option)
    }));
    setIsOpenJobSearch(false);
  }

  useEffect(() => {
    if (profile.jobSearch) {
      setProfile((prev) => ({ ...prev, company: "", companyBuild: true }));
    }
  }, [profile.jobSearch]);

  // select 드롭다운
  const [isOpenDomain, setIsOpenDomain] = useState(false); // 드롭다운 열림/닫힘 상태
  const [isOpenCohort, setIsOpenCohort] = useState(false);
  const [isOpenCampus, setIsOpenCampus] = useState(false);
  const [isOpenJobSearch, setIsOpenJobSearch] = useState(false);
  const dropdownRefDomain = useRef(null); // 드롭다운 요소 참조
  const dropdownRefCohort = useRef(null);
  const dropdownRefCampus = useRef(null);
  const dropdownRefJobSearch = useRef(null);

  // 드롭다운 외부 클릭 감지
  useEffect(() => {
    const handleClickOutside = (event) => {
      if (dropdownRefDomain.current && !dropdownRefDomain.current.contains(event.target)) {
        setIsOpenDomain(false); // 드롭다운 닫기
      }
      if (dropdownRefCohort.current && !dropdownRefCohort.current.contains(event.target)) {
        setIsOpenCohort(false);
      }
      if (dropdownRefCampus.current && !dropdownRefCampus.current.contains(event.target)) {
        setIsOpenCampus(false);
      }
      if (dropdownRefJobSearch.current && !dropdownRefJobSearch.current.contains(event.target)) {
        setIsOpenJobSearch(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  const submit = async () => {

    const requestBody = {
      email: profile.email + emailDomain,
      password: profile.password,
      name: profile.name,
      cohort: profile.cohort,
      campus: profile.campus,
      jobSearch: profile.jobSearch,
      company: profile.company,
      companyBlind: profile.companyBuild
    };
  
    try {
      const response = await axios.post("/api/member/signup", requestBody, {
        headers: {
          "Content-Type": "application/json"
        }
      });

      console.log("회원가입 성공:", response.data);
      alert("회원가입이 완료되었습니다!");
      navigate("/login"); // 로그인 화면으로 이동
    } catch (error) {
      console.error("회원가입 실패:", error.response ? error.response.data : error.message);
      alert("회원가입에 실패했습니다. 다시 시도해주세요.");
    }

    console.log("이메일 : " + profile.email + emailDomain + "\n"
      + "비밀번호 : " + profile.password + "\n"
      + "이름 : " + profile.name + "\n"
      + "기수 : " + profile.cohort + "\n"
      + "캠퍼스 : " + profile.campus + "\n"
      + "취준상태 : " + profile.jobSearch + "\n"
      + "회사명 : " + profile.company + "\n"
      + "공개여부 : " + profile.companyBuild + "\n"
    );
  }

  return (
    <div className={styles.signupContainer}>
      <div className={styles.title}>
        <img src="/images/bigLogo.png" alt="Logo" className={styles.logo} /> <span>회원가입</span>
      </div>
      <div className={styles.form}>

        <div className={styles.item}>
          <div className={styles.block}>
            <span className={styles.subTitle}><img src="/images/email.png" />이메일</span>
            <span className={styles.warningE}>이미 가입된 이메일입니다.</span>
          </div>
          <div className={styles.emailInput}>
            <input type="text" name="email" value={profile.email} onChange={handleChange} />
            <div className={styles.customDropdown} ref={dropdownRefDomain}>
              {/* 드롭다운 헤더 */}
              <div
                className={`${styles.dropdownHeader} ${isOpenDomain ? styles.dropdownHeaderOpen : ""}`}
                onClick={() => { setIsOpenDomain(!isOpenDomain); setIsOpenCohort(false); setIsOpenCampus(false); setIsOpenJobSearch(false); }} // 열림/닫힘 상태 토글
              >
                <div>{emailDomain}</div>
                <img src="/images/드롭다운화살표.png" alt="그림" className={`${styles.arrow} ${isOpenDomain ? styles.up : styles.down}`} />
              </div>

              {/* 드롭다운 옵션 목록 */}
              {isOpenDomain && (
                <ul className={styles.dropdownOptions}>
                  <li onClick={() => handleChangeEmailDomain("@gmail.com")}>@gmail.com</li>
                  <li onClick={() => handleChangeEmailDomain("@naver.com")}>@naver.com</li>
                  <li onClick={() => handleChangeEmailDomain("@daum.com")}>@daum.com</li>
                </ul>
              )}
            </div>
            <div className={styles.duplication}>중복확인<img src="/images/중복체크전.png" className={styles.duplicationCheck} /></div>
          </div>
        </div>

        <div className={styles.item}>
          <div className={styles.block}>
            <span className={styles.subTitle}><img src="/images/password.png" />비밀번호</span>
            <span className={styles.warning}>{passwordErrors.special && <span>8자 이상 20자 이하</span>}</span>
            <span className={styles.warning}>{passwordErrors.number && <span>숫자 포함</span>}</span>
            <span className={styles.warning}>{passwordErrors.letter && <span>영문 포함</span>}</span>
            <span className={styles.warning}>{passwordErrors.special && <span>특수문자 포함</span>}</span>
          </div>
          <input type="password" name="password" value={profile.password} onChange={handleChange} />
        </div>

        <div className={styles.item}>
          <div className={styles.block}>
            <span className={styles.subTitle}><img src="/images/name.png" />이름</span>
          </div>
          <input type="text" name="name" value={profile.name} onChange={handleChange} />
        </div>


        <div className={`${styles.item} ${styles.coNcam}`}>
          <div>
            <span className={styles.subTitle}><img src="/images/cohort.png" />기수</span>
            <div className={styles.customDropdown} ref={dropdownRefCohort}>
              {/* 드롭다운 헤더 */}
              <div
                className={`${styles.dropdownHeader} ${isOpenCohort ? styles.dropdownHeaderOpen : ""}`}
                onClick={() => { setIsOpenCohort(!isOpenCohort); setIsOpenDomain(false); setIsOpenCampus(false); setIsOpenJobSearch(false); }} // 열림/닫힘 상태 토글
              >
                {profile.cohort}
                <img src="/images/드롭다운화살표.png" alt="그림" className={`${styles.arrow} ${isOpenCohort ? styles.up : styles.down}`} />
              </div>

              {/* 드롭다운 옵션 목록 */}
              {isOpenCohort && (
                <ul className={styles.dropdownOptions}>
                  {[...Array(13)].map((_, index) => (
                    <li key={index + 1} value={index + 1} onClick={() => handleChangeCohort(index + 1)}>{index + 1}</li>
                  ))}
                </ul>
              )}
            </div>
          </div>

          <div>
            <span className={styles.subTitle}><img src="/images/campus.png" />캠퍼스</span>
            <div className={styles.customDropdown} ref={dropdownRefCampus}>
              {/* 드롭다운 헤더 */}
              <div
                className={`${styles.dropdownHeader} ${isOpenCampus ? styles.dropdownHeaderOpen : ""}`}
                onClick={() => { setIsOpenCampus(!isOpenCampus); setIsOpenDomain(false); setIsOpenCohort(false); setIsOpenJobSearch(false); }} // 열림/닫힘 상태 토글
              >
                {profile.campus}
                <img src="/images/드롭다운화살표.png" alt="그림" className={`${styles.arrow} ${isOpenCampus ? styles.up : styles.down}`} />
              </div>

              {/* 드롭다운 옵션 목록 */}
              {isOpenCampus && (
                <ul className={styles.dropdownOptions}>
                  <li onClick={() => handleChangeCampus("서울캠퍼스")}>서울캠퍼스</li>
                  <li onClick={() => handleChangeCampus("대전캠퍼스")}>대전캠퍼스</li>
                  <li onClick={() => handleChangeCampus("광주캠퍼스")}>광주캠퍼스</li>
                  <li onClick={() => handleChangeCampus("구미캠퍼스")}>구미캠퍼스</li>
                  <li onClick={() => handleChangeCampus("부울경캠퍼스")}>부울경캠퍼스</li>
                </ul>
              )}
            </div>
          </div>
        </div>

        <div className={`${styles.item} ${styles.jobNcam}`}>
          <div>
            <span className={styles.subTitle}><img src="/images/status.png" />취업상태</span>
            <div className={styles.customDropdown} ref={dropdownRefJobSearch}>
              {/* 드롭다운 헤더 */}
              <div
                className={`${styles.dropdownHeader} ${isOpenJobSearch ? styles.dropdownHeaderOpen : ""}`}
                onClick={() => { setIsOpenJobSearch(!isOpenJobSearch); setIsOpenDomain(false); setIsOpenCohort(false); setIsOpenCampus(false); }} // 열림/닫힘 상태 토글
              >
                {profile.jobSearch ? "취업준비중" : "재직중"}
                <img src="/images/드롭다운화살표.png" alt="그림" className={`${styles.arrow} ${isOpenJobSearch ? styles.up : styles.down}`} />
              </div>

              {/* 드롭다운 옵션 목록 */}
              {isOpenJobSearch && (
                <ul className={styles.dropdownOptions}>
                  <li onClick={() => handleChangeJobSearch("true")}>취업준비중</li>
                  <li onClick={() => handleChangeJobSearch("false")}>재직중</li>
                </ul>
              )}
            </div>
          </div>

          <div>
            <span className={styles.subTitle}><img src="/images/company.png" />회사명</span>
            <input type="text" name="company" value={profile.jobSearch ? "" : profile.company} onChange={handleChange} disabled={profile.jobSearch} />
          </div>

          <div>
            <span className={styles.subTitle}>회사명 공개여부</span>
            <div className={styles.onoff}>
              <div
                className={profile.jobSearch ? styles.disable : (profile.companyBuild ? styles.active : styles.inactive)}
                onClick={!profile.jobSearch ? () => setProfile((prev) => ({ ...prev, companyBuild: true })) : undefined}
              >공개</div>
              <div
                className={profile.jobSearch ? styles.disable : (profile.companyBuild ? styles.inactive : styles.active)}
                onClick={!profile.jobSearch ? () => setProfile((prev) => ({ ...prev, companyBuild: false })) : undefined}
              >비공개</div>
            </div>
          </div>
        </div>
      </div>
      <div className={styles.bottom}>
        <div className={styles.goLogin} onClick={submit}>로그인 화면으로</div>
        <div className={styles.submit} onClick={submit}>회원가입</div>
      </div>
    </div>
  );
}

export default NewSignup;
