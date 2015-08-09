/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import java.sql.Date;

/**
 *
 * @author EngComp
 */
public class ServidorBean {

    private int codigo,nFotos,tentativas,validaFoto;
    private String obs,profissao,sexo,status,t1,t2,tipo,bairro,cep,estado,cidade,complemento,caminhoFoto,nome,EstadoCivil,email,logradouro,CPF,RG,filiacao;
    private Date dtNascimento,dtCadastro;
    private double renda,limite;
    private boolean fotoTirada=false;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public ServidorBean(){
        
    }

    public String getNome() {
        
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getValidaFoto() {
        return validaFoto;
    }

    public void setValidaFoto(int validaFoto) {
        this.validaFoto = validaFoto;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public String getEstadoCivil() {
        return EstadoCivil;
    }

    public void setEstadoCivil(String EstadoCivil) {
        this.EstadoCivil = EstadoCivil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getRG() {
        return RG;
    }

    public void setRG(String RG) {
        this.RG = RG;
    }

    public String getFiliacao() {
        return filiacao;
    }

    public void setFiliacao(String filiacao) {
        this.filiacao = filiacao;
    }
    public void setNFotos(int nFotos){
        this.nFotos=nFotos;
    }
    
    // GETTER
    public int getCodigo() {
        return codigo;
    }

    public int getnFotos() {
        return nFotos;
    }

    public int getTentativas() {
        return tentativas;
    }

    public String getObs() {
        return obs;
    }

    public String getProfissao() {
        return profissao;
    }

    public double getRenda() {
        return renda;
    }

    public String getSexo() {
        return sexo;
    }

    public String getStatus() {
        return status;
    }

    public String getT1() {
        return t1;
    }

    public String getT2() {
        return t2;
    }

    public String getTipo() {
        return tipo;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCep() {
        return cep;
    }

    public String getCidade() {
        return cidade;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public boolean isFotoTirada() {
        return fotoTirada;
    }

    public Date getDtNascimento() {
        return dtNascimento;
    }

    public Date getDtCadastro() {
        return dtCadastro;
    }

    //SETTER
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setnFotos(int nFotos) {
        this.nFotos = nFotos;
    }

    public void setTentativas(int tentativas) {
        this.tentativas = tentativas;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public void setRenda(double renda) {
        this.renda = renda;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setT1(String t1) {
        this.t1 = t1;
    }

    public void setT2(String t2) {
        this.t2 = t2;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }

    public void setDtNascimento(Date dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public void setDtCadastro(Date dtCadastro) {
        this.dtCadastro = dtCadastro;
    }
    
    public void setFotoTirada(boolean fotoTirada) {
        this.fotoTirada=fotoTirada;
    }
    
    

}
