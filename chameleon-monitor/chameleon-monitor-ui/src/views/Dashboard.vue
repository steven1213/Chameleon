<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <metric-card
          title="活跃连接数"
          :value="metrics.activeConnections"
        />
      </el-col>
      <!-- 其他指标卡片 -->
    </el-row>
    
    <div class="charts">
      <qps-chart :data="qpsData" />
      <connection-chart :data="connectionData" />
    </div>
  </div>
</template>

<script setup lang="ts">
import {onMounted, ref} from 'vue'

const metrics = ref({})
const qpsData = ref([])

onMounted(async () => {
  // 获取初始数据
  // 设置定时刷新
})
</script>
