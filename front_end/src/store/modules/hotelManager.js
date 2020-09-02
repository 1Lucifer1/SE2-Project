import {
    addRoomAPI,
    addHotelAPI,
    cancelHotelAPI,
    changeRoomAPI,
    dealRoomAPI,
    getHotelByManagerIdAPI,
    updateHotelInfoAPI,
} from '@/api/hotelManager'
import {
    getManagerOrdersAPI,
    orderCheckInAPI,
    orderCheckOutAPI,
    errorOrderAPI,
    rmErrorOrderAPI,
} from '@/api/order'
import {
    hotelAllCouponsAPI,
    hotelTargetMoneyAPI,
    hotelMultipleRoomCouponAPI,
    hotelTimeCouponAPI,
    hotelBirthdayCouponAPI,
} from '@/api/coupon'
import { message } from 'ant-design-vue'
import {getOrderAPI, getUserOrdersAPI} from "../../api/order";

const hotelManager = {
    state: {
        orderList: [],
        addHotelParams: {
            name: '',
            address: '',
            bizRegion:'XiDan',
            hotelStar:'',
            rate: 0,
            description:'',
            phoneNum:'',
            managerId:'',
        },
        addHotelModalVisible: false,
        updateHotelInfoModalVisible:false,
        addRoomParams: {
            roomType: '',
            hotelId: '',
            price: '',
            total: 0,
            curNum: 0,
        },
        addCouponParams:{
            couponType:'',
            description:'',
            couponName:'',
            hotelId:0,
            target_money:0,
            discount_money:0,
            discount:1,
            status:1,
        },
        addRoomModalVisible: false,
        couponVisible: false,
        addCouponVisible: false,
        changeRoomVisible:false,
        dealRoomChangeModalVisible:false,
        activeHotelId: 0,
        activeRoomId:0,
        couponList: [],
        managerOrderVisible: false,
        activeOrderId: 0,
        activeOrderDetail: {

        },
        changeRoomId: 0,
        managerHotelList: [],
    },
    mutations: {
        set_orderList: function(state, data) {
            state.orderList = data
        },
        set_addHotelModalVisible: function(state, data) {
            state.addHotelModalVisible = data
        },
        set_dealRoomChangeModalVisible: function(state, data) {
            state.dealRoomChangeModalVisible = data
        },
        set_addHotelParams: function(state, data) {
            state.addHotelParams = {
                ...state.addHotelParams,
                ...data,
            }
        },
        set_addRoomModalVisible: function(state, data) {
            state.addRoomModalVisible = data
        },
        set_addRoomParams: function(state, data) {
            state.addRoomParams = {
                ...state.addRoomParams,
                ...data,
            }
        },
        set_updateHotelInfoModalVisible: function(state,data){
            state.updateHotelInfoModalVisible = data
        },
        set_addCouponParams:function(state,data){
            state.addCouponParams={
                ...state.addCouponParams,
                ...data,
            }
        },
        set_couponVisible: function(state, data) {
            state.couponVisible = data
        },
        set_changeRoomVisible: function(state,data){
            state.changeRoomVisible = data
        },
        set_activeHotelId: function(state, data) {
            state.activeHotelId = data
        },
        set_activeRoomId: function(state,data){
            state.activeRoomId = data
        },
        set_couponList: function(state, data) {
            state.couponList = data
        },
        set_addCouponVisible: function(state, data) {
            state.addCouponVisible =data
        },
        set_managerOrderVisible: function (state, data) {
            state.managerOrderVisible = data;
        },
        set_activeOrderId: function (state, data) {
            state.activeOrderId = data;
        },
        set_activeOrderDetail: function (state, data) {
            state.activeOrderDetail = {
                ...state.activeOrderDetail,
                ...data,
            }
        },
        set_changeRoomId: function (state, data) {
            state.changeRoomId = data;
        },
        set_managerHotelList: function (state, data) {
            state.managerHotelList = data;
        }
    },
    actions: {
        getManagerOrders: async({ state, commit , getters }) => {

            const res = await getManagerOrdersAPI(getters.userId);
            if(res){
                commit('set_orderList', res)
            }
        },
        getManagerHotelList: async({ state, commit, getters}) => {
            //alert(Number(getters.currentManagerId));
            //alert("1")
            //alert(getters.currentManagerId);
            if(getters.currentManagerId!=0){
                const res = await getHotelByManagerIdAPI(Number(getters.currentManagerId));
                //alert(res);
                if(res){
                    commit('set_managerHotelList', res)
                }
            }
            else{
                const res = await getHotelByManagerIdAPI(Number(getters.userId));
                //alert(res);
                if(res){
                    commit('set_managerHotelList', res)
                }
            }
        },
        updateHotelInfo: async({ rootGetters, state, dispatch }, data) => {
            //alert(data.description);
            //alert(data.name);
            const params = {
                id: rootGetters.currentHotelId,
                ...data,
            }
            const res = await updateHotelInfoAPI(params)
            if(res){
                message.success('修改成功')
                //dispatch('getHotelInfo')
            }
        },
        addHotel: async({ state, dispatch, commit }) => {
            const res = await addHotelAPI(state.addHotelParams)
            if(res){
                dispatch('getHotelList')
                commit('set_addHotelParams', {
                    name: '',
                    address: '',
                    bizRegion:'XiDan',
                    hotelStar:'',
                    rate: 0,
                    description:'',
                    phoneNum:'',
                    managerId:'',
                })
                commit('set_addHotelModalVisible', false)
                message.success('添加成功')
            }else{
                message.error('添加失败')
            }
        },
        cancelHotel: async({ state, dispatch }, hotelId) => {
            const res = await cancelHotelAPI(hotelId)
            if(res) {
                dispatch('getHotelList')
                message.success('取消成功')
            }else{
                message.error('取消失败')
            }
        },
        changeRoom: async({ state, dispatch,commit }, num)=>{
            //alert("s");
            const res = await changeRoomAPI(state.changeRoomId, num);
            if(res){
                commit('set_changeRoomVisible', false)
                message.success('修改成功')
            }
            else{
                message.error('添加失败')
            }

        },
        dealRoom: async({ state, dispatch,commit }, num)=>{
            //alert("s");
            const res = await dealRoomAPI(state.changeRoomId, num);
            if(res){
                commit('set_dealRoomChangeModalVisible', false)
                message.success('修改成功')
            }
            else{
                message.error('添加失败')
            }

        },
        addRoom: async({ state, dispatch, commit }) => {
            const res = await addRoomAPI(state.addRoomParams)
            if(res){
                commit('set_addRoomModalVisible', false)
                commit('set_addRoomParams', {
                    roomType: '',
                    hotelId: '',
                    price: '',
                    total: 0,
                    curNum: 0,
                })
                message.success('添加成功')
            }else{
                message.error('添加失败')
            }
        },
        getHotelCoupon: async({ state, commit }) => {
            const res = await hotelAllCouponsAPI(state.activeHotelId)
            if(res) {
                // 获取到酒店策略之后的操作（将获取到的数组赋值给couponList）
                commit('set_couponList',res)
            }
        },
        addHotelCoupon: async({ commit, dispatch }, data) => {
            // alert(data.hotelId)
            // alert(data.type)
            if(data.type==3) {
                const res = await hotelTargetMoneyAPI(data)
                if(res){
                    // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                    dispatch('getHotelCoupon')
                    commit('set_addCouponParams', {
                        couponName: '',
                        couponType: '',
                        description:'',
                        hotelId:0,
                        state: 1,
                        target_money:0,
                        discount_money:0,
                        discount:1,
                    })
                    commit('set_addCouponVisible',false)
                    commit('set_couponVisible',true)

                    message.success('添加策略成功')
                }else {
                    // 添加失败后的操作
                    message.error('添加失败')
                }

            }
            else if(data.type==4) {
                const res = await hotelTimeCouponAPI(data)
                if (res) {
                    // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                    dispatch('getHotelCoupon')
                    commit('set_addCouponParams', {
                        couponName: '',
                        couponType: '',
                        description: '',
                        hotelId: 0,
                        state: 1,
                        target_money: 0,
                        discount_money: 0,
                        discount: 1,
                    })
                    commit('set_addCouponVisible', false)
                    commit('set_couponVisible', true)

                    message.success('添加策略成功')
                } else {
                    // 添加失败后的操作
                    message.error('添加失败')
                }
            }
            else if(data.type==2) {
                const res = await hotelMultipleRoomCouponAPI(data)
                if (res) {
                    // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                    dispatch('getHotelCoupon')
                    commit('set_addCouponParams', {
                        couponName: '',
                        couponType: '',
                        description: '',
                        hotelId: 0,
                        state: 1,
                        target_money: 0,
                        discount_money: 0,
                        discount: 1,
                    })
                    commit('set_addCouponVisible', false)
                    commit('set_couponVisible', true)

                    message.success('添加策略成功')
                } else {
                    // 添加失败后的操作
                    message.error('添加失败')
                }
            }
            else{
                const res = await hotelBirthdayCouponAPI(data)
                if (res) {
                    // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                    dispatch('getHotelCoupon')
                    commit('set_addCouponParams', {
                        couponName: '',
                        couponType: '',
                        description: '',
                        hotelId: 0,
                        state: 1,
                        target_money: 0,
                        discount_money: 0,
                        discount: 1,
                    })
                    commit('set_addCouponVisible', false)
                    commit('set_couponVisible', true)

                    message.success('添加策略成功')
                } else {
                    // 添加失败后的操作
                    message.error('添加失败')
                }
            }
        },
        getOrderDetail: async({ state, commit}) => {
            const res = await getOrderAPI(state.activeOrderId);
            if(res) {
                // 获取到酒店策略之后的操作（将获取到的数组赋值给couponList）
                commit('set_activeOrderDetail',res);
            }
        },
        orderCheckIn: async ({ state, dispatch }, orderId)=>{
            const res = await orderCheckInAPI(orderId);
            if(res){
                dispatch('getManagerOrders');
                dispatch('getOrderDetail');
                message.success("订单已入住, " + res);
            }else{
                message.error("操作失败");
            }
        },
        orderCheckOut: async ({ state, dispatch }, orderId)=>{
            const res = await orderCheckOutAPI(orderId);
            if(res){
                dispatch('getManagerOrders');
                dispatch('getOrderDetail');
                message.success("订单已退房, " + res);
            }else{
                message.error("操作失败");
            }
        },
        errorOrder: async ({ state, dispatch }, orderId)=>{
            const res = await errorOrderAPI(orderId);
            if(res){
                dispatch('getManagerOrders');
                dispatch('getOrderDetail');
                message.success("操作成功, " + res);
            }else{
                message.error("操作失败");
            }
        },
        rmErrorOrder: async ({ state, dispatch }, orderId)=>{
            const res = await rmErrorOrderAPI(orderId);
            if(res){
                dispatch('getManagerOrders');
                dispatch('getOrderDetail');
                message.success("操作成功, " + res);
            }else{
                message.error("操作失败");
            }

        },
    }
}
export default hotelManager