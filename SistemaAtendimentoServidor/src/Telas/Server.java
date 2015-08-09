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
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
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
                    }

                }
            } catch (IOException ex) {
                Mensagem cm = new Mensagem();
                cm.setName(message.getName());
                disconnect(cm, output);
                sendOnlines();

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void atualizarPainel(Mensagem message) {
        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            message.setAction(Action.SEND_ONE);
            try {
                kv.getValue().writeObject(message);
            } catch (IOException ex) {
                Logger.getLogger(Server.class
                        .getName()).log(Level.SEVERE, null, ex);
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

        if (message.getText().equals("convencional")) {
            if (filaprioritaria.estaVazia()) {
                if (filacomum.estaVazia() == false) {
                    System.out.println("chamando: " + filacomum.primeiro());
                    message.setAtual(filacomum.primeiro());
                    message.setUltima(ultima);
                    message.setPenultima(penultima);
                    message.setAntepenultima(antepenultima);
                    filacomum.remover();
                    atualizarPainel(message);
                    antepenultima = penultima;
                    penultima = ultima;
                    ultima = message.getAtual();
                }
            } else {
                if (filaprioritaria.estaVazia() == false) {
                    System.out.println("chamando: " + filaprioritaria.primeiro());
                    message.setAtual(filaprioritaria.primeiro());
                    message.setUltima(ultima);
                    message.setPenultima(penultima);
                    message.setAntepenultima(antepenultima);
                    filaprioritaria.remover();
                    atualizarPainel(message);
                    antepenultima = penultima;
                    penultima = ultima;
                    ultima = message.getAtual();
                }
            }
        } else {
            if (message.getText().equals("popular")) {
                if (filapopular.estaVazia() == false) {
                    System.out.println("chamando: " + filapopular.primeiro());
                    message.setAtual(filapopular.primeiro());
                    message.setUltima(ultima);
                    message.setPenultima(penultima);
                    message.setAntepenultima(antepenultima);
                    filapopular.remover();
                    atualizarPainel(message);
                    antepenultima = penultima;
                    penultima = ultima;
                    ultima = message.getAtual();
                }
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
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    fis = new FileInputStream("C:/SENHAS.pdf");
                    Imprimir printPDFFile = new Imprimir(fis, "SENHAS.pdf");
                    printPDFFile.print();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                } catch (PrinterException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
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
        //sendAll(message);
        System.out.println(message.getName() + " ::..  FECHADO!" + "\n");
    }

    private void sendOne(Mensagem message, ObjectOutputStream output) {
        try {
            output.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(Server.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendAll(Mensagem message) {
        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            if (!kv.getKey().equals(message.getName())) {
                message.setAction(Action.SEND_ONE);
                try {
                    kv.getValue().writeObject(message);
                } catch (IOException ex) {
                    Logger.getLogger(Server.class
                            .getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(Server.class
                        .getName()).log(Level.SEVERE, null, ex);
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
