package com.tiagoperroni.principal.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiagoperroni.principal.domain.Pessoa;
import com.tiagoperroni.principal.domain.Tecnico;
import com.tiagoperroni.principal.dtos.TecnicoDTO;
import com.tiagoperroni.principal.repositories.PessoaRepository;
import com.tiagoperroni.principal.repositories.TecnicoRepository;
import com.tiagoperroni.principal.resources.exceptions.DataIntegratyViolationException;
import com.tiagoperroni.principal.serviceexceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não econtrato! Id: " + id + ", Tipo " + Tecnico.class.getName()));

	}

	public List<Tecnico> findAll() {
		return repository.findAll();
	}

	public Tecnico create(TecnicoDTO objDTO) {
		if (findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		// podia ser assim mas na segunda oipcao tem menos codigo
		// Tecnico newObj = new Tecnico(null, objDTO.getNome(), objDTO.getCpf(),
		// objDTO.getTelefone());
		return repository.save(new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));
	}

	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		Tecnico oldObj = findById(id);

		if (findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		return repository.save(oldObj);
	}
	
	public void delete(Integer id) {
		Tecnico obj = findById(id);
		
		if(obj.getList().size() > 0 ) {
			throw new DataIntegratyViolationException("Técnico possui ordens de serviço e não pode ser deletado!");
		}
		
		repository.deleteById(id);
	}

	private Pessoa findByCPF(TecnicoDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
		if (obj != null) {
			return obj;
		}
		return null;
	}


}
