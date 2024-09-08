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
    }
    
}

export default loginApi;