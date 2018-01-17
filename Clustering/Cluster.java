/**
* CS5900-03 Database
* Dr. Zhang
* Water Quality Project
*
* Author: Steve Jia
* Date: 2016-04-12
* Filename: Cluster.java
**/
import java.util.List;
import java.util.ArrayList;

public class Cluster
{
   public int clusterId;
   public DataPoint centroid;
   public List<DataPoint> pointsList;

   /**
   * Constructor: Cluster()
   *  Creates a new Cluster object, and initializes its centroid to null,
   *  create a new ArrayList for DataPoint objects, and assigns a new clusterId
   **/
   public Cluster(int newClusterId)
   {
      clusterId = newClusterId;
      centroid = null;
      pointsList = new ArrayList<DataPoint>();
   }

   /**
   * Method: addPoint()
   *  Add a DataPoint reference to this cluster
   **/
   public void addDataPoint(DataPoint newDataPoint)
   {
      pointsList.add(newDataPoint);
   }

   /**
   * Method: clearPointsList()
   *  Removes all DataPoint references from the list
   **/
   public void clearPointsList()
   {
      pointsList.clear();
   }

   /**
   * Method: printCluster()
   *   Displays the DataPoint values in this cluster
   **/
   public void printCluster()
   {
		System.out.println("[Cluster: " + clusterId+"]");
		System.out.println("[Centroid: " + centroid + "]");
		System.out.println("[Points: \n");
		for(DataPoint p : pointsList) {
			System.out.println(p);
		}
		System.out.println("]");
	}
}
