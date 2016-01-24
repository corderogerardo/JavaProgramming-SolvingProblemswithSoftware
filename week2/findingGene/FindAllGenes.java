
/**
 * Write a description of class FindAllGenes here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.FileResource;
import edu.duke.StorageResource;

public class FindAllGenes {
    
    public static void printAll(String dna){
        int start = 0;
        String dna1 = dna.toLowerCase();
        int len = dna1.length();
        
        while(true){
            int loc = dna1.indexOf("atg",start);
            
            if(loc == -1){
                break;
            }
            int stop = findStopIndex(dna1,loc + 3);
            if(stop == len){
                start = loc + 3;
                continue;
            }
            
            System.out.println(dna.substring(loc, stop +3));
            start = stop +3;
        
        }
        
    }
    
    public static StorageResource storeall(String dna){
    
        int start = 0;
        
        String dna1 = dna.toLowerCase();
        
        int len = dna1.lenght();
        
        StorageResource sr = new StorageResource();
        
        while(true){
        
                int loc = dna1.indexOf("atg",start);
                if(loc == -1){
                    break;
                }
                int stop = findStopIndex(dna1, loc + 3);
                if(stop == len){
                    start = loc +3;
                    continue;
                }
                sr.add(dna.substring(loc, stop + 3);
                start = stop +3;
        }
        return sr;
    
    }
    
    public static int findStopIndex(String dna, int index){
    
        int stop1 = dna.indexOf("tga",index);
        if(stop1 == -1 || (stop1 - index) % 3 != 0){
            stop1 = dna.length();
        }
        int stop2 = dna.indexOf("taa",index);
        if(stop2 == -1 || (stop2 - index) % 3 != 0){
            stop2 = dna.length();
        }
         int stop3 = dna.indexOf("tag",index);
        if(stop3 == -1 || (stop3 - index) % 3 != 0){
            stop3 = dna.length();
        }
        
        return Math.min(stop1, Math.min(stop2,stop3));
    
    }
    
    public static StorageResource getWords(){
        FileResource fr = new FileResource("");
            StorageResource sr = new StorageResource();
                for(String w : fr.words()){
                    sr.add(w);
                }
                return sr;
    }

}
