
package uno;

import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        JogoUno jogo = new JogoUno();

        System.out.println("=== JOGO UNO ===");
        boolean continuar = true;

        while (continuar) {

            // Vez do jogador 1
            System.out.println("\n===== SUA VEZ =====");

            System.out.println("\nCarta na mesa: "
                    + jogo.getCartaAtual().getCor() + " - "
                    + jogo.getCartaAtual().getValor());

            jogo.mostrarMaoJogador();

            System.out.println("\nDigite o número da carta que deseja jogar.");
            System.out.println("Digite -1 para comprar uma carta.");
            System.out.println("Digite -2 para sair.");
            System.out.print("Sua escolha: ");

            if (!entrada.hasNextInt()) {
                System.out.println(
                        "Entrada inválida! Digite um número inteiro."
                );
                entrada.next();
                continue;
            }

            int indice = entrada.nextInt();

            if (indice == -2) {
                continuar = false;
                System.out.println("Jogo encerrado!");
                continue;
            }

            if (indice == -1) {
                jogo.comprarCarta();
            } else if (jogo.jogarCarta(indice)) {
                System.out.println("\nJogada aceita!");
            } else {
                System.out.println("\nJogada inválida!");
                System.out.println(
                        "Escolha uma carta válida ou digite -1 para comprar."
                );
                continue;
            }

            // Verifica se o jogador venceu
            if (jogo.jogadorVenceu()) {
                System.out.println("\nPARABÉNS! VOCÊ VENCEU O UNO!");
                continuar = false;
                continue;
            }

            // Vez da máquina
            jogo.jogarMaquina();

            // Verifica se a máquina venceu
            if (jogo.maquinaVenceu()) {
                System.out.println("\nA MÁQUINA VENCEU O UNO!");
                continuar = false;
            }
        }

        entrada.close();
    }
}
