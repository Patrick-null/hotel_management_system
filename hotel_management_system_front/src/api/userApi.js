import service from ".";

const userApi={
    
    //查询所有
    selectAll(pageNum,flag){
        return service.get('/admin/guest/selectAllInfo',{
            params: {
                pageNum,
                flag
            }
          })
    },
    //用户注册
    enroll(enroll,info){
        console.log(enroll);
        console.log(info);
        return service.post('/admin/guest/enroll',{enroll,info})
    },
}

export default userApi;