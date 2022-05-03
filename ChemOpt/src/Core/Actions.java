package Core;

import Simulation.Simulate;

public enum Actions {
    RAISETEMP{
        @Override
        public void applyTo(State state, Simulate simulate) {
            if (state.getTemp() < 99.5) {
                state.setTemp(state.getTemp() + 0.5);
            }
        }
    }, LOWERTEMP{
        @Override
        public void applyTo(State state, Simulate simulate) {
            if (state.getTemp() > 0) {
                state.setTemp(state.getTemp() - 0.5);
            }

        }
    }, RAISEPH{
        @Override
        public void applyTo(State state, Simulate simulate) {
            if (state.getpH() < 14.0) {
                state.setpH(state.getpH() + 0.5);
            }

        }
    }, LOWERPH{
        @Override
        public void applyTo(State state, Simulate simulate) {
            if (state.getpH() > 0) {
                state.setpH(state.getpH() - 0.5);
            }

        }
    }, ADDWATER{
        @Override
        public void applyTo(State state, Simulate simulate) {
            if (state.getM1() == null){
                state.setM1(simulate.getAllReactants().get(0));
            }
            else if (state.getM2() == null && state.getM1()!=simulate.getAllReactants().get(0)){
                state.setM2((simulate.getAllReactants().get(0)));
            }
        }
    }, ADDMETHANE{
        @Override
        public void applyTo(State state, Simulate simulate) {
            if (state.getM1() == null){
                state.setM1(simulate.getAllReactants().get(1));
            }
            else if (state.getM2() == null && state.getM1()!=simulate.getAllReactants().get(1)){
                state.setM2((simulate.getAllReactants().get(1)));
            }
        }
    }, ADDETHANOL{
        @Override
        public void applyTo(State state, Simulate simulate) {
            if (state.getM1() == null){
                state.setM1(simulate.getAllReactants().get(2));
            }
            else if (state.getM2() == null && state.getM1()!=simulate.getAllReactants().get(2)){
                state.setM2((simulate.getAllReactants().get(2)));
            }
        }
    }, ADDCARBON{
        @Override
        public void applyTo(State state, Simulate simulate) {
            if (state.getM1() == null){
                state.setM1(simulate.getAllReactants().get(3));
            }
            else if (state.getM2() == null && state.getM1()!=simulate.getAllReactants().get(3)){
                state.setM2((simulate.getAllReactants().get(3)));
            }
        }
    },CLEAR{
        @Override
        public void applyTo(State state, Simulate simulate) {
            state.setM1(null);
            state.setM2(null);
        }
    }, NOTHING{
        @Override
        public void applyTo(State state, Simulate simulate) {

        }
    };

    abstract public void applyTo(State state, Simulate simulate);
}
