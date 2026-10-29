
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

            if (vezJogador) {

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

                int escolha = scanner.nextInt();

                if (escolha == -2) {
                    System.out.println("Você saiu do jogo.");
                    break;
                }

                if (escolha == -1) {
                    jogo.comprarCarta();

                    // Compra normal encerra o turno do jogador.
                    vezJogador = false;
                    continue;
                }

                boolean jogou = jogo.jogarCarta(escolha);

                if (!jogou) {
                    System.out.println("Jogada inválida! Tente novamente.");
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

                // Aplica o efeito antes de verificar o próximo turno.
                if (jogo.devePularVez()) {

                    if (jogo.existePenalidade()) {
                        jogo.aplicarEfeitoNaMaquina();
                    }

                    jogo.finalizarPuloDeVez();

                    System.out.println(
                            "A máquina perdeu a vez! Você joga novamente.");

                    // Continua sendo a vez do jogador.
                    vezJogador = true;

                } else {
                    vezJogador = false;
                }

                if (jogo.jogadorVenceu()) {
                    System.out.println("\nParabéns! Você venceu!");
                    partidaAtiva = false;
                }

            } else {

                jogo.jogarMaquina();

                if (jogo.maquinaVenceu()) {
                    System.out.println("\nA máquina venceu!");
                    partidaAtiva = false;
                    continue;
                }

                if (jogo.devePularVez()) {

                    if (jogo.existePenalidade()) {
                        jogo.aplicarEfeitoNoJogador();
                    }

                    jogo.finalizarPuloDeVez();

                    System.out.println(
                            "Você perdeu a vez! A máquina joga novamente.");

                    // Continua sendo a vez da máquina.
                    vezJogador = false;

                } else {
                    vezJogador = true;
                }

                if (jogo.jogadorVenceu()) {
                    System.out.println("\nVocê venceu!");
                    partidaAtiva = false;
                }
            }
        }

        scanner.close();
        System.out.println("\n===== FIM DE JOGO =====");
    }

    private static void escolherCor(Scanner scanner, JogoUno jogo) {

        boolean corValida = false;

        while (!corValida) {

            System.out.println("\nEscolha uma cor:");
            System.out.println("1 - Vermelho");
            System.out.println("2 - Azul");
            System.out.println("3 - Verde");
            System.out.println("4 - Amarelo");

            System.out.print("Sua escolha: ");

            int opcao = scanner.nextInt();

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
