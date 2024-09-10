<template>
  <el-row :gutter="24" style="margin-bottom: 20px;">
    <el-col :span="12">
      <el-card
        style="max-width: 600px;height: 120px;border-radius: 15px;background-color: rgb(113, 157, 227); color: #fff;">

        <el-row :gutter="20" style="margin-bottom: 10px;" align="bottom">
          <el-col :span="6" style="font-size: 18px; font-weight: 600;">
            订单量
          </el-col>
        </el-row>
        <el-row :gutter="24" align="bottom">
          <el-col :span="24" style="padding-bottom: 10px;">
            <el-icon size="28" style="margin-top: 18px;">
              <Tickets />
            </el-icon>
            <span style="float: right;font-size: 40px;">{{ OrderVolume }}</span>
          </el-col>
        </el-row>
      </el-card>
    </el-col>
    <el-col :span="12">
      <el-card
        style="max-width: 600px;height: 120px;border-radius: 15px;background-color: rgb(81, 68, 180);color: #fff;">
        <el-row :gutter="20" style="margin-bottom: 10px;" align="bottom">
          <el-col :span="6" style="font-size: 18px; font-weight: 600;">
            住房人数
          </el-col>
        </el-row>
        <el-row :gutter="24" align="bottom">
          <el-col :span="24" style="padding-bottom: 10px;">
            <el-icon size="28" style="margin-top: 18px;">
              <User />
            </el-icon>
            <span style="float: right;font-size: 40px;">{{ GuestVolume }}</span>
          </el-col>
        </el-row>
      </el-card>
    </el-col>
  </el-row>


  <el-row :gutter="24">
    <el-col :span="12">
      <el-card>
        <div id="RtypeByGender" style="width: 507px; height: 380px;"></div>
      </el-card>
    </el-col>
    <el-col :span="12">
      <el-card>
        <div id="RtypeByGender1" style="width: 507px; height: 380px;"></div>
      </el-card>
    </el-col>
  </el-row>




</template>
<script setup>
import indexApi from '@/api/indexApi'
import { ref, reactive,watch } from 'vue';
import { ElMessage } from 'element-plus'
import { ElLoading } from 'element-plus'
import * as echarts from "echarts";
import { onMounted } from "vue";

//订单量
const OrderVolume = ref('')
//住客人数
const GuestVolume = ref('')

const manList = ref({})

const womanList = ref({})


const ct = ref([])

const ct1 = ref([])
const type1= ref({})


//获取订单量
function getOrderVolume() {
  indexApi.getOrderVolume()
    .then(resp => {
      OrderVolume.value = resp.data
    })
}
//获取住客人数
function getGuestVolume() {
  indexApi.getGuestVolume()
    .then(resp => {
      GuestVolume.value = resp.data
    })
}
getGuestVolume()
getOrderVolume()
GetManList()
GetManList1()

//获取出租房间
function GetManList1() {
  indexApi.selectRtypeBy1()
    .then(resp => {
      type1.value = resp.data.map(item => item.type);
        ct1.value = resp.data.map(item => item.ct);
        // 重新初始化图表以更新数据
        console.log(type1.value);
        console.log(ct1.value);
        RtypeByGender1();
    })
}


function RtypeByGender1() {


// 基于准备好的dom，初始化echarts实例
let myChart = echarts.init(document.getElementById('RtypeByGender1'));

// 指定图表的配置项和数据
let option = {
  title: {
    text: '住客人数',
    left: 'left'
  },
  xAxis: {
    type: 'category',
    data: type1.value
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      data: ct1.value,
      type: 'bar'
    }
  ]
};
// 使用刚指定的配置项和数据显示图表。
myChart.setOption(option);
}



//获取剩余房间
function GetManList() {
  indexApi.selectRtypeByGender()
    .then(resp => {
      manList.value = resp.data.map(item => item.type);
        ct.value = resp.data.map(item => item.ct);
        // 重新初始化图表以更新数据
        RtypeByGender();
    })
}

function RtypeByGender() {


  // 基于准备好的dom，初始化echarts实例
  let myChart = echarts.init(document.getElementById('RtypeByGender'));

  // 指定图表的配置项和数据
  let option = {
    title: {
    text: '房间剩余',
    left: 'left'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    top: '5%',
    left: 'center'
  },
  series: [
    {
      name: 'Access From',
      type: 'pie',
      radius: ['40%', '70%'],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 10,
        borderColor: '#fff',
        borderWidth: 2
      },
      label: {
        show: false,
        position: 'center'
      },
      emphasis: {
        label: {
          show: true,
          fontSize: 40,
          fontWeight: 'bold'
        }
      },
      labelLine: {
        show: false
      },
      
      data: manList.value.map((name, index) => ({ value: ct.value[index], name }))
    }
  ]
};
  // 使用刚指定的配置项和数据显示图表。
  myChart.setOption(option);
}


// onMounted(() => {
//   RtypeByGender();
// })



</script>

<style></style>