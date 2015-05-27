/*
	CMSC 132 Project: Simple Computer
	Group Members:	Dela Cruz, Precious Mae A., 2009-45749
					<>
					<>
					<>
*/



import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;



public class SimpleComputer implements ActionListener{
	
	private static InstructionSet instructionSet = new InstructionSet();
	private static Pipeline pipeline;
	private static JFrame frame;
	private static JPanel topPanel, centerPanel, opcodePanel, registerPanel, inputPanel, registersPanel, cyclePanel, pipelinePanel, codePanel, clockCyclePanel, subPipelinePanel, instAreaPanel;
	private static JButton[] opcodeList = new JButton[18];
	private static JButton[] registerList = new JButton[11];
	private static JTextArea[][] pp, cc;
	private static JTextArea[] ii;
	private static JButton addInst, process, reset, clear, next;
	private static JLabel opcodeLabel, registerLabel, inputLabel, cycleLabel, registersLabel, pipelineLabel, codeLabel, ccLabel;
	private static JTextField inputField;
	private static JTextArea instructionArea, registersArea, pipelineArea, machineCodeArea, actualInstArea, clockCycleArea;
	private static int operandCtr=0, lineCtr=0, nextCtr, limit, limit1;
	private static String tempInst="";
	private static String[][] instArray, machineCodes;
	


	public SimpleComputer() {

		frame = new JFrame( "Simple Computer" );
		topPanel = new JPanel();
		centerPanel = new JPanel();
		opcodePanel = new JPanel();
		registerPanel = new JPanel();
		inputPanel = new JPanel();
		cyclePanel = new JPanel();
		registersPanel = new JPanel();
		pipelinePanel = new JPanel();
		codePanel = new JPanel();
		clockCyclePanel = new JPanel();
		subPipelinePanel = new JPanel();
		instAreaPanel = new JPanel();

		initTopPanel();
		initCenterPanel();

		initFrame( 1000, 700 );
		frame.setVisible( true );
	}

	private void initTopPanel() {

		topPanel.setPreferredSize( new Dimension( 1000, 140 ) );
		topPanel.setBackground( new Color( 160, 0, 0 ) );
		topPanel.setLayout( new FlowLayout() );

		initOpcodePanel();
		initRegisterPanel();
		initInputPanel();
		
		frame.add( topPanel, BorderLayout.NORTH );
	}

	private void initCenterPanel() {

		centerPanel.setPreferredSize( new Dimension( 1000, 550 ) );
		centerPanel.setBackground( new Color( 160, 0, 0 ) );
		centerPanel.setLayout( new FlowLayout() );

		initCyclePanel();
		initRegistersPanel();
		initPipelinePanel();
		initCodePanel();

		updateRegisters();

		frame.add( centerPanel );
	}

	private void initOpcodePanel() {

		opcodePanel.setPreferredSize( new Dimension( 410, 135 ) );
		opcodePanel.setBackground( new Color( 255, 170, 170 ) );

		opcodeLabel = new JLabel( "OPCODES" );
		opcodeLabel.setFont( new Font( "Dialog", Font.BOLD, 20 ) );
		opcodeLabel.setPreferredSize( new Dimension( 400, 20 ) );
		opcodePanel.add( opcodeLabel );

		opcodeList[0] = new JButton( "LOAD" );
		opcodeList[1] = new JButton( "STORE" );
		opcodeList[2] = new JButton( "SAVE" );
		opcodeList[3] = new JButton( "INC" );
		opcodeList[4] = new JButton( "DEC" );
		opcodeList[5] = new JButton( "ADD" );
		opcodeList[6] = new JButton( "SUB" );
		opcodeList[7] = new JButton( "MUL" );
		opcodeList[8] = new JButton( "DIV" );
		opcodeList[9] = new JButton( "CMP" );
		opcodeList[10] = new JButton( "AND" );
		opcodeList[11] = new JButton( "OR" );
		opcodeList[12] = new JButton( "NOT" );
		opcodeList[13] = new JButton( "XOR" );
		opcodeList[14] = new JButton( "JE" );
		opcodeList[15] = new JButton( "JG" );
		opcodeList[16] = new JButton( "JL" );
		opcodeList[17] = new JButton( "JMP" );

		for(int i=0; i<opcodeList.length; i++) {
			opcodeList[i].addActionListener( this );
			opcodePanel.add( opcodeList[i] );
		}

		topPanel.add( opcodePanel );
	}

