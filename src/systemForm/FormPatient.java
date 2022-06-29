package systemForm;

import java.awt.*;
//กิจกรรมที่ 5
import java.awt.event.*;
import java.sql.*;
//กิจกรรมที่ 5
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import main.*;

public class FormPatient extends JInternalFrame{
	
	//กิจกรรมที่ 4
	Connection conn = MyConnect.getConnection();	
	//กิจกรรมที่ 4
	
	JTextField txtPatientID,txtPatientName,txtPatientSex,txtPatientAddr,txtPatientTel,txtSearchPatient;
	JRadioButton rdM,rdF;
	ButtonGroup groupSex;
	JPanel pNorth,panelForm,panelSearch,panelBuntton,panelTable,panelSex,panelForm2;
	JLabel labelPatientID,labelPatientName,labelPatientSex,labelPatientAddr,labelPatientTel,labelForm1;
	JButton btnSave,btnEdit,btnDelete,btnClear;
	JTable tablePatient;
	DefaultTableModel modelPatient;
	
	Font fontTH = new Font("Tahoma",Font.PLAIN,12);
	
	
	public FormPatient() {
		
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
		pNorth.setLayout(new GridLayout(1,2,10,5));
		pNorth.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		
		panelForm = new JPanel();
		panelForm.setLayout(new GridLayout(5,2,10,5));
		
		labelPatientID = new JLabel("รหัสผู้ป่วย :");
		txtPatientID = new JTextField();
		txtPatientID.setPreferredSize(new Dimension(200,30));		
		panelForm.add(labelPatientID);
		panelForm.add(txtPatientID);
		txtPatientID.setEditable(false);
		
		labelPatientName = new JLabel("ชื่อ-นามสกุล :");
		txtPatientName = new JTextField();
		txtPatientName.setPreferredSize(new Dimension(200,30));		
		panelForm.add(labelPatientName);
		panelForm.add(txtPatientName);
		
		labelPatientSex = new JLabel("เพศ :");
		panelForm.add(labelPatientSex);
		
		panelSex = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelSex.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panelForm.add(panelSex);
		
		rdM = new JRadioButton("ชาย");
		rdM.setActionCommand("ชาย");
		rdM.setSelected(true);
		rdM.setEnabled(true);				
		rdF = new JRadioButton("หญิง");
		rdF.setActionCommand("หญิง");
		rdF.setSelected(false);

		groupSex = new ButtonGroup();
		groupSex.add(rdM);
		groupSex.add(rdF);
		panelSex.add(rdM);
		panelSex.add(rdF);
		
		labelPatientAddr = new JLabel("ที่อยู่ :");
		txtPatientAddr = new JTextField();
		txtPatientAddr.setPreferredSize(new Dimension(200,30));		
		panelForm.add(labelPatientAddr);
		panelForm.add(txtPatientAddr);
		
		labelPatientTel = new JLabel("เบอร์ติดต่อ :");
		txtPatientTel = new JTextField();
		txtPatientTel.setPreferredSize(new Dimension(200,30));		
		panelForm.add(labelPatientTel);
		panelForm.add(txtPatientTel);		
		pNorth.add(panelForm);
		
		panelForm2 = new JPanel();
		panelForm2.setLayout(new GridLayout(2,1,10,5));
		
		labelForm1 = new JLabel("จัดการข้อมูลผู้ป่วย",0);
		labelForm1.setFont(new Font("Tahoma",Font.BOLD+Font.ITALIC,25));
		labelForm1.setForeground(Color.BLUE);
		panelForm2.add(labelForm1);
		//panelForm2.add(new JLabel(" ",0));
		
		panelSearch = new JPanel();
		panelSearch.setBorder(BorderFactory.createTitledBorder("ค้นหา"));
		txtSearchPatient = new JTextField();
		txtSearchPatient.setPreferredSize(new Dimension(200,30));
		
		panelSearch.add(txtSearchPatient);	
		panelForm2.add(panelSearch);
		
		pNorth.add(panelForm2);
		
		//กิจกรรมที่ 5.2
		txtSearchPatient.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent keyevent) {
				showData();
			}
		});
		//กิจกรรมที่ 5.2
		
		c.add(pNorth, BorderLayout.NORTH);
		
		panelBuntton = new JPanel();
		btnSave = new JButton("เพิ่มข้อมูล");
		
		//กิจกรรมที่ 6.1
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insert();
			}
		});
		//กิจกรรมที่ 6.1
		
		btnEdit = new JButton("แก้ไข");
		btnEdit.setVisible(false);		
		
		//กิจกรรมที่ 6.2
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});
		//กิจกรรมที่ 6.2
		
		btnDelete = new JButton("ลบ");
		btnDelete.setVisible(false);
		
		//กิจกรรมที่ 6.3
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
		//กิจกรรมที่ 6.3
		
		btnClear = new JButton("ล้าง");	
		
		//กิจกรรมที่ 6.4
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});
		//กิจกรรมที่ 6.4
		
		panelBuntton.add(btnSave);
		panelBuntton.add(btnEdit);
		panelBuntton.add(btnDelete);	
		panelBuntton.add(btnClear);
		c.add(panelBuntton, BorderLayout.CENTER);	
		
		//ทำตาราง
		panelTable = new JPanel();		
		JScrollPane scrollTable = new JScrollPane();
		scrollTable.setPreferredSize(new Dimension(972,370));		
		tablePatient = new JTable();
		Object data[][] = {
				{null,null,null,null,null},
				{null,null,null,null,null},
				{null,null,null,null,null},
				{null,null,null,null,null},
				{null,null,null,null,null},
		};
		String header[] = {"รหัสผู้ป่วย", "ชื่อ-นามสกุล", "เพศ", "ที่อยู่", "เบอร์ติดต่อ"};
		modelPatient = new DefaultTableModel(data,header) {
			//ทำให้แก้ไขข้อมูลในตารางไม่ได้
			public boolean isCellEditable(int row, int columns) { 
				return false;
			} 
			//ทำให้แก้ไขข้อมูลในตารางไม่ได้
		};
		tablePatient.setModel(modelPatient);	
		
		//กิจกรรมที่ 6.2,6.3 คลิกเมาส์เลือกรายการ
		tablePatient.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				int index = tablePatient.getSelectedRow();
				//txtPatientID.setEditable(false);
				txtPatientID.setText(tablePatient.getValueAt(index,0).toString());
				txtPatientName.setText(tablePatient.getValueAt(index,1).toString());
				if(tablePatient.getValueAt(index,2).toString().equals("ชาย")) {
					  rdM.setSelected(true);
				}else if(tablePatient.getValueAt(index,2).toString().equals("หญิง")) {
					  rdF.setSelected(true);
				}
				txtPatientAddr.setText(tablePatient.getValueAt(index,3).toString());				
				txtPatientTel.setText(tablePatient.getValueAt(index,4).toString());
				btnSave.setVisible(false);
				btnEdit.setVisible(true);
				btnDelete.setVisible(true);
			}
		});
		//กิจกรรมที่ 6.2,6.3 คลิกเมาส์เลือกรายการ
		
		scrollTable.setViewportView(tablePatient);
		panelTable.add(scrollTable);
		c.add(panelTable, BorderLayout.SOUTH);
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
			//กิจกรรมที่ 5.2
			String search = txtSearchPatient.getText().trim();
			//trim ใช้ตัดวรรค
			//กิจกรรมที่ 5.2
			String sql = "SELECT * FROM patient "
					//กิจกรรมที่ 5.2
					+ " WHERE PATIENT_ID LIKE '%" + search + "%' "
					+ " OR PATIENT_NAME LIKE '%" + search + "%' "
					+ " OR PATIENT_SEX LIKE '%" + search + "%' "
					+ " OR PATIENT_ADDR LIKE '%" + search + "%' "
					+ " OR PATIENT_TEL LIKE '%" + search + "%' ";
					//กิจกรรมที่ 5.2
			ResultSet rs = conn.createStatement().executeQuery(sql);
			int row = 0;
			while(rs.next()) {
				modelPatient.addRow(new Object[0]);
				modelPatient.setValueAt(rs.getString("PATIENT_ID"), row, 0);
				modelPatient.setValueAt(rs.getString("PATIENT_NAME"), row, 1);
				modelPatient.setValueAt(rs.getString("PATIENT_SEX"), row, 2);
				modelPatient.setValueAt(rs.getString("PATIENT_ADDR"), row, 3);
				modelPatient.setValueAt(rs.getString("PATIENT_TEL"), row, 4);
				row++;
			}
			tablePatient.setModel(modelPatient);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	//กิจกรรมที่ 5.1
	
	//กิจกรรมที่ 6.1
	public void insert() {
		try {
			if(txtPatientName.getText().equals("") || txtPatientAddr.getText().equals("") || txtPatientTel.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "กรุณากรอกข้อมูลให้ครบถ้วน","แจ้งเตือน",JOptionPane.WARNING_MESSAGE);
			}else{
	
				String sql = "INSERT INTO patient VALUES(?,?,?,?,?,?)";
				PreparedStatement pre = conn.prepareStatement(sql);
				pre.setInt(1, 0);
				pre.setString(2,txtPatientName.getText().trim());
				pre.setString(3,groupSex.getSelection().getActionCommand());
				pre.setString(4,txtPatientAddr.getText().trim());			
				pre.setString(5,txtPatientTel.getText().trim());
				pre.setString(6,"");
				if(pre.executeUpdate() != -1) {
					JOptionPane.showMessageDialog(this, "บันทึกข้อมูลเรียบร้อยแล้ว","ผลการบันทึกรายการ",JOptionPane.INFORMATION_MESSAGE);
					showData();
					txtPatientID.setText("");
					txtPatientName.setText("");
					rdM.setSelected(true);
					txtPatientAddr.setText("");				
					txtPatientTel.setText("");
				}
				
			}			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
	
	}
	//กิจกรรมที่ 6.1
	
	//กิจกรรมที่ 6.2
	public void update() {
		try {
			if(txtPatientName.getText().equals("") || txtPatientAddr.getText().equals("") || txtPatientTel.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "กรุณากรอกข้อมูลให้ครบถ้วน","แจ้งเตือน",JOptionPane.WARNING_MESSAGE);
			}else{
				
				if(tablePatient.getSelectedRow() == -1) return;
				String sql = "UPDATE patient SET "	+ " PATIENT_NAME=?, "
						+ " PATIENT_SEX=?, " + " PATIENT_ADDR=?, "
						+ " PATIENT_TEL=? " + " WHERE PATIENT_ID=? ";
				PreparedStatement pre = conn.prepareStatement(sql);			
				pre.setString(1,txtPatientName.getText().trim());
				pre.setString(2,groupSex.getSelection().getActionCommand());
				pre.setString(3,txtPatientAddr.getText().trim());			
				pre.setString(4,txtPatientTel.getText().trim());
				pre.setString(5,txtPatientID.getText().trim());
				if(pre.executeUpdate() != -1) {
					JOptionPane.showMessageDialog(this, "แก้ไขข้อมูลเรียบร้อยแล้ว","ผลการแก้ไขรายการ",JOptionPane.INFORMATION_MESSAGE);
					showData();
					txtPatientID.setText("");
					txtPatientName.setText("");
					rdM.setSelected(true);
					txtPatientAddr.setText("");				
					txtPatientTel.setText("");
				}
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
	
	}
	//กิจกรรมที่ 6.2
	
	//กิจกรรมที่ 6.3
	public void delete() {
		try {
			if(tablePatient.getSelectedRow() == -1) return;
			String sql = "DELETE FROM patient WHERE PATIENT_ID=? ";
			PreparedStatement pre = conn.prepareStatement(sql);
			pre.setString(1,txtPatientID.getText().trim());
			if(pre.executeUpdate() != -1) {
				JOptionPane.showMessageDialog(this, "ลบข้อมูลเรียบร้อยแล้ว","ผลการลบรายการ",JOptionPane.INFORMATION_MESSAGE);
				showData();
				//txtPatientID.setEditable(true);
				txtPatientID.setText("");
				txtPatientName.setText("");
				rdM.setSelected(true);
				txtPatientAddr.setText("");				
				txtPatientTel.setText("");
			}
				
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		
	}
	//กิจกรรมที่ 6.3
	
	//กิจกรรมที่ 6.4
	public void clear() {

				//txtPatientID.setEditable(true);
				txtPatientID.setText("");
				txtPatientName.setText("");
				rdM.setSelected(true);
				txtPatientAddr.setText("");				
				txtPatientTel.setText("");
				btnSave.setVisible(true);
				btnEdit.setVisible(false);
				btnDelete.setVisible(false);
	}
	//กิจกรรมที่ 6.4

}
