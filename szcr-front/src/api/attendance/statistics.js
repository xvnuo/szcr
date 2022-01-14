import request from '@/utils/request'

// 查询考勤统计列表
export function listStatistics(query) {
  return request({
    url: '/attendance/statistics/list',
    method: 'get',
    params: query
  })
}

// 查询考勤统计详细
export function getStatistics(statisticsId) {
  return request({
    url: '/attendance/statistics/' + statisticsId,
    method: 'get'
  })
}

// 新增考勤统计
export function addStatistics(data) {
  return request({
    url: '/attendance/statistics',
    method: 'post',
    data: data
  })
}

// 修改考勤统计
export function updateStatistics(data) {
  return request({
    url: '/attendance/statistics',
    method: 'put',
    data: data
  })
}

// 删除考勤统计
export function delStatistics(statisticsId) {
  return request({
    url: '/attendance/statistics/' + statisticsId,
    method: 'delete'
  })
}

// 导出考勤统计
export function exportStatistics(query) {
  return request({
    url: '/attendance/statistics/export',
    method: 'get',
    params: query
  })
}