<template>
  <el-card>
    欢迎来到Patrick酒店管理系统
  </el-card>
</template>
<script setup>
import indexApi from '@/api/indexApi'
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus'
import { ElLoading } from 'element-plus'
import * as echarts from "echarts";
import { onMounted } from "vue";

//服务器路径
const SERVER_ADDR = ref(import.meta.env.VITE_SERVER_ADDR)







  

</script>

<style></style>