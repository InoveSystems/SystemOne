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

/**
 *
 * @author EngComp
 */
public class ConnectionFactory {

    public static Connection con;

    public static Connection openConnection() {

        //Se não houver conexão, retorna uma nova instanciada
        if (con == null) {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException ex) {
                System.out.println("asdnasudsad");
            }
            try {
                con = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/inovesystems", "inovesystems", "inove+1052");
                JOptionPane.showMessageDialog(null, "CONECTADO COM SUCESSO!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de dados!", "", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return con;
    }
}
