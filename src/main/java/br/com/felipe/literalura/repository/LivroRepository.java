package br.com.felipe.literalura.repository;

import br.com.felipe.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    Optional<Livro> findByTituloIgnoreCase(String titulo);

    List<Livro> findLivrosByIdiomaIgnoreCase(String idioma);
}
