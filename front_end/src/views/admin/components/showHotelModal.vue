<template>
    <div>
        <a-modal
                :visible="showHotelModalVisible"
                title="管理权限"
                width="900px"
                :footer="null"
                @cancel="cancel"
        >
            <div style="width: 100%; text-align: right; margin:20px 0">
                <a-button type="primary" @click="addHotelManage"><a-icon type="plus" />添加酒店</a-button>
            </div>
            <a-table
                    :columns="columns"
                    :dataSource="managerHotelList"
                    bordered
            >
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="deleteHotel(record)">删除酒店</a-button>
                        <a-divider type="vertical"></a-divider>
                    </span>
            </a-table>
        </a-modal>
        <ManageAddHotelModal></ManageAddHotelModal>
    </div>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from "vuex";
    //import AddHotelManageModal from "./addHotelManageModal";
    import ManageAddHotelModal from "./manageAddHotelModal";
    const columns = [
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
        // {
        //     title: '操作',
        //     key: 'action',
        //     scopedSlots: { customRender: 'action' },
        // },
    ];

    export default {
        name: "showHotelModal",
        components: {ManageAddHotelModal},
        computed: {
            ...mapGetters([
                'showHotelModalVisible',
                'managerHotelList'
            ])
        },
        data() {
            return {
                formLayout: 'horizontal',
                pagination: {},
                columns,

            }
        },
        // watch: {
        //     'activeOrderDetail':{
        //         handler:function(){
        //             this.checkIn = !(this.activeOrderDetail.orderState == '已预订');
        //             this.orderError = !(this.activeOrderDetail.orderState == '已预订');
        //             this.checkOut = !(this.activeOrderDetail.orderState == '已入住');
        //             this.rmOrderError = !(this.activeOrderDetail.orderState == '异常订单');
        //         }
        //     }
        // },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'showHotelModal' });
        },
        async mounted() {
            await this.getHotelList(),
            await this.getManagerHotelList()
            //await this.getAllOrders()
        },
        // updated() {
        //     this.getManagerHotelList()
        // },
        methods: {
            ...mapMutations([
                'set_showHotelModalVisible',
                'set_addHotelManageModalVisible',
                'set_manageAddHotelModalVisible'

            ]),
            ...mapActions([
                'getHotelList',
                'getManagerHotelList'
            ]),
            cancel() {
                this.set_showHotelModalVisible(false)
            },
            addHotelManage(){
                this.set_manageAddHotelModalVisible(true)
            },
            deleteHotel(record){
                this.cancelHotel(record.id);
            },
        }

    }
</script>

<style scoped lang="less">
    .order-info {
        display: flex;
        align-items: stretch;
        justify-content: flex-start;

        .info {
            padding: 10px 0;
            display: flex;
            flex-direction: column;
            margin-left: 20px;

            .items {
                display: flex;
                align-items: center;
                margin-bottom: 10px;

                .label {
                    text-align: right;
                    margin-right: 10px;
                    font-size: 16px;
                    width: 150px;
                }

                .value {
                    margin-right: 15px;
                    font-size: 16px;
                }
            }

            .button-info {
                align-items: center;
                position: relative;
                display: block;
                .button {
                    margin-right: 50px;
                    font-size: 16px;
                    width: 150px;
                    height: 30px;
                }
            }

        }
    }

</style>

