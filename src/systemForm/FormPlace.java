package systemForm;

import java.awt.*;
import java.sql.*;
import java.text.*;
import java.awt.event.*;
import java.awt.font.*;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import main.*;

public class FormPlace extends JInternalFrame {
	
	JDesktopPane desktop;
	
	Connection conn = MyConnect.getConnection();
	JButton btNew,btEdit,btRoom,btSearch;
	JTextField tfSearch;
	JLabel headL1;
	JTable placeTable;
	DefaultTableModel placeModel,tableModel;
	
	public FormPlace() {
//		super("Place Management",
//		          true, //resizable
//		          true, //closable
//		          true, //maximizable
//		          true);//iconifiable
		Font fn = new Font("Tahoma", Font.PLAIN, 13);
		Font Headfn = new Font("Tahoma", Font.BOLD, 24);
		
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
		
		if(conn != null) {
			System.out.println("Connection Success");
		} else {
			System.out.println("Not Connection");
		}
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		setSize(700, 500);
		
		JPanel headPanel = new JPanel();
		JLabel headL1 = new JLabel("จัดการสถานที่กักตัว",SwingConstants.CENTER);
		headL1.setFont(Headfn);
		headPanel.setBounds(10,10,10,10);
		headPanel.add(headL1);
		c.add(headPanel,BorderLayout.NORTH);
		
		
		JPanel pButton = new JPanel();
		pButton.setLayout(new GridLayout(10,1,15,20));
		pButton.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		
		btNew = new JButton("เพิ่ม");
		btEdit = new JButton("แก้ไข");
		btRoom = new JButton("จัดการห้อง");
		btNew.setAlignmentX(Component.CENTER_ALIGNMENT);
		btEdit.setAlignmentX(Component.CENTER_ALIGNMENT);
		btRoom.setAlignmentX(Component.CENTER_ALIGNMENT);
		pButton.add(Box.createVerticalStrut(100));
		pButton.add(btNew);
        pButton.add(btEdit);
        pButton.add(btRoom);
		
		JPanel pTable = new JPanel();
		
		JPanel pSearch = new JPanel();
		pSearch.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		tfSearch = new JTextField();
		tfSearch.setPreferredSize(new Dimension(300, 30));
		JLabel lbSearch = new JLabel("ค้นหา");
		JButton btRef = new JButton("Refresh");
		pSearch.add(lbSearch);
		pSearch.add(tfSearch);
		pSearch.add(btRef);
		
		tfSearch.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				showData();
			}
		});
		
		btRef.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showData();
			}
		});
		
		pTable.setLayout(new BoxLayout(pTable, BoxLayout.Y_AXIS));
		JScrollPane scrollTable = new JScrollPane();
		scrollTable.setPreferredSize(new Dimension(300, 395));
		placeTable = new JTable();
		Object data[][] = {
				{null,null,null,null,null},
				{null,null,null,null,null},
				{null,null,null,null,null},
				{null,null,null,null,null},
				{null,null,null,null,null}
		};
		String header[] = {"ID","สถานที่","ที่อยู่","เบอร์โทรศัพท์","จำนวนห้อง"};
		tableModel = new DefaultTableModel(data,header) {
			public boolean isCellEditable(int row,int column) {
				return false;
			}
		};
		placeTable.setModel(tableModel);
		scrollTable.setViewportView(placeTable);
		setCellsAlignment(placeTable, SwingConstants.CENTER);
		
		placeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		placeTable.getColumnModel().getColumn(0).setPreferredWidth(20);
		placeTable.getColumnModel().getColumn(1).setPreferredWidth(372);
		placeTable.getColumnModel().getColumn(2).setPreferredWidth(200);
		placeTable.getColumnModel().getColumn(3).setPreferredWidth(180);
		placeTable.getColumnModel().getColumn(4).setPreferredWidth(90);
		
		TableColumnModel tcm = placeTable.getColumnModel();
		tcm.removeColumn( tcm.getColumn(0));
		
		pTable.add(pSearch);
		pTable.add(scrollTable);
		c.add(pTable,BorderLayout.CENTER);
		c.add(pButton,BorderLayout.EAST);
		c.add(new JPanel(),BorderLayout.WEST);
		
		btNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desktop = getDesktopPane();
				FormPlace_ARUD pfARUD = new FormPlace_ARUD();
				pfARUD.tfID.setEditable(false);
				pfARUD.btEdit.setVisible(false);
				pfARUD.btDel.setVisible(false);				
				pfARUD.headL1.setText("เพิ่มข้อมูลสถานที่กักตัว");
				call(pfARUD);							
			}
			
		});
		
		btEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (placeTable.getSelectedRow() == -1) 
				{
					JOptionPane.showMessageDialog(null, "กรุณาเลือกข้อมูลในตาราง",
							"แก้ไขข้อมูลไม่สำเร็จ",JOptionPane.INFORMATION_MESSAGE);
					return;
				} else {
				desktop = getDesktopPane();
				FormPlace_ARUD pfARUD = new FormPlace_ARUD();
				pfARUD.btAdd.setVisible(false);
				pfARUD.tfID.setEditable(false);
				pfARUD.headL1.setText("แก้ไขข้อมูลสถานที่กักตัว");
				int index = placeTable.getSelectedRow();
//				pfARUD.tfID.setText(placeTable.getValueAt(index, 0).toString());
				pfARUD.tfID.setText(placeTable.getModel().getValueAt(placeTable.getSelectedRow(),0).toString());
				pfARUD.tfName.setText(placeTable.getValueAt(index, 0).toString());
				pfARUD.tfAddr.setText(placeTable.getValueAt(index, 1).toString());
				pfARUD.tfTel.setText(placeTable.getValueAt(index, 2).toString());
				
				call(pfARUD);
				
			}
			}});
		
		btRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (placeTable.getSelectedRow() == -1) 
				{
					JOptionPane.showMessageDialog(null, "กรุณาเลือกข้อมูลในตาราง",
							"แก้ไขข้อมูลไม่สำเร็จ",JOptionPane.INFORMATION_MESSAGE);
					return;
				} else {
				desktop = getDesktopPane();
				FormPlace_Room pRoom = new FormPlace_Room();
				pRoom.btDelroom.setEnabled(false);
				int index = placeTable.getSelectedRow();
//				pRoom.keep.setText(placeTable.getValueAt(index, 0).toString());
				pRoom.keep.setText(placeTable.getModel().getValueAt(placeTable.getSelectedRow(),0).toString());
				pRoom.headL2.setText(placeTable.getValueAt(index, 0).toString());
				pRoom.showData();
				call(pRoom);
			}
			}});
		
		placeModel = (DefaultTableModel) placeTable.getModel();
		showData();
	}
	
	public void call(JInternalFrame jif) {  //กำหนดค่าของ Class ที่ใช้ JInternalFrame
		try {
//		BasicInternalFrameUI bi = (BasicInternalFrameUI)jif.getUI();
//		bi.setNorthPane(null);   //เอา WindowsBar [-][_][X] ออก
	    Dimension desktopSize = this.getSize();
	    Dimension jInternalFrameSize = jif.getSize();
	    int width = (desktopSize.width - jInternalFrameSize.width) / 2;
	    int height = (desktopSize.height - jInternalFrameSize.height) / 2;
//	    int height = 0;
	    jif.setLocation(width, height);
//		jif.setBorder(null); //เอาขอบออก
	    jif.setVisible(true); //แสดงผล
//	    jif.setMaximum(true); //ขยายเต็มจอ
//	    desktop.removeAll();
//		desktop.repaint();
	    desktop.add(jif);
	    jif.toFront();//เอา class ที่เรียกมาใส่ใน DesktopPane
//		} catch (PropertyVetoException e1) {
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	public void showData() {
		try {
			int totalRow = placeTable.getRowCount()-1;
			while (totalRow > -1) {
				placeModel.removeRow(totalRow);
				totalRow--;
			}
			String search = tfSearch.getText().trim();
			String sql = 
					
					"SELECT place.*, COUNT(room.Room_Num) AS countRoom FROM place"
					+" left JOIN room on room.PLACE_ID = place.PLACE_ID"
					+" WHERE PLACE_NAME LIKE'%"+search+"%'"
					+" OR PLACE_ADDR LIKE'%"+search+"%'"
					+" OR PLACE_TEL LIKE'%"+search+"%'"
					+" GROUP BY place.PLACE_ID"
					+" ORDER BY place.PLACE_ID ASC"
					;
			
			ResultSet rs = conn.createStatement().executeQuery(sql);
			int row = 0;
			while (rs.next()) {
				placeModel.addRow(new Object[0]);
				placeModel.setValueAt(rs.getString("PLACE_ID"), row, 0);
				placeModel.setValueAt(rs.getString("PLACE_NAME"), row, 1);
				placeModel.setValueAt(rs.getString("PLACE_ADDR"), row, 2);
				placeModel.setValueAt(rs.getString("PLACE_TEL"), row, 3);
				placeModel.setValueAt(rs.getString("countRoom"), row, 4);
				row++;
			}
			placeTable.setModel(placeModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setCellsAlignment(JTable table, int alignment)
    {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(alignment);

        TableModel tableModel = table.getModel();

        for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++)
        {
            table.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
        }
    }
	
	
}
