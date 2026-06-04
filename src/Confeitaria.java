// Importa o pacote Swing para componentes visuais padrão (JFrame, JPanel, JLabel, JButton).
import javax.swing.*;

// Importa o pacote AWT para lidar com layouts, cores, fontes, cursores e estilização gráfica nativa.
import java.awt.*;

// Importa o adaptador de componentes para escutar eventos de redimensionamento (mudar tamanho da tela).
import java.awt.event.ComponentAdapter;

// Importa a classe que captura as propriedades do evento de redimensionamento (como a nova largura).
import java.awt.event.ComponentEvent;

// Importa o fluxo de entrada de dados para conseguir ler arquivos binários de dentro do projeto (como as fontes).
import java.io.InputStream;

/**
 * Classe principal da Janela (Dashboard da Confeitaria).
 * Herda de JFrame para se comportar como uma janela nativa do sistema operacional.
 */
public class Confeitaria extends JFrame { // Início da definição da classe Confeitaria.

    // Declara um espaço na memória para guardar a fonte Playfair Display no estilo Regular.
    private Font playfairRegular;

    // Declara um espaço na memória para guardar a fonte Playfair Display no estilo Bold (Negrito).
    private Font playfairBold;

    // Declara um espaço na memória para guardar a fonte Playfair Display no estilo Italic (Itálico).
    private Font playfairItalic;

