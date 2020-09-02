import {mount, createLocalVue, shallowMount} from '@vue/test-utils'
//import axios from 'axios'
import Vuex, {mapGetters} from 'vuex'
import HotelDetail from '../../src/views/hotel/hotelDetail.vue'
import HotelList from '../../src/views/hotel/hotelList.vue'
import AddRemarkModal from '../../src/views/hotel/components/addRemarkModal.vue'
import ChangeRoomModal from '../../src/views/hotel/components/changeRoomModal.vue'
import DealRoomChangeModal from '../../src/views/hotel/components/dealRoomChangeModal.vue'
import HotelCard from '../../src/views/hotel/components/hotelCard.vue'
import OrderModal from '../../src/views/hotel/components/orderModal.vue'
import RemarkList from '../../src/views/hotel/components/remarkList.vue'
import RoomList from '../../src/views/hotel/components/roomList.vue'
import UpdateHotelInfoModal from '../../src/views/hotel/components/updateHotelInfoModal.vue'
import store from '../../src/store'
import router from '../../src/router'
const localVue = createLocalVue();
localVue.use(Vuex);

import Antd from 'ant-design-vue'
import Vue from 'vue'
Vue.use(Antd);

describe('test hotel page', () => {

    it('renders hotelDetail test', () => {
        const wrapper = shallowMount(HotelDetail, { store, router, localVue, Vue});
        //expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('renders hotelList test', () => {
        const wrapper = mount(HotelList, { store, router, localVue, Vue });
        //expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('renders addRemarkModal test', () => {
        const wrapper = mount(AddRemarkModal, { store, router, localVue, Vue });
        //expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('renders changeRoomModal test', () => {
        const wrapper = mount(ChangeRoomModal, { store, router, localVue, Vue });
        //expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('renders dealRoomModal test', () => {
        const wrapper = mount(DealRoomChangeModal, { store, router, localVue, Vue });
        //expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('renders hotelCard test', () => {
        const wrapper = shallowMount(HotelCard, { store, router, localVue, Vue
        , propsData: {
            hotel: {
                title: "1",
                name: "1"
            }
            }
        });
        //expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('renders orderModal test', () => {
        const wrapper = mount(OrderModal, { store, router, localVue, Vue });
        //expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('renders remarkList test', () => {
        const wrapper = shallowMount(RemarkList, { store, router, localVue, Vue
            , propsData: {
                remarks: {
                    length: 1,
                    title: "1",
                    name: "1"
                }
            }
        });
        //expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('renders roomList test', () => {
        const wrapper = mount(RoomList, { store, router, localVue, Vue });
        //expect(wrapper.isVueInstance()).toBeTruthy();
    });

    it('renders updateHotelInfoModal test', () => {
        const wrapper = mount(UpdateHotelInfoModal, { store, router, localVue, Vue });
        //expect(wrapper.isVueInstance()).toBeTruthy();
    });

});
