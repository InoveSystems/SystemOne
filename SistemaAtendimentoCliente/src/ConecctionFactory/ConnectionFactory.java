/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConecctionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.postgresql.util.PSQLException;

/**
 *
 * @author Ritiele
 */
public class ConnectionFactory {

    public static Connection con;

    public static Connection openConnection(String ip) throws SQLException {

        //Se não houver conexão, retorna uma nova instanciada
        if (con == null) {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("org.postgresql.Driver nao encontrado. Entre em contado com o administrador do sistema!");
            }
            con = DriverManager.getConnection("jdbc:postgresql://" + ip + ":5432/postgres", "postgres", "ibanez2017");
        }
        return con;
    }
}
