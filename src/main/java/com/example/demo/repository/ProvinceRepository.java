package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.obj.Province;

public interface ProvinceRepository extends CrudRepository<Province,Long>{

	List<Province> findByNombrePronviciaContains(String pattern);
	
	List<Province> findByCommunidadeProvinciaContains(String pattern);

}
