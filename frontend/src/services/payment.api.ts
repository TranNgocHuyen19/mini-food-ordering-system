import { createServiceInstance } from './base.api';

export const paymentApi = createServiceInstance(
  import.meta.env.VITE_PAYMENT_SERVICE_URL || 'http://172.16.34.168:8080/api'
);

export default paymentApi;
