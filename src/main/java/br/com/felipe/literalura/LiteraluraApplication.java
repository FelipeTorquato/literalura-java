package br.com.felipe.literalura;

import br.com.felipe.literalura.principal.Principal;
import br.com.felipe.literalura.repository.AutorRepository;
import br.com.felipe.literalura.repository.LivroRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

    private LivroRepository livroRepository;
    private AutorRepository autorRepository;

    public static void main(String[] args) {
        SpringApplication.run(LiteraluraApplication.class, args);
    }

    public LiteraluraApplication(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Principal p = new Principal(livroRepository, autorRepository);
        p.menu();
    }
}
