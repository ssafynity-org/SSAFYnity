"use client";
import { apiFetch } from "@/lib/apiClient";
import { useState } from "react";
import Link from "next/link";
import styles from "./login.module.css";
import GlassBox from "@/components/GlassBox";

export default function LoginPage() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [isEmailFocused, setIsEmailFocused] = useState(false);
    const [isPasswordFocused, setIsPasswordFocused] = useState(false);
    const [error, setError] = useState("");
    const [isLoading, setIsLoading] = useState(false);

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        setError("");
        setIsLoading(true);

        try {
            const response = (await apiFetch("/auth/login", {
                method: "POST",
                body: JSON.stringify({ email, password }),
            })) as Response;

            if (response.ok) {
                // 로그인 성공 처리
                console.log("로그인 성공");
            } else {
                const data = await response.json();
                setError(data.message || "로그인에 실패했습니다.");
            }
        } catch {
            setError("로그인에 실패했습니다.");
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <div className={styles.container}>
            <GlassBox className={styles.loginCard}>
                <div className={styles.header}>
                    <h1 className={styles.title}>SSAFYNITY</h1>
                    <p className={styles.subtitle}>
                        SSAFY 멤버들을 위한 커뮤니티
                    </p>
                </div>

                <form onSubmit={handleSubmit} className={styles.form}>
                    <div className={styles.inputGroup}>
                        <input
                            type="email"
                            id="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            onFocus={() => setIsEmailFocused(true)}
                            onBlur={() => setIsEmailFocused(email.length > 0)}
                            className={styles.input}
                            required
                        />
                        <label
                            htmlFor="email"
                            className={`${styles.label} ${
                                isEmailFocused ? styles.labelFloating : ""
                            }`}
                        >
                            이메일
                        </label>
                    </div>

                    <div className={styles.inputGroup}>
                        <input
                            type="password"
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            onFocus={() => setIsPasswordFocused(true)}
                            onBlur={() =>
                                setIsPasswordFocused(password.length > 0)
                            }
                            className={styles.input}
                            required
                        />
                        <label
                            htmlFor="password"
                            className={`${styles.label} ${
                                isPasswordFocused ? styles.labelFloating : ""
                            }`}
                        >
                            비밀번호
                        </label>
                    </div>

                    {error && (
                        <div className={styles.errorMessage}>{error}</div>
                    )}

                    <button
                        type="submit"
                        className={styles.loginButton}
                        disabled={isLoading}
                    >
                        {isLoading ? "로그인 중..." : "로그인"}
                    </button>

                    <div className={styles.signupSection}>
                        <p className={styles.signupText}>
                            아직 계정이 없으신가요?
                        </p>
                        <Link href="/signup" className={styles.signupButton}>
                            회원가입
                        </Link>
                    </div>
                </form>
            </GlassBox>
        </div>
    );
}
