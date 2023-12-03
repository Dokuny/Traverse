<script setup>
import PlanDetailMap from "@/components/PlanDetailMap.vue";
import PlanDetailTimeline from "@/components/PlanDetailTimeline.vue";
import {onBeforeMount, ref} from "vue";
import {useMyPlansStore} from "@/stores/myPlans";
import {useRoute} from "vue-router";
import router from "@/router";

const store = useMyPlansStore();
const route = useRoute();

const curPlan = ref(null);



onBeforeMount(()=>{
  store.getPlanFindById(route.params.id)
  .then(plan => {
    if (plan === null) {
      router.replace({name: "error"});
    } else {
      curPlan.value = plan;
    }
  })
})

</script>

<template>
    <div>
        <Splitter style="height: 90vh" layout="vertical" class="card">
            <SplitterPanel class="flex align-items-center justify-content-center" :min-size="3" :size="50">
                <PlanDetailMap v-if="curPlan" :cur-plan="curPlan" />
            </SplitterPanel>
            <SplitterPanel class="flex align-items-center justify-content-center" :min-size="10" :size="10" style="overflow-y: auto">
                <PlanDetailTimeline v-if="curPlan" :cur-plan="curPlan"/>
            </SplitterPanel>
        </Splitter>
    </div>
</template>

<style scoped lang="scss"></style>
