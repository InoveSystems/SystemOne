package Telas;

import Bean.FuncionarioBean;
import ComRede.Conexao;
import ComRede.Mensagem;
import DAO.FuncionarioDAO;
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ritiele
 */
public class Config extends javax.swing.JFrame {

    private Mensagem message;
    public Conexao service;
    public String caixa = "20";
    private Socket socket;
    private String cod1;
    private boolean novo = true;
    private boolean config = false;
    private Login login = new Login();
    public String IPCom = "127.0.0.1";
    public String diretorioUsuario = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
    public File IPConfig = new File(diretorioUsuario + File.separator + "InoveSystems" + File.separator + "Config" + File.separator + "IPConfig.txt");
    public File arquivo = new File(diretorioUsuario + File.separator + "InoveSystems" + File.separator + "Config" + File.separator + "CaixaConfig.txt");
    public boolean salvarclick = false;

    public Config() {

        initComponents();
        //setAlwaysOnTop(true);
        login.setVisible(false);
        login.setEnabled(false);
        ImageIcon tab1Icon = new ImageIcon(
                this.getClass().getResource("/Imagens/funcionariocad.png"));
        ImageIcon tab2Icon = new ImageIcon(
                this.getClass().getResource("/Imagens/grafico.png"));
        ImageIcon tab3Icon = new ImageIcon(
                this.getClass().getResource("/Imagens/config.png"));
        jPanes.setIconAt(0, tab1Icon);
        jPanes.setIconAt(1, tab2Icon);
        jPanes.setIconAt(2, tab3Icon);

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
                        // jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), " Caixa " + caixa, javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N
                        //ConectarServidor();
                        jIPconexao.setText(IPCom);
                        jCaixa.setText(caixa);

                    }
                }.start();

            }
        }.
                start();

    }

    public Config(Boolean Config) {
        initComponents();
        //setAlwaysOnTop(false);
        setEnabled(true);
        config = Config;
        ImageIcon tab1Icon = new ImageIcon(
                this.getClass().getResource("/Imagens/funcionariocad.png"));
        ImageIcon tab2Icon = new ImageIcon(
                this.getClass().getResource("/Imagens/grafico.png"));
        ImageIcon tab3Icon = new ImageIcon(
                this.getClass().getResource("/Imagens/config.png"));
        jPanes.setIconAt(0, tab1Icon);
        jPanes.setIconAt(1, tab2Icon);
        jPanes.setIconAt(2, tab3Icon);
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
                        // jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), " Caixa " + caixa, javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14))); // NOI18N
                        // ConectarServidor();
                        jIPconexao.setText(IPCom);
                        jCaixa.setText(caixa);

                    }
                }.start();

            }
        }.
                start();
    }

    public boolean ConectarServidor() {
        if (!caixa.isEmpty()) {
            message = new Mensagem();
            this.message.setAction(Mensagem.Action.CONNECT);
            this.message.setName(caixa + "" + caixa);
            this.service = new Conexao();
            this.socket = this.service.connect(IPCom);
            new Thread(new Config.ListenerSocket(this.socket)).start();
            this.service.send(message);
        }
        return true;
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
        disconnected();

    }

    public void disconnected() {
        try {
            socket.close();
        } catch (IOException ex) {
            statuslabel.setText("Erro ao sair! Tente mais tarde.");

        } catch (NullPointerException ex) {

        }
    }

    private class ListenerSocket implements Runnable {

        private ObjectInputStream input;

        public ListenerSocket(Socket socket) {
            try {
                this.input = new ObjectInputStream(socket.getInputStream());
            } catch (NullPointerException ex) {
                try {
                    statuslabel.setText("Erro ao conectar ao servidor!");
                    Thread.currentThread().sleep(3000);
                    statuslabel.setText("Verifique a aplicação servidor!");
                    Thread.currentThread().sleep(3000);
                    statuslabel.setText("Verifique as configurações!");
                    Thread.currentThread().sleep(3000);
                    statuslabel.setText("Tente Novamente!");
                    Thread.currentThread().sleep(3000);
//                    new Thread() {
//                        @Override
//                        public void run() {
//                            statuslabel.setText("Tentando conectar novamente ...");
//                            ConectarServidor();
//                        }
//                    }.start();
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
                        // receive(message);
                    } else if (action.equals(action.USERS_ONLINE)) {
                        //refreshOnlines(message);
                    }

                }
            } catch (NullPointerException ex) {

            } catch (IOException ex) {
                return;
//                
            } catch (ClassNotFoundException ex) {
                statuslabel.setText("Erro ao conectar ao servidor!");

            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanes = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jNome = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jTelMo = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTelRe = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jTelCo = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel24 = new javax.swing.JLabel();
        jLogradouro = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jCidade = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jNumero = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jBairro = new javax.swing.JTextField();
        jComboEstado = new javax.swing.JComboBox();
        jLabel29 = new javax.swing.JLabel();
        jEmail = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jCPF = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jCod = new javax.swing.JTextField();
        jSenha = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jConSenha = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel37 = new javax.swing.JLabel();
        jComplemento = new javax.swing.JTextField();
        jRadioAdministrador = new javax.swing.JRadioButton();
        buttonNovo = new javax.swing.JButton();
        buttonEditar = new javax.swing.JButton();
        buttonExcluir = new javax.swing.JButton();
        buttonSalvar = new javax.swing.JButton();
        buttonCalcelar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPesquisar = new javax.swing.JTextField();
        jComboPesquisar = new javax.swing.JComboBox();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jComboTipo = new javax.swing.JComboBox();
        jLabel31 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jComboFiltro = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jComboPeriodo = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jIPconexao = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jCaixa = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        statuslabel = new javax.swing.JTextField();
        ButtonEditar = new javax.swing.JButton();
        ButtonSalvar = new javax.swing.JButton();
        ButtonCancelar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Inove Systems - Configurações");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanes.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Usuario", "Nome Completo", "CPF", "Telefone Móvel", "Telefone Residencial"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(100);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(1).setMinWidth(260);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(260);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(260);
            jTable1.getColumnModel().getColumn(2).setMinWidth(150);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(150);
            jTable1.getColumnModel().getColumn(3).setMinWidth(150);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(150);
            jTable1.getColumnModel().getColumn(4).setMinWidth(150);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(150);
        }

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jNome.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jNome.setForeground(new java.awt.Color(51, 51, 51));
        jNome.setEnabled(false);
        jNome.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jNome.setPreferredSize(new java.awt.Dimension(500, 25));
        jNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNomeActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel20.setText("Nome Completo:");

        jTelMo.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTelMo.setForeground(new java.awt.Color(51, 51, 51));
        jTelMo.setEnabled(false);
        jTelMo.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTelMo.setPreferredSize(new java.awt.Dimension(500, 25));
        jTelMo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTelMoActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel21.setText("Telefone Móvel:");

        jTelRe.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTelRe.setForeground(new java.awt.Color(51, 51, 51));
        jTelRe.setEnabled(false);
        jTelRe.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTelRe.setPreferredSize(new java.awt.Dimension(500, 25));
        jTelRe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTelReActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel22.setText("Telefone Residencial:");

        jTelCo.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTelCo.setForeground(new java.awt.Color(51, 51, 51));
        jTelCo.setEnabled(false);
        jTelCo.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jTelCo.setPreferredSize(new java.awt.Dimension(500, 25));
        jTelCo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTelCoActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel23.setText("Telefone Comercial:");

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel24.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel24.setText("Logradouro:");

        jLogradouro.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLogradouro.setForeground(new java.awt.Color(51, 51, 51));
        jLogradouro.setEnabled(false);
        jLogradouro.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jLogradouro.setPreferredSize(new java.awt.Dimension(500, 25));
        jLogradouro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLogradouroActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel25.setText("Cidade:");

        jCidade.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jCidade.setForeground(new java.awt.Color(51, 51, 51));
        jCidade.setEnabled(false);
        jCidade.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jCidade.setPreferredSize(new java.awt.Dimension(500, 25));
        jCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCidadeActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel26.setText("Estado:");

        jLabel27.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel27.setText("Numero:");

        jNumero.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jNumero.setForeground(new java.awt.Color(51, 51, 51));
        jNumero.setEnabled(false);
        jNumero.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jNumero.setPreferredSize(new java.awt.Dimension(500, 25));
        jNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNumeroActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel28.setText("Bairro:");

        jBairro.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jBairro.setForeground(new java.awt.Color(51, 51, 51));
        jBairro.setEnabled(false);
        jBairro.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jBairro.setPreferredSize(new java.awt.Dimension(500, 25));
        jBairro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBairroActionPerformed(evt);
            }
        });

        jComboEstado.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboEstado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
        jComboEstado.setEnabled(false);
        jComboEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboEstadoActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel29.setText("Email:");

        jEmail.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jEmail.setForeground(new java.awt.Color(51, 51, 51));
        jEmail.setEnabled(false);
        jEmail.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jEmail.setPreferredSize(new java.awt.Dimension(500, 25));
        jEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEmailActionPerformed(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel30.setText("CPF:");

        jCPF.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jCPF.setForeground(new java.awt.Color(51, 51, 51));
        jCPF.setText("02704098077");
        jCPF.setEnabled(false);
        jCPF.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jCPF.setPreferredSize(new java.awt.Dimension(500, 25));
        jCPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCPFActionPerformed(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel32.setText("Usuário:");

        jCod.setEditable(false);
        jCod.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jCod.setForeground(new java.awt.Color(51, 51, 51));
        jCod.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jCod.setText("1234566");
        jCod.setEnabled(false);
        jCod.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jCod.setPreferredSize(new java.awt.Dimension(500, 25));
        jCod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCodActionPerformed(evt);
            }
        });

        jSenha.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jSenha.setForeground(new java.awt.Color(51, 51, 51));
        jSenha.setEnabled(false);
        jSenha.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jSenha.setPreferredSize(new java.awt.Dimension(500, 25));
        jSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSenhaActionPerformed(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel33.setText("Senha:");

        jLabel34.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel34.setText("Confirmar Senha:");

        jConSenha.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jConSenha.setForeground(new java.awt.Color(51, 51, 51));
        jConSenha.setEnabled(false);
        jConSenha.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jConSenha.setPreferredSize(new java.awt.Dimension(500, 25));
        jConSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jConSenhaActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel37.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel37.setText("Complemento:");

        jComplemento.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jComplemento.setForeground(new java.awt.Color(51, 51, 51));
        jComplemento.setEnabled(false);
        jComplemento.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jComplemento.setPreferredSize(new java.awt.Dimension(500, 25));
        jComplemento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComplementoActionPerformed(evt);
            }
        });

        jRadioAdministrador.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jRadioAdministrador.setText("Administrador");
        jRadioAdministrador.setEnabled(false);
        jRadioAdministrador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jRadioAdministrador.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jNome, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCod, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jConSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioAdministrador, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTelMo, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTelCo, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTelRe, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel26)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel37)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)))))
                .addGap(5, 5, 5))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jCod, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                        .addComponent(jSenha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jConSenha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jRadioAdministrador, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jSeparator1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jCPF, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                        .addComponent(jLabel30))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel20)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel29))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTelMo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel21))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTelRe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel22))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTelCo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel23)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCidade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboEstado))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLogradouro, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                            .addComponent(jLabel24))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(jNumero, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                            .addComponent(jLabel28)
                            .addComponent(jBairro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel37)
                            .addComponent(jComplemento, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
                        .addGap(4, 4, 4)))
                .addGap(1, 1, 1))
        );

        buttonNovo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        buttonNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/add2.png"))); // NOI18N
        buttonNovo.setText("Novo Funcionário");
        buttonNovo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        buttonNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNovoActionPerformed(evt);
            }
        });

        buttonEditar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        buttonEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/editar24x24.png"))); // NOI18N
        buttonEditar.setText("Editar Cadastro");
        buttonEditar.setEnabled(false);
        buttonEditar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        buttonEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEditarActionPerformed(evt);
            }
        });

        buttonExcluir.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        buttonExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/close24x24.png"))); // NOI18N
        buttonExcluir.setText("Excluir Cadastro");
        buttonExcluir.setEnabled(false);
        buttonExcluir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        buttonExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExcluirActionPerformed(evt);
            }
        });

        buttonSalvar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        buttonSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/save24x24.png"))); // NOI18N
        buttonSalvar.setText("Salvar Cadastro");
        buttonSalvar.setEnabled(false);
        buttonSalvar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        buttonSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSalvarActionPerformed(evt);
            }
        });

        buttonCalcelar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        buttonCalcelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/cancel24x24.png"))); // NOI18N
        buttonCalcelar.setText("Cancelar Cadastro");
        buttonCalcelar.setEnabled(false);
        buttonCalcelar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        buttonCalcelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCalcelarActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/PesquisarCli.png"))); // NOI18N
        jButton1.setText("Pesquisar Cadastro");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPesquisar.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jPesquisar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPesquisar.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jPesquisar.setPreferredSize(new java.awt.Dimension(500, 25));
        jPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPesquisarActionPerformed(evt);
            }
        });

        jComboPesquisar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboPesquisar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Código", "CPF", "Nome" }));
        jComboPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboPesquisarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(3, 3, 3)
                        .addComponent(jComboPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(jButton1))
                    .addComponent(jSeparator2)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(buttonNovo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2)
                        .addComponent(buttonEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(3, 3, 3)
                        .addComponent(buttonExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2)
                        .addComponent(buttonSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(3, 3, 3)
                        .addComponent(buttonCalcelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(103, 103, 103))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(buttonEditar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonExcluir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonNovo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(buttonSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonCalcelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(3, 3, 3)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jComboPesquisar))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jPesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addGap(5, 5, 5))
        );

        jPanes.addTab("", jPanel1);

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel8.setPreferredSize(new java.awt.Dimension(850, 526));

        jComboTipo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tempo de Atendimento", "Tempo de Espera ", "Quantidade de Atendimentos" }));
        jComboTipo.setToolTipText("");
        jComboTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboTipoActionPerformed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel31.setText("Tipo de Relatório:");

        jLabel38.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel38.setText("Filtro:");

        jComboFiltro.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboFiltro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Atendente", "Caixa", "Tipo de Ficha" }));
        jComboFiltro.setToolTipText("");
        jComboFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboFiltroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jDateChooser1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel40.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jLabel40.setText("Período:");

        jLabel41.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel41.setText("/");

        jDateChooser2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jComboPeriodo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboPeriodo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Horario", "Manhã", "Tarde", "Noite", "Dia", "Mês", "Ano" }));
        jComboPeriodo.setToolTipText("");
        jComboPeriodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboPeriodoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboPeriodo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jComboPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)))
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(397, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(419, Short.MAX_VALUE))
        );

        jPanes.addTab("", jPanel2);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jIPconexao.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jIPconexao.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jIPconexao.setText("192.168.2.1");
        jIPconexao.setEnabled(false);
        jIPconexao.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jIPconexao.setPreferredSize(new java.awt.Dimension(500, 25));
        jIPconexao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jIPconexaoActionPerformed(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel36.setText("IP do Servidor:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jIPconexao, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jIPconexao, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jCaixa.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jCaixa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jCaixa.setText("01");
        jCaixa.setEnabled(false);
        jCaixa.setMaximumSize(new java.awt.Dimension(1000, 10000));
        jCaixa.setPreferredSize(new java.awt.Dimension(500, 25));
        jCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCaixaActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel35.setText("Identificação do caixa:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        statuslabel.setEditable(false);
        statuslabel.setBackground(new java.awt.Color(255, 255, 255));
        statuslabel.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        statuslabel.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        statuslabel.setText("Verificar ...");
        statuslabel.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        statuslabel.setFocusable(false);
        statuslabel.setMaximumSize(new java.awt.Dimension(1000, 10000));
        statuslabel.setPreferredSize(new java.awt.Dimension(500, 25));
        statuslabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statuslabelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statuslabel, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(statuslabel, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
        );

        ButtonEditar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        ButtonEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/editar24x24.png"))); // NOI18N
        ButtonEditar.setText("Editar Configuração");
        ButtonEditar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ButtonEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonEditarActionPerformed(evt);
            }
        });

        ButtonSalvar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        ButtonSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/save24x24.png"))); // NOI18N
        ButtonSalvar.setText("Salvar Configuração");
        ButtonSalvar.setEnabled(false);
        ButtonSalvar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ButtonSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonSalvarActionPerformed(evt);
            }
        });

        ButtonCancelar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        ButtonCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/cancel24x24.png"))); // NOI18N
        ButtonCancelar.setText("Cancelar Configuração");
        ButtonCancelar.setEnabled(false);
        ButtonCancelar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ButtonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonCancelarActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton2.setText("verificar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(ButtonSalvar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ButtonCancelar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ButtonEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(5, 5, 5))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(ButtonEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(ButtonSalvar)
                        .addGap(5, 5, 5)
                        .addComponent(ButtonCancelar)))
                .addGap(5, 5, 5)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(171, 171, 171)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(170, 170, 170))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(176, 176, 176)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(181, Short.MAX_VALUE))
        );

        jPanes.addTab("", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanes, javax.swing.GroupLayout.PREFERRED_SIZE, 949, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanes, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jComboPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboPesquisarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboPesquisarActionPerformed

    private void jPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPesquisarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPesquisarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jPesquisar.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "DIGITE UM ITEM DE PESQUISA VÁLIDO!", "Inove Systems - Informação", JOptionPane.ERROR_MESSAGE);
            atualizarTabela();
            jPesquisar.grabFocus();
        } else {
            new Thread() {
                @Override
                public void run() {
                    clearTable();
                }
            }.
                    start();
            try {
                FuncionarioBean funcionario = new FuncionarioBean();
                if (jComboPesquisar.getSelectedIndex() == 0) {
                    funcionario.setCodigo(Integer.parseInt(jPesquisar.getText()));
                    Pesquisar(funcionario);
                } else {
                    if (jComboPesquisar.getSelectedIndex() == 1) {
                        funcionario.setCPF(jPesquisar.getText());
                        Pesquisar(funcionario);
                    } else {
                        if (jComboPesquisar.getSelectedIndex() == 2) {
                            funcionario.setNome(jPesquisar.getText());
                            Pesquisar(funcionario);
                        }
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "PESQUISA INVÁLIDA!" + "\n" + "VERIFIQUE OS DADOS PESQUISADOS!", "Inove Systems - Informação", JOptionPane.ERROR_MESSAGE);
                atualizarTabela();
                jPesquisar.grabFocus();
            }
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void buttonCalcelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCalcelarActionPerformed
        novo = true;
        jCod.setText(cod1);
        jCod.setEnabled(false);
        jSenha.setEnabled(false);
        jConSenha.setEnabled(false);
        jNome.setEnabled(false);
        jCPF.setEnabled(false);
        jEmail.setEnabled(false);
        jTelCo.setEnabled(false);
        jTelMo.setEnabled(false);
        jTelRe.setEnabled(false);
        jCidade.setEnabled(false);
        jComboEstado.setEnabled(false);
        jNumero.setEnabled(false);
        jBairro.setEnabled(false);
        jComplemento.setEnabled(false);
        jLogradouro.setEnabled(false);
        jRadioAdministrador.setEnabled(false);
        buttonSalvar.setEnabled(false);
        buttonEditar.setEnabled(false);
        buttonExcluir.setEnabled(false);
        buttonCalcelar.setEnabled(false);
        buttonNovo.setEnabled(true);
        LimparTela();
    }//GEN-LAST:event_buttonCalcelarActionPerformed

    private void buttonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSalvarActionPerformed
        if (jSenha.getText().equals(jConSenha.getText())) {
            FuncionarioBean funcionario = new FuncionarioBean();
            FuncionarioDAO enviar = null;
            try {
                enviar = new FuncionarioDAO();
            } catch (SQLException ex) {

            }
            String Estado;
            try {
                funcionario.setCodigo(Integer.parseInt(jCod.getText()));
                funcionario.setNome(jNome.getText().toUpperCase());
                funcionario.setCPF(jCPF.getText().toUpperCase());
                funcionario.setSenha(jSenha.getText().toUpperCase());
                funcionario.setEmail(jEmail.getText().toLowerCase());
                funcionario.setCidade(jCidade.getText().toUpperCase());
                Estado = jComboEstado.getSelectedItem() + "".toUpperCase();
                funcionario.setEstado(Estado);
                funcionario.setLogradouro(jLogradouro.getText().toUpperCase());
                funcionario.setNumero(jNumero.getText().toUpperCase());
                funcionario.setBairro(jBairro.getText().toUpperCase());
                funcionario.setComplemento(jComplemento.getText().toUpperCase());
                funcionario.setTelefone_mv(jTelMo.getText().toUpperCase());
                funcionario.setTelefone_rs(jTelRe.getText().toUpperCase());
                funcionario.setTelefone_cm(jTelCo.getText().toUpperCase());
                funcionario.setAdministrador(jRadioAdministrador.isSelected());
                if (novo) {
                    enviar.create(funcionario);
                } else {
                    enviar.update(funcionario);
                }
                jCod.setEnabled(false);
                jSenha.setEnabled(false);
                jConSenha.setEnabled(false);
                jNome.setEnabled(false);
                jCPF.setEnabled(false);
                jEmail.setEnabled(false);
                jTelCo.setEnabled(false);
                jTelMo.setEnabled(false);
                jTelRe.setEnabled(false);
                jCidade.setEnabled(false);
                jComboEstado.setEnabled(false);
                jNumero.setEnabled(false);
                jBairro.setEnabled(false);
                jComplemento.setEnabled(false);
                jLogradouro.setEnabled(false);
                jRadioAdministrador.setEnabled(false);
                buttonCalcelar.setEnabled(false);
                buttonNovo.setEnabled(true);
                buttonSalvar.setEnabled(false);
                atualizarTabela();
                LimparTela();
            } catch (SQLException ex) {
                System.out.println("Erro ao salvar os dados no banco!");
            } catch (NumberFormatException ex) {

            }
        } else {
            JOptionPane.showMessageDialog(null, "A SENHA NÃO CONFERE!" + "\n" + "VERIFIFIQUE SUA SENHA!", "Inove Systems - Informação", JOptionPane.ERROR_MESSAGE);
            jSenha.setText(null);
            jConSenha.setText(null);
            jSenha.grabFocus();
        }

    }//GEN-LAST:event_buttonSalvarActionPerformed

    private void buttonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExcluirActionPerformed

        try {
            FuncionarioBean funcionario = new FuncionarioBean();
            FuncionarioDAO enviar = new FuncionarioDAO();
            funcionario.setCodigo(Integer.parseInt(jCod.getText()));
            enviar.delete(funcionario);
            buttonNovo.setEnabled(true);
            buttonEditar.setEnabled(false);
            buttonCalcelar.setEnabled(false);
            buttonSalvar.setEnabled(false);
            buttonExcluir.setEnabled(false);
            jCod.setEnabled(false);
            jSenha.setEnabled(false);
            jNome.setEnabled(false);
            jCPF.setEnabled(false);
            jEmail.setEnabled(false);
            jTelCo.setEnabled(false);
            jTelMo.setEnabled(false);
            jTelRe.setEnabled(false);
            jCidade.setEnabled(false);
            jComboEstado.setEnabled(false);
            jNumero.setEnabled(false);
            jBairro.setEnabled(false);
            jComplemento.setEnabled(false);
            jLogradouro.setEnabled(false);
            jRadioAdministrador.setEnabled(false);
            atualizarTabela();
            LimparTela();
        } catch (SQLException ex) {
            System.out.println("Erro ao excluir os dados do banco!");
        }
    }//GEN-LAST:event_buttonExcluirActionPerformed

    private void buttonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEditarActionPerformed
        novo = false;
        buttonExcluir.setEnabled(false);
        buttonEditar.setEnabled(false);
        buttonSalvar.setEnabled(true);
        jCod.setEnabled(true);
        jCod.setEditable(false);
        jSenha.setEnabled(true);
        jConSenha.setEnabled(true);
        jNome.setEnabled(true);
        jCPF.setEnabled(true);
        jEmail.setEnabled(true);
        jTelCo.setEnabled(true);
        jTelMo.setEnabled(true);
        jTelRe.setEnabled(true);
        jCidade.setEnabled(true);
        jComboEstado.setEnabled(true);
        jNumero.setEnabled(true);
        jBairro.setEnabled(true);
        jComplemento.setEnabled(true);
        jLogradouro.setEnabled(true);
        jRadioAdministrador.setEnabled(true);

    }//GEN-LAST:event_buttonEditarActionPerformed

    private void buttonNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNovoActionPerformed
        novo = true;
        jCod.setEnabled(true);
        jCod.setEditable(false);
        jSenha.setEnabled(true);
        jConSenha.setEnabled(true);
        jNome.setEnabled(true);
        jCPF.setEnabled(true);
        jEmail.setEnabled(true);
        jTelCo.setEnabled(true);
        jTelMo.setEnabled(true);
        jTelRe.setEnabled(true);
        jCidade.setEnabled(true);
        jComboEstado.setEnabled(true);
        jNumero.setEnabled(true);
        jBairro.setEnabled(true);
        jComplemento.setEnabled(true);
        jLogradouro.setEnabled(true);
        jRadioAdministrador.setEnabled(true);
        buttonSalvar.setEnabled(true);
        buttonCalcelar.setEnabled(true);
        buttonNovo.setEnabled(false);
    }//GEN-LAST:event_buttonNovoActionPerformed

    private void jCodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCodActionPerformed

    private void jCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCPFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCPFActionPerformed

    private void jEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jEmailActionPerformed

    private void jComboEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboEstadoActionPerformed

    private void jBairroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBairroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBairroActionPerformed

    private void jNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNumeroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jNumeroActionPerformed

    private void jCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCidadeActionPerformed

    private void jLogradouroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLogradouroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLogradouroActionPerformed

    private void jTelCoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTelCoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTelCoActionPerformed

    private void jTelReActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTelReActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTelReActionPerformed

    private void jTelMoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTelMoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTelMoActionPerformed

    private void jNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jNomeActionPerformed

    private void jCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCaixaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCaixaActionPerformed

    private void jIPconexaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jIPconexaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jIPconexaoActionPerformed

    private void statuslabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statuslabelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_statuslabelActionPerformed

    private void ButtonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonEditarActionPerformed
        //Cliente cliente = new Cliente();
        statuslabel.setText("Verificar ...");
        ButtonEditar.setEnabled(false);
        ButtonSalvar.setEnabled(true);
        ButtonCancelar.setEnabled(true);
        jCaixa.setEnabled(true);
        jIPconexao.setEnabled(true);

    }//GEN-LAST:event_ButtonEditarActionPerformed

    private void ButtonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonSalvarActionPerformed
        salvarclick = true;
        new Thread() {
            @Override
            public void run() {
                caixa = jCaixa.getText();
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
                IPCom = jIPconexao.getText();
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
                ButtonEditar.setEnabled(true);
                ButtonSalvar.setEnabled(false);
                ButtonCancelar.setEnabled(false);
                jCaixa.setEnabled(false);
                jIPconexao.setEnabled(false);
                disconnected();
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            statuslabel.setText("Começando os Testes ...");
                            Thread.currentThread().sleep(3000);
                        } catch (InterruptedException ex) {

                        }
                        ConectarServidor();

                    }
                }.start();

            }
        }.start();


    }//GEN-LAST:event_ButtonSalvarActionPerformed

    private void ButtonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonCancelarActionPerformed
        ButtonEditar.setEnabled(true);
        ButtonSalvar.setEnabled(false);
        ButtonCancelar.setEnabled(false);
        jCaixa.setEnabled(false);
        jIPconexao.setEnabled(false);
    }//GEN-LAST:event_ButtonCancelarActionPerformed

    private void jComplementoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComplementoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComplementoActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
