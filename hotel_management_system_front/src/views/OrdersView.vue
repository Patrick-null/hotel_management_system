<template>
  <el-row>
    <el-col :span="24">
      <el-card style="height: 550px;">
        <div class="common-layout">
          <el-container>
            <el-header style="height: 30px;">
              <el-form :inline="true" class="demo-form-inline">
                <el-button type="success" style="margin-bottom: 10px;" @click="ordersAddShow">添加</el-button>
                <el-button type="primary" :icon="Download" style="margin-bottom: 10px;" @click="downloadOrder">下载</el-button>
                <el-form-item label="搜索框" style="float: right;">
                  <el-input v-model="flag" placeholder="请输入需要搜索的姓名" clearable @input="selectAll(1)" />
                </el-form-item>
              </el-form>
              <!-- <el-form :inline="true" class="demo-form-inline">
                <el-form-item>
                  
                  <el-upload :on-success="uploadOrders" action="http://localhost:8080/admin/orders/upload"
                    :headers="headers" method="post" name="file" list-type="none" :show-file-list="false">
                    <el-button type="warning" :icon="UploadFilled" circle>
                      <el-icon>
                        <Upload />
                      </el-icon>
                    </el-button>
                  </el-upload>
                </el-form-item>
                <el-form-item style="float: right;margin-right: 80px;">
                  <el-input v-model="sname" placeholder="请输入姓名" clearable @input="selectByPage(1)"
                    style="width: 250px;" />
                  <el-input v-model="sage" placeholder="请输入年龄" clearable @input="selectByPage(1)"
                    style="width: 250px;" />
                </el-form-item>
              </el-form> -->
            </el-header>
            <el-main style="height: 430px;">
              <el-table :data="ordersList.list" border style="width: 100%">
                <el-table-column prop="oid" label="ID" width="40px" />
                <el-table-column prop="ono" label="订单编号" />
                <el-table-column prop="guests[0].gname" label="下单人" />
                <el-table-column prop="moneys" label="订单金额" />
                <el-table-column prop="otime" label="下单时间" show-overflow-tooltip />
                <el-table-column prop="ostart" label="入住时间" show-overflow-tooltip />
                <el-table-column prop="oend" label="离房时间" show-overflow-tooltip />
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
            </el-main>
            <el-footer>
              <el-row class="row-bg" justify="center" style="margin-top: 20px;">
                <el-pagination background layout="prev, pager, next" :total="ordersList.total"
                  :page-size="ordersList.pageSize" @change="selectAll" />
              </el-row>
            </el-footer>
          </el-container>
        </div>




      </el-card>
    </el-col>

  </el-row>



  <!-- 新增窗口开始 -->
  <el-dialog v-model="ordersAddShowWin" title="添加订单" width="500">
    <el-form>
      <el-form-item label="姓名" label-width="20%">
        <el-input v-model="guestBuy.gname" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="性别" label-width="20%">
        <el-radio-group v-model="guestBuy.ggender">
          <el-radio-button label="男" value="男" />
          <el-radio-button label="女" value="女" />
        </el-radio-group>
      </el-form-item>
      <el-form-item label="身份证号" label-width="20%">
        <el-input v-model="guestBuy.gno" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="联系方式" label-width="20%">
        <el-input v-model="guestBuy.gphone" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="住房时间" label-width="20%">
        <el-date-picker v-model="guestBuy.gstart" type="date" placeholder="入店时间" style="width: 140px;" />
        <el-icon style="margin-left: 3px; margin-right: 3px;">
          <Switch />
        </el-icon>
        <el-date-picker v-model="guestBuy.gend" type="date" placeholder="离店时间" style="width: 140px;" />
      </el-form-item>
      <el-form-item label="订单房间" label-width="20%">
        <el-select v-model="guestBuy.rid" placeholder="" style="width: 300px">
          <el-option v-for="item in spareRoomList" :key="item.rid" :label="item.rno + '-' + item.rtype"
            :value="item.rid" />
        </el-select>
      </el-form-item>

      <el-form-item label="订单人员" label-width="20%">
        <div class="flex gap-2">

          <el-tag v-for="guest in guestListAddDy" :key="guest" closable>
            {{ guest.gname }}
          </el-tag>
          <el-button class="button-new-tag" size="small" @click="then(guestBuy.rid)">
            添加住户
          </el-button>
        </div>
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

  <!-- 新增多人窗口开始 -->
  <el-dialog v-model="guestAddShowWin" title="添加会员" width="500">
    <el-form @submit.guestOne="submit">
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
        <el-button @click="guestAddShowWin = false">取消</el-button>
        <el-button type="primary" @click="insertOne">确认</el-button>
      </div>
    </template>
  </el-dialog>
  <!-- 新增多人窗口结束 -->



  <!-- 修改窗口开始 -->
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

      <el-form-item label="订单人员" label-width="20%">
        <el-select :placeholder="ordersUpd.guests[0].gname" style="width: 300px">
          <el-option v-for="item in ordersUpd.guests" :key="item.gname" :label="item.gname"
            :value="item.gname" />
        </el-select>
      </el-form-item>


      <el-form-item label="订单房间" label-width="20%">
        <el-select v-model="ordersUpd.rid" placeholder="" style="width: 300px">
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
  <!-- 修改窗口结束 -->
