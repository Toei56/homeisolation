package systemForm;

//import MenuForm;
//import MenuReport;

import java.awt.*;
import java.awt.event.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


import main.*;

public class FormCheckIn extends JInternalFrame{

	Font fontTH = new Font("Tahoma",Font.PLAIN,12);	


	
Connection conn = MyConnect.getConnection();

	JTable tablePlace,tablePatient,tableBook;
	DefaultTableModel modelPlace,modelPatient,modelBook;
	
	JTextField txtSearchPlace,txtSearchBook,txtSearchPatient,txtPatientStatus,txtBookID,txtPlaceID,txtPlaceName,txtPlaceRoom,
	txtPlaceAddr,txtPatientID,txtPatientName,txtDate ;
	JLabel lBookID,lPlaceID,lPlaceName,lPateintID,lPatientName,lDate;
	
	JButton cmdCheckIn;
	JButton cmdClear;
	JButton searchNull;
	
	String bID,plID,ptID ;
	String plName,ptName,rNum;
	String roomStatus = "";
	String ptstatus;

	JComboBox roomBox;
	public FormCheckIn() {
		Container c = this.getContentPane();
		c.setLayout(new GridLayout(4,1));
		c.setBackground(Color.WHITE);
		
		UIManager.put("OptionPane.messageFont",fontTH);	
		UIManager.put("TitledBorder.font",fontTH);	
		UIManager.put("Label.font",fontTH);	
		UIManager.put("Button.font",fontTH);	
		UIManager.put("Table.font",fontTH);	
		UIManager.put("TableHeader.font",fontTH);	
		UIManager.put("TextField.font",fontTH);	
		UIManager.put("RadioButton.font",fontTH);
		/*--------------------Headder---------------------
		 * 
		 */
		JPanel paneHead = new JPanel();
		paneHead.setLayout(new GridLayout(2,1));
		paneHead.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		JPanel paneHead1 = new JPanel();
		paneHead1.setLayout(new BorderLayout());
		
		JLabel head = new JLabel("CHECK IN",SwingConstants.CENTER);
		head.setFont(new Font("Tahoma" , Font.BOLD , 25));
		head.setForeground(Color.RED);
		
		JPanel paneHead2 = new JPanel();
	
		paneHead.setLayout(new FlowLayout());
		//JLabel searchLabel = new JLabel("ค้นหาสถานที่");
		txtSearchPlace = new JTextField();
		txtSearchPlace.setPreferredSize(new Dimension(300,20));
//		txtSearchPlace.addKeyListener(new KeyAdapter() {
//			public void keyReleased(KeyEvent keyevent) {
//				//showDataPlace();
//			}
//		});
		JButton searchPlaceButton = new JButton("ค้นหาสถานที่");
		searchPlaceButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				showDataPlace();
			}
		});
//		searchNull = new JButton("ค้นหาสถานที่ว่าง");
//		searchNull.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e) {
//				showDataPlaceNull();
//			}
//		});
		paneHead1.add(head,BorderLayout.NORTH);
		
		
		
		paneHead2.add(txtSearchPlace);
		paneHead2.add(searchPlaceButton);
		//paneHead2.add(searchNull);
		paneHead1.add(paneHead2,BorderLayout.CENTER);
		paneHead.add(paneHead1);
		
		
		c.add(paneHead);
		

		//---------Grid 2----------------
		JScrollPane scTable = new JScrollPane();
		scTable.setPreferredSize(new Dimension(450,300));
	
		tablePlace = new JTable();
		
		tablePlace.addMouseListener(new MouseAdapter() {// set Event Mouse
			public void mouseClicked(MouseEvent m) {
				int index = tablePlace.getSelectedRow();
				//cmdChackIn.setEnabled(false);
				txtPlaceID.setEditable(false);
				txtPlaceName.setEditable(false);
				txtPlaceRoom.setEditable(false);
				txtPlaceAddr.setEditable(false);
				cmdCheckIn.setEnabled(false);
				
				String id = tablePlace.getValueAt(index, 0).toString();
				plName = tablePlace.getValueAt(index, 1).toString();
				//String room = tablePlace.getValueAt(index, 2).toString();
				//String status = tablePlace.getValueAt(index, 3).toString();
				String addr = tablePlace.getValueAt(index, 2).toString();
//				
//				TableColumn roomColumn = tablePlace.getColumnModel().getColumn(3);
//				
//				roomColumn.setCellEditor(new DefaultCellEditor(roomBox));
				tablePlace.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(roomBox));
				
				if (roomBox.getSelectedItem() != null || roomBox.getSelectedItem() != "")  {
					
					txtPlaceID.setText(id);
					txtPlaceName.setText(plName);
					//txtPlaceRoom.setText(room);
					txtPlaceAddr.setText(addr);
					cmdCheckIn.setEnabled(false);
				}

				
//
				 findRoom(Integer.parseInt(id));
//					String[] arr = new String[list.size()];
//					for(int i = 0;i < arr.length; i++) {
//						arr[i] = String.valueOf(list.get(i));
//					}
//				System.out.println(list);
			}
		});
		
		
		
		Object dataPlace[][] = {
				{null,null,null,null},
				{null,null,null,null},
				{null,null,null,null},
				{null,null,null,null},
				{null,null,null,null}
		};
		String headerPlace[] = {"รหัสสถานที่","ชื่อสถานที่","ที่อยู่","ห้องที่ว่าง"};
		
		//modelPlace = new DefaultTableModel(dataPlace,new Object[]{"รหัสสถานที่","ชื่อสถานที่","ที่อยู่","ห้องที่ว่าง"}); 
