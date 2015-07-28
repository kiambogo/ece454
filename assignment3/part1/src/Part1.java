import java.io.*;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class Part1 {
  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
    if (otherArgs.length != 2) {
      System.err.println("Usage: wordcount <in> <out>");
      System.exit(2);
    }
    conf.set("mapreduce.output.textoutputformat.separator", ",");
    Job job = Job.getInstance(conf, "max gene");
    job.setJarByClass(Part1.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }

  public static class TokenizerMapper 
       extends Mapper<Object, Text, Text, Text>{
    
    private Text sample = new Text();
      
    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
      StringTokenizer itr = new StringTokenizer(value.toString(), ",");
      String max = ""; 
      double maxVal = 0;
      Integer counter = 0;
      while (itr.hasMoreTokens()) {
        if (counter == 0) {
          sample.set(itr.nextToken());
          counter++;
        } else {
          double val = Double.parseDouble(itr.nextToken());
          if (val > maxVal) {
            maxVal = val;
            max = "gene_" + Integer.toString(counter);
          } else if (val == maxVal) {
            max += ",gene_" + Integer.toString(counter);
          }
          counter++;
        }
      }
      context.write(sample, new Text(max));
    }
  }
}
