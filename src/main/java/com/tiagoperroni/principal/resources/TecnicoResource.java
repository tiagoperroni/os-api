package com.tiagoperroni.principal.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tiagoperroni.principal.domain.Tecnico;
import com.tiagoperroni.principal.dtos.TecnicoDTO;
import com.tiagoperroni.principal.service.TecnicoService;

//classe do tipo rest que vai receber requisicoes do tipo http
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {

	@Autowired
	private TecnicoService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
		TecnicoDTO objDTO = new TecnicoDTO(service.findById(id));
		return ResponseEntity.ok().body(objDTO);

	}

	@GetMapping
	public ResponseEntity<List<TecnicoDTO>> findAll() {

		List<TecnicoDTO> listDTO = service.findAll().stream().map(obj -> new TecnicoDTO(obj))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);

//outra forma de realizar a consulta acima
//		List<Tecnico> list = service.findById();
//		List<TecnicoDTO> listDTO = new ArrayList<>();	
//		
//		for(Tecnico obj : list) {
//			listDTO.add(new TecnicoDTO(obj));
//		}
		//
//		list.forEach(obj -> listDTO.add(new TecnicoDTO(obj)));
	}

	@PostMapping
	public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO objDTO) {
		Tecnico newObj = service.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();

		return ResponseEntity.created(uri).build();

	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO objDTO) {
		TecnicoDTO newObj = new TecnicoDTO(service.update(id, objDTO));
		return ResponseEntity.ok().body(newObj);
	}
	
	/*
	 * delete tecnico
	 * */
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
		
	}
	
}
