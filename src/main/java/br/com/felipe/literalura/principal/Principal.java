package br.com.felipe.literalura.principal;

import br.com.felipe.literalura.service.ConsumoApi;

import java.util.Scanner;
import java.util.logging.Logger;

public class Principal {

    private Logger logger = Logger.getLogger(getClass().getName());
    private Scanner scanner = new Scanner(System.in);
    private static final String ENDERECO = "https://gutendex.com/books/?search=";

    public void menu() {
        int opcao = -1;
        while (opcao != 0) {
            var menu = """
                    Escolha o número de sua opção:
                    1- Buscar livro pelo título
                    2- Listar livros reistrados
                    3- Listar autores reistrados
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
            }
        }


    }

    private void buscarLivroWeb() {
        System.out.println("Digite o nome do livro:");
        String nomeLivro = scanner.nextLine();
        ConsumoApi consumoApi = new ConsumoApi();
        String resposta = consumoApi.obterDados(ENDERECO + nomeLivro.replace(" ", "+"));
        System.out.println(resposta);
    }
}
