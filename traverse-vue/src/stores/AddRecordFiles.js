import {defineStore} from "pinia";
import {ref} from "vue";


export const useAddRecordFilesStore = defineStore('files', () => {
    const files = ref([]);


    const clear = () => {
        files.value = [];
    }

    return{
        files,
        clear
    }
});