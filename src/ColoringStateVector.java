import jdrasil.graph.Bag;
import jdrasil.workontd.StateVector;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ColoringStateVector implements StateVector<Integer> {

    /** Tree width of the graph we are working on. */
    private int tw;

    /** The number of colors that are allowed to color the graph. */
    private int q;

    /** All states stored in the vector. */
    Set<State> states;

    /** Initial a state vector for a given number of colors. */
    public ColoringStateVector(int tw, int q) {
        this.tw = tw;
        this.q = q;
        this.states = new HashSet<>();
        this.states.add(new State(tw+1));
    }

    @Override
    public StateVector<Integer> introduce(Bag<Integer> bag, Integer v, Map<Integer, Integer> treeIndex) {
        Set<State> newStates = new HashSet<>();
        for (State state : states) {
            for (int color = 1; color <= q; color++) {
                State newState = new State(state);
                newState.colors[treeIndex.get(v)] = color;
                newStates.add(newState);
            }
        }
        this.states = newStates;
        return this;
    }

    @Override
    public StateVector<Integer> forget(Bag<Integer> bag, Integer v, Map<Integer, Integer> map) {
        Set<State> newStates = new HashSet<>();
        for (State state : states) {
            state.colors[map.get(v)] = 0;
            newStates.add(state);
        }
        this.states = newStates;
        return this;
    }

    @Override
    public StateVector<Integer> join(Bag<Integer> bag, StateVector<Integer> stateVector, Map<Integer, Integer> map) {
        ColoringStateVector o = (ColoringStateVector) stateVector;

        Set<State> newStates = new HashSet<>();
        if (this.states.size() < o.states.size()) {
            for (State state : this.states) {
                if (o.states.contains(state)) newStates.add(state);
            }
        } else {
            for (State state : o.states) {
                if (this.states.contains(state)) newStates.add(state);
            }
        }

        this.states = newStates;
        return this;
    }

    @Override
    public StateVector<Integer> edge(Bag<Integer> bag, Integer v, Integer w, Map<Integer, Integer> map) {
        Set<State> newStates = new HashSet<>();
        for (State state : states) {
            if (state.colors[map.get(v)] != state.colors[map.get(w)]) newStates.add(state);
        }
        this.states = newStates;
        return this;
    }

    @Override
    public boolean shouldReduce(Bag<Integer> bag, Map<Integer, Integer> map) {
        return false;
    }

    @Override
    public void reduce(Bag<Integer> bag, Map<Integer, Integer> map) {
    }
}

class State {
    int[] colors;

    public State(int tw) {
        this.colors = new int[tw+1];
    }
    public State(State o) {
        this.colors = Arrays.copyOf(o.colors, o.colors.length);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State that = (State) o;
        return Arrays.equals(colors, that.colors);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(colors);
    }

    @Override
    public String toString() {
        return Arrays.toString(colors);
    }
}
