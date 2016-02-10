/**
 * 
 */
 

import java.io.File;

import org.apache.commons.csv.*;
import edu.duke.*;

/**
 * @author ilham
 *
 */
public class babyNames {
	
	static CSVParser loadCSV(){
		FileResource fr = new FileResource();
		CSVParser csv = fr.getCSVParser(false);
		return csv;
	}
	
	static CSVParser loadCSV(String path){
		FileResource fr = new FileResource(path);
		CSVParser csv = fr.getCSVParser(false);
		return csv;
	}
	
	static CSVParser loadCSV(File file){
		FileResource fr = new FileResource(file);
		CSVParser csv = fr.getCSVParser(false);
		return csv;
	}
	
	/**
	 * prints total number of births, male births and female births and total
	 * number of names, male names and female names.
	 */
	static void totalBirths(){
		CSVParser csv = loadCSV();
		int totalBorn = 0;
		int fBorn = 0;
		int mBorn = 0;
		int totalNames = 0;
		int fNames = 0;
		int mNames = 0;
		for (CSVRecord rec: csv){
			int numBorn = Integer.parseInt(rec.get(2));
			totalBorn += numBorn;
			totalNames++;
			if (rec.get(1).equals("M")){
				mBorn += numBorn;
				mNames++;
			}else{
				fBorn += numBorn;
				fNames++;
			}
		}
		System.out.println("Total number of births: " + totalBorn);
		System.out.println("Total number of male births: " + mBorn);
		System.out.println("Total number of female births: " + fBorn);
		System.out.println("Total number of names: " + totalNames);
		System.out.println("Total number of male names: " + mNames);
		System.out.println("Total number of female names: " + fNames);
	}
	
	/**
	 * Returns the rank of given name in the given year's file, for the given gender
	 * @param year
	 * @param name
	 * @param gender F for female, M for male
	 * @return an integer rank of the name, returns -1 if name is not found in the file
	 */
	
	static int getRank(CSVParser csv, String name, String gender){
		int rank = 0;
		boolean foundIt = false;
		for (CSVRecord rec: csv){
			if (rec.get(1).equals(gender)){
				rank++;
				if (rec.get(0).equals(name)){
					foundIt = true;
					break;
				}
			}
		}
		if (foundIt){
			return rank;
		}else{
			return -1;
		}
	}
	
	static int getRank(int year, String name, String gender){
		CSVParser csv = loadCSV("yob" + year + ".csv");
		return getRank(csv, name, gender);
	}
	
	/**
	 * Gets the name with the given rank and gender in the given year's file
	 * @param year
	 * @param rank
	 * @param gender F for female, M for male
	 * @return the name, and the string "NO NAME" if the given rank is out of range
	 */
	static String getName(int year, int rank, String gender){
		CSVParser csv = loadCSV("yob" + year + ".csv");
		String name = null;
		int iterations = 0;
		boolean foundIt = false;
		for (CSVRecord rec: csv){
			if (rec.get(1).equals(gender)){
				iterations++;
				if (iterations == rank){
					foundIt = true;
					name = rec.get(0);
					break;
				}
			}	
		}
		if (foundIt){
			return name;
		}else{
			return "NO NAME";
		}
	}
	
	/**
	 * Gives the name in newYear with the same popularity ranks as the given name in
	 * the given year.
	 * @param name
	 * @param year
	 * @param newYear
	 * @param gender F for female, M for male
	 */
	static void whatIsNameInYear(String name, int year, int newYear, String gender){
		int rank = getRank(year, name, gender);
		String newName = getName(newYear, rank, gender);
		System.out.println("Babies named " + name + " born in " + year + " would be "
				+ "named " + newName + " if they were born in " + newYear + " instead.");
	}
	
	/**
	 * Selects a range of files and returns the year in which the given name, gender pair
	 * have the highest rank. Returns -1 if the given name, gender pair does not
	 * appear in any of the selected files.
	 * @param name
	 * @param gender
	 * @return
	 */
	static int yearOfHighestRank(String name, String gender){
		DirectoryResource dr = new DirectoryResource();
		Integer highestRank = null;
		String path = "us_babynames_by_year";
		for (File file: dr.selectedFiles()){
			CSVParser csv = loadCSV(file);
			int rank = getRank(csv, name, gender);
			if (rank != -1){
				if (highestRank == null && rank !=-1){
					highestRank = rank;
					path = file.getPath();
				}
				//highest rank is numerically lower, i.e. rank n is higher than rank n+1
				else if (highestRank > rank && rank != -1){
					highestRank = rank;
					path = file.getPath();
				}
			}
		}
		//System.out.println(path);
		if (path != null){
			int startIndex = path.lastIndexOf("\\yob") + 4;
			int endIndex = path.indexOf(".csv");
			String year = path.substring(startIndex, endIndex);
			int yearInt = Integer.parseInt(year);
			return yearInt;
		}else{
			return -1;
		}
	}
	
	/**
	 * Calculates the average rank of a name-gender pair over selected files.
	 * @param name
	 * @param gender "M" for male, "F" for female
	 * @return the average rank, returns -1.0 if name-gender pair not found in any of selected
	 * files
	 */
	static double getAverageRank(String name, String gender){
		DirectoryResource dr = new DirectoryResource();
		int num_of_files = 0;
		double totalRank = 0;
		for (File file: dr.selectedFiles()){
			CSVParser csv = loadCSV(file);
			int rank = getRank(csv, name, gender);
			if (rank !=-1){
				num_of_files++;
				totalRank += rank;
			}
		}
		if (num_of_files !=0){
			return totalRank/num_of_files;
		}else{
			return -1.0;
		}
	}
	
	/**
	 * Gets the total number of births of babies with the same gender in the given year
	 * with names that are ranked higher than name.
	 * @param year
	 * @param name
	 * @param gender
	 * @return the number of births, returns -1 if name-gender pair is not found
	 */
	static int getTotalBirthsRankedHigher(int year, String name, String gender){
		int rank = getRank(year, name, gender);
		CSVParser csv = loadCSV("yob" + year + ".csv");
		if (rank !=-1){
			int iterations = 0;
			int num_of_births = 0;
			for (CSVRecord rec: csv){
				if (iterations >= rank -1 ){
					//e.g. if the name is ranked 3, then the sum will stop at
					//the name ranked 2, so births with rank 1 and 2 only are added
					break;
				}
				if (rec.get(1).equals(gender)){
					iterations++;
					num_of_births += Integer.parseInt(rec.get(2));					
				}
			}
			return num_of_births;
		}else{
			return -1;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//totalBirths();
		//System.out.println(getRank(1960, "Emily", "M"));
		//System.out.println(getName(1982, 450, "M"));
		//whatIsNameInYear("Owen", 1974, 2014, "M");
		System.out.println(yearOfHighestRank("Mich", "M"));
		//System.out.println(getAverageRank("Robert", "M"));
		//System.out.println(getTotalBirthsRankedHigher(1990, "Drew", "M"));
	}

}
