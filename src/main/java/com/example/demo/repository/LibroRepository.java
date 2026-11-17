package com.example.demo.repository;

import com.example.demo.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

	// Leer todos los libros
	@Query(value = "SELECT * FROM libros ORDER BY id", nativeQuery = true)
	List<Libro> leerLibros();

	// Buscar un libro por id (ej: SELECT * FROM libros WHERE id = 100)
	@Query(value = "SELECT * FROM libros WHERE id = :id", nativeQuery = true)
	Libro buscarPorId(@Param("id") Long id);

	// Buscar libros por título (coincidencia parcial, sin importar mayúsculas)
	// ej: SELECT * FROM libros WHERE titulo ILIKE '%quijote%' ORDER BY id
	@Query(value = "SELECT * FROM libros WHERE titulo ILIKE CONCAT('%', :titulo, '%') ORDER BY id", nativeQuery = true)
	List<Libro> buscarPorTitulo(@Param("titulo") String titulo);

	// Registrar un libro nuevo
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO libros (titulo, editorial, genero, paginas) VALUES (:titulo, :editorial, :genero, :paginas)", nativeQuery = true)
	void insertarLibro(@Param("titulo") String titulo,
	                   @Param("editorial") String editorial,
	                   @Param("genero") String genero,
	                   @Param("paginas") Integer paginas);

	// Eliminar un libro por id
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM libros WHERE id = :id", nativeQuery = true)
	void eliminarLibro(@Param("id") Long id);

}
