package com.alura.LiterAlura_Desafio;

import com.alura.LiterAlura_Desafio.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByIdiomaIgnoreCase(String idioma);

    long countByIdiomaIgnoreCase(String idioma);  // contar livros por idioma
}