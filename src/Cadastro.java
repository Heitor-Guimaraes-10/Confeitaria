// Importa o pacote Swing para usar componentes de interface gráfica (como JDialog, JPanel, JTextField, JButton).
import javax.swing.*;

// Importa a classe para criar máscaras de formatação em campos de texto (como a data DD/MM/AAAA).
import javax.swing.text.MaskFormatter;

// Importa o pacote AWT para gerenciar layouts (BorderLayout, GridBagLayout) e estilos/cores (Color, Insets).
import java.awt.*;

// Importa a classe URI para manipular, formatar e validar o endereço (URL) do servidor/back-end.
import java.net.URI;

// Importa o cliente HTTP do Java usado para configurar e disparar as requisições de rede.
import java.net.http.HttpClient;

// Importa a classe que configura os dados da requisição (método POST, URL, cabeçalhos, corpo JSON).
import java.net.http.HttpRequest;

// Importa a classe que manipula a resposta que o servidor devolve (código de status, corpo do texto).
import java.net.http.HttpResponse;

// Importa a exceção gerada caso o padrão da máscara de formatação de texto (data) falhe ou seja inválido.
import java.text.ParseException;

// Declaração da classe 'Cadastro', que herda (extends) de JDialog para se comportar como uma janela secundária/popup.
public class Cadastro extends JDialog {

    // Declara uma variável estática (compartilhada na memória) que inicia em 1001 para servir como gerador de IDs automáticos.
    private static int contadorId = 1001;

