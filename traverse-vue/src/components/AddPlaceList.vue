<script setup>
import { ref } from 'vue';
import { useCreatePlanStore } from '@/stores/createPlan';
import { storeToRefs } from 'pinia';
import { useKakaoPlaceStore } from '@/stores/KakaoPlace';
import AddPlaceListItem from '@/components/AddPlaceListItem.vue';

let creatPlaceStore = useCreatePlanStore();
const kakaoStore = useKakaoPlaceStore();

const keyword = ref('');

const searchPlacesFirst = () => {
    kakaoStore.searchPlaceFirstByKeyword(keyword.value);
    console.log(kakaoStore.places);
};

const searchPlacesMore = () => {
    kakaoStore.searchPlaceMoreByKeyword(keyword.value);
};

const keywordHandler = (e) => {
    keyword.value = e.target.value;
};
</script>

<template>
    <div class="col-12">
        <div class="p-inputgroup">
            <input placeholder="장소명 혹은 주소를 입력해주세요." class="searchInput pi pi-search" @input="keywordHandler" :value="keyword" @keyup.enter="searchPlacesFirst" />
            <Button class="pi pi-search searchBtn" size="small" @click="searchPlacesFirst" />
        </div>
        <div>
            <template v-for="place in kakaoStore.places" :key="place.place_id">
                <AddPlaceListItem :place="place" />
            </template>
            <div class="card more mt-3" @click="searchPlacesMore" v-show="kakaoStore.isMore">
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
