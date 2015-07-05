/**
 * ECE 454/750: Distributed Computing
 *
 * Code written by Wojciech Golab, University of Waterloo, 2015
 * Implementation by Christopher Poenaru, Anthony Clark, and Thomasz Szwiega University of Waterloo, 2015
 *
 */
package ece454750s15a2;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class TriangleCountImpl {
  private byte[] input;
  private int numCores;
  private CopyOnWriteArrayList<Triangle> triangleList;

  public TriangleCountImpl(byte[] input, int numCores) {
    this.input = input;
    this.numCores = numCores;
    this.triangleList= new CopyOnWriteArrayList<Triangle>();
  }

  public List<String> getGroupMembers() {
    ArrayList<String> ret = new ArrayList<String>();
    ret.add("cpoenaru");
    ret.add("am3clark");
    ret.add("tszwiega");
    return ret;
  }

  public List<Triangle> enumerateTriangles() throws IOException {
    ArrayList<HashSet<Integer>> adjacencyList = getAdjacencyList(input);
    if (numCores == 1) {
      return triangleCount(0, 1, adjacencyList);
    } else {
      CountThread[] threads = new CountThread[numCores];
      ArrayList<Integer> ret = new ArrayList<Integer>();
      for(int i = 0; i < numCores; i++) {
        threads[i] = new CountThread(i, numCores, adjacencyList);
        threads[i].start();
        ret.add(1);
      }
      while(ret.contains(1)) {
        for(int i = 0; i < numCores; i++){
          if (!threads[i].isAlive()) {
            ret.set(i, 0);
          }
        }
      }
      return triangleList;
    }
  }

  public List<Triangle> triangleCount(int threadNum, int totalThreads, ArrayList<HashSet<Integer>> adjacencyList) {
    // triangle counting algorithm
    ArrayList<Triangle> ret = new ArrayList<Triangle>();
    int numVertices = adjacencyList.size();
    for (int i = threadNum; i < numVertices; i+=totalThreads) {
      for (Integer neighbour_j : adjacencyList.get(i)) {
        for (Integer neighbour_k : adjacencyList.get(i)) {
          if (neighbour_j < neighbour_k) {
            if (adjacencyList.get(neighbour_j).contains(neighbour_k)) {
              ret.add(new Triangle(i, neighbour_j, neighbour_k));
            }
          }
        }
      }
    }
    return ret;
  }

  class CountThread extends Thread {
    private int threadNum;
    private int totalThreads;
    private ArrayList<HashSet<Integer>> adjacencyList;

    public CountThread(int threadNum, int totalThreads, ArrayList<HashSet<Integer>> adjacencyList) {
      this.threadNum = threadNum;
      this.totalThreads = totalThreads;
      this.adjacencyList = adjacencyList;
    }

    @Override
    public void run() {
      triangleList.addAll(triangleCount(threadNum, totalThreads, adjacencyList));
      return;
    }
  }

  public ArrayList<HashSet<Integer>> getAdjacencyList(byte[] data) throws IOException {
    InputStream istream = new ByteArrayInputStream(data);
    BufferedReader br = new BufferedReader(new InputStreamReader(istream));
    String strLine = br.readLine();
    if (!strLine.contains("vertices") || !strLine.contains("edges")) {
      System.err.println("Invalid graph file format. Offending line: " + strLine);
      System.exit(-1);
    }
    String parts[] = strLine.split(", ");
    int numVertices = Integer.parseInt(parts[0].split(" ")[0]);
    int numEdges = Integer.parseInt(parts[1].split(" ")[0]);
    System.out.println("Found graph with " + numVertices + " vertices and " + numEdges + " edges");

    ArrayList<HashSet<Integer>> adjacencyList = new ArrayList<HashSet<Integer>>(numVertices);
    for (int i = 0; i < numVertices; i++) {
      adjacencyList.add(new HashSet<Integer>());
    }
    while ((strLine = br.readLine()) != null && !strLine.equals(""))   {
      parts = strLine.split(": ");
      int vertex = Integer.parseInt(parts[0]);
      if (parts.length > 1) {
        parts = parts[1].split(" +");
        for (String part: parts) {
          int num = Integer.parseInt(part);
          if (num > vertex) {
            adjacencyList.get(vertex).add(num);
          }
        }
      }
    }
    br.close();
    return adjacencyList;
  }
}
