import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.InputStream;

/**
 * Classe principal da Janela (Dashboard da Confeitaria).
 * Herda de JFrame para se comportar como uma janela nativa do sistema operacional.
 */
public class Confeitaria extends JFrame {

    // Variáveis globais para armazenar as fontes personalizadas carregadas dos arquivos .ttf
    private Font playfairRegular;
    private Font playfairBold;
    private Font playfairItalic;

    /**
     * Construtor da classe: Aqui é onde toda a interface gráfica é montada e exibida.
     */
    public Confeitaria() {
        // 1. CARREGAMENTO DAS FONTES CUSTOMIZADAS
        // Chama o método auxiliar passando o nome do estilo e o tamanho inicial desejado
        playfairRegular = carregarFonte("Regular", 14f);
        playfairBold = carregarFonte("Bold", 16f);
        playfairItalic = carregarFonte("Italic", 14f);

        // CONFIGURAÇÕES BÁSICAS DA JANELA PRINCIPAL
        setTitle("Confeitaria Tia Rosa - Dashboard"); // Define o título na barra superior
        setSize(1020, 680); // Define a largura (1020px) e altura (680px) iniciais da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Encerra o processo do Java ao fechar a janela
        setLocationRelativeTo(null); // Faz a janela abrir perfeitamente centralizada na tela do usuário

        // Define o layout padrão da janela como BorderLayout (divide em Norte, Sul, Leste, Oeste e Centro)
        setLayout(new BorderLayout());

        // PALETA DE CORES OFICIAL DO FIGMA (Definidas usando o padrão RGB)
        Color rosaPrincipal = new Color(218, 118, 155); // Rosa assinatura da marca
        Color bgGeral = new Color(255, 244, 246);       // Fundo claro rosado
        Color textoCorpo = new Color(60, 60, 60);       // Cinza escuro para melhor leitura de textos

        // =================================================================
        // 1. PAINEL LATERAL (SIDEBAR)
        // =================================================================
        JPanel sidebar = new JPanel();
        sidebar.setBackground(Color.WHITE); // Fundo totalmente branco limpo
        sidebar.setPreferredSize(new Dimension(300, 0)); // Largura fixa de 300px, altura estica (0 indica responsivo)
        sidebar.setLayout(new BorderLayout()); // Usa BorderLayout para organizar topo e itens internos
        // Cria uma linha sutil cinza do lado direito para separar o menu do resto da tela
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(240, 240, 240)));

        // Subpainel do topo da sidebar (Onde fica a logo e o nome da loja)
        JPanel topSidebar = new JPanel();
        topSidebar.setBackground(Color.WHITE);
        // BoxLayout.Y_AXIS empilha os componentes verticalmente (um embaixo do outro)
        topSidebar.setLayout(new BoxLayout(topSidebar, BoxLayout.Y_AXIS));
        // Adiciona um espaçamento interno (Padding): 30px acima, 10px nas laterais, 20px abaixo
        topSidebar.setBorder(BorderFactory.createEmptyBorder(30, 10, 20, 10));

        // Tenta localizar o arquivo de imagem Logo.png dentro da pasta de recursos do projeto
        java.net.URL urlLogo = getClass().getResource("/images/Logo.png");
        if (urlLogo != null) {
            ImageIcon iconeOriginal = new ImageIcon(urlLogo);
            // Redimensiona a imagem para 160x160 pixels de forma suave (Smooth) para não pixelar
            Image imgRedimensionada = iconeOriginal.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
            JLabel lblLogoImagem = new JLabel(new ImageIcon(imgRedimensionada));
            lblLogoImagem.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza horizontalmente na Sidebar
            topSidebar.add(lblLogoImagem); // Adiciona a imagem ao topo da Sidebar

            // Adiciona um espaço fixo invisível de 15 pixels entre a imagem e o texto abaixo
            topSidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        // Texto com o Nome da Confeitaria
        JLabel lblLogoTexto = new JLabel("Confeitaria Tia Rosa", SwingConstants.CENTER);
        lblLogoTexto.setFont(playfairBold.deriveFont(Font.BOLD, 22f)); // Aplica a fonte Playfair BOLD em 22px
        lblLogoTexto.setForeground(rosaPrincipal); // Aplica a cor rosa oficial
        lblLogoTexto.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza horizontalmente
        topSidebar.add(lblLogoTexto); // Adiciona o texto ao painel do topo

        // Insere o bloco montado (Logo + Nome) na parte norte (superior) da sidebar
        sidebar.add(topSidebar, BorderLayout.NORTH);

        // Painel para conter os botões de navegação
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(Color.WHITE);
        // GridLayout(0, 1) cria uma coluna única onde todas as linhas têm o mesmo tamanho exato
        menuPanel.setLayout(new GridLayout(0, 1, 0, 0));

        // Array com os textos de cada botão do menu lateral
        String[] itensMenu = {
                "  Cadastrar Produto/Categoria",
                "  Controle de Validade",
                "  Relatório de Movimentação",
                "  Buscar Produto",
                "  Movimentação de Produtos",

        };

        // Loop automático para criar, estilizar e adicionar cada botão do menu
        for (String item : itensMenu) {
            JButton btn = new JButton(item);
            btn.setBackground(Color.WHITE);
            btn.setForeground(rosaPrincipal);
            btn.setFont(playfairRegular.deriveFont(14f)); // Fonte Playfair Regular
            btn.setHorizontalAlignment(SwingConstants.LEFT); // Alinha o texto do botão à esquerda
            // Cria uma linha divisória rosa bem fininha na parte inferior de cada botão
            btn.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(245, 210, 220)));
            btn.setFocusPainted(false); // Remove aquela borda pontilhada feia de clique do Java antigo
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Transforma o mouse em "mãozinha" de clique
            btn.setPreferredSize(new Dimension(300, 50)); // Garante altura confortável de 50px por botão
            menuPanel.add(btn); // Adiciona o botão na grade do menu

            if (item.contains("Cadastrar Produto")) {
                btn.addActionListener(e -> {
                    // Cria a janela de cadastro
                    Cadastro tela = new Cadastro(this);
                    // Mostra a janela
                    tela.setVisible(true);
                });
        }

        }


        // Um painel envelopador (wrapper) usado para segurar o menu no topo, impedindo os botões de esticarem verticalmente
        JPanel wrapperMenu = new JPanel(new BorderLayout());
        wrapperMenu.setBackground(Color.WHITE);
        wrapperMenu.add(menuPanel, BorderLayout.NORTH);
        sidebar.add(wrapperMenu, BorderLayout.CENTER);

        // Posiciona a Sidebar finalizada à esquerda (Oeste) do JFrame principal
        add(sidebar, BorderLayout.WEST);

        // =================================================================
        // 2. PAINEL PRINCIPAL (CONTEÚDO DA DASHBOARD)
        // =================================================================
        // Cria o painel usando nossa classe customizada que desenha a imagem de fundo automaticamente
        PainelComFundo conteudo = new PainelComFundo("/images/Fundo.png");
        conteudo.setLayout(new BorderLayout()); // Layout para separar o cabeçalho de data e a grade de cards

        // Cabeçalho superior contendo as informações de Data
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false); // Torna transparente para que a imagem de fundo do painel apareça por trás
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS)); // Empilha os textos verticalmente
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 10, 40)); // Margens externas de posicionamento

        // Label da Data por extenso
        JLabel lblData = new JLabel("06 de março de 2026");
        lblData.setFont(playfairRegular.deriveFont(16f)); // Playfair Regular tamanho 16px
        lblData.setForeground(textoCorpo);
        headerPanel.add(lblData);

        // Label do Dia da Semana
        JLabel lblDiaSemana = new JLabel("Quarta-Feira");
        lblDiaSemana.setFont(playfairBold.deriveFont(Font.BOLD, 20f)); // Playfair Bold tamanho 20px
        lblDiaSemana.setForeground(textoCorpo);
        headerPanel.add(lblDiaSemana);

        // Insere o cabeçalho de data no topo (Norte) do painel de conteúdo
        conteudo.add(headerPanel, BorderLayout.NORTH);

        // GRADE DE CARDS RESPONSIVA
        // Cria uma matriz de 2 linhas por 2 colunas com espaçamento de 30px entre os cards
        JPanel gridCards = new JPanel(new GridLayout(2, 2, 30, 30));
        gridCards.setOpaque(false); // Transparente para não cobrir o fundo
        // Espaçamento externo para que os cards não encostem nas bordas da janela (Margens)
        gridCards.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));

        // Adiciona os 4 cards na grade chamando a fábrica dinâmica.
        // Usamos tags <html> e <br> para que o texto quebre linhas de forma responsiva lá dentro
        gridCards.add(criarCardClean("Produto perto do Vencimento", "O Produto \"Farinha de Trigo\"<br>está perto do vencimento", "Vencimento: 21/05/2026<br>Vence daqui: 15 dias<br><br>Hoje é: 06/05/2026", rosaPrincipal, textoCorpo));
        gridCards.add(criarCardClean("Produto Acabando", "O Produto \"Fermento\"<br>está perto de Acabar", "Em estoque: 0,5 g<br>Estimativa de acabar: 3 Dias", rosaPrincipal, textoCorpo));
        gridCards.add(criarCardClean("Produto Acabando", "O Produto \"Óleo\"<br>está perto de Acabar", "Em estoque: 3 Litros<br>Estimativa de acabar: 5 dias", rosaPrincipal, textoCorpo));
        gridCards.add(criarCardClean("Produto perto do Vencimento", "O Produto \"Leite\"<br>está perto do vencimento", "Vencimento: 10/05/2026<br>Vence daqui: 4 dias<br><br>Hoje é: 06/05/2026", rosaPrincipal, textoCorpo));

        // Adiciona a grade de cards no Centro do painel. Como está no Centro, ela vai expandir para ocupar todo o resto da tela!
        conteudo.add(gridCards, BorderLayout.CENTER);

        // Adiciona o painel de conteúdo principal no centro do JFrame
        add(conteudo, BorderLayout.CENTER);

        // Torna a janela visível na tela após a renderização de todas as estruturas
        setVisible(true);
    }

    // =================================================================
    // FÁBRICA DE CARDS RESPONSIVOS (TEXTO + CAIXA DINÂMICOS)
    // =================================================================
    /**
     * Método fábrica que constrói um painel customizado no estilo de card moderno (UI/UX clean).
     * Cria bordas redondas, fundos translúcidos e gerencia o tamanho dinâmico dos textos internos.
     */
    private JPanel criarCardClean(String titulo, String msg, String detalhes, Color corRosa, Color corTexto) {

        // Instanciamos um JPanel sobrescrevendo a pintura nativa dele (Classe Anônima)
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                // Habilita o Antialiasing (Suavização de pixel), deixando os cantos redondos lisos e profissionais
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // 1. DESENHA O FUNDO DO CARD: Branco com opacidade de 180 (vai de 0 a 255). Cria o efeito semi-transparente.
                g2.setColor(new Color(255, 255, 255, 180));
                // fillRoundRect preenche uma caixa com cantos arredondados (raio de 35px nos cantos)
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 35, 35);

                // 2. DESENHA A BORDA DO CARD: Na cor rosa e com espessura de 3 pixels
                g2.setColor(corRosa);
                g2.setStroke(new BasicStroke(3)); // Define espessura da linha
                // drawRoundRect desenha o contorno com 35px de arredondamento
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 35, 35);

                g2.dispose(); // Libera a memória do objeto gráfico utilizado
                super.paintComponent(g); // Executa o comportamento base padrão
            }
        };

        card.setOpaque(false); // Desativa o fundo retangular opaco padrão para que nossa arte redonda apareça perfeitamente
        card.setLayout(new BorderLayout(0, 10)); // Divide o card internamente em Norte, Centro e Sul com espaço de 10px entre eles
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding interno de 20px para afastar os textos da borda do card

        // CRIAÇÃO DAS LABELS (Rótulos de texto)
        // Título do Card (Fica posicionado no topo - NORTH)
        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setForeground(corRosa);
        card.add(lblTitulo, BorderLayout.NORTH);

        // Mensagem Central (Fica posicionada no centro expandindo - CENTER)
        JLabel lblMsg = new JLabel("<html><center>" + msg + "</center></html>", SwingConstants.CENTER);
        lblMsg.setForeground(Color.BLACK);
        card.add(lblMsg, BorderLayout.CENTER);

        // Detalhes Textuais (Fica posicionado na base inferior - SOUTH)
        JLabel lblDetalhes = new JLabel("<html>" + detalhes + "</html>");
        lblDetalhes.setForeground(corTexto);
        card.add(lblDetalhes, BorderLayout.SOUTH);

        // 🔥 O SISTEMA DE RESPONSIVIDADE DE LETRAS (Ouvinte de redimensionamento)
        // Monitora o card. Toda vez que a janela mudar de tamanho, este bloco é executado automaticamente
        card.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Base matemática: a largura de referência antiga do card era 310 pixels
                // Se a largura atual do card subir para 620 pixels, o fator de escala será 2.0 (dobro do tamanho)
                float fatorEscala = card.getWidth() / 310f;

                // Travas de segurança (Clamping): Impede o texto de sumir (fator menor que 0.9) ou virar um outdoor exagerado (maior que 1.8)
                if (fatorEscala < 0.9f) fatorEscala = 0.9f;
                if (fatorEscala > 1.8f) fatorEscala = 1.8f;

                // Aplica a nova escala em tempo real multiplicando o tamanho base da fonte pelo fator calculado
                lblTitulo.setFont(playfairBold.deriveFont(Font.BOLD, 15f * fatorEscala)); // Atualiza o título (Bold)
                lblMsg.setFont(playfairRegular.deriveFont(13f * fatorEscala));          // Atualiza o miolo (Regular)
                lblDetalhes.setFont(playfairItalic.deriveFont(12f * fatorEscala));      // Atualiza os detalhes (Italic)
            }
        });

        return card; // Retorna o painel do card pronto para ser adicionado na tela
    }

    // =================================================================
    // MOTOR ISOLADO PARA CARREGAR OS ARQUIVOS .TTF
    // =================================================================
    /**
     * Lê os arquivos de fontes na pasta /fonts/, monta a tipografia em memória e trata falhas.
     */
    private Font carregarFonte(String estilo, float tamanho) {
        try {
            // Monta dinamicamente a String baseado no estilo pedido. Ex: /fonts/PlayfairDisplay-Regular.ttf
            String caminho = "/fonts/PlayfairDisplay-" + estilo + ".ttf";
            // Pega o arquivo de dentro do arquivo .JAR compilado em formato de Stream de leitura
            InputStream stream = getClass().getResourceAsStream(caminho);

            if (stream == null) {
                // Caso você digite o nome errado, ele avisa no console e usa Arial para o app não quebrar
                System.out.println("Aviso: Não encontrei a fonte " + caminho + ". Usando Arial.");
                return new Font("Arial", Font.PLAIN, (int) tamanho);
            }
            // Transforma o arquivo binário em uma Fonte TrueType utilizável pelo Java Swing e define o tamanho
            return Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(tamanho);
        } catch (Exception e) {
            // Caso ocorra qualquer erro de leitura, imprime o erro e adota a fonte padrão do sistema
            e.printStackTrace();
            return new Font("Arial", Font.PLAIN, (int) tamanho);
        }
    }

    /**
     * Ponto de inicialização principal do sistema Java.
     */
    public static void main(String[] args) {
        // Aloca a execução da interface gráfica dentro da Thread correta de eventos do Swing (Thread de Despacho de Eventos)
        // Isso evita bugs visuais aleatórios em computadores diferentes
        SwingUtilities.invokeLater(() -> new Confeitaria());
    }
}

