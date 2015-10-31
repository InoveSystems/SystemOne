/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Telas;

import ComRede.Conexao;
import ComRede.Mensagem;
import XML.LeitorXml;
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
import java.net.SocketException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileSystemView;

public class PainelTouch extends javax.swing.JFrame {

    private Socket socket;
    private Mensagem message;
    private Conexao service;
    String caixa = "20";
    String add;
    LeitorXml leitor = new LeitorXml();
    int inicio;
    int fim;
    PainelTouch.FilaComum f1 = new PainelTouch.FilaComum();
    String teste = " ";
    String IPCom = "127.0.0.1";
    String diretorioUsuario = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
    File IPConfig = new File(diretorioUsuario + File.separator + "InoveSystems" + File.separator + "Config" + File.separator + "IPConfig.txt");
    File arquivo = new File(diretorioUsuario + File.separator + "InoveSystems" + File.separator + "Config" + File.separator + "PainelConfig.txt");

    public PainelTouch() {

        //setExtendedState(MAXIMIZED_BOTH);
        //setUndecorated(true);
        //Image cursorImage = Toolkit.getDefaultToolkit().getImage("xparent.gif");
        //Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "");
        //setCursor(blankCursor);
        initComponents();
        new Thread() {
            @Override
            public void run() {
                try {

                    ImagemAguarde.setEnabled(false);
                    ImagemAguarde.setVisible(false);
//                    MenImp.setEnabled(false);
//                    MenImp.setVisible(false);
                } catch (NullPointerException ex) {

                }
            }
        }.start();
        f1.adicionar();

        //lendo ou criando arquivo com o ip do servidor
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
                            statuslabel.setText("Erro ao criar IPConfig!");
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
                    statuslabel.setText("Erro! IPConfig não encontrado!");
                } catch (IOException ex) {

                }

            }
        }.
                start();

        new Thread() {
            @Override
            public void run() {
                FileReader fr;
                try {
                    if (!arquivo.exists()) {
                        try {
                            arquivo.createNewFile();
                            FileWriter fw = new FileWriter(arquivo, false);
                            BufferedWriter bw = new BufferedWriter(fw);
                            bw.write(caixa);
                            bw.newLine();
                            bw.close();
                            fw.close();
                        } catch (IOException ex) {
                            statuslabel.setText("Erro ao ler Configurações!");
                        }
                    } else {
                        fr = new FileReader(arquivo);
                        BufferedReader br = new BufferedReader(fr);
                        while (br.ready()) {
                            String linha = br.readLine();
                            caixa = linha;
                        }
                        br.close();
                        fr.close();
                    }
                } catch (FileNotFoundException ex) {
                    statuslabel.setText("Arquivo de config. não encontrado!");
                } catch (IOException ex) {
                    statuslabel.setText("Erro ao ler Configurações!");
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
                statuslabel.setText("Erro ao conectar ao servidor!");
                try {
                    Thread.currentThread().sleep(5000);
                    statuslabel.setText("Tentando conectar novamente ...");
                    Thread.currentThread().sleep(5000);
                    new Thread() {
                        @Override
                        public void run() {
                            statuslabel.setText("Tentando conectar novamente ...");
                            ConectarServidor();
                        }
                    }.start();
                } catch (InterruptedException ex1) {
                    Logger.getLogger(PainelTouch.class.getName()).log(Level.SEVERE, null, ex1);
                }

            } catch (IOException ex) {

                Logger.getLogger(PainelTouch.class.getName()).log(Level.SEVERE, null, ex);
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
                        socket.close();
                    } else if (action.equals(action.SEND_ONE)) {
                        receive(message);
                    } else if (action.equals(action.USERS_ONLINE)) {
                        refreshOnlines(message);
                    }

                }
            } catch (NullPointerException ex) {

            } catch (IOException ex) {
                statuslabel.setText("Erro ao conectar ao servidor!");
                try {
                    Thread.currentThread().sleep(5000);
                    statuslabel.setText("Tentando conectar novamente ...");
                    Thread.currentThread().sleep(5000);
                    new Thread() {
                        @Override
                        public void run() {
                            statuslabel.setText("Tentando conectar novamente ...");
                            ConectarServidor();
                        }
                    }.start();
                } catch (InterruptedException ex1) {
                    Logger.getLogger(PainelTouch.class.getName()).log(Level.SEVERE, null, ex1);
                }
                return;
//                
            } catch (ClassNotFoundException ex) {

                Logger.getLogger(PainelTouch.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void connected(Mensagem message) {
        if (message.getText().equals("NO")) {
            statuslabel.setText("Erro Grave! Contate o Suporte!");
            try {
                Thread.currentThread().sleep(3000);
                statuslabel.setText("O sistema sera fechado!");
                Thread.currentThread().sleep(3000);
                this.message.setAction(Mensagem.Action.DISCONNECT);

                this.service.send(this.message);
                System.exit(0);
            } catch (InterruptedException ex) {
                Logger.getLogger(PainelTouch.class.getName()).log(Level.SEVERE, null, ex);
            }

            return;
        }
        this.message = message;
        statuslabel.setText("Conexão realizada com sucesso!");
    }

    private void disconnected() {
        try {
            socket.close();
        } catch (IOException ex) {
            statuslabel.setText("Erro ao sair! Tente mais tarde.");
        } catch (NullPointerException ex) {

        }
    }

    private void receive(Mensagem message) {

        String text;
        if (message.getStatus().equals("erro_impressao")) {
            if (message.getName().equals(caixa)) {
                int resposta = JOptionPane.showConfirmDialog(null, "Erro de Impressão! Verifique o Painel de Impressão! \n" + "Tudo pronto? \n" + "Deseja reimprimir a ficha de atendimento?");

                if (resposta == JOptionPane.YES_OPTION) {
                    text = "yes";
                    if (!text.isEmpty()) {
                        message.setName(message.getName());
                        message.setText(text);
                        message.setIdFinalizar(message.getIdFinalizar());
                        message.setStatus("yes");
                        message.setAction(Mensagem.Action.REIMPRIMIR);
                        service.send(message);
                    }
                }

                if (resposta == JOptionPane.NO_OPTION) {
                    text = "no";
                    if (!text.isEmpty()) {
                        message.setName(message.getName());
                        message.setText(text);
                        message.setIdFinalizar(message.getIdFinalizar());
                        message.setStatus("no");
                        message.setAction(Mensagem.Action.REIMPRIMIR);
                        service.send(message);
                    }
                }

                if (resposta == JOptionPane.CANCEL_OPTION) {
                    text = "no";
                    if (!text.isEmpty()) {
                        message.setName(message.getName());
                        message.setText(text);
                        message.setIdFinalizar(message.getIdFinalizar());
                        message.setStatus("no");
                        message.setAction(Mensagem.Action.REIMPRIMIR);
                        service.send(message);
                    }
                }

                if (resposta == JOptionPane.CLOSED_OPTION) {
                    text = "no";
                    if (!text.isEmpty()) {
                        message.setName(message.getName());
                        message.setText(text);
                        message.setIdFinalizar(message.getIdFinalizar());
                        message.setStatus("no");
                        message.setAction(Mensagem.Action.REIMPRIMIR);
                        service.send(message);
                    }
                }

            }

        }

    }

    private void refreshOnlines(Mensagem message) {
        System.out.println(message.getSetOnlines().toString());
        Set<String> names = message.getSetOnlines();
        String[] array = (String[]) names.toArray(new String[names.size()]);

    }

    public boolean ConectarServidor() {
//        String name = "20";
        if (!caixa.isEmpty()) {
            message = new Mensagem();
            this.message.setAction(Mensagem.Action.CONNECT);
            this.message.setName(caixa);
            this.service = new Conexao();
            this.socket = this.service.connect(IPCom);
            new Thread(new PainelTouch.ListenerSocket(this.socket)).start();
            this.service.send(message);

        }
        return true;
    }

    class FilaComum {

        int inicio;
        int fim;
        int tamanho;
        int qtdeElementos;
        String filaComum[];

        public FilaComum() {
            add = leitor.adicionar();
            inicio = fim = -1;
            tamanho = add.length();
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

        public void adicionar() {
            filaComum = new String[tamanho];
            new Thread() {
                @Override
                public void run() {

                    if (!estaCheia()) {
                        if (inicio == -1) {
                            inicio = 0;
                        }
                        for (int i = 0; i < add.length(); i++) {
                            filaComum[i] = String.valueOf(add.charAt(i));
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

                    teste = teste + filaComum[i];

                }
                inicio++;
                qtdeElementos--;

                if (inicio == 1) {
                    try {
                        TextExemplo.setText("            ..:: 3D - Soluções Tecnológicas ::..");
                        Thread.currentThread().sleep(5000);
                        TextExemplo.setText("     TELEFONE: (53) 32481203 - Pinheiro Machado-RS");
                        Thread.currentThread().sleep(5000);
                        TextExemplo.setText(teste);
                        Thread.currentThread().sleep(1000);
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
                inicio = 0;
                fim = 0;
                tamanho = 0;
                for (int i = 0; i < add.length(); i++) {
                    filaComum[i] = "";
                }
                new Thread() {
                    @Override
                    public void run() {
                        f1.adicionar();
                    }
                }.start();
            }
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TextExemplo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        ImagemAguarde = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        statuslabel = new javax.swing.JLabel();
        jConfig = new javax.swing.JLabel();
        jConfigIP = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("3D - Soluções Tecnológicas - Cliente");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TextExemplo.setFont(new java.awt.Font("Franklin Gothic Book", 1, 48)); // NOI18N
        TextExemplo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TextExemplo.setAutoscrolls(true);
        TextExemplo.setDoubleBuffered(true);
        TextExemplo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(TextExemplo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 920, 1340, 66));

        jLabel2.setText("  ");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel2MousePressed(evt);
            }
        });
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 720, 200));

        ImagemAguarde.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        ImagemAguarde.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Aguarde.png"))); // NOI18N
        getContentPane().add(ImagemAguarde, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 430, 790, 170));

        jLabel1.setText("  ");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel1MousePressed(evt);
            }
        });
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 380, 720, 200));

        jLabel3.setText("  ");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel3MousePressed(evt);
            }
        });
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(124, 634, 730, 200));

        statuslabel.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        statuslabel.setForeground(new java.awt.Color(204, 204, 204));
        statuslabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statuslabel.setText("Conectando...");
        getContentPane().add(statuslabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 120, 260, 50));

        jConfig.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jConfigMouseClicked(evt);
            }
        });
        getContentPane().add(jConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 10, 280, 80));

        jConfigIP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jConfigIP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Setings 32.png"))); // NOI18N
        jConfigIP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jConfigIPMouseClicked(evt);
            }
        });
        getContentPane().add(jConfigIP, new org.netbeans.lib.awtextra.AbsoluteConstraints(1240, 124, 40, 40));

        jLabel17.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Fundo.png"))); // NOI18N
        jLabel17.setToolTipText("");
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 1024));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MousePressed
        new Thread() {
            @Override
            public void run() {
                jLabel2.setEnabled(false);
                ImagemAguarde.setEnabled(true);
                ImagemAguarde.setVisible(true);
            }
        }.start();
        String text = "F";
        String name = this.message.getName();
        if (!text.isEmpty()) {
            this.message = new Mensagem();
            this.message.setName(name);
            this.message.setText(text);
            this.message.setAction(Mensagem.Action.PRINT);
            this.service.send(this.message);
        }

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(4000);
                    jLabel2.setEnabled(true);
                    ImagemAguarde.setEnabled(false);
                    ImagemAguarde.setVisible(false);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PainelTouch.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }.start();

    }//GEN-LAST:event_jLabel2MousePressed

    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed
        new Thread() {
            @Override
            public void run() {
                jLabel1.setEnabled(false);
                ImagemAguarde.setEnabled(true);
                ImagemAguarde.setVisible(true);
            }
        }.start();
        String text = "C";
        String name = this.message.getName();
        if (!text.isEmpty()) {
            this.message = new Mensagem();
            this.message.setName(name);
            this.message.setText(text);
            this.message.setAction(Mensagem.Action.PRINT);
            this.service.send(this.message);
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(4000);
                    jLabel1.setEnabled(true);
                    ImagemAguarde.setEnabled(false);
                    ImagemAguarde.setVisible(false);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PainelTouch.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }.start();
    }//GEN-LAST:event_jLabel1MousePressed

    private void jLabel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MousePressed
        new Thread() {
            @Override
            public void run() {
                jLabel3.setEnabled(false);
                ImagemAguarde.setEnabled(true);
                ImagemAguarde.setVisible(true);
            }
        }.start();
        String text = "P";
        String name = this.message.getName();
        if (!text.isEmpty()) {
            this.message = new Mensagem();
            this.message.setName(name);
            this.message.setText(text);
            this.message.setAction(Mensagem.Action.PRINT);
            this.service.send(this.message);
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(4000);
                    jLabel3.setEnabled(true);
                    ImagemAguarde.setEnabled(false);
                    ImagemAguarde.setVisible(false);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PainelTouch.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }.start();
    }//GEN-LAST:event_jLabel3MousePressed

    private void jConfigMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jConfigMouseClicked
        String caixaProb;
        int resposta = 0;
        caixaProb = JOptionPane.showInputDialog(null, "QUAL O NÚMERO DE SEU PAINEL ?", "3D Soluções Tecnológicas - Configuração", 3);
        try {
            if ((!caixaProb.equals(null)) && (!caixaProb.equals(""))) {
                resposta = JOptionPane.showConfirmDialog(null, "CERTIFIQUE-SE QUE ESTE PAINEL NÃO EXISTE NA REDE! \n" + "O SEU PAINEL É O NÚMERO " + caixaProb + " ?");
                if (resposta == JOptionPane.YES_OPTION) {
                    caixa = caixaProb;
                    JOptionPane.showMessageDialog(null, "PAINEL " + caixa + " CRIADO COM SUCESSO!", "3D Soluções Tecnológicas - Informação", 1);
                } else {

                }
            } else {
                do {
                    caixaProb = JOptionPane.showInputDialog(null, "ERRO GRAVE! PAINEL INVALIDO! \nQUAL O NÚMERO DE SEU PAINEL ?", "3D Soluções Tecnológicas - Configuração", 3);
                } while ((caixaProb.equals(null)) || (caixaProb.equals("")));
                resposta = JOptionPane.showConfirmDialog(null, "CERTIFIQUE-SE QUE ESTE PAINEL NÃO EXISTE NA REDE! \n" + "O SEU PAINEL É O NÚMERO " + caixaProb + " ?");
                if (resposta == JOptionPane.YES_OPTION) {
                    caixa = caixaProb;
                    JOptionPane.showMessageDialog(null, "PAINEL " + caixa + " CRIADO COM SUCESSO!", "3D Soluções Tecnológicas - Informação", 1);
                }
            }
        } catch (NullPointerException ex) {

        }
        new Thread() {
            @Override
            public void run() {
                try {
                    if (!arquivo.exists()) {
                        try {
                            arquivo.createNewFile();
                        } catch (IOException ex) {
                            statuslabel.setText("Erro ao criar CaixaConfig!");
                        }
                    }
                    FileWriter fw;
                    try {
                        fw = new FileWriter(arquivo, false);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write(caixa);
                        bw.newLine();
                        bw.close();
                        fw.close();

                    } catch (IOException ex) {
                        statuslabel.setText("Erro ao ler CaixaConfig!");
                    }

                } catch (NullPointerException ex) {

                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                disconnected();
            }
        }.start();
    }//GEN-LAST:event_jConfigMouseClicked

    private void jConfigIPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jConfigIPMouseClicked
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
                            JOptionPane.showMessageDialog(null, " erro ao criar ", "3D Soluções Tecnológicas - Informação", 1);

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
                        JOptionPane.showMessageDialog(null, " erro ao ler", "3D Soluções Tecnológicas - Informação", 1);
                        statuslabel.setText("Erro ao ler IPConfig!");
                    }
                } catch (NullPointerException ex) {
                    statuslabel.setText("Erro ao ler IPConfig!");
                }
            }
        }.start();
    }//GEN-LAST:event_jConfigIPMouseClicked

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel17MouseClicked

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
            java.util.logging.Logger.getLogger(PainelTouch.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PainelTouch.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PainelTouch.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PainelTouch.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                new PainelTouch().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel ImagemAguarde;
    private javax.swing.JLabel TextExemplo;
    private javax.swing.JLabel jConfig;
    private javax.swing.JLabel jConfigIP;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    public javax.swing.JLabel statuslabel;
    // End of variables declaration//GEN-END:variables
}
