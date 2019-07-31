package finTest;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.toedter.calendar.JCalendar;

public class FirstTab {
	private JTabbedPane tabbedPane;
	Database db;
	private JCalendar calendar;
	private DefaultTableModel timeModel;
	private DefaultTableModel attendModel;
	private JScrollPane timeScrollPane;
	private JTable timeTable;
	private JTable attendTable;
	private JPanel AttendanceP;
	private JButton saveButton;
	private String selectedSub;
	private SimpleDateFormat sdf;
	private SimpleDateFormat sdf2;
	private SimpleDateFormat sdf3;
	private String date;
	private String time;
	private String currentMonth;
	private String clickDay;
	private String clickDate;
	
	public static final long CURRENT_TIME_MILLIS = System.currentTimeMillis();
	public FirstTab(JTabbedPane tabbedPane, Database db) {
		JPanel mainP = new JPanel();
		this.tabbedPane = tabbedPane;
		this.db = db;
		tabbedPane.addTab("메인", null, mainP, null);
		
		Thread cm = new Thread(new ClockMenu());
		cm.start();
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf2 = new SimpleDateFormat("HH:mm:ss");
		sdf3 = new SimpleDateFormat("yyyy-MM");
		
		calendar = new JCalendar();
		calendar.getDayChooser().addPropertyChangeListener("day", new PropertyChangeListener() {
		    @Override
		    public void propertyChange(PropertyChangeEvent e) {
		    	clickDay = e.getNewValue().toString();
				currentMonth = sdf3.format(calendar.getDate());
				clickDate = currentMonth + "-" + clickDay;
				Calendar c = Calendar.getInstance();
				Date mydate;
				try {
					mydate = sdf.parse(clickDate);
					c.setTime(mydate);
					int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
					loadTimeTable(dayOfWeek);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
		});
		
		timeScrollPane = new JScrollPane();
		
		setTimeTable();
		loadTimeTable(Calendar.DAY_OF_WEEK);
		timeTable.addMouseListener(new MouseClick());
		
		AttendanceP = new JPanel();
		
		saveButton = new JButton("저장하기");
		saveButton.addActionListener(new ButtonListener());
						
		GroupLayout gl_mainP = new GroupLayout(mainP);
		
		//메인 패널 레이아웃
		gl_mainP.setHorizontalGroup(
			gl_mainP.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainP.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_mainP.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_mainP.createSequentialGroup()
							.addComponent(AttendanceP, GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_mainP.createSequentialGroup()
							.addComponent(calendar, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE)
							.addGap(30)
							.addComponent(timeScrollPane, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
							.addComponent(saveButton)
							.addGap(29))))
		);
		gl_mainP.setVerticalGroup(
			gl_mainP.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_mainP.createSequentialGroup()
					.addGroup(gl_mainP.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_mainP.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_mainP.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(timeScrollPane, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
								.addComponent(calendar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addGroup(gl_mainP.createSequentialGroup()
							.addGap(71)
							.addComponent(saveButton)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(AttendanceP, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		mainP.setLayout(gl_mainP);
		createattendTable();
	}
	
	//시간표 테이블을 만드는 함수
	private void setTimeTable() {
		String header[] = { "교시", "수업명" };
		timeModel = new DefaultTableModel(header, 0) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		timeTable = new JTable(timeModel);
		timeScrollPane = new JScrollPane(timeTable);
	}
	
	//시간표를 불러오는 함수
	public void loadTimeTable(int dayOfWeek) {
		timeModel.getDataVector().clear();
		timeTable.updateUI();
		String sql = "SELECT pro.lTime, sub.Sub_name " +
				"FROM project as pro " +
				"JOIN subject as sub " +
				"ON pro.Sub_num = sub.Sub_num " +
				"WHERE pro.day = '" + whatDay(dayOfWeek) + "' ORDER BY pro.lTime ASC;";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Object[] rowData = { rs.getString(1), rs.getString(2) };
				timeModel.addRow(rowData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//메인 패널의 출석부를 만드는 함수
	private void createattendTable() {
		String header[] = { "학번", "이름", "출석여부" };
		attendModel = new DefaultTableModel(header, 0) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				if (mColIndex == 2)
					return true;
				else
					return false;
			}

			public Class<?> getColumnClass(int col) {
				Object obj = getValueAt(0, col);
				return obj.getClass();
			}

		};
		attendTable = new JTable(attendModel);
		JScrollPane attendScrollPane = new JScrollPane(attendTable);

		String[] values = new String[] { "출석", "지각", "결석" };
		TableColumn col = attendTable.getColumnModel().getColumn(2);
		col.setCellEditor(new AttendComboBoxEditor(values));
		col.setCellRenderer(new AttendComboBoxRenderer(values));
		GroupLayout gl_AttendanceP = new GroupLayout(AttendanceP);
		gl_AttendanceP.setHorizontalGroup(
			gl_AttendanceP.createParallelGroup(Alignment.LEADING)
				.addComponent(attendScrollPane, GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)
		);
		gl_AttendanceP.setVerticalGroup(
			gl_AttendanceP.createParallelGroup(Alignment.LEADING)
				.addComponent(attendScrollPane, GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
		);
		AttendanceP.setLayout(gl_AttendanceP);
	}
	
	//출석부를 부르는 함수
	public void loadattendTable() {
		attendModel.getDataVector().clear();
		attendTable.updateUI();
		try {
			String sql = "SELECT stu.Stu_num, stu.Stu_name" + 
					" FROM student as stu ORDER BY stu.Stu_num ASC;";
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Object[] rowData = { rs.getString(1), rs.getString(2), "출석" };
				attendModel.addRow(rowData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	//출석부를 저장하는 함수
	public void save() {
		date = sdf.format(calendar.getDate());
		time = sdf2.format(calendar.getDate());
		for (int i = 0; i < attendModel.getRowCount(); i++) {
			String stdNum = Util.toValue(attendModel.getValueAt(i, 0).toString());
			//String stdName = Util.toValue(attendModel.getValueAt(i, 2).toString());
			String attend = Util.toValue(attendModel.getValueAt(i, 2).toString());
			String sql = "INSERT INTO attendance(Pro_num, Stu_num, aDate, aTime, attend) " + 
					"SELECT Pro_num, " + stdNum + ", '" + date + "', '" + time + "', " + attend + " " + 
					"FROM project " + 
					"WHERE Sub_num = "
					+ changeSubNum() + ";";
			db.executeUpdate(sql);
		}
	}
	
	//오늘의 요일을 체크(일요일 1 ~ 토요일 7)
	private String whatDay(int dayOfWeek) {
		String today;
		switch (dayOfWeek) {
		case 1: today = "일";
		break;
		case 2: today = "월";
		break;
		case 3: today = "화";
		break;
		case 4: today = "수";
		break;
		case 5: today = "목";
		break;
		case 6: today = "금";
		break;
		case 7: today = "토";
		break;
		default: today = "N";
		break;
		}
		return today;
	}
	
	//메인 패널의 출석부 테이블에서 출석여부를 정하는 콤보박스를 위한 클래스
	class AttendComboBoxRenderer extends JComboBox<String>implements TableCellRenderer {
		public AttendComboBoxRenderer(String[] items) {
			super(items);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				super.setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(table.getBackground());
			}
			setSelectedItem(value);
			return this;
		}
	}
	//메인 패널의 출석부 테이블에서 출석여부를 정하는 콤보박스를 위한 클래스
	class AttendComboBoxEditor extends DefaultCellEditor {
		public AttendComboBoxEditor(String[] items) {
			super(new JComboBox<String>(items));
		}
	}

	//시간표에서 과목을 더블클릭할때 이벤트
	class MouseClick implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if (e.getClickCount() == 2) {
				JTable target = (JTable)e.getSource();
				int row = target.getSelectedRow();
				selectedSub = GetData(target, row, 1).toString();
				loadattendTable();
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	//시간표를 더블클릭 했을때 해당 시간의 수업명을 저장하는 함수
	public Object GetData(JTable table, int row_index, int col_index){
		return table.getModel().getValueAt(row_index, col_index);
	}
	
	private String changeSubNum() {
		String subNum = "0";
		try {
			String sql = "SELECT Sub_num FROM subject WHERE Sub_name = '" + selectedSub + "';";
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				subNum = rs.getString("Sub_num");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return subNum;
	}
	
	//저장하기 이벤트
	class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == saveButton) {
				save();
			}
		}
	}
	class ClockMenu implements Runnable {
		ClockMenu() {
		}
		
		public void run() {
			while (true) {
				GregorianCalendar gc = new GregorianCalendar();
				try {
					time = gc.get(gc.HOUR) + ":" + gc.get(gc.MINUTE) + ":" + gc.get(gc.SECOND);
					Thread.sleep(1000);
				} catch (InterruptedException ie) {
					
				}
			}
		}
	}
}
