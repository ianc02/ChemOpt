package Chem;

public class Molecule {
    private double molarmass;
    private double dh;
    private double ds;
    private double dg;
    private String name;

    public Molecule(double mm, double dh, double ds, double dg, String name){
        this.molarmass = mm;
        this.dh = dh;
        this.ds = ds;
        this.dg = dg;
        this.name = name;

    }

    public double getDg() {
        return dg;
    }

    public double getDh() {
        return dh;
    }

    public double getDs() {
        return ds;
    }

    public double getMolarmass() {
        return molarmass;
    }

    public String getName() {
        return name;
    }
}
