import { createServiceInstance } from './base.api';

export const orderApi = createServiceInstance(
  import.meta.env.VITE_ORDER_SERVICE_URL || 'http://172.16.49.152:8083/api'
);

export default orderApi;
