/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author EngComp
 */
public class ServidorBean {

    private int Codigo;
    private String Tipo;
    private int NumeroFicha;
    private Timestamp  DataHora;
    private Timestamp  TempoAtendimento;
    private Timestamp  TempoEspera;
    private String EstouroAtendimento;
    private Boolean AtendimentoStatus;

    public ServidorBean(int Codigo, String Tipo, int NumeroFicha, Boolean AtendimentoStatus) {
        this.Codigo = Codigo;
        this.Tipo = Tipo;
        this.NumeroFicha = NumeroFicha;
        this.AtendimentoStatus = AtendimentoStatus;
    }

    public Boolean getAtendimentoStatus() {
        return AtendimentoStatus;
    }

    public void setAtendimentoStatus(Boolean AtendimentoStatus) {
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

    public Timestamp  getDataHora() {
        return DataHora;
    }

    public void setDataHora(Timestamp  DataHora) {
        this.DataHora = DataHora;
    }

    public Timestamp  getTempoAtendimento() {
        return TempoAtendimento;
    }

    public void setTempoAtendimento(Timestamp  TempoAtendimento) {
        this.TempoAtendimento = TempoAtendimento;
    }

    public Timestamp  getTempoEspera() {
        return TempoEspera;
    }

    public void setTempoEspera(Timestamp  TempoEspera) {
        this.TempoEspera = TempoEspera;
    }

    public String getEstouroAtendimento() {
        return EstouroAtendimento;
    }

    public void setEstouroAtendimento(String EstouroAtendimento) {
        this.EstouroAtendimento = EstouroAtendimento;
    }

    

  

}
