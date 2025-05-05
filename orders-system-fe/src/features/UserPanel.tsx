import { useNavigate } from "react-router-dom";
import Nav from "../components/Nav";
import EmailsSection from "./EmailsSection";
import PartsCatalogue from "./PartsCatalogue";
import "./UserPanel.scss";

const UserPanel = () => {
  const navigate = useNavigate();
  const deleteLocalStorage = () => {
    localStorage.removeItem("userInfo");
    navigate("/");
  };
  return (
    <div className="user-panel">
      <Nav name="Purchase Order" />
      <button onClick={deleteLocalStorage} className="logout-button">
        Logout
      </button>
      <div className="user-panel-container">
        <PartsCatalogue />
        <EmailsSection />
      </div>
    </div>
  );
};
export default UserPanel;
