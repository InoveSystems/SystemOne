/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.GraficoBean;
import ConecctionFactory.ConnectionFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author EngComp
 */
public class GraficoDAO {

    public String IPCom = "127.0.0.1";
    public String diretorioUsuario = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
    public File IPConfig = new File(diretorioUsuario + File.separator + "InoveSystems" + File.separator + "Config" + File.separator + "IPConfig.txt");
    public Connection conexao;

    public GraficoDAO() throws SQLException {
        FileReader fr;
        //lendo ou criando arquivo com o ip do servidor
        try {
            if (!IPConfig.exists()) {
                try {
                    IPConfig.createNewFile();
                    FileWriter fw = new FileWriter(IPConfig, false);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(IPCom);
                    bw.newLine();
                    bw.close();
                    fw.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro! Não foi possivel criar IPConfig! \nEntre em contado com o administrador do sistema! \nInove Systems - www.inovesystems.com.br", "Inove Systems - Informação", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
            } else {
                fr = new FileReader(IPConfig);
                BufferedReader br = new BufferedReader(fr);
                while (br.ready()) {
                    String linha = br.readLine();
                    IPCom = linha;
                }
                br.close();
                fr.close();

            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Erro! IPConfig não encontrado! \nEntre em contado com o administrador do sistema! \nInove Systems - www.inovesystems.com.br", "Inove Systems - Informação", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } catch (IOException ex) {

        }
        this.conexao = ConnectionFactory.openConnection(IPCom);
    }

//    public ResultSet retriveficha(GraficoBean server) throws SQLException {
//        Statement stm = this.conexao.createStatement();
//        ResultSet rs;
//        String sql = "SELECT * FROM fichas WHERE tipo='" + server.getTipo() + "' AND numero='" + server.getNumeroFicha() + "'";
//        rs = stm.executeQuery(sql);
//        return rs;
//    }
//
//    public ResultSet retrivefichaAberta(GraficoBean server) throws SQLException {
//        Statement stm = this.conexao.createStatement();
//        ResultSet rs;
//        String sql = "SELECT * FROM fichas WHERE caixa='" + server.getIdcaixa() + "' AND atendimentoiniciado='" + server.getAtendimentoIniciado() + "' AND atendimentofinalizado='" + server.getAtendimentoFinalizado() + "'";
//        rs = stm.executeQuery(sql);
//        return rs;
//    }
    public ResultSet retriveAtendenteTempo(java.util.Date inicio, java.util.Date fim) throws SQLException {
        Statement stm = this.conexao.createStatement();
        ResultSet rs;
        String sql = "SELECT funcionarios.nome as nome, AVG(fichas.tempo_atendimento::time) as media FROM fichas INNER JOIN funcionarios ON fichas.cod_func = funcionarios.cod  WHERE (fichas.tempo_atendimento::date) >='"+inicio+"' AND (fichas.tempo_atendimento::date) <='" +fim+ "'group by funcionarios.nome";
        rs = stm.executeQuery(sql);
        return rs;
    }
    
    public ResultSet retriveCaixaTempo(java.util.Date inicio, java.util.Date fim) throws SQLException {
        Statement stm = this.conexao.createStatement();
        ResultSet rs;
        String sql = "SELECT fichas.caixa as caixa, AVG(fichas.tempo_atendimento::time) as media FROM fichas WHERE ((fichas.tempo_atendimento::date) >='"+inicio+"' AND (fichas.tempo_atendimento::date) <='" +fim+ "') AND caixa<>''group by fichas.caixa";
        rs = stm.executeQuery(sql);
        return rs;
    }
    
    public ResultSet retriveTipoTempo(java.util.Date inicio, java.util.Date fim) throws SQLException {
        Statement stm = this.conexao.createStatement();
        ResultSet rs;
        String sql = "SELECT fichas.tipo as tipo, AVG(fichas.tempo_atendimento::time) as media FROM fichas WHERE (fichas.tempo_atendimento::date) >='"+inicio+"' AND (fichas.tempo_atendimento::date) <='" +fim+ "' group by fichas.tipo";
        rs = stm.executeQuery(sql);
        return rs;
    }
    
    

}
