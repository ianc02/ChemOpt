package Simulation;

import Core.Duple;
import Chem.Molecule;
import Chem.Rxn;
import Core.State;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;


public class Simulate {

    private HashMap<Molecule, ArrayList<Molecule>> validrxns;
    private ArrayList<Molecule> allReactants;
    private final Scanner input = new Scanner(System.in);
    private ArrayList<String> lowerAllReactants;
    private HashMap<String, Molecule> stringmoleculeHashMap;

    private HashMap<Molecule, ArrayList<Duple<Integer, ArrayList<Molecule>>>> products;

    private Rxn rxn;

    private ArrayList<String> optimizations;

    public double temp;
    public double pH;
    public Molecule product;
    public String opt;

    public PositveLearner positveLearner;
    public NegativeLearner negativeLearner;



    public Simulate(){
        positveLearner = new PositveLearner(this);
        negativeLearner = new NegativeLearner(this);
        temp = 25.0;
        pH = 7.0;
        optimizations = new ArrayList<>();
        optimizations.add("YIELD");
        optimizations.add("TIME");
        optimizations.add("PURITY");
        products = new HashMap<>();
        lowerAllReactants = new ArrayList<>();
        stringmoleculeHashMap = new HashMap<>();
        allReactants = new ArrayList<>();
        Molecule h2o = new Molecule(18.05,-285.5,69.9,-237.2, "Water");
        allReactants.add(h2o);
        Molecule ch4 = new Molecule(16.043,-74.8,186.2,-50.8, "Methane");
        allReactants.add(ch4);
        Molecule ch3ch2oh = new Molecule(46.069,-277.7,160.7,-174.9, "Ethanol");
        allReactants.add(ch3ch2oh);
        Molecule co2 = new Molecule(44.01,-393.5,213,-394.4, "Carbon Dioxide");
        allReactants.add(co2);


        Molecule h2 = new Molecule(2.02,0,130.6,0, "Hydrogen");
        products.put(h2, new ArrayList<>());
        productAdder(2, h2, ch4, co2);
        productAdder(3, h2, ch4, h2o);

        Molecule co = new Molecule(28.01,-110.5,197.7,-137.2, "Carbon Monoxide");
        products.put(co, new ArrayList<>());
        productAdder(2, co, ch4, co2);
        productAdder(1, co, ch4, h2o);


        Molecule h2co3 = new Molecule(62.03,-677.14,-56.9,-527.9, "Carbonic Acid");
        products.put(h2co3, new ArrayList<>());
        productAdder(1, h2co3, co2, h2o);

        validrxns = new HashMap<Molecule, ArrayList<Molecule>>();
        for (Molecule r :
                allReactants) {
            lowerAllReactants.add((r.getName()).toLowerCase(Locale.ROOT));
            stringmoleculeHashMap.put(r.getName().toLowerCase(Locale.ROOT), r);
            validrxns.put(r, new ArrayList<>());
        }
        for (Molecule r :
                products.keySet()) {
            lowerAllReactants.add((r.getName()).toLowerCase(Locale.ROOT));
            stringmoleculeHashMap.put(r.getName().toLowerCase(Locale.ROOT), r);
        }


        addValidReaction(h2o, co2);
        addValidReaction(co2,ch4);
        addValidReaction(h2o, ch4);


    }

    public void addReactant(Molecule m){
        allReactants.add(m);
        validrxns.put(m, new ArrayList<>());
    }

    public void productAdder(int mols, Molecule prod, Molecule m1, Molecule m2){
        ArrayList<Molecule> a = new ArrayList<Molecule>();
        a.add(m1);
        a.add(m2);
        Duple<Integer, ArrayList<Molecule>> d = new Duple<Integer, ArrayList<Molecule>>(mols, a);

        products.get(prod).add(d);

    }
    public void addValidReaction(Molecule m1, Molecule m2){
        if (!validrxns.containsKey(m1)){
            validrxns.put(m1, new ArrayList<>());
        }
        if (!validrxns.containsKey(m2)){
            validrxns.put(m2, new ArrayList<>());
        }
        if(!validrxns.get(m1).contains(m2)){
            validrxns.get(m1).add(m2);
        }
        if(!validrxns.get(m2).contains(m1)){
            validrxns.get(m2).add(m1);
        }
    }

    public ArrayList<Molecule> getAllReactants(){
        return allReactants;
    }


    private void addMolecule(){
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        String decision = "";
        System.out.println("What is the name of your molecule?");
        decision = input.nextLine();
        decision = decision.toLowerCase(Locale.ROOT);


    }

