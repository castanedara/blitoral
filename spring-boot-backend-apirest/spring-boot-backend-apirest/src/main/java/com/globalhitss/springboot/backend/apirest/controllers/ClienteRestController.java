package com.globalhitss.springboot.backend.apirest.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.globalhitss.springboot.backend.apirest.models.entity.Cliente;
import com.globalhitss.springboot.backend.apirest.models.services.IClienteService;

@CrossOrigin(origins = { "*" })
@RestController
//@RequestMapping("/api")
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;

	private final Logger log = LoggerFactory.getLogger(ClienteRestController.class);
	
	@GetMapping(path = "/clientes")
	public List<Cliente> index() {
		return clienteService.findAll();
	}

	@GetMapping(path = "/clientes/pagina")
	public ResponseEntity<?> index(Pageable pageable) {
		return ResponseEntity.ok().body(clienteService.findAll(pageable));
	}
	
	@GetMapping(path = "/clientes/{codigo}")
	public ResponseEntity<?> show(@PathVariable Integer codigo) {
		Cliente cliente = null;
		Map<String, Object> response = new HashMap<>();

		try {
			cliente = clienteService.findById(codigo);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (cliente == null) {
			response.put("mensaje",
					"El clinete ID: ".concat(codigo.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

		}
		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}

	@PostMapping(path = "/clientes")
	public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) {
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		cliente.setCreateAt(new Date());
		Map<String, Object> response = new HashMap<>();
		Cliente clienteNew = null;

		try {
			clienteNew = clienteService.save(cliente);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El clinete ha sido creado con éxito!");
		response.put("cliente", clienteNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	@PutMapping("/clientes/{codigo}")
	public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente,BindingResult result, @PathVariable Integer codigo ) {
		if(result.hasErrors()) {
			return this.validar(result);
		}
		Cliente clienteActual = clienteService.findById(codigo);
		Cliente clienteUpdated = null;
		log.info("update");
		Map<String, Object> response = new HashMap<>();

		if (clienteActual == null) {
			response.put("mensaje", "Error: El clente ID: "
					.concat(codigo.toString().concat(" no existe en la base de datos, no se puede editar!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			clienteActual.setApellido(cliente.getApellido());
			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setEmail(cliente.getEmail());
			clienteActual.setCreateAt(cliente.getCreateAt());
			clienteUpdated = clienteService.save(clienteActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El cliente ha sido actualizado con éxito!");
		response.put("cliente", clienteUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/clientes/{codigo}")
	public ResponseEntity<?> delete(@PathVariable Integer codigo) {
		Map<String, Object> response = new HashMap<>();
		try {
			clienteService.delete(codigo);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el clinete de la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El cliente ha sido eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}

	protected ResponseEntity<?> validar(BindingResult result){
		Map<String, Object> errores = new HashMap<>();
		result.getFieldErrors().forEach(err -> {
			errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
		});
		
		return ResponseEntity.badRequest().body(errores);
	}
	
	
	
	
}
