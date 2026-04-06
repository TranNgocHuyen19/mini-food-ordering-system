/// <reference types="vite/client" />

interface ImportMetaEnv {
  readonly VITE_USER_SERVICE_URL: string;
  readonly VITE_FOOD_SERVICE_URL: string;
  readonly VITE_ORDER_SERVICE_URL: string;
  readonly VITE_PAYMENT_SERVICE_URL: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
