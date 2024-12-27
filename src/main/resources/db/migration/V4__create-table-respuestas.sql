CREATE TABLE respuestas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_de_creacion VARCHAR(255) NOT NULL,
    solucion VARCHAR(255) NOT NULL,
    autor_id BIGINT,
    topico_id BIGINT,
    FOREIGN KEY (autor_id) REFERENCES autores(id),
    FOREIGN KEY (topico_id) REFERENCES topicos(id)
);