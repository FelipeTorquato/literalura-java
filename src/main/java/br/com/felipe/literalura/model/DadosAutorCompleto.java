package br.com.felipe.literalura.model;

import java.util.List;
import java.util.stream.Collectors;

public record DadosAutorCompleto(String nome,
                                 String anoDeNascimento,
                                 String anoDeFalecimento,
                                 List<LivroDTO> livros) {
    public DadosAutorCompleto(Autor autor) {
        this(autor.getNome(),
                autor.getAnoDeNascimento(),
                autor.getAnoDeFalecimento(),
                autor.getLivros().stream()
                        .map(LivroDTO::new)
                        .collect(Collectors.toList())
        );
    }

}
