package com.tiagoperroni.principal.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiagoperroni.principal.domain.Cliente;
import com.tiagoperroni.principal.domain.OS;
import com.tiagoperroni.principal.domain.Tecnico;
import com.tiagoperroni.principal.domain.enums.Prioridade;
import com.tiagoperroni.principal.domain.enums.Status;
import com.tiagoperroni.principal.repositories.ClienteRepository;
import com.tiagoperroni.principal.repositories.OSRepository;
import com.tiagoperroni.principal.repositories.TecnicoRepository;

//pode injetar instancia da nossa classe em artes diferentes do nosso codigo
@Service
public class DBService {

	// estamos informando que quem vai ficar responsavel pelo ciclo de vida é o
	// springboot pela injecao de dependencia
	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private OSRepository osRepository;

	public void instaciaDB() {

		Tecnico t1 = new Tecnico(null, "Tiago Perroni", "685.218.730-69", "(44) 99911-9988");
		Tecnico t2 = new Tecnico(null, "Linus Torvalds", "641.760.040-88", "(81) 98899-5588");
		Cliente c1 = new Cliente(null, "Betina Campos", "980.545.240-99", "(43) 99789-5544");
		Cliente c2 = new Cliente(null, "Amanda Silva", "926.648.260-93", "(41) 99879-5324");
		OS os1 = new OS(null, Prioridade.ALTA, "Teste create OS", Status.ANDAMENTO, t1, c1);
		OS os2 = new OS(null, Prioridade.MEDIA, "Urgênte essa", Status.ABERTO, t2, c2);

		t1.getList().add(os1);
		c1.getList().add(os1);
		t2.getList().add(os2);
		c2.getList().add(os2);

		tecnicoRepository.saveAll(Arrays.asList(t1, t2));
		clienteRepository.saveAll(Arrays.asList(c1, c2));
		osRepository.saveAll(Arrays.asList(os1, os2));

	}

}
