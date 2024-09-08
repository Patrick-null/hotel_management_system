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
    }
}

export default facilityApi;