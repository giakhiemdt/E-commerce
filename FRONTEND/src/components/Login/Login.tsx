import React, {useEffect, useState} from "react";
import { useLogin } from "../../hooks/useAuth";


const Login: React.FC = () => {
    const {handleLogin, error: apiError, loading} = useLogin();
    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [error, setError] = useState<string>("");

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

        if (password === "") {
            setError("Please enter your password");
            return;
        }

        const success: boolean | undefined = await handleLogin(username, password);
        if (success) {
            window.location.replace("/home");
        }
        return
    }

    return (
        <div style={{paddingTop: '32.5px'}}>
            <h3>Login</h3>
            {error && <p>{error}</p>}
            <form onSubmit={(e) => {
                e.preventDefault();
                submitHandler()
            }}>
                <h5>Username</h5>
                <input onChange={(e) => setUsername(e.target.value.trim())}
                       type="text" placeholder="Enter your username"
                       value={username}/>
                <h5>Password</h5>
                <input onChange={(e) => setPassword(e.target.value.trim())}
                       type="password" placeholder="Enter your password"
                       value={password}/>
                <button type="submit">{loading ? "Loading..." : "Login"}</button>
            </form>
        </div>
    )
}

export default Login;