package systemReport;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
//import fromSQL.MyConnect;


import main.*;

@SuppressWarnings("serial")
public class ReportHistory extends JInternalFrame {

	Connection conn = MyConnect.getConnection();
	private static JLabel jtitle;
	private static JTextField tfsearch;
	private static JTable tbreport;
	private static JScrollPane spreport;
    private static JPanel pnorth, psearch, ptitle;
	private static DefaultTableModel modelreport;
	
	public ReportHistory() 
	{
		UIManager.put("TextField.font", new Font("Tahoma",Font.PLAIN,14));
		UIManager.put("Label.font", new Font("Tahoma",Font.BOLD,30));
		UIManager.put("Table.font", new Font("Tahoma",Font.PLAIN,14));
		UIManager.put("TableHeader.font", new Font("Tahoma",Font.BOLD,14));
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());		
	    setSize(900, 900);
		pnorth = new JPanel();
		pnorth.setLayout(new GridLayout(2,1,1,1));
		ptitle = new JPanel();
		ptitle.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		jtitle = new JLabel("รายงานการใช้บริการ");
		jtitle.setLayout(new FlowLayout(FlowLayout.CENTER));
		ptitle.add(jtitle);
		pnorth.add(ptitle);
		
		Object data[][] = {
				{null,null,null,null,null,null,null},{null,null,null,null,null,null,null},{null,null,null,null,null,null,null},{null,null,null,null,null,null,null},{null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null},{null,null,null,null,null,null,null},{null,null,null,null,null,null,null},{null,null,null,null,null,null,null},{null,null,null,null,null,null,null},
				};
		String column[] = {"<html><center>รหัส<br>การจอง<br></center></html>","สถานที่","ตั้งอยู่ที่","ห้องที่","ชื่อ-นามสกุล ผู้ป่วย","CHECK IN","CHECK OUT"};
		spreport = new JScrollPane();
		spreport.setPreferredSize(new Dimension(800,800));
		tbreport = new JTable();
		modelreport = new DefaultTableModel(data, column)
				{
					public boolean isCellEditable(int row, int column)
					{
						return false;
					}
				};
		tbreport.setModel(modelreport);
		spreport.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

		spreport.setViewportView(tbreport);
		
		//เซ็ทความกว้าง tbhead
		tbreport.getColumnModel().getColumn(0).setPreferredWidth(60);
		tbreport.getColumnModel().getColumn(1).setPreferredWidth(160);
		tbreport.getColumnModel().getColumn(2).setPreferredWidth(100);
		tbreport.getColumnModel().getColumn(3).setPreferredWidth(60);
		tbreport.getColumnModel().getColumn(4).setPreferredWidth(160);
		tbreport.getColumnModel().getColumn(5).setPreferredWidth(120);
		tbreport.getColumnModel().getColumn(6).setPreferredWidth(120);
		
		//ตั้งความสูง row
		tbreport.setRowHeight(28);
	
		//ตำแหน่งตัวอักษรใน row
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tbreport.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		tbreport.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		tbreport.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		tbreport.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		tbreport.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		tbreport.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
		tbreport.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
		
		//search
		psearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		psearch.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
		tfsearch = new JTextField("");
		tfsearch.setBorder(BorderFactory.createTitledBorder(null,"ค้นหา",TitledBorder.LEFT, TitledBorder.TOP,new Font("Tahoma",Font.BOLD,15),Color.black));
		tfsearch.setPreferredSize(new Dimension(220,50));
		tfsearch.setHorizontalAlignment ( SwingConstants.CENTER );
		tfsearch.addKeyListener(new KeyAdapter()
				{
					public void keyReleased(KeyEvent k)
					{
						showData();
					}
				});

		psearch.add(tfsearch);
		psearch.setBounds(100, 100, 100, 100);
		pnorth.add(psearch);
		c.add(pnorth,BorderLayout.NORTH);
		c.add(spreport,BorderLayout.CENTER);

		showData();
	}
	
	public void showData()
	{
		try
		{
			int totalRow = tbreport.getRowCount() -1;
			while(totalRow > -1)
			{
				modelreport.removeRow(totalRow);
				totalRow--;
			}			
			String search = tfsearch.getText().trim();
			String sql = "SELECT book.book_id, place.place_name, place.place_addr, book.room_num, patient.patient_name, book.check_in, book.check_out"
					+		" from book"
					+		" inner join place on place.place_id = book.place_id"
					+		" inner join patient on patient.patient_id = book.patient_id"
					+		 " WHERE book_id LIKE '%" + search + "%' " 
					+		 " OR place_name LIKE '%" + search  + "%' "
					+		 " OR place_addr LIKE '%" + search  + "%' "
					+		 " OR room_num LIKE '%" + search + "%' "
					+ 		 " OR patient_name LIKE '%"  + search + "%' "
					+		 " OR check_in LIKE '%" + search + "%' " 
					+		 " OR check_out LIKE '%" + search + "%' " 
					+		" order by book.book_id";
			
			ResultSet rs = conn.createStatement().executeQuery(sql);
			int row = 0;
			while (rs.next())
			{
				modelreport.addRow(new Object[0]);
				modelreport.setValueAt(rs.getString("book_id"),row,0);
				modelreport.setValueAt(rs.getString("place_name"),row,1);
				modelreport.setValueAt(rs.getString("place_addr"),row,2);
				modelreport.setValueAt(rs.getString("room_num"),row,3);
				modelreport.setValueAt(rs.getString("patient_name"),row,4);
				modelreport.setValueAt(rs.getString("check_in"),row,5);
				modelreport.setValueAt(rs.getString("check_out"),row,6);
				row++;
			}
			tbreport.setModel(modelreport);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
