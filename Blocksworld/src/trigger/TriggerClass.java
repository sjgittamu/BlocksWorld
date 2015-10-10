package trigger;

import java.util.ArrayList;
import java.util.List;

import compute.Search;
import util.Block;
import util.InitializeUtil;
import util.State;

public class TriggerClass {
	State finalState;
	State initialState;
	public static void main(String[] args) {

		//setting up essentials
		int blocksNum = Integer.valueOf(args[0]);
		int stacksNum = Integer.valueOf(args[1]);
		State.stacksNum = stacksNum;
		State.blocksNum = blocksNum;
		List<Block> blocksList = new ArrayList<Block>();
		for(int i=0; i<blocksNum; i++){
			blocksList.add(new Block((char)('A'+i)));
		}
		
		//createRandomStartEndStates
		InitializeUtil init = new InitializeUtil();
		//create a final state for reference
		State finalState = init.createFinalState(stacksNum, blocksNum,blocksList);
		
		//place blocks randomly in stacks
		//State initState = init.createFakeInitState(stacksNum, blocksNum, blocksList);
		State initState = init.createInitState(stacksNum, blocksNum, blocksList);
		System.out.println("running blocksworld: "+blocksNum+", "+stacksNum);
		System.out.println("start state: \n"+initState);
		
		//Map<State, List<State>> graph = init.createGraph(initState,stacksNum,blocksNum, finalState);
		//initState.setGraph(graph);
		Search search = new Search();
		List<State> path = search.AStarSsearch(initState, finalState);
		if(path != null){
		for (int i=path.size()-1; i>=0; i--) {
			State state = path.get(i);
			System.out.println(state.toString());
		}
		
		System.out.println("solution path: "+path.size());
		}
		//List<State> successors = init.generateSuccessors(initState, stacksNum);
		//initState.getDistanceFromGoalNode(finalState);

		//Create whole graph .. make a stopping condition..say 10 steps or something
		//call search function and then return|| setup heuristics along with its




		/*StateAndGraph initializeStateGraph = new StateAndGraph();


		State startState = initializeStateGraph.generateStartState();
		Map<State, List<State>> graph = initializeStateGraph.createGraph(startState);

		startState.setGraph(graph);
		State goal = initializeStateGraph.getFinalState();
	//	List<State> successors = startState.successors(startState, goal);

		startState.getDistanceFromGoalNode(goal, initializeStateGraph);
	//	System.out.println(successors.size());
		 */	}


}
