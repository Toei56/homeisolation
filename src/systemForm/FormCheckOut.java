package systemForm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

//import com.toedter.calendar.JDateChooser;
import main.*;

public class FormCheckOut extends JInternalFrame{

	Font fontTH = new Font("Tahoma",Font.PLAIN,12);	
	Connection conn = MyConnect.getConnection();

	JTable tablePlace,tablePatient,tableBook;
	DefaultTableModel modelPlace,modelPatient,modelBook;
	
	JTextField txtSearchPlace,txtSearchBook,txtSearchPatient,txtBookID,txtPlaceID,txtPlaceName,txtPlaceRoom,
	txtPlaceAddr,txtPatientID,txtPatientName,txtDate ;
	JLabel lBook,lPlaceID,lPlaceName,lPateintID,lPatientName,lDate;
	
	JButton cmdCheckOut;
	JButton cmdClear;
	JButton searchNull;
	
	String bID,plID,ptID,rNum ;
	
	JComboBox dateBox,mountBox,yearBox;
	String[] d = new String[32];
	String[] m = {"01","02","03","04","05","06","07","08","09","10","11","12"};
	String[] y = {"2020","2021","2022"};
	public FormCheckOut() {
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
		c.setBackground(Color.WHITE);
		
		JPanel paneHead = new JPanel();
		paneHead.setLayout(new GridLayout(2,1));
		paneHead.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		//
		JLabel head = new JLabel("CHECK OUT",SwingConstants.CENTER);
		head.setFont(new Font("Tahoma" , Font.BOLD , 25));
		head.setForeground( Color.RED);
		
		JPanel paneHead2 = new JPanel();
		paneHead.setLayout(new GridLayout(3,1));
		

		lBook = new JLabel("ค้นหาข้อมูลการจอง");
		txtSearchBook = new JTextField();
		txtSearchBook.setPreferredSize(new Dimension(300,20));
//		txtSearchBook.addKeyListener(new KeyAdapter() {
//			public void keyReleased(KeyEvent keyevent) {
//				showDataBooking();
//			}
//		});
		JButton searchBtn = new JButton("ค้นหา");
		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				showDataBooking();

			}
		});
		for(int i=0;i < d.length-1;i++) {
			d[i] = String.valueOf(i+1);
		}
	
		dateBox = new JComboBox(d);
		 mountBox = new JComboBox(m);
		 yearBox = new JComboBox(y);
		
		JButton searchBtnByD = new JButton("ค้นหาจากวันที่ทำ");
		searchBtnByD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				showDataBookingByDate();

			}
		});
		paneHead2.add(lBook);
		paneHead2.add(txtSearchBook);
		paneHead2.add(searchBtn);
		paneHead2.add(dateBox);
		paneHead2.add(mountBox);
		paneHead2.add(yearBox);
		paneHead2.add(searchBtnByD);
		
		paneHead.add(head);
		paneHead.add(paneHead2);
		
		
		c.add(paneHead,BorderLayout.NORTH);
		
		
		
		JPanel table3Panel = new JPanel();
		table3Panel.setLayout(new BorderLayout());
		
		JPanel tableinner3 = new JPanel();
		tableinner3.setLayout(new FlowLayout());
		