    /**
     * Construtor da classe: Aqui é onde toda a interface gráfica é montada e exibida.
     */
    public Confeitaria() { // Início do construtor que é chamado ao dar 'new Confeitaria()'.

        // 1. CARREGAMENTO DAS FONTES CUSTOMIZADAS
        // Chama o método 'carregarFonte', busca o arquivo do estilo Regular e define tamanho base 14.
        playfairRegular = carregarFonte("Regular", 14f);

        // Chama o método auxiliar para carregar a fonte em negrito com tamanho inicial 16.
        playfairBold = carregarFonte("Bold", 16f);

        // Chama o método auxiliar para carregar a fonte em itálico com tamanho inicial 14.
        playfairItalic = carregarFonte("Italic", 14f);

        // CONFIGURAÇÕES BÁSICAS DA JANELA PRINCIPAL
        // Modifica o texto que aparece na barra de título cinza superior da janela.
        setTitle("Confeitaria Tia Rosa - Dashboard");

        // Define que ao abrir, a janela terá inicialmente 1020 pixels de largura e 680 pixels de altura.
        setSize(1020, 680);

        // Garante que o processo do Java seja totalmente encerrado no gerenciador de tarefas ao clicar no 'X' de fechar.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Passando 'null', o Java calcula o tamanho do monitor e centraliza a janela perfeitamente na tela.
        setLocationRelativeTo(null);

        // Aplica o BorderLayout na janela principal para dividi-la em 5 regiões clássicas: Norte, Sul, Leste, Oeste e Centro.
        setLayout(new BorderLayout());

        // PALETA DE CORES OFICIAL DO FIGMA (Definidas usando o padrão RGB)
        // Cria a cor rosa característica da identidade visual da confeitaria.
        Color rosaPrincipal = new Color(218, 118, 155);

        // Cria uma cor de fundo esbranquiçada com um leve toque sutil de rosa para as áreas limpas.
        Color bgGeral = new Color(255, 244, 246);

        // Cria um tom de cinza bem escuro (quase preto) para os textos comuns, evitando cansar as vistas do usuário.
        Color textoCorpo = new Color(60, 60, 60);

        // =================================================================
        // 1. PAINEL LATERAL (SIDEBAR)
        // =================================================================
        // Instancia um painel retangular comum que servirá como o menu lateral do sistema.
        JPanel sidebar = new JPanel();

        // Define a cor de fundo deste menu lateral como branco puro.
        sidebar.setBackground(Color.WHITE);

        // Fixa a largura do menu em 300px. O '0' na altura indica que ela deve esticar e ocupar 100% da lateral.
        sidebar.setPreferredSize(new Dimension(300, 0));

        // Define o layout interno do menu lateral como BorderLayout para organizar o topo (logo) e o centro (botões).
        sidebar.setLayout(new BorderLayout());

        // Aplica uma borda cinza bem clarinha de apenas 1 pixel de espessura somente no lado direito (efeito de linha divisória).
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(240, 240, 240)));

        // Subpainel do topo da sidebar (Onde fica a logo e o nome da loja)
        JPanel topSidebar = new JPanel();

        // Define o fundo do topo do menu como branco.
        topSidebar.setBackground(Color.WHITE);

        // Aplica o BoxLayout na vertical (Y_AXIS), empilhando os componentes adicionados um embaixo do outro.
        topSidebar.setLayout(new BoxLayout(topSidebar, BoxLayout.Y_AXIS));

        // Define uma margem interna: joga a logo 30px para baixo, dá 10px de folga nas laterais e 20px de espaço antes dos botões.
        topSidebar.setBorder(BorderFactory.createEmptyBorder(30, 10, 20, 10));

        // Tenta localizar o arquivo de imagem Logo.png dentro da estrutura de pastas de recursos do projeto.
        java.net.URL urlLogo = getClass().getResource("/images/Logo.png");

        // Valida se o arquivo da imagem realmente existe na pasta informada para evitar travar o programa.
        if (urlLogo != null) { // Caso a imagem exista...

            // Carrega a imagem original na memória do Java.
            ImageIcon iconeOriginal = new ImageIcon(urlLogo);

            // Redimensiona a imagem para 160x160 pixels aplicando o algoritmo SMOOTH, mantendo as bordas da logo lisinhas.
            Image imgRedimensionada = iconeOriginal.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);

            // Envolve a imagem tratada dentro de um JLabel (rótulo gráfico) para permitir sua inserção na tela.
            JLabel lblLogoImagem = new JLabel(new ImageIcon(imgRedimensionada));

            // Força o alinhamento horizontal da imagem para ficar perfeitamente centralizada na barra lateral.
            lblLogoImagem.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Adiciona fisicamente a imagem dentro do subpainel superior do menu.
            topSidebar.add(lblLogoImagem);

            // Cria e insere um espaçador fixo invisível de 15 pixels de altura apenas para afastar a imagem do texto abaixo.
            topSidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        } // Fim do bloco de validação da imagem.

        // Texto com o Nome da Confeitaria
        // Instancia o rótulo de texto passando o conteúdo escrito e ordenando que o texto fique centralizado internamente.
        JLabel lblLogoTexto = new JLabel("Confeitaria Tia Rosa", SwingConstants.CENTER);

        // Altera a tipografia do texto para a fonte customizada 'playfairBold', forçando estilo Negrito e tamanho 22.
        lblLogoTexto.setFont(playfairBold.deriveFont(Font.BOLD, 22f));

        // Aplica o rosa oficial como a cor desse texto.
        lblLogoTexto.setForeground(rosaPrincipal);

        // Centraliza a caixa de texto horizontalmente em relação ao espaço disponível no BoxLayout.
        lblLogoTexto.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adiciona o texto abaixo do espaçador no painel superior.
        topSidebar.add(lblLogoTexto);

        // Insere o subpainel completo (Imagem + Texto) na posição superior (NORTH) do menu lateral.
        sidebar.add(topSidebar, BorderLayout.NORTH);

        // Painel para conter os botões de navegação
        JPanel menuPanel = new JPanel();

        // Define o fundo do painel de botões como branco.
        menuPanel.setBackground(Color.WHITE);

        // Aplica o GridLayout(0, 1), indicando colunas infinitas mas estritamente 1 linha por componente. Isso deixa todos os botões idênticos.
        menuPanel.setLayout(new GridLayout(0, 1, 0, 0));

        // Cria uma coleção de textos (Array de Strings) contendo o nome exato de cada opção que o menu exibirá.
        String[] itensMenu = {
                "  Cadastrar Produto/Categoria",
                "  Controle de Validade",
                "  Relatório de Movimentação",
                "  Buscar Produto",
                "  Movimentação de Produtos",
        };

        // Loop 'for-each': Passa automaticamente por cada texto dentro do array 'itensMenu' para fabricar os botões.
        for (String item : itensMenu) { // Início do laço de repetição.

            // Instancia um novo botão na memória aplicando o texto do item atual do loop.
            JButton btn = new JButton(item);

            // Define o fundo do botão como branco puro.
            btn.setBackground(Color.WHITE);

            // Define o texto do botão com a cor rosa principal.
            btn.setForeground(rosaPrincipal);

            // Altera a tipografia do botão para a fonte customizada Regular em tamanho 14.
            btn.setFont(playfairRegular.deriveFont(14f));

            // Alinha o texto escrito e os espaços para começarem no canto esquerdo do botão.
            btn.setHorizontalAlignment(SwingConstants.LEFT);

            // Desenha uma borda rosa bem clarinha de 1 pixel de espessura somente na parte inferior do botão (efeito divisório).
            btn.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(245, 210, 220)));

            // Desativa a pintura da bordinha pontilhada de foco que o Java coloca nativamente ao clicar em botões.
            btn.setFocusPainted(false);

            // Altera o comportamento do ponteiro do mouse ao passar por cima do botão, virando a "mãozinha" de link/clique.
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Configura o botão para ter obrigatoriamente 300px de largura e confortáveis 50px de altura.
            btn.setPreferredSize(new Dimension(300, 50));

            // Adiciona o botão recém-estilizado na grade vertical de menus.
            menuPanel.add(btn);

            // Estrutura de decisão: Se o texto do botão atual contiver o termo "Cadastrar Produto"...
            if (item.contains("Cadastrar Produto")) { // Início do bloco do gatilho.

                // Vincula um ouvinte de cliques (ActionListener) no botão usando expressão Lambda.
                btn.addActionListener(e -> {

                    // Instancia a classe 'Cadastro' (da outra tela), passando 'this' (esta tela atual) como dona dela.
                    Cadastro tela = new Cadastro(this);

                    // Torna a janela flutuante de cadastro visível na tela para o usuário trabalhar.
                    tela.setVisible(true);
                }); // Fim do evento de clique.
            } // Fim da verificação do botão específico.
        } // Fim do loop automático de criação de botões.

        // Um painel envelopador (wrapper) usado para segurar o menu no topo, impedindo os botões de esticarem verticalmente
        JPanel wrapperMenu = new JPanel(new BorderLayout());

        // Define o fundo do envelopador como branco.
        wrapperMenu.setBackground(Color.WHITE);

        // Adiciona a grade de botões na porção norte (superior) do envelopador para que eles fiquem empacotados sem deformar.
        wrapperMenu.add(menuPanel, BorderLayout.NORTH);

        // Adiciona o envelopador no centro da barra lateral para preencher o resto do espaço vazio em branco.
        sidebar.add(wrapperMenu, BorderLayout.CENTER);

        // Fixa a barra lateral totalmente configurada no lado esquerdo (WEST) da nossa janela principal.
        add(sidebar, BorderLayout.WEST);

        // =================================================================
        // 2. PAINEL PRINCIPAL (CONTEÚDO DA DASHBOARD)
        // =================================================================
        // Instancia a classe customizada 'PainelComFundo' (criada no fim do arquivo) carregando a imagem de textura.
        PainelComFundo conteudo = new PainelComFundo("/images/Fundo.png");

        // Aplica BorderLayout no painel central para separar as informações de data no topo e os cards no meio.
        conteudo.setLayout(new BorderLayout());

        // Cabeçalho superior contendo as informações de Data
        JPanel headerPanel = new JPanel();

        // Define transparência total para o painel de cabeçalho, permitindo ver a imagem de fundo que está por trás.
        headerPanel.setOpaque(false);

        // Configura BoxLayout na vertical para empilhar a data por extenso e o dia da semana.
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        // Define margens de posicionamento: afasta 25px do topo, dá 40px de recuo nas laterais e 10px de folga abaixo.
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 40, 10, 40));

        // Label da Data por extenso
        JLabel lblData = new JLabel("06 de março de 2026");

        // Aplica a fonte Playfair Regular em tamanho 16.
        lblData.setFont(playfairRegular.deriveFont(16f));

        // Define a cor da letra usando o tom de cinza escuro corporativo.
        lblData.setForeground(textoCorpo);

        // Insere a data no painel de cabeçalho.
        headerPanel.add(lblData);

        // Label do Dia da Semana
        JLabel lblDiaSemana = new JLabel("Quarta-Feira");

        // Aplica a fonte Playfair Bold em estilo Negrito e tamanho 20.
        lblDiaSemana.setFont(playfairBold.deriveFont(Font.BOLD, 20f));

        // Define a cor da letra como cinza escuro.
        lblDiaSemana.setForeground(textoCorpo);

        // Adiciona o dia da semana logo abaixo da data por extenso.
        headerPanel.add(lblDiaSemana);

        // Insere o cabeçalho de textos na região superior (NORTH) do painel de conteúdo.
        conteudo.add(headerPanel, BorderLayout.NORTH);

        // GRADE DE CARDS RESPONSIVA
        // Cria um layout de grade fixa com exatamente 2 linhas e 2 colunas, aplicando 30px de espaçamento interno entre os blocos.
        JPanel gridCards = new JPanel(new GridLayout(2, 2, 30, 30));

        // Deixa a grade transparente para preservar e exibir a imagem de fundo do painel.
        gridCards.setOpaque(false);

        // Cria uma moldura invisível ao redor da grade para os cards não tocarem diretamente nas extremidades da tela.
        gridCards.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));

        // Adiciona os 4 cards na grade chamando a fábrica dinâmica.
        // Usamos tags <html> e <br> para que o texto quebre linhas de forma responsiva lá dentro
        gridCards.add(criarCardClean("Produto perto do Vencimento", "O Produto \"Farinha de Trigo\"<br>está perto do vencimento", "Vencimento: 21/05/2026<br>Vence daqui: 15 dias<br><br>Hoje é: 06/05/2026", rosaPrincipal, textoCorpo));
        gridCards.add(criarCardClean("Produto Acabando", "O Produto \"Fermento\"<br>está perto de Acabar", "Em estoque: 0,5 g<br>Estimativa de acabar: 3 Days", rosaPrincipal, textoCorpo));
        gridCards.add(criarCardClean("Produto Acabando", "O Produto \"Óleo\"<br>está perto de Acabar", "Em estoque: 3 Litros<br>Estimativa de acabar: 5 dias", rosaPrincipal, textoCorpo));
        gridCards.add(criarCardClean("Produto perto do Vencimento", "O Produto \"Leite\"<br>está perto do vencimento", "Vencimento: 10/05/2026<br>Vence daqui: 4 dias<br><br>Hoje é: 06/05/2026", rosaPrincipal, textoCorpo));

        // Adiciona a grade preenchida com os 4 cards na posição central (CENTER), forçando o painel a expandir ao máximo.
        conteudo.add(gridCards, BorderLayout.CENTER);

        // Adiciona o painel de conteúdo completo na posição central da janela de exibição do Windows/Mac.
        add(conteudo, BorderLayout.CENTER);

        // Ativa o sinalizador gráfico tornando a tela visível e operacional para o usuário final.
        setVisible(true);
    } // Fim do método construtor.

    // =================================================================
    // FÁBRICA DE CARDS RESPONSIVOS (TEXTO + CAIXA DINÂMICOS)
    // =================================================================
    /**
     * Método fábrica que constrói um painel customizado no estilo de card moderno (UI/UX clean).
     * Cria bordas redondas, fundos translúcidos e gerencia o tamanho dinâmico dos textos internos.
     */
    private JPanel criarCardClean(String titulo, String msg, String detalhes, Color corRosa, Color corTexto) { // Início da fábrica.

        // Instancia um painel redefinindo o comportamento de pintura em tempo real (Classe Anônima).
        JPanel card = new JPanel() { // Início do bloco interno da classe oculta.
            @Override
            protected void paintComponent(Graphics g) { // Método encarregado de desenhar o componente na tela.

                // Converte a biblioteca gráfica padrão 'Graphics' para a versão avançada em 2D.
                Graphics2D g2 = (Graphics2D) g.create();

                // Ativa a suavização de serrilhados (Antialiasing), garantindo que as curvas do card fiquem lisas e profissionais.
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // 1. DESENHA O FUNDO DO CARD: Branco com opacidade de 180 (vai de 0 a 255). Cria o efeito semi-transparente.
                g2.setColor(new Color(255, 255, 255, 180));

                // Pinta o fundo com cantos arredondados baseando-se no tamanho atual do card, aplicando raio de curvatura de 35px.
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 35, 35);

                // 2. DESENHA A BORDA DO CARD: Na cor rosa e com espessura de 3 pixels
                g2.setColor(corRosa);

                // Configura a caneta de desenho para ter uma linha firme e grossa de 3 pixels.
                g2.setStroke(new BasicStroke(3));

                // Desenha o contorno perfeito do card, aplicando recuo de segurança (-3) para a linha não ser cortada na tela.
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 35, 35);

                // Libera imediatamente os recursos gráficos da placa de vídeo usados na renderização desse card.
                g2.dispose();

                // Solicita ao Java que finalize as operações estruturais padrões da rotina do componente.
                super.paintComponent(g);
            } // Fim da reescrita do método paintComponent.
        }; // Fim da classe anônima do painel customizado.

        // Desliga a opacidade sólida nativa do painel para evitar que o fundo quadrado cinza do Java cubra nosso desenho redondo.
        card.setOpaque(false);

        // Configura o layout interno do card em BorderLayout, adotando um distanciamento vertical de 10px entre as linhas.
        card.setLayout(new BorderLayout(0, 10));

        // Aplica um recuo interno (Padding) de 20px em todos os lados para os textos não tocarem na linha rosa da borda.
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // CRIAÇÃO DAS LABELS (Rótulos de texto)
        // Instancia o texto do título configurando a centralização do parágrafo no topo.
        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);

        // Modifica a cor do título do card para o rosa principal da confeitaria.
        lblTitulo.setForeground(corRosa);

        // Insere o rótulo do título na posição superior (NORTH) do card.
        card.add(lblTitulo, BorderLayout.NORTH);

        // Mensagem Central (Fica posicionada no centro expandindo - CENTER)
        // Injeta códigos HTML para forçar a centralização completa do bloco de mensagem do miolo.
        JLabel lblMsg = new JLabel("<html><center>" + msg + "</center></html>", SwingConstants.CENTER);

        // Aplica a cor preta absoluta na fonte da mensagem do meio.
        lblMsg.setForeground(Color.BLACK);

        // Fixa a mensagem no coração (CENTER) do card.
        card.add(lblMsg, BorderLayout.CENTER);

        // Detalhes Textuais (Fica posicionado na base inferior - SOUTH)
        // Utiliza tags HTML para processar as quebras de linha (`<br>`) enviadas por parâmetro na mensagem inferior.
        JLabel lblDetalhes = new JLabel("<html>" + detalhes + "</html>");

        // Aplica a cor de texto cinza escuro padrão da interface nos detalhes.
        lblDetalhes.setForeground(corTexto);

        // Anexa o bloco descritivo na base inferior (SOUTH) do card.
        card.add(lblDetalhes, BorderLayout.SOUTH);

        // 🔥 O SISTEMA DE RESPONSIVIDADE DE LETRAS (Ouvinte de redimensionamento)
        // Adiciona um escutador que vigia o card. Se o usuário esticar a janela, esse bloco roda na hora.
        card.addComponentListener(new ComponentAdapter() { // Início do ouvinte de tamanho.
            @Override
            public void componentResized(ComponentEvent e) { // Método acionado no exato instante da mudança de escala.

                // Calcula a proporção matemática dividindo a largura atual do card pelo tamanho de referência de design (310px).
                float fatorEscala = card.getWidth() / 310f;

                // Travas de segurança (Clamping): Impede o texto de sumir (fator menor que 0.9) ou virar um outdoor exagerado (maior que 1.8)
                if (fatorEscala < 0.9f) fatorEscala = 0.9f;
                if (fatorEscala > 1.8f) fatorEscala = 1.8f;

                // Modifica dinamicamente o tamanho da fonte do Título multiplicando 15px originais pela escala calculada.
                lblTitulo.setFont(playfairBold.deriveFont(Font.BOLD, 15f * fatorEscala));

                // Modifica o tamanho da fonte da Mensagem multiplicando o tamanho base 13px pela escala.
                lblMsg.setFont(playfairRegular.deriveFont(13f * fatorEscala));

                // Modifica o tamanho da fonte dos Detalhes multiplicando a escala pelo tamanho base de 12px em itálico.
                lblDetalhes.setFont(playfairItalic.deriveFont(12f * fatorEscala));
            } // Fim da lógica de redimensionamento.
        }); // Fim do ouvinte.

        // Devolve o objeto do card totalmente customizado, estilizado e responsivo para quem solicitou.
        return card;
    } // Fim do método fábrica criarCardClean.

    // =================================================================
    // MOTOR ISOLADO PARA CARREGAR OS ARQUIVOS .TTF
    // =================================================================
    /**
     * Lê os arquivos de fontes na pasta /fonts/, monta a tipografia em memória e trata falhas.
     */
    private Font carregarFonte(String estilo, float tamanho) { // Início do carregador.
        try { // Abre bloco de captura de erros caso o arquivo da fonte esteja corrompido ou sumido.

            // Concatena textos para deduzir o endereço físico do arquivo. Ex: "/fonts/PlayfairDisplay-Regular.ttf".
            String caminho = "/fonts/PlayfairDisplay-" + estilo + ".ttf";

            // Lê o arquivo binário diretamente de dentro do arquivo empacotado compilado (.JAR).
            InputStream stream = getClass().getResourceAsStream(caminho);

            // Se o arquivo não existir ou o nome for digitado errado...
            if (stream == null) {

                // Emite uma mensagem de erro silenciosa no console interno do desenvolvedor.
                System.out.println("Aviso: Não encontrei a fonte " + caminho + ". Usando Arial.");

                // Cria e devolve a fonte padrão do Windows (Arial) para salvar o software de travar.
                return new Font("Arial", Font.PLAIN, (int) tamanho);
            } // Fim da checagem de segurança.

            // Converte os dados brutos lidos do arquivo em um objeto de Fonte TrueType nativa e define seu tamanho.
            return Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(tamanho);

        } catch (Exception e) { // Entra aqui se o arquivo falhar criticamente na leitura.

            // Imprime o relatório técnico do erro no terminal do desenvolvedor para auditoria.
            e.printStackTrace();

            // Retorna a fonte Arial de segurança em caso de colapso de carregamento.
            return new Font("Arial", Font.PLAIN, (int) tamanho);
        } // Fim do bloco de tratamento.
    } // Fim do método de carregamento de fontes.

    /**
     * Ponto de inicialização principal do sistema Java.
     */
    public static void main(String[] args) { // Método Main do projeto.

        // Aloca a inicialização da janela gráfica na fila de processamento da Event Dispatch Thread (EDT) do Swing.
        // Isso previne falhas de renderização e bugs visuais entre sistemas operacionais distintos.
        SwingUtilities.invokeLater(() -> new Confeitaria());
    } // Fim do Main.
} // Fim da Classe Confeitaria.