</template>

<script setup>
import ordersApi from '@/api/ordersApi'
import guestApi from '@/api/guestApi'
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus'
import { ElLoading } from 'element-plus'
import roomApi from '@/api/roomApi';
import { useTokenStore } from '@/stores/token';

//订单编号
const orderNumber = ref()
//新增订单实体
const ordersAdd = ref({
  ono: '',
  gno: '',
  otime: '',
  ostart:'',
  oend:'',
  moneys: '',
  guest: {},
  guests: [{}]
})

//下单人
const guestBuy = ref({
  gname: '',
  gno: '',
  ggender: '',
  gphone: '',
  gstart: '',
  gend: '',
  rid: ''
})
//房间内多个人员 - 静态
let guestListAdd = []

//房间内多个人员 - 动态
const guestListAddDy = ref([])

//房间内的单个人员
const guestOne = ref({
  gname: '',
  gno: '',
  ggender: '',
  gphone: '',
  gstart: '',
  gend: '',
  rid: ''
})

//显示添加订单窗口标识
const ordersAddShowWin = ref(false)
//显示添加住客窗口标识
const guestAddShowWin = ref(false)
//修改订单实体
const ordersUpd = ref({
  ono: '',
  gno: '',
  otime: '',
  ostart:'',
  oend:'',
  moneys: '',
  guest: {
    gname:''
  },
  guests: [{}]
})
//修改订单窗口标识
const ordersUpdShowWin = ref(false)
//所有空余客房信息
const spareRoomList = ref([])

//所有订单的集合
const ordersList = ref({
  total: 0,
  pageSize: 0
})

//搜索
const flag = ref('')

//判断人数是否超过房间人数
const sum = ref()

//判断是否超过房间人数
function then(rid) {

  roomApi.selectById(rid)
    .then(resp => {
      if (resp.data.rtype == '单人间') {

        ElMessage.error("房间已满")

      } else if (resp.data.rtype == '双人间') {
        if (guestListAddDy.value.length < 1) {
          guestAddShowWin.value = true;
        } else {
          ElMessage.error("房间已满")
        }
      } else if (resp.data.rtype == '豪华套房') {
        if (guestListAddDy.value.length < 3) {
          guestAddShowWin.value = true;
        } else {
          ElMessage.error("房间已满")
        }
      } else if (resp.data.rtype == '标准间') {
        if (guestListAddDy.value.length < 2) {
          guestAddShowWin.value = true;
        } else {
          ElMessage.error("房间已满")
        }
      } else if (resp.data.rtype == '总统套房') {
        if (guestListAddDy.value.length < 3) {
          guestAddShowWin.value = true;
        } else {
          ElMessage.error("房间已满")
        }
      }



    })

}


//添加住客
function insertOne() {
  guestOne.value.gstart = guestBuy.value.gstart
  guestOne.value.gend = guestBuy.value.gend
  guestOne.value.rid = guestBuy.value.rid


  // guestListAdd.push(guestOne.value)
  // guestListAddDy.value = guestListAdd
  if(guestListAddDy.value.guest.gno!=guestOne.value.gno){
    guestListAddDy.value.push(guestOne.value)
  }
  
  //数据清空
  guestOne.value = {}
  //关闭页面
  guestAddShowWin.value = false



}


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
        selectAll(ordersList.value.pageNum)
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
  ordersUpd.value.guests.push(ordersUpd.value.guest)
  
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
        selectAll(ordersList.value.pageNum)
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
      selectRoom(0)
      ordersUpd.value = resp.data
      ordersUpd.value.guest=ordersUpd.value.guests[0]
      
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
  ordersAdd.value.guest = guestBuy.value
  //自动获取下单时间 存储到orders实体中
  ordersAdd.value.otime = nowDate(time); ordersAdd.value.on
  //将下单人的gno存到
  ordersAdd.value.gno = ordersAdd.value.guest.gno;

  //获取订单编号
  ordersAdd.value.ono=getTime;

  //将多人添加到房间中
  ordersAdd.value.guests = guestListAddDy.value;
  guestListAddDy.value={}

  //将下单人存到房间中
  if(ordersAdd.value.guests!=null){
    ordersAdd.value.guests.push(guestBuy.value)
  }else{
    ordersAdd.value.guests=guestBuy.value
  }

  ordersAdd.value.ostart=guestBuy.value.gstart
  ordersAdd.value.oend=guestBuy.value.gend
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
        ordersAdd.value = {}
        guestBuy.value = {}


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
const tokenStore = useTokenStore()
const headers = reactive({
  token: tokenStore.tokenStr
});

//下载
function downloadOrder() {
    ordersApi.download()
      .then(resp => {
        let blob = new Blob([resp], { type: 'application/xlsx' });
        let url = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.download = "全部订单.xlsx";
        link.click();
        URL.revokeObjectURL(url);
      })
  }

  //上传
  function uploadOrders(response) {
    if (response.code == 10000) {
      selectByPage(1);
      ElMessage.success("上传成功");
    } else {
      ElMessage.error(response.msg);
    }

  }


//查询所有订单
function selectAll(pageNum) {
  ordersApi.selectAll(pageNum, flag.value)
    .then(resp => {
      ordersList.value = resp.data
    })
}


selectAll(1);
</script>

<style></style>