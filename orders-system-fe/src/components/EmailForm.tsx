interface IEmailForm {
  handleSubmit: (event: React.FormEvent<HTMLFormElement>) => void;
  handleChange: (event: React.ChangeEvent<HTMLSelectElement>) => void;
  handleInputChange: (
    event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => void;
  handleTextAreaChange: (event: React.ChangeEvent<HTMLTextAreaElement>) => void;
  emailSent: boolean;
}

const EmailForm = (props: IEmailForm) => {
  const {
    handleSubmit,
    handleChange,
    handleInputChange,
    handleTextAreaChange,
    emailSent,
  } = props;

  return (
    <form onSubmit={handleSubmit}>
      {emailSent ? (
        <div className="form-content">
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
      ) : (
        <div className="form-content">
          <div className="input-row subject">
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
      )}
    </form>
  );
};

export default EmailForm;
