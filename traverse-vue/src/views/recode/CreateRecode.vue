<script setup>
import RecordListView from '@/views/recode/RecordListView.vue';
import { onBeforeMount, ref } from 'vue';
import { useCreateRecordStore } from '@/stores/createRecord';
import AddRecordDestinationItem from '@/components/AddRecordDestinationItem.vue';

let store = useCreateRecordStore();

const myRecords = ref([]);

onBeforeMount(() => {
  store.getRecords().then((datas) => {
    myRecords.value = datas;
    console.log(myRecords.value);
  });
});

const addRecord = (record) => {
  store.getRecords().then((datas) => {
    myRecords.value = datas;
  })
};
</script>

<template>
  <div className="card">
    <div className="flex justify-content-between">
      <h4>하이라이트</h4>
      <AddRecordDestinationItem @add-record="addRecord" />
    </div>
    <div className="record-menu" style="margin: 8px">
      <div className="container">
        <div className="record-menu__content">
          <RecordListView :records="myRecords" />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.record-menu {
  overflow: hidden;
}
</style>
