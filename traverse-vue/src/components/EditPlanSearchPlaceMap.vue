<script setup>
import { GoogleMap, InfoWindow, Marker, MarkerCluster } from 'vue3-google-map';
import { useCreatePlanStore } from '@/stores/createPlan';
import { ref, watch } from 'vue';
import {useEditPlanStore} from "@/stores/editPlan";

const store = useEditPlanStore();

const center = ref({ lat: 37.5666103, lng: 126.9783882 });
const mapRef = ref(null);

watch(store.selectPlaces, () => {
    if (mapRef.value?.ready) {
        if (store.selectPlaces.length === 0) {
        } else {
            mapRef.value.map.panTo({
                lat: store.selectPlaces[store.selectPlaces.length - 1].lat,
                lng: store.selectPlaces[store.selectPlaces.length - 1].lng
            });
        }
    }
});

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

const naverSearch = (keyword) => {
    const url = `https://map.naver.com/p/search/${keyword}?c=15.00,0,0,0,dh`;
    window.open(url);
};
</script>

<template>
    <GoogleMap :api-key="apiKey" region="KR" ref="mapRef" style="width: 100%; height: 100%" :center="center" :zoom="8" language="kor">
        <MarkerCluster>
            <Marker v-for="place in store.selectPlaces" :options="{ position: { lat: place.lat, lng: place.lng }, icon: { url: getMarketImage(place.place_type), scaledSize: { width: 50, height: 50 } } }">
                <InfoWindow>
                    <div id="contet" class="flex-column" style="width: 300px">
                        <div id="siteNotice"></div>
                        <h3 id="firstHeading" class="firstHeading pointer" style="font-weight: 600" @click="naverSearch(place.name)">{{ place.name }}</h3>
                        <p>{{ place.address }}</p>

                        <div id="bodyContent">
                            <img :src="place.thumbnail" class="info" />
                        </div>
                    </div>
                </InfoWindow>
            </Marker>
        </MarkerCluster>
    </GoogleMap>
</template>

<style scoped lang="scss">
.pointer {
    cursor: pointer;
}
.info {
    width: 300px;
}
</style>
