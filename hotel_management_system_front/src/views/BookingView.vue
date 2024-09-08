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

            <el-card v-for="room in roomList.list" :key="room.rid">
              <div class="common-layout">

                <el-container>
                  <el-aside width="100px">
                    <el-image style="width: 100px; height: 100px" :src="'http://localhost:8080/upload/' + room.ravatar"
                      fit />
                  </el-aside>
                  <el-main width="250px">
                    <el-descriptions direction="horizontal">
                      <el-descriptions-item label="名称">{{ room.rno }}</el-descriptions-item>
                      <el-descriptions-item label="数量">{{ room.rtype }}</el-descriptions-item>
                      <el-descriptions-item label="价值">{{ room.rprice }}</el-descriptions-item>
                    </el-descriptions>
                  </el-main>
                  <el-main style="float: right;">
                    <el-checkbox-group size="large" style="float: right;">

                      <el-checkbox-button @click="buyRoomShowWin(room)">
                        订购
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
        <el-pagination background layout="prev, pager, next" :total="roomList.total" :page-size="roomList.pageSize"
          @change="selectAll" />
      </el-row>
    </el-container>
  </div>

  <!-- 订购房间窗口开始 -->
  <el-drawer v-model="buyRoomShow" title="订购房间">
    <el-card style="max-width: 480px;margin-bottom: 20px;">

      <div class="common-layout">
        <el-container>
          <el-aside width="100px" style="padding-top: 18px;">
            <el-image style="width: 100px; height: 100px" :src="'http://localhost:8080/upload/' + thisRoom.ravatar"
              fit />
          </el-aside>
          <el-main>
            <el-descriptions direction="horizontal" column="1">
              <el-descriptions-item label="名称">{{ thisRoom.rno }}</el-descriptions-item>
              <el-descriptions-item label="数量">{{ thisRoom.rtype }}</el-descriptions-item>
              <el-descriptions-item label="价值">{{ thisRoom.rprice }}</el-descriptions-item>
            </el-descriptions>
          </el-main>
        </el-container>
      </div>
    </el-card>

    <el-form label-width="auto" style="max-width: 600px">
      <el-form-item v-model="orderGuest.gname" style="width: 300px;" label="姓名" >
        <el-input  />
      </el-form-item>
      <el-form-item v-model="orderGuest.gno" style="width: 300px;" label="身份证">
        <el-input />
      </el-form-item>
      <el-form-item label="性别">
        <el-radio-group v-model="orderGuest.ggender" >
          <el-radio-button label="男" value="男" />
          <el-radio-button label="女" value="女" />
        </el-radio-group>
      </el-form-item>
      <el-form-item v-model="orderGuest.gphone" label="联系方式" style="width: 300px;">
        <el-input />
      </el-form-item>
      <el-form-item  label="入住时间">
        <el-date-picker v-model="orderGuest.gstart" type="date"  :size="size"  style="width: 230px;"/>
      </el-form-item>
      <el-form-item label="退房时间">
        <el-date-picker v-model="orderGuest.gend" type="date"  :size="size"  style="width: 230px;" />
      </el-form-item>


    </el-form>
  </el-drawer>

  <!-- 订购房间窗口结束 -->
</template>

<script setup>
import bookingApi from '@/api/bookingApi'
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus'
import { ElLoading } from 'element-plus'
import facilityApi from '@/api/facilityApi';

//所有房间的集合
const roomList = ref({
  total: 0,
  pageSize: 0
})
//搜索标识
const flag = ref('')
//选择的当前房间
const thisRoom = ref({})

//订购房间窗口标识
const buyRoomShow = ref(false)

//订单人
const orderGuest = ref({})

//显示订购房间窗口
function buyRoomShowWin(room) {

  console.log(room);

  thisRoom.value = room
  buyRoomShow.value = true
}

//订购房间
function buyRoom() {

}

function selectAll(pageNum) {
  bookingApi.selectAllRoom(pageNum, flag.value)
    .then(resp => {
      roomList.value = resp.data
    })
}
selectAll(1)

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