package systemForm;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import main.MyConnect;

public class FormPlace_Room extends JInternalFrame{
	JDesktopPane desktop;
	Connection conn = MyConnect.getConnection();
	String setplace,id;
	JLabel headL2,keepID;
	JTextField keep,tfNum;
	JButton btAddroom,btDelroom;
	JTable roomTable;
	DefaultTableModel roomModel,myModel;
	public FormPlace_Room() {
		super("Room",
		          true, //resizable
		          true, //closable
		          true, //maximizable
		          true);//iconifiable
		
		Font fn = new Font("Tahoma", Font.PLAIN, 13);
		Font Headfn = new Font("Tahoma", Font.BOLD, 24);
		Dimension btDi = new Dimension(80,50);
		Dimension textDi = new Dimension(200,30);
		
		UIManager.put("OptionPane.messageFont", fn);
		UIManager.put("TitledBorder.font", fn);
		UIManager.put("Label.font", fn);
		UIManager.put("Button.font", fn);
		UIManager.put("Table.font", fn);
		UIManager.put("TableHeader.font", fn);
		UIManager.put("TextField.font", fn);
		UIManager.put("InternalFrame.TitleFont", fn);
		UIManager.put("Panel.font", fn);
		UIManager.put("OptionPane.font", fn);
		UIManager.put("ToolBar.font", fn);
//		if(conn != null) {
//			System.out.println("Connection Success");
//		} else {
//			System.out.println("Not Connection");
//		}
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		setSize(500, 320);
		
		JPanel headPanel = new JPanel();
		headPanel.setLayout(new BoxLayout(headPanel, BoxLayout.Y_AXIS));
		JPanel headP1 = new JPanel();
		JPanel headP2 = new JPanel();
		JLabel headL1 = new JLabel("เพิ่ม ลบ ห้องพัก",SwingConstants.CENTER);
		headL2 = new JLabel("ชื่อสถานที่ที่เลือก",SwingConstants.CENTER);
		keep = new JTextField();
		
		
		
		headL1.setFont(Headfn);
		headPanel.setBounds(10,10,10,10);
		headP1.add(headL1);
		headP2.add(headL2);
		//headP2.add(keep);
		headPanel.add(headP1);
		headPanel.add(headP2);
		
//		place pp = new place();
//		int index = pp.placeTable.getSelectedRow();
//		keep.setText(pp.placeTable.getValueAt(index, 0).toString());
//		String a = keep.getText();
//		System.out.println(a);///
		
		
		
		c.add(headPanel,BorderLayout.NORTH);
		
		JPanel pTable = new JPanel();
		pTable.setLayout(new BoxLayout(pTable, BoxLayout.Y_AXIS));
		JScrollPane scrollTable = new JScrollPane();
		scrollTable.setPreferredSize(new Dimension(200, 100));
	 	roomTable = new JTable();
		Object data[][] = {
				{null,null},
				{null,null},
				{null,null},
				{null,null},
				{null,null}
		};
		String header[] = {"เลขที่ห้อง","สถานะ"};
		myModel = new DefaultTableModel(data,header) {
			public boolean isCellEditable(int row,int column) {
				return false;
			}
		};
		roomTable.setModel(myModel);
		scrollTable.setViewportView(roomTable);
		pTable.add(Box.createVerticalStrut(30));
		pTable.add(scrollTable);
		
		
		
		JPanel pbox = new JPanel();
		pbox.setLayout(new BoxLayout(pbox, BoxLayout.Y_AXIS));
		JPanel pField = new JPanel(new FlowLayout());
		JPanel pButton = new JPanel(new FlowLayout());
		
		JLabel lbNum = new JLabel("เลขที่ห้อง");
		tfNum = new JTextField();
		tfNum.setPreferredSize(textDi);
		
		btAddroom = new JButton("เพิ่มห้อง");
		btDelroom = new JButton("ลบห้อง");
		JButton btCancelroom = new JButton("ยกเลิกการเลือก");
		JButton btCloseroom = new JButton("จบการทำงาน");
		pButton.add(btAddroom);
		pButton.add(btDelroom);
		pButton.add(btCancelroom);
		pButton.add(btCloseroom);
		
		
		pField.add(lbNum);
		pField.add(tfNum);
		
		pTable.add(Box.createVerticalStrut(10));
		pTable.add(pField);
		pTable.add(pButton);
		pTable.add(Box.createVerticalGlue());
		
		c.add(headPanel,BorderLayout.NORTH);
		c.add(pTable,BorderLayout.CENTER);
		
		btAddroom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				insert();
			}
		});
		
		btDelroom.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
		
		
		btCancelroom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btAddroom.setEnabled(true);
				btDelroom.setEnabled(false);
				tfNum.setText("");
			}
		});
		
		btCloseroom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				FormPlace m = new FormPlace();
				m.showData();
			}
		});
		
		roomTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int index = roomTable.getSelectedRow();
				btAddroom.setEnabled(false);
				btDelroom.setEnabled(true);
				tfNum.setText(roomTable.getValueAt(index, 0).toString());
			}
		});
		
		
		
		roomModel = (DefaultTableModel) roomTable.getModel();
	}
	
	public void showData() {
		try {
			int totalRow = roomTable.getRowCount()-1;
			while (totalRow > -1) {
				roomModel.removeRow(totalRow);
				totalRow--;
			}
			
			String sql = "SELECT * FROM room WHERE PLACE_ID=?";
			PreparedStatement myStmt; 
			myStmt = conn.prepareStatement(sql);
			myStmt.setString(1,keep.getText());
			
			ResultSet rs = myStmt.executeQuery();
			int row = 0;
			while (rs.next()) {
				roomModel.addRow(new Object[0]);
				roomModel.setValueAt(rs.getString("Room_Num"), row, 0);
				roomModel.setValueAt(rs.getString("Room_STATUS"), row, 1);
				row++;
			}
			roomTable.setModel(roomModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
		public void insert() {
			try {
				if(tfNum.getText().isEmpty()) {
					JOptionPane.showMessageDialog(this, "กรุณาระบุหมายเลขห้อง",
							"ผลการบันทึกรายการ",JOptionPane.WARNING_MESSAGE);
				}  else {
				String sql = "INSERT INTO room VALUES (?,'ว่าง',?)";
		//		PreparedStatement pre = conn.prepareStatement(sql);
		//		pre.setString(1, tfNum.getText().trim());
		//		pre.setString(2, keep.getText().trim());
				PreparedStatement in; 
				in = conn.prepareStatement(sql);
				in.setString(1,tfNum.getText());
				in.setString(2,keep.getText());
				if(in.executeUpdate() != -1) {
					JOptionPane.showMessageDialog(this, "บันทึกข้อมูลเรียบร้อยแล้ว",
							"ผลการบันทึกรายการ",JOptionPane.INFORMATION_MESSAGE);
					showData();
					tfNum.setText("");
				}}
				} catch (SQLException e) {
						e.printStackTrace();
				}
		}
		
		public void delete() {
			try {
				String sql = "DELETE FROM room WHERE room_Num=?";
				PreparedStatement pre = conn.prepareStatement(sql);
				pre.setString(1, tfNum.getText().trim());
				
				if(pre.executeUpdate() != -1) {
					JOptionPane.showMessageDialog(this, "ลบข้อมูลเรียบร้อยแล้ว",
							"ผลการลบรายการ",JOptionPane.INFORMATION_MESSAGE);
					showData();
					btAddroom.setEnabled(true);
					btDelroom.setEnabled(false);
					tfNum.setText("");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
}
