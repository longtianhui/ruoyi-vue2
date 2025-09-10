
import Pagination from "@/components/Pagination";

export const paginationMixin = {
  components: { Pagination },
  data() {
    return {
      // ---------------- 通用状态 ----------------
      loading: false, // 加载状态
      total: 0, // 总记录数
      list: [], // 列表数据

      // ---------------- 通用查询参数 ----------------
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
    };
  },
  created() {
    // 组件创建时，自动调用 getList 方法获取数据
    // this.getList 是一个约定，混入此 mixin 的组件必须实现它
    this.getList();
  },
  methods: {
    /**
     * 搜索按钮操作
     */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /**
     * 重置按钮操作
     */
    resetQuery() {
      this.resetForm("queryForm"); // 假设您有 resetForm 这个通用方法
      this.handleQuery();
    },
    // 注意：最核心的 getList 方法由使用该 mixin 的组件自己去实现
    // 因为每个页面的API请求和数据处理逻辑都不同
  },
};
