<template>

  <div class="common-layout">
    <el-container>
      <el-header style="padding: 0;">
        <el-card>
          <el-input v-model="flag" style="width: 240px;float:right;" placeholder="搜索" @input="selectAll(1)"/>
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
                      <el-descriptions-item label="类型">{{ room.rtype }}</el-descriptions-item>
                      <el-descriptions-item label="价格">{{ room.rprice }}</el-descriptions-item>
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
      <el-form-item label="姓名">
        <el-input v-model="orderGuest.gname" autocomplete="off" style="width: 230px;" />
      </el-form-item>
      <el-form-item label="身份证">
        <el-input v-model="orderGuest.gno" autocomplete="off" style="width: 230px;" />
      </el-form-item>
      <el-form-item label="性别">
        <el-radio-group v-model="orderGuest.ggender">
          <el-radio-button label="男" value="男" />
          <el-radio-button label="女" value="女" />
        </el-radio-group>
      </el-form-item>
      <el-form-item label="联系方式">
        <el-input v-model="orderGuest.gphone" autocomplete="off" style="width: 230px;" />
      </el-form-item>
      <el-form-item label="入住时间">
        <el-date-picker v-model="orderGuest.gstart" type="date" :size="size" style="width: 230px;" />
      </el-form-item>
      <el-form-item label="退房时间">
        <el-date-picker v-model="orderGuest.gend" type="date" :size="size" style="width: 230px;" />
      </el-form-item>
      <el-form-item label="订单人员">
        <div class="flex gap-2">
          <el-tag v-for="guest in thisOrdersList" :key="guest" closable @click="deleteMem(guest.mid)"
            @close="handleClose(guest)">
            {{ guest.gname }}
          </el-tag>
          <el-button class="button-new-tag" size="small" @click="memberAddShow">
            + New Guest
          </el-button>
        </div>
      </el-form-item>

    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="buyRoomShow = false">取消</el-button>
        <el-button type="primary" @click="buyRoom">确认</el-button>
      </div>
    </template>
  </el-drawer>

  <!-- 订购房间窗口结束 -->

  <!-- 新增窗口开始 -->
  <el-dialog v-model="memberAddShowWin" title="添加会员" width="500">
    <el-form>

      <el-form-item label="姓名" label-width="20%">
        <el-input v-model="guestOne.gname" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="性别" label-width="20%">
        <el-radio-group v-model="guestOne.ggender">
          <el-radio-button label="男" value="男" />
          <el-radio-button label="女" value="女" />
        </el-radio-group>
      </el-form-item>
      <el-form-item label="身份证号" label-width="20%">
        <el-input v-model="guestOne.gno" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="联系方式" label-width="20%">
        <el-input v-model="guestOne.gphone" autocomplete="off" style="width: 300px;" />
      </el-form-item>

    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="memberAddShowWin = false">取消</el-button>
        <el-button type="primary" @click="insertMember">确认</el-button>
      </div>
    </template>
  </el-dialog>
  <!-- 新增窗口结束 -->

</template>

<script setup>
import bookingApi from '@/api/bookingApi'
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus'
import { ElLoading } from 'element-plus'
import facilityApi from '@/api/facilityApi'
import infoApi from '@/api/infoApi';




//个人信息实体
const info = ref({
  name:'',
  gender:'',
  no:'',
  phone:'',
  addr:''
})

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

//添加住客窗口标识
const memberAddShowWin = ref(false)

//订单人
const orderGuest = ref({})

//订单人员集合
const thisOrdersList = ref([])

//添加的住客实体
const guestOne = ref({

})

//订单实体
const order = ref({})





//显示订购房间窗口
function buyRoomShowWin(room) {
  console.log(info.value);
  orderGuest.value.gname=info.value.name
  orderGuest.value.gno=info.value.no
  orderGuest.value.ggender=info.value.gender
  orderGuest.value.gphone=info.value.phone
  thisRoom.value = room
  orderGuest.value.rid=room.rid
  buyRoomShow.value = true
}

//显示添加住客窗口
function memberAddShow() {
  if ((thisOrdersList.value.length + 2) > thisRoom.value.rnum) {
    ElMessage.error("该房间已经满了")
  }
  memberAddShowWin.value = true
}

//添加住客
function insertMember() {
  const biaoshi = ref(true)
  for (let index = 0; index < thisOrdersList.value.length; index++) {
    if (guestOne.value.gno == thisOrdersList.value[index].gno) {
      biaoshi.value = false
    }
  }
  if (biaoshi.value == true) {
    guestOne.value.gstart = orderGuest.value.gstart
    guestOne.value.gend = orderGuest.value.gend
    guestOne.value.rid = orderGuest.value.rid

    thisOrdersList.value.push(guestOne.value)
    guestOne.value = {};
    memberAddShowWin.value = false
    ElMessage({
      message: "添加成功",
      type: 'success',
      duration: 1200
    })
 
  } else {
    ElMessage.error({
      message: "该客人已经添加,不能重复添加",
      duration: 1200
    })
  }
}

//订购房间
function buyRoom() {
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(0, 0, 0, 0.7)',
  })


  order.value.guest = orderGuest.value
  order.value.ono=getTime
  //自动获取下单时间 存储到orders实体中
  order.value.otime = nowDate(time); order.value.on
  //将下单人的gno存到
  order.value.gno = order.value.guest.gno;
  //将下单人存到订单中
  thisOrdersList.value.push(orderGuest.value)


  //将多人添加到房间中
  order.value.guests = thisOrdersList.value;
  thisOrdersList.value = {}

  bookingApi.buyRoom(order.value)
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
        buyRoomShow.value=false
        orderGuest.value={}

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
  bookingApi.selectAllRoom(pageNum, flag.value)
    .then(resp => {
      roomList.value = resp.data
    })
}
selectAll(1)


//获取时间
let getTime = new Date().getTime(); //获取到当前时间戳
let time = new Date(getTime); //创建一个日期对象
function nowDate(time) {
  let year = time.getFullYear(); // 年
  let month = (time.getMonth() + 1).toString().padStart(2, '0'); // 月
  let date = time.getDate().toString().padStart(2, '0'); // 日
  let hour = time.getHours().toString().padStart(2, '0'); // 时
  let minute = time.getMinutes().toString().padStart(2, '0'); // 分
  let second = time.getSeconds().toString().padStart(2, '0'); // 秒
  return (
    year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second
  )
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