import { axios } from '@/utils/request'
const api = {
    remarkPre: '/api/remark'
}
export function getRemarkListByHotelIdAPI(param){
    return axios({
        url: `${api.remarkPre}/${param.hotelId}/getHotelRemark`,
        method: 'GET'
    })
}
export function addRemarkAPI(data) {
    return axios({
        url: `${api.remarkPre}/addRemark`,
        method: 'POST',
        data
    })
}