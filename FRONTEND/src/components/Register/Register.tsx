import React, {useEffect, useState} from "react";
import { useRegister } from "../../hooks/useAuth"
import { useNavigate } from "react-router-dom"

const Register: React.FC = () => {
    const { handleRegister, error: apiError, loading } = useRegister();
    const [username, setUsername] = useState<string>("");
    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [repassword, setRepassword] = useState<string>("");
    const [error, setError] = useState<string>("");
    const navigate = useNavigate();

    useEffect(() => {
        if (apiError) {
            setError(apiError);
        }
    }, [apiError]);

    const submitHandler = async () => {
        if (username === "") {
            setError("Please enter a username");
            return;
        }

        if (username.length <= 6) {
            setError("Username must be at least 6 characters long");
            return;
        }

        if (username.includes(" ")) {
            setError("Username not contains space");
            return;
        }

        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            setError("Invalid email.");
            return;
        }

        if (password === "") {
            setError("Please enter your password");
            return;
        }

        // if (password.length <= 6) {
        //     setError("Password must be at least 6 characters long");
        //     return;
        // }

        if (password.includes(" ")) {
            setError("Password not contains space");
            return;
        }

        if (repassword !== password) {
            setError("Passwords do not match");
            return;
        }

        const isSuccess: boolean | undefined = await handleRegister(username, email, password, repassword);
        if (isSuccess) {
            navigate("/login")
        }
        return;
    }

    return (
        <div style={{paddingTop: '32.5px'}}>
            <h3>Register</h3>
            {error && <p>{error}</p>}
            <form onSubmit={(e) => {
                e.preventDefault();
                submitHandler()
            }}>
                <h5>Username</h5>
                <input onChange={(e) => setUsername(e.target.value.trim())} type="text"
                       placeholder="Enter your username"
                       value={username}/>
                <h5>Email</h5>
                <input onChange={(e) => setEmail(e.target.value.trim())} type="email"
                       placeholder="Enter your email"
                       value={email}/>
                <h5>Password</h5>
                <input onChange={(e) => setPassword(e.target.value.trim())} type="password"
                       placeholder="Enter your password"
                       value={password}/>
                <h5>Confirm Password</h5>
                <input onChange={(e) => setRepassword(e.target.value.trim())} type="password"
                       placeholder="ReEnter your password"
                       value={repassword}/>
                <button type="submit">{loading ? "Loading..." : "Register"}</button>
            </form>
        </div>
    )
}

export default Register