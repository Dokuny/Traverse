import { defineStore } from 'pinia';
import axios from 'axios';
import { ref } from 'vue';

export const useKakaoPlaceStore = defineStore('googleStore', () => {
    const pageNo = ref(1);
    const pageSize = ref(15);

    const places = ref([]);

    const isMore = ref(false);
    const searchPlaceByKeyword = (keyword) => {
        axios
            .create({
                headers: {
                    Authorization: `KakaoAK ${import.meta.env.VITE_KAKAO_API_KEY}`
                },
                params: {
                    query: keyword,
                    page: pageNo.value,
                    size: pageSize.value
                }
            })
            .get('https://dapi.kakao.com/v2/local/search/keyword.json')
            .then((response) => {
                if (response.data.documents.length < 15) {
                    isMore.value = false;
                } else {
                    isMore.value = true;
                }

                if (response.data.documents.length > 0) {
                    const convertedPlaces = response.data.documents.map((item) => ({
                        place_id: item.id,
                        address: item.road_address_name === '' ? item.address_name : item.road_address_name,
                        name: item.place_name,
                        lat: Number(item.y),
                        lng: Number(item.x),
                        thumbnail: '',
                        placeType: ''
                    }));
                    for (let item of convertedPlaces) {
                        places.value.push(item);
                    }
                }
            });
    };

    const searchPlaceMoreByKeyword = (keyword) => {
        pageNo.value += 1;
        searchPlaceByKeyword(keyword);
    };

    const searchPlaceFirstByKeyword = (keyword) => {
        pageNo.value = 1;
        places.value = [];
        searchPlaceByKeyword(keyword);
    };



    return { searchPlaceFirstByKeyword, searchPlaceMoreByKeyword, places, isMore };
});