	private void initRegisterPanel() {

		registerPanel.setPreferredSize( new Dimension( 255, 135 ) );
		registerPanel.setBackground( new Color( 255, 170, 170 ) );

		registerLabel = new JLabel( "REGISTERS" );
		registerLabel.setFont( new Font( "Dialog", Font.BOLD, 20 ) );
		registerLabel.setPreferredSize( new Dimension( 240, 20 ) );
		registerPanel.add( registerLabel );

		registerList[0] = new JButton( "MAR0" );
		registerList[1] = new JButton( "MAR1" );
		registerList[2] = new JButton( "R0" );
		registerList[3] = new JButton( "R1" );
		registerList[4] = new JButton( "R2" );
		registerList[5] = new JButton( "R3" );
		registerList[6] = new JButton( "R4" );
		registerList[7] = new JButton( "R5" );
		registerList[8] = new JButton( "R6" );
		registerList[9] = new JButton( "R7" );
		registerList[10] = new JButton( "IMMEDIATE" );

		for(int i=0; i<registerList.length; i++) {
			registerList[i].addActionListener( this );
			registerList[i].setEnabled( false );
			registerPanel.add( registerList[i] );
		}

		topPanel.add( registerPanel );
	}

	private void initInputPanel() {

		inputPanel.setPreferredSize( new Dimension( 310, 135 ) );
		inputPanel.setBackground( new Color( 255, 170, 170 ) );

		inputLabel = new JLabel( "INSTRUCTION INPUT" );
		inputLabel.setFont( new Font( "Dialog", Font.BOLD, 20 ) );
		inputLabel.setPreferredSize( new Dimension( 300, 20 ) );
		inputPanel.add( inputLabel );

		inputField = new JTextField();
		inputField.setPreferredSize( new Dimension( 300, 50 ) );
		inputField.setFont( new Font( "Dialog", Font.BOLD, 40 ) );
		inputField.setEditable( false );
		inputPanel.add( inputField );

		addInst = new JButton( "ADD INSTRUCTION" );
		addInst.setPreferredSize( new Dimension( 140, 30 ) );
		addInst.addActionListener( this );
		inputPanel.add( addInst );

		clear = new JButton( "CLEAR FIELD" );
		clear.setPreferredSize( new Dimension( 140, 30 ) );
		clear.addActionListener( this );
		inputPanel.add( clear );

		topPanel.add( inputPanel );
	}

	private void initInstAreaPanel() {

		instAreaPanel.setPreferredSize( new Dimension( 125, 240 ) );
		instAreaPanel.setBackground( Color.WHITE );

		cyclePanel.add( instAreaPanel );
	}

	private void initClockCyclePanel() {

		clockCyclePanel.setPreferredSize( new Dimension( 350, 240 ) );
		clockCyclePanel.setBackground( Color.WHITE );

		cyclePanel.add( clockCyclePanel );
	}

	private void initCyclePanel() {

		cyclePanel.setPreferredSize( new Dimension( 490, 280 ) );
		cyclePanel.setBackground( new Color( 255, 170, 170 ) );

		cycleLabel = new JLabel( "CYCLES" );
		cycleLabel.setFont( new Font( "Dialog", Font.BOLD, 20 ) );
		cycleLabel.setPreferredSize( new Dimension( 490, 20 ) );
		cyclePanel.add( cycleLabel );

		instructionArea = new JTextArea( 12, 42 );
		instructionArea.setEditable( false );
		instructionArea.setMargin( new Insets( 10, 10, 0, 0 ) );
		cyclePanel.add( instructionArea );

		process = new JButton( "PROCESS" );
		process.setPreferredSize( new Dimension( 90, 30 ) );
		process.addActionListener( this );
		cyclePanel.add( process );

		reset = new JButton( "RESET" );
		reset.setPreferredSize( new Dimension( 90, 30 ) );
		reset.addActionListener( this );
		cyclePanel.add( reset );

		centerPanel.add( cyclePanel );
	}

