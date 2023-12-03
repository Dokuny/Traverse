<script setup>
import { useCreatePlanStore } from '@/stores/createPlan';
import { useToast } from 'primevue/usetoast';
import { ref } from 'vue';
import { useConfirm } from 'primevue/useconfirm';
import { instanceWithAuth } from '@/util/axiosInstanceSupport';
import router from '@/router';
import {useEditPlanStore} from "@/stores/editPlan";
const props = defineProps({
  listHeight: Number,
  curPlan : Object,
});

let confirm = useConfirm();
let toast = useToast();

let store = useEditPlanStore();


const title = ref('');


let deleteSelect = (key) => {
    let idx = -1;
    for (let i = 0; i < store.selectPlaces.length; i++) {
        if (store.selectPlaces[i].key === key) {
            idx = i;
            break;
        }
    }

    if (idx !== -1) {
        store.selectPlaces.splice(idx, 1);

        toast.add({ severity: 'error', life: 2000, summary: '장소가 삭제되었습니다.' });
    }
};


const complete = () => {
    confirm.require({
        header: '계획명',
        group: 'save',
        accept: () => {
            if (title.value === '') {
                alert('계획명을 입력해주세요.');
            } else {
                if (store.selectPlaces.length === 0) {
                    alert('적어도 하나 이상의 장소를 선택해야합니다.');
                    title.value = "";
                } else {
                    let destinations = store.selectPlaces.map((place) => ({
                        placeId: place.place_id,
                        destinationId: place.destination_id,
                        visitedAt: new Date(place.visited_at),
                        comment: place.comment
                    }));

                    instanceWithAuth
                        .put(`/plans/${props.curPlan.plan_id}`, {
                            title: title.value,
                            destinations: destinations
                        })
                        .then(() => {
                            toast.add({ severity: 'info', life: 2000, summary: '계획이 수정되었습니다.' });
                            store.selectPlaces = [];
                            title.value = '';
                            router.replace({ name: 'my-plan' });
                        });
                }
            }
        },
        reject: () => {
            title.value = '';
        }
    });
};

const reset = () => {
    store.selectPlaces = [];
};





</script>

<template>
    <div class="col-12 selectContainer" style="height: 100%">
        <ConfirmDialog group="save">
            <template #message="slotProps">
                <div class="flex flex-column align-items-center w-full gap-3 border-bottom-1 surface-border">
                    <InputText type="text" placeholder="계획명을 입력해주세요." v-model="title" />
                </div>
            </template>
        </ConfirmDialog>
        <div class="p-buttonset flex justify-content-end">
            <Button class="buttons" label="저장" icon="pi pi-check" @click="complete" />
            <Button class="buttons" label="초기화" icon="pi pi-times" @click="reset" />
        </div>
        <DataTable
            :value="[...store.selectPlaces].sort((a, b) => new Date(a.visited_at) - new Date(b.visited_at))"
            rowGroupMode="subheader"
            groupRowsBy="group"
            sortMode="single"
            sortField="date"
            :sortOrder="1"
            scrollable
            :scrollHeight="props.listHeight + 'px'"
            style="width: 100%"
            class="selectContainer"
            :data-key="(item) => item.key"
        >
            <Column field="group" header="그룹"></Column>
            <Column field="thumbnail" header="사진" style="min-width: 10%">
                <template #body="slotProps">
                    <img :src="slotProps.data.thumbnail === '' ? '/common/no-image.svg' : slotProps.data.thumbnail" width="80" />
                </template>
            </Column>
            <Column field="name" header="이름" style="min-width: 30%"></Column>
            <Column field="place_type" header="타입" style="min-width: 10%"></Column>
            <Column field="address" header="주소" style="min-width: 20%"></Column>
            <Column field="time" header="시간" style="min-width: 10%"></Column>
            <Column field="" header="Status" style="min-width: 3%">
                <template #body="slotProps">
                    <Button icon="pi pi-times" @click="deleteSelect(slotProps.data.key)" class="p-button-rounded p-button-danger bg-white p-b removeLine utton-text mr-2 mb-2" style="color: red; border: none" />
                </template>
            </Column>
            <template #groupheader="slotProps">
                <span class="image-text font-bold ml-2 group-header">{{ slotProps.data.group }}</span>
            </template>
            <!--            <template #groupfooter="slotProps">-->
            <!--                <td style="text-align: right" class="text-bold pr-6">Total Customers: {{ calculateCustomerTotal(slotProps.data.representative.name) }}</td>-->
            <!--            </template>-->
        </DataTable>
    </div>
</template>

<style scoped lang="scss">
.removeLine:active {
    border: none;
}

.buttons {
    background-color: #0076be;
    font-family: Pretendard-Regular;
    color: white;
    border: #98dde3;
    margin-bottom: 10px;
}

.group-header {
    font-family: 'Noto Sans KR';
}

.selectContainer {
    font-family: 'Pretendard-Regular';
    font-weight: 900;
}
</style>
