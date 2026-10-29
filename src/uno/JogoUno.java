
package uno;

public class JogoUno {

    private Baralho baralho;
    private Carta cartaAtual;
    private String corVigente;
    private Jogador jogador1;
    private Jogador jogador2;
    private int cartasParaComprar;
    private boolean pularVez;

    public JogoUno() {

        baralho = new Baralho();
        baralho.criarCartas();
        baralho.embaralhar();

        jogador1 = new Jogador("Jogador 1");
        jogador2 = new Jogador("Máquina");

        cartasParaComprar = 0;
        pularVez = false;

        // Distribui sete cartas para cada participante.
        for (int i = 0; i < 7; i++) {
            jogador1.receberCarta(baralho.comprarCarta());
            jogador2.receberCarta(baralho.comprarCarta());
        }

        // Procura uma carta inicial que não seja coringa.
        cartaAtual = baralho.comprarCarta();

        while (cartaAtual != null
                && (cartaAtual.getValor().equals("Coringa")
                || cartaAtual.getValor().equals("Coringa +4"))) {

            baralho.adicionarCarta(cartaAtual);
            baralho.embaralhar();
            cartaAtual = baralho.comprarCarta();
        }

        if (cartaAtual != null) {
            corVigente = cartaAtual.getCor();
        }
    }

    public String getCorVigente() {
        return corVigente;
    }

    public Carta getCartaAtual() {
        return cartaAtual;
    }

    public boolean escolherCor(String cor) {

        if (cor == null) {
            return false;
        }

        if (cor.equalsIgnoreCase("Vermelho")
                || cor.equalsIgnoreCase("Azul")
                || cor.equalsIgnoreCase("Verde")
                || cor.equalsIgnoreCase("Amarelo")) {

            corVigente = cor;
            return true;
        }

        return false;
    }

    public void mostrarMaoJogador() {
        jogador1.mostrarMao();
    }

    private boolean temCartaDaCor(Jogador jogador, String cor) {

        for (int i = 0; i < jogador.quantidadeCartas(); i++) {

            Carta carta = jogador.getCarta(i);

            if (carta != null
                    && carta.getCor().equalsIgnoreCase(cor)) {
                return true;
            }
        }

        return false;
    }

    public boolean podeJogar(Carta carta, Jogador jogador) {

        if (carta == null || jogador == null || cartaAtual == null) {
            return false;
        }

        if (carta.getValor().equals("Coringa")) {
            return true;
        }

        if (carta.getValor().equals("Coringa +4")) {
            return !temCartaDaCor(jogador, corVigente);
        }

        return carta.getCor().equalsIgnoreCase(corVigente)
                || carta.getValor().equals(cartaAtual.getValor());
    }

    // Registra o efeito da carta que acabou de ser jogada.
    private void registrarEfeito(Carta carta) {

        cartasParaComprar = 0;
        pularVez = false;

        String valor = carta.getValor();

        if (valor.equals("+2")) {

            cartasParaComprar = 2;
            pularVez = true;

        } else if (valor.equals("Coringa +4")) {

            cartasParaComprar = 4;
            pularVez = true;

        } else if (valor.equals("Bloqueio")
                || valor.equals("Inverter")) {

            pularVez = true;
        }
    }

    // Jogador humano tenta jogar uma carta.
    public boolean jogarCarta(int indice) {

        Carta carta = jogador1.getCarta(indice);

        if (carta == null || !podeJogar(carta, jogador1)) {
            return false;
        }

        jogador1.removerCarta(indice);
        cartaAtual = carta;

        if (!carta.getValor().equals("Coringa")
                && !carta.getValor().equals("Coringa +4")) {

            corVigente = carta.getCor();
        }

        registrarEfeito(carta);

        return true;
    }

    // Jogador humano compra uma carta.
    public void comprarCarta() {

        Carta cartaComprada = baralho.comprarCarta();

        if (cartaComprada != null) {

            jogador1.receberCarta(cartaComprada);

            System.out.println("Você comprou: "
                    + cartaComprada.getCor() + " - "
                    + cartaComprada.getValor());

        } else {

            System.out.println("O baralho acabou! Não há cartas para comprar.");
        }
    }

