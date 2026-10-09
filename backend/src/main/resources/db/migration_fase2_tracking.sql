-- Migration: Fase 2 - Seguimiento de Actividad y Métricas para USEI
-- Tables: evento_acceso, vista_pasantia

CREATE TABLE IF NOT EXISTS evento_acceso (
    idevento SERIAL PRIMARY KEY,
    padron_id INT NOT NULL REFERENCES padron_estudiante(idpadron) ON DELETE CASCADE,
    fecha_hora TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    origen VARCHAR(30) DEFAULT 'WEB', -- 'WEB' | 'CORREO'
    ip VARCHAR(45)
);

CREATE TABLE IF NOT EXISTS vista_pasantia (
    idvista SERIAL PRIMARY KEY,
    padron_id INT NOT NULL REFERENCES padron_estudiante(idpadron) ON DELETE CASCADE,
    pasantias_idpasantias INT NOT NULL REFERENCES pasantias(idpasantias) ON DELETE CASCADE,
    primera_vista TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    ultima_vista TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    veces INT DEFAULT 1,
    origen VARCHAR(30) DEFAULT 'WEB', -- 'WEB' | 'CORREO'
    CONSTRAINT uq_padron_pasantia UNIQUE (padron_id, pasantias_idpasantias)
);

CREATE INDEX IF NOT EXISTS idx_evento_padron ON evento_acceso(padron_id);
CREATE INDEX IF NOT EXISTS idx_evento_fecha ON evento_acceso(fecha_hora);
CREATE INDEX IF NOT EXISTS idx_vista_padron ON vista_pasantia(padron_id);
CREATE INDEX IF NOT EXISTS idx_vista_pasantia ON vista_pasantia(pasantias_idpasantias);
