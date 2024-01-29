package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.obj.Municipio;

public interface MunicipioRepository extends CrudRepository<Municipio,Long>{
	
	List<Municipio> findByCodProv(int CodProv);
	
	Municipio findByCodProvAndCodINE(int codProv, int codINE);
	
	Municipio findById(int id);
	
	
	List<Municipio> findByNombreLike(String pattern);
	
}
