package NaiveBayes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class NaiveBayes {
	//类目录
	private String[] _Category ;
	//该类下的文件
	private String[] dirName = new String[1];
	private String[] dirNext;
	//先验概率
	HashMap[] probility;

	public NaiveBayes()
	{
		GetFileName getFileName = new GetFileName();
		dirName[0] = "train/";
		 _Category  = getFileName.getFile(dirName[0]); //得到所有的类别
		
		dirNext = new String[1];
		probility = new HashMap[_Category.length];
		 for(int j = 0;j < _Category.length;j++)
		 {
			 probility[j] = new HashMap<String,Double>();
		 }
	}
	public HashMap[] getHashMap()
	{
		return probility;
	}
	public void Tainer_NaiveBayes()
	{
		GetFileName getFileName = new GetFileName();
//		dirName[0] = "train/";
//		 _Category  = getFileName.getFile(dirName[0]); //得到所有的类别
		 
		 HashMap[] temp = new HashMap[_Category.length];
		 for(int j = 0;j < probility.length;j++)
		 {
			 temp[j] = new HashMap<String,Integer>();
		 }
		 int CategoryNumber = _Category.length; //类别的数目
		 
		 //一次计算每个类别下的类条件概率
		 for(int i = 0; i < CategoryNumber;i++)
		 {
			 dirNext[0] = dirName[0]+ _Category[i] + "/";
			 String _catalog[] = getFileName.getFile(dirNext[0]);//得到该类下的所有目录
			 int fileNumber;
			 fileNumber = _catalog.length;
			 for(int j = 0;j < _catalog.length;j++)
			 {
				 System.out.println(_catalog[j]);
				try {
					FileInputStream fileInputStream = new FileInputStream(dirNext[0]+_catalog[j]);
					InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "GBK");
					BufferedReader reader = new BufferedReader(inputStreamReader);
					String lineContent = "";
					boolean mark = true;
					while ((lineContent = reader.readLine()) != null)
					{
						if(temp[i].containsKey(lineContent))
						{
							temp[i].put(lineContent,(Integer)temp[i].get(lineContent)+1);
							mark = false;
						}
						else if(mark && !lineContent.isEmpty())
						{
							temp[i].put(lineContent, 1);
						}
						
					}//while
						fileInputStream.close();
						inputStreamReader.close();
						reader.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e1) {
						Throwable e = null;
						e.printStackTrace();
					}
				

	            Iterator iterD = temp[i].entrySet().iterator();
	           
	            while (iterD.hasNext())
	            {
	                Map.Entry entry = (Map.Entry) iterD.next();
	                Object key = entry.getKey();
	                String keyS = key.toString();
	                Object val = entry.getValue();
                    Integer valI = (Integer)val;
	                probility[i].put(keyS,(valI - 0.0)/(fileNumber - 0.0));//df+1
	            }//while

			 }//for
			 	
		 }//for
	}

	public String Test_NaiveBayes(String _class,String fileName,int num) throws IOException
	{
		double max = 0.0;
		double temp = 1;
		String Testclass = "";

		for(int j = 0; j < _Category.length;j++)
		{
//			double max = 0.0;
//			double temp = 1;
			try {
				FileInputStream fileInputStream = new FileInputStream(fileName);
				InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "GBK");
				BufferedReader reader = new BufferedReader(inputStreamReader);
				String lineContent = "";

					boolean flag = true;
					while((lineContent = reader.readLine()) != null)
					{
						Iterator iterD = probility[j].entrySet().iterator();
						while (iterD.hasNext())
						{
							Map.Entry entry = (Map.Entry) iterD.next();
							Object key = entry.getKey();
							String keyS = key.toString();
							Object val = entry.getValue();
							Double valI = (Double)val;
					
							  if(keyS.contentEquals(lineContent))
							  {
							    	temp *= valI * 1000;
							    	flag = false;
							    	break;
							  }		 
						}//while
						if(flag == true && !iterD.hasNext())
						{
							 temp = 0 ;
							 break;
						}
						
					}//while
				if(temp > max)
				{
					max = temp;
					Testclass = _Category[j];
				}
				//add(input,num);
				fileInputStream.close();
				inputStreamReader.close();
				reader.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e1) {
				Throwable e = null;
				e.printStackTrace();
			}
			
		}//for
	   return Testclass;
		
		}
	}
	
