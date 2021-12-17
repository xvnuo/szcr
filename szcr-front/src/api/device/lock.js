import request from '@/utils/request'

// 查询门锁设备列表
export function listLock(query) {
  return request({
    url: '/device/lock/list',
    method: 'get',
    params: query
  })
}

// 查询门锁设备详细
export function getLock(id) {
  return request({
    url: '/device/lock/' + id,
    method: 'get'
  })
}

// 新增门锁设备
export function addLock(data) {
  return request({
    url: '/device/lock',
    method: 'post',
    data: data
  })
}

// 修改门锁设备
export function updateLock(data) {
  return request({
    url: '/device/lock',
    method: 'put',
    data: data
  })
}

// 删除门锁设备
export function delLock(id) {
  return request({
    url: '/device/lock/' + id,
    method: 'delete'
  })
}

// 导出门锁设备
export function exportLock(query) {
  return request({
    url: '/device/lock/export',
    method: 'get',
    params: query
  })
}