    // Construtor da classe Cadastro, que recebe a janela principal ('Frame parent') como dona desta janela.
    public Cadastro(Frame parent) {

        // Chama o construtor da classe pai (JDialog), definindo quem a abriu, o título e se ela é modal (bloqueia o fundo).
        super(parent, "Cadastrar Produto", true);

        // Define a largura (400px) e a altura (450px) da janela de cadastro.
        setSize(400, 450);

        // Posiciona a janela de cadastro exatamente no centro da janela principal (parent).
        setLocationRelativeTo(parent);

        // Define o layout da janela principal como BorderLayout (divide a tela em Norte, Sul, Leste, Oeste e Centro).
        setLayout(new BorderLayout());

        // Cria um painel (container) para o formulário usando GridBagLayout (layout em forma de grade flexível).
        JPanel formPanel = new JPanel(new GridBagLayout());

        // Aplica uma borda invisível de 20 pixels nas quatro laterais do painel para gerar um espaçamento interno (respiro).
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Instancia o objeto que dita as regras de posicionamento, alinhamento e tamanho de cada componente no GridBagLayout.
        GridBagConstraints gbc = new GridBagConstraints();

        // Define uma margem externa de 5 pixels em todos os lados (superior, esquerdo, inferior, direito) de cada componente.
        gbc.insets = new Insets(5, 5, 5, 5);

        // Configura para que os componentes estiquem horizontalmente para preencher todo o espaço da sua célula na grade.
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ==========================================================
        // CAMPO ID
        // ==========================================================

        // Adiciona o campo ID chamando o método auxiliar 'adicionarCampo' criado lá embaixo
        adicionarCampo(
                formPanel, // Passa o painel onde o campo será inserido.
                "ID do Produto:", // Passa o texto do rótulo (Label).
                new JTextField(String.valueOf(contadorId)), // Instancia um campo de texto preenchido com o ID atual convertido para String.
                gbc, // Passa o objeto com as configurações de layout.
                0, // Parâmetro X (coluna).
                0, // Parâmetro Y (linha).
                false // Define 'false' para desativar o campo (o usuário não pode alterar o ID).
        );

        // ==========================================================
        // CAMPO NOME
        // ==========================================================

        // Instancia um campo de texto limpo e editável para que o usuário digite o nome do produto.
        JTextField txtNome = new JTextField();

        // Adiciona o campo de nome chamando o método auxiliar.
        adicionarCampo(
                formPanel, // Passa o painel do formulário.
                "Nome do Produto:", // Rótulo do campo.
                txtNome, // O componente de texto criado acima.
                gbc, // Configurações de layout.
                0, // Coluna.
                1, // Linha 1 na grade.
                true // Define 'true' para permitir que o usuário digite no campo.
        );

        // ==========================================================
        // CAMPO DATA DE VALIDADE
        // ==========================================================

        // Chama o método 'criarCampoData' que retorna um campo de texto especial já com máscara protetora de data.
        JFormattedTextField txtValidade = criarCampoData();

        // Adiciona o campo de validade ao formulário.
        adicionarCampo(
                formPanel, // Passa o painel.
                "Validade (DD/MM/AAAA):", // Rótulo do campo.
                txtValidade, // Componente formatado de data.
                gbc, // Configurações de layout.
                0, // Coluna.
                2, // Linha 2 na grade.
                true // Permitido editar.
        );

        // ==========================================================
        // CAMPO QUANTIDADE
        // ==========================================================

        // Instancia um campo de texto comum para receber a quantidade do produto.
        JTextField txtQtd = new JTextField();

        // Adiciona o campo de quantidade ao formulário.
        adicionarCampo(
                formPanel, // Passa o painel.
                "Quantidade:", // Rótulo do campo.
                txtQtd, // Componente de texto da quantidade.
                gbc, // Configurações de layout.
                0, // Coluna.
                3, // Linha 3 na grade.
                true // Permitido editar.
        );

        // ==========================================================
        // CAMPO UNIDADE DE MEDIDA
        // ==========================================================

        // Instancia uma caixa de seleção suspensa (Dropdown) configurada para armazenar textos (Strings).
        JComboBox<String> cbUnidade = new JComboBox<>(
                new String[]{"kg", "L", "g", "ml", "un"} // Cria e passa um array de strings com as opções do menu.
        );

        // Adiciona a caixa de seleção ao formulário.
        adicionarCampo(
                formPanel, // Passa o painel.
                "Unidade de Medida:", // Rótulo do campo.
                cbUnidade, // O componente dropdown.
                gbc, // Configurações de layout.
                0, // Coluna.
                4, // Linha 4 na grade.
                true // Permitido interagir.
        );

        // ==========================================================
        // BOTÃO SALVAR
        // ==========================================================

        // Instancia um botão clássico exibindo o texto "Salvar Produto".
        JButton btnSalvar = new JButton("Salvar Produto");

        // Define a cor de fundo do botão usando o sistema RGB (gera um tom de rosa/salmão).
        btnSalvar.setBackground(new Color(218, 118, 155));

        // Define a cor da fonte do texto do botão como branca.
        btnSalvar.setForeground(Color.WHITE);

        // Vincula um escutador de eventos (ActionListener) via expressão Lambda para capturar o momento do clique no botão.
        btnSalvar.addActionListener(e -> {

            // Pega o texto do campo nome, remove espaços extras nas pontas (trim) e checa se ficou vazio.
            if (txtNome.getText().trim().isEmpty()) {

                // Se estiver vazio, exibe uma caixinha de alerta flutuante na tela.
                JOptionPane.showMessageDialog(
                        this, // Define esta janela de cadastro como o elemento pai do alerta.
                        "Nome do produto é obrigatório!" // Texto da mensagem de erro.
                );

                // Interrompe o código do botão imediatamente, impedindo que o envio ao back-end aconteça.
                return;
            }

            // ======================================================
            // MONTA O JSON
            // ======================================================

            // Monta uma string formatada seguindo rigorosamente o padrão JSON com os dados capturados da tela.
            String json = String.format(
                    "{\"nome\":\"%s\", \"validade\":\"%s\", \"quantidade\":\"%s\", \"unidade\":\"%s\"}",
                    txtNome.getText(), // Substitui o primeiro '%s' pelo texto do campo Nome.
                    txtValidade.getText(), // Substitui o segundo '%s' pela data digitada.
                    txtQtd.getText(), // Substitui o terceiro '%s' pelo número da quantidade.
                    cbUnidade.getSelectedItem() // Substitui o quarto '%s' pelo item que estiver selecionado no dropdown.
            );

            // Cria e inicia instantaneamente uma Thread paralela para rodar a requisição HTTP sem travar os botões da tela.
            new Thread(() -> enviarParaBackend(json)).start();
        }); // Fim do bloco do evento do botão.

        // Adiciona o painel do formulário na região central do JDialog.
        add(formPanel, BorderLayout.CENTER);

        // Adiciona o botão de salvar fixado na borda inferior (Sul) do JDialog.
        add(btnSalvar, BorderLayout.SOUTH);
    } // Fim do método Construtor Cadastro.

