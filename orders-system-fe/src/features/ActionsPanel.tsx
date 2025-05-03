import "./ActionsPanel.scss";
import Alert from "../components/Alert";
import Email from "./Email";
import emailsData from "../utilities/emails.json";

const ActionsPanel = () => {
  return (
    <div className="actions-panel-container">
      <Email email={emailsData.emails[0]} />
      <Alert />
    </div>
  );
};

export default ActionsPanel;
