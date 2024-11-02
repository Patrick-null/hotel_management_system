import router from "@/router";
import axios from "axios";
import { ElMessage } from "element-plus";
import { useTokenStore } from "@/stores/token";

const service = axios.create({
    baseURL:import.meta.env.VITE_SERVER_ADDR
});

//axios的拦截器

service.interceptors.request.use(function (config) {
    //如果不是登录请求就要在请求头中添加token
    const tokenStore = useTokenStore()
    if (!config.url.startsWith("/login") || !config.url.startsWith("/loginUser")) {
        //config.headers.token = sessionStorage.getItem('token');
        config.headers.token = tokenStore.tokenStr
    }
    return config;
}, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
});


// 添加响应拦截器
service.interceptors.response.use(resp => {
    //获取续期的jwt
    let token = resp.headers.token;
    const tokenStore = useTokenStore()
   tokenStore.update(token)
    //将续期的jwt放到sessionStorage中
    //sessionStorage.setItem("token",token)
    

    
    return resp.data
}, error => {
    if(error.status == 403){
        ElMessage.error({
            message:'登录信息已过期，请重新登录',
            duration:1200,
            onClose:()=>{
                const tokenStore = useTokenStore()
                //删除token
                //sessionStorage.removeItem('token');
                tokenStore.$reset()
                //跳转到登录页
                router.push('/login');
            }
        })
    }
});

export default service;