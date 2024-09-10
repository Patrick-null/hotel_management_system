import service from ".";

const facilityApi={
  
    //查询所有
    selectAllRoom(pageNum,flag){
        return service.get('/user/selectAllRoom',{
            params: {
                pageNum,
                flag
            }
          })
    },
    //订购房间
    buyRoom(order){
        return service.post('/user/buyRoom',order)
    },
    //查询我的订单
    selectMyAll(pageNum,gno,flag){
        console.log(gno);
        
        return service.get('/user/selectMyAll',{
            params: {
                pageNum,
                gno,
                flag
            }
          })
    },
    //使用订单
    useOrder(oid){
        return service.post(`/user/useOrder/${oid}`)
    },
    //修改订单
    updateOrders(orders){
        return service.put('/user/updateOrder',orders)
    },
    //根据ID查询
    selectById(oid){
        return service.get(`/user/selectById/${oid}`)
    },
    //查询所有空闲房间
    selectByStateSpareRoom(rstate){
        return service.get(`/user/selectByState/${rstate}`)
    },
}

export default facilityApi;