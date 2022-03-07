package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.demo.dao.ProductoDao;
import com.example.demo.entity.Producto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class ProductoTest {

	@Autowired
	private ProductoDao productoDao;
	
	
	@Test
	@Rollback(false)
	public void testGuardarProducto() {
		Producto producto=new Producto("TV Samsung",600);
		Producto productoGuardado=productoDao.save(producto);
		assertNotNull(productoGuardado);
	}
	
	@Test
	public void testBuscarProductoPorNombre() {
		String nombre="TV Samsung";
		Producto producto=productoDao.findByNombre(nombre);
		assertThat(producto.getNombre()).isEqualTo(nombre);
	}
	
	@Test
	public void testBuscarProductoPorNombreNoExistente() {
		String nombre="Iphone";
		Producto producto=productoDao.findByNombre(nombre);
		assertNull(producto);
	}
	
	@Test
	@Rollback(false)
	public void testActualizarProducto() {
		String nombre="Tv Sony";
		Producto producto=new Producto(nombre,850);
		producto.setId((long) 1);
		productoDao.save(producto);
		
		Producto productoActualizado=productoDao.findByNombre(nombre);
		assertThat(productoActualizado.getNombre()).isEqualTo(nombre);
	}
	
	@Test
	public void testListarProductos() {
		List<Producto> productos=(List<Producto>) productoDao.findAll();
		
		assertThat(productos).size().isGreaterThan(0);
	}
	
	@Test
	@Rollback(false)
	public void TestBorrarProducto() {
		Long id=(long)1;
		boolean existe=productoDao.findById(id).isPresent();
		
		productoDao.deleteById(id);
		
		boolean existe2=productoDao.findById(id).isPresent();
		
		assertTrue(existe);
		assertFalse(existe2);
	}
	
}
