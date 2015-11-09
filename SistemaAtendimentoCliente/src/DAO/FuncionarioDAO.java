/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.FuncionarioBean;
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
public class FuncionarioDAO {

    Connection conexao;

    public FuncionarioDAO() {
        this.conexao = ConnectionFactory.openConnection();
    }

    public void create(FuncionarioBean funcionario) throws SQLException {
        String sql = "INSERT INTO funcionarios (cod,nome,cpf,email,telefone_mv,telefone_rs,telefone_cm,cidade,estado,logradouro,numero,bairro,complemento,senha,administrador) VALUES (NEXTVAL('seqfunc'),?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pst = this.conexao.prepareStatement(sql);
        pst.setString(1, funcionario.getNome());
        pst.setString(2, funcionario.getCPF());
        pst.setString(3, funcionario.getEmail());
        pst.setString(4, funcionario.getTelefone_mv());
        pst.setString(5, funcionario.getTelefone_rs());
        pst.setString(6, funcionario.getTelefone_cm());
        pst.setString(7, funcionario.getCidade());
        pst.setString(8, funcionario.getEstado());
        pst.setString(9, funcionario.getLogradouro());
        pst.setString(10, funcionario.getNumero());
        pst.setString(11, funcionario.getBairro());
        pst.setString(12, funcionario.getComplemento());
        pst.setString(13, funcionario.getSenha());
        pst.setBoolean(14, funcionario.getAdministrador());
        pst.executeUpdate();
        Statement st = this.conexao.createStatement();
        ResultSet rs = st.executeQuery("SELECT CURRVAL('seqfunc')");
        if (rs.next()) {
            funcionario.setCodigo(rs.getInt(1));
        }
        rs.close();
        st.close();
        pst.close();
    }

    public void update(FuncionarioBean funcionario) throws SQLException {
        String sql = "UPDATE funcionarios SET nome?,cpf?,email?,telefone_mv?,telefone_rs?,telefone_cm?,cidade?,estado?,logradouro?,numero?,bairro?,complemento?,senha?,administrador? WHERE cod=?";
        PreparedStatement pst = this.conexao.prepareStatement(sql);
        pst.setString(1, funcionario.getNome());
        pst.setString(2, funcionario.getCPF());
        pst.setString(3, funcionario.getEmail());
        pst.setString(4, funcionario.getTelefone_mv());
        pst.setString(5, funcionario.getTelefone_rs());
        pst.setString(6, funcionario.getTelefone_cm());
        pst.setString(7, funcionario.getCidade());
        pst.setString(8, funcionario.getEstado());
        pst.setString(9, funcionario.getLogradouro());
        pst.setString(10, funcionario.getNumero());
        pst.setString(11, funcionario.getBairro());
        pst.setString(12, funcionario.getComplemento());
        pst.setString(13, funcionario.getSenha());
        pst.setBoolean(14, funcionario.getAdministrador());
        pst.setInt(15, funcionario.getCodigo());
        pst.executeUpdate();
        pst.close();
    }

    public ResultSet retriveId(FuncionarioBean funcionario) throws SQLException {
        Statement stm = this.conexao.createStatement();
        ResultSet rs;
        String sql = "SELECT * FROM funcionarios WHERE cod=" + funcionario.getCodigo();
        rs = stm.executeQuery(sql);
        return rs;
    }

    public ResultSet retriveNome(FuncionarioBean funcionario) throws SQLException {
        Statement stm = this.conexao.createStatement();
        ResultSet rs;
        String sql = "SELECT * FROM funcionarios WHERE nome='" + funcionario.getNome()+"'";
        rs = stm.executeQuery(sql);
        return rs;
    }

    public ResultSet retriveCPF(FuncionarioBean funcionario) throws SQLException {
        Statement stm = this.conexao.createStatement();
        ResultSet rs;
        String sql = "SELECT * FROM funcionarios WHERE cpf='" + funcionario.getCPF()+"'";
        rs = stm.executeQuery(sql);
        return rs;
    }

    public ResultSet retriveTotal() throws SQLException {
        Statement stm = this.conexao.createStatement();
        ResultSet rs;
        String sql = "SELECT * FROM funcionarios ";
        rs = stm.executeQuery(sql);
        return rs;
    }

}
