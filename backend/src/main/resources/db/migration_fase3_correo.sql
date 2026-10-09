-- Migration Fase 3: Trazabilidad y Auditoría de Notificaciones por Correo Electrónico
-- UCB Internship System - USEI

CREATE TABLE IF NOT EXISTS envio_correo_pasantia (
    idenvio SERIAL PRIMARY KEY,
    pasantias_idpasantias INT NOT NULL,
    padron_id INT,
    correo_destinatario VARCHAR(150) NOT NULL,
    estado VARCHAR(30) NOT NULL DEFAULT 'ENVIADO', -- ENCOLADO, ENVIADO, FALLIDO
    fecha_envio TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    intentos INT DEFAULT 1,
    error_mensaje TEXT,
    CONSTRAINT fk_envio_correo_pasantia FOREIGN KEY (pasantias_idpasantias) REFERENCES pasantias(idpasantias) ON DELETE CASCADE,
    CONSTRAINT fk_envio_correo_padron FOREIGN KEY (padron_id) REFERENCES padron_estudiante(idpadron) ON DELETE SET NULL
);

-- Índices para optimizar consultas de auditoría y métricas de alcance por convocatoria
CREATE INDEX IF NOT EXISTS idx_envio_correo_pasantia ON envio_correo_pasantia(pasantias_idpasantias);
CREATE INDEX IF NOT EXISTS idx_envio_correo_padron ON envio_correo_pasantia(padron_id);
CREATE INDEX IF NOT EXISTS idx_envio_correo_estado ON envio_correo_pasantia(estado);
CREATE INDEX IF NOT EXISTS idx_envio_correo_fecha ON envio_correo_pasantia(fecha_envio);
