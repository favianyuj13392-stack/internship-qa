-- Migration: Fase 1 - Padrón de Estudiantes y Control de Acceso Institucional
-- Tables: importacion_padron, importacion_padron_error, padron_estudiante

CREATE TABLE IF NOT EXISTS importacion_padron (
    idimportacion SERIAL PRIMARY KEY,
    admin_kcuuid VARCHAR(100),
    nombre_archivo VARCHAR(255),
    hash_archivo VARCHAR(64),
    fecha TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    modo VARCHAR(20) NOT NULL, -- 'SIMULACION' | 'APLICADO'
    total_nuevos INT DEFAULT 0,
    total_actualizados INT DEFAULT 0,
    total_sin_cambios INT DEFAULT 0,
    total_errores INT DEFAULT 0,
    total_desactivados INT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS importacion_padron_error (
    iderror SERIAL PRIMARY KEY,
    importacion_padron_id INT NOT NULL REFERENCES importacion_padron(idimportacion) ON DELETE CASCADE,
    fila INT NOT NULL,
    campo VARCHAR(100),
    motivo VARCHAR(500)
);

CREATE TABLE IF NOT EXISTS padron_estudiante (
    idpadron SERIAL PRIMARY KEY,
    correo VARCHAR(150) NOT NULL UNIQUE,
    nombres VARCHAR(150),
    apellidos VARCHAR(150),
    carreras_idcarreras INT REFERENCES carreras(idcarreras),
    estado VARCHAR(30) DEFAULT 'ACTIVO', -- 'ACTIVO' | 'INACTIVO' | 'EGRESADO'
    anio_ingreso INT,
    codigo_estudiante VARCHAR(50),
    kc_uuid VARCHAR(100),
    usuarios_idusuarios INT REFERENCES usuarios(idusuarios),
    primer_acceso TIMESTAMP WITHOUT TIME ZONE,
    ultimo_acceso TIMESTAMP WITHOUT TIME ZONE,
    importacion_padron_id INT REFERENCES importacion_padron(idimportacion)
);

CREATE INDEX IF NOT EXISTS idx_padron_correo ON padron_estudiante(LOWER(correo));
CREATE INDEX IF NOT EXISTS idx_padron_kcuuid ON padron_estudiante(kc_uuid);
CREATE INDEX IF NOT EXISTS idx_padron_estado ON padron_estudiante(estado);
CREATE INDEX IF NOT EXISTS idx_padron_carrera ON padron_estudiante(carreras_idcarreras);
