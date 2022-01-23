import request from '@/utils/request'

// 查询月度考勤统计列表
export function listStatistics(query) {
  return request({
    url: '/attendance/statistics/list',
    method: 'get',
    params: query
  })
}

// 查询月度考勤统计详细
export function getStatistics(statisticsId) {
  return request({
    url: '/attendance/statistics/' + statisticsId,
    method: 'get'
  })
}

// 新增月度考勤统计
export function addStatistics(data) {
  return request({
    url: '/attendance/statistics',
    method: 'post',
    data: data
  })
}

// 修改月度考勤统计
export function updateStatistics(data) {
  return request({
    url: '/attendance/statistics',
    method: 'put',
    data: data
  })
}

// 删除月度考勤统计
export function delStatistics(statisticsId) {
  return request({
    url: '/attendance/statistics/' + statisticsId,
    method: 'delete'
  })
}

// 导出月度考勤统计
export function exportStatistics(query) {
  return request({
    url: '/attendance/statistics/export',
    method: 'get',
    params: query
  })
}