import React from "react";
import { Route, Routes } from "react-router-dom"
import HomePage from "../pages/HomePage"
import RegisterPage from "../pages/RegisterPage"
import LoginPage from "../pages/LoginPage"
import AccountPage from "../pages/AccountPage"
import ManagePage from "../pages/ManagePage"
import {AuthRoute, UnauthRoute} from "./CustomeRoute"

const AppRoutes: React.FC = () => {
    return (
        <Routes>
            {/*Những route public ai cũng có thể tới*/}
            <Route path={'/'} element={<HomePage/>}/>
            <Route path={'/home'} element={<HomePage/>}/>

            {/*Những route sau khi đăng nhập không thể đi tới*/}
            <Route element={<UnauthRoute/>}>
                <Route path={'/register'} element={<RegisterPage/>}/>
                <Route path={'/login'} element={<LoginPage/>}/>
            </Route>

            {/*Những route cần đăng nhập mới có thể đi tới*/}
            <Route element={<AuthRoute/>}>
                <Route path={'/account'} element={<AccountPage/>}/>
                <Route path={'/manage'} element={<ManagePage/>}/>
            </Route>
        </Routes>
    )
}

export default AppRoutes;
