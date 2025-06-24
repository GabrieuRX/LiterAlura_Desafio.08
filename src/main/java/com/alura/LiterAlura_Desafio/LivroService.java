package com.alura.LiterAlura_Desafio;


import com.alura.LiterAlura_Desafio.Livro;
import com.alura.LiterAlura_Desafio.LivroRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public List<Livro> listarPorIdioma(String idioma) {
        return livroRepository.findByIdiomaIgnoreCase(idioma);
    }

    public long contarLivrosPorIdioma(String idioma) {
        return livroRepository.countByIdiomaIgnoreCase(idioma);
    }

}
