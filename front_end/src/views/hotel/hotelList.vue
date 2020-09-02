<template>

  <div class="hotelList">
      <el-carousel :interval="4000" type="card" height="200px">
          <el-carousel-item>
              <h3 class="medium">
                  <img src="http://i.shuotu.vip/n=FBC5178405ED6B01A2EEFF36E1CF636C.jpg/p=_qYqlXON[4@m5" style="width:100%;"/>
              </h3>
          </el-carousel-item>
          <el-carousel-item>
              <h3 class="medium">
                  <img src="http://i.shuotu.vip/n=987B0CBEDEFE2752D13E5BF0EF1DF7F8.jpg/p=w2WrUBVAXs:@2" style="width:100%;"/>
              </h3>
          </el-carousel-item>
          <el-carousel-item>
              <h3 class="medium">
                  <img src="http://i.shuotu.vip/n=E443792B0DB66DB159469C479251798F.jpg/p=8jZkbq^X4h]gQ" style="width:100%;"/>
              </h3>
          </el-carousel-item>
      </el-carousel>
      <p style="font-size: 50px; margin-top:80px;">精选房源 安心入住</p>
      <p style="font-size: 50px;color:#88b0c0">把全世界带到您的眼前</p>



                                     <!--hotel_manage视角-->
      <div  v-if="userInfo.userType==='HotelManager'">
          <a-layout>
              <a-layout-content style="min-width: 100%;">
                  <a-spin :spinning="hotelListLoading">
                      <div class="card-wrapper">
                          <HotelCard :hotel="item" v-for="item in managerHotelList" :key="item.index" @click.native="jumpToDetails(item.id)"></HotelCard>
                          <div v-for="item in emptyBox" :key="item.name" class="emptyBox ant-col-xs-7 ant-col-lg-5 ant-col-xxl-3">
                          </div>
                          <a-pagination showQuickJumper :total="managerHotelList.totalElements" :defaultCurrent="1" @change="pageChange"></a-pagination>
                      </div>
                  </a-spin>
              </a-layout-content>
          </a-layout>
      </div>

                                      <!--custom视角-->
      <div v-if="userInfo.userType!=='HotelManager'">
          <el-button @click="dialog = true" style="margin-left: 16px;" round>
              定制个人显示列表
          </el-button>
                <el-drawer
                title="定制你的个人酒店列表"
                :before-close="handleClose"
                :visible.sync="dialog"
                direction="ltr"
                :with-header="false"
                custom-class="demo-drawer"
                style="width:60%"
                ref="drawer"
            >
                    <div style="position: relative; top: 30%; left: 10%">
                <a-form :form="form" :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }"  >
                    <a-form-item label="酒店名称">
                        <a-input
                                v-decorator="['name', { rules: [{ required: false }] }]"
                                placeholder="不限"
                        />
                    </a-form-item>
                    <a-form-item label="地址">
                        <a-input
                                v-decorator="['address', { rules: [{ required: false }] }]"
                                placeholder="不限"
                        />
                    </a-form-item>
                    <a-form-item label="商圈">
                        <a-input
                                v-decorator="['bizRegion', { rules: [{ required: false }] }]"
                                placeholder="不限"
                        />
                    </a-form-item>

                    <!--<a-form-item label="星级">-->
                        <!--<a-select-->
                            <!--v-decorator="[-->
                              <!--'star',-->
                              <!--{ rules: [{ required: false }] },-->
                            <!--]"-->
                            <!--@change="handleSelectChange"-->
                            <!--placeholder="不限"-->
                        <!--&gt;-->
                            <!--<a-select-option value="1">-->
                                <!--一星级-->
                            <!--</a-select-option>-->
                            <!--<a-select-option value="2">-->
                                <!--二星级-->
                            <!--</a-select-option>-->
                            <!--<a-select-option value="3">-->
                                <!--三星级-->
                            <!--</a-select-option>-->
                            <!--<a-select-option value="4">-->
                                <!--四星级-->
                            <!--</a-select-option>-->
                            <!--<a-select-option value="5">-->
                                <!--五星级-->
                            <!--</a-select-option>-->
                        <!--</a-select>-->
                    <!--</a-form-item>-->
                    <a-form-item>
                        <a-switch checked-children="列表" un-checked-children="卡片" @change="onChange" />
                    </a-form-item>
                </a-form>
                    </div>
            </el-drawer>
          <div style="margin-top: 30px;" v-if="!isTableDisplay" >
            <a-layout>
                <a-layout-content style="min-width: 100%;">
                  <a-spin :spinning="hotelListLoading">

                    <div class="card-wrapper" v-if="userInfo.userType!='HotelManager'">
                        <div v-if="isSearching">
                            <a-pagination showQuickJumper :total="hotelList.totalElements" :defaultCurrent="1" @change="pageChange"></a-pagination>
                            <HotelCard :hotel="item" v-for="item in displayHotelList" :key="item.index" @click.native="jumpToDetails(item.id)"></HotelCard>
                            <div v-for="item in emptyBox" :key="item.name" class="emptyBox ant-col-xs-7 ant-col-lg-5 ant-col-xxl-3">
                            </div>
                        </div>
                        <div v-if="!isSearching">
                            <a-pagination showQuickJumper :total="hotelList.totalElements" :defaultCurrent="1" @change="pageChange"></a-pagination>
                            <HotelCard :hotel="item" v-for="item in hotelList" :key="item.index" @click.native="jumpToDetails(item.id)"></HotelCard>
                            <div v-for="item in emptyBox" :key="item.name" class="emptyBox ant-col-xs-7 ant-col-lg-5 ant-col-xxl-3">
                            </div>

                        </div>
                    </div>
                  </a-spin>
              </a-layout-content>
            </a-layout>
          </div>
          <div style="margin-top: 30px;" v-if="isTableDisplay" >
              <div v-if="!isSearching">
                <a-table :columns="columns" :data-source="hotelList" @change="handleChange">
                      <span slot="name" slot-scope="text, record" @click="jumpToDetails(record.id)">
                        <a href="javascript:">{{text}}</a>
                    </span>
                </a-table>
              </div>
              <div v-if="isSearching">
                  <a-table :columns="columns" :data-source="displayHotelList" @change="handleChange">
                      <span slot="name" slot-scope="text, record" @click="jumpToDetails(record.id)">
                        <a href="javascript:">{{text}}</a>
                    </span>
                  </a-table>
              </div>
          </div>
      </div>

  </div>
