import {defineStore} from "pinia";
import {instanceWithAuth} from "@/util/axiosInstanceSupport";
import {ref} from "vue";

export const useMyRecordStore = defineStore("myRecordStore", () => {

    const recordDetail = ref({});

    //레코드의 아이디에 해당하는 레코드 가져오기
    const getRecordById = async (recordId) => {
        await instanceWithAuth.get(`/records/me/${recordId}`)
            .then((response) => {
                // console.log("아이디로 이미지 찾기:");
                // console.log(response.data.dataBody);
                recordDetail.value = response.data.dataBody;
            });
    };


    return{
        recordDetail,
        getRecordById,
    }
})