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
    private ImageIcon imgFechada1;
    private ImageIcon imgFechada2;
    private ImageIcon imgBandeira;
    private ImageIcon imgMina;
    private ImageIcon[] imgAbertas;

    public TelaCampoMinado(int linhas, int colunas, int minas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;
        this.tabuleiro = new Tabuleiro(linhas, colunas, minas);
        carregarImagens();
        inicializarInterface();
    }
    /**
     * Carrega todas as imagens necessárias para o jogo a partir do diretório "Images".
     */
    public void carregarImagens() {

        imgFechada1 = new ImageIcon(getClass().getResource("/Images/fechada1.png"));
        imgFechada2 = new ImageIcon(getClass().getResource("/Images/fechada2.png"));
        imgBandeira = new ImageIcon(getClass().getResource("/Images/bandeira.png"));
        imgMina = new ImageIcon(getClass().getResource("/Images/mina.png"));

        // Carrega as imagens de "aberta0.png" até "aberta8.png"
        imgAbertas = new ImageIcon[9];
        for (int i = 0; i < 9; i++) {
            imgAbertas[i] = new ImageIcon(getClass().getResource("/Images/aberta" + i + ".png"));
        }
    }

    //Inicializa a interface gráfica do Campo Minado.
    private void inicializarInterface() {
        frame = new JFrame("Campo Minado");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(linhas, colunas));

        // Inicializa a matriz de botões
        botoes = new JButton[linhas][colunas];

        // Loop para criar e adicionar os botões à grade
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                JButton botao = new JButton();
                botao.setPreferredSize(new Dimension(30, 30)); // Células com 30x30px
                botao.setIcon((i + j) % 2 == 0 ? imgFechada1 : imgFechada2); // Alterna fechada1 e fechada2

                final int linha = i; // Variável final para uso no listener de evento
                final int coluna = j; // Variável final para uso no listener de evento

                // Adiciona um MouseListener para detectar cliques do mouse
                botao.addMouseListener(new MouseAdapter() {

                    //vai verificar se é botão esquerdo ou direito
                	@Override
                    public void mouseClicked(MouseEvent e) {
                        //se for esquerdo ele vai selecionar a celula equivalente, no entanto
                        //vai verificar se a celula está com bandeira
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            Celula celula = tabuleiro.getCelula(linha, coluna);
                            new Thread(() -> Som.tocarSom("src/audio/somClick.wav")).start();                            if (celula != null) {
                                //não faz nada se a célula estiver marcada com a bandeira
                                if (celula.estaMarcada()) {
                                }
                                else {
                                    tabuleiro.abrir(linha, coluna);
                                }
                            }
                        }
                        //se for direito vai adicionar ou remover bandeira
                        else if (SwingUtilities.isRightMouseButton(e)) {
                            Celula celula = tabuleiro.getCelula(linha, coluna);
                            new Thread(() -> Som.tocarSom("src/audio/somTocha.wav")).start();
                            if (celula != null && !celula.estaRevelada()) {
                                celula.alternarMarcacao();
                            }
                        }
                        atualizarBotoes(); //atualiza os botões da interface
                    }
                });

                botoes[i][j] = botao; //armazena o botão na matriz
                frame.add(botao); //adiciona o botao a janela
            }
        }

        //ajustar a largura e altura do frame com base na dificuldade
        int largura = colunas * 30;
        int altura = linhas * 30;
        frame.setSize(largura, altura);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null); // Centraliza a janela
        frame.setVisible(true);
        atualizarBotoes(); //serve pra garantir que os botões atualizem na inicialização
    }

    /**
     * esse metodo atualiza o estado visual de todos os botões com base no estado atual das células.
     * este método será chamado após cada interação do usuário ou quando o estado do jogo muda.
     */
    private void atualizarBotoes() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                //pega a célula correspondente do tabuleiro
                Celula celula = tabuleiro.getCelula(i, j);
                JButton botao = botoes[i][j]; //pega o botão correspondente na interface
                
              //entra se a celula estiver revelada
                if (celula.estaRevelada()) {
                    //entra se a celula revelada tiver uma mina
                    if (celula instanceof CelulaComMina) {
                        botao.setIcon(imgMina);
                        new Thread(() -> Som.tocarSom("src/audio/somExplosao.wav")).start();
                        //lógica para fim de jogo (adiciona ai bruno ou leleo)
                    }
                    else if (celula instanceof CelulaVazia) {
                        //entra se a celula revelada for vazia
                        int minasVizinhas = ((CelulaVazia) celula).getMinasAoRedor();
                        botao.setIcon(imgAbertas[minasVizinhas]);
                        /** espaço da lógica para revelar células vazias ao redor se minasVizinhas for 0
                         *aí bruno ou leleo adiciona a exception
                         *inclusive na classe Tabuleiro tem o metodo
                         *revelarCelulasVaziasProximas
                         *fica a criterio de voces
                         *talvez tenham que mexer na logica desse metodo
                         */
                    }
                }
                //se a celula estiver marcada com uma bandeira e não estiver revelada
                else if (celula.estaMarcada()) {
                    botao.setIcon(imgBandeira);
                }
                else {
                    //célula fechada, alterna as imagens da grama escura e clara
                    botao.setIcon((i + j) % 2 == 0 ? imgFechada1 : imgFechada2);
                }
            }
        }
    }
}