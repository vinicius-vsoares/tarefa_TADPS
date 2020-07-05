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
import view.TelaFormulario;
import view.TelaLogin;
import view.TelaMenu;
import view.TelaSobre;

public class FormularioCriacaoAlunoController implements ActionListener {
	private Formulario form;
	private TelaFormulario telaForm;
	private FormularioDAO formDAO;
	private Aluno alu;

	public FormularioCriacaoAlunoController(TelaFormulario telaForm, Aluno alu) {
		super();
		this.alu = alu;
		this.telaForm = telaForm;
		this.form = null;
		this.telaForm.getSubmit().addActionListener(this);
		this.telaForm.getClean().addActionListener(this);
		this.telaForm.getMntmSair().addActionListener(this);
		this.telaForm.getMntmPginaInicial().addActionListener(this);
		this.telaForm.getMntmSobre().addActionListener(this);

		this.telaForm.getTextNome().setText(alu.getNome());
		this.telaForm.getTfSerie().setText(Integer.toString(alu.getSerie()));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("Enviar")) {
			if (!this.telaForm.getTextArea().getText().isEmpty() && !this.telaForm.getObs().getText().isEmpty()
					&& !this.telaForm.getTextTelefone().getText().isEmpty()
					&& this.telaForm.getComboBoxAno().getSelectedIndex() != 0
					&& this.telaForm.getComboBoxDia().getSelectedIndex() != 0
					&& this.telaForm.getComboBoxMes().getSelectedIndex() != 0
					&& !this.telaForm.getTextNovaArea().getText().isEmpty()) {

				String dia = Integer.toString(this.telaForm.getComboBoxDia().getSelectedIndex());
				String mes = Integer.toString(this.telaForm.getComboBoxMes().getSelectedIndex());
				String ano = this.telaForm.getComboBoxAno().getSelectedItem().toString();

				form = new Formulario();
				String data = ano + "-" + mes + "-" + dia;

				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();
				System.out.println(data);
				System.out.println(dateFormat.format(date));
				this.form.setData_ini(data);
				this.form.setData(dateFormat.format(date));
				this.form.setArea_atual(this.telaForm.getTextArea().getText());
				this.form.setObs(this.telaForm.getObs().getText());
				this.form.setArea_nova(this.telaForm.getTextNovaArea().getText());
				this.form.setTelefone(this.telaForm.getTextTelefone().getText());
				this.form.setAluno(alu);

				formDAO = new FormularioDAO(this.form);
				if (formDAO.insertForm()) {
					TelaMenu ma2 = new TelaMenu();
					ma2.setVisible(true);
					this.telaForm.dispose();
					ma2.setLocationRelativeTo(null);
					new MenuAlunoController(alu, ma2);
					JOptionPane.showMessageDialog(null, "Formulário cadastrado com sucesso!", "Sucesso",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Erro ao inserir. Tente novamente!", "Erro",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Existem campos não preenchidos!", "Erro",
						JOptionPane.INFORMATION_MESSAGE);
			}

		}

		if (e.getActionCommand().equals("Limpar")) {
			this.telaForm.getComboBoxAno().setSelectedIndex(0);
			this.telaForm.getComboBoxDia().setSelectedIndex(0);
			this.telaForm.getComboBoxMes().setSelectedIndex(0);
			this.telaForm.getTextNome().setText("");
			this.telaForm.getTextArea().setText("");
			this.telaForm.getTextNovaArea().setText("");
			this.telaForm.getTextTelefone().setText("");
			this.telaForm.getObs().setText("");
		}

		if (e.getActionCommand().equals("Sair")) {
			alu = null;
			TelaLogin tl = new TelaLogin();
			tl.setVisible(true);
			tl.setLocationRelativeTo(null);
			new LoginController(tl);
			this.telaForm.dispose();

			JOptionPane.showMessageDialog(null, "Sua sessão foi encerrada com sucesso!", "Sucesso",
					JOptionPane.INFORMATION_MESSAGE);
		}
		if (e.getActionCommand().equals("Página inicial")) {
			TelaMenu ma2 = new TelaMenu();
			ma2.setVisible(true);
			this.telaForm.dispose();
			ma2.setLocationRelativeTo(null);
			new MenuAlunoController(alu, ma2);
		}

		if (e.getActionCommand().equals("Sobre")) {
			TelaSobre ts = new TelaSobre();
			ts.setVisible(true);
			ts.setLocationRelativeTo(null);
		}

	}

}