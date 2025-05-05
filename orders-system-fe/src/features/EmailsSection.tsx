import { useContext, useEffect, useState } from "react";
import "./EmailForm.scss";
import { OrderSystemContext } from "../App";
import EmailForm from "../components/EmailForm";
import "./EmailsSection.scss";
import axios from "axios";
import { URL } from "../utilities/constants";
import Email from "./Email";

const EmailThread = ({ emailData }: any) => {
  return <Email emailData={emailData} />;
};

const EmailsSection = () => {
  const { userInfo, setUserInfo } = useContext(OrderSystemContext);
  const [emailSent, setEmailSent] = useState(false);
  const [currentEmail, setCurrentEmail] = useState({});
  interface AgentEmail {
    body?: string;
    isSentFromAgent?: boolean;
  }

  const [agentEmail, setAgentEmail] = useState<AgentEmail>({});
  const [emails, setEmails] = useState<any[]>([]);
  const [showFiltered, setShowFiltered] = useState(false);

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const createEmailUrl = URL + `email?userId=${userInfo.id}`;

    try {
      if (agentEmail) {
        try {
          await axios.post(createEmailUrl, {
            subject: userInfo.subject,
            emailAddress: userInfo.emailAddress,
            body: userInfo.body + agentEmail?.body,
            isSentFromAgent: false,
          });
        } catch (error) {
          console.error("Error sending email:", error);
        }
      } else {
        await axios.post(createEmailUrl, {
          subject: userInfo.subject,
          emailAddress: userInfo.emailAddress,
          body: userInfo.body,
          isSentFromAgent: false,
        });

        setUserInfo((prev: any) => ({
          ...prev,
          subject: userInfo.subject,
        }));
      }
      const fetchedEmails = await fetchEmails();
      console.log("Fetched Emails --", fetchedEmails?.user?.emails);

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

  const fetchEmails = async () => {
    const getEmailsUrl = URL + `user/${userInfo.id}/emails`;
    console.log("Get Emails URL --", getEmailsUrl);

    try {
      const response = await axios.get(getEmailsUrl);
      return response.data;
    } catch (error) {
      console.error("Error fetching emails:", error);
      return [];
    }
  };

  useEffect(() => {
    if (!emailSent) return;

    const getEmails = async () => {
      setCurrentEmail(userInfo);
      localStorage.setItem("userInfo", JSON.stringify(userInfo));

      const fetchedEmails = await fetchEmails();
      if (fetchedEmails.length > 0) {
        const agent = fetchedEmails[0]?.user?.emails[1];
        setAgentEmail(agent);
        setEmails((prev) => [...prev, userInfo, agent]);
      }
    };

    getEmails();
  }, [emailSent]);

  useEffect(() => {
    const timer = setTimeout(() => {
      setShowFiltered(true);
    }, 1200); // 3 seconds

    return () => clearTimeout(timer); // Cleanup
  }, [emailSent]);

  const filteredEmails = emails.filter((email) => email?.role !== "USER");

  return (
    <div className="email-section-container">
      {emailSent && <EmailThread emailData={currentEmail} />}
      {showFiltered &&
        filteredEmails.map((email, index) => (
          <>
            <EmailThread emailData={email} />
          </>
        ))}
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
