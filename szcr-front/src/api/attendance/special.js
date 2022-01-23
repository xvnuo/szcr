import request from '@/utils/request'

// 查询异常考勤列表
export function listSpecial(query) {
  return request({
    url: '/attendance/special/list',
    method: 'get',
    params: query
  })
}

// 查询异常考勤详细
export function getSpecial(specialId) {
  return request({
    url: '/attendance/special/' + specialId,
    method: 'get'
  })
}

// 新增异常考勤
export function addSpecial(data) {
  return request({
    url: '/attendance/special',
    method: 'post',
    data: data
  })
}

// 修改异常考勤
export function updateSpecial(data) {
  return request({
    url: '/attendance/special',
    method: 'put',
    data: data
  })
}

// 删除异常考勤
export function delSpecial(specialId) {
  return request({
    url: '/attendance/special/' + specialId,
    method: 'delete'
  })
}

// 导出异常考勤
export function exportSpecial(query) {
  return request({
    url: '/attendance/special/export',
    method: 'get',
    params: query
  })
}