package br.com.gel.security;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtils {

   private static final Logger LOG = LoggerFactory.getLogger(SecurityUtils.class);

   private SecurityUtils() {
   }

   /**
    * Get the login of the current user.
    *
    * @return the login of the current user.
    */
   public static Optional<String> getCurrentUsername() {
      final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      if (authentication == null) {
         LOG.debug("nenhuma autenticação encontrada no contexto de segurança");
         return Optional.empty();
      }

      String username = null;
      if (authentication.getPrincipal() instanceof UserDetails) {
         UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
         username = springSecurityUser.getUsername();
      } else if (authentication.getPrincipal() instanceof String) {
         username = (String) authentication.getPrincipal();
      }

      LOG.debug("encontrado o usuario '{}' no contexto de segurança", username);

      return Optional.ofNullable(username);
   }
}
