package lab2;

import javax.swing.*;
import java.awt.* ;
import java.awt.event.* ;
import java.util.*;
import java.io.*;

/*
 * �ļ��������ļ�GUI����
 */
public class GUI extends JFrame{
	JPanel jp0 = new JPanel();   //��ӭ�Ͱ���
	JPanel jp1 = new JPanel(); 	//�ļ�ѡ�еĲ���
	JPanel jp2 = new JPanel(); 	//�����Ĳ���
	JPanel jp3 = new JPanel();  //�ļ���ʾ
	
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
	
	JTree tree;  //�ļ�չʾ��
	
	
	JTextField text_filePath = new JTextField("                                                                                                                 ");
	
	/*
	 * DesTructor
	 */
	GUI(){
		setTitle("20186471 File Manager");
		setSize(650,450);
		setLayout(new BorderLayout());  //����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //�ر�
		
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
