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
    }
}

export default facilityApi;