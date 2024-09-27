package controladores;

import javax.swing.*;

import modelos.Tabuleiro;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JogoController {
    private JFrame mainFrame;

    public JogoController() {
        mainFrame = new JFrame("Jogo de Tabuleiro");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600); 
        mainFrame.setLocationRelativeTo(null);

        JButton carregarPartidaButton = new JButton("Carregar Nova Partida");
        carregarPartidaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarNovaPartida();
            }
        });

        mainFrame.add(carregarPartidaButton);
        mainFrame.setVisible(true);
    }

    public void iniciarNovaPartida() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Nova Partida");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            Tabuleiro tabuleiro = new Tabuleiro();
            frame.add(tabuleiro);
            frame.pack(); 
            frame.setLocationRelativeTo(null); 
            frame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        new JogoController();
    }
}





