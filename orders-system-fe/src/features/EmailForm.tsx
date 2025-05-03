import { useContext } from "react";
import "./EmailForm.scss";
import { OrderSystemContext } from "../App";

const EmailForm = () => {
  const { userEmail, setUserEmail } = useContext(OrderSystemContext);

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    console.log("Email sent", userEmail);
    event.preventDefault();
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
    <form onSubmit={handleSubmit}>
      <div className="form-content">
        <div className="input-row">
          <label htmlFor="subject">Subject:</label>

          <select onChange={handleChange} id="order-type" name="order-type">
            <option value="">Select Order Type</option>
            <option value="inquiry">Inquiry</option>
            <option value="purchase">Purchase</option>
            <option value="customer-support">Customer Support</option>
          </select>
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
        <label htmlFor="body">Message:</label>
        <textarea
          onChange={(e) => handleTextAreaChange(e)}
          id="body"
          name="body"
          required
        ></textarea>

        <div className="button-row">
          <button type="submit">Send Email</button>
        </div>
      </div>
    </form>
  );
};
export default EmailForm;
