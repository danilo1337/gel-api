package br.com.gel.security.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gel.security.SecurityUtils;
import br.com.gel.security.model.Usuario;
import br.com.gel.security.repository.UsuarioRepository;

@Service
@Transactional
public class UsuarioService {

   private final UsuarioRepository usuarioRepository;

   public UsuarioService(UsuarioRepository usuarioRepository) {
      this.usuarioRepository = usuarioRepository;
   }

   @Transactional(readOnly = true)
   public Optional<Usuario> getUserWithAuthorities() {
      return SecurityUtils.getCurrentUsername().flatMap(usuarioRepository::findOneWithPapelByCpf);
   }
}
