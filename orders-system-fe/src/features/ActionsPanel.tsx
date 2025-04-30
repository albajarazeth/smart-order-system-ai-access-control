import emailsData from "../utilities/emails.json";
import { FaUserCircle } from "react-icons/fa";
import "./ActionsPanel.scss";
import React from "react";
import Alert from "../components/Alert";

const Email = () => {
  const email = emailsData.emails[0];
  return (
    <div className="email-container">
      <div className="subject">{email.subject}</div>
      <div className="sender">
        <FaUserCircle size={20} />{" "}
        <div className="info">
          <div>{email.name}</div>
          <div className="from">&lt;{email.from}&gt;</div>
        </div>
      </div>
      <div className="body">
        {email.body.split("\n").map((line, index) => (
          <React.Fragment key={index}>
            {line}
            <br />
          </React.Fragment>
        ))}
      </div>
    </div>
  );
};

const ActionsPanel = () => {
  return (
    <div className="actions-panel-container">
      <Email />
      <Alert />
    </div>
  );
};

export default ActionsPanel;
