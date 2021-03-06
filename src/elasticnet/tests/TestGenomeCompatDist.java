package elasticnet.tests;
import java.util.Random;

import elasticnet.Genome;
import elasticnet.NeatConfig;
import elasticnet.NeuralNetwork;
import elasticnet.Population;

public class TestGenomeCompatDist {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int pop_size = 50;
		Population test_pop = new Population(0, new NeatConfig(5, 1), pop_size, false);
		Random dice = new Random();
		Integer[] sorted_idx_list = new Integer[pop_size];
		for(int i = 0; i < pop_size; i++)
		{
			Genome g = test_pop.genomes.get(i);
			int dice_roll = dice.nextInt(10);
			g.fitness = dice_roll;
		}
		System.out.println(test_pop.genomes.size());
		int null_count = 0;
		for(Integer ix: test_pop.connection_genes.keySet())
		{
			if(test_pop.connection_genes.get(ix) == null)
			{
				null_count += 1;
			}
		}
		for(Integer ix: test_pop.node_genes.keySet())
		{
			if(test_pop.node_genes.get(ix) == null)
			{
				null_count += 1;
			}
		}
		test_pop.speciate_population();
		System.out.println(test_pop.get_species().size());
	}

}
