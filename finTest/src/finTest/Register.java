package finTest;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

public class Register extends JFrame {

	private JPanel contentPane;
	private JLabel idLabel;
	private JLabel pwLabel;
	private JLabel nameLabel;
	private JLabel bdLabel;
	private JLabel phoneLabel;
	private JTextField idField;
	private JTextField pwField;
	private JTextField nameField;
	private JDateChooser bdChooser;
	private String birthDay = "";
	private SimpleDateFormat sdf;
	private JTextField phoneField;
	private JLabel lblNewLabel_5;
	private JButton joinBtn;
	private JButton exitBtn;
	private JButton duplicateBtn;
	Database db;
	private boolean available = false;

	/**
	 * Create the frame.
	 */
	public Register(Database db) {
		this.db = db;
		setTitle("회원 가입");
		setBounds(100, 100, 360, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblNewLabel_5());
		contentPane.add(getPhoneLabel());
		contentPane.add(getPhoneField());
		contentPane.add(getBdLabel());
		contentPane.add(getJoinBtn());
		contentPane.add(getExitBtn());
		contentPane.add(getIdLabel());
		contentPane.add(getIdField());
		contentPane.add(getPwLabel());
		contentPane.add(getPwField());
		contentPane.add(getNameLabel());
		contentPane.add(getNameField());
		contentPane.add(getDuplicateBtn());
		
		bdChooser = new JDateChooser();
		bdChooser.setBounds(90, 245, 115, 25);
		contentPane.add(bdChooser);
		sdf = new SimpleDateFormat("yyyy-MM-dd");
	}
	public JLabel getIdLabel() {
		if (idLabel == null) {
			idLabel = new JLabel("ID");
			idLabel.setBounds(20, 100, 40, 20);
		}
		return idLabel;
	}
	public JLabel getPwLabel() {
		if (pwLabel == null) {
			pwLabel = new JLabel("비밀번호");
			pwLabel.setBounds(20, 150, 70, 20);
		}
		return pwLabel;
	}
	public JLabel getNameLabel() {
		if (nameLabel == null) {
			nameLabel = new JLabel("이름");
			nameLabel.setBounds(20, 200, 45, 20);
		}
		return nameLabel;
	}
	public JLabel getBdLabel() {
		if (bdLabel == null) {
			bdLabel = new JLabel("생년월일");
			bdLabel.setBounds(20, 250, 70, 20);
		}
		return bdLabel;
	}
	public JLabel getPhoneLabel() {
		if (phoneLabel == null) {
			phoneLabel = new JLabel("연락처");
			phoneLabel.setBounds(20, 300, 50, 20);
		}
		return phoneLabel;
	}
	public JTextField getIdField() {
		if (idField == null) {
			idField = new JTextField();
			idField.setBounds(90, 100, 115, 25);
			idField.setColumns(10);
		}
		return idField;
	}
	public JTextField getPwField() {
		if (pwField == null) {
			pwField = new JTextField();
			pwField.setBounds(90, 150, 115, 25);
			pwField.setColumns(10);
		}
		return pwField;
	}
	public JTextField getNameField() {
		if (nameField == null) {
			nameField = new JTextField();
			nameField.setBounds(90, 200, 115, 25);
			nameField.setColumns(10);
		}
		return nameField;
	}
	public JTextField getPhoneField() {
		if (phoneField == null) {
			phoneField = new JTextField();
			phoneField.setBounds(90, 300, 115, 25);
			phoneField.setColumns(10);
		}
		return phoneField;
	}
	public JLabel getLblNewLabel_5() {
		if (lblNewLabel_5 == null) {
			lblNewLabel_5 = new JLabel("회원 가입");
			lblNewLabel_5.setBounds(91, 40, 121, 29);
			lblNewLabel_5.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		}
		return lblNewLabel_5;
	}
	public JButton getJoinBtn() {
		if (joinBtn == null) {
			joinBtn = new JButton("가입 하기");
			joinBtn.setBounds(55, 350, 100, 35);
			
			joinBtn.addActionListener(new ButtonListener());
		}
		return joinBtn;
	}
	public JButton getExitBtn() {
		if (exitBtn == null) {
			exitBtn = new JButton("나가기");
			exitBtn.setBounds(200, 350, 90, 35);
			
			exitBtn.addActionListener(new ButtonListener());
		}
		return exitBtn;
	}
	public JButton getDuplicateBtn() {
		if (duplicateBtn == null) {
			duplicateBtn = new JButton("중복 확인");
			duplicateBtn.setBounds(220, 100, 105, 30);
			
			duplicateBtn.addActionListener(new ButtonListener());
		}
		return duplicateBtn;
	}
	
	class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == duplicateBtn) {
				String checkid = idField.getText();
				int check = 0;
				String sql = "SELECT EXISTS(SELECT * FROM login WHERE Log_ID = '" + checkid + "');";
				try {
					ResultSet rs = db.executeQuery(sql);
					while (rs.next()) {
						check = rs.getInt(1);
					}
				} catch (SQLException sqle) {
					// TODO Auto-generated catch block
					sqle.printStackTrace();
				}
				if (check == 1) {
					JOptionPane.showMessageDialog(null, "사용중인 아이디입니다.");
				} else if (idField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "아이디를 입력해주세요.");
				}				
				else {
					JOptionPane.showMessageDialog(null, "사용 가능한 아이디입니다.");
					available = true;
				}
			}
			
			if (e.getSource() == joinBtn) {
				if (available) {
					if (checkFormat()) {
						if (bdChooser.getDate() != null)
							birthDay = sdf.format(bdChooser.getDate());
						String sql = "INSERT INTO login VALUES('" + idField.getText() + "', '" + pwField.getText() + "', '" + nameField.getText()
						+ "', " + Util.toValue(birthDay) + ", '" + phoneField.getText() + "');";
						String sql2 = "INSERT INTO professor(Pro_ID) VALUES('" + idField.getText() +"');";
						db.executeUpdate(sql);
						db.executeUpdate(sql2);

						JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다.");
					} else {
						JOptionPane.showMessageDialog(null, "가입정보를 확인해주시기 바랍니다.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "중복 확인을 해주시기 바랍니다.");
				}
			}
			
			if (e.getSource() == exitBtn) {
				dispose();
			}
		}
	}
	
	private boolean checkFormat() {
		if (idField.getText().length() > 12) {
			JOptionPane.showMessageDialog(null, "아이디는 12자리 까지 가능합니다.");
			return false;
		}
		if (idField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "아이디를 입력해주세요.");
			return false;
		}
		if (pwField.getText().length() > 12) {
			JOptionPane.showMessageDialog(null, "비밀번호는 12자리 까지 가능합니다.");
			return false;
		}
		if (pwField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요.");
			return false;
		}
		if (nameField.getText().length() > 10) {
			JOptionPane.showMessageDialog(null, "이름은 10자리 까지 가능합니다.");
			return false;
		}
		if (nameField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "이름을 입력해주세요.");
			return false;
		}
		if (bdChooser.getDate() == null) {
			JOptionPane.showMessageDialog(null, "생년월일을 선택해주세요.");
			return false;
		}
		if (phoneField.getText().length() > 13) {
			JOptionPane.showMessageDialog(null, "전화번호는 13자리 까지 가능합니다.");
			return false;
		}
		return true;	
	}
}
