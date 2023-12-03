import axios, { AxiosError } from 'axios';
import { useAuthStore } from '@/stores/auth';

const authStore = useAuthStore();

function createInstance() {
    return axios.create({ baseURL: import.meta.env.VITE_API_BASE_URL });
}

function createInstanceWithAuth() {
    const instance = axios.create({ baseURL: import.meta.env.VITE_API_BASE_URL });

    instance.interceptors.request.use(
        async (config) => {
            const refresh = authStore.getRefreshToken();
            const access = authStore.getAccessToken();

            config.headers.Authorization = `Bearer ${access}`;
            // config.headers['Content-Type'] = 'application/json';

            return config;
        },
        (error) => {
            return Promise.reject(error);
        }
    );

    instance.interceptors.response.use(
        (response) => {
            return response;
        },
        async (error) => {
            console.log(error);
            if (error.response.status === 401) {
                const resultCode = error.response.data.dataHeader.resultCode;

                console.log(resultCode);
                if (resultCode === 'EXPIRED_TOKEN') {

                    await authStore.refreshTokens(authStore.getAccessToken(), authStore.getRefreshToken())
                    .then((result) => {
                        if (result) {
                           // 요청 재시도
                            return instance.request(error.config);
                        }else {
                            authStore.logout();
                          window.location.href = '/auth/login';
                        }
                    });
                }
            } else {
                // window.location.href = '/auth/error';
            }

        }
    );

    return instance;
}

export const instance = createInstance();
export const instanceWithAuth = createInstanceWithAuth();
