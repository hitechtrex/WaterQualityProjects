/**
* CS5900-03 Database
* Dr. Zhang
* Water Quality Project
*
* Author: Steve Jia
* Date: 2016-04-12
* Filename: KMeans.java
**/
import java.util.*;
import java.io.*;

public class KMeans
{
   //declare reference variables
   private int numClusters = 5; //set number of clusters to 5
   private int numDataPoints;
   //input data are normalized, so data range is between 0 and 1
   private final static int maxRange = 1;
   private final static int minRange = 0;
   public List<Cluster> clusterList;
   private List<DataPoint> dataSet;

   /**
   * Constructor: KMeans()
   **/
   public KMeans()
   {
      clusterList = new ArrayList<Cluster>();
      //dataSet = new ArrayList<DataPoint>();
   }


   /**
   * Method: initClusters()
   *  Initialize clusters, for each cluster, generate a new centroid DataPoint
   *   object. After the clusters are created, read in the input file for data
   **/
   public void initClusters(File dataFile)
   {
      for(int i = 0; i < numClusters; i++)
      {
         //create Cluster object
         Cluster cluster = new Cluster(i);
         //create and assign initial random centroid data points
         cluster.centroid = DataPoint.genRandomPoint(minRange, maxRange);
         clusterList.add(cluster);
      }

      //read in the data file and get the complete data set
      dataSet = readDataFile(dataFile);
   }//end: initClusters()


   /**
   * Method: clusterData()
   *  This is the main working method to cluster each DataPoint object and move
   *  each cluster's centroid until the distance between the centroid's previous
   *   location is the same as the new location
   **/
   public void clusterData()
   {
      //declare and initializes a variable as the end condition
      boolean clusterUnstable = true;
      while(clusterUnstable)
      {
         //save current centroid locations
         List<DataPoint> pastCentroids = getCurrentCentroids();
         //de-cluster all the data points
         clearClusters();
         //assign each data point to a cluster based on its distance to each
         //  centroid
         assignCluster();
         //re-calculate centroid locations based on the data points in
         //  the cluster
         repositionCentroids();
         //get current centroid locations and compare them with the previous
         //   locations
         List<DataPoint> currentCentroids = getCurrentCentroids();
         clusterUnstable = compareCentroids(pastCentroids, currentCentroids);
      }

      //display cluster information
      for(Cluster clust : clusterList)
      {
         System.out.println("Cluster Size: " + clust.pointsList.size());
         clust.printCluster();
      }
   }

   /**
   * Method: getCurrentCentroids()
   *  Collect current centroid data and store them in a new list
   **/
   private List<DataPoint> getCurrentCentroids()
   {
      List<DataPoint> centList = new ArrayList<DataPoint>(numClusters);
      for(Cluster clust : clusterList)
      {
         centList.add(new DataPoint("Centroid", 99,
                                    clust.centroid.x,
                                    clust.centroid.y,
                                    clust.centroid.z,
                                    clust.centroid.a,
                                    clust.centroid.b,
                                    clust.centroid.c));
      }
      return centList;
   }//end: getAllCentroids()


   /**
   * Method: assignCluster()
   *   compare the distance between each data point to each centroid, and then
   *   assigns the data point to the cluster with the smallest distance
   **/
   private void assignCluster()
   {
      //initialization
      double max = Double.MAX_VALUE;
      int clusterId = 0;
      double distance = 0.0;

      //System.out.println("Assigning Data Points to Clusters");
      for (DataPoint dp : dataSet)
      {
         double min = max;
         for(int i = 0; i < numClusters; i++)
         {
            //calculate the distnace
            distance = DataPoint.calcDistance(dp, clusterList.get(i).centroid);
            //update the min distance and set cluster number
            if(distance < min)
            {
               min = distance;
               clusterId = i;
            }
         }
         //assign cluster number and add data point to that cluster
         dp.clusterId = clusterId;
         clusterList.get(clusterId).addDataPoint(dp);
      }
   }//end: assignCluster()


