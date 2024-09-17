<template>
  <el-row>
    <el-col :span="24">
      <el-card style="height: 550px;">
        <div class="common-layout">
          <el-container>
            <el-header style="height: 30px;">
              <el-form :inline="true" class="demo-form-inline">
                <el-button type="success" style="margin-bottom: 10px;" @click="enrollShowWin = true"> 注册</el-button>
                <el-button type="primary"  style="margin-bottom: 10px;" @click="">下载</el-button>
                <el-form-item label="搜索框" style="float: right;">
                  <el-input v-model="flag" placeholder="请输入需要搜索的姓名" clearable @input="selectAll(1)" />
                </el-form-item>
              </el-form>
            </el-header>
            <el-main style="height: 430px;">
              <el-table :data="infoList.list" border style="width: 100%">
                <el-table-column prop="id" label="ID" width="40px" />
                <el-table-column prop="name" label="姓名" />
                <el-table-column prop="no" label="身份证号" />
                <el-table-column prop="gender" label="性别" />
                <el-table-column prop="phone" label="手机号" />
                <el-table-column prop="addr" label="地址" />
                <el-table-column prop="admin.username" label="用户名" />
                <el-table-column fixed="right" label="操作">
                  <template #default="scope">
                    <el-button size="small" type="success" @click="">修改</el-button>
                    <el-popconfirm confirm-button-text="使用" cancel-button-text="取消" title="你确认要删除吗?"
                      @confirm="" width="200px">
                      <template #reference>
                        <el-button size="small" type="danger">删除</el-button>
                      </template>
                    </el-popconfirm>
                  </template>
                </el-table-column>
              </el-table>
            </el-main>
            <el-footer>
              <el-row class="row-bg" justify="center" style="margin-top: 20px;">
                <el-pagination background layout="prev, pager, next" :total="infoList.total"
                  :page-size="infoList.pageSize" @change="selectAll" />
              </el-row>
            </el-footer>
          </el-container>
        </div>
      </el-card>
    </el-col>
  </el-row>

  <!-- 添加用户窗口开始 -->
  <el-dialog v-model="enrollShowWin" title="添加用户" width="500" center>

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
  <!-- 添加用户窗口结束 -->
</template>

<script setup>
import userApi from '@/api/userApi'
import loginApi from '@/api/loginApi'
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus'
import { ElLoading } from 'element-plus'
import { useTokenStore } from '@/stores/token';


const infoList = ref({
  total:0,
  pageSize:0
})
const flag = ref("")

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

        userApi.enroll(enroll.value,info.value)
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
                    info.value = {}
                    enrollPwd.value = ""
                    enroll.value = {}
                    selectAll(1)
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

function selectAll(pageNum){
  userApi.selectAll(pageNum,flag.value)
    .then(resp => {
      infoList.value=resp.data
    })
}
selectAll(1)
</script>

<style></style>