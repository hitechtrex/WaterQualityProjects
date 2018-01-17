/**
* CS5900-03 Database
* Dr. Zhang
* Water Quality Project
*
* Author: Steve Jia
* Date: 2016-04-12
* Filename: ClusterWaterData.java
**/
import java.util.*;
import java.io.*;

public class ClusterWaterData
{
   public static void main(String[] args)
   {
      //import the input data file
      File inputFile = new File("ClusterInputDataSet.txt");
      //create new KMeans object
      KMeans km = new KMeans();
      //process the input data
      km.initClusters(inputFile);
      //cluster the data
      km.clusterData();
      //save the output data into a separate file
      km.saveDataToFile("ClusterOutputDataSet.txt");
   }
}
