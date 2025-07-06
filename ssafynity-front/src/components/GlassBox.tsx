"use client";

import { useHoverAnimation } from "@/hooks/useHoverAnimation";
import styles from "./GlassBox.module.css";

export default function GlassBox({
    children,
    className,
    animationType = "1",
}: {
    children: React.ReactNode;
    className?: string;
    animationType?: "1" | "2";
}) {
    const {
        isHovered,
        elementRef,
        handleMouseEnter,
        handleMouseLeave,
        handleAnimationEnd,
    } = useHoverAnimation();
    return (
        <div
            ref={elementRef}
            className={`${styles.glassBox} ${
                isHovered
                    ? animationType === "1"
                        ? styles.hovered
                        : styles.hovered2
                    : ""
            } ${className}`}
            onMouseEnter={handleMouseEnter}
            onMouseLeave={handleMouseLeave}
            onAnimationEnd={handleAnimationEnd}
        >
            {children}
        </div>
    );
}
