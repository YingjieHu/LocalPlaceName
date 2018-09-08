package edu.buffalo.gsda;

import java.io.FileReader;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Vector;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import edu.princeton.cs.algs4.Out;

public class MainForSSI 
{
	// An example of reading an input file (with place names and their associated coordinates), and ranking place names based on their geo-indicativeness
	public static void main(String[] args) 
	{
		try 
		{
			String inputFileString = "SSI_Input.csv"; // args[0]; //  // path to the input file
			double maximumDistance = 0.17; //Double.parseDouble(args[1]);//  // this is the maximum distance of the study area in degrees
			
			FileReader inputFileReader = new FileReader(inputFileString);
			CSVParser csvParser = new CSVParser(inputFileReader, CSVFormat.DEFAULT.EXCEL);
			Hashtable<String, Vector<String>> nameCoordinateTable = new Hashtable<>();
			
			for(CSVRecord csvRecord : csvParser)
			{
				String name = csvRecord.get(0);
				String[] coords = csvRecord.get(1).split("\\|");
				Vector<String> coordVector = new Vector<>();
				for(int i=0;i<coords.length;i++)
					coordVector.add(coords[i]);
				
				nameCoordinateTable.put(name, coordVector);
			}
			csvParser.close();
			
			
			// Build a priority queue which will be used to sort place names based on their SSI entropy
			PriorityQueue<NameEntropy> namePriorityQueue = new PriorityQueue<NameEntropy>(new Comparator<NameEntropy>() {
				public int compare(NameEntropy o1, NameEntropy o2) {
					if(o1.entropy_sqrt < o2.entropy_sqrt)
						return -1;
					else if (o1.entropy_sqrt > o2.entropy_sqrt) 
						return 1;
					else
						return 0;
				}
			});
			
			Enumeration<String> namesEnumeration = nameCoordinateTable.keys();
			while(namesEnumeration.hasMoreElements())
			{
				String thisCandidateName = namesEnumeration.nextElement();
				Vector<String> coordinateVector = nameCoordinateTable.get(thisCandidateName);
				
				if(coordinateVector.size() < 3) continue; // if this name is associated with fewer than 3 points, then remove it
				
				double[][] locationArray = new double[coordinateVector.size()][2];
				for(int i=0;i<coordinateVector.size();i++)
				{
					String thisCoordPair = coordinateVector.get(i);
					String[] coordInfo = thisCoordPair.split(" ");
					double thisLng = Double.valueOf(coordInfo[0]);
				    double thisLat = Double.valueOf(coordInfo[1]);
				    locationArray[i][0] = thisLng;
				    locationArray[i][1] = thisLat;
				}
		
				// Key step: computing the entropy value using scale-structure identification
				Hashtable<String, Double> resultHashtable = ScaleStructure.compute(locationArray, maximumDistance, 100000);
				
				NameEntropy nameEntropy = new NameEntropy(thisCandidateName, resultHashtable.get("entropy"));
				nameEntropy.entropy_sqrt = resultHashtable.get("entropy_sqrt");
				nameEntropy.entropy_log = resultHashtable.get("entropy_log");
				nameEntropy.entropy_count = resultHashtable.get("entropy_count");			
								
				namePriorityQueue.add(nameEntropy);
			}
			
			
			Out outputFile = new Out("SSI_Output.csv");
			outputFile.println("name,entropy_sqrt,entropy,entropy_log,entropy_count");
			while(!namePriorityQueue.isEmpty())
			{
				NameEntropy thisNameEntropy = namePriorityQueue.poll();
				
				outputFile.println("\""+thisNameEntropy.name+"\","+thisNameEntropy.entropy_sqrt+","+thisNameEntropy.entropy+","+thisNameEntropy.entropy_log+","+thisNameEntropy.entropy_count);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

	}

}

class NameEntropy
{
	public String name = null;
	public double entropy = -1;
	public double entropy_sqrt = 0;
	public double entropy_log = 0;
	public double entropy_count = 0;
	
	public NameEntropy(String name, double entropy)
	{
		this.name = name;
		this.entropy = entropy;
	}
}
