package main;

import java.sql.Connection;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import systemForm.*;
import systemReport.*;

public class mainForm extends JFrame{
	
		Connection conn = MyConnect.getConnection();
		JDesktopPane desktop;
		JPanel pmain,puser,ppass,pblogin,pback;
		JLabel ltitle,luser,lpass,lalert;
		JTextField tuser,tpass;
		JButton blogin,bback;
		
	public mainForm() {
		Font fn = new Font("Tahoma", Font.PLAIN, 14);
		Font fn2 = new Font("Tahoma", Font.BOLD, 14);
		
		UIManager.put("Menu.font", fn2);
		UIManager.put("MenuItem.font", fn2);
		UIManager.put("TextField.font",fn2);
		UIManager.put("Button.font", fn2);
		UIManager.put("Label.font", fn);
		desktop = new JDesktopPane();
		setContentPane(desktop);
//		if(conn != null) {
//			System.out.println("Connection Success");
//		} else {
//			System.out.println("Not Connection");
//		}
		
		
		
		// Menu Bar
        JMenuBar menuBar=new JMenuBar();
        
        // Menu 1
        JMenu menu1 = new JMenu("ระบบการจอง");
        JMenuItem menu1_1 = new JMenuItem("CHECK-IN");
        JMenuItem menu1_2 = new JMenuItem("CHECK-OUT");
        menu1.add(menu1_1);
        menu1.add(menu1_2);
        menuBar.add(menu1);
        
        menu1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desktop.removeAll();
				desktop.repaint();
				FormCheckIn fCheckIn = new FormCheckIn();
				//fPatient.setBackground(Color.blue);
				setJIF(fCheckIn);
				
			}
		});
        
        menu1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desktop.removeAll();
				desktop.repaint();
				FormCheckOut fFormCheckOut = new FormCheckOut();
				setJIF(fFormCheckOut);
				
			}
		});
        
        
//        JMenuItem menu1_1 = new JMenuItem("จอง");
//        menu1.add(menu1_1);
 //       JButton bt1 = new JButton("จอง");
        //setbuttonMenu(bt1);
//        bt1.setBorder(BorderFactory.createEmptyBorder());
//        bt1.setContentAreaFilled(false);
//        bt1.setBorderPainted(false);
//        menuBar.add(menu1);
    //    menuBar.add(bt1);
        
       // bt1.addActionListener(new ActionListener() {
		//	public void actionPerformed(ActionEvent e) {
			//	desktop.removeAll();
				//desktop.repaint();
				//place framePlace = new place();
				//framePlace.setBackground(Color.red);
				//setJIF(framePlace);
			//}
		//});
        
        
        // Menu 2
        JMenu menu2 = new JMenu("ระบบจัดการข้อมูลพื้นฐาน");
        JMenuItem menu2_1 = new JMenuItem("จัดการข้อมูลผู้ป่วย");
        JMenuItem menu2_2 = new JMenuItem("จัดการข้อมูลสถานที่กักตัว");
        //JMenuItem menu2_3 = new JMenuItem("จัดการข้อมูลห้องกักตัว");
        menu2.add(menu2_1);
        menu2.add(menu2_2);
        //menu2.add(menu2_3);
        menuBar.add(menu2);
        
        menu2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desktop.removeAll();
				desktop.repaint();
				FormPatient fPatient = new FormPatient();
				setJIF(fPatient);
				
			}
		});

        
        menu2_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desktop.removeAll();
				desktop.repaint();
				FormPlace fPlace = new FormPlace();
				setJIF(fPlace);
				
			}
		});
        
       /* menu2_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desktop.removeAll();
				desktop.repaint();
				//FormRoom fRoom = new FormRoom();
				//setJIF(fRoom);
				
			}
		});*/
        
        // Menu 3
        JMenu menu3 = new JMenu("รายงาน");
        JMenuItem menu3_1 = new JMenuItem("รายงานข้อมูลผู้ป่วย");
        JMenuItem menu3_2 = new JMenuItem("รายงานข้อมูลสถานที่กักตัว");
        //JMenuItem menu3_3 = new JMenuItem("รายงานข้อมูลห้องกักตัว");
        JMenuItem menu3_4 = new JMenuItem("รายงานข้อมูลการใช้บริการ");
        menu3.add(menu3_1);
        menu3.add(menu3_2);
        //menu3.add(menu3_3);
        menu3.add(menu3_4);
        menuBar.add(menu3);
        
        menu3_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desktop.removeAll();
				desktop.repaint();
				ReportPatient rPatient = new ReportPatient();
				setJIF(rPatient);
			}
		});
        
        menu3_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desktop.removeAll();
				desktop.repaint();
				ReportPlace rPlace = new ReportPlace();
				setJIF(rPlace);
			}
		});
        
        /*menu3_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desktop.removeAll();
				desktop.repaint();
				//ReportRoom rRoom = new ReportRoom();
				//setJIF(rRoom);
			}
		});*/
        
        menu3_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desktop.removeAll();
				desktop.repaint();
				ReportHistory rHistory = new ReportHistory();
				setJIF(rHistory);
			}
		});
        
		Panel panelBar = new Panel();
		FlowLayout flowLayout = (FlowLayout) panelBar.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		
		menuBar.add(panelBar);
		
