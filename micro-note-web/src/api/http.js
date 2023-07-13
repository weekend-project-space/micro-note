const BASE_URL = '/api/';

const request = async (url, params = {}, method = 'GET') => {
  let options = {
    method
  };
  if (Object.keys(params).length) {
    options.headers = {
      'Content-Type': 'application/json'
    };
    if ('GET' === method) {
      url += '?' + (new URLSearchParams(params)).toString();
    } else {
      options.body = JSON.stringify(params);
    }
  }
  return fetch(BASE_URL + url, options).then(response => {
    let {
      status,
      statusText
    } = response;
    // 只要状态码是以2或者3开始的，才是真正的获取成功
    if (status >= 200 && status < 400) {
      if (response.headers.get('content-length') !== '0' && response.headers.get('content-type').indexOf('application/json') > -1) {
        return response.json();
      } else {
        return response;
      }
    }
    return Promise.reject({
      code: 'STATUS ERROR',
      status,
      statusText,
      response
    });
  }).catch(async reason => {
    if (reason && reason.code === 'STATUS ERROR') {
      return Promise.reject({
        data: await reason.response.json(),
        status: reason.status
      });
    } else if (!navigator.onLine) {
      // @2 网络中断
      // ...
    } else {
      // @3 请求被终止
      // ...
    }
    return Promise.reject(reason);
  });

};
export const get = (url, params) => request(url, params, 'GET');
export const post = (url, data) => request(url, data, 'POST');
export const put = (url, data) => request(url, data, 'PUT');
export const del = (url, data) => request(url, data, 'DELETE');
