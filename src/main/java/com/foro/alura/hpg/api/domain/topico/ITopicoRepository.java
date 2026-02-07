package com.foro.alura.hpg.api.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Interface que actúa como la capa de persistencia para la entidad Topico.
 * * Al extender de JpaRepository, Spring genera automáticamente una implementación
 * con todos los métodos CRUD (save, findAll, delete, etc.) sin que tengamos que escribir código SQL.
 * * ¿Por qué usamos Generics <Topico, Long>?
 * 1. 'Topico': Le indica a Spring que esta interfaz debe manejar objetos de la clase Topico
 * (mapeo a la tabla 'topicos' en la base de datos).
 * 2. 'Long': Especifica el tipo de dato de la llave primaria (ID) definida en la entidad Topico.
 * Esto permite que métodos como 'findById(Long id)' funcionen con el tipo de dato correcto.
 */
@Repository
public interface ITopicoRepository extends JpaRepository<Topico, Long> {

    // Verificamos duplicados (para saber si el tópico ya lo tenías)
    // Spring genera el SQL: SELECT count(*) > 0 FROM topicos WHERE titulo = ? AND mensaje = ?
    boolean existsByTituloAndMensaje(String titulo, String mensaje);

    /**
     * Busca tópicos filtrando por nombre de curso (que contenga el texto)
     * y por el año de creación.
     */
    @Query("""
            SELECT t FROM Topico t 
            WHERE t.curso LIKE %:nombreCurso% 
            AND YEAR(t.fechaDeCreacion) = :anio
            """)
    Page<Topico> buscarPorCursoYAnio(String nombreCurso, Integer anio, Pageable paginacion);

    Page<Topico> findAllByActivoTrue(Pageable paginacion);
}