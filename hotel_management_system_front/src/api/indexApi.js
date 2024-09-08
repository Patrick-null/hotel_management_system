import service from ".";

const indexApi={
    //查找订单量
    getOrderVolume(){
        return service.get('/admin/getOrderVolume')
    },
    //查找住客人数
    getGuestVolume(){
        return service.get('/admin/getGuestVolume')
    },
    //根据性别查询每个房间的人数
    selectRtypeByGender(gender){
        console.log(gender);
        return service.get(`/admin/selectRtypeByGender/${gender}`)
    },
    //查询所有房间的类型
    getAllRtype(){
        return service.get('/admin/getAllRtype')
    }
}

export default indexApi;