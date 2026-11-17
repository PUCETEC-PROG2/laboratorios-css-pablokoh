package com.example.demo.controller;

import com.example.demo.model.Libro;
import com.example.demo.service.LibroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/libros")
public class LibroController {

	private final LibroService libroService;

	public LibroController(LibroService libroService) {
		this.libroService = libroService;
	}

	@GetMapping
	public String listar(Model model) {
		model.addAttribute("libros", libroService.leerLibros());
		return "libros/lista";
	}

	@GetMapping("/buscar")
	public String buscar(@RequestParam(required = false) Long id, Model model) {
		if (id == null) {
			return "redirect:/libros";
		}
		Libro libro = libroService.buscarLibroPorId(id);
		model.addAttribute("libros", libro == null ? List.of() : List.of(libro));
		model.addAttribute("idBuscado", id);
		return "libros/lista";
	}

	@GetMapping("/buscar-titulo")
	public String buscarPorTitulo(@RequestParam(required = false) String titulo, Model model) {
		if (titulo == null || titulo.isBlank()) {
			return "redirect:/libros";
		}
		model.addAttribute("libros", libroService.buscarLibrosPorTitulo(titulo.trim()));
		model.addAttribute("tituloBuscado", titulo.trim());
		return "libros/lista";
	}

	@GetMapping("/formulario")
	public String formulario(Model model) {
		model.addAttribute("libro", new Libro());
		return "libros/formulario";
	}

	@PostMapping("/guardar")
	public String guardar(@ModelAttribute Libro libro) {
		libroService.guardarLibro(libro);
		return "redirect:/libros";
	}

	// Muestra el formulario con los datos del libro para editarlo
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable Long id, Model model) {
		Libro libro = libroService.buscarLibroPorId(id);
		if (libro == null) {
			return "redirect:/libros";
		}
		model.addAttribute("libro", libro);
		return "libros/formulario";
	}

	@PostMapping("/actualizar/{id}")
	public String actualizar(@PathVariable Long id, @ModelAttribute Libro libro) {
		libroService.actualizarLibro(id, libro);
		return "redirect:/libros";
	}

	@PostMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id) {
		libroService.eliminarLibro(id);
		return "redirect:/libros";
	}

}