//		{
//			//			
//			//กำหนดให้แก้ไขไม่ได้
//			public boolean isCellEditable(int row,int columns) {
//				return false;
//			}
//		};
		
		modelPlace = new DefaultTableModel(dataPlace,headerPlace) {
			//ทำให้แก้ไขข้อมูลในตารางไม่ได้
			public boolean isCellEditable(int row, int columns) {
			    if (columns <= 2)
			      return false;
			    return super.isCellEditable(row, columns);
			  }
			//ทำให้แก้ไขข้อมูลในตารางไม่ได้
		};

 //   tablePlace.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(roomBox));
//		col.setCellEditor(new DefaultCellEditor(roomBox));
		tablePlace.setModel(modelPlace);
		
		


		
		scTable.setViewportView(tablePlace);
		c.add(scTable);
		showDataPlace();
		
		//--------------------------T2--------------------
		JPanel table2Panel = new JPanel();
		table2Panel.setLayout(new BorderLayout());
		
		JPanel tableinner2 = new JPanel();
		tableinner2.setLayout(new GridLayout(2,1));
		
		JPanel subPanel1 = new JPanel();
		subPanel1.setLayout(new FlowLayout());
		JPanel subPanel2 = new JPanel();
		subPanel2.setLayout(new FlowLayout());
//	JLabel lRoom = new JLabel("ห้อง");
		 roomBox = new JComboBox();
//		 subPanel1.add(lRoom);
//		 subPanel1.add(roomBox);
		 
		JLabel searchPatientLabel = new JLabel("ค้นหารายบุคคล");
		txtSearchPatient = new JTextField();
		txtSearchPatient.setPreferredSize(new Dimension(300,20));
		txtSearchPatient.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent keyevent) {
				showDataPatient();
			}
		});
		subPanel2.add(searchPatientLabel);
		subPanel2.add(txtSearchPatient);
		tableinner2.add(subPanel1);
		tableinner2.add(subPanel2);
//		tableinner2.add(searchPatientLabel);
//		tableinner2.add(txtSearchPatient);
		
		table2Panel.add(tableinner2,BorderLayout.NORTH);
		
				JScrollPane scTable2 = new JScrollPane();
				scTable2.setPreferredSize(new Dimension(450,300));
				
				tablePatient = new JTable();
				
				tablePatient.addMouseListener(new MouseAdapter() {// set Event Mouse
					public void mouseClicked(MouseEvent m) {
						int index = tablePatient.getSelectedRow();
						
						txtPatientID.setEditable(false);
						txtPatientName.setEditable(false);
						
						
						String patientID = tablePatient.getValueAt(index, 0).toString();
						ptName = tablePatient.getValueAt(index, 1).toString();
						 //ptstatus = tablePatient.getValueAt(index, 2).toString();
						
						if((tablePatient.getValueAt(index, 2) == null || tablePatient.getValueAt(index, 2).toString().equals("ได้รับการรักษาแล้ว"))
							&& roomBox.getSelectedItem() != null)  {
//							&& (roomStatus.equals("ว่าง") || roomStatus.equals("")) && (roomBox.getSelectedItem().toString() != "")
//							 || roomBox.getSelectedItem() != null
							txtPatientID.setText(patientID);
							txtPatientName.setText(ptName);
							
							cmdCheckIn.setEnabled(true);
						}else {
							
						}
					

					}
				});
				
				Object dataPatient[][] = {
						{null,null,null},
						{null,null,null},
						{null,null,null},
						{null,null,null},
						{null,null,null}
				};
				String headerPatient[] = {"รหัสผู้ป่วย","ชื่อ-สกุลผู้ป่วย","สถานะผู้ป่วย"};
				
				modelPatient = new DefaultTableModel(dataPatient,headerPatient) {
					
					//กำหนดให้แก้ไขไม่ได้
					public boolean isCellEditable(int row,int columns) {
						return false;
					}
				};

				tablePatient.setModel(modelPatient);
				
	
				scTable2.setViewportView(tablePatient);
				table2Panel.add(scTable2,BorderLayout.CENTER);
				c.add(table2Panel);
				showDataPatient();
				
			//----------------------------T3------------------------------------	
