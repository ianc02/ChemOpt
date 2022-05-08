package Simulation;

import AI.Qtable;
import Chem.Molecule;
import Core.Actions;
import Core.Duple;
import Core.State;

import java.util.ArrayList;

public class NegativeLearner {

    private Simulate sim;
    private Qtable q;

    private State state;
    private int r;
    public NegativeLearner(Simulate sim){
        this.state = new State();
        state.setTemp(25.0);
        state.setpH(7.0);
        this.sim = sim;
        this.q = new Qtable(2002843,Actions.values().length,0,8,200, 0.5);

    }

    public State control(){
        //getState();
        r = reward();
        int index = 0;
        String t = Integer.toString((int)state.getTemp() * 2);
        String p = Integer.toString((int)state.getpH() * 2);
        if (!(state.getM1() == null)){
            if (!(state.getM2() == null)){
                String m = Integer.toString(sim.getAllReactants().indexOf(state.getM1()));
                String mm = Integer.toString(sim.getAllReactants().indexOf(state.getM2()));
                index = Integer.parseInt(t + p + m + mm);
            }
            else {
                String m = Integer.toString(sim.getAllReactants().indexOf(state.getM1()));
                index = Integer.parseInt(t + p + m);
            }
        }
        else{
            index = Integer.parseInt(t+p);
        }


        int chosenAction = q.senseActLearn(index, r);
//        System.out.println(index);
//        System.out.println(r);
//        System.out.println(chosenAction);

        Actions.values()[chosenAction].applyTo(state, sim);
        return state;
    }



    public Integer reward(){
        if (state.getM1() == null){
            return -1;
        }
        if (state.getM2() == null){
            return -1;
        }

        if (sim.getOpt().equals("YIELD")){
            for (Duple<Integer, ArrayList<Molecule>> prods:
                    sim.getProducts().get(sim.getProduct())) {
                if (prods.getSecond().contains(state.getM1()) && prods.getSecond().contains(state.getM2())){
                    return prods.getFirst() * 100;
                }
            }
            return -1;
        }

        if (sim.getOpt().equals("TIME")){

            if (0 < state.getTemp() && state.getTemp() <99.0){
                return (int) (state.getTemp()-100);
            }
            else{
                sim.setOpt("YIELD");
            }
        }
        if (sim.getOpt().equals("PURITY")){
            if (state.getpH() != 7.0){
                return (int) (-1 * state.getpH() - 7);
            }
            else{
                sim.setOpt("YIELD");
            }
        }
        return 0;
    }

    public State getstate(){
        return state;
    }
}