    // Máquina escolhe a cor que possui em maior quantidade.
    private String escolherCorMaquina() {

        String[] cores = {
            "Vermelho", "Azul", "Verde", "Amarelo"
        };

        String corEscolhida = "Vermelho";
        int maiorQuantidade = -1;

        for (String cor : cores) {

            int quantidade = 0;

            for (int i = 0; i < jogador2.quantidadeCartas(); i++) {

                Carta carta = jogador2.getCarta(i);

                if (carta.getCor().equalsIgnoreCase(cor)) {
                    quantidade++;
                }
            }

            if (quantidade > maiorQuantidade) {
                maiorQuantidade = quantidade;
                corEscolhida = cor;
            }
        }

        return corEscolhida;
    }

    // Máquina procura uma carta válida.
    public void jogarMaquina() {

        System.out.println("\nVez da máquina!");

        for (int i = 0; i < jogador2.quantidadeCartas(); i++) {

            Carta carta = jogador2.getCarta(i);

            if (podeJogar(carta, jogador2)) {

                jogador2.removerCarta(i);
                cartaAtual = carta;

                if (carta.getValor().equals("Coringa")
                        || carta.getValor().equals("Coringa +4")) {

                    corVigente = escolherCorMaquina();

                    System.out.println("A máquina escolheu a cor: "
                            + corVigente);

                } else {

                    corVigente = carta.getCor();
                }

                System.out.println("A máquina jogou: "
                        + carta.getCor() + " - "
                        + carta.getValor());

                registrarEfeito(carta);

                return;
            }
        }

        // Se não encontrar carta válida, tenta comprar.
        Carta cartaComprada = baralho.comprarCarta();

        if (cartaComprada != null) {

            jogador2.receberCarta(cartaComprada);

            System.out.println("A máquina comprou uma carta.");

        } else {

            System.out.println("O baralho acabou! A máquina não pode comprar.");
        }

        cartasParaComprar = 0;
        pularVez = false;
    }

    // Compra cartas de penalidade.
    private void comprarCartas(Jogador jogador, int quantidade) {

        for (int i = 0; i < quantidade; i++) {

            Carta cartaComprada = baralho.comprarCarta();

            if (cartaComprada != null) {

                jogador.receberCarta(cartaComprada);

            } else {

                System.out.println(
                        "O baralho acabou! Não foi possível comprar todas as cartas.");

                break;
            }
        }
    }

    public void aplicarEfeitoNaMaquina() {

        if (cartasParaComprar > 0) {

            int quantidade = cartasParaComprar;
            cartasParaComprar = 0;

            System.out.println("\nA máquina deverá comprar "
                    + quantidade + " cartas!");

            comprarCartas(jogador2, quantidade);
        }
    }

    public void aplicarEfeitoNoJogador() {

        if (cartasParaComprar > 0) {

            int quantidade = cartasParaComprar;
            cartasParaComprar = 0;

            System.out.println("\nVocê deverá comprar "
                    + quantidade + " cartas!");

            comprarCartas(jogador1, quantidade);
        }
    }

    public boolean devePularVez() {
        return pularVez;
    }

    public void finalizarPuloDeVez() {

        pularVez = false;
        cartasParaComprar = 0;
    }

    public boolean existePenalidade() {
        return cartasParaComprar > 0;
    }

    // Verifica se o baralho está vazio.
    public boolean baralhoEstaVazio() {
        return baralho.quantidadeCartas() == 0;
    }

    // Verifica se o jogador possui alguma jogada possível.
    public boolean jogadorPodeJogar() {

        for (int i = 0; i < jogador1.quantidadeCartas(); i++) {

            if (podeJogar(jogador1.getCarta(i), jogador1)) {
                return true;
            }
        }

        return false;
    }

    // Verifica se a máquina possui alguma jogada possível.
    public boolean maquinaPodeJogar() {

        for (int i = 0; i < jogador2.quantidadeCartas(); i++) {

            if (podeJogar(jogador2.getCarta(i), jogador2)) {
                return true;
            }
        }

        return false;
    }

    // Empate quando o baralho acaba e ninguém consegue jogar.
    public boolean jogoEmpatado() {

        return baralhoEstaVazio()
                && !jogadorPodeJogar()
                && !maquinaPodeJogar()
                && !jogadorVenceu()
                && !maquinaVenceu();
    }

    public boolean jogadorVenceu() {
        return jogador1.quantidadeCartas() == 0;
    }

    public boolean maquinaVenceu() {
        return jogador2.quantidadeCartas() == 0;
    }
}
