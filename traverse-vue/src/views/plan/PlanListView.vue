<script setup>
import { FilterMatchMode, FilterOperator } from 'primevue/api';
import { onBeforeMount, onMounted, ref } from 'vue';
import { useMyPlansStore } from '@/stores/myPlans';
import { useRouter } from 'vue-router';

const router = useRouter();

const myPlanStore = useMyPlansStore();

const filters1 = ref(null);

const loading2 = ref(null);

const expandedRows = ref([]);

onBeforeMount(() => {
    loading2.value = false;

    initFilters1();
});

const initFilters1 = () => {
    filters1.value = {
        global: { value: null, matchMode: FilterMatchMode.CONTAINS },
        name: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
        'country.name': { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }] },
        representative: { value: null, matchMode: FilterMatchMode.IN },
        date: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.DATE_IS }] },
        balance: { operator: FilterOperator.AND, constraints: [{ value: null, matchMode: FilterMatchMode.EQUALS }] },
        status: { operator: FilterOperator.OR, constraints: [{ value: null, matchMode: FilterMatchMode.EQUALS }] },
        activity: { value: [0, 50], matchMode: FilterMatchMode.BETWEEN },
        verified: { value: null, matchMode: FilterMatchMode.EQUALS }
    };
};

const expandAll = () => {
    expandedRows.value = myPlanStore.myPlans.filter((p) => p.plan_id);
};
const collapseAll = () => {
    expandedRows.value = null;
};

onMounted(() => {
    myPlanStore.getMyPlans();
});

const moveDetailPage = (plan) => {
    router.push({
        name: 'plan-detail',
        params: { id: plan.plan_id }
    });
};
</script>

<template>
    <div class="grid">
        <div class="col-12">
            <div class="card">
                <DataTable :value="myPlanStore.myPlans" class="font" v-model:expandedRows="expandedRows" dataKey="plan_id" responsiveLayout="scroll">
                    <template #header>
                        <div>
                            <Button icon="pi pi-plus" label="전부 펼치기" @click="expandAll" class="mr-2 mb-2 font" />
                            <Button icon="pi pi-minus" label="전부 접기" @click="collapseAll" class="mb-2 font" />
                        </div>
                    </template>
                    <Column :expander="true" headerStyle="min-width: 3rem" class="font" />
                    <Column field="title" header="계획명" :sortable="true" />
                    <Column :field="(item) => item.destinations[0].visited_at.substring(0, 10) + ' ~ ' + item.destinations[item.destinations.length - 1].visited_at.substring(0, 10)" header="여행 날짜" :sortable="true">
                        <template #body="slotProps">
                            {{ slotProps.data.destinations[0].visited_at.substring(0, 10) + ' ~ ' + slotProps.data.destinations[slotProps.data.destinations.length - 1].visited_at.substring(0, 10) }}
                        </template>
                    </Column>
                    <Column field="created_at" header="작성 날짜" :sortable="true">
                        <template #body="slotProps">
                            {{ slotProps.data.created_at.substring(0, 10) }}
                        </template>
                    </Column>
                    <Column headerStyle="width:4rem">
                        <template #body="slotProps">
                            <Button icon="pi pi-search" @click="moveDetailPage(slotProps.data)" />
                        </template>
                    </Column>

                    <template #expansion="slotProps">
                        <div class="p-3">
                            <h5 class="accent">{{ slotProps.data.title }}</h5>
                            <DataTable class="font" :value="slotProps.data.destinations" responsiveLayout="scroll" data-key="destination_id">
                                <Column field="slotProps.index" header="사진">
                                    <template #body="slotProps">
                                        <img :src="slotProps.data.thumbnail === '' ? '/common/no-image.svg' : slotProps.data.thumbnail" width="100" height="80" />
                                    </template>
                                </Column>
                                <Column field="name" header="장소명" :sortable="true" />
                                <Column field="place_type" header="타입" :sortable="true">
                                    <template #body="slotProps">
                                        {{ slotProps.data.place_type }}
                                    </template>
                                </Column>
                                <Column field="address" header="주소" :sortable="true" />
                                <Column field="visited_at" header="날짜" :sortable="true">
                                    <template #body="slotProps">
                                        {{ slotProps.data.visited_at.substring(0, 16) }}
                                    </template>
                                </Column>
                            </DataTable>
                        </div>
                    </template>
                </DataTable>
            </div>
        </div>
    </div>
</template>

<style scoped lang="scss">
::v-deep(.p-datatable-frozen-tbody) {
    font-weight: bold;
}

::v-deep(.p-datatable-scrollable .p-frozen-column) {
    font-weight: bold;
}
a {
    text-decoration: none;
    color: inherit;
}

.font {
    font-family: 'Pretendard-Regular';
    font-weight: 900;
}

.accent {
    font-family: 'yg-jalnan';
}
</style>
