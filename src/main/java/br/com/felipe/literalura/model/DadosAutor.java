package br.com.felipe.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosAutor(@JsonAlias("name") String nome, @JsonAlias("birth_year") String anoDeNascimento,
                         @JsonAlias("death_year") String anoDeFalecimento) {
    @Override
    public String toString() {
        return nome;
    }
}
