package com.tiagoperroni.principal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tiagoperroni.principal.domain.OS;

@Repository
public interface OSRepository extends JpaRepository<OS, Integer>{

}
