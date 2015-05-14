import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;



public class InstructionSet {
	
	protected String[][] map;
	private File file;
	private Scanner scanner;
	protected String operand1, operand2, newOp2="", newOp1="", output;
	protected boolean isFound;
//	protected int oneCtr;
	


	public InstructionSet() {

		initMap( "machineCodes.txt" );
	}

	protected void initMap( String f ) {
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

		isFound = find( operand2 );

		if( isFound==false )
			newOp2 = convertToBinary( operand2 );
		else
			for(int i=0; i<map.length; i++)
				if( map[i][0].equals( operand2 ) )
					newOp2 = map[i][1];			

		for(int i=0; i<map.length; i++)
			if( map[i][0].equals( operand1 ) )
				map[i][1] = newOp2;
	}

	protected void store() {}

	protected void save() {}

	protected void increment() {

		int temp;

		isFound = find( operand2 );

		if( isFound==false )
			temp = Integer.parseInt( operand2 ) + 1;
		else { 
			for(int i=0; i<map.length; i++)
				if( map[i][0].equals( operand2 ) )
					newOp2 = map[i][1];			

			temp = convertToDecimal( newOp2 ) + 1;
		}

		newOp2 = convertToBinary( Integer.toString( temp ) );

		for(int i=0; i<map.length; i++)
			if( map[i][0].equals( operand1 ) )
				map[i][1] = newOp2;
	}

	protected void decrement() {

		int temp;

		isFound = find( operand2 );

		if( isFound==false )
			temp = Integer.parseInt( operand2 ) - 1;
		else { 
			for(int i=0; i<map.length; i++)
				if( map[i][0].equals( operand2 ) )
					newOp2 = map[i][1];			

			temp = convertToDecimal( newOp2 ) - 1;
		}

		newOp2 = convertToBinary( Integer.toString( temp ) );

		for(int i=0; i<map.length; i++)
			if( map[i][0].equals( operand1 ) )
				map[i][1] = newOp2;		
	}

	protected void add() {

		int temp1=0, temp2=0;

		isFound = find( operand2 );

		if( isFound==false )
			temp2 = Integer.parseInt( operand2 );
		else
			for(int i=0; i<map.length; i++)
				if( map[i][0].equals( operand2 ) )
					temp2 = convertToDecimal( map[i][1] );

		for(int i=0; i<map.length; i++)
			if( map[i][0].equals( operand1 ) )
				temp1 = convertToDecimal( map[i][1] );

		newOp2 = convertToBinary( Integer.toString( temp1 += temp2 ) );

		for(int i=0; i<map.length; i++)
			if( map[i][0].equals( operand1 ) )
				map[i][1] = newOp2;		
	}

	protected void subtract() {

		int temp1=0, temp2=0;

		isFound = find( operand2 );

		if( isFound==false )
			temp2 = Integer.parseInt( operand2 );
		else
			for(int i=0; i<map.length; i++)
				if( map[i][0].equals( operand2 ) )
					temp2 = convertToDecimal( map[i][1] );

		for(int i=0; i<map.length; i++)
			if( map[i][0].equals( operand1 ) )
				temp1 = convertToDecimal( map[i][1] );

		newOp2 = convertToBinary( Integer.toString( temp1 -= temp2 ) );

		for(int i=0; i<map.length; i++)
			if( map[i][0].equals( operand1 ) )
				map[i][1] = newOp2;				
	}

	protected void multiply() {

		int temp1=0, temp2=0;

		isFound = find( operand2 );

		if( isFound==false )
			temp2 = Integer.parseInt( operand2 );
		else
			for(int i=0; i<map.length; i++)
				if( map[i][0].equals( operand2 ) )
					temp2 = convertToDecimal( map[i][1] );

		for(int i=0; i<map.length; i++)
			if( map[i][0].equals( operand1 ) )
				temp1 = convertToDecimal( map[i][1] );

		newOp2 = convertToBinary( Integer.toString( temp1 *= temp2 ) );

		for(int i=0; i<map.length; i++)
			if( map[i][0].equals( operand1 ) )
				map[i][1] = newOp2;						
	}

