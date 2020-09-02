<template>
    <div class="info-wrapper">
        <a-tabs>
            <a-tab-pane tab="我的信息" key="1">
<!--                <el-upload-->
<!--                        class="avatar-uploader"-->
<!--                        action="http://i.shuotu.vip/"-->
<!--                        :show-file-list="false"-->
<!--                        :on-success="handleAvatarSuccess"-->
<!--                        :before-upload="beforeAvatarUpload">-->
<!--                    <img v-if="imageUrl" :src="imageUrl" class="avatar">-->
<!--                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>-->
<!--                </el-upload>-->
                <a-form :form="form" style="margin-top: 30px">

                    <a-form-item label="用户名" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <a-input
                                placeholder="请填写用户名"
                                v-decorator="['userName', { rules: [{ required: true, message: '请输入用户名' }] }]"
                                v-if="modify"
                        />
                        <span v-else>{{ userInfo.userName }}</span>
                    </a-form-item>
                    <a-form-item label="邮箱" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ userInfo.email }}</span>
                    </a-form-item>

                    <a-form-item label="生日" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ userInfo.birthday }}</span>
                    </a-form-item>

                    <a-form-item label="手机号" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <a-input
                                placeholder="请填写手机号"
                                v-decorator="['phoneNumber', { rules: [{ required: true, message: '请输入手机号' }] }]"
                                v-if="modify"
                        />
                        <span v-else>{{ userInfo.phoneNumber}}</span>
                    </a-form-item>
                    <a-form-item label="信用值" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ userInfo.credit }}</span>
                    </a-form-item>
                    <a-form-item label="密码" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }" v-if="modify">
                        <a-input
                                placeholder="请输入新密码"
                                v-decorator="['password', { rules: [{ required: true, message: '请输入新密码' }] }]"
                        />
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="modify">
                        <a-button type="primary" @click="saveModify">
                            保存
                        </a-button>
                        <a-button type="default" style="margin-left: 30px" @click="cancelModify">
                            取消
                        </a-button>
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 8, offset: 4 }" v-else>
                        <a-button type="primary" @click="modifyInfo">
                            修改信息
                        </a-button>
                    </a-form-item>
                </a-form>
            </a-tab-pane>
            <a-tab-pane tab="我的订单" key="2" v-if="userInfo.userType=='Client'">
                <a-table
                        :columns="columns"
                        :dataSource="userOrderList"
                        bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="roomType" slot-scope="text">
                        <span v-if="text == 'BigBed'">大床房</span>
                        <span v-if="text == 'DoubleBed'">双床房</span>
                        <span v-if="text == 'Family'">家庭房</span>
                    </span>
                    <a-tag slot="orderState" color="red" slot-scope="text" v-if="text == '异常订单'">
                        {{ text }}
                    </a-tag>
                    <a-tag slot="orderState" color="blue" slot-scope="text" v-else>
                        {{ text }}
                    </a-tag>
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="showOrder(record)">查看</a-button>
                        <a-divider type="vertical" v-if="record.orderState == '已预订'"></a-divider>
                        <a-popconfirm
                                title="你确定撤销该笔订单吗？"
                                @confirm="confirmCancelOrder(record.id)"
                                @cancel="cancelCancelOrder"
                                okText="确定"
                                cancelText="取消"
                                v-if="record.orderState == '已预订'"
                        >
                            <a-button type="danger" size="small">撤销</a-button>
                        </a-popconfirm>
                        
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="我的积分" key="3" v-if="userInfo.userType=='Client'">
                <a-table
                        :columns="columns1"
                        :dataSource="userClientList"
                        bordered
                >
                    <span slot="changeTime" slot-scope="time">
                        {{ time.toString() }}
                    </span>
                    <a-tag slot="orderState" color="red" slot-scope="text" v-if="text == '订单异常'">
                        {{ text }}
                    </a-tag>
                    <a-tag slot="orderState" color="blue" slot-scope="text" v-else>
                        {{ text }}
                    </a-tag>
                </a-table>
            </a-tab-pane>
