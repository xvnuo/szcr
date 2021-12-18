import request from '@/utils/request'

// 查询考勤记录列表
export function listRecord(query) {
  return request({
    url: '/attendance/record/list',
    method: 'get',
    params: query
  })
}

// 查询考勤记录详细
export function getRecord(recordId) {
  return request({
    url: '/attendance/record/' + recordId,
    method: 'get'
  })
}

// 新增考勤记录
export function addRecord(data) {
  return request({
    url: '/attendance/record',
    method: 'post',
    data: data
  })
}

// 修改考勤记录
export function updateRecord(data) {
  return request({
    url: '/attendance/record',
    method: 'put',
    data: data
  })
}

// 删除考勤记录
export function delRecord(recordId) {
  return request({
    url: '/attendance/record/' + recordId,
    method: 'delete'
  })
}

// 导出考勤记录
export function exportRecord(query) {
  return request({
    url: '/attendance/record/export',
    method: 'get',
    params: query
  })
}