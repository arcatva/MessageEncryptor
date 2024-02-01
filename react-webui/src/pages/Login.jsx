import React, { useEffect, useState } from "react";
import axios from "axios";
import { useAuth } from "../providers/authProvider";
import Textfield from "../components/Textfield";
import { useNavigate } from "react-router-dom";
import Button from "../components/Button";
import Capsule from "../components/Capsule";
import typeWriter from "../utils/typeWriter";

const Login = () => {
  const { token, setToken } = useAuth();
  const navigate = useNavigate();
  const [capsuleText, setCapsuleText] = useState("");

  const [isErr, setIsErr] = useState(false);

  useEffect(() => {
    typeWriter(setCapsuleText, "Admin Login");
  }, []);

  useEffect(() => {
    if (token) {
      navigate("/", { replace: true });
    }
  }, [token]);

  const login = async () => {
    try {
      const email = document.getElementById("Email").value;
      if (!email) {
        console.error("Please input email");
        return;
      }
      //TODO: change urls

      // Get jwt-token
      const response = await axios.post("https://localhost/api/v1/login", {
        email: document.getElementById("Email").value,
        password: document.getElementById("Password").value,
      });
      const token = response.data["token"];
      setToken(token);
      console.log(token);
      localStorage.setItem("token", token);
      // Get userinfo
    } catch (error) {
      setIsErr(true);
      setCapsuleText(error.response.data.message);
    }
  };
  return (
    <div className="flex flex-col grow">
      <div className="flex py-28 justify-center  ">
        <Capsule capsuleText={capsuleText} errState={isErr} />
      </div>
      <div className="flex flex-col grow space-y-8 justify-center items-center h-2/3">
        <Textfield
          name={"Email"}
          type={"email"}
          placeholder={"example@example.com"}
        />
        <Textfield name={"Password"} type={"password"} placeholder={""} />
        <Button onClick={login} name={"Login"} />
      </div>
    </div>
  );
};

export default Login;
