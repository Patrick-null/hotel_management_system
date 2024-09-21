<template>
  <div class="common-layout">
    <el-container>
      <el-header style="padding: 0; ">
        <el-card>
          <el-input v-model="flag" style="width: 240px;float:right;" placeholder="搜索" @input="selectMyAll(1)" />
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
                          <el-button size="small" type="success">使用</el-button>
                        </template>
                      </el-popconfirm>
                      <el-button size="small" type="primary" @click="ordersUpdShow(order.ono, order.gno)">修改</el-button>
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



  <!-- 修改订单窗口开始 -->
  <el-dialog v-model="ordersUpdShowWin" title="修改订单" width="500">
    <el-form>
      <el-form-item label="订单号" label-width="20%">
        <el-input v-model="ordersUpd.ono" disabled autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="下单人" label-width="20%">
        <el-input v-model="ordersUpd.guest.gname" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="下单时间" label-width="20%">
        <el-date-picker type="datetime" disabled :placeholder="ordersUpd.otime" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="入住时间" label-width="20%">
        <el-date-picker v-model="ordersUpd.ostart" type="date" :placeholder="ordersUpd.ostart" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="离店时间" label-width="20%">
        <el-date-picker v-model="ordersUpd.oend" type="date" :placeholder="ordersUpd.oend" style="width: 300px;" />
      </el-form-item>

      <el-form-item label="订单人员" label-width="20%">
        <el-select :placeholder="ordersUpd.guests[0].gname" style="width: 300px">
          <el-option v-for="item in ordersUpd.guests" :key="item.gname" :label="item.gname" :value="item.gname" />
        </el-select>
      </el-form-item>


      <el-form-item label="订单房间" label-width="20%">
        <el-select v-model="ordersUpd.rid" style="width: 300px">
          <el-option v-for="item in spareRoomList" :key="item.rid" :label="item.rno + '-' + item.rtype"
            :value="item.rid" />
        </el-select>
      </el-form-item>


    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="ordersUpdShowWin = false">取消</el-button>
        <el-button type="primary" @click="update">确认</el-button>
      </div>
    </template>
  </el-dialog>
  <!-- 修改订单窗口结束 -->
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

//修改窗口标识
const ordersUpdShowWin = ref(false)
//修改订单实体
const ordersUpd = ref({
  ono: '',
  gno: '',
  otime: '',
  ostart: '',
  oend: '',
  moneys: '',
  ostate: '',
  guest: {
    gname: '',
    gstart: '',
    gend: ''
  },
  guests: [{}]
})
//所有空余客房信息
const spareRoomList = ref([])
//修改订单
function update() {
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(0, 0, 0, 0.7)',
  })
  //ordersUpd.value.otime = nowDate(time)
  ordersUpd.value.guest.gstart = ordersUpd.value.ostart
  ordersUpd.value.guest.gend = ordersUpd.value.oend
  ordersUpd.value.guests.push(ordersUpd.value.guest)
  bookingApi.updateOrders(ordersUpd.value)
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
        ordersUpdShowWin.value = false

        //刷新表格

        selectMyAll(myAllOrders.value.pageNum)
      } else {
        ElMessage({
          message: resp.msg,
          type: 'error',
          duration: 1200
        });
      }

    })
}


//显示修改窗口并回显数据
function ordersUpdShow(ono, gno) {
  //回现数据


  bookingApi.selectByOno(ono, gno)
    .then(resp => {
      selectRoom(0)

      ordersUpd.value = resp.data
      //ordersUpd.value.guest=ordersUpd.value.guests[0]
      if (ordersUpd.value.ostate == 1) {
        ElMessage.error({
          message: "订单已完成 无法修改",
          type: 'success',
          duration: 1200
        })
      } else {
        //显示窗口
        ordersUpdShowWin.value = true
      }
    })
}


//查询所有空闲房间
function selectRoom(rstate) {
  bookingApi.selectByStateSpareRoom(rstate)
    .then(resp => {
      spareRoomList.value = resp.data
    })

}

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
      admin.value = resp.data
      info.value = admin.value.info
      selectMyAll(1)
    })
}
selectUserInfo()

//我的订单实体所有
const myAllOrders = ref({})

function selectMyAll(pageNum) {
  bookingApi.selectMyAll(pageNum, info.value.no, flag.value)
    .then(resp => {
      myAllOrders.value = resp.data
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