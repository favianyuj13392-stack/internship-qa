/**
 * Configuración centralizada de videos tutoriales.
 * Permite cambiar los videos de cualquier sección sin alterar los templates de las vistas.
 */

/**
 * Convierte cualquier formato de URL de YouTube (embed, watch, youtu.be, shorts)
 * a una URL embed válida y segura.
 *
 * @param {string} url - URL original de YouTube
 * @returns {string} URL lista para iframe embed
 */
export function toYouTubeEmbedUrl(url) {
  if (!url || typeof url !== "string") return "";
  const trimmed = url.trim();

  // Si ya es una URL de embed, retornarla directamente
  if (trimmed.includes("/embed/")) {
    return trimmed;
  }

  // Formato corto: https://youtu.be/<id>
  const shortMatch = trimmed.match(/youtu\.be\/([a-zA-Z0-9_-]+)/);
  if (shortMatch && shortMatch[1]) {
    return `https://www.youtube.com/embed/${shortMatch[1]}?autoplay=1`;
  }

  // Formato estándar: https://www.youtube.com/watch?v=<id>
  const watchMatch = trimmed.match(/[?&]v=([a-zA-Z0-9_-]+)/);
  if (watchMatch && watchMatch[1]) {
    return `https://www.youtube.com/embed/${watchMatch[1]}?autoplay=1`;
  }

  // Formato shorts: https://www.youtube.com/shorts/<id>
  const shortsMatch = trimmed.match(/\/shorts\/([a-zA-Z0-9_-]+)/);
  if (shortsMatch && shortsMatch[1]) {
    return `https://www.youtube.com/embed/${shortsMatch[1]}?autoplay=1`;
  }

  return trimmed;
}

export const TUTORIAL_VIDEOS = {
  // Administrador - Solicitud de Pasantías
  ADMIN_SOLICITUD_PASANTIA: {
    key: "ADMIN_SOLICITUD_PASANTIA",
    title: "Solicitudes de pasantías",
    description: "Lugar donde podrás ver las solicitudes de pasantías.",
    videoUrl: "https://www.youtube.com/embed/S_CGed6E610?feature=oembed",
  },
  // Administrador - Empresas
  ADMIN_EMPRESAS: {
    key: "ADMIN_EMPRESAS",
    title: "Empresas",
    description: "Gestión y administración de empresas registradas.",
    videoUrl: "https://www.youtube.com/embed/S_CGed6E610?feature=oembed",
  },
  // Administrador - Solicitudes de Registro de Empresa
  ADMIN_SOLICITUD_EMPRESAS: {
    key: "ADMIN_SOLICITUD_EMPRESAS",
    title: "Solicitudes de empresas",
    description: "Revisión y aprobación de nuevas empresas solicitantes.",
    videoUrl: "https://www.youtube.com/embed/S_CGed6E610?feature=oembed",
  },
  // Administrador - Pasantías
  ADMIN_PASANTIAS: {
    key: "ADMIN_PASANTIAS",
    title: "Pasantías",
    description: "Administración y supervisión de ofertas de pasantías.",
    videoUrl: "https://www.youtube.com/embed/S_CGed6E610?feature=oembed",
  },
  // Administrador - Pasantías sin Aplicantes
  ADMIN_PASANTIAS_SIN_APLICANTES: {
    key: "ADMIN_PASANTIAS_SIN_APLICANTES",
    title: "Pasantías sin postulantes",
    description: "Revisión de ofertas de pasantías que aún no tienen postulantes.",
    videoUrl: "https://www.youtube.com/embed/S_CGed6E610?feature=oembed",
  },
  // Completar Registro - Estudiante
  REGISTRO_ESTUDIANTE: {
    key: "REGISTRO_ESTUDIANTE",
    title: "Registro de estudiante",
    description: "Guía paso a paso para completar tus datos de estudiante.",
    videoUrl: "https://www.youtube.com/embed/S_CGed6E610?feature=oembed",
  },
  // Completar Registro - Empresa
  REGISTRO_EMPRESA: {
    key: "REGISTRO_EMPRESA",
    title: "Registro de empresa",
    description: "Guía para dar de alta tu empresa en el programa de pasantías.",
    videoUrl: "https://www.youtube.com/embed/S_CGed6E610?feature=oembed",
  },
  // Home - Mejores Empresas
  HOME_BEST_COMPANIES: {
    key: "HOME_BEST_COMPANIES",
    title: "Encuentra pasantías en las mejores empresas",
    description: "Conoce las mejores empresas asociadas.",
    videoUrl: "https://www.youtube.com/embed/vJl3o_5Mmkw?si=w5e9F26u_KmIbyrd",
  },
  // Home - Two Job CTA
  HOME_TWO_JOB_CTA: {
    key: "HOME_TWO_JOB_CTA",
    title: "Video Tutorial General",
    description: "Plataforma de pasantías UCB.",
    videoUrl: "https://www.youtube.com/embed/vJl3o_5Mmkw?si=w7jC8V4wzHRp_-lS",
  },
};

/**
 * Obtiene la configuración de video por clave con URL embebida lista para iframe.
 *
 * @param {string} key - Clave del video definida en TUTORIAL_VIDEOS
 * @returns {{ key: string, title: string, description: string, videoUrl: string, embedUrl: string }}
 */
export function getTutorialVideo(key) {
  const item = TUTORIAL_VIDEOS[key];
  if (!item) {
    return {
      key: key || "DEFAULT",
      title: "Tutorial",
      description: "",
      videoUrl: "https://www.youtube.com/embed/S_CGed6E610?feature=oembed",
      embedUrl: "https://www.youtube.com/embed/S_CGed6E610?feature=oembed",
    };
  }
  return {
    ...item,
    embedUrl: toYouTubeEmbedUrl(item.videoUrl),
  };
}

export default {
  TUTORIAL_VIDEOS,
  toYouTubeEmbedUrl,
  getTutorialVideo,
};
