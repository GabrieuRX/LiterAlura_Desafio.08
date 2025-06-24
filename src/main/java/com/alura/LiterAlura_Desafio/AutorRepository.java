package com.alura.LiterAlura_Desafio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.alura.LiterAlura_Desafio.Autor;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNomeAndAnoNascimento(String nome, Integer anoNascimento);
    List<Autor> findByAnoFalecimentoIsNullOrAnoFalecimentoGreaterThanEqual(int ano);
}
