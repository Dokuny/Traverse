import { ref } from 'vue';
import { defineStore } from 'pinia';
import axios from 'axios';
import { instance, instanceWithAuth } from '@/util/axiosInstanceSupport';

export const useCreatePlanStore = defineStore('searchPlace', () => {
    const requestTypes = ref(new Set());

    const places = ref([]);

    const requestKeyword = ref('');

    const lastId = ref(0);

    const isMore = ref(false);

    const selectType = (type) => {
        let set = requestTypes.value;
        if (set.has(type)) {
            set.delete(type);
        } else {
            set.add(type);
        }
    };

    const keywordHandler = (text) => {
        requestKeyword.value = text;
    };

    const searchPlaceMore = () => {
        instance
            .get('/places', {
                params: {
                    keyword: requestKeyword.value,
                    types: Array.from(requestTypes.value).join(','),
                    id: lastId.value
                }
            })
            .then((response) => {
                if (response.data.dataBody.length < 20) {
                    isMore.value = false;
                }else {
                    isMore.value = true;
                }

                if (response.data.dataBody.length > 0) {
                    for (let more of response.data.dataBody) {
                        places.value.push(more);
                    }
                    lastId.value = places.value[places.value.length - 1].place_id;
                }
            });
    };

    const searchPlaceFirst = () => {
        lastId.value = 0;

        instance
            .get('/places', {
                params: {
                    keyword: requestKeyword.value,
                    types: Array.from(requestTypes.value).join(','),
                    id: lastId.value
                }
            })
            .then((response) => {
                if (response.data.dataBody.length < 20) {
                    isMore.value = false;
                }else {
                    isMore.value = true;
                }
                if (response.data.dataBody.length > 0) {
                    places.value = response.data.dataBody;
                    lastId.value = places.value[places.value.length
                    - 1].place_id;
                }else {
                    places.value = [];
                }
            });
    };

    // ================= 선택된 장소 ======================

    const selectPlaces = ref([]);

    // ================= 모달창 ===================


    return {
        selectType,
        requestTypes,
        places,
        requestKeyword,
        keywordHandler,
        searchPlaceMore,
        searchPlaceFirst,
        selectPlaces,
        isMore
    };
});
