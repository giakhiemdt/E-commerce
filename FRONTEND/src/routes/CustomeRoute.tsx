import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from '../utils/AuthContext';

export const AuthRoute: React.FC = () => {
    const context = useAuth();
    return context.decodedToken ? (
        <Outlet />
    ) : (
        <Navigate to="/login" replace />
    );
};

export const UnauthRoute: React.FC = () => {
    const context = useAuth();
    return context.decodedToken ? (
        <Navigate to="/home" replace />
    ) : (
        <Outlet/>
    )
}