<!--            <a-tab-pane tab="我的酒店" key="4" v-if="userInfo.userType=='Client'">-->
<!--                <a-table-->
<!--                        :columns="columns2"-->
<!--                        :dataSource="userHotelList"-->
<!--                        bordered-->
<!--                >-->
<!--                    &lt;!&ndash;                    <span slot="changeTime" slot-scope="time">&ndash;&gt;-->
<!--                    &lt;!&ndash;                        {{ time.toString() }}&ndash;&gt;-->
<!--                    &lt;!&ndash;                    </span>&ndash;&gt;-->
<!--                    &lt;!&ndash;                    <a-tag slot="orderState" color="red" slot-scope="text" v-if="text == '订单异常'">&ndash;&gt;-->
<!--                    &lt;!&ndash;                        {{ text }}&ndash;&gt;-->
<!--                    &lt;!&ndash;                    </a-tag>&ndash;&gt;-->
<!--                    &lt;!&ndash;                    <a-tag slot="orderState" color="blue" slot-scope="text" v-else>&ndash;&gt;-->
<!--                    &lt;!&ndash;                        {{ text }}&ndash;&gt;-->
<!--                    &lt;!&ndash;                    </a-tag>&ndash;&gt;-->
<!--                </a-table>-->
<!--            </a-tab-pane>-->
            <ShowOrder></ShowOrder>

        </a-tabs>
    </div>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import ShowOrder from './showOrder'
    const columns = [
        {
            title: '订单号',
            dataIndex: 'id',
        },
        {
            title: '酒店名',
            dataIndex: 'hotelName',
        },
        {
            title: '房型',
            dataIndex: 'roomType',
            scopedSlots: { customRender: 'roomType' }
        },
        {
            title: '入住时间',
            dataIndex: 'checkInDate',
            scopedSlots: { customRender: 'checkInDate' }
        },
        {
            title: '离店时间',
            dataIndex: 'checkOutDate',
            scopedSlots: { customRender: 'checkOutDate' }
        },
        {
            title: '入住人数',
            dataIndex: 'peopleNum',
        },
        {
            title: '房间数',
            dataIndex: 'roomNum',
        },
        {
            title: '房价',
            dataIndex: 'price',
        },
        {
            title: '状态',
            filters: [{ text: '已预订', value: '已预订' },
                { text: '已撤销', value: '已撤销' },
                { text: '已入住', value: '已入住' },
                { text: '已退房', value: '已退房' },
                {text:'异常订单',value:'异常订单'}],
            onFilter: (value, record) => record.orderState.includes(value),
            dataIndex: 'orderState',
            scopedSlots: { customRender: 'orderState' }
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: { customRender: 'action' },
        },

    ];
    const columns1 = [
        {
            title: '记录号',
            dataIndex: 'id',
        },
        {
            title: '变更时间',
            dataIndex: 'changeTime',
            scopedSlots: {customRender: 'changeTime'},
        },
        {
            title: '订单ID',
            dataIndex: 'orderId',
            //scopedSlots: { customRender: 'checkInDate' }
        },
        {
            title: '信用值变化',
            dataIndex: 'creditChange',
            //scopedSlots: { customRender: 'checkOutDate' }
        },
        {
            title: '信用值结果',
            dataIndex: 'creditResult',
        },
        {
            title: '行为',
            filters: [{ text: '订单执行', value: '1' }, { text: '订单异常', value: '2' }, { text: '订单撤销', value: '3' },{text:'充值',value:'4'}],
            onFilter: (value, record) => record.actionType.includes(value),
            dataIndex: 'actionType',
            scopedSlots: { customRender: 'actionType' }
        },

    ];
    const columns2 = [
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
    export default {
        name: 'info',
        data(){
            return {
                imageUrl:'',
                modify: false,
                formLayout: 'horizontal',
                pagination: {},
                columns,
                columns1,
                columns2,
                data: [],
                form: this.$form.createForm(this, { name: 'coordinated' }),
            }
        },
        components: {
            ShowOrder,
        },
        computed: {
            ...mapGetters([
                'userId',
                'userInfo',
                'userOrderList',
                'orderVisible',
                'orderList',
                'clientList',
                'userClientList',
                'userHotelList'
            ])
        },
        async mounted() {
            await this.getUserInfo()
            await this.getUserOrders()
            await this.getUserClient()
            await this.getUserHotels()
        },
        methods: {
            ...mapMutations([
                'set_orderVisible',
                'set_userId',
                'set_userOrderId',
            ]),
            ...mapActions([
                'getUserInfo',
                'getUserOrders',
                'updateUserInfo',
                'cancelOrder',
                'getUserClient',
                'getUserOrderDetail',
                'getUserHotels'
            ]),
            saveModify() {
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const data = {
                            userName: this.form.getFieldValue('userName'),
                            phoneNumber: this.form.getFieldValue('phoneNumber'),
                            password: this.form.getFieldValue('password')
                        }
                        this.updateUserInfo(data).then(()=>{
                            this.modify = false
                        })
                    }
                });
            },
            modifyInfo() {
                setTimeout(() => {
                    this.form.setFieldsValue({
                        'userName': this.userInfo.userName,
                        'phoneNumber': this.userInfo.phoneNumber,
                    })
                }, 0)
                this.modify = true
            },
            cancelModify() {
                this.modify = false
            },
            confirmCancelOrder(orderId){
                this.cancelOrder(orderId)
            },
            cancelCancelOrder() {

            },
            showOrder(record){
                this.set_userOrderId(record.id);
                this.getUserOrderDetail();
                this.set_orderVisible(true);
            },
            handleAvatarSuccess(res, file) {
                this.imageUrl = URL.createObjectURL(file.raw);
            },
            beforeAvatarUpload(file) {
                const isJPG = file.type === 'image/jpeg';
                const isLt2M = file.size / 1024 / 1024 < 2;

                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            }
        }

    }
</script>
<style scoped lang="less">
    .info-wrapper {
        padding: 50px;
        .chart {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 20px
        }
    }
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }
    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }
</style>
<style lang="less">
    .info-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">

</style>