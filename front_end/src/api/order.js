import { axios } from '@/utils/request'
const api = {
    orderPre: '/api/order'
}
export function reserveHotelAPI(data) {
    return axios({
        url: `${api.orderPre}/addOrder`,
        method: 'POST',
        data,
    })
}
export function getAllOrdersAPI() {
    return axios({
        url: `${api.orderPre}/getAllOrders`,
        method: 'GET',
    })
}
export function getUserOrdersAPI(data) {
    return axios({
        url: `${api.orderPre}/${data.userId}/getUserOrders`,
        method: 'GET',
    })
}
export function getManagerOrdersAPI(userId) {
    return axios({
        url: `${api.orderPre}/${userId}/getManagerOrders`,
        method: 'GET',
    })
}
export function cancelOrderAPI(orderId) {
    return axios({
        url: `${api.orderPre}/${orderId}/annulOrder`,
        method: 'GET',
    })
}

export function orderCheckInAPI(orderId) {
    return axios({
        url: `${api.orderPre}/${orderId}/orderCheckIn`,
        method: 'POST',
    })
}

export function orderCheckOutAPI(orderId) {
    return axios({
        url: `${api.orderPre}/${orderId}/orderCheckOut`,
        method: 'POST',
    })
}

export function errorOrderAPI(orderId) {
    return axios({
        url: `${api.orderPre}/${orderId}/errorOrder`,
        method: 'POST',
    })
}

export function rmErrorOrderAPI(orderId) {
    return axios({
        url: `${api.orderPre}/${orderId}/rmErrorOrder`,
        method: 'POST',
    })
}

export function getOrderAPI(orderId){
    return axios({
        url: `${api.orderPre}/${orderId}/getOrderByOrderId`,
        method: 'GET',
    })
}