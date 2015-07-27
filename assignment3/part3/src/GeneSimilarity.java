import java.io.*;
import java.util.StringTokenizer;
import java.util.*;

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
import org.apache.hadoop.mapreduce.lib.chain.*;

public class GeneSimilarity {
  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
    if (otherArgs.length != 2) {
      System.err.println("Usage: wordcount <in> <out>");
      System.exit(2);
    }
    Job job = Job.getInstance(conf, "gene similarity");

    Configuration mc1 = new Configuration(false);
    ChainMapper.addMapper(job, Mapper1.class, Object.class, Text.class, IntWritable.class, Text.class, mc1);

    Configuration mc2 = new Configuration(false);
    ChainMapper.addMapper(job, Mapper2.class, IntWritable.class, Text.class, Text.class, DoubleWritable.class, mc2);

    Configuration rc = new Configuration(false);
    ChainReducer.setReducer(job, Reducer1.class, Text.class, DoubleWritable.class, Text.class, DoubleWritable.class, rc);

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(DoubleWritable.class);
	job.setJarByClass(GeneSimilarity.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }

  	public static class Mapper1
       extends Mapper<Object, Text, IntWritable, Text>{
		private final static IntWritable columns = new IntWritable();
		public void map(Object key, Text value, Context context
						) throws IOException, InterruptedException {
			String line = value.toString();
			String[] terms = line.split(",");
			int cols = terms.length;
			columns.set(cols);
			for (int i = 0; i < cols; i++) {
				context.write(columns, new Text(terms[i]));
			}
		}
	}

	public static class Mapper2
       extends Mapper<IntWritable, Text, Text, DoubleWritable>{

		private final Text pair = new Text();

		public void map(IntWritable key, Iterator<Text> values, Context context
						) throws IOException, InterruptedException {
			List<String> terms = new ArrayList<String>();
			while (values.hasNext()){
				Text term = values.next();
				terms.add(term.toString());
			}
			int columns = key.get();
			int samples = terms.size()/columns;
			for (int i = 0; i < samples; i++) {
				for (int j = i; j < samples; j++){
					if (j == i)
						continue;
					pair.set(terms.get(i*columns) + "-" + terms.get(j*columns));
					for(int k = 1; k < columns; k++){
						DoubleWritable value1 = new DoubleWritable(Double.valueOf(terms.get(i*columns+k)));
						DoubleWritable value2 = new DoubleWritable(Double.valueOf(terms.get(j*columns+k)));
						if (value1.get() != 0 && value2.get() != 0){
							context.write(pair, value1);
							context.write(pair, value2);
						}
					}
				}
			}
		}
	}

	private static class Reducer1 extends
			Reducer<Text, DoubleWritable, Text, DoubleWritable> {

		private final static DoubleWritable result = new DoubleWritable();

		public void reduce(Text key, DoubleWritable values, Context context)
				throws IOException, InterruptedException { double product = 1.0; double sum = 0.0; Integer counter = 0; for (DoubleWritable val : values) { counter += 1;
				product += val.get();
				if (counter == 2){
					sum += product;
					product = 1;
					counter = 0;
				}
			}
			if (sum != 0){
				result.set(sum);
				context.write(key, result);
			}
		}
	}
}



