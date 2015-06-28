/**
 * ECE 454/750: Distributed Computing
 *
 * Code written by Wojciech Golab, University of Waterloo, 2015
 *
 * DO NOT MODIFY THIS FILE
 *
 */
package ece454750s15a2

import java.io._
import scala.compat._

object TriangleCount {
  def main(args: Array[String]) {
    val argsList = args.zipWithIndex.toList
    val argMap = collection.mutable.Map[String, String]()
    val arg_ncores: Option[Int] = None;
    val arg_graphFileName: Option[String] = None;
    val arg_outputFileName: Option[String] = None;

    println("Starting triangle count")
    println(argsList)

    argsList.map{ arg =>
      arg match { 
        case ("-ncores", i) => argMap += ("ncores" -> (argsList.find(c => c == (c._1, i+1))).getOrElse(throw new RuntimeException("Must specify number of cores!"))._1)
        case ("-if", i) => argMap += ("if" -> (argsList.find(c => c == (c._1, i+1)).getOrElse(throw new RuntimeException("Must specify input graph filename!"))._1))
        case ("-of", i) => argMap += ("of" -> (argsList.find(c => c == (c._1, i+1)).getOrElse(throw new RuntimeException("Must specify output filename!"))._1))
        case _ => {} 
      }
    }

    for {
      ncores <- argMap get("ncores")
      graphFileName <- argMap get("if") 
      outputFileName <- argMap get("of") 
      } yield {
        count(ncores.toInt, graphFileName, outputFileName)
      }
  }

  def count(numCores: Int, graphFileName: String, outputFileName: String) {
    println("Running on " + numCores + " cores");
    println("Reading data from " + graphFileName);

    // read graph data from input file to main memory
    val infile: File = new File(graphFileName);
    val data: List[String] = scala.io.Source.fromFile(infile).getLines.toList

    // start the clock
    val startTime: Long = Platform.currentTime

    // do the computation
    val impl: TriangleCountImpl = new TriangleCountImpl(data, numCores)
    val ret: List[Triangle] = impl.enumerateTriangles

    // stop the clock
    val endTime: Long = Platform.currentTime

    // write triangles to output file
    val fout: FileOutputStream= new FileOutputStream(outputFileName)
    val pw: PrintWriter = new PrintWriter(fout)
    ret foreach { t =>
      pw.println(t)
    }
    pw.close();

    // report the running time of the computation
    val diffTime = endTime - startTime;
    println(s"Done in $diffTime ms");
    println(s"Found ${ret.size} triangles");
    println(s"Group members: ${impl.getGroupMembers}");
  }
}
