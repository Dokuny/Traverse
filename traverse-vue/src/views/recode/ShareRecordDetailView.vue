<script setup>
import { ref, onBeforeMount } from 'vue';
import { useMyRecordStore } from '@/stores/myRecord';
import { useRoute } from 'vue-router';
import router from '@/router';
import { useToast } from 'primevue/usetoast';
import { useConfirm } from 'primevue/useconfirm';
import { instanceWithAuth } from '@/util/axiosInstanceSupport';
import { useShareRecordStore } from "@/stores/shareRecord";

const route = useRoute();
const store = useShareRecordStore();
const toast = useToast();
const confirm = useConfirm();

onBeforeMount(() => {

});

const images = ref();
const responsiveOptions = ref([
    {
        breakpoint: '991px',
        numVisible: 4
    },
    {
        breakpoint: '767px',
        numVisible: 3
    },
    {
        breakpoint: '575px',
        numVisible: 1
    }
]);

</script>

<template>
    <div class="card">
        <div class="flex justify-content-center align-items-center">
            <Galleria :value="store.curRecord.record_images" :responsiveOptions="responsiveOptions" :numVisible="5" :circular="true" containerStyle="max-width: 640px" :showItemNavigators="true" :showThumbnails="false" :showItemNavigatorsOnHover="true">
                <template #item="slotProps">
                    <img :src="slotProps.item.url" :alt="no - image" style="height: 350px; width: 350px; border-radius: 10px" class="shadow-1" />
                </template>
            </Galleria>
        </div>
        <div class="flex justify-content-center align-items-center mt-5">
            <img :src="store.curRecord.member_thumbnail" style="width: 50px; height: 50px; border-radius: 50%"/>
            <h3 class="ml-3">{{store.curRecord.member_nickname}}님의 <span style="color:#007aff">{{store.curRecord.place_name}}</span>에서 기록</h3>
        </div>
        <div class="flex justify-content-center align-items-center">
            <div>
                <p class="m-0">
                  {{ store.curRecord.record_content }}
                </p>
            </div>
        </div>
    </div>
</template>

<style scoped lang="scss"></style>
