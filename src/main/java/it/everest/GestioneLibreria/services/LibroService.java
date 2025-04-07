package it.everest.GestioneLibreria.services;

import java.util.List;
import java.util.Optional;
import it.everest.GestioneLibreria.entities.Libro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LibroService {
    List<Libro> all();
    Page<Libro> paginate(Pageable pageable);
    Optional<Libro> get(Long id);
    List<Libro> getByTitolo(String titolo);
    List<Libro> getByAutore(String autore);
    Libro create(Libro libro);
    Libro update(Long id, Libro libro);
    void delete(Long id);
}