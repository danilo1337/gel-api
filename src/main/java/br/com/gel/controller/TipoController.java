package br.com.gel.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.gel.interfaces.IController;
import br.com.gel.model.Tipo;
import br.com.gel.service.TipoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping(value = "/api/v1/tipos")
public class TipoController extends DefaultController implements IController<Tipo, Long> {
	@Autowired
	private TipoService service;

	/**
	 * Armazena uma {@link Tipo} no sistema
	 *
	 * @param tipo Representação do recurso
	 * @return ResponseEntity tipo
	 */
	@Override
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Armazena o registro do tipo.")
	public ResponseEntity<?> create(@Valid @RequestBody Tipo tipo) {
		log.trace("Criando tipo {}", tipo);
		tipo = service.create(tipo);
		HttpHeaders responseHeaders = getHttpHeaders(tipo.getId());
		return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).body(tipo);
	}

	/**
	 * Retorna um {@link Tipo} pelo identificador informado
	 *
	 * @param id Identificador do recurso
	 * @return
	 */
	@Override
	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Retorna o tipo pelo seu Identificador.")
	public ResponseEntity<?> read(@PathVariable Long id) {
		log.trace("Buscando tipo por identificador {}", id);
		Tipo tipo = service.read(id);
		HttpHeaders responseHeaders = getHttpHeaders(tipo.getId());
		return ResponseEntity.ok().headers(responseHeaders).body(tipo);
	}

	/**
	 * Pesquisa um registro de {@link Tipo} baseado numa descrição
	 *
	 * @param descricao Campo a ser pesquisado
	 * @param page      Página inicial
	 * @param size      Tamanho da paginação
	 * @return
	 */
	@Override
	@GetMapping(path = "/page", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Listar os tipos em ordem alfabética.")
	public ResponseEntity<?> read(@RequestParam(required = false) String nome,
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "20") Integer size) {
		Page<Tipo> list = service.read(nome, PageRequest.of(page, size));
		ResponseHeaderPaginable responseHeaderPaginable = new ResponseHeaderPaginable(page, list);
		responseHeaderPaginable.invoke();
		HttpStatus status = responseHeaderPaginable.getStatus();
		return ResponseEntity.status(status).header(CONTENT_RANGE_HEADER, responseHeaderPaginable.responsePageRange())
				.body(list);
	}

	/**
	 * Pesquisa todos os registros de {@link Tipo}
	 *
	 * @return
	 */
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Listar todas os tipos em ordem alfabética.")
	public ResponseEntity<?> read() {
		List<Tipo> list = service.read();
		return ResponseEntity.ok().body(list);
	}

	/**
	 * Atualização registro de um {@link Tipo}
	 *
	 * @param id            Identificador do recurso
	 * @param tipoCategoria Representação do recurso
	 * @return
	 */
	@Override
	@PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Altera, restritamente, todo o registro do tipo.")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Tipo tipo) {
		log.trace("Alterando tipo {}", tipo);
		tipo.setId(id);
		service.update(tipo);
		HttpHeaders responseHeaders = getHttpHeaders(null);
		return ResponseEntity.noContent().headers(responseHeaders).build();
	}

	/**
	 * Remove um registro de {@link Tipo}
	 *
	 * @param id Identificador da {@link Tipo}
	 * @return
	 */
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Remove o registro do tipo.")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		log.trace("Removendo tipo {}", id);
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@Override
	@RequestMapping(method = RequestMethod.OPTIONS)
	public ResponseEntity<?> options() {
		return ResponseEntity.ok()
				.allow(HttpMethod.POST, HttpMethod.GET, HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.OPTIONS).build();
	}

}
