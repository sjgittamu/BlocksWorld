package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

public class StateAndGraph {/*

	int stacksNum ;
	int blocksNum ;
	static State finalState ;
	List<Block> blocksList = new ArrayList<Block>();

	public State getFinalState(){
		return finalState;
	}
	public State generateStartState(){
		//random number of stacks.. give min n max limit
		Random rand = new Random();
		stacksNum = 3;//2+ rand.nextInt(3);	// stacks between 2 to 4 i guess

		//random number of blocks.. give min n max limit
		blocksNum = 6;//4+ rand.nextInt(3); 	//blocks between 4 to 6

		//generate a list of blocks of dat size
		
		for(int i=0; i<blocksNum; i++){
			blocksList.add(new Block((char)('A'+i)));
		}

		//place blocks randomly in stacks
		State initState = createInitState(stacksNum, blocksNum, blocksList);

		for(int i=0; i<blocksNum; i++){
			blocksList.add(new Block((char)('A'+i)));
		}
		//create a final state for reference
		finalState = createFinalState(stacksNum, blocksNum,blocksList);

		//return the init state
		return initState;
	}
	
	List<Block> getSortedFinalBlockList(){
		Collections.sort(blocksList, new Comparator<Block>() {
			public int compare(Block o1, Block o2) {
				return o1.getCh().compareTo(o2.getCh());
			}
		});
		return blocksList;
	}

	private State createFinalState(int stacks, int blocks, List<Block> blocksList) {
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

	private State createInitState(int stacks, int blocks, List<Block> blocksList) {
		State initState = new State();
		initState.stacksNum = stacks;
		initState.blocksNum = blocks;
		int remainingBlocks = blocks;
		List<Stack<Block>>  stacksOfBlocks = new ArrayList<Stack<Block>>();

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
				Block nextBlockToBePlacedInStack = blocksList.get(charIdx);
				blocksList.remove(charIdx);
				stack.push(nextBlockToBePlacedInStack);
			}
			stacksOfBlocks.add(stack);
		}
		initState.setStackList(stacksOfBlocks);
		return initState;
	}

	public Map<State, List<State>> createGraph(State startState){
		Map<State, List<State>> graph = new HashMap<State, List<State>>();

		//foreachState
		//pickup top of each of the stacks
		//put it on each of the remaining stacks
		return graph;
	}
	
	
	public List<State> generateSuccessors(State state){
		List<State> successorStates = new ArrayList<State>();
		List<Stack<Block>> stacksInCurrState = state.getStackList();
		for(int i=0; i< stacksNum; i++){
			List<State> stateForCurrStackPop = new ArrayList<>();
			Stack<Block> currStack = stacksInCurrState.get(i);
			if(!currStack.isEmpty()){
				stateForCurrStackPop = getStatesForThisStackBlock(stacksInCurrState, i);
			}
			successorStates.addAll(stateForCurrStackPop);
		}
		return successorStates;
	}
	private List<State> getStatesForThisStackBlock(List<Stack<Block>>  stacksInCurrState, int i) {
		List<Stack<Block>> copyOfCurrStateStacks =null;
		int popStack = i;
		List<State> successorStates = new ArrayList<>();
		for(int j=0; j< stacksNum; j++){
			if(i != j){	//different  stack
				copyOfCurrStateStacks = stacksInCurrState;
				Block currBlock = copyOfCurrStateStacks.get(popStack).pop();
				copyOfCurrStateStacks.get(j).push(currBlock);
				State newState = new State(copyOfCurrStateStacks);
				successorStates.add(newState);
				popStack = j;
			}
		}
		return successorStates;
	}

	public List<State> generateSuccessors(State state){
		setState(state);
		List<State> successorStates = new ArrayList<State>();
		List<Stack<Block>> stacksInCurrState = state.getStackList();

		List<Stack<Block>>  copyOfCurrStateStacks = null;
		int popStack =0;

		for(int i=0; i< stacksNum; i++){
			Stack<Block> currStack = stacksInCurrState.get(i);
			popStack = i;
			if(!currStack.isEmpty()){
				for(int j=0; j< stacksNum; j++){
					if(i != j){	//different  stack
						copyOfCurrStateStacks = stacksInCurrState;
						Block currBlock = copyOfCurrStateStacks.get(popStack).pop();
						copyOfCurrStateStacks.get(j).push(currBlock);
						State newState = new State(copyOfCurrStateStacks);
						successorStates.add(newState);
						popStack = j;
					}
				}
				stacksInCurrState.get(i).push(stacksInCurrState.get(popStack).pop());
			}
			
		}
		
		
		for (Stack<Block> currStack : stacksInCurrState) {
			if(!currStack.isEmpty()){
				Block topmostBlock = currStack.peek();
				for (Stack<Block> otherStack : stacksInCurrState) {
					if(!otherStack.equals(currStack)){
						copyOfCurrStateStacks = stacksInCurrState;
						otherStack.push(topmostBlock);
					}else{
						otherStack.pop();
					}
				}
				State newState = new State();
				newState.setStackList(copyOfCurrStateStacks);
				successorStates.add(newState);
			}
		}


		return successorStates;

		
		List<State> successorStates = new ArrayList<State>();
		List<Stack<Block>> stacksInState = state.getStackList();
		List<Stack<Block>> temp ;
		for(int i=0; i<stacksNum; i++){
			temp = stacksInState;
			Stack<Block> currStack = temp.get(i);
			Block currBlock = currStack.peek();
			for(int j=0; j<stacksNum; i++){
				if(i != j){				//push this block in every other stack except this one
					State currstate = new State();
					temp.get(j).push(currBlock);
					List<Stack<Block>> stacks= currstate.getStackList();
					stacks.add(currStack);

				}							//TODO pop the topmost element from ith stack
			}
		}


		return null;
		 }
	}
*/}
