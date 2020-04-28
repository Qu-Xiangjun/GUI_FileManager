package lab2;


import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import java.io.*;

public class fileManage {	
	private static  byte password = 123;
	/*
	 * �����ļ���
	 */
	public File createDir(String dir) {
		File newDir = new File(dir);
		if(!newDir.exists()) {	//�ļ���δ������
			newDir.mkdirs();	//������Դ������·���µ��ļ���
			System.out.println("�ļ��д����ɹ�");
		}
		return newDir;
	}
	
	
	/*
	 * ɾ���ļ��к��ļ�
	 * ���õݹ�ɾ���ļ���
	 */
	public void deleteFile(File file) {
		if(file.isDirectory()) {  //ɾ���ļ���
			File[] files = file.listFiles(); 
			for(File f:files) {
				deleteFile(f);	//�ݹ�ɾ���ļ�������
			}
		}
		//ɾ���ļ� or �ļ���
		//��file���ļ��У���ɾ�����ļ������ݺ�ɾ���ļ���
		//��file���ļ�������һ��������ֱ��ɾ��
		file.delete();	
	}
	
	/*
	 * �ļ�����
	 * @param copiedFilePath ����Ŀ���ַ
	 * @param pastedFilePath ճ����ַ
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
		System.out.println("�ļ�������ɣ�λ��Ϊ��"+pastedFile.getAbsolutePath());
		fin.close();
		fout.flush();
		fout.close();
	}
	
	/*
	 * �ļ��еĸ���
	 * @param copiedFolderPath ����Ŀ���ַ
	 * @param pastedFolderPath ճ����ַ
	 */
	public void folderCopy(String copiedFolderPath,String pastedFolderPath) throws IOException {
		File copiedFolder = new File(copiedFolderPath);
		File pastedFolder = new File(pastedFolderPath);
		String [] filesPath = copiedFolder.list();
		if(!pastedFolder.exists()) //��Ŀ���ַΪ��
			pastedFolder.mkdirs(); //�����ļ��У��ζ༶����
		for(String fp:filesPath) {
			//�������ļ���
			if(new File(copiedFolder + File.separator + fp).isDirectory()) 
				folderCopy(copiedFolderPath+File.separator+fp,pastedFolderPath + File.separator + fp);
			//�����ļ����е��ļ��������ļ����ƺ���fileCopy
			else
				fileCopy(copiedFolderPath+File.separator+fp,pastedFolderPath + File.separator + fp);
		}
		System.out.println("�ļ��и�����ɣ�λ��Ϊ��"+pastedFolder.getAbsolutePath());
	}
	
	/*
	 * �ļ�����
	 * ���ܷ�ʽ�� pwd ���ļ������������
	 * @param encrypted_file �Ǵ������ļ�
	 * @param crypt_file Ϊ���ܺ���ļ�
	 */
	public void encryptFile(File encrypted_file, String crypt_file_Path) throws IOException {
		if(!encrypted_file.exists()) {
			System.out.println("�ļ������ڣ�����ʧ�ܣ�");
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
				b[i] = (byte) (b[i]^password); //������ֵ�����������
			}
			fout.write(b,0,count);
		}
		System.out.println("���ܳɹ���");
		fin.close();
		fout.flush();
		fout.close();
	}
	
	/*
	 * �ļ�����
	 * ���ܷ�ʽ��pwd 123 ���ļ������������
	 * @param decrypted_file �Ǵ������ļ�
	 * @param crypt_file Ϊ���ܺ���ļ�
	 */
	public void decryptFile(File decrypted_file, String crypt_file_Path) throws IOException {
		if(!decrypted_file.exists()) {
			System.out.println("�ļ������ڣ�����ʧ�ܣ�");
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
				b[i] = (byte) (b[i]^password); //������ֵ�����������
			}
			fout.write(b,0,count);
		}
		System.out.println("���ܳɹ���");
		fin.close();
		fout.flush();
		fout.close();
	}
	
	/*
	 * ѹ���ļ�
	 * @param zipFilePath1�Ǵ�ѹ���ļ�
	 * @param zipFilePath2��ѹ������ļ�
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
        	//��Ϊ�ļ��е�ʱ��
        	//�ݹ��ļ�·��
            String [] filesPath = inputFile.list();
            for(String fp:filesPath){
            	zipHelp(zipOut,new File(inputFile.getPath()+File.separator+fp),fileName+File.separator+fp);         
            }
        }else {
        	//��Ϊ�ļ���ʱ��ת��Ϊѹ���ļ�
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
	 * ��ѹ�ļ�
	 * @param zipFilePath1�Ǵ���ѹ�ļ�
	 * @param zipFilePath2�ǽ�ѹ����ļ�
	 */
	public void unzipFile(String unzipFilePath1,String unzipFilePath2) throws IOException{
		File inFile = new File(unzipFilePath1);
		File outFile = null;
		ZipFile zipFile = new ZipFile(inFile);
		ZipInputStream zipInput = new ZipInputStream(new FileInputStream(inFile));
		ZipEntry entry = null;  //��Ŀ
		InputStream input = null;  //������
		OutputStream output = null;  //�����
		while((entry = zipInput.getNextEntry())!=null) {
//			System.out.println("��ѹ��" + entry.getName() + "�ļ�");
            outFile = new File(unzipFilePath2 + File.separator + entry.getName());
            if(!outFile.getParentFile().exists()){  //�ϼ�Ŀ¼�����ڣ�����Ŀ¼
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
