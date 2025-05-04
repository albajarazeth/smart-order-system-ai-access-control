import { createContext, useState } from "react";
import "./App.scss";
import UserPanel from "./features/UserPanel";
import AdminPanel from "./features/AdminPanel";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./components/Login";

export const OrderSystemContext = createContext<any>(null);

interface IEmail {
  name: string;
  emailAddress: string;
  password: string;
  subject: string;
  body: string;
  role: string;
  id: any;
}

function App() {
  const [userInfo, setUserInfo] = useState<IEmail>({
    name: "",
    emailAddress: "",
    password: "",
    subject: "",
    body: "",
    role: "USER",
    id: null,
  });

  return (
    <OrderSystemContext.Provider value={{ userInfo, setUserInfo }}>
      <div className="order-system-app">
        <BrowserRouter>
          <Routes>
            <Route path="/" element={<Login />} />
            <Route path="/user" element={<UserPanel />} />
            <Route path="/admin" element={<AdminPanel />} />
          </Routes>
        </BrowserRouter>
      </div>
    </OrderSystemContext.Provider>
  );
}

export default App;
