package uno;

import java.util.ArrayList;

public class Pilha {
    private ArrayList<Carta> cartas;

    public Pilha() {
        cartas = new ArrayList<>();
    }

    public void empilhar(Carta carta) {
        if (carta != null) {
            cartas.add(carta);
        }
    }

    public Carta getTopo() {
        if (cartas.isEmpty()) {
            return null;
        }
        return cartas.get(cartas.size() - 1);
    }

    public int getQuantidade() {
        return cartas.size();
    }
}
