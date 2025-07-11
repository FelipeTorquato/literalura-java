package br.com.felipe.literalura.principal;

import br.com.felipe.literalura.model.Autor;
import br.com.felipe.literalura.model.Livro;
import br.com.felipe.literalura.model.RespostaAPI;
import br.com.felipe.literalura.repository.AutorRepository;
import br.com.felipe.literalura.repository.LivroRepository;
import br.com.felipe.literalura.service.ConsumoApi;
import br.com.felipe.literalura.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class Principal {

    private Logger logger = Logger.getLogger(getClass().getName());
    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados converteDados = new ConverteDados();
    private LivroRepository livroRepository;
    private AutorRepository autorRepository;
    private List<Livro> livros = new ArrayList<>();
    private List<Autor> autores = new ArrayList<>();
    private static final String ENDERECO = "https://gutendex.com/books/?search=";

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void menu() {
        int opcao = -1;
        while (opcao != 0) {
            var menu = """
                    Escolha o número de sua opção:
                    1- Buscar livro pelo título
                    2- Listar livros registrados
                    3- Listar autores registrados
                    4- Listar autores vivos em um determinado ano
                    5- Listar livros em um determinado idioma
                    0- Sair
                    """;

            logger.info(menu);
            opcao = scanner.nextInt();
            scanner.nextLine();
            if (opcao == 0) {
                logger.info("Saindo");
            } else if (opcao == 1) {
                buscarLivroWeb();
            } else if (opcao == 2) {
                listarLivros();
            } else if (opcao == 3) {
                listarAutores();
            }
        }

    }

    private RespostaAPI getDadosLivro() {
        System.out.println("Digite o nome do livro:");
        String nomeLivro = scanner.nextLine();
        var json = consumoApi.obterDados(ENDERECO + nomeLivro.replace(" ", "+"));
        System.out.println(json);
        return converteDados.obterDados(json, RespostaAPI.class);
    }

    private void buscarLivroWeb() {
        RespostaAPI dados = getDadosLivro();
        Livro livro = new Livro(dados);
        livroRepository.save(livro);
        salvarAutor(dados);
        System.out.println(dados);
    }

    private void listarLivros() {
        livros = livroRepository.findAll();
        livros.forEach(System.out::println);
    }

    private void listarAutores() {
        autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    private void salvarAutor(RespostaAPI dados) {
        Autor autor = new Autor(dados);
        autorRepository.save(autor);
    }
}
