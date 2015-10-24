/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Telas;

import ComRede.Conexao;
import ComRede.Mensagem;
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
import java.net.URISyntaxException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileSystemView;

public class Cliente extends javax.swing.JFrame {

    private Socket socket;
    private Mensagem message;
    private Conexao service;
    String caixa = "20";
    //File arquivo = new File(getClass().getResource("/Config/CaixaConfig.txt").getFile());
    //File arquivo = new File("C://SistemaAtendimentoCliente/CaixaConfig.txt");
    boolean StatusMensage;
    String IPCom = "127.0.0.1";
    String diretorioUsuario = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
    File IPConfig = new File(diretorioUsuario + File.separator + "InoveSystems" + File.separator + "Config" + File.separator + "IPConfig.txt");
    File arquivo = new File(diretorioUsuario + File.separator + "InoveSystems" + File.separator + "Config" + File.separator + "CaixaConfig.txt");

    public Cliente() {
        initComponents();
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
                            //JOptionPane.showMessageDialog(null, " erro ao criar arquivo", "3D Soluções Tecnológicas - Informação", 1);
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
                    //JOptionPane.showMessageDialog(null, " erro arquivo nao encontrado ", "3D Soluções Tecnológicas - Informação", 1);
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
                            statuslabel.setText("Erro ao criar CaixaConfig!");
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
                        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Atendente " + caixa, javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N
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
                    statuslabel.setText("Erro ao conectar ao servidor!");
                }
            } catch (IOException ex) {
                statuslabel.setText("Erro ao conectar ao servidor!");
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
                    statuslabel.setText("Erro ao conectar ao servidor!");
                }
                return;
//                
            } catch (ClassNotFoundException ex) {
                statuslabel.setText("Erro ao conectar ao servidor!");
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
                statuslabel.setText("Erro ao fechar o sistema!");
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

        }
    }

    private void receive(Mensagem message) {
        String text;
        //atualiza vom a nova ficha
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
        }
        // avisa que nao tem clientes na fila
        if (message.getStatus().equals("no")) {
            if (message.getName().equals(caixa)) {
                JOptionPane.showMessageDialog(null, "Não á clientes a serem atendidos! \n        Verififique outras Filas!", "Inove Systems - Informação", JOptionPane.INFORMATION_MESSAGE);
            }
        }
