package com.alura.LiterAlura_Desafio;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    // Salva o autor apenas se não existir outro com o mesmo nome e anoNascimento
    public Autor salvar(Autor autor) {
        Optional<Autor> autorExistente = autorRepository.findByNomeAndAnoNascimento(
                autor.getNome(), autor.getAnoNascimento());

        return autorExistente.orElseGet(() -> autorRepository.save(autor));
    }

    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    public List<Autor> listarAutoresVivosNoAno(int ano) {
        return autorRepository.findByAnoFalecimentoIsNullOrAnoFalecimentoGreaterThanEqual(ano);
    }

    public Optional<Autor> buscarPorNomeENascimento(String nome, Integer anoNascimento) {
        return autorRepository.findByNomeAndAnoNascimento(nome, anoNascimento);
    }
}