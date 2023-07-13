import * as request from './http'

export function listAll(params) {
  return request.get('tags/list', params)
}
