import { FaUserCircle } from "react-icons/fa";

const Email = (email: any) => {
  const emailData = email?.userEmail?.userEmail;
  return (
    <div className="email-container">
      <div className="subject">{emailData?.subject}</div>
      <div className="sender">
        <FaUserCircle size={20} />{" "}
        <div className="info">
          {/* <div>{email.name}</div> */}
          <div className="from">&lt;{emailData?.emailAddress}&gt;</div>
        </div>
      </div>
      <div className="body">
        {/* {email.body.split("\n").map((line, index) => (
          <React.Fragment key={index}>
            {line}
            <br />
          </React.Fragment>
        ))} */}
        {email?.body}
      </div>
    </div>
  );
};

export default Email;
