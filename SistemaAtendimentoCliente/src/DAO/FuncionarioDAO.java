package DAO;

import Bean.FuncionarioBean;
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
 * @author Ritiele
 */
public class FuncionarioDAO {

    public String IPCom = "127.0.0.1";
    public String diretorioUsuario = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
    public File IPConfig = new File(diretorioUsuario + File.separator + "InoveSystems" + File.separator + "Config" + File.separator + "IPConfig.txt");
    public Connection conexao;

    public FuncionarioDAO() throws SQLException {
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

        String sql = "UPDATE funcionarios SET nome=?,cpf=?,email=?,telefone_mv=?,telefone_rs=?,telefone_cm=?,cidade=?,estado=?,logradouro=?,numero=?,bairro=?,complemento=?,senha=?,administrador=? WHERE cod=?";
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

        String sql = "SELECT * FROM funcionarios WHERE nome LIKE'" + funcionario.getNome().toUpperCase() + '%' + "'";
        //  String sql = "SELECT * FROM funcionarios WHERE nome='" + funcionario.getNome() + "'";
        rs = stm.executeQuery(sql);
        return rs;
    }

    public ResultSet retriveCPF(FuncionarioBean funcionario) throws SQLException {
        Statement stm = this.conexao.createStatement();
        ResultSet rs;
        String sql = "SELECT * FROM funcionarios WHERE cpf LIKE'" + funcionario.getCPF().toUpperCase() + '%' + "'";
        rs = stm.executeQuery(sql);
        return rs;
    }

    public ResultSet retriveTotal() throws SQLException {
        Statement stm = this.conexao.createStatement();
        ResultSet rs;
        String sql = "SELECT * FROM funcionarios ORDER BY cod ASC";
        rs = stm.executeQuery(sql);
        return rs;
    }

    public void delete(FuncionarioBean funcionario) throws SQLException {
        String sql = "DELETE FROM funcionarios WHERE cod=?";
        PreparedStatement pst = this.conexao.prepareStatement(sql);
        pst.setInt(1, funcionario.getCodigo());
        pst.executeUpdate();
        pst.close();
    }

}
