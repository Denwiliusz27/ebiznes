import {useQuery} from "react-query";
import {Product} from "../models/Product";
import Api from "../Api";

function Products(){

    const products = useQuery<Product[]>({
        queryKey: ["products"],
        queryFn: () => Api.get("/products").then((res) => res.data),
        enabled: true,
    });

    return(
        <div className="p-6 w-2/5 flex flex-col justify-items-center mx-auto">
            <h1 className="py-3 font-bold text-center">Available products:</h1>
            {products.isFetching ? (
                <p>Loading...</p>
            ) : (
                <ul className="p-6 divide-y divide-slate-200">
                    {products.data?.map((product) => (
                        <li className="first:pt-0 last:pb-0 py-3">
                            <div>
                                <p><span className="font-bold">Name: </span>{product.name}</p>
                                <p><span className="font-bold">Author: </span>{product.author}</p>
                                <p><span className="font-bold">Price: </span>{product.price}</p>
                                <p><span className="font-bold">Amount: </span>{product.amount}</p>
                                <p><span className="font-bold">Desciption: </span>{product.description}</p>
                            </div>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    )
}

export default Products;