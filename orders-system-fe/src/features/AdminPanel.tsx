import ActionsPanel from "./ActionsPanel";
import EmailsPanel from "./EmailsPanel";

const AdminPanel = () => {
  return (
    <>
      <div className="nav">
        <h1>AI Smart Order System</h1>

        <div className="switch-container">
          Switch to Admin Panel
          <div className="toggle-container">
            <div className="toggle-circle"></div>
          </div>
        </div>
      </div>
      <div className="order-system-container">
        <EmailsPanel />
        <ActionsPanel />
      </div>
    </>
  );
};
export default AdminPanel;
