import {BrowserRouter, Route, Routes} from "react-router-dom";
import AdminLayout from "./Layout/AdminLayout/AdminLayout.jsx";
import Dashboard from "./Pages/Dashboard.jsx";
import {Worker} from "./Pages/Worker.jsx";
import {OneWorkerPage} from "./Pages/OneWorkerPage.jsx";
import {Eslatma} from "./Pages/Eslatma.jsx";
import {Login} from "./Pages/Login.jsx";
import {Verification} from "./Pages/Verification.jsx";
import {ResetPassword} from "./Pages/ResetPassword.jsx";
import {NotFoundaPage} from "./Component/NotFoundaPage.jsx";
import {Shikoyatlar} from "./Pages/Shikoyatlar.jsx";

const App = () => {

  return (
    <div>
        <BrowserRouter>
            <Routes>
                <Route path={"/"} element={<AdminLayout/>}>
                    <Route index element={<Dashboard/>}/>
                    <Route path={"/worker"} element={<Worker/>}/>
                    <Route path={"/worker/:id"} element={<OneWorkerPage/>}/>
                    <Route path={"/eslatmalar"} element={<Eslatma/>}/>
                    <Route path={"/shikoyatlar"} element={<Shikoyatlar/>}/>
                </Route>
                <Route path={"/auth/login"} element={<Login/>}/>
                <Route path={"/auth/verification"} element={<Verification/>}/>
                <Route path={"/auth/resetpassword"} element={<ResetPassword/>}/>
                <Route path={"*"} element={<NotFoundaPage/>}/>
            </Routes>
        </BrowserRouter>
    </div>
  )
}
export default App

