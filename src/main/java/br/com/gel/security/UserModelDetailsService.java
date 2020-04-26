package br.com.gel.security;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.gel.security.model.Usuario;
import br.com.gel.security.repository.UsuarioRepository;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class UserModelDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserModelDetailsService.class);

    private final UsuarioRepository usuarioRepository;

    public UserModelDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Autenticando usuário '{}'", login);

        if (new EmailValidator().isValid(login, null)) {
            return usuarioRepository.findOneWithPapelByEmailIgnoreCase(login)
                    .map(user -> createSpringSecurityUser(login, user)).orElseThrow(() -> new UsernameNotFoundException(
                            "Usuário com o email " + login + " não encontrado no banco de dados"));
        }

        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        return usuarioRepository.findOneWithPapelByCpf(lowercaseLogin)
                .map(user -> createSpringSecurityUser(lowercaseLogin, user))
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuário " + lowercaseLogin + " não encontrado no banco de dados"));

    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin,
            Usuario usuario) {
        if (!usuario.isAtivado()) {
            throw new UserNotActivatedException("Usuário " + lowercaseLogin + " não ativado");
        }
        List<GrantedAuthority> grantedAuthorities = usuario.getPapeis().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getNome())).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(usuario.getCpf(), usuario.getSenha(),
                grantedAuthorities);
    }
}