//      JMenuItem menu1_1 = new JMenuItem("จอง");
 //     menu1.add(menu1_1);
    	JButton btexit = new JButton("ออกจากโปรแกรม ");
    	setbuttonMenu(btexit);
    	btexit.setForeground(Color.RED);
      	btexit.setBorder(BorderFactory.createEmptyBorder());
      	btexit.setContentAreaFilled(false);
      	btexit.setBorderPainted(false);
      	menuBar.add(btexit);
      	
      	/*
      	menu1.setEnabled(false); // ปิดเมนูบาร์
      	menu2.setEnabled(false); // ปิดเมนูบาร์
      	menu3.setEnabled(false); // ปิดเมนูบาร์
      	//menuBar.setVisible(false); // ปิดเมนูบาร์
      	 
      	 */
      
      	btexit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(NORMAL);
			}
		});
        
        setJMenuBar(menuBar);
        
		Container c = this.getContentPane();
		c.setLayout(new GridLayout(4,1));
		
		c.add(new JLabel(" ",0));
        ltitle = new JLabel("โปรแกรมการจองที่กักตัว สำหรับผู้ติดเชื้อโควิด-19",0);
		ltitle.setFont(new Font("Tahoma",Font.BOLD+Font.ITALIC,25));
		ltitle.setForeground(Color.BLUE);
		
		c.add(ltitle);
		
        
        /*
        //login
		Container c = this.getContentPane();
		c.setLayout(new GridLayout(13,1));
		
		c.add(new JLabel(" ",0));
		c.add(new JLabel(" ",0));
		c.add(new JLabel(" ",0));
		
		
        ltitle = new JLabel("โปรแกรมการจองที่กักตัว สำหรับผู้ติดเชื้อโควิด-19",0);
		ltitle.setFont(new Font("Tahoma",Font.BOLD+Font.ITALIC,25));
		ltitle.setForeground(Color.BLUE);
		c.add(ltitle);
				
		c.add(new JLabel(" ",0));
		puser = new JPanel();
		puser.setLayout(new GridLayout(1,2,10,5));		
		luser = new JLabel("ชื่อผู้ใช้ (USERNAME) :");
		tuser = new JTextField();
		tuser.setPreferredSize(new Dimension(200,30));		
		puser.setBorder(BorderFactory.createEmptyBorder(0,235,0,235));
		puser.add(luser);
		puser.add(tuser);
		c.add(puser);
				
		ppass = new JPanel();
		ppass.setLayout(new GridLayout(1,2,10,5));		
		lpass = new JLabel("รหัสผ่าน (PASSWORD) :");
		tpass = new JTextField();
		tpass.setPreferredSize(new Dimension(200,30));	
		ppass.setBorder(BorderFactory.createEmptyBorder(0,235,0,235));
		ppass.add(lpass);
		ppass.add(tpass);
		c.add(ppass);
		
		pblogin = new JPanel();
		blogin = new JButton("เข้าสู่ระบบ");
		blogin.setForeground(Color.BLUE);
		pblogin.add(blogin);
		c.add(pblogin);		
		//c.add(new JLabel(" ",0));
		
        lalert = new JLabel(" ",0);
        lalert.setFont(new Font("Tahoma",Font.BOLD+Font.ITALIC,14));
        lalert.setForeground(Color.RED);
		c.add(lalert);
		
		blogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tuser.getText().equals("admin") && tpass.getText().equals("1234")) {
					menuBar.setVisible(true);
					puser.setVisible(false);
					ppass.setVisible(false);
					pblogin.setVisible(false);
					lalert.setText("");
					menu1.setEnabled(true);
					menu2.setEnabled(true);
					menu3.setEnabled(true);
					ltitle.setText( "โปรแกรมการจองที่กักตัว สำหรับผู้ติดเชื้อโควิด-19 : ยินดีต้อนรับ");
					ltitle.setForeground(new Color(0, 100, 0));
				}else{
					lalert.setText( "ชื่อผู้ใช้ หรือรหัสผ่านไม่ถูกต้อง");
					//JOptionPane.showMessageDialog(this, tuser.getText().trim()+"ชื่อผู้ใช้ หรือรหัสผ่านไม่ถูกต้อง"+tpass.getText().trim(),"แจ้งเตือน",JOptionPane.INFORMATION_MESSAGE);				

				}
			}
		});
		
		//login
        */
        
        setSize(1000,670);	//แก้หน้าต่าง
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 362, 249);
		setTitle("Home Isolation System");
		getContentPane().setLayout(null);
	}
	
	public void setJIF(JInternalFrame jif) {
		try {
		BasicInternalFrameUI bi = (BasicInternalFrameUI)jif.getUI();
		bi.setNorthPane(null);
//	    Dimension desktopSize = this.getSize();
//	    Dimension jInternalFrameSize = jif.getSize();
//	    int width = (desktopSize.width - jInternalFrameSize.width) / 2;
//	    int height = (desktopSize.height - jInternalFrameSize.height) / 2;
//	    int height = 0;
//	    jif.setLocation(width, height);
		jif.setBorder(null);
	    jif.setVisible(true);
	    jif.setMaximum(true);
	    desktop.add(jif);
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
		}
	}

	public void setbuttonMenu (JButton bt) {
		 bt.setBackground(Color.WHITE);
		 bt.setBorderPainted(false);
	     bt.addMouseListener(new java.awt.event.MouseAdapter() {
	            public void mouseEntered(java.awt.event.MouseEvent evt) {
	            	bt.setBackground(Color.LIGHT_GRAY);
	            }

	            public void mouseExited(java.awt.event.MouseEvent evt) {
	            	bt.setBackground(Color.WHITE);
	            }
	        });
	}
	
	public static void main(String[] agrs) {
		new mainForm();
		
	}
}
