package com.foro.alura.hpg.api.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para el registro de un nuevo Tópico.
 * * Utilizamos Bean Validation para asegurar que los datos lleguen completos
 * antes de tocar la base de datos.
 */
public record DatosRegistroTopico(
        @NotBlank(message = "El título es obligatorio")
        String titulo,

        @NotBlank(message = "El mensaje no puede estar vacío")
        String mensaje,

        @NotNull(message = "El ID del usuario es obligatorio")
        Long usuarioId,

        @NotBlank(message = "Debe especificar el curso")
        String curso
) {}
