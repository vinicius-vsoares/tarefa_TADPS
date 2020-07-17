package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import dao.FormularioDAO;
import modelo.Aluno;
import modelo.Formulario;
import view.Formulario;
import view.TelaLogin;
import view.TelaMenu;
import view.TelaInformacoesAPP;

public class FormularioCriacaoAlunoController implements ActionListener {
    private Formulario formulario;
    private TelaFormulario telaFormulario;
    private FormularioDAO formularioDAO;
    private Aluno aluno;

    public FormularioCriacaoAlunoController(TelaFormulario telaFormulario, Aluno aluno) {
        super();
        //atribuição de variáveis globais para ser utilizada no restante de código
        this.aluno = aluno;
        this.telaFormulario = telaFormulario;
        this.formulario = null;
        this.telaFormulario.getBtEnviar().addActionListener(this);
        this.telaFormulario.getBtLimpar().addActionListener(this);
        this.telaFormulario.getBtSair().addActionListener(this);
        this.telaFormulario.getBtPaginaInicial().addActionListener(this);
        this.telaFormulario.getBtSobre().addActionListener(this);
        this.telaFormulario.getTextoNome().setText(aluno.getNome());
        this.telaFormulario.getTextoSerie().setText(Integer.toString(aluno.getSerie()));
    }

	/*
		função genérica que é acionada a cada clique de um botão na tela. Para saber qual a opção selecionada é necessário verificar o comando
		atribuído para cada função (getActionCommand)
	 */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Enviar":
                opcaoEnviar();
                break;
            case "Limpar":
                opcaoLimpar();
                break;
            case "Sair":
                opcaoSair();
                break;
            case "Página inicial":
                opcaoPaginaInicial();
                break;
            case "Sobre":
                opcaoSobre();
                break;
        }
    }

    private void opcaoEnviar() {
        // verifica se existe algum campo de texto em branco
        if (!this.telaFormulario.getTextArea().getText().isEmpty() && !this.telaFormulario.getObservacao().getText().isEmpty() &&
            !this.telaFormulario.getTextTelefone().getText().isEmpty() &&
            this.telaFormulario.getComboBoxAno().getSelectedIndex() != 0 &&
            this.telaFormulario.getComboBoxDia().getSelectedIndex() != 0 &&
            this.telaFormulario.getComboBoxMes().getSelectedIndex() != 0 &&
            !this.telaFormulario.getTextNovaArea().getText().isEmpty()) {

            String dia = Integer.toString(this.telaFormulario.getComboBoxDia().getSelectedIndex());
            String mes = Integer.toString(this.telaFormulario.getComboBoxMes().getSelectedIndex());
            String ano = this.telaFormulario.getComboBoxAno().getSelectedItem().toString();

            formulario = new Formulario();
            String data = ano + "-" + mes + "-" + dia;

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            this.formulario.setData_ini(data);
            this.formulario.setData(dateFormat.format(date));
            this.formulario.setAreaAtual(this.telaFormulario.getTextArea().getText());
            this.formulario.setObservacao(this.telaFormulario.getTextObservacao().getText());
            this.formulario.setNovaArea(this.telaFormulario.getTextNovaArea().getText());
            this.formulario.setTelefone(this.telaFormulario.getTextTelefone().getText());
            this.formulario.setAluno(aluno);

            formularioDAO = new FormularioDAO(this.formulario);
            // verificação se o cadastro do formulário foi bem sucedido
            if (formularioDAO.insertForm()) {
                TelaMenu telaMenu = new TelaMenu();
                telaMenu.setVisible(true);
                this.telaFormulario.dispose();
                telaMenu.setLocationRelativeTo(null);
                new MenuAlunoController(aluno, telaMenu);
                JOptionPane.showMessageDialog(null, "Formulário cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao inserir. Tente novamente!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Existem campos não preenchidos!", "Erro", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void opcaoLimpar() {
        //define todas as variáveis para seu valor default
        this.telaFormulario.getComboBoxAno().setSelectedIndex(0);
        this.telaFormulario.getComboBoxDia().setSelectedIndex(0);
        this.telaFormulario.getComboBoxMes().setSelectedIndex(0);
        this.telaFormulario.getTextoNome().setText("");
        this.telaFormulario.getTextArea().setText("");
        this.telaFormulario.getTextNovaArea().setText("");
        this.telaFormulario.getTextTelefone().setText("");
        this.telaFormulario.getObservacao().setText("");
    }

    private void sair() {
        this.aluno = null;
        TelaLogin telaLogin = new TelaLogin();
        telaLogin.setVisible(true);
        telaLogin.setLocationRelativeTo(null);
        new LoginController(telaLogin);
        this.telaFormulario.dispose();
    }

    private void opcaoSair() {
        try {
            sair();
            JOptionPane.showMessageDialog(null, "Sua sessão foi encerrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro, tente novamente!", "Erro", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void opcaoPaginaInicial() {
        TelaMenu telaMenu = new TelaMenu();
        telaMenu.setVisible(true);
        this.telaFormulario.dispose();
        telaMenu.setLocationRelativeTo(null);
        new MenuAlunoController(aluno, telaMenu);
    }

    private void opcaoSobre() {
        TelaInformacoesApp telaInformacoesApp = new TelaInformacoesAPP();
        telaInformacoesApp.setVisible(true);
        telaInformacoesApp.setLocationRelativeTo(null);
    }

}