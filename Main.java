package NaiveBayes;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
	public static void main(String[] args) throws IOException
	{
		//��Ŀ¼
		 String[] _Category ;
		 String TestClass;
		long startTime=System.currentTimeMillis();//��ȡ��ʼʱ��

		//�����µ��ļ�
		String[] dirName  = new String[1];
		GetFileName getFileName = new GetFileName();
		dirName[0] = "test/";
		 _Category  = getFileName.getFile(dirName[0]); //�õ����е����
		 
		 int TestNum[]  = new int[_Category.length]; //����ָ�������ĵ���
		 int correctfile[]  = new int[_Category.length]; //��ȷ������ĵ���
		 
		 for(int i = 0;i < _Category.length;i++)
		 {
			 TestNum[i] = 0;
			 correctfile[i]= 0;
		 }
		 NaiveBayes naiveBayes = new NaiveBayes();
		 naiveBayes.Tainer_NaiveBayes();
		 HashMap[]  probility = naiveBayes.getHashMap();
		 HashMap  test   = new HashMap<String,Integer>();
		 for(int i = 0; i < _Category.length;i++ )
		 {
			 test.put(_Category[i],0);
		 }
		 for(int i = 0; i < _Category.length;i++)
		 {
			 String dirNext[] = new String[1];
			dirNext[0] =  dirName[0]+ _Category[i] + "/";
			 String _catalog[] = getFileName.getFile(dirNext[0]);//�õ������µ�����Ŀ¼
			 int fileNumber;
			 fileNumber = _catalog.length;
			 
			 for(int j = 0;j < _catalog.length;j++)
			 {
				 TestClass = naiveBayes.Test_NaiveBayes(_Category[i], dirNext[0] + _catalog[j],i);
				 System.out.println("TestClass"+TestClass);
				 System.out.println("_Category" + _Category[i]);
				 if(TestClass.equals(_Category[i]))
				 {
					 correctfile[i] ++;
				 }

				 if(!TestClass.isEmpty())
				 {
					 test.put(TestClass, (Integer)test.get(TestClass)+1);
				 }
				 
			 }

		 }
		 
		 System.out.println("׼ȷ��  = �ֶԵ�������/�����Ϊ�����������");
		 for(int k = 0;k < _Category.length;k++)
		 {
			 String dirNext[] = new String[1];
			dirNext[0] =  "test/"+ _Category[k] + "/";
			 String _catalog[] = getFileName.getFile(dirNext[0]);//�õ������µ�����Ŀ¼
			 int longth = _catalog.length;
			 if ( longth >50 &&longth  < 100)
				 longth = longth -40;
			 else if(longth < 50 && longth >10)
			 {
				 longth = longth - 10;
			 }
			 else if(longth >  100)
			 {
				 longth = longth - 40;
			 }
			if(_catalog.length > 0)
			{
				System.out.printf("%s��׼ȷ��Ϊ%5f\t\t\t\t�ٻ���Ϊ��%5f\n",_Category[k],
						 (double)correctfile[k] / (Integer)test.get(_Category[k]),
						 (double)correctfile[k] / longth);	
			}
			 
		 }
		 long endTime=System.currentTimeMillis(); //��ȡ����ʱ��
		 System.out.print("����ʱ�䣺");
		 System.out.println((endTime-startTime)+"ms");
			
	}
}

