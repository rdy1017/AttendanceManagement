package finTest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

public class StudentTab {
	private StudentTab tableFrame;
	private JTabbedPane tabbedPane;
	Database db;
	private JTextField stucodeTF;
	private JButton stucodeBtn;
	private JTable stuTable;
	private JButton addStu;
	private JButton editStu;
	private JButton deleteStu;
	private JScrollPane stuScrollPane;
	private DefaultTableModel stuModel;
	
	public StudentTab(JTabbedPane tabbedPane, Database db) {
		tableFrame = this;
		JPanel studentP = new JPanel();
		this.tabbedPane = tabbedPane;
		this.db = db;
		tabbedPane.addTab("학생관리", null, studentP, null);
		
		JLabel stuLabel = new JLabel("학     번");
		
		stucodeTF = new JTextField();
		stucodeTF.setColumns(10);
		
		stucodeBtn = new JButton("검 색");
		stucodeBtn.addActionListener(new ButtonListener());
		setstuTable();
		
		
		addStu = new JButton("추가");
		addStu.addActionListener(new ButtonListener());
		editStu = new JButton("수정");
		editStu.addActionListener(new ButtonListener());
		deleteStu = new JButton("삭제");
		deleteStu.addActionListener(new ButtonListener());
		
		GroupLayout gl_studentP = new GroupLayout(studentP);
		gl_studentP.setHorizontalGroup(
			gl_studentP.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_studentP.createSequentialGroup()
					.addGap(57)
					.addComponent(stuLabel)
					.addGap(34)
					.addComponent(stucodeTF, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 194, Short.MAX_VALUE)
					.addComponent(stucodeBtn)
					.addGap(119))
				.addGroup(gl_studentP.createSequentialGroup()
					.addGroup(gl_studentP.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(Alignment.LEADING, gl_studentP.createSequentialGroup()
							.addContainerGap()
							.addComponent(stuScrollPane, GroupLayout.PREFERRED_SIZE, 686, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_studentP.createSequentialGroup()
							.addGap(131)
							.addComponent(addStu)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(editStu)
							.addGap(135)
							.addComponent(deleteStu)
							.addGap(115)))
					.addContainerGap(17, Short.MAX_VALUE))
		);
		gl_studentP.setVerticalGroup(
			gl_studentP.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_studentP.createSequentialGroup()
					.addGap(45)
					.addGroup(gl_studentP.createParallelGroup(Alignment.BASELINE)
						.addComponent(stuLabel)
						.addComponent(stucodeTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(stucodeBtn))
					.addPreferredGap(ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
					.addComponent(stuScrollPane, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_studentP.createParallelGroup(Alignment.BASELINE)
						.addComponent(editStu)
						.addComponent(deleteStu)
						.addComponent(addStu))
					.addGap(20))
		);
		studentP.setLayout(gl_studentP);
	}
	
	//학생 탭에서 학생 테이블을 만드는 함수
	private void setstuTable() {
		String header[] = { "학번", "학년", "이름" };
		stuModel = new DefaultTableModel(header, 0) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		stuTable = new JTable(stuModel);
		stuScrollPane = new JScrollPane(stuTable);
	}
	

	public void addData(String a, String b, String c) {
		String data[] = { a, b, c };
		stuModel.addRow(data);
	}

	public void removeData() {
		int index = stuTable.getSelectedRow();
		stuModel.removeRow(index);
	}
	
	public void modifyData(String a, String b, String c) {
		String data[] = { a, b, c };
		int index = stuTable.getSelectedRow();
		stuModel.insertRow(index, data);
		stuModel.removeRow(index + 1);
	}

	public String[] getData() {
		int index = stuTable.getSelectedRow();
		String data[] = { stuModel.getValueAt(index, 0).toString(),
				stuModel.getValueAt(index, 1).toString(),
				stuModel.getValueAt(index, 2).toString() };
		return data;
	}

	class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == addStu) {
				new StudentAdd(tableFrame);
			} else if (e.getSource() == editStu) {
				int index = stuTable.getSelectedRow();
				if (index != -1)
					new StudentEdit(tableFrame);	
			} else if (e.getSource() == deleteStu) {
				int index = stuTable.getSelectedRow();
				if (index != -1)
					removeRow();
			} else if (e.getSource() == stucodeBtn){
				searchTable();
			}
		}
	}

	public void loadTable() {
		stuModel.getDataVector().clear();
		stuTable.updateUI();
		String sql = "SELECT * FROM student;";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Object[] rowData = { rs.getString(1), rs.getString(2), rs.getString(3) };
				stuModel.addRow(rowData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void searchTable() {
		stuModel.getDataVector().clear();
		stuTable.updateUI();
		String code = Util.toLikeValue(stucodeTF.getText());
		String sql = "SELECT * FROM student WHERE `Stu_num` LIKE " + code + ";";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Object[] rowData = { rs.getString(1), rs.getString(2), rs.getString(3) };
				stuModel.addRow(rowData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void removeRow() {
		int selectedRow = stuTable.getSelectedRow();
		if (selectedRow != -1) {
			String selectedCode = Util.toValue(stuModel.getValueAt(selectedRow, 0).toString());
			String sql = "DELETE FROM student WHERE `Stu_num`=" + selectedCode + ";";
			db.executeUpdate(sql);
			loadTable();
		}
	}

	public void addRow(String Stu_num, String Stu_grd, String Stu_name) {
		String sql = "INSERT INTO student VALUES ("
				
				+ Util.toValue(Stu_num) + " , " + Util.toValue(Stu_grd) + ", " + Util.toValue(Stu_name)
				+ ");";
		db.executeUpdate(sql);
		loadTable();
	}
	
	public void modifyRow(String Stu_num, String Stu_grd, String Stu_name) {
		int selectedRow = stuTable.getSelectedRow();
		if(selectedRow != -1){
			String selectedCode = Util.toValue(stuModel.getValueAt(selectedRow, 0).toString());
			String sql = "UPDATE student SET `Stu_num`=" + Util.toValue(Stu_num) + ", `Stu_grd`= " + Util.toValue(Stu_grd) + ", `Stu_name`=" + Util.toValue(Stu_name) + " WHERE `Stu_num`= " + selectedCode + ";";
			db.executeUpdate(sql);
			loadTable();
		}
	}
}
