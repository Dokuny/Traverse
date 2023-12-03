<script setup>
// 위치 조회
import { onMounted, onUpdated, ref, watch } from 'vue';
import axios from 'axios';
import { instance } from "@/util/axiosInstanceSupport";

const weather = ref({
    district: '',
    temparature: '',
    title: ''
});

const isSnow = ref(false);
const isSun = ref(false);
const isRain = ref(false);
const isCloud = ref(false);

const isLoaded = ref(false);

const requestWeatherInfo = async (latitude, longitude) => {
    try {
        await instance
            .get("/weather/now", {
                params: {
                    lat: latitude,
                    lng: longitude
                }
            })
            .then((response) => {
                const data = response.data;

                weather.value.district = data.dataBody.district;
                weather.value.temparature = data.dataBody.temparature;
                weather.value.title = data.dataBody.weather;

                switch (weather.value.title) {
                    case '맑음':
                        isSun.value = true;
                        break;
                    case '구름':
                        isCloud.value = true;
                        break;
                    case '비':
                        isRain.value = true;
                        break;
                    case '눈':
                        isSnow.value = true;
                        break;
                }
                isLoaded.value = true;
            });
    } catch (error) {
        /* empty */
    }
};

const doSomethingSuccess = (position) => {
    let latitude = position.coords.latitude;
    let longitude = position.coords.longitude;

    requestWeatherInfo(latitude, longitude);
};

const doSomethingError = (error) => {};

onMounted(() => {
    navigator.geolocation.getCurrentPosition(doSomethingSuccess, doSomethingError);
});
</script>

<template>
    <div class="col-6 lg:col-6 xl:col-3">
        <div class="card mb-3 h-full">
            <h5 class="mb-0" style="height:10%">🎈  현재 날씨</h5>
            <div v-show="!isLoaded" class="flex-column h-full">
              <ProgressSpinner  style="width: 100%; height: 100%"  strokeWidth="8" fill="var(--surface-ground)"
                               animationDuration="0.8s"/>
            </div>
            <div v-show="isLoaded" class="flex-column justify-content-center align-items-center" style="height:90%">
                <div style="text-align: center; height: 70%; width:100%" class="flex justify-content-center align-items-center">
                    <div
                        :class="{
                            sun: isSun,
                            snow: isSnow,
                            cloud: isCloud,
                            rain: isRain
                        }"
                        alt="Image"

                    />
                </div>
                <div class="flex-column" style="justify-content: space-between;">
                    <div class="text-900 font-bold text-4xl w-full mt-2 mb-1" style="text-align: center;">{{ weather.temparature }} °C</div>
                    <div class="flex align-items-center">
                        <i class="pi pi-map-marker" />
                        <div class="text-500 font-medium w-full" style="text-align: center">
                            {{ weather.district }}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped lang="scss">
.sun {
    content: url('/weather/sun.png');
    width: 80%;
    height: 90%;
}

.rain {
    content: url('/weather/rain.png');
    width: 80%;
    height: 90%;
}

.cloud {
    content: url('/weather/cloud.png');
    width: 80%;
    height: 90%;
}

.snow {
    content: url('/weather/snow.png');
    width: 80%;
    height: 90%;
}

.card {
    margin: 0;
}
</style>
