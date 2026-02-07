package com.foro.alura.hpg.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  //  La etiqueta sera @RestController porque no sera una aplicación normal sino una REST.
@RequestMapping("/hello")  //  Esta clase 'HelloController' va a estar escuchando en uno cierto path de
                           //  la URL específica y es aca donde deberemos mapearla.
public class HelloController {

    //  Para que esto funcione y la pagina no nos devuelva un mensaje de:
    //  Whitelabel Error Page
    //  This application has no explicit mapping for /error, so you are seeing this as a fallback.
    //  Sun Feb 01 21:29:38 ART 2026
    //  There was an unexpected error (type=Not Found, status=404).
    //  No static resource hello.

    //  Ahora tenemos que setear a que verbo HTTP Spring va a estar escuchando.
    //  Verbos: GET - POST - PUT - DELETE
    @GetMapping  //  Va a estar respondiendo a un verbo de tipo GET en esa ruta "/hello".
    public String hello() {
        return("¡Hello World! Desde Foro Hub by P4154N0.");
    }

}
