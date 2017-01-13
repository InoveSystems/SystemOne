/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ServidorBean;
import ConecctionFactory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author EngComp
 */
public class ServidorDAO {

    Connection conexao;

    public ServidorDAO() {
        this.conexao = ConnectionFactory.openConnection();
    }

    public void create(ServidorBean server) throws SQLException {
        String sql = "INSERT INTO fichas (cod,tipo,numero,data_hora_impre,atendimentoiniciado,atendimentofinalizado) VALUES (NEXTVAL('seqfichas'),?,?,now(),?,?)";
        PreparedStatement pst = this.conexao.prepareStatement(sql);
        pst.setString(1, server.getTipo());
        pst.setInt(2, server.getNumeroFicha());
        pst.setBoolean(3, server.getAtendimentoIniciado());
        pst.setBoolean(4, server.getAtendimentoFinalizado());
        pst.executeUpdate();
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery("SELECT CURRVAL('seqfichas')");
        if (rs.next()) {
            server.setCodigo(rs.getInt(1));
        }
        rs.close();
        st.close();
        pst.close();
    }

    public void update(ServidorBean server) throws SQLException {
        String sql = "UPDATE fichas SET caixa=?,data_hora_inicio=?, tempo_espera=?,estouro_justificativa=?,atendimentoiniciado=?, atendimentofinalizado=?,cod_func=? WHERE cod=?";
        PreparedStatement pst = this.conexao.prepareStatement(sql);
        pst.setString(1, server.getIdcaixa());
        pst.setTimestamp(2, server.getDataHoraIni());
        pst.setTimestamp(3, server.getTempoEspera());
        pst.setString(4, server.getEstouroAtendimento());
        pst.setBoolean(5, server.getAtendimentoIniciado());
        pst.setBoolean(6, server.getAtendimentoFinalizado());
        pst.setInt(8, server.getCodigo());
        pst.setInt(7, server.getCod_Func());
        pst.executeUpdate();
        pst.close();
    }

    public void updatefinalizar(ServidorBean server) throws SQLException {
        String sql = "UPDATE fichas SET data_hora_fim=?, tempo_atendimento=?,estouro_justificativa=?,atendimentoiniciado=?, atendimentofinalizado=? WHERE cod=?";
        PreparedStatement pst = this.conexao.prepareStatement(sql);
        pst.setTimestamp(1, server.getDataHoraFim());
        pst.setTimestamp(2, server.getTempoAtendimento());
        pst.setString(3, server.getEstouroAtendimento());
        pst.setBoolean(4, server.getAtendimentoIniciado());
        pst.setBoolean(5, server.getAtendimentoFinalizado());
        pst.setInt(6, server.getCodigo());
        pst.executeUpdate();
        pst.close();
    }

    public ResultSet retriveficha(ServidorBean server) throws SQLException {
        Statement stm = this.conexao.createStatement();
        ResultSet rs;
        String sql = "SELECT * FROM fichas WHERE tipo='" + server.getTipo() + "' AND numero='" + server.getNumeroFicha() + "'";
        rs = stm.executeQuery(sql);
        return rs;
    }

    public ResultSet retrivefichaAberta(ServidorBean server) throws SQLException {
        Statement stm = this.conexao.createStatement();
        ResultSet rs;
        String sql = "SELECT * FROM fichas WHERE caixa='" + server.getIdcaixa() + "' AND atendimentoiniciado='" + server.getAtendimentoIniciado() + "' AND atendimentofinalizado='" + server.getAtendimentoFinalizado() + "'";
        rs = stm.executeQuery(sql);
        return rs;
    }
}
