package systemForm;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import main.MyConnect;

public class FormPlace_ARUD extends JInternalFrame {
	String getID;
	JTextField tfName,tfAddr,tfTel,tfID;
	JPanel pButton;
	JButton btAdd,btEdit,btDel,btCancel;
	JLabel headL1;
	JDesktopPane desktop;
	Connection conn = MyConnect.getConnection();
	
	public FormPlace_ARUD() {
		super("Place",
		          true, //resizable
		          true, //closable
		          true, //maximizable
		          true);//iconifiable
		
		Font fn = new Font("Tahoma", Font.PLAIN, 13);
		Font Headfn = new Font("Tahoma", Font.BOLD, 24);
		Dimension btDi = new Dimension(200,30);
		
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
		getID = "";
		
		Container c = getContentPane();
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
		setSize(350, 320);
		//หัวข้อ
		JPanel headPanel = new JPanel();
		headL1 = new JLabel("แก้ไขข้อมูลสถานที่กักตัว",SwingConstants.CENTER);
		headL1.setFont(Headfn);
//		headPanel.setBounds(10,10,10,10);
		headPanel.add(headL1);
		c.add(headPanel);
		
		
		//ส่วนกรอกข้อมูล
		JPanel pField = new JPanel(new GridLayout(4,2,5,5));
		pField.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
		JLabel lbID = new JLabel("ID  ",SwingConstants.RIGHT);
		JLabel lbName = new JLabel("ชื่อสถานที่  ",SwingConstants.RIGHT);
		JLabel lbAddr = new JLabel("ที่อยู่  ",SwingConstants.RIGHT);
		JLabel lbTel = new JLabel("เบอร์โทรศัพท์  ",SwingConstants.RIGHT);
		
		tfID= new JTextField();
		tfID.setPreferredSize(btDi);
		tfName = new JTextField();
		tfName.setPreferredSize(btDi);
		tfAddr = new JTextField();
		tfName.setPreferredSize(btDi);
		tfTel = new JTextField();
		tfName.setPreferredSize(btDi);
		
		pField.add(lbID);
		pField.add(tfID);
		pField.add(lbName);
		pField.add(tfName);
		pField.add(lbAddr);
		pField.add(tfAddr);
		pField.add(lbTel);
		pField.add(tfTel);
		
		c.add(pField);
		
		//ส่วนจัดการปุ่ม
		pButton = new JPanel(new FlowLayout());
		
		btAdd = new JButton("เพิ่มข้อมูล");
		btEdit = new JButton("แก้ไขข้อมูล");
		btDel = new JButton("ลบข้อมูล");
		btCancel = new JButton("ยกเลิก");
		
		pButton.add(btAdd);
		pButton.add(btEdit);
		pButton.add(btDel);
		pButton.add(btCancel);
		
		c.add(Box.createVerticalGlue());
		c.add(pButton);
		
		btAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				insert();
				setVisible(false);
			}
		});
		
		btEdit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				update();
				setVisible(false);
			}
		});
		
		btDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delete();
				setVisible(false);
			}
		});
		
		btCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
	}
		public void insert() {
		
		try {
			if(tfName.getText().isEmpty()|tfAddr.getText().isEmpty()|
					tfTel.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "กรอกข้อมูลไม่ครบ",
						"ผลการบันทึกรายการ",JOptionPane.WARNING_MESSAGE);
			} else {
			String sql = "INSERT INTO place VALUES (null,?,?,?)";
			PreparedStatement pre = conn.prepareStatement(sql);
			pre.setString(1, tfName.getText().trim());
			pre.setString(2, tfAddr.getText().trim());
			pre.setString(3, tfTel.getText().trim());
			
			if(pre.executeUpdate() != -1) {
				JOptionPane.showMessageDialog(this, "บันทึกข้อมูลเรียบร้อยแล้ว",
						"ผลการบันทึกรายการ",JOptionPane.INFORMATION_MESSAGE);
				tfName.setText("");
				tfAddr.setText("");
				tfTel.setText("");
			}}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		public void update() {
			try {
				String sql = "UPDATE `place` SET `PLACE_NAME` = ?, `PLACE_ADDR` = ?, `PLACE_TEL` = ? WHERE `place`.`PLACE_ID` = ?";
				
				PreparedStatement pre = conn.prepareStatement(sql);
				pre.setString(1, tfName.getText().trim());
				pre.setString(2, tfAddr.getText().trim());
				pre.setString(3, tfTel.getText().trim());
				pre.setString(4, tfID.getText().trim());
				
				if(pre.executeUpdate() != -1) {
					JOptionPane.showMessageDialog(this, "แก้ไขข้อมูลเรียบร้อยแล้ว",
							"ผลการบันทึกรายการ",JOptionPane.WARNING_MESSAGE);
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public void delete() {
			try {
				String sql = "DELETE FROM place WHERE PLACE_ID=?";
				PreparedStatement pre = conn.prepareStatement(sql);
				pre.setString(1, tfID.getText().trim());
				
				if(pre.executeUpdate() != -1) {
					JOptionPane.showMessageDialog(this, "ลบข้อมูลลูกค้าเรียบร้อยแล้ว",
							"ผลการลบรายการ",JOptionPane.INFORMATION_MESSAGE);
					FormPlace m = new FormPlace();
					m.showData();
				}
			} catch (SQLException e) {
				//e.printStackTrace();
				JOptionPane.showMessageDialog(this, "ยังมีห้องพักค้างอยู่ในระบบ",
						"ผลการลบรายการ",JOptionPane.INFORMATION_MESSAGE);
			}
		}
}
