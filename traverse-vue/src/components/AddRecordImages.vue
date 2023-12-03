<script setup>
import { useToast } from 'primevue/usetoast';
import { ref } from 'vue';
import { useAddRecordFilesStore } from '@/stores/AddRecordFiles';

const toast = useToast();

const store = useAddRecordFilesStore();

// const totalSize = ref(0);
// const totalSizePercent = ref(0);
const files = ref([]);

//사진 삭제
const onRemoveTemplatingFile = (file, removeFileCallback, index) => {
    removeFileCallback(index);

    // totalSize.value = parseInt(totalSize.value.toString()) - 1;
    console.log('삭제 후 :' + files.value.length);
    // totalSizePercent.value = totalSize.value * 10;
    console.log('현재 파일 정보 :');
    files.value.forEach((file) => console.log(file.name));
};

const onClearTemplatingUpload = (clear) => {
    clear();
    // totalSize.value = 0;
    // totalSizePercent.value = 0;
};

const onSelectedFiles = (event) => {
    files.value = event.files;
    // files.value.forEach((file) => {
    //   // totalSize.value += parseInt(formatSize(file.size));
    // });
    //   console.log("추가 전:"+ totalSize.value);
    // totalSize.value = parseInt(totalSize.value.toString()) + files.value.length;
    console.log('현재 파일 정보 :');
    files.value.forEach((file) => console.log(file.name));
};

const uploadEvent = (callback) => {
  files.value.forEach((file) => {
    store.files.push(file);
  });
  console.log(store.files);
  callback();
};

const onTemplatedUpload = () => {
    toast.add({ severity: 'info', summary: 'Success', detail: 'File Uploaded', life: 3000 });
};
</script>

<template>
    <div class="card">
        <Toast />
        <FileUpload name="demo[]" url="/api/upload" @upload="onTemplatedUpload($event)" :multiple="true" accept="image/*" :maxFileSize="1000000" @select="onSelectedFiles">
            <template #header="{ chooseCallback, uploadCallback, clearCallback, files }">
                <div class="flex flex-wrap justify-content-between align-items-center flex-1 gap-2">
                    <div class="flex gap-2">
                        <Button @click="chooseCallback()" icon="pi pi-images" rounded outlined></Button>
                        <Button @click="uploadEvent(uploadCallback)" icon="pi pi-cloud-upload" rounded outlined severity="success" :disabled="!files || files.length === 0"></Button>
                    </div>
                </div>
            </template>
            <template #content="{ files, uploadedFiles, removeUploadedFileCallback, removeFileCallback }">
                <div v-if="files.length > 0">
                    <h5>Pending</h5>
                    <div class="flex flex-wrap p-0 sm:p-5 gap-5">
                        <div v-for="(file, index) of files" :key="file.name + file.type + file.size" class="card m-0 px-6 flex flex-column border-1 surface-border align-items-center gap-3">
                            <div>
                                <img role="presentation" :alt="file.name" :src="file.objectURL" width="100" height="50" class="shadow-2" />
                            </div>
                            <span class="font-semibold">{{ file.name }}</span>
                            <Badge value="Pending" severity="warning" />
                            <Button icon="pi pi-times" @click="onRemoveTemplatingFile(file, removeFileCallback, index)" outlined rounded severity="danger" />
                        </div>
                    </div>
                </div>

                <div v-if="uploadedFiles.length > 0">
                    <h5>Completed</h5>
                    <div class="flex flex-wrap p-0 sm:p-5 gap-5">
                        <div v-for="(file, index) of uploadedFiles" :key="file.name + file.type + file.size" class="card m-0 px-6 flex flex-column border-1 surface-border align-items-center gap-3">
                            <div>
                                <img role="presentation" :alt="file.name" :src="file.objectURL" width="100" height="50" class="shadow-2" />
                            </div>
                            <span class="font-semibold">{{ file.name }}</span>
                            <Badge value="Completed" class="mt-3" severity="success" />
                            <Button icon="pi pi-times" @click="removeUploadedFileCallback(index)" outlined rounded severity="danger" />
                        </div>
                    </div>
                </div>
            </template>
            <template #empty>
                <div class="flex align-items-center justify-content-center flex-column">
                    <i class="pi pi-cloud-upload border-2 border-circle p-5 text-8xl text-400 border-400" />
                    <p class="mt-4 mb-0">Drag and drop files to here to upload.</p>
                </div>
            </template>
        </FileUpload>
    </div>
</template>

<style scoped lang="scss"></style>
