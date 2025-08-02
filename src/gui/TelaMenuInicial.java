package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaMenuInicial extends JFrame {
    private JComboBox<String> comboDificuldade;
    private JButton btnIniciar;

    /**
     * Construtor da classe TelaMenuInicial.
     * Configura a janela do menu, adiciona os componentes e os listeners de eventos.
     */

    public TelaMenuInicial() {
        setTitle("Campo Minado - Menu"); //Define o título da janela
        setSize(300, 150); //Define o tamanho da janela (largura, altura)
        setLocationRelativeTo(null); //Centraliza a janela na tela
        setDefaultCloseOperation(EXIT_ON_CLOSE); //Define a operação de fechamento padrão
        setLayout(new BorderLayout()); //Define o layout da janela como BorderLayout

        JLabel label = new JLabel("Selecione a dificuldade:", JLabel.CENTER); //Cria um rótulo centralizado
        add(label, BorderLayout.NORTH); //Adiciona o rótulo na parte superior da janela

        //Cria um jcombobox com as dificuldades
        comboDificuldade = new JComboBox<>(new String[]{"Fácil", "Médio", "Difícil"});
        add(comboDificuldade, BorderLayout.CENTER); //adiciona no centro da janela

        btnIniciar = new JButton("Iniciar Jogo"); //botão para iniciar o jogo
        add(btnIniciar, BorderLayout.SOUTH); //posiciona o botão embaixo na janela

        //adiciona um actionlistener ao botão "Iniciar Jogo"
        //quando o botão é clicado, o metodo iniciarJogo() é chamado
        btnIniciar.addActionListener(e -> iniciarJogo());

        setVisible(true); //torna a janela visivel
    }

    /**
     * Inicia o jogo com a dificuldade selecionada.
     * Obtem a dificuldade escolhida no jcombobox, define as dimensões do tabuleiro e o número de minas,
     * fecha a tela de menu e abre a tela principal do jogo.
     */
    private void iniciarJogo() {
        String dificuldade = (String) comboDificuldade.getSelectedItem(); //pega a dificuldade selecionada
        int linhas = 0, colunas = 0, minas = 0; //define as variaveis para linhas, colunas e quantidade de minas.

        switch (dificuldade) {
            case "Fácil" -> { linhas = 9; colunas = 9; minas = 10; } //Dificuldade Fácil: 9x9 com 10 minas
            case "Médio" -> { linhas = 16; colunas = 16; minas = 20; } //Dificuldade Médio: 16x16 com 20 minas
            case "Difícil" -> { linhas = 16; colunas = 30; minas = 40; } //Dificuldade Difícil: 16x30 com 40 minas
        }

        dispose(); //Fecha a tela de menu
        new TelaCampoMinado(linhas, colunas, minas); //Cria e exibe a nova tela do jogo
    }
}