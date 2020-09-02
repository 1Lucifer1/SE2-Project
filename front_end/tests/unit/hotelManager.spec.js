import {mount, createLocalVue } from '@vue/test-utils'
import Vuex from 'vuex'
import ManageHotel from '../../src/views/hotelManager/manageHotel.vue'
import AddCoupon from '../../src/views/hotelManager/components/addCoupon.vue'
import AddHotelModal from '../../src/views/hotelManager/components/addHotelModal.vue'
import AddRoomModal from '../../src/views/hotelManager/components/addRoomModal.vue'
import Coupon from '../../src/views/hotelManager/components/coupon.vue'
import ShowOrderDetail from '../../src/views/hotelManager/components/showOrderDetail.vue'
import store from '../../src/store'
import router from '../../src/router'
const localVue = createLocalVue();
localVue.use(Vuex);

import Antd from 'ant-design-vue'
import Vue from 'vue'
Vue.use(Antd);

describe('test hotelManager page', () => {

    it('renders manageHotel test', () => {
        const wrapper = mount(ManageHotel, { store, router, localVue, Vue });
        //expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('renders addCoupon test', () => {
        const wrapper = mount(AddCoupon, { store, router, localVue, Vue });
        //expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('renders addHotelModal test', () => {
        const wrapper = mount(AddHotelModal, { store, router, localVue, Vue });
        //expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('renders addRoomModal test', () => {
        const wrapper = mount(AddRoomModal, { store, router, localVue, Vue });
        //expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('renders Coupon test', () => {
        const wrapper = mount(Coupon, { store, router, localVue, Vue });
        expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('renders showOrderDetail test', () => {
        const wrapper = mount(ShowOrderDetail, { store, router, localVue, Vue });
        expect(wrapper.isVueInstance()).toBeTruthy();
    });

});
