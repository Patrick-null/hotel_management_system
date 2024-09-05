import service from ".";

const memberApi={
    //添加
    insert(member){
        return service.post('/admin/member',member)
    },
    //删除
    delete(mid){
        return service.delete(`/admin/member/${mid}`)
    },
    //修改
    update(member){
        return service.put('/admin/member',member)
    },
    //查询所有
    selectAll(){
        return service.get('/admin/member/selectAll')
    },
    //根据ID查询
    selectById(mid){
        return service.get(`/admin/member/selectById/${mid}`)
    }
}

export default memberApi;