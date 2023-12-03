<script setup>
import SearchPlaceListItem from '@/components/SearchPlaceListItem.vue';
import { onMounted, ref } from 'vue';
import { useCreatePlanStore } from '@/stores/createPlan';
import { storeToRefs } from 'pinia';

let creatPlaceStore = useCreatePlanStore();
const { requestTypes } = storeToRefs(creatPlaceStore);

const keyword = ref('');

const keywordHandler = (e) => {
    creatPlaceStore.keywordHandler(e.target.value);
    keyword.value = e.target.value;
};

const searchPlacesFirst = () => {
    creatPlaceStore.searchPlaceFirst();
};

const searchPlacesMore = () => {
    creatPlaceStore.searchPlaceMore();
};

onMounted(() => {
    searchPlacesFirst();
});
</script>

<template>
    <div class="col-12">
        <div class="p-inputgroup">
            <input placeholder="장소명 혹은 주소를 입력해주세요." class="searchInput pi pi-search" @input="keywordHandler" :value="keyword" @keyup.enter="searchPlacesFirst" />
            <Button class="pi pi-search searchBtn" size="small" @click="searchPlacesFirst" />
        </div>
        <div class="typeBtnList">
            <button @click="[creatPlaceStore.selectType('관광지'), searchPlacesFirst()]" :class="{ on: requestTypes.has('관광지'), none: !requestTypes.has('관광지') }">관광지</button>
            <button @click="[creatPlaceStore.selectType('숙박'), searchPlacesFirst()]" :class="{ on: requestTypes.has('숙박'), none: !requestTypes.has('숙박') }">숙박</button>
            <button @click="[creatPlaceStore.selectType('음식점'), searchPlacesFirst()]" :class="{ on: requestTypes.has('음식점'), none: !requestTypes.has('음식점') }">음식점</button>
            <button @click="[creatPlaceStore.selectType('레포츠'), searchPlacesFirst()]" :class="{ on: requestTypes.has('레포츠'), none: !requestTypes.has('레포츠') }">레포츠</button>
            <button @click="[creatPlaceStore.selectType('쇼핑'), searchPlacesFirst()]" :class="{ on: requestTypes.has('쇼핑'), none: !requestTypes.has('쇼핑') }">쇼핑</button>
            <button @click="[creatPlaceStore.selectType('문화시설'), searchPlacesFirst()]" :class="{ on: requestTypes.has('문화시설'), none: !requestTypes.has('문화시설') }">문화시설</button>
        </div>
        <div>
            <template v-for="place in creatPlaceStore.places" :key="place.place_id">
                <SearchPlaceListItem :place="place" />
            </template>
            <div class="card more" @click="searchPlacesMore" v-show="creatPlaceStore.isMore">
                <span>+ 더보기</span>
            </div>
        </div>
    </div>
</template>

<style scoped lang="scss">
.searchBtn {
    box-shadow: 0 2px 8px 0 rgba(31, 38, 135, 0.08) !important;
    background-color: #98dde3;
    border-color: #98dde3;
}

.searchInput:focus {
    outline: none;
}

.searchInput::placeholder {
    color: #dddfe2;
    font-family: 'Noto Sans KR';
}

.searchInput {
    color: black;
    width: 100%;
    height: 40px;
    padding-left: 15px;
    font-family: 'Noto Sans KR';
    box-shadow: 0 2px 8px 0 rgba(31, 38, 135, 0.08) !important;

    border: none;
}

.typeBtnList {
    margin-top: 10px;
}

.typeBtnList > button {
    font-family: 'Noto Sans KR';
    margin-right: 5px;
    margin-top: 5px;
    margin-bottom: 5px;
    line-height: 30px;
    border-radius: 15%;
    cursor: pointer;
}

.on {
    border: 1px solid #0088ff;
    background-color: #0088ff;
    color: white;
}

.none {
    border: 1px solid darkgray;
    color: darkgray;
    background-color: white;
}

.more {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 5vh;
    margin: 0;
    padding: 10px;
    background-color: #fafafa;
    cursor: pointer;
}
</style>
