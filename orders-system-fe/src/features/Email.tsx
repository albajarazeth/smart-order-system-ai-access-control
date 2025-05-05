import { FaUserCircle } from "react-icons/fa";
import "./Email.scss";
import React from "react";
const Email = (emailData: any) => {
  const userInfo = emailData.emailData;
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
          {userInfo?.isSentFromAgent &&
            userInfo?.body.split("\n").map((line: any, index: any) => (
              <React.Fragment key={index}>
                {line}
                <br />
              </React.Fragment>
            ))}
          {!userInfo?.isSentFromAgent && userInfo.body}
        </div>
      </div>
    </div>
  );
};

export default Email;
