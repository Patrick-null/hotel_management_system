import service from ".";

const ordersApi={
    //添加
    insert(orders){
        return service.post('/admin/orders',orders)
    },
    //删除
    delete(oid){
        return service.delete(`/admin/orders/${oid}`)
    },
    //修改
    update(orders){
        return service.put('/admin/orders',orders)
    },
    //查询所有
    selectAll(pageNum,flag){
        return service.get('/admin/orders/selectAll',{
            params: {
                pageNum,
                flag
            }
          })
    },
    //根据ID查询
    selectById(oid){
        return service.get(`/admin/orders/selectById/${oid}`)
    },
    //查询所有空房间
    selectByStateSpareRoom(rstate){
        return service.get(`/admin/orders/selectByState/${rstate}`)
    },
    download(){
        return service.get('/admin/orders/download',{
            
            responseType:'blob'
        })
    }
}

export default ordersApi;