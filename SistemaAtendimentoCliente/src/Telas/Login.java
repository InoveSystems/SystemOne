package Telas;

import Bean.FuncionarioBean;
import DAO.FuncionarioDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Ritiele
 */
public class Login extends javax.swing.JFrame {

    public Login() {
        initComponents();
        jLogin.setSelectionStart(0);
        jLogin.setSelectionEnd(7);

    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator3 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jSenha = new javax.swing.JPasswordField();
        jLogin = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jStatus = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSair = new javax.swing.JButton();
        jConfirmar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Inove Systems - Login");
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jSenha.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jSenha.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLogin.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLogin.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jLogin.setText("USUÁRIO");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("..:: Acesso ao Sistema ::..");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jStatus.setBackground(new java.awt.Color(255, 255, 255));
        jStatus.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jStatus.setText("Digite seu Login de Usuario e Senha!");
        jStatus.setOpaque(true);

        jSair.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/exit.png"))); // NOI18N
        jSair.setText("Sair!");
        jSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSairActionPerformed(evt);
            }
        });

        jConfirmar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jConfirmar.setText("Confirmar");
        jConfirmar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jConfirmarActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/LoginFundo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jConfirmar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSair, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSenha, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLogin, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jStatus, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2))
                .addGap(1, 1, 1))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addComponent(jSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSair)
                                    .addComponent(jConfirmar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addGap(5, 5, 5)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

    }//GEN-LAST:event_formWindowOpened
    public void Pesquisar(FuncionarioBean Funcionario) {
        new Thread() {
            @Override
            public void run() {
                try {
                    int cod = 0;
                    String nome = "";
                    String senha = "";
                    FuncionarioDAO funcionario = new FuncionarioDAO();
                    ResultSet rs;
                    System.out.println(Funcionario.getCodigo());
                    rs = funcionario.retriveId(Funcionario);
                    if (rs.next()) {
                        do {
                            cod = rs.getInt("cod");
                            nome = rs.getString("nome");
                            senha = rs.getString("senha");
                        } while (rs.next());
                        if (jSenha.getText().trim().equals(senha)) {
                            setVisible(false);
                            Cliente cliente = new Cliente(cod, nome);
                            cliente.setVisible(true);
                        } else {
                            jStatus.setText("SENHA INCORRETA!");
                            Thread.currentThread().sleep(1000);
                            jStatus.setText("Verifique sua Senha!");
                            Thread.currentThread().sleep(1000);
                            jStatus.setText("Digite seu Login de Usuario e Senha!");
                             jSair.setEnabled(true);
                            jConfirmar.setEnabled(true);
                            jLogin.setEnabled(true);
                            jSenha.setEnabled(true);
                            jSenha.grabFocus();
                        }
                    } else {
                        jStatus.setText("Usuário não Localizado!");
                        Thread.currentThread().sleep(1000);
                        jStatus.setText("Verifique os dados de Login!");
                        Thread.currentThread().sleep(1000);
                        jStatus.setText("Digite seu Login de Usuario e Senha!");
                         jSair.setEnabled(true);
                        jConfirmar.setEnabled(true);
                        jLogin.setEnabled(true);
                        jSenha.setEnabled(true);
                    }
                } catch (SQLException ex) {
                    try {
                        jStatus.setText("Erro ao conectar com o banco de dados!");
                        Thread.currentThread().sleep(1000);
                        jStatus.setText("Entre em contato com o suporte tecnico!");
                        Thread.currentThread().sleep(1000);
                        jStatus.setText("Digite seu Login de Usuario e Senha!");
                         jSair.setEnabled(true);
                        jConfirmar.setEnabled(true);
                        jLogin.setEnabled(true);
                        jSenha.setEnabled(true);
                    } catch (InterruptedException ex1) {

                    }
                } catch (NullPointerException ex) {

                } catch (InterruptedException ex) {

                }
            }
        }.start();
    }


    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed

    }//GEN-LAST:event_formWindowClosed

    private void jSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSairActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jSairActionPerformed

    private void jConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jConfirmarActionPerformed
        jConfirmar.setEnabled(false);
        jSair.setEnabled(false);
        jLogin.setEnabled(false);
        jSenha.setEnabled(false);
        new Thread() {
            @Override
            public void run() {
                jLogin.getText();
                if ((jLogin.getText().trim().equals("")) || (jSenha.getText().trim().equals(""))) {
                    try {
                        jStatus.setText("Digite um USUÁRIO/SENHA válido!");
                        Thread.currentThread().sleep(1000);
                        jStatus.setText("Verifique os dados de Login!");
                        Thread.currentThread().sleep(1000);
                        jStatus.setText("Digite seu Login de Usuario e Senha!");
                        jConfirmar.setEnabled(true);
                        jSair.setEnabled(true);
                        jLogin.setEnabled(true);
                        jSenha.setEnabled(true);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    jLogin.grabFocus();
                } else {
                    try {
                        if ((jLogin.getText().trim().equals("config")) && (jSenha.getText().trim().equals("inove+1052"))) {
                            Config config = new Config(true);
                            config.jPanes.setSelectedIndex(2);
                            config.setVisible(true);
                            setEnabled(false);
                            setVisible(false);
                        } else {
                            jStatus.setText("Aguarde! Conectando ao sistema ...");
                            Thread.currentThread().sleep(1000);
                            FuncionarioBean funcionario = new FuncionarioBean();
                            funcionario.setCodigo(Integer.parseInt(jLogin.getText()));
                            Pesquisar(funcionario);
                            //jConfirmar.setEnabled(true);
                        }
                    } catch (NumberFormatException ex) {
                        try {
                            jStatus.setText("USUÁRIO INVÁLIDO!");
                            Thread.currentThread().sleep(1000);
                            jStatus.setText("Verifique os dados de Login!");
                            Thread.currentThread().sleep(1000);
                            jStatus.setText("Digite seu Login de Usuario e Senha!");
                            jConfirmar.setEnabled(true);
                            jSair.setEnabled(true);
                            jLogin.setEnabled(true);
                            jSenha.setEnabled(true);
                        } catch (InterruptedException ex1) {
                            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }.start();
    }//GEN-LAST:event_jConfirmarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //  <editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //    </editor-fold>
        //    </editor-fold>
        //    </editor-fold>
        //    </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jConfirmar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jLogin;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton jSair;
    private javax.swing.JPasswordField jSenha;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel jStatus;
    // End of variables declaration//GEN-END:variables

}
