<script setup>
import { computed, onUpdated, ref, watch } from "vue";
import { Swiper, SwiperSlide } from "swiper/vue";
import "swiper/css";
import { useRouter } from "vue-router";

const router = useRouter();

const props = defineProps({
  records: Array
});

const list = ref([]);

const swiperOptions = ref({
  slidesPerView: 'auto'
});

const viewRecord = (record) => {
  router.push({
    name: 'record-detail',
    params: { id: record.record_id }
  });
};


const highlights = computed(()=>{
  return props.records.filter((value) => value.is_highlight);
})

</script>

<template>
  <div>
    <div v-if="props.records.length === 0">추가한 기록이 없어요! 나만의 여행 기록을 만들어보세요!</div>
    <div v-else>
      <!-- 하이라이트 스와이프 부분 -->
      <div v-if="highlights.length === 0" style="text-align: center">
        하이라이트로 설정한 기록이 없어요!<br />
        별 표시로 편리하게 하이라이트를 추가해보세요!
      </div>
      <div v-else>
        <!-- 레코드 뿌려주면서 하이라이트 배열에 존재한다면 하이라이트도 띄우기-->
        <swiper class="record-menu-slider col-12" :slides-per-view="swiperOptions.slidesPerView">
          <swiper-slide class="record-menu-slider__item" v-for="(highlight, index) in highlights" :key="highlight.id" style="margin: 12px" :style="{ borderRadius: '50%' }" @click="viewHighlight(index)">
            <div class="record-menu-slider__item-title">
              <!-- 첫번째 record 이미지-->
              <img class="shadow-1 img" :src="highlight.record_images[0].url" />
            </div>
          </swiper-slide>
        </swiper>
      </div>
      <h4 class="mt-6">내 기록들</h4>
      <div class="flex justify-content-center card">
        <DataView :value="props.records" layout="grid" :paginator="true" :rows="12" style="width: 1000px">
          <template #grid="slotProps">
            <div class="m-3 flex justify-content-center align-items-center" style="height: 300px; width: 300px;cursor:pointer">
              <img :src="slotProps.data.record_images[0].url" :alt="slotProps.data.title" class="shadow-1 img" @click="viewRecord(slotProps.data)" />
            </div>
          </template>
        </DataView>
      </div>
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
