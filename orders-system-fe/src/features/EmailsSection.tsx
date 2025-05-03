import { useContext, useState } from "react";
import "./EmailForm.scss";
import { OrderSystemContext } from "../App";
import EmailForm from "../components/EmailForm";
import "./EmailsSection.scss";
import axios from "axios";
import { URL } from "../utilities/constants";
import EmailPreview from "../components/EmailPreview";
import Email from "./Email";

const EmailThread = (userEmail: any) => {
  return <Email userEmail={userEmail} />;
};

const EmailsSection = () => {
  const { userEmail, setUserEmail } = useContext(OrderSystemContext);
  const [emailSent, setEmailSent] = useState(false);

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    try {
      axios.post(URL, userEmail);
      setEmailSent(true);
    } catch (error) {
      console.error("Error sending email:", error);
    }
  };

  const handleChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const selectedValue = event.target.value;
    setUserEmail((prev: any) => ({
      ...prev,
      subject: selectedValue,
    }));
  };

  const handleInputChange = (
    event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const { name, value } = event.target;
    setUserEmail((prev: any) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleTextAreaChange = (
    event: React.ChangeEvent<HTMLTextAreaElement>
  ) => {
    const { name, value } = event.target;
    setUserEmail((prevState: any) => ({
      ...prevState,
      [name]: value,
    }));
  };

  return (
    <div className="email-section-container">
      {emailSent && <EmailThread userEmail={userEmail} />}
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
