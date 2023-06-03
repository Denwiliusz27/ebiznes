import {Route, Routes} from "react-router-dom";
import Login from "./components/Login";
import Register from "./components/Register";
import AuthRoute from "./AuthRoute";
import Home from "./components/Home";
import Navbar from "./components/Navbar";

function App() {
    return (
        <>
            <div className="App">
                <Routes>
                    <Route path="/" element={<Navbar/>}>
                        <Route path="login" element={<Login/>}></Route>
                        <Route path="register" element={<Register/>}></Route>
                        <Route path="/home" element={
                            <AuthRoute>
                                <Home/>
                            </AuthRoute>
                        }></Route>
                    </Route>
                </Routes>
            </div>
        </>
    )
}

export default App
