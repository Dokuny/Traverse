<script setup>
import WeatherCard from '@/components/WeatherCard.vue';
import DdayCard from '@/components/DDayCard.vue';
import YoutubeCarousel from '@/components/YoutubeCarousel.vue';
import TypeStatisticsChartCard from '@/components/TypeStatisticsChartCard.vue';
import PlaceStatisticsChartCard from '@/components/PlaceStatisticsChartCard.vue';
import { ref } from "vue";

const trasportInfoToggle = ref(true);

</script>

<template>
    <div class="grid">
        <WeatherCard />
        <DdayCard />
        <TypeStatisticsChartCard />
        <PlaceStatisticsChartCard />

        <YoutubeCarousel />
        <div class="col-12">
            <div class="card">
                <div class="flex align-content-center">
                  <h5 class="mb-4">🚌 교통편 조회</h5> <InputSwitch v-model="trasportInfoToggle" class="ml-3"/>
                </div>
                <iframe v-show="trasportInfoToggle" class="border" src="https://www.letskorail.com/ebizprd/EbizPrdTicketpr21100W_pr21110.do" width="100%" height="800px" frameborder="0" />
                 <iframe v-show="!trasportInfoToggle" class="border" src="https://txbus.t-money.co.kr/runinf/runInf.do" width="100%" height="800px" frameborder="0" />
            </div>

        </div>
    </div>
</template>

<style lang="scss" scoped>
.border {
    border-radius: 20px;
}
</style>
