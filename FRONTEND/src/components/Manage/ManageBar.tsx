import React from "react";

const ManageBar: React.FC = () => {

    return (
        <div>
            <div className="d-grid gap-3">
                <button type="button" className="btn btn-primary btn-block">Overview</button>
                <button type="button" className="btn btn-primary btn-block">Accounts</button>
                <button type="button" className="btn btn-primary btn-block">Products</button>
                <button type="button" className="btn btn-primary btn-block">Orders</button>
            </div>
        </div>
    )

}

export default ManageBar;