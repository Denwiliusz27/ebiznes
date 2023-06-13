import axios from "axios";

const BASE_URL = "http://localhost:9000";

const Api = axios.create({
    headers: {
        "Content-Type": "application/json",
    },
    baseURL: BASE_URL,
});

export default Api;