<template>
    <div>
        <div>
            <a-button style="float: right" type="primary" @click="add" v-if="ableToRemark">
                评论
            </a-button>
        </div>
        <br>
        <div>
            <a-list
                    class="comment-list"
                    :header="`${remarks.length} replies`"
                    item-layout="horizontal"
                    :data-source="remarks"
            >
                <a-list-item slot="renderItem" slot-scope="item">
                    <a-comment :author="item.userName" :avatar="'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png'">
                        <p slot="content">
                            {{ item.content }}
                        </p>
                        <a-tooltip slot="datetime" :title="$moment(item.remarkTime,'YYYY-MM-DD HH:mm:ss').format('YYYY-DD-MM')">
                            <span>{{ $moment(item.remarkTime,'YYYY-MM-DD HH:mm:ss').fromNow() }}</span>
                        </a-tooltip>
                        <a-rate style="font-size: 15px" :value="item.star" disabled/>
                    </a-comment>
                </a-list-item>
            </a-list>
        </div>
        <addRemarkModal></addRemarkModal>
    </div>

</template>
<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import addRemarkModal from './addRemarkModal'
    export default {
        name:'remarkList',
        props: {
            remarks: {
                type: Array
            },
            ableToRemark:Boolean
        },
        components: {
            addRemarkModal
        },
        computed: {
            ...mapGetters([
                'addRemarkModalVisible',
                'userInfo'
            ])
        },
        monuted() {

        },
        methods: {
            ...mapMutations([
                'set_addRemarkModalVisible',
            ]),
            ...mapActions([

            ]),
            add(remark) {
                console.log("PRESS")
                this.set_addRemarkModalVisible(true)
            }
        }
    }
</script>