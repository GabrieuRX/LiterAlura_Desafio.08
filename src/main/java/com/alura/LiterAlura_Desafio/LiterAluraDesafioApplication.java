package com.alura.LiterAlura_Desafio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class LiterAluraDesafioApplication implements CommandLineRunner {

	private final LivroService livroService;
	private final ConversorDeDados conversorDeDados;
	private final AutorService autorService;

	public LiterAluraDesafioApplication(LivroService livroService, ConversorDeDados conversorDeDados, AutorService autorService) {
		this.livroService = livroService;
		this.conversorDeDados = conversorDeDados;
		this.autorService = autorService;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraDesafioApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		int opcao;

		do {
			System.out.println("\n Bem-vindo ao Catálogo LiterAlura!");
			System.out.println("1 - Buscar livro por título");
			System.out.println("2 - Listar todos os livros");
			System.out.println("3 - Listar livros por idioma");
			System.out.println("4 - Listar todos os autores");
			System.out.println("5 - Listar autores vivos em um determinado ano");
			System.out.println("6 - Estatísticas de livros por idioma");
			System.out.println("7 - Sair");
			System.out.print("Escolha uma opção: ");
			opcao = scanner.nextInt();
			scanner.nextLine(); // limpar o buffer

			switch (opcao) {
				case 1 -> buscarLivroPorTitulo(scanner);
				case 2 -> listarTodosLivros();
				case 3 -> listarLivrosPorIdioma(scanner);
				case 4 -> listarTodosAutores();
				case 5 -> listarAutoresVivos(scanner);
				case 6 -> mostrarEstatisticaPorIdioma(scanner);
				case 7 -> System.out.println("Saindo...");
				default -> System.out.println("Opção inválida!");
			}
		} while (opcao != 7);
	}

	private void buscarLivroPorTitulo(Scanner scanner) {
		System.out.print("\nDigite o título do livro: ");
		String titulo = scanner.nextLine();

		String url = "https://gutendex.com/books/?search=" + titulo.replace(" ", "+");

		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(url))
					.build();

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			Livro livro = conversorDeDados.converterJsonParaLivro(response.body());
			livroService.salvar(livro);

			System.out.println("\n Livro salvo com sucesso:");
			System.out.println(livro);

		} catch (Exception e) {
			System.out.println("Erro ao buscar livro: " + e.getMessage());
		}
	}

	private void listarTodosLivros() {
		System.out.println("\n Lista de todos os livros salvos:");
		List<Livro> livros = livroService.listarTodos();

		if (livros.isEmpty()) {
			System.out.println("Nenhum livro encontrado.");
		} else {
			livros.forEach(System.out::println);
		}
	}

	private void listarLivrosPorIdioma(Scanner scanner) {
		System.out.print("\nDigite o idioma (ex: en, pt, es): ");
		String idioma = scanner.nextLine();

		List<Livro> livros = livroService.listarPorIdioma(idioma);

		if (livros.isEmpty()) {
			System.out.println("Nenhum livro encontrado nesse idioma.");
		} else {
			System.out.println("\nLivros no idioma '" + idioma + "':");
			livros.forEach(System.out::println);
		}
	}

	private void listarTodosAutores() {
		System.out.println("\n Lista de autores cadastrados:");
		List<Autor> autores = autorService.listarTodos();

		if (autores.isEmpty()) {
			System.out.println("Nenhum autor cadastrado.");
		} else {
			autores.forEach(System.out::println);
		}
	}

	private void listarAutoresVivos(Scanner scanner) {
		System.out.print("\nDigite o ano para ver autores vivos: ");
		int ano = scanner.nextInt();
		scanner.nextLine();

		List<Autor> vivos = autorService.listarAutoresVivosNoAno(ano);

		if (vivos.isEmpty()) {
			System.out.println("Nenhum autor vivo encontrado nesse ano.");
		} else {
			System.out.println("\nAutores vivos no ano " + ano + ":");
			vivos.forEach(System.out::println);
		}
	}
	private void mostrarEstatisticaPorIdioma(Scanner scanner) {
		System.out.println("\nEscolha o idioma para ver a quantidade de livros:");
		System.out.println("1 - Português");
		System.out.println("2 - Inglês");
		System.out.print("Digite a opção: ");

		int opcao = scanner.nextInt();
		scanner.nextLine(); // limpar buffer

		String idioma = null;
		switch (opcao) {
			case 1 -> idioma = "pt";
			case 2 -> idioma = "en";
			default -> {
				System.out.println("Opção inválida.");
				return;
			}
		}

		long quantidade = livroService.contarLivrosPorIdioma(idioma);
		System.out.printf("Existem %d livro(s) no idioma '%s'.\n", quantidade, idioma);
	}




}