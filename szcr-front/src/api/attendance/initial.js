import request from '@/utils/request'

// 查询原始考勤记录列表
export function listInitial(query) {
  return request({
    url: '/attendance/initial/list',
    method: 'get',
    params: query
  })
}

// 查询原始考勤记录详细
export function getInitial(initialId) {
  return request({
    url: '/attendance/initial/' + initialId,
    method: 'get'
  })
}

// 新增原始考勤记录
export function addInitial(data) {
  return request({
    url: '/attendance/initial',
    method: 'post',
    data: data
  })
}

// 修改原始考勤记录
export function updateInitial(data) {
  return request({
    url: '/attendance/initial',
    method: 'put',
    data: data
  })
}

// 删除原始考勤记录
export function delInitial(initialId) {
  return request({
    url: '/attendance/initial/' + initialId,
    method: 'delete'
  })
}

// 导出原始考勤记录
export function exportInitial(query) {
  return request({
    url: '/attendance/initial/export',
    method: 'get',
    params: query
  })
}