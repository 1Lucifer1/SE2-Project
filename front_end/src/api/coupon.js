import { axios } from '@/utils/request'
const api = {
    couponPre: '/api/coupon'
}
export function hotelTargetMoneyAPI(data) {
    return axios({
        url: `${api.couponPre}/hotelTargetMoney`,
        method: 'POST',
        data,
    })
}
export function hotelBirthdayCouponAPI(data){
    return axios({
        url: `${api.couponPre}/hotelBirthdayCoupon`,
        method: 'POST',
        data,
    })
}
export function hotelMultipleRoomCouponAPI(data){
    return axios({
        url: `${api.couponPre}/hotelMultipleRoomCoupon`,
        method: 'POST',
        data,
    })
}
export function hotelTimeCouponAPI(data){
    return axios({
        url: `${api.couponPre}/hotelTimeCoupon`,
        method: 'POST',
        data,
    })
}

export function hotelAllCouponsAPI(hotelId) {
    return axios({
        url: `${api.couponPre}/hotelAllCoupons`,
        method: 'GET',
        params: {hotelId: hotelId},
    })
}

export function orderMatchCouponsAPI(params) {
    return axios({
        url: `${api.couponPre}/orderMatchCoupons`,
        method: 'GET',
        params,
    })
}