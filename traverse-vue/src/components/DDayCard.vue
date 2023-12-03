<script setup>
// 위치 조회
import { onMounted, onUpdated, ref } from 'vue';
import { instanceWithAuth } from '@/util/axiosInstanceSupport';

let isTouring = ref(false);

const dday = ref('');

const planInfo = ref(null);

const requestDDay = async () => {
    instanceWithAuth.get('/plans/me/near').then((response) => {
        // 성공
        if (response.data.dataHeader.successCode === 0) {
            planInfo.value = response.data.dataBody;
            if (planInfo.value !== null) {
                calcDDay();
            }
        }
    });
};

const calcDDay = () => {
    if (planInfo.value === null) {
        isTouring.value = false;
        return;
    }

    let today = new Date();
    let endDay = new Date(planInfo.value.started_at);
    let startDay = new Date(planInfo.value.ended_at);

    console.log(startDay);
    console.log(endDay);

    let resultDay = 0;
    // 여행 시작 전
    if (startDay - today >= 0) {
        // 여행 전
        resultDay = (startDay - today) / (1000 * 60 * 60 * 24);
        isTouring.value = false;
    } else if (endDay - today >= 0) {
        // 여행 중 몇일째
        resultDay = (today - startDay) / (1000 * 60 * 60 * 24);
        isTouring.value = true;
    }

    dday.value = Math.round(resultDay);
};

onMounted(() => {
    requestDDay();
});
</script>

<template>
    <div class="col-6 lg:col-6 xl:col-3">
        <div class="card mb-3 h-full">
            <h5 class="mb-0 mt-0">📅 계획 D-Day</h5>
            <div class="w-full table h-full flex justify-content-center align-items-center">
                <div v-if="planInfo" class="text-900 font-bold text-7xl" style="text-align: center">
                    <div>
                        <h5 class="mb-0 mt-0" v-show="!isTouring">
                            <span style="color: #0076be">{{ planInfo.title }}</span> 까지
                        </h5>
                        <h5 class="mb-0 mt-0" v-show="isTouring">
                            <span style="color: #0076be">{{ planInfo.title }}</span> 중
                        </h5>
                    </div>
                    <p v-show="isTouring">{{ dday }}일 째</p>
                    <p v-show="!isTouring">{{ dday }} 일</p>
                </div>
              <div class="flex align-items-center justify-content-center h-full">
                <h3 v-show="!planInfo" style="text-align: center">
                여행 계획이 없습니다 😥
                </h3>
              </div>
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
