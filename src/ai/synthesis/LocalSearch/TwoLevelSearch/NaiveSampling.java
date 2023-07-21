package ai.synthesis.LocalSearch.TwoLevelSearch;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

import ai.synthesis.Synthesis_Base.CFG.Control;
import ai.synthesis.LocalSearch.EvaluateGameState.BehavioralFeature;
import ai.synthesis.LocalSearch.EvaluateGameState.FeatureFactory;
import ai.synthesis.LocalSearch.EvaluateGameState.FeatureFactory1;
import ai.synthesis.LocalSearch.EvaluateGameState.Feature;
import ai.synthesis.LocalSearch.EvaluateGameState.Feature;
import ai.synthesis.LocalSearch.LS_CFG.FactoryLS;
import ai.synthesis.LocalSearch.LS_CFG.Node_LS;
import util.Pair;

public class NaiveSampling implements Level1 {
	int change_NS ;
	boolean salvar=true;
	Random r;
	double e0;
	double el;
	double eg;
	int n_arms=7;
	double desc= 0.95;
	int opt=13;
	long T_inicial;
	int[] v_opt = {0,1,2,3,4,5,7,10,15,20,25,35,50};
	String[] name_arm= {"W  ","L  ","R  ","H  ","Ba ","Br ","Re "};
	Map<Integer,Integer> map;
	
	Integer[][] TiVik;
	/*Double[][] MiVik= {{1.1,0.8,1.0,1.0,1.0,2.3,4.0,3.8,1.5,3.7,4.6,4.1,4.6   },
			   {2.1,2.0,null,null,2.5,3.4,null,null,null,null,null,null,null},
			   {2.1,2.225,4.0,null,null,null,null,null,null,null,null,null,null},
			   {2.1,3.0,0.75,null,null,null,null,null,0.75,null,null,null,null},
			   {2.273130085832136,1.008888888888889,0.5309278350515465,null,null,0.5,0.5,null,null,null,null,null,0.75   },
			   {2.1774193548387104,0.7672413793103449,2.8599290780141837,1.7428571428571429,0.5337837837837838,0.5,null,0.875,null,null,null,null,null},
			   {2.156621728786673,1.0833333333333333,0.75,null,null,0.5,1.0,0.5492125984251968,0.604166666666667,1.0696202531645569,1.1570512820512822,4.434300341296927,3.598101265822785}
	};*/
	Double[][] MiVik;
	TreeMap<Feature,Integer> T;
	TreeMap<Feature,Double> M;
	TreeMap<Feature,String> Backup;
	FeatureFactory fn;
	public NaiveSampling(double e0, double el, double eg, int troca) {
		super();
		this.change_NS = troca;
		map = new HashMap<Integer,Integer>();
		for(int i =0;i<this.opt;i++) {
			map.put(this.v_opt[i], i);
		}
		this.T_inicial = System.currentTimeMillis();
		r =new Random();
		this.e0 = e0;
		this.el = el;
		this.eg = eg;
		TiVik = new Integer[this.n_arms][this.opt];
		MiVik = new Double[this.n_arms][this.opt];
		this.fn = new FeatureFactory1();
		for(int i=0;i<this.n_arms;i++) {
			for(int j=0;j<this.opt;j++) {
				TiVik[i][j]=0;
				if(MiVik[i][j]!=null)
				TiVik[i][j]=1;
			}
		}
		T = new TreeMap<Feature, Integer>();
		M = new TreeMap<Feature, Double>();
		this.Backup = new TreeMap<Feature, String>();
		
	}
	
	
	
	
	
