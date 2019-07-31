package finTest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SubjectAdd extends JFrame{
	private JPanel panel; // �ٸ� ������Ʈ�� ���� �� �ִ� �� �κ�
	Database db;
	private JTextField textField; // �۾��� �Է� �� �� �ִ� �ؽ�Ʈ�ʵ�
	private JTextField nameTextField;
	private JButton saveButton;
	private JButton cancelButton;
	private SubjectTab tableFrame;
	private SubjectAdd subjectFrame;
	private JComboBox profComboBox;
	private ArrayList<String> profList;
	private JComboBox dayComboBox;
	private JComboBox ltimeComboBox;
	StaticTab st;

	public SubjectAdd(SubjectTab tableFrame, Database db, StaticTab st) {
		this.tableFrame = tableFrame;
		this.db = db;
		this.st = st;
		subjectFrame = this;
		profList = new ArrayList<String>();
		setTitle("����"); // �������� Ÿ��Ʋ ����
		setSize(400, 300); // �������� ũ�� ����

		setPanel(); // ���� �ǳ��� �����ϴ� �κ�
		setSubNumLabel(); // ���� �ϳ� �߰��ϴ� �κ�
		setSubNameLabel();
		setSubProfLabel();
		setTextField(); // �ؽ�Ʈ �ʵ� �ϳ��� �߰��ϴ� �κ�
		setNameTextField();
		setprofComboBox();
		setdayComboBox();
		setltimeComboBox();
		setSaveButton();
		setCancelButton();
		
		setVisible(true); // JFrame�� �⺻������ ������ �־� ���̰� �����Ͼ� ȭ�鿡 ��Ÿ����.
	}

	private void setPanel() {
		panel = new JPanel(); // �ǳ� ����
		panel.setLayout(null); // �ǳ��� ���̾ƿ��� ���� ���� ����
		// null�� �����ؾ� ��ǥ�� �̿��� �����Ӱ� ��ġ ����
		getContentPane().add(panel); // ������� �ǳ��� �������� ���̴� �κ�
		// add�տ� "��ü." �̺κ��� ���� ������ �� Ŭ���� ��ü�� �������̱� ������ this.add(panel)���� this��
		// ������ ��
	}

	private void setSubNumLabel() {
		JLabel label = new JLabel(); // �� ����
		label.setText("�����ڵ�"); // ���� �۾� ����
		label.setBounds(20, 0, 100, 100); // �տ� �� �κ��� ��ġ x,y �ڿ� �� �κ��� ũ�� x,y
		panel.add(label); // �ǳ��� �󺧿� ���δ�.
	}

	private void setSubNameLabel() {
		JLabel label = new JLabel(); // �� ����
		label.setText("�����"); // ���� �۾� ����
		label.setBounds(20, 40, 100, 100); // �տ� �� �κ��� ��ġ x,y �ڿ� �� �κ��� ũ�� x,y
		panel.add(label); // �ǳ��� �󺧿� ���δ�.
	}

	private void setSubProfLabel() {
		JLabel label = new JLabel(); // �� ����
		label.setText("����"); // ���� �۾� ����
		label.setBounds(20, 80, 100, 100); // �տ� �� �κ��� ��ġ x,y �ڿ� �� �κ��� ũ�� x,y
		panel.add(label); // �ǳ��� �󺧿� ���δ�.
	}

	private void setTextField() {
		textField = new JTextField();
		textField.setBounds(150, 35, 200, 30);
		panel.add(textField);
	}

	private void setNameTextField() {
		nameTextField = new JTextField();
		nameTextField.setBounds(150, 75, 200, 30);
		panel.add(nameTextField);
	}

	private void setSaveButton() {
		saveButton = new JButton(); // �� ����
		saveButton.setText("����"); // ���� �۾� ����
		saveButton.setBounds(50, 210, 100, 30); // �տ� �� �κ��� ��ġ x,y �ڿ� �� �κ��� ũ��
												// x,y
		saveButton.addActionListener(new ButtonListener());
		panel.add(saveButton); // �ǳ��� �󺧿� ���δ�.
	}

	private void setCancelButton() {
		cancelButton = new JButton(); // �� ����
		cancelButton.setText("���"); // ���� �۾� ����
		cancelButton.setBounds(250, 210, 100, 30); // �տ� �� �κ��� ��ġ x,y �ڿ� �� �κ���
													// ũ�� x,y
		cancelButton.addActionListener(new ButtonListener());
		panel.add(cancelButton); // �ǳ��� �󺧿� ���δ�.
	}
	
	private void setprofComboBox() {
		String sql = "SELECT Log_name FROM login;";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				profList.add(rs.getString("Log_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		profComboBox = new JComboBox(profList.toArray(new String[profList.size()]));
		profComboBox.setBounds(150, 115, 200, 30);
		
		panel.add(profComboBox);
	}
	
	private void setdayComboBox() {
		String[] day = { "��", "ȭ", "��", "��", "��" };
		dayComboBox = new JComboBox(day);
		dayComboBox.setBounds(20, 160, 100, 30);
		
		panel.add(dayComboBox);
	}
	
	private void setltimeComboBox() {
		String[] ltime = { "1����", "2����", "3����", "4����", "5����", "6����", "7����", "8����", "9����" };
		ltimeComboBox = new JComboBox(ltime);
		ltimeComboBox.setBounds(250, 160, 100, 30);
		
		panel.add(ltimeComboBox);
	}
	
	private String changeLTime() {
		String ltime = ltimeComboBox.getSelectedItem().toString().substring(0, 1);
		return ltime;		
	}
	
	private String findProNum() {
		String proName = profComboBox.getSelectedItem().toString();
		String ProNum = "";
		String sql = "SELECT p.Pro_num FROM professor as p JOIN login as l ON p.Pro_ID = l.Log_ID WHERE l.Log_name = " + Util.toValue(proName) + ";";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				ProNum = rs.getString("Pro_num");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ProNum;
	}

	class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == saveButton) {
				tableFrame.addRow(textField.getText(), nameTextField.getText(), findProNum());
				tableFrame.addProject(dayComboBox.getSelectedItem().toString(), changeLTime(), textField.getText());
				st.addSet(nameTextField.getText());
				subjectFrame.dispose();
			} else if (e.getSource() == cancelButton)
				subjectFrame.dispose();

		}
	}
}
