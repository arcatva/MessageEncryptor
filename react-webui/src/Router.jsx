import Navbar from "./components/common/Navbar";
import Home from "./pages/Home";
import Login from "./pages/Login";
import { useAuth } from "./providers/authProvider";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Error from "./pages/Error";

const Router = () => {
  const { token, setToken } = useAuth();
  const routes = [
    {
      element: <Navbar />,
      children: [
        {
          path: "/",
          element: <Home />,
        },
        {
          path: "/login",
          element: <Login />,
        },
      ],
      errorElement: <Error />,
    },
  ];

  const router = createBrowserRouter(routes);

  return <RouterProvider router={router} />;
};
export default Router;
