package com.foro.alura.hpg.api.controller;

import com.foro.alura.hpg.api.domain.usuario.DatosRegistroUsuario;
import com.foro.alura.hpg.api.domain.usuario.IUsuarioRepository;
import com.foro.alura.hpg.api.domain.usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController               //  La etiqueta sera @RestController porque no sera una aplicación normal sino una REST.
@RequestMapping("/usuarios")  //  Esta clase 'UsuarioController' va a estar escuchando en uno cierto path de
                              //  la URL específica y es acá donde deberemos mapearla.
public class UsuarioController {

    /**
     * Inyección de dependencias del repositorio de Usuarios.
     * * Gracias a la anotación @Autowired, Spring Boot localiza la implementación
     * automática de IUsuarioRepository y la instancia por nosotros.
     * * Esto nos permite interactuar con la tabla de usuarios en la base de datos
     * (buscar, guardar, validar) sin necesidad de inicializar la variable manualmente con 'new'.
     */
    @Autowired
    private IUsuarioRepository iUsuarioRepository;


//    @PostMapping
//    public void registrar(@RequestBody String json) {  //  Con esto le decimos que el parámetro recibido es un String
//                                                       //  que viene especificamente en el 'body' de la request.
//        System.out.println("JSON para usuario: " + json);
//    }

    @PostMapping
    public void registrar(@RequestBody @Valid DatosRegistroUsuario datos) {  //  Con esto le decimos que el parámetro recibido es un String
        //  que viene especificamente en el 'body' de la request.
        System.out.println("JSON para usuario: " + datos);

        Usuario usuario = new Usuario(datos);

        iUsuarioRepository.save(usuario);

    }

}
