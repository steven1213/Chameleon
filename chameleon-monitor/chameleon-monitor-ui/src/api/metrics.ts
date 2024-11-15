import request from '@/utils/request'

export function getMetrics(params: any) {
  return request({
    url: '/api/metrics',
    method: 'get',
    params
  })
}
