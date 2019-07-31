package finTest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StudentEdit extends JFrame{
	private JPanel panel; // �ٸ� ������Ʈ�� ���� �� �ִ� �� �κ�
	private JTextField StuNumField; // �۾��� �Է� �� �� �ִ� �ؽ�Ʈ�ʵ�
	private JTextField StuGraField;
	private JTextField StuNameField;
	private JButton saveButton;
	private JButton cancelButton;
	private StudentTab tableFrame;
	private StudentEdit subjectFrame;

	public StudentEdit(StudentTab tableFrame) {
		this.tableFrame = tableFrame;
		subjectFrame = this;
		setTitle("�л�");
		setSize(400, 300); 

		setPanel();
		setStuNumLabel();
		setStuGraLabel();
		setStuNameLabel();
		setStuNumField();
		setStuGraField();
		setStuNameField();
		setSaveButton();
		setCancelButton();
		
		String data[] = tableFrame.getData();
		StuNumField.setText(data[0]);
		StuGraField.setText(data[1]);
		StuNameField.setText(data[2]);
		
		setVisible(true);
	}

	private void setPanel() {
		panel = new JPanel();
		panel.setLayout(null);
		add(panel);
	}

	private void setStuNumLabel() {
		JLabel label = new JLabel();
		label.setText("�й�");
		label.setBounds(20, 10, 100, 100);
		panel.add(label); 
	}

	private void setStuGraLabel() {
		JLabel label = new JLabel();
		label.setText("�г�"); 
		label.setBounds(20, 50, 100, 100);
		panel.add(label);
	}

	private void setStuNameLabel() {
		JLabel label = new JLabel(); 
		label.setText("�̸�");
		label.setBounds(20, 90, 100, 100); 
		panel.add(label); 
	}

	private void setStuNumField() {
		StuNumField = new JTextField();
		StuNumField.setBounds(80, 45, 200, 30);
		panel.add(StuNumField);
	}

	private void setStuGraField() {
		StuGraField = new JTextField();
		StuGraField.setBounds(80, 85, 200, 30);
		panel.add(StuGraField);
	}

	private void setStuNameField() {
		StuNameField = new JTextField();
		StuNameField.setBounds(80, 125, 200, 30);
		panel.add(StuNameField);
	}

	private void setSaveButton() {
		saveButton = new JButton();
		saveButton.setText("����");
		saveButton.setBounds(50, 210, 100, 30); 
		saveButton.addActionListener(new ButtonListener());
		panel.add(saveButton);
	}

	private void setCancelButton() {
		cancelButton = new JButton();
		cancelButton.setText("���");
		cancelButton.setBounds(250, 210, 100, 30); 
		cancelButton.addActionListener(new ButtonListener());
		panel.add(cancelButton);
	}

	class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == saveButton) {
				tableFrame.modifyRow(StuNumField.getText(), StuGraField.getText(),
						StuNameField.getText());
				subjectFrame.dispose();
			} else if (e.getSource() == cancelButton)
				subjectFrame.dispose();

		}
	}
}
