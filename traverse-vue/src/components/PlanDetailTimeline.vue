<script setup>
import { nextTick, reactive, ref, watch } from 'vue';
import { useMyPlansStore } from '@/stores/myPlans';
import { useToast } from 'primevue/usetoast';
import { instanceWithAuth } from '@/util/axiosInstanceSupport';
import { useConfirm } from 'primevue/useconfirm';
import router from '@/router';
import Stomp from 'webstomp-client';
import { useAuthStore } from '@/stores/auth';
import { onBeforeRouteLeave } from 'vue-router';

const myPlanStore = useMyPlansStore();
const authStore = useAuthStore();
let confirm = useConfirm();

let toast = useToast();

const props = defineProps({
    curPlan: Object
});

const items = ref([
    {
        label: '수정하기',
        icon: 'pi pi-pencil',
        command: () => {
            router.push({ name: 'plan-edit' });
        }
    },
    {
        label: '삭제하기',
        icon: 'pi pi-trash',
        command: () => {
            deleteConfirm();
        }
    }
]);

const menu = ref(null);

const toggle = (event) => {
    menu.value.toggle(event);
};

const deletePlan = (planId) => {
    instanceWithAuth.delete(`/plans/${planId}`).then((response) => {
        router.replace('/my-plan');
        toast.add({ severity: 'info', summary: '계획이 삭제되었습니다.', life: 3000 });
    });
};

const deleteConfirm = () => {
    confirm.require({
        message: '계획을 삭제하시겠습니까?',
        header: '계획 삭제',
        icon: 'pi pi-exclamation-triangle',
        rejectClass: 'p-button-text p-button-text',
        acceptClass: 'p-button-danger p-button-text',
        accept: () => {
            deletePlan(props.curPlan.plan_id);
        },
        reject: () => {}
    });
};

const chatVisible = ref(false);
const chatText = ref('');

const openChatRoom = (placeId, name) => {
    chatVisible.value = true;
    curPlaceName.value = name;
    connect(placeId);
};

const isConnected = ref(false);
let socket = reactive(null);
let stomp = reactive(null);
const curPlaceId = ref(null);
const curPlaceName = ref(null);

const connect = (placeId) => {
    const serverURL = `${import.meta.env.VITE_WS_BASE_URL}`;

    socket = new WebSocket(serverURL);
    stomp = Stomp.over(socket);

    stomp.connect({ Authorization: 'Bearer ' + authStore.getAccessToken() }, (frame) => {
        isConnected.value = true;
        curPlaceId.value = placeId;
        stomp.subscribe(`/topic/room.${placeId}`, (message) => {
            const chat = JSON.parse(message.body);

            chats.value.push({ memberId: chat.memberId, message: chat.message, nickname: chat.nickname, thumbnail: chat.thumbnail });
        });
    });
};

const disconnect = () => {
    if (isConnected.value) {
        isConnected.value = false;
        stomp.disconnect();
        curPlaceId.value = null;
        chats.value = [];
    }
};
onBeforeRouteLeave((to) => {
    disconnect();
});

// 채팅방 연결 로직
const chats = ref([]);

const sendMessage = () => {
    if (chatText.value !== '') {
        console.log(authStore.loginMember);
        const message = {
            message: chatText.value,
            nickname: authStore.loginMember.nickname,
            thumbnail: authStore.loginMember.thumbnail,
            memberId: authStore.loginMember.id
        };
        stomp.send(`/pub/chat/${curPlaceId.value}`, JSON.stringify(message), { Authorization: `Bearer ${authStore.getAccessToken()}` });
        chatText.value = '';
    }
};

const end = ref(null);

watch(chats.value, async (value, oldValue) => {
    await nextTick();

    end.value.scrollIntoView({ behavior: 'smooth' });
});
</script>

<template>
    <Panel class="h-full w-full" :header="props.curPlan.title">
        <div v-if="myPlanStore.pathInfos" class="infos">
          <span>거리 : {{myPlanStore.pathInfos.distance / 1000 }} km</span>
          <span>시간 : {{Math.floor(myPlanStore.pathInfos.duration / 3600) }} 시간 {{Math.floor((myPlanStore.pathInfos.duration % 3600) / 60)}} 분</span>
          <span>톨비 : {{myPlanStore.pathInfos.toll_fee }} 원</span>
          <span>택시비 : {{myPlanStore.pathInfos.taxi_fee }} 원</span>
        </div>
        <template #icons>
            <button class="p-panel-header-icon p-link mr-2" @click="toggle">
                <span class="pi pi-cog"></span>
            </button>
            <Menu ref="menu" id="config_menu" :model="items" popup />
        </template>
        <Timeline :value="props.curPlan.destinations" class="font">
            <template #opposite="slotProps">
                <small class="p-text-secondary chatEnter" @click="openChatRoom(slotProps.item.place_id, slotProps.item.name)">{{ slotProps.item.name }}</small>
            </template>
            <template #content="slotProps">
                {{ slotProps.item.visited_at.substring(0, 16) }}
            </template>
        </Timeline>

        <Dialog modal v-model:visible="chatVisible" :header="curPlaceName" :style="{ width: '30vw' }" :breakpoints="{ '1199px': '75vw', '575px': '90vw' }" @update:visible="disconnect">
            <div style="width: 100%; height: 500px" class="chatBody" ref="chatBody">
                <template v-for="(chat, index) in chats" :key="index">
                    <div v-if="chat.memberId !== authStore.loginMember.id" class="card flex">
                        <img class="profileThumbnail" :src="chat.thumbnail" />
                        <div>
                            <p>{{ chat.nickname }}</p>
                            <p class="message">{{ chat.message }}</p>
                        </div>
                    </div>
                    <div v-if="chat.memberId === authStore.loginMember.id" class="card flex justify-content-end">
                        <div>
                            <p style="text-align: end">{{ chat.nickname }}</p>
                            <p class="message">{{ chat.message }}</p>
                        </div>
                        <img class="profileThumbnail" :src="chat.thumbnail" />
                    </div>
                </template>
                <div ref="end" />
            </div>
            <InputText type="text" v-model="chatText" class="w-full font" @keyup.enter="sendMessage" />
        </Dialog>
    </Panel>
</template>

<style scoped lang="scss">
@media screen and (max-width: 960px) {
    ::v-deep(.customized-timeline) {
        .p-timeline-event:nth-child(even) {
            flex-direction: row !important;

            .p-timeline-event-content {
                text-align: left !important;
            }
        }

        .p-timeline-event-opposite {
            flex: 0;
        }
    }
}

.font {
    font-family: 'Pretendard-Regular';
    font-weight: 600;
}

.chatEnter {
    cursor: pointer;
}

.profileThumbnail {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    margin: 0 10px;
}
.message {
    font-family: 'Pretendard-Regular';
    word-break: break-all;
}

.infos {
  font-family: 'Pretendard-Regular';
  font-weight: 600;
  margin-bottom: 20px;
  text-align : center;
}

.chatBody {
    overflow: scroll;
}

span {
  margin : 0 10px;
}
</style>
