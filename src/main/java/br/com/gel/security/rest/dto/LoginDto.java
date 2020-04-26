package br.com.gel.security.rest.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO for storing a user's credentials.
 */
@Getter @Setter @ToString
public class LoginDto {

   @NotNull
   @Size(min = 1, max = 50)
   private String cpf;

   @NotNull
   @Size(min = 4, max = 100)
   private String senha;
}
