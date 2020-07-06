package apps;

import structures.*;
import java.util.ArrayList;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {

		PartialTreeList list = new PartialTreeList();
		for (int i=0; i < graph.vertices.length; i++) {
			PartialTree thing = new PartialTree(graph.vertices[i]);
			MinHeap<PartialTree.Arc> arcs = new MinHeap<PartialTree.Arc>();
			for (Vertex.Neighbor nbr=graph.vertices[i].neighbors; nbr != null; nbr=nbr.next) {
				PartialTree.Arc temp = new PartialTree.Arc(graph.vertices[i], nbr.vertex, nbr.weight);
				arcs.insert(temp);
			}
			thing.getArcs().merge(arcs);			
			list.append(thing);
		}
		return list;
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {
		/* COMPLETE THIS METHOD */
		ArrayList<PartialTree.Arc> result = new ArrayList<PartialTree.Arc>();
		while(ptlist.size() > 1){
			PartialTree tree = ptlist.remove();
			while(tree.getArcs().size() > 0){
				PartialTree.Arc arc = tree.getArcs().deleteMin();
				if(!tree.getRoot().equals(arc.v2.getRoot())){ //don't belong to the same tree
					PartialTree tree2 = ptlist.removeTreeContaining(arc.v2);
					result.add(arc);
					tree.merge(tree2);
					ptlist.append(tree);
					break;
				}
			}
		}
		return result;
	}
}