//				JPanel table3Panel = new JPanel();
//				table3Panel.setLayout(new BorderLayout());
//				
//				JPanel tableinner3 = new JPanel();
//				tableinner3.setLayout(new FlowLayout());
//				
//				JLabel searchBookLabel = new JLabel("ค้นหาข้อมูลการจอง");
//				txtSearchBook = new JTextField();
//				txtSearchBook.setPreferredSize(new Dimension(300,20));
//				txtSearchBook.addKeyListener(new KeyAdapter() {
//					public void keyReleased(KeyEvent keyevent) {
//						showDataBooking();
//					}
//				});
//				tableinner3.add(searchBookLabel);
//				tableinner3.add(txtSearchBook);
//				
//				JScrollPane scTable3 = new JScrollPane();
//				scTable3.setPreferredSize(new Dimension(450,300));
//				
//				tableBook = new JTable();
//				
//				tableBook.addMouseListener(new MouseAdapter() {// set Event Mouse
//					public void mouseClicked(MouseEvent m) {
//						int index = tableBook.getSelectedRow();
//						//cmdChackIn.setEnabled(false);
//						//txtBookID.setText("");
//						//txtBookID.setEditable(false);
//						bID = tableBook.getValueAt(index, 0).toString();
//						plID = tableBook.getValueAt(index, 1).toString();
//						ptID = tableBook.getValueAt(index, 3).toString();
//						
//						System.out.println(ptID);
//					}
//				});
//				
//				Object dataBook[][] = {
//						{null,null,null,null,null,null,null},
//						{null,null,null,null,null,null,null},
//						{null,null,null,null,null,null,null},
//						{null,null,null,null,null,null,null},
//						{null,null,null,null,null,null,null}
//				};
//				String headerBook [] = {"Book ID","PLACE ID","PLACE NAME","PATIENT ID","PATIENT NAME","IN DATE","OUT DATE"};
//				
//				modelBook = new DefaultTableModel(dataBook,headerBook) {
//					
//					//กำหนดให้แก้ไขไม่ได้
//					public boolean isCellEditable(int row,int columns) {
//						return false;
//					}
//				};
//				showDataBooking();
//				tableBook.setModel(modelBook);
//				scTable3.setViewportView(tableBook);
//				table3Panel.add(tableinner3,BorderLayout.NORTH);
//				table3Panel.add(scTable3,BorderLayout.CENTER);
//				c.add(table3Panel);
				
				//----------------------Tail---------------------------
				JPanel tail = new JPanel();
				tail.setLayout(new FlowLayout());
				
				JPanel tail1 = new JPanel();
				tail1.setLayout(new GridLayout(5,2));
				
				JPanel tail2 = new JPanel();
				tail2.setLayout(new GridLayout(3,2));
				
				JPanel tail3 = new JPanel();
				tail3.setLayout(new GridLayout(5,1));
				
				
				
				
				
				JLabel l1 = new JLabel("PLACE ID :");
				txtPlaceID = new JTextField();
				txtPlaceID.setPreferredSize(new Dimension(100,5));
				txtPlaceID.setEditable(false);
//				tail1.add(l1);				
//				tail1.add(txtPlaceID);		

				
				JLabel l2 = new JLabel("PLACE NAME :");
				txtPlaceName = new JTextField();
				txtPlaceName.setPreferredSize(new Dimension(100,5));
				txtPlaceName.setEditable(false);
//				tail1.add(l2);				
//				tail1.add(txtPlaceName);		
				
				JLabel l3 = new JLabel("PLACE ROOM :");
				txtPlaceRoom = new JTextField();
				txtPlaceRoom.setPreferredSize(new Dimension(100,5));
				txtPlaceRoom.setEditable(false);
