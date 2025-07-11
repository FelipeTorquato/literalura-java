package br.com.felipe.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RespostaAPI(@JsonAlias("results") List<DadosLivro> livro) {
    @Override
    public String toString() {
        return "----- LIVRO -----" + "\n" +
                "Título: " + livro.getFirst().titulo() + "\n" +
                "Autor: " + livro.getFirst().autores() + "\n" +
                "Idioma: " + livro.getFirst().idioma() + "\n" +
                "Número de downloads: " + livro.getFirst().numeroDownloads() + "\n" +
                "-------------------" + "\n";
    }
}
