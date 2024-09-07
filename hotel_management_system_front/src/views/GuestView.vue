<template>
  <el-row>
    <el-col :span="24">
      <el-card style="height: 550px;">
        <div class="common-layout">
          <el-container>
            <el-header style="height: 30px;">
              <el-form :inline="true" class="demo-form-inline">
                <el-button type="success"  @click="guestAddShow">添加</el-button>
                <el-form-item label="搜索框" style="float: right;">
                  <el-input v-model="flag" placeholder="请输入需要搜索的姓名" clearable @input="selectAll(1)" />
                </el-form-item>
              </el-form>
            </el-header>
            <el-main style="height: 445px;">
              <el-table :data="guestList.list" border style="width: 100%">
                <el-table-column prop="gid" label="ID" width="50" />
                <el-table-column prop="gname" label="姓名" width="70px" />
                <el-table-column prop="ggender" label="性别" width="60px" />
                <el-table-column prop="gno" label="身份证号" width="90px" show-overflow-tooltip />
                <el-table-column prop="gphone" label="手机号" width="80" show-overflow-tooltip />
                <el-table-column prop="gstart" label="入住时间" width="110px" show-overflow-tooltip />
                <el-table-column prop="gend" label="离店时间" width="110px" show-overflow-tooltip />
                <el-table-column label="状态" width="90">
                  <template #default="scope">
                    <el-tag v-if="scope.row.gstate == 0" type="primary" effect="dark">已预约</el-tag>
                    <el-tag v-else-if="scope.row.gstate == 1" type="success" effect="dark">已入住</el-tag>
                    <el-tag v-else type="info" effect="dark">已离店</el-tag>
                  </template>
                </el-table-column>
                <el-table-column prop="room.rno" label="房间号" width="70" />

                <el-table-column prop="room.rtype" label="房间类型" width="100" />
                <el-table-column fixed="right" label="操作" width="140">
                  <template #default="scope">
                    <el-button size="small" type="success" @click="guestUpdShow(scope.row.gid)">修改</el-button>
                    <el-popconfirm confirm-button-text="删除" cancel-button-text="取消" title="你确认要删除吗?" width="200px"
                      @confirm="deleteByGid(scope.row.gid)">
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
                <el-pagination background layout="prev, pager, next" :total="guestList.total"
                  :page-size="guestList.pageSize" @change="selectAll" />
              </el-row>
            </el-footer>
          </el-container>
        </div>





      </el-card>
    </el-col>

  </el-row>


  <!-- 新增窗口开始 -->
  <el-dialog v-model="guestAddShowWin" title="添加员工" width="500">
    <el-form>
      <el-form-item label="姓名" label-width="20%">
        <el-input v-model="guestAdd.gname" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="性别" label-width="20%">
        <el-radio-group v-model="guestAdd.ggender" style="width: 300px;">
          <el-radio-button label="男" value="男" />
          <el-radio-button label="女" value="女" />
        </el-radio-group>
      </el-form-item>
      <el-form-item label="身份证号" label-width="20%">
        <el-input v-model="guestAdd.gno" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="手机号" label-width="20%">
        <el-input v-model="guestAdd.gphone" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="入住时间" label-width="20%">
        <el-date-picker v-model="guestAdd.gstart" type="date" placeholder="入住时间" :size="size" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="离店时间" label-width="20%">
        <el-date-picker v-model="guestAdd.gend" type="date" placeholder="离店时间" :size="size" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="房间" label-width="20%">
        <el-select v-model="guestAdd.rid" placeholder="" style="width: 300px">
          <el-option v-for="item in spareRoomList" :key="item.rid" :label="item.rno + '-' + item.rtype"
            :value="item.rid" />
        </el-select>
      </el-form-item>


    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="guestAddShowWin = false">取消</el-button>
        <el-button type="primary" @click="insert">确认</el-button>
      </div>
    </template>
  </el-dialog>
  <!-- 新增窗口结束 -->

  <!-- 修改窗口开始 -->
  <el-dialog v-model="guestUpdShowWin" title="添加员工" width="500">
    <el-form>
      <el-form-item label="姓名" label-width="20%">
        <el-input v-model="guestUpd.gname" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="性别" label-width="20%">
        <el-radio-group v-model="guestUpd.ggender" style="width: 300px;">
          <el-radio-button label="男" value="男" />
          <el-radio-button label="女" value="女" />
        </el-radio-group>
      </el-form-item>
      <el-form-item label="身份证号" label-width="20%">
        <el-input v-model="guestUpd.gno" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="手机号" label-width="20%">
        <el-input v-model="guestUpd.gphone" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="入住时间" label-width="20%">
        <el-date-picker v-model="guestUpd.gstart" type="date" placeholder="入住时间" :size="size" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="离店时间" label-width="20%">
        <el-date-picker v-model="guestUpd.gend" type="date" placeholder="离店时间" :size="size" style="width: 300px;" />
      </el-form-item>

      <el-form-item label="房间" label-width="20%">
        <el-select :placeholder="guestUpd.room.rtype" v-model="guestUpd.rid" style="width: 300px">
          <el-option v-for="item in spareRoomList" :key="item.rid" :label="item.rno + '-' + item.rtype"
            :value="item.rid" />
        </el-select>
      </el-form-item>


    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="guestUpdShowWin = false">取消</el-button>
        <el-button type="primary" @click="update">确认</el-button>
      </div>
    </template>
  </el-dialog>
  <!-- 修改窗口结束 -->

</template>
<script setup>

import guestApi from '@/api/guestApi'
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus'
import { ElLoading } from 'element-plus'

//所有住客信息
const guestList = ref([])

//搜索的实体
const flag = ref('')

//新增住客实体
const guestAdd = ref({})
//新增住客窗口标识
const guestAddShowWin = ref(false)
//修改住客实体
const guestUpd = ref({
  room: {
    rtype: ''
  }
})
//修改住客窗口标识
const guestUpdShowWin = ref(false)
//所有空余客房信息
const spareRoomList = ref([])

//删除客人信息
function deleteByGid(gid) {
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(0, 0, 0, 0.7)',
  })
  guestApi.delete(gid)
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

//获取客房信息
function selectRoom(rstate) {
  guestApi.selectByStateSpareRoom(rstate)
    .then(resp => {
      spareRoomList.value = resp.data
    })
}
//修改
function update() {
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(0, 0, 0, 0.7)',
  })
  guestApi.update(guestUpd.value)
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
        guestUpdShowWin.value = false



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

//显示修改窗口并加载客房数据
function guestUpdShow(gid) {
  //加载客房信息
  selectRoom(0);

  //修改数据回填
  guestApi.selectById(gid)
    .then(resp => {
      guestUpd.value = resp.data;
      console.log(resp);

    })

  //显示修改窗口
  guestUpdShowWin.value = true;
}

//添加
function insert() {
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(0, 0, 0, 0.7)',
  })
  guestApi.insert(guestAdd.value)
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
        guestAddShowWin.value = false

        //清空数据
        guestAdd.value = ""

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

//显示新增窗口并加载客房数据
function guestAddShow() {
  //加载客房信息
  selectRoom(0);

  //显示新增窗口
  guestAddShowWin.value = true;
}

function selectAll(pageNum) {
  guestApi.selectAll(pageNum, flag.value)
    .then(resp => {
      console.log(resp);

      guestList.value = resp.data

    })
}
selectAll(1);
</script>

<style></style>