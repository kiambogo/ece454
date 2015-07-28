import java.util.*;
import java.io.IOException;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;

public class Part1UDF extends EvalFunc<String> {
  public String exec(Tuple input) throws IOException {
    String n = new String(input.toDelimitedString(","));
    // Remove the first element which is the sample name
    String genes = n.substring(1, n.length() - 1);
    List<String> geneList = Arrays.asList(genes.split(","));
    String max = ""; 
    double maxVal = 0;
    for (int i = 0; i < geneList.size(); i++) {
      Double val = Double.parseDouble(geneList.get(i));
      if (val > maxVal) {
        maxVal = val;
        max = "gene_" + Integer.toString(i+1);
      } else if (val == maxVal) {
        max += ",gene_" + Integer.toString(i+1);
      }
    }
    return max;
  }
}
