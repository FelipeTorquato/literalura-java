package br.com.felipe.literalura.model;

// DTO para representar os dados de um livro de forma simplificada
public record LivroDTO(
        Long id,
        String titulo,
        String idioma,
        Integer numeroDownloads
) {
    // Construtor que converte a entidade Livro para o DTO
    public LivroDTO(Livro livro) {
        this(livro.getId(), livro.getTitulo(), livro.getIdioma(), livro.getNumeroDownloads());
    }
}
