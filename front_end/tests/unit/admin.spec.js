import {mount, createLocalVue } from '@vue/test-utils'
import Vuex from 'vuex'
import ManageUser from '../../src/views/admin/manageUser.vue'
import AddClientModal from '../../src/views/admin/components/addClientModal.vue'
import AddHotelManageModal from '../../src/views/admin/components/addHotelManageModal.vue'
import AddManagerModal from '../../src/views/admin/components/addManagerModal.vue'
import ManageAddHotelModal from '../../src/views/admin/components/addHotelManageModal.vue'
import ShowHotelModal from '../../src/views/admin/components/showHotelModal.vue'
import store from '../../src/store'
import router from '../../src/router'

const localVue = createLocalVue();
localVue.use(Vuex);

import Antd from 'ant-design-vue'
import Vue from 'vue'
Vue.use(Antd);

describe('test admin page', () => {

    it('renders manageUser test', () => {
        const wrapper = mount(ManageUser, { store, router, localVue, Vue });
        expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('renders addClientModal test', () => {
        const wrapper = mount(AddClientModal, { store, router, localVue, Vue });
        expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('renders addHotelManageModal test', () => {
        const wrapper = mount(AddHotelManageModal, { store, router, localVue , Vue});
        expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('renders addManagerModal test', () => {
        const wrapper = mount(AddManagerModal, { store, router, localVue , Vue});
        expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('renders manageAddHotelModal test', () => {
        const wrapper = mount(ManageAddHotelModal, { store, router, localVue , Vue});
        expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('renders showHotelModal test', () => {
        const wrapper = mount(ShowHotelModal, { store, router, localVue, Vue});
        expect(wrapper.isVueInstance()).toBeTruthy();
    });
});