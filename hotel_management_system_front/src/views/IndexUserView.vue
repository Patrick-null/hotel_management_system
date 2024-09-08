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
            <span style="float: right;font-size: 40px;">123</span>
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
            <span style="float: right;font-size: 40px;">456</span>
          </el-col>
        </el-row>
      </el-card>
    </el-col>
  </el-row>


  <el-row :gutter="24">
    <el-col :span="12">
      <el-card>
        <div id="" style="width: 507px; height: 380px;"></div>
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







  

</script>

<style></style>