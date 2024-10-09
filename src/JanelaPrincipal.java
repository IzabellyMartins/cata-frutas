import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import modelos.Tabuleiro;
import modelos.TabuleiroPrevia;

import java.time.*;

import java.util.*;

import javax.swing.JFileChooser;

public class JanelaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Font fixedsysFont;
    
    int qPedras;
    int qOuro;
    int qOuroC;
    int qLaranja;
    int qLaranjaC;
    int qAbacate;
    int qAbacateC;
    int qCoco;
    int qCocoC;
    int qAcerola;
    int qAcerolaC;
    int qAmora;
    int qAmoraC;
    int qGoiaba;
    int qGoiabaC;
    int qBichadas;
    int qMochila;
    String dim;
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                JanelaPrincipal frame = new JanelaPrincipal();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    int acao = 1; //Verificação para impedir que a prévia seja ativa antes de salvar, um flag de interrupção é sempre ativo
    int acao2 = 1; //Verificação para impedir que a prévia seja ativa antes de salvar um arquivo carregado, um flag de interrupção é sempre ativo
    int acao3 = 1;
    
    public JanelaPrincipal() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        setTitle("Cata-Frutas");


        try {
            fixedsysFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Fixedsys.ttf")).deriveFont(24f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fixedsysFont); 
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            fixedsysFont = new Font("Arial", Font.PLAIN, 24);
        }

        contentPane = new JPanel(new BorderLayout()) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image backgroundImage = new ImageIcon(getClass().getResource("/imagens/fundo.png")).getImage();
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JPanel helpPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        helpPanel.setOpaque(false);

        JButton helpButton = new JButton();
        helpButton.setPreferredSize(new Dimension(40, 40));
        helpButton.setBorder(BorderFactory.createRaisedBevelBorder());
        helpButton.setBackground(Color.WHITE);
        helpButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        ImageIcon helpIconImage = new ImageIcon(getClass().getResource("/imagens/interrogacao.png"));
        Image scaledImage = helpIconImage.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        helpButton.setIcon(new ImageIcon(scaledImage));

        helpButton.addActionListener(e -> mostrarRegras());

        helpPanel.add(helpButton);
        contentPane.add(helpPanel, BorderLayout.NORTH);

        JPanel buttonPanel = criarPainelInicial();
        contentPane.add(buttonPanel, BorderLayout.CENTER);
    }

    private void mostrarRegras() {
        // Criar o diálogo para exibir as regras
        JDialog regrasDialog = new JDialog(this, "Como funciona o jogo?", true);
        regrasDialog.setSize(700, 400); 
        regrasDialog.setLocationRelativeTo(this); 

        JTextArea regrasArea = new JTextArea();
        regrasArea.setEditable(false); 

  
        try (InputStream is = getClass().getResourceAsStream("/Regras.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String line;
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            regrasArea.setText(content.toString());

        } catch (IOException e) {
            regrasArea.setText("Não foi possível carregar as regras.");
            e.printStackTrace();
        }


        JScrollPane scrollPane = new JScrollPane(regrasArea);
        regrasDialog.add(scrollPane);


        regrasDialog.setVisible(true);
    }

    private JPanel criarPainelInicial() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Sejam bem-vindos ao Cata-Frutas!");
        titleLabel.setFont(fixedsysFont.deriveFont(Font.BOLD, 40f));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        GridBagConstraints gbcTitle = new GridBagConstraints();
        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0;
        gbcTitle.insets = new Insets(10, 0, 10, 0);
        buttonPanel.add(titleLabel, gbcTitle);

        JButton button1 = new JButton("Carregar nova partida");
        button1.setFont(new Font("Arial", Font.BOLD, 16));
        button1.setForeground(Color.BLACK);

        JButton button2 = new JButton("Personalizar partida");
        button2.setFont(new Font("Arial", Font.BOLD, 16));
        button2.setForeground(Color.BLACK);

        button1.setPreferredSize(new Dimension(300, 40));
        button2.setPreferredSize(new Dimension(300, 40));

        GridBagConstraints gbcButton1 = new GridBagConstraints();
        gbcButton1.gridx = 0;
        gbcButton1.gridy = 1;
        gbcButton1.insets = new Insets(10, 0, 0, 0);
        buttonPanel.add(button1, gbcButton1);

        GridBagConstraints gbcButton2 = new GridBagConstraints();
        gbcButton2.gridx = 0;
        gbcButton2.gridy = 2;
        gbcButton2.insets = new Insets(10, 0, 0, 0);
        buttonPanel.add(button2, gbcButton2);


        button1.addActionListener(e -> carregarTabuleiro());
        button2.addActionListener(e -> mostrarFormularioPersonalizacao());

        return buttonPanel;
    }

    private void carregarTabuleiro() {
        dispose(); // Fecha a janela atual

        // Cria uma nova janela com o tabuleiro
        JFrame tabuleiroFrame = new JFrame("Tabuleiro");
        tabuleiroFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Cria e adiciona o painel do tabuleiro
        Tabuleiro tabuleiro = new Tabuleiro();
        tabuleiroFrame.add(tabuleiro);

     
        tabuleiroFrame.pack();
        tabuleiroFrame.setLocationRelativeTo(null);
        tabuleiroFrame.setResizable(true); 
        tabuleiroFrame.setVisible(true); 
    }

    private void mostrarFormularioPersonalizacao() {
        JDialog formDialog = new JDialog(JanelaPrincipal.this, "Personalizar Partida", false); //False par que seja não-modal, assim é possível fechar a 
        																					   //janela de pévia sem ter qure fechar a de formulário
        																					   //antes dela. Caso true, modal, uma depende da outra.
        formDialog.setSize(400, 800); 
        formDialog.setLocationRelativeTo(JanelaPrincipal.this);
        formDialog.setLayout(new GridBagLayout());

        // Campos para personalizar a dimensão e número de pedras
        JLabel labelDimensao = new JLabel("Lado do tabuleiro quadrado (NxN): (Digite N)");
        JTextField textDimensao = new JTextField(10);

        JLabel labelPedras = new JLabel("Número de Pedras:");
        JTextField textPedras = new JTextField(10);

        // Campos para o número de frutas
        JLabel labelFrutas = new JLabel("- Seleção de frutas abaixo -", JLabel.RIGHT);
        
        // Maracujás
        JLabel labelOuro = new JLabel("Maracujás no jogo:");
        JTextField textOuro = new JTextField(10);
        JLabel labelOuroChao = new JLabel("Maracujás no chão:");
        JTextField textOuroChao = new JTextField(10);
        
        // Laranjas
        JLabel labelLaranja = new JLabel("Laranja:");
        JTextField textLaranja = new JTextField(10);
        JLabel labelLaranjaChao = new JLabel("Laranja no chão:");
        JTextField textLaranjaChao = new JTextField(10);
        
        // Abacates
        JLabel labelAbacate = new JLabel("Abacate:");
        JTextField textAbacate = new JTextField(10);
        JLabel labelAbacateChao = new JLabel("Abacate no chão:");
        JTextField textAbacateChao = new JTextField(10);
        
        // Coco
        JLabel labelCoco = new JLabel("Coco:");
        JTextField textCoco = new JTextField(10);
        JLabel labelCocoChao = new JLabel("Coco no chão:");
        JTextField textCocoChao = new JTextField(10);
        
        // Acerola
        JLabel labelAcerola = new JLabel("Acerola:");
        JTextField textAcerola = new JTextField(10);
        JLabel labelAcerolaChao = new JLabel("Acerola no chão:");
        JTextField textAcerolaChao = new JTextField(10);
        
        // Amora
        JLabel labelAmora = new JLabel("Amora:");
        JTextField textAmora= new JTextField(10);
        JLabel labelAmoraChao = new JLabel("Amora no chão:");
        JTextField textAmoraChao = new JTextField(10);
        
        // Goiaba
        JLabel labelGoiaba = new JLabel("Goiaba:");
        JTextField textGoiaba = new JTextField(10);
        JLabel labelGoiabaChao = new JLabel("Goiaba no chão:");
        JTextField textGoiabaChao = new JTextField(10);
        
        JLabel labelBichadas = new JLabel("Frutas Bichadas:");
        JTextField textBichadas = new JTextField(10);
        JLabel labelMochila = new JLabel("Capacidade da mochila:");
        JTextField textMochila = new JTextField(10);
        JLabel labelNome = new JLabel("Nome do arquivo:");
        JTextField textNome = new JTextField(10);
        JButton buttonPrevia = new JButton("Prévia do mapa");
        JButton buttonSalvar = new JButton("Salvar Configurações/Validar Configurações");
        JButton buttonCarregarConfig = new JButton("Carregar configurações para prévia");
        	
        	// NOTA DE IMPLEMENTAÇÃO
        	// ADICIONAR UM AVISO: MUDANÇAS FEITAS APÓS CARREGAR AS CONFIGURAÇÕES DEVEM SER SALVAS PARA ENTRAREM EM EFEITO.
        	// UMA ALTERAÇÃO NAS CONFIGURAÇÕES NÃO APARECERÁ NA PRÉVIA A MENOS QUE O MAPA SEJA SALVO.
        
        	// NOTA 2: NA VERDADE, DEIXA SÓ UM AVISO DE QUE O MAPA DEVE SER CARREGADO PARA TER SUAS MUDANÇAS VISUALIZADAS NA PRÉVIA.
        	
        buttonSalvar.addActionListener(e -> {
            acao = 0; // Permite que a prévia seja vista
            acao2 = 0; // Permite que a prévia seja vista
            
            // Variáveis para armazenar os valores
            String dimensao = textDimensao.getText();
            String pedrasStr = textPedras.getText();
            String ouroStr = textOuro.getText();
            String ouroChaoStr = textOuroChao.getText();
            String laranjaStr = textLaranja.getText();
            String laranjaChaoStr = textLaranjaChao.getText();
            String abacateStr = textAbacate.getText();
            String abacateChaoStr = textAbacateChao.getText();
            String cocoStr = textCoco.getText();
            String cocoChaoStr = textCocoChao.getText();
            String acerolaStr = textAcerola.getText();
            String acerolaChaoStr = textAcerolaChao.getText();
            String amoraStr = textAmora.getText();
            String amoraChaoStr = textAmoraChao.getText();
            String goiabaStr = textGoiaba.getText();
            String goiabaChaoStr = textGoiabaChao.getText();
            String bichadasStr = textBichadas.getText();
            String mochilaStr = textMochila.getText();
            
            // Verifica se algum campo está vazio
            if (dimensao.isEmpty() || pedrasStr.isEmpty() || ouroStr.isEmpty() || ouroChaoStr.isEmpty() ||
                laranjaStr.isEmpty() || laranjaChaoStr.isEmpty() || abacateStr.isEmpty() || abacateChaoStr.isEmpty() ||
                cocoStr.isEmpty() || cocoChaoStr.isEmpty() || acerolaStr.isEmpty() || acerolaChaoStr.isEmpty() ||
                amoraStr.isEmpty() || amoraChaoStr.isEmpty() || goiabaStr.isEmpty() || goiabaChaoStr.isEmpty() ||
                bichadasStr.isEmpty() || mochilaStr.isEmpty()) {
                
                // Exibe uma caixa de diálogo com uma mensagem de erro
                JOptionPane.showMessageDialog(null, "Todos os campos devem ser preenchidos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return; // Interrompe a execução se algum campo estiver vazio
            }

            // Tenta converter as variáveis digitadas para verificar se é possível ler as configurações
            try {
                int pedras = Integer.parseInt(pedrasStr);
                int ouro = Integer.parseInt(ouroStr);
                int ouroChao = Integer.parseInt(ouroChaoStr);
                int laranja = Integer.parseInt(laranjaStr);
                int laranjaChao = Integer.parseInt(laranjaChaoStr);
                int abacate = Integer.parseInt(abacateStr);
                int abacateChao = Integer.parseInt(abacateChaoStr);
                int coco = Integer.parseInt(cocoStr);
                int cocoChao = Integer.parseInt(cocoChaoStr);
                int acerola = Integer.parseInt(acerolaStr);
                int acerolaChao = Integer.parseInt(acerolaChaoStr);
                int amora = Integer.parseInt(amoraStr);
                int amoraChao = Integer.parseInt(amoraChaoStr);
                int goiaba = Integer.parseInt(goiabaStr);
                int goiabaChao = Integer.parseInt(goiabaChaoStr);
                int bichadas = Integer.parseInt(bichadasStr);
                int mochila = Integer.parseInt(mochilaStr);
                int dim = Integer.parseInt(dimensao);
                
                // Valida valores
                if (dim < 3) {
                    JOptionPane.showMessageDialog(null, "A menor dimensão admitida é 3 x 3 (N = 3)", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                } 
                if (pedras < 0) {
                    JOptionPane.showMessageDialog(null, "Digite zero ou um valor positivo para pedras", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }         
                if (ouro < 0 || ouroChao < 0) {
                    JOptionPane.showMessageDialog(null, "Digite zero ou um valor positivo para as frutas ouro", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (laranja < 0 || laranjaChao < 0) {
                	JOptionPane.showMessageDialog(null, "Digite zero ou um valor positivo para as laranjas", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (abacate < 0 || abacateChao < 0) {
                	JOptionPane.showMessageDialog(null, "Digite zero ou um valor positivo para os abacates", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (coco < 0 || cocoChao < 0) {
                	JOptionPane.showMessageDialog(null, "Digite zero ou um valor positivo para os cocos", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (acerola < 0 || acerolaChao < 0) {
                	JOptionPane.showMessageDialog(null, "Digite zero ou um valor positivo para as acerolas", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (amora < 0 || amoraChao < 0) {
                	JOptionPane.showMessageDialog(null, "Digite zero ou um valor positivo para as amoras", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (goiaba < 0 || goiabaChao < 0) {
                	JOptionPane.showMessageDialog(null, "Digite zero ou um valor positivo para as goiabas", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (bichadas < 0) {
                	JOptionPane.showMessageDialog(null, "Digite zero ou um valor positivo para as frutas bichadas", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (mochila < 0) {
                	JOptionPane.showMessageDialog(null, "Digite zero ou um valor positivo para a mochila", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                
                // Verifica se a quantidade total é maior que o tamanho do tabuleiro
                if ((pedras + ouro + laranja + abacate + coco + acerola + amora + goiaba + ouroChao + laranjaChao + abacateChao + cocoChao + acerolaChao + amoraChao + goiabaChao) > (dim * dim)) {
                    JOptionPane.showMessageDialog(null, "Quantidade de elementos no chão é maior que o tamanho do tabuleiro", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Verifica existência de frutas ouro.
                if (ouro < 1) {
                	JOptionPane.showMessageDialog(null, "Deve haver no minimo uma fruta ouro.", "Erro", JOptionPane.ERROR_MESSAGE);
                	return;
                }
                
                // Verifica se bichadas uma porcentagem válida
                if (bichadas > 100) {
                	// Exibe uma caixa de diálogo com uma mensagem de erro
                	JOptionPane.showMessageDialog(null, "Quantidade frutas bichadas não pode ser maior que 100%", "Erro", JOptionPane.ERROR_MESSAGE);
                	return;
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, insira apenas números válidos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return; // Interrompe a execução em caso de erro de conversão
            }

            // Se todas as validações passarem, construa a string para salvar
            WriterDemo writer = new WriterDemo();
            StringBuilder sb = new StringBuilder();
            
            sb.append("dimensao ").append(dimensao).append("\n");
            sb.append("pedras ").append(pedrasStr).append("\n");
            sb.append("maracuja ").append(ouroStr).append(" ").append(ouroChaoStr).append("\n");
            sb.append("laranja ").append(laranjaStr).append(" ").append(laranjaChaoStr).append("\n");
            sb.append("abacate ").append(abacateStr).append(" ").append(abacateChaoStr).append("\n");
            sb.append("coco ").append(cocoStr).append(" ").append(cocoChaoStr).append("\n");
            sb.append("acerola ").append(acerolaStr).append(" ").append(acerolaChaoStr).append("\n");
            sb.append("amora ").append(amoraStr).append(" ").append(amoraChaoStr).append("\n");
            sb.append("goiaba ").append(goiabaStr).append(" ").append(goiabaChaoStr).append("\n");
            sb.append("bichadas ").append(bichadasStr).append("\n");
            sb.append("mochila ").append(mochilaStr).append("\n");

            // Escrever no arquivo
            writer.writeToFile(textNome.getText() + ".txt", sb.toString());
        });

        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);


        gbc.gridx = 0;
        gbc.gridy = 0;
        formDialog.add(labelDimensao, gbc);

        gbc.gridx = 1;
        formDialog.add(textDimensao, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formDialog.add(labelPedras, gbc);

        gbc.gridx = 1;
        formDialog.add(textPedras, gbc);


        gbc.gridx = 0;
        gbc.gridy = 2;
        formDialog.add(labelFrutas, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formDialog.add(labelOuro, gbc);
        gbc.gridx = 1;
        formDialog.add(textOuro, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        formDialog.add(labelOuroChao, gbc);
        gbc.gridx = 1;
        formDialog.add(textOuroChao, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formDialog.add(labelLaranja, gbc);
        gbc.gridx = 1;
        formDialog.add(textLaranja, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        formDialog.add(labelLaranjaChao, gbc);
        gbc.gridx = 1;
        formDialog.add(textLaranjaChao, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        formDialog.add(labelAbacate, gbc);
        gbc.gridx = 1;
        formDialog.add(textAbacate, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        formDialog.add(labelAbacateChao, gbc);
        gbc.gridx = 1;
        formDialog.add(textAbacateChao, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        formDialog.add(labelCoco, gbc);
        gbc.gridx = 1;
        formDialog.add(textCoco, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 10;
        formDialog.add(labelCocoChao, gbc);
        gbc.gridx = 1;
        formDialog.add(textCocoChao, gbc);

        gbc.gridx = 0;
        gbc.gridy = 11;
        formDialog.add(labelAcerola, gbc);
        gbc.gridx = 1;
        formDialog.add(textAcerola, gbc);

        gbc.gridx = 0;
        gbc.gridy = 12;
        formDialog.add(labelAcerolaChao, gbc);
        gbc.gridx = 1;
        formDialog.add(textAcerolaChao, gbc);

        gbc.gridx = 0;
        gbc.gridy = 13;
        formDialog.add(labelAmora, gbc);
        gbc.gridx = 1;
        formDialog.add(textAmora, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 14;
        formDialog.add(labelAmoraChao, gbc);
        gbc.gridx = 1;
        formDialog.add(textAmoraChao, gbc);

        gbc.gridx = 0;
        gbc.gridy = 15;
        formDialog.add(labelGoiaba, gbc);
        gbc.gridx = 1;
        formDialog.add(textGoiaba, gbc);

        gbc.gridx = 0;
        gbc.gridy = 16;
        formDialog.add(labelGoiabaChao, gbc);
        gbc.gridx = 1;
        formDialog.add(textGoiabaChao, gbc);
       
        gbc.gridx = 0;
        gbc.gridy = 17;
        formDialog.add(labelBichadas, gbc);
        gbc.gridx = 1;
        formDialog.add(textBichadas, gbc);
       
        gbc.gridx = 0;
        gbc.gridy = 18;
        formDialog.add(labelMochila, gbc);
        gbc.gridx = 1;
        formDialog.add(textMochila, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 19;
        formDialog.add(labelNome, gbc);
        gbc.gridx = 1;
        formDialog.add(textNome, gbc);
  
        gbc.gridx = 0;
        gbc.gridy = 20;
        gbc.gridwidth = 2; 
        formDialog.add(buttonPrevia, gbc);
       
        gbc.gridy = 21;
        formDialog.add(buttonSalvar, gbc);

        gbc.gridy = 22;
        formDialog.add(buttonCarregarConfig, gbc);

        // Ação para iniciar a partida
        buttonPrevia.addActionListener(e -> {
        	
        	if (acao == 1) {
        		// Exibe uma caixa de diálogo com uma mensagem de erro
                JOptionPane.showMessageDialog(null, "Antes de ver a prévia, salve ou carregue as configurações", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
        	} else {
	    		acao = 0; // Zera o flag de interrupção
	    	
	        	if (acao2 == 1) {
	        		// Exibe uma caixa de diálogo com uma mensagem de erro
	                JOptionPane.showMessageDialog(null, "Antes de ver a prévia, valide as configurações do arquivo carregado", "Erro", JOptionPane.ERROR_MESSAGE);
	                return;
	        	} else {
	        		if(acao3 == 1) {
	        			JOptionPane.showMessageDialog(null, "Resolva os erros ou valide antes de executar a prévia.", "Erro", JOptionPane.ERROR_MESSAGE);
	        		} else {
	        		carregarTabuleiroPrevia();
		    		acao3 = 1; // Zera o flag de interrupção
		    		return;
		    		}
	        	}
	        }
        });

        
        // Ação para carregar configurações
        buttonCarregarConfig.addActionListener(e -> {
            //Como cada arquivo com configurações possio um nome, é mais eficaz usar uma caixa de diálogo para selecionar os arquivos
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Selecionar arquivo de configurações");

            int userSelection = fileChooser.showOpenDialog(formDialog);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToOpen = fileChooser.getSelectedFile();
                carregarConfiguracoes(textDimensao, fileToOpen, textPedras, textOuro, textCoco, textAbacate, textLaranja, textAcerola,
                		textAmora, textGoiaba, textBichadas, textMochila, textOuroChao, textAbacateChao, textLaranjaChao, textAcerolaChao,
                		textAmoraChao, textGoiabaChao, textCocoChao, textNome);
            }
        });
        
        // Tornar o dialog visível após adicionar todos os componentes
        formDialog.setVisible(true);

    }

    public void carregarConfiguracoes(JTextField comboDimensao, File fileToOpen, JTextField textPedras, JTextField textOuro, JTextField textCoco, JTextField textAbacate,
    		JTextField textLaranja, JTextField textAcerola, JTextField textAmora, JTextField textGoiaba, JTextField textBichadas, JTextField textMochila,
    		JTextField textOuroChao, JTextField textAbacateChao, JTextField textLaranjaChao, JTextField textAcerolaChao,
    		JTextField textAmoraChao, JTextField textGoiabaChao, JTextField textCocoChao, JTextField textNome) {
        
    	try {
	    		if (fileToOpen.getName().endsWith(".txt")) {
	    			
		    		
		    		if(fileToOpen.canRead() == true ){
		    			    			
		    			Scanner reader = new Scanner(fileToOpen);
		    			
			            // Salva as variáveis digitadas para verificar se é possível ler as configurações
		    			try { 
		    				this.dim = reader.nextLine().replace("dimensao ", "");
		    			} catch (NumberFormatException e) {
			                JOptionPane.showMessageDialog(null, "O campo 'dimensao' deve conter um número inteiro positivo!", "Erro", JOptionPane.ERROR_MESSAGE);
			                return;
			            }
		    			if (this.dim.isEmpty()) {
		                    JOptionPane.showMessageDialog(null, "Campo 'dimensao' vazio ou mal formatado!", "Erro", JOptionPane.ERROR_MESSAGE);
		                    return;
		                }
		    			comboDimensao.setText(this.dim);
		    			
		    			String SqPedras = reader.nextLine().replace("pedras ", "");
		    			if (SqPedras.isEmpty()) {
		                    JOptionPane.showMessageDialog(null, "Campo 'pedras' vazio ou mal formatado!", "Erro", JOptionPane.ERROR_MESSAGE);
		                    return;
		                }
			            textPedras.setText(SqPedras);
			            try {
			            	this.qPedras = Integer.valueOf(SqPedras);
			            } catch (NumberFormatException e) {
			                JOptionPane.showMessageDialog(null, "O campo 'pedras' deve conter um número inteiro positivo!", "Erro", JOptionPane.ERROR_MESSAGE);
			                return;
			            }
		
			            String SqOuro = reader.nextLine().replace("maracuja ",""); 
			            if (SqOuro.isEmpty()) {
		                    JOptionPane.showMessageDialog(null, "Campo 'maracuja' vazio ou mal formatado!", "Erro", JOptionPane.ERROR_MESSAGE);
		                    return;
		                }
			            textOuro.setText(SqOuro.split(" ")[0]);
			            try {
			            	this.qOuro = Integer.valueOf(SqOuro.split(" ")[0]);
			            } catch (NumberFormatException e) {
			                JOptionPane.showMessageDialog(null, "O campo 'maracuja' deve conter um número inteiro positivo!", "Erro", JOptionPane.ERROR_MESSAGE);
			                return;
			            }
			            textOuroChao.setText(SqOuro.split(" ")[1]);
			            try {
			            	this.qOuroC = Integer.valueOf(SqOuro.split(" ")[1]);
			            } catch (NumberFormatException e) {
			                JOptionPane.showMessageDialog(null, "O campo 'maracuja' deve conter um número inteiro positivo!", "Erro", JOptionPane.ERROR_MESSAGE);
			                return;
			            }
			            
			            String SqLaranja = reader.nextLine().replace("laranja ",""); 
			            if (SqLaranja.isEmpty()) {
		                    JOptionPane.showMessageDialog(null, "Campo 'laranjas' vazio ou mal formatado!", "Erro", JOptionPane.ERROR_MESSAGE);
		                    return;
		                }
			            textLaranja.setText(SqLaranja.split(" ")[0]);
			            try {
			            	this.qLaranja = Integer.valueOf(SqLaranja.split(" ")[0]);
			            } catch (NumberFormatException e) {
			                JOptionPane.showMessageDialog(null, "O campo 'laranjas' deve conter um número inteiro positivo!", "Erro", JOptionPane.ERROR_MESSAGE);
			                return;
			            }
			            textLaranjaChao.setText(SqLaranja.split(" ")[1]);
			            try {
			            	this.qLaranjaC = Integer.valueOf(SqLaranja.split(" ")[1]);
			            } catch (NumberFormatException e) {
			                JOptionPane.showMessageDialog(null, "O campo 'laranjas' deve conter um número inteiro positivo!", "Erro", JOptionPane.ERROR_MESSAGE);
			                return;
			            }	
			            
			            String SqAbacate = reader.nextLine().replace("abacate ",""); 
			            if (SqAbacate.isEmpty()) {
		                    JOptionPane.showMessageDialog(null, "Campo 'abacate' vazio ou mal formatado!", "Erro", JOptionPane.ERROR_MESSAGE);
		                    return;
		                }
			            textAbacate.setText(SqAbacate.split(" ")[0]);
			            try {
			            	this.qAbacate = Integer.valueOf(SqAbacate.split(" ")[0]);
			            } catch (NumberFormatException e) {
			                JOptionPane.showMessageDialog(null, "O campo 'abacate' deve conter um número inteiro positivo!", "Erro", JOptionPane.ERROR_MESSAGE);
			                return;
			            }
			            textAbacateChao.setText(SqAbacate.split(" ")[1]);
			            try {
			            	this.qAbacateC = Integer.valueOf(SqAbacate.split(" ")[1]);
			            } catch (NumberFormatException e) {
			                JOptionPane.showMessageDialog(null, "O campo 'abacate' deve conter um número inteiro positivo!", "Erro", JOptionPane.ERROR_MESSAGE);
			                return;
			            }
			            
			            String SqCoco = reader.nextLine().replace("coco ",""); 
			            if (SqCoco.isEmpty()) {
		                    JOptionPane.showMessageDialog(null, "Campo 'coco' vazio ou mal formatado!", "Erro", JOptionPane.ERROR_MESSAGE);
		                    return;
		                }
			            textCoco.setText(SqCoco.split(" ")[0]);
			            try {
			            	this.qCoco = Integer.valueOf(SqCoco.split(" ")[0]);
			            } catch (NumberFormatException e) {
			                JOptionPane.showMessageDialog(null, "O campo 'coco' deve conter um número inteiro positivo!", "Erro", JOptionPane.ERROR_MESSAGE);
			                return;
			            }
			            textCocoChao.setText(SqCoco.split(" ")[1]);
			            try {
			            	this.qCocoC = Integer.valueOf(SqCoco.split(" ")[1]);
			            } catch (NumberFormatException e) {
			                JOptionPane.showMessageDialog(null, "O campo 'coco' deve conter um número inteiro positivo!", "Erro", JOptionPane.ERROR_MESSAGE);
			                return;
			            }
			            
			            String SqAcerola = reader.nextLine().replace("acerola ",""); 
			            if (SqAcerola.isEmpty()) {
		                    JOptionPane.showMessageDialog(null, "Campo 'acerola' vazio ou mal formatado!", "Erro", JOptionPane.ERROR_MESSAGE);
		                    return;
		                }
			            textAcerola.setText(SqAcerola.split(" ")[0]);
			            try {
			            	this.qAcerola = Integer.valueOf(SqAcerola.split(" ")[0]);
			            } catch (NumberFormatException e) {
			                JOptionPane.showMessageDialog(null, "O campo 'acerola' deve conter um número inteiro positivo!", "Erro", JOptionPane.ERROR_MESSAGE);
			                return;
			            }
			            textAcerolaChao.setText(SqAcerola.split(" ")[1]);
			            try {
			            	this.qAcerolaC = Integer.valueOf(SqAcerola.split(" ")[1]);
			            } catch (NumberFormatException e) {
			                JOptionPane.showMessageDialog(null, "O campo 'acerola' deve conter um número inteiro positivo!", "Erro", JOptionPane.ERROR_MESSAGE);
			                return;
			            }
			            
			            String SqAmora = reader.nextLine().replace("amora ",""); 
			            if (SqAmora.isEmpty()) {
		                    JOptionPane.showMessageDialog(null, "Campo 'amora' vazio ou mal formatado!", "Erro", JOptionPane.ERROR_MESSAGE);
		                    return;
		                }
			            textAmora.setText(SqAmora.split(" ")[0]);
			            try {
			            	this.qAmora = Integer.valueOf(SqAmora.split(" ")[0]);
			            } catch (NumberFormatException e) {
			                JOptionPane.showMessageDialog(null, "O campo 'amora' deve conter um número inteiro positivo!", "Erro", JOptionPane.ERROR_MESSAGE);
			                return;
			            }
			            textAmoraChao.setText(SqAmora.split(" ")[1]);
			            try {
			            	this.qAmoraC = Integer.valueOf(SqAmora.split(" ")[1]);
			            } catch (NumberFormatException e) {
			                JOptionPane.showMessageDialog(null, "O campo 'amora' deve conter um número inteiro positivo!", "Erro", JOptionPane.ERROR_MESSAGE);
			                return;
			            }
			            String SqGoiaba = reader.nextLine().replace("goiaba ",""); 
			            if (SqGoiaba.isEmpty()) {
		                    JOptionPane.showMessageDialog(null, "Campo 'goiaba' vazio ou mal formatado!", "Erro", JOptionPane.ERROR_MESSAGE);
		                    return;
		                }
			            textGoiaba.setText(SqGoiaba.split(" ")[0]);
			            try {
			            this.qGoiaba = Integer.valueOf(SqGoiaba.split(" ")[0]);
			            } catch (NumberFormatException e) {
			                JOptionPane.showMessageDialog(null, "O campo 'goiaba' deve conter um número inteiro positivo!", "Erro", JOptionPane.ERROR_MESSAGE);
			                return;
			            }
			            textGoiabaChao.setText(SqGoiaba.split(" ")[1]);
			            try {
			            	this.qGoiabaC = Integer.valueOf(SqGoiaba.split(" ")[1]);
			            } catch (NumberFormatException e) {
			                JOptionPane.showMessageDialog(null, "O campo 'goiaba' deve conter um número inteiro positivo!", "Erro", JOptionPane.ERROR_MESSAGE);
			                return;
			            }
			            
		    			String SqBichadas = reader.nextLine().replace("bichadas ", "");
					    if (SqBichadas.isEmpty()) {
		                   JOptionPane.showMessageDialog(null, "Campo 'bichadas' vazio ou mal formatado!", "Erro", JOptionPane.ERROR_MESSAGE);
		                   return;
		                }
			            textBichadas.setText(SqBichadas);
			            try {
			            	this.qBichadas = Integer.valueOf(SqBichadas);
			            } catch (NumberFormatException e) {
			                JOptionPane.showMessageDialog(null, "O campo 'bichadas' deve conter um número inteiro positivo entre 0 e 100!", "Erro", JOptionPane.ERROR_MESSAGE);
			                return;
			            }
			            
		    			String SqMochila = reader.nextLine().replace("mochila ", "");
		    			if (SqMochila.isEmpty()) {
		                    JOptionPane.showMessageDialog(null, "Campo 'mochila' vazio ou mal formatado!", "Erro", JOptionPane.ERROR_MESSAGE);
		                    return;
		                 }
		    			textMochila.setText(SqMochila);
		    			try {
		    				this.qMochila = Integer.valueOf(SqMochila);
		    			} catch (NumberFormatException e) {
			                JOptionPane.showMessageDialog(null, "O campo 'mochila' deve conter um número inteiro positivo!", "Erro", JOptionPane.ERROR_MESSAGE);
			                return;
			            }
			            textNome.setText(fileToOpen.getName().replace(".txt", ""));
			            
			            //Frutas totais disponíveis na partida
			            
			            int pedras = this.qPedras;
			            int ouro = this.qOuro;
			            int laranja = this.qLaranja;
			            int abacate = this.qAbacate;
			            int coco = this.qCoco;
			            int acerola = this.qAcerola;
			            int amora = this.qAmora; 
			            int goiaba = this.qGoiaba;
			            int bichadas = this.qBichadas;
			            
			            //Frutas que estrão já disponíveis no chão
			            
			            int ouroC = Integer.parseInt(textOuroChao.getText());
			            int laranjaC = Integer.parseInt(textLaranjaChao.getText());
			            int abacateC = Integer.parseInt(textAbacateChao.getText());
			            int cocoC = Integer.parseInt(textCocoChao.getText());
			            int acerolaC = Integer.parseInt(textAcerolaChao.getText());
			            int amoraC = Integer.parseInt(textAmoraChao.getText()); 
			            int goiabaC = Integer.parseInt(textGoiabaChao.getText());
			         
			            int dim = Integer.valueOf(this.dim);
			            
			            if ((pedras + ouro + laranja + abacate + coco + acerola + amora + goiaba + ouroC + laranjaC +
			            		abacateC + cocoC + acerolaC + amoraC + goiabaC) > (dim * dim)) {
			        		// Exibe uma caixa de diálogo com uma mensagem de erro
			                JOptionPane.showMessageDialog(null, "Quantidade de elementos no chão é maior que o tamanho do tabuleiro", "Erro", JOptionPane.ERROR_MESSAGE);
			            } else if (bichadas > 100) {
			            	// Exibe uma caixa de diálogo com uma mensagem de erro
			                JOptionPane.showMessageDialog(null, "Quantidade frutas bichadas não pode ser maior que 100%", "Erro", JOptionPane.ERROR_MESSAGE);
			            } else if (bichadas < 0 || pedras < 0 || laranja < 0 || abacate < 0 || coco < 0 || acerola < 0 || amora < 0 || goiaba < 0) {
			            	// Exibe uma caixa de diálogo com uma mensagem de erro
			                JOptionPane.showMessageDialog(null, "Nenhum dos valores pode ser menor que zero", "Erro", JOptionPane.ERROR_MESSAGE);
			            } else if (dim < 3) {
			            	JOptionPane.showMessageDialog(null, "O tabuleiro deve possuir no minimo dimensão de 3x3 (N = 3).", "Erro", JOptionPane.ERROR_MESSAGE);
			            } else if (ouro < 1) {
			            	JOptionPane.showMessageDialog(null, "Deve haver no minimo uma fruta ouro.", "Erro", JOptionPane.ERROR_MESSAGE);
			            }else if ((ouroC + laranjaC + abacateC + cocoC + acerolaC + amoraC + goiabaC) > (ouro + laranja + abacate + coco + acerola + amora + goiaba)){ 
			            	JOptionPane.showMessageDialog(null, "A quantidade total de frutas deve ser maior que a quantidade total de frutas no chão.", "Erro", JOptionPane.ERROR_MESSAGE);
			            }else {
			        		acao = 0; // Zera o flag de interrupção
			        		//carregarTabuleiroPrevia();
			        	}

		    		}else {
		    				BufferedReader reader = new BufferedReader(new FileReader("configuracoes.txt"));
		    	            textPedras.setText(reader.readLine());
		    	            textOuro.setText(reader.readLine());
		    	            textLaranja.setText(reader.readLine());
		    	            textAbacate.setText(reader.readLine());
		    	            textCoco.setText(reader.readLine());
		    	            textAcerola.setText(reader.readLine());
		    	            textAmora.setText(reader.readLine());
		    	            textGoiaba.setText(reader.readLine());
		    	            textBichadas.setText(reader.readLine());
		    	            textMochila.setText(reader.readLine()); 
		    	            textNome.setText(fileToOpen.getName());
		    	            
		    	}
	    		} else {
	    	        // Exibe uma mensagem de erro se a extensão não for a esperada
	    	        JOptionPane.showMessageDialog(null, "Arquivo inválido. Apenas arquivos .txt são permitidos.", "Erro", JOptionPane.ERROR_MESSAGE);
	    	    }
    		}catch (IOException e) {
                e.printStackTrace();
                }
    }

	private void carregarTabuleiroPrevia() {
		
			acao2  = 1; // O flag é ativo novamente para que o usuário seja alerta para slavar possíveis alterações 
    	
    	    // Cria uma nova janela para a prévia
    	    JFrame previaFrame = new JFrame("Prévia do Tabuleiro");
    	    previaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha apenas a janela da prévia
    	    TabuleiroPrevia tabuleiro = new TabuleiroPrevia(dim, qPedras, qOuro, qOuroC, qLaranja, qLaranjaC, qAbacate, qAbacateC,
    	    		qCoco, qCocoC,qAcerola,qAcerolaC,qAmora,qAmoraC,qGoiaba,qGoiabaC);
    	    previaFrame.add(tabuleiro);
    	    
    	    // Tamanho da janela da prévia
    	    int dimINT = Integer.valueOf(dim); 
    	    previaFrame.setSize(70*dimINT, 70*dimINT); // Ajuste o tamanho conforme necessário

    	    // Define a localização da janela da prévia ao lado da janela atual
    	    int x = this.getX() + this.getWidth(); // Posição X da janela atual + largura
    	    int y = this.getY(); // Mantém a mesma posição Y

    	    // Define a localização da janela da prévia
    	    previaFrame.setLocation(x, y);
    	    
    	    // Torna a janela da prévia visível
    	    previaFrame.setVisible(true);
    	}

  
	// Classe WriterDemo para salvar configurações em um arquivo de texto
    class WriterDemo {

        public void writeToFile(String nomeArquivo, String content) {
            try (Writer w = new FileWriter(nomeArquivo)) {
                w.write(content);
                //System.out.println("Configurações salvas com sucesso!");
                JOptionPane.showMessageDialog(null, "Configurações salvas/validadas com sucesso!");
                acao3 = 0;
            } catch (IOException e) {
                //System.out.println("Erro ao salvar as configurações: " + e.getMessage());
                JOptionPane.showMessageDialog(null, "Erro ao salvar as configurações: " + e.getMessage());
            }
        }
    }
}