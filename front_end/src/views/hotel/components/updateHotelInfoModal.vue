<template>
    <a-modal
            :visible="updateHotelInfoModalVisible"
            title="修改酒店信息"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >

        <!--                 这里是添加策略模态框区域，请编写表单-->
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="酒店星级" v-bind="formItemLayout">
                <a-select
                        v-decorator="[
                                        'hotelStar',
                                        {rules: [{ required: true, message: '请选择酒店星级'}] }]"
                        @change="changeType"
                >
                    <a-select-option value="One">一星</a-select-option>
                    <a-select-option value="Two">二星</a-select-option>
                    <a-select-option value="Three">三星</a-select-option>
                    <a-select-option value="Four">四星</a-select-option>
                    <a-select-option value="Five">五星</a-select-option>

                </a-select>
            </a-form-item>
            <a-form-item label="酒店名称" v-bind="formItemLayout">
                <a-input
                        placeholder="请输入酒店名称"
                        v-decorator="['name',{ rules:[{ required: true, message:'请输入酒店名称'}] }]"/>
            </a-form-item>
            <a-form-item label="酒店简介" v-bind="formItemLayout">
                <a-input
                        type="textarea"
                        :rows="4"
                        placeholder="请填写酒店简介"
                        v-decorator="['description',{ rules: [{ required: true, message:'请输入酒店简介' }] }]"/>
            </a-form-item>
            <a-form-item label="酒店地址">
                <a-input
                        placeholder="请填写酒店地址"
                        v-decorator="['address',{ rules:[{ required: true, message:'请输入酒店地址'}] }]"/>
            </a-form-item>
            <a-form-item label="酒店商圈" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写酒店商圈"
                        v-decorator="['bizRegion',{ rules:[{ required: true, message:'请输入酒店商圈'}] }]"/>
            </a-form-item>
            <a-form-item label="酒店电话" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写酒店电话"
                        v-decorator="['phoneNum',{ rules:[{ required: true, message:'请输入酒店电话'}] }]"/>
            </a-form-item>
        </a-form>

    </a-modal>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    const moment = require('moment')

    export default {
        name: 'updateHotelInfoModal',
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
                'addCouponVisible',
                'updateHotelInfoModalVisible',
                'currentHotelInfo'
            ])
        },
        beforeCreate() {
            // 表单名默认为“form”
            this.form = this.$form.createForm(this, { name: 'updateHotelInfoModal' });
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_addCouponParams',
                'set_addCouponVisible',
                'set_updateHotelInfoModalVisible',
                'set_activeHotelId'
            ]),
            ...mapActions([
                // addHotelCoupon：添加酒店策略接口
                'addHotelCoupon',
                'getHotelById',
                'updateHotelInfo'
            ]),
            cancel() {
                this.set_updateHotelInfoModalVisible(false)
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
                            hotelStar: this.form.getFieldValue('hotelStar'),
                            name: this.form.getFieldValue('name'),
                            description: this.form.getFieldValue('description'),
                            address: this.form.getFieldValue('address'),
                            bizRegion: this.form.getFieldValue('bizRegion'),
                            phoneNum:Number(this.form.getFieldValue('phoneNum')),
                        };
                        this.updateHotelInfo(data);
                        this.set_updateHotelInfoModalVisible(false)
                    }
                });
            },
        }
    }
</script>
