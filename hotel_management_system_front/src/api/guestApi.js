import service from ".";

const guestApi={
    //添加
    insert(guest){
        return service.post('/admin/guest',guest)
    },
    //删除住客
    delete(gid){
        return service.delete(`/admin/guest/${gid}`)
    },
    //修改住客
    update(guest){
        return service.put('/admin/guest',guest)
    },
    //查询所有住客
    selectAll(){
        return service.get('/admin/guest/selectAll')
    },
    //根据ID查询住客
    selectById(gid){
        return service.get(`/admin/guest/selectById/${gid}`)
    },
    //查询所有空房间
    selectByStateSpareRoom(rstate){
        return service.get(`/admin/room/selectByState/${rstate}`)
    }
}

export default guestApi;