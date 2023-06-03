import {Link, Route, Routes} from "react-router-dom";
import Login from "./components/Login";
import Register from "./components/Register";

function App() {

  return (
    <>
        <div className="App">
            <div className="flex flex-row justify-center bg-amber-100 h-16">
                <div className="hover:bg-amber-500 flex flex-row items-center">
                    <Link to="/login" className="px-12">Login</Link>
                </div>
                <div className="hover:bg-amber-500 flex flex-row items-center">
                    <Link to="/register" className="px-12">Register</Link>
                </div>
            </div>
            <Routes>
                {/*<Route path="/" element={<Hello/>}></Route>*/}
                <Route path="/login" element={<Login/>}></Route>
                <Route path="/register" element={<Register/>}></Route>
                <Route path="/home" element={

                }></Route>
            </Routes>
        </div>
    </>
  )
}

export default App
