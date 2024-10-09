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
    private int direcao; 

    private Timer timer;

    private String[][] tabuleiroObjetos; // Matriz para representar objetos no tabuleiro
    private int pedras = 3;
    private int arvores = 7;
    private int frutas = 5;
    
    private Random random = new Random(); // Instância de Random para ser utilizada globalmente

    private String[] personagens = { "dwarf1", "dwarf2", "dwarf3", "dwarf4", "dwarf5", "dwarf6" };
    private String personagemSelecionado; 

    private final int TILE_SIZE = 64; // Tamanho do tile (quadrado) do tabuleiro
    private final int NUM_TILES_X = 6; // Número de tiles na horizontal
    private final int NUM_TILES_Y = 6; // Número de tiles na vertical

    public Tabuleiro() {
        tabuleiroObjetos = new String[NUM_TILES_X][NUM_TILES_Y]; // Inicializa o tabuleiro
        selecionarPersonagemAleatorio(); 
        carregarImagens();
        gerarObjetosNoTabuleiro();
        gerarPosicaoInicialPersonagem(); // Gera posição inicial aleatória do personagem

        frameAtual = 0;
        direcao = 0; 
        timer = new Timer(150, this); 
        timer.start();
        setPreferredSize(new Dimension(NUM_TILES_X * TILE_SIZE, NUM_TILES_Y * TILE_SIZE));
        setPreferredSize(new Dimension(900, 900));
        
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
        personagemSelecionado = personagens[random.nextInt(personagens.length)];
    }
    // Carregar os sprites individuais com base no personagem selecionado
    private void carregarImagens() {
        try {
            tileImage = new ImageIcon(getClass().getResource("/imagens/grama.png")).getImage();

            sprites = new BufferedImage[12]; 
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

    private void gerarObjetosNoTabuleiro() {
        int objetosRestantes = pedras + arvores + frutas;

        while (objetosRestantes > 0) {
            int x = random.nextInt(NUM_TILES_X); 
            int y = random.nextInt(NUM_TILES_Y); 
            
            if (tabuleiroObjetos[x][y] == null) { 
                if (pedras > 0) {
                    tabuleiroObjetos[x][y] = "pedra";
                    pedras--;
                } else if (arvores > 0) {
                    tabuleiroObjetos[x][y] = "arvore" + random.nextInt(7); 
                    arvores--;
                } else if (frutas > 0) {
                    tabuleiroObjetos[x][y] = "fruta" + random.nextInt(7); 
                    frutas--;
                }
                objetosRestantes--;
            }
        }
    }
    // Faz o personagem nascer aleatóriamente no mapa 
    private void gerarPosicaoInicialPersonagem() {
        boolean posicaoValida = false;
        while (!posicaoValida) {
            int x = random.nextInt(NUM_TILES_X);
            int y = random.nextInt(NUM_TILES_Y);
            
            if (tabuleiroObjetos[x][y] == null) { 
                posX = x;
                posY = y;
                posicaoValida = true; 
            }
        }
    }

    @Override
    // Desenha as pedras e árvores pelo mapa 
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < NUM_TILES_X; i++) {
            for (int j = 0; j < NUM_TILES_Y; j++) {
                g.drawImage(tileImage, i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);

                if (tabuleiroObjetos[i][j] != null) {
                    switch (tabuleiroObjetos[i][j]) {
                        case "pedra":
                            g.drawImage(new ImageIcon(getClass().getResource("/imagens/pedra.png")).getImage(), i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
                            break;
                        case "arvore1":
                        case "arvore2":
                        case "arvore3":
                        case "arvore4":
                        case "arvore5":
                        case "arvore6":
                        case "arvore7":
                            g.drawImage(new ImageIcon(getClass().getResource("/imagens/" + tabuleiroObjetos[i][j] + ".png")).getImage(), i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
                            break;
                        case "fruta1":
                        case "fruta2":
                        case "fruta3":
                        case "fruta4":
                        case "fruta5":
                        case "fruta6":
                        case "fruta7":
                            g.drawImage(new ImageIcon(getClass().getResource("/imagens/" + tabuleiroObjetos[i][j] + ".png")).getImage(), i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, this);
                            break;
                    }
                }
            }
        }

        // Desenha o personagem baseado na direção e no frame atual
        int spriteIndex = direcao * 3 + frameAtual;
        g.drawImage(sprites[spriteIndex], posX * TILE_SIZE, posY * TILE_SIZE, TILE_SIZE, TILE_SIZE, this); 
    }

    private void moverPersonagem(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                direcao = 2; 
                posX = Math.max(0, posX - 1); 
                break;
            case KeyEvent.VK_RIGHT:
                direcao = 3; 
                posX = Math.min(NUM_TILES_X - 1, posX + 1); 
                break;
            case KeyEvent.VK_UP:
                direcao = 1; 
                posY = Math.max(0, posY - 1); 
                break;
            case KeyEvent.VK_DOWN:
                direcao = 0; 
                posY = Math.min(NUM_TILES_Y - 1, posY + 1); 
                break;
        }

        frameAtual = (frameAtual + 1) % 3;

        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frameAtual = (frameAtual + 1) % 3; 
        repaint(); 
    }
}




           








