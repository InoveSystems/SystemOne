package Telas;

import Bean.ServidorBean;
import ComRede.Mensagem.Action;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import ComRede.Mensagem;
import DAO.ServidorDAO;
import Impressao.Imprimir;
import java.awt.print.PrinterException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import Impressao.PdfCriar;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static java.time.Instant.now;
import java.util.Date;
import javax.swing.JOptionPane;
import org.joda.time.LocalTime;
import org.joda.time.Period;

public class Server {

    ServidorDAO servidorDAO = new ServidorDAO();
    ServidorBean servidorBean = new ServidorBean();
    static Image image = Toolkit.getDefaultToolkit().getImage("C:/Users/EngComp/Documents/NetBeansProjects/Sanar/SistemaAtendimentoCliente/src/Imagens/3D.png");
    static TrayIcon trayIcon = new TrayIcon(image, "..:: Inove Systems ::.. Status: ATIVO!");
    private Socket socket;
    private ServerSocket serverSocket;
    private Map<String, ObjectOutputStream> mapOnlines = new HashMap<String, ObjectOutputStream>();
    private int SenhaComum = 0;
    private int SenhaPrioritaria = 0;
    private int SenhaPopular = 0;
    int incComum = 0;
    int incPrioritaria = 0;
    int incPopular = 0;
    FilaComum filacomum = new FilaComum();
    FilaPrioritaria filaprioritaria = new FilaPrioritaria();
    FilaPopular filapopular = new FilaPopular();
    Imprimir print;
    PdfCriar pdf = new PdfCriar();
    String atual;
    String ultima;
    String penultima;
    String antepenultima;
    String tes;
    String tipo = null;
    int numero = 0;
    boolean finalizou = false;
    boolean finalizou1 = false;
    java.util.Date date_impressao;
    java.util.Date date_fim;
    boolean finalizou2 = false;

    public Server() {

    }

    public void Conect() {
        try {
            serverSocket = new ServerSocket(8888);
            System.out.println("Servidor ON");
            while (true) {
                socket = serverSocket.accept();
                new Thread(new ListenerSocket(socket)).start();
            }
        } catch (IOException ex) {

        }
    }

    private class ListenerSocket implements Runnable {

        private ObjectOutputStream output;
        private ObjectInputStream input;

        public ListenerSocket(Socket socket) {

            try {
                this.output = new ObjectOutputStream(socket.getOutputStream());
                this.input = new ObjectInputStream(socket.getInputStream());
            } catch (IOException ex) {

            }
        }

        @Override
        public void run() {
            Mensagem message = null;
            try {
                while ((message = (Mensagem) input.readObject()) != null) {
                    Action action = message.getAction();

                    if (action.equals(Action.CONNECT)) {
                        boolean isConnect = connect(message, output);
                        if (isConnect) {
                            mapOnlines.put(message.getName(), output);
                            sendOnlines();
                        }

                    } else if (action.equals(Action.DISCONNECT)) {
                        disconnect(message, output);
                        sendOnlines();
                        return;
                    } else if (action.equals(Action.SEND_ONE)) {
                        sendOne(message, output);
                    } else if (action.equals(Action.SEND_ALL)) {
                        sendAll(message);
                    } else if (action.equals(Action.USERS_ONLINE)) {

                    } else if (action.equals(action.CALL)) {
                        chamar(message);
                    } else if (action.equals(action.PRINT)) {
                        imprimir(message);

                    } else if (action.equals(action.FINALIZAR)) {
                        finalizacao(message);
                    }

                }
            } catch (IOException ex) {
                Mensagem cm = new Mensagem();
                cm.setName(message.getName());
                disconnect(cm, output);
                sendOnlines();

            } catch (ClassNotFoundException ex) {

            }
        }

    }

