<script setup>

import {useRouter} from "vue-router";

const router = useRouter();

const login = (domain) => {
  window.location.href = `${import.meta.env.VITE_API_BASE_URL}/auth/${domain}`;
};
</script>

<template>
  <div class="surface-ground flex align-items-center justify-content-center min-h-screen min-w-screen overflow-hidden" style="background-image: url('/common/back.jpg'); background-size: cover; background-position: center;  backdrop-filter: blur(10px);">
    <div class="flex flex-column align-items-center justify-content-center">
      <div style="border-radius: 56px; padding: 0.3rem; background: linear-gradient(180deg, #0076be 10%, rgba(33, 150, 243, 0) 30%);">
        <div class="w-full surface-card py-8 px-5 sm:px-8" style="border-radius: 53px">
          <div class="text-center">
            <img src="/layout/images/full-logo.png" alt="Image" height="300" />
          </div>
          <div>
            <Button label="네이버로 로그인" class="p-button-raised p-button-success w-full p-3 text-xl naver-logo" @click="login('naver')"></Button>
            <Button label="카카오로 로그인" class="p-button-raised p-button-warning w-full p-3 text-xl kakao-logo" @click="login('kakao')"></Button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.p-button-success {
  margin: 10px 0;
}

.kakao-logo:before {
  content: url('/icon/btn_kakao.svg');
}

.naver-logo:before {
  content: url('/icon/btn_naver.svg');
}


</style>
