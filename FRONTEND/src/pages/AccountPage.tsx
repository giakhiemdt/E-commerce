import React from "react";
import { Navbar } from "../components/Navbar"
import Footer from "../components/Footer"
import { Account } from "../components/Account"

const AccountPage: React.FC = () => {

    return (
        <div>
            <Navbar/>
            <Account/>
            <Footer/>
        </div>
    )
}

export default AccountPage;