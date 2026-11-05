package uno;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class Tela {
    private static final Color FUNDO = new Color(18, 25, 38);
    private static final Color PAINEL = new Color(29, 40, 57);
    private static final Color VERDE_MESA = new Color(19, 105, 75);
    private static final Color VERDE_ESCURO = new Color(12, 75, 56);
    private static final Color TEXTO = new Color(245, 247, 250);
    private static final Color TEXTO_SECUNDARIO = new Color(174, 187, 202);
    private static final Color DESTAQUE = new Color(245, 190, 55);

    private final Jogo jogo;
    private final JFrame janela;
    private final JPanel painelMao;
    private JLabel labelQuantidade;
    private JLabel labelStatus;
    private JLabel labelCorAtual;
    private JLabel labelTopo;
    private JButton botaoComprar;

    public Tela() {
        jogo = new Jogo();

        configurarAparencia();

        janela = new JFrame("UNO | Jogo de cartas");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(1100, 760);
        janela.setMinimumSize(new Dimension(820, 620));
        janela.setLocationRelativeTo(null);
        janela.setLayout(new BorderLayout());
        janela.getContentPane().setBackground(FUNDO);

        JPanel conteudo = new JPanel(new BorderLayout(0, 18));
        conteudo.setOpaque(false);
        conteudo.setBorder(new EmptyBorder(22, 28, 22, 28));

        conteudo.add(criarCabecalho(), BorderLayout.NORTH);

        JPanel mesa = criarMesa();
        conteudo.add(mesa, BorderLayout.CENTER);

        JPanel rodape = new JPanel(new BorderLayout(0, 10));
        rodape.setOpaque(false);
        rodape.add(criarAreaStatus(), BorderLayout.NORTH);

        painelMao = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 12));
        painelMao.setOpaque(false);

        JScrollPane rolagem = new JScrollPane(painelMao);
        rolagem.setBorder(BorderFactory.createEmptyBorder());
        rolagem.setOpaque(false);
        rolagem.getViewport().setOpaque(false);
        rolagem.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        rolagem.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        rolagem.setPreferredSize(new Dimension(900, 175));

        JPanel areaMao = new JPanel(new BorderLayout(0, 6));
        areaMao.setOpaque(false);
        JLabel tituloMao = new JLabel("SUAS CARTAS");
        tituloMao.setForeground(TEXTO);
        tituloMao.setFont(new Font("SansSerif", Font.BOLD, 13));
        areaMao.add(tituloMao, BorderLayout.NORTH);
        areaMao.add(rolagem, BorderLayout.CENTER);
        rodape.add(areaMao, BorderLayout.CENTER);

        conteudo.add(rodape, BorderLayout.SOUTH);
        janela.add(conteudo, BorderLayout.CENTER);

        atualizarTela();
        janela.setVisible(true);
    }

    private void configurarAparencia() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // Mantém a aparência padrão caso o tema do sistema não esteja disponível.
        }
    }

    private JPanel criarCabecalho() {
        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setOpaque(false);

        JPanel marca = new JPanel(new BorderLayout(0, 3));
        marca.setOpaque(false);

        JLabel titulo = new JLabel("UNO");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 34));
        titulo.setForeground(DESTAQUE);

        JLabel subtitulo = new JLabel("DESAFIE A SORTE. JOGUE SUA CARTA.");
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 11));
        subtitulo.setForeground(TEXTO_SECUNDARIO);

        marca.add(titulo, BorderLayout.NORTH);
        marca.add(subtitulo, BorderLayout.SOUTH);

        JPanel contador = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 4));
        contador.setOpaque(false);

        JLabel textoContador = new JLabel("CARTAS NO BARALHO");
        textoContador.setFont(new Font("SansSerif", Font.BOLD, 10));
        textoContador.setForeground(TEXTO_SECUNDARIO);

        labelQuantidade = new JLabel("100");
        labelQuantidade.setFont(new Font("SansSerif", Font.BOLD, 20));
        labelQuantidade.setForeground(TEXTO);

        contador.add(textoContador);
        contador.add(labelQuantidade);

        cabecalho.add(marca, BorderLayout.WEST);
        cabecalho.add(contador, BorderLayout.EAST);
        return cabecalho;
    }

    private JPanel criarMesa() {
        JPanel mesa = new FeltroPanel();
        mesa.setLayout(new GridBagLayout());
        mesa.setBorder(new EmptyBorder(22, 22, 22, 22));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 22, 8, 22);
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;

        JPanel areaBaralho = new JPanel(new BorderLayout(0, 12));
        areaBaralho.setOpaque(false);

        JLabel tituloBaralho = new JLabel("BARALHO", SwingConstants.CENTER);
        tituloBaralho.setForeground(new Color(225, 241, 233));
        tituloBaralho.setFont(new Font("SansSerif", Font.BOLD, 13));

        botaoComprar = new JButton("<html><center>COMPRAR<br><span style='font-size:10px'>CLIQUE AQUI</span></center></html>");
        botaoComprar.setPreferredSize(new Dimension(145, 190));
        botaoComprar.setFont(new Font("SansSerif", Font.BOLD, 17));
        botaoComprar.setForeground(Color.WHITE);
        botaoComprar.setBackground(new Color(30, 45, 68));
        botaoComprar.setFocusPainted(false);
        botaoComprar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(245, 190, 55), 3),
                new EmptyBorder(12, 12, 12, 12)));
        botaoComprar.addActionListener(e -> comprarCarta());

        areaBaralho.add(tituloBaralho, BorderLayout.NORTH);
        areaBaralho.add(botaoComprar, BorderLayout.CENTER);

        JPanel areaPilha = new JPanel(new BorderLayout(0, 12));
        areaPilha.setOpaque(false);

        JLabel tituloPilha = new JLabel("CARTA DA MESA", SwingConstants.CENTER);
        tituloPilha.setForeground(new Color(225, 241, 233));
        tituloPilha.setFont(new Font("SansSerif", Font.BOLD, 13));

        labelTopo = new JLabel("", SwingConstants.CENTER);
        labelTopo.setPreferredSize(new Dimension(145, 190));
        labelTopo.setOpaque(true);
        labelTopo.setFont(new Font("SansSerif", Font.BOLD, 21));
        labelTopo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 3),
                new EmptyBorder(12, 12, 12, 12)));

        areaPilha.add(tituloPilha, BorderLayout.NORTH);
        areaPilha.add(labelTopo, BorderLayout.CENTER);

        JPanel infoCor = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        infoCor.setOpaque(false);
        JLabel legendaCor = new JLabel("COR ATUAL:");
        legendaCor.setForeground(new Color(225, 241, 233));
        legendaCor.setFont(new Font("SansSerif", Font.BOLD, 11));

        labelCorAtual = new JLabel("—");
        labelCorAtual.setForeground(DESTAQUE);
        labelCorAtual.setFont(new Font("SansSerif", Font.BOLD, 13));

        infoCor.add(legendaCor);
        infoCor.add(labelCorAtual);

        c.gridx = 0;
        mesa.add(areaBaralho, c);
        c.gridx = 1;
        mesa.add(criarSeparadorVisual(), c);
        c.gridx = 2;
        mesa.add(areaPilha, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        c.insets = new Insets(18, 5, 0, 5);
        mesa.add(infoCor, c);

        return mesa;
    }

    private JPanel criarSeparadorVisual() {
        JPanel separador = new JPanel();
        separador.setOpaque(false);
        separador.setPreferredSize(new Dimension(45, 10));
        JLabel versus = new JLabel("•");
        versus.setForeground(new Color(225, 241, 233, 150));
        versus.setFont(new Font("SansSerif", Font.BOLD, 28));
        separador.add(versus);
        return separador;
    }

    private JPanel criarAreaStatus() {
        JPanel status = new JPanel(new BorderLayout());
        status.setBackground(PAINEL);
        status.setBorder(new EmptyBorder(11, 15, 11, 15));

        labelStatus = new JLabel("Sua vez! Clique no baralho para comprar uma carta.");
        labelStatus.setForeground(TEXTO);
        labelStatus.setFont(new Font("SansSerif", Font.PLAIN, 13));

        status.add(labelStatus, BorderLayout.CENTER);
        return status;
    }

    private void atualizarTela() {
        labelQuantidade.setText(String.valueOf(jogo.getQuantidadeBaralho()));
        botaoComprar.setEnabled(jogo.getQuantidadeBaralho() > 0);

        Carta topo = jogo.getTopoJogo();
        if (topo != null) {
            labelTopo.setText("<html><center>" + topo.getValor()
                    + "<br><span style='font-size:12px'>" + topo.getCor()
                    + "</span></center></html>");
            labelTopo.setBackground(corDaCarta(topo.getCor()));
            labelTopo.setForeground(corDoTexto(topo.getCor()));
        }

        labelCorAtual.setText(jogo.getCorVigente());

        painelMao.removeAll();
        for (int i = 0; i < jogo.getQuantidadeMao(); i++) {
            adicionarBotaoCarta(i);
        }

        painelMao.revalidate();
        painelMao.repaint();
    }

    public void adicionarBotaoCarta(int indice) {
        Carta carta = jogo.getCartaMao(indice);
        if (carta == null) {
            return;
        }

        JButton botao = new JButton("<html><center>"
                + carta.getValor() + "<br><span style='font-size:10px'>"
                + carta.getCor() + "</span></center></html>");

        botao.setPreferredSize(new Dimension(105, 135));
        botao.setFont(new Font("SansSerif", Font.BOLD, 15));
        botao.setOpaque(true);
        botao.setBackground(corDaCarta(carta.getCor()));
        botao.setForeground(corDoTexto(carta.getCor()));
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 210), 2),
                new EmptyBorder(8, 5, 8, 5)));
        botao.setToolTipText("Clique para jogar esta carta");
        botao.addActionListener(e -> jogarCarta(indice));

        painelMao.add(botao);
    }

    private void comprarCarta() {
        int quantidadeAnterior = jogo.getQuantidadeMao();
        jogo.comprar();

        if (jogo.getQuantidadeMao() > quantidadeAnterior) {
            labelStatus.setText("Carta comprada! Confira sua mão.");
        } else {
            labelStatus.setText("O baralho está vazio. Não há cartas para comprar.");
        }
        atualizarTela();
    }

    private void jogarCarta(int indice) {
        Carta carta = jogo.getCartaMao(indice);
        if (!jogo.jogar(indice)) {
            JOptionPane.showMessageDialog(janela,
                    "Essa carta não combina com a cor ou o valor da carta da mesa.",
                    "Jogada inválida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if ("Coringa".equals(carta.getValor())
                || "Coringa +4".equals(carta.getValor())) {
            escolherCor();
        }

        labelStatus.setText("Você jogou " + carta.mostrar() + ".");
        atualizarTela();

        if (jogo.getQuantidadeMao() == 0) {
            JOptionPane.showMessageDialog(janela,
                    "Parabéns! Você ficou sem cartas.",
                    "Fim de jogo", JOptionPane.INFORMATION_MESSAGE);
            botaoComprar.setEnabled(false);
            for (java.awt.Component componente : painelMao.getComponents()) {
                componente.setEnabled(false);
            }
        }
    }

    private void escolherCor() {
        String[] cores = {"Vermelho", "Amarelo", "Verde", "Azul"};
        String cor = (String) JOptionPane.showInputDialog(
                janela,
                "Escolha a cor que ficará valendo:",
                "Escolher cor",
                JOptionPane.QUESTION_MESSAGE,
                null,
                cores,
                cores[0]);

        jogo.escolherCor(cor == null ? cores[0] : cor);
    }

    private Color corDaCarta(String cor) {
        switch (cor.toLowerCase(Locale.ROOT)) {
            case "vermelho":
                return new Color(205, 55, 58);
            case "amarelo":
                return new Color(247, 204, 55);
            case "verde":
                return new Color(35, 145, 91);
            case "azul":
                return new Color(48, 112, 195);
            default:
                return new Color(48, 53, 65);
        }
    }

    private Color corDoTexto(String cor) {
        return "Amarelo".equalsIgnoreCase(cor) ? new Color(28, 32, 38) : Color.WHITE;
    }

    private static class FeltroPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g2 = (Graphics2D) graphics.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            int largura = getWidth();
            int altura = getHeight();
            g2.setPaint(new GradientPaint(0, 0, VERDE_MESA,
                    largura, altura, VERDE_ESCURO));
            g2.fillRoundRect(0, 0, largura, altura, 34, 34);

            g2.setColor(new Color(255, 255, 255, 35));
            g2.setStroke(new java.awt.BasicStroke(2f));
            g2.drawRoundRect(10, 10, largura - 20, altura - 20, 28, 28);
            g2.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Tela::new);
    }
}