    private void pickMolecule(){
        Molecule r1;
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        while (true) {
            System.out.println("Here are your available products:");
            String out = "";
            String decision = "";
            for (Molecule m :
                    products.keySet()) {
                out += m.getName() + ", ";
            }
            System.out.println(out);
            System.out.println("");
            System.out.println("Please pick one.");
            decision = input.nextLine();
            decision = decision.toLowerCase(Locale.ROOT);
                if (lowerAllReactants.contains(decision)) {
                    r1 = stringmoleculeHashMap.get(decision);
                    product = r1;
                    pickOpt(r1);

                    break;
                } else {
                    System.out.println("That is not an available reactant, please try again.");
                    System.out.println("");
                }
        }
    }


    private void pickOpt(Molecule m1){
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        while (true) {
            System.out.println("Here are your available optimizations:");
            String out = "";
            String decision = "";
            for (String o :
                    optimizations) {
                out += o + ", ";
            }
            System.out.println(out);
            System.out.println("");
            System.out.println("Please pick one.");
            decision = input.nextLine();
            decision = decision.toUpperCase(Locale.ROOT);
            if (optimizations.contains(decision)) {
                opt = decision;
                setOptimizations(m1, decision);
                break;
            } else {
                System.out.println("That is not an available optimization, please try again.");
                System.out.println("");
            }
        }
    }


    private void setOptimizations(Molecule m1, String dec) {
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        while (true) {
            System.out.println("Would you like the POSITIVE or NEGATIVE learner?");
            String decision = "";
            System.out.println("");
            System.out.println("Please pick one.");
            decision = input.nextLine();
            decision = decision.toLowerCase(Locale.ROOT);
            if (decision.equals("positive")) {
                optimize(m1, decision);
                break;
            } else if (decision.equals("negative")) {
                negOptimize(m1, decision);
                break;
            } else {
                System.out.println("That is not an available optimization, please try again.");
                System.out.println("");
            }
        }
    }

    private void optimize(Molecule m1, String dec){
        positveLearner.getstate().setpH(7.0);
        positveLearner.getstate().setTemp(25.0);
        positveLearner.getstate().setM2(null);
        positveLearner.getstate().setM1(null);
        for (int i = 0; i < 500000; i++) {
            positveLearner.control();
        }
       State state = positveLearner.control();
        if (!(state.getM1() == null)){System.out.println(state.getM1().getName());}
       if (!(state.getM2() == null)){System.out.println(state.getM2().getName());}
       System.out.println(state.getTemp());
       System.out.println(state.getpH());
    }

    private void negOptimize(Molecule m1, String dec){
        negativeLearner.getstate().setpH(7.0);
        negativeLearner.getstate().setTemp(25.0);
        negativeLearner.getstate().setM2(null);
        negativeLearner.getstate().setM1(null);
        for (int i = 0; i < 500000; i++) {
            negativeLearner.control();
        }
        State state = negativeLearner.control();
        if (!(state.getM1() == null)){System.out.println(state.getM1().getName());}
        if (!(state.getM2() == null)){System.out.println(state.getM2().getName());}
        System.out.println(state.getTemp());
        System.out.println(state.getpH());
    }





    public double getSpontaneousTemp(Molecule molecule){
        return (-molecule.getDh() / -molecule.getDs());
    }

    public static void main(String[] args) {
        Simulate s = new Simulate();
        Scanner input = new Scanner(System.in);
        ArrayList<Molecule> allReactants = s.getAllReactants();

        System.out.println("Welcome to the Chemical Reaction Optimizer!");
        System.out.println("Please type in what you would like to do.");
        System.out.println("Just as a fair warning, there is a bug that I was unable to figure out");
        System.out.println("That causes Reactants to be missing after repeated run throughs. If this");
        System.out.println("occurs, please rerun the program. I am sorry for the inconvenience.");
        System.out.println("");


        label:
        while (true){
            System.out.println("You may PICK a molecule to optimize, or QUIT the system.");
            String decision = input.nextLine();
            decision = decision.toLowerCase(Locale.ROOT);
            switch (decision) {
                case "pick":
                    s.pickMolecule();
                    break;
                case "add":
                    s.addMolecule();
                    break;
                case "quit":
                    break label;
                default:
                    System.out.println("Please enter either 'pick' or 'quit'.");
                    decision = input.nextLine();
                    break;
            }
        }


    }

    public Molecule getProduct() {
        return product;
    }
    public String getOpt(){
        return opt;
    }
    public void setOpt(String op){
        opt = op;
    }

    public HashMap<Molecule, ArrayList<Duple<Integer, ArrayList<Molecule>>>> getProducts() {
        return products;
    }
}
