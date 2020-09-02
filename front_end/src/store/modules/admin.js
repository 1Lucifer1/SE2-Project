import {
    getManagerListAPI,
    addManagerAPI,
    deleteUserAPI,
    getClientListAPI,
    addClientAPI
} from '@/api/admin'
import { message } from 'ant-design-vue'

const admin = {
    state: {
        managerList: [

        ],
        activeUserId:0,
        clientList:[

        ],
        currentManagerId:0,
        addManagerModalVisible: false,
        showHotelModalVisible:false,
        addHotelManageModalVisible:false,
        addClientModalVisible:false,
        manageAddHotelModalVisible:false,
        //updateHotelInfoModalVisible:false,
        addHotelManageParams:{
            name: '',
            address: '',
            bizRegion:'XiDan',
            hotelStar:'',
            rate: 0,
            description:'',
            phoneNum:'',
            managerId:'',
        },
        addManagerParams: {
            email:'',
            password:''
        }
    },
    mutations: {
        set_managerList: function(state, data) {
            state.managerList = data
        },
        set_clientList: function(state,data){
            state.clientList = data
        },
        set_activeUserId: (state,data) =>{
            state.activeUserId = data
        },
        set_addManagerModalVisible: function(state, data) {
            state.addManagerModalVisible = data
        },
        set_showHotelModalVisible: function(state,data){
            state.showHotelModalVisible = data
        },
        set_manageAddHotelModalVisible: function(state,data){
            state.manageAddHotelModalVisible=data
        },
        set_addHotelManageModalVisible: function(state,data){
            state.addHotelManageModalVisible = data
        },
        set_updateHotelInfoModalVisible: function(state,data){
            state.updateHotelInfoModalVisible = data
        },
        set_addClientModalVisible: function(state,data){
            state.addClientModalVisible = data
        },
        set_currentManagerId: function(state,data){
            state.currentManagerId = data
        },
        set_addManagerParams: function(state, data) {
            state.addManagerParams = {
                ...state.addManagerParams,
                ...data,
            }
        },
        set_addHotelManageParams: function(state, data) {
            state.addHotelManageParams = {
                ...state.addHotelManageParams,
                ...data,
            }
        }
    },
    actions: {
        getManagerList: async({ commit }) => {
            const res = await getManagerListAPI()
            if(res){
                commit('set_managerList', res)
            }
        },
        getClientList: async({ commit }) => {
            const res = await getClientListAPI()
            if(res){
                commit('set_clientList', res)
            }
        },

        addManager: async({ state, commit, dispatch }) => {
            const res = await addManagerAPI(state.addManagerParams)
            if(res) {
                commit('set_addManagerParams',{
                    email:'',
                    password:''
                })
                commit('set_addManagerModalVisible', false)
                message.success('添加成功')
                dispatch('getManagerList')
            }else{
                message.error('添加失败')
            }
        },
        addClient: async({ state, dispatch,commit }, client)=>{
            const res = await addClientAPI(client,state.activeUserId);
            if(res){
                commit('set_addClientModalVisible', false)
                message.success('修改成功')
            }
            else{
                message.error('添加失败')
            }

        },
        addHotelManage: async({ state, commit, dispatch }) => {

        },
        deleteUser: async({ state, dispatch }, userId) => {
            const res = await deleteUserAPI(userId)
            if(res) {
                dispatch('getManagerList')
                message.success('取消成功')
            }else{
                message.error('取消失败')
            }
        },
    }
}
export default admin