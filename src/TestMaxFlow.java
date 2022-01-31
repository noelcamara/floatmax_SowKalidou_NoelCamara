import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;

import java.io.IOException;


public class    TestMaxFlow {

    public static void main(String[] args) throws IOException, GraphParseException {
        Graph g = new SingleGraph("test");
        /*g.addNode("s");
        g.addNode("1");
        g.addNode("2");
        g.addNode("t");
        g.addEdge("e1", "s", "1", true).setAttribute("cap", 10);
        g.addEdge("e2", "s", "2", true).setAttribute("cap", 10);
        g.addEdge("e3", "1", "2", true).setAttribute("cap", 5);
        g.addEdge("e4", "1", "t", true).setAttribute("cap", 8);
        g.addEdge("e5", "2", "t", true).setAttribute("cap", 7); */

        // Test avec un autre graphe (l'exemple du cours)
         //g.read("src/test.dgs");

        // Lecture du graphe associé au réseau routier
         g.read("src/reseauRoutier.dgs");

        // Lecture du graphe associé aux tronçons de l'autoroute
        //g.read("src/reseauRoutier2.dgs");

        System.setProperty("org.graphstream.ui", "swing");
        g.display(false);

        MaxFlow mf = new MaxFlow();
        mf.setCapacityAttribute("cap");
        mf.init(g);
        mf.setSource(g.getNode("A"));
        mf.setSink(g.getNode("I"));
        mf.compute();

        System.out.println(mf.getFlow());
        for (int i = 0; i < g.getEdgeCount(); i++) {
            Edge e = g.getEdge(i);
            double flow = mf.getFlow(e);
            double cap = mf.getCapacity(e);
            if (flow >= 0) e.setAttribute("ui.label", (int) flow + " / " + (int) cap);
            if (cap == flow) e.setAttribute("ui.style", "fill-color: red;");
        }

        g.setAttribute("ui.stylesheet", "node {size: 20px;fill-color: green;}");
        for (Node n : g) {
            n.setAttribute("ui.label", n.getId());
        }

        grapheDEcart();
    }

    private static void grapheDEcart() throws IOException, GraphParseException {
        Graph grapheDEcart = new MultiGraph("grapheDEcart");
        grapheDEcart.read("src/grapheDEcart.dgs");
        grapheDEcart.display(false);
        for (int i = 0; i < grapheDEcart.getEdgeCount(); i++) {
            Edge e = grapheDEcart.getEdge(i);
            if (e.hasAttribute("color"))
                e.setAttribute("ui.style", "fill-color: red;");
        }
        grapheDEcart.setAttribute("ui.stylesheet", "node {size: 20px;fill-color: green;}");
        for (Node n : grapheDEcart) {
            n.setAttribute("ui.label", n.getId());
        }
    }

}
