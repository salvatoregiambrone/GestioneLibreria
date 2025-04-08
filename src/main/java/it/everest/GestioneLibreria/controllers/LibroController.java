package it.everest.GestioneLibreria.controllers;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.everest.GestioneLibreria.entities.Libro;
import it.everest.GestioneLibreria.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.*;

@RestController
@RequestMapping("/api/libri")
@Tag(name = "Libri", description = "Operazioni relative alla gestione dei libri")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @Operation(summary = "Ottieni tutti i libri", description = "Restituisce una lista di tutti i libri registrati.")
    @GetMapping
    public List<Libro> all() {
        return libroService.all();
    }

    @Operation(summary = "Restituisce una pagina di libri ordinati",
            description = "Permette di specificare pagina, dimensione, campo e direzione di ordinamento.")
    @GetMapping("/paginate")
    public Page<Libro> paginate(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc") ?
                    Sort.by(sortBy).descending() :
                    Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return libroService.paginate(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> get(@PathVariable Long id) {
        return libroService.get(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/titolo/{titolo}")
    public List<Libro> getByTitolo(@PathVariable String titolo) {
        return libroService.getByTitolo(titolo);
    }

    @GetMapping("/autore/{autore}")
    public List<Libro> getByAutore(@PathVariable String autore) {
        return libroService.getByAutore(autore);
    }

    @Operation(summary = "Crea un nuovo libro",
            description = "Crea un libro e associa eventuali categorie esistenti o nuove. Richiede un oggetto JSON di tipo Libro.")
    @PostMapping
    public Libro create(@RequestBody Libro libro) {
        return libroService.create(libro);
    }

    @Operation(summary = "Aggiorna un libro esistente",
            description = "Aggiorna i dati di un libro identificato da ID e sostituisce le categorie associate.")
    @PutMapping("/{id}")
    public Libro update(@PathVariable Long id, @RequestBody Libro libro) {
        return libroService.update(id, libro);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        libroService.delete(id);
    }
}