//		JLabel searchBookLabel = new JLabel("ค้นหาข้อมูลการจอง");
//		txtSearchBook = new JTextField();
//		txtSearchBook.setPreferredSize(new Dimension(300,20));
//		txtSearchBook.addKeyListener(new KeyAdapter() {
//			public void keyReleased(KeyEvent keyevent) {
//				showDataBooking();
//			}
//		});
//		tableinner3.add(searchBookLabel);
//		tableinner3.add(txtSearchBook);
		
		JScrollPane scTable3 = new JScrollPane();
		scTable3.setPreferredSize(new Dimension(450,600));
		
		tableBook = new JTable();
		
		tableBook.addMouseListener(new MouseAdapter() {// set Event Mouse
			public void mouseClicked(MouseEvent m) {
				int index = tableBook.getSelectedRow();
				//cmdChackIn.setEnabled(false);
				//txtBookID.setText("");
				//txtBookID.setEditable(false);
				bID = tableBook.getValueAt(index, 0).toString();
				plID = tableBook.getValueAt(index, 1).toString();
				ptID = tableBook.getValueAt(index, 4).toString();
				rNum = tableBook.getValueAt(index, 3).toString();
//				 outDate = tableBook.getValueAt(index, 7);
//				System.out.println(outDate);
				if(tableBook.getValueAt(index, 7) == null) {
					cmdCheckOut.setEnabled(true);
				}else {
					cmdCheckOut.setEnabled(false);
				}
			
				//cmdCheckOut.setEnabled(false);
			}
		});
		
		Object dataBook[][] = {
				{null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null}
		};
		String headerBook [] = {"รหัสการจอง","รหัสสถานที่","ชื่อสถานที่","ห้อง","รหัสผู้ป่วย","ชื่อ-สกุล ผู้ป่วย","CHECK IN","CHECK OUT"};
		
		modelBook = new DefaultTableModel(dataBook,headerBook) {
			
			//กำหนดให้แก้ไขไม่ได้
			public boolean isCellEditable(int row,int columns) {
				return false;
			}
		};
		showDataBooking();
		tableBook.setModel(modelBook);
		scTable3.setViewportView(tableBook);
		table3Panel.add(tableinner3,BorderLayout.NORTH);
		table3Panel.add(scTable3,BorderLayout.CENTER);
		c.add(table3Panel,BorderLayout.CENTER);
		
		
		 cmdCheckOut = new JButton("CHECH OUT");
		 cmdCheckOut.setFont(new Font("Tahoma",Font.BOLD,12));
		 cmdCheckOut.setBackground(new Color(153,204,255));
		cmdCheckOut.setEnabled(false);
		cmdCheckOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				try {
					checkOut();
					showDataBooking();
				} catch (SQLException ex) {
					
					ex.printStackTrace();
				}
			}
		});
		cmdCheckOut.setEnabled(true);
		JPanel pTail = new JPanel();
		pTail.setLayout(new BorderLayout());
		JPanel pFlow = new JPanel();
		pFlow.setLayout(new FlowLayout());
		pFlow.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30));
		JPanel pSub = new JPanel();
		pSub.setLayout(new GridLayout(3,1));
		
		JButton cmdDeleteCust = new JButton("DELETE");
		cmdDeleteCust.setForeground(Color.WHITE);
		cmdDeleteCust.setBackground(new Color(255,153,153));
		cmdDeleteCust.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				delete();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	});
		
		pFlow.add(cmdCheckOut);
		pFlow.add(cmdDeleteCust);
		pSub.add(pFlow);
		pTail.add(pSub,BorderLayout.NORTH);
		
		c.add(pTail,BorderLayout.SOUTH);
		
		
	}
	//----------------------Class Check Out-----------------
	
	public void checkOut() throws SQLException {
		DateTimeFormatter formatter  = DateTimeFormatter.ofPattern( "dd-MM-yyyy HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String time = now.format(formatter);
//		System.out.println(rNum);
//		System.out.println(plID);
//		System.out.println(ptID);
		
		String updateRoomS = "UPDATE room SET room_status = 'ว่าง' WHERE place_id = ? AND room_num = ?";
		String updateCheckOutS = "UPDATE book SET check_out = ? WHERE book_id = ? ";
		String updatePatientS = "UPDATE patient SET patient_status = 'ได้รับการรักษาแล้ว' WHERE patient_id = ? ";
		Savepoint save = conn.setSavepoint();
		conn.setAutoCommit(false);
	
		try (
				
				PreparedStatement updateBook = conn.prepareStatement(updateCheckOutS);
				PreparedStatement updateRoom = conn.prepareStatement(updateRoomS);
				PreparedStatement updatePatient = conn.prepareStatement(updatePatientS);
				
				){
	
		
			updateBook.setString(1, time);
			updateBook.setString(2, bID);			
			updateBook.executeUpdate();
			
			updateRoom.setString(1,plID);
			updateRoom.setString(2,rNum);
			updateRoom.executeUpdate();
			
			updatePatient.setString(1,ptID);	
			updatePatient.executeUpdate();
//			
//			System.out.println(updateBook);
//			System.out.println(updatePlace);
//			System.out.println(updatePatient);
			conn.commit();
			JOptionPane.showMessageDialog(this, "CHECK OUT SUCCESS","ผลการบันทึกรายการ",
					JOptionPane.INFORMATION_MESSAGE);
//			txtPlaceID.setText("");
//			txtPlaceName.setText("");
//			txtPlaceRoom.setText("");
//			txtPlaceAddr.setText("");
//			txtPatientID.setText("");
//			txtPatientName.setText("");
		
			showDataBooking();
		}
		
		
		catch(SQLException e) {  //SQL Exception should be
			conn.rollback(save);
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "ทำรายการไม่สำเร็จ","ผลการบันทึกรายการ",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		//cmdCheckOut.setEnabled(false);
			
	}
	/*---------------------SHOW DATA BOOKING--------------------------
	 * 
	 */
	public void showDataBooking() {
		
		try {
			int totalRow = tableBook.getRowCount() -1;//Clear Data
			
	          while (totalRow > -1) {
	        	  modelBook.removeRow(totalRow);
	                totalRow--;
	            }// add record
	            String search = txtSearchBook.getText().trim();
	            String sql = " select b.book_id,pl.place_id,pl.place_name,b.room_num,"
	            		+ "pt.patient_id,pt.patient_name,b.check_in,b.check_out from book b "
	            		+ " inner join place pl on b.place_id = pl.place_id "
	            		+ " inner join patient pt on b.patient_id = pt.patient_id "
	            		+ " GROUP BY b.book_id"
	            		+ " HAVING pl.place_name LIKE '%"+search+"%'"
	            				+ " OR pt.patient_name LIKE '%"+search+"%'"

	            						+ " ORDER BY b.check_in DESC";
	            
	            ResultSet r = conn.createStatement().executeQuery(sql);
	            int row = 0;
	            while (r.next()) {
	            	modelBook.addRow(new Object[0]);
	            	modelBook.setValueAt(r.getString("book_id"), row, 0);
	            	modelBook.setValueAt(r.getString("place_id"), row, 1);
	            	modelBook.setValueAt(r.getString("place_name"), row, 2);
	            	modelBook.setValueAt(r.getString("room_num"), row, 3);
	            	modelBook.setValueAt(r.getString("patient_id"), row, 4);
	            	modelBook.setValueAt(r.getString("patient_name"), row, 5);
	            	modelBook.setValueAt(r.getString("check_in"), row, 6);
	            	modelBook.setValueAt(r.getString("check_out"), row, 7);
	          
	            		row++;
	            }
	            tableBook.setModel(modelBook);
	           
		}
		catch(Exception e){
			e.printStackTrace();
		}
			
	
}
	public void showDataBookingByDate() {
	
	try {
		int totalRow = tableBook.getRowCount() -1;//Clear Data
		
          while (totalRow > -1) {
        	  modelBook.removeRow(totalRow);
                totalRow--;
            }// add record
            //String search = txtSearchBook.getText().trim();
            String sql = " select b.book_id,pl.place_id,pl.place_name,b.room_num,"
            		+ "pt.patient_id,pt.patient_name,b.check_in,b.check_out from book b "
            		+ " inner join place pl on b.place_id = pl.place_id "
            		+ " inner join patient pt on b.patient_id = pt.patient_id "
            		+ " GROUP BY b.book_id"
            		+ " HAVING b.check_in LIKE '%"+dateBox.getSelectedItem().toString()+"-"+mountBox.getSelectedItem().toString()+"-"+yearBox.getSelectedItem().toString()+"%'";
            
            ResultSet r = conn.createStatement().executeQuery(sql);
            int row = 0;
            while (r.next()) {
            	modelBook.addRow(new Object[0]);
            	modelBook.setValueAt(r.getString("book_id"), row, 0);
            	modelBook.setValueAt(r.getString("place_id"), row, 1);
            	modelBook.setValueAt(r.getString("place_name"), row, 2);
            	modelBook.setValueAt(r.getString("room_num"), row, 3);
            	modelBook.setValueAt(r.getString("patient_id"), row, 4);
            	modelBook.setValueAt(r.getString("patient_name"), row, 5);
            	modelBook.setValueAt(r.getString("check_in"), row, 6);
            	modelBook.setValueAt(r.getString("check_out"), row, 7);
          
            		row++;
            }
            tableBook.setModel(modelBook);
           
	}
	catch(Exception e){
		e.printStackTrace();
	}
		

}
	public void delete() throws SQLException{
		String delBookS = "DELETE FROM book WHERE book_id = ? ";
		String updatePlaceS = "update room set room_status = 'ว่าง' where place_id = ?";
		String updatePatientS = "UPDATE patient SET patient_status = null WHERE patient_id = ? ";
		Savepoint save = conn.setSavepoint();
		conn.setAutoCommit(false);
		
		int selectOption =JOptionPane.showConfirmDialog(this, "YOU WANT TO DELETE ?","ยืนยันการลบ",
				JOptionPane.YES_NO_OPTION);
		if(selectOption == JOptionPane.YES_OPTION) {
		try (
				PreparedStatement delBooking = conn.prepareStatement(delBookS);
				PreparedStatement updatePlace = conn.prepareStatement(updatePlaceS);
				PreparedStatement updatePatient = conn.prepareStatement(updatePatientS);
				){
//			DateTimeFormatter formatter  = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss");
//			LocalDateTime now = LocalDateTime.now();
//			String time = now.format(formatter);
			delBooking.setString(1, bID);			
			delBooking.executeUpdate();
			
			updatePlace.setString(1,plID);	
			updatePlace.executeUpdate();
			
			updatePatient.setString(1,ptID);	
			updatePatient.executeUpdate();
			
			conn.commit();
		
//			txtPlaceID.setText("");
//			txtPlaceName.setText("");
//			txtPlaceRoom.setText("");
//			txtPlaceAddr.setText("");
//			txtPatientID.setText("");
//			txtPatientName.setText("");
			
			showDataBooking();
		}
		catch(SQLException e) {  //SQL Exception should be
			conn.rollback(save);
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "SOMETING WRONG","ผลการบันทึกรายการ",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		}else {
			System.out.println("ยกเลิกการทำรายการ");
		}
	
	}

//	public static void main(String args[]) {
//		FormCheckOut m = new FormCheckOut();
//		//กำหนดขนาด
//		m.setSize(800, 600);
//		//กำหนดข้อความ title
//		m.setTitle("Booking Form");
//		//กำหนดให้หยุดการทำงานเมื่อปิดหน้าจอ
//		m.setDefaultCloseOperation(EXIT_ON_CLOSE);
//		//ให้แสดงผลกึ่งกลางจอ
//		m.setLocationRelativeTo(null);
//		//กำหนดให้แสดงผล
//		m.setVisible(true);
//	}
}
