import React, { useState, useCallback, useRef } from "react";
import Cropper from "react-easy-crop";
import getCroppedImg from "../utils/cropImageHelper";

const ImageCropper = ({ imageSrc, onCropComplete, onCancel }) => {
  const [crop, setCrop] = useState({ x: 0, y: 0 });
  const [zoom, setZoom] = useState(1);
  const [croppedAreaPixels, setCroppedAreaPixels] = useState(null);
  const imgInput = useRef(null); // 파일 업로드를 위한 ref

  const onCropCompleteHandler = useCallback((croppedArea, croppedAreaPixels) => {
    setCroppedAreaPixels(croppedAreaPixels);
  }, []);

  const handleCrop = async () => {
    if (!imageSrc || !croppedAreaPixels) return;
    const croppedImage = await getCroppedImg(imageSrc, croppedAreaPixels);
    onCropComplete(croppedImage);
  };

  return (
    <div className="cropper-modal"> {/* ✅ 모달 배경 */}
      <div className="cropper-content"> {/* ✅ 크롭 UI 컨테이너 */}
        <h3>Adjust your image</h3>
        <div className="cropper-wrapper">
          <Cropper
            image={imageSrc}
            crop={crop}
            zoom={zoom}
            aspect={1}
            onCropChange={setCrop}
            onZoomChange={setZoom}
            onCropComplete={onCropCompleteHandler}
          />
          {/* ✅ 버튼을 크롭 UI 내부에 배치 */}
          <div className="cropper-buttons">
            <button className="crop-button" onClick={handleCrop}>Crop & Save</button>
            <button className="cancel-button" onClick={onCancel}>Cancel</button>
            <button className="upload-button" onClick={() => imgInput.current.click()}></button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ImageCropper;
