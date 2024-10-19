import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class TelaDeLogin extends JFrame {
    private final JLabel lblLogin;
    private final JLabel lblSenha;

    private final JTextField txtLogin;
    private final JPasswordField txtSenha;

    private final JButton btnLogar;
    private final JLabel lblNotificacoes;

    public TelaDeLogin() {
        super("Tela de Login");
        setLayout(new FlowLayout());

        lblLogin = new JLabel("Login:");
        lblLogin.setToolTipText("Login");
        add(lblLogin);

        txtLogin = new JTextField(15);
        add(txtLogin);

        lblSenha = new JLabel("Senha:");
        lblSenha.setToolTipText("Senha");
        add(lblSenha);

        txtSenha = new JPasswordField(15);
        add(txtSenha);

        btnLogar = new JButton("Login");
        add(btnLogar); 

        lblNotificacoes = new JLabel ("notificações");
        add (lblNotificacoes);

        ButtonHandler buttonHandler = new ButtonHandler();
        btnLogar.addActionListener(buttonHandler);
    }

    // criar handler
    private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            try{
                Connection conexao = MySQLConnector.conectar();
                String strSqlLogin = "select * from `db_senac`.`tbl_senac` where `email` = '" + txtLogin.getText() + "' and `senha` = '" + String.valueOf(txtSenha.getPassword()) + "';";
                Statement stmSqlLogin = conexao.createStatement();
                ResultSet rstSqlLogin = stmSqlLogin.executeQuery(strSqlLogin);
                if (rstSqlLogin.next()){
                    lblNotificacoes.setText("Login realizado com sucesso.");
                    //aqui vamos notificar o usuario que o login e senha foram encontrados
                    
                } else {
                    lblNotificacoes.setText("Não foi possivel encontrar o login e/ou senha digitados. Por favor, verifique e tente novamente");
                    // aqui vamos notificar o usuario que o login e senha nao foram encontrados
                }
            }
            catch (Exception e) {
                lblNotificacoes.setText("Houve um problema e não será possivel realizar o login neste momento. Por favor, tenta novamente mais tarde.");
                System.err.println("ops! Deu ruim, se liga no erro" + e);
            }
        }
    }
    
    public static void main(String[] args){
        TelaDeLogin appTelaDeLogin = new TelaDeLogin();
        appTelaDeLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appTelaDeLogin.setSize(200, 250);
        appTelaDeLogin.setVisible(true);

    }
}