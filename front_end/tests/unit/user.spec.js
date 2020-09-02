import {mount, createLocalVue } from '@vue/test-utils'
import Vuex from 'vuex'
import Info from '../../src/views/user/info.vue'
import ShowOrder from '../../src/views/user/showOrder.vue'
import store from '../../src/store'
import router from '../../src/router'
const localVue = createLocalVue();
localVue.use(Vuex);

import Antd from 'ant-design-vue'
import Vue from 'vue'
Vue.use(Antd);

describe('test user page', () => {

    it('renders info test', () => {
        const wrapper = mount(Info, { store, router, localVue, Vue });
        expect(wrapper.isVueInstance()).toBeTruthy();
    });
    it('renders shoeOrder test', () => {
        const wrapper = mount(ShowOrder, { store, router, localVue, Vue });
        expect(wrapper.isVueInstance()).toBeTruthy();
    });

});