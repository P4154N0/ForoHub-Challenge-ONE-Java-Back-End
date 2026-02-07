package com.foro.alura.hpg.api.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

/**
 * Interface que define la capa de acceso a datos para la entidad Usuario.
 * * Al extender JpaRepository, heredamos automáticamente todos los métodos necesarios
 * para realizar operaciones CRUD (Crear, Leer, Actualizar, Borrar) en la base de datos.
 * * ¿Por qué usamos esta configuración de Generics <Usuario, Long>?
 * 1. 'Usuario': Especifica que este repositorio gestionará objetos de la clase Usuario,
 * mapeando los resultados directamente a esta entidad.
 * 2. 'Long': Indica que el tipo de dato de la clave primaria (@Id) de la entidad Usuario es Long,
 * lo que permite a Spring Data JPA tipar correctamente las búsquedas por ID.
 */
@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByEmail(String email);
}