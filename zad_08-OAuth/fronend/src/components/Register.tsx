import {useEffect} from "react";
import {useNavigate} from "react-router-dom";

function Register() {
    const navigate = useNavigate();
    let token = localStorage.getItem('token')

    useEffect(() => {
        token = window.localStorage.getItem("token");
        if (token) {
            navigate("/home")
        }
    }, [token]);

    return (
        <div>
            <h1 className="p-9 text-center font-bold">Register</h1>
        </div>
    )
}

export default Register;