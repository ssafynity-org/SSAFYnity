import styles from "./AnimatedBackground.module.css";

export default function AnimatedBackground() {
    return (
        <>
            <div className={styles.blobContainer}>
                <div className={styles.blob}></div>
            </div>
            <div className={styles.blobTextContainerWrapper}>
                <div className={styles.blobTextContainer + " " + styles.textS1}>
                    S
                </div>
                <div className={styles.blobTextContainer + " " + styles.textS2}>
                    S
                </div>
                <div className={styles.blobTextContainer + " " + styles.textA1}>
                    A
                </div>
                <div className={styles.blobTextContainer + " " + styles.textF1}>
                    F
                </div>
                <div className={styles.blobTextContainer + " " + styles.textY1}>
                    Y
                </div>
            </div>
        </>
    );
}
