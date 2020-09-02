<template>
    <div>
        <a-modal
                :visible="managerOrderVisible"
                title="订单详情"
                width="900px"
                :footer="null"
                @cancel="cancel"
        >
            <div class="order-info">
                <div class="info">
                    <div class="items">
                        <span class="label">订单号：</span>
                        <span class="value">{{ activeOrderDetail.id }}</span>
                    </div>
                    <div class="items">
                        <span class="label">客户ID：</span>
                        <span class="value">{{ activeOrderDetail.userId }}</span>
                    </div>
                    <div class="items">
                        <span class="label">酒店ID：</span>
                        <span class="value">{{ activeOrderDetail.userId }}</span>
                    </div>
                    <div class="items">
                        <span class="label">酒店名：</span>
                        <span class="value">{{ activeOrderDetail.hotelName }}</span>
                    </div>
                    <div class="items">
                        <span class="label">入住时间：</span>
                        <span class="value">{{ activeOrderDetail.checkInDate }}</span>
                    </div>
                    <div class="items">
                        <span class="label">离开时间：</span>
                        <span class="value">{{ activeOrderDetail.checkOutDate }}</span>
                    </div>
                    <div class="items">
                        <span class="label">房型：</span>
                        <span class="value">
                            <span v-if="activeOrderDetail.roomType == 'BigBed'">大床房</span>
                            <span v-if="activeOrderDetail.roomType == 'DoubleBed'">双床房</span>
                            <span v-if="activeOrderDetail.roomType == 'Family'">家庭房</span>
                        </span>
                    </div>
                    <div class="items">
                        <span class="label">房间数量：</span>
                        <span class="value">{{ activeOrderDetail.roomNum }}</span>
                    </div>
                    <div class="items">
                        <span class="label">人数：</span>
                        <span class="value">{{ activeOrderDetail.peopleNum }}</span>
                    </div>
                    <div class="items">
                        <span class="label">有无儿童：</span>
                        <span class="value">
                            <span v-if="activeOrderDetail.haveChild == true">有</span>
                            <span v-else>无</span>
                        </span>
                    </div>
                    <div class="items">
                        <span class="label">订单创建日期：</span>
                        <span class="value">{{ activeOrderDetail.createDate }}</span>
                    </div>
                    <div class="items">
                        <span class="label">取消日期：</span>
                        <span class="value">
                            <span v-if="activeOrderDetail.cancellationDate">{{ activeOrderDetail.cancellationDate }}</span>
                            <span v-else>无</span>
                        </span>
                    </div>
                    <div class="items">
                        <span class="label">价钱：</span>
                        <span class="value">￥{{ activeOrderDetail.price }}</span>
                    </div>
                    <div class="items">
                        <span class="label">客户名：</span>
                        <span class="value">{{ activeOrderDetail.clientName }}</span>
                    </div>
                    <div class="items">
                        <span class="label">客户电话：</span>
                        <span class="value">{{ activeOrderDetail.phoneNumber }}</span>
                    </div>
                    <div class="items">
                        <span class="label">订单状态：</span>
                        <a-tag class="value" slot="orderState" color="red" v-if="activeOrderDetail.orderState == '异常订单'">
                            {{ activeOrderDetail.orderState }}
                        </a-tag>
                        <a-tag class="value" slot="orderState" color="blue" v-else>
                            {{ activeOrderDetail.orderState }}
                        </a-tag>
                    </div>
                    <div class="items">
                        <span class="label">Check In时间：</span>
                        <span class="value">
                            <span v-if="activeOrderDetail.checkInTime">{{ activeOrderDetail.checkInTime }}</span>
                            <span v-else>无</span>
                        </span>
                    </div>
                    <div class="items">
                        <span class="label">Check Out时间：</span>
                        <span class="value">
                            <span v-if="activeOrderDetail.checkOutTime">{{ activeOrderDetail.checkOutTime }}</span>
                            <span v-else>无</span>
                        </span>
                    </div>
                    <div class="button-info">
                        <a-button class="button" size="small" type="primary" :disabled="checkIn" @click="checkInOrder(activeOrderDetail.id)">
                            Check In
                        </a-button>
                        <a-button class="button" size="small" type="primary" :disabled="checkOut" @click="checkOutOrder(activeOrderDetail.id)">
                            Check Out
                        </a-button>
                        <a-button class="button" size="small" type="danger" :disabled="orderError" @click="setOrderError(activeOrderDetail.id)">
                            设为错误订单
                        </a-button>
                        <a-button class="button" size="small" type="danger" :disabled="rmOrderError" @click="removeOrderError(activeOrderDetail.id)">
                            延迟入住
                        </a-button>
                    </div>
                </div>
            </div>
        </a-modal>
    </div>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from "vuex";

    export default {
        name: "showOrderDetail",
        components: {},
        computed: {
            ...mapGetters([
                'managerOrderVisible',
                'activeOrderDetail',
            ])
        },
        data() {
            return {
                checkIn: true,
                checkOut: true,
                orderError: true,
                rmOrderError: true,
            }
        },
        watch: {
            'activeOrderDetail':{
                handler:function(){
                    this.checkIn = !(this.activeOrderDetail.orderState == '已预订');
                    this.orderError = !(this.activeOrderDetail.orderState == '已预订');
                    this.checkOut = !(this.activeOrderDetail.orderState == '已入住');
                    this.rmOrderError = !(this.activeOrderDetail.orderState == '异常订单');
                }
            }
        },
        methods: {
            ...mapMutations([
                'set_managerOrderVisible',
                'getOrderDetail',
                'set_activeOrderId',
            ]),
            ...mapActions([
                'getOrderDetail',
                'orderCheckIn',
                'orderCheckOut',
                'errorOrder',
                'rmErrorOrder',
            ]),
            cancel() {
                this.set_managerOrderVisible(false)
                //location.reload()
            },
            setOrderError(id) {
                this.errorOrder(id);
                /*this.checkIn = true;
                this.checkOut = true;
                this.orderError = true;
                this.rmOrderError = false;*/
                //location.reload()
                //this.set_managerOrderVisible(false)
            },
            removeOrderError(id) {
                this.rmErrorOrder(id);
                /*this.checkIn = true;
                this.checkOut = false;
                this.orderError = true;
                this.rmOrderError = true;*/
                //location.reload()
                //this.set_managerOrderVisible(false)
            },
            checkInOrder(id) {
                this.orderCheckIn(id);
                /*this.checkIn = true;
                this.checkOut = false;
                this.orderError = true;
                this.rmOrderError = true;*/
                //location.reload()
                //this.set_managerOrderVisible(false)
            },
            checkOutOrder(id) {
                this.orderCheckOut(id);
                /*this.checkIn = true;
                this.checkOut = true;
                this.orderError = true;
                this.rmOrderError = true;*/
                //location.reload()
                //this.set_managerOrderVisible(false)
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

