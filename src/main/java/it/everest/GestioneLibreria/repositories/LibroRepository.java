package it.everest.GestioneLibreria.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import it.everest.GestioneLibreria.entities.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    List<Libro> findByTitoloContainingIgnoreCase(String titolo);

    List<Libro> findByAutoreContainingIgnoreCase(String autore);
}