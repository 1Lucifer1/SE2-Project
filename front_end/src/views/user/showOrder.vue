<template>
    <div>
        <a-modal
                :visible="orderVisible"
                title="订单详情"
                width="900px"
                :footer="null"
                @cancel="cancel"
        >
            <div class="order-info">
                <div class="info">
                    <div class="items">
                        <span class="label">订单号：</span>
                        <span class="value">{{ userOrderDetail.id }}</span>
                    </div>
                    <div class="items">
                        <span class="label">客户ID：</span>
                        <span class="value">{{ userOrderDetail.userId }}</span>
                    </div>
                    <div class="items">
                        <span class="label">酒店ID：</span>
                        <span class="value">{{ userOrderDetail.userId }}</span>
                    </div>
                    <div class="items">
                        <span class="label">酒店名：</span>
                        <span class="value">{{ userOrderDetail.hotelName }}</span>
                    </div>
                    <div class="items">
                        <span class="label">入住时间：</span>
                        <span class="value">{{ userOrderDetail.checkInDate }}</span>
                    </div>
                    <div class="items">
                        <span class="label">离开时间：</span>
                        <span class="value">{{ userOrderDetail.checkOutDate }}</span>
                    </div>
                    <div class="items">
                        <span class="label">房型：</span>
                        <span class="value">
                            <span v-if="userOrderDetail.roomType == 'BigBed'">大床房</span>
                            <span v-if="userOrderDetail.roomType == 'DoubleBed'">双床房</span>
                            <span v-if="userOrderDetail.roomType == 'Family'">家庭房</span>
                        </span>
                    </div>
                    <div class="items">
                        <span class="label">房间数量：</span>
                        <span class="value">{{ userOrderDetail.roomNum }}</span>
                    </div>
                    <div class="items">
                        <span class="label">人数：</span>
                        <span class="value">{{ userOrderDetail.peopleNum }}</span>
                    </div>
                    <div class="items">
                        <span class="label">有无儿童：</span>
                        <span class="value">
                            <span v-if="userOrderDetail.haveChild == true">有</span>
                            <span v-else>无</span>
                        </span>
                    </div>
                    <div class="items">
                        <span class="label">订单创建日期：</span>
                        <span class="value">{{ userOrderDetail.createDate }}</span>
                    </div>
                    <div class="items">
                        <span class="label">取消日期：</span>
                        <span class="value">
                            <span v-if="userOrderDetail.cancellationDate">{{ userOrderDetail.cancellationDate }}</span>
                            <span v-else>无</span>
                        </span>
                    </div>
                    <div class="items">
                        <span class="label">价钱：</span>
                        <span class="value">￥{{ userOrderDetail.price }}</span>
                    </div>
                    <div class="items">
                        <span class="label">客户名：</span>
                        <span class="value">{{ userOrderDetail.clientName }}</span>
                    </div>
                    <div class="items">
                        <span class="label">客户电话：</span>
                        <span class="value">{{ userOrderDetail.phoneNumber }}</span>
                    </div>
                    <div class="items">
                        <span class="label">订单状态：</span>
                        <a-tag class="value" slot="orderState" color="red" v-if="userOrderDetail.orderState == '异常订单'">
                            {{ userOrderDetail.orderState }}
                        </a-tag>
                        <a-tag class="value" slot="orderState" color="blue" v-else>
                            {{ userOrderDetail.orderState }}
                        </a-tag>
                    </div>
                </div>
            </div>
        </a-modal>
    </div>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    //import AddCoupon from './addCoupon'
    export default {
        name: 'showOrder',
        data() {
            return {

            }
        },
        // components: {
        //     AddCoupon,
        // },
        computed: {
            ...mapGetters([
                'orderVisible',
                'userOrderDetail',
            ])
        },
        methods: {
            ...mapMutations([
                //'set_addCouponVisible',
                'set_orderVisible',
            ]),
            ...mapActions([
                'getUserOrderDetail',
            ]),
            cancel() {
                this.set_orderVisible(false)
            },
        },
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