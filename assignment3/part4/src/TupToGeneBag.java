import java.io.IOException;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.BagFactory;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.DataType;

public class TupToGeneBag extends EvalFunc<DataBag>
{
	public DataBag exec(Tuple input) throws IOException {
		if (input == null || input.size() == 0 || input.get(0) == null)
			return null;

		BagFactory myBagFactory = BagFactory.getInstance();
		TupleFactory myTupleFactory = TupleFactory.getInstance();


		try{
			DataBag db =  myBagFactory.newDefaultBag();

			for(int i=1; i<input.size(); i++){
				Tuple tp = myTupleFactory.newTuple(2);

				tp.set(0, "gene_"+ Integer.toString(i+1));

				double val = Double.parseDouble(input.get(i) + "");

				if(val > 0.5){
					tp.set(1, 1);
				}
				else{
					tp.set(1, 0);
				}
				db.add(tp);
			}

			return db;
		} catch(Exception e){
			throw new IOException("Caught exception processing input row ", e);
		}
	}
}
