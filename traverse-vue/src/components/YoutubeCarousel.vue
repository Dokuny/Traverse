<script setup>
import { onMounted, ref } from "vue";
import { instance } from "@/util/axiosInstanceSupport";

const youtubes = ref([]);
const images = ref([]);

const carouselResponsiveOptions = ref([
    {
        breakpoint: '1024px',
        numVisible: 3,
        numScroll: 3
    },
    {
        breakpoint: '768px',
        numVisible: 2,
        numScroll: 2
    },
    {
        breakpoint: '560px',
        numVisible: 1,
        numScroll: 1
    }
]);

onMounted(async () => {
    try {
        const response = await instance.get('/youtube/daily');

        const data = response.data;

        for (const youtube of data.dataBody) {
            youtubes.value.push(youtube.code);
        }
    } catch (error) {}
});
</script>

<template>
    <div class="col-12">
        <div class="card">
            <h5>📷 오늘의 인기 여행지</h5>
            <Carousel :value="youtubes" :numVisible="3" :numScroll="3" :circular="false" :responsiveOptions="carouselResponsiveOptions">
                <template #item="youtube">
                    <div class="border-round m-2 text-center p-2">
                        <div class="my-6">
                            <iframe style="border-radius: 3%" id="player" type="text/html" width="100%" height="300px" :src="`https://www.youtube.com/embed/` + youtube.data" frameborder="0"></iframe>
                        </div>
                    </div>
                </template>
            </Carousel>
        </div>
    </div>
</template>
