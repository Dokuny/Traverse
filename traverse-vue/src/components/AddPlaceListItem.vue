<script setup>
import { onMounted, ref } from 'vue';
import { useCreatePlanStore } from '@/stores/createPlan';
import { useToast } from 'primevue/usetoast';
import { instance } from '@/util/axiosInstanceSupport';

let toast = useToast();
const place = ref({});

const success = () => {
    toast.add({ severity: 'success', summary: '장소가 등록되었습니다.', life: 2000 });
};

const store = useCreatePlanStore();

const isSelected = ref({ select: false });

const props = defineProps({
    place: Object
});

const select = () => {
    if (isSelected.value.select) {
        isSelected.value.select = false;
    } else {
        open();
    }
};
const isOpenedModal = ref(false);

onMounted(() => {
    place.value = props.place;
});

const open = () => {
    isOpenedModal.value = true;
};

const add = () => {
    if (date.value === '') {
        alert('날짜를 선택해주세요.');
        return;
    }
    if (selectedType.value === '') {
        alert('장소 타입을 선택해주세요.');
        return;
    }

    const group = `${date.value.getFullYear()}년 ${date.value.getMonth() + 1}월 ${date.value.getDate()}일`;

    const formatDate = new Date(date.value).toISOString();
    const time = `${date.value.getHours()}시 ${date.value.getMinutes()}분`;

    // 값 리스트에 저장 하기
    for (const item of store.selectPlaces) {
        if (item.place_id === place.value.place_id && group + time === item.key) {
            alert('동일한 장소가 같은 시간에 등록되어 있습니다.');
            return;
        }
    }

    // 여기서 새로 저장하려는 장소가 이름, 주소, 타입이 동일한 객체가 있는지 확인 후 저장.
    instance
        .get('/places/duplicate-check', {
            params: {
                name: place.value.name,
                address: place.value.address,
                type: selectedType.value
            }
        })
        .then((response) => {
            const isDuplicate = response.data.dataBody;

            if (!isDuplicate) {
                instance
                    .post('/places', {
                        name: place.value.name,
                        address: place.value.address,
                        lat: place.value.lat,
                        lng: place.value.lng,
                        thumbnail: place.value.thumbnail,
                        placeType: selectedType.value
                    })
                    .then((response) => {
                        store.selectPlaces.push({
                            place_id: response.data.dataBody,
                            name: place.value.name,
                            place_type: place.value.place_type,
                            address: place.value.address,
                            visited_at: formatDate,
                            comment: comment.value,
                            time: time,
                            group: group,
                            thumbnail: place.value.thumbnail,
                            key: group + time + '/' + place.value.place_id,
                            lat: place.value.lat,
                            lng: place.value.lng
                        });

                        success();
                        isOpenedModal.value = false;
                        isSelected.value.select = true;
                        comment.value = '';
                    });
            } else {
                alert('이미 등록되어있는 장소 입니다.');
                return;
            }
        });
};
const comment = ref('');
const date = ref('');
const selectedType = ref('');
const types = ref([{ name: '관광지' }, { name: '숙박' }, { name: '레포츠' }, { name: '문화시설' }, { name: '쇼핑' }, { name: '음식점' }]);
</script>

<template>
    <div class="card">
        <img class="image" :src="place.thumbnail === '' ? '/common/no-image.svg' : place.thumbnail" style="height: 60px; width: 60px; margin-right: 10px" />
        <div style="width: 75%">
            <p class="name">{{ place.name }}</p>
            <div>
                <span class="address">{{ place.address }}</span>
            </div>
        </div>
        <div class="addBtnContainer">
            <button class="pi" @click="select" :class="{ roundBtn: !isSelected.select, 'pi-plus': !isSelected.select, 'roundBtn-select': isSelected.select, 'pi-check': isSelected.select }"></button>
        </div>
        <div>
            <Dialog header="신규 장소 등록" v-model:visible="isOpenedModal" :modal="true" style="padding-bottom: 0; width: 400px">
                <div class="flex-column justify-content-center align-items-center">
                    <Calendar id="calendar-24h" v-model="date" inline showTime hourFormat="24" :style="{ width: '100%', height: '100%' }" />
                    <Dropdown v-model="selectedType" :options="types" optionValue="name" optionLabel="name" placeholder="Select a Type" class="w-full mt-5" />
                    <InputText type="text" placeholder="간단한 메모를 입력해주세요." v-model="comment" class="introduceInput mt-2" :style="{ width: '100%', height: '100%' }" />
                </div>
                <template #footer>
                    <div style="display: flex; flex-direction: column">
                        <Button label="추가" @click="add" icon="pi pi-check" class="p-button-outlined" style="margin: 0" />
                    </div>
                </template>
            </Dialog>
        </div>
    </div>
</template>

<style scoped lang="scss">
.card {
    margin: 10px 0;

    width: 100%;
    display: flex;
    padding: 10px;

    justify-content: space-between;
    align-items: center;
}

.introduceInput {
    font-family: 'Noto Sans KR';
}

.addBtnContainer {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 10%;
    margin-left: 5px;
    margin-right: 5px;
    padding-right: 5px;
}

.roundBtn {
    border-radius: 10px;
    width: 30px;
    height: 30px;
    border: none;
    background-color: #cccccc;
    color: white;
    cursor: pointer;
}

.roundBtn-select {
    border-radius: 35%;
    width: 30px;
    height: 30px;
    border: none;
    background-color: #98dde3;
    color: white;
    cursor: pointer;
}

.roundBtn-select:active,
.roundBtn:active {
    /* Start the shake animation and make the animation last for 0.5 seconds */
    animation: shake 0.5s;

    /* When the animation is finished, start again */
    animation-iteration-count: infinite;
}

@keyframes shake {
    0% {
        transform: rotate(0deg);
    }
    10% {
        transform: rotate(15deg);
    }
    20% {
        transform: rotate(-15deg);
    }
    30% {
        transform: rotate(10deg);
    }
    40% {
        transform: rotate(-10deg);
    }
    50% {
        transform: rotate(5deg);
    }
    60% {
        transform: rotate(-5deg);
    }
    70% {
        transform: rotate(0deg);
    }
    100% {
        transform: rotate(0deg);
    }
}

.image {
    border-radius: 20%;
}

.name {
    font-family: 'Pretendard-Regular';
    font-weight: 600;
    font-size: large;
    margin: 0;
}

.type {
    font-family: 'Pretendard-Regular';
    margin-right: 10px;
    display: inline-block;
    font-size: small;
}

.address {
    color: darkgray;
    display: inline-block;
    font-family: 'Pretendard-Regular';
    font-size: small;
}

.red {
    color: #b22222;
}

.blue {
    color: #0076be;
}

.sky {
    color: #20b2aa;
}

.green {
    color: #808000;
}

.yellow {
    color: #d2691e;
}

.purple {
    color: #8a2be2;
}
</style>
