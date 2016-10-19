package NaiveBayes;

import java.io.File;

class GetFileName {
    public GetFileName()
    {
    	
    }

    public String[] getFile(String path){
        File file = new File(path);
        File[] array = file.listFiles();
        String[] allFileName = new String[array.length];
        for(int i=0;i<array.length;i++){
            allFileName[i] = array[i].getName();
           // System.out.println(array[i].getName());
        }
        return allFileName;
    }
}

