package experiments;

import java.util.ArrayList;

import elasticnet.BaseGenome;
import elasticnet.NeatConfig;
import elasticnet.NeuralNetwork;
import elasticnet.BasePopulation;

public class Xor {
	public String[] signs = new String[4];
	
	public Xor()
	{
	}
	
	public void generate_data()
	{
		
	}
	
	public void run_pop() {
		//this.permute_signs(2);
		
		double[][] inputs = {
				{0.0, 0.0},
				{1.0, 0.0},
				{0.0, 1.0},
				{1.0, 1.0}
		};
		
		double[] outputs = {0.0, 1.0, 1.0, 0.0};
		
		int num_epochs = this.signs.length/2;
		
		int pop_size = 100;
		
		BasePopulation test_pop = new BasePopulation(0, new NeatConfig(), pop_size, false);
		
		int num_gens = 50;
		
		double best_fitness = 0.0;
		
		long best_genome_id = 0;
		
		for(int i = 0; i < num_gens; i++)
		{
			System.out.println(test_pop.BaseGenomes.size());
			
			System.out.println(test_pop.get_species().size());
			
			for(BaseGenome current_genome : test_pop.BaseGenomes.values())
			{
				current_genome.fitness = 4.0;
				
				for(int z = 0; z < 4; z++)
				{
					current_genome.activate(inputs[z]);
					// getting out of bounds index here
					ArrayList<Double> out_vals = current_genome.get_output();
					
					current_genome.fitness -= Math.pow(out_vals.get(0) - outputs[z], 2);
					
					current_genome.reset_vals();
				}
				if(current_genome.fitness > best_fitness)
				{
					best_genome_id = current_genome.id;
					best_fitness = current_genome.fitness;
					System.out.println(" new best fitness ");
					System.out.println(best_genome_id);
					System.out.println(best_fitness);
				}
			}
			//System.out.println("speciating");
			test_pop.speciate_BasePopulation();
			//System.out.println("reproducing");
			test_pop.the_reproduction_function();
			System.out.println("reproduction complete");
		}
		System.out.print("best fit id: ");
		System.out.println(best_genome_id);
		System.out.print("best fit value: ");
		System.out.println(best_fitness);
	}
	
	public void permute_signs(int coord_len) {
		int num_permutes = (int)Math.pow(coord_len, 2.0);
		
		String str_len = "%" + Integer.toString(coord_len) + "s"; 
		
		for(long ix = 0; ix < num_permutes; ix++) {
			this.signs[(int)ix] = String.format(str_len, Long.toBinaryString(ix)).replace(' ', '0');
		}
	}

}
