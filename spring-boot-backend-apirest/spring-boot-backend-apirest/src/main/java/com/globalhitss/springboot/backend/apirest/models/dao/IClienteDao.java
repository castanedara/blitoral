package com.globalhitss.springboot.backend.apirest.models.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

import com.globalhitss.springboot.backend.apirest.models.entity.Cliente;

public interface IClienteDao extends JpaRepository<Cliente, Integer> {
	
	public Iterable<Cliente> findAllByOrderByCodigoAsc();
	public Page<Cliente> findAllByOrderByCodigoAsc(Pageable pageable);

}
