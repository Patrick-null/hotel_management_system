<template>
  <el-row>
    <el-col :span="24">
      <el-card style="height: 550px;">
        <div class="common-layout">
          <el-container>
            <el-header style="height: 30px;">
              <el-form :inline="true" class="demo-form-inline">
                <el-button type="success" style="margin-bottom: 10px;" @click="">添加</el-button>
                <el-form-item label="搜索框" style="float: right;">
                  <el-input v-model="flag" placeholder="请输入需要搜索的姓名" clearable @input="selectAll(1)" />
                </el-form-item>
              </el-form>

            </el-header>
            <el-main style="height: 430px;">
              <el-table :data="facilityList.list" border style="width: 100%">
                <el-table-column prop="fid" label="ID" width="50px" />
                <el-table-column prop="fname" label="设施名称" width="80px" />
                <el-table-column prop="favatar" label="设施照片" width="90px">
                  <template #default="scope">
                    <el-avatar shape="square" :size="50" :src="'http://localhost:8080/upload/' + scope.row.favatar" />
                  </template>
                </el-table-column>
                <el-table-column prop="fnum" label="数量" width="100px" />
                <el-table-column prop="rvalue" label="价格" show-overflow-tooltip width="75px" />
                <el-table-column label="状态" width="75px">
                  <template #default="scope">
                    <el-tag v-if="scope.row.rstate == 0" type="success" effect="dark">空闲</el-tag>
                    <el-tag v-else-if="scope.row.rstate == 1" type="primary" effect="dark">已预订</el-tag>
                    <el-tag v-else-if="scope.row.rstate == 2" type="info" effect="dark">已入住</el-tag>
                    <el-tag v-else="scope.row.rstate == 3" type="danger" effect="dark">未开放</el-tag>
                  </template>
                </el-table-column>
                <el-table-column fixed="right" label="操作">
                  <template #default="scope">
                    <el-button size="small" type="success" @click="">修改</el-button>
                    <el-popconfirm confirm-button-text="删除" cancel-button-text="取消" title="你确认要删除吗?" width="200px"
                      @confirm="">
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
                <el-pagination background layout="prev, pager, next" :total="facilityList.total"
                  :page-size="facilityList.pageSize" @change="selectAll" />
              </el-row>

            </el-footer>
          </el-container>
        </div>



      </el-card>
    </el-col>

  </el-row>
</template>

<script setup>
import facilityApi from '@/api/facilityApi'
import { ref, reactive } from 'vue';
import { ElMessage } from 'element-plus'
import { ElLoading } from 'element-plus'

//所有设施的集合
const facilityList = ref({
  total: 0,
  pageSize: 0
})
//搜索的标识
const flag = ref('')

function selectAll(pageNum){
  facilityApi.selectAll(pageNum,flag.value)
    .then(resp => {
      facilityList.value=resp.data
      console.log(resp.data);
      
    })
}

selectAll(1)
</script>

<style scoped>
</style>