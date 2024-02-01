import { createContext, useContext, useEffect, useMemo, useState } from "react";
import axios from "axios";
import { useAuth } from "./authProvider";

const UserInfoContext = createContext();

const UserInfoProvider = ({ children }) => {
  const [userInfo, setUserInfo] = useState(() => {
    const savedUser = localStorage.getItem("user");
    return savedUser ? JSON.parse(savedUser) : null;
  });

  useEffect(() => {
    if (userInfo) {
      localStorage.setItem("user", JSON.stringify(userInfo));
      console.log("saving userinfo");
    } else {
      localStorage.removeItem("user");
    }
  }, [userInfo]);

  const contextValue = useMemo(
    () => ({
      userInfo,
      setUserInfo,
    }),
    [userInfo],
  );
  return (
    <UserInfoContext.Provider value={contextValue}>
      {children}
    </UserInfoContext.Provider>
  );
};
export const useUserInfo = () => {
  return useContext(UserInfoContext);
};
export default UserInfoProvider;
