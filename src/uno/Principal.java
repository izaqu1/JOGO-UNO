
package uno;

import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        JogoUno jogo = new JogoUno();

        boolean vezJogador = true;
        boolean partidaAtiva = true;

        System.out.println("===== JOGO UNO =====");

        while (partidaAtiva) {

            // Verifica se alguém venceu.
            if (jogo.jogadorVenceu()) {
                System.out.println("\nParabéns! Você venceu!");
                break;
            }

            if (jogo.maquinaVenceu()) {
                System.out.println("\nA máquina venceu!");
                break;
            }

            // Verifica se a partida terminou em empate.
            if (jogo.jogoEmpatado()) {
                System.out.println("\n===== EMPATE =====");
                System.out.println(
                    "O baralho acabou e nenhum jogador pode jogar."
                );
                break;
            }

            if (vezJogador) {

                // Se não houver jogada nem cartas para comprar.
                if (!jogo.jogadorPodeJogar()
                        && jogo.baralhoEstaVazio()) {

                    System.out.println(
                        "\nVocê não possui jogadas possíveis e o baralho acabou."
                    );

                    vezJogador = false;
                    continue;
                }

                System.out.println("\n===== SUA VEZ =====");

                System.out.println("Carta na mesa: "
                        + jogo.getCartaAtual().mostrar());

                System.out.println("Cor vigente: "
                        + jogo.getCorVigente());

                jogo.mostrarMaoJogador();

                System.out.println("\nDigite o número da carta.");
                System.out.println("-1 para comprar uma carta.");
                System.out.println("-2 para sair do jogo.");

                System.out.print("Sua escolha: ");

                // Lê a entrada como texto para evitar InputMismatchException.
                String entrada = scanner.nextLine();

                int escolha;

                try {
                    escolha = Integer.parseInt(entrada.trim());
                } catch (NumberFormatException e) {
                    System.out.println(
                        "Entrada inválida! Digite um número inteiro."
                    );
                    continue;
                }

                if (escolha == -2) {
                    System.out.println("Você saiu do jogo.");
                    break;
                }

                if (escolha == -1) {

                    if (jogo.baralhoEstaVazio()) {
                        System.out.println(
                            "Não há cartas disponíveis para comprar."
                        );
                        continue;
                    }

                    jogo.comprarCarta();

                    // Compra normal encerra o turno.
                    vezJogador = false;
                    continue;
                }

                boolean jogou = jogo.jogarCarta(escolha);

                if (!jogou) {
                    System.out.println(
                        "Jogada inválida! Tente novamente."
                    );
                    continue;
                }

                // Se jogou coringa, escolhe a nova cor.
                String valor = jogo.getCartaAtual().getValor();

                if (valor.equals("Coringa")
                        || valor.equals("Coringa +4")) {

                    escolherCor(scanner, jogo);
                }

                System.out.println("Você jogou: "
                        + jogo.getCartaAtual().mostrar());

                // Verifica a vitória antes de aplicar os efeitos.
                if (jogo.jogadorVenceu()) {
                    System.out.println("\nParabéns! Você venceu!");
                    break;
                }

                // Aplica os efeitos da carta.
                if (jogo.devePularVez()) {

                    if (jogo.existePenalidade()) {
                        jogo.aplicarEfeitoNaMaquina();
                    }

                    jogo.finalizarPuloDeVez();

                    System.out.println(
                        "A máquina perdeu a vez! Você joga novamente."
                    );

                    vezJogador = true;

                } else {
                    vezJogador = false;
                }

            } else {

                // Vez da máquina.
                jogo.jogarMaquina();

                // Verifica se a máquina venceu.
                if (jogo.maquinaVenceu()) {
                    System.out.println("\nA máquina venceu!");
                    break;
                }

                if (jogo.devePularVez()) {

                    if (jogo.existePenalidade()) {
                        jogo.aplicarEfeitoNoJogador();
                    }

                    jogo.finalizarPuloDeVez();

                    System.out.println(
                        "Você perdeu a vez! A máquina joga novamente."
                    );

                    vezJogador = false;

                } else {
                    vezJogador = true;
                }
            }
        }

        scanner.close();

        System.out.println("\n===== FIM DE JOGO =====");
    }

    // Permite escolher a cor depois de jogar um coringa.
    private static void escolherCor(Scanner scanner, JogoUno jogo) {

        boolean corValida = false;

        while (!corValida) {

            System.out.println("\nEscolha uma cor:");
            System.out.println("1 - Vermelho");
            System.out.println("2 - Azul");
            System.out.println("3 - Verde");
            System.out.println("4 - Amarelo");

            System.out.print("Sua escolha: ");

            String entrada = scanner.nextLine();

            int opcao;

            try {
                opcao = Integer.parseInt(entrada.trim());
            } catch (NumberFormatException e) {
                System.out.println(
                    "Entrada inválida! Digite um número de 1 a 4."
                );
                continue;
            }

            String cor = "";

            switch (opcao) {

                case 1:
                    cor = "Vermelho";
                    break;

                case 2:
                    cor = "Azul";
                    break;

                case 3:
                    cor = "Verde";
                    break;

                case 4:
                    cor = "Amarelo";
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }

            if (!cor.isEmpty()) {
                corValida = jogo.escolherCor(cor);
            }
        }

        System.out.println("Cor vigente: " + jogo.getCorVigente());
    }
}
