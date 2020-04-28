package lab2;

import javax.swing.*;
import java.awt.* ;
import java.awt.event.* ;
import java.util.*;
import java.io.*;

/*
 * 文件管理器的简单GUI界面
 */
public class GUI extends JFrame{
	JPanel jp0 = new JPanel();   //欢迎和帮助
	JPanel jp1 = new JPanel(); 	//文件选中的部分
	JPanel jp2 = new JPanel(); 	//操作的部分
	JPanel jp3 = new JPanel();  //文件显示
	
	JLabel label_fileRoad = new JLabel("File path");
	JLabel label_helper = new JLabel("Welcome to 20186471 file explorer, please select file and operation");
	
	JButton bt_selectFile = new JButton("Browse files");
	JButton bt_create = new JButton("New folder");
	JButton bt_delete = new JButton("Delete file");
	JButton bt_copy = new JButton("Copy file");
	JButton bt_encrypt = new JButton("Encrypt file");
	JButton bt_decrypt = new JButton("Decrypt files");
	JButton bt_zip = new JButton("Zip files");
	JButton bt_unzip = new JButton("Unzip files");
	JButton bt_show = new JButton("Display folder");
	
	JTree tree;  //文件展示树
	
	
	JTextField text_filePath = new JTextField("                                                                                                                 ");
	
	/*
	 * DesTructor
	 */
	GUI(){
		setTitle("20186471 File Manager");
		setSize(650,450);
		setLayout(new BorderLayout());  //布局
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //关闭
		
		jp0.add(label_helper);
		jp0.setSize(300, 300);
		
		label_fileRoad.setSize(50, 50);
		bt_selectFile.setSize(50,50);
		
		
		jp1.add(label_fileRoad);
		jp1.add(text_filePath);		
		jp1.add(bt_selectFile);
		jp1.setSize(300,200);
		
		jp2.setLayout(new GridLayout(9,1));
		jp2.add(bt_create);
		jp2.add(bt_delete);
		jp2.add(bt_copy);
		jp2.add(bt_encrypt);
		jp2.add(bt_decrypt);
		jp2.add(bt_zip);
		jp2.add(bt_unzip);
		jp2.add(bt_show);
		
		jp3.setSize(500,300);
		
		add(jp1,BorderLayout.NORTH);
		add(jp3,BorderLayout.CENTER);
		add(jp2,BorderLayout.EAST);
		add(jp0,BorderLayout.SOUTH);
	}
	
//	public static void main(String[] args)  {
//		GUI manager = new GUI();
//		manager.setVisible(true);
//	}
//	
}
