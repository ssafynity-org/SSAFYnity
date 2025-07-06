import { useState, useRef } from "react";

export const useHoverAnimation = () => {
    const [isHovered, setIsHovered] = useState(false); // 호버했는지
    const [isAnimating, setIsAnimating] = useState(false); // 애니메이션 진행 중인지
    const elementRef = useRef<HTMLDivElement>(null); // 마우스 호버 시 애니메이션 적용을 위한 ref

    // 마우스 호버 시 애니메이션 적용
    const handleMouseEnter = () => {
        if (!isAnimating) {
            setIsHovered(true);
            setIsAnimating(true);
        }
    };

    // 마우스가 헤더에서 벗어나면 애니메이션 종료
    const handleMouseLeave = () => {
        if (isAnimating) return;
        setIsHovered(false);
    };

    // 애니메이션 종료 시 애니메이션 상태 초기화
    const handleAnimationEnd = () => {
        setIsAnimating(false);
        setIsHovered(false);
    };

    return {
        isHovered,
        isAnimating,
        elementRef,
        handleMouseEnter,
        handleMouseLeave,
        handleAnimationEnd,
    };
};
