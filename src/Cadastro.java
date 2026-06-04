import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;

public class Cadastro extends JDialog {
    private static int contadorId = 1001;

    public Cadastro(Frame parent) {
        super(parent, "Cadastrar Produto", true);
        setSize(400, 450);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- CAMPOS ---
        adicionarCampo(formPanel, "ID do Produto:", new JTextField(String.valueOf(contadorId)), gbc, 0, 0, false);

        JTextField txtNome = new JTextField();
        adicionarCampo(formPanel, "Nome do Produto:", txtNome, gbc, 0, 1, true);

        // Campo de Data com Máscara
        JFormattedTextField txtValidade = criarCampoData();
        adicionarCampo(formPanel, "Validade (DD/MM/AAAA):", txtValidade, gbc, 0, 2, true);

        JTextField txtQtd = new JTextField();
        adicionarCampo(formPanel, "Quantidade:", txtQtd, gbc, 0, 3, true);

        JComboBox<String> cbUnidade = new JComboBox<>(new String[]{"kg", "L", "g", "ml", "un"});
        adicionarCampo(formPanel, "Unidade de Medida:", cbUnidade, gbc, 0, 4, true);

        // --- BOTÃO SALVAR ---
        JButton btnSalvar = new JButton("Salvar Produto");
        btnSalvar.setBackground(new Color(218, 118, 155));
        btnSalvar.setForeground(Color.WHITE);

        btnSalvar.addActionListener(e -> {
            if (txtNome.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome do produto é obrigatório!");
                return;
            }

            // Monta o JSON (Simples)
            String json = String.format(
                    "{\"nome\":\"%s\", \"validade\":\"%s\", \"quantidade\":\"%s\", \"unidade\":\"%s\"}",
                    txtNome.getText(), txtValidade.getText(), txtQtd.getText(), cbUnidade.getSelectedItem()
            );


            new Thread(() -> enviarParaBackend(json)).start();
        });

        add(formPanel, BorderLayout.CENTER);
        add(btnSalvar, BorderLayout.SOUTH);
    }

    private void enviarParaBackend(String json) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()


                    //============================================================================================
                    //============================================================================================
                    //=======================MUDA PARA O LOCALHOST CERTO COM O NOME DA AÇÃO=======================
                    //============================================================================================
                    //============================================================================================
                    .uri(URI.create("COLA AQUI A URL DO LOCALHOST COM O METÓDO"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
                    dispose();
                });
            } else {
                throw new Exception("Erro do servidor: " + response.statusCode());
            }

        } catch (java.net.ConnectException e) {
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(this, "Erro: Servidor Offline! Verifique o Back-end.", "Erro", JOptionPane.ERROR_MESSAGE));
        } catch (Exception e) {
            SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(this, "Erro ao enviar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE));
        }
    }

    private JFormattedTextField criarCampoData() {
        try {
            MaskFormatter mascara = new MaskFormatter("##/##/####");
            mascara.setPlaceholderCharacter('_');
            return new JFormattedTextField(mascara);
        } catch (ParseException e) {
            return new JFormattedTextField();
        }
    }

    private void adicionarCampo(JPanel panel, String label, Component comp, GridBagConstraints gbc, int x, int y, boolean editavel) {
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        comp.setEnabled(editavel);
        panel.add(comp, gbc);
    }
}
