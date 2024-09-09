<template>
    <el-row>
        <el-col :span="16">
            <el-card>
                <el-row>
                    <el-col :span="24">
                        <div class="grid-content ep-bg-purple-light" style="text-align: center;">客房预约</div>
                    </el-col>
                </el-row>
                <el-form :model="orders" label-width="auto" style="max-width: 600px">

                    <el-form-item label="姓名" label-width="20%">
                        <el-input v-model="orders.member.mname" autocomplete="off" style="width: 300px;" />
                    </el-form-item>

                    <el-form-item label="身份证号" label-width="20%">
                        <el-input v-model="orders.member.mno" autocomplete="off" style="width: 300px;" />
                    </el-form-item>
                    <el-form-item label="联系方式" label-width="20%">
                        <el-input v-model="orders.member.mphone" autocomplete="off" style="width: 300px;" />
                    </el-form-item>
                    <el-form-item label="住房时间" label-width="20%">
                        <el-date-picker v-model="memberAdd.mstart" type="date" placeholder="入店时间"
                            style="width: 140px;" />
                        <el-icon style="margin-left: 3px; margin-right: 3px;">
                            <Switch />
                        </el-icon>
                        <el-date-picker v-model="memberAdd.mend" type="date" placeholder="离店时间"
                            style="width: 140px;" />
                    </el-form-item>
                    <el-form-item label="订单房间" label-width="20%">
                        <el-select v-model="orders.room.rid" placeholder="" style="width: 300px">
                            <el-option v-for="item in spareRoomList" :key="item.rid"
                                :label="item.rno + '-' + item.rtype" :value="item.rid" />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="订单人员" label-width="20%">
                        <div class="flex gap-2">
                            <el-tag v-for="guest in memberList" :key="guest" closable @click="deleteMem(guest.mid)">
                                {{ guest.mname }}
                            </el-tag>
                            <el-button class="button-new-tag" size="small" @click="memberAddShowWin = true">
                                + New Guest
                            </el-button>
                        </div>
                    </el-form-item>
                </el-form>
                <template #footer>
                    <div class="dialog-footer">
                        <el-button @click="">取消</el-button>
                        <el-button type="primary" @click="insert">确认</el-button>
                    </div>
                </template>
            </el-card>
        </el-col>

    </el-row>



    <!-- 新增窗口开始 -->
    <el-dialog v-model="memberAddShowWin" title="添加会员" width="500">
        <el-form>

            <el-form-item label="姓名" label-width="20%">
                <el-input v-model="memberAdd.mname" autocomplete="off" style="width: 300px;" />
            </el-form-item>
            <el-form-item label="性别" label-width="20%">
                <el-radio-group v-model="memberAdd.mgender">
                    <el-radio-button label="男" value="男" />
                    <el-radio-button label="女" value="女" />
                </el-radio-group>
            </el-form-item>
            <el-form-item label="身份证号" label-width="20%">
                <el-input v-model="memberAdd.mno" autocomplete="off" style="width: 300px;" />
            </el-form-item>
            <el-form-item label="联系方式" label-width="20%">
                <el-input v-model="memberAdd.mphone" autocomplete="off" style="width: 300px;" />
            </el-form-item>

        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="memberAddShowWin = false">取消</el-button>
                <el-button type="primary" @click="insertMember">确认</el-button>
            </div>
        </template>
    </el-dialog>
    <!-- 新增窗口结束 -->
</template>

<script setup>
import ordersApi from '@/api/ordersApi'
import guestApi from '@/api/guestApi'
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus'
import { ElLoading } from 'element-plus'
import memberApi from '@/api/memberApi';


//显示新增会员窗口
const memberAddShowWin = ref(false)
//显示分配住客窗口
const AssShowWin = ref(false)

//新增会员实体
const memberAdd = ref({
    mstart:'',
    mend:''
})
//订单实体
const orders = ref({
    ono: '',
    member: {
        mname: '',
        mno: '',
        mphone: '',
    },
    room: {
        rid: '',
        rno: '',
        rtype: '',
        guests: ref([])
    }
})
//所有会员集合
const memberList = ref([])


//删除住客
function deleteMem(mid){
    memberApi.delete(mid)
        .then(resp => {
            selectAllMem()
        })
}

//更新选中住客信息
function selectAllMem() {
    //显示所有会员信息
    memberApi.selectAll()
        .then(resp => {
            memberList.value = resp.data
        })
}
selectAllMem()


//新增会员
function insertMember() {
    const loading = ElLoading.service({
        lock: true,
        text: 'Loading',
        background: 'rgba(0, 0, 0, 0.7)',
    })
    memberApi.insert(memberAdd.value)
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
                //刷新
                selectAllMem()

                //添加到订单人员中
                
   

                //关闭窗口
                memberAddShowWin.value = false

            } else {
                ElMessage({
                    message: resp.msg,
                    type: 'error',
                    duration: 1200
                });
            }

        })
}

//提交订单
function insert() {

    orders.room.guests.value=memberList.value
    console.log(memberList.value);
    

    console.log("orders实体");
    console.log(orders.value);
    
    ordersApi.insert(orders.value)
        .then(resp => {

            console.log("提交订单回调函数");
            
            console.log(resp);
            
        })
    

}

//所有空闲房间
const spareRoomList = ref({})
//获取所有空闲房间
function selectSpareRoom() {
    guestApi.selectByStateSpareRoom(0)
        .then(resp => {

            spareRoomList.value = resp.data
        })
}
selectSpareRoom()
</script>

<style></style>