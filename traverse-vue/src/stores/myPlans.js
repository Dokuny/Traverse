import { defineStore } from 'pinia';
import { ref } from 'vue';
import { instanceWithAuth } from '@/util/axiosInstanceSupport';
import axios from 'axios';

export const useMyPlansStore = defineStore('myPlanStore', () => {
    const myPlans = ref([]);

    const pathInfos = ref(null);

    const getMyPlans = () => {
        instanceWithAuth.get('/plans/me').then((response) => {
            myPlans.value = response.data.dataBody;
            pathInfos.value = null;
        });
    };

    const getPath = async (destinations) => {
        if (destinations.length > 1) {
            const start = destinations[0];
            const end = destinations[destinations.length - 1];

            let waypoints = [];

            if (destinations.length > 2) {
                waypoints = destinations.slice(1, destinations.length - 1).map((destination) => ({ name: destination.name, x: destination.lng, y: destination.lat }));
            }

            let response = await axios.post(
                'https://apis-navi.kakaomobility.com/v1/waypoints/directions',
                {
                    origin: {
                        name: start.name,
                        x: start.lng,
                        y: start.lat
                    },
                    destination: {
                        name: end.name,
                        x: end.lng,
                        y: end.lat
                    },
                    waypoints: waypoints,
                    priority: 'RECOMMEND',
                    car_fuel: 'GASOLINE',
                    car_hipass: false,
                    alternatives: false,
                    road_details: false
                },
                {
                    headers: {
                        Authorization: `KakaoAK ${import.meta.env.VITE_KAKAO_API_KEY}`,
                        'Content-Type': 'application/json'
                    }
                }
            );
            console.log(response.data);

            const route = response.data.routes[0];

            const locations = [];

            for (const section of route.sections) {
                for (const road of section.roads) {
                    road.vertexes.forEach((vertex, index) => {
                        if (index % 2 === 0) {
                            locations.push({ lat: road.vertexes[index + 1], lng: road.vertexes[index] });
                        }
                    });
                }
            }

            pathInfos.value = {
                locations: locations,
                duration: response.data.routes[0].summary.duration,
                distance: response.data.routes[0].summary.distance,
                taxi_fee: response.data.routes[0].summary.fare.taxi,
                toll_fee: response.data.routes[0].summary.fare.toll
            };

            return pathInfos.value;
        }
    };

    const getPlanFindById = async (planId) => {
        try {
            const response = await instanceWithAuth.get(`/plans/me/${planId}`);
            return response.data.dataBody;
        } catch (error) {
            console.log(error);
        }
    };

    return { myPlans, getMyPlans, getPath, getPlanFindById, pathInfos };
});
