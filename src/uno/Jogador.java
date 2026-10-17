
package uno;

import java.util.ArrayList;

public class Jogador {
    private String nome;
    private ArrayList<Carta> mao;

    public Jogador(String nome) {
        this.nome = nome;
        this.mao = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void receberCarta(Carta carta) {
        mao.add(carta);
    }

    public void mostrarMao() {
        System.out.println("Cartas de " + nome + ":");

        for (int i = 0; i < mao.size(); i++) {
            Carta carta = mao.get(i);

            System.out.println(i + " - "
                    + carta.getCor() + " - "
                    + carta.getValor());
        }
    }

    public Carta getCarta(int indice) {
        if (indice >= 0 && indice < mao.size()) {
            return mao.get(indice);
        }

        return null;
    }

    public void removerCarta(int indice) {
        if (indice >= 0 && indice < mao.size()) {
            mao.remove(indice);
        }
    }

    // Retorna a quantidade de cartas na mão
    public int quantidadeCartas() {
        return mao.size();
    }
}
