<template>
  <el-row>
    <el-col :span="24">
      <el-card>
        <el-form :inline="true" class="demo-form-inline">
          <el-button type="success" style="margin-bottom: 10px;" @click="ordersAddShowWin = true">添加</el-button>
          <el-form-item label="搜索框" style="float: right;">
            <el-input placeholder="请输入需要搜索的姓名" clearable />
          </el-form-item>
        </el-form>

        <el-table :data="ordersList" border style="width: 100%">
          <el-table-column prop="oid" label="ID" />
          <el-table-column prop="ono" label="订单编号" />
          <el-table-column prop="gid" label="下单人" />
          <el-table-column prop="otime" label="下单时间" show-overflow-tooltip />
          <el-table-column label="状态">
            <template #default="scope">
              <el-tag v-if="scope.row.ostate == 0" type="success" effect="dark">订单未使用</el-tag>
              <el-tag v-else-if="scope.row.ostate == 1" type="primary" effect="dark">订单完成</el-tag>
            </template>
          </el-table-column>
          
          <el-table-column fixed="right" label="操作">
            <template #default="scope">
              <el-button size="small" type="success" @click="ordersUpdShow(scope.row.rid)">修改</el-button>
              <el-popconfirm confirm-button-text="删除" cancel-button-text="取消" title="你确认要删除吗?" width="200px"
                >
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
  <el-dialog v-model="ordersAddShowWin" title="添加订单" width="500">
    <el-form>
      <el-form-item label="订单号" label-width="20%">
        <el-input v-model="ordersAdd.ono" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="下单人" label-width="20%">
        <el-input v-model="ordersAdd.gid" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="订单时间" label-width="20%">
        <el-input v-model="ordersAdd.otime" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="订单房间" label-width="20%">
        <el-input v-model="ordersAdd.rno" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="订单人员" label-width="20%">
        <el-input v-model="ordersAdd.gid" autocomplete="off" style="width: 300px;" />
      </el-form-item>

    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="ordersAddShowWin = false">取消</el-button>
        <el-button type="primary" @click="insert">确认</el-button>
      </div>
    </template>
  </el-dialog>
  <!-- 新增窗口结束 -->
</template>

<script setup>
import ordersApi from '@/api/ordersApi'
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus'
import { ElLoading } from 'element-plus'

//新增订单实体
const ordersAdd = ref({})
//显示添加订单窗口标识
const ordersAddShowWin = ref(false)
//修改订单实体
const ordersUpd = ref({})
//修改订单窗口标识
const ordersUpdShowWin = ref(false)
//所有空余客房信息
const spareRoomList = ref([])

//所有订单的集合
const ordersList = ref([])

//增加订单
function insert() {
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(0, 0, 0, 0.7)',
  })
  ordersApi.insert(ordersAdd.value)
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
        ordersAddShowWin.value = false

        //清空数据
        ordersAdd.value = ""

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


//查询所有订单
function selectAll(){
  ordersApi.selectAll()
    .then(resp => {
      ordersList.value = resp.data
    })
}
selectAll();
</script>

<style>
</style>