</template>
<script>
import HotelCard from './components/hotelCard'
import { mapGetters, mapActions, mapMutations } from 'vuex'

export default {
  name: 'home',
  components: {
    HotelCard
  },
  data(){
    return{
        formLayout: 'horizontal',
        emptyBox: [{ name: 'box1' }, { name: 'box2'}, {name: 'box3'}],
        isBoard:true,
        dialog: false,
        loading: false,
        formLabelWidth: '80px',
        timer: null,
        form: this.$form.createForm(this, { name: 'coordinated' }),
        sortedInfo: null,
        filteredInfo: null,
    };
    },
  async mounted() {
    //alert(userInfo.id),
    await this.getHotelList(),
    await this.getManagerHotelList()
  },
  computed: {
      columns() {
          let { sortedInfo, filteredInfo } = this;
          sortedInfo = sortedInfo || {};
          filteredInfo = filteredInfo || {};
          const columns = [
              {
                  title: '酒店名称',
                  dataIndex: 'name',
                  key: 'name',
                  scopedSlots: { customRender: 'name'},
                  sorter: (a, b) => a.name.length - b.name.length,
                  sortOrder: sortedInfo.columnKey === 'name' && sortedInfo.order,
                  ellipsis: true,
              },
              {
                  title: '评分',
                  dataIndex: 'rate',
                  key: 'rate',
                  sorter: (a, b) => a.rate - b.rate,
                  sortOrder: sortedInfo.columnKey === 'rate' && sortedInfo.order,
              },
              {
                  title: '星级',
                  dataIndex: 'hotelStar',
                  key: 'hotelStar',
                  filters: [
                      {
                          text: 'One',
                          value: 'One',
                      },
                      {
                          text: 'Two',
                          value: 'Two',
                      },
                      {
                          text: 'Three',
                          value: 'Three',
                      },
                      {
                          text: 'Four',
                          value: 'Four',
                      },
                      {
                          text: 'Five',
                          value: 'Five',
                      },
                  ],
                  onFilter: (value, record) => record.hotelStar.includes(value),
                  // sorter: (a, b) => a.hotelStar - b.hotelStar,
                  // sortOrder: sortedInfo.columnKey === 'hotelStar' && sortedInfo.order,
              },
              {
                  title: '地址',
                  dataIndex: 'address',
                  key: 'address',
              },
              {
                  title: '描述',
                  dataIndex: 'description',
                  key: 'description',
              },

          ];
          return columns;
      },
    ...mapGetters([
      'hotelList',
      'hotelListLoading',
      'displayHotelList',
      'isSearching',
      'hotelListLoading',
      'managerHotelList',
        'userInfo',
        'isTableDisplay'
    ])
  },
  methods: {
      ...mapMutations([
          'set_hotelListParams',
          'set_hotelListLoading',
          'set_displayHotelList',
          'set_hotelListLoading',
          'set_activeHotelId',
          'set_isTableDisplay',
          'set_isSearchingtParams'
      ]),
      ...mapActions([
          'getHotelList',
          'getdisplayHotelList',
          'getHotelList',
          'getManagerHotelList'
      ]),
      handleChange(pagination, filters, sorter) {
          // console.log('Various parameters', pagination, sorter);
          this.sortedInfo = sorter;
      },
      pageChange(page, pageSize) {
          const data = {
              pageNo: page - 1
          }
          this.set_hotelListParams(data)
          this.set_hotelListLoading(true)
          this.getHotelList()
          this.getManagerHotelList()
      },
      jumpToDetails(id) {
          this.set_activeHotelId(id);
          //alert(id);
          this.$router.push({name: 'hotelDetail', params: {hotelId: id}})
      },
      onChange(checked) {
          this.set_isTableDisplay(checked)
      },
      handleClose(done) {
          this.dialog = false;
          var name_data = null;
          var address_data = null;
          var bizRegion_data = null;
          if(this.form.getFieldValue('name') || this.form.getFieldValue('address') || this.form.getFieldValue('bizRegion')) {
              if (this.form.getFieldValue('name') !== "") {
                  name_data = this.form.getFieldValue('name')
              }
              if (this.form.getFieldValue('address') !== "") {
                  address_data = this.form.getFieldValue('address')
              }
              if (this.form.getFieldValue('bizRegion') !== "") {
                  bizRegion_data = this.form.getFieldValue('bizRegion')
              }
              const data = {
                  name: this.form.getFieldValue('name'),
                  description: null,
                  address: this.form.getFieldValue('address'),
                  bizRegion: this.form.getFieldValue('bizRegion'),
                  phoneNum: null,
                  hotelStar: null,
                  roomType: null,
                  maxPrice: null,
                  minPrice: null,
                  roomNum: null,
                  checkInDate: null,
                  checkOutDate: null,
                  minRate: null,
                  maxRate: null,
              };
              this.set_hotelListLoading(true);
              this.getdisplayHotelList(data);
          }
          else{
              this.set_isSearchingtParams(false);
          }
      },
      cancelForm() {
          this.loading = false;
          this.dialog = false;
          clearTimeout(this.timer);
      },
  }
}
</script>
<style scoped lang="less">
  .hotelList {
    border:0;
    text-align: center;
    padding: 80px 0;
    .emptyBox {
      height: 0;
      margin: 10px 10px
    }
    .card-wrapper{
      display: flex;
      justify-content: space-around;
      flex-wrap: wrap;
      flex-grow: 3;
      min-height: 800px
    }
    .card-wrapper .card-item {
      margin: 30px;
      position: relative;
      height: 200px;
    }
      .el-carousel__item h3 {
          color: #475669;
          font-size: 14px;
          opacity: 1;
          line-height: 200px;
          margin: 0;
      }
  }
</style>