# SkyRulers – AI-Powered Parts Ordering with Access Control
Authorization with Permit.io
User Authorization Model
The application implements the following user authorization model, ensuring that only authorized personnel can interact with specific tasks like approving orders and handling email responses.
Supervisor (Due to time constraints this was auto approved)
Permissions:


Approve or reject suggestions when order items need to be substituted.


View all emails and order history to keep track of order status and history.


Capabilities:


Manage orders: Approve or reject orders and substitutions.


Approve/modify part substitutions: Review and modify suggested part substitutions.


View email interactions: Track all emails related to orders and substitutions.


Access logs and audit trails: Review detailed logs for accountability.


Client (User)
Permissions:


Can approve quotes, place orders, and interact with email notifications.


Cannot modify any order details or substitutions directly.


Capabilities:


Approve final quotes: Approve the final quote sent by the BuilderBot or ApprovalAgent.


View order status and history: Check the current status and history of their orders.


Interact with email notifications: Approve/reject quotes via email.



AI Authorization Model
The application implements the following AI authorization model to control the access of different AI agents (like PartPickerAI, ApprovalAgent, and BuilderBot) based on their tasks. Each AI agent has specific roles and permissions for interacting with orders and email communications.
AI Agent Roles and Permissions
PartPickerAI
Role: Parses incoming emails and suggests part substitutions when needed.


Permissions:


read_emails: Can read incoming emails about part requests.


suggest_substitution: Can suggest substitutions for unavailable parts but cannot approve or finalize orders.


send_emails: Can send emails with part substitution suggestions.


ApprovalAgent
Role: Initiates the approval workflow for part substitutions and order approvals.


Permissions:


read_emails: Can read part request emails and substitutions.


suggest_substitution: Can suggest substitutions but requires human approval.


initiate_approval_workflow: Can trigger the approval process for supervisors or clients.


send_emails: Can send approval request notifications to supervisors/clients.


BuilderBot
Role: Finalizes and submits orders after approvals.


Permissions:


read_emails: Can read finalized orders and approvals.


send_emails: Can send final order confirmation emails to clients.


create_order: Can create and place orders after all necessary approvals.


modify_orders: Can update orders after part substitution approval.



AI Capabilities
read_emails: Ability to read incoming emails about order requests, substitutions, or approvals.


send_emails: Ability to send email notifications like part substitution requests, approval requests, or order confirmations.


suggest_substitution: Ability to suggest substitutions for unavailable parts.


create_order: Ability to create a new order or finalize an existing one after all necessary approvals.


initiate_approval_workflow: Ability to trigger the approval process, requesting supervisor or client action.


Diagram of Workflows:[Here](https://app.diagrams.net/#G1E_f0FxF3U4XU23mvYPOPye0IeNoy8cXz#%7B%22pageId%22%3A%22jNlKdp63iCFvfioK3i8F%22%7D)

Example Workflow with Authorization
1. Order Request and Email Parsing
PartPickerAI receives an email about an order request, reads it, and suggests a part substitution if the requested part is unavailable.


The ApprovalAgent reads the email and suggests the substitution to the Supervisor for approval.


2. Supervisor Approval (All orders approved due to time)
The Supervisor receives an approval email from the ApprovalAgent.


The Supervisor can approve or reject the part substitution.


If approved, the order moves to the next step in the process.


3. Client Quote Approval
After the part substitution is approved, the BuilderBot generates the final quote for the client.


The Client receives the email with the quote and must approve it before the order is placed.


4. Finalizing the Order
After the Client approves the final quote, the BuilderBot finalizes the order and sends a confirmation email to the client.


This marks the completion of the order process.



Process to implement AI Authorization with Permit.io
Set Up Permit.io:(Due to limited Java documentation much of this was not able to be implemented however we were able to apply some of Permit.io’s functionality)


Configure roles and permissions for both human users (Supervisor, Client, Admin) and AI agents (PartPickerAI, ApprovalAgent, BuilderBot) within Permit.io.


Define the appropriate permissions for each role.


Define AI Permissions:


Set AI capabilities based on their roles, ensuring that each AI agent can only perform the tasks it's authorized to do (e.g., PartPickerAI can suggest substitutions, but only the Supervisor can approve them).


Real-Time Permissions Management:


Use Permit.io to dynamically manage permissions and access controls for users and AI agents in real-time, ensuring compliance and security without disruptions.


Audit and Logs:


Enable detailed audit logs for both user actions and AI agent activities, ensuring traceability and accountability in the order approval process.