	protected void divide() {

		int temp1=0, temp2=0;

		isFound = find( operand2 );

		if( isFound==false )
			temp2 = Integer.parseInt( operand2 );
		else
			for(int i=0; i<map.length; i++)
				if( map[i][0].equals( operand2 ) )
					temp2 = convertToDecimal( map[i][1] );

		for(int i=0; i<map.length; i++)
			if( map[i][0].equals( operand1 ) )
				temp1 = convertToDecimal( map[i][1] );

		newOp2 = convertToBinary( Integer.toString( temp1 /= temp2 ) );

		for(int i=0; i<map.length; i++)
			if( map[i][0].equals( operand1 ) )
				map[i][1] = newOp2;						
	}

	protected void compare() {}

	protected void and() {

		output = "";
		isFound = find( operand2 );

		if( isFound==false ) {
			newOp2 = convertToBinary( operand2 );
		}
		else {
			for(int i=0; i<map.length; i++)
				if( map[i][0].equals( operand2 ) )
					newOp2 = map[i][1];						
		}

		for(int i=0; i<map.length; i++)
			if( map[i][0].equals( operand1 ) )
				newOp1 = map[i][1];

		for(int i=0; i<8; i++) {
			if( newOp1.charAt(i)=='1' && newOp2.charAt(i)=='1' )
				output += "1";
			else
				output += "0";
		}

		for(int i=0; i<map.length; i++)
			if( map[i][0].equals( operand1 ) )
				map[i][1] = output;						
	}

	protected void or() {

		output = "";
		isFound = find( operand2 );

		if( isFound==false ) {
			newOp2 = convertToBinary( operand2 );
		}
		else {
			for(int i=0; i<map.length; i++)
				if( map[i][0].equals( operand2 ) )
					newOp2 = map[i][1];						
		}

		for(int i=0; i<map.length; i++)
			if( map[i][0].equals( operand1 ) )
				newOp1 = map[i][1];

		for(int i=0; i<8; i++) {
			if( newOp1.charAt(i)=='1' || newOp2.charAt(i)=='1' )
				output += "1";
			else
				output += "0";
		}

		for(int i=0; i<map.length; i++)
			if( map[i][0].equals( operand1 ) )
				map[i][1] = output;						
	}

	protected void not() {}

	protected void xor() {}

	protected void jumpIfEqual() {}

	protected void jumpIfGreaterThan() {}

	protected void jumpIfLessThan() {}

	protected void jump() {}

	protected boolean find( String operand ) {

		for(int i=0; i<map.length; i++)
			if( map[i][0].equals( operand ) )
				return true;

		return false;
	}

	protected String convertToBinary( String operand ) {

			newOp2 = "";

			String tmp = Integer.toBinaryString( Integer.parseInt( operand ) );

			while( newOp2.length()<(8-tmp.length()) )
				newOp2 += "0";

			newOp2 += tmp;

			return newOp2;
	}

	protected int convertToDecimal( String operand ) {

		int bit=1;
		int decimal=0;

		for(int i=(operand.length()-1); i>=0; i--) {
			if( operand.charAt(i)=='1' )
				decimal = decimal + bit;

			bit = bit * 2;
		}

		return decimal;
	}

	protected String reverse( String str ) {

		String temp="";

		for(int i=7; i>=0; i--)
			temp += str.charAt(i);

		return temp;
	}
}

/*
		for(int i=7; i>=0; i--) {
			oneCtr = 0;

			if( carry=="1" ) oneCtr++;
			if( newOp1.charAt(i)=='1' ) oneCtr++;
			if( newOp2.charAt(i)=='1' ) oneCtr++;

			if( oneCtr==0 ) {
				output += "0";
				carry = "0";
			}
			else if( oneCtr==1 ) {
				output += "1";
				carry = "0";
			}
			else if( oneCtr==2 ) {
				output += "0";
				carry = "1";
			}
			else if( oneCtr==3 ) {
				output += "1";
				carry = "1";
			}
		}

*/