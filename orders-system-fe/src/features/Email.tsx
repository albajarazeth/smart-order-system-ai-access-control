import { FaUserCircle } from "react-icons/fa";
import "./Email.scss";
const Email = (user: any) => {
  const stored = localStorage.getItem("userInfo");
  const userInfo = stored ? JSON.parse(stored) : null;

  console.log("MY USER --", userInfo);
  return (
    <div className="email-container">
      <div className="email-content">
        <div className="subject">{userInfo?.subject}</div>
        <div className="sender">
          <FaUserCircle size={20} />{" "}
          <div className="info">
            <div>{userInfo?.name}</div>
            <div className="from">&lt;{userInfo?.emailAddress}&gt;</div>
          </div>
        </div>
        <div className="body">
          {/* {email.body.split("\n").map((line, index) => (
          <React.Fragment key={index}>
            {line}
            <br />
          </React.Fragment>
        ))} */}
          {userInfo?.body}
        </div>
      </div>
    </div>
  );
};

export default Email;
