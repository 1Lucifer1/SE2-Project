<template>
    <div class="manageUser-wrapper">
        <a-tabs>
            <a-tab-pane tab="酒店管理员管理" key="1">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addManager"><a-icon type="plus" />添加酒店管理人员</a-button>
                </div>
                <a-table
                    :columns="columns"
                    :dataSource="managerList"
                    bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="action" slot-scope="record">
                        <a-popconfirm
                                title="确定想删除该管理员及其管理的酒店吗？"
                                @confirm="subUser(record)"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger">删除管理员</a-button>
                        </a-popconfirm>
                        <a-divider type="vertical"></a-divider>
                        <a-button type="primary" @click="showHotels(record)">查看权限</a-button>
                    </span>

                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="酒店管理" key="2">
<!--                <div style="width: 100%; text-align: right; margin:20px 0">-->
<!--                    <a-button type="primary" @click="addHotel"><a-icon type="plus" />添加酒店</a-button>-->
<!--                    &lt;!&ndash;                    添加酒店按钮触发&ndash;&gt;-->
<!--                </div>-->
                <a-table
                        :columns="columns1"
                        :dataSource="hotelList"
                        bordered
                >
                    <span slot="action" slot-scope="record">
<!--                        <a-button type="primary" size="small" @click="addRoom(record)">录入房间</a-button>-->
<!--                        <a-divider type="vertical"></a-divider>-->
<!--                        <a-button type="info" size="small" @click="showCoupon(record)">优惠策略</a-button>-->
<!--                        <a-divider type="vertical"></a-divider>-->
                        <!--                        //蹦出来的提示框-->

                        <a-divider type="vertical"></a-divider>
                        <a-popconfirm
                                title="确定想删除该酒店吗？"
                                @confirm="deleteHotel(record)"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger" size="small">删除酒店</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="用户管理" key="3">
<!--                <div style="width: 100%; text-align: right; margin:20px 0">-->
<!--                    <a-button type="primary" @click="addManager"><a-icon type="plus" />添加酒店管理人员</a-button>-->
<!--                </div>-->
                <a-table
                        :columns="columns3"
                        :dataSource="clientList"
                        bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="action" slot-scope="record">
                        <a-popconfirm
                                title="确定想删除该用户吗？"
                                @confirm="subUser(record)"
                                okText="确定"
                                cancelText="取消"
                        >
                            <a-button type="danger">删除用户</a-button>
                        </a-popconfirm>
                        <a-divider type="vertical"></a-divider>
                        <a-button type="primary" @click="addClient(record)">信用充值</a-button>
                    </span>

                </a-table>
            </a-tab-pane>
        </a-tabs>
        <AddManagerModal></AddManagerModal>
        <ShowHotelModal></ShowHotelModal>
        <AddClient></AddClient>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddManagerModal from './components/addManagerModal'
import ShowHotelModal from './components/showHotelModal'
//import ManageAddHotelModal from "./components/manageAddHotelModal";
import AddClient from "./components/addClientModal";
const columns = [
    {  
        title: '用户邮箱',
        dataIndex: 'email',
    },
    // {
    //     title: '用户名',
    //     dataIndex: 'userName',
    // },
    // {
    //     title: '用户密码',
    //     dataIndex: 'password',
    // },
    // {
    //     title: '用户手机号',
    //     dataIndex: 'phoneNumber',
    // },
    // {
    //     title: '信用值',
    //     dataIndex: 'credit',
    // },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },
  ];
const columns1 = [
    {
        title: '酒店名',
        dataIndex: 'name',
    },
    {
        title: '商圈',
        dataIndex: 'bizRegion',
    },
    {
        title: '地址',
        dataIndex: 'address',
    },
    {
        title: '酒店星级',
        dataIndex: 'hotelStar'
    },
    {
        title: '评分',
        dataIndex: 'rate',
    },
    {
        title: '简介',
        dataIndex: 'description',
    },
    {
        title: '操作',
        key: 'action',
        scopedSlots: { customRender: 'action' },
    },
];
const columns3 = [
    {
        title: '用户邮箱',
        dataIndex: 'email',
    },
    {
        title: '用户名',
        dataIndex: 'userName',
    },
    // {
    //     title: '用户密码',
    //     dataIndex: 'password',
    // },
    {
        title: '用户手机号',
        dataIndex: 'phoneNumber',
    },
    {
        title: '信用值',
        dataIndex: 'credit',
    },
    {
        title: '操作',
        key: 'action',
        scopedSlots: { customRender: 'action' },
    },
];
export default {
    name: 'manageHotel',
    data(){
        return {
            formLayout: 'horizontal',
            pagination: {},
            columns,
            columns1,
            columns3,
            data: [],
            form: this.$form.createForm(this, { name: 'manageUser' }),
        }
    },
    components: {
        AddManagerModal,
        ShowHotelModal,
        AddClient
    },
    computed: {
        ...mapGetters([
            'addManagerModalVisible',
            'managerList',
            'clientList',
            'showHotelModalVisible',
            'hotelList',
            'addClientModalVisible',
            'currentManagerId'
        ])
    },
    mounted() {
      this.getManagerList(),
      this.getHotelList(),
      this.getClientList()
    },
    methods: {
        ...mapActions([
            'getManagerList',
            'deleteUser',
            'getHotelList',
            'cancelHotel',
            'getClientList',
            'getManagerHotelList'
        ]),
        ...mapMutations([
            'set_addManagerModalVisible',
            'set_showHotelModalVisible',
            'set_manageAddHotelModalVisible',
            'set_addClientModalVisible',
            'set_activeUserId',
            'set_currentManagerId'
        ]),
        addManager(){
            this.set_addManagerModalVisible(true)
        },
        subUser(record){
            this.deleteUser(record.id)
        },
        showHotels(record){
            this.set_currentManagerId(record.id);
            this.set_showHotelModalVisible(true);
            this.getManagerHotelList();
        },
        deleteHotel(record){
            this.cancelHotel(record.id);
        },
        addHotel() {
            this.set_manageAddHotelModalVisible(true)
        },
        addClient(record){
            this.set_addClientModalVisible(true);
            this.set_activeUserId(record.id);
            //alert(record.id);
        }
    }
}
</script>
<style scoped lang="less">
    .manageUser-wrapper {
        padding: 50px;
        .chart {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 20px
        }
    }
</style>
<style lang="less">
    .manageUser-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">
    
</style>