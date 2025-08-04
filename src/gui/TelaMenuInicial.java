package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import audio.MusicaFundo;
import audio.Som;

public class TelaMenuInicial extends JFrame {
    private JComboBox<String> comboDificuldade;
    private JButton btnIniciar;
    private JLabel lblNewLabel_2;

    /**
     * Construtor da classe TelaMenuInicial.
     * Configura a janela do menu, adiciona os componentes e os listeners de eventos.
     */

    public TelaMenuInicial() {
    	setIconImage(Toolkit.getDefaultToolkit().getImage(TelaMenuInicial.class.getResource("/Images/Campo-Minado.png")));
        setSize(883, 603); //Define o tamanho da janela (largura, altura)
        setLocationRelativeTo(null); //Centraliza a janela na tela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        
        MusicaFundo musica = new MusicaFundo();
        musica.tocarMusica("src/audio/musicaMenu.wav");

        btnIniciar = new JButton(""); //botão para iniciar o jogo
        btnIniciar.setBackground(new Color(111, 111, 111));
        btnIniciar.setIcon(new ImageIcon(TelaMenuInicial.class.getResource("/Images/botao.png")));
        btnIniciar.setBounds(262, 314, 301, 30);
        // Adiciona o efeito sonoro de clique do mouse no botão iniciar
        btnIniciar.addMouseListener(efeitoClickMouse);
        getContentPane().add(btnIniciar); //posiciona o botão embaixo na janela
        
        //adiciona um actionlistener ao botão "Iniciar Jogo"
        //quando o botão é clicado, o metodo iniciarJogo() é chamado
        btnIniciar.addActionListener(e -> {
            iniciarJogo();
        });
        try {        
			        JLabel lblNewLabel = new JLabel("");
			        lblNewLabel.setIcon(new ImageIcon(TelaMenuInicial.class.getResource("/Images/Campo-Minado.png")));
			        lblNewLabel.setBounds(36, 28, 833, 234);
			        getContentPane().add(lblNewLabel);
			        
			                //Cria um IconBox com as dificuldades
			                comboDificuldade = new JComboBox<>(new String[]{"Fácil", "Médio", "Difícil"});
			                comboDificuldade.setBackground(SystemColor.windowBorder);
			                comboDificuldade.setBounds(261, 386, 302, 30);
			                getContentPane().add(comboDificuldade);
			                comboDificuldade.setModel(new DefaultComboBoxModel(new String[] {"Selecione dificuldade:", "Fácil", "Médio", "Difícil"}));
			                comboDificuldade.setFont(new Font("Bodoni MT", Font.BOLD, 19));
			                // adiciona o efeito sonoro de clique do mouse
			                comboDificuldade.addMouseListener(efeitoClickMouse);
			                
			                lblNewLabel_2 = new JLabel("");
			                lblNewLabel_2.setIcon(new ImageIcon(TelaMenuInicial.class.getResource("/Images/Captura de tela 2025-08-03 151125.png")));
			                lblNewLabel_2.setBounds(0, -39, 921, 605);
			                getContentPane().add(lblNewLabel_2);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        setVisible(true); //torna a janela visivel
    }

    /*
     * Inicia o jogo com a dificuldade selecionada.
     * Obtem a dificuldade escolhida no jcombobox, define as dimensões do tabuleiro e o número de minas,
     * fecha a tela de menu e abre a tela principal do jogo.
     */
    private void iniciarJogo() {
        String dificuldade = (String) comboDificuldade.getSelectedItem(); //pega a dificuldade selecionada

        if ("Selecione dificuldade:".equals(dificuldade)) {
            try {
                throw new exceptions.NenhumaDificuldadeSelecionada();
            } catch (exceptions.NenhumaDificuldadeSelecionada ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return; // não continua com o jogo
            }
        }

        int linhas = 0, colunas = 0, minas = 0; //define as variaveis para linhas, colunas e quantidade de minas.

        switch (dificuldade) {
            case "Fácil" -> { linhas = 9; colunas = 9; minas = 10; } //Dificuldade Fácil: 9x9 com 10 minas
            case "Médio" -> { linhas = 16; colunas = 16; minas = 20; } //Dificuldade Médio: 16x16 com 20 minas
            case "Difícil" -> { linhas = 16; colunas = 30; minas = 40; } //Dificuldade Difícil: 16x30 com 40 minas
        }

        dispose(); //Fecha a tela de menu
        new TelaCampoMinado(linhas, colunas, minas); //Cria e exibe a nova tela do jogo
    }
    
    // metodo responsavel por gerar o som de clique do mouse
    private final MouseListener efeitoClickMouse = new MouseAdapter() {
    	@Override
    	public void mousePressed(MouseEvent e) {
    		new Thread(() -> Som.tocarSom("src/audio/somClick.wav")).start();
    	}
    };
}