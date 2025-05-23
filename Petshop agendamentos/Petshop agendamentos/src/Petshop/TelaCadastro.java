package petshop;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TelaCadastro extends JFrame {
    private JTextField campoNomeDono, campoCpf, campoNomeAnimal, campoTipoAnimal, campoRacaAnimal;
    private JComboBox<String> comboServico, comboHorario;
    private JButton botaoCadastrar;

    public TelaCadastro() {
        setTitle("Agendamento - Petshop");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel labelNomeDono = new JLabel("Nome do Dono:");
        labelNomeDono.setBounds(10, 10, 120, 20);
        add(labelNomeDono);

        campoNomeDono = new JTextField();
        campoNomeDono.setBounds(140, 10, 200, 20);
        add(campoNomeDono);

        JLabel labelCpf = new JLabel("CPF:");
        labelCpf.setBounds(10, 40, 120, 20);
        add(labelCpf);

        campoCpf = new JTextField();
        campoCpf.setBounds(140, 40, 200, 20);
        add(campoCpf);        
        ((AbstractDocument) campoCpf.getDocument()).setDocumentFilter(new DocumentFilter() {
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if ((fb.getDocument().getLength() + string.length()) <= 11 && string.matches("\\d+")) {
            super.insertString(fb, offset, string, attr);
        }
    }

    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if ((fb.getDocument().getLength() - length + text.length()) <= 11 && text.matches("\\d+")) {
            super.replace(fb, offset, length, text, attrs);
        }
    }
});


        JLabel labelNomeAnimal = new JLabel("Nome do Animal:");
        labelNomeAnimal.setBounds(10, 70, 120, 20);
        add(labelNomeAnimal);

        campoNomeAnimal = new JTextField();
        campoNomeAnimal.setBounds(140, 70, 200, 20);
        add(campoNomeAnimal);

        JLabel labelTipoAnimal = new JLabel("Tipo de Animal:");
        labelTipoAnimal.setBounds(10, 100, 120, 20);
        add(labelTipoAnimal);

        campoTipoAnimal = new JTextField();
        campoTipoAnimal.setBounds(140, 100, 200, 20);
        add(campoTipoAnimal);

        JLabel labelRacaAnimal = new JLabel("Raça do Animal:");
        labelRacaAnimal.setBounds(10, 130, 120, 20);
        add(labelRacaAnimal);

        campoRacaAnimal = new JTextField();
        campoRacaAnimal.setBounds(140, 130, 200, 20);
        add(campoRacaAnimal);

        JLabel labelServico = new JLabel("Serviço:");
        labelServico.setBounds(10, 160, 120, 20);
        add(labelServico);

        comboServico = new JComboBox<>(new String[] {"Banho", "Tosa", "Banho e Tosa"});
        comboServico.setBounds(140, 160, 200, 20);
        add(comboServico);

        JLabel labelHorario = new JLabel("Horário:");
        labelHorario.setBounds(10, 190, 120, 20);
        add(labelHorario);

        comboHorario = new JComboBox<>();
        for (int i = 8; i <= 17; i++) {
            comboHorario.addItem(String.format("%02d:00", i));
        }
        comboHorario.setBounds(140, 190, 200, 20);
        add(comboHorario);

        botaoCadastrar = new JButton("Cadastrar");
        botaoCadastrar.setBounds(140, 230, 120, 30);
        add(botaoCadastrar);
        
        JButton botaoListar = new JButton("Listar Agendamentos");
        botaoListar.setBounds(140, 270, 200, 30);
        add(botaoListar);

        botaoCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Agendamento agendamento = new Agendamento();
                agendamento.setNomeDono(campoNomeDono.getText());
                agendamento.setCpf(campoCpf.getText());
                agendamento.setNomeAnimal(campoNomeAnimal.getText());
                agendamento.setTipoAnimal(campoTipoAnimal.getText());
                agendamento.setRacaAnimal(campoRacaAnimal.getText());
                agendamento.setServico(comboServico.getSelectedItem().toString());
                agendamento.setHorario(comboHorario.getSelectedItem().toString());

                new AgendamentoDAO().cadastrar(agendamento);
                JOptionPane.showMessageDialog(null, "Agendamento realizado com sucesso!");
                campoNomeDono.setText("");
                campoCpf.setText("");
                campoNomeAnimal.setText("");
                campoTipoAnimal.setText("");
                campoRacaAnimal.setText("");
                comboServico.setSelectedIndex(0);
                comboHorario.setSelectedIndex(0);
            }
        });
        botaoListar.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        StringBuilder resultado = new StringBuilder();
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM agendamentos");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                resultado.append("ID: ").append(rs.getInt("id")).append("\n");
                resultado.append("Dono: ").append(rs.getString("nome_dono")).append("\n");
                resultado.append("CPF: ").append(rs.getString("cpf")).append("\n");
                resultado.append("Animal: ").append(rs.getString("nome_animal")).append("\n");
                resultado.append("Tipo: ").append(rs.getString("tipo_animal")).append("\n");
                resultado.append("Raça: ").append(rs.getString("raca_animal")).append("\n");
                resultado.append("Serviço: ").append(rs.getString("servico")).append("\n");
                resultado.append("Horário: ").append(rs.getString("horario")).append("\n");
                resultado.append("----------------------------------\n");
            }

            JTextArea areaTexto = new JTextArea(resultado.toString());
            areaTexto.setEditable(false);
            JScrollPane scroll = new JScrollPane(areaTexto);
            scroll.setPreferredSize(new java.awt.Dimension(500, 300));
            JOptionPane.showMessageDialog(null, scroll, "Agendamentos", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar agendamentos: " + ex.getMessage());
        }
    }
});


    }

    public static void main(String[] args) {
        new TelaCadastro().setVisible(true);
    }
}