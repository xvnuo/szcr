import request from '@/utils/request'

// 查询节假日信息列表
export function listHoliday(query) {
  return request({
    url: '/attendance/holiday/list',
    method: 'get',
    params: query
  })
}

// 查询节假日信息详细
export function getHoliday(holidayId) {
  return request({
    url: '/attendance/holiday/' + holidayId,
    method: 'get'
  })
}

// 新增节假日信息
export function addHoliday(data) {
  return request({
    url: '/attendance/holiday',
    method: 'post',
    data: data
  })
}

// 修改节假日信息
export function updateHoliday(data) {
  return request({
    url: '/attendance/holiday',
    method: 'put',
    data: data
  })
}

// 节假日状态修改
export function changeHolidayStatus(holidayId, status) {
  const data = {
    holidayId,
    status
  }
  return request({
    url: '/attendance/holiday/changeStatus',
    method: 'put',
    data: data
  })
}

// 删除节假日信息
export function delHoliday(holidayId) {
  return request({
    url: '/attendance/holiday/' + holidayId,
    method: 'delete'
  })
}

// 导出节假日信息
export function exportHoliday(query) {
  return request({
    url: '/attendance/holiday/export',
    method: 'get',
    params: query
  })
}
