/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

/**
 *
 * @author EngComp
 */
public class FuncionarioBean {

    private int Codigo;
    private String Nome;
    private String CPF;
    private String Email;
    private String Telefone_mv;
    private String Telefone_rs;
    private String Telefone_cm;
    private String Cidade;
    private String Estado;
    private String Logradouro;
    private String Numero;
    private String Bairro;
    private String Complemento;
    private String Senha;
    private boolean Administrador;

    public FuncionarioBean(int Codigo, String Nome, String CPF, String Email, String Telefone_mv, String Telefone_rs, String Telefone_cm, String Cidade, String Estado, String Logradouro, String Numero, String Bairro, String Complemento, String Senha, boolean Administrador) {

        this.Codigo = Codigo;
        this.Nome = Nome;
        this.CPF = CPF;
        this.Email = Email;
        this.Telefone_mv = Telefone_mv;
        this.Telefone_rs = Telefone_rs;
        this.Telefone_cm = Telefone_cm;
        this.Cidade = Cidade;
        this.Estado = Estado;
        this.Logradouro = Logradouro;
        this.Numero = Numero;
        this.Bairro = Bairro;
        this.Complemento = Complemento;
        this.Senha = Senha;
        this.Administrador = Administrador;

    }
    public FuncionarioBean() {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int Codigo) {
        this.Codigo = Codigo;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getTelefone_mv() {
        return Telefone_mv;
    }

    public void setTelefone_mv(String Telefone_mv) {
        this.Telefone_mv = Telefone_mv;
    }

    public String getTelefone_rs() {
        return Telefone_rs;
    }

    public void setTelefone_rs(String Telefone_rs) {
        this.Telefone_rs = Telefone_rs;
    }

    public String getTelefone_cm() {
        return Telefone_cm;
    }

    public void setTelefone_cm(String Telefone_cm) {
        this.Telefone_cm = Telefone_cm;
    }

    public String getCidade() {
        return Cidade;
    }

    public void setCidade(String Cidade) {
        this.Cidade = Cidade;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public String getLogradouro() {
        return Logradouro;
    }

    public void setLogradouro(String Logradouro) {
        this.Logradouro = Logradouro;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String Numero) {
        this.Numero = Numero;
    }

    public String getBairro() {
        return Bairro;
    }

    public void setBairro(String Bairro) {
        this.Bairro = Bairro;
    }

    public String getComplemento() {
        return Complemento;
    }

    public void setComplemento(String Complemento) {
        this.Complemento = Complemento;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String Senha) {
        this.Senha = Senha;
    }

    public boolean getAdministrador() {
        return Administrador;
    }

    public void setAdministrador(boolean Administrador) {
        this.Administrador = Administrador;
    }

}
