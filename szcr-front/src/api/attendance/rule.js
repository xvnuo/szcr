import request from '@/utils/request'

// 查询考勤规则列表
export function listRule(query) {
  return request({
    url: '/attendance/rule/list',
    method: 'get',
    params: query
  })
}

// 查询考勤规则详细
export function getRule(ruleId) {
  return request({
    url: '/attendance/rule/' + ruleId,
    method: 'get'
  })
}

// 新增考勤规则
export function addRule(data) {
  return request({
    url: '/attendance/rule',
    method: 'post',
    data: data
  })
}

// 修改考勤规则状态
export function changeRuleStatus(ruleId, status) {
  const data = {
    ruleId,
    status
  }
  return request({
    url: '/attendance/rule/changeStatus',
    method: 'put',
    data: data
  })
}

// 修改考勤规则
export function updateRule(data) {
  return request({
    url: '/attendance/rule',
    method: 'put',
    data: data
  })
}

// 删除考勤规则
export function delRule(ruleId) {
  return request({
    url: '/attendance/rule/' + ruleId,
    method: 'delete'
  })
}

// 导出考勤规则
export function exportRule(query) {
  return request({
    url: '/attendance/rule/export',
    method: 'get',
    params: query
  })
}

// 查询已分配规则的用户列表
export function assignedUserList(query) {
  return request({
    url: '/attendance/rule/authUser/assignedList',
    method: 'get',
    params: query
  })
}

// 查询未分配规则的用户列表
export function unassignedUserList(query) {
  return request({
    url: '/attendance/rule/authUser/unassignedList',
    method: 'get',
    params: query
  })
}

// 批量取消用户分配的考勤规则
export function authUserCancelAll(data) {
  return request({
    url: '/attendance/rule/authUser/cancelAll',
    method: 'put',
    params: data
  })
}

// 批量分配考勤规则给用户
export function authUserSelectAll(data) {
  return request({
    url: '/attendance/rule/authUser/selectAll',
    method: 'put',
    params: data
  })
}
