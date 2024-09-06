import service from ".";

const facilityApi={
    //添加
    insert(facility){
        return service.post('/admin/facility',facility)
    },
    //删除
    delete(fid){
        return service.delete(`/admin/facility/${fid}`)
    },
    //修改
    update(facility){
        return service.put('/admin/facility',facility)
    },
    //查询所有
    selectAll(pageNum,flag){
        return service.get('/admin/facility/selectAll',{
            params: {
                pageNum,
                flag
            }
          })
    },
    //根据ID查询
    selectById(fid){
        return service.get(`/admin/facility/selectById/${fid}`)
    }
}

export default facilityApi;