<script setup>
import { onBeforeMount, ref } from 'vue';
import router from '@/router';
import { useMyRecordStore } from '@/stores/myRecord';
import { useRoute } from 'vue-router';
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
const myHighlight = ref(null);

onBeforeMount(() => {
    store.getRecordById(route.params.id).then((record) => {
        if (record === null) {
            router.replace({ name: 'error' });
        } else {
            myRecord.value = store.recordDetail;
            myTitle.value = myRecord.value.title;
            myContent.value = myRecord.value.content;
            myHighlight.value = myRecord.value.is_highlight;
            myRecordImgs.value = myRecord.value.record_images.map((value) => ({
                id: value.record_image_id,
                url: value.url
            }));
        }

        console.log(myRecord.value);
    });
});

const deleteImages = (imageUrl) => {
    if (myRecordImgs.value.length === 1) {
        alert('사진은 최소 한장이 있어야 합니다.');
        return;
    }
    let idx = myRecordImgs.value.findIndex((image) => image.url === imageUrl);
    myRecordImgs.value.splice(idx, 1);
};

const completeEdit = () => {
    confirm.require({
        target: event.currentTarget,
        message: '기록을 수정하시겠습니까?',
        icon: 'pi pi-info-circle',
        acceptClass: 'p-button-primary p-button-sm',
        accept: () => {
            instanceWithAuth
                .put(`/records/${route.params.id}`, {
                    content: myContent.value,
                    highlight: myHighlight.value,
                    images: myRecordImgs.value.map((value) => value.id)
                })
                .then((response) => {
                    router.replace(`/record/${route.params.id}`);
                    toast.add({ severity: 'info', summary: '기록이 수정되었습니다.', life: 3000 });
                });
        },
        reject: () => {}
    });
};

const toggleHighlight = () => {
    myHighlight.value = !myHighlight.value;
};
</script>

<template>
    <div class="card">
        <div class="p-buttonset flex justify-content-end">
            <Button @click="completeEdit" icon="pi pi-check" label="수정"></Button>
        </div>
        <div class="flex justify-content-center align-items-center">
            <Galleria :value="myRecordImgs" :responsiveOptions="responsiveOptions" :numVisible="5" :circular="true" containerStyle="max-width: 640px" :showItemNavigators="true" :showThumbnails="false" :showItemNavigatorsOnHover="true">
                <template #item="slotProps">
                    <div>
                        <img :src="slotProps.item.url" :alt="no - image" style="height: 350px; width: 350px; border-radius: 10px" class="shadow-1" />
                        <div class="deleteBtn pi pi-times" style="color: red; cursor: pointer" @click="deleteImages(slotProps.item.url)" />
                    </div>
                </template>
            </Galleria>
        </div>
        <div class="flex justify-content-center align-items-center mt-5">
            <h2 class="mr-4">{{ myTitle }}에서의 나의기록</h2>
            <Button icon="pi pi-heart" :class="myHighlight ? 'p-button-rounded mr-2 mb-2' : 'p-button-rounded p-button-outlined mr-2 mb-2'" @click="toggleHighlight" />
        </div>
        <div class="flex justify-content-center align-items-center">
            <div>
                <span class="p-float-label">
                    <Textarea id="value" v-model="myContent" :class="{ 'p-invalid': errorMessage }" rows="4" cols="30" aria-describedby="text-error" />
                </span>
            </div>
        </div>
    </div>
</template>

<style scoped lang="scss">
.deleteBtn {
    position: absolute;
    top: 10px;
    right: 10px;
}
</style>
