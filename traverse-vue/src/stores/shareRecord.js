import {defineStore} from "pinia";
import {instanceWithAuth} from "@/util/axiosInstanceSupport";
import {ref} from "vue";

export const useShareRecordStore = defineStore("shareRecord", () => {

    const curRecord = ref(null);

    const setRecord = (record) => {
        curRecord.value = record;
    }

    return{
        curRecord, setRecord
    }
})