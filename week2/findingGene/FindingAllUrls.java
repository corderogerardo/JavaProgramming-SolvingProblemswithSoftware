import edu.duke.StorageResource;
import edu.duke.URLResource;

public class FindingAllUrls {

	private static String UrlLoc = "http://www.dukelearntoprogram.com/course2/data/newyorktimes.html";
	
	public static StorageResource findURLS(){
		
		StorageResource mStorageResource = new StorageResource();

		URLResource res = new URLResource(UrlLoc);
		analyzeAndStoreURLS2(mStorageResource,res.asString());
		
		return mStorageResource;
	}
	
	
	public static void printURLS(StorageResource mStorageResource){
		
		//Print all URLs
		for(String url: mStorageResource.data()){
				System.out.println(url);
		}
		
		//Total number of all URLs
		System.out.println("Total number of URLs = " + mStorageResource.size());
		
		//Print number of secure links
		int noSecuredLinks = 0;
		for(String url: mStorageResource.data()){
			if(url.startsWith("https")){
				noSecuredLinks++;
			}
		}
		System.out.println("Total number of Secured links  = " + noSecuredLinks);
		
		//Number of links that have ".com"
		int noDotComLinks = 0;
		for(String url: mStorageResource.data()){
			if(url.indexOf(".com") != -1){
				noDotComLinks++;
			}
		}
		System.out.println("Total number of DotCom links  = " + noDotComLinks);

		//Number of links that end with .com or .com/
		int simpleLinks = 0;
		for(String url: mStorageResource.data()){
			if(url.endsWith(".com") || url.endsWith(".com/")){
				simpleLinks++;
			}
		}
		
		System.out.println("Total number of links that end with .com or .com/  = " + simpleLinks);

		//Number of dots 
		int dotCount = 0;
		for(String url: mStorageResource.data()){
			dotCount += url.length() - url.replaceAll(".", "").length();
		}
		
		System.out.println("Total number of dots = " + dotCount);

	}
	
	private static void analyzeAndStoreURLS2(StorageResource mStorageResource,String ipString){
		
		String ipStringPostProc = 	ipString.toLowerCase();	
		
		int start = 0;
		
		while(true){
			int index = ipStringPostProc.indexOf("href=",start);
			if(index == -1){
				break;
			}
			
			int firstQuote = index+6;
			int endQuote = ipStringPostProc.indexOf("\"",firstQuote);
			String sub = ipString.substring(firstQuote, endQuote);
			if(sub.startsWith("http")){
				mStorageResource.add(sub);
			}
			start = endQuote+1;
		}
	}
	
	private static void analyzeAndStoreURLS(StorageResource mStorageResource,String ipString){
	
		String ipStringPostProc = 	ipString.toLowerCase();	
		
		int startIndex = ipStringPostProc.indexOf("href=\"");
		if(startIndex == -1){
			// do nothing
		}else{
			startIndex = startIndex+6;
			int endIndex = 0;
			endIndex = ipStringPostProc.indexOf("\"", startIndex);
			String sub = ipString.substring(startIndex,endIndex);
			if(sub.startsWith("http"))
				mStorageResource.add(sub);
		}

	}
	
	public static void main(String[] args) {
		StorageResource mStorageResource = findURLS();
		printURLS(mStorageResource);
    }

	
}
