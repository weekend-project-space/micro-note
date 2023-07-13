import * as request from './http'

export function findAll(params) {
  return request.get('note', params)
}

export function get(id) {
  return request.get(`note/${id}`)
}

export function save(data) {
  return request.post('note', data)
}

export function update(id, data) {
  return request.put(`note/${id}`, data)
}

export function del(id) {
  return request.del(`note/${id}`)
}
