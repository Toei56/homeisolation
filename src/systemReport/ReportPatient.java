package systemReport;

import java.awt.*;
//กิจกรรมที่ 5
import java.awt.event.*;
import java.sql.*;
//กิจกรรมที่ 5
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import main.*;

public class ReportPatient extends JInternalFrame{
	
	//กิจกรรมที่ 4
	Connection conn = MyConnect.getConnection();	
	//กิจกรรมที่ 4
	
	JPanel pNorth,panelTable;
	JLabel labelReportMain,labelReport1,labelReport2;
	JTable tablePatient;
	DefaultTableModel modelPatient;
	
	Font fontTH = new Font("Tahoma",Font.PLAIN,12);
	
	public ReportPatient() {
		
		//กิจกรรมที่ 4
		if(conn != null) {
			System.out.println("database connected");
		}else {
			System.out.println("database not connect");
		}
		//กิจกรรมที่ 4
		
		UIManager.put("OptionPane.messageFont",fontTH);
		UIManager.put("TitledBorder.font",fontTH);
		UIManager.put("Label.font",fontTH);
		UIManager.put("Button.font",fontTH);
		UIManager.put("Table.font",fontTH);
		UIManager.put("TableHeader.font",fontTH);
		UIManager.put("TextField.font",fontTH);
		UIManager.put("RadioButton.font",fontTH);
		
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
		
		
		pNorth = new JPanel();
		pNorth.setLayout(new GridLayout(2,1,10,5));
		pNorth.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		
		labelReport1 = new JLabel("รายงานข้อมูลผู้ป่วย",0);
		labelReport1.setFont(new Font("Tahoma",Font.BOLD+Font.ITALIC,25));
		labelReport1.setForeground(Color.BLUE);
		pNorth.add(labelReport1);
		
		labelReport2= new JLabel("โปรแกรมการจองที่กักตัว สำหรับผู้ติดเชื้อโควิด-19",0);
		labelReport2.setFont(new Font("Tahoma",Font.BOLD+Font.ITALIC,20));
		labelReport2.setForeground(Color.BLUE);
		pNorth.add(labelReport2);
		
		c.add(pNorth, BorderLayout.NORTH);
		

		
		//c.add(labelReport1, BorderLayout.CENTER);
		
		//ทำตาราง
		panelTable = new JPanel();		
		JScrollPane scrollTable = new JScrollPane();
		scrollTable.setPreferredSize(new Dimension(972,520));		
		tablePatient = new JTable();
		Object data[][] = {
				{null,null,null,null,null,null},
				{null,null,null,null,null,null},
				{null,null,null,null,null,null},
				{null,null,null,null,null,null},
				{null,null,null,null,null,null},
		};
		String header[] = {"รหัสผู้ป่วย", "ชื่อ-นามสกุล", "เพศ", "ที่อยู่", "เบอร์ติดต่อ", "สถานะ"};
		modelPatient = new DefaultTableModel(data,header) {
			//ทำให้แก้ไขข้อมูลในตารางไม่ได้
			public boolean isCellEditable(int row, int columns) { 
				return false;
			} 
			//ทำให้แก้ไขข้อมูลในตารางไม่ได้
		};
		tablePatient.setModel(modelPatient);			
		
		scrollTable.setViewportView(tablePatient);
		panelTable.add(scrollTable);
		c.add(panelTable, BorderLayout.CENTER);
		//ทำตาราง
		
		//กิจกรรมที่ 5.1
		showData();
		//กิจกรรมที่ 5.1
	}
	
	//กิจกรรมที่ 5.1
	public void showData() {
		try {
			int totalRow = tablePatient.getRowCount()-1;
			while(totalRow>-1) {
				modelPatient.removeRow(totalRow);
				totalRow--;
			}

			String sql = "SELECT * FROM patient ";

			ResultSet rs = conn.createStatement().executeQuery(sql);
			int row = 0;
			while(rs.next()) {
				modelPatient.addRow(new Object[0]);
				modelPatient.setValueAt(rs.getString("PATIENT_ID"), row, 0);
				modelPatient.setValueAt(rs.getString("PATIENT_NAME"), row, 1);
				modelPatient.setValueAt(rs.getString("PATIENT_SEX"), row, 2);
				modelPatient.setValueAt(rs.getString("PATIENT_ADDR"), row, 3);
				modelPatient.setValueAt(rs.getString("PATIENT_TEL"), row, 4);
				modelPatient.setValueAt(rs.getString("PATIENT_STATUS"), row, 5);
				row++;
			}
			tablePatient.setModel(modelPatient);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	//กิจกรรมที่ 5.1
	
}
