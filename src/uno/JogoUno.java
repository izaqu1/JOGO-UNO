
package uno;

public class JogoUno {

    private Baralho baralho;
    private Carta cartaAtual;
    private Jogador jogador;

    public JogoUno() {
        baralho = new Baralho();
        baralho.criarCartas();
        baralho.embaralhar();

        jogador = new Jogador("Jogador 1");

        for (int i = 0; i < 7; i++) {
            jogador.receberCarta(baralho.comprarCarta());
        }

        cartaAtual = baralho.comprarCarta();
    }

    public Carta getCartaAtual() {
        return cartaAtual;
    }

    public void mostrarMaoJogador() {
        jogador.mostrarMao();
    }

    public boolean podeJogar(Carta carta) {
        return carta.getCor().equals(cartaAtual.getCor())
                || carta.getValor().equals(cartaAtual.getValor());
    }

    public boolean jogarCarta(Carta carta) {
        if (podeJogar(carta)) {
            cartaAtual = carta;
            return true;
        }

        return false;
    }
}
