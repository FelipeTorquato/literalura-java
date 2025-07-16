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
    private String idioma;
    private Integer numeroDownloads;

    @ManyToOne
    private Autor autor;

    public Livro(DadosLivro dadosLivro) {
        this.titulo = dadosLivro.titulo();
    }

    public Livro() {
    }

    @Override
    public String toString() {
        return "----- LIVRO -----" + "\n" +
                "Título: " + titulo + "\n" +
                "Autor: " + autor.getNome() + "\n" +
                "Idioma: " + idioma + "\n" +
                "Número de downloads: " + numeroDownloads + "\n" +
                "-------------------" + "\n";
    }
}
