import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import modelos.Tabuleiro;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class JanelaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel dynamicContentPane; // Painel dinâmico que será substituído
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
        setBounds(100, 100, 800, 600); // Aumentei o tamanho da janela
        setTitle("Cata-Frutas");

        // Carregar a fonte Fixedsys
        try {
            fixedsysFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Fixedsys.ttf")).deriveFont(24f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fixedsysFont); // Registra a fonte
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            fixedsysFont = new Font("Arial", Font.PLAIN, 24); // Fallback para Arial
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

        // Painel para o conteúdo que será trocado
        dynamicContentPane = new JPanel();
        dynamicContentPane.setOpaque(false); // Permite o fundo visível
        contentPane.add(dynamicContentPane, BorderLayout.CENTER);

        // Painel para o botão de ajuda
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

        helpButton.addActionListener(e -> {
            JDialog infoDialog = new JDialog(JanelaPrincipal.this, "Como funciona o jogo?", true);
            infoDialog.setSize(300, 200);
            infoDialog.setLocationRelativeTo(JanelaPrincipal.this);

            JPanel dialogContentPane = new JPanel() {
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

            dialogContentPane.setLayout(new BorderLayout());

            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);

            try (InputStream inputStream = getClass().getResourceAsStream("/Regras.txt");
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                StringBuilder regrasTexto = new StringBuilder();
                String linha;
                while ((linha = reader.readLine()) != null) {
                    regrasTexto.append(linha).append("\n");
                }
                textArea.setText(regrasTexto.toString());
            } catch (IOException ex) {
                textArea.setText("Erro ao carregar as regras do jogo.");
                ex.printStackTrace();
            }

            JButton closeButton = new JButton("Fechar");
            closeButton.addActionListener(actionEvent -> infoDialog.dispose());

            dialogContentPane.add(new JScrollPane(textArea), BorderLayout.CENTER);
            dialogContentPane.add(closeButton, BorderLayout.SOUTH);

            infoDialog.setContentPane(dialogContentPane);
            infoDialog.setVisible(true);
        });

        helpPanel.add(helpButton);
        contentPane.add(helpPanel, BorderLayout.NORTH);

        // Criar painel inicial com os botões
        JPanel buttonPanel = criarPainelInicial();
        dynamicContentPane.add(buttonPanel);
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

        // Ações para abrir o tabuleiro
        button1.addActionListener(e -> carregarTabuleiro());
        button2.addActionListener(e -> carregarTabuleiro());

        return buttonPanel;
    }

    private void carregarTabuleiro() {
        // Remove o conteúdo atual e carrega o tabuleiro
        contentPane.remove(dynamicContentPane);
        dynamicContentPane = new Tabuleiro(); // Alterado para usar a classe Tabuleiro
        dynamicContentPane.setOpaque(false); // Para manter o fundo visível
        contentPane.add(dynamicContentPane, BorderLayout.CENTER);
        contentPane.revalidate();
        contentPane.repaint();
    }
}


