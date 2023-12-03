<script setup>
// 위치 조회
import StatisticsChart from '@/components/StatisticsChart.vue';
import { instanceWithAuth } from '@/util/axiosInstanceSupport';
import { onBeforeMount, ref } from 'vue';

const data2 = ref(null);

const getTouringAreas = () => {
    instanceWithAuth.get('/statistics/me/areas').then((response) => {
        const results = response.data.dataBody;
        if (results.seoul !== null) {
            data2.value = {
                type: ['서울', '부산', '대구', '인천', '광주', '대전', '울산', '세종', '경기', '강원', '충북', '충남', '전북', '전남', '경북', '경남', '제주'],
                data: [
                    results.seoul,
                    results.busan,
                    results.daegu,
                    results.incheon,
                    results.gwangju,
                    results.daejeon,
                    results.ulsan,
                    results.sejong,
                    results.gyeongi,
                    results.gangwon,
                    results.chungbuk,
                    results.chungnam,
                    results.jeonbuk,
                    results.jeonnam,
                    results.gyeongbuk,
                    results.gyeongnam,
                    results.jeju
                ]
            };
        }
    });
};

onBeforeMount(() => {
    getTouringAreas();
});
</script>

<template>
    <div class="col-6 lg:col-6 xl:col-3">
        <div class="card mb-3 h-full">
            <h5>📌 내 여행 장소</h5>
            <TabPanel header="내 여행 장소" v-if="data2">
                <StatisticsChart :data="data2" />
            </TabPanel>
            <div class="flex align-items-center justify-content-center h-full">
                <h3 v-if="!data2" style="text-align: center">여행 계획이 없습니다 😥</h3>
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
