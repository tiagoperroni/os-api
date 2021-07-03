package com.tiagoperroni.principal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiagoperroni.principal.domain.Cliente;
import com.tiagoperroni.principal.domain.OS;
import com.tiagoperroni.principal.domain.Tecnico;
import com.tiagoperroni.principal.domain.enums.Status;
import com.tiagoperroni.principal.dtos.OSDTO;
import com.tiagoperroni.principal.repositories.OSRepository;
import com.tiagoperroni.principal.serviceexceptions.ObjectNotFoundException;

@Service
public class OsService {

	@Autowired
	private OSRepository repository;
	
	@Autowired
	private TecnicoService tecnicoService;
	
	@Autowired
	private ClienteService clienteService;

	public OS findById(Integer id) {
		Optional<OS> obj = repository.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo " + OS.class.getName()));

	}
	
	public List<OS> findAll(){
		return repository.findAll();
	}
	
	public OS create(@Valid OSDTO obj) {
		
		return fromDTO(obj);
	}
	
	public OS update(@Valid OSDTO obj) {
		findById(obj.getId());
		return fromDTO(obj);
		
	}
	
	private OS fromDTO(OSDTO obj) {
		OS newObj = new OS();
		newObj.setId(obj.getId());
		newObj.setObservacoes(obj.getObservacoes());
		newObj.setPrioridade(obj.getPrioridade());
		newObj.setStatus(Status.toEnum(obj.getStatus().getCod()));
				
		Tecnico tec = tecnicoService.findById(obj.getTecnico());
		Cliente cli = clienteService.findById(obj.getCliente());
		
		newObj.setTecnico(tec);
		newObj.setCliente(cli);
		
		if(newObj.getStatus().getCod().equals(2)) {
			newObj.setDataFechamento(LocalDateTime.now());
		}
		
		return repository.save(newObj);
		
		
	}

}
