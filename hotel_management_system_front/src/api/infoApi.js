import service from ".";

const infoApi={
    //增加
    insert(info){
        return service.post('/info',info)
    },delete(aid){
        return service.delete(`/info/${aid}`)
    },update(info){
        return service.put('/info',info)
    },selectById(aid){
        return service.get(`/info/${aid}`)
    },selectByUsername(){
        return service.get('/info/selectByUsername/')
    },selectByID(id){
        return service.get(`/info/selectByID/${id}`)
    },
}

export default infoApi;