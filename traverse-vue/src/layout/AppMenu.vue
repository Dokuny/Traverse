<script setup>
import { ref } from 'vue';

import AppMenuItem from './AppMenuItem.vue';

const model = ref([
    {
        label: '홈',
        items: [{ label: '🏠 대시 보드', to: '/', category: '대시 보드' }]
    },
    {
        label: '여행 전',
        items: [
            { label: '⏱ 여행 계획 세우기', to: '/plan', category: '/plan' },
            { label: '📌 내 여행 계획', to: '/my-plan', category: '/my-plan' }
        ]
    },
    {
        label: '여행 중',
        items: [{ label: '📝 여행 기록', to: '/record', category: '여행 기록' }]
    },
    {
        label: '여행 후',
        items: [{ label: '👪 기록 공유', to: '/community/record' }]
    }
]);
</script>

<template>
    <ul class="layout-menu">
        <template v-for="(item, i) in model" :key="item">
            <app-menu-item v-if="!item.separator" :item="item" :index="i"></app-menu-item>
            <li v-if="item.separator" class="menu-separator"></li>
        </template>
    </ul>
</template>

<style lang="scss" scoped>
.logo {
    display: flex;
    align-items: center;
}
</style>
