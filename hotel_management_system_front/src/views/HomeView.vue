<template>
  <div class="common-layout">
    <el-container>
      <el-header>酒店管理系统</el-header>
      <el-container>
        <el-aside width="200px">
          <el-menu default-active="2" class="el-menu-vertical-demo" @select="toPage">
            <el-menu-item index="/index">
              <el-icon>
                <location />
              </el-icon>
              <span>首页</span>
            </el-menu-item>
            <el-sub-menu index="1">
              <template #title>
                <el-icon>
                  <location />
                </el-icon>
                <span>酒店管理</span>
              </template>
              <el-menu-item-group title="第一模块">
                <el-menu-item index="/employee">员工管理</el-menu-item>
                <el-menu-item index="/dept">部门管理</el-menu-item>
              </el-menu-item-group>
            </el-sub-menu>
            <el-sub-menu index="2">
              <template #title>
                <el-icon>
                  <location />
                </el-icon>
                <span>客房管理</span>
              </template>
              <el-menu-item-group title="第二模块">
                <el-menu-item index="/guest">宾客管理</el-menu-item>
                <el-menu-item index="/room">房间管理</el-menu-item>
              </el-menu-item-group>
            </el-sub-menu>
            <el-menu-item index="/orders">
              <el-icon>
                <location />
              </el-icon>
              <span>订单管理</span>
            </el-menu-item>
            <el-menu-item index="/member">
              <el-icon>
                <location />
              </el-icon>
              <span>用户界面</span>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-container>
          <el-main>
            <RouterView />
          </el-main>
          <el-footer>Footer</el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>
<script setup>
import router from '@/router';
import { ElMessage } from 'element-plus';
import { RouterView, RouterLink } from 'vue-router'

function toPage(indexPath) {
  router.push(indexPath);
}

</script>