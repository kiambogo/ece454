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
  private int ncores;
  private CopyOnWriteArrayList<Triangle> triangles;

  public TriangleCountImpl(byte[] input, int ncores) {
    this.input = input;
    this.ncores = ncores;
    this.triangles= new CopyOnWriteArrayList<Triangle>();
  }

  public List<String> getGroupMembers() {
    ArrayList<String> ret = new ArrayList<String>();
    ret.add("cpoenaru");
    ret.add("am3clark");
    ret.add("tszwiega");
    return ret;
  }

  public List<Triangle> enumerateTriangles() throws IOException {
    ArrayList<HashSet<Integer>> adjacencyLists = getAdjacencyList(input);
    if (ncores == 1) {
      return triangleCount(0, 1, adjacencyLists);
    } else {


      try{
        ExecutorService executor = Executors.newFixedThreadPool(ncores);
        for(int i=0; i<ncores; i++){
          Runnable worker = new CounterThread(i, ncores, adjacencyLists);
          executor.execute(worker);
        }

        executor.shutdown();
        executor.awaitTermination(100000l,TimeUnit.SECONDS);
      }
      catch(Exception ex){}

      return triangles;
    }
  }

  public List<Triangle> triangleCount(int thread_id, int thread_count, ArrayList<HashSet<Integer>> adjacencyLists) {
    // triangle counting
    ArrayList<Triangle> ret = new ArrayList<Triangle>();
    int vertice_count = adjacencyLists.size();
    HashSet<Integer> neighbours = new HashSet<Integer>();

    for (int i = thread_id; i < vertice_count; i += thread_count) {
      neighbours = adjacencyLists.get(i);

      for (int j : neighbours) { // all neighbours j > i
        for (int k : neighbours) {

          if (j < k) {

            if (adjacencyLists.get(j).contains(k)) {

              // i < j < k
              ret.add(new Triangle(i, j, k));
            }
          }
        }
      }
    }
    return ret;
  }

  class CounterThread implements Runnable {
    private final int id;
    private final int thread_count;
    private ArrayList<HashSet<Integer>> adjacencyLists;

    public CounterThread(int id, int thread_count, ArrayList<HashSet<Integer>> adjacencyLists){
      this.id = id;
      this.thread_count = thread_count;
      this.adjacencyLists = adjacencyLists;

    }

    @Override
    public void run() {
      triangles.addAll(triangleCount(id, thread_count, adjacencyLists));
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

      int i = Integer.parseInt(parts[0]);

      if (parts.length > 1) {
        parts = parts[1].split(" +");
        for (String part: parts) {

          int j = Integer.parseInt(part);

          // Only include neighbouts greater than i
          if (j > i) {
            adjacencyList.get(i).add(j);
          }
        }
      }
    }
    br.close();
    return adjacencyList;
  }
}
