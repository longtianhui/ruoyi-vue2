import request from "@/utils/request";
// 测试接口
export function getTestData() {
  return request({
    url: "/test/public/getData",
    method: "get",
  });
}
//  测试分页接口
export function getPage(queryParams) {
  return request({
    url: "/test/getPage",
    method: "post",
    data: queryParams
  });
}
