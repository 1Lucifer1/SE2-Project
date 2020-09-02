<template>
    <a-modal
        :visible="addRemarkModalVisible"
        title="评论"
        cancelText="取消"
        okText="提交"
        @cancel="cancelRemark"
        @ok="handleSubmit"
    >
        <a-form :form="form">
            <a-form-item v-bind="formItemLayout" label="评论">
                <a-textarea :rows="4"
                    v-decorator="[
                        'content',
                        { rules: [{required: true, message: 'qwq你倒是说话呀', }] }
                    ]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="打分">
                <a-select
                    v-decorator="[
                        'star',
                        { rules: [{ required: true}] },
                    ]"
                    placeholder="打个分吧"
                >
                    <a-select-option :value="1">
                        1
                    </a-select-option>
                    <a-select-option :value="2">
                        2
                    </a-select-option>
                     <a-select-option :value="3">
                        3
                    </a-select-option>
                    <a-select-option :value="4">
                        4
                    </a-select-option>
                    <a-select-option :value="5">
                        5
                    </a-select-option>
                </a-select>
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
const moment = require('moment')
export default {
    name: 'orderModal',
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
            'addRemarkModalVisible',
            'currentOrderRoom',
            'currentHotelId',
            'currentHotelInfo',
            'userId',
            'userInfo'
        ]),
        
    },
    beforeCreate() {
        this.form = this.$form.createForm(this, { name: 'orderModal' });
    },
    methods: {
        ...mapMutations([
            'set_addRemarkModalVisible'
        ]),
        ...mapActions([
            'addRemark'
        ]),
        cancelRemark() {
            this.set_addRemarkModalVisible(false)
        },
        confirmRemark() {

        },

        handleSubmit(e) {
            e.preventDefault();
            this.form.validateFieldsAndScroll((err, values) => {
                if (!err) {
                    const data = {
                        userId: this.userId,
                        hotelId: this.currentHotelId,
                        content: this.form.getFieldValue('content'),
                        star: this.form.getFieldValue('star'),
                        remarkTime: moment().format('YYYY-MM-DD HH:mm:ss'),
                        userName: this.userInfo.userName
                    }
                    this.addRemark(data)
                    this.set_addRemarkModalVisible(false)
                }
            });
        },
    },
}
</script>