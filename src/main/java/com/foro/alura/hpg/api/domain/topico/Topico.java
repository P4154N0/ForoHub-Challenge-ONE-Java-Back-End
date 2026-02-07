package com.foro.alura.hpg.api.domain.topico;

import com.foro.alura.hpg.api.domain.usuario.Usuario; // Asegúrate de que el paquete sea coherente con el nombre Usuario
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean activo;

    private String titulo;
    private String mensaje;

    @Column(name = "fecha_de_creacion")
    private LocalDateTime fechaDeCreacion;

    @Enumerated(EnumType.STRING) // Indica que en la DB se guardará el texto (ej. "ABIERTO") y no el número.
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY) // Relación Muchos a Uno: Muchos tópicos pertenecen a un solo Usuario.
    @JoinColumn(name = "usuario_id")   // Nombre de la columna física en la tabla 'topicos' que hace de FK.
    private Usuario autor;             // Mantener el nombre 'autor' aquí es semánticamente correcto (el usuario actúa como autor).

    private String curso;

    /**
     * Constructor para persistir un nuevo Tópico.
     * * Este constructor es el puente entre la capa de presentación (DTO) y la capa de persistencia (Entidad).
     * * @param datos Objeto Record 'DatosRegistroTopico' con la información validada del cliente.
     * @param usuario Objeto de la entidad 'Usuario' recuperado de la base de datos para la asociación.
     */
    public Topico(DatosRegistroTopico datos, Usuario usuario) {
        this.id = null; // Se inicializa en null para delegar la generación del ID a la base de datos (IDENTITY).
        this.activo = true;
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.fechaDeCreacion = LocalDateTime.now(); // Sincroniza la creación con la hora del servidor.
        this.status = Status.ABIERTO;               // Definición de estado inicial según regla de negocio.
        this.curso = datos.curso();
        this.autor = usuario;                       // Vincula este tópico con un usuario existente.
    }

    public void actualizarDatos(@Valid DatosActualizacionTopico datos) {
        //  La comprobación por medio de un if es para saber si el dato no es nulo, ya que al actualizar
        //  uno o varios de los tres campos actualizables pueden venir nulos y si los datos estarían cargados
        //  con antelación estos se perderían y pasarían a ser nulos.
        if (datos.titulo() != null) {
            this.titulo = datos.titulo();
        }

        if (datos.mensaje() != null) {
            this.mensaje = datos.mensaje();
        }

        if (datos.curso() != null) {
            this.curso = datos.curso();
        }
    }

    public void eliminar() {
        this.activo = false;
    }
}
