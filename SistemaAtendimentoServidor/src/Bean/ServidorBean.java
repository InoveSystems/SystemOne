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
    private Timestamp DataHoraImp;
    private String idcaixa;
    private Timestamp DataHoraIni;
    private Timestamp DataHoraFim;
    private Timestamp TempoAtendimento;
    private Timestamp TempoEspera;
    private String EstouroAtendimento;
    private Boolean AtendimentoIniciado;
    private Boolean AtendimentoFinalizado;

    public ServidorBean(int Codigo, String Tipo, int NumeroFicha, Boolean AtendimentoIniciado) {
        this.Codigo = Codigo;
        this.Tipo = Tipo;
        this.NumeroFicha = NumeroFicha;
        this.AtendimentoIniciado = AtendimentoIniciado;
    }

    public Boolean getAtendimentoIniciado() {
        return AtendimentoIniciado;
    }

    public void setAtendimentoIniciado(Boolean AtendimentoIniciado) {
        this.AtendimentoIniciado = AtendimentoIniciado;
    }

    public Boolean getAtendimentoFinalizado() {
        return AtendimentoFinalizado;
    }

    public void setAtendimentoFinalizado(Boolean AtendimentoFinalizado) {
        this.AtendimentoFinalizado = AtendimentoFinalizado;
    }

    public Timestamp getDataHoraImp() {
        return DataHoraImp;
    }

    public void setDataHoraImp(Timestamp DataHoraImp) {
        this.DataHoraImp = DataHoraImp;
    }

    public String getIdcaixa() {
        return idcaixa;
    }

    public void setIdcaixa(String idcaixa) {
        this.idcaixa = idcaixa;
    }

    public Timestamp getDataHoraIni() {
        return DataHoraIni;
    }

    public void setDataHoraIni(Timestamp DataHoraIni) {
        this.DataHoraIni = DataHoraIni;
    }

    public Timestamp getDataHoraFim() {
        return DataHoraFim;
    }

    public void setDataHoraFim(Timestamp DataHoraFim) {
        this.DataHoraFim = DataHoraFim;
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

    public Timestamp getTempoAtendimento() {
        return TempoAtendimento;
    }

    public void setTempoAtendimento(Timestamp TempoAtendimento) {
        this.TempoAtendimento = TempoAtendimento;
    }

    public Timestamp getTempoEspera() {
        return TempoEspera;
    }

    public void setTempoEspera(Timestamp TempoEspera) {
        this.TempoEspera = TempoEspera;
    }

    public String getEstouroAtendimento() {
        return EstouroAtendimento;
    }

    public void setEstouroAtendimento(String EstouroAtendimento) {
        this.EstouroAtendimento = EstouroAtendimento;
    }

}
