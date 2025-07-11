package br.com.felipe.literalura.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String anoDeNascimento;
    private String anoDeFalecimento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor() {
    }

    public Autor(RespostaAPI dadosAutor) {
        this.nome = dadosAutor.livro().getFirst().autores().getFirst().nome();
        this.anoDeNascimento = dadosAutor.livro().getFirst().autores().getFirst().anoDeNascimento();
        this.anoDeFalecimento = dadosAutor.livro().getFirst().autores().getFirst().anoDeFalecimento();
        setLivros(livros);
    }

    public void setLivros(List<Livro> livros) {
        livros.forEach(l -> l.setAutor(this));
        this.livros = livros;
    }

    @Override
    public String toString() {
        return  "Autor: " + nome + "\n" +
                "Ano de nascimento: " + anoDeNascimento + "\n" +
                "Ano de falecimento: " + anoDeFalecimento + "\n" +
                "Livros: " + livros + "\n" +
                "-------------------" + "\n";
    }
}
