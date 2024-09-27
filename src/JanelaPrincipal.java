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
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import modelos.Tabuleiro; 

public class JanelaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Font fixedsysFont;

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
        JDialog formDialog = new JDialog(JanelaPrincipal.this, "Personalizar Partida", true);
        formDialog.setSize(400, 500); 
        formDialog.setLocationRelativeTo(JanelaPrincipal.this);
        formDialog.setLayout(new GridBagLayout());

        // Campos para personalizar a dimensão e número de pedras
        JLabel labelDimensao = new JLabel("Dimensão do Tabuleiro:");
        JComboBox<String> comboDimensao = new JComboBox<>(new String[]{"6x6", "8x8", "10x10"});

        JLabel labelPedras = new JLabel("Número de Pedras:");
        JTextField textPedras = new JTextField(10);

        // Campos para o número de frutas
        JLabel labelFrutas = new JLabel("Número de frutas abaixo:");
        JLabel labelOuro = new JLabel("Maracujás (fruta Ouro a fruta da vitória!):");
        JTextField textOuro = new JTextField(10);
        JLabel labelCoco = new JLabel("Coco:");
        JTextField textCoco = new JTextField(10);
        JLabel labelAbacate = new JLabel("Abacate:");
        JTextField textAbacate = new JTextField(10);
        JLabel labelLaranja = new JLabel("Laranja:");
        JTextField textLaranja = new JTextField(10);
        JLabel labelAcerola = new JLabel("Acerola:");
        JTextField textAcerola = new JTextField(10);
        JLabel labelAmora = new JLabel("Amora:");
        JTextField textAmora= new JTextField(10);
        JLabel labelGoiaba = new JLabel("Goiaba:");
        JTextField textGoiaba = new JTextField(10);

        JButton buttonIniciar = new JButton("Iniciar Partida");
        JButton buttonCarregarConfig = new JButton("Carregar configurações");


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);


        gbc.gridx = 0;
        gbc.gridy = 0;
        formDialog.add(labelDimensao, gbc);

        gbc.gridx = 1;
        formDialog.add(comboDimensao, gbc);

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
        formDialog.add(labelCoco, gbc);
        gbc.gridx = 1;
        formDialog.add(textCoco, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formDialog.add(labelAbacate, gbc);
        gbc.gridx = 1;
        formDialog.add(textAbacate, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        formDialog.add(labelLaranja, gbc);
        gbc.gridx = 1;
        formDialog.add(textLaranja, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        formDialog.add(labelAcerola, gbc);
        gbc.gridx = 1;
        formDialog.add(textAcerola, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        formDialog.add(labelAmora, gbc);
        gbc.gridx = 1;
        formDialog.add(textAmora, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        formDialog.add(labelGoiaba, gbc);
        gbc.gridx = 1;
        formDialog.add(textGoiaba, gbc);
  
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2; 
        formDialog.add(buttonIniciar, gbc);

        gbc.gridy = 11;
        formDialog.add(buttonCarregarConfig, gbc);

        formDialog.setVisible(true);

        // Ação para iniciar a partida
        buttonIniciar.addActionListener(e -> {
            // Lógica para iniciar a partida com as configurações definidas
            String dimensao = (String) comboDimensao.getSelectedItem();
            String numPedras = textPedras.getText();
            String numOuro = textOuro.getText();
            String numCoco = textCoco.getText();
            String numAbacate = textAbacate.getText();
            String numLaranja = textLaranja.getText();
            String numAcerola = textAcerola.getText();
            String numAmora = textAmora.getText();
            String numGoiaba = textGoiaba.getText();

            // Aqui vamos adicionar a  lógica para tratar esses valores conforme necessário

            
            formDialog.dispose();
            carregarTabuleiro(); 
        });

        // Ação para carregar configurações
        buttonCarregarConfig.addActionListener(e -> {
            // Lógica para carregar as configurações
            carregarConfiguracoes(textPedras, textOuro, textCoco, textAbacate, textLaranja, textAcerola);
        });
    }

    private void carregarConfiguracoes(JTextField textPedras, JTextField textOuro, JTextField textCoco, JTextField textAbacate, JTextField textLaranja, JTextField textAcerola) {
        // Implementar a lógica para carregar as configurações de um arquivo
        try (BufferedReader reader = new BufferedReader(new FileReader("configuracoes.txt"))) {
            textPedras.setText(reader.readLine());
            textOuro.setText(reader.readLine());
            textCoco.setText(reader.readLine());
            textAbacate.setText(reader.readLine());
            textLaranja.setText(reader.readLine());
            textAcerola.setText(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
            
        }
    }
}







