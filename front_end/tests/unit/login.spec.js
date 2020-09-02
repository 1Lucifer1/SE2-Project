import {mount, createLocalVue } from '@vue/test-utils'
import Vuex from 'vuex'
import Login from '../../src/views/login.vue'
import store from '../../src/store'
import router from '../../src/router'
const localVue = createLocalVue();
localVue.use(Vuex);

import Antd from 'ant-design-vue'
import Vue from 'vue'
Vue.use(Antd);

describe('test login page', () => {

  it('renders login test', () => {
    const wrapper = mount(Login, { store, router, localVue, Vue });
    expect(wrapper.isVueInstance()).toBeTruthy();
    //expect(wrapper.element).toMatchSnapshot()
    // const email = "333@qq.com";
    // const password = "123456";
    // form.setFieldsValue({
    //   'username': email,
    // });
    // test('testInputEmail', () => {
    //   expect(wrapper.form.getFieldValue("username")).toBe(email);
    // })
    // test({
    //
    // });
    //expect(wrapper.text()).toMatch(msg)
  });
});

