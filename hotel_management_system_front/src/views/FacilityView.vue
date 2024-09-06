<template>
  <el-row>
    <el-col :span="24">
      <el-card style="height: 550px;">
        <div class="common-layout">
          <el-container>
            <el-header style="height: 30px;">
              <el-form :inline="true" class="demo-form-inline">
                <el-button type="success" style="margin-bottom: 10px;" @click="facilityAddShowWin = true">添加</el-button>
                <el-form-item label="搜索框" style="float: right;">
                  <el-input v-model="flag" placeholder="请输入需要搜索的姓名" clearable @input="selectAll(1)" />
                </el-form-item>
              </el-form>

            </el-header>
            <el-main style="height: 420px;">
              <el-table :data="facilityList.list" border style="width: 100%">
                <el-table-column prop="fid" label="ID" width="50px" />
                <el-table-column prop="fname" label="设施名称" width="90px" />
                <el-table-column prop="favatar" label="设施照片" width="90px">
                  <template #default="scope">
                    <el-image style="width: 60px; height: 43px" :src="'http://localhost:8080/upload/' + scope.row.favatar" contain />
                  </template>
                </el-table-column>
                <el-table-column prop="fnum" label="数量" width="100px" />
                <el-table-column prop="fvalue" label="价格" show-overflow-tooltip width="75px" />
                <el-table-column label="状态" width="100px">
                  <template #default="scope">
                    <el-tag v-if="scope.row.rstate == 0" type="success" effect="dark">空闲</el-tag>
                    <el-tag v-else-if="scope.row.rstate == 1" type="primary" effect="dark">已预订</el-tag>
                    <el-tag v-else-if="scope.row.rstate == 2" type="info" effect="dark">已入住</el-tag>
                    <el-tag v-else="scope.row.rstate == 3" type="danger" effect="dark">未开放</el-tag>
                  </template>
                </el-table-column>
                <el-table-column fixed="right" label="操作">
                  <template #default="scope">
                    <el-button size="small" type="success" @click="facilityUpdShow(scope.row.fid)">修改</el-button>
                    <el-popconfirm confirm-button-text="删除" cancel-button-text="取消" title="你确认要删除吗?" width="200px"
                      @confirm="">
                      <template #reference>
                        <el-button size="small" type="danger">删除</el-button>
                      </template>
                    </el-popconfirm>
                  </template>
                </el-table-column>
              </el-table>
            </el-main>
            <el-footer>
              <el-row class="row-bg" justify="center" style="margin-top: 20px;">
                <el-pagination background layout="prev, pager, next" :total="facilityList.total"
                  :page-size="facilityList.pageSize" @change="selectAll" />
              </el-row>

            </el-footer>
          </el-container>
        </div>



      </el-card>
    </el-col>

  </el-row>



  <!-- 新增窗口开始 -->
  <el-dialog v-model="facilityAddShowWin" title="添加房间" width="500">
    <el-form>
      <el-form-item label="设施名称" label-width="20%">
        <el-input v-model="facilityAdd.fname" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="设施数量" label-width="20%">
        <el-input v-model="facilityAdd.fnum" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="设施价值" label-width="20%">
        <el-input v-model="facilityAdd.fvalue" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="设施照片" label-width="20%">
        <el-upload class="avatar-uploader" action="http://localhost:8080/admin/upload" name="pic"
          :show-file-list="false" :on-success="handleAvatarSuccessAdd" :before-upload="beforeAvatarUploadAdd">
          <img v-if="imageUrlAdd" :src="imageUrlAdd" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon">
            <Plus />
          </el-icon>
        </el-upload>
      </el-form-item>

    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="facilityAddShowWin = false">取消</el-button>
        <el-button type="primary" @click="insert">确认</el-button>
      </div>
    </template>
  </el-dialog>
  <!-- 新增窗口结束 -->

   <!-- 修改窗口开始 -->
   <el-dialog v-model="facilityUpdShowWin" title="添加房间" width="500">
    <el-form>
      <el-form-item label="设施名称" label-width="20%">
        <el-input v-model="facilityUpd.fname" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="设施数量" label-width="20%">
        <el-input v-model="facilityUpd.fnum" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="设施价值" label-width="20%">
        <el-input v-model="facilityUpd.fvalue" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="设施照片" label-width="20%">
        <el-upload class="avatar-uploader" action="http://localhost:8080/admin/upload" name="pic"
          :show-file-list="false" :on-success="handleAvatarSuccessUpd" :before-upload="beforeAvatarUploadUpd">
          <img v-if="imageUrlUpd" :src="imageUrlUpd" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon">
            <Plus />
          </el-icon>
        </el-upload>
      </el-form-item>

    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="facilityUpdShowWin = false">取消</el-button>
        <el-button type="primary" @click="update">确认</el-button>
      </div>
    </template>
  </el-dialog>
  <!-- 修改窗口结束 -->

