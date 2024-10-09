 package controladores;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import modelos.Tabuleiro;

public class JogoController {
    private JFrame mainFrame;

    public JogoController() {
        mainFrame = new JFrame("Jogo de Tabuleiro");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600); 
        mainFrame.setLocationRelativeTo(null);

        // Cria um painel para adicionar o botão e outros componentes no futuro
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        
        JButton carregarPartidaButton = new JButton("Carregar Nova Partida");
        carregarPartidaButton.addActionListener(e -> iniciarNovaPartida());

        // Adiciona o botão ao painel principal
        painelPrincipal.add(carregarPartidaButton, BorderLayout.CENTER);

        // Adiciona o painel principal à janela principal
        mainFrame.add(painelPrincipal);
        mainFrame.setVisible(true);
    }

    public void iniciarNovaPartida() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Nova Partida");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Cria o Tabuleiro
            Tabuleiro tabuleiro = new Tabuleiro();

            // Define o layout do JFrame e adiciona o Tabuleiro
            frame.setLayout(new BorderLayout());
            frame.add(tabuleiro, BorderLayout.CENTER);

            // Ajusta o tamanho e visibilidade do JFrame
            frame.pack();  // Ajusta o frame para o tamanho preferido do tabuleiro
            frame.setLocationRelativeTo(null);  // Centraliza na tela
            frame.setVisible(true);

            // Força a revalidação e repintura do tabuleiro
            tabuleiro.revalidate();
            tabuleiro.repaint();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JogoController::new);
    }
}










