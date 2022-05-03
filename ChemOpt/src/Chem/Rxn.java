package Chem;

import java.util.ArrayList;
import java.util.HashMap;

public class Rxn {
    private Molecule r1;
    private Molecule r2;
    private HashMap<Molecule, ArrayList<Molecule>> validrxns;


    public Rxn(Molecule react1, Molecule react2, HashMap<Molecule, ArrayList<Molecule>> vr){
        this.r1 = react1;
        this.r2 = react2;
        this.validrxns = vr;
    }

    public boolean checkValidity(){
        return (validrxns.get(r1).contains(r2) || validrxns.get(r2).contains(r1));
    }




}
