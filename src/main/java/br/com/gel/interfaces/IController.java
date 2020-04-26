package br.com.gel.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Interface genérica para camada de controle
 *
 * @param <T>
 * @param <N>
 */
public interface IController<T, N> {

    ResponseEntity<?> create(T entity);

    ResponseEntity<?> read(@PathVariable N id);

    ResponseEntity<?> read(String descricao, Integer page, Integer size);

    ResponseEntity<?> update(N id, T entity);

    ResponseEntity<?> delete(N id);

    ResponseEntity<?> options();

}
