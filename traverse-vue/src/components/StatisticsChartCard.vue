<script setup>

// 위치 조회
import {computed, ref, watch} from "vue";
import axios from "axios";
import StatisticsChart from "@/components/StatisticsChart.vue";





const data1 = {
  type: ['관광지', '숙박', '레포츠', '음식점', '쇼핑', '문화시설'],
  data: [15, 23, 52, 32, 14, 51]
}

const data2 = {
  type: ['서울', '경기', '전라', '경상', '충청', '강원', '제주'],
  data: [15, 23, 52, 32, 14, 51, 53]
}



</script>


<template>
  <div class="col-6 lg:col-6 xl:col-3">
    <div class="card mb-3 h-full">
      <h5>여행 통계</h5>
      <TabView>
        <!--      여행 장소는 전국 8도로 파싱하면 될 듯  -->
        <TabPanel header="내 여행 타입">
          <StatisticsChart :data="data1"/>
        </TabPanel>
        <TabPanel header="여행 장소">
          <StatisticsChart :data="data2"/>
        </TabPanel>
        <!--        <TabPanel header="여행 타입">-->
        <!--          <StatisticsChart/>-->
        <!--        </TabPanel>-->
      </TabView>
    </div>
  </div>
</template>

<style scoped lang="scss">
.table {
  display: table;
}

.table > div {
  display: table-cell;
  vertical-align: middle;
}

</style>