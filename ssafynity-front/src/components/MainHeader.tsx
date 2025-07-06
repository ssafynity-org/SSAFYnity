import Link from "next/link";
// import Image from "next/image";
import styles from "./MainHeader.module.css";
import GlassBox from "./GlassBox";

export default function MainHeader() {
    console.log("MainHeader");
    return (
        <div className={styles.headerContainer}>
            <GlassBox className={styles.header} animationType="2">
                <div className={styles.headerTitle}>SSAFYNITY</div>
                <div className={styles.headerButtons}>
                    <div>
                        {/* <Image
                        src="icons/moon.svg"
                        alt="moon"
                        width={24}
                        height={24}
                    /> */}
                        {/* <Image
                            src="icons/sun.svg"
                            alt="sun"
                            width={24}
                            height={24}
                        />
                        <Image
                            src="icons/system.svg"
                            alt="system"
                            width={24}
                            height={24}
                        /> */}
                    </div>
                    <Link href="/login" className={styles.headerButton}>
                        로그인
                    </Link>
                    <Link href="/signup" className={styles.headerButton}>
                        회원가입
                    </Link>
                </div>
            </GlassBox>
        </div>
    );
}
