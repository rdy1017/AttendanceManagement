package finTest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StudentAdd extends JFrame {
	private JPanel panel; 
	private JTextField numField;
	private JTextField departField;
	private JTextField gradeField;
	private JTextField stuNameField;
	private JButton saveButton;
	private JButton cancelButton;
	private StudentTab tableFrame;
	private StudentAdd studentFrame;

	public StudentAdd(StudentTab tableFrame) {
		this.tableFrame = tableFrame;
		studentFrame = this;
		setTitle("학생");
		setSize(400, 300);

		setPanel();
		setStuNumLabel();
		setStuGradeLabel();
		setStuNameLabel();
		setStuNumTextField();
		setStuGradeTextField();
		setStuNameTextField();
		setSaveButton();
		setCancelButton();
		
		setVisible(true); 
	}

	private void setPanel() {
		panel = new JPanel();
		panel.setLayout(null);
		add(panel);
	}

	private void setStuNumLabel() {
		JLabel label = new JLabel(); // 라벨 생성
		label.setText("학번"); // 라벨의 글씨 설정
		label.setBounds(20, 10, 100, 100); // 앞에 두 부분은 위치 x,y 뒤에 두 부분은 크기 x,y
		panel.add(label); // 판낼에 라벨에 붙인다.
	}

	private void setStuGradeLabel() {
		JLabel label = new JLabel(); // 라벨 생성
		label.setText("학년"); // 라벨의 글씨 설정
		label.setBounds(20, 50, 100, 100); // 앞에 두 부분은 위치 x,y 뒤에 두 부분은 크기 x,y
		panel.add(label); // 판낼에 라벨에 붙인다.
	}

	private void setStuNameLabel() {
		JLabel label = new JLabel(); 
		label.setText("이름"); 
		label.setBounds(20, 90, 100, 100); 
		panel.add(label);
	}

	private void setStuNumTextField() {
		numField = new JTextField();
		numField.setBounds(80, 45, 200, 30);
		panel.add(numField);
	}

	private void setStuGradeTextField() {
		gradeField = new JTextField();
		gradeField.setBounds(80, 85, 200, 30);
		panel.add(gradeField);
	}

	private void setStuNameTextField() {
		stuNameField = new JTextField();
		stuNameField.setBounds(80, 125, 200, 30);
		panel.add(stuNameField);
	}

	private void setSaveButton() {
		saveButton = new JButton(); 
		saveButton.setText("저장"); 
		saveButton.setBounds(50, 210, 100, 30);
		saveButton.addActionListener(new ButtonListener());
		panel.add(saveButton); 
	}

	private void setCancelButton() {
		cancelButton = new JButton(); 
		cancelButton.setText("취소"); 
		cancelButton.setBounds(250, 210, 100, 30); 
													
		cancelButton.addActionListener(new ButtonListener());
		panel.add(cancelButton); 
	}

	class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == saveButton) {
				tableFrame.addRow(numField.getText(), gradeField.getText(),
						stuNameField.getText());
				studentFrame.dispose();
			} else if (e.getSource() == cancelButton)
				studentFrame.dispose();

		}
	}
}
