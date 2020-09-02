import { axios } from '@/utils/request'
const api = {
    hotelPre: '/api/hotel'
}
export function addRoomAPI(data) {
    return axios({
        url: `${api.hotelPre}/roomInfo`,
        method: 'POST',
        data,
    })
}
export function addHotelAPI(data) {
    return axios({
        url: `${api.hotelPre}/addHotel`,
        method: 'POST',
        data,
    })
}
export function changeRoomAPI(roomId, num){
    return axios({
        url: `${api.hotelPre}/${roomId}/${num}/changeRoom`,
        method: 'POST',
    })
}
export function updateHotelInfoAPI(data) {
    return axios({
        url: `${api.hotelPre}/${data.id}/hotelInfo/updateHotelInfo`,
        method: 'POST',
        data
    })
}
export function dealRoomAPI(roomId, num){
    return axios({
        url: `${api.hotelPre}/${roomId}/${num}/dealRoom`,
        method: 'POST',
    })
}
export function cancelHotelAPI(hotelId){
    return axios({
        url: `${api.hotelPre}/${hotelId}/cancelHotel`,
        method: 'POST',
    })
}
export function getHotelByManagerIdAPI(managerId) {
    return axios({
        url: `${api.hotelPre}/${managerId}/getManagerHotel`,
        method: 'GET',
    })
}