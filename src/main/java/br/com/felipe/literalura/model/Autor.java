package br.com.felipe.literalura.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;
    private Integer anoDeNascimento;
    private Integer anoDeFalecimento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor() {
    }

    public Autor(DadosAutor dadosAutor) {
        this.nome = dadosAutor.nome();
        this.anoDeNascimento = dadosAutor.anoDeNascimento();
        this.anoDeFalecimento = dadosAutor.anoDeFalecimento();
    }

    @Override
    public String toString() {
        String livrosStr = livros.stream()
                .map(Livro::getTitulo)
                .collect(Collectors.joining(", "));

        return "Autor: " + nome + "\n" +
                "Ano de nascimento: " + anoDeNascimento + "\n" +
                "Ano de falecimento: " + anoDeFalecimento + "\n" +
                "Livros: [" + livrosStr + "]\n" +
                "-------------------" + "\n";
    }
}
