package systemReport;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import main.*;

public class ReportPlace extends JInternalFrame{
		
	Connection conn = MyConnect.getConnection();
	JTable placeTable;
	DefaultTableModel placeModel,tableModel;
	
	public ReportPlace() {
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
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		setSize(500, 320);
		
		JPanel headPanel = new JPanel();
		headPanel.setLayout(new BoxLayout(headPanel, BoxLayout.Y_AXIS));
		JPanel headP1 = new JPanel();
		JPanel headP2 = new JPanel();
		JLabel headL1 = new JLabel("รายงานข้อมูลสถานที่",SwingConstants.CENTER);
		
		headL1.setFont(Headfn);
		headPanel.setBounds(10,10,10,10);
		headP1.add(headL1);
		headPanel.add(headP1);
		headPanel.add(headP2);
		
		c.add(headPanel,BorderLayout.NORTH);
		JPanel pTable = new JPanel();
		pTable.setLayout(new BoxLayout(pTable, BoxLayout.Y_AXIS));
		JScrollPane scrollTable = new JScrollPane();
		scrollTable.setPreferredSize(new Dimension(300, 395));
		placeTable = new JTable();
		Object data[][] = {
				{null,null,null,null,null,null},
				{null,null,null,null,null,null},
				{null,null,null,null,null,null},
				{null,null,null,null,null,null},
				{null,null,null,null,null,null}
		};
		String header[] = {"สถานที่","ที่อยู่","เบอร์โทรศัพท์","ห้องทั้งหมด","ห้องว่าง","ห้องไม่ว่าง"};
		tableModel = new DefaultTableModel(data,header) {
			public boolean isCellEditable(int row,int column) {
				return false;
			}
		};
		placeTable.setModel(tableModel);
		scrollTable.setViewportView(placeTable);
		setCellsAlignment(placeTable, SwingConstants.CENTER);
//		
//		placeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//		placeTable.getColumnModel().getColumn(0).setPreferredWidth(20);
//		placeTable.getColumnModel().getColumn(1).setPreferredWidth(372);
//		placeTable.getColumnModel().getColumn(2).setPreferredWidth(200);
//		placeTable.getColumnModel().getColumn(3).setPreferredWidth(180);
//		placeTable.getColumnModel().getColumn(4).setPreferredWidth(90);
		
//		TableColumnModel tcm = placeTable.getColumnModel();
//		tcm.removeColumn( tcm.getColumn(0));
		
//		pTable.add(pSearch);
		pTable.add(scrollTable);
		c.add(pTable,BorderLayout.CENTER);
		
		placeModel = (DefaultTableModel) placeTable.getModel();
		showDataRe();
	}
	
	public void showDataRe() {
		try {
			int totalRow = placeTable.getRowCount()-1;
			while (totalRow > -1) {
				placeModel.removeRow(totalRow);
				totalRow--;
			}
//			String search = tfSearch.getText().trim();
			String sql = 
					
					"SELECT place.*, COUNT(room.Room_Num) AS countRoom ,SUM(room.room_status='ว่าง') AS RoomAct ,SUM(room.room_status='ไม่ว่าง') AS RoomUnAct FROM place"
					+" left JOIN room on room.PLACE_ID = place.PLACE_ID"
					+" GROUP BY place.PLACE_ID"
					+" ORDER BY place.PLACE_ID ASC"
					;
			
			ResultSet rs = conn.createStatement().executeQuery(sql);
			int row = 0;
			while (rs.next()) {
				placeModel.addRow(new Object[0]);
				placeModel.setValueAt(rs.getString("PLACE_NAME"), row, 0);
				placeModel.setValueAt(rs.getString("PLACE_ADDR"), row, 1);
				placeModel.setValueAt(rs.getString("PLACE_TEL"), row, 2);
				placeModel.setValueAt(rs.getString("countRoom"), row, 3);
				placeModel.setValueAt(rs.getString("RoomAct"), row, 4);
				placeModel.setValueAt(rs.getString("RoomUnAct"), row, 5);
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
