package lab2;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.* ;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Controller {
	private fileManage model;  //文件模型
	private GUI gui;  //视图窗口
	
	Controller(){
		//model 和 GUI 实例化
		model =  new fileManage() ;  //文件模型
		gui = new GUI();  //视图窗口
		gui.setVisible(true);
		
		/*
		 * 浏览文件按钮事件
		 */
		gui.bt_selectFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				JLabel choose = new JLabel();
				chooser.showDialog(choose, "Select file");
				File file = chooser.getSelectedFile();  //被选中的文件
				gui.text_filePath.setText(file.getAbsolutePath());
;			}
		});
		
		/*
		 * 新建文件夹按钮事件
		 */
		gui.bt_create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String path = gui.text_filePath.getText();
				File new_file = model.createDir(path);
				if(new_file != null) {
					JOptionPane.showMessageDialog(null, "Successfully created"+path);
				}
			}
		});
		
		/*
		 * 删除文件按钮事件
		 */
		gui.bt_delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String path = gui.text_filePath.getText();
				File deletedFile = new File(path);
				model.deleteFile(deletedFile);
				JOptionPane.showMessageDialog(null, "Deleted successfully"+path);			}
		});
		
		/*
		 * 复制文件按钮事件
		 */
		gui.bt_copy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String copyPath = gui.text_filePath.getText();
				String pastePath = JOptionPane.showInputDialog(gui,"File path:","Please enter the storage path of the copied file",JOptionPane.PLAIN_MESSAGE);
				File copyFile = new File(copyPath);
				File pasteFile = new File(pastePath);
				if(copyFile.isDirectory()) {
					try {
						model.folderCopy(copyPath, pastePath);
						JOptionPane.showMessageDialog(null,"Folder copied successfully");
					}catch(IOException exception) {
						JOptionPane.showMessageDialog(null,"Folder copy failed");
						exception.printStackTrace();
					}
				}else {
					try {
						model.fileCopy(copyPath, pastePath);
						JOptionPane.showMessageDialog(null,"Folder copied successfully");
					}catch(IOException exception) {
						JOptionPane.showMessageDialog(null,"Folder copy failed");
						exception.printStackTrace();
					}
				}
				
			}
		});
		
		/*
		 * 加密文件按钮事件
		 */
		gui.bt_encrypt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String path=JOptionPane.showInputDialog(gui,"Encrypted file path:","Please enter the file path",JOptionPane.PLAIN_MESSAGE);
				File f = new File(gui.text_filePath.getText());
				try {
					model.encryptFile(f, path);
					JOptionPane.showMessageDialog(null, "Encryption succeeded!");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Encryption failed! File not found or not selected!");
				}
			}
		});
		
		/*
		 * 解密文件按钮事件
		 */
		gui.bt_decrypt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String path=JOptionPane.showInputDialog
						(gui,"Decrypt file path:" ,"please enter the file storage path",JOptionPane.PLAIN_MESSAGE);
				File f = new File(gui.text_filePath.getText());
				try {
					model.decryptFile(f, path);
					JOptionPane.showMessageDialog(null, "Decryption succeeded! ");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Encryption failed! File not found or not selected! ");
				}
			}
		});
		
		/*
		 * 压缩文件按钮事件
		 */
		gui.bt_zip.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String filePath = JOptionPane.showInputDialog
						(gui,"Compressed file path:" ,"please enter the file storage path",JOptionPane.PLAIN_MESSAGE);
				String file_path = gui.text_filePath.getText();  //待压缩
				try {
					model.zipFile(file_path, filePath);
					JOptionPane.showMessageDialog(null, "Zip succeeded! ");
				}catch(IOException exception) {
					JOptionPane.showMessageDialog(null, "Zip failed! ");
					exception.printStackTrace();
				}
			}
		});
		
		/*
		 * 解压文件按钮事件
		 */
		gui.bt_unzip.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String filePath = JOptionPane.showInputDialog
						(gui,"Extract file path:" ,"please enter the file storage path",JOptionPane.PLAIN_MESSAGE);
				String file_path = gui.text_filePath.getText();   //待解压
				try {
					model.unzipFile(file_path, filePath);
					JOptionPane.showMessageDialog(null, "Decompression succeeded! ");
				}catch(IOException exception) {
					JOptionPane.showMessageDialog(null, "Decompression failed! ");
					exception.printStackTrace();
				}
			}
		});
		
		/*
		 * 展示文件按钮事件
		 */
		gui.bt_show.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File file = new File(gui.text_filePath.getText());
//				new FileTree(file);
				
				DefaultMutableTreeNode root = addTree(file);
				gui.tree = new JTree(root);
				
				JScrollPane jscrollPane = new JScrollPane(){//sp1滚动面板的大小 
					   @Override
					   public Dimension getPreferredSize() {
					     return new Dimension(500, 300);//括号内参数，可以根据需要更改
					   }
				 };	
				jscrollPane.setViewportView(gui.tree);
				gui.jp3.removeAll();
				gui.jp3.add(jscrollPane);
				jscrollPane.setSize(500,300);
				gui.setVisible(true);
			}
		});
	}
	
	/*
	 * 添加树文件 
	 */
	public DefaultMutableTreeNode addTree(File f) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(f);
		File [] files = f.listFiles();
		if(f!=null)
			for(File file:files) {
				if(file.isDirectory()) {
					root.add(addTree(file));
				}else
					root.add(new DefaultMutableTreeNode(file));
				
			}
		return root;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Controller frame = new Controller();
	}

}
