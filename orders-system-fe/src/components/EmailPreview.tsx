import "./EmailPreview.scss";

const EmailPreview = (userEmail: any) => {
  const email = userEmail?.userEmail.userEmail;
  console.log("EmailPreview", email);

  return (
    <div className="preview-container">
      {/* <div className="name">{emailAddress}</div> */}
      <div className="subject-container">{/* <div>{body}</div> */}</div>
      <div>02/02/20</div>
    </div>
  );
};

export default EmailPreview;
