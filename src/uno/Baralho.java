package uno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Baralho {
    private Carta[] cartas;
    private int quantidade;

    public Baralho() {
        cartas = new Carta[108];
        quantidade = 0;
        criarCartas();
        embaralhar();
    }

    private void criarCartas() {
        String[] cores = {"Vermelho", "Amarelo", "Verde", "Azul"};

        for (String cor : cores) {
            adicionar(cor, "0");

            for (int numero = 1; numero <= 9; numero++) {
                adicionar(cor, String.valueOf(numero));
                adicionar(cor, String.valueOf(numero));
            }

            for (int i = 0; i < 2; i++) {
                adicionar(cor, "Bloqueio");
                adicionar(cor, "Inverter");
                adicionar(cor, "+2");
            }
        }

        for (int i = 0; i < 4; i++) {
            adicionar("Sem cor", "Coringa");
            adicionar("Sem cor", "Coringa +4");
        }
    }

    public void adicionar(String cor, String valor) {
        if (quantidade < cartas.length) {
            cartas[quantidade] = new Carta(cor, valor);
            quantidade++;
        }
    }

    public void embaralhar() {
        List<Carta> lista = new ArrayList<>();
        for (int i = 0; i < quantidade; i++) {
            lista.add(cartas[i]);
        }
        Collections.shuffle(lista);
        for (int i = 0; i < quantidade; i++) {
            cartas[i] = lista.get(i);
        }
    }

    public Carta comprar() {
        if (quantidade == 0) {
            return null;
        }

        Carta carta = cartas[quantidade - 1];
        cartas[quantidade - 1] = null;
        quantidade--;
        return carta;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
