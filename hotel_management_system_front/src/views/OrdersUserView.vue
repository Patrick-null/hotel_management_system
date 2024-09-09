<template>
  <div class="common-layout">
    <el-container>
      <el-header style="padding: 0; ">
        <el-card>
          <el-input v-model="flag" style="width: 240px;float:right;" placeholder="搜索" @input="selectMyAll(1)"/>
        </el-card>
      </el-header>
      <el-main style=" padding: 0;  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2)">
        <el-card style="height: 452px;">
          <ul class="infinite-list" style="overflow: auto">
            <el-card v-for="order in myAllOrders.list" :key="order.oid">

              <div class="common-layout">
                <el-container>
                  <el-aside width="100px">
                    <el-image style="width: 100px; height: 100px"
                      :src="'http://localhost:8080/upload/' + order.room.ravatar" fit />
                  </el-aside>
                  <el-main width="250px">
                    <el-descriptions direction="horizontal">
                      <el-descriptions-item label="订单编号">{{ order.ono }}</el-descriptions-item>
                      
                      <el-descriptions-item label="订单时间">{{ order.otime }}</el-descriptions-item>
                      <el-descriptions-item label="订单金额">{{ order.moneys }}</el-descriptions-item>
                      <el-descriptions-item label="房间类型">{{ order.room.rtype }}</el-descriptions-item>
                      <el-descriptions-item label="房间人员">
                        <el-popover placement="right" :width="400" trigger="click">
                          <template #reference>
                            <el-button style="" size="small">查看</el-button>
                          </template>
                          <el-table :data="order.guests">
                            <el-table-column property="gname" label="姓名" />
                            <el-table-column property="ggender" label="性别" />
                            <el-table-column property="gno" label="身份证" />
                            <el-table-column property="gphone" label="手机号" />
                          </el-table>
                        </el-popover>
                      </el-descriptions-item>
                      <el-descriptions-item label="订单状态">
                        <el-tag v-if="order.ostate == 0" effect="light" size="small">未使用</el-tag>
                        <el-tag v-else="order.ostate==1" effect="light" type="success" size="small">已使用</el-tag>
                      </el-descriptions-item>
                    </el-descriptions>
                  </el-main>
                  <el-main style="float: right;">

                    <el-checkbox-group size="large" style="float: right;">
                      <el-popconfirm confirm-button-text="使用" cancel-button-text="取消" title="你确认要使用吗?" width="200px"
                        @confirm="useOrder(order.oid)">
                        <template #reference>
                          <el-button size="small" type="danger">使用</el-button>
                        </template>
                      </el-popconfirm>
                    </el-checkbox-group>

                  </el-main>
                </el-container>

              </div>

            </el-card>

          </ul>
        </el-card>
      </el-main>
      <el-row class="row-bg" justify="center" style="margin-top: 20px;">
        <el-pagination background layout="prev, pager, next" :total="myAllOrders.total"
          :page-size="myAllOrders.pageSize" @change="selectMyAll" />
      </el-row>
    </el-container>
  </div>
</template>
<script setup>
import bookingApi from '@/api/bookingApi';
import infoApi from '@/api/infoApi';
import { useOrderedChildren } from 'element-plus';
import { ref, reactive } from 'vue';
import { ElLoading } from 'element-plus'
import { ElMessage } from 'element-plus'
import { ElNotification } from 'element-plus'

const admin = ref({
  aid: 0,
  username: '',
  info: {
    aid: '',
    name: '',
    gender: '',
    no: '',
    phone: '',
    addr: ''
  }
})
const flag = ref("")

const info = ref({})

//用户使用订单
function useOrder(oid) {
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(0, 0, 0, 0.7)',
  })
  bookingApi.useOrder(oid)
    .then(resp => {
      loading.close()
      if (resp.code == 10000) {

        ElNotification({
          title: resp.msg,
          message: '使用成功',
          type: 'success',
        })


        selectMyAll(myAllOrders.value.pageNum)
      } else {
        ElMessage.error({
          message: resp.msg,
          type: 'success',
          duration: 1200
        })
      }
    }
    )
}


//获取个人信息
function selectUserInfo() {
  infoApi.selectByUsername()
    .then(resp => {
      console.log(resp.data);

      admin.value = resp.data
      info.value = admin.value.info
      //console.log(info.value.no);
      selectMyAll(1)
    })
}
selectUserInfo()

//我的订单实体所有
const myAllOrders = ref({})

function selectMyAll(pageNum) {
  bookingApi.selectMyAll(pageNum, info.value.no,flag.value)
    .then(resp => {
      console.log(resp.data);
      myAllOrders.value = resp.data
      console.log(myAllOrders.value);

    })
}



</script>

<style scoped>
.infinite-list {
  height: 410px;
  padding: 0;
  margin: 0;
  list-style: none;
}

.infinite-list .infinite-list-item {
  display: flex;
  align-items: center;
  justify-content: center;




}

.infinite-list .infinite-list-item+.list-item {
  margin-top: 5px;
}
</style>