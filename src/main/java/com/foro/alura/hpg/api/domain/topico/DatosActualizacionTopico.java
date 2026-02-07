package com.foro.alura.hpg.api.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizacionTopico(
        @NotNull(message = "El ID del Tópico es obligatorio")
        Long id,

        String titulo,
        String mensaje,
        String curso
) {}
