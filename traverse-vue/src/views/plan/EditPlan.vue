<script setup>
import {onBeforeRouteLeave, RouterView, useRoute, useRouter} from "vue-router";
import {onBeforeMount, ref} from "vue";
import SearchPlaceMap from "@/components/SearchPlaceMap.vue";
import {useKakaoPlaceStore} from "@/stores/KakaoPlace";
import {useEditPlanStore} from "@/stores/editPlan";
import {useMyPlansStore} from "@/stores/myPlans";
import EditPlanSelectPlaceList from "@/components/EditPlanSelectPlaceList.vue";

let router = useRouter();

let route = useRoute();
let store = useEditPlanStore();
let kakaoStore = useKakaoPlaceStore();
let myPlanStore = useMyPlansStore();
const curPlan = ref(null);


const searchSelect = ref(true);
const addSelect = ref(false);

const switchColor = (target) => {
    if (target === 'add') {
        searchSelect.value = false;
        addSelect.value = true;
        router.replace({ name: 'plan-edit-add'});
        store.places = []
        store.requestKeyword = "";
    } else {
        searchSelect.value = true;
        addSelect.value = false;
        router.replace({ name: 'plan-edit-search' });
      kakaoStore.places = [];
      kakaoStore.isMore = false;
    }
};

const listHeight = ref(300);

const updateListHeight = (resize) => {
    listHeight.value = resize.sizes[1] * 8;
};

onBeforeRouteLeave((to, from, next) => {
    if (store.selectPlaces.length > 0) {
        if (confirm('작성중인 계획이 있습니다. 이동하시겠습니까?')) {
            store.selectPlaces = [];
            store.requestTypes = new Set();
            store.places = [];
            store.requestKeyword = '';
            next();
        } else {
            next(false);
        }
    } else {
        next();
    }
});

onBeforeMount(async ()=>{
  await router.replace({name: 'plan-edit-search'});

  await myPlanStore.getPlanFindById(route.params.id)
  .then(plan => {
    curPlan.value = plan;
    store.selectPlaces = [...plan.destinations];
  })
})


</script>

<template>
    <div class="col-13" style="height: 81vh" id="createPlanContainer">
        <div class="card h-full" style="position: relative">
            <Splitter style="height: 100%" class="mb-5">
                <SplitterPanel :size="30" :minSize="30" style="overflow: scroll">
                    <div class="tabView">
                        <div style="width: 50%" class="tabPanel" :class="{ selected: searchSelect }" @click="switchColor('search')">
                            <p>장소 검색</p>
                        </div>
                        <div style="width: 50%" class="tabPanel" :class="{ selected: addSelect }" @click="switchColor('add')">
                            <p>신규 장소 등록</p>
                        </div>
                    </div>
                    <div>
                        <router-view></router-view>
                    </div>
                </SplitterPanel>
                <SplitterPanel :size="70" style="overflow: scroll">
                    <Splitter layout="vertical" @resize="updateListHeight">
                        <SplitterPanel :size="70" :min-size="10">
                            <SearchPlaceMap v-if="curPlan" />
                        </SplitterPanel>
                        <SplitterPanel :size="30" :min-size="25" >
                            <EditPlanSelectPlaceList v-if="curPlan" :listHeight="listHeight" :cur-plan="curPlan"/>
                        </SplitterPanel>
                    </Splitter>
                </SplitterPanel>
            </Splitter>
        </div>
    </div>
</template>

<style scoped lang="scss">
.tabView {
    display: flex;
    text-align: center;
    margin: 10px 0;
    cursor: pointer;
}

.tabPanel {
    padding: 10px;
}

.selected {
    color: #0076be;
    border-bottom: 1px solid #0076be;
}
</style>
