package trigger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import compute.Search;
import util.Block;
import util.InitializeUtil;
import util.State;

public class Test {
	State finalState;
	State initialState;
	public static void main(String[] args) {

		//setting up essentials
		Random rand = new Random();
		int stacksNum = 3;//2+ rand.nextInt(3);	// stacks between 2 to 4 i guess
		int blocksNum = 5;//4+ rand.nextInt(3); 	//blocks between 4 to 6
		State.stacksNum = stacksNum;
		State.blocksNum = blocksNum;
		List<Block> blocksList = new ArrayList<Block>();
		for(int i=0; i<blocksNum; i++){
			blocksList.add(new Block((char)('A'+i)));
		}
		
		//createRandomStartEndStates
		InitializeUtil init = new InitializeUtil();
		//place blocks randomly in stacks
		State initState = init.createFakeInitState(stacksNum, blocksNum, blocksList);
		//State initState = init.createInitState(stacksNum, blocksNum, blocksList);

		//create a final state for reference
		State finalState = init.createFinalState(stacksNum, blocksNum,blocksList);
		//Map<State, List<State>> graph = init.createGraph(initState,stacksNum,blocksNum, finalState);
		//initState.setGraph(graph);
		Search search = new Search();
		
		State s = new State();
		List<Stack<Block>> list = new ArrayList<>();
/*		Stack<Block> s1 = new Stack<Block>();
		s1.push(new Block('A'));
		Stack<Block> s2 = new Stack<Block>();
		s2.push(new Block('B'));
		Stack<Block> s3 = new Stack<Block>();
		s3.push(new Block('D'));
		s3.push(new Block('E'));
		s3.push(new Block('C'));
*/		
		Stack<Block> s1 = new Stack<Block>();
		s1.push(new Block('A'));
		s1.push(new Block('B'));
		s1.push(new Block('C'));
		Stack<Block> s2 = new Stack<Block>();
		
		Stack<Block> s3 = new Stack<Block>();
		s3.push(new Block('D'));
		s3.push(new Block('E'));
		

		
		list.add(s1);
		list.add(s2);
		list.add(s3);
		
		s.setStackList(list);
		s.setFnValue(initState, finalState, s);
		
		/*List<State> path = search.AStarSsearch(initState, finalState);
		
		
		for (int i=path.size()-1; i>=0; i--) {
			State state = path.get(i);
			System.out.println(state.toString());
		}
		System.out.println("solution path: "+path.size());
		*///List<State> successors = init.generateSuccessors(initState, stacksNum);
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
