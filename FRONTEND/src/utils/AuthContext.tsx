import {createContext, useContext, ReactNode} from "react";
import { useDecodedToken } from "../hooks/useAuth"
import { AuthContextType } from "../models/DecodedToken"

const AuthContext = createContext<AuthContextType | undefined>(undefined);

interface AuthProviderProps {
    children: ReactNode;
}

export const AuthProvider = ({ children }: AuthProviderProps) => {
    const { decodedToken, loading } = useDecodedToken(); // Sử dụng useDecodedToken

    return (
        <AuthContext.Provider value={{ decodedToken, loading }}>
            {children}
        </AuthContext.Provider>
    );
}

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (context === undefined) {
        throw new Error("useAuth must be used within an AuthProvider");
    }
    return context;
};