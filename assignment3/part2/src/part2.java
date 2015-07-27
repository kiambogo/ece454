import java.io.*;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class GeneScore {
  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
    if (otherArgs.length != 2) {
      System.err.println("Usage: wordcount <in> <out>");
      System.exit(2);
    }
    Job job = Job.getInstance(conf, "gene score");
    job.setJarByClass(GeneScore.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setReducerClass(GeneScoreClass.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }

    public static class GeneScoreClass
        extends Reducer<Text, DoubleWritable, Text, DoubleWritable>{

        private DoubleWritable result = new DoubleWritable();

        public void reduce(Text key, Iterable<DoubleWritable> values, Context context)
            throws IOException, InterruptedException{
            
            double score = 0.0;
            int count = 0;
            for(IntWritable val : values){
                score += val.get();
                count++;
            }        
            score = score/count;
            result.set(score);
            context.write(key, result);
        }

    }

    public static class TokenizerMapper 
        extends Mapper<Object, Text, Text, DoubleWritable>{
    
        private Text gene = new Text();
        private final static DoubleWritable zero = new DoubleWritable(0.0);
        private final static DoubleWritable one = new DoubleWritable(1.0);
      
        public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {

            StringTokenizer itr = new StringTokenizer(value.toString(), ",");
            Integer counter = 0;

            while (itr.hasMoreTokens()) {
                if (counter == 0) {
                    int.nextToken();
                    counter++;
                } else {
                    double val =  Double.parseDouble(itr.nextToken());

                    gene.set("gene_" + Integer.toString(counter));
                    if (val > 0.5) {
                        context.write(gene, one);
                    } else {
                        context.write(gene, zero);
                    }
                    counter++;
                }
            }
        }
    }
}
