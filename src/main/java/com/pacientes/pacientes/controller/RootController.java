package com.pacientes.pacientes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint raíz dedicado exclusivamente a satisfacer el health check por
 * defecto del ALB/Target Group de ECS (que golpea "/" cada ~30s esperando
 * un 200). Sin este endpoint, esas peticiones caían en 404 (no hay ningún
 * @RequestMapping para "/"), lo que hace que ECS marque la tarea como no
 * saludable y la reinicie en bucle — dejando el servicio sin targets
 * registrados durante varios minutos en cada ciclo, y propagando 503 a
 * quien dependa de este servicio (auth-service, bff).
 */
@RestController
public class RootController {

    @GetMapping("/")
    public ResponseEntity<Void> root() {
        return ResponseEntity.ok().build();
    }
}