package elasticnet;
import java.util.HashMap;

public class NodeGene {

	public boolean is_output;
	public ConnectionGene[] connections;
	int level = 0;
	String activation = "";
	int genome_id;
	int inno_id;
	
	public NodeGene(int inno_id, String act) {
		this.inno_id = inno_id;
		this.activation = act;
	}
	
	
}
