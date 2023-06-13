import Products from "./components/Products";
import {Link, Route, Routes} from "react-router-dom";
import Hello from "./components/Hello";
import Payments from "./components/Payments";


function App() {
    return (
        <div className="App">
            <div className="flex flex-row justify-center bg-amber-100 h-16">
                <div className="hover:bg-amber-500 flex flex-row items-center">
                    <Link to="/" className="px-12 ">Home</Link>
                </div>
                <div className="hover:bg-amber-500 flex flex-row items-center">
                    <Link to="/products" className="px-12">Products</Link>
                </div>
                <div className="hover:bg-amber-500 flex flex-row items-center">
                    <Link to="/payments" className="px-12">Payments</Link>
                </div>
            </div>
            <Routes>
                <Route path="/" element={<Hello/>}></Route>
                <Route path="/products" element={<Products/>}></Route>
                <Route path="/payments" element={<Payments/>}></Route>
            </Routes>
        </div>
    );
}

export default App;