<script setup>
// 위치 조회
import StatisticsChart from '@/components/StatisticsChart.vue';
import { instanceWithAuth } from '@/util/axiosInstanceSupport';
import { onBeforeMount, ref } from 'vue';

const data1 = ref(null);

const getTouringTypes = () => {
  instanceWithAuth.get('/statistics/me/types').then((response) => {
    const results = response.data.dataBody;

    const types = results.map((result) => result.place_type);
    const cnts = results.map((result) => result.cnt);

    data1.value = {
      type: types,
      data: cnts
    };

  });
};

onBeforeMount(() => {
  getTouringTypes();
});
</script>

<template>
  <div class="col-6 lg:col-6 xl:col-3">
    <div class="card mb-3 h-full">
      <h5>🚠 내 여행 타입</h5>
      <TabPanel header="내 여행 타입" v-if="data1">
        <StatisticsChart :data="data1" />
      </TabPanel>
      <div class="flex align-items-center justify-content-center h-full">
        <h3 v-if="!data1" style="text-align: center">여행 계획이 없습니다 😥</h3>
      </div>
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
