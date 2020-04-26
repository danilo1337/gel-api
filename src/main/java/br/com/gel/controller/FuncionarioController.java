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
import br.com.gel.model.Funcionario;
import br.com.gel.service.FuncionarioService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping(value = "/api/v1/funcionarios")
public class FuncionarioController extends DefaultController implements IController<Funcionario, Long> {
	@Autowired
	private FuncionarioService service;

	/**
	 * Armazena uma {@link Funcionario} no sistema
	 *
	 * @param funcionario Representação do recurso
	 * @return ResponseEntity funcionario
	 */
	@Override
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Armazena o registro do funcionario.")
	public ResponseEntity<?> create(@Valid @RequestBody Funcionario funcionario) {
		log.trace("Criando funcionario {}", funcionario);
		funcionario = service.create(funcionario);
		HttpHeaders responseHeaders = getHttpHeaders(funcionario.getId());
		return ResponseEntity.status(HttpStatus.CREATED).headers(responseHeaders).body(funcionario);
	}

	/**
	 * Retorna um {@link Funcionario} pelo identificador informado
	 *
	 * @param id Identificador do recurso
	 * @return
	 */
	@Override
	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Retorna o funcionario pelo seu Identificador.")
	public ResponseEntity<?> read(@PathVariable Long id) {
		log.trace("Buscando funcionario por identificador {}", id);
		Funcionario funcionario = service.read(id);
		HttpHeaders responseHeaders = getHttpHeaders(funcionario.getId());
		return ResponseEntity.ok().headers(responseHeaders).body(funcionario);
	}

	/**
	 * Pesquisa um registro de {@link Funcionario} baseado numa descrição
	 *
	 * @param descricao Campo a ser pesquisado
	 * @param page      Página inicial
	 * @param size      Tamanho da paginação
	 * @return
	 */
	@Override
	@GetMapping(path = "/page", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Listar os funcionarios em ordem alfabética.")
	public ResponseEntity<?> read(@RequestParam(required = false) String nome,
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "20") Integer size) {
		Page<Funcionario> list = service.read(nome, PageRequest.of(page, size));
		ResponseHeaderPaginable responseHeaderPaginable = new ResponseHeaderPaginable(page, list);
		responseHeaderPaginable.invoke();
		HttpStatus status = responseHeaderPaginable.getStatus();
		return ResponseEntity.status(status).header(CONTENT_RANGE_HEADER, responseHeaderPaginable.responsePageRange())
				.body(list);
	}

	/**
	 * Retorna um {@link Funcionario} pelo cpf informado
	 *
	 * @param cpf cpf do funcionario
	 * @return
	 */
	@GetMapping(path = "/cpf/{cpf}", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Retorna o funcionario pelo seu cpf.")
	public ResponseEntity<?> consultaPorCpf(@PathVariable String cpf) {
		log.trace("Buscando funcionario por cpf {}", cpf);
		Funcionario funcionario = service.buscarPorCpf(cpf);
		HttpHeaders responseHeaders = getHttpHeaders(funcionario.getId());
		return ResponseEntity.ok().headers(responseHeaders).body(funcionario);
	}

	/**
	 * Pesquisa todos os registros de {@link Funcionario}
	 *
	 * @return
	 */
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Listar todas os funcionarios em ordem alfabética.")
	public ResponseEntity<?> read() {
		List<Funcionario> list = service.read();
		return ResponseEntity.ok().body(list);
	}

	/**
	 * Atualização registro de um {@link Funcionario}
	 *
	 * @param id            Identificador do recurso
	 * @param tipoCategoria Representação do recurso
	 * @return
	 */
	@Override
	@PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Altera, restritamente, todo o registro do funcionario.")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Funcionario funcionario) {
		log.trace("Alterando funcionario {}", funcionario);
		funcionario.setId(id);
		service.update(funcionario);
		HttpHeaders responseHeaders = getHttpHeaders(null);
		return ResponseEntity.noContent().headers(responseHeaders).build();
	}

	/**
	 * Remove um registro de {@link Funcionario}
	 *
	 * @param id Identificador da {@link Funcionario}
	 * @return
	 */
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Remove o registro do funcionario.")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		log.trace("Removendo funcionario {}", id);
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
