<script setup>
import { useCreateRecordStore } from "@/stores/createRecord";
import { onMounted, ref } from "vue";
import router from "@/router";
import { instanceWithAuth } from "@/util/axiosInstanceSupport";
import AddRecordImages from "@/components/AddRecordImages.vue";
import { useAddRecordFilesStore } from "@/stores/AddRecordFiles";

const emits = defineEmits(['addRecord']);

const store = useCreateRecordStore();
const fileStore = useAddRecordFilesStore();
const display = ref(false);
const displayConfirmation = ref(false);
const selectedPlan = ref({});
const selectedDestination = ref({});
const isChecked = ref(false);

onMounted(() => {
    store.getMyPlans();
});
const open = () => {
    //열때마다 처음부터 저장하기 위한 입력 모두 초기화
    selectedPlan.value = {};
    selectedDestination.value = {};
    display.value = true;
};

const cancel = () => {
    displayConfirmation.value = false;
    selectedPlan.value = {};
    selectedDestination.value = {};
    display.value = false;
};
const closeConfirmation = () => {
    display.value = false;
};

const goCreatePlan = () => {
    router.replace('/plan');
};
const reSelect = () => {
    selectedPlan.value = {};
    selectedDestination.value = {};
};

const addHightlight = () => {
    if (isChecked.value) {
        isChecked.value = false;
    } else {
        isChecked.value = true;
    }
};

const selectPlan = (planData) => {
    selectedPlan.value = planData;
    //계획 정할 때 계획에 대한 여행지 가져오기
    store.getDestinationsInPlan(planData.plan_id);
    //기록에 대한 파일 초기화 처리
    fileStore.files = [];
};

const selectDestination = (destination) => {
    console.log(destination.data);
    if (destination === '') {
        selectedDestination.value = { name: '어딘가' };
        selectedDestination.value.place_id = null;
    } else {
        selectedDestination.value = destination.data;
    }
    isChecked.value = false;
};

const content = ref('');
const recordImages = ref([]);
const newRecordId = ref(null);
const isHighlight = ref(false);

//기록 추가 로직
const save = async () => {
    //메모 입력X
    if (content.value === '') {
        alert('메모를 입력하세요');
        return;
    }
    if (fileStore.files.length === 0) {
        alert('사진을 하나 이상 등록해주세요.');
        return;
    }

    console.log(fileStore.files.length);
    if (fileStore.files.length > 10) {
        alert('사진은 최대 10장까지만 등록가능합니다.');
        return;
    }

    //하이라이트 추가
    if (isChecked.value) {
        isHighlight.value = true;
    } else {
        isHighlight.value = false;
    }

    const formData = new FormData();

    const data = {
        placeId: selectedDestination.value.place_id,
        planId: selectedPlan.value.plan_id,
        content: content.value,
        isHighlight: isHighlight.value
    };
    formData.append('data', new Blob([JSON.stringify(data)], { type: 'application/json' }));

    //이미지 담아주기
    // const fileList = [];
    for (let i = 0; i < fileStore.files.length; i++) {
        formData.append('files', fileStore.files[i]);
    }

    getLocalImageUrl(fileStore.files[0]);
    console.log(thumbnail.value);
    console.log(store.records);

    await instanceWithAuth
        .post('/records', formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        })
        .then((response) => {
            newRecordId.value = response.data.dataBody;
            console.log('저장한 레코드 아이디: ' + newRecordId.value);

            emits('addRecord');

            selectedPlan.value = {};
            selectedDestination.value = {};
            isChecked.value = false;
            fileStore.clear();
            content.value = '';
            recordImages.value = [];
            display.value = false;
            thumbnail.value = '';
        });
};

const thumbnail = ref('');

const getLocalImageUrl = (file) => {
    if (file) {
        // 이미지를 읽어와서 화면에 표시
        const reader = new FileReader();
        reader.onload = () => {
            thumbnail.value = reader.result;
        };
        reader.readAsDataURL(file);
    }
};
</script>

