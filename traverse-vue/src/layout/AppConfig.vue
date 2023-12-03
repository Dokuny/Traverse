<script setup>
import Button from "primevue/button";

import { ref } from "vue";
defineProps({
    simple: {
        type: Boolean,
        default: false
    }
});

const visible = ref(false);

const onConfigButtonClick = () => {
    visible.value = !visible.value;
};

</script>

<template>
    <button class="layout-config-button p-link" type="button" @click="onConfigButtonClick()">
        <i class="pi pi-cog"></i>
    </button>


</template>

<style lang="scss" scoped></style>
