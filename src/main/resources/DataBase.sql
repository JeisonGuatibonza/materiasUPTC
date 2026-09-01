-- 1. Crear y seleccionar la base de datos
CREATE DATABASE IF NOT EXISTS materiasUPTC CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE materiasUPTC;

-- 2. Tabla Estudiantes
CREATE TABLE IF NOT EXISTS estudiantes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    correo VARCHAR(150) NOT NULL UNIQUE,
    fecha_nacimiento DATE NOT NULL,
    programa VARCHAR(100) NOT NULL
);

-- 3. Tabla Materias
CREATE TABLE IF NOT EXISTS materias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    creditos INT NOT NULL,
    semestre INT NOT NULL
);

-- 4. Tabla Inscripciones (Tabla intermedia con Claves Foráneas)
CREATE TABLE IF NOT EXISTS inscripciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    estudiante_id BIGINT NOT NULL,
    materia_id BIGINT NOT NULL,
    periodo VARCHAR(10) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    
    -- Definición de relaciones (Foreign Keys)
    CONSTRAINT fk_inscripciones_estudiante 
        FOREIGN KEY (estudiante_id) 
        REFERENCES estudiantes(id) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE,
        
    CONSTRAINT fk_inscripciones_materia 
        FOREIGN KEY (materia_id) 
        REFERENCES materias(id) 
        ON DELETE CASCADE 
        ON UPDATE CASCADE
);