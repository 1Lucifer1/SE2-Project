<template>
    <a-modal
            :visible="addClientModalVisible"
            title="信用充值"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >

        <!--                 这里是添加策略模态框区域，请编写表单-->
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="充值金额">
                <a-input
                        placeholder="金额以100倍添加至信用值"
                        v-decorator="['client',{ rules:[{ required: true, message:'请输入金额'}] }]"/>
            </a-form-item>
        </a-form>

    </a-modal>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'

    export default {
        name: 'addClientModal',
        data() {
            return {
                formItemLayout: {
                    labelCol: {
                        xs: { span: 12 },
                        sm: { span: 6 },
                    },
                    wrapperCol: {
                        xs: { span: 24 },
                        sm: { span: 16 },
                    },
                },
            }
        },
        computed: {
            ...mapGetters([
                'addClientModalVisible',
                'activeRoomId',
                'changeRoomId',
                'activeUserId'
            ])
        },
        beforeCreate() {
            // 表单名默认为“form”
            this.form = this.$form.createForm(this, { name: 'addClientModal' });
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_changeRoomParams',
                'set_addClientModalVisible'
            ]),
            ...mapActions([
                // addHotelCoupon：添加酒店策略接口
                'changeRoomId',
                'changeRoom',
                'addClient',
            ]),
            cancel() {
                this.set_addClientModalVisible(false)
            },
            changeType(v){

            },
            handleSubmit(e) {

                e.preventDefault();

                this.form.validateFieldsAndScroll((err, values) => {

                    if (!err) {

                        //alert(actionType.signal);
                        const data = {
                            // 这里添加接口参数
                            client: Number(this.form.getFieldValue('client')),
                        };
                        //alert(data.roomNum)
                        // this.set_addCouponParams(data);
                        // this.addHotelCoupon(data);
                        this.addClient(data.client);
                        this.set_addClientModalVisible(false);
                    }
                });
            },
        }
    }
</script>
