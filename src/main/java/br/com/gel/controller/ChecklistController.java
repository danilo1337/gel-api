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
import br.com.gel.model.Checklist;
import br.com.gel.service.ChecklistService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping(value = "/api/v1/checklist")
public class ChecklistController extends DefaultController implements IController<Checklist, Long> {
	@Autowired
	private ChecklistService service;

	/**
	 * Armazena uma {@link Checklist} no sistema
	 *
	 * @param checklist Representação do recurso
	 * @return ResponseEntity checklist
	 */
	@Override
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Armazena o registro do checklist.")
	public ResponseEntity<?> create(@Valid @RequestBody Checklist checklist) {
		log.trace("Criando checklist {}", checklist);
		checklist = service.create(checklist);
		HttpHeaders responseHeaders = getHttpHeaders(checklist.getId());
		return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).body(checklist);
	}

	/**
	 * Retorna um {@link Checklist} pelo identificador informado
	 *
	 * @param id Identificador do recurso
	 * @return
	 */
	@Override
	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Retorna o checklist pelo seu Identificador.")
	public ResponseEntity<?> read(@PathVariable Long id) {
		log.trace("Buscando checklist por identificador {}", id);
		Checklist checklist = service.read(id);
		HttpHeaders responseHeaders = getHttpHeaders(checklist.getId());
		return ResponseEntity.ok().headers(responseHeaders).body(checklist);
	}

	/**
	 * Pesquisa um registro de {@link Checklist} baseado numa descrição
	 *
	 * @param descricao Campo a ser pesquisado
	 * @param page      Página inicial
	 * @param size      Tamanho da paginação
	 * @return
	 */
	@Override
	@GetMapping(path = "/page", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Listar os checklist em ordem alfabética.")
	public ResponseEntity<?> read(@RequestParam(required = false) String nome,
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "20") Integer size) {
		Page<Checklist> list = service.read(nome, PageRequest.of(page, size));
		ResponseHeaderPaginable responseHeaderPaginable = new ResponseHeaderPaginable(page, list);
		responseHeaderPaginable.invoke();
		HttpStatus status = responseHeaderPaginable.getStatus();
		return ResponseEntity.status(status).header(CONTENT_RANGE_HEADER, responseHeaderPaginable.responsePageRange())
				.body(list);
	}

	/**
	 * Pesquisa todos os registros de {@link Checklist}
	 *
	 * @return
	 */
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Listar todas os checklist em ordem alfabética.")
	public ResponseEntity<?> read() {
		List<Checklist> list = service.read();
		return ResponseEntity.ok().body(list);
	}

	/**
	 * Atualização registro de um {@link Checklist}
	 *
	 * @param id            Identificador do recurso
	 * @param tipoCategoria Representação do recurso
	 * @return
	 */
	@Override
	@PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Altera, restritamente, todo o registro do checklist.")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Checklist checklist) {
		log.trace("Alterando checklist {}", checklist);
		checklist.setId(id);
		service.update(checklist);
		HttpHeaders responseHeaders = getHttpHeaders(null);
		return ResponseEntity.noContent().headers(responseHeaders).build();
	}

	/**
	 * Remove um registro de {@link Checklist}
	 *
	 * @param id Identificador da {@link Checklist}
	 * @return
	 */
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Remove o registro do checklist.")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		log.trace("Removendo checklist {}", id);
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
