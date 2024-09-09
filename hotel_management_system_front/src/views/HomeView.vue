<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="200">
        <!--  -->

        <el-menu default-active="2" class="el-menu-vertical-demo" :collapse="isCollapse" @open="handleOpen"
          @close="handleClose" @select="toPage">
          <el-menu-item index="/index">
            <el-icon>
              <HomeFilled />
            </el-icon>
            <template #title>首页</template>
          </el-menu-item>
          <el-sub-menu index="1">
            <template #title>
              <el-icon>
                <Shop />
              </el-icon>
              <span>员工管理</span>
            </template>
            <el-menu-item-group>
              <template #title><span>Group One</span></template>
              <el-menu-item index="/employee">员工管理</el-menu-item>
              <el-menu-item index="/dept">部门管理</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index="2">
            <template #title>
              <el-icon>
                <UserFilled />
              </el-icon>
              <span>客房管理</span>
            </template>
            <el-menu-item-group>
              <template #title><span>Group One</span></template>
              <el-menu-item index="/guest">住客管理</el-menu-item>
              <el-menu-item index="/room">房间管理</el-menu-item>
              <el-menu-item index="/facility">设施管理</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>

          <el-menu-item index="/orders">
            <el-icon>
              <List />
            </el-icon>
            <template #title>订单管理</template>
          </el-menu-item>
          <el-menu-item index="/member">
            <el-icon>
              <setting />
            </el-icon>
            <template #title>用户管理</template>
          </el-menu-item>
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
                        {{ admin.username }}
                      </span>
                    </div>
                  </template>
                  <template #extra>
                    <div class="flex items-center">
                      <el-button @click="pwdUpdWin = true">修改密码</el-button>
                      <el-button @click="infoShowWin = true">个人信息</el-button>
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

  <!-- 个人信息页面开始 -->
  <el-drawer v-model="infoShowWin" title="个人信息">
    <el-descriptions direction="vertical" column="1">
      <el-descriptions-item label="姓名">{{ admin.info.name }}</el-descriptions-item>
      <el-descriptions-item label="性别">{{ admin.info.gender }}</el-descriptions-item>
      <el-descriptions-item label="身份证号">{{ admin.info.no }}</el-descriptions-item>
      <el-descriptions-item label="手机号">{{ admin.info.phone }}</el-descriptions-item>
      <el-descriptions-item label="地址">{{ admin.info.addr }}</el-descriptions-item>
    </el-descriptions>

    <template #footer>
      <div style="flex: auto">
        <el-button type="primary" @click="updInfoShowInfo = true">修改信息</el-button>
      </div>
    </template>
  </el-drawer>
  <!-- 个人信息页面结束 -->
  <!-- 修改信息窗口开始 -->
  <el-dialog v-model="updInfoShowInfo" title="修改信息" width="500" align-center>
    <template #footer>
      <el-form-item label="姓名" label-width="20%">
        <el-input v-model="admin.info.name" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="性别" label-width="20%">
        <el-radio-group v-model="admin.info.gender">
          <el-radio-button label="男" value="男" />
          <el-radio-button label="女" value="女" />
        </el-radio-group>
      </el-form-item>
      <el-form-item label="身份证号" label-width="20%">
        <el-input v-model="admin.info.no" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="联系方式" label-width="20%">
        <el-input v-model="admin.info.phone" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="地址" label-width="20%">
        <el-input v-model="admin.info.addr" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <div class="dialog-footer">
        <el-button @click="updInfoShowInfo = false">取消</el-button>
        <el-button type="primary" @click="updateInfo">
          保存
        </el-button>
      </div>
    </template>
  </el-dialog>
  <!-- 修改信息窗口结束 -->

   <!-- 修改密码窗口开始 -->
   <el-dialog v-model="pwdUpdWin" title="修改密码" width="500" align-center>
    <template #footer>
      <el-form-item  label="用户名" label-width="20%">
        <el-input disabled v-model="userAndpwd.username" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="输入密码" label-width="20%">
        <el-input type="password" v-model="userAndpwd.password" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <el-form-item label="确认密码" label-width="20%">
        <el-input type="password" v-model="passwordTwo" autocomplete="off" style="width: 300px;" />
      </el-form-item>
      <div class="dialog-footer">
        <el-button @click="pwdUpdWin = false">取消</el-button>
        <el-button type="primary" @click="updPwd">
          提交
        </el-button>
      </div>
    </template>
  </el-dialog>
  <!-- 修改密码窗口结束 -->

</template>
<script setup>
import router from '@/router';
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus';
import { RouterView, RouterLink } from 'vue-router'
import infoApi from '@/api/infoApi';
import { ElLoading } from 'element-plus'

//个人信息实体
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


  
const userAndpwd = ref({
  username:'',
  password:''
})
const passwordTwo = ref('')

//修改密码
const pwdUpdWin = ref(false);

//修改信息标识
const updInfoShowInfo = ref(false)


//显示个人信息页面
const infoShowWin = ref(false)
//获取个人信息
function selectUserInfo() {
  infoApi.selectByUsername()
    .then(resp => {

      admin.value = resp.data
      userAndpwd.value.username=resp.data.username

    })
}

//修改密码
function updPwd(){
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(0, 0, 0, 0.7)',
  })
  if(userAndpwd.value.password == passwordTwo.value){
    loginApi.updPwd(userAndpwd.value)
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
        pwdUpdWin.value = false
        router.push('/login')
      } else {
        ElMessage({
          message: resp.msg,
          type: 'error',
          duration: 1200
        });
      }
    })
  }else{
    loading.close()
    ElMessage({
          message: "两次密码不一致",
          type: 'error',
          duration: 1200
        });
  }
  
}


selectUserInfo()

function updateInfo(){
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(0, 0, 0, 0.7)',
  })

  infoApi.update(admin.value.info)
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
        updInfoShowInfo.value=false
      } else {
        ElMessage({
          message: resp.msg,
          type: 'error',
          duration: 1200
        });
      }
    })
}

function toPage(indexPath) {
  router.push(indexPath);
}
function handleClose() {
}
function handleOpen() {
}


function logout() {
  sessionStorage.removeItem('token');
  router.push('/login')
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