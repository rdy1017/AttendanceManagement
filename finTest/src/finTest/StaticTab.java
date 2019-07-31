package finTest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class StaticTab extends JFrame {
	private StaticTab statictab;
	Database db;
	private JTabbedPane tabbedPane;
	private DefaultTableModel staticModel;
	private JTable staticTable;
	private JScrollPane staticScrollPane;
	private JLabel subject;
	public JComboBox subComboBox;
	private JLabel stu_name;
	private JTextField searchName;
	private JButton searchBtn;
	private String selectedSubject;
	private String selectedSubNum;
	public ArrayList<String> subjectList;
	
	public StaticTab(JTabbedPane tabbedPane, Database db) {
		statictab = this;
		JPanel statP = new JPanel();
		this.tabbedPane = tabbedPane;
		this.db = db;
		tabbedPane.addTab("통계", null, statP, null);
		subjectList = new ArrayList<String>();
		
		subject = new JLabel("과목 선택");
		comboBoxSetup();
		stu_name = new JLabel("이름");		
		searchName = new JTextField();
		searchName.setColumns(10);
		searchBtn = new JButton("검색");
		searchBtn.addActionListener(new ButtonListener());
		
		setstaticTable();
		
		GroupLayout gl_statP = new GroupLayout(statP);
		gl_statP.setHorizontalGroup(
			gl_statP.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_statP.createSequentialGroup()
					.addContainerGap()
					.addComponent(staticScrollPane, GroupLayout.PREFERRED_SIZE, 689, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(gl_statP.createSequentialGroup()
					.addContainerGap()
					.addComponent(subject)
					.addGap(52)
					.addComponent(subComboBox, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
					.addGap(63)
					.addComponent(stu_name)
					.addGap(43)
					.addComponent(searchName, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
					.addGap(42)
					.addComponent(searchBtn)
					.addContainerGap(56, Short.MAX_VALUE))
		);
		gl_statP.setVerticalGroup(
			gl_statP.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_statP.createSequentialGroup()
					.addGap(19)
					.addGroup(gl_statP.createParallelGroup(Alignment.BASELINE)
						.addComponent(subject)
						.addComponent(stu_name)
						.addComponent(subComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(searchName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(searchBtn))
					.addGap(18)
					.addComponent(staticScrollPane, GroupLayout.PREFERRED_SIZE, 307, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(39, Short.MAX_VALUE))
		);
		statP.setLayout(gl_statP);
	}
	
	//통계 탭에서 테이블을 생성하는 함수
	private void setstaticTable() {
		String header[] = { "학번", "이름", "날짜", "교시", "출석여부" };
		staticModel = new DefaultTableModel(header, 0) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		staticTable = new JTable(staticModel);
		staticScrollPane = new JScrollPane(staticTable);
	}
	
	public void loadTable() {

		staticModel.getDataVector().clear();
		staticTable.updateUI();
		String sql = "SELECT a.Stu_num, stu.Stu_name, a.aDate, pro.lTime, a.attend " +
				"FROM attendance as a " + 
				"JOIN student as stu " + 
				"ON a.Stu_num = stu.Stu_num " + 
				"JOIN project as pro " +
				"ON a.Pro_num = pro.Pro_num " +
				"WHERE pro.Sub_num = " + selectedSubNum;
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				String stdNum = rs.getString("a.Stu_num");
				String name = rs.getString("stu.Stu_name");
				String date = rs.getString("a.aDate");
				String lTime = rs.getString("pro.lTime");
				String attend = rs.getString("a.attend");
				Object data[] = { stdNum, name, date, lTime, attend };
				staticModel.addRow(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void searchToName() {

		staticModel.getDataVector().clear();
		staticTable.updateUI();
		String sql = "SELECT a.Stu_num, stu.Stu_name, a.aDate, pro.lTime, a.attend " +
				"FROM attendance as a " + 
				"JOIN student as stu " + 
				"ON a.Stu_num = stu.Stu_num " + 
				"JOIN project as pro " +
				"ON a.Pro_num = pro.Pro_num " +
				"WHERE stu.Stu_name LIKE " + Util.toLikeValue(searchName.getText());
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				String stdNum = rs.getString("a.Stu_num");
				String name = rs.getString("stu.Stu_name");
				String date = rs.getString("a.aDate");
				String lTime = rs.getString("pro.lTime");
				String attend = rs.getString("a.attend");
				Object data[] = { stdNum, name, date, lTime, attend };
				staticModel.addRow(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void comboBoxSetup() {
		String sql = "SELECT Sub_name FROM subject";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				subjectList.add(rs.getString("Sub_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		subComboBox = new JComboBox(subjectList.toArray(new String[subjectList.size()]));
		subComboBox.addActionListener(new ComboBoxListener());
	}
	
	public void removeSet(Object ob) {
		this.subComboBox.removeItem(ob);
		this.subComboBox.revalidate();
	}
	
	public void addSet(Object ob) {
		this.subComboBox.addItem(ob);
		this.subComboBox.revalidate();
	}
	
	class ComboBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JComboBox jcb =(JComboBox)e.getSource();
            selectedSubject = jcb.getSelectedItem().toString();
            String subName = Util.toValue(selectedSubject);
            String sql = "SELECT Sub_num FROM subject WHERE Sub_name=" + subName;
            try {
            	ResultSet rs = db.executeQuery(sql);
            	while(rs.next()) {
            		selectedSubNum = rs.getString("Sub_num");
            	}
            } catch (SQLException se) {
    			// TODO Auto-generated catch block
    			se.printStackTrace();
    		}
            loadTable();
		}
	}
	
	class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == searchBtn) {
				searchToName();
			}
		}
	}
}
