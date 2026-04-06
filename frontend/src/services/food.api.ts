import { createServiceInstance } from './base.api';

export const foodApi = createServiceInstance(
  import.meta.env.VITE_FOOD_SERVICE_URL || 'http://172.16.48.89:8083/api'
);

export default foodApi;
