import React from "react";
import { Navbar } from "../components/Navbar"
import Footer from "../components/Footer"
import { Accounts } from "../components/Account"
import { ManageBar } from "../components/Manage"

const ManagePage: React.FC = () => {
    return (
        <div>
            <Navbar/>
            <div style={{display: "flex", justifyContent: "space-between"}}>
                <div className={'col-3'}>
                    <ManageBar/>
                </div>
                <div className={'col-8'}>
                    <Accounts />
                </div>
            </div>
            <Footer/>
        </div>
    )
}

export default ManagePage;