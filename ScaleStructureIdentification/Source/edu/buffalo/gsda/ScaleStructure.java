package edu.buffalo.gsda;

import java.util.Enumeration;
import java.util.Hashtable;

import edu.princeton.cs.algs4.CC;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Queue;


// The Scale Structure Identification Method
public class ScaleStructure 
{
	// *** The parameter "locationArray" contains an array of location coordinates associated with a place name
	// *** The parameter "maximumDistance" specifies the maximum distance of the study area, either the maximum distance along x axis or the maximum distance along the y axis
	// *** The parameter "factor" is used to convert degrees to meters. If your location coordinates are in lat lng degrees, please use 100000; If your location coordinates are 
	//      already in meters, please use 1 here.
	// Note: if your locations are in degrees, you need to ensure that your maximum distance is also in degrees
	public static Hashtable<String, Double> compute(double[][] locationArray, double maximumDistance, double factor)  
	{
		try 
		{
			Hashtable<String, Double> distanceTable = new Hashtable<>();
			
			for(int i=0;i<locationArray.length;i++)
				for(int j=i+1;j<locationArray.length;j++)
				{
					double xDistance = (locationArray[i][0] - locationArray[j][0]) * factor;
					double yDistance = (locationArray[i][1] - locationArray[j][1]) * factor;
					
					double absDistance = Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
					distanceTable.put(i+"-"+j, absDistance);
				}
			
			double maximumDistanceConverted = maximumDistance * factor;
			// Calculate how many steps in total for the calculation, using a base of 2 
			int maxStep = (int)(Math.log(maximumDistanceConverted)/Math.log(2)); 
			
			int step = 1;
			double cutDistance = Math.pow(2, step); // The distance threshold based on which an edge is built or not
			double totalEntropyValue = 0;
			while (step < maxStep) 
			{
				Graph graph = new Graph(locationArray.length);
				Enumeration<String> edgeEnumeration = distanceTable.keys();
				while (edgeEnumeration.hasMoreElements()) 
				{
					String thisEdge = edgeEnumeration.nextElement();
					double thisDistance = distanceTable.get(thisEdge);
					// if the distance between two points is smaller than the cut distance, then build an edge
					if(thisDistance <= cutDistance)
					{
						String[] locationInfo = thisEdge.split("-");
						graph.addEdge(Integer.parseInt(locationInfo[0]), Integer.parseInt(locationInfo[1]));
					}
				}
				
				
				// construct connected component from the graph
				CC connectedComponent = new CC(graph);
				int m = connectedComponent.count();
				
		        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[m];
		        for (int i = 0; i < m; i++) 
		        {
		            components[i] = new Queue<Integer>();
		        }
		        for (int v = 0; v < graph.V(); v++) {
		            components[connectedComponent.id(v)].enqueue(v);
		        }

		        // Calculate entropy under this cut distance
		        double thisEntropy = 0;
		        for (int i = 0; i < m; i++)
		        {
		        	double componentPercentage = (components[i].size()*1.0 / locationArray.length*1.0);
		            thisEntropy += (-1) * componentPercentage * Math.log(componentPercentage); 
		        }

		        totalEntropyValue += thisEntropy;
		        
				step++;
				cutDistance =Math.pow(2, step); 
			}
			
			Hashtable<String, Double> resultTable = new Hashtable<>();
			resultTable.put("entropy", totalEntropyValue);  // this is the original SSI
			resultTable.put("entropy_sqrt", totalEntropyValue*(1.0/Math.pow(locationArray.length, 0.5))); 
			resultTable.put("entropy_log", totalEntropyValue*(1.0/(Math.log(locationArray.length)/Math.log(2)))); 
			resultTable.put("entropy_count", totalEntropyValue*(1.0/locationArray.length)); 
			
			return resultTable;

		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
}
