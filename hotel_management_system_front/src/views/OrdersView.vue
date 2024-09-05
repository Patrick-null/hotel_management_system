<template>
  <el-row>
    <el-col :span="24">
      <el-card>
        <el-form :inline="true" class="demo-form-inline">
          <el-button type="success" style="margin-bottom: 10px;" @click="ordersAddShow">添加</el-button>
          <el-form-item label="搜索框" style="float: right;">
            <el-input placeholder="请输入需要搜索的姓名" clearable />
          </el-form-item>
        </el-form>

        <el-table :data="ordersList" border style="width: 100%">
          <el-table-column prop="oid" label="ID" />
          <el-table-column prop="ono" label="订单编号" />
          <el-table-column prop="guest.gname" label="下单人" />
          <el-table-column prop="otime" label="下单时间" show-overflow-tooltip />
          <el-table-column label="状态">
            <template #default="scope">
              <el-tag v-if="scope.row.ostate == 0" type="success" effect="dark">订单未使用</el-tag>
              <el-tag v-else-if="scope.row.ostate == 1" type="primary" effect="dark">订单完成</el-tag>
            </template>
          </el-table-column>

          <el-table-column fixed="right" label="操作">
            <template #default="scope">
              <el-button size="small" type="success" @click="ordersUpdShow(scope.row.oid)">修改</el-button>
              <el-popconfirm confirm-button-text="使用" cancel-button-text="取消" title="你确认要使用吗?"
                @confirm="deleteByOid(scope.row.oid)" width="200px">
                <template #reference>
                  <el-button size="small" type="danger">使用</el-button>
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
      <el-form-item label="姓名" label-width="20%">
        <el-input v-model="ordersAdd.guest.gname" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="性别" label-width="20%">
        <el-radio-group v-model="ordersAdd.guest.ggender">
          <el-radio-button label="男" value="男" />
          <el-radio-button label="女" value="女" />
        </el-radio-group>
      </el-form-item>
      <el-form-item label="身份证号" label-width="20%">
        <el-input v-model="ordersAdd.guest.gno" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="联系方式" label-width="20%">
        <el-input v-model="ordersAdd.guest.gphone" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="住房时间" label-width="20%">
        <el-date-picker v-model="ordersAdd.guest.gstart" type="date" placeholder="入店时间" style="width: 140px;" />
        <el-icon style="margin-left: 3px; margin-right: 3px;">
          <Switch />
        </el-icon>
        <el-date-picker v-model="ordersAdd.guest.gend" type="date" placeholder="离店时间" style="width: 140px;" />
      </el-form-item>
      <el-form-item label="订单房间" label-width="20%">
        <el-select v-model="ordersAdd.guest.rid" placeholder="" style="width: 300px">
          <el-option v-for="item in spareRoomList" :key="item.rid" :label="item.rno + '-' + item.rtype"
            :value="item.rid" />
        </el-select>
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



  <!-- 修改窗口开始 -->
  <el-dialog v-model="ordersUpdShowWin" title="修改订单" width="500">
    <el-form>
      <el-form-item label="订单号" label-width="20%">
        <el-input v-model="ordersUpd.ono" disabled autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="下单人" label-width="20%">
        <el-input v-model="ordersUpd.gid" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="下单时间" label-width="20%">
        <el-date-picker type="datetime" disabled :placeholder="ordersUpd.otime" style="width: 300px;" />
      </el-form-item>

      <el-form-item label="订单人员" label-width="20%">
        <el-input v-model="ordersUpd.gid" autocomplete="off" style="width: 300px;" />
      </el-form-item>

    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="ordersUpdShowWin = false">取消</el-button>
        <el-button type="primary" @click="update">确认</el-button>
      </div>
    </template>
  </el-dialog>
  <!-- 修改窗口结束 -->
</template>

<script setup>
import ordersApi from '@/api/ordersApi'
import guestApi from '@/api/guestApi'
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus'
import { ElLoading } from 'element-plus'

//订单编号
const orderNumber = ref()
//新增订单实体
const ordersAdd = ref({
  ono: '',
  gno:'',
  otime:'',
  guest: {
    gname: '',
    gno: '',
    ggender:'',
    gphone: '',
    gstart: '',
    gend: '',
    rid: ''
  },
  guests:[{}]
})

//显示添加订单窗口标识
const ordersAddShowWin = ref(false)
//显示添加住客窗口标识
const guestAddShowWin = ref(false)
//修改订单实体
const ordersUpd = ref({})
//修改订单窗口标识
const ordersUpdShowWin = ref(false)
//所有空余客房信息
const spareRoomList = ref([])

//所有订单的集合
const ordersList = ref([])



//删除订单
function deleteByOid(oid) {
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(0, 0, 0, 0.7)',
  })
  ordersApi.delete(oid)
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


//修改订单
function update() {
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(0, 0, 0, 0.7)',
  })
  ordersUpd.value.otime = nowDate(time);
  ordersApi.update(ordersUpd.value)
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


//显示修改窗口并回显数据
function ordersUpdShow(oid) {
  //回现数据
  ordersApi.selectById(oid)
    .then(resp => {
      ordersUpd.value = resp.data
      console.log(resp);
    })
  //显示窗口
  ordersUpdShowWin.value = true
}

//增加订单
function insert() {
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(0, 0, 0, 0.7)',
  })
  //自动获取下单时间 存储到orders实体中
  ordersAdd.value.otime = nowDate(time); ordersAdd.value.on
  //将下单人的gno存到
  ordersAdd.value.gno=ordersAdd.value.guest.gno;
  console.log("213");

  //将搜索到房间添加到添加订单中

  
  console.log(ordersAdd.value);

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


//显示增加窗口
function ordersAddShow() {
  //获取所有空闲房间
  selectRoom(0)
  //显示新增窗口
  ordersAddShowWin.value = true;
}

//显示增加住房人员窗口
function showInputGuest() {

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


//查询所有空闲房间
function selectRoom(rstate) {
  guestApi.selectByStateSpareRoom(rstate)
    .then(resp => {
      spareRoomList.value = resp.data
    })
}




//查询所有订单
function selectAll() {
  ordersApi.selectAll()
    .then(resp => {
      ordersList.value = resp.data
    })
}
selectAll();
</script>

<style></style>