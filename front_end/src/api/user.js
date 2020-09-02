import { axios } from '@/utils/request'

const api = {
    userPre: '/api/user',
    hotelPre: '/api/hotel'
}
export function loginAPI(data){
    return axios({
        url:`${api.userPre}/login`,
        method: 'POST',
        data
    })
}
export function registerAPI(data){
    return axios({
        url: `${api.userPre}/register`,
        method: 'POST',
        data
    })
}
export function getUserInfoAPI(id){
    return axios({
        url: `${api.userPre}/${id}/getUserInfo`,
        method: 'GET'
    })
}
export function getUserClientAPI(data){
    return axios({
        url:`${api.userPre}/${data.userId}/getUserClient`,
        method: 'GET',
    })
}
export function getUserHotelsAPI(data){
    return axios({
        url:`${api.hotelPre}/${data.userId}/getUserHotels`,
        method: 'GET',
    })
}
export function updateUserInfoAPI(data) {
    return axios({
        url: `${api.userPre}/${data.id}/userInfo/update`,
        method: 'POST',
        data
    })
}

export function getUserNameAPI(id) {
    return axios({
        url: `${api.userPre}/${id}/name`,
        method: 'GET',
    })
}
