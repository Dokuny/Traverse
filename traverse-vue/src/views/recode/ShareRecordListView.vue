<script setup>
import { onBeforeMount, ref } from "vue";
import { useRouter } from "vue-router";
import { instanceWithAuth } from "@/util/axiosInstanceSupport";
import { useShareRecordStore } from "@/stores/shareRecord";

const router = useRouter();

let store = useShareRecordStore();

const list = ref([]);

const viewRecord = (record) => {
    store.setRecord(record);

    router.push({
        name: 'community-record',
        params: { id: record.record_id }
    });

};

onBeforeMount(() => {
    instanceWithAuth.get('/records').then((response) => {
        let dataBody = response.data.dataBody;

        list.value = dataBody;
      console.log(list.value);
    });
});
</script>

<template>
    <div class="card">
      <h4>기록 공유 👪</h4>
        <div class="flex justify-content-center">
            <DataView :value="list" layout="grid" :paginator="true" :rows="12" style="width: 1000px">
                <template #grid="slotProps">
                    <div class="m-3 flex justify-content-center align-items-center" style="height: 300px; width: 300px; cursor: pointer">
                        <img :src="slotProps.data.record_images[0].url" :alt="slotProps.data.title" class="shadow-1 img" @click="viewRecord(slotProps.data)" />
                    </div>
                </template>
            </DataView>
        </div>
    </div>
</template>

<style lang="scss" scoped>
.grid-container {
    display: grid;
    grid-template-columns: repeat(3, 1fr); /* Display three items per row */
    gap: 16px; /* Adjust the gap between grid items */
}

.grid-item {
    //border: 1px solid #ccc;
    height: 100%;
    width: 100%;
    padding: 10px;
    text-align: center;
}

.img {
    width: 300px;
    height: 300px;
    border-radius: 10px;
}

.selected-record-place {
    background-color: #fff;
    padding: 16px;
    border-radius: 8px;
    cursor: pointer;
}

.record-menu-slider {
    overflow: visible;

    &__item {
        padding: 0.5rem;
        width: 350px;
        height: 300px;
        border-radius: 0.75rem;
        cursor: pointer;
    }

    &__item-title {
        text-align: center;
    }

    .record-menu-slider__item {
        background-color: #fff; /* 각 그리드 아이템의 스타일 설정 */
        padding: 16px;
        border-radius: 8px;
        cursor: pointer;
    }

    .record-menu-slider__item-title {
        text-align: center; /* 각 그리드 아이템의 제목 스타일 설정 */
    }
}
</style>
