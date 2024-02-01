import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import AuthProvider from "./providers/authProvider";
import Router from "./Router";
import UserInfoProvider from "./providers/userInfoProvider";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <AuthProvider>
    <UserInfoProvider>
      <Router />
    </UserInfoProvider>
  </AuthProvider>,
);