	private void initRegistersPanel() {

		registersPanel.setPreferredSize( new Dimension( 490, 280 ) );
		registersPanel.setBackground( new Color( 255, 170, 170 ) );

		registersLabel = new JLabel( "REGISTERS" );
		registersLabel.setFont( new Font( "Dialog", Font.BOLD, 20 ) );
		registersLabel.setPreferredSize( new Dimension( 490, 20 ) );
		registersPanel.add( registersLabel );

		registersArea = new JTextArea( 14, 42 );
		registersArea.setEditable( false );
		registersArea.setMargin( new Insets( 10, 10, 0, 0 ) );
		registersPanel.add( registersArea );

		centerPanel.add( registersPanel );
	}

	private void initSubPipelinePanel() {

		subPipelinePanel.setPreferredSize( new Dimension( 470, 160 ) );
		subPipelinePanel.setBackground( Color.WHITE );

		pipelinePanel.add( subPipelinePanel );
	}

	private void initPipelinePanel() {

		pipelinePanel.setPreferredSize( new Dimension( 490, 230 ) );
		pipelinePanel.setBackground( new Color( 255, 170, 170 ) );

		pipelineLabel = new JLabel( "PIPELINE" );
		pipelineLabel.setFont( new Font( "Dialog", Font.BOLD, 20 ) );
		pipelineLabel.setPreferredSize( new Dimension( 490, 20 ) );
		pipelinePanel.add( pipelineLabel );

		initSubPipelinePanel();

		ccLabel = new JLabel();
		ccLabel.setFont( new Font( "Dialog", Font.BOLD, 20 ) );
		pipelinePanel.add( ccLabel );

		next = new JButton( "NEXT" );
		next.setPreferredSize( new Dimension( 90, 30 ) );
		next.addActionListener( this );
		pipelinePanel.add( next );

		centerPanel.add( pipelinePanel );
	}

	private void initCodePanel() {

		codePanel.setPreferredSize( new Dimension( 490, 230 ) );
		codePanel.setBackground( new Color( 255, 170, 170 ) );

		codeLabel = new JLabel( "CODE" );
		codeLabel.setFont( new Font( "Dialog", Font.BOLD, 20 ) );
		codeLabel.setPreferredSize( new Dimension( 490, 20 ) );
		codePanel.add( codeLabel );

		machineCodeArea = new JTextArea( "MACHINE CODE\n", 12, 21 );
		machineCodeArea.setEditable( false );
		codePanel.add( machineCodeArea );

		actualInstArea = new JTextArea( "ACTUAL INSTRUCTION\n", 12, 21 );
		actualInstArea.setEditable( false );
		codePanel.add( actualInstArea );

		centerPanel.add( codePanel );
	}

	private static void initFrame( int x, int y ) {
		frame.setSize( x, y );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setResizable( false );
		frame.setLocationRelativeTo( null );
	}

