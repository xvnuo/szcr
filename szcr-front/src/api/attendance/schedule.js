import request from '@/utils/request'

// 查询排班列表
export function listSchedule(query) {
  return request({
    url: '/attendance/schedule/list',
    method: 'get',
    params: query
  })
}

// 查询排班详细
export function getSchedule(scheduleId) {
  return request({
    url: '/attendance/schedule/' + scheduleId,
    method: 'get'
  })
}

// 新增排班
export function addSchedule(data) {
  return request({
    url: '/attendance/schedule',
    method: 'post',
    data: data
  })
}

// 修改排班
export function updateSchedule(data) {
  return request({
    url: '/attendance/schedule',
    method: 'put',
    data: data
  })
}

// 删除排班
export function delSchedule(scheduleId) {
  return request({
    url: '/attendance/schedule/' + scheduleId,
    method: 'delete'
  })
}

// 导出排班
export function exportSchedule(query) {
  return request({
    url: '/attendance/schedule/export',
    method: 'get',
    params: query
  })
}
