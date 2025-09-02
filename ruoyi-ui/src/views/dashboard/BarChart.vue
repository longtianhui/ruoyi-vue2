<template>
  <div :class="className" :style="{ height: height, width: width }" />
</template>

<script>
// 导入 ECharts 核心库
import * as echarts from 'echarts'
// 导入并应用 ECharts 主题，这里使用的是 'macarons' 主题
require('echarts/theme/macarons')
// 导入用于图表自适应的 mixin
import resize from './mixins/resize'

// 定义动画持续时间，单位毫秒
const animationDuration = 6000

export default {
  // 混入 resize mixin，使图表在窗口大小变化时自动重绘
  mixins: [resize],
  // 组件属性，用于从父组件接收参数
  props: {
    // 容器的类名
    className: {
      type: String,
      default: 'chart'
    },
    // 容器的宽度
    width: {
      type: String,
      default: '100%'
    },
    // 容器的高度
    height: {
      type: String,
      default: '300px'
    }
  },
  data() {
    return {
      // 用于存储 ECharts 实例
      chart: null
    }
  },
  // 组件挂载后执行
  mounted() {
    // 确保 DOM 更新后再初始化图表
    this.$nextTick(() => {
      this.initChart()
    })
  },
  // 组件销毁前执行
  beforeDestroy() {
    // 如果 ECharts 实例存在，则销毁它以释放内存
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    // 初始化图表的方法
    initChart() {
      // 在 DOM 元素上初始化 ECharts 实例，并应用主题
      this.chart = echarts.init(this.$el, 'macarons')

      // 设置图表配置项
      this.chart.setOption({
        // 提示框配置
        tooltip: {
          // 触发类型：'axis' 表示当鼠标悬停在坐标轴上时触发
          trigger: 'axis',
          // 坐标轴指示器
          axisPointer: {
            // 指示器类型：'shadow' 表示阴影指示器
            type: 'shadow'
          }
        },
        // 网格配置，控制图表的绘图区域
        grid: {
          top: 10,
          left: '2%',
          right: '2%',
          bottom: '3%',
          // 包含坐标轴标签，防止标签溢出
          containLabel: true
        },
        // X轴配置
        xAxis: [{
          // X轴类型为类目轴
          type: 'category',
          // X轴数据
          data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
          // 刻度线配置
          axisTick: {
            // 刻度线与标签对齐
            alignWithLabel: true
          }
        }],
        // Y轴配置
        yAxis: [{
          // Y轴类型为数值轴
          type: 'value',
          // 刻度线配置
          axisTick: {
            // 不显示刻度线
            show: false
          }
        }],
        // 系列数据，定义图表类型和数据
        series: [{
          // 系列名称
          name: 'pageA',
          // 图表类型为柱状图
          type: 'bar',
          // 堆叠分组名称，相同名称的系列会堆叠在一起
          stack: 'vistors',
          // 柱子的宽度
          barWidth: '60%',
          // 系列数据
          data: [79, 52, 200, 334, 390, 330, 220],
          // 动画持续时间
          animationDuration
        }, {
          name: 'pageB',
          type: 'bar',
          stack: 'vistors',
          barWidth: '60%',
          data: [80, 52, 200, 334, 390, 330, 220],
          animationDuration
        }, {
          name: 'pageC',
          type: 'bar',
          stack: 'vistors',
          barWidth: '60%',
          data: [30, 52, 200, 334, 390, 330, 220],
          animationDuration
        }]
      })
    }
  }
}
</script>