import jdrasil.workontd.StateVector;
import jdrasil.workontd.StateVectorFactory;

public class ColoringStateVectorFactory implements StateVectorFactory<Integer> {

    /** The number of allowed colors. */
    private int q;

    /** Initialize a new factory for a specific number of allowed colors. */
    public ColoringStateVectorFactory(int q) {
        this.q = q;
    }

    @Override
    public StateVector<Integer> createStateVectorForLeaf(int tw) {
        return new ColoringStateVector(tw, q);
    }
}
