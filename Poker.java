import java.io.IOException;
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
import java.util.ArrayList;

public class Poker{
  public static class PokerMapper extends Mapper<Object, Text, Text, IntWritable>{
    public void map(Object key, Text value, Context context ) throws IOException, InterruptedException {
		
		//Create Iterator from the cards	
		StringTokenizer itr = new StringTokenizer(value.toString());
      
		while (itr.hasMoreTokens()) {
		
			//Collect Pairs, emit pairs
			//Fprmat: "Suit,Rank"
			String[] Card = itr.nextToken().split(",");	
			context.write(new Text(Card[0]), new IntWritable(Integer.parseInt(Card[1])));
      }
    }
  }
  
	public static class PokerReducer extends Reducer<Text,IntWritable,Text,IntWritable> {
		public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

			//Collect all seen cards
			ArrayList<Integer> seen = new ArrayList<Integer>();

			for (IntWritable val : values) {
				seen.add(val.get()); 
			}
		
			//There are 13 ranks, 1-13.
			//For every rank, check if its in the seen cards
			//If not, emit that rank (key is the suit)
			for (int i =1;i<14;i++){
				if (!seen.contains(i)){
					context.write(key, new IntWritable(i));
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = new Job(conf, "Missing Poker Card Identifier");
		job.setJarByClass(Poker.class);
		job.setMapperClass(PokerMapper.class);
		job.setReducerClass(PokerReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
