
package uno;

import java.util.ArrayList;
import java.util.Collections;

public class Baralho {

    private ArrayList<Carta> cartas;

    public Baralho() {
        cartas = new ArrayList<>();
    }

    // Adiciona uma carta ao baralho.
    public void adicionarCarta(Carta carta) {

        if (carta != null) {
            cartas.add(carta);
        }
    }

    // Mostra todas as cartas disponíveis.
    public void mostrarCartas() {

        for (Carta carta : cartas) {

            System.out.println(
                carta.getCor() + " - " + carta.getValor()
            );
        }
    }

    // Cria as 108 cartas do UNO.
    public void criarCartas() {

        String[] cores = {
            "Vermelho", "Azul", "Verde", "Amarelo"
        };

        for (String cor : cores) {

            // Uma carta de número 0 por cor.
            cartas.add(new Carta(cor, "0"));

            // Duas cartas de cada número de 1 a 9.
            for (int numero = 1; numero <= 9; numero++) {

                cartas.add(new Carta(cor, String.valueOf(numero)));
                cartas.add(new Carta(cor, String.valueOf(numero)));
            }

            // Duas cartas de cada tipo de ação.
            for (int i = 0; i < 2; i++) {

                cartas.add(new Carta(cor, "Bloqueio"));
                cartas.add(new Carta(cor, "Inverter"));
                cartas.add(new Carta(cor, "+2"));
            }
        }

        // Quatro coringas de cada tipo.
        for (int i = 0; i < 4; i++) {

            cartas.add(new Carta("Sem cor", "Coringa"));
            cartas.add(new Carta("Sem cor", "Coringa +4"));
        }
    }

    // Embaralha as cartas.
    public void embaralhar() {
        Collections.shuffle(cartas);
    }

    // Compra uma carta do baralho.
    public Carta comprarCarta() {

        if (cartas.isEmpty()) {
            return null;
        }

        return cartas.remove(0);
    }

    // Retorna a quantidade de cartas disponíveis.
    public int quantidadeCartas() {
        return cartas.size();
    }
}
