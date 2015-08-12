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
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.swing.JOptionPane;

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
        String sql = "UPDATE fichas SET caixa=?,data_hora_inicio=?, tempo_espera=?,estouro_justificativa=?,atendimentoiniciado=?, atendimentofinalizado=? WHERE cod=?";
        PreparedStatement pst = this.conexao.prepareStatement(sql);
        pst.setString(1, server.getIdcaixa());
        pst.setTimestamp(2, server.getDataHoraIni());
        pst.setTimestamp(3, server.getTempoEspera());
        pst.setString(4, server.getEstouroAtendimento());
        pst.setBoolean(5, server.getAtendimentoIniciado());
        pst.setBoolean(6, server.getAtendimentoFinalizado());
        pst.setInt(7, server.getCodigo());
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

//    public int getUltimoCodigo() throws SQLException {
//        Statement st = conexao.createStatement();
//        String sqlDLL = "select max(codigo) from cliente";
//        ResultSet rs = st.executeQuery(sqlDLL);
//        try {
//            int id = 0;
//            while (rs.next()) {
//
//                id = rs.getInt(1);
//            }
//            st.close();
//            rs.close();
//
//            return id;
//
//        } catch (SQLException ex) {
//            Logger.getLogger(DAO.ServidorDAO.class.getName()).log(Level.SEVERE, null, ex);
//            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados!", "Erro", JOptionPane.INFORMATION_MESSAGE);
//            System.exit(0);
//
//        }
//
//        return 0;
//    }
//
//    public List<ServidorBean> getCliente(int codigo) throws SQLException {
//        List<ServidorBean> lista = new ArrayList<ServidorBean>();
//        String sql = "select dtCadastro,satus,nome,tipo,rg,cpf,sexo,estadocivil,filiacao,dtNascimento,cep,cidade,estado,t1,t2,email,logradouro,bairro,complemento,profissao,renda,limiteCompras,obs,nFotos,caminhoFoto,validaFoto,tentativas,codigo from cliente where codigo=" + codigo + " order by codigo";
//        Statement st = this.conexao.createStatement();
//        ResultSet rs = st.executeQuery(sql);
//        numeroLinhas = 0;
//        while (rs.next()) {
//            ServidorBean clienteBean = new ServidorBean();
//            clienteBean.setCodigo(rs.getInt(28));
//            clienteBean.setNome(rs.getString(3));
//            clienteBean.setProfissao(rs.getString(20));
//            clienteBean.setSexo(rs.getString(7));
//            clienteBean.setStatus(rs.getString(2));
//            clienteBean.setT1(rs.getString(14));
//            clienteBean.setT2(rs.getString(15));
//            clienteBean.setBairro(rs.getString(18));
//            clienteBean.setCidade(rs.getString(12));
//            clienteBean.setCep(rs.getString(11));
//            clienteBean.setComplemento(rs.getString(19));
//            clienteBean.setCaminhoFoto(rs.getString(25));
//            clienteBean.setObs(rs.getString(23));
//            clienteBean.setEstadoCivil(rs.getString(8));
//            clienteBean.setEmail(rs.getString(16));
//            clienteBean.setLogradouro(rs.getString(17));
//            clienteBean.setCPF(rs.getString(6));
//            clienteBean.setRG(rs.getString(5));
//            clienteBean.setFiliacao(rs.getString(9));
//            clienteBean.setTentativas(rs.getInt(27));
//            clienteBean.setDtCadastro(rs.getDate(1));
//            clienteBean.setDtNascimento(rs.getDate(10));
//            clienteBean.setRenda(rs.getDouble(21));
//            clienteBean.setLimite(rs.getDouble(22));
//            clienteBean.setNFotos(rs.getInt(24));
//            clienteBean.setValidaFoto(rs.getInt(26));
//            clienteBean.setStatus(rs.getString(2));
//            clienteBean.setTipo(rs.getString(4));
//            clienteBean.setEstado(rs.getString(13));
//            lista.add(clienteBean);
//            numeroLinhas++;
//        }
//        rs.close();
//        st.close();
//        return lista;
//    }
//
//    public void setCliente(String nome, double limite, String bairro, String Cep, String CPF, String cidade, String complemento, String email, String estado, String filiacao, String logradouro, String obs, String profissao, String RG, double renda, String t1, String t2, String sexo, String status, String tipo, int tentativas, Date dtNascimento, String estadoCivil) {
//        try {
//
//            Statement st = conexao.createStatement();
//
//            String sqlDDL = "insert into cliente (dtCadastro,limiteCompras,nome,bairro,cep,cpf,cidade,complemento,email,estado,filiacao,logradouro,obs,profissao,RG,t1,t2,sexo,satus,tipo,tentativas,renda,dtNascimento,estadocivil)values(now()," + limite + ",lower('" + nome + "'),'" + bairro + "', '" + Cep + "', '" + CPF + "','" + cidade + "','" + complemento + "','" + email + "','" + estado + "','" + filiacao + "','" + logradouro + "','" + obs + "','" + profissao + "','" + RG + "','" + t1 + "', '" + t2 + "', '" + sexo + "','" + status + "','" + tipo + "'," + tentativas + "," + renda + ",'" + dtNascimento + "','" + estadoCivil + "')";
//            int ret = st.executeUpdate(sqlDDL);
//            //logs.log(Level.INFO, String.format("Número de registros inseridos %s", ret));
//            JOptionPane.showMessageDialog(null, "Dados gravados com sucesso!", "Salvo", JOptionPane.INFORMATION_MESSAGE);
//            st.close();
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "Erro ao gravar o caminho no banco de dados", "Erro", JOptionPane.ERROR_MESSAGE);
//            Logger.getLogger(ServidorDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
//
//    public void atualizaCliente(int codigo, String nome, double limite, String bairro, String Cep, String CPF, String cidade, String complemento, String email, String estado, String filiacao, String logradouro, String obs, String profissao, String RG, double renda, String t1, String t2, String sexo, String status, String tipo, int tentativas, Date dtNascimento, String estadoCivil) {
//        Statement st;
//        try {
//            st = conexao.createStatement();
//            String sql = "update cliente set nome=lower('" + nome + "'),satus='" + status + "',tipo='" + tipo + "',rg='" + RG + "',cpf='" + CPF + "',sexo='" + sexo + "',estadocivil='" + estadoCivil + "',filiacao='" + filiacao + "',\n"
//                    + "dtNascimento='" + dtNascimento + "',cep='" + Cep + "',cidade='" + cidade + "',estado='" + estado + "',t1='" + t1 + "',t2='" + t2 + "',email='" + email + "',logradouro='" + logradouro + "',bairro='" + bairro + "',\n"
//                    + "complemento='" + complemento + "',profissao='" + profissao + "',renda=" + renda + ",limiteCompras=" + limite + ",obs='" + obs + "',tentativas='" + tentativas + "' where codigo=" + codigo + "";
//            int ret = st.executeUpdate(sql);
//            JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!", "Salvo", JOptionPane.INFORMATION_MESSAGE);
//            st.close();
//
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, "Erro ao gravar o caminho no banco de dados", "Erro", JOptionPane.ERROR_MESSAGE);
//            Logger.getLogger(ServidorDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
//
//    public int ContaCod(int cod) throws SQLException {
//        Statement st = conexao.createStatement();
//        String sqlDLL = "select tentativas from cliente where codigo=" + cod + " ";
//        ResultSet rs = st.executeQuery(sqlDLL);
//        try {
//            int count = 0;
//            while (rs.next()) {
//                count = rs.getInt(1);
//                if (count == 0) {
//                    count++;
//                }
//                st.close();
//                rs.close();
//                return count;
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(ServidorDAO.class.getName()).log(Level.SEVERE, null, ex);
//            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados!", "Erro", JOptionPane.INFORMATION_MESSAGE);
//            System.exit(0);
//
//        }
//        return 0;
//    }
//
//    public List<ServidorBean> getCliente(String nome, String CPF, String RG) throws SQLException {
//        List<ServidorBean> lista = new ArrayList<ServidorBean>();
//        String terminal = null;
//        String sql = null;
//        if (nome != null) {
//            terminal = "nome like lower('" + nome + "%')";
//        }
//        if (CPF != null) {
//            terminal = "cpf='" + CPF + "'";
//        }
//        if (RG != null) {
//            terminal = "rg='" + RG + "'";
//        }
//        sql = "select dtCadastro,satus,initcap(nome),tipo,rg,cpf,sexo,estadocivil,filiacao,dtNascimento,cep,cidade,estado,t1,t2,email,logradouro,bairro,complemento,profissao,renda,limiteCompras,obs,nFotos,caminhoFoto,validaFoto,tentativas,codigo from cliente where " + terminal + " order by nome";
//        Statement st = this.conexao.createStatement();
//        ResultSet rs = st.executeQuery(sql);
//        numeroLinhas = 0;
//        while (rs.next()) {
//            ServidorBean clienteBean = new ServidorBean();
//            clienteBean.setCodigo(rs.getInt(28));
//            clienteBean.setNome(rs.getString(3));
//            clienteBean.setProfissao(rs.getString(20));
//            clienteBean.setSexo(rs.getString(7));
//            clienteBean.setStatus(rs.getString(2));
//            clienteBean.setT1(rs.getString(14));
//            clienteBean.setT2(rs.getString(15));
//            clienteBean.setBairro(rs.getString(18));
//            clienteBean.setCidade(rs.getString(12));
//            clienteBean.setCep(rs.getString(11));
//            clienteBean.setComplemento(rs.getString(19));
//            clienteBean.setCaminhoFoto(rs.getString(25));
//            clienteBean.setObs(rs.getString(23));
//            clienteBean.setEstadoCivil(rs.getString(8));
//            clienteBean.setEmail(rs.getString(16));
//            clienteBean.setLogradouro(rs.getString(17));
//            clienteBean.setCPF(rs.getString(6));
//            clienteBean.setRG(rs.getString(5));
//            clienteBean.setFiliacao(rs.getString(9));
//            clienteBean.setTentativas(rs.getInt(27));
//            clienteBean.setDtCadastro(rs.getDate(1));
//            clienteBean.setDtNascimento(rs.getDate(10));
//            clienteBean.setRenda(rs.getDouble(21));
//            clienteBean.setLimite(rs.getDouble(22));
//            clienteBean.setNFotos(rs.getInt(24));
//            clienteBean.setValidaFoto(rs.getInt(26));
//            clienteBean.setStatus(rs.getString(2));
//            clienteBean.setTipo(rs.getString(4));
//            clienteBean.setEstado(rs.getString(13));
//            lista.add(clienteBean);
//            numeroLinhas++;
//        }
//        rs.close();
//        st.close();
//        return lista;
//    }
//
//    public List<ServidorBean> getCliente() throws SQLException {
//        List<ServidorBean> lista = new ArrayList<ServidorBean>();
//        String sql = "select dtCadastro,satus,initcap(nome),tipo,rg,cpf,sexo,estadocivil,filiacao,dtNascimento,cep,cidade,estado,t1,t2,email,logradouro,bairro,complemento,profissao,renda,limiteCompras,obs,nFotos,caminhoFoto,validaFoto,tentativas,codigo from cliente order by nome";
//        Statement st = this.conexao.createStatement();
//        ResultSet rs = st.executeQuery(sql);
//        numeroLinhas = 0;
//        while (rs.next()) {
//            ServidorBean clienteBean = new ServidorBean();
//            clienteBean.setCodigo(rs.getInt(28));
//            clienteBean.setNome(rs.getString(3));
//            clienteBean.setProfissao(rs.getString(20));
//            clienteBean.setSexo(rs.getString(7));
//            clienteBean.setStatus(rs.getString(2));
//            clienteBean.setT1(rs.getString(14));
//            clienteBean.setT2(rs.getString(15));
//            clienteBean.setBairro(rs.getString(18));
//            clienteBean.setCidade(rs.getString(12));
//            clienteBean.setCep(rs.getString(11));
//            clienteBean.setComplemento(rs.getString(19));
//            clienteBean.setCaminhoFoto(rs.getString(25));
//            clienteBean.setObs(rs.getString(23));
//            clienteBean.setEstadoCivil(rs.getString(8));
//            clienteBean.setEmail(rs.getString(16));
//            clienteBean.setLogradouro(rs.getString(17));
//            clienteBean.setCPF(rs.getString(6));
//            clienteBean.setRG(rs.getString(5));
//            clienteBean.setFiliacao(rs.getString(9));
//            clienteBean.setTentativas(rs.getInt(27));
//            clienteBean.setDtCadastro(rs.getDate(1));
//            clienteBean.setDtNascimento(rs.getDate(10));
//            clienteBean.setRenda(rs.getDouble(21));
//            clienteBean.setLimite(rs.getDouble(22));
//            clienteBean.setNFotos(rs.getInt(24));
//            clienteBean.setValidaFoto(rs.getInt(26));
//            clienteBean.setStatus(rs.getString(2));
//            clienteBean.setTipo(rs.getString(4));
//            clienteBean.setEstado(rs.getString(13));
//            lista.add(clienteBean);
//            numeroLinhas++;
//        }
//        rs.close();
//        st.close();
//        return lista;
//    }
}
