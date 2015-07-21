package ece454.part1;

import java.io.*;
import org.apache.hadoop.util.*;

public class MaxGene {
  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "word count");
    job.setJarByClass(WordCount.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);


    String inputFolder = args[0];
    String outputFolder = args[1];

    System.out.println("Input folder: " + inputFolder);
    System.out.println("Output folder: " + outputFolder);

    // read graph data from input file to main memory
    File infile = new File(inputFolder);
    InputStream in = new FileInputStream(infile);
    byte[] data = new byte[(int)infile.length()];
    in.read(data);
    in.close();
  }

  public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {

  }

}
