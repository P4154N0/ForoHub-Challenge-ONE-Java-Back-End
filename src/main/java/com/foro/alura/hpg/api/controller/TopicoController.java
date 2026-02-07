package com.foro.alura.hpg.api.controller;

import com.foro.alura.hpg.api.domain.topico.*;
import com.foro.alura.hpg.api.domain.usuario.Usuario;
import com.foro.alura.hpg.api.domain.usuario.IUsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController              //  La etiqueta sera @RestController porque no sera una aplicación normal sino una REST.
@RequestMapping("/topicos")  //  Esta clase 'TopicoController' va a estar escuchando en uno cierto path de
                             //  la URL específica y es acá donde deberemos mapearla.
public class TopicoController {

    /**
     * Inyección de dependencias del repositorio de Usuarios.
     * * Gracias a la anotación @Autowired, Spring Boot localiza la implementación
     * automática de IUsuarioRepository y la instancia por nosotros.
     * * Esto nos permite interactuar con la tabla de usuarios en la base de datos
     * (buscar, guardar, validar) sin necesidad de inicializar la variable manualmente con 'new'.
     */
    @Autowired
    private IUsuarioRepository iUsuarioRepository;

    /**
     * Inyección de dependencias del repositorio de Tópicos.
     * * Al utilizar @Autowired, delegamos a Spring la gestión del ciclo de vida de esta variable,
     * conectando nuestra lógica con la implementación automática de JpaRepository.
     * * A través de esta instancia, podemos realizar todas las operaciones CRUD (Guardar,
     * Listar, Actualizar, Eliminar) directamente sobre la tabla de tópicos.
     */
    @Autowired
    private ITopicoRepository iTopicoRepository;

//    //  Forma 1.
//    //  Esta es la manera más simple de recibir datos desde un JSON.
//    //  NOTA: no es la mejor forma yá que lo que estamos recibiendo es un String el cual si quisieramos obtener un dato
//    //  de alguno de los atributos en particular tendíamos que tratar mucho el String.
//    @PostMapping //  Va a estar respondiendo a un verbo de tipo POST en esa ruta "/topicos".
//    public void registrar(@RequestBody String json) {  //  Con esto le decimos que el parámetro recibido es un String
//                                                       //  que viene especificamente en el 'body' de la request.
//        System.out.println("JSON para topico: " + json);
//    }

    //  Forma 2.
    /**
     * Método para registrar un nuevo tópico en el sistema.
     * @param datos DTO (Record) que representa el JSON recibido en la solicitud.
     * * El proceso sigue tres pasos clave:
     * 1. Vinculación: Obtenemos una referencia del Usuario desde la DB usando el ID del JSON.
     * 2. Mapeo: Transformamos los datos de entrada (DTO) en una entidad de persistencia (Topico).
     * 3. Persistencia: El repositorio guarda la entidad, ejecutando el INSERT en MySQL.
     */
    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroTopico datos, UriComponentsBuilder uriBuilder) {
        // Imprimimos el Record en consola para verificar que Jackson parseó bien el JSON.
        System.out.println("JSON recibido: " + datos);

        // 1. REGLA DE NEGOCIO: Verificamos duplicados antes de procesar nada.
        // Le pedimos al repositorio que ejecute un SELECT EXISTS en la base de datos.
        if (iTopicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            return ResponseEntity.badRequest().body("Error: El tópico ya existe (mismo título y mensaje).");
        }

        // 2. VINCULACIÓN: Obtenemos una referencia del autor.
        // No trae todos los datos del usuario, solo prepara su ID para la relación (FK).
        Usuario usuario = iUsuarioRepository.getReferenceById(datos.usuarioId());

        // 3. PERSISTENCIA: Transformamos el DTO a Entidad y guardamos.
        // El constructor de Topico ya asigna la fecha y el status 'ABIERTO' automáticamente.
        Topico topico = new Topico(datos, usuario);
        iTopicoRepository.save(topico);

        // 4. CONSTRUCCIÓN DE LA RESPUESTA (La parte de la URI):
        // ¿Qué hace esta línea paso a paso?
        // .path("/topicos/{id}") -> Define la estructura de la URL. El {id} es un "marcador de posición".
        // .buildAndExpand(topico.getId()) -> Toma el ID que generó la base de datos y lo pone en el {id}.
        // .toUri() -> Convierte toda esa construcción en un objeto URI real (una dirección web).
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        // 5. RESPUESTA FINAL:
        // Retornamos el código 201 Created.
        // .created(uri) -> Agrega un encabezado llamado 'Location' con la dirección del nuevo tópico.
        // .body(...) -> Envía en el cuerpo del JSON los datos que acabamos de guardar.
        return ResponseEntity.created(uri).body(new DatosRespuestaTopico(topico));
    }

