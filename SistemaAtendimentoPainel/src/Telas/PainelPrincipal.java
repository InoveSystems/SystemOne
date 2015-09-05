/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Telas;

import ComRede.Conexao;
import ComRede.Mensagem;
import XML.LeitorXml;
import XML.LeitorXmlTemp1;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author EngComp
 */
public class PainelPrincipal extends javax.swing.JFrame {

    File IPConfig = new File(getClass().getResource("/Config/IPConfig.txt").getFile());
    private Socket socket;
    private Mensagem message;
    private Conexao service;
    String caixa = "Painel Principal";
    String add;
    LeitorXml leitor = new LeitorXml();
    LeitorXmlTemp1 temperatura = new LeitorXmlTemp1();
    int inicio;
    int fim;
    PainelPrincipal.Fila f1 = new PainelPrincipal.Fila();
    String teste = " ";
    String IPCom = "127.0.0.1";

    /**
     * Creates new form PainelPrincipal
     */
    public PainelPrincipal() {

        setExtendedState(MAXIMIZED_BOTH);
        setUndecorated(true);
        initComponents();
        f1.adicionar();
        Image cursorImage = Toolkit.getDefaultToolkit().getImage("xparent.gif");
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "");
        setCursor(blankCursor);
        DateValue.setText(getDateTime());

