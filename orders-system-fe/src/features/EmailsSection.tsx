import { useContext, useState } from "react";
import "./EmailForm.scss";
import { OrderSystemContext } from "../App";
import EmailForm from "../components/EmailForm";
import "./EmailsSection.scss";
import axios from "axios";
import { URL } from "../utilities/constants";
import Email from "./Email";

const EmailThread = ({ user }: any) => {
  return <Email userEmail={user} />;
};

const EmailsSection = () => {
  const { userInfo, setUserInfo } = useContext(OrderSystemContext);
  const [emailSent, setEmailSent] = useState(false);
  const [user, setUser] = useState<any>(null);

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const url = URL + `email?userId=${userInfo.id}`;

    try {
      await axios.post(url, {
        subject: userInfo.subject,
        emailAddress: userInfo.emailAddress,
        body: userInfo.body,
        isSentFromAgent: false,
      });

      const updatedUser = {
        ...userInfo,
        subject: userInfo.subject,
      };
      localStorage.setItem("userInfo", JSON.stringify(updatedUser));
      setUser(updatedUser);

      setEmailSent(true);
    } catch (error) {
      console.error("Error sending email:", error);
    }
  };

  const handleChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const selectedValue = event.target.value;
    setUserInfo((prev: any) => ({
      ...prev,
      subject: selectedValue,
    }));
  };

  const handleInputChange = (
    event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const { name, value } = event.target;
    setUserInfo((prev: any) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleTextAreaChange = (
    event: React.ChangeEvent<HTMLTextAreaElement>
  ) => {
    const { name, value } = event.target;
    setUserInfo((prevState: any) => ({
      ...prevState,
      [name]: value,
    }));
  };

  return (
    <div className="email-section-container">
      {emailSent && <EmailThread user={user} />}
      <div className="emails-section">
        <EmailForm
          emailSent={emailSent}
          handleSubmit={handleSubmit}
          handleChange={handleChange}
          handleInputChange={handleInputChange}
          handleTextAreaChange={handleTextAreaChange}
        />
      </div>
    </div>
  );
};

export default EmailsSection;
