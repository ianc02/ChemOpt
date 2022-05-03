package AI;


import java.util.Arrays;

public class Qtable {
    private double[][] q;
    private int[][] visits;
    private int targetVisits;
    private double discount, rateConstant;
    private int lastState, lastAction;

    // TODO:
    //  Calculate the learning rate using this formula: 1/(1 + total visits for this (state, action) pair/rateConstant)
    //  Should pass QTableTest.testLearningRate().
    public double getLearningRate(int state, int action) {
        return 1 / (1 + visits[state][action]/rateConstant);
    }

    // TODO: Find the action for the given state that has the highest q value.
    //  Should pass QTableTest.testBestAction()
    public int getBestAction(int state) {
        double max = -1000000.0;
        int spot = -1;
        for (int i = 0; i<q[state].length; i++){
            if (q[state][i] > max){

                max = q[state][i];
                spot = i;
            }
        }
        return spot;

    }

    // TODO: Returns true if any action for this state is below the target
    //  visits. Returns false otherwise.
    //  Should pass QTableTest.testIsExploring()
    public boolean isExploring(int state) {
        for (int action :
                visits[state]) {
            if (action < targetVisits) {
                return true;
            }
        }
        return false;
    }

    // TODO: Returns the least visited action in state.
    //  Should pass QTableTest.testLeastVisitedAction()
    public int leastVisitedAction(int state) {

        int min = 100000000;
        int action = 0;
        int count = 0;
        for (int a :
                visits[state]){
            if (min > a){
                min = a;
                action = count;
            }
            count++;
        }
        return action;
    }

    // TODO:
    //  1. Calculate the update for the last state and action.
    //  2. Modify the q-value for the last state and action.
    //  3. Increase the visit count for the last state and action.
    //  4. Select the action for the new state.
    //     * If we are exploring, use the least visited action.
    //     * Otherwise, use the best action.
    //  5. Update the last state and action.
    //  6. Return the selected action.
    //  Should pass QTableTest.testSenseActLearn()
    //
    //  Q update formula:
    //    Q(s, a) = (1 - learningRate) * Q(s, a) + learningRate * (discount * maxa(Q(s', a)) + r(s))
    public int senseActLearn(int newState, double reward) {
        double update = ((1- getLearningRate(lastState, lastAction)) * getQ(lastState, lastAction) + getLearningRate(lastState,lastAction) * (discount * getQ(newState, getBestAction(newState)) + reward));
        q[lastState][lastAction] = update;
        visits[lastState][lastAction] +=1;
        int newAction = -8;
        if (isExploring(newState)){
            newAction = leastVisitedAction(newState);
        }
        else{
            newAction = getBestAction(newState);
        }
        lastState = newState;
        lastAction = newAction;
        return newAction;
    }

    public Qtable(int states, int actions, int startState, int targetVisits, int rateConstant, double discount) {
        this.targetVisits = targetVisits;
        this.rateConstant = rateConstant;
        this.discount = discount;
        q = new double[states][actions];
        visits = new int[states][actions];
        lastState = startState;
        lastAction = 0;
    }

    private Qtable() {}


    public double getQ(int state, int action) {
        return q[state][action];
    }

    public int getLastState() {
        return lastState;
    }

    public int getLastAction() {
        return lastAction;
    }
}