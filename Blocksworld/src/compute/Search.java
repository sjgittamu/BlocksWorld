package compute;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.DataQueue;
import util.State;

public class Search {
	
	public List<State> AStarSsearch(State root, State goal){
		Set<State> visiting = new HashSet<>();
		Set<State> visited = new HashSet<>();
		DataQueue frontier = new DataQueue();
		//initialize with starting point
		frontier.addElement(root);
		visiting.add(root);
		root.setFnValue(root,goal,root);	
		//explored set initialization
		//loop in
		List<State> path= null;
		int iter = 0;
		int maxFrontierSize = 0;
		
		while(!frontier.isEmpty()){
			State nextElement = frontier.popNextElement();
			int dep= nextElement.getParent() == null ? 0:nextElement.getParent().getDepth()+1;
			nextElement.setDepth(dep);
			System.out.println("iter="+iter+", queue="+frontier.getSize()+", f=g+h="+nextElement.getFnValue()+", depth="+dep);
			//System.out.println(output.getId()/*output.getxCoor()+" "+output.getyCoor()*/);
			if(nextElement.equals(goal)){
				System.out.println("success! depth="+dep+", total goal tests="+iter+", max_queue_size="+maxFrontierSize);
				path = nextElement.traceback(root);
				return path;
			}
			List<State> successors = nextElement.successors(root,goal);
			for (State currState : successors) {
				if(!visited.contains(currState) && !visiting.contains(currState)){	//new node
					visiting.add(currState);
					currState.setParent(nextElement);
					frontier.addElement(currState);
					if(maxFrontierSize < frontier.getSize()){
						maxFrontierSize = frontier.getSize();
					}
				}else{
					visited.add(currState);
					visiting.remove(currState);
				}
			}
			iter++;
			if(iter > 180000){
				path = nextElement.traceback(root);
				System.out.println("Iteration limit exceeded: This problem can not be reduced to a valid solution ");
				return path;
			}
		}
		System.out.println("This problem can not be reduced to a valid solution ");
		return path;
	
	}
}
