import service from ".";
import qs from "qs";

const loginApi = {
    login(admin){
        console.log(admin);
        return service.post('/login',qs.stringify(admin))
    },
    loginTwo(admin){
        console.log(admin);
        return service.post('/loginTwo',qs.stringify(admin))
    },
    //获取用户名
    getUsername(){
        return service.get('/admin/getUsername')
    },
    //修改密码
    updPwdUser(userAndpwd){
        return service.put('/user/userAndpwd',userAndpwd)
    },
    //修改密码
    updPwd(userAndpwd){
        return service.put('/admin/userAndpwd',userAndpwd)
    },
    //用户注册
    enroll(enroll,info){
        console.log(enroll);
        console.log(info);
        
        
        return service.post('/enroll',{enroll,info})
    },
    //验证码
    captcha(){
        return service.post('/captcha')
    }
    
}

export default loginApi;