	public NaiveSampling(double e0, double el, double eg, int troca, String string) throws FileNotFoundException {
		// TODO Auto-generated constructor stub
		super();
		this.change_NS = troca;
		map = new HashMap<Integer,Integer>();
		for(int i =0;i<this.opt;i++) {
			map.put(this.v_opt[i], i);
		}
		this.T_inicial = System.currentTimeMillis();
		r =new Random();
		this.e0 = e0;
		this.el = el;
		this.eg = eg;
		TiVik = new Integer[this.n_arms][this.opt];
		MiVik = new Double[this.n_arms][this.opt];
		this.fn = new FeatureFactory1();
		for(int i=0;i<this.n_arms;i++) {
			for(int j=0;j<this.opt;j++) {
				TiVik[i][j]=0;
				if(MiVik[i][j]!=null)
				TiVik[i][j]=1;
			}
		}
		T = new TreeMap<Feature, Integer>();
		M = new TreeMap<Feature, Double>();
		this.Backup = new TreeMap<Feature, String>();
		this.carregarBackup(string);
	}

	public void carregarBackup(String path) throws FileNotFoundException {
		
		
		Scanner in = new Scanner(new FileReader(path));
		String a1="";
		String a2="";
		while (in.hasNextLine()) {
		    String line = in.nextLine();
		    String dados[] = line.split("\t");
		    if(dados[0].equals("Backup")) {
		    	
		    	
		    	
		    	int i=dados[1].indexOf("ReS");
		    	String novS =dados[1];
		    	String SS =dados[2];
		    	Feature nov = fn.create(novS);
		    	//System.out.println(line);
		    	this.Backup.put(nov, SS);
		    }
		}
		
	in.close();
	
	return ;
	}
		
		
	



	@Override
	public void imprimir() {
		for(int i=0;i<this.n_arms;i++) {
			System.out.print(this.name_arm[i]);
			for(int j=0;j<this.opt;j++) {
				System.out.print(this.v_opt[j]+"("+this.MiVik[i][j]+","+this.TiVik[i][j]+")   ");
			}
			
			System.out.println();
			
		}
		System.out.println("Mellhor: "+this.exploitSA());
	}
	
	
	public int exploreX(int i) {
		int aux = r.nextInt(this.opt);
		return this.v_opt[aux];
	}
	
	public int exploitX(int i) {
		double maior =-1111;
		int index =-1;
		for(int j =0;j<this.opt;j++) {
			if(TiVik[i][j]==0)continue;
			if(MiVik[i][j]>maior) {
				maior= MiVik[i][j];
				index = j;
			}
		}
		if(index==-1)return this.exploreX(i);
		return this.v_opt[index];
	}
	
	public Feature explore() {
		List<Integer> list = new ArrayList<>();
		for(int i=0;i<this.n_arms;i++) {
			if(r.nextFloat()<this.el) {
				System.out.print("e0");
				list.add(this.exploitX(i));
			}else {
				System.out.print("e1");
				
				list.add(this.exploreX(i));
			}
		}
		System.out.println("");
		return this.fn.create(new BehavioralFeature(list));
		
	}
	
	public Feature exploit() {
		if(r.nextFloat()<this.eg && this.M.size()!=0) {
			return this.exploitSA();
		}else {
			
			return this.exploreSA();
		}
	}
	

	private Feature exploreSA() {
		// TODO Auto-generated method stub
		int aux = r.nextInt(this.M.size());
		for(Feature nov : this.M.keySet()) {
			if(aux==0)return nov;
			aux--;
		}
		return null;
	}


	private Feature exploitSA() {
		// TODO Auto-generated method stub
		Feature nov=null;
		double maior=-1111111;
		
		for(Entry<Feature, Double> m : this.M.entrySet()) {
			if(m.getValue()>maior) {
				nov = m.getKey();
				maior = m.getValue();
			}
		}
		return nov;
	}

	public Node_LS getScript(Feature nov) {
		double maior =-1;
		String melhor="S;Empty";
		Feature seed= fn.create();
		System.out.println("guia "+nov);
		for(Entry<Feature, String> m : this.Backup.entrySet()) {
			//System.out.println("\t "+nov.semelhaca(m.getKey())+" "+m.getKey()+" "+ m.getValue());
			if(nov.semelhaca(m.getKey())>maior) {
				maior = nov.semelhaca(m.getKey());
				melhor = m.getValue();
				seed =m.getKey();
			}
		}
		System.out.println("Mais Proximo "+seed);
		return (Node_LS) Control.load(melhor, new FactoryLS());
	}
	
