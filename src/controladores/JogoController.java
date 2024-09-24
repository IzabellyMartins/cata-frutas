package controladores;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import modelos.Tabuleiro;

public class JogoController {

    public void iniciarNovaPartida() {
        // Abre uma nova janela com o tabuleiro para nova partida
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Nova Partida");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(500, 500);

            // Adiciona o tabuleiro ao frame
            Tabuleiro tabuleiro = new Tabuleiro();
            frame.add(tabuleiro);

            frame.setVisible(true);
        });
    }

    public void personalizarPartida() {
        // Abre uma nova janela com o tabuleiro para personalização
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Personalizar Partida");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(500, 500);

            // Adiciona o tabuleiro ao frame
            Tabuleiro tabuleiro = new Tabuleiro();
            frame.add(tabuleiro);

            frame.setVisible(true);
        });
    }
}
