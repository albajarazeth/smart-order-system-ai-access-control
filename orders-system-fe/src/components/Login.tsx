import { useContext, useEffect, useState } from "react";
import "./Login.scss";
import { MdOutlineSettingsSystemDaydream } from "react-icons/md";
import { OrderSystemContext } from "../App";
import axios from "axios";
import { URL } from "../utilities/constants";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const { userInfo, setUserInfo } = useContext(OrderSystemContext);
  const [activeTab, setActiveTab] = useState("login");
  const navigate = useNavigate();

  const handleInputChange = (
    event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const { name, value } = event.target;
    setUserInfo((prev: any) => ({
      ...prev,
      [name]: value,
    }));
  };

  const onSubmitHandler = async (e: any) => {
    const url = URL + "user";

    e.preventDefault();
    if (activeTab === "login") {
    } else {
      try {
        let response = await axios.post(url, {
          name: userInfo.name,
          emailAddress: userInfo.emailAddress,
          password: userInfo.password,
          role: userInfo.role,
        });
        setUserInfo({ ...userInfo, id: response.data.id });
        navigate("/user");
      } catch (e) {
        console.error("Error sending email:", e);
      }
    }
  };

  useEffect(() => {
    localStorage.setItem("userInfo", JSON.stringify(userInfo));
  }, [onSubmitHandler]);

  const formSection = (type: any) => {
    switch (type) {
      case "login":
        return (
          <>
            <div className="input-row">
              <label htmlFor="emailAddress">Email:</label>
              <input
                onChange={(e) => handleInputChange(e)}
                type="emailAddress"
                id="emailAddress"
                name="emailAddress"
                required
              />
            </div>
            <div className="input-row">
              <label htmlFor="password">Password:</label>
              <input
                onChange={(e) => handleInputChange(e)}
                type="password"
                id="password"
                name="password"
                required
              />
            </div>
          </>
        );

      case "register":
        return (
          <>
            <div className="input-row">
              <label htmlFor="name">Name:</label>
              <input
                onChange={(e) => handleInputChange(e)}
                type="text"
                id="name"
                name="name"
                required
              />
            </div>
            <div className="input-row">
              <label htmlFor="emailAddress">Email:</label>
              <input
                onChange={(e) => handleInputChange(e)}
                type="emailAddress"
                id="emailAddress"
                name="emailAddress"
                required
              />
            </div>
            <div className="input-row">
              <label htmlFor="password">Password:</label>
              <input
                onChange={(e) => handleInputChange(e)}
                type="password"
                id="password"
                name="password"
                required
              />
            </div>
          </>
        );

      default:
        return null;
    }
  };

  return (
    <div className="login-container">
      <nav>
        <ul>
          <li>
            <MdOutlineSettingsSystemDaydream size={50} />
          </li>
          <li>AI Smart Order Systems</li>
        </ul>
      </nav>

      <div className="heading-container">
        <div>
          <h1>Let's Get Started!</h1>
        </div>

        <div className="login-form-cotainer">
          <div className="options">
            <div
              className={`${activeTab === "login" ? "active" : "inactive"}`}
              onClick={() => setActiveTab("login")}
            >
              Login
            </div>
            <div
              className={`${activeTab === "register" ? "active" : "inactive"}`}
              onClick={() => setActiveTab("register")}
            >
              Register
            </div>
          </div>
          <div className="login-form">
            <form onSubmit={(e) => onSubmitHandler(e)}>
              {formSection(activeTab)}
              <div>
                <button className="submit" type="submit">
                  Submit
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>

      <div className="footer">
        <div className="section">
          <h3>Built with</h3>
          <img src="https://www.permit.io/_next/image?url=%2F_next%2Fstatic%2Fmedia%2Flogo_nav.611e4f29.svg&w=640&q=75" />
        </div>
      </div>
    </div>
  );
};
export default Login;
