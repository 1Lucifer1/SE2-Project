<template>
    <a-modal
            :visible="dealRoomChangeModalVisible"
            title="修改现有房间"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >

        <!--                 这里是添加策略模态框区域，请编写表单-->
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="处理类型" v-bind="formItemLayout">
                <a-select
                        v-decorator="[
                                        'actionType',
                                        {rules: [{ required: true, message: '请选择处理类型'}] }]"
                >
                    <a-select-option value="-1">增加房间</a-select-option>
                    <a-select-option value="1">减少房间</a-select-option>

                </a-select>
            </a-form-item>
            <a-form-item label="可用房间数目">
                <a-input
                        placeholder="请填写房间数目"
                        v-decorator="['roomNumber',{ rules:[{ required: true, message:'请输入达标金额'}] }]"/>
            </a-form-item>
        </a-form>

    </a-modal>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'

    export default {
        name: 'dealRoomChangeModal',
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
                'changeRoomVisible',
                'activeRoomId',
                'changeRoomId',
                'dealRoomChangeModalVisible',
            ])
        },
        beforeCreate() {
            // 表单名默认为“form”
            this.form = this.$form.createForm(this, { name: 'dealRoomChangeModal' });
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_changeRoomParams',
                'set_dealRoomChangeModalVisible'
            ]),
            ...mapActions([
                // addHotelCoupon：添加酒店策略接口
                'changeRoomId',
                'changeRoom',
                'dealRoom',
            ]),
            cancel() {
                this.set_dealRoomChangeModalVisible(false)
            },
            changeType(v){

            },
            handleSubmit(e) {

                e.preventDefault();

                this.form.validateFieldsAndScroll((err, values) => {

                    if (!err) {
                        const actionType = {
                            signal: Number(this.form.getFieldValue('actionType')),
                        };
                        //alert(actionType.signal);
                        const data = {
                            // 这里添加接口参数
                            roomNum: Number(this.form.getFieldValue('roomNumber'))*actionType.signal,
                        };
                        //alert(data.roomNum)
                        // this.set_addCouponParams(data);
                        // this.addHotelCoupon(data);
                        this.dealRoom(data.roomNum);
                        this.set_dealRoomChangeModalVisible(false);
                    }
                });
            },
        }
    }
</script>
