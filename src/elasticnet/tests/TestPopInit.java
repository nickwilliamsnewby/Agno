package elasticnet.tests;
import java.io.IOException;
import java.io.PrintWriter;

import elasticnet.*;

public class TestPopInit {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Population test_pop = new Population(0, new NeatConfig(), 10, false);
		
		test_pop.set_up_first_pop();
		
		test_pop.connection_genes.forEach((k,v) -> System.out.println("key: "+k+" value:"+v));
	}

}
