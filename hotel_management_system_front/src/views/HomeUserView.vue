<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="200">
        <!--  -->

        <el-menu default-active="2" class="el-menu-vertical-demo" :collapse="isCollapse" @open="handleOpen"
          @close="handleClose" @select="toPage">
          <el-menu-item index="/indexUser">
            <el-icon>
              <HomeFilled />
            </el-icon>
            <template #title>首页</template>
          </el-menu-item>
          <el-menu-item index="/booking">
            <el-icon>
              <HomeFilled />
            </el-icon>
            <template #title>预订房间</template>
          </el-menu-item>
          <el-sub-menu index="1">
            <template #title>
              <el-icon>
              <List />
            </el-icon>
              <span>订单管理</span>
            </template>
            <el-menu-item-group>
              <template #title><span>Group One</span></template>
              <el-menu-item index="/ordersUser">我的订单</el-menu-item>
              <el-menu-item index="/user">我的</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
        </el-menu>
        <!--  -->
      </el-aside>
      <el-container>
        <el-header height="30" style=" box-shadow:  0 2px 12px 0 rgba(0, 0, 0, 0.1);">
          <div class="common-layout">
            <el-container>
              <el-aside width="60px"><el-radio-group v-model="isCollapse" style="margin-top: 20px">
                  <el-switch v-model="isCollapse" inline-prompt active-text="折叠" inactive-text="隐藏" />
                </el-radio-group>
              </el-aside>
              <el-main>
                <el-page-header :icon="null">
                  <template #content>
                    <div class="flex items-center">
                      <el-avatar :size="32" class="mr-3" src="http://localhost:8080/upload/patrick.jpg" />
                      <span class="text-sm mr-2" style="color: var(--el-text-color-regular)">
                        {{ username }}
                      </span>
                    </div>
                  </template>
                  <template #extra>
                    <div class="flex items-center">
                      <el-button>个人信息</el-button>
                      <el-button type="primary" class="ml-2"  color="#626aef" @click="centerDialogVisible = true">退出</el-button>
                    </div>
                  </template>
                </el-page-header>

              </el-main>
            </el-container>

          </div>

        </el-header>
        <el-main>
          <RouterView />
        </el-main>
        <el-footer height="30" style=" box-shadow:  0 2px 12px 0 rgba(0, 0, 0, 0.1);">
          <div style="height: 40px; text-align: center; font-size: 15px; line-height: 40px; font-weight: 500;">
            Copyright MIT © 2024 Patrick
          </div>
        </el-footer>
      </el-container>
    </el-container>
  </div>


  <!-- 退出登录提示框 -->
  

  <el-dialog v-model="centerDialogVisible" title="退出登录" width="300" align-center style="border-radius: 20px;">
    <span>你确定要退出吗</span>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="centerDialogVisible = false">取消</el-button>
        <el-button type="primary" color="#626aef" @click="logout">
          确定
        </el-button>
      </div>
    </template>
  </el-dialog>
  <!-- 退出登录提示框 -->
</template>
<script setup>
import router from '@/router';
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus';
import { RouterView, RouterLink } from 'vue-router'

const cdcd = ref()

function toPage(indexPath) {
  router.push(indexPath);
}
function handleClose() {
}
function handleOpen() {
}


function logout() {
  sessionStorage.removeItem('token');
  router.push('/loginUser')
}




import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from '@element-plus/icons-vue'
import loginApi from '@/api/loginApi';

const isCollapse = ref(true)

const centerDialogVisible = ref(false)

//个人信息
const username = ref("")
username.value=sessionStorage.getItem('username');



//获取个人信息
function getUsername() {
  loginApi.getUsername()
    .then(resp => {
      username.value = resp.data
    })
}



</script>

<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}

.text-sm {
  font-size: 0.875rem;
  /* 14px */
  line-height: 1.25rem;
  /* 20px */
}

.items-center {
  align-items: center;
}

/* layer: default */
.flex {
  display: flex;
}

/* layer: default */
.mr-2 {
  margin-right: 0.5rem;
  /* 8px */
}

.mr-3 {
  margin-right: 0.75rem;
  /* 12px */
}

.ml-2 {
  margin-left: 0.5rem;
  /* 8px */
}
</style>