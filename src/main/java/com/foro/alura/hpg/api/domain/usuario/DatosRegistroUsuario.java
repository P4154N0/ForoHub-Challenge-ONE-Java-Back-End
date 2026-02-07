package com.foro.alura.hpg.api.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO para el registro de un nuevo Usuario.
 * * Este Record asegura que los datos mínimos necesarios para crear un perfil
 * de usuario (quien luego será autor de tópicos) sean válidos.
 */
public record DatosRegistroUsuario(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "El apellido es obligatorio")
        String apellido,

        @NotBlank(message = "El correo electrónico es requerido")
        @Email(message = "El formato del correo electrónico es inválido")
        String email,

        @NotBlank(message = "La contraseña no puede estar en blanco")
        String password  // Agregamos @NotBlank porque en la DB es obligatorio
) {
}