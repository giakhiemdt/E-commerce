export interface DecodeToken {
    role: string;
    sub: string;
    iat: number;
    exp: number;
}

export interface AuthContextType {
    decodedToken: DecodeToken | null;
    loading: boolean;
}