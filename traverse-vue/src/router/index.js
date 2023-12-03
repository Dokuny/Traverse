import { createRouter, createWebHistory } from 'vue-router';
import AppLayout from '@/layout/AppLayout.vue';
import { useAuthStore } from '@/stores/auth';

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            component: AppLayout,
            beforeEnter: (to, from, next) => {
                const authStore = useAuthStore();

                if (authStore.isLogin()) {
                    next();
                } else {
                    next({ name: 'login' });
                }
            },
            children: [
                {
                    path: '',
                    name: 'dashboard',
                    component: () => import('@/views/Dashboard.vue')
                },
                {
                    path: 'plan',
                    name: 'plan',
                    redirect: { name: 'plan-search', replace: true },
                    children: [
                        {
                            path: 'place/search',
                            alias: '/plan',
                            name: 'plan-search',
                            component: () => import('@/components/SearchPlaceList.vue')
                        },
                        {
                            path: 'place/add',
                            alias: '/plan',
                            name: 'plan-add',
                            component: () => import('@/components/AddPlaceList.vue')
                        }
                    ],
                    component: () => import('@/views/plan/CreatePlan.vue')
                },
                {
                    path: '/my-plan',
                    name: 'my-plan',
                    component: () => import('@/views/plan/PlanListView.vue')
                },
                {
                    path: '/my-plan/:id',
                    name: 'plan-detail',
                    component: () => import('@/views/plan/PlanDetailView.vue')
                },
                {
                    path: 'my-plan/:id/edit',
                    name: 'plan-edit',
                    redirect: { name: 'plan-edit-search', replace: true },
                    children: [
                        {
                            path: 'place/search',
                            name: 'plan-edit-search',
                            component: () => import('@/components/SearchPlaceList.vue')
                        },
                        {
                            path: 'place/add',
                            name: 'plan-edit-add',
                            component: () => import('@/components/AddPlaceList.vue')
                        }
                    ],
                    component: () => import('@/views/plan/EditPlan.vue')
                },
                {
                    path: '/record',
                    name: 'record',
                    component: () => import('@/views/recode/CreateRecode.vue')
                },
                {
                    path: '/record/:id',
                    name: 'record-detail',
                    component: () => import('@/views/recode/RecordDetailView.vue')
                },
                {
                    path: '/record/:id/edit',
                    name: 'record-edit',
                    component: () => import('@/views/recode/RecordEditView.vue')
                },
                {
                    path: '/community/record',
                    name: 'recodeList',
                    component: () => import('@/views/recode/ShareRecordListView.vue')
                },
                {
                    path: '/community/record/:id',
                    name: 'community-record',
                    component: () => import('@/views/recode/ShareRecordDetailView.vue')
                },

                {
                    path: 'profile',
                    name: 'profile',
                    component: () => import('@/views/pages/ProfileView.vue')
                },
                {
                    path: '/pages/empty',
                    name: 'empty',
                    component: () => import('@/views/pages/Empty.vue')
                },

            ]
        },
        {
            path: '/auth/login',
            name: 'login',
            component: () => import('@/views/pages/auth/LoginView.vue')
        },
        {
            path: '/auth/access',
            name: 'accessDenied',
            component: () => import('@/views/pages/auth/Access.vue')
        },
        {
            path: '/auth/error',
            name: 'error',
            component: () => import('@/views/pages/auth/Error.vue')
        },
        {
            path: '/oauth/redirected/:domain',
            name: 'login-server',
            component: () => import('@/views/pages/auth/LoginLoadingView.vue')
        }
    ]
});

export default router;
