import { Link } from "react-router-dom";
import Button from "../components/Button";

const Error = () => {
  return (
    <div className="flex flex-col h-screen justify-center text-center">
      <div className="">
        <div className="text-6xl my-14 text-gray-800">404 Bad Request!</div>
        <div className="my-16">
          <Link to={"/"} replace={true}>
            <Button name={"Go Home"} />
          </Link>
        </div>
      </div>
    </div>
  );
};
export default Error;
