import edu.duke.*;

public class HelloWorld {
	/**
	 * Read file of ways to say hello and print each line of the file
	 */
	public void sayHello(){
		//URLResource rs = new URLResource("http://nytimes.com"); 
		FileResource resource = new FileResource("somefile.txt");
		for (String line : resource.words()) {
			System.out.println(line);
		}
	}
}
