package gui;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import audio.Som;

public class TelaCampoMinado {
    private JFrame frame;
    private JButton[][] botoes;
    private Tabuleiro tabuleiro;
    private int linhas;
    private int colunas;
    private int minas;

    // Ícones gráficos usados no jogo
    private ImageIcon imgFechada1;
    private ImageIcon imgFechada2;
    private ImageIcon imgBandeira;
    private ImageIcon imgMina;
    private ImageIcon[] imgAbertas;

    // Controla se o jogo terminou
    private boolean jogoFinalizado = false;

    //Construtor que inicia a interface com o número de linhas, colunas e minas.
    public TelaCampoMinado(int linhas, int colunas, int minas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;
        this.tabuleiro = new Tabuleiro(linhas, colunas, minas);
        carregarImagens();
        inicializarInterface();
    }

    //carrega as imagens
    private void carregarImagens() {
        imgFechada1 = new ImageIcon(getClass().getResource("/Images/fechada1.png"));
        imgFechada2 = new ImageIcon(getClass().getResource("/Images/fechada2.png"));
        imgBandeira = new ImageIcon(getClass().getResource("/Images/bandeira.png"));
        imgMina = new ImageIcon(getClass().getResource("/Images/mina.png"));

        imgAbertas = new ImageIcon[9];
        for (int i = 0; i < 9; i++) {
            imgAbertas[i] = new ImageIcon(getClass().getResource("/Images/aberta" + i + ".png"));
        }
    }

    //inicializa a interface gráfica com os botões
    private void inicializarInterface() {
        frame = new JFrame("Campo Minado");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(linhas, colunas));

        botoes = new JButton[linhas][colunas];

        // Cria e adiciona os botões com seus listeners
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                JButton botao = new JButton();
                botao.setPreferredSize(new Dimension(30, 30));
                botao.setIcon((i + j) % 2 == 0 ? imgFechada1 : imgFechada2);

                final int linha = i;
                final int coluna = j;

                // Listener de clique do mouse
                botao.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (jogoFinalizado) return;

                        Celula celula = tabuleiro.getCelula(linha, coluna);

                        if (SwingUtilities.isLeftMouseButton(e)) {
                            // Clique esquerdo revela a célula
                            new Thread(() -> Som.tocarSom("src/audio/somClick.wav")).start();
                            if (celula != null && !celula.estaMarcada()) {
                                tabuleiro.abrir(linha, coluna);
                                
                                //verifica se tem mina
                                if (celula instanceof CelulaComMina) {
                                    new Thread(() -> Som.tocarSom("src/audio/somExplosao.wav")).start();
                                    encerrarJogo(false); // Encerra o jogo com derrota
                                } else if (verificarVitoria()) {
                                    encerrarJogo(true); // Encerra o jogo com vitória
                                }
                            }
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            // Clique direito alterna marcação, ou seja, coloca ou tira bandeira(tocha)
                            new Thread(() -> Som.tocarSom("src/audio/somTocha.wav")).start();
                            if (celula != null && !celula.estaRevelada()) {
                                celula.alternarMarcacao();
                            }
                        }

                        atualizarBotoes();
                    }
                });

                botoes[i][j] = botao;
                frame.add(botao);
            }
        }

        frame.setSize(colunas * 30, linhas * 30);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        atualizarBotoes();
    }

    //Atualiza os ícones dos botões com base no estado atual das células.
    private void atualizarBotoes() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                Celula celula = tabuleiro.getCelula(i, j);
                JButton botao = botoes[i][j];

                if (celula.estaRevelada()) {
                    if (celula instanceof CelulaComMina) {
                        botao.setIcon(imgMina);
                    } else if (celula instanceof CelulaVazia) {
                        int minasVizinhas = ((CelulaVazia) celula).getMinasAoRedor();
                        botao.setIcon(imgAbertas[minasVizinhas]);
                    }
                } else if (celula.estaMarcada()) {
                    botao.setIcon(imgBandeira);
                } else {
                    botao.setIcon((i + j) % 2 == 0 ? imgFechada1 : imgFechada2);
                }
            }
        }
    }

    // Função responsável por encerrar o jogo em caso de vitória ou derrota
    private void encerrarJogo(boolean vitoria) {
        jogoFinalizado = true;

        // Revela todas as células
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                Celula celula = tabuleiro.getCelula(i, j);
                if (!celula.estaRevelada()) {
                    celula.revelar();
                }
            }
        }

        atualizarBotoes();

        // Cria a tela de fim de jogo e fecha a janela do jogo
        SwingUtilities.invokeLater(() -> {
            new TelaFimDeJogo(vitoria);
            frame.dispose();
        });
    }

    // Método que verifica se todas as células sem minas foram reveladas, se sim, o jogador vence
    private boolean verificarVitoria() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                Celula celula = tabuleiro.getCelula(i, j);
                if (!celula.temMina() && !celula.estaRevelada()) {
                    return false;
                }
            }
        }
        return true;
    }
}
