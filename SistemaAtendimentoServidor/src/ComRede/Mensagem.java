package ComRede;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.swing.Action;

public class Mensagem implements Serializable {

    private Set<String> setOnlines = new HashSet<String>();
    private Action action;
    private String name;
    private String text;
    private String nameReserved;
    private String atual;
    private String ultima;
    private String penultima;
    private String antepenultima;
    private String status;
    private String tipo;
    private int numero;
    private int idFinalizar;

    public enum Action {

        CONNECT, DISCONNECT, SEND_ONE, SEND_ALL, USERS_ONLINE, PRINT, CALL, FINALIZAR, REIMPRIMIR
    }

    public Set<String> getSetOnlines() {
        return setOnlines;
    }

    public void setSetOnlines(Set<String> setOnlines) {
        this.setOnlines = setOnlines;
    }

    public Action getAction() {
        return action;
    }

    public int getIdFinalizar() {
        return idFinalizar;
    }

    public void setIdFinalizar(int idFinalizar) {
        this.idFinalizar = idFinalizar;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Mensagem() {
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNameReserved() {
        return nameReserved;
    }

    public void setNameReserved(String nameReserved) {
        this.nameReserved = nameReserved;
    }

    public String getAtual() {
        return atual;
    }

    public void setAtual(String atual) {
        this.atual = atual;
    }

    public String getUltima() {
        return ultima;
    }

    public void setUltima(String ultima) {
        this.ultima = ultima;
    }

    public String getPenultima() {
        return penultima;
    }

    public void setPenultima(String penultima) {
        this.penultima = penultima;
    }

    public String getAntepenultima() {
        return antepenultima;
    }

    public void setAntepenultima(String antepenultima) {
        this.antepenultima = antepenultima;
    }

}
