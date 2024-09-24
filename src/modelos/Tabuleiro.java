package modelos;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Tabuleiro extends JPanel {
    
    private static final long serialVersionUID = 1L;
    private JButton[][] bordas;

    public Tabuleiro() {
        setLayout(new GridLayout(8, 8));
        bordas = new JButton[8][8];

        // Inicializando as bordas
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                bordas[i][j] = new JButton();
                
                // Carregar e redimensionar a imagem
                ImageIcon originalIcon = new ImageIcon(getClass().getResource("/imagens/bloco.png"));
                ImageIcon scaledIcon = new ImageIcon(originalIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
                bordas[i][j].setIcon(scaledIcon);

                bordas[i][j].setPreferredSize(new Dimension(30, 30));
                bordas[i][j].setEnabled(false);

                add(bordas[i][j]);
            }
        }
    }
}