//				tail1.add(l3);				
//				tail1.add(txtPlaceRoom);	
				
				JLabel pa = new JLabel("PLACE ADDR :");
				txtPlaceAddr = new JTextField();
				txtPlaceAddr.setPreferredSize(new Dimension(100,5));
				txtPlaceAddr.setEditable(false);
//				tail1.add(pa);				
//				tail1.add(txtPlaceAddr);
//				
				
				JLabel l4 = new JLabel("PATIENT ID :");
				txtPatientID = new JTextField();
				txtPatientID.setPreferredSize(new Dimension(100,5));
				txtPatientID.setEditable(false);
//				tail2.add(l4);				
//				tail2.add(txtPatientID);		

				
				JLabel l5 = new JLabel("PATIENT NAME :");
				txtPatientName = new JTextField();
				txtPatientName.setPreferredSize(new Dimension(100,5));
				txtPatientName.setEditable(false);
//				tail2.add(l5);				
//				tail2.add(txtPatientName);		
//				
//				JLabel l6 = new JLabel("DATE :");
//				txtDate = new JTextField();
//				txtDate.setPreferredSize(new Dimension(100,5));
//				tail2.add(l6);				
//				tail2.add(txtDate);
			
				
				
				cmdCheckIn = new JButton("CHECK IN");
				cmdCheckIn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					
						try {
							checkIn();
						} catch (SQLException ex) {
							
//							ex.printStackTrace();
							JOptionPane.showMessageDialog(null, "ทำรายการไม่สำเร็จ","ผลการบันทึกรายการ",JOptionPane.ERROR_MESSAGE);
						
						}
					}
				});
	
	
		
				cmdCheckIn.setEnabled(false);
		tail3.add(cmdCheckIn);
