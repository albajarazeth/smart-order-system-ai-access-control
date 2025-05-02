import "./EmailForm.scss";

const EmailForm = () => {
  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    // Handle form submission logic here
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="form-content">
        <select id="order-type" name="order-type">
          <option value="">Select Order Type</option>
          <option value="inquiry">Inquiry</option>
          <option value="purchase">Purchase</option>
          <option value="customer-support">Customer Support</option>
        </select>
        <div className="input-row">
          <label htmlFor="email">Email:</label>
          <input type="email" id="email" name="email" required />
        </div>
        <div className="input-row">
          <label htmlFor="subject">Subject:</label>
          <input type="text" id="subject" name="subject" required />
        </div>
        <label htmlFor="message">Message:</label>
        <textarea id="message" name="message" required></textarea>

        <div className="button-row">
          <button type="submit">Send Email</button>
        </div>
      </div>
    </form>
  );
};
export default EmailForm;
