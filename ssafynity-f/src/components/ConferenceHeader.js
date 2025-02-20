import "../styles/ConferenceHeader.css";

function conferenceHeader() {

    return (
        <div className="conferenceHeader">
            <img src="/images/bigLogo.png" alt="Logo"/>
            <div className="conferenceHeader-discription">
                <h1>다양한 기술 컨퍼런스를 확인해보세요</h1>
                <p>성장을 위한 공간입니다.</p>
            </div>
        </div>
    )
}

export default conferenceHeader;