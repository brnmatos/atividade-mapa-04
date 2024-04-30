import axios from "axios";

const service = axios.create({
    baseURL: "http://localhost:9090",
});

service.defaults.timeout = 9900000;

service.interceptors.request.use(
    config => {
        return config
    },
    error => {
        return Promise.reject(error)
    }
)

service.interceptors.response.use(
    response => {
        const res = response.data;
        return res
    },
    error => {
        return Promise.reject(error)       
    }
)

export default service