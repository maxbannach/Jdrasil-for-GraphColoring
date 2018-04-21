import java.io.*;
import jdrasil.algorithms.SmartDecomposer;
import jdrasil.graph.*;
import jdrasil.workontd.DynamicProgrammingOnTreeDecomposition;

public class Main {

    public static Graph<Integer> theGraph;

    public static void main(String[] args) {
        try {
            Graph<Integer> G = GraphFactory.emptyGraph();

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String line; String[] ll;
            while ( (line = in.readLine()) != null ) {
                ll = line.split(" ");
                if (ll[0].equals("c")) {
                    // ignore comments
                } else if (ll[0].equals("p")) {
                    int n = Integer.parseInt(ll[2]);
                    for (int v = 0; v < n; v++) G.addVertex(v);
                } else if (ll[0].equals("e")) {
                    int u = Integer.parseInt(ll[1])-1;
                    int v = Integer.parseInt(ll[2])-1;
                    handleSelfLoop(G, in, u, v);
                } else {
                    int u = Integer.parseInt(ll[0])-1;
                    int v = Integer.parseInt(ll[1])-1;
                    handleSelfLoop(G, in, u, v);
                }
            }
            in.close();

            theGraph = G;
            new Main().solve(G);
        } catch (IOException e) {
            System.err.println("Failed to read a graph from stdin.");
            e.printStackTrace();
        }
    }

    /** add an edge to the graph or exit if found a self-loop */
    private static void handleSelfLoop(Graph<Integer> g, BufferedReader in, int u, int v) throws IOException {
        if (u != v) {
            g.addEdge(u, v);
        } else {
            System.out.println("chi(G) = infinity (self-loop)");
            in.close();
            System.exit(0);
        }
    }

    public void solve(Graph<Integer> G) {
        System.out.println("|V| = " + G.getNumVertices() + ", |E| = " + G.getNumberOfEdges() + ", cc(G) = " + G.getConnectedComponents().size());

        // compute the tree decomposition
        TreeDecomposition<Integer> td = null;
        try {
            td = new SmartDecomposer<>(G).call();
        } catch (Exception e) {
            System.err.println("Failed to compute tree decomposition.");
            e.printStackTrace();
            return;
        }
        System.out.println("tw(G) = " + td.getWidth());

        int q = 2;
        while (true) {
            DynamicProgrammingOnTreeDecomposition<Integer> solver = new DynamicProgrammingOnTreeDecomposition<Integer>(G, new ColoringStateVectorFactory(q), true, td);
            ColoringStateVector rootVector = (ColoringStateVector) solver.run();
            if (rootVector.states.size() == 0) {
                System.out.println("chi(G) > " + q);
                q++;
            } else {
                System.out.println("chi(G) = " + q);
                break;
            }
        }
    }
}
