<script setup>
import { useRoute, useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
import { onMounted } from 'vue';

const route = useRoute();
const router = useRouter();

const domain = route.params.domain;
const authCode = route.query.code;

const authStore = useAuthStore();

onMounted(async () => {
    if (authStore.isLogin()) {
        await router.replace({ name: 'dashboard' });
    } else {
        await authStore.login(domain, authCode).then((result) => {
            if (result) {
                router.replace({ name: 'dashboard' });
            } else {
                router.replace({ name: 'error' });
            }
        });
    }
});
</script>

<template>
    <div class="surface-ground flex align-items-center justify-content-center min-h-screen min-w-screen overflow-hidden">
        <div class="flex flex-column align-items-center justify-content-center">
            <div style="border-radius: 56px; padding: 0.3rem; background: linear-gradient(180deg, #0076be 10%, rgba(33, 150, 243, 0) 30%)">
                <div class="w-full surface-card py-8 px-5 sm:px-8" style="border-radius: 53px">
                    <div class="text-center">
                        <img src="/layout/images/full-logo.png" alt="Image" height="300" />
                    </div>
                    <div class="login-btn-container">
                        <div class="w-full flex align-items-center mb-3 py-5 border-300 border-bottom-1">
                            <span class="flex justify-content-center align-items-center bg-white-500 border-round" style="height: 3.5rem; width: 3.5rem">
                                <i class="pi pi-spin pi-spinner" style="font-size: 2rem"></i>
                            </span>
                            <span class="ml-4 flex flex-column">
                                <span class="text-900 lg:text-xl font-medium mb-0">로그인 중입니다.</span>
                                <span class="text-600 lg:text-xl">기다려주세요.</span>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped lang="scss"></style>
