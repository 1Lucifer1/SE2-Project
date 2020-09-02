<template>
    <a-layout>
        <a-layout-content>
            <div class="hotelDetailCard">
                <h1>
                    {{ currentHotelInfo.title }}
                </h1>
                <div class="hotel-info">
                    <a-card style="width: 240px">
                        <img
                            alt="example"
                            src="@/assets/cover.jpeg"
                            slot="cover"
                            referrerPolicy="no-referrer"
                            />
                    </a-card>
                    <div class="info">
                        <div class="items" v-if="currentHotelInfo.name">
                            <span class="label">酒店名称：</span>
<!--                            <a-input-->
<!--                                    placeholder="请填写酒店名名"-->
<!--                                    v-decorator="['name', { rules: [{ required: true, message: '请输入酒店名称' }] }]"-->
<!--                                    v-if="modify"-->
<!--                            />-->
                            <span class="value" >{{ currentHotelInfo.name }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.address">
                            <span class="label">地址</span>
<!--                            <a-input-->
<!--                                    placeholder="请填写地址"-->
<!--                                    v-decorator="['address', { rules: [{ required: true, message: '请输入地址' }] }]"-->
<!--                                    v-if="modify"-->
<!--                            />-->
                            <span class="value" >{{ currentHotelInfo.address }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.rate">
                            <span class="label">评分:</span>
                            <a-rate style="font-size: 15px" :value="currentHotelInfo.rate" disabled allowHalf/>
                        </div>
                        <div class="items" v-if="currentHotelInfo.hotelStar">
                            <span class="label">星级:</span>
                            <span v-if="currentHotelInfo.hotelStar === 'One'">一星级</span>
                            <span v-if="currentHotelInfo.hotelStar === 'Two'">二星级</span>
                            <span v-if="currentHotelInfo.hotelStar === 'Three'">三星级</span>
                            <span v-if="currentHotelInfo.hotelStar === 'Four'">四星级</span>
                            <span v-if="currentHotelInfo.hotelStar === 'Five'">五星级</span>
<!--                            <a-input-->
<!--                                    placeholder="请填写星级"-->
<!--                                    v-decorator="['star', { rules: [{ required: true, message: '请输入星级' }] }]"-->
<!--                                    v-if="modify"-->
<!--                            />-->
<!--                            <a-rate style="font-size: 15px" :value="currentHotelInfo.rate" disabled allowHalf />-->
                        </div>
                        <div class="items" v-if="currentHotelInfo.description">
                            <span class="label">酒店简介:</span>
<!--                            <a-input-->
<!--                                    placeholder="请填写酒店简介"-->
<!--                                    v-decorator="['description', { rules: [{ required: true, message: '请输入酒店简介' }] }]"-->
<!--                                    v-if="modify"-->
<!--                            />-->
                            <span class="value" >{{ currentHotelInfo.description }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.bizRegion">
                            <span class="label">酒店商圈:</span>
<!--                            <a-input-->
<!--                                    placeholder="请填写酒店商圈"-->
<!--                                    v-decorator="['bizRegion', { rules: [{ required: true, message: '请输入酒店商圈' }] }]"-->
<!--                                    v-if="modify"-->
<!--                            />-->
                            <span class="value">{{ currentHotelInfo.bizRegion }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.phoneNum">
                            <span class="label">酒店电话:</span>
<!--                            <a-input-->
<!--                                    placeholder="请填写电话"-->
<!--                                    v-decorator="['phoneNum', { rules: [{ required: true, message: '请输入电话' }] }]"-->
<!--                                    v-if="modify"-->
<!--                            />-->
                            <span class="value" >{{ currentHotelInfo.phoneNum }}</span>
                        </div>
<!--                        <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="modify">-->
<!--                            <a-button type="primary" @click="saveModify">-->
<!--                                保存-->
<!--                            </a-button>-->
<!--                            <a-button type="default" style="margin-left: 30px" @click="cancelModify">-->
<!--                                取消-->
<!--                            </a-button>-->
<!--                        </a-form-item>-->
                        <a-form-item :wrapper-col="{ span: 8, offset: 4 }" v-if="userInfo.userType=='HotelManager'">
                            <a-button type="primary" @click="updateInfo(currentHotelInfo.id)">
                                修改信息
                            </a-button>
                        </a-form-item>
                    </div>
                </div>
                <a-divider></a-divider>
                <a-tabs>
                    <a-tab-pane tab="房间信息" key="1" v-if="userInfo.userType=='Client'">
                        <RoomList :rooms="currentHotelInfo.rooms"></RoomList>
                    </a-tab-pane>
                    <a-tab-pane tab="酒店详情" key="2" @click="getHotelDetails()">
                        <span class="room-title">房间详细信息</span>
                        <a-table
                                :columns="columns"
                                :dataSource="currentHotelInfo.rooms"
                                bordered

                        >
                            <span slot="price" slot-scope="text">
                                <span>￥{{ text }}</span>
                            </span>
                            <span slot="action" slot-scope="record" >
                             <a-button type="primary" size="small" @click="changeRoom(record)" v-if="userInfo.userType=='HotelManager'">修改总房间数目</a-button>
                             <a-divider type="vertical"></a-divider>
                             <a-button type="primary" size="small" @click="dealRoom(record)" v-if="userInfo.userType=='HotelManager'">修改剩余房间数目</a-button>
                             <a-divider type="vertical"></a-divider>
                            </span>
                        </a-table>
<!--                        <a-table-->
<!--                                :columns="columns1"-->
<!--                                :dataSource="currentHotelInfo.rooms"-->
<!--                                bordered-->
<!--                                v-if="userInfo.userType!='HotelManager'"-->

<!--                        >-->
<!--                            <span slot="price" slot-scope="text">-->
<!--                                <span>￥{{ text }}</span>-->
<!--                            </span>-->
<!--                            <span slot="action" slot-scope="record" >-->
<!--                             <a-button type="primary" size="small" @click="changeRoom(record)">修改房间数目</a-button>-->
<!--                             <a-divider type="vertical"></a-divider>-->
<!--                            </span>-->
<!--                        </a-table>-->
                    </a-tab-pane>
                        <a-tab-pane tab="用户评论" key="3">
                            <RemarkList :remarks="currentHotelInfo.remarks" :able-to-remark="ableToRemark"></RemarkList>
                        </a-tab-pane>
<!--                    <a-tab-pane tab="酒店详情" key="2" @click="getHotelDetails()" v-if="userInfo.userType!='HotelManager'">-->
<!--                        <span class="room-title">房间详细信息</span>-->
<!--                        <a-table-->
<!--                                :columns="columns1"-->
<!--                                :dataSource="currentHotelInfo.rooms"-->
<!--                                bordered-->

<!--                        >-->
<!--                            <span slot="price" slot-scope="text">-->
<!--                                <span>￥{{ text }}</span>-->
<!--                            </span>-->
<!--                            <span slot="action" slot-scope="record" >-->
<!--                             <a-button type="primary" size="small" @click="changeRoom(record)">修改房间数目</a-button>-->
<!--                             <a-divider type="vertical"></a-divider>-->
<!--                            </span>-->
<!--                        </a-table>-->
<!--                    </a-tab-pane>-->
                </a-tabs>
            </div>
        </a-layout-content>
        <ChangeRoomModal></ChangeRoomModal>
        <DealRoomChangeModal></DealRoomChangeModal>
        <UpdateHotelInfoModal></UpdateHotelInfoModal>
    </a-layout>

</template>
<script>
import { mapGetters, mapActions, mapMutations } from 'vuex'
import RoomList from './components/roomList'
import ChangeRoomModal from "./components/changeRoomModal";
import DealRoomChangeModal from "./components/dealRoomChangeModal";
const columns = [
    {
        title: '房型',
        dataIndex: 'roomType',
        key: 'roomType',
    },
    /*{
        title: '床型',
        dataIndex: 'bedType',
        key: 'bedType',
    },
    {
        title: '早餐',
        dataIndex: 'breakfast',
        key: 'breakfast',
    },
    {
        title: '入住人数',
        key: 'peopleNum',
        dataIndex: 'peopleNum',
    },*/
    {
        title: '房价',
        key: 'price',
        dataIndex: 'price',
        scopedSlots: { customRender: 'price'}
    },
    {
        title: '总房间数',
        key: 'total',
        dataIndex: 'total',
        scopedSlots: { customRender: 'total'}
    },
    {
        title: '现存房间数',
        key: 'curNum',
        dataIndex: 'curNum',
        scopedSlots: { customRender: 'curNum'}
    },
    {
        title: '操作',
        key: 'action',
        scopedSlots: { customRender: 'action' },
    },
];
const columns1 = [
    {
        title: '房型',
        dataIndex: 'roomType',
        key: 'roomType',
    },
//     /*{
//         title: '床型',
//         dataIndex: 'bedType',
//         key: 'bedType',
//     },
//     {
//         title: '早餐',
//         dataIndex: 'breakfast',
//         key: 'breakfast',
//     },
//     {
//         title: '入住人数',
//         key: 'peopleNum',
//         dataIndex: 'peopleNum',
//     },*/
    {
        title: '房价',
        key: 'price',
        dataIndex: 'price',
        scopedSlots: { customRender: 'price'}
    },
    {
        title: '总房间数',
        key: 'total',
        dataIndex: 'total',
        scopedSlots: { customRender: 'total'}
    },
    {
        title: '现存房间数',
        key: 'curNum',
        dataIndex: 'curNum',
        scopedSlots: { customRender: 'curNum'}
    },
    {
        title: '操作',
        key: 'action',
        scopedSlots: { customRender: 'action' },
    },
];
import RemarkList from './components/remarkList'
import UpdateHotelInfoModal from "./components/updateHotelInfoModal";
export default {
    name: 'hotelDetail',
    components: {
        UpdateHotelInfoModal,
        DealRoomChangeModal,
        ChangeRoomModal,
        RoomList,
        RemarkList
    },
    props: {
        rooms: {
            type: Array
        }
    },
    data() {
        return {
            modify: false,
            formLayout: 'horizontal',
            pagination: {},
            columns,
            columns1,
        }
    },

    computed: {
        ...mapGetters([
            'currentHotelInfo',
            'changeRoomVisible',
            'userInfo',
            'dealRoomChangeModalVisible',
            'updateHotelInfoModalVisible',
            'ableToRemark',
        ])
    },
    mounted() {
        this.set_currentHotelId(Number(this.$route.params.hotelId))
        this.getHotelById()
        this.judgeIfAbleToRemark()
        //this.set_changeRoomVisible()
    },
    beforeRouteUpdate(to, from, next) {
        this.set_currentHotelId(Number(to.params.hotelId))
        this.getHotelById()
        this.judgeIfAbleToRemark()
        next()
    },
    methods: {
        ...mapMutations([
            'set_currentHotelId',
            'set_changeRoomVisible',
            'set_changeRoomId',
            'set_dealRoomChangeModalVisible',
            'set_updateHotelInfoModalVisible'

        ]),
        ...mapActions([
            'getHotelById',
            'judgeIfAbleToRemark',
            'updateHotelInfo'
        ]),
        getHotelDetails(){
            this.getHotelById()
            this.judgeIfAbleToRemark()
        },
        changeRoom(record){
            //alert("准备修改了")
            /*this.$router.push({
                path: "./changeRoom",
                query: { id: record.id }
            });*/
            this.set_changeRoomId(record.id);
            this.set_changeRoomVisible(true);
        },
        dealRoom(record){
            this.set_changeRoomId(record.id);
            this.set_dealRoomChangeModalVisible(true);
        },
        updateInfo(){
            //this.set_activeHotelId(currentHotelInfo.id);
            this.set_updateHotelInfoModalVisible(true);
        },
        saveModify() {
            this.form.validateFields((err, values) => {
                //alert("jinlaile");
                if (!err) {
                    const data = {
                        name: this.form.getFieldValue('name'),
                        address: this.form.getFieldValue('address'),
                        bizRegion: this.form.getFieldValue('bizRegion'),
                        hotelStar: this.form.getFieldValue('star'),
                        phoneNum: this.form.getFieldValue('phoneNum'),
                        description: this.form.getFieldValue('description')
                    }
                    this.updateHotelInfo(data).then(()=>{
                        this.modify = false
                    })
                }
            });
        },
        modifyInfo() {
            setTimeout(() => {
                this.form.setFieldsValue({
                    'name': this.currentHotelInfo.userName,
                    'address': this.currentHotelInfo.address,
                    'bizRegion': this.currentHotelInfo.bizRegion,
                    'hotelStar': this.currentHotelInfo.hotelStar,
                    'phoneNum': this.currentHotelInfo.phoneNum,
                    'description': this.currentHotelInfo.description,
                })
            }, 0)
            this.modify = true
        },
        cancelModify() {
            this.modify = false
        },
    }
}
</script>
<style scoped lang="less">
    .hotelDetailCard {
        padding: 50px 50px;
    }
    .hotel-info {
        display: flex;
        align-items: stretch;
        justify-content: flex-start;
        .info{
            padding: 10px 0;
            display: flex;
            flex-direction: column;
            margin-left: 20px;
            .items {
                display: flex;
                align-items: center;
                margin-bottom: 10px;
                .label{
                    margin-right: 10px;
                    font-size: 18px;
                }
                .value {
                    margin-right: 15px
                }
            }
        }
    }
    .room-title{
        position:relative;
        display: block;
        text-align: center;
        align-items: center;
        font-size: 18px;
        margin-bottom: 16px;
    }
</style>