//    /**
//     * Endpoint para listar todos los tópicos sin paginación.
//     * * Este método recupera la lista completa de la base de datos, la transforma
//     * de Entidades (JPA) a DTOs de respuesta y la retorna al cliente.
//     * * Nota: No se recomienda para tablas con grandes volúmenes de datos.
//     * * @return Una lista de objetos DatosRespuestaTopico.
//     */
//    @GetMapping("/lista-completa") // Ruta diferenciada para pruebas
//    public List<DatosRespuestaTopico> listarTodo() {
//        // .findAll(): Recupera todos los registros de la tabla 'topicos'.
//        // .stream(): Inicia un flujo de datos para procesar la lista.
//        // .map(DatosRespuestaTopico::new): Transforma cada Entidad en un DTO usando su constructor.
//        // .toList(): Convierte el flujo procesado de vuelta a una lista de Java.
//        return iTopicoRepository.findAll().stream()
//                .map(DatosRespuestaTopico::new)
//                .toList();
//    }


    /**
     * Endpoint para obtener el detalle de un tópico específico por su ID.
     * * @param id Identificador único del tópico (recibido via @PathVariable).
     * @return ResponseEntity con los datos del tópico o 404 si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity detallarTopico(@PathVariable Long id) {
        // 1. VERIFICACIÓN Y CONSULTA:
        // Buscamos el tópico en la base de datos. Usamos findById porque devuelve un Optional,
        // lo que nos permite manejar de forma elegante si el ID no existe.
        var topicoOptional = iTopicoRepository.findById(id);

        if (topicoOptional.isEmpty()) {
            // REGLA DE NEGOCIO: Si el ID no se ingresó correctamente o no existe,
            // devolvemos un 404 Not Found.
            return ResponseEntity.notFound().build();
        }

        // 2. RESPUESTA:
        // Si existe, extraemos la entidad y la convertimos a nuestro DTO de respuesta.
        var topico = topicoOptional.get();
        return ResponseEntity.ok(new DatosRespuestaTopico(topico));
    }

    /**
     * Endpoint para listar tópicos de forma paginada y eficiente.
     * * Utiliza el objeto 'Pageable' de Spring Data para controlar el número de página,
     * el tamaño de la misma y el ordenamiento (ej: /topicos?size=10&page=0&sort=fechaDeCreacion,desc).
     * * @param paginacion Objeto que captura los parámetros de consulta de la URL.
     * @return Un objeto Page con los datos del tópico e información de la paginación.
     */
    @GetMapping
    public ResponseEntity<Page<DatosRespuestaTopico>> listar(@PageableDefault(size = 10, sort = {"fechaDeCreacion"}) Pageable paginacion) {
        // 1. Invocamos al repositorio pasando el objeto de paginación.
        // 2. Spring Data JPA ejecuta un 'SELECT' con 'LIMIT' y 'OFFSET' automáticamente.
        // 3. Transformamos el contenido de la página (.map) manteniendo la estructura de Page.

        //  Return para utilizar con el método de eliminación física.
        var page = iTopicoRepository.findAll(paginacion).map(DatosRespuestaTopico::new);

        //  Return para utilizar con el método de eliminación lógica.
        //var page = iTopicoRepository.findAllByActivoTrue(paginacion).map(DatosRespuestaTopico::new);

        // 4. Retornamos la respuesta envuelta en un ResponseEntity con estatus 200 OK
        return ResponseEntity.ok(page);
    }

    /**
     * Endpoint para listar tópicos con filtros de búsqueda opcionales.
     * * Criterios: Nombre del curso (búsqueda parcial) y año específico.
     * * @param curso Nombre o parte del nombre del curso.
     * @param anio Año de creación (ej: 2026).
     * @param paginacion Configuración de página y orden.
     */
    @GetMapping("/buscar")
    public ResponseEntity<Page<DatosRespuestaTopico>> listarConFiltros(
            @RequestParam(required = false) String curso,
            @RequestParam(required = false) Integer anio,
            @PageableDefault(size = 10, sort = "fechaDeCreacion") Pageable paginacion) {

        Page<Topico> pagina;

        // Lógica de filtrado:
        if (curso != null && anio != null) {
            // Si ambos filtros están presentes, usamos el método personalizado
            pagina = iTopicoRepository.buscarPorCursoYAnio(curso, anio, paginacion);
        } else {
            // Si no hay filtros, devolvemos el listado normal
            pagina = iTopicoRepository.findAll(paginacion);
        }

        // Transformamos la página de Entidades a DTOs
        var respuesta = pagina.map(DatosRespuestaTopico::new);
        return ResponseEntity.ok(respuesta);
    }

    /**
     * Endpoint para actualizar los datos de un tópico existente.
     * * @param datos DTO que contiene el ID del tópico y los campos a modificar.
     * @return ResponseEntity con el código 200 OK y los datos actualizados.
     */
    @PutMapping
    @Transactional
    public ResponseEntity actualizar(@RequestBody @Valid DatosActualizacionTopico datos) {
        // 1. Buscamos la referencia del tópico en la base de datos por su ID.
        // getReferenceById es eficiente porque no trae todos los datos de inmediato (Lazy).
        Topico topico = iTopicoRepository.getReferenceById(datos.id());

        // 2. Ejecutamos la lógica de actualización definida en la entidad.
        // Al estar dentro de una @Transactional, Hibernate detecta los cambios
        // y hace el UPDATE en la base de datos automáticamente al terminar el método.
        topico.actualizarDatos(datos);

        // 3. Retornamos un 200 OK junto con el DTO de respuesta.
        // Usamos DatosRespuestaTopico para no exponer la entidad completa y mantener la coherencia.
        return ResponseEntity.ok(new DatosRespuestaTopico(topico));
    }

    /**
     * Endpoint para eliminar un tópico del sistema.
     * * @param id El identificador único del tópico recibido en la URL.
     * @return 204 No Content si la operación es exitosa, o 404 Not Found si el ID no existe.
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminar(@PathVariable Long id) {
        // 1. VERIFICACIÓN: Validamos que el tópico exista antes de intentar borrarlo.
        // Esto evita excepciones innecesarias y nos permite dar una respuesta clara (404).
        if (!iTopicoRepository.existsById(id)) {
            // Si no existe, devolvemos un 404 para informar al cliente.
            return ResponseEntity.notFound().build();
        }

        // 2.
        // OPCIÓN A: ELIMINACIÓN FÍSICA: Se ejecuta un DELETE directo en la base de datos.
        iTopicoRepository.deleteById(id);

        // 2.
        // OPCIÓN B: Eliminación lógica (Recomendada para FORO HUB).
        // Cambia el campo 'activo' a false para que no aparezca en los listados pero persista la historia.
        //var topico = iTopicoRepository.getReferenceById(id);
        //topico.eliminar(); // Este método en la Entidad hace: this.activo = false;

        // 3.
        // OPCIÓN A.
        // RESPUESTA: Según el estándar REST, un borrado exitoso devuelve 204 (No Content).
        return ResponseEntity.noContent().build();

        // 3.
        // OPCIÓN B.
        // RESPUESTA: Retornamos 200 OK con el cuerpo del tópico eliminado (para ver el tópico que borramos).
        // Obtenemos los datos antes de borrar para poder devolverlos
        //var topico = iTopicoRepository.getReferenceById(id);
        //var datosRespuesta = new DatosRespuestaTopico(topico);
        //return ResponseEntity.ok(datosRespuesta);
    }
}
