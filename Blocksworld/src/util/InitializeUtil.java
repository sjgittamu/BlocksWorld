package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class InitializeUtil {


	public State createInitState(int stacks, int blocks, List<Block> blocksList) {
		State initState = new State();
		int remainingBlocks = blocks;
		List<Stack<Block>>  stacksOfBlocks = new ArrayList<Stack<Block>>();
		List<Integer> takenBlocks = new ArrayList<>();			// keep record so that no duplicate blocks, dont have to remove from list
		Random rn = new Random();
		for(int i=0; i< stacks; i++){
			Stack<Block> stack = new Stack<Block>();
			int blocksInThisStack ;
			if(i == stacks-1)
				blocksInThisStack = remainingBlocks;
			else
				blocksInThisStack = rn.nextInt(remainingBlocks);
			remainingBlocks = remainingBlocks - blocksInThisStack;
			for(int j=0; j< blocksInThisStack; j++){
				int charIdx = rn.nextInt(blocksList.size());
				while(takenBlocks.contains(charIdx)){
					charIdx = rn.nextInt(blocksList.size());
				}
				takenBlocks.add(charIdx);
				Block nextBlockToBePlacedInStack = blocksList.get(charIdx);
				stack.push(nextBlockToBePlacedInStack);
			}
			stacksOfBlocks.add(stack);
		}
		initState.setStackList(stacksOfBlocks);
		return initState;
	}

	public State createFinalState(int stacks, int blocks, List<Block> blocksList) {
		List<Stack<Block>>  stacksOfBlocks = new ArrayList<Stack<Block>>(stacks);
		State finalState = new State();
		Stack<Block> firstStack = new Stack<Block>();

		Collections.sort(blocksList, new Comparator<Block>() {
			public int compare(Block o1, Block o2) {
				return o1.getCh().compareTo(o2.getCh());
			}
		});

		for (Block block : blocksList) {
			firstStack.push(block);
		}
		stacksOfBlocks.add(firstStack);
		for(int i=0; i<stacks-1; i++){
			Stack<Block> restStack = new Stack<Block>();
			stacksOfBlocks.add(restStack);
		}
		finalState.setStackList(stacksOfBlocks);
		return finalState;
	}

	public static List<State> generateSuccessors(State state, int stacksNum){
		List<State> successorStates = new ArrayList<State>();
		List<Stack<Block>> stacksInCurrState = state.getStackList();

		for(int i=0; i< stacksNum; i++){
			List<State> stateForCurrStackPop = new ArrayList<>();
			Stack<Block> currStack = stacksInCurrState.get(i);
			if(!currStack.isEmpty()){
				stateForCurrStackPop = getStatesForThisStackBlock(stacksInCurrState, i, stacksNum);
				successorStates.addAll(stateForCurrStackPop);
			}

		}
		return successorStates;
	}

	private static List<State> getStatesForThisStackBlock(List<Stack<Block>>  stacksInCurrState, int i, int stacksNum) {

		List<State> successorStates = new ArrayList<>();
		for(int j=0; j< stacksNum; j++){
			if(i != j ){	//different  stack
				State newState =new State(stacksInCurrState);
				List<Stack<Block>> copyOfCurrStateStacks = newState.getStackList();
				Block currBlock = copyOfCurrStateStacks.get(i).pop();
				copyOfCurrStateStacks.get(j).push(currBlock);
				successorStates.add(newState);
			}
		}
		return successorStates;
	}

	public State createFakeInitState(int stacksNum, int blocksNum, List<Block> blocksList) {
		List<Stack<Block>> list = new ArrayList<Stack<Block>>();
		Stack<Block> stack1 = new Stack<Block>();
		Stack<Block> stack2 = new Stack<Block>();
		Stack<Block> stack3 = new Stack<Block>();
		Stack<Block> stack4 = new Stack<Block>();
		Stack<Block> stack5 = new Stack<Block>();
		Stack<Block> stack6 = new Stack<Block>();
		/*stack1.add(new Block('A'));
		stack1.add(new Block('D'));
		stack1.add(new Block('F'));
		stack3.add(new Block('B'));
		stack3.add(new Block('E'));
		stack3.add(new Block('C'));
		 */
		stack1.add(new Block('F'));
		
		
		stack2.add(new Block('E'));
		stack2.add(new Block('A'));
		stack2.add(new Block('G'));
		stack2.add(new Block('D'));
		
		stack3.add(new Block('I'));
		
		stack4.add(new Block('J'));
		stack4.add(new Block('H'));
		
		stack6.add(new Block('B'));
		stack6.add(new Block('C'));
		
		
		
		
		list.add(0, stack1);
		list.add(1, stack2);
		list.add(2, stack3);
		list.add(2, stack4);
		list.add(2, stack5);
		list.add(2, stack6);

		State state = new State(list);

		return state;
	}

	/*public Map<State, List<State>> createGraph(State initState, int stacksNum, int blocksNum,State goal) {
		Map<State, List<State>> graph = new HashMap<State, List<State>>();
		graph.put(initState, generateSuccessors(initState, stacksNum));
		Queue<State> statesToGenerateSuccessorsFor = new LinkedList<>();//as no heuristic set a of now
		for (List<State> states : graph.values()) {
			for (State state : states) {
				statesToGenerateSuccessorsFor.add(state);
			}
			
		}

		Iterator<State> iter = statesToGenerateSuccessorsFor.iterator();
		for (int i=0;i<1000000;i++) {
			State state = statesToGenerateSuccessorsFor.remove();
			if(state.equals(goal))
				return graph;
			List<State> succ = generateSuccessors(state, stacksNum);
			for (State state2 : succ) {
				statesToGenerateSuccessorsFor.add(state2);
			}

			graph.put(state, succ);


		}
		return graph;
	}*/


}