</template>



<script setup>
import facilityApi from '@/api/facilityApi'
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus'
import { ElLoading } from 'element-plus'

//所有设施的集合
const facilityList = ref({
  total: 0,
  pageSize: 0
})
//搜索的标识
const flag = ref('')

//新增设施标识
const facilityAddShowWin =ref(false)
//新增设施实体
const facilityAdd = ref({})

//修改设施标识
const facilityUpdShowWin = ref(false)
//修改设施实体
const facilityUpd = ref({})

//新增上传设施照片的地址
const imageUrlAdd = ref("")

//修改上传设施照片的地址
const imageUrlUpd = ref("")


//新增上传头像的前的函数
function beforeAvatarUploadAdd(rawFile) {
  if (rawFile.type !== 'image/jpeg') {
    ElMessage.error('图片仅支持jpg格式!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('图片不能大于2MB!')
    return false
  }
  return true
}

//新增上传之后的函数
function handleAvatarSuccessAdd(resp, uploadFile) {
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(0, 0, 0, 0.7)',
  })
  console.log(resp)
  loading.close()
  if (resp.code == 10000) {
    ElMessage.success({
      message: resp.msg,
      type: 'success',
      duration: 1200
    })
    imageUrlAdd.value = "http://localhost:8080/upload/" + resp.data;
    facilityAdd.value.favatar = resp.data


  } else {
    ElMessage.error({
      message: resp.msg,
      type: 'success',
      duration: 1200
    })
  }

}


//修改上传头像的前的函数
function beforeAvatarUploadUpd(rawFile) {
  if (rawFile.type !== 'image/jpeg') {
    ElMessage.error('图片仅支持jpg格式!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('图片不能大于2MB!')
    return false
  }
  return true
}

//修改上传之后的函数
function handleAvatarSuccessUpd(resp, uploadFile) {
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(0, 0, 0, 0.7)',
  })
  console.log(resp)
  loading.close()
  if (resp.code == 10000) {
    ElMessage.success({
      message: resp.msg,
      type: 'success',
      duration: 1200
    })
    imageUrlUpd.value = "http://localhost:8080/upload/" + resp.data;
    facilityUpd.value.favatar = resp.data


  } else {
    ElMessage.error({
      message: resp.msg,
      type: 'success',
      duration: 1200
    })
  }

}

//显示修改窗口并回现数据
function facilityUpdShow(fid){
  //回现数据
  facilityApi.selectById(fid)
    .then(resp => {
      facilityUpd.value = resp.data
      imageUrlUpd.value = `http://localhost:8080/upload/${facilityUpd.value.favatar}`

      
      
    })
  //显示窗口
  facilityUpdShowWin.value = true
}

//修改设施函数
function update(){
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(0, 0, 0, 0.7)',
  })
  facilityApi.update(facilityUpd.value)
    .then(resp => {
      loading.close()
      //判断是否成功
      if (resp.code == 10000) {
        //弹出消息
        ElMessage({
          message: resp.msg,
          type: 'success',
          duration: 1200
        })

        //隐藏对话框
        facilityUpdShowWin.value = false
        

        //刷新表格
        selectAll(1)
      } else {
        ElMessage({
          message: resp.msg,
          type: 'error',
          duration: 1200
        });
      }

    })
}

//添加设施函数
function insert(){
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(0, 0, 0, 0.7)',
  })
  facilityApi.insert(facilityAdd.value)
    .then(resp => {
      loading.close()
      //判断是否成功
      if (resp.code == 10000) {
        //弹出消息
        ElMessage({
          message: resp.msg,
          type: 'success',
          duration: 1200
        })

        //隐藏对话框
        facilityAddShowWin.value = false

        
        //清空数据
        facilityAdd.value = ""
        //清空数据
        imageUrlAdd.value = ""

        //刷新表格
        selectAll(1)
      } else {
        ElMessage({
          message: resp.msg,
          type: 'error',
          duration: 1200
        });
      }

    })
}

//查询所有
function selectAll(pageNum){
  facilityApi.selectAll(pageNum,flag.value)
    .then(resp => {
      facilityList.value=resp.data
      console.log(resp.data);
      
    })
}

selectAll(1)
</script>

<style scoped>
.avatar-uploader,
.avatar {
  width: 178px;
  height: 178px;
  display: block;
  border: 1px dotted #dcdfe6;
  border-radius: 5px;
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>