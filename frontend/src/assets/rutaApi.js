const backendOrigin = (import.meta.env.VITE_BACKEND_ORIGIN || "").replace(/\/+$/, "");
const apiPath = (import.meta.env.VITE_BACKEND_URL || "/api/v1").replace(/^\/+/, "");
const RutaApi = backendOrigin ? `${backendOrigin}/${apiPath}` : `/${apiPath}`;

export default RutaApi;
