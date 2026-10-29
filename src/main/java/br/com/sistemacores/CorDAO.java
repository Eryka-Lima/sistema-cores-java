
package br.com.sistemacores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CorDAO {

    public void cadastrar(Cor cor) throws SQLException {
        String sql = """
            INSERT INTO cores
                (nome, familia, codigo_hex, tipo, metalica, acabamento)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cor.getNome());
            stmt.setString(2, cor.getFamilia());
            stmt.setString(3, cor.getCodigoHex());
            stmt.setString(4, cor.getTipo());

            if (cor instanceof CorEspecial especial) {
                stmt.setBoolean(5, especial.isMetalica());
                stmt.setString(6, especial.getAcabamento());
            } else {
                stmt.setNull(5, java.sql.Types.BOOLEAN);
                stmt.setNull(6, java.sql.Types.VARCHAR);
            }

            stmt.executeUpdate();

            try (ResultSet chaves = stmt.getGeneratedKeys()) {
                if (chaves.next()) {
                    cor.setId(chaves.getInt(1));
                }
            }
        }
    }

    public List<Cor> listarTodos() throws SQLException {
        String sql = "SELECT * FROM cores ORDER BY id";
        List<Cor> cores = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                cores.add(mapearCor(rs));
            }
        }

        return cores;
    }

    public List<Cor> buscarPorNome(String nome) throws SQLException {
        String sql = """
            SELECT * FROM cores
            WHERE nome LIKE ?
            ORDER BY nome
            """;

        List<Cor> cores = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    cores.add(mapearCor(rs));
                }
            }
        }

        return cores;
    }

    private Cor mapearCor(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nome = rs.getString("nome");
        String familia = rs.getString("familia");
        String codigoHex = rs.getString("codigo_hex");
        String tipo = rs.getString("tipo");

        if ("ESPECIAL".equalsIgnoreCase(tipo)) {
            boolean metalica = rs.getBoolean("metalica");
            String acabamento = rs.getString("acabamento");

            return new CorEspecial(
                    id, nome, familia, codigoHex,
                    metalica, acabamento
            );
        }

        return new Cor(id, nome, familia, codigoHex, tipo);
    }
}
