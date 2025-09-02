<template>
  <div class="dashboard-editor-container">
    <panel-group @handleSetLineChartData="handleSetLineChartData" />

    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="lineChartData" />
    </el-row>

    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <raddar-chart />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <pie-chart />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <bar-chart />
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
// 导入所有需要的子组件
import PanelGroup from './dashboard/PanelGroup'
import LineChart from './dashboard/LineChart'
import RaddarChart from './dashboard/RaddarChart'
import PieChart from './dashboard/PieChart'
import BarChart from './dashboard/BarChart'

// 静态的折线图数据，包含四种类型
const lineChartData = {
  newVisitis: { // 新访客数据
    expectedData: [100, 120, 161, 134, 105, 160, 165],
    actualData: [120, 82, 91, 154, 162, 140, 145]
  },
  messages: { // 消息数据
    expectedData: [200, 192, 120, 144, 160, 130, 140],
    actualData: [180, 160, 151, 106, 145, 150, 130]
  },
  purchases: { // 购买数据
    expectedData: [80, 100, 121, 104, 105, 90, 100],
    actualData: [120, 90, 100, 138, 142, 130, 130]
  },
  shoppings: { // 购物数据
    expectedData: [130, 140, 141, 142, 145, 150, 160],
    actualData: [120, 82, 91, 154, 162, 140, 130]
  }
}

export default {
  name: 'Index',
  components: {
    PanelGroup,
    LineChart,
    RaddarChart,
    PieChart,
    BarChart
  },
  data() {
    return {
      // 初始化折线图数据为新访客的数据
      lineChartData: lineChartData.newVisitis
    }
  },
  methods: {
    // 处理切换折线图数据的方法
    handleSetLineChartData(type) {
      // 根据传入的类型（如 'messages'）动态更新数据
      this.lineChartData = lineChartData[type]
    }
  }
}
</script>

<style lang="scss" scoped>
// 外层容器样式
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245); // 灰色背景
  position: relative;

  // 图表容器样式
  .chart-wrapper {
    background: #fff; // 白色背景
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

// 媒体查询，适配小屏幕设备
@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px; // 缩小内边距
  }
}
</style>