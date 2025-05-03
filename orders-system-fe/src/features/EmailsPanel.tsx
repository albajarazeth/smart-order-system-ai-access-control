import EmailPreview from "../components/EmailPreview";
import emailsData from "../utilities/emails.json";
import "./EmailsPanel.scss";

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
