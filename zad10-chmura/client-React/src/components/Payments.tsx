import {useState} from "react";
import {Payment} from "../models/Payment";
import Api from "../Api";
import {useMutation} from "react-query";

function Payments() {
    const [payment, setPayment] = useState<Payment>({cardNr: "", amount: 0, price: 0, productName: ""})
    const [sended, setSender] = useState(false)

    const inputChange = (e: React.FormEvent<HTMLInputElement>) => {
        setPayment({...payment, [e.currentTarget.name]: e.currentTarget.value})
        setSender(false);
    }

    const {mutate} = useMutation(async () => {
            const response = await Api.post(`payments/create`, payment);
            return response.data
        },
        {
            onSuccess: (responseData) => {
                console.log("Sended payment succesfully")
                console.log(responseData)
                setSender(true);
            }
        });

    const sendPayment = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        payment.amount = parseInt(String(payment.amount))
        payment.price = parseInt(String(payment.price))
        mutate();
    }


    return (
        <div className="p-6 w-2/5 flex flex-col justify-items-center mx-auto">
            <h1 className="p-3 font-bold text-center">Make payment</h1>
            <form onSubmit={sendPayment} className="flex flex-col">
                <label className="font-bold py-6">Card number:
                    <input className="ml-1  font-normal text-center px-1 border-b-2 border-amber-500
                                    hover:border-2 hover:border-amber-500 hover:rounded-md  focus:border-2
                                    focus:border-amber-500 focus:rounded-md outline-none "
                           type="text"
                           name="cardNr"
                           value={payment.cardNr}
                           onChange={inputChange}
                    />
                </label>
                <label className="font-bold py-6">Product:
                    <input className="ml-1  font-normal text-center px-1 border-b-2 border-amber-500
                                    hover:border-2 hover:border-amber-500 hover:rounded-md  focus:border-2
                                    focus:border-amber-500 focus:rounded-md outline-none "
                           type="text"
                           name="productName"
                           value={payment.productName}
                           onChange={inputChange}
                    />
                </label>
                <label className="font-bold py-6">Price:
                    <input className="ml-1  font-normal text-center px-1 border-b-2 border-amber-500
                                    hover:border-2 hover:border-amber-500 hover:rounded-md  focus:border-2
                                    focus:border-amber-500 focus:rounded-md outline-none "
                           type="number"
                           name="price"
                           value={payment.price}
                           onChange={inputChange}
                    />
                </label>
                <label className="font-bold py-6">Amount:
                    <input className="ml-1  font-normal text-center px-1 border-b-2 border-amber-500
                                    hover:border-2 hover:border-amber-500 hover:rounded-md  focus:border-2
                                    focus:border-amber-500 focus:rounded-md outline-none "
                           type="number"
                           name="amount"
                           value={payment.amount}
                           onChange={inputChange}
                    />
                </label>
                <button type="submit" className="basis-2/5 px-8 py-2  transition hover:scale-110 delay-150 rounded-lg text-black
                                                bg-amber-100 hover:bg-mber-500 hover:shadow-amber-500 text-white shadow-lg shadow-amber-500">
                    Pay
                </button>
                {sended &&
                    <div className="text-center p-6">Sended successfully</div>
                }
            </form>
        </div>
    )
}

export default Payments;