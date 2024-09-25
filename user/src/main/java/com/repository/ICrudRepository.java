package com.repository;

import java.util.List;
import java.util.Optional;

public interface ICrudRepository<T> {
	
	List<T> findAll();	
	
	Optional<T> findById(Integer id);
	
	void insert(T obj);
	
	void update(T obj, Integer id);
	
	void delete(Integer id);
	
	void saveAll(List<T> objs);
}
