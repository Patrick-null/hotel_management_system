<template>
    <div style="height: 100%; width: 100%;  margin: 0;
    display: flex;
    justify-content: center;
    align-items: center;">
        <el-row>
            <el-col :span="24">
                <el-card style="max-width: 600px;border-radius: 20px; width: 300px; text-align: center; 
                background: rgba(255, 255, 255, 0.5); /* 半透明白色背景 */">
                    <template #header>
                        <div class="card-header">
                            <span>用户登录</span>
                        </div>
                    </template>
                    <el-form ref="loginXY" :model="admin" style="max-width: 240px; " status-icon label-width="auto"
                        class="demo-ruleForm" :rules="rules">
                        <el-form-item prop="username">
                            <el-input v-model="admin.username" autocomplete="off" placeholder="请输入用户名" />
                        </el-form-item>
                        <el-form-item prop="password">
                            <el-input v-model="admin.password" type="password" autocomplete="off" placeholder="请输入密码" />
                        </el-form-item>
                        <el-form-item  prop="password">
                            <el-input v-model="admin.captcha"  autocomplete="off" prop="captcha"
                             placeholder="请输入验证码" />
                        </el-form-item>
                        <el-form-item>
                            <el-image style="width: 120px; height: 38px" :src="captchaPhoto"  @click="getCaptcha"/>
                        </el-form-item>
                        <div style="display: flex; justify-content: center; max-width: 600px;">
                            <el-button style="width: 100px; margin-top: 10px;" round @click="login"
                                color="#626aef">登录</el-button>
                            <el-button style="width: 100px; margin-top: 10px; border: 1px #909399 solid;" round
                                @click="enrollShowWin = true" color="#fff">注册</el-button>
                        </div>

                    </el-form>
                    <el-divider>
                        <span style="font-weight: 200; font-size: 12px;">其他登录方式</span>
                    </el-divider>
                    <el-button color="#626aef" @click="router.push('/login')">管理员登录</el-button>
                </el-card>
            </el-col>

        </el-row>
    </div>

    <!-- 注册窗口开始 -->
    <el-dialog v-model="enrollShowWin" title="注册" width="500" center>

        <template #footer>
            <el-form ref="adminXY" :model="enroll" :rules="adminrules">

                <el-form-item label="用户名" label-width="20%" prop="username">
                    <el-input v-model="enroll.username" autocomplete="off" style="width: 300px;" />
                </el-form-item>
                <el-form-item label="密码" label-width="20%" prop="password">
                    <el-input type="password" v-model="enroll.password" autocomplete="off" style="width: 300px;" />
                </el-form-item>
                <el-form-item label="重复密码" label-width="20%" prop="password">
                    <el-input type="password" v-model="enrollPwd" autocomplete="off" style="width: 300px;" />
                </el-form-item>
            </el-form>
            <el-form ref="enrollXY" :model="info" :rules="enrollrules">
                <el-form-item label="姓名" label-width="20%" prop="name">
                    <el-input v-model="info.name" autocomplete="off" style="width: 300px;" />
                </el-form-item>
                <el-form-item label="性别" label-width="20%">
                    <el-radio-group v-model="info.gender">
                        <el-radio-button label="男" value="男" />
                        <el-radio-button label="女" value="女" />
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="身份证号" label-width="20%" prop="no">
                    <el-input v-model="info.no" autocomplete="off" style="width: 300px;" />
                </el-form-item>
                <el-form-item label="联系方式" label-width="20%" prop="phone">
                    <el-input v-model="info.phone" autocomplete="off" style="width: 300px;" />
                </el-form-item>
                <el-form-item label="地址" label-width="20%" prop="addr">
                    <el-input v-model="info.addr" autocomplete="off" style="width: 300px;" />
                </el-form-item>

                <div class="dialog-footer">
                    <el-button @click="enrollShowWin = false">取消</el-button>
                    <el-button type="primary" @click="enrollShow">注册</el-button>
                </div>
            </el-form>
        </template>

    </el-dialog>
    <!-- 注册窗口结束 -->
