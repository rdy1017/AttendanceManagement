package finTest;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

public class PageBundle extends JFrame {

	private JPanel contentPane;
	private PageBundle mp;
	Database db;
	
	/**
	 * Create the frame.
	 */
	public PageBundle(Database db) {
		mp = this;
		this.db = db;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("�������ý���");
		setBounds(100, 100, 750, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
		);
				
		//���� �� ����
		FirstTab maintab = new FirstTab(tabbedPane, db);
		//���� �� ����
		SubjectTab subtab = new SubjectTab(tabbedPane, db);
		//�л� �� ����
		StudentTab stutab = new StudentTab(tabbedPane, db);		
		//��� �� ����
		StaticTab statictab = new StaticTab(tabbedPane, db);
		subtab.test(statictab);
		
//	    tabbedPane.addChangeListener(new ChangeListener() {
//	        public void stateChanged(ChangeEvent e) {
//	            if (tabbedPane.getSelectedIndex() == 3) {
//	            	statictab.reset();
//	            }
//	        	System.out.println("Tab: " + tabbedPane.getSelectedIndex());
//	        }
//	    });
				
		contentPane.setLayout(gl_contentPane);
	}
}
