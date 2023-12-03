<script setup>
import { ref, onBeforeMount } from 'vue';
import { useMyRecordStore } from '@/stores/myRecord';
import { useRoute } from 'vue-router';
import router from '@/router';
import { useToast } from 'primevue/usetoast';
import { useConfirm } from 'primevue/useconfirm';
import { instanceWithAuth } from '@/util/axiosInstanceSupport';

const route = useRoute();
const store = useMyRecordStore();
const toast = useToast();
const confirm = useConfirm();

const myRecord = ref({});
const myRecordImgs = ref([]);
const myTitle = ref('');
const myContent = ref('');

onBeforeMount(() => {
    store.getRecordById(route.params.id).then((record) => {
        if (record === null) {
            router.replace({ name: 'error' });
        } else {
            myRecord.value = store.recordDetail;
            myTitle.value = myRecord.value.title;
            myContent.value = myRecord.value.content;
            myRecordImgs.value = myRecord.value.record_images.map((value) => ({
                url: value.url
            }));
        }
    });
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

const deleteRecord = (event) => {
    confirm.require({
        target: event.currentTarget,
        message: '기록을 삭제하시겠습니까?',
        icon: 'pi pi-info-circle',
        acceptClass: 'p-button-danger p-button-sm',
        accept: () => {
            instanceWithAuth.delete(`/records/${route.params.id}`).then((response) => {
                router.replace({ name: 'record' });
                toast.add({ severity: 'info', summary: '기록이 삭제되었습니다.', life: 3000 });
            });
        },
        reject: () => {}
    });
};

const moveEditView = () => {
    router.push({ name: 'record-edit' });
};
</script>

<template>
    <div class="card">
        <div class="p-buttonset flex justify-content-end">
            <Button @click="moveEditView" icon="pi pi-check" label="수정"></Button>
            <Button @click="deleteRecord($event)" icon="pi pi-times" label="삭제" severity="danger"></Button>
        </div>
        <div class="flex justify-content-center align-items-center">
            <Galleria :value="myRecordImgs" :responsiveOptions="responsiveOptions" :numVisible="5" :circular="true" containerStyle="max-width: 640px" :showItemNavigators="true" :showThumbnails="false" :showItemNavigatorsOnHover="true">
                <template #item="slotProps">
                    <img :src="slotProps.item.url" :alt="no - image" style="height: 350px; width: 350px; border-radius: 10px" class="shadow-1" />
                </template>
            </Galleria>
        </div>
        <div class="flex justify-content-center align-items-center mt-5">
            <h2>{{ myTitle }}에서의 나의기록</h2>
        </div>
        <div class="flex justify-content-center align-items-center">
            <div>
                <p class="m-0">
                    {{ myContent }}
                </p>
            </div>
        </div>
    </div>
</template>

<style scoped lang="scss"></style>
