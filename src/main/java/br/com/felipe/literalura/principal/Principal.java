package br.com.felipe.literalura.principal;

import br.com.felipe.literalura.model.*;
import br.com.felipe.literalura.repository.AutorRepository;
import br.com.felipe.literalura.repository.LivroRepository;
import br.com.felipe.literalura.service.ConsumoApi;
import br.com.felipe.literalura.service.ConverteDados;

import java.util.*;
import java.util.logging.Logger;

public class Principal {

    private final Logger logger = Logger.getLogger(getClass().getName());
    private final Scanner scanner = new Scanner(System.in);
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final ConverteDados converteDados = new ConverteDados();
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private List<Livro> livros = new ArrayList<>();
    private List<Autor> autores = new ArrayList<>();
    private static final String ENDERECO = "https://gutendex.com/books/?search=";

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void menu() {
        int opcao;
        while (true) {
            var menu = """
                    Escolha o número de sua opção:
                    1- Buscar livros pelo título
                    2- Listar livros registrados
                    3- Listar autores registrados
                    4- Listar autores vivos em um determinado ano
                    5- Listar livros em um determinado idioma
                    0- Sair
                    """;

            System.out.println(menu);
            opcao = scanner.nextInt();
            scanner.nextLine();
            if (opcao == 0) {
                logger.info("Saindo");
                System.exit(0);
                break;
            } else if (opcao == 1) {
                buscarLivroWeb();
            } else if (opcao == 2) {
                listarLivros();
            } else if (opcao == 3) {
                listarAutores();
            } else if (opcao == 4) {
                listarAutoresVivosPorData();
            } else if (opcao == 5) {
                listarLivrosDeDeterminadoIdioma();
            }
        }

    }

    private RespostaAPI getDadosLivro() {
        System.out.println("Digite o nome do livro:");
        String nomeLivro = scanner.nextLine();
        var json = consumoApi.obterDados(ENDERECO + nomeLivro.replace(" ", "+"));
        return converteDados.obterDados(json, RespostaAPI.class);
    }

    private void buscarLivroWeb() {
        RespostaAPI dados = getDadosLivro();
        if (dados != null && !dados.livros().isEmpty()) {
            DadosLivro dadosLivro = dados.livros().get(0);

            Optional<Livro> livroExistente = livroRepository.findByTituloIgnoreCase(dadosLivro.titulo());
            if (livroExistente.isPresent()) {
                System.out.println("O livro já está cadastrado no banco de dados.");
                return;
            }

            DadosAutor dadosAutor = dadosLivro.autores().get(0);
            Optional<Autor> autorExistente = autorRepository.findByNomeIgnoreCase(dadosAutor.nome());

            Autor autor;
            if (autorExistente.isPresent()) {
                autor = autorExistente.get();
                System.out.println("Autor já existente no banco: " + autor.getNome());
            } else {
                System.out.println("Novo autor, cadastrando no banco: " + dadosAutor.nome());
                autor = new Autor(dadosAutor);
            }

            Livro livro = new Livro(dadosLivro);
            livro.setAutor(autor);

            livro.setIdioma(dadosLivro.idiomas().get(0));
            livro.setNumeroDownloads(dadosLivro.numeroDownloads());

            autorRepository.save(autor);
            livroRepository.save(livro);

            System.out.println("Livro salvo com sucesso!");
            System.out.println(livro);

        } else {
            System.out.println("Nenhum livros encontrado com esse título.");
        }
    }

    private void listarLivros() {
        livros = livroRepository.findAll();
        livros.forEach(System.out::println);
    }

    private void listarAutores() {
        autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado no banco de dados.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresVivosPorData() {
        System.out.println("Digite o ano para consultar os autores vivos:");

        try {
            int ano = scanner.nextInt();
            scanner.nextLine();

            autores = autorRepository.findAutoresVivosEmAno(ano);

            if (autores.isEmpty()) {
                System.out.println("Nenhum autor vivo foi encontrado para o ano de " + ano + ".");
            } else {
                System.out.println("\n----- Autores Vivos em " + ano + " -----");
                autores.forEach(System.out::println);
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um número de ano válido.");
            scanner.nextLine();
        }
    }

    private void listarLivrosDeDeterminadoIdioma() {
        System.out.println("Digite o idioma para consultar os livros:");

        try {
            String idioma = scanner.nextLine();

            livros = livroRepository.findLivrosByIdiomaIgnoreCase(idioma);

            if (livros.isEmpty()) {
                System.out.println("Nenhum livro encontrado para o idioma " + idioma + ".");
            } else {
                System.out.println("\n----- Livros no idioma " + idioma + " -----");
                livros.forEach(System.out::println);
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, digite um texto válido.");
            scanner.nextLine();
        }
    }
}
