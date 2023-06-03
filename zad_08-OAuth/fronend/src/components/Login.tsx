import {useState} from "react";
import {useMutation} from "react-query";
import Api from "../Api";
import {AxiosError} from "axios";
import {useNavigate} from "react-router-dom";


function Login() {
    const navigate = useNavigate();
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("")

    const {mutate} = useMutation(async () => {
            const response = await Api.post(`login`, {
                email: email,
                password: password,
            });
            return response.data
        },
        {
            onSuccess: (responseData) => {
                console.log(responseData)

                window.localStorage.setItem("token", responseData.token);
                window.localStorage.setItem("user", responseData.user);
                navigate("/hello");

            },
            onError: (error: AxiosError) => {
                if (error.response.status == 404) {
                    setError("User doesn't exist")
                } else if (error.response.status == 401){
                    setError("Incorrect password")
                }
            }
        });

    const handleEmailChange = (event: React.FormEvent<HTMLInputElement>) => {
        setEmail(event.currentTarget.value);
        setError("")
    }

    const handlePasswordChange = (event: React.FormEvent<HTMLInputElement>) => {
        setPassword(event.currentTarget.value);
        setError("")
    }

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        mutate();
    };


    return (
        <div className="flex flex-col items-center pt-20">
            <div className="w-2/5 pb-14 text-center text-2xl font-medium">
                <p>Login</p>
            </div>
            <div className='w-96 font-medium'>
                <form className=" " onSubmit={handleSubmit} method={"POST"}>
                    <label className="flex flex-col py-2">
                        <span className="flex py-2 after:content-['*'] after:text-red-600 font-bold">Email</span>
                        <input type="email" name="email" placeholder={"adres@email"} value={email} required
                               onChange={handleEmailChange}
                               className=" py-3 px-5 border shadow-sm border-slate-300 placeholder-slate-400
                                   border- ring-red-600 block w-full rounded-md sm:text-sm focus:ring-1"/>
                    </label>
                    <label className="flex flex-col py-2">
                        <span className="flex py-2 after:content-['*'] after:text-red-600 font-bold">Password</span>
                        <input type="password" name="password" value={password} required
                               onChange={handlePasswordChange}
                               className="py-3 px-5 border shadow-sm border-slate-300 placeholder-slate-400
                                   border-red-600 ring-red-600 block w-full rounded-md sm:text-sm focus:ring-1"/>
                    </label>
                    <div className="flex flex-col justify-center items-center">
                        <input type="submit" value="Login"
                               className="mt-12 mb-3 px-12 py-2 bg-amber-400 transition hover:scale-110 delay-150 rounded-lg
                               hover:hover:bg-amber-500 hover:shadow-amber-700 text-white shadow-lg shadow-amber-500"></input>
                    </div>
                    {error &&
                        <p className="p-3 text-center text-red-600">{error}</p>
                    }
                </form>
            </div>
        </div>
    );
}

export default Login;