// =================================================================
// PAINEL DE BACKGROUND DINÂMICO COM VÉU DE OPACIDADE INTEGRADO
// =================================================================
/**
 * Classe utilitária especializada em desenhar uma imagem de fundo esticando-se de forma
 * inteligente conforme a resolução do monitor do usuário.
 */
class PainelComFundo extends JPanel {
    private Image imagemDeFundo; // Objeto que guarda a imagem pura em memória

    /**
     * Construtor: Recebe o endereço do recurso da imagem de fundo
     */
    public PainelComFundo(String caminhoImagem) {
        java.net.URL url = getClass().getResource(caminhoImagem);
        if (url != null) {
            // Converte a URL encontrada em um objeto do tipo Image utilizável
            this.imagemDeFundo = new ImageIcon(url).getImage();
        }
    }

    /**
     * Sobrescreve o ciclo de pintura nativo do painel para injetar a imagem por baixo dos componentes
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Executa a rotina limpa de limpeza gráfica padrão do componente
        if (imagemDeFundo != null) {
            // 1. DESENHA A IMAGEM: Adapta os pixels da imagem para ocupar da coordenada (0,0) até toda a largura e altura atuais da janela
            g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);

            // 2. CRIA O VÉU DE SUAVIZAÇÃO: Desenha um retângulo por cima de toda a imagem com cor branca translúcida (Opacidade de 200)
            // Isso esmaece a imagem de fundo para que as letras escuras dos cards continuem legíveis e elegantes
            g.setColor(new Color(255, 255, 255, 200));
            g.fillRect(0, 0, getWidth(), getHeight()); // Desenha a película cobrindo todo o painel
        }
    }
}