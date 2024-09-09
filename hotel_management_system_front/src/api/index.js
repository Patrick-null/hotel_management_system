import router from "@/router";
import axios from "axios";
import { ElMessage } from "element-plus";

const service = axios.create({
    baseURL: 'http://localhost:8080'
});

//axios的拦截器

service.interceptors.request.use(function (config) {
    //如果不是登录请求就要在请求头中添加token
    if (!config.url.startsWith("/login") || !config.url.startsWith("/loginUser")) {
        config.headers.token = sessionStorage.getItem('token');
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
   
    //将续期的jwt放到sessionStorage中
    sessionStorage.setItem("token",token)
    console.log(123);
    
    console.log(token);
    
    return resp.data
}, error => {
    if(error.status == 403){
        ElMessage.error({
            message:'登录信息已过期，请重新登录',
            duration:1200,
            onClose:()=>{
                //删除token
                sessionStorage.removeItem('token');
                //跳转到登录页
                router.push('/login');
            }
        })
    }
});

export default service;