// =================================================================
// PAINEL DE BACKGROUND DINÂMICO COM VÉU DE OPACIDADE INTEGRADO
// =================================================================
/**
 * Classe utilitária especializada em desenhar uma imagem de fundo esticando-se de forma
 * inteligente conforme a resolução do monitor do usuário.
 */
class PainelComFundo extends JPanel { // Definição da classe PainelComFundo.

    // Cria uma variável para hospedar a imagem pura decodificada na memória RAM.
    private Image imagemDeFundo;

    /**
     * Construtor: Recebe o endereço do recurso da imagem de fundo
     */
    public PainelComFundo(String caminhoImagem) {

        // Localiza a URL física da imagem de textura dentro do projeto.
        java.net.URL url = getClass().getResource(caminhoImagem);

        // Se a imagem for encontrada com sucesso...
        if (url != null) {

            // Converte os pixels da URL em um objeto de imagem utilizável e guarda na variável global.
            this.imagemDeFundo = new ImageIcon(url).getImage();
        } // Fim da validação.
    } // Fim do construtor.

    /**
     * Sobrescreve o ciclo de pintura nativo do painel para injetar a imagem por baixo dos componentes
     */
    @Override
    protected void paintComponent(Graphics g) { // Método de pintura do painel.

        // Chama a rotina nativa para limpar fantasmas gráficos da memória antes de iniciar o desenho.
        super.paintComponent(g);

        // Se a imagem de fundo tiver sido carregada com sucesso...
        if (imagemDeFundo != null) {

            // 1. DESENHA A IMAGEM: Modifica e estica a imagem do plano de fundo para cobrir da coordenada (0,0) até os limites atuais de largura e altura da janela.
            g.drawImage(imagemDeFundo, 0, 0, getWidth(), getHeight(), this);

            // 2. CRIA O VÉU DE SUAVIZAÇÃO: Desenha um retângulo por cima de toda a imagem com cor branca translúcida (Opacidade de 200)
            // Isso esmaece a imagem de fundo para que as letras escuras dos cards continuem legíveis e elegantes
            g.setColor(new Color(255, 255, 255, 200));

            // Pinta o preenchimento desse quadrado cobrindo 100% da área, gerando o efeito de película/filtro esmaecido.
            g.fillRect(0, 0, getWidth(), getHeight());
        } // Fim do bloco de desenho da imagem.
    } // Fim da reescrita do paintComponent.
} // Fim da classe PainelComFundo.