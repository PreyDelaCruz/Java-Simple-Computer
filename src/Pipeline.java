/*
	CMSC 132 Project: Simple Computer
	Group Members:	Dela Cruz, Precious Mae A., 2009-45749
					<>
					<>
					<>
*/



import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;



public class Pipeline {

	protected ArrayList<String>[] pipe;
	protected String[][] operand;
	protected int lineCount, lineOfDependency, cycleCount=0, fdeCtr, fIndex=0, max=0;
	protected boolean hasDependency;
	protected String[] fde = new String[3];



	public Pipeline( int lineCtr ) {

		this.lineCount = lineCtr;
		this.pipe = new ArrayList[lineCtr];
		this.operand = new String[lineCtr][2];

		for(int i=0; i<lineCtr; i++)
			pipe[i] = new ArrayList();

		fde[0] = "F";
		fde[1] = "D";
		fde[2] = "E";
	}

	protected void addOperand( String op1, String op2 , int index) {

		this.operand[index][0] = op1;
		this.operand[index][1] = op2;
	}

	protected void startClockCycle() {

		int start, nullCtr=0, tmp;
		boolean added;

		for(int i=0; i<3; i++)
			pipe[0].add( i, fde[i] );

		cycleCount = 3;
		fIndex = 1;
		nullCtr++;

		for(int i=1; i<lineCount; i++) {
			lineOfDependency = checkDependency( i );
			fdeCtr = 0;

			if( hasDependency==true ) {
				start = pipe[lineOfDependency].size();

				for(int a=0; a<nullCtr; a++)
					added = pipe[i].add( "N" );

				while( pipe[i].size()<start )
					added = pipe[i].add( "S" );

				tmp = start;

				while( fdeCtr<3 ) {
					boolean duplicate = false;
					for(int j=0; j<i; j++) {
						if( pipe[j].size()>4 ) {
							if( pipe[j].get(tmp).equals(fde[fdeCtr]) ) {
								added = pipe[i].add( "S" );
								tmp++;
								duplicate = true;
								break;
							}
						}
					}
					if( duplicate==false ) {
						added = pipe[i].add( fde[fdeCtr] );
						fdeCtr++;
					}
				}
			}
			else {				
				for(int a=0; a<nullCtr; a++)
					added = pipe[i].add( "N" );

				while( fdeCtr<3 ) {
					boolean duplicate = false;

					for(int j=0; j<i; j++) {
						if( pipe[j].size()>pipe[i].size() ) {
							if( pipe[j].get(pipe[i].size()).equals(fde[fdeCtr]) ) {
								added = pipe[i].add( "S" );
								duplicate = true;
								break;
							}
						}
					}

					if( duplicate==false ) {
						added = pipe[i].add( fde[fdeCtr] );
						fdeCtr++;
					}
				}

				nullCtr++;
			}

		}

		countMax();
		fill();

/*		for(int i=0; i<pipe.length; i++) {
			for(int j=0; j<pipe[i].size(); j++)
				System.out.print( pipe[i].get(j) + " " );
			System.out.println();
		}
*/	}

	protected int checkDependency( int index ) {

		int i;

		hasDependency = false;

		for(i=(index-1); i>=0; i--) {
			if( operand[index][0].equals(operand[i][0]) || operand[index][0].equals(operand[i][1]) || operand[index][1].equals(operand[i][0]) ) {
//				System.out.println( "operation " + index + " has dependency with " + i );
				hasDependency = true;
				break;
			}
		}

		return i;
	}

	private void countMax() {

		for(int i=0; i<pipe.length; i++) {
			if( pipe[i].size()>max ) {
				max = pipe[i].size();
			}
		}
	}

	private void fill() {

		for(int i=0; i<pipe.length; i++) 
			if( pipe[i].size()<max ) 
				while( pipe[i].size()!=max )
					pipe[i].add( "N" );
	}
}