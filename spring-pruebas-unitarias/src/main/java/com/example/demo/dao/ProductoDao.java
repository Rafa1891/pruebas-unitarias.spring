package com.example.demo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Producto;

@Repository
public interface ProductoDao extends CrudRepository<Producto,Long>{

	public Producto findByNombre(String nombre);
}
