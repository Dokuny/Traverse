import { defineStore } from 'pinia';
import { ref } from 'vue';
import axios from 'axios';
import { instance, instanceWithAuth } from '@/util/axiosInstanceSupport';

export const useCreateRecordStore = defineStore('records', () => {
    const plans = ref([]);
    const places = ref([]);
    const records = ref([]);
    const record_images = ref([]);
    const highlights = ref([]);
    const destinations = ref([]);
    const destinationPlaces = ref([]);

    //내 계획 정보 가져오기
    const getMyPlans =  async () => {

        await instanceWithAuth.get("/plans/me")
        .then(response => {
            // console.log(response.data.dataBody);
            plans.value = response.data.dataBody;
            // console.log("내 계획들: " + plans.value[0].plan_id);
        })
    };

    const getDestinationsInPlan = async (planId) => {

        console.log(planId);
        await instanceWithAuth.get('/plans/me/' + planId)
        .then((response) => {
            console.log("!!!!!!"+response.data.dataBody.destinations);
            destinations.value = response.data.dataBody.destinations;
            console.log(destinations.value);
        });

    };

    const getImgByPlaceId = (placeId) => {
        for (let i = 0; i < dummyPlaces.value.length; i++) {
            if (dummyPlaces.value[i].place_id == placeId) {
                return dummyPlaces.value[i].thumbnail;
            }
        }
    };
    const getRecords = async () => {
        const datas = await instanceWithAuth.get('/records/me').then((response) => {
            records.value = response.data.dataBody;
            return response.data.dataBody;
        });
        return datas;
    };


    const getHighlights = () => {
        let newhighlights = [];
        for (let i = 0; i < records.value.length; i++) {
            if(records.value[i].is_highlight){
                newhighlights.push(records.value);
            }
        }
        highlights.value = newhighlights;
    };

    // 선택된 하이라이트의 인덱스
    const highlightIndex = ref(null);

    // 현재 선택된 하이라이트가 활성화되었는지 여부
    const isHighlightActive = ref(false);


    const addRecord = (record) => {
        records.value.push(record);
    }


    return {
        destinations,
        plans,
        getMyPlans,
        getDestinationsInPlan,
        getImgByPlaceId,
        getHighlights,
        destinationPlaces,
        records,
        getRecords,
        record_images,
        highlights,
        highlightIndex,
        isHighlightActive,
        addRecord
    };
});
