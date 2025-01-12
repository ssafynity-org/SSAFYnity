import React from 'react';
import Lottie from 'react-lottie';

const defaultOptions = (path) => ({
  loop: false,
  autoplay: true, 
  animationData: require('../components/loadingcheck.json'),
  rendererSettings: {
    preserveAspectRatio: 'xMidYMid slice'
  }
});

function LottieAnimation({ lottiePath, height, width }) {
  return <Lottie options={defaultOptions(lottiePath)} height={height} width={width} />;
}

export default LottieAnimation;