/**
 * ECE 454/750: Distributed Computing
 *
 * Code written by Wojciech Golab, University of Waterloo, 2015
 *
 * IMPLEMENT YOUR SOLUTION IN THIS FILE
 *
 */

package ece454750s15a2

import java.io._

class TriangleCountImpl(input: List[String], numCores: Int) {
  def getGroupMembers: List[String] = List("cpoenaru", "ac3clark", "TOM") 

  def enumerateTriangles: List[Triangle] = {
    val adjacencyList = getAdjacencyList(input)

    // this code is single-threaded and ignores numCores

    // naive triangle counting algorithm
    (adjacencyList.zipWithIndex flatMap { case (n1: List[Int], i: Int) =>
      n1 flatMap { j: Int =>
        adjacencyList(j) flatMap { k: Int =>
          adjacencyList(k) map { l: Int =>
            if (i < j && j < k && l == i) {
              Some(Triangle(i, j, k))
            } else {
              None
            }
          }
        }
      }
    }).flatten
  }

  def getAdjacencyList(input: List[String]): List[List[Int]] = {
    if (!input.head.contains("vertices") || !input.head.contains("edges")) {
      throw new RuntimeException("Invalid graph file format. Offending line: " + input.head)
    }

  val parts: List[String] = input.head.split(", ").toList
  val numVertices = (parts(0).split(" "))(0)
  val numEdges = (parts(1).split(" "))(0)
  println("Found graph with " + numVertices + " vertices and " + numEdges + " edges")


  //  val istream: InputStream = new ByteArrayInputStream(input.tail.asInstanceOf[Array[Byte]])
  // val br: BufferedReader = new BufferedReader(new InputStreamReader(istream))
  //val strLine: String = br.readLine()
  //val vertices: List[List[Int]] = Stream.continually(br.readLine()).takeWhile(line => line != null && line != "").toList map { strLine =>
  input.tail.filter(_ != "") map { strLine =>
    val parts = strLine.split(": ")
    val vertex: Int = parts(0).toInt
    if (parts.length > 1) {
      (parts(1).split(" +") flatMap { part =>
        List[Int](part.toInt)
      }).toList
    }
    else List[Int]()
  }
  //br.close()
  //vertices
  //}
  }
}
