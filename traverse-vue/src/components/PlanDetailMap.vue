<script setup>
import { GoogleMap, InfoWindow, Marker, MarkerCluster, Polyline } from 'vue3-google-map';
import { onMounted, ref } from 'vue';
import { useMyPlansStore } from '@/stores/myPlans';
import { onBeforeRouteLeave } from "vue-router";

const props = defineProps({
    curPlan: Object
});

const store = useMyPlansStore();

const center = ref({ lat: 37.5666103, lng: 126.9783882 });
const mapRef = ref(null);

const apiKey = import.meta.env.VITE_GOOGLE_API_KEY;

const getMarketImage = (type) => {
    let icon = '';

    if (type === '관광지') {
        icon = 'tour';
    } else if (type === '숙박') {
        icon = 'house';
    } else if (type === '문화시설') {
        icon = 'culture';
    } else if (type === '레포츠') {
        icon = 'leports';
    } else if (type === '쇼핑') {
        icon = 'shop';
    } else {
        icon = 'restaurant';
    }

    return '/icon/' + icon + '.png';
};

const isVisible = ref(false);

const lineConfig = ref(null);

const naverSearch = (keyword) => {
    const url = `https://map.naver.com/p/search/${keyword}?c=15.00,0,0,0,dh`;
    window.open(url);
};

const isComplete = ref(false);

const plan = ref(null);

const polyLine = ref(null);

const paths = ref(null);

onMounted(async () => {
    if (!isComplete.value) {
        if (props.curPlan.destinations.length > 1) {
            await store.getPath(props.curPlan.destinations).then((info) => {
                if (info !== undefined) {
                    paths.value = info.locations;


                    lineConfig.value = {
                        path: info.locations,
                        geodesic: true,
                        strokeColor: '#FE7966',
                        strokeOpacity: 1.0,
                        strokeWeight: 3
                    };


                    console.log(polyLine.value.polyline);
                }
            });
        }
        isVisible.value = true;
        isComplete.value = true;
    }
});

const isAnimate = ref(false);

function animateCircle() {
    let count = 0;

    if (isAnimate.value) {
        return;
    }

    isAnimate.value = true;
    const interval = window.setInterval(() => {
        count += 0.05;
        if (count >= 100) {
            clearInterval(interval);
            isAnimate.value = false;
            console.log('완료');
        }

        lineConfig.value = {
            path: paths.value,
            geodesic: true,
            strokeColor: '#FE7966',
            strokeOpacity: 1.0,
            strokeWeight: 3,
            icons: [
                {
                    icon: {
                        path: google.maps.SymbolPath.CIRCLE,
                        scale: 8,
                        strokeColor: '#0076be'
                    },
                    offset: count + '%' // 애니메이션의 시작 위치 (100%는 끝지점, 0%는 시작지점)
                }
            ]
        };
    }, 10);
}
</script>

<template>
    <div style="width: 100%" class="flex-column h-full justify-content-center">
        <div v-show="!isVisible" class="h-full w-full">
            <div class="flex align-items-center justify-content-center h-full w-full">
                <ProgressSpinner style="width: 30%; height: 30%" strokeWidth="8" fill="var(--surface-ground)" animationDuration="0.8s" />
            </div>
        </div>
        <GoogleMap v-show="isVisible" :api-key="apiKey" region="KR" ref="mapRef" style="width: 100%; height: 100%" :center="center" :zoom="7" language="kor">
            <Polyline ref="polyLine" :options="lineConfig" @click="animateCircle" />
            <MarkerCluster>
                <Marker v-for="place in props.curPlan.destinations" :key="place.place_id" :options="{ position: { lat: place.lat, lng: place.lng }, icon: { url: getMarketImage(place.place_type), scaledSize: { width: 40, height: 40 } } }">
                    <InfoWindow>
                        <div id="contet" class="flex-column font" style="width: 300px">
                            <div id="siteNotice"></div>
                            <h3 id="firstHeading" class="firstHeading pointer" style="font-weight: 600" @click="naverSearch(place.name)">{{ place.name }}</h3>
                            <p>{{ place.address }}</p>
                            <div id="bodyContent">
                                <img :src="place.thumbnail" class="info" />
                            </div>
                            <p>{{ place.comment }}</p>
                        </div>
                    </InfoWindow>
                </Marker>
            </MarkerCluster>
        </GoogleMap>
    </div>
</template>

<style scoped lang="scss">
.pointer {
    cursor: pointer;
}
.info {
    width: 300px;
}

.font {
    font-weight: 600;
    font-family: 'Pretendard-Regular';
}
</style>