</template>

<script setup>
import loginApi from '@/api/loginApi';
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus'
import { ElLoading } from 'element-plus'
import router from '@/router';
import { ElNotification } from 'element-plus'
import service from '@/api';
import infoApi from '@/api/infoApi';
import { useTokenStore } from '@/stores/token';

const loginXY = ref(null)

const rules = reactive({
    username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
    password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
})


const adminXY = ref(null)
const adminrules = reactive({
    username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
    password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
})



const enrollXY = ref(null)
const enrollrules = reactive({
    name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
    no: [{ required: true, message: '请输入身份证号', trigger: 'blur' }],
    phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
    addr: [{ required: true, message: '请输入地址', trigger: 'blur' }],
    gender: [{ required: true, message: '请选择性别', trigger: 'blur' }]
})


//登录实体
const admin = ref({
    username: '',
    password: '',
    captcha:'',
    captchaId:''
})
//验证码照片
const captchaPhoto = ref("")

//注册实体
const enroll = ref({
    username: '',
    password: ''
})
//注册密码
const enrollPwd = ref('')
//用户信息
const info = ref({
    name: '',
    gender: '男',
    no: '',
    phone: '',
    addr: '',
    aid: ''

})

//注册窗口标识
const enrollShowWin = ref(false)
//注册实体
function enrollShow() {
    const loading = ElLoading.service({
        lock: true,
        text: 'Loading',
        background: 'rgba(0, 0, 0, 0.7)',
    })
    if (enroll.value.username == "" || enroll.value.username == "") {
        loading.close()
        ElMessage({
            message: "用户名或密码不能为空",
            type: 'error',
            duration: 1200
        });  
    } else if (enroll.value.password != enrollPwd.value) {
        loading.close()
        ElMessage({
            message: "两次密码不一致，请确认后输入",
            type: 'error',
            duration: 1200
        });
    } else if(info.value.name==""||info.value.no==""||info.value.phone==""||info.value.addr==""){
        loading.close()
        ElMessage({
            message: "请填写完整信息",
            type: 'error',
            duration: 1200
        });
    }else {
        console.log(enroll.value);
        console.log(info.value);
        
        loginApi.enroll(enroll.value,info.value)
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
                    enrollShowWin.value = false
                    console.log(resp.data);

                    // info.value.aid = resp.data.aid
                    //吧信息添加到数据库
                    // infoApi.insert(info.value)
                    //     .then(resp => {
                    //         if (resp.code == 10000) {
                    //             //弹出消息
                    //             ElMessage({
                    //                 message: resp.msg,
                    //                 type: 'success',
                    //                 duration: 1200
                    //             })
                    //         } else {
                    //             ElMessage({
                    //                 message: resp.msg,
                    //                 type: 'error',
                    //                 duration: 1200
                    //             });
                    //         }
                    //     })

                    //清空数据
                    info.value = {}
                    enrollPwd.value = ""
                    enroll.value = {}
                    router.push('/loginUser')
                } else {
                    ElMessage({
                        message: resp.msg,
                        type: 'error',
                        duration: 1200
                    });
                }
            })
    }
}



function login() {
    const loading = ElLoading.service({
        lock: true,
        text: 'Loading',
        background: 'rgba(0, 0, 0, 0.7)',
    })
    loginApi.loginTwo(admin.value)
        .then(resp => {
            loading.close()
            if (resp.code == 10000) {

                ElNotification({
                    title: '登录成功',
                    message: '欢迎回来，' + admin.value.username + '!',
                    type: 'success',
                })

                const tokenStore = useTokenStore();
                tokenStore.update(resp.data)
                //将jwt保存在token中
                // sessionStorage.setItem('token', resp.data)
                sessionStorage.setItem('username', admin.value.username)
                //重定向到首页
                router.push('/user')
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
