import { createServiceInstance } from './base.api';

export const userApi = createServiceInstance(
  import.meta.env.VITE_USER_SERVICE_URL || 'http://172.16.49.139:8081/api'
);

export default userApi;
