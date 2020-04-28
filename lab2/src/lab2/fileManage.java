package lab2;


import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import java.io.*;

public class fileManage {	
	private static  byte password = 123;
	/*
	 * 创建文件夹
	 */
	public File createDir(String dir) {
		File newDir = new File(dir);
		if(!newDir.exists()) {	//文件夹未被创建
			newDir.mkdirs();	//这里可以创建多层路径下的文件夹
			System.out.println("文件夹创建成功");
		}
		return newDir;
	}
	
	
	/*
	 * 删除文件夹和文件
	 * 利用递归删除文件夹
	 */
	public void deleteFile(File file) {
		if(file.isDirectory()) {  //删除文件夹
			File[] files = file.listFiles(); 
			for(File f:files) {
				deleteFile(f);	//递归删除文件夹内容
			}
		}
		//删除文件 or 文件夹
		//当file是文件夹，则删除完文件夹内容后删除文件夹
		//当file是文件，则上一步跳过，直接删除
		file.delete();	
	}
	
	/*
	 * 文件复制
	 * @param copiedFilePath 复制目标地址
	 * @param pastedFilePath 粘贴地址
	 */
	public void fileCopy(String copiedFilePath,String pastedFilePath) throws IOException {
		File copiedFile = new File(copiedFilePath);
		FileInputStream fin = new FileInputStream(copiedFile);
		File pastedFile = new File(pastedFilePath);
		if(!pastedFile.exists())
			pastedFile.createNewFile();
		FileOutputStream fout = new FileOutputStream(pastedFile);
		int count = 0,size = 10240;
		byte [] b = new byte[size];
		while((count = fin.read(b,0,size))!=-1)
			fout.write(b, 0, count);
		System.out.println("文件复制完成，位置为："+pastedFile.getAbsolutePath());
		fin.close();
		fout.flush();
		fout.close();
	}
	
	/*
	 * 文件夹的复制
	 * @param copiedFolderPath 复制目标地址
	 * @param pastedFolderPath 粘贴地址
	 */
	public void folderCopy(String copiedFolderPath,String pastedFolderPath) throws IOException {
		File copiedFolder = new File(copiedFolderPath);
		File pastedFolder = new File(pastedFolderPath);
		String [] filesPath = copiedFolder.list();
		if(!pastedFolder.exists()) //当目标地址为空
			pastedFolder.mkdirs(); //创建文件夹，课多级创建
		for(String fp:filesPath) {
			//复制子文件夹
			if(new File(copiedFolder + File.separator + fp).isDirectory()) 
				folderCopy(copiedFolderPath+File.separator+fp,pastedFolderPath + File.separator + fp);
			//复制文件夹中的文件，调用文件复制函数fileCopy
			else
				fileCopy(copiedFolderPath+File.separator+fp,pastedFolderPath + File.separator + fp);
		}
		System.out.println("文件夹复制完成，位置为："+pastedFolder.getAbsolutePath());
	}
	
	/*
	 * 文件加密
	 * 加密方式用 pwd 与文件内容异或运算
	 * @param encrypted_file 是代加密文件
	 * @param crypt_file 为加密后的文件
	 */
	public void encryptFile(File encrypted_file, String crypt_file_Path) throws IOException {
		if(!encrypted_file.exists()) {
			System.out.println("文件不存在，加密失败！");
			return ;
		}
		File cryptFile = new File(crypt_file_Path);
		if(!cryptFile.exists())
			cryptFile.createNewFile();
		FileInputStream fin = new FileInputStream(encrypted_file);
		FileOutputStream fout = new FileOutputStream(cryptFile);
		int count = 0;
		int size = 10240;
		byte [] b = new byte[size];
		while((count = fin.read(b,0,size))!=-1) {
			for(int i = 0;i<count;i++) {
				b[i] = (byte) (b[i]^password); //密码与值进行异或运算
			}
			fout.write(b,0,count);
		}
		System.out.println("加密成功！");
		fin.close();
		fout.flush();
		fout.close();
	}
	
	/*
	 * 文件解密
	 * 解密方式用pwd 123 与文件内容异或运算
	 * @param decrypted_file 是代加密文件
	 * @param crypt_file 为加密后的文件
	 */
	public void decryptFile(File decrypted_file, String crypt_file_Path) throws IOException {
		if(!decrypted_file.exists()) {
			System.out.println("文件不存在，解密失败！");
			return ;
		}
		File cryptFile = new File(crypt_file_Path);
		if(!cryptFile.exists())
			cryptFile.createNewFile();
		FileInputStream fin = new FileInputStream(decrypted_file);
		FileOutputStream fout = new FileOutputStream(cryptFile);
		int count = 0;
		int size = 10240;
		byte [] b = new byte[size];
		while((count = fin.read(b,0,size))!=-1) {
			for(int i = 0;i<count;i++) {
				b[i] = (byte) (b[i]^password); //密码与值进行异或运算
			}
			fout.write(b,0,count);
		}
		System.out.println("解密成功！");
		fin.close();
		fout.flush();
		fout.close();
	}
	
	/*
	 * 压缩文件
	 * @param zipFilePath1是待压缩文件
	 * @param zipFilePath2是压缩后的文件
	 */
	public void zipFile(String zipFilePath1,String zipFilePath2) throws IOException{
		File file = new File(zipFilePath1);
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFilePath2));
        zipHelp(zipOut,file,file.getName());
        zipOut.close();
        zipOut.close();
	}
	
	public void zipHelp(ZipOutputStream zipOut,File inputFile,String fileName) throws IOException {
		InputStream input = null;
		if(inputFile.isDirectory()){  
        	//当为文件夹的时候
        	//递归文件路径
            String [] filesPath = inputFile.list();
            for(String fp:filesPath){
            	zipHelp(zipOut,new File(inputFile.getPath()+File.separator+fp),fileName+File.separator+fp);         
            }
        }else {
        	//当为文件的时候，转换为压缩文件
        	input = new FileInputStream(inputFile);
            zipOut.putNextEntry(new ZipEntry(fileName));
            int count = 0;
            byte [] b = new byte[10240];
            while((count = input.read(b,0,10240)) != -1){
                zipOut.write(b,0,count);
            }
            input.close();
        }
		
	}
	
	/*
	 * 解压文件
	 * @param zipFilePath1是待解压文件
	 * @param zipFilePath2是解压后的文件
	 */
	public void unzipFile(String unzipFilePath1,String unzipFilePath2) throws IOException{
		File inFile = new File(unzipFilePath1);
		File outFile = null;
		ZipFile zipFile = new ZipFile(inFile);
		ZipInputStream zipInput = new ZipInputStream(new FileInputStream(inFile));
		ZipEntry entry = null;  //条目
		InputStream input = null;  //输入流
		OutputStream output = null;  //输出流
		while((entry = zipInput.getNextEntry())!=null) {
//			System.out.println("解压缩" + entry.getName() + "文件");
            outFile = new File(unzipFilePath2 + File.separator + entry.getName());
            if(!outFile.getParentFile().exists()){  //上级目录不存在，创建目录
                outFile.getParentFile().mkdirs();
            }
            if(!outFile.exists()){
                outFile.createNewFile();
            }
            input = zipFile.getInputStream(entry);
            output = new FileOutputStream(outFile);
            int count = 0;
            byte [] b = new byte[10240];
            while((count = input.read(b,0,10240)) != -1){
            	output.write(b,0,count);
            }
            input.close();
            output.flush();
            output.close();
		}
	}
}
