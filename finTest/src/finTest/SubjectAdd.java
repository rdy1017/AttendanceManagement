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
	private JPanel panel; // 다른 컴포넌트를 붙일 수 있는 판 부분
	Database db;
	private JTextField textField; // 글씨를 입력 할 수 있는 텍스트필드
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
		setTitle("과목"); // 프레임의 타이틀 설정
		setSize(400, 300); // 프레임의 크기 설정

		setPanel(); // 메인 판넬을 생성하는 부분
		setSubNumLabel(); // 라벨을 하나 추가하는 부분
		setSubNameLabel();
		setSubProfLabel();
		setTextField(); // 텍스트 필드 하나를 추가하는 부분
		setNameTextField();
		setprofComboBox();
		setdayComboBox();
		setltimeComboBox();
		setSaveButton();
		setCancelButton();
		
		setVisible(true); // JFrame은 기본적으로 숨겨져 있어 보이게 설정하야 화면에 나타난다.
	}

	private void setPanel() {
		panel = new JPanel(); // 판낼 생성
		panel.setLayout(null); // 판낼의 레이아웃을 없을 으로 설정
		// null로 설정해야 좌표를 이용해 자유롭게 배치 가능
		getContentPane().add(panel); // 만들어진 판낼을 프레임이 붙이는 부분
		// add앞에 "객체." 이부분이 없는 이유는 이 클래스 자체가 프레임이기 때문에 this.add(panel)에서 this가
		// 생략된 것
	}

	private void setSubNumLabel() {
		JLabel label = new JLabel(); // 라벨 생성
		label.setText("과목코드"); // 라벨의 글씨 설정
		label.setBounds(20, 0, 100, 100); // 앞에 두 부분은 위치 x,y 뒤에 두 부분은 크기 x,y
		panel.add(label); // 판낼에 라벨에 붙인다.
	}

	private void setSubNameLabel() {
		JLabel label = new JLabel(); // 라벨 생성
		label.setText("과목명"); // 라벨의 글씨 설정
		label.setBounds(20, 40, 100, 100); // 앞에 두 부분은 위치 x,y 뒤에 두 부분은 크기 x,y
		panel.add(label); // 판낼에 라벨에 붙인다.
	}

	private void setSubProfLabel() {
		JLabel label = new JLabel(); // 라벨 생성
		label.setText("교수"); // 라벨의 글씨 설정
		label.setBounds(20, 80, 100, 100); // 앞에 두 부분은 위치 x,y 뒤에 두 부분은 크기 x,y
		panel.add(label); // 판낼에 라벨에 붙인다.
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
		saveButton = new JButton(); // 라벨 생성
		saveButton.setText("저장"); // 라벨의 글씨 설정
		saveButton.setBounds(50, 210, 100, 30); // 앞에 두 부분은 위치 x,y 뒤에 두 부분은 크기
												// x,y
		saveButton.addActionListener(new ButtonListener());
		panel.add(saveButton); // 판낼에 라벨에 붙인다.
	}

	private void setCancelButton() {
		cancelButton = new JButton(); // 라벨 생성
		cancelButton.setText("취소"); // 라벨의 글씨 설정
		cancelButton.setBounds(250, 210, 100, 30); // 앞에 두 부분은 위치 x,y 뒤에 두 부분은
													// 크기 x,y
		cancelButton.addActionListener(new ButtonListener());
		panel.add(cancelButton); // 판낼에 라벨에 붙인다.
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
		String[] day = { "월", "화", "수", "목", "금" };
		dayComboBox = new JComboBox(day);
		dayComboBox.setBounds(20, 160, 100, 30);
		
		panel.add(dayComboBox);
	}
	
	private void setltimeComboBox() {
		String[] ltime = { "1교시", "2교시", "3교시", "4교시", "5교시", "6교시", "7교시", "8교시", "9교시" };
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
