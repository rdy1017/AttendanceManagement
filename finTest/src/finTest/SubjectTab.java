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

public class SubjectTab {
	private SubjectTab tableFrame;
	private JTabbedPane tabbedPane;
	Database db;
	private DefaultTableModel subModel;
	private JTable subTable;
	private JTextField subcodeTF;
	private JScrollPane subScrollPane;
	private JLabel subLabel;
	private JButton subcodeBtn;
	private JButton addSub;
	private JButton editSub;		
	private JButton deleteSub;
	StaticTab st;
	
	public SubjectTab(JTabbedPane tabbedPane, Database db) {
		tableFrame = this;
		JPanel subjectP = new JPanel();
		this.tabbedPane = tabbedPane;
		this.db = db;
		tabbedPane.addTab("과목관리", null, subjectP, null);
		
		subLabel = new JLabel("과목 코드");
		
		subcodeTF = new JTextField();
		subcodeTF.setColumns(10);
		
		subcodeBtn = new JButton("검 색");
		subcodeBtn.addActionListener(new ButtonListener());
		setSubTable();
		
		addSub = new JButton("추가");
		addSub.addActionListener(new ButtonListener());
		editSub = new JButton("수정");
		editSub.addActionListener(new ButtonListener());
		deleteSub = new JButton("삭제");
		deleteSub.addActionListener(new ButtonListener());
		
		//과목 패널 레이아웃
		GroupLayout gl_subjectP = new GroupLayout(subjectP);
		gl_subjectP.setHorizontalGroup(
			gl_subjectP.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_subjectP.createSequentialGroup()
					.addGap(57)
					.addComponent(subLabel)
					.addGap(34)
					.addComponent(subcodeTF, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 194, Short.MAX_VALUE)
					.addComponent(subcodeBtn)
					.addGap(119))
				.addGroup(gl_subjectP.createSequentialGroup()
					.addGroup(gl_subjectP.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(Alignment.LEADING, gl_subjectP.createSequentialGroup()
							.addContainerGap()
							.addComponent(subScrollPane, GroupLayout.PREFERRED_SIZE, 686, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_subjectP.createSequentialGroup()
							.addGap(131)
							.addComponent(addSub)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(editSub)
							.addGap(135)
							.addComponent(deleteSub)
							.addGap(115)))
					.addContainerGap(17, Short.MAX_VALUE))
		);
		gl_subjectP.setVerticalGroup(
			gl_subjectP.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_subjectP.createSequentialGroup()
					.addGap(45)
					.addGroup(gl_subjectP.createParallelGroup(Alignment.BASELINE)
						.addComponent(subLabel)
						.addComponent(subcodeTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(subcodeBtn))
					.addPreferredGap(ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
					.addComponent(subScrollPane, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_subjectP.createParallelGroup(Alignment.BASELINE)
						.addComponent(editSub)
						.addComponent(deleteSub)
						.addComponent(addSub))
					.addGap(20))
		);
		subjectP.setLayout(gl_subjectP);
		//과목 패널 레이아웃
	}

	//과목 패널에서 과목 테이블을 만들기 위한 함수
	private void setSubTable() {
		String header[] = { "과목코드", "과목명", "교수"};
		subModel = new DefaultTableModel(header, 0) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		subTable = new JTable(subModel);
		subScrollPane = new JScrollPane(subTable);
	}
	
	public String[] getData() {
		int index = subTable.getSelectedRow();
		String data[] = { subModel.getValueAt(index, 0).toString(), subModel.getValueAt(index, 1).toString(),
				subModel.getValueAt(index, 2).toString() };
		return data;
	}
	
	class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == addSub) {
				new SubjectAdd(tableFrame, db, st);
			} else if (e.getSource() == editSub) {
				int index = subTable.getSelectedRow();
				if (index != -1)
					new SubjectEdit(tableFrame, db, st);
			} else if (e.getSource() == deleteSub) {
				int index = subTable.getSelectedRow();
				st.removeSet(subModel.getValueAt(index, 1));
				removeRow();
			} else if (e.getSource() == subcodeBtn) {
				searchTable();
			}
		}
	}

	public void loadTable() {
		subModel.getDataVector().clear();
		subTable.updateUI();
		String sql = "SELECT sub.Sub_num, sub.Sub_name, l.Log_name FROM subject AS sub JOIN professor AS pro ON pro.Pro_num = sub.Pro_num JOIN login AS l ON l.Log_ID = pro.Pro_ID;";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Object[] rowData = { rs.getString(1), rs.getString(2), rs.getString(3) };
				subModel.addRow(rowData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//menuFrame.loadSubject();
	}

	public void searchTable() {
		subModel.getDataVector().clear();
		subTable.updateUI();
		String code = Util.toLikeValue(subcodeTF.getText());
		String sql = "SELECT sub.Sub_num, sub.Sub_name, l.Log_name FROM subject AS sub JOIN professor AS pro ON pro.Pro_num = sub.Pro_num JOIN login AS l ON l.Log_ID = pro.Pro_ID WHERE `Sub_num` LIKE " + code + ";";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Object[] rowData = { rs.getString(1), rs.getString(2), rs.getString(3) };
				subModel.addRow(rowData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void removeRow() {
		int selectedRow = subTable.getSelectedRow();
		if (selectedRow != -1) {
			String selectedCode = Util.toValue(subModel.getValueAt(selectedRow, 0).toString());
			String sql = "DELETE FROM subject WHERE `Sub_num`=" + selectedCode + ";";
			db.executeUpdate(sql);
			loadTable();
		}
	}

	public void addRow(String code, String name, String pro) {
		String sql = "INSERT INTO subject VALUES (" + Util.toValue(code) + ", " + Util.toValue(name) + ", " + Util.toValue(pro) + ");";
		db.executeUpdate(sql);
		loadTable();
	}
	
	public void addProject(String day, String lTime, String Sub_num) {
		String sql = "INSERT INTO project(day, lTime, Sub_num) VALUES (" + Util.toValue(day) + ", " + lTime + ", " + Sub_num + ");";
		db.executeUpdate(sql);
		loadTable();
	}
	
	public void modifyRow(String code, String name, String pro) {
		int selectedRow = subTable.getSelectedRow();
		if(selectedRow != -1){
			String selectedCode = Util.toValue(subModel.getValueAt(selectedRow, 0).toString());
			String sql = "UPDATE subject SET `Sub_num`= " + Util.toValue(code) + ", `Sub_name`=" + Util.toValue(name) + ", `Pro_num`=" + Util.toValue(pro) + " WHERE `Sub_num`= " 
					+ selectedCode + ";";
			db.executeUpdate(sql);
			loadTable();
		}
	}
	
	public void modifyProject(String day, String lTime, String Sub_num) {
		int selectedRow = subTable.getSelectedRow();
		if(selectedRow != -1){
			String selectedCode = Util.toValue(subModel.getValueAt(selectedRow, 0).toString());
			String sql = "UPDATE project SET `day`= " + Util.toValue(day) + ", `lTime`=" + lTime + ", `Sub_num`=" + Util.toValue(Sub_num) + " WHERE `Sub_num`= " 
					+ selectedCode + ";";
			db.executeUpdate(sql);
			loadTable();
		}
	}
	
	public void test(StaticTab st) {
		this.st = st;
	}
}
