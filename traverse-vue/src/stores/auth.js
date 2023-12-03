import { ref } from "vue";
import { defineStore } from "pinia";
import { useCookies } from "vue3-cookies";
import axios from "axios";

const { cookies } = useCookies();

export const useAuthStore = defineStore(
  'auth',
  () => {
    const loginMember = ref(null);

    const isLogin = () => {
      const refreshToken = getRefreshToken();
      return refreshToken !== null;
    };

    const login = async (domain, code) => {
      try {
        const url = `${import.meta.env.VITE_API_BASE_URL}/auth/${domain}/login`;
        await axios.get(url, { params: { code: code } }).then((response) => {
          const data = response.data;
          setMember(data);
        });
        return true;
      } catch (error) {
        return false;
      }
    };

    const updateMember = (userInfo) => {
      loginMember.value = {
        id: userInfo.id,
        email: userInfo.email,
        nickname: userInfo.nickname,
        thumbnail: userInfo.thumbnail
      };
    };

    const setMember = (data) => {
      loginMember.value = {
        id: data.dataBody.memberInfo.id,
        email: data.dataBody.memberInfo.email,
        nickname: data.dataBody.memberInfo.nickname,
        thumbnail: data.dataBody.memberInfo.thumbnail
      };
      cookies.keys().forEach((cookie) => cookies.remove(cookie));
      cookies.set('Authorization', data.dataBody.tokens.accessToken, import.meta.env.VITE_ACCESS_TIME);
      cookies.set('Refresh', data.dataBody.tokens.refreshToken, import.meta.env.VITE_ACCESS_TIME);
    };

    const logout = async () => {
      cookies.keys().forEach((cookie) => cookies.remove(cookie));
    };

    const getAccessToken = () => {
      return cookies.get('Authorization');
    };

    const getRefreshToken = () => {
      return cookies.get('Refresh');
    };

    const refreshTokens = async (access, refresh) => {
      const result = await axios
      .post(`${import.meta.env.VITE_API_BASE_URL}/auth/refresh`, {
        accessToken: access,
        refreshToken: refresh
      })
      .then((response) => {
        const data = response.data;
        setMember(data);

        return true;
      })
      .catch((error) => {
        cookies.keys().forEach((cookie) => cookies.remove(cookie));
        return false;
      });

      return result;
    };

    return { login, isLogin, logout, getAccessToken, refreshTokens, getRefreshToken, loginMember, updateMember };
  },
  { persist: true }
);
