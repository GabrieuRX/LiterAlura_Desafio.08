package com.alura.LiterAlura_Desafio;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class ConversorDeDados {

    private final ObjectMapper mapper = new ObjectMapper();
    private final AutorService autorService;

    public ConversorDeDados(AutorService autorService) {
        this.autorService = autorService;
    }

    public Livro converterJsonParaLivro(String json) {
        try {
            RespostaApi resposta = mapper.readValue(json, RespostaApi.class);

            if (resposta.getResultados().isEmpty()) {
                throw new RuntimeException(" Nenhum livro encontrado.");
            }

            LivroApi livroApi = resposta.getResultados().get(0);

            Autor autorApi = livroApi.getAutores().isEmpty()
                    ? new Autor("Autor desconhecido", null, null)
                    : livroApi.getAutores().get(0);

            // Verifica se o autor já existe no banco (evita duplicata)
            Optional<Autor> autorExistente = autorService.buscarPorNomeENascimento(autorApi.getNome(), autorApi.getAnoNascimento());

            Autor autor = autorExistente.orElseGet(() -> autorService.salvar(autorApi));

            // Pega dados do livro
            String titulo = livroApi.getTitulo();
            String idioma = livroApi.getIdiomas().isEmpty() ? "indefinido" : livroApi.getIdiomas().get(0);
            int downloads = livroApi.getNumeroDownloads();

            // Cria o livro com o autor como objeto
            return new Livro(titulo, autor, idioma, downloads);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao converter JSON: " + e.getMessage());
        }
    }
}