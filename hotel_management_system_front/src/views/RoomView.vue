<template>
  <el-row>
    <el-col :span="24">
      <el-card style="height: 550px;">
        <div class="common-layout">
          <el-container>
            <el-header style="height: 30px;">
              <el-form :inline="true" class="demo-form-inline">
                <el-button type="success" style="margin-bottom: 10px;" @click="roomAddShowWin = true">添加</el-button>
                <el-form-item label="搜索框" style="float: right;">
                  <el-input v-model="flag" placeholder="请输入需要搜索的姓名" clearable @input="selectAll(1)" />
                </el-form-item>
              </el-form>

            </el-header>
            <el-main style="height: 420px;">
              <el-table :data="roomList.list" border style="width: 100%">
                <el-table-column prop="rid" label="ID" width="50px" />
                <el-table-column prop="rno" label="房间号" width="80px" />
                <el-table-column prop="ravatar" label="房间照片" width="100px">
                  <template #default="scope">

                    <el-image style="width: 60px; height: 43px"
                      :src="'http://localhost:8080/upload/' + scope.row.ravatar" contain />
                  </template>
                </el-table-column>
                <el-table-column prop="rtype" label="房间类型" width="100px" />
                <el-table-column prop="rprice" label="价格" show-overflow-tooltip width="75px" />
                <el-table-column label="状态" width="75px">
                  <template #default="scope">
                    <el-tag v-if="scope.row.rstate == 0" type="success" effect="dark">空闲</el-tag>
                    <el-tag v-else-if="scope.row.rstate == 1" type="primary" effect="dark">已预订</el-tag>
                    <el-tag v-else-if="scope.row.rstate == 2" type="info" effect="dark">已入住</el-tag>
                    <el-tag v-else="scope.row.rstate == 3" type="danger" effect="dark">未开放</el-tag>
                  </template>
                </el-table-column>

                <el-table-column label="房间设施">
                  <template #default="scope">
                    <el-button type="primary" style="margin-left: 16px"
                      @click="facilityAddShow(scope.row.facilityList,scope.row.rid)">房间设施</el-button>
                  </template>
                </el-table-column>

                <el-table-column label="房间人员">
                  <template #default="scope">
                    <el-popover placement="right" width="320px" trigger="hover">
                      <template #reference>
                        <el-button style="margin-right: 16px">房间人员</el-button>
                      </template>
                      <el-table :data="scope.row.guestList">
                        <el-table-column width="70px" property="gname" label="姓名" />
                        <el-table-column width="100px" property="gno" label="身份证号" />
                        <el-table-column width="120px" property="gphone" label="联系方式" />
                      </el-table>
                    </el-popover>
                  </template>
                </el-table-column>
                <el-table-column fixed="right" label="操作">
                  <template #default="scope">
                    <el-button size="small" type="success" @click="roomUpdShow(scope.row.rid)">修改</el-button>
                    <el-popconfirm confirm-button-text="删除" cancel-button-text="取消" title="你确认要删除吗?" width="200px"
                      @confirm="deleteByRid(scope.row.rid)">
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
                <el-pagination background layout="prev, pager, next" :total="roomList.total"
                  :page-size="roomList.pageSize" @change="selectAll" />
              </el-row>

            </el-footer>
          </el-container>
        </div>
      </el-card>
    </el-col>

  </el-row>

  <!-- 新增抽屉开始 -->
  <el-drawer v-model="drawer" direction="ltr" size="50%">
    <template #header>
      <h4>房间设施</h4>
    </template>
    <template #default>
      <div>
        
          <el-card v-for="facility in facilityAllList" style="max-width: 480px">
            
              <div class="common-layout">
                <el-container>
                  <el-aside width="100px">
                    <el-image style="width: 100px; height: 100px"
                      :src="'http://localhost:8080/upload/' + facility.favatar" fit />
                  </el-aside>
                  <el-main>
                    <el-descriptions direction="vertical" colum="1">
                      <el-descriptions-item label="名称">{{ facility.fname }}</el-descriptions-item>
                      <el-descriptions-item label="数量">{{ facility.fnum }}</el-descriptions-item>
                      <el-descriptions-item label="价值">{{ facility.fvalue }}</el-descriptions-item>
                    </el-descriptions>
                  </el-main>
                  <el-main>
                    <el-checkbox-group v-model="facilityYes.value" size="large" >
                      <el-checkbox-button :key="facility.fid" :value="facility" >
                        选中
                      </el-checkbox-button>
                    </el-checkbox-group>
                  </el-main>
                </el-container>
              </div>
          </el-card>
        
      </div>
    </template>

    <template #footer>
      <div style="flex: auto">
        <el-button type="primary" @click="insertFacility()">添加设施</el-button>
      </div>
    </template>
  </el-drawer>
  <!-- 新增抽屉结束 -->

  <!-- 查看抽屉开始 -->
  <el-drawer v-model="drawer2">
    <template #header>
      <h4>房间设施</h4>
    </template>
    <template #default>
      <div>
        <el-card v-for="facility in facilityList" style="max-width: 480px">
          <div class="common-layout">
            <el-container>
              <el-aside width="100px">
                <el-image style="width: 100px; height: 100px" :src="'http://localhost:8080/upload/' + facility.favatar"
                  fit />
              </el-aside>
              <el-main>
                <el-descriptions direction="vertical" colum="1">
                  <el-descriptions-item label="名称">{{ facility.fname }}</el-descriptions-item>
                  <el-descriptions-item label="数量">{{ facility.fnum }}</el-descriptions-item>
                  <el-descriptions-item label="价值">{{ facility.fvalue }}</el-descriptions-item>
                </el-descriptions>
              </el-main>

            </el-container>
          </div>


        </el-card>
      </div>
    </template>

    <template #footer #default="scope">
      <div style="flex: auto">
        <el-button type="primary" @click="facilityDialogShow()">添加设施</el-button>
        
      </div>
    </template>

  </el-drawer>

  <!-- 查看抽屉结束 -->

  <!-- 新增窗口开始 -->
  <el-dialog v-model="roomAddShowWin" title="添加房间" width="500">
    <el-form>
      <el-form-item label="房间号" label-width="20%">
        <el-input v-model="roomAdd.rno" autocomplete="off" style="width: 300px;" />
      </el-form-item>

      <el-form-item label="房间类型" label-width="20%">
        <el-input v-model="roomAdd.rtype" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="房间价格" label-width="20%">
        <el-input v-model="roomAdd.rprice" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="头像" label-width="20%">
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
        <el-button @click="roomAddShowWin = false">取消</el-button>
        <el-button type="primary" @click="insert">确认</el-button>
      </div>
    </template>
  </el-dialog>
  <!-- 新增窗口结束 -->


  <!-- 修改窗口开始 -->
  <el-dialog v-model="roomUpdShowWin" title="添加员工" width="500">
    <el-form>
      <el-form-item label="房间号" label-width="20%">
        <el-input v-model="roomUpd.rno" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="房间类型" label-width="20%">
        <el-input v-model="roomUpd.rtype" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="房间价格" label-width="20%">
        <el-input v-model="roomUpd.rprice" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="房间照片" label-width="20%">
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
        <el-button @click="roomUpdShowWin = false">取消</el-button>
        <el-button type="primary" @click="update">确认</el-button>
      </div>
    </template>
  </el-dialog>
  <!-- 修改窗口结束 -->




