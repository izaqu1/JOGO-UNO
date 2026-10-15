package uno;

import java.util.Collections;
import java.util.ArrayList;

public class Baralho {

    private ArrayList<Carta> cartas;

    public Baralho() {
        cartas = new ArrayList<>();
    }

    public void adicionarCarta(Carta carta) {
        cartas.add(carta);
    }

    public void mostrarCartas() {
        for (Carta carta : cartas) {
            System.out.println(
                carta.getCor() + " - " + carta.getValor()
            );
        }
    }

    public void criarCartas() {
    	String[] cores = {
        "Vermelho", "Azul", "Verde", "Amarelo"
    	};

    	for (String cor : cores) {
    		for (int numero = 0; numero <= 9; numero++) {
    			cartas.add(new Carta(cor, String.valueOf(numero)));
    		}
    	}
    }
    public void embaralhar() {
        Collections.shuffle(cartas);
    }

    public Carta comprarCarta() {
    	if (cartas.isEmpty()) {
    		return null;
    }

    	return cartas.remove(0);
    }
    public int quantidadeCartas() {
        return cartas.size();
    }

}
