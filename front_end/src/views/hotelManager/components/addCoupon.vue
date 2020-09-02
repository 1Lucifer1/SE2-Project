<template>
    <a-modal
            :visible="addCouponVisible"
            title="添加优惠策略"
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
                                    <a-select-option value="1">生日优惠</a-select-option>
                                    <a-select-option value="2">多间优惠</a-select-option>
                                    <a-select-option value="3">满减优惠</a-select-option>
                                    <a-select-option value="4">限时特惠</a-select-option>

                                </a-select>
                            </a-form-item>
                            <a-form-item label="券名" v-bind="formItemLayout" >
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
                            <a-form-item v-bind="formItemLayout" label="有效日期" v-if="type==='4'">
                                <a-range-picker
                                    :show-time="{ format: 'HH:mm' }"
                                    format="YYYY-MM-DD HH:mm"
                                    :placeholder="['开始日期','结束日期']"
                                    @change="changeDate"
                                    v-decorator="[
                                            'date',
                                            {
                                                rules: [{ required: true, message: '请选择起止时间' }]
                                            }
                                        ]"
                                />
                            </a-form-item>
                            <a-form-item label="达标金额" v-if="type==='3'">
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
                                <a-select
                                        v-decorator="[
                    'discount',
                    { rules: [{ required: true, message: '请选择折扣' }] }]"
                                >
                                    <a-select-option value="1">无折扣</a-select-option>
                                    <a-select-option value="0.9">九折 </a-select-option>
                                    <a-select-option value="0.8">八折</a-select-option>
                                    <a-select-option value="0.7">七折</a-select-option>
                                    <a-select-option value="0.6">六折 </a-select-option>
                                    <a-select-option value="0.5">五折</a-select-option>
                                    <a-select-option value="0.4">四折 </a-select-option>
                                    <a-select-option value="0.3">三折</a-select-option>
                                    <a-select-option value="0.2">二折</a-select-option>
                                    <a-select-option value="0.1">一折 </a-select-option>
                                </a-select>
                            </a-form-item>
                        </a-form>

    </a-modal>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    const moment = require('moment')

    export default {
        name: 'addCouponModal',
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
                type:0
            }
        },
        computed: {
            ...mapGetters([
                'activeHotelId',
                'addCouponVisible',
            ])
        },
        beforeCreate() {
            // 表单名默认为“form”
            this.form = this.$form.createForm(this, { name: 'addCouponModal' });
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_addCouponParams',
                'set_addCouponVisible'
            ]),
            ...mapActions([
                // addHotelCoupon：添加酒店策略接口
                'addHotelCoupon'
            ]),
            cancel() {
                this.set_addCouponVisible(false)
            },
            changeType(v){
                this.type = v
            },
            changeDate(v){

            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        if(this.type === '4'){
                            const data = {
                                // 这里添加接口参数
                                type: Number(this.form.getFieldValue('couponType')),
                                name: this.form.getFieldValue('couponName'),
                                description: this.form.getFieldValue('description'),
                                targetMoney: 0,
                                discountMoney: Number(this.form.getFieldValue('discount_money')),
                                discount:Number(this.form.getFieldValue('discount')),
                                hotelId: Number(this.activeHotelId),
                                startTime: moment(this.form.getFieldValue('date')[0]).format('YYYY-MM-DD hh:mm:ss'),
                                endTime: moment(this.form.getFieldValue('date')[1]).format('YYYY-MM-DD hh:mm:ss'),
                                status: 1,
                            };
                            this.set_addCouponParams(data);
                            this.addHotelCoupon(data);
                            this.set_addCouponVisible(false)
                        }
                        else if(this.type === '3') {
                            const data = {
                                // 这里添加接口参数
                                type: Number(this.form.getFieldValue('couponType')),
                                name: this.form.getFieldValue('couponName'),
                                description: this.form.getFieldValue('description'),
                                targetMoney: Number(this.form.getFieldValue('target_money')),
                                discountMoney: Number(this.form.getFieldValue('discount_money')),
                                discount: Number(this.form.getFieldValue('discount')),
                                hotelId: Number(this.activeHotelId),
                                startTime: "",
                                endTime: "",
                                status: 1,
                            };
                            this.set_addCouponParams(data);
                            this.addHotelCoupon(data);
                            this.set_addCouponVisible(false)
                        }
                        else{
                            const data = {
                                // 这里添加接口参数
                                type: Number(this.form.getFieldValue('couponType')),
                                name: this.form.getFieldValue('couponName'),
                                description: this.form.getFieldValue('description'),
                                targetMoney: 0,
                                discountMoney: Number(this.form.getFieldValue('discount_money')),
                                discount: Number(this.form.getFieldValue('discount')),
                                hotelId: Number(this.activeHotelId),
                                startTime: "",
                                endTime: "",
                                status: 1,
                            };
                            this.set_addCouponParams(data);
                            this.addHotelCoupon(data);
                            this.set_addCouponVisible(false)
                        }

                    }
                });
            },
        }
    }
</script>
