
package br.com.sistemacores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL =  "http//";

    private static final String USUARIO = "Eryka";

    private static final String SENHA = "Batata123";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(
                URL, USUARIO, SENHA
        );
    }


}
