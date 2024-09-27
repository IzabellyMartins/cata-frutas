package modelos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Random;

public class Tabuleiro extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private Image tileImage; 
    private BufferedImage[] sprites; 

    private int posX, posY; 
    private int frameAtual; 
    private int direcao; // 0: Baixo, 1: Esquerda, 2: Direita, 3: Cima

    private Timer timer;

    // Array com os nomes dos personagens disponíveis
    private String[] personagens = { "dwarf1", "dwarf2", "dwarf3", "dwarf4", "dwarf5", "dwarf6" };
    private String personagemSelecionado; // Armazena o nome do personagem selecionado aleatoriamente

    public Tabuleiro() {
        selecionarPersonagemAleatorio(); 
        carregarImagens();
        posX = 3; // Posição inicial no meio do tabuleiro (PRECISAMOS AJEITAR PARA NASCER NO MEIO AINDA)
        posY = 3;
        frameAtual = 0;
        direcao = 0; 
        timer = new Timer(150, this); // Timer para alternar frames da animação
        timer.start();

        // Adiciona um KeyListener para capturar as teclas de movimento
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                moverPersonagem(e);
            }
        });

        setFocusable(true); 
    }

    // Função para selecionar aleatoriamente um personagem
    private void selecionarPersonagemAleatorio() {
        Random random = new Random();
        personagemSelecionado = personagens[random.nextInt(personagens.length)];
    }

    // Carregar os sprites individuais com base no personagem selecionado
    private void carregarImagens() {
        try {
            tileImage = new ImageIcon(getClass().getResource("/imagens/grama.png")).getImage(); 

            // Carregar os sprites individuais do personagem selecionado
            sprites = new BufferedImage[12]; // 4 direções x 3 frames
            for (int i = 0; i < 3; i++) {
                // Frente (baixo)
                sprites[i] = ImageIO.read(getClass().getResource("/imagens/" + personagemSelecionado + "_frente" + (i + 1) + ".png"));
                // Costas (cima)
                sprites[i + 3] = ImageIO.read(getClass().getResource("/imagens/" + personagemSelecionado + "_costas" + (i + 1) + ".png"));
                // Esquerda
                sprites[i + 6] = ImageIO.read(getClass().getResource("/imagens/" + personagemSelecionado + "_esquerda" + (i + 1) + ".png"));
                // Direita
                sprites[i + 9] = ImageIO.read(getClass().getResource("/imagens/" + personagemSelecionado + "_direita" + (i + 1) + ".png"));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Desenha o fundo
        g.drawImage(tileImage, 0, 0, getWidth(), getHeight(), this);

        // Desenha o personagem baseado na direção e no frame atual
        int spriteIndex = direcao * 3 + frameAtual; // Calcula o índice com base na direção e no frame
        g.drawImage(sprites[spriteIndex], posX * 64, posY * 64, 64, 64, this); 
    }

    // Movimenta o personagem baseado na tecla pressionada
    private void moverPersonagem(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                direcao = 2; // Esquerda
                posX = Math.max(0, posX - 1); // Movimenta para a esquerda
                break;
            case KeyEvent.VK_RIGHT:
                direcao = 3; // Direita
                posX = Math.min(getWidth() / 64 - 1, posX + 1); // Movimenta para a direita
                break;
            case KeyEvent.VK_UP:
                direcao = 1; // Cima
                posY = Math.max(0, posY - 1); // Movimenta para cima
                break;
            case KeyEvent.VK_DOWN:
                direcao = 0; // Baixo
                posY = Math.min(getHeight() / 64 - 1, posY + 1); // Movimenta para baixo
                break;
        }

        // Atualiza o frame da animação
        frameAtual = (frameAtual + 1) % 3;

        // Repaint para desenhar o novo estado do tabuleiro
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frameAtual = (frameAtual + 1) % 3; // Alterna o frame atual da animação
        repaint(); // Redesenha o tabuleiro com a nova posição e frame
    }
}



