package uno;

public class Jogo {
    private Baralho baralho;
    private Pilha pilhaJogo;
    private Carta[] mao;
    private int quantidadeMao;
    private String corVigente;

    public Jogo() {
        baralho = new Baralho();
        pilhaJogo = new Pilha();
        mao = new Carta[108];
        quantidadeMao = 0;

        for (int i = 0; i < 7; i++) {
            comprar();
        }

        Carta inicial = baralho.comprar();
        while (inicial != null
                && ("Coringa".equals(inicial.getValor())
                || "Coringa +4".equals(inicial.getValor()))) {
            baralho.adicionar(inicial.getCor(), inicial.getValor());
            baralho.embaralhar();
            inicial = baralho.comprar();
        }

        if (inicial != null) {
            pilhaJogo.empilhar(inicial);
            corVigente = inicial.getCor();
        }
    }

    public void comprar() {
        if (quantidadeMao >= mao.length) {
            return;
        }

        Carta carta = baralho.comprar();
        if (carta != null) {
            mao[quantidadeMao] = carta;
            quantidadeMao++;
        }
    }

    public boolean jogar(int indice) {
        if (indice < 0 || indice >= quantidadeMao) {
            return false;
        }

        Carta carta = mao[indice];
        if (!jogadaValida(carta)) {
            return false;
        }

        pilhaJogo.empilhar(carta);

        for (int i = indice; i < quantidadeMao - 1; i++) {
            mao[i] = mao[i + 1];
        }

        quantidadeMao--;
        mao[quantidadeMao] = null;

        if (!"Coringa".equals(carta.getValor())
                && !"Coringa +4".equals(carta.getValor())) {
            corVigente = carta.getCor();
        }

        return true;
    }

    private boolean jogadaValida(Carta carta) {
        Carta topo = pilhaJogo.getTopo();
        if (carta == null || topo == null) {
            return false;
        }

        if ("Coringa".equals(carta.getValor())) {
            return true;
        }

        if ("Coringa +4".equals(carta.getValor())) {
            return !temCartaDaCorVigente();
        }

        return carta.getCor().equalsIgnoreCase(corVigente)
                || carta.getValor().equals(topo.getValor());
    }

    private boolean temCartaDaCorVigente() {
        for (int i = 0; i < quantidadeMao; i++) {
            if (mao[i].getCor().equalsIgnoreCase(corVigente)) {
                return true;
            }
        }
        return false;
    }

    public boolean escolherCor(String cor) {
        if (cor == null) {
            return false;
        }

        String[] cores = {"Vermelho", "Amarelo", "Verde", "Azul"};
        for (String permitida : cores) {
            if (permitida.equalsIgnoreCase(cor)) {
                corVigente = permitida;
                return true;
            }
        }
        return false;
    }

    public Carta getCartaMao(int indice) {
        if (indice < 0 || indice >= quantidadeMao) {
            return null;
        }
        return mao[indice];
    }

    public Carta getTopoJogo() {
        return pilhaJogo.getTopo();
    }

    public int getQuantidadeMao() {
        return quantidadeMao;
    }

    public int getQuantidadeBaralho() {
        return baralho.getQuantidade();
    }

    public String getCorVigente() {
        return corVigente;
    }
}
