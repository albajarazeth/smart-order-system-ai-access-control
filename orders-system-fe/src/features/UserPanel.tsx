import EmailForm from "./EmailForm";
import PartsCatalogue from "./PartsCatalogue";
import "./UserPanel.scss";

const UserPanel = () => {
  return (
    <div className="user-panel">
      <div className="user-panel-container">
        <PartsCatalogue />
        <EmailForm />
      </div>
    </div>
  );
};
export default UserPanel;
