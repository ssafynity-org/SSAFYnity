import "../styles/ConferenceHeader.css";

function ConferenceHeader() {
    return (
        <div className="conferenceHeader">
            <img 
                src="/images/tech-folder.png" 
                alt="Logo" 
                style={{ width: "100px", height: "auto" }}
            />
            <div className="conferenceHeader-discription">
                <h1>Conference</h1>
            </div>
        </div>
    )
}

export default ConferenceHeader;
