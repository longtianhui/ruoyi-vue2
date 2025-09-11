<template>
  <div class="test-container">
    <h2>测试数据列表</h2> 分页

    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="list" border style="width: 100%; margin-bottom: 20px;">
      <el-table-column prop="id" label="序号" width="80" align="center"></el-table-column>
      <el-table-column prop="content" label="测试内容" align="center"></el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 上传流程 -->
    <el-upload ref="upload" :limit="1" accept=".jpg, .png" :action="upload.url" :headers="upload.headers"
      :file-list="upload.fileList" :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess"
      :auto-upload="false">
      <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
      <el-button style="margin-left: 10px;" size="small" type="success" :loading="upload.isUploading"
        @click="submitUpload">上传到服务器</el-button>
      <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
    </el-upload>


    <!-- 下载组件 -->
    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleDownload(scope.row)">下载</el-button>

    <!-- 打开页签 -->
    <el-button size="mini" type="primary" @click="opentab">打开页签</el-button>

        <!-- 提示消息 -->
    <el-button size="mini" type="primary" @click="notify">modal对象</el-button>
  </div>
</template>

<script>
import { getPage } from '@/api/test/index'
import { getToken } from "@/utils/auth";

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
      // 上传参数
      upload: {
        // 是否禁用上传
        isUploading: false,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/common/upload",
        // 上传的文件列表
        fileList: []
      },
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
    },
    handleAdd() {

      this.upload.fileList = [];
    }
    , handleUpdate(row) {

      this.upload.fileList = [{ name: this.form.fileName, url: this.form.filePath }];
    },
    // 文件提交处理
    submitUpload() {
      this.$refs.upload.submit();
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.isUploading = false;
      this.form.filePath = response.url;
      this.msgSuccess(response.msg);
    },
    // 文件下载处理
    handleDownload(row) {
      var name = row.fileName;
      var url = row.filePath;
      var suffix = url.substring(url.lastIndexOf("."), url.length);
      const a = document.createElement('a')
      a.setAttribute('download', name + suffix)
      a.setAttribute('target', '_blank')
      a.setAttribute('href', url)
      a.click()
    },
    opentab(){
      this.$tab.openPage("用户管理1","/system/user");
    },
    notify(){
      this.$modal.msg("默认消息");
    }
  }
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