//		tail3.add(cmdEditCust);
//		tail3.add(cmdDeleteCust);
//		tail3.add(cmdClear);
		
		tail.add(tail1);
		tail.add(tail2);
		tail.add(tail3);
		c.add(tail);
	}

	public void showDataPatient() {
		try {
			int totalRow = tablePatient.getRowCount() -1;//Clear Data
			
	          while (totalRow > -1) {
	        	  modelPatient.removeRow(totalRow);
	                totalRow--;
	            }// add record
	            String search = txtSearchPatient.getText().trim();
	            String sql = "SELECT patient_id,patient_name,patient_status FROM patient "
	            		+" WHERE (patient_status NOT LIKE 'กำลังรักษาอยู่' OR patient_status IS NULL) "
	            		+ " AND patient_name LIKE '%"+search+"%'" ; 
	            
	            ResultSet r = conn.createStatement().executeQuery(sql);
	            int row = 0;
	            while (r.next()) {
	            	modelPatient.addRow(new Object[0]);
	            	modelPatient.setValueAt(r.getString("patient_id"), row, 0);
	            	modelPatient.setValueAt(r.getString("patient_name"), row, 1);
	            	modelPatient.setValueAt(r.getString("patient_status"), row, 2);

	            		row++;
	            }
	            tablePatient.setModel(modelPatient);
	            
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*----------------------SHOW DATA PLACE--------------------
	 * 
	 */
	public void showDataPlace() {
		
		try {
			int totalRow = tablePlace.getRowCount() -1;//Clear Data
		
	          while (totalRow > -1) {
	        	  modelPlace.removeRow(totalRow);
	                totalRow--;
	            }// add record
	            String search = txtSearchPlace.getText().trim();
	            
	            /*
	            String sql =  "SELECT place_id,place_name,place_addr FROM place "
	            		+ " WHERE place_id LIKE '%"+search+"%'"
	            		+" OR place_name LIKE '%"+search+"%' "
	            		+ " OR place_addr LIKE '%"+search+"%'";
	            */
	            String sql =  "SELECT DISTINCT place.place_id,place.place_name,place.place_addr "
	            		+ "FROM place "
	            		+ "INNER JOIN room ON place.place_id = room.place_id "
	            		+ "WHERE place.place_id LIKE '%"+search+"%'"
	            		+ "OR place.place_name LIKE '%"+search+"%'"
	            		+ "OR place.place_addr LIKE '%"+search+"%'";
	            
	            ResultSet r = conn.createStatement().executeQuery(sql);
	            int row = 0;
	            while (r.next()) {
	            	modelPlace.addRow(new Object[0]);
	            	modelPlace.setValueAt(r.getString("place_id"), row, 0);
	            	modelPlace.setValueAt(r.getString("place_name"), row, 1);
//	            	modelPlace.setValueAt(r.getString("place_room"), row, 2);
//	            	modelPlace.setValueAt(r.getString("place_status"), row, 3);
	            	modelPlace.setValueAt(r.getString("place_addr"), row, 2);
	            		row++;
	            }
	        
	            tablePlace.setModel(modelPlace);
	          
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
		public void showDataPlaceNull() {
			try {
				int totalRow = tablePlace.getRowCount() -1;//Clear Data
				
		          while (totalRow > -1) {
		        	  modelPlace.removeRow(totalRow);
		                totalRow--;
		            }// add record
		           // String search = txtSearchPlace.getText().trim();
		            String sql =  "SELECT place_id,place_name,place_addr FROM place WHERE place_status = 'ว่าง'";
		            
		            ResultSet r = conn.createStatement().executeQuery(sql);
		            int row = 0;
		            while (r.next()) {
		            	modelPlace.addRow(new Object[0]);
		            	modelPlace.setValueAt(r.getString("place_id"), row, 0);
		            	modelPlace.setValueAt(r.getString("place_name"), row, 1);
//		            	modelPlace.setValueAt(r.getString("place_room"), row, 2);
//		            	modelPlace.setValueAt(r.getString("place_status"), row, 3);
		            	modelPlace.setValueAt(r.getString("place_addr"), row, 2);
		            		row++;
		            }
		            tablePlace.setModel(modelPlace);
		            
			}
			catch(Exception e){
				e.printStackTrace();
			}
				
		
	}

		
		public void showDataBooking() {
			
			try {
				int totalRow = tableBook.getRowCount() -1;//Clear Data
				
		          while (totalRow > -1) {
		        	  modelBook.removeRow(totalRow);
		                totalRow--;
		            }// add record
		            String search = txtSearchBook.getText().trim();
		            String sql = " select b.book_id,pl.place_id,pl.place_name,"
		            		+ "pt.patient_id,pt.patient_name,b.book_date,b.check_out from book b "
		            		+ " inner join place pl on b.place_id = pl.place_id "
		            		+ " inner join patient pt on b.patient_id = pt.patient_id "
		            		+ " GROUP BY b.book_id"
		            		+ " HAVING place_name LIKE '%"+search+"%'"
		            				+ " OR patient_name LIKE '%"+search+"%'"
		            				+ " OR place_id LIKE '%"+search+"%' "	            				
		            				+ " OR patient_id LIKE '%"+search+"%'"
		            						+ " ORDER BY b.book_date DESC";
		            
		            ResultSet r = conn.createStatement().executeQuery(sql);
		            int row = 0;
		            while (r.next()) {
		            	modelBook.addRow(new Object[0]);
		            	modelBook.setValueAt(r.getString("book_id"), row, 0);
		            	modelBook.setValueAt(r.getString("place_id"), row, 1);
		            	modelBook.setValueAt(r.getString("place_name"), row, 2);
		            	//modelBook.setValueAt(r.getString("place_status"), row, 3);
		            	modelBook.setValueAt(r.getString("patient_id"), row, 3);
		            	modelBook.setValueAt(r.getString("patient_name"), row, 4);
		            	modelBook.setValueAt(r.getString("book_date"), row, 5);
		            	modelBook.setValueAt(r.getString("check_out"), row, 6);
		          
		            		row++;
		            }
		            tablePlace.setModel(modelPlace);
		           
			}
			catch(Exception e){
				e.printStackTrace();
			}
				
		
	}
		
		/*---------------------CHECK IN--------------------
		 * 
		 */
		public void checkIn() throws SQLException{
			
			String insertBookingS = "INSERT INTO book (place_id,room_num,patient_id,check_in) values(?,?,?,?);";
			String updateRoomS = "update room set room_status = 'ไม่ว่าง' where room_num = ?";
			String updatePatientS = "UPDATE patient SET patient_status = 'กำลังรักษาอยู่' WHERE patient_id = ? ";
			
//			if(roomBox.getSelectedItem().toString() == null ) {
//				JOptionPane.showMessageDialog(null, "ทำรายการไม่สำเร็จ","ผลการบันทึกรายการ",
//						JOptionPane.ERROR_MESSAGE);
//			}
			Savepoint save = conn.setSavepoint();
			conn.setAutoCommit(false);
			try (
					PreparedStatement insertBooking = conn.prepareStatement(insertBookingS);
					PreparedStatement updateRoom = conn.prepareStatement(updateRoomS);
					PreparedStatement updatePatient = conn.prepareStatement(updatePatientS);
					){
				DateTimeFormatter formatter  = DateTimeFormatter.ofPattern( "dd-MM-yyyy HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				String time = now.format(formatter);
					String roomNum = roomBox.getSelectedItem().toString();
				
				insertBooking.setString(1,txtPlaceID.getText().trim());//parse parameter values
				insertBooking.setLong(2,Integer.parseInt(roomBox.getSelectedItem().toString()));
				insertBooking.setString(3,txtPatientID.getText().trim());
				insertBooking.setString(4,time);
				insertBooking.executeUpdate();
				
				updateRoom.setLong(1,Integer.parseInt(roomBox.getSelectedItem().toString()));			
				updateRoom.executeUpdate();
				
				updatePatient.setString(1,txtPatientID.getText().trim());
				updatePatient.executeUpdate();
				
				conn.commit();
				JOptionPane.showMessageDialog(this, "CHECK IN SUCCESS"+"\r\n คุณ :"+ptName
				+"\r\n ได้เข้ารับการรักษาที่ :"+plName+"\r\n หมายเลขห้อง :"+roomNum+"\r\n เมื่อเวลา :"+time,"ผลการบันทึกรายการ",
						JOptionPane.INFORMATION_MESSAGE);
				txtPlaceID.setText("");
				txtPlaceName.setText("");
				txtPlaceRoom.setText("");
				txtPlaceAddr.setText("");
				txtPatientID.setText("");
				txtPatientName.setText("");
				cmdCheckIn.setEnabled(false);
				showDataPlace();
				showDataPatient();
				//showDataBooking();
			
			}
			catch(SQLException e) {  //SQL Exception should be
				JOptionPane.showMessageDialog(null, "ทำรายการไม่สำเร็จ","ผลการบันทึกรายการ",
						JOptionPane.ERROR_MESSAGE);
				conn.rollback(save);
				//e.printStackTrace();
			
				return;
			}
			cmdCheckIn.setEnabled(false);
		}

		public void delete() throws SQLException{
			String delBookS = "DELETE FROM book WHERE book_id = ? ";
			String updatePlaceS = "update place set place_status = 'ว่าง' where place_id = ?";
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
//				DateTimeFormatter formatter  = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss");
//				LocalDateTime now = LocalDateTime.now();
//				String time = now.format(formatter);
				delBooking.setString(1, bID);			
				delBooking.executeUpdate();
				
				updatePlace.setString(1,plID);	
				updatePlace.executeUpdate();
				
				updatePatient.setString(1,ptID);	
				updatePatient.executeUpdate();
				
				conn.commit();
			
				txtPlaceID.setText("");
				txtPlaceName.setText("");
				txtPlaceRoom.setText("");
				txtPlaceAddr.setText("");
				txtPatientID.setText("");
				txtPatientName.setText("");
				showDataPlace();
				showDataPatient();
				//showDataBooking();
			}
			catch(SQLException e) {  //SQL Exception should be
				conn.rollback(save);
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "ใส่ข้อมูลไม่ถูกต้อง","ผลการบันทึกรายการ",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			}else {
				System.out.println("ยกเลิกการทำรายการ");
			}
		
		}
		
	
		public class Room {
			int roomNum;
			String roomStatus;
		}
		public void findRoom(int i) {
			//ArrayList<Integer> rooms = new ArrayList<Integer>();
			roomBox.removeAllItems();
			try {
				//int totalRow = tableBook.getRowCount() -1;//Clear Data
				
//		          while (totalRow > -1) {
//		        	  modelBook.removeRow(totalRow);
//		                totalRow--;
//		            }// add record
		          String sql = "SELECT * FROM room WHERE place_id = "+i+" AND (room_status = null OR room_status = 'ว่าง')" ; 
		            
		            ResultSet r = conn.createStatement().executeQuery(sql);
		            int row = 0;
		            while (r.next()) {
		            
		            	 String roomNum = r.getString("room_num");
		            	 roomBox.addItem(roomNum);
		            			 row++;
		            }
			}
		          catch(Exception e){
		        	  e.printStackTrace();
		          }
			
			
			
		}
		

//	public static void main(String[] args) {
//		// สร้าง object ของคลาส MainMenu
//
//	
//		
//		FormCheckIn m = new FormCheckIn();
//		//กำหนดขนาด
//		m.setSize(650, 600);
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
