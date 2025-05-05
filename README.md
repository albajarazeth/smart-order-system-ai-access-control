# 🚀 SkyRulers – AI-Powered Drone Parts Ordering with Fine-Grained Access Control

## 🔐 Authorization with Permit.io

SkyRulers integrates **[Permit.io](https://www.permit.io/)** for robust **RBAC (Role-Based Access Control)** to securely manage actions performed by human users and AI agents in the ordering workflow.

---

## 👤 User Authorization Model

### 🧑‍💼 Supervisor *(Auto-approved for this demo)*

#### **Permissions:**
- Approve or reject part substitutions.
- View all emails and order history.

#### **Capabilities:**
- **Manage Orders:** Approve or reject orders and substitutions.
- **Review Substitutions:** Approve or modify suggested part substitutions.
- **Monitor Emails:** Track all emails related to orders and substitutions.
- **Audit Logs:** Access logs for full accountability.

---

### 🧑 Client (User)

#### **Permissions:**
- Approve quotes.
- Place orders.
- Interact with order-related emails.

#### **Restrictions:**
- ❌ Cannot modify orders or substitutions directly.

#### **Capabilities:**
- **Approve Quotes:** Accept or reject final quotes sent by the system.
- **Track Orders:** View status and history of past orders.
- **Email Interaction:** Respond to order and quote emails.

---

## 🤖 AI Agent Authorization Model

Each AI agent in the system has a distinct role with tightly scoped permissions.

---

### 🧠 **PartPickerAI**
Parses emails and suggests part substitutions.

#### **Permissions:**
- `read_emails`
- `suggest_substitution`
- `send_emails`

---

### 🧠 **ApprovalAgent**
Handles approval workflow initiation and communication.

#### **Permissions:**
- `read_emails`
- `suggest_substitution`
- `initiate_approval_workflow`
- `send_emails`

---

### 🤖 **BuilderBot**
Finalizes orders after necessary approvals.

#### **Permissions:**
- `read_emails`
- `send_emails`
- `create_order`
- `modify_orders`

---

### 🧩 AI Capabilities Explained
| Capability                 | Description                                                                 |
|----------------------------|-----------------------------------------------------------------------------|
| `read_emails`              | Can access and parse order-related emails.                                  |
| `send_emails`              | Sends notifications: substitutions, approvals, and confirmations.          |
| `suggest_substitution`     | Proposes alternative parts when requested ones are unavailable.             |
| `create_order`             | Finalizes and submits orders post-approval.                                 |
| `initiate_approval_workflow` | Triggers supervisor/client approval workflows.                            |

---

## 🔁 Workflow Diagram

[📊 View the full workflow diagram →](https://app.diagrams.net/#G1E_f0FxF3U4XU23mvYPOPye0IeNoy8cXz#%7B%22pageId%22%3A%22jNlKdp63iCFvfioK3i8F%22%7D)

---

## ✅ Example Authorization Workflow

1. **Order Request**
   - `PartPickerAI` receives an email → parses it and suggests substitutions.
   - `ApprovalAgent` reads and sends the substitution to the Supervisor.

2. **Supervisor Approval** *(Auto-approved in this build)*
   - Approves or rejects substitutions.
   - Approved orders move forward.

3. **Client Approval**
   - `BuilderBot` generates a quote and sends it via email.
   - Client must approve it.

4. **Finalize Order**
   - Once approved, `BuilderBot` finalizes the order and sends confirmation.

---

## ⚙️ Permit.io Implementation Summary

> Note: Due to time constraints and limited Java SDK documentation, some features are pending full integration.

### ✅ What’s Implemented:
- Basic Permit.io integration in AI agent services.
- Authorization checks for inventory access.

### 🔧 Planned Features:
- Full enforcement of role-based permissions.
- Real-time permission updates from Permit.io dashboard.
- Dynamic AI access control.
- Detailed audit logging for AI and human actions.

---

## 📌 Permit.io Setup Steps (Summary)

1. **Create Roles in Permit.io Dashboard**:
   - `supervisor`, `client`, `PartPickerAI`, `ApprovalAgent`, `BuilderBot`

2. **Define Permissions**:
   - Use Permit.io’s policy builder to assign granular capabilities.

3. **Enforce Permissions in Code**:
   - Using Permit SDK’s `.check()` method to gate critical actions.

4. **Enable Audit Logs**:
   - Activate logging for transparency and traceability.

---

## 📬 Feedback & Contributions

Pull requests welcome! If you have suggestions or improvements for integrating advanced AI authorization in Java using Permit.io, feel free to contribute.

---

### 🔗 Related Resources

- [Permit.io Java SDK Docs](https://docs.permit.io/integrate/sdks/backend/java)
- [Spring Boot Starter Guide](https://spring.io/guides/gs/spring-boot/)
- [Gemini AI for Java](https://cloud.google.com/vertex-ai/docs/generative-ai/start/quickstarts/quickstart-java)

