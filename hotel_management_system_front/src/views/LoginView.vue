<template>
    <div style="height: 100%; width: 100%;  margin: 0;
    display: flex;
    justify-content: center;
    align-items: center;">
        <el-row >
            <el-col :span="24">
                <el-card style="max-width: 600px;border-radius: 20px; width: 300px; text-align: center; 
                background: rgba(255, 255, 255, 0.5); /* 半透明白色背景 */">
                    <template #header>
                        <div class="card-header">
                            <span>管理员登录</span>
                        </div>
                    </template>
                    <el-form ref="loginXY" style="max-width: 240px; " status-icon label-width="auto"
                        class="demo-ruleForm"  :model="admin" :rules="rules">
                        <el-form-item prop="username" >
                            <el-input v-model="admin.username" type="password" autocomplete="off" 
                                placeholder="请输入用户名" />
                        </el-form-item>
                        <el-form-item prop="password">
                            <el-input v-model="admin.password" type="password" autocomplete="off" prop="password"
                             placeholder="请输入密码" />
                        </el-form-item>
                        <el-form-item  prop="captcha">
                            <el-input v-model="admin.captcha"  autocomplete="off" prop="captcha"
                             placeholder="请输入验证码" />
                        </el-form-item>
                        <el-form-item>
                            <el-image style="width: 120px; height: 38px" :src="captchaPhoto"  @click="getCaptcha"/>
                        </el-form-item>
                        <el-button style="width: 200px; margin-top: 10px;" round @click="login"
                            color="#626aef">登录</el-button>
                    </el-form>
                   
                    <el-divider>
                        <span style="font-weight: 200; font-size: 12px;">其他登录方式</span>
                    </el-divider>
                    <el-button color="#626aef" @click="router.push('/loginUser')">用户登录</el-button>
                </el-card>
            </el-col>

        </el-row>
    </div>
</template>

<script setup>
import loginApi from '@/api/loginApi';
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus'
import { ElLoading } from 'element-plus'
import router from '@/router';
import { ElNotification } from 'element-plus'
import service from '@/api';
import { useTokenStore } from '@/stores/token.js';

const tokenStore = useTokenStore();
const loginXY = ref(null) 

const rules =reactive({
    username:[{required:true,message:'请输入用户名',trigger:'blur'}],
    password:[{required:true,message:'请输入密码',trigger:'blur'}],
    captcha:[{required:true,message:'请输入验证码',trigger:'blur'}],
})


//登录实体
const admin = ref({
    username: '',
    password: '',
    captcha:'',
    captchaId:''
})
//验证码图片
const captchaPhoto = ref("")
//用户名
const username = ref('')

function login() {
    const loading = ElLoading.service({
        lock: true,
        text: 'Loading',
        background: 'rgba(0, 0, 0, 0.7)',
    })
    loginApi.login(admin.value)
        .then(resp => {
            loading.close()
            if (resp.code == 10000) {

                ElNotification({
                    title: '登录成功',
                    message: '欢迎回来，' + admin.value.username +'!',
                    type: 'success',
                })

                //将jwt保存在token中
                //sessionStorage.setItem('token', resp.data)
                tokenStore.update(resp.data)
                sessionStorage.setItem('username', admin.value.username)
                //重定向到首页
                router.push('/admin')
            } else {
                ElMessage.error({
                    message: resp.msg,
                    type: 'success',
                    duration: 1200
                })
            }
        })
}

//获取验证码
function getCaptcha(){
    loginApi.captcha()
        .then(resp => {
            captchaPhoto.value=resp.data.captchaImageBase64Data;
            admin.value.captchaId=resp.data.captchaId
        })
}

getCaptcha();
</script>
<style scoped></style>
