import Link from "next/link";
import MainHeader from "@/components/MainHeader";
import styles from "./page.module.css";

export default function Home() {
    return (
        <>
            <MainHeader />
            <div className={styles.titleContainer}>
                <h1>SSAFY만을 위한 커뮤니티 플랫폼</h1>
            </div>
            <Link href="/login" className={styles.startButton}>
                시작하기
            </Link>
        </>
    );
}
