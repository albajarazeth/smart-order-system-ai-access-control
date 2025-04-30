import "./App.scss";
import ActionsPanel from "./features/ActionsPanel";
import EmailsPanel from "./features/EmailsPanel";

function App() {
  return (
    <div className="order-system-app">
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
    </div>
  );
}

export default App;
