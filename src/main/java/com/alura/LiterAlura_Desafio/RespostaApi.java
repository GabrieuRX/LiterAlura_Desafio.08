package com.alura.LiterAlura_Desafio;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RespostaApi {

    @JsonAlias("results")
    private List<LivroApi> resultados;

    public List<LivroApi> getResultados() {
        return resultados;
    }

    public void setResultados(List<LivroApi> resultados) {
        this.resultados = resultados;
    }
}