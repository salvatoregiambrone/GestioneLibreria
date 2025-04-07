package it.everest.GestioneLibreria.controllers;

import java.util.List;
import it.everest.GestioneLibreria.entities.Libro;
import it.everest.GestioneLibreria.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.*;

@RestController
@RequestMapping("/api/libri")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping
    public List<Libro> all() {
        return libroService.all();
    }
    
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

    @PostMapping
    public Libro create(@RequestBody Libro libro) {
        return libroService.create(libro);
    }

    @PutMapping("/{id}")
    public Libro update(@PathVariable Long id, @RequestBody Libro libro) {
        return libroService.update(id, libro);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        libroService.delete(id);
    }
}