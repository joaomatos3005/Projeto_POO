import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AgendamentoDAO {

    public void cadastrar(Agendamento agendamento) {
        String sql = "INSERT INTO agendamentos (nome_dono, cpf, nome_animal, tipo_animal, raca_animal, servico, horario) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, agendamento.getNomeDono());
            stmt.setString(2, agendamento.getCpf());
            stmt.setString(3, agendamento.getNomeAnimal());
            stmt.setString(4, agendamento.getTipoAnimal());
            stmt.setString(5, agendamento.getRacaAnimal());
            stmt.setString(6, agendamento.getServico());
            stmt.setString(7, agendamento.getHorario());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}