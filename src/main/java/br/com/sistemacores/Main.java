
package br.com.sistemacores;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final CorDAO dao = new CorDAO();

    public static void main(String[] args) {
        int opcao;

        do {
            System.out.println("\n===== SISTEMA DE CORES =====");
            System.out.println("1 - Cadastrar cor comum");
            System.out.println("2 - Cadastrar cor especial");
            System.out.println("3 - Listar todas as cores");
            System.out.println("4 - Buscar cor por nome");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro();

            try {
                switch (opcao) {
                    case 1 -> cadastrarCorComum();
                    case 2 -> cadastrarCorEspecial();
                    case 3 -> listarCores();
                    case 4 -> buscarCor();
                    case 0 -> System.out.println("Sistema encerrado.");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (SQLException e) {
                System.out.println("Erro ao acessar o banco: "
                        + e.getMessage());
            }

        } while (opcao != 0);

        scanner.close();
    }

    private static void cadastrarCorComum() throws SQLException {
        System.out.println("\n--- Cadastro de cor comum ---");

        String nome = lerTexto("Nome: ");
        String familia = lerTexto("Família: ");
        String codigoHex = lerTexto("Código HEX (ex.: #FF0000): ");

        Cor cor = new Cor(nome, familia, codigoHex, "COMUM");
        dao.cadastrar(cor);

        System.out.println("Cor cadastrada com sucesso! ID: "
                + cor.getId());
    }

    private static void cadastrarCorEspecial() throws SQLException {
        System.out.println("\n--- Cadastro de cor especial ---");

        String nome = lerTexto("Nome: ");
        String familia = lerTexto("Família: ");
        String codigoHex = lerTexto("Código HEX (ex.: #FFD700): ");

        boolean metalica;

        while (true) {
            String resposta = lerTexto("É metálica? (S/N): ");

            if (resposta.equalsIgnoreCase("S")) {
                metalica = true;
                break;
            } else if (resposta.equalsIgnoreCase("N")) {
                metalica = false;
                break;
            } else {
                System.out.println("Digite S ou N.");
            }
        }

        String acabamento = lerTexto("Acabamento: ");

        CorEspecial cor = new CorEspecial(
                nome, familia, codigoHex, metalica, acabamento
        );

        dao.cadastrar(cor);

        System.out.println("Cor especial cadastrada com sucesso! ID: "
                + cor.getId());
    }

    private static void listarCores() throws SQLException {
        System.out.println("\n--- Todas as cores ---");
        exibirResultados(dao.listarTodos());
    }

    private static void buscarCor() throws SQLException {
        String nome = lerTexto("\nDigite o nome da cor para buscar: ");
        List<Cor> resultados = dao.buscarPorNome(nome);

        System.out.println("\n--- Resultado da busca ---");
        exibirResultados(resultados);
    }

    private static void exibirResultados(List<Cor> cores) {
        if (cores.isEmpty()) {
            System.out.println("Nenhuma cor encontrada.");
            return;
        }

        for (Cor cor : cores) {
            System.out.println(cor);
        }
    }

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    private static int lerInteiro() {
        while (true) {
            String valor = scanner.nextLine().trim();

            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                System.out.print("Digite um número válido: ");
            }
        }
    }
}
