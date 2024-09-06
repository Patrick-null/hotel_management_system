import service from ".";

const roomApi={
    //添加
    insert(room){
        return service.post('/admin/room',room)
    },
    //删除
    delete(rid){
        return service.delete(`/admin/room/${rid}`)
    },
    //修改
    update(room){
        return service.put('/admin/room',room)
    },
    //查询所有
    selectAll(pageNum,flag){
        return service.get('/admin/room/selectAll',{
            params: {
                pageNum,
                flag
            }
          })
    },
    //根据ID查询
    selectById(rid){
        return service.get(`/admin/room/selectById/${rid}`)
    },
    //查询所有空房间
    selectByStateSpareRoom(rstate){
        return service.get(`/admin/room/selectByState/${rstate}`)
    }
}

export default roomApi;