<template>
    <Dialog header="기록 추가하기" v-model:visible="display" :breakpoints="{ '960px': '75vw' }" @close="closeConfirmation" :style="{ width: '50vw' }" :modal="true">
        <div>
            <div v-if="store.plans.length == 0">
                <h4>기록할 수 있는 계획이 없어요</h4>
                <Button label="여행계획 세우러 가기" @click="goCreatePlan" icon="pi pi-compass" />
                <br />
            </div>
            <div v-else>
                <div v-if="Object.keys(selectedPlan).length === 0">
                    <!-- 계획 정하기 -->
                    <h4>계획 고르기</h4>
                    <div class="list">
                        <div class="col-12">
                            <DataView :value="store.plans" layout="list" :paginator="true" :rows="6">
                                <template #list="slotProps">
                                    <div class="col-12 md:col-4">
                                        <div class="card m-3 border-1 surface-border" @click="selectPlan(slotProps.data)">
                                            <div class="align-items-center justify-content-between">
                                                <div class="text-center">
                                                    {{ slotProps.data.title }}
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </template>
                            </DataView>
                        </div>
                    </div>
                </div>
                <div v-show="Object.keys(selectedPlan).length !== 0 && Object.keys(selectedDestination).length === 0">
                    <!-- 계획의 여행지 정하기 -->
                    <h4>기록할 여행지 고르기</h4>
                    <Carousel :value="store.destinations" :circular="false">
                        <template #item="destination">
                            <div class="border-round m-2 text-center p-2" style="border: 1px solid var(--surface-d)">
                                <div class="my-5">
                                    <img :src="destination.data.thumbnail !== '' ? destination.data.thumbnail : '/common/no-image.svg'" class="product-image" :style="{ height: '10vw', weight: '10vw' }" />
                                </div>
                                <div>
                                    <h5 class="mb-1">
                                        {{ destination.data.name }}
                                    </h5>
                                    <div class="my-5">
                                        <Button label="선택" @click="selectDestination(destination)" icon="pi pi-check" class="p-button-outlined" />
                                    </div>
                                </div>
                            </div>
                        </template>
                    </Carousel>
                </div>

                <div v-if="Object.keys(selectedPlan).length != 0 && Object.keys(selectedDestination).length != 0">
                    <!-- 모두 고른 후 기록 진행하기 -->
                    <div class="upload-content">
                        나의 계획 "{{ selectedPlan.title }}"의 여행지<br />"{{ selectedDestination.name }}" 에서의 기록 남기기
                        <Button label="다시 기록하기" @click="reSelect" icon="pi pi-fw pi-tag" class="p-button-outlined p-button-warning mr-2 mb-2 mt-3" style="width: 150px; height: 30px; font-size: smaller" />
                        <div class="mt-3">
                            <!-- 사진 추가 -->
                            <AddRecordImages />
                        </div>
                      <div class="highlight mt-4">
                        <Button icon="pi pi-heart" :class="isChecked ? 'p-button-rounded mr-2 mb-2' : 'p-button-rounded p-button-outlined mr-2 mb-2'" @click="addHightlight" />
                        <span class="highlight-text" v-tooltip="'꼭 기억하고 싶은 기록들을 상단에 모아주는 기능'"> 하이라이트에 추가 </span>
                      </div>
                        <div style="float: right" class="mt-4">
                            <!-- 메모 추가 -->
                            <Textarea inputId="textarea" rows="3" cols="50" v-model="content" placeholder="기록에 대한 메모를 입력하세요"/>
                        </div>
                        <!-- 하이라이트 추가 -->

                    </div>
                    <div style="margin-top: auto; text-align: right">
                        <Button label="기록하기" @click="save" icon="pi pi-check" class="p-button-outlined" />
                    </div>
                </div>
            </div>
        </div>
    </Dialog>
    <!-- 기록 추가 삭제 확인 dialog -->
    <Dialog header="Confirmation" v-model:visible="displayConfirmation" :style="{ width: '350px' }" :modal="true">
        <div class="flex align-items-center justify-content-center">
            <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
            <span>작성 중인 항목이 있어요<br />기록 추가를 취소할까요?</span>
        </div>
        <template #footer>
            <Button label="계속 작성하기" icon="pi pi-check" @click="closeConfirmation" class="p-button-text" autofocus />
            <Button label="취소" icon="pi pi-check" @click="cancel" class="p-button-text" autofocus />
        </template>
    </Dialog>
    <Button class="buttons" label="기록 추가하기" icon="pi pi-plus" @click="open" />
</template>

<style scoped lang="scss">
.upload-content {
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
    margin-bottom: 30px;
    height: 100%;
}

.highlight {
    display: flex;
    align-items: center;
}

.highlight-text {
    margin-right: 8px;
}
</style>
