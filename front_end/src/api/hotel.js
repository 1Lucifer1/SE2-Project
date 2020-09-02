import { axios } from '@/utils/request'
const api = {
    hotelPre: '/api/hotel'
}
export function getHotelsAPI() {
    return axios({
        url: `${api.hotelPre}/all`,
        method: 'get',
    })
}
export function getHotelByIdAPI(param) {
    return axios({
        url: `${api.hotelPre}/${param.hotelId}/detail`,
        method: 'GET',
    })
}

export function getRemarkListByHotelIdAPI(param){
    return axios({
        url: `${api.hotelPre}/${param.hotelId}/getHotelRemark`,
        method: 'GET'
    })
}
export function addRemarkAPI(data) {
    return axios({
        url: `${api.hotelPre}/addRemark`,
        method: 'POST',
        data
    })
}

export function updateHotelRate(data) {
    return axios({
        url: `${api.hotelPre}/${data.hotelId}/rateUpdate`,
        method: 'POST',
        data,

    })
}
export function searchHotelAPI(data) {
    return axios({
        url: `${api.hotelPre}/search`,
        method: 'POST',
        data,

    })
}

