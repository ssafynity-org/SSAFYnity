import { useParams } from "react-router-dom";

const ConferenceVideo = () => {
  const { videoId } = useParams();

  return (
    <div className="video-page" style={{ maxWidth: "960px", margin: "0 auto" }}>
      <h2>컨퍼런스 영상</h2>
      <iframe
        width="960"
        height="540"
        src={`https://www.youtube.com/embed/${videoId}?autoplay=1`}
        title="YouTube video player"
        frameBorder="0"
        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
        allowFullScreen
        style={{ borderRadius: "20px", width: "100%" }}
      />
    </div>
  );
};

export default ConferenceVideo;
