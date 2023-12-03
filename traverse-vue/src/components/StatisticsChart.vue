<script setup>
import {ref, watch} from 'vue';
import {useLayout} from '@/layout/composables/layout';

const {layoutConfig} = useLayout();
let documentStyle = getComputedStyle(document.documentElement);
let textColor = documentStyle.getPropertyValue('--text-color');
let textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
let surfaceBorder = documentStyle.getPropertyValue('--surface-border');

const props = defineProps({
  data : Object
})

const backColor = ["--blue-400", "--green-400",'--yellow-400','--cyan-400','--pink-400','--indigo-400','--teal-400','--cyan-500',"--orange-400", "--bluegray-400",'--purple-400','--red-400','--primary-400','--yellow-900','--green-900','--blue-900', '--teal-900']
const hoverColor = ["--blue-300", "--green-300",'--yellow-300','--cyan-300','--pink-300','--indigo-300','--teal-300','--cyan-400',"--orange-300", "--bluegray-300",'--purple-300','--red-300','--primary-300','--yellow-800','--green-800','--blue-800','--teal-800']


const pieData = ref(null);
const pieOptions = ref(null);

const setColorOptions = () => {
  documentStyle = getComputedStyle(document.documentElement);
  textColor = documentStyle.getPropertyValue('--text-color');
  textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
  surfaceBorder = documentStyle.getPropertyValue('--surface-border');
};

const setChart = () => {

  const back = [];
  const hover = [];
  for (let i = 0; i < props.data.data.length ; i++) {
    back.push(documentStyle.getPropertyValue(backColor[i]));
    hover.push(documentStyle.getPropertyValue(hoverColor[i]));
  }
  pieData.value = {
    labels: props.data.type,
    datasets: [
      {
        data: props.data.data,
        backgroundColor: back,
        hoverBackgroundColor: hover
      }
    ]
  };

  pieOptions.value = {
    plugins: {
      legend: {
        labels: {
          usePointStyle: true,
          color: textColor
        }
      }
    }
  };
};

watch(
  layoutConfig.theme,
  () => {
    setColorOptions();
    setChart();
  },
  {immediate: true}
);
</script>

<template>
  <div class="grid p-fluid">
    <div class="line-height-3 m-0 w-full">
      <Chart type="doughnut" v-show="pieData.labels.length !== 0" :data="pieData" :options="pieOptions"></Chart>
    </div>
  </div>
</template>
