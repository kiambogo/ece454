import java.io.IOException;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.DefaultTuple;
import org.apache.hadoop.io.WritableComparable;

public class DotProduct extends EvalFunc<String>
{
	public String exec(Tuple input) throws IOException{
		try{
			String s1 = input.get(0).toString();
			Tuple g1 = (DefaultTuple) input.get(1);
			String s2 = input.get(2).toString();
			Tuple g2 = (DefaultTuple) input.get(3);
			double prodsum = 0;
			for (int i = 0; i < g1.size(); i++){
				prodsum += Double.parseDouble(g1.get(i).toString()) * Double.parseDouble(g2.get(i).toString()) ;
			}
			if (prodsum != 0){
				return s1+","+s2+","+prodsum;
			}
			else{
				return "";
			}
			
		}catch(Exception e){
			throw new IOException("Caught exception processing input row " + e.getMessage(), e);
		}
	}
}