    private void atualizarPainel(Mensagem message) {
        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            message.setAction(Action.SEND_ONE);
            try {
                kv.getValue().writeObject(message);
            } catch (IOException ex) {

            }
        }
        message.setName(message.getName());
        message.setAtual(message.getAtual());
        message.setUltima(message.getUltima());
        message.setPenultima(message.getPenultima());
        message.setAntepenultima(message.getAntepenultima());
        sendAll(message);
    }

    private void chamar(Mensagem message) {
        int idAtendimento = 0;

        if (message.getText().equals("convencional")) {
            if (filaprioritaria.estaVazia()) {
                if (filacomum.estaVazia() == false) {
                    finalizou = false;
                    try {
                        //Pesquisa se tem ficha em aberto para aquele caixa
                        ServidorBean PesquisarInicio = new ServidorBean();
                        ServidorDAO ini = new ServidorDAO();
                        PesquisarInicio.setIdcaixa(message.getName());
                        PesquisarInicio.setAtendimentoIniciado(true);
                        PesquisarInicio.setAtendimentoFinalizado(false);
                        ResultSet rsi;
                        rsi = ini.retrivefichaAberta(PesquisarInicio);
                        while (rsi.next()) {
                            idAtendimento = rsi.getInt("cod");
                            tipo = rsi.getString("tipo");
                            numero = rsi.getInt("numero");
                            date_impressao = rsi.getTimestamp("data_hora_impre");
                            numero = Integer.parseInt(String.format("%03d", numero));
                            message.setIdFinalizar(idAtendimento);
                            finalizar(message);
                            finalizou = true;
                        }
                        if (!finalizou) {
                            //se nao tiver ficha aberta Pesquisa ID da ficha e adiciona um caixa a ela iniciando atendimento = true
                            try {
                                System.out.println("CAIXA: " + message.getName());
                                System.out.println("chamando: " + filacomum.primeiro());
                                ServidorBean Pesquisar = new ServidorBean();
                                ServidorDAO c = new ServidorDAO();
                                Pesquisar.setTipo(filacomum.primeiro().substring(0, 1));
                                Pesquisar.setNumeroFicha(Integer.parseInt(filacomum.primeiro().substring(1, 4)));
                                ResultSet rs = c.retriveficha(Pesquisar);
                                while (rs.next()) {
                                    idAtendimento = rs.getInt("cod");
                                    date_impressao = rs.getTimestamp("data_hora_impre");
                                }
                                ServidorBean Atualizar = new ServidorBean();
                                Atualizar.setCodigo(idAtendimento);
                                Atualizar.setIdcaixa(message.getName());
                                try {
                                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");//formata a data e hora atual                                    
                                    java.util.Date minhaData;//cria a minha data e hora atual                                   
                                    minhaData = dateFormat.parse(getDateTime()); //minha data  recebe a data e hora                                     
                                    java.sql.Timestamp sqlDate = new java.sql.Timestamp(minhaData.getTime()); // coloca pra timestamp do banco
                                    LocalTime start = new LocalTime(date_impressao);// pesquisar data no banco e por aqui
                                    LocalTime end = new LocalTime(sqlDate);
                                    Period period = new Period(start, end);
                                    java.util.Date resul;
                                    resul = dateFormat.parse(getDate() + " " + period.getHours() + ":" + period.getMinutes() + ":" + period.getSeconds());
                                    java.sql.Timestamp sqlDate2 = new java.sql.Timestamp(resul.getTime()); // coloca pra timestamp do banco
                                    Atualizar.setTempoEspera(sqlDate2); // calcula o tempo de espera 
                                    Atualizar.setDataHoraIni(sqlDate); //atualiz a a hora que iniciou o atendimento
                                    Atualizar.setAtendimentoIniciado(true);
                                    Atualizar.setAtendimentoFinalizado(false);
                                    ServidorDAO atualiza = new ServidorDAO();
                                    new Thread() {
                                        @Override
                                        public void run() {
                                            try {
                                                atualiza.update(Atualizar);
                                            } catch (SQLException ex) {

                                            }
                                        }
                                    }.start();
                                    message.setStatus("yes");
                                    message.setAtual(filacomum.primeiro());
                                    message.setUltima(ultima);
                                    message.setPenultima(penultima);
                                    message.setAntepenultima(antepenultima);
                                    filacomum.remover();
                                    atualizarPainel(message);
                                    tes = antepenultima;
                                    antepenultima = penultima;
                                    penultima = ultima;
                                    ultima = message.getAtual();
                                } catch (ParseException ex) {

                                }
                            } catch (SQLException ex) {

                            }
                        }
                    } catch (SQLException ex) {

                    }
                } else {

                    try {
                        //se vazia filaa Pesquisa se tem ficha em aberto para aquele caixa
                        ServidorBean PesquisarInicio = new ServidorBean();
                        ServidorDAO ini = new ServidorDAO();
                        PesquisarInicio.setIdcaixa(message.getName());
                        PesquisarInicio.setAtendimentoIniciado(true);
                        PesquisarInicio.setAtendimentoFinalizado(false);
                        ResultSet rsi;
                        rsi = ini.retrivefichaAberta(PesquisarInicio);

                        while (rsi.next()) {
                            idAtendimento = rsi.getInt("cod");
                            tipo = rsi.getString("tipo");
                            numero = rsi.getInt("numero");
                            // date_impressao = rsi.getTimestamp("data_hora_impre");
                            numero = Integer.parseInt(String.format("%03d", numero));
                            message.setIdFinalizar(idAtendimento);
                            finalizar(message);
                            finalizou = true;
                        }

                    } catch (SQLException ex) {

                    }
                    message.setStatus("no");
                    message.setAtual(ultima);
                    message.setUltima(penultima);
                    message.setPenultima(antepenultima);
                    message.setAntepenultima(tes);
                    atualizarPainel(message);

                }
            } else {
                if (filaprioritaria.estaVazia() == false) {
                    finalizou1 = false;
                    try {
                        //Pesquisa se tem ficha em aberto para aquele caixa
                        ServidorBean PesquisarInicio = new ServidorBean();
                        ServidorDAO ini = new ServidorDAO();
                        PesquisarInicio.setIdcaixa(message.getName());
                        PesquisarInicio.setAtendimentoIniciado(true);
                        PesquisarInicio.setAtendimentoFinalizado(false);
                        ResultSet rsi;
                        rsi = ini.retrivefichaAberta(PesquisarInicio);
                        while (rsi.next()) {
                            idAtendimento = rsi.getInt("cod");
                            tipo = rsi.getString("tipo");
                            numero = rsi.getInt("numero");
                            date_impressao = rsi.getTimestamp("data_hora_impre");
                            numero = Integer.parseInt(String.format("%03d", numero));
                            message.setIdFinalizar(idAtendimento);
                            finalizar(message);
                            finalizou1 = true;

                        }
                        if (!finalizou1) {
                            //se nao tiver ficha aberta Pesquisa ID da ficha e adiciona um caixa a ela iniciando atendimento = true
                            try {
                                System.out.println("CAIXA: " + message.getName());
                                System.out.println("chamando: " + filaprioritaria.primeiro());
                                ServidorBean Pesquisar = new ServidorBean();
                                ServidorDAO c = new ServidorDAO();
                                Pesquisar.setTipo(filaprioritaria.primeiro().substring(0, 1));
                                Pesquisar.setNumeroFicha(Integer.parseInt(filaprioritaria.primeiro().substring(1, 4)));
                                ResultSet rs = c.retriveficha(Pesquisar);
                                while (rs.next()) {
                                    idAtendimento = rs.getInt("cod");
                                    date_impressao = rs.getTimestamp("data_hora_impre");
                                }
                                ServidorBean Atualizar = new ServidorBean();
                                Atualizar.setCodigo(idAtendimento);
                                Atualizar.setIdcaixa(message.getName());
                                try {
                                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");//formata a data e hora atual                                    
                                    java.util.Date minhaData;//cria a minha data e hora atual                                   
                                    minhaData = dateFormat.parse(getDateTime()); //minha data  recebe a data e hora                                     
                                    java.sql.Timestamp sqlDate = new java.sql.Timestamp(minhaData.getTime()); // coloca pra timestamp do banco
                                    LocalTime start = new LocalTime(date_impressao);// pesquisar data no banco e por aqui
                                    LocalTime end = new LocalTime(sqlDate);
                                    Period period = new Period(start, end);
                                    java.util.Date resul;
                                    resul = dateFormat.parse(getDate() + " " + period.getHours() + ":" + period.getMinutes() + ":" + period.getSeconds());
                                    java.sql.Timestamp sqlDate2 = new java.sql.Timestamp(resul.getTime()); // coloca pra timestamp do banco
                                    Atualizar.setTempoEspera(sqlDate2); // calcula o tempo de espera 
                                    Atualizar.setDataHoraIni(sqlDate); //atualiz a a hora que iniciou o atendimento
                                    Atualizar.setAtendimentoIniciado(true);
                                    Atualizar.setAtendimentoFinalizado(false);
                                    ServidorDAO atualiza = new ServidorDAO();
                                    atualiza.update(Atualizar);
                                    message.setStatus("yes");
                                    message.setAtual(filaprioritaria.primeiro());
                                    message.setUltima(ultima);
                                    message.setPenultima(penultima);
                                    message.setAntepenultima(antepenultima);
                                    filaprioritaria.remover();
                                    atualizarPainel(message);
                                    tes = antepenultima;
                                    antepenultima = penultima;
                                    penultima = ultima;
                                    ultima = message.getAtual();
                                } catch (ParseException ex) {

                                }
                            } catch (SQLException ex) {

                            }
                        }
                    } catch (SQLException ex) {

                    }
                } else {

                    try {
                        //se vazia filaa Pesquisa se tem ficha em aberto para aquele caixa
                        ServidorBean PesquisarInicio = new ServidorBean();
                        ServidorDAO ini = new ServidorDAO();
                        PesquisarInicio.setIdcaixa(message.getName());
                        PesquisarInicio.setAtendimentoIniciado(true);
                        PesquisarInicio.setAtendimentoFinalizado(false);
                        ResultSet rsi;

                        rsi = ini.retrivefichaAberta(PesquisarInicio);

                        while (rsi.next()) {
                            idAtendimento = rsi.getInt("cod");
                            tipo = rsi.getString("tipo");
                            numero = rsi.getInt("numero");
                            numero = Integer.parseInt(String.format("%03d", numero));
                            message.setIdFinalizar(idAtendimento);
                            finalizar(message);
                            finalizou1 = true;
                        }
                    } catch (SQLException ex) {

                    }
                    message.setStatus("no");
                    message.setAtual(ultima);
                    message.setUltima(penultima);
                    message.setPenultima(antepenultima);
                    message.setAntepenultima(tes);
                    atualizarPainel(message);

                }
            }
        } else {
            if (message.getText().equals("popular")) {
                if (filapopular.estaVazia() == false) {
                    finalizou2 = false;
                    try {
                        //Pesquisa se tem ficha em aberto para aquele caixa
                        ServidorBean PesquisarInicio = new ServidorBean();
                        ServidorDAO ini = new ServidorDAO();
                        PesquisarInicio.setIdcaixa(message.getName());
                        PesquisarInicio.setAtendimentoIniciado(true);
                        PesquisarInicio.setAtendimentoFinalizado(false);
                        ResultSet rsi;
                        rsi = ini.retrivefichaAberta(PesquisarInicio);
                        while (rsi.next()) {
                            idAtendimento = rsi.getInt("cod");
                            tipo = rsi.getString("tipo");
                            numero = rsi.getInt("numero");
                            date_impressao = rsi.getTimestamp("data_hora_impre");
                            numero = Integer.parseInt(String.format("%03d", numero));
                            message.setIdFinalizar(idAtendimento);
                            finalizar(message);
                            finalizou2 = true;
                        }
                        if (!finalizou2) {
                            //se nao tiver ficha aberta Pesquisa ID da ficha e adiciona um caixa a ela iniciando atendimento = true
                            try {
                                System.out.println("CAIXA: " + message.getName());
                                System.out.println("chamando: " + filapopular.primeiro());
                                ServidorBean Pesquisar = new ServidorBean();
                                ServidorDAO c = new ServidorDAO();
                                Pesquisar.setTipo(filapopular.primeiro().substring(0, 1));
                                Pesquisar.setNumeroFicha(Integer.parseInt(filapopular.primeiro().substring(1, 4)));
                                ResultSet rs = c.retriveficha(Pesquisar);
                                while (rs.next()) {
                                    idAtendimento = rs.getInt("cod");
                                    date_impressao = rs.getTimestamp("data_hora_impre");
                                }
                                ServidorBean Atualizar = new ServidorBean();
                                Atualizar.setCodigo(idAtendimento);
                                Atualizar.setIdcaixa(message.getName());
                                try {
                                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");//formata a data e hora atual                                    
                                    java.util.Date minhaData;//cria a minha data e hora atual                                   
                                    minhaData = dateFormat.parse(getDateTime()); //minha data  recebe a data e hora                                     
                                    java.sql.Timestamp sqlDate = new java.sql.Timestamp(minhaData.getTime()); // coloca pra timestamp do banco
                                    LocalTime start = new LocalTime(date_impressao);// pesquisar data no banco e por aqui
                                    LocalTime end = new LocalTime(sqlDate);
                                    Period period = new Period(start, end);
                                    java.util.Date resul;
                                    resul = dateFormat.parse(getDate() + " " + period.getHours() + ":" + period.getMinutes() + ":" + period.getSeconds());
                                    java.sql.Timestamp sqlDate2 = new java.sql.Timestamp(resul.getTime()); // coloca pra timestamp do banco
                                    Atualizar.setTempoEspera(sqlDate2); // calcula o tempo de espera 
                                    Atualizar.setDataHoraIni(sqlDate); //atualiz a a hora que iniciou o atendimento
                                    Atualizar.setAtendimentoIniciado(true);
                                    Atualizar.setAtendimentoFinalizado(false);
                                    ServidorDAO atualiza = new ServidorDAO();
                                    atualiza.update(Atualizar);
                                    message.setStatus("yes");
                                    message.setAtual(filapopular.primeiro());
                                    message.setUltima(ultima);
                                    message.setPenultima(penultima);
                                    message.setAntepenultima(antepenultima);
                                    filapopular.remover();
                                    atualizarPainel(message);
                                    tes = antepenultima;
                                    antepenultima = penultima;
                                    penultima = ultima;
                                    ultima = message.getAtual();
                                } catch (ParseException ex) {

                                }
                            } catch (SQLException ex) {

                            }
                        }
                    } catch (SQLException ex) {

                    }
                } else {

                    try {
                        //se vazia filaa Pesquisa se tem ficha em aberto para aquele caixa
                        ServidorBean PesquisarInicio = new ServidorBean();
                        ServidorDAO ini = new ServidorDAO();
                        PesquisarInicio.setIdcaixa(message.getName());
                        PesquisarInicio.setAtendimentoIniciado(true);
                        PesquisarInicio.setAtendimentoFinalizado(false);
                        ResultSet rsi;

                        rsi = ini.retrivefichaAberta(PesquisarInicio);

                        while (rsi.next()) {
                            idAtendimento = rsi.getInt("cod");
                            tipo = rsi.getString("tipo");
                            numero = rsi.getInt("numero");
                            numero = Integer.parseInt(String.format("%03d", numero));
                            message.setIdFinalizar(idAtendimento);
                            finalizar(message);
                            finalizou2 = true;
                        }
                    } catch (SQLException ex) {

                    }
                    message.setStatus("no");
                    message.setAtual(ultima);
                    message.setUltima(penultima);
                    message.setPenultima(antepenultima);
                    message.setAntepenultima(tes);
                    atualizarPainel(message);
                }
            }
        }
    }

    void finalizar(Mensagem message) {
        message.setStatus("finalizar");
        message.setAtual(ultima);
        message.setUltima(penultima);
        message.setPenultima(antepenultima);
        message.setAntepenultima(tes);
        message.setTipo(tipo);
        message.setNumero(numero);
        atualizarPainel(message);

    }

    void finalizacao(Mensagem message) {
        if (message.getTipo().equals("C")) {
            if (message.getStatus().equals("yes")) {
                ServidorBean AtualizarFinalizar = new ServidorBean();
                AtualizarFinalizar.setCodigo(message.getIdFinalizar());
                AtualizarFinalizar.setIdcaixa(message.getName());
                try {
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    java.util.Date minhaData1;
                    minhaData1 = dateFormat.parse(getDateTime());
                    java.sql.Timestamp sqlDate = new java.sql.Timestamp(minhaData1.getTime());
                    ServidorBean AtualizarFinalizar1 = new ServidorBean();
                    ServidorDAO c = new ServidorDAO();
                    AtualizarFinalizar1.setTipo(message.getTipo());
                    AtualizarFinalizar1.setNumeroFicha(message.getNumero());
                    ResultSet rs = c.retriveficha(AtualizarFinalizar1);
                    while (rs.next()) {
                        date_fim = rs.getTimestamp("data_hora_impre");
                    }
                    LocalTime start = new LocalTime(date_fim);// pesquisar data no banco e por aqui
                    LocalTime end = new LocalTime(sqlDate);
                    Period period = new Period(start, end);
                    java.util.Date resul;
                    resul = dateFormat.parse(getDate() + " " + period.getHours() + ":" + period.getMinutes() + ":" + period.getSeconds());
                    java.sql.Timestamp sqlDate2 = new java.sql.Timestamp(resul.getTime()); // coloca pra timestamp do banco
                    AtualizarFinalizar.setTempoAtendimento(sqlDate2);
                    AtualizarFinalizar.setDataHoraFim(sqlDate);
                    AtualizarFinalizar.setAtendimentoIniciado(true);
                    AtualizarFinalizar.setAtendimentoFinalizado(true);
                    AtualizarFinalizar.setEstouroAtendimento("Finalizado");
                    ServidorDAO atualiza = new ServidorDAO();
                    atualiza.updatefinalizar(AtualizarFinalizar);
                    finalizou = true;
                } catch (SQLException ex) {

                } catch (ParseException ex) {

                }
            }
            if (message.getStatus().equals("no")) {
                finalizou = true;
            }
        }

        if (message.getTipo().equals("P")) {
            if (message.getStatus().equals("yes")) {
                ServidorBean AtualizarFinalizar = new ServidorBean();
                AtualizarFinalizar.setCodigo(message.getIdFinalizar());
                AtualizarFinalizar.setIdcaixa(message.getName());
                try {
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    java.util.Date minhaData1;
                    minhaData1 = dateFormat.parse(getDateTime());
                    java.sql.Timestamp sqlDate = new java.sql.Timestamp(minhaData1.getTime());

                    ServidorBean AtualizarFinalizar2 = new ServidorBean();
                    ServidorDAO p = new ServidorDAO();
                    AtualizarFinalizar2.setTipo(message.getTipo());
                    AtualizarFinalizar2.setNumeroFicha(message.getNumero());
                    ResultSet rs = p.retriveficha(AtualizarFinalizar2);
                    while (rs.next()) {
                        date_fim = rs.getTimestamp("data_hora_impre");
                    }
                    LocalTime start = new LocalTime(date_fim);// pesquisar data no banco e por aqui
                    LocalTime end = new LocalTime(sqlDate);
                    Period period = new Period(start, end);
                    java.util.Date resul;
                    resul = dateFormat.parse(getDate() + " " + period.getHours() + ":" + period.getMinutes() + ":" + period.getSeconds());
                    java.sql.Timestamp sqlDate2 = new java.sql.Timestamp(resul.getTime()); // coloca pra timestamp do banco          
                    AtualizarFinalizar.setDataHoraFim(sqlDate);
                    AtualizarFinalizar.setTempoAtendimento(sqlDate2);
                    AtualizarFinalizar.setAtendimentoIniciado(true);
                    AtualizarFinalizar.setAtendimentoFinalizado(true);
                    AtualizarFinalizar.setEstouroAtendimento("Finalizado");
                    ServidorDAO atualiza = new ServidorDAO();
                    atualiza.updatefinalizar(AtualizarFinalizar);
                    finalizou1 = true;
                } catch (SQLException ex) {

                } catch (ParseException ex) {

                }
            }
            if (message.getStatus().equals("no")) {
                finalizou1 = true;
            }
        }
        if (message.getTipo().equals("F")) {
            if (message.getStatus().equals("yes")) {
                ServidorBean AtualizarFinalizar = new ServidorBean();
                AtualizarFinalizar.setCodigo(message.getIdFinalizar());
                AtualizarFinalizar.setIdcaixa(message.getName());
                try {
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                    java.util.Date minhaData1;
                    minhaData1 = dateFormat.parse(getDateTime());
                    java.sql.Timestamp sqlDate = new java.sql.Timestamp(minhaData1.getTime());

                    ServidorBean AtualizarFinalizar3 = new ServidorBean();
                    ServidorDAO f = new ServidorDAO();
                    AtualizarFinalizar3.setTipo(message.getTipo());
                    AtualizarFinalizar3.setNumeroFicha(message.getNumero());
                    ResultSet rs = f.retriveficha(AtualizarFinalizar3);
                    while (rs.next()) {
                        date_fim = rs.getTimestamp("data_hora_impre");
                    }
                    LocalTime start = new LocalTime(date_fim);// pesquisar data no banco e por aqui
                    LocalTime end = new LocalTime(sqlDate);
                    Period period = new Period(start, end);
                    java.util.Date resul;
                    resul = dateFormat.parse(getDate() + " " + period.getHours() + ":" + period.getMinutes() + ":" + period.getSeconds());
                    java.sql.Timestamp sqlDate2 = new java.sql.Timestamp(resul.getTime()); // coloca pra timestamp do banco
                    AtualizarFinalizar.setDataHoraFim(sqlDate);
                    AtualizarFinalizar.setTempoAtendimento(sqlDate2);
                    AtualizarFinalizar.setAtendimentoIniciado(true);
                    AtualizarFinalizar.setAtendimentoFinalizado(true);
                    AtualizarFinalizar.setEstouroAtendimento("Finalizado");
                    ServidorDAO atualiza = new ServidorDAO();
                    atualiza.updatefinalizar(AtualizarFinalizar);//  
                    finalizou2 = true;
                } catch (SQLException ex) {

                } catch (ParseException ex) {

                }
            }
            if (message.getStatus().equals("no")) {
                finalizou2 = true;
            }

        }

    }

    private void imprime(String ficha) {

        new Thread() {
            @Override
            public void run() {
                pdf.criarpdf(ficha);
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                FileInputStream fis;
                try {
                    try {
                        Thread.currentThread().sleep(1000);

                    } catch (InterruptedException ex) {

                    }

                    fis = new FileInputStream("C:/SENHAS.pdf");
                    Imprimir printPDFFile = new Imprimir(fis, "SENHAS.pdf");
                    printPDFFile.print();
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                int teste;
                                ServidorBean Envios = new ServidorBean();
                                boolean Status = false;
                                Envios.setTipo(ficha.substring(0, 1));
                                teste = (Integer.parseInt(ficha.substring(1, 4)));
                                Envios.setNumeroFicha(Integer.parseInt(String.format("%03d", teste)));
                                Envios.setAtendimentoIniciado(Status);
                                Envios.setAtendimentoFinalizado(Status);
                                ServidorDAO enviar = new ServidorDAO();
                                enviar.create(Envios);
                            } catch (SQLException ex) {

                            }
                        }
                    }.start();

                } catch (FileNotFoundException ex) {

                } catch (IOException ex) {

                } catch (PrinterException ex) {
                    JOptionPane.showMessageDialog(null, "Problemas com a impressão! \n * Verifique se há impressora instalada! \n * Verifique os cabos da impressorea! \n Entre em contato com o suporte! ", "Inove Systems - Informação", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }.start();
    }

    private void imprimir(Mensagem message) {

        if (message.getText().equals("C")) {
            if (SenhaComum <= 998) {
                SenhaComum = SenhaComum + 1;
                String sc = String.format("%03d", SenhaComum);
                System.out.println("C" + sc);
                filacomum.adicionar("C" + sc);
                imprime("C" + sc);
            } else {
                SenhaComum = 0;
                String sc = String.format("%03d", SenhaComum);
                System.out.println("C" + sc);
                filacomum.adicionar("C" + sc);
                imprime("C" + sc);
            }
        } else {
            if (message.getText().equals("P")) {
                if (SenhaPrioritaria <= 998) {
                    SenhaPrioritaria = SenhaPrioritaria + 1;
                    String sp = String.format("%03d", SenhaPrioritaria);
                    System.out.println("P" + sp);
                    filaprioritaria.adicionar("P" + sp);
                    imprime("P" + sp);
                } else {
                    SenhaPrioritaria = 0;
                    String sp = String.format("%03d", SenhaPrioritaria);
                    System.out.println("P" + sp);
                    filaprioritaria.adicionar("P" + sp);
                    imprime("P" + sp);
                }
            } else {
                if (message.getText().equals("F")) {

                    if (SenhaPopular <= 998) {
                        SenhaPopular = SenhaPopular + 1;
                        String sf = String.format("%03d", SenhaPopular);
                        System.out.println("F" + sf);
                        filapopular.adicionar("F" + sf);
                        imprime("F" + sf);
                    } else {
                        SenhaPopular = 0;
                        String sf = String.format("%03d", SenhaPopular);
                        System.out.println("F" + sf);
                        filapopular.adicionar("F" + sf);
                        imprime("F" + sf);
                    }
                }
            }
        }
    }

    private boolean connect(Mensagem message, ObjectOutputStream output) {
        if (mapOnlines.size() == 0) {
            message.setText("YES");
            sendOne(message, output);
            return true;
        }
        if (mapOnlines.containsKey(message.getName())) {
            message.setText("NO");
            sendOne(message, output);
            return false;
        } else {
            message.setText("YES");
            sendOne(message, output);
            return true;
        }
    }

    private void disconnect(Mensagem message, ObjectOutputStream output) {
        mapOnlines.remove(message.getName());
        message.setAction(Action.SEND_ONE);
        System.out.println(message.getName() + " ::..  FECHADO!" + "\n");
    }

    private void sendOne(Mensagem message, ObjectOutputStream output) {
        try {
            output.writeObject(message);

        } catch (IOException ex) {

        }
    }

    private void sendAll(Mensagem message) {
        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            if (!kv.getKey().equals(message.getName())) {
                message.setAction(Action.SEND_ONE);
                try {
                    kv.getValue().writeObject(message);

                } catch (IOException ex) {

                }
            }
        }
    }

    private void sendOnlines() {
        Set<String> setNames = new HashSet<String>();
        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            setNames.add(kv.getKey());
        }
        Mensagem message = new Mensagem();
        message.setAction(Action.USERS_ONLINE);
        message.setSetOnlines(setNames);
        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            message.setName(kv.getKey());
            try {
                System.out.println("ATIVADO:" + kv.getKey());
                kv.getValue().writeObject(message);

            } catch (IOException ex) {

            }

        }
    }

    class FilaComum {

        int inicio;
        int fim;
        int tamanho;
        int qtdeElementos;
        String filaComum[];

        public FilaComum() {
            inicio = fim = -1;
            tamanho = 1000;
            filaComum = new String[tamanho];
            qtdeElementos = 0;
        }

        public String primeiro() {
            return filaComum[inicio];
        }

        public boolean estaVazia() {
            if (qtdeElementos == 0) {
                return true;
            }
            return false;
        }

        public boolean estaCheia() {
            if (qtdeElementos == tamanho - 1) {
                return true;
            }
            return false;
        }

        public void adicionar(String e) {
            if (!estaCheia()) {
                if (inicio == -1) {
                    inicio = 0;
                }
                fim++;
                filaComum[fim] = e;
                qtdeElementos++;
            }
        }

        public void remover() {
            if (!estaVazia()) {
                inicio++;
                qtdeElementos--;
            }
        }
    }

    class FilaPrioritaria {

        int inicio;
        int fim;
        int tamanho;
        int qtdeElementos;
        String filaPrioritaria[];

        public FilaPrioritaria() {
            inicio = fim = -1;
            tamanho = 1000;
            filaPrioritaria = new String[tamanho];
            qtdeElementos = 0;
        }

        public String primeiro() {
            return filaPrioritaria[inicio];
        }

        public boolean estaVazia() {
            if (qtdeElementos == 0) {
                return true;
            }
            return false;
        }

        public boolean estaCheia() {
            if (qtdeElementos == tamanho - 1) {
                return true;
            }
            return false;
        }

        public void adicionar(String e) {
            if (!estaCheia()) {
                if (inicio == -1) {
                    inicio = 0;
                }
                fim++;
                filaPrioritaria[fim] = e;
                qtdeElementos++;
            }
        }

        public void remover() {
            if (!estaVazia()) {
                inicio++;
                qtdeElementos--;
            }
        }
    }

    class FilaPopular {

        int inicio;
        int fim;
        int tamanho;
        int qtdeElementos;
        String filaPopular[];

        public FilaPopular() {
            inicio = fim = -1;
            tamanho = 1000;
            filaPopular = new String[tamanho];
            qtdeElementos = 0;
        }

        public String primeiro() {
            return filaPopular[inicio];
        }

        public boolean estaVazia() {
            if (qtdeElementos == 0) {
                return true;
            }
            return false;
        }

        public boolean estaCheia() {
            if (qtdeElementos == tamanho - 1) {
                return true;
            }
            return false;
        }

        public void adicionar(String e) {
            if (!estaCheia()) {
                if (inicio == -1) {
                    inicio = 0;
                }
                fim++;
                filaPopular[fim] = e;
                qtdeElementos++;
            }
        }

        public void remover() {
            if (!estaVazia()) {
                inicio++;
                qtdeElementos--;
            }
        }
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static void main(String args[]) {
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            trayIcon.setImageAutoSize(true);
            trayIcon.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    trayIcon.displayMessage("Inove Systems - www.inovesystems.com.br", "Servidor ONLINE!", TrayIcon.MessageType.INFO);
                }
            }
            );
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println("Erro ao adicionar tray");
            }
        }
        Server serv = new Server();
        serv.Conect();
    }
}
