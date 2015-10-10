package util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

public class State implements Comparable<State>{
	List<Stack<Block>> stackList;
	Double fnValue;
	public static int stacksNum;
	public static int blocksNum;
	State parent;
	int depth;
	Double gValue;
	Double hValue;
	List<Block> nextBlocks = new ArrayList<>();


	public State(List<Stack<Block>> stackList) {
		super();
		this.stackList = new ArrayList<Stack<Block>>(stackList.size());
		for (Stack<Block> stack : stackList) {
			this.stackList.add((Stack<Block>)stack.clone());
		}
		fnValue=0.0;
	}

	public State() {}
	Double getHeuristicH(State goal, State currState){
		this.hValue =getDistanceFromGoalNode(goal, currState);
		//System.out.println("H: "+this.hValue);
		return this.hValue;
	}
	Double getHeuristicG(State start,State currState){
		this.gValue = outOfPlaceNodes(start, currState, true);
		//System.out.println("G: "+this.gValue);
		return this.gValue; 
	}

	public List<Stack<Block>> getStackList() {
		return stackList;
	}
	public void setStackList(List<Stack<Block>> stackList) {
		this.stackList = stackList;
	}
	public Double getFnValue() {
		return fnValue;
	}
	public void setFnValue(State start, State goal, State currState) {
		this.fnValue = getHeuristicG(start, currState)+getHeuristicH(goal, currState);
		//System.out.println(this.fnValue);
	}
	Stack<Block> getStack(int index){
		return stackList.get(index);
	}
	Block popTopOfStack(int index){
		return getStack(index).pop();
	}
	Block peekTopOfStack(int index){
		return getStack(index).peek();
	}

	public State getParent() {
		return parent;
	}
	public void setParent(State parent) {
		this.parent = parent;
	}

	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public List<State> traceback(State root){

		State state = this;
		List<State> path = new ArrayList<>();
		path.add(this);
		while(state != root){
			state = state.parent;
			path.add(state);
		}
		return path;
	}

	public List<State> successors(State root, State goal) {
		//List<State> successorStates =graph.get(this);
		List<State> successorStates = InitializeUtil.generateSuccessors(this,stacksNum);
		for (State state : successorStates) {
			if(state.parent==null){
				state.setFnValue(root, goal, state);
			}
		}
		return successorStates;

	}


	public double getDistanceFromGoalNode(State goal, State currState){	//lesser score is better
		double outOfPlace = outOfPlaceNodes(goal, currState, false);		//30 out of 100
		//System.out.println("outOfPlace"+outOfPlace);
		int nextStepsTopHops = nextNodesNearStackTop(goal);	//70 out of 100
		//System.out.println("nextStepsTopHops "+nextStepsTopHops);
		
		return outOfPlace+nextStepsTopHops;
	}

	private int nextNodesNearStackTop(State goal) {
		List<Block> nextBlocksToBePlaced = nextBlocks;
		if(nextBlocksToBePlaced.size() == 0)
			return 0;
		int toBePicked = (nextBlocksToBePlaced.size()>2 ? Math.max(nextBlocksToBePlaced.size()/4, 2) : nextBlocksToBePlaced.size());		//25% or two next closest values
		int totalHops =0;
		for(int i=0; i<toBePicked; i++){
			Block nextBlock = nextBlocksToBePlaced.get(i);
			int hops = hopsToTop(nextBlock,goal, blocksNum-nextBlocksToBePlaced.size()-i);
			totalHops += hops;
		}
		
		return (totalHops*30)/(toBePicked*blocksNum);		//can be max tobepickd*blocksnum down...dont know if i shud divide
		//return totalHops;
	}

	private int hopsToTop(Block nextBlock, State goal, int blkLoc) {
		// find the block's location in goal.. suppose 2nd from bottom
		// find current location of block.. search all curr stacks, find stack num and index
		//distance from top
		//search every stack.. ee how many hops from top
		int idx = 0;
		for(int i=0; i<stacksNum; i++){
			idx =1;	//reset for every stack
			Stack<Block> s =this.getStack(i);
			Iterator<Block> iter = s.iterator();
			while (iter.hasNext()) {
				if(iter.next().getCh() != nextBlock.getCh()){
					idx++;
				}else{
					return s.size()-idx+1;		//as counted from bottom to top, hops will be subtracted
				}
			}
			
		} 
		return idx;
	}

	private double outOfPlaceNodes(State goal, State currState, boolean isStart) {
		int outOfPlaceCounter = 0;
		for(int i=0; i<stacksNum; i++){
			Stack<Block> goalStack = goal.getStack(i);
			Stack<Block> currStack = currState.getStack(i);
			int stackDiff = compareStacks(currStack, goalStack, isStart);
			outOfPlaceCounter += stackDiff;
		}

		if(isStart)
			return (outOfPlaceCounter*70/(2*blocksNum)) ;
		else
			return (outOfPlaceCounter*70/(blocksNum)) ;
		
	}

	private int compareStacks(Stack<Block> currStack, Stack<Block> goalStack, boolean isStart) {
		int outOfPlace =0 ;
		ListIterator<Block> currIter = currStack.listIterator();
		ListIterator<Block> goalIter = goalStack.listIterator();
		
		while(currIter.hasNext() && goalIter.hasNext()){			//both have something in them
			Block nextGoalBlock = goalIter.next();
			if(currIter.next().getCh() !=  nextGoalBlock.getCh()){		//traverse in opposite side 
				outOfPlace++;
				if(!isStart)
					nextBlocks.add(nextGoalBlock);
			}
		}
		while(currIter.hasNext()){
			currIter.next();
			outOfPlace++;
		}
		while(goalIter.hasNext()){
			Block b = goalIter.next();
			if(!isStart)
				nextBlocks.add(b);
			if(isStart){
				outOfPlace++;
			}
		}

		return outOfPlace;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stackList == null) ? 0 : stackList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (stackList == null) {
			if (other.stackList != null)
				return false;
		} else if (!stackList.equals(other.stackList))
			return false;
		return true;
	}

	@Override
	public int compareTo(State o2) {
		int returnValue = this.fnValue.compareTo(o2.fnValue);
		return (returnValue == 0 ? this.hValue.compareTo(o2.hValue) : returnValue); 
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i=1; i<=stacksNum; i++){
			sb.append(i+" | ");
			Stack<Block> stack = this.getStack(i-1);
			ListIterator<Block> blockIter = stack.listIterator();
			while(blockIter.hasNext()){
				sb.append(blockIter.next().getCh()+" ");
			}
			sb.append("\n");
		}
		return sb.toString(); 
	}


}



















/*private List<Block> nextBlocks(State goal, StateAndGraph initializeStateGraph) {
List<Block> differenceInBlks = new ArrayList<>();
Stack<Block> firstStackCurrState =this.getStack(0);
List<Block> currBlocks =  new ArrayList<Block>();
while(!firstStackCurrState.isEmpty()){			//iterate and save.. saved in list in opposite order thus traverse backwards in next if cond
	currBlocks.add(firstStackCurrState.pop());
}
int currBlkLength = currBlocks.size();
List<Block> finalBlocks = initializeStateGraph.getSortedFinalBlockList();
for (int i=0; i<finalBlocks.size(); i++) {
	if(finalBlocks.get(i) != currBlocks.get(currBlkLength-i-1)){
		differenceInBlks.add(finalBlocks.get(i));
	}
}
return differenceInBlks;
}*/
