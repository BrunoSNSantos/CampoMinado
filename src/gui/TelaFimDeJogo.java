package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import audio.MusicaFundo;

public class TelaFimDeJogo extends JFrame {
	
	public TelaFimDeJogo(boolean vitoria) {
		setTitle("Fim de Jogo");
		setSize(500, 300);
		setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);
        setResizable(false);
        
        JLabel mensagem = new JLabel(vitoria ? "Parabén, você venceu!!!" : "Kaboom! Você perdeu!");
        mensagem.setFont(new Font("Arial", Font.BOLD, 22));
        mensagem.setHorizontalAlignment(SwingConstants.CENTER);
        mensagem.setBounds(50, 30, 400, 50);
        add(mensagem);
        
        JButton btnMenu = new JButton("Voltar ao Menu");
        btnMenu.setBounds(100, 120, 130, 40);
        btnMenu.addActionListener(e -> {
            dispose();
            new TelaMenuInicial();
        });
        add(btnMenu);
        
        JButton btnSair = new JButton("Sair");
        btnSair.setBounds(270, 120, 130, 40);
        btnSair.addActionListener(e -> System.exit(0));
        add(btnSair);
	}
}
