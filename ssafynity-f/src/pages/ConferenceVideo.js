import { useParams } from "react-router-dom";

const ConferenceVideo = () => {   // 컴포넌트 파일명/이름 변경
  const { videoId } = useParams(); // URL에서 videoId 추출

  console.log("들어왔음");
  return (
    <div className="video-container">
      <h2>컨퍼런스 영상</h2> {/* 화면에 표시되는 제목 */}
      <iframe
        width="960"
        height="540"
        src={`https://www.youtube.com/embed/${videoId}?autoplay=1&mute=1`}
        title="YouTube video player"
        frameBorder="0"
        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
        allowFullScreen
        style={{ borderRadius: "20px" }}
      />
    </div>
  );
};

export default ConferenceVideo;
