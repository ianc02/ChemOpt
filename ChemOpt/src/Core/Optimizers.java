package Core;

public enum Optimizers {

    TIME{
        @Override
        String getname() {
            return "TIME";
        }
    }, YIELD{
        @Override
        String getname() {
            return "YIELD";
        }

    }, COST{
        @Override
        String getname() {
            return "COST";
        }

    }, PURITY{
        @Override
        String getname() {
            return "PURITY";
        }

    };



    abstract String getname();


    public int getIndex() {
        for (int i = 0; i < Optimizers.values().length; i++) {
            if (Optimizers.values()[i].equals(this)) {
                return i;
            }
        }
        throw new IllegalStateException("This should never happen");
    }


}
