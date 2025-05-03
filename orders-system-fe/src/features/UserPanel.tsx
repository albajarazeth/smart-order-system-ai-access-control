import EmailsSection from "./EmailsSection";
import PartsCatalogue from "./PartsCatalogue";
import "./UserPanel.scss";

const UserPanel = () => {
  return (
    <div className="user-panel">
      <div className="user-panel-container">
        <PartsCatalogue />
        <EmailsSection />
      </div>
    </div>
  );
};
export default UserPanel;
