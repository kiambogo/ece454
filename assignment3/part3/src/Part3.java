import java.io.*;
import java.util.StringTokenizer;
import java.util.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class Part3 {
  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
    if (otherArgs.length != 2) {
      System.err.println("Usage: wordcount <in> <out>");
      System.exit(2);
    }
    conf.set("mapreduce.output.textoutputformat.separator", ",");
	FileSystem fs = FileSystem.get(conf);
	Path temp = new Path(args[0], "ece454tempP3");	
    Job job1 = Job.getInstance(conf, "gene-sim-prod");
    job1.setJarByClass(Part3.class);
    job1.setMapperClass(GeneMapper.class);
    job1.setReducerClass(ProductReducer.class);
	job1.setMapOutputKeyClass(IntWritable.class);
    job1.setMapOutputValueClass(Text.class);
    job1.setOutputKeyClass(Text.class);
    job1.setOutputValueClass(DoubleWritable.class);
    FileInputFormat.addInputPath(job1, new Path(otherArgs[0]));
    FileOutputFormat.setOutputPath(job1, temp);
	job1.waitForCompletion(true);

	Job job2 = Job.getInstance(conf, "gene-sim-sum");
	job2.setJarByClass(Part3.class);
	job2.setInputFormatClass(KeyValueTextInputFormat.class);
	job2.setMapperClass(MapRead.class);
	job2.setCombinerClass(SumReducer.class);
	job2.setReducerClass(SumReducer.class);
	job2.setOutputKeyClass(Text.class);
    job2.setOutputValueClass(DoubleWritable.class);
	FileInputFormat.addInputPath(job2, temp);
    FileOutputFormat.setOutputPath(job2, new Path(args[1]));
	job2.waitForCompletion(true);
	fs.delete(temp,true);
  }

  	public static class GeneMapper
       extends Mapper<Object, Text, IntWritable, Text>{
		private final IntWritable gene = new IntWritable();
		
		protected void map(Object key, Text value, Context context
						) throws IOException, InterruptedException {
			String line = value.toString();
			String[] terms = line.split(",");
			int cols = terms.length;
			int sampleLength = terms[0].length();
			for (int i = 1; i < cols; i++) {
				StringBuilder val = new StringBuilder(sampleLength+5);
				val.append(terms[0]).append(",").append(terms[i]);
				gene.set(i);
				context.write(gene, new Text(val.toString()));
			}
		}
	}

	public static class ProductReducer
       extends Reducer<IntWritable, Text, Text, DoubleWritable>{

		private final Text pair = new Text();
		private final DoubleWritable product = new DoubleWritable();		

		protected void reduce(IntWritable key, Iterable<Text> values, Context context
						) throws IOException, InterruptedException {
			List<String> samples = new ArrayList<String>();
			List<Double> terms = new ArrayList<Double>();
			for (Text val : values) {
				String[] sampleGene = val.toString().split(",");
				samples.add(sampleGene[0]);
				terms.add(Double.valueOf(sampleGene[1]));
			}
			int numSamples = samples.size();
			int numChars = 2*(numSamples/10 + 1);
			for (int i = 0; i < numSamples; i++) {
				for (int j = i; j < numSamples; j++){
					if (j == i)
						continue;
					double value = terms.get(i)*terms.get(j); 
					if (value == 0){
						continue;
					}
					else{
						StringBuilder samplePair = new StringBuilder(15 + numChars);
						samplePair.append(samples.get(i)).append(",").append(samples.get(j));
						pair.set(samplePair.toString());
						product.set(value);
						context.write(pair, product);
					}
				}
			}
		}
	}
	
	public static class MapRead extends 
			Mapper<Text, Text, Text, DoubleWritable> {
		
		protected void map(Text key, Text value, Context context) 
				throws IOException, InterruptedException {
			DoubleWritable val = new DoubleWritable(Double.parseDouble(value.toString()));
			context.write(key, val);
		}
	}

	public static class SumReducer extends
			Reducer<Text, DoubleWritable, Text, DoubleWritable> {

		private final static DoubleWritable result = new DoubleWritable();
		
		public void reduce(Text key, Iterable<DoubleWritable> values, Context context)
				throws IOException, InterruptedException { 
			double sum = 0.0;
			for (DoubleWritable val : values) {
				sum += val.get();
			}
			result.set(sum);
			context.write(key, result);
		}
	} 
}