    // ==============================================================
    // MÉTODO RESPONSÁVEL POR ENVIAR OS DADOS AO BACK-END
    // ==============================================================

    // Método privado que recebe a String JSON montada e faz a comunicação com o servidor externo.
    private void enviarParaBackend(String json) {

        // Inicia o bloco 'try' para capturar qualquer erro de internet ou servidor que possa acontecer no caminho.
        try {

            // Instancia uma fábrica de clientes HTTP do Java para gerenciar a conexão de rede.
            HttpClient client = HttpClient.newHttpClient();

            // Inicia o padrão de construção (Builder) para preparar as configurações do pacote HTTP que será enviado.
            HttpRequest request = HttpRequest.newBuilder()

                    // Converte a string de texto com a URL fornecida em um objeto URI válido e anexa à requisição.
                    .uri(URI.create("COLA AQUI A URL DO LOCALHOST COM O METÓDO"))

                    //============================================================================================
                    //============================================================================================
                    //=======================MUDA PARA O LOCALHOST CERTO COM O NOME DA AÇÃO=======================
                    //============================================================================================
                    //============================================================================================
                    .uri(URI.create("COLA AQUI A URL DO LOCALHOST COM O METÓDO"))
                    .header("Content-Type", "application/json")

                    // Configura o método como POST (criação de dados) e anexa a String JSON convertida em um fluxo de dados.
                    .POST(HttpRequest.BodyPublishers.ofString(json))

                    // Finaliza e gera o objeto final HttpRequest pronto para o disparo.
                    .build();

            // Dispara a requisição de forma síncrona através do cliente e armazena a resposta recebida.
            HttpResponse<String> response =
                    client.send(
                            request, // O pacote de requisição que criamos logo acima.
                            HttpResponse.BodyHandlers.ofString() // Define que a resposta textual vinda do servidor deve ser lida como String.
                    );

            // Analisa se o código de status HTTP retornado pelo servidor significa sucesso (200 OK ou 201 Created).
            if (
                    response.statusCode() == 200 ||
                            response.statusCode() == 201
            ) {

                // Redireciona a execução de volta para a Thread principal do Swing (EDT) para que possamos mexer na tela com segurança.
                SwingUtilities.invokeLater(() -> {

                    // Exibe uma caixinha de aviso indicando sucesso no cadastro.
                    JOptionPane.showMessageDialog(
                            this, // Define a janela atual como pai.
                            "Produto cadastrado com sucesso!" // Texto da mensagem.
                    );

                    // Fecha a janela de cadastro atual e limpa os componentes da memória do computador.
                    dispose();
                }); // Fim do invokeLater de sucesso.

            } else { // Entra aqui caso o servidor responda, mas com um código de erro (ex: 400, 404, 500).

                // Lança manualmente uma exceção descrevendo qual foi o código de status problemático retornado.
                throw new Exception(
                        "Erro do servidor: " + response.statusCode()
                );
            } // Fim do bloco if/else do statusCode.

        } // Fim do bloco try. Captura erros específicos de falha física de conexão (servidor desligado/offline).
        catch (java.net.ConnectException e) {

            // Direciona o alerta de erro para ser exibido de forma segura na tela (Thread do Swing).
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(
                            this, // Janela pai.
                            "Erro: Servidor Offline! Verifique o Back-end.", // Mensagem explicativa.
                            "Erro", // Título da barra da janela pop-up.
                            JOptionPane.ERROR_MESSAGE // Aplica o ícone nativo de erro (X vermelho).
                    )
            );
        } // Captura qualquer outro tipo de erro genérico desconhecido que aconteça durante o processo.
        catch (Exception e) {

            // Direciona o alerta visual para a Thread gráfica do Swing.
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(
                            this, // Janela pai.
                            "Erro ao enviar: " + e.getMessage(), // Exibe o texto exato do erro capturado pelo sistema.
                            "Erro", // Título da barra da janela.
                            JOptionPane.ERROR_MESSAGE // Aplica o ícone de erro (X vermelho).
                    )
            );
        } // Fim do tratamento de erros.
    } // Fim do método enviarParaBackend.

    // ==============================================================
    // CRIA O CAMPO DE DATA COM MÁSCARA
    // ==============================================================

    // Método privado que fabrica e devolve um JFormattedTextField personalizado para receber datas.
    private JFormattedTextField criarCampoData() {

        // Inicia o try pois o construtor do MaskFormatter pode disparar erros caso o padrão de texto seja inválido.
        try {

            // Instancia o formatador definindo o padrão de máscara onde cada '#' aceita estritamente apenas números.
            MaskFormatter mascara =
                    new MaskFormatter("##/##/####");

            // Configura o caractere sublinhado '_' para aparecer visualmente nos espaços vazios enquanto o usuário não digita.
            mascara.setPlaceholderCharacter('_');

            // Retorna o novo componente de campo formatado alimentado com a máscara que acabamos de criar.
            return new JFormattedTextField(mascara);

        } // Caso o padrão "##/##/####" falhe por algum erro de sintaxe interno do Java.
        catch (ParseException e) {

            // Cria e retorna um campo formatado vazio padrão (sem máscara) para evitar que o software quebre e pare de funcionar.
            return new JFormattedTextField();
        } // Fim do catch.
    } // Fim do método criarCampoData.

    // ==============================================================
    // MÉTODO AUXILIAR PARA ADICIONAR CAMPOS AO FORMULÁRIO
    // ==============================================================

    // Método auxiliar reutilizável que automatiza a colocação organizada de rótulos (Labels) e inputs lado a lado na tela.
    private void adicionarCampo(
            JPanel panel, // O painel destino onde os elementos serão inseridos.
            String label, // O texto descritivo que ficará à esquerda do campo.
            Component comp, // O objeto visual de entrada de dados em si (JTextField, JComboBox, etc).
            GridBagConstraints gbc, // A referência das regras de posicionamento da grade.
            int x, // Parâmetro de coluna (não usado de forma dinâmica aqui devido ao valor estático abaixo).
            int y, // O número exato da linha da grade onde o par (label e input) vai sentar.
            boolean editavel // Controla se o campo de entrada aceita digitação (true) ou fica congelado (false).
    ) {

        // Força a coordenada X (coluna) em 0 para garantir que o rótulo descritivo fique sempre posicionado no lado esquerdo.
        gbc.gridx = 0;

        // Atualiza a coordenada Y (linha) com base no número recebido por parâmetro no método.
        gbc.gridy = y;

        // Cria dinamicamente um JLabel com o texto recebido e o adiciona no painel respeitando as configurações do gbc.
        panel.add(new JLabel(label), gbc);

        // Altera a coordenada X (coluna) para 1 para mover a mira do layout para a direita do rótulo.
        gbc.gridx = 1;

        // Altera o estado de ativação do componente de entrada com base no booleano recebido.
        comp.setEnabled(editavel);

        // Adiciona o componente de entrada de dados no painel na coluna da direita, mantendo a mesma linha.
        panel.add(comp, gbc);
    } // Fim do método adicionarCampo.
} // Fim da classe Cadastro.
