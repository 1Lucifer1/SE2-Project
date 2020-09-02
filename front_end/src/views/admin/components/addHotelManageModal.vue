<template>
    <a-modal
            :visible="addHotelManageModalVisible"
            title="添加酒店管理权限"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >

        <!--                 这里是添加策略模态框区域，请编写表单-->
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="优惠券类型" v-bind="formItemLayout">
                <a-select
                        v-decorator="[
                                        'couponType',
                                        {rules: [{ required: true, message: '请选择优惠券类型'}] }]"
                        @change="changeType"
                >
                    <a-select-option value="1">限时优惠</a-select-option>
                    <a-select-option value="2">多间优惠</a-select-option>
                    <a-select-option value="3">满减优惠</a-select-option>
                    <a-select-option value="4">生日特惠</a-select-option>

                </a-select>
            </a-form-item>
            <a-form-item label="券名" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写券名"
                        v-decorator="['couponName',{ rules:[{ required: true, message:'请输入券名'}] }]"/>
            </a-form-item>
            <a-form-item label="优惠简介" v-bind="formItemLayout">
                <a-input
                        type="textarea"
                        :rows="4"
                        placeholder="请填写优惠简介"
                        v-decorator="['description',{ rules: [{ required: true, message:'请输入优惠简介' }] }]"/>
            </a-form-item>
            <a-form-item label="达标金额">
                <a-input
                        placeholder="请填写达标金额"
                        v-decorator="['target_money',{ rules:[{ required: true, message:'请输入达标金额'}] }]"/>
            </a-form-item>
            <a-form-item label="优惠金额" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写优惠金额"
                        v-decorator="['discount_money',{ rules:[{ required: true, message:'请输入优惠金额'}] }]"/>
            </a-form-item>
            <a-form-item label="折扣" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写折扣"
                        v-decorator="['discount',{ rules:[{ required: true, message:'请输入折扣'}] }]"/>
            </a-form-item>
        </a-form>

    </a-modal>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'

    export default {
        name: 'addHotelManageModal',
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
                'activeHotelId',
                'addHotelManageModalVisible',
            ])
        },
        beforeCreate() {
            // 表单名默认为“form”
            this.form = this.$form.createForm(this, { name: 'addHotelManageModal' });
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_addCouponParams',
                'set_addCouponVisible',
                'set_addHotelManageParams',
                'set_addHotelManageModalVisible',
            ]),
            ...mapActions([
                // addHotelCoupon：添加酒店策略接口
                'addHotelCoupon',
                'addHotelManage'
            ]),
            cancel() {
                this.set_addHotelManageModalVisible(false)
            },
            changeType(v){
                // if( v == '3') {
                //
                // }else{
                //     this.$message.warning('请实现')
                // }
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            // 这里添加接口参数
                            type: Number(this.form.getFieldValue('couponType')),
                            name: this.form.getFieldValue('couponName'),
                            description: this.form.getFieldValue('description'),
                            targetMoney: Number(this.form.getFieldValue('target_money')),
                            discountMoney: Number(this.form.getFieldValue('discount_money')),
                            discount:Number(this.form.getFieldValue('discount')),
                            hotelId: Number(this.activeHotelId),
                            status: 1,
                        };
                        this.set_addHotelManageParams(data);
                        this.addHotelManage(data);
                        this.set_addHotelManageModalVisible(false)
                    }
                });
            },
        }
    }
</script>
