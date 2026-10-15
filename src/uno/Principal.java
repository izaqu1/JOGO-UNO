package uno;





public class Principal {

    public static void main(String[] args) {
    	JogoUno jogo = new JogoUno();

    	System.out.println("Carta na mesa:");
    	System.out.println(
    	    jogo.getCartaAtual().getCor() + " - " +
    	    jogo.getCartaAtual().getValor()
    	);

    	jogo.mostrarMaoJogador();
}

}
