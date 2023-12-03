<script setup>
import { useConfirm } from 'primevue/useconfirm';
import { ref } from 'vue';
import { useToast } from 'primevue/usetoast';
import { useAuthStore } from '@/stores/auth';
import { instanceWithAuth } from '@/util/axiosInstanceSupport';

const toast = useToast();
const confirm = useConfirm();
const authStore = useAuthStore();

const update = (event) => {
    confirm.require({
        target: event.currentTarget,
        message: '프로필 수정을 진행하시겠습니까?',
        icon: 'pi pi-exclamation-triangle',
        accept: () => {
            toast.add({ severity: 'info', summary: '프로필이 업데이트 되었습니다.', life: 3000 });
            updateProfile();
        },
        reject: () => {}
    });
};

const resign = (event) => {
    confirm.require({
        target: event.currentTarget,
        message: '회원님과 관련된 모든 정보가 삭제됩니다.',
        header: '정말로 탈퇴하시겠습니까?',
        icon: 'pi pi-info-circle',
        acceptClass: 'p-button-danger p-button-sm',
        accept: () => {
            toast.add({ severity: 'info', summary: 'Confirmed', detail: 'Record deleted', life: 3000 });
            authStore.logout();
          window.location.href = "/auth/login";
        },
        reject: () => {
        }
    });
};

const selectedFile = ref(null);
const imageUrl = ref(authStore.loginMember.thumbnail);
const nickname = ref(authStore.loginMember.nickname);
const email = ref(authStore.loginMember.email);

const handleFileChange = (event) => {
    selectedFile.value = event.target.files[0];

    console.log(selectedFile.value);

    if (selectedFile.value) {
        // 이미지를 읽어와서 화면에 표시
        const reader = new FileReader();
        reader.onload = () => {
            imageUrl.value = reader.result;
        };
        reader.readAsDataURL(selectedFile.value);
    }
};

const updateProfile = () => {
    // 여기서 프로필 이미지 변경되었는지 확인 후, 업로드해야함

    const formData = new FormData();

    const form = {
        nickname: nickname.value,
        email: email.value
    };

    formData.append('form', new Blob([JSON.stringify(form)],{type:"application/json"}));
    if (selectedFile.value) {
        formData.append('thumbnail', selectedFile.value);
    }

    instanceWithAuth
        .put('/members/me', formData, {
            headers: { 'Content-Type': 'multipart/form-data' }
        })
        .then((response) => {
            const userInfo = response.data.dataBody;
            authStore.updateMember(userInfo);
        });
};
</script>

<template>
    <div class="card">
        <div class="flex justify-content-center">
            <label for="profileImage">
                <img :src="imageUrl" alt="Uploaded Image" v-if="imageUrl" class="profileImage" />
            </label>
            <input type="file" @change="handleFileChange" style="display: none" id="profileImage" />
        </div>
        <div>
            <form @submit="onSubmit" class="flex flex-column gap-2">
                <label for="nickname">닉네임</label>
                <InputText id="nickname" v-model="nickname" />

                <label for="email">이메일</label>
                <InputText id="email" v-model="email" />
                <small class="p-error" id="text-error">{{ errorMessage || '&nbsp;' }}</small>
                <div class="btns">
                    <Button class="m-2" @click="update($event)" icon="pi pi-check" label="프로필 수정"></Button>
                    <Button class="m-2" @click="resign($event)" icon="pi pi-times" label="회원 탈퇴" severity="danger"></Button>
                </div>
            </form>
        </div>
    </div>
</template>

<style scoped lang="scss">
.btns {
    display: flex;
    justify-content: center;
}

.profileImage {
    border-radius: 50%;
    width: 200px;
    height: 200px;
    object-fit: cover;
}
</style>
