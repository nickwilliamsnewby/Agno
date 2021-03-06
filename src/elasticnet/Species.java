package elasticnet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.google.gson.Gson;

// keeping this lightweight, only storing indexes into the "population"
// array list of genomes
public class Species {
	int speciesID;
	public ArrayList<Long> member_ids = new ArrayList<Long>();	
	long rep_id;
	int pop_id;
	double adjust_fit_sum = 0.0;
	double best_fitness = 0.0;
	int best_genome_index;
	int[] sorted_idx_array;
	public Species(int id, long first_id)
	{
		this.speciesID = id;
		this.member_ids.add(first_id);
	}
	
	public double get_adjusted_fitness_sum(HashMap<Long,BaseGenome> genomes, SorterUtil sorter_util)
	{
		//System.out.println(this.member_ids);
		this.sorted_idx_array = new int[this.member_ids.size()];
		
		HashMap<Integer, Double> fit_sort_dict = new HashMap<Integer, Double>();
		
		ArrayList<Long> deleted_genomes = new ArrayList<Long>();
		// TODO change to hash map iteration
		for(int x = 0; x < this.member_ids.size(); x++)
		{
			// suspect we are getting null genomes here, need to check sorting isnt
			// modifying genome id values
			long member_id = this.member_ids.get(x);
			if(genomes.containsKey(member_id) == true)
			{
				BaseGenome fit_genome = genomes.get(member_id);
				this.adjust_fit_sum += fit_genome.get_prime(this.member_ids.size());
				fit_sort_dict.put(x, fit_genome.fitness);
				this.sorted_idx_array[x] = x;	
			}
			else
			{
				deleted_genomes.add(member_id);
			}
		}
		this.member_ids.removeAll(deleted_genomes);
		if(this.member_ids.size() > 1)
		{
			sorter_util.quick_sort_big_dumb(this.sorted_idx_array, fit_sort_dict, 0, this.sorted_idx_array.length-1);
			//System.out.println(this.sorted_idx_array);
		}
		return this.adjust_fit_sum;
	}
	
	public void add_genome(Long genomeId, double fitness)
	{
		this.member_ids.add(genomeId);
	}
	
	public long get_best_genome_idx()
	{
		return this.member_ids.get(this.sorted_idx_array[this.sorted_idx_array.length - 1]);
	}
	
	public void have_mercy(int num_elites, 
			HashMap<Long, BaseGenome> genomes,
			InnovationService inno_service)
	{
		int num_members = this.sorted_idx_array.length;
		if (num_elites == 0)
		{
			return;
		}
		
		ArrayList<BaseGenome> remove_these = new ArrayList<BaseGenome>();
		
		/*
		for(int x = 0; x < num_elites; x++)
		{
			long g_id = this.member_ids.get(this.sorted_idx_array[x]);
			//System.out.println(g_id);
			BaseGenome removing = genomes.get(g_id);
			genomes.remove(g_id);
		}
		*/
		ArrayList<Long> remove_these_ids = new ArrayList<Long>();
		
		for(int x = 0; x < num_elites; x++)
		{
			BaseGenome tots_removable = genomes.get(this.member_ids.get(this.sorted_idx_array[x]));
			genomes.remove(tots_removable);
			remove_these_ids.add(tots_removable.id);
		}
		this.member_ids.removeAll(remove_these_ids);
	}
	
	
	public String as_json()
	{
		Gson gson = new Gson();
		String json_string = gson.toJson(this);
		return json_string;
	}
}
