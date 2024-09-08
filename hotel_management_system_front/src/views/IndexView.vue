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
        <div id="" style="width: 507px; height: 380px;"></div>
      </el-card>
    </el-col>
  </el-row>




</template>
<script setup>
import indexApi from '@/api/indexApi'
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus'
import { ElLoading } from 'element-plus'
import * as echarts from "echarts";
import { onMounted } from "vue";




function RtypeByGender() {

  let manArr = [];
  let manArrType = [];
  let womanArr = [];







  // 基于准备好的dom，初始化echarts实例
  var myChart = echarts.init(document.getElementById('RtypeByGender'));

  // 指定图表的配置项和数据
  var option = {
  title: {
    text: '男女住房比例',
    subtext: '人数'
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    data: ['男', '女']
  },
  toolbox: {
    show: true,
    feature: {
      dataView: { show: true, readOnly: false },
      magicType: { show: true, type: ['line', 'bar'] },
      restore: { show: true },
      saveAsImage: { show: true }
    }
  },
  calculable: true,
  xAxis: [
    {
      type: 'category',
      // prettier-ignore
      data: allRtype.value
    }
  ],
  yAxis: [
    {
      type: 'value'
    }
  ],
  series: [
    {
      name: '男',
      type: 'bar',
      data: [
        2, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3
      ]
    },
    {
      name: '女',
      type: 'bar',
      data: [
        2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3
      ]
    }
  ]
};

  // 使用刚指定的配置项和数据显示图表。
  myChart.setOption(option);
}

onMounted(() => {
  RtypeByGender();
})

//订单量
const OrderVolume = ref('')
//住客人数
const GuestVolume = ref('')
//男每个房间的集合
const manList = ref({})
//女每个房间的集合
const womanList = ref({})
//所有房间类型的集合
const allRtype = ref({})

//给这3个集合赋值
function selectRtypeByGender() {
  indexApi.selectRtypeByGender('男')
    .then(resp => {
      manList.value = resp.data
      console.log(manList.value);
    })

  indexApi.selectRtypeByGender('女')
    .then(resp => {
      womanList.value = resp.data
      console.log(womanList.value);
    })
    indexApi.getAllRtype()
      .then(resp => {
        allRtype.value = resp.data
      })
}
selectRtypeByGender()

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

</script>

<style></style>