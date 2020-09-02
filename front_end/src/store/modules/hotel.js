import { message } from 'ant-design-vue'
import store from '@/store'
import {
    getHotelsAPI,
    getHotelByIdAPI,
    getRemarkListByHotelIdAPI,
    addRemarkAPI,
    updateHotelRate,
    searchHotelAPI,
} from '@/api/hotel'
import {
    reserveHotelAPI,
    getUserOrdersAPI
} from '@/api/order'
import {
    orderMatchCouponsAPI,
} from '@/api/coupon'

const hotel = {
    state: {
        hotelList: [

        ],
        isSearching: false,
        isTableDisplay: false,
        displayHotelList: [

        ],
        hotelListParams: {
            pageNo: 0,
            pageSize: 12
        },
        hotelListLoading: true,
        currentHotelId: '',
        currentHotelInfo: {

        },
        orderModalVisible: false,
        addRemarkModalVisible: false,
        currentOrderRoom: {

        },
        currentRemarkList: {

        },
        orderMatchCouponList: [

        ],
        ableToRemark: false
    },
    mutations: {
        set_hotelList: function(state, data) {
            state.hotelList = data
        },
        set_isTableDisplay: function(state, data) {
            state.isTableDisplay = data;
        },
        set_isSearchingtParams: function(state, data) {
            state.isSearching = data;
        },
        set_hotelListParams: function(state, data) {
            state.hotelListParams = {
                ...state.hotelListParams,
                ...data,
            }
        },
        set_hotelListLoading: function(state, data) {
            state.hotelListLoading = data
        },
        set_currentHotelId: function(state, data) {
            state.currentHotelId = data
        },
        set_currentHotelInfo: function(state, data) {
            state.currentHotelInfo = {
                ...state.currentHotelInfo,
                ...data,
            }
        },
        set_orderModalVisible: function(state, data) {
            state.orderModalVisible = data
        },
        set_addRemarkModalVisible: function(state, data) {
            state.addRemarkModalVisible = data
        },
        set_currentOrderRoom: function(state, data) {
            state.currentOrderRoom = {
                ...state.currentOrderRoom,
                ...data,
            }
        },
        set_displayHotelList: function(state, data) {
            state.displayHotelList = data
        },
        set_orderMatchCouponList: function(state, data) {
            state.orderMatchCouponList = data
        },
        set_ableToRemark: function(state, data){
            state.ableToRemark = data
        }
    },

    actions: {
        getHotelList: async({commit, state}) => {
            const res = await getHotelsAPI()
            if(res){
                commit('set_hotelList', res)
                commit('set_hotelListLoading', false)
            }
        },
        getdisplayHotelList: async({commit, state}, data) => {
            const res = await searchHotelAPI(data);
            if(res){
                commit('set_displayHotelList', res)
                commit('set_hotelListLoading', false)
                commit('set_isSearchingtParams', true);
            }
        },
        getHotelById: async({commit, state}) => {
            const res = await getHotelByIdAPI({
                hotelId: state.currentHotelId
            })
            if(res){
                commit('set_currentHotelInfo', res)
            }
        },
        addOrder: async({ state, commit }, data) => {
            const res = await reserveHotelAPI(data)
            console.log(res)
            if(res){
                message.success('预定成功')

            }
        },
        addRemark: async({ rootGetters, commit }, data) => {
            const res = await addRemarkAPI(data);
            console.log(rootGetters)
            if(res){
                store.dispatch('getHotelById')
                commit('set_addRemarkModalVisible', false)
                var remarkList = rootGetters.currentHotelInfo.remarks;
                var rate = 0;
                var finalRate;
                if(remarkList){
                    for(var i = 0; i < remarkList.length; i++) {
                        rate += Number(remarkList[i].star)
                    }
                    finalRate = ((rate / i).toFixed(1)).toString()
                }
                else {
                    finalRate = "0"
                }
                data = {
                    hotelId: rootGetters.currentHotelId,
                    rate: finalRate
                }
                const res_sec = await updateHotelRate(data)
                if(res_sec){
                    message.success('评论成功')
                }
            }
        },
        getOrderMatchCoupons: async({ state, commit }, data) => {
            const res = await orderMatchCouponsAPI(data)
            if(res){
                commit('set_orderMatchCouponList', res)
            }
        },
        judgeIfAbleToRemark:async({ rootGetters, commit }) => {
            var orderList = rootGetters.userOrderList;
            var hotelId = rootGetters.currentHotelId;
            for(var i = 0; i < orderList.length; i++){
                if(orderList[i].hotelId === hotelId ){
                    commit('set_ableToRemark', true);
                    return
                }
            }
            commit('set_ableToRemark', false);
        },
    }
}

export default hotel