   /**
   * Method: repositionCentroids()
   *   Calculates the average location of the centroid based on all the data
   *   points in that cluster, and then assigns the newly calculated position
   *   to the centroid
   **/
   private void repositionCentroids()
   {
      for(Cluster clust : clusterList)
      {
         double sumX = 0.0;
         double sumY = 0.0;
         double sumZ = 0.0;
         double sumA = 0.0;
         double sumB = 0.0;
         double sumC = 0.0;

         List<DataPoint> dpList = clust.pointsList;

         if(dpList.size() > 0)
         {
            //find the sum of each coordinate
            for(DataPoint dp : dpList)
            {
               sumX += dp.x;
               sumY += dp.y;
               sumZ += dp.z;
               sumA += dp.a;
               sumB += dp.b;
               sumC += dp.c;
            }
            //calculate the average for the new location
            clust.centroid.x = sumX/dpList.size();
            clust.centroid.y = sumY/dpList.size();
            clust.centroid.z = sumZ/dpList.size();
            clust.centroid.a = sumA/dpList.size();
            clust.centroid.b = sumB/dpList.size();
            clust.centroid.c = sumC/dpList.size();
         }
      }
   }//end: repositionCentroid()

   /**
   * Method: clearClusters()
   *   clears all the DataPoint references for all the clusters
   **/
   private void clearClusters()
   {
      for(Cluster clust : clusterList)
      {
         clust.clearPointsList();
      }
   }

   /**
   * Method: compareCentroids()
   *   compare the coordinates between two lists of data points and see if they
   *   the same
   **/
   private boolean compareCentroids(List<DataPoint> pastCentroids,
                                    List<DataPoint> currentCentroids)
   {
      boolean result = true;
      double distance = 0.0;
      //calculate a sum for all the centroids' distance between their previous
      //  locations and their current locations
      for(int i = 0; i < numClusters; i++)
      {
         distance += DataPoint.calcDistance(pastCentroids.get(i),
                                            currentCentroids.get(i));
      }
      System.out.println("Centroids Distance: " + distance);
      //if the sum of distnaces is zero, then it means the clusters are stable
      if(distance == 0)
      {
         result = false;
      }
      return result;
   }

   /**
   * Method: readDataFile()
   *   Read in the input data file and creates new DataPoint objects
   **/
   public List<DataPoint> readDataFile(File dataFile)
   {
      //declares an ArrayList that contains DataPoint objects
      List<DataPoint> list = new ArrayList<DataPoint>();

      try
      {
         //declare a scanner to read the file
         Scanner scan = new Scanner(dataFile);

         String line; //string variable for the file

         System.out.println("Reading Data File...");
         while(scan.hasNext())
         {
            //read the line
            line = scan.nextLine();

            //split the numbers into an array
            String[] texts = line.split(",");
            //grab the date and siteId from data
            String date = texts[0];
            int siteId = Integer.parseInt(texts[1]);
            //convert texts to numbers
            double x = Double.parseDouble(texts[2]);
            double y = Double.parseDouble(texts[3]);
            double z = Double.parseDouble(texts[4]);
            double a = Double.parseDouble(texts[5]);
            double b = Double.parseDouble(texts[6]);
            double c = Double.parseDouble(texts[7]);
            //create new DataPoint object and add it to the list
            list.add(new DataPoint(date, siteId, x, y, z, a, b, c));
         }
         System.out.println("File Read, Number of Data Points Read: " + list.size());
      }
      catch (FileNotFoundException e)
      {
         System.out.println(e);
      }

      return list;
   }//end readDataFile()

   /**
   * Method: saveDataToFile()
   *  After the clusters are stable, save all the data to an output file for
   *   further analysis
   **/
   public void saveDataToFile(String outputFile)
   {
      try
      {
         //first print writer is for see5 data
         PrintWriter pw = new PrintWriter(new File(outputFile));
         //second print writer is for prediction data
         PrintWriter pw2 = new PrintWriter(new File("Short"+outputFile));

         if (pw != null && pw2 != null )
         {
            for (Cluster clust : clusterList)
            {
               for(DataPoint dp : clust.pointsList)
               {
                 pw.println(dp.toString());
                 pw2.println(dp.toStringShort());
               }
            }
         }
         pw.close();
         pw2.close();
      }
      catch (Exception x)
      {
         System.out.println(x.toString());
      }
   }//end: saveDataToFile()
}
