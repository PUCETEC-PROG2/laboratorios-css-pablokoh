package com.example.demo.service;

import com.example.demo.model.Libro;
import com.example.demo.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

	private final LibroRepository libroRepository;

	public LibroService(LibroRepository libroRepository) {
		this.libroRepository = libroRepository;
	}

	// SELECT * FROM libros ORDER BY id
	public List<Libro> leerLibros() {
		return libroRepository.leerLibros();
	}

	// SELECT * FROM libros WHERE id = ?   (devuelve null si no existe)
	public Libro buscarLibroPorId(Long id) {
		return libroRepository.buscarPorId(id);
	}

	// SELECT * FROM libros WHERE titulo ILIKE '%?%' ORDER BY id   (lista vacía si no hay coincidencias)
	public List<Libro> buscarLibrosPorTitulo(String titulo) {
		return libroRepository.buscarPorTitulo(titulo);
	}

	// INSERT INTO libros (titulo, editorial, genero, paginas) VALUES (?, ?, ?, ?)
	public void guardarLibro(Libro libro) {
		libroRepository.insertarLibro(libro.getTitulo(), libro.getEditorial(), libro.getGenero(), libro.getPaginas());
	}

	// Actualizar un libro: id = libro a cambiar, libro = datos nuevos
	// Si no existe lanza RuntimeException("El libro no existe")
	// save() genera: UPDATE libros SET titulo = ?, editorial = ?, genero = ?, paginas = ? WHERE id = ?
	public Libro actualizarLibro(Long id, Libro libro) {
		Libro libroExistente = libroRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("El libro no existe"));

		libroExistente.setTitulo(libro.getTitulo());
		libroExistente.setEditorial(libro.getEditorial());
		libroExistente.setGenero(libro.getGenero());
		libroExistente.setPaginas(libro.getPaginas());

		return libroRepository.save(libroExistente);
	}

	// DELETE FROM libros WHERE id = ?
	// Si no existe lanza RuntimeException("El libro no existe")
	public void eliminarLibro(Long id) {
		Libro libroEliminar = libroRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("El libro no existe"));

		libroRepository.eliminarLibro(libroEliminar.getId());
	}

}
