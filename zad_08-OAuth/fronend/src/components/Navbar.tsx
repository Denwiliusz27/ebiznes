import {Link, Outlet, useNavigate} from "react-router-dom";
import {useEffect} from "react";
import {useMutation} from "react-query";
import Api from "../Api";

function Navbar() {
    const navigate = useNavigate();
    let token = localStorage.getItem('token')

    useEffect(() => {
        token = window.localStorage.getItem("token");

        if (token) {
            navigate("/home")
        }
    }, [token]);


    const {mutate} = useMutation(async () => {
            const response = await Api.post(`logout`, {
                id: Number(window.localStorage.getItem("user_id"))
            });
            return response.data
        },
        {
            onSuccess: () => {
                localStorage.clear()
                navigate('/')
            },
        });


    const logout = (event: React.MouseEvent) => {
        event.preventDefault();
        mutate()
    }

    return (
        <div>
            <nav className="flex flex-row justify-center bg-amber-100 h-16">
                {!token ? (
                    <div className="flex flex-row">
                        <div className="hover:bg-amber-500 flex flex-row items-center">
                            <Link to="/login" className="px-12">Login</Link>
                        </div>
                        <div className="hover:bg-amber-500 flex flex-row items-center">
                            <Link to="/register" className="px-12">Register</Link>
                        </div>
                    </div>
                ) : (
                    <div className="flex flex-row">
                        <div className="hover:bg-amber-500 flex flex-row items-center">
                            <Link to="/login" className="px-12" onClick={logout}>Logout</Link>
                        </div>
                    </div>
                )}
            </nav>
            <Outlet/>
        </div>
    )
}

export default Navbar;