//       
        if (!config) {
            new Thread() {
                @Override
                public void run() {
                    atualizarTabela();
                }
            }.
                    start();
        } else {
            jPanes.setEnabledAt(0, false);
            jPanes.setEnabledAt(1, false);
        }
    }//GEN-LAST:event_formWindowOpened

    private void jConSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jConSenhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jConSenhaActionPerformed

    private void jSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSenhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jSenhaActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        buttonNovo.setEnabled(false);
        buttonExcluir.setEnabled(true);
        buttonCalcelar.setEnabled(true);
        buttonEditar.setEnabled(true);
        int cod = 0;
        DefaultTableModel Model = (DefaultTableModel) jTable1.getModel();
        int row = jTable1.getSelectedRow();
        cod = Integer.parseInt(Model.getValueAt(row, 0).toString());
        try {
            FuncionarioBean Funcionario = new FuncionarioBean();
            FuncionarioDAO funcionario = new FuncionarioDAO();
            Funcionario.setCodigo(cod);
            ResultSet rs;
            rs = funcionario.retriveId(Funcionario);
            if (rs.next()) {
                do {
                    cod = rs.getInt("cod");
                    jCod.setText(Integer.toString(cod));
                    String senha = rs.getString("senha");
                    jSenha.setText(senha);
                    jConSenha.setText(senha);
                    String nome = rs.getString("nome");
                    jNome.setText(nome);
                    String CPF = rs.getString("cpf");
                    jCPF.setText(CPF);
                    String TelefoneMv = rs.getString("telefone_mv");
                    jTelMo.setText(TelefoneMv);
                    String TelefoneRs = rs.getString("telefone_rs");
                    jTelRe.setText(TelefoneRs);
                    String email = rs.getString("email");
                    jEmail.setText(email);
                    String cidade = rs.getString("cidade");
                    jCidade.setText(cidade);
                    String estado = rs.getString("estado");
                    for (int i = 0; i < jComboEstado.getItemCount(); i++) {
                        if (estado.equals(jComboEstado.getItemAt(i))) {
                            jComboEstado.setSelectedIndex(i);
                        }
                    }
                    String telefoneCm = rs.getString("telefone_cm");
                    jTelCo.setText(telefoneCm);
                    String logradouro = rs.getString("logradouro");
                    jLogradouro.setText(logradouro);
                    String numero = rs.getString("numero");
                    jNumero.setText(numero);
                    String bairro = rs.getString("bairro");
                    jBairro.setText(bairro);
                    String complemento = rs.getString("complemento");
                    jComplemento.setText(complemento);
                    boolean administrador = rs.getBoolean("administrador");
                    jRadioAdministrador.setSelected(administrador);
                } while (rs.next());
            } else {
                JOptionPane.showMessageDialog(null, "NÃO FOI POSSÍVEL LOCALIZAR ESTE CADASTRO!" + "\n" + "VERIFIQUE OS DADOS DE PESQUISA!", "Inove Systems - Informação", JOptionPane.ERROR_MESSAGE);
                atualizarTabela();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jTable1MouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        disconnected();
        if (config) {
            login.setVisible(true);
            login.setEnabled(true);
            setVisible(false);
            setEnabled(false);
            config = false;
        }
        if (salvarclick) {
            JOptionPane.showMessageDialog(null, "É necessario REINICIAR o sistema para SALVAR MODIFICAÇÕES! ", "Inove Systems - Informação", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

    }//GEN-LAST:event_formWindowClosed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        disconnected();
        new Thread() {
            @Override
            public void run() {
                try {
                    statuslabel.setText("Começando os Testes ...");
                    Thread.currentThread().sleep(3000);
                } catch (InterruptedException ex) {

                }
                ConectarServidor();
            }
        }.start();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboTipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboTipoActionPerformed

    private void jComboFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboFiltroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboFiltroActionPerformed

    private void jComboPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboPeriodoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboPeriodoActionPerformed
    public void LimparTela() {
        jBairro.setText(null);
        jCPF.setText(null);
        jCidade.setText(null);
        jComplemento.setText(null);
        jConSenha.setText(null);
        jSenha.setText(null);
        jTelCo.setText(null);
        jTelMo.setText(null);
        jTelRe.setText(null);
        jEmail.setText(null);
        jLogradouro.setText(null);
        jNumero.setText(null);
        jNome.setText(null);
        jPesquisar.setText(null);
    }

    public void clearTable() {

        try {
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.getDataVector().removeAllElements();
            jTable1.revalidate();
            jTable1.repaint();

        } catch (NullPointerException ex) {

        }

    }

    public void atualizarTabela() {
        new Thread() {
            @Override
            public void run() {
                try {
                    int cod = 0;
                    clearTable();
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    FuncionarioDAO funcionario = new FuncionarioDAO();
                    ResultSet rs = funcionario.retriveTotal();
                    while (rs.next()) {
                        cod = rs.getInt("cod");
                        String nome = rs.getString("nome");
                        String CPF = rs.getString("cpf");
                        String TelefoneMv = rs.getString("telefone_mv");
                        String TelefoneRs = rs.getString("telefone_rs");
                        model.addRow(new Object[]{cod, nome, CPF, TelefoneMv, TelefoneRs});
                    }
                    cod1 = cod + 1 + "";
                    jCod.setText(cod1);
                } catch (SQLException e) {
                    System.out.println("Erro de sql3");
                } catch (NullPointerException ex) {
                    // JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de dados! \nEntre em contado com o administrador do sistema! \nInove Systems - www.inovesystems.com.br", "Inove Systems - Informação", JOptionPane.ERROR_MESSAGE);
                }

            }
        }.start();
    }

    public void Pesquisar(FuncionarioBean Funcionario) {
        int cod = 0;
        try {
            new Thread() {
                @Override
                public void run() {
                    clearTable();
                }
            }.
                    start();

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            FuncionarioDAO funcionario = new FuncionarioDAO();
            if (jComboPesquisar.getSelectedIndex() == 0) {

                ResultSet rs = funcionario.retriveId(Funcionario);
                if (rs.next()) {
                    do {
                        cod = rs.getInt("cod");
                        String nome = rs.getString("nome");
                        String CPF = rs.getString("cpf");
                        String TelefoneMv = rs.getString("telefone_mv");
                        String TelefoneRs = rs.getString("telefone_rs");
                        model.addRow(new Object[]{cod, nome, CPF, TelefoneMv, TelefoneRs});
                    } while (rs.next());
                } else {
                    JOptionPane.showMessageDialog(null, "NÃO FOI POSSÍVEL LOCALIZAR ESTE CADASTRO!" + "\n" + "VERIFIQUE OS DADOS DE PESQUISA!", "Inove Systems - Informação", JOptionPane.ERROR_MESSAGE);
                    atualizarTabela();
                }

            } else {
                if (jComboPesquisar.getSelectedIndex() == 2) {

                    ResultSet rs = funcionario.retriveNome(Funcionario);
                    if (rs.next()) {
                        do {
                            cod = rs.getInt("cod");
                            String nome = rs.getString("nome");
                            String CPF = rs.getString("cpf");
                            String TelefoneMv = rs.getString("telefone_mv");
                            String TelefoneRs = rs.getString("telefone_rs");
                            model.addRow(new Object[]{cod, nome, CPF, TelefoneMv, TelefoneRs});
                        } while (rs.next());
                    } else {
                        JOptionPane.showMessageDialog(null, "NÃO FOI POSSÍVEL LOCALIZAR ESTE CADASTRO!" + "\n" + "VERIFIQUE OS DADOS DE PESQUISA!", "Inove Systems - Informação", JOptionPane.ERROR_MESSAGE);

                        atualizarTabela();
                    }

                } else {
                    if (jComboPesquisar.getSelectedIndex() == 1) {

                        ResultSet rs = funcionario.retriveCPF(Funcionario);
                        if (rs.next()) {

                            do {
                                cod = rs.getInt("cod");
                                String nome = rs.getString("nome");
                                String CPF = rs.getString("cpf");
                                String TelefoneMv = rs.getString("telefone_mv");
                                String TelefoneRs = rs.getString("telefone_rs");
                                model.addRow(new Object[]{cod, nome, CPF, TelefoneMv, TelefoneRs});
                            } while (rs.next());
                        } else {
                            JOptionPane.showMessageDialog(null, "NÃO FOI POSSÍVEL LOCALIZAR ESTE CADASTRO!" + "\n" + "VERIFIQUE OS DADOS DE PESQUISA!", "Inove Systems - Informação", JOptionPane.ERROR_MESSAGE);
                            atualizarTabela();
                        }

                    }
                }

            }

        } catch (SQLException e) {
            System.out.println("Erro banco");
        } catch (NullPointerException ex) {

        }
    }

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
            java.util.logging.Logger.getLogger(Config.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Config.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Config.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Config.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Config().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonCancelar;
    private javax.swing.JButton ButtonEditar;
    private javax.swing.JButton ButtonSalvar;
    private javax.swing.JButton buttonCalcelar;
    private javax.swing.JButton buttonEditar;
    private javax.swing.JButton buttonExcluir;
    private javax.swing.JButton buttonNovo;
    private javax.swing.JButton buttonSalvar;
    private javax.swing.JTextField jBairro;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JTextField jCPF;
    private javax.swing.JTextField jCaixa;
    private javax.swing.JTextField jCidade;
    private javax.swing.JTextField jCod;
    private javax.swing.JComboBox jComboEstado;
    private javax.swing.JComboBox jComboFiltro;
    private javax.swing.JComboBox jComboPeriodo;
    private javax.swing.JComboBox jComboPesquisar;
    private javax.swing.JComboBox jComboTipo;
    private javax.swing.JTextField jComplemento;
    private javax.swing.JTextField jConSenha;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JTextField jEmail;
    private javax.swing.JTextField jIPconexao;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JTextField jLogradouro;
    private javax.swing.JTextField jNome;
    private javax.swing.JTextField jNumero;
    public javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    public javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    public javax.swing.JTabbedPane jPanes;
    private javax.swing.JTextField jPesquisar;
    private javax.swing.JRadioButton jRadioAdministrador;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jSenha;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTelCo;
    private javax.swing.JTextField jTelMo;
    private javax.swing.JTextField jTelRe;
    private javax.swing.JTextField statuslabel;
    // End of variables declaration//GEN-END:variables
}
