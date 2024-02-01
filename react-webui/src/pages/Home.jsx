import { useAuth } from "../providers/authProvider";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Textfield from "../components/Textfield";
import Button from "../components/Button";
import axios from "axios";
import DetailsList from "../components/DetailsList";

const Home = () => {
  const { token } = useAuth();
  const [respData, setRespData] = useState(null);
  const navigator = useNavigate();
  const [method, setMethod] = React.useState("AES");
  const [decryptMethod, setDecryptMethod] = React.useState("AES");
  useEffect(() => {
    if (!token) {
      navigator("/login", { replace: true });
    }
  }, []);

  const options = [
    { label: "AES", value: "AES" },

    { label: "DES", value: "DES" },

    { label: "CaesarCipher", value: "CaesarCipher" },
  ];

  const handleChange = (event) => {
    setMethod(event.target.value);
  };
  const handleDecryptChange = (event) => {
    setDecryptMethod(event.target.value);
  };
  const create = () => {
    const t = document.getElementById("Message").value;
    if (!t) {
      alert("Please input message");
    }
    axios
      .post("https://localhost/api/v1/protected/create-message", {
        content: document.getElementById("Message").value,
        encryption_method: method,
      })
      .then((resp) => {
        console.log(resp.data);
        alert(
          "ID: " +
            resp.data.id +
            "\n" +
            "Content: " +
            resp.data.content +
            "\n" +
            "CipherText: " +
            resp.data.cipher_text +
            "\n" +
            "Method: " +
            resp.data.encryption_method,
        );
        setRespData(resp.data);
      });
  };

  const decrypt = () => {
    const c = document.getElementById("CipherText").value;
    if (!c) {
      alert("Please input cipher text");
    }
    axios
      .post("https://localhost/api/v1/protected/decrypt-message", {
        cipher_text: document.getElementById("CipherText").value,
        encryption_method: decryptMethod,
      })
      .then((resp) => {
        console.log(resp.data);
        alert(
          "Content: " +
            resp.data.content +
            "\n" +
            "CipherText: " +
            resp.data.cipher_text +
            "\n" +
            "Method: " +
            resp.data.encryption_method,
        );
        setRespData(resp.data);
      });
  };
  const get = () => {
    const c = document.getElementById("ID").value;
    if (!c) {
      alert("Please input id");
    }
    axios
      .post("https://localhost/api/v1/protected/get-message", {
        id: document.getElementById("ID").value,
      })
      .then((resp) => {
        console.log(resp.data);
        alert(
          "ID: " +
            resp.data.id +
            "\n" +
            "Content: " +
            resp.data.content +
            "\n" +
            "CipherText: " +
            resp.data.cipher_text +
            "\n" +
            "Method: " +
            resp.data.encryption_method,
        );
        setRespData(resp.data);
      });
  };
  return (
    <div className="flex flex-col  ">
      <div className="flex flex-col grow mt-28 space-y-12">
        <div className="flex grow space-x-5 justify-center items-center h-2/3">
          <Textfield
            name={"Message"}
            type={"text"}
            placeholder={"Hello World"}
          />
          <div>
            <select
              value={method}
              onChange={handleChange}
              className="rounded-lg border-2"
            >
              {options.map((option) => (
                <option value={option.value}>{option.label}</option>
              ))}
            </select>
          </div>

          <Button onClick={create} name={"Encrypt"} />
        </div>
        <div className="flex grow space-x-5 justify-center items-center h-2/3">
          <Textfield
            name={"CipherText"}
            type={"text"}
            placeholder={"p144dE7lQbr4vEvwJaQ4XA=="}
          />
          <div>
            <select
              value={decryptMethod}
              onChange={handleDecryptChange}
              className="rounded-lg border-2"
            >
              {options.map((option) => (
                <option value={option.value}>{option.label}</option>
              ))}
            </select>
          </div>

          <Button onClick={decrypt} name={"Decrypt"} />
        </div>
        <div className="flex grow space-x-5 justify-center items-center h-2/3">
          <Textfield name={"ID"} type={"id"} placeholder={"1"} />

          <Button onClick={get} name={"Get"} />
        </div>
        <div className="flex-col flex grow space-x-5 justify-center items-center h-2/3 my-20">
          <div className="text-zinc-800 my-5 font-bold">Result</div>
          {respData && <DetailsList data={respData} />}
        </div>
      </div>
    </div>
  );
};

export default Home;
