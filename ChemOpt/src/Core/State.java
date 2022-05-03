package Core;

import Chem.Molecule;

public class State {

    private Molecule m1;
    private Molecule m2;
    private double temp;
    private double pH;


    public State(){
        temp = 25.0;
        pH = 7.0;
        m1 = null;
        m2 = null;
    }

    public double getpH() {
        return pH;
    }

    public double getTemp() {
        return temp;
    }

    public Molecule getM1() {
        return m1;
    }

    public Molecule getM2() {
        return m2;
    }

    public void setM1(Molecule m1) {
        this.m1 = m1;
    }

    public void setM2(Molecule m2) {
        this.m2 = m2;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setpH(double pH) {
        this.pH = pH;
    }

    public void clear(){
        this.m1 = null;
        this.m2 = null;
    }
}
