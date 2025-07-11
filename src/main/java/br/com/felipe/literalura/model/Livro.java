package br.com.felipe.literalura.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
//    private String autor;
    private String idioma;
    private Integer numeroDownloads;

    @ManyToOne(fetch = FetchType.LAZY)
    private Autor autor;

    public Livro(RespostaAPI dadosLivro) {
        this.titulo = dadosLivro.livro().getFirst().titulo();
        this.autor = new Autor(dadosLivro);
        this.idioma = dadosLivro.livro().getFirst().idioma().getFirst();
        this.numeroDownloads = dadosLivro.livro().getFirst().numeroDownloads();
    }

    public Livro() {
    }

    @Override
    public String toString() {
        return "----- LIVRO -----" + "\n" +
                "Título: " + titulo + "\n" +
                "Autor: " + autor + "\n" +
                "Idioma: " + idioma + "\n" +
                "Número de downloads: " + numeroDownloads + "\n" +
                "-------------------" + "\n";
    }
}
