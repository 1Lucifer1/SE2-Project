import Vue from 'vue'
import router from '@/router'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'
import { message } from 'ant-design-vue'
import {
    loginAPI,
    registerAPI,
    getUserInfoAPI,
    updateUserInfoAPI,
    getUserClientAPI,
    getUserHotelsAPI
} from '@/api/user'

import {
    getUserOrdersAPI,
    cancelOrderAPI,
} from '@/api/order'
import {getOrderAPI} from "../../api/order";

const getDefaultState = () => {
    return {
        userId: '',
        userInfo: {

        },
        userOrderList: [

        ],
        orderVisible : false ,
        userClientList: [

        ],
        userOrderId: 0,
        userOrderDetail: {

        }
    }
}

const user = {
    state : getDefaultState(),

    mutations: {
        reset_state: function(state) {
            state.token = '',
            state.userId = '',
            state.userInfo = {
                
            },
            state.userOrderList = [],
            state.userClientList=[],
            state.orderVisible=false
        },
        set_token: function(state, token){
            state.token = token
        },
        set_orderVisible: function(state,data){
            state.orderVisible=data
        },
        set_email: (state, data) => {
            state.email = data
        },
        set_userId: (state, data) => {
            state.userId = data
        },
        set_activeUserId: (state,data) =>{
            state.activeUserId = data
        },
        set_userInfo: (state, data) => {
            state.userInfo = {
                ...state.userInfo,
                ...data
            }
        },
        set_userOrderList: (state, data) => {
            state.userOrderList = data
        },
        set_userClientList : (state,data) =>{
            state.userClientList = data
        },
        set_userOrderId: function (state, data) {
            state.userOrderId = data;
        },
        set_userOrderDetail: function (state, data) {
            state.userOrderDetail = {
                ...state.userOrderDetail,
                ...data,
            }
        },

    },

    actions: {
        login: async ({ dispatch, commit }, userData) => {
            const res = await loginAPI(userData)
            if(res){
                setToken(res.id)
                commit('set_userId', res.id)
                dispatch('getUserInfo')
                router.push('/hotel/hotelList')
            }
        },
        register: async({ commit }, data) => {
            const res = await registerAPI(data)
            if(res){
                message.success('注册成功')
            }
        },
        getUserInfo({ state, commit }) {
            return new Promise((resolve, reject) => {
              getUserInfoAPI(state.userId).then(response => {
                const data = response
                if (!data) {
                  reject('登录已过期，请重新登录')
                }
                commit('set_userInfo', data)
                commit('set_userId', data.id)
                resolve(data)
              }).catch(error => {
                reject(error)
              })
            })
        },
        getUserHotels: async({ state, commit }) => {
            const data = {
                userId: Number(state.userId)
            }
            const res = await getUserHotelsAPI(data)
            if(res){
                commit('set_userHotelList', res)
                console.log(state.userHotelList)
            }
        },
        updateUserInfo: async({ state, dispatch }, data) => {
            const params = {
                id: state.userId,
                ...data,
            }
            const res = await updateUserInfoAPI(params)
            if(res){
                message.success('修改成功')
                dispatch('getUserInfo')
            }
        },
        getUserOrders: async({ state, commit }) => {
            const data = {
                userId: Number(state.userId)
            }
            const res = await getUserOrdersAPI(data)
            if(res){
                commit('set_userOrderList', res)
                console.log(state.userOrderList)
            }
        },
        getUserClient : async({state,commit})=>{
            const data = {
                userId: Number(state.userId)
            }
            const res = await getUserClientAPI(data)
            if(res){
                commit('set_userClientList',res)
                console.log(state.userClientList)
            }
        },
        getUserOrderDetail: async({ state, commit }) => {
            const res = await getOrderAPI(state.userOrderId);
            if(res) {
                // 获取到酒店策略之后的操作（将获取到的数组赋值给couponList）
                commit('set_userOrderDetail',res);
            }
        },
        cancelOrder: async({ state, dispatch }, orderId) => {
            const res = await cancelOrderAPI(orderId)
            if(res) {
                dispatch('getUserOrders')
                dispatch('getManagerOrders')
                dispatch('getUserClient')
                message.success('撤销成功')
            }else{
                message.error('撤销失败')
            }
        },
        logout: async({ commit }) => {
            removeToken()
            resetRouter()
            commit('reset_state')
        },
        resetToken({ commit }) {
            return new Promise(resolve => {
                removeToken() // must remove  token  first
                commit('reset_state')
                resolve()
            })
        },
    }
}

export default user
