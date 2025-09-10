<template>
  <div class="test-container">
    <h2>测试数据列表</h2>  分页

    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="list" border style="width: 100%; margin-bottom: 20px;">
      <el-table-column prop="id" label="序号" width="80" align="center"></el-table-column>
      <el-table-column prop="content" label="测试内容" align="center"></el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />
  </div>
</template>

<script>
import { getPage } from '@/api/test/index'

// 1. 导入我们创建的 mixin
import { paginationMixin } from '../../mixins/paginationMixin';

export default {
  name: 'Index',
  // 2. 混入 mixin
  mixins: [paginationMixin],
  data() {
    return {
      // 这里不再需要定义 loading, total, list, queryParams
      // 因为它们已经从 mixin 中混入进来了
      // 如果有额外的查询参数，可以在这里定义并合并
      // 例如: queryParams: { ...this.queryParams, customParam: 'value' }
    }
  },
  methods: {
    /**
     * 获取列表数据 (实现 mixin 的约定)
     * 这个方法会覆盖 mixin 中可能存在的同名方法
     */
    getList() {
      this.loading = true; // this.loading 来自 mixin
      // this.queryParams 也来自 mixin
      getPage(this.queryParams).then((response) => {
        if (response.code === 200) {
          this.list = response.rows.map((item, index) => ({
            id: (this.queryParams.pageNum - 1) * this.queryParams.pageSize + index + 1,
            content: item
          }));
          this.total = response.total; // this.list 和 this.total 也来自 mixin
        } else {
          this.$message.error(response.msg || '获取数据失败');
        }
        this.loading = false;
      }).catch(error => {
        console.error('请求出错:', error);
        this.loading = false;
        this.$message.error('请求失败，请稍后重试');
      });
    }
  }
  // created 钩子也不需要了，因为 mixin 已经帮我们调用了 getList
}
</script>

<style lang="scss" scoped>
.test-container {
  padding: 20px;
  background-color: #fff;
  min-height: calc(100vh - 120px);

  h2 {
    margin-bottom: 20px;
    color: #1f2329;
    font-size: 18px;
    font-weight: 500;
  }
}
</style>
