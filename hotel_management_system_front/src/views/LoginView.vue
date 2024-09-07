<template>
    <div class="common-layout">
        <el-container>
            <el-header height="150px"></el-header>
            <el-main>
                <el-row justify="center">
                    <el-col :span="8" align="middle">
                        <el-card style="max-width: 480px;border-radius: 20px; ">
                            <template #header>
                                <div class="card-header">
                                    <span>用户登录</span>
                                </div>
                            </template>
                            <el-form ref="ruleFormRef" style="max-width: 240px;height: 200px; padding-top: 20px;"
                                status-icon label-width="auto" class="demo-ruleForm">
                                <el-form-item prop="pass">
                                    <el-input v-model="admin.username" type="password" autocomplete="off"
                                        placeholder="请输入用户名" />
                                </el-form-item>
                                <el-form-item prop="checkPass">
                                    <el-input v-model="admin.password" type="password" autocomplete="off"
                                        placeholder="请输入密码" />
                                </el-form-item>
                                <el-button style="width: 200px; margin-top: 10px;" round @click="login">登录</el-button>
                            </el-form>
                        </el-card>

                    </el-col>

                </el-row>
            </el-main>
        </el-container>
    </div>


</template>

<script setup>
import loginApi from '@/api/loginApi';
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus'
import { ElLoading } from 'element-plus'
import router from '@/router';
import { ElNotification } from 'element-plus'

//登录实体
const admin = ref({
    username: '',
    password: ''
})

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
                    message: '欢迎管理员回来',
                    type: 'success',
                })

                //将jwt保存在token中
                sessionStorage.setItem('token', resp.data)
                //重定向到首页
                router.push('/index')
            } else {
                ElMessage.error({
                    message: resp.msg,
                    type: 'success',
                    duration: 1200
                })
            }
        })
}


</script>
<style scoped></style>
