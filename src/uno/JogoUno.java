
package uno;

public class JogoUno {

    private Baralho baralho;
    private Carta cartaAtual;
    private Jogador jogador1;
    private Jogador jogador2;

    public JogoUno() {
        baralho = new Baralho();
        baralho.criarCartas();
        baralho.embaralhar();

        jogador1 = new Jogador("Jogador 1");
        jogador2 = new Jogador("Máquina");

        // Distribui 7 cartas para cada jogador
        for (int i = 0; i < 7; i++) {
            jogador1.receberCarta(baralho.comprarCarta());
            jogador2.receberCarta(baralho.comprarCarta());
        }

        // Define a primeira carta da mesa
        cartaAtual = baralho.comprarCarta();
    }

    public Carta getCartaAtual() {
        return cartaAtual;
    }

    // Mostra as cartas do jogador humano
    public void mostrarMaoJogador() {
        jogador1.mostrarMao();
    }

    // Verifica se uma carta pode ser jogada
    public boolean podeJogar(Carta carta) {
        return carta.getCor().equals(cartaAtual.getCor())
                || carta.getValor().equals(cartaAtual.getValor());
    }

    // O jogador humano tenta jogar uma carta
    public boolean jogarCarta(int indice) {
        Carta carta = jogador1.getCarta(indice);

        if (carta == null) {
            return false;
        }

        if (podeJogar(carta)) {
            jogador1.removerCarta(indice);
            cartaAtual = carta;
            return true;
        }

        return false;
    }

    // O jogador humano compra uma carta
    public void comprarCarta() {
        Carta cartaComprada = baralho.comprarCarta();

        if (cartaComprada != null) {
            jogador1.receberCarta(cartaComprada);

            System.out.println("Você comprou: "
                    + cartaComprada.getCor() + " - "
                    + cartaComprada.getValor());
        } else {
            System.out.println("O baralho acabou!");
        }
    }

    // A máquina procura uma carta válida
    public void jogarMaquina() {
        System.out.println("\nVez da máquina!");

        for (int i = 0; i < jogador2.quantidadeCartas(); i++) {
            Carta carta = jogador2.getCarta(i);

            if (podeJogar(carta)) {
                jogador2.removerCarta(i);
                cartaAtual = carta;

                System.out.println("A máquina jogou: "
                        + carta.getCor() + " - "
                        + carta.getValor());

                return;
            }
        }

        // Se não encontrar uma carta válida, compra
        Carta cartaComprada = baralho.comprarCarta();

        if (cartaComprada != null) {
            jogador2.receberCarta(cartaComprada);

            System.out.println("A máquina comprou uma carta.");
        } else {
            System.out.println("O baralho acabou!");
        }
    }

    // Verifica a vitória do jogador humano
    public boolean jogadorVenceu() {
        return jogador1.quantidadeCartas() == 0;
    }

    // Verifica a vitória da máquina
    public boolean maquinaVenceu() {
        return jogador2.quantidadeCartas() == 0;
    }
}
