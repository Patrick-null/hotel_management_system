<template>
  <div class="common-layout">
    <el-container>
      <el-header style="padding: 0;">
        <el-card>

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
                      <el-descriptions-item label="订单人">{{ admin.info.name }}</el-descriptions-item>
                      <el-descriptions-item label="订单时间">{{ order.otime }}</el-descriptions-item>
                      <el-descriptions-item label="房间类型">{{ order.room.rtype }}</el-descriptions-item>
                      <el-descriptions-item label="房间人员">{{ order.room.guest }}</el-descriptions-item>
                      <el-descriptions-item label="订单状态">
                        <el-tag v-if="order.ostate==0" effect="light" size="small">未使用</el-tag>
                        <el-tag v-else="order.ostate==1" effect="light" type="success" size="small">已使用</el-tag>
                      </el-descriptions-item>
                    </el-descriptions>
                  </el-main>
                  <el-main style="float: right;">
                    <el-checkbox-group size="large" style="float: right;">

                      <el-checkbox-button @click="">
                        使用
                      </el-checkbox-button>

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
          :page-size="myAllOrders.pageSize" @change="selectAll" />
      </el-row>
    </el-container>
  </div>
</template>
<script setup>
import bookingApi from '@/api/bookingApi';
import infoApi from '@/api/infoApi';
import { ref, reactive } from 'vue';

const admin = ref({
  aid: 0,
  username: '',
  info: {
    aid:'',
    name: '',
    gender: '',
    no: '',
    phone: '',
    addr: ''
  }
})
const info = ref({})
//获取个人信息
function selectUserInfo() {
  infoApi.selectByUsername()
    .then(resp => {
      console.log(resp.data);
      
      admin.value = resp.data
      info.value=admin.value.info
    })
}
selectUserInfo()

//我的订单实体所有
const myAllOrders = ref({})

function selectMyAll(pageNum, aid) {
  bookingApi.selectMyAll(pageNum, aid)
    .then(resp => {
      console.log(resp.data);
      myAllOrders.value = resp.data

    })
}
selectMyAll("1", admin.value.aid)
</script>

<style></style>