	private void startPipeline() {

		String tempRegister;

		instArray = new String[lineCtr][3];
		pipeline = new Pipeline( lineCtr );

		String[] temp = instructionArea.getText().split( "\n" );

		for(int i=0; i<temp.length; i++)
			instArray[i] = temp[i].split( "," );
		
		instAreaPanel.setLayout( new GridLayout( lineCtr, 1 ) );
		ii = new JTextArea[lineCtr];

		for(int i=0; i<lineCtr; i++) {
			ii[i] = new JTextArea( instArray[i][0] + "," + instArray[i][1] + "," + instArray[i][2] );
			ii[i].setFont( new Font( "Dialog", Font.BOLD, 15 ) );
			ii[i].setMargin( new Insets( 5, 5, 0, 0 ) );
			ii[i].setLineWrap( true );
			instAreaPanel.add( ii[i] );
		}

		for(int i=0; i<(instArray.length); i++) {
			updateCodes( instArray[i][0], instArray[i][1], instArray[i][2] );
			instructionSet.doOperation( instArray[i][0], instArray[i][1], instArray[i][2] );
			pipeline.addOperand( instArray[i][1], instArray[i][2] , i);
			updateRegisters();
		}

		pipeline.startClockCycle();

		nextCtr = 0;
		limit = pipeline.max;
		limit1 = 0;
		pp = new JTextArea[2][3];
		subPipelinePanel.setLayout( new GridLayout( 2, 3 ) ); //	CHANGED
		cc = new JTextArea[lineCtr][pipeline.max];
		clockCyclePanel.setLayout( new GridLayout( lineCtr, pipeline.max ) );

		for(int i=0; i<lineCtr; i++) {
			for(int j=0; j<pipeline.max; j++) {
				cc[i][j] = new JTextArea();
				cc[i][j].setFont( new Font( "Dialog", Font.BOLD, 20 ) );
				cc[i][j].setLineWrap( true );
				cc[i][j].setMargin( new Insets( 5, 10, 0, 0 ) );
				clockCyclePanel.add( cc[i][j] );
			}
		}

		for(int i=0; i<2; i++) {
			for(int j=0; j<3; j++) {
				pp[i][j] = new JTextArea();
				pp[i][j].setFont( new Font( "Dialog", Font.BOLD, 22 ) );
				if( i==0 ) {
					if( j==0 ) pp[i][j].setText( "FETCH" );
					if( j==1 ) pp[i][j].setText( "DECODE" );
					if( j==2 ) pp[i][j].setText( "EXECUTE" );
				}
				pp[i][j].setLineWrap( true );
				pp[i][j].setMargin( new Insets( 15, 15, 0, 0 ) );
				pp[i][j].setEditable( false );
				subPipelinePanel.add( pp[i][j] );
			}
		}

		initInstAreaPanel();
		updateClockCycle( limit1 );
		limit1++;
		updatePipeline( nextCtr );
		nextCtr++;
	}

	private void updateCodes( String opcode, String operand1, String operand2 ) {
		
		boolean found=false;

		for(int i=0; i<instructionSet.map.length; i++) {
			if( instructionSet.map[i][0].equals( opcode ) ) {
				machineCodeArea.append( instructionSet.map[i][1] + " " );
			}
		}

		for(int i=0; i<instructionSet.map.length; i++) {
			if( instructionSet.map[i][0].equals( operand1 ) ) {
				machineCodeArea.append( instructionSet.map[i][1] + " " );
				found = true;
			}
		}

		if( found==false ) {
			machineCodeArea.append( operand1 + " " );
		}

		found = false;

		for(int i=0; i<instructionSet.map.length; i++) {
			if( instructionSet.map[i][0].equals( operand2 ) ) {
				machineCodeArea.append( instructionSet.map[i][1] + "\n" );
				found = true;
			}
		}

		if( found==false ) {
			machineCodeArea.append( operand2 + "\n" );
		}

		actualInstArea.append( opcode + "," + operand1 + "," + operand2 + "\n" );
	}

	private void updateClockCycle( int index ) {

		for(int j=0; j<pipeline.max; j++) {				
			if( pipeline.pipe[index].get(j)!="N" ) {
				cc[index][j].setText( pipeline.pipe[index].get(j) );

				if( pipeline.pipe[index].get(j)=="F" )
					cc[index][j].setBackground( new Color( 130, 255, 130 ) );
				if( pipeline.pipe[index].get(j)=="D" )
					cc[index][j].setBackground( new Color( 215, 160, 215 ) );
				if( pipeline.pipe[index].get(j)=="E" )
					cc[index][j].setBackground( new Color( 140, 140, 255 ) );
				if( pipeline.pipe[index].get(j)=="S" )
					cc[index][j].setBackground( new Color( 255, 255, 140 ) );
			}
		}
	}

	private void updatePipeline( int index ) {

		ccLabel.setText( "Clock Cycle: " + (index+1) );

		for(int j=0; j<3; j++) {
			pp[0][j].setBackground( Color.WHITE );
			pp[1][j].setText( "" );
		}		

		for(int i=0; i<lineCtr; i++) {
			if( pipeline.pipe[i].get(index)=="F" ) {
				pp[0][0].setBackground( new Color( 130, 255, 130 ) );
				pp[1][0].setText( instArray[i][0] + "," + instArray[i][1] + "," + instArray[i][2] );
			}
			if( pipeline.pipe[i].get(index)=="D" ) {
				pp[0][1].setBackground( new Color( 215, 160, 215 ) );
				pp[1][1].setText( instArray[i][0] + "," + instArray[i][1] + "," + instArray[i][2] );
			}
			if( pipeline.pipe[i].get(index)=="E" ) {
				pp[0][2].setBackground( new Color( 140, 140, 255 ) );
				pp[1][2].setText( instArray[i][0] + "," + instArray[i][1] + "," + instArray[i][2] );
			}
		}		
	}

