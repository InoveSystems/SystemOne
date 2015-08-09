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

    private int Codigo;
    private String Tipo;
    private int NumeroFicha;
    private Date DataHora;
    private Date TempoAtendimento;
    private Date TempoEspera;
    private String EstouroAtendimento;
    private Boolean AtendimentoStatus;

    public ServidorBean(int Codigo, String Tipo, int NumeroFicha, Date DataHora, Boolean AtendimentoStatus) {
        this.Codigo = Codigo;
        this.Tipo = Tipo;
        this.NumeroFicha = NumeroFicha;
        this.DataHora = DataHora;
        this.AtendimentoStatus = AtendimentoStatus;
    }

    public ServidorBean() {
    }

    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int Codigo) {
        this.Codigo = Codigo;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public int getNumeroFicha() {
        return NumeroFicha;
    }

    public void setNumeroFicha(int NumeroFicha) {
        this.NumeroFicha = NumeroFicha;
    }

    public Date getDataHora() {
        return DataHora;
    }

    public void setDataHora(Date DataHora) {
        this.DataHora = DataHora;
    }

    public Date getTempoAtendimento() {
        return TempoAtendimento;
    }

    public void setTempoAtendimento(Date TempoAtendimento) {
        this.TempoAtendimento = TempoAtendimento;
    }

    public Date getTempoEspera() {
        return TempoEspera;
    }

    public void setTempoEspera(Date TempoEspera) {
        this.TempoEspera = TempoEspera;
    }

    public String getEstouroAtendimento() {
        return EstouroAtendimento;
    }

    public void setEstouroAtendimento(String EstouroAtendimento) {
        this.EstouroAtendimento = EstouroAtendimento;
    }

    public Boolean getAtendimentoFinalizado() {
        return AtendimentoStatus;
    }

    public void setAtendimentoFinalizado(Boolean AtendimentoFinalizado) {
        this.AtendimentoStatus = AtendimentoStatus;
    }

}
