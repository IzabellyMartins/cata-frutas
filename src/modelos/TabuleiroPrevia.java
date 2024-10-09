package modelos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Random;


public class TabuleiroPrevia extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private Image tileImage; 
    private BufferedImage[] sprites; 

    private int posX, posY; 
    private int frameAtual; 
    private int direcao; 

    private Timer timer;

    private String[][] tabuleiroObjetos; // Matriz para representar objetos no tabuleiro
    private int pedras;
    
    int ouro;
    int ouroC;
    int laranja;
    int laranjaC;
    int abacate ;
    int abacateC ;
    int coco;
    int cocoC;
    int acerola;
    int acerolaC;
    int amora;
    int amoraC;
    int goiaba;
    int goiabaC;
    
    private int arvores;
    private int frutas;
    
    private Random random = new Random(); // Instância de Random para ser utilizada globalmente

    private String[] personagens = { "dwarf1", "dwarf2", "dwarf3", "dwarf4", "dwarf5", "dwarf6" };
    private String personagemSelecionado; 

    private final int TILE_SIZE = 64; // Tamanho do tile (quadrado) do tabuleiro
    private final int dimINT;
    private final int NUM_TILES_X = 6; // Número de tiles na horizontal
    private final int NUM_TILES_Y = 6; // Número de tiles na vertical

    public TabuleiroPrevia(String dim, int qPedras,int qOuro,int qOuroC,int qLaranja,int qLaranjaC,int qAbacate,int qAbacateC,int
    		qCoco,int qCocoC,int qAcerola,int qAcerolaC,int qAmora,int qAmoraC, int qGoiaba, int qGoiabaC) {
    	
    	this.dimINT = Integer.valueOf(dim); 
        tabuleiroObjetos = new String[dimINT][dimINT]; // Inicializa o tabuleiro
        
        this.pedras = qPedras;
        this.ouro = qOuro;
        this.ouroC = qOuroC;
        this.laranja = qLaranja;
        this.laranjaC = qLaranjaC;
        this.abacate = qAbacate;
        this.abacateC = qAbacateC;
        this.coco = qCoco;
        this.cocoC = qCocoC;
        this.acerola = qAcerola;
        this.acerolaC = qAcerolaC;
        this.amora = qAmora;
        this.amoraC = qAmoraC;
        this.goiaba = qGoiaba;
        this.goiabaC = qGoiabaC;
        
        
        arvores = laranja + abacate + coco + acerola + amora + goiaba;
        frutas = ouroC + laranjaC + abacateC + cocoC + acerolaC + amoraC + goiabaC; 
        
        selecionarPersonagemAleatorio(); 
        carregarImagens();
        gerarObjetosNoTabuleiro();
        gerarPosicaoInicialPersonagem(); // Gera posição inicial aleatória do personagem

        frameAtual = 0;
        direcao = 0; 
        timer = new Timer(150, this); 
        timer.start();
        setPreferredSize(new Dimension(dimINT * TILE_SIZE, dimINT * TILE_SIZE));
        setPreferredSize(new Dimension(900, 900));

        setFocusable(true); 
    }
    // Função para selecionar aleatoriamente um personagem
    private void selecionarPersonagemAleatorio() {
        personagemSelecionado = personagens[random.nextInt(personagens.length)];
    }
    // Carregar os sprites individuais com base no personagem selecionado
    private void carregarImagens() {
    	tileImage = new ImageIcon(getClass().getResource("/imagens/grama.png")).getImage();

    }

    private void gerarObjetosNoTabuleiro() {
        int objetosRestantes = pedras + arvores + frutas;

        while (objetosRestantes > 0) {
            int x = random.nextInt(dimINT); 
            int y = random.nextInt(dimINT); 
            
            // EU SEI QUE ISSO É FEIO, MAS EU ESTAVA CANSADO E QUERIA TESTAR SE AO MENOS IA FUNCIONAR :/ 
            
            if (tabuleiroObjetos[x][y] == null) { 
                if (pedras > 0) {
                    tabuleiroObjetos[x][y] = "pedra";
                    pedras--;
                    objetosRestantes--;
                } else if (laranja > 0) {
                	tabuleiroObjetos[x][y] = "arvore5";
                	laranja--;
                	objetosRestantes--;
                } else if (abacate > 0) {
                	tabuleiroObjetos[x][y] = "arvore2";
                	abacate--;
                	objetosRestantes--;
                } else if (coco > 0) {
                	tabuleiroObjetos[x][y] = "arvore3";
                	coco--;
                	objetosRestantes--;
                } else if (acerola > 0) {
                	tabuleiroObjetos[x][y] = "arvore7";
                	acerola--;
                	objetosRestantes--;
                } else if (amora > 0) {
                	tabuleiroObjetos[x][y] = "arvore4";
                	amora--;
                	objetosRestantes--;
                } else if (goiaba > 0) {
                	tabuleiroObjetos[x][y] = "arvore6";
                	goiaba--;
                	objetosRestantes--;
                } else if (ouroC > 0) {
                	tabuleiroObjetos[x][y] = "fruta1";
                	ouroC--;
                	objetosRestantes--;
                } else if (laranjaC > 0) {
                	tabuleiroObjetos[x][y] = "fruta3";
                	laranjaC--;
                	objetosRestantes--;
                } else if (abacateC > 0) {
                	tabuleiroObjetos[x][y] = "fruta4";
                	abacateC--;
                	objetosRestantes--;
                } else if (cocoC > 0) {
                	tabuleiroObjetos[x][y] = "fruta7";
                	cocoC--;
                	objetosRestantes--;
                } else if (acerolaC > 0) {
                	tabuleiroObjetos[x][y] = "fruta5";
                	acerolaC--;
                	objetosRestantes--;
                } else if (amoraC > 0) {
                	tabuleiroObjetos[x][y] = "fruta6";
                	amoraC--;
                	objetosRestantes--;
                } else if (goiabaC > 0) {
                	tabuleiroObjetos[x][y] = "fruta2";
                	goiabaC--;
                	objetosRestantes--;
                }
            }
        }
    }
    // Faz o personagem nascer aleatóriamente no mapa 
    private void gerarPosicaoInicialPersonagem() {
        boolean posicaoValida = false;
        while (!posicaoValida) {
            int x = random.nextInt(dimINT);
            int y = random.nextInt(dimINT);
            
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

        for (int i = 0; i < dimINT; i++) {
            for (int j = 0; j < dimINT; j++) {
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


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        frameAtual = (frameAtual + 1) % 3; 
        repaint(); 
    }

	}