	private void updateRegisters() {

		registersArea.setText( "" );

		for(int i=18; i<instructionSet.map.length; i++) {
			registersArea.append( instructionSet.map[i][0] + " = " + instructionSet.map[i][1] + "\n");
		}
	}

	public void actionPerformed( ActionEvent e ) {

		if( e.getSource()==next ) {
			if( instructionArea.getText().isEmpty() ) {
				JOptionPane.showMessageDialog( null, "Instruction area empty!" );
			}
			else {
				if( limit1<lineCtr ) {
					updateClockCycle( nextCtr );				
					limit1++;
				}

				if( nextCtr<limit ) {
					updatePipeline( nextCtr );
					nextCtr++;
				}

				if( nextCtr==limit )
					next.setEnabled( false );
			}			
		}

		if( e.getSource()==addInst ) {
			if( inputField.getText().isEmpty() || operandCtr<2 ) {
				JOptionPane.showMessageDialog( null, "Input field empty or incomplete!" );
			}
			else {
				instructionArea.append( inputField.getText()+"\n" );
				tempInst = "";
				inputField.setText( "" );
				
				for(int j=0; j<opcodeList.length; j++)
					opcodeList[j].setEnabled( true );
				for(int j=0; j<registerList.length; j++)
					registerList[j].setEnabled( false );

				operandCtr = 0;
				lineCtr++;
			}
		}

		if( e.getSource()==clear ) {
			tempInst = "";
			inputField.setText( tempInst );

			for(int j=0; j<opcodeList.length; j++)
				opcodeList[j].setEnabled( true );
			for(int j=0; j<registerList.length; j++)
					registerList[j].setEnabled( false );

			operandCtr = 0;
		}

		if( e.getSource()==process ) {
			if( instructionArea.getText().isEmpty() ) {
				JOptionPane.showMessageDialog( null, "Instruction area empty!" );
			}
			else {
				instructionArea.setVisible( false );
				process.setVisible( false );
				reset.setVisible( false );
				initInstAreaPanel();
				initClockCyclePanel();
				startPipeline();
			}
		}

		if( e.getSource()==reset ) {
			instructionArea.setText( "" );
			machineCodeArea.setText( "" );
			actualInstArea.setText( "" );
			instructionSet.initMap( "machineCodes.txt" );
			updateRegisters();

			for(int j=0; j<registerList.length; j++)
				registerList[j].setEnabled( false );

			lineCtr = 0;
		}

		for(int i=0; i<opcodeList.length; i++) {
			if( e.getSource()==opcodeList[i] ) {
				for(int j=0; j<opcodeList.length; j++) {
					opcodeList[j].setEnabled( false );
				}
				
				tempInst = tempInst + opcodeList[i].getText() + ",";
				inputField.setText( tempInst );

				for(int j=0; j<registerList.length; j++) {
					registerList[j].setEnabled( true );
				}
				break;
			}
		}

		if( operandCtr<2 ) {
			for(int i=0; i<registerList.length; i++) {
				if( e.getSource()==registerList[i] ) {
					if( registerList[i].getText()=="IMMEDIATE" ) {
						if( operandCtr==0 ) {
							JOptionPane.showMessageDialog( null, "Cannot put immediate value as destination!" );
						}
						else {
							for(int j=0; j<registerList.length; j++) {
								registerList[j].setEnabled( false );
							}

							String inputValue = JOptionPane.showInputDialog( "Please input a value: " );
							tempInst = tempInst + inputValue;

							inputField.setText( tempInst );
							operandCtr++;
						}
					}
					else {
						if( operandCtr==0 ) {
							tempInst = tempInst + registerList[i].getText() + ",";
						}
						else {
							for(int j=0; j<registerList.length; j++) {
								registerList[j].setEnabled( false );
							}

							tempInst = tempInst + registerList[i].getText();
						}

						inputField.setText( tempInst );
						operandCtr++;
					}
				}
			}
		}
	}

	public static void main( String[] args ) {

		SimpleComputer sc = new SimpleComputer();
	}
}