</template>

<script setup>
import roomApi from '@/api/roomApi'
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus'
import { ElLoading } from 'element-plus'
import facilityApi from '@/api/facilityApi';



//查看设施抽屉
const drawer2 = ref(false)
//新增设施抽屉
const drawer = ref(false)


//新增房间实体
const roomAdd = ref({})
//显示添加房间窗口标识
const roomAddShowWin = ref(false)
//修改房间实体
const roomUpd = ref({})
//修改住客窗口标识
const roomUpdShowWin = ref(false)
//所有空余客房信息
const spareRoomList = ref([])

//所有房间的集合
const roomList = ref({
  total: 0,
  pageSize: 0
})

//特定房间内设施列表
const facilityList = ref({})
//所有设施集合
const facilityAllList = ref([])

//选中设施实体
const facilityYes = ref([])
//需要添加设施的房间号
const facilityRoom = ref('');

//搜索
const flag = ref("")

//新增上传头像的地址
const imageUrlAdd = ref("")

//修改上传头像的地址
const imageUrlUpd = ref("")



//获取显示抽屉并将获取特定房间内的设施
function facilityAddShow(facility,rid) {
  facilityList.value = facility
  facilityRoom.value=rid
  drawer2.value = true
}

//获取所有设施
function facilityAll() {
  
}

//获取所有设施，并显示添加设施抽屉
function facilityDialogShow() {
  facilityApi.selectAllTwo()
    .then(resp => {
      facilityAllList.value = resp.data
    })
  //显示添加设施抽屉
  drawer.value = true;
}

//上传添加的设施
function insertFacility(){
  facilityApi.insertFacilityAndRoom(facilityYes.value,facilityRoom.value)
    .then(resp => {

    })
}



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
    roomAdd.value.ravatar = resp.data


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
    roomUpd.value.ravatar = resp.data


  } else {
    ElMessage.error({
      message: resp.msg,
      type: 'success',
      duration: 1200
    })
  }

}

//删除客人信息
function deleteByRid(rid) {
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(0, 0, 0, 0.7)',
  })
  roomApi.delete(rid)
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

function roomUpdShow(rid) {

  //修改数据回填
  roomApi.selectById(rid)
    .then(resp => {
      roomUpd.value = resp.data;

      imageUrlUpd.value = `http://localhost:8080/upload/${roomUpd.value.ravatar}`

    })

  //显示修改窗口
  roomUpdShowWin.value = true;
}

//修改房间
function update() {
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(0, 0, 0, 0.7)',
  })
  roomApi.update(roomUpd.value)
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
        roomUpdShowWin.value = false



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

//添加
function insert() {
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(0, 0, 0, 0.7)',
  })
  roomApi.insert(roomAdd.value)
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
        roomAddShowWin.value = false

        //清空数据
        roomAdd.value = ""
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

//查询所有房间
function selectAll(pageNum) {
  roomApi.selectAll(pageNum, flag.value)
    .then(resp => {
      roomList.value = resp.data;
      console.log(roomList.value.list);
    })
}
selectAll(1);
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