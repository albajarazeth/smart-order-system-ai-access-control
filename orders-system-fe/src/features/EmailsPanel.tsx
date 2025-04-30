import emailsData from "../utilities/emails.json";
import "./EmailsPanel.scss";

const EmailPreview = ({ email }: { email: any }) => {
  return (
    <div className="preview-container">
      <div className="name">{email.name}</div>
      <div className="subject-container">
        <div>{email.subject}</div>
      </div>
      <div>02/02/20</div>
    </div>
  );
};

const EmailsPanel = () => {
  return (
    <div className="email-list">
      {emailsData.emails.map((email) => (
        <EmailPreview email={email} />
      ))}
    </div>
  );
};

export default EmailsPanel;
