<template>
  <el-row>
    <el-col :span="24">
      <el-card>
        <el-form :inline="true" class="demo-form-inline">
          <el-button type="success" style="margin-bottom: 10px;" @click="roomAddShowWin = true">添加</el-button>
          <el-form-item label="搜索框" style="float: right;">
            <el-input placeholder="请输入需要搜索的姓名" clearable />
          </el-form-item>
        </el-form>

        <el-table :data="roomList" border style="width: 100%">
          <el-table-column prop="rid" label="ID" />
          <el-table-column prop="rno" label="房间号" />
          <el-table-column prop="rtype" label="房间类型" />
          <el-table-column prop="rprice" label="房间价格" show-overflow-tooltip />
          <el-table-column label="状态">
            <template #default="scope">
              <el-tag v-if="scope.row.rstate == 0" type="success" effect="dark">空闲</el-tag>
              <el-tag v-else-if="scope.row.rstate == 1" type="primary" effect="dark">已预订</el-tag>
              <el-tag v-else-if="scope.row.rstate == 2" type="info" effect="dark">已入住</el-tag>
              <el-tag v-else-if="scope.row.rstate == 3" type="danger" effect="dark">未开放</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="" label="房间设施" />
          <el-table-column  label="房间人员">
          <template #default="scope">
            <el-popover placement="right" :width="400" trigger="click">
              <template #reference>
                <el-button style="margin-right: 16px">Click to activate</el-button>
              </template>
              <el-table :data="scope.row.guests">
                <el-table-column width="150" property="gname" label="姓名" />
                <el-table-column width="100" property="gno" label="身份证号" />
                <el-table-column width="300" property="gphone" label="联系方式" />
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
      </el-card>
    </el-col>

  </el-row>

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


//新增住客实体
const roomAdd = ref({})
//显示添加房间窗口标识
const roomAddShowWin = ref(false)
//修改住客实体
const roomUpd = ref({})
//修改住客窗口标识
const roomUpdShowWin = ref(false)
//所有空余客房信息
const spareRoomList = ref([])

//所有房间的集合
const roomList = ref([])


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
        selectAll()
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
        selectAll()
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

        //刷新表格
        selectAll()
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
function selectAll() {
  roomApi.selectAll()
    .then(resp => {
      roomList.value = resp.data;
      console.log(roomList.value);
    })
}
selectAll();
</script>

<style></style>