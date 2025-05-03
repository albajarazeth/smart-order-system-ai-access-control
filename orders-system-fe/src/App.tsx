import { createContext, useState } from "react";
import "./App.scss";
import UserPanel from "./features/UserPanel";
import AdminPanel from "./features/AdminPanel";

export const OrderSystemContext = createContext<any>(null);

interface IEmail {
  emailAddress: string;
  subject: string;
  body: string;
}

function App() {
  const [userEmail, setUserEmail] = useState<IEmail>({
    emailAddress: "",
    subject: "",
    body: "",
  });

  return (
    <OrderSystemContext.Provider value={{ userEmail, setUserEmail }}>
      <div className="order-system-app">
        {/* <AdminPanel /> */}
        <UserPanel />
      </div>
    </OrderSystemContext.Provider>
  );
}

export default App;
