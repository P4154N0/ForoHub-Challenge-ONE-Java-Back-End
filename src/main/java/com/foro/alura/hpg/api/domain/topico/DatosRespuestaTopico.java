package com.foro.alura.hpg.api.domain.topico;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) para representar la respuesta de un Tópico.
 * * Se utiliza para devolver solo la información necesaria al cliente,
 * evitando exponer la entidad JPA completa o datos sensibles del usuario.
 */
public record DatosRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaDeCreacion,
        String curso,
        String nombreAutor, // Devolvemos el nombre del usuario (autor) por claridad
        Status status
) {
    /**
     * Constructor compacto para convertir una entidad 'Topico' en este DTO.
     * * Facilita la creación del objeto en el Controller: 'new DatosRespuestaTopico(topico)'.
     */
    public DatosRespuestaTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaDeCreacion(),
                topico.getCurso(),
                topico.getAutor().getNombre(), // Obtenemos el nombre desde la relación
                topico.getStatus()
        );
    }
}