	@Override
	public Pair<Feature, Node_LS> getSeed() {
		// TODO Auto-generated method stub
		long paraou = System.currentTimeMillis()-this.T_inicial;
		
		if((paraou*1.0)/1000.0 >change_NS*3600) {
			this.e0 = 0.9;
			this.el = 0.7;
			this.eg = 0.9;
		}
		
		if((paraou*1.0)/1000.0 >2*change_NS*3600 && this.salvar) {
			
			this.salvaScript();
			this.salvar=false;
		}
		
		Feature nov;
		if(r.nextFloat()<this.e0&&this.T.size()!=0) {
			nov= this.exploit();
		}else {
			
			nov= this.explore();
		}
		if(this.T.size()!=0)this.desconto();
		return new Pair<>(nov,this.getScript(nov));
	}

	public void salvaScript(){
		System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOo");
		
		for(Entry<Feature, String> m : this.Backup.entrySet()) {
			//System.out.println("\t "+nov.semelhaca(m.getKey())+" "+m.getKey()+" "+ m.getValue());
			System.out.println("Backup\t"+m.getKey()+"\t"+m.getValue());
				
			
		}

	}
	
	@Override
	public void update(Node_LS j, Feature nov, double reward) {
		// TODO Auto-generated method stub
		int[] list = converte(nov).convertList();
		for(int i =0;i<this.n_arms;i++) {
			int aux = map.get(list[i]);
			
			if(TiVik[i][aux]==0) {
				TiVik[i][aux]=1;
				MiVik[i][aux]=reward;
			}else {
				MiVik[i][aux]=(MiVik[i][aux]*TiVik[i][aux]+reward)/(TiVik[i][aux]+1);
				TiVik[i][aux]+=1;
			}
		}
		
		if(T.containsKey((Feature)nov)) {
			int T_aux=T.get(nov);
			double M_aux= M.get(nov);
			double aux = (M_aux*T_aux+reward)/(T_aux+1);
			T.put( nov, 1+T_aux);
			M.put( nov, aux);
			
		}else {
			T.put( nov, 1);
			M.put( nov, reward);
		}
		this.Backup.put(nov, Control.salve(j));

	}

	@Override
	public FeatureFactory getFN() {
		// TODO Auto-generated method stub
		return this.fn;
	}
	
	public int closestArm(int i){
		int maior =10000;
		int index=-1;
		for(int j=0;j<this.opt;j++) {
			int aux = Math.abs(i-this.v_opt[j]);
			if (aux<maior) {
				maior=aux;
				index=j;
			}
		}
		return this.v_opt[index];
	}
	
	public Feature converte(Feature nov) {
		List<Integer> list =new ArrayList<>();
		list.add(this.closestArm(nov.convertList()[0]));
		list.add(this.closestArm(nov.convertList()[1]));
		list.add(this.closestArm(nov.convertList()[2]));
		list.add(this.closestArm(nov.convertList()[3]));
		list.add(this.closestArm(nov.convertList()[4]));
		list.add(this.closestArm(nov.convertList()[5]));
		list.add(this.closestArm(nov.convertList()[6]));
		return this.fn.create(new BehavioralFeature(list));
	}
	
	
	public void desconto() {
		Iterator m = this.M.entrySet().iterator();
		while (m.hasNext()) {
			
			Entry pairs = (Entry)m.next();
		
			pairs.setValue((double)(pairs.getValue())*this.desc);
			
			
			
		}
		
		
		for(int i =0;i<this.n_arms;i++) {
			for(int j =0;j<this.opt;j++) {
			
			
				if(MiVik[i][j]!=null)MiVik[i][j]=MiVik[i][j]*this.desc;
				
			
			}
		}
		
		
	}
	
	public static void main(String[] args) throws Exception {
		NaiveSampling NS = new NaiveSampling(0.3,0.3,0.5,24);
	NS.imprimir();
		for(int i=0;i<10;i++) {
			System.out.println(NS.getSeed());
		}
		
		
	}

}
