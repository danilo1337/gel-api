package br.com.gel.security.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gel.security.model.Usuario;
import br.com.gel.security.service.UsuarioService;

@RestController
@RequestMapping("/api")
public class UsuarioRestController {

   private final UsuarioService usuarioService;

   public UsuarioRestController(UsuarioService usuarioService) {
      this.usuarioService = usuarioService;
   }

   @GetMapping("/usuario")
   public ResponseEntity<Usuario> getUsuarioAtual() {
      return ResponseEntity.ok(usuarioService.getUserWithAuthorities().get());
   }
}
