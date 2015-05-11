import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;



public class InstructionSet {
	
	protected String[][] map;
	private File file;
	private Scanner scanner;
	protected String operand1, operand2, op1OldValue;
	


	public InstructionSet() {

		initMap( "machineCodes.txt" );
	}

	private void initMap( String f ) {
		String line, token="";
		String[] temp, subtemp;

		try {
			file = new File( f );
			scanner = new Scanner( file );

			for(int lineNum=1; scanner.hasNextLine(); lineNum++) {
				line = scanner.nextLine();
				token = token.concat( ( line=line.concat( "#" ) ) );
			}
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog( null, "File not found!" );
		}	

		temp = token.split( "#" );

		map = new String[temp.length][2];

		for(int i=0; i<temp.length; i++) {
			subtemp = temp[i].split( "," );

			for(int j=0; j<subtemp.length; j++) 
				map[i][j] = subtemp[j];				
		}
	}

	protected void doOperation( String opcode, String op1, String op2 ) {

		this.operand1 = op1;
		this.operand2 = op2;

		switch( opcode ) {
			case "LOAD": load(); break;
			case "STORE": store(); break;
			case "SAVE": save(); break;
			case "INC": increment(); break;
			case "DEC": decrement(); break;
			case "ADD": add(); break;
			case "SUB": subtract(); break;
			case "MUL": multiply(); break;
			case "DIV": divide(); break;
			case "CMP": compare(); break;
			case "AND": and(); break;
			case "OR": or(); break;
			case "NOT": not(); break;
			case "XOR": xor(); break;
			case "JE": jumpIfEqual(); break;
			case "JG": jumpIfGreaterThan(); break;
			case "JL": jumpIfLessThan(); break;
			case "JMP": jump(); break;
			default: break;
		}
	}

	protected void load() {

		boolean isFound=false;
		String newOp2="", tmp;

		for(int i=0; i<map.length; i++) {
			if( !map[i][0].equals(operand2) )
				isFound = false;
			else {
				isFound = true;
				break;
			}	
		}
		
		if( isFound==false ) {
			tmp = Integer.toBinaryString( Integer.parseInt( operand2 ) );

			while( newOp2.length()<(8-tmp.length()) )
				newOp2 += "0";

			newOp2 += tmp;

			for(int i=0; i<map.length; i++)
				if( map[i][0].equals(operand1) )
					map[i][1] = newOp2;
		}
		else {
			for(int i=0; i<map.length; i++)
				if( map[i][0].equals(operand2) )
					newOp2 = map[i][1];

			for(int i=0; i<map.length; i++)
				if( map[i][0].equals(operand1) )
					map[i][1] = newOp2;
		}
	}

	protected void store() {

		



	}

	protected void save() {

	



	}

	protected void increment() {

		
	}

	protected void decrement() {

		
	}

	protected void add() {

		


	}

	protected void subtract() {

		



	}

	protected void multiply() {

		



	}

	protected void divide() {

		


	}

	protected void compare() {

		


	}

	protected void and() {

		



	}

	protected void or() {

		



	}

	protected void not() {

		



	}

	protected void xor() {

		


	}

	protected void jumpIfEqual() {

		



	}

	protected void jumpIfGreaterThan() {

		



	}

	protected void jumpIfLessThan() {

		



	}

	protected void jump() {

		


	}
}