//        //avisa que tem clientes a finalizar
        if (message.getStatus().equals("finalizar")) {

            if (message.getName().equals(caixa)) {
                int resposta = JOptionPane.showConfirmDialog(null, "VOCÊ DEVE FINALIZAR O ATENDIMENTO ANTERIOR! \n" + "DESEJA FINALIZAR O ATENDIMENTO  " + message.getTipo() + String.format("%03d", message.getNumero()) + " ?");

                if (resposta == JOptionPane.YES_OPTION) {
                    text = "yes";
                    if (!text.isEmpty()) {
                        message.setName(message.getName());
                        message.setText(text);
                        message.setIdFinalizar(message.getIdFinalizar());
                        message.setStatus("yes");
                        message.setAction(Mensagem.Action.FINALIZAR);
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
                        message.setAction(Mensagem.Action.FINALIZAR);
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
                        message.setAction(Mensagem.Action.FINALIZAR);
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
                        message.setAction(Mensagem.Action.FINALIZAR);
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

        if (!caixa.isEmpty()) {
            message = new Mensagem();
            this.message.setAction(Mensagem.Action.CONNECT);
            this.message.setName(caixa);
            this.service = new Conexao();
            this.socket = this.service.connect(IPCom);
            new Thread(new Cliente.ListenerSocket(this.socket)).start();
            this.service.send(message);
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jbConvencionalPrint = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jbPrioritariaPrint = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jbPopularPrint = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        jbConvencional = new javax.swing.JButton();
        jbPopular = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        atuallabel = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        ultimalabel = new javax.swing.JLabel();
        penultimalabel = new javax.swing.JLabel();
        antepenultimalabel = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        idlabel = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jPanel5 = new javax.swing.JPanel();
        jSeparator11 = new javax.swing.JSeparator();
        statuslabel = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jSeparator13 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("3D - Soluções Tecnológicas - Cliente");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/atendimento.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/sanar.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(26, 26, 26)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), " Imprimir ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        jbConvencionalPrint.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jbConvencionalPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/normal48.png"))); // NOI18N
        jbConvencionalPrint.setText("C");
        jbConvencionalPrint.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jbConvencionalPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbConvencionalPrintActionPerformed(evt);
            }
        });

        jSeparator4.setToolTipText("");
        jSeparator4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Convencional");

        jbPrioritariaPrint.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jbPrioritariaPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/idoso48.png"))); // NOI18N
        jbPrioritariaPrint.setText("P");
        jbPrioritariaPrint.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jbPrioritariaPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPrioritariaPrintActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Prioritária");

        jSeparator5.setToolTipText("");
        jSeparator5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jbPopularPrint.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jbPopularPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/popular.fw.png"))); // NOI18N
        jbPopularPrint.setText("F");
        jbPopularPrint.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jbPopularPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPopularPrintActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Farmácia Popular");

        jSeparator6.setToolTipText("");
        jSeparator6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jSeparator5)
            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jbConvencionalPrint, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jbPrioritariaPrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jbPopularPrint, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbConvencionalPrint)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbPrioritariaPrint)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbPopularPrint))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), " Atendente 01 ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });

        jbConvencional.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jbConvencional.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Masculino.png"))); // NOI18N
        jbConvencional.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jbConvencional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbConvencionalActionPerformed(evt);
            }
        });

        jbPopular.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jbPopular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Feminino.png"))); // NOI18N
        jbPopular.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jbPopular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPopularActionPerformed(evt);
            }
        });

        jSeparator1.setToolTipText("");
        jSeparator1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Convencional");

        jSeparator3.setToolTipText("");
        jSeparator3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Popular");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbConvencional, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbPopular, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(2, 2, 2))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbConvencional)
                    .addComponent(jbPopular))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(98, 98, 98))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), " Senhas  ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N

        atuallabel.setFont(new java.awt.Font("Arial", 1, 60)); // NOI18N
        atuallabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        atuallabel.setText("F000");

        jSeparator2.setToolTipText("");
        jSeparator2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        ultimalabel.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        ultimalabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ultimalabel.setText("F000");

        penultimalabel.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        penultimalabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        penultimalabel.setText("C000");

        antepenultimalabel.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        antepenultimalabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        antepenultimalabel.setText("C000");

        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator8.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator9.setToolTipText("");
        jSeparator9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        idlabel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        idlabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        idlabel.setText("Atendente 00");

        jSeparator10.setToolTipText("");
        jSeparator10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(idlabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(ultimalabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(penultimalabel, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(antepenultimalabel, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(atuallabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator9, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(idlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(atuallabel, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(ultimalabel, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(penultimalabel))
                    .addComponent(antepenultimalabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator8)
                    .addComponent(jSeparator7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jSeparator11.setOrientation(javax.swing.SwingConstants.VERTICAL);

        statuslabel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        statuslabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statuslabel.setText("Conectando...");

        jLabel15.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/you32.png"))); // NOI18N
        jLabel15.setToolTipText("Inove Systems - Video Aulas");

        jLabel16.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/3D.png"))); // NOI18N
        jLabel16.setToolTipText("Inove Systems - Home Page");
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Setings 32.png"))); // NOI18N
        jLabel14.setToolTipText("Inove Systems - Suporte");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });

        jSeparator12.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator13.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(statuslabel, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator11)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(statuslabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator12)
                    .addComponent(jSeparator13))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbConvencionalPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbConvencionalPrintActionPerformed
        String text = "C";
        String name = this.message.getName();
        if (!text.isEmpty()) {
            this.message = new Mensagem();
            this.message.setName(name);
            this.message.setText(text);
            this.message.setAction(Mensagem.Action.PRINT);
            this.service.send(this.message);
        }
    }//GEN-LAST:event_jbConvencionalPrintActionPerformed

    private void jbConvencionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbConvencionalActionPerformed
        String text = "convencional";
        String name = this.message.getName();
        if (!text.isEmpty()) {
            this.message = new Mensagem();
            this.message.setName(name);
            this.message.setText(text);
            this.message.setAction(Mensagem.Action.CALL);
            this.service.send(this.message);
        }
    }//GEN-LAST:event_jbConvencionalActionPerformed

    private void jbPopularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPopularActionPerformed
        String text = "popular";
        String name = this.message.getName();
        if (!text.isEmpty()) {
            this.message = new Mensagem();
            this.message.setName(name);
            this.message.setText(text);
            this.message.setAction(Mensagem.Action.CALL);
            this.service.send(this.message);
        }
    }//GEN-LAST:event_jbPopularActionPerformed

    private void jbPrioritariaPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPrioritariaPrintActionPerformed
        String text = "P";
        String name = this.message.getName();
        if (!text.isEmpty()) {
            this.message = new Mensagem();
            this.message.setName(name);
            this.message.setText(text);
            this.message.setAction(Mensagem.Action.PRINT);
            this.service.send(this.message);
        }
    }//GEN-LAST:event_jbPrioritariaPrintActionPerformed

    private void jbPopularPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPopularPrintActionPerformed
        String text = "F";
        String name = this.message.getName();
        if (!text.isEmpty()) {
            this.message = new Mensagem();
            this.message.setName(name);
            this.message.setText(text);
            this.message.setAction(Mensagem.Action.PRINT);
            this.service.send(this.message);
        }
    }//GEN-LAST:event_jbPopularPrintActionPerformed

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked

    }//GEN-LAST:event_jPanel4MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        String caixaProb;
        this.message = new Mensagem();
        this.message.setAtual(atuallabel.getText());
        this.message.setUltima(ultimalabel.getText());
        this.message.setPenultima(penultimalabel.getText());
        this.message.setAntepenultima(antepenultimalabel.getText());
        this.message.setName(caixa);
        String name = this.message.getName();
        this.message.setAction(Mensagem.Action.DISCONNECT);
        this.service.send(this.message);
        int resposta = 0;
        caixaProb = JOptionPane.showInputDialog(null, "QUAL O NÚMERO DE SEU CAIXA ?", "3D Soluções Tecnológicas - Configuração", 3);
        try {
            if ((!caixaProb.equals(null)) && (!caixaProb.equals(""))) {
                resposta = JOptionPane.showConfirmDialog(null, "CERTIFIQUE-SE QUE ESTE CAIXA NÃO EXISTE NA REDE! \n" + "O SEU CAIXA É O NÚMERO " + caixaProb + " ?");
                if (resposta == JOptionPane.YES_OPTION) {
                    caixa = caixaProb;
                    JOptionPane.showMessageDialog(null, "CAIXA " + caixa + " CRIADO COM SUCESSO!", "3D Soluções Tecnológicas - Informação", 1);
                } else {

                }
            } else {
                do {
                    caixaProb = JOptionPane.showInputDialog(null, "ERRO GRAVE! CAIXA INVALIDO! \nQUAL O NÚMERO DE SEU CAIXA ?", "3D Soluções Tecnológicas - Configuração", 3);
                } while ((caixaProb.equals(null)) || (caixaProb.equals("")));
                resposta = JOptionPane.showConfirmDialog(null, "CERTIFIQUE-SE QUE ESTE CAIXA NÃO EXISTE NA REDE! \n" + "O SEU CAIXA É O NÚMERO " + caixaProb + " ?");
                if (resposta == JOptionPane.YES_OPTION) {
                    caixa = caixaProb;
                    JOptionPane.showMessageDialog(null, "CAIXA " + caixa + " CRIADO COM SUCESSO!", "3D Soluções Tecnológicas - Informação", 1);
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
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Atendente " + caixa, javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N
        new Thread() {
            @Override
            public void run() {
                message.setAction(Mensagem.Action.CONNECT);
                message.setName(caixa);
                service = new Conexao();
                socket = service.connect(IPCom);
                new Thread(
                        new Cliente.ListenerSocket(socket)).start();
                service.send(message);
            }
        }.start();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        try {
            java.awt.Desktop.getDesktop().browse(new java.net.URI("http://www.inovesystems.com.br"));
        } catch (URISyntaxException ex) {
            statuslabel.setText("Site temporariamente indisponivel!");
        } catch (IOException ex) {
            statuslabel.setText("Site temporariamente indisponivel!");
        }
    }//GEN-LAST:event_jLabel16MouseClicked

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
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
                            JOptionPane.showMessageDialog(null, " Erro ao criar IPConfig! ", "3D Soluções Tecnológicas - Informação", 1);

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
                        JOptionPane.showMessageDialog(null, " Erro ao ler IPConfig! ", "3D Soluções Tecnológicas - Informação", 1);
                        statuslabel.setText("Erro ao ler IPConfig!");
                    }
                } catch (NullPointerException ex) {
                    statuslabel.setText("Erro ao ler IPConfig!");
                }
            }
        }.start();

    }//GEN-LAST:event_jLabel14MouseClicked

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
            java.util.logging.Logger.getLogger(Cliente.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cliente.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cliente.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cliente.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Cliente().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel antepenultimalabel;
    private javax.swing.JLabel atuallabel;
    private javax.swing.JLabel idlabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JButton jbConvencional;
    private javax.swing.JButton jbConvencionalPrint;
    private javax.swing.JButton jbPopular;
    private javax.swing.JButton jbPopularPrint;
    private javax.swing.JButton jbPrioritariaPrint;
    private javax.swing.JLabel penultimalabel;
    public javax.swing.JLabel statuslabel;
    private javax.swing.JLabel ultimalabel;
    // End of variables declaration//GEN-END:variables
}