        new Thread() {
            @Override
            public void run() {
                FileReader fr;
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

                } catch (IOException ex) {

                }
                new Thread() {
                    @Override
                    public void run() {
                        ConectarServidor();
                    }
                }.start();
            }
        }.
                start();

    }

    private class ListenerSocket implements Runnable {

        private ObjectInputStream input;

        public ListenerSocket(Socket socket) {
            try {
                this.input = new ObjectInputStream(socket.getInputStream());
            } catch (NullPointerException ex) {
                System.out.println("Erro ao conectar ao servidor!");
                //   statuslabel.setText("Erro ao conectar ao servidor!");
                try {
                    Thread.currentThread().sleep(5000);
                    System.out.println("Tentando conectar novamente ...");
                    //statuslabel.setText("Tentando conectar novamente ...");
                    Thread.currentThread().sleep(5000);
                    new Thread() {
                        @Override
                        public void run() {
                            // statuslabel.setText("Tentando conectar novamente ...");
                            ConectarServidor();
                        }
                    }.start();
                } catch (InterruptedException ex1) {
                    Logger.getLogger(PainelPrincipal.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } catch (IOException ex) {
                Logger.getLogger(PainelPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {
            Mensagem message = null;
            try {
                while ((message = (Mensagem) input.readObject()) != null) {
                    Mensagem.Action action = message.getAction();
                    if (action.equals(action.CONNECT)) {
                        connected(message);
                    } else if (action.equals(action.DISCONNECT)) {
                        disconnected();
//                        socket.close();
                    } else if (action.equals(action.SEND_ONE)) {
                        receive(message);
                    } else if (action.equals(action.USERS_ONLINE)) {
                        refreshOnlines(message);
                    }

                }
            } catch (NullPointerException ex) {

            } catch (IOException ex) {
                System.out.println("Erro ao conectar ao servidor!");
                try {
                    Thread.currentThread().sleep(5000);
                    System.out.println("Tentando conectar novamente ...");
                    Thread.currentThread().sleep(5000);
                    new Thread() {
                        @Override
                        public void run() {
                            //          statuslabel.setText("Tentando conectar novamente ...");
                            ConectarServidor();
                        }
                    }.start();
                } catch (InterruptedException ex1) {
                    Logger.getLogger(PainelPrincipal.class.getName()).log(Level.SEVERE, null, ex1);
                }
                return;
//                
            } catch (ClassNotFoundException ex) {

                Logger.getLogger(PainelPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void connected(Mensagem message) {
        if (message.getText().equals("NO")) {
            System.out.println("Erro Grave! Contate o Suporte!");
            //statuslabel.setText("Erro Grave! Contate o Suporte!");
            try {
                Thread.currentThread().sleep(3000);
                System.out.println("O sistema sera fechado!");
                //      statuslabel.setText("O sistema sera fechado!");
                Thread.currentThread().sleep(3000);
                this.message.setAction(Mensagem.Action.DISCONNECT);

                this.service.send(this.message);
                System.exit(0);
            } catch (InterruptedException ex) {
                Logger.getLogger(PainelPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }

            return;
        }
        this.message = message;
        // statuslabel.setText("Conexão realizada com sucesso!");
    }

    private void disconnected() {
        try {
            socket.close();
        } catch (IOException ex) {

        }
    }

    private void receive(Mensagem message) {
        if (message.getStatus().equals("yes")) {
            idlabel.setText("");
            atuallabel.setText("");
            ultimalabel.setText("");
            penultimalabel.setText("");
            antepenultimalabel.setText("");
            idlabel.setText("Atendente " + message.getName());
            atuallabel.setText(message.getAtual());
            ultimalabel.setText(message.getUltima());
            penultimalabel.setText(message.getPenultima());
            antepenultimalabel.setText(message.getAntepenultima());
            new Thread() {
                @Override
                public void run() {
                    audio();
                }
            }.start();
        } else {

        }

    }

    private void refreshOnlines(Mensagem message) {
        System.out.println(message.getSetOnlines().toString());
        Set<String> names = message.getSetOnlines();
        String[] array = (String[]) names.toArray(new String[names.size()]);
//        this.listOnlines.setListData(array);
//        this.listOnlines.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        this.listOnlines.setLayoutOrientation(JList.VERTICAL);
    }

    public boolean ConectarServidor() {
//        String name = "20";
        if (!caixa.isEmpty()) {
            message = new Mensagem();
            this.message.setAction(Mensagem.Action.CONNECT);
            this.message.setName(caixa);
            this.service = new Conexao();
            this.socket = this.service.connect(IPCom);
            new Thread(new PainelPrincipal.ListenerSocket(this.socket)).start();
            this.service.send(message);
        }
        return true;
    }

    class Fila {

        int inicio;
        int fim;
        int tamanho;
        int qtdeElementos;
        String fila[];

        public Fila() {
            add = leitor.adicionar();
            inicio = fim = -1;
            tamanho = add.length();
//            fila = new String[tamanho];
            qtdeElementos = 0;
        }

        public String primeiro() {
            return fila[inicio];
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

        public void adicionar() {
            fila = new String[tamanho];
            new Thread() {
                @Override
                public void run() {

            if (!estaCheia()) {
                if (inicio == -1) {
                    inicio = 0;
                }
//                filaComum[fim] = e;
                System.out.println(add.length());
                for (int i = 0; i < add.length(); i++) {
                    fila[i] = String.valueOf(add.charAt(i));
                    fim++;
                    qtdeElementos++;
                }

            }

            new Thread() {
                @Override
                public void run() {
                    f1.remover();
                }
            }.start();
            }
            }.start();

        }

        public void remover() {

            while (!estaVazia()) {
                teste = "";
                for (int i = inicio; i < fim; i++) {
                    teste = teste + fila[i];
                }
                inicio++;
                qtdeElementos--;
                if (inicio == 1) {
                    try {
                        new Thread() {
                            @Override
                            public void run() {
                                TempValue.setText(temperatura.adicionar());
                            }
                        }.start();
                        TextExemplo.setText("                              ..:: INOVE SYSTEMS ::..");
                        Thread.currentThread().sleep(5000);
                        TextExemplo.setText("      TELEFONE: (53) 32481203 - Pinheiro Machado-RS");
                        Thread.currentThread().sleep(5000);
                        TextExemplo.setText("                         www.inovesystems.com.br");
                        Thread.currentThread().sleep(5000);

                        new Thread() {
                            @Override
                            public void run() {
                                System.gc();
                                add = "";
                                add = leitor.adicionar();
                                TextExemplo.setText(teste);
                            }
                        }.start();
                        Thread.currentThread().sleep(100);
                    } catch (InterruptedException ex) {

                    }
                }
                TextExemplo.setText("");
                TextExemplo.setText(teste);

                try {
                    Thread.currentThread().sleep(150);
                } catch (InterruptedException ex) {

                }
            }
            if (estaVazia()) {
                try {
                    inicio = 0;
                    fim = 0;
                    tamanho = 0;

                    for (int i = 0; i < add.length(); i++) {
                        fila[i] = "";
                    }
                    new Thread() {
                        @Override
                        public void run() {
                            tamanho = add.length();
                            f1.adicionar();
                        }
                    }.start();
                } catch (ArrayIndexOutOfBoundsException ex) {
                    new Thread() {
                        @Override
                        public void run() {
                            for (int i = 0; i < fila.length; i++) {
                                fila[i] = "";
                            }
                            tamanho = add.length();
                            f1.adicionar();
                        }
                    }.start();
                }
            }
        }

    }

    private void audio() {
        try {
            java.io.InputStream in = (java.io.InputStream) (new java.io.FileInputStream("C:/SistemaAtendimentoPainel/sounds/Atenção.mp3"));
            javazoom.jl.player.Player p = new javazoom.jl.player.Player(in);
            p.play();
        } catch (FileNotFoundException | JavaLayerException e) {
        }
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ultimalabel = new javax.swing.JLabel();
        penultimalabel = new javax.swing.JLabel();
        antepenultimalabel = new javax.swing.JLabel();
        atuallabel = new javax.swing.JLabel();
        idlabel = new javax.swing.JLabel();
        TextExemplo = new javax.swing.JLabel();
        TempValue = new javax.swing.JLabel();
        DateValue = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        ultimalabel.setFont(new java.awt.Font("Arial", 1, 90)); // NOI18N
        ultimalabel.setForeground(new java.awt.Color(255, 255, 255));
        ultimalabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ultimalabel.setText("F000");
        getContentPane().add(ultimalabel);
        ultimalabel.setBounds(940, 830, 290, 90);

        penultimalabel.setFont(new java.awt.Font("Arial", 1, 90)); // NOI18N
        penultimalabel.setForeground(new java.awt.Color(255, 255, 255));
        penultimalabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        penultimalabel.setText("C000");
        getContentPane().add(penultimalabel);
        penultimalabel.setBounds(1270, 832, 290, 90);

        antepenultimalabel.setFont(new java.awt.Font("Arial", 1, 90)); // NOI18N
        antepenultimalabel.setForeground(new java.awt.Color(255, 255, 255));
        antepenultimalabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        antepenultimalabel.setText("C000");
        getContentPane().add(antepenultimalabel);
        antepenultimalabel.setBounds(1595, 832, 290, 90);

        atuallabel.setFont(new java.awt.Font("Arial", 1, 335)); // NOI18N
        atuallabel.setForeground(new java.awt.Color(255, 255, 255));
        atuallabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        atuallabel.setText("F000");
        getContentPane().add(atuallabel);
        atuallabel.setBounds(930, 310, 980, 410);

        idlabel.setFont(new java.awt.Font("Franklin Gothic Book", 1, 100)); // NOI18N
        idlabel.setForeground(new java.awt.Color(255, 255, 255));
        idlabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        idlabel.setText("Atendente 00");
        getContentPane().add(idlabel);
        idlabel.setBounds(940, 220, 940, 80);

        TextExemplo.setFont(new java.awt.Font("Franklin Gothic Book", 1, 70)); // NOI18N
        TextExemplo.setForeground(new java.awt.Color(255, 255, 255));
        TextExemplo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TextExemplo.setAutoscrolls(true);
        TextExemplo.setDoubleBuffered(true);
        TextExemplo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(TextExemplo);
        TextExemplo.setBounds(0, 970, 2050, 100);

        TempValue.setFont(new java.awt.Font("Arial", 1, 83)); // NOI18N
        TempValue.setForeground(new java.awt.Color(255, 255, 255));
        TempValue.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TempValue.setText("00 ºC");
        getContentPane().add(TempValue);
        TempValue.setBounds(580, 830, 310, 90);

        DateValue.setFont(new java.awt.Font("Arial", 1, 83)); // NOI18N
        DateValue.setForeground(new java.awt.Color(255, 255, 255));
        DateValue.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        DateValue.setText("28/06/2015");
        getContentPane().add(DateValue);
        DateValue.setBounds(40, 830, 530, 90);

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Principal.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 1920, 1080);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        String ipconexao;
        int resposta = 0;
        ipconexao = JOptionPane.showInputDialog(null, "QUAL O IP DO SERVIDOR?", "3D Soluções Tecnológicas - Configuração", 3);
        try {
            if ((!ipconexao.equals(null)) && (!ipconexao.equals(""))) {
                resposta = JOptionPane.showConfirmDialog(null, "CERTIFIQUE-SE QUE ESTE IP EXISTA NA REDE! \n" + "ESTE É O IP DO SERVIDOR " + ipconexao + " ?");
                if (resposta == JOptionPane.YES_OPTION) {
                    IPCom = ipconexao;
                    JOptionPane.showMessageDialog(null, "IP " + IPCom + " CONFIGURADO COM SUCESSO!", "3D Soluções Tecnológicas - Informação", 1);
                    disconnected();

                } else {

                }
            } else {
                do {
                    ipconexao = JOptionPane.showInputDialog(null, "ERRO GRAVE! IP INVALIDO! \nQUAL O IP DO SERVIDOR ?", "3D Soluções Tecnológicas - Configuração", 3);
                } while ((ipconexao.equals(null)) || (ipconexao.equals("")));
                resposta = JOptionPane.showConfirmDialog(null, "CERTIFIQUE-SE QUE ESTE IP EXISTA NA REDE! \n" + "ESTE É O IP DO SERVIDOR " + ipconexao + " ?");
                if (resposta == JOptionPane.YES_OPTION) {
                    IPCom = ipconexao;
                    JOptionPane.showMessageDialog(null, "IP " + IPCom + " CONFIGURADO COM SUCESSO!", "3D Soluções Tecnológicas - Informação", 1);
                    disconnected();
                }
            }
        } catch (NullPointerException ex) {
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    if (!IPConfig.exists()) {
                        try {
                            IPConfig.createNewFile();

                        } catch (IOException ex) {

                        }
                    }
                    FileWriter fw;
                    try {
                        fw = new FileWriter(IPConfig, false);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write(IPCom);
                        bw.newLine();
                        bw.close();
                        fw.close();

                    } catch (IOException ex) {

                    }

                } catch (NullPointerException ex) {
                }
            }
        }.start();
//        new Thread() {
//            @Override
//            public void run() {
//                message.setAction(Mensagem.Action.CONNECT);
//                message.setName(caixa);
//                service = new Conexao();
//                socket = service.connect(IPCom);
//                new Thread(
//                        new PainelPrincipal.ListenerSocket(socket)).start();
//                service.send(message);
//            }
//        }.start();
    }//GEN-LAST:event_jLabel1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PainelPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PainelPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PainelPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PainelPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PainelPrincipal().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel DateValue;
    private javax.swing.JLabel TempValue;
    private javax.swing.JLabel TextExemplo;
    private javax.swing.JLabel antepenultimalabel;
    private javax.swing.JLabel atuallabel;
    private javax.swing.JLabel idlabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel penultimalabel;
    private javax.swing.JLabel ultimalabel;
    // End of variables declaration//GEN-END:variables
}
