import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;



public class SimpleComputer implements ActionListener{
	
	protected static InstructionSet instructionSet = new InstructionSet();
	private static JFrame frame;
	private static JPanel topPanel, centerPanel, opcodePanel, registerPanel, inputPanel, registersPanel, cyclePanel, pipelinePanel, codePanel;
	private static JButton[] opcodeList = new JButton[18];
	private static JButton[] registerList = new JButton[11];
	private static JButton addInst, process, reset, clear;
	private static JLabel opcodeLabel, registerLabel, inputLabel, cycleLabel, registersLabel, pipelineLabel, codeLabel;
	private static JTextField inputField;
	private static JTextArea instructionArea, registersArea, pipelineArea, machineCodeArea, actualInstArea;
	private static int operandCtr=0, lineCtr=0;
	private static String tempInst="";
	private static String[][] instArray, machineCodes;
	private static String[] clockCycle;
	


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

		initTopPanel();
		initCenterPanel();

		initFrame( 1000, 700 );
		frame.setVisible( true );
	}

	private void initTopPanel() {

		topPanel.setPreferredSize( new Dimension( 1000, 140 ) );
		topPanel.setBackground( Color.RED );
		topPanel.setLayout( new FlowLayout() );

		initOpcodePanel();
		initRegisterPanel();
		initInputPanel();
		
		frame.add( topPanel, BorderLayout.NORTH );
	}

	private void initCenterPanel() {

		centerPanel.setPreferredSize( new Dimension( 1000, 550 ) );
		centerPanel.setBackground( Color.BLUE );
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
		opcodePanel.setBackground( Color.GREEN );

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
		registerPanel.setBackground( Color.YELLOW );

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
		inputPanel.setBackground( Color.GRAY );

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

	private void initCyclePanel() {

		cyclePanel.setPreferredSize( new Dimension( 490, 280 ) );
		cyclePanel.setBackground( Color.PINK );

		cycleLabel = new JLabel( "CYCLES" );
		cycleLabel.setFont( new Font( "Dialog", Font.BOLD, 20 ) );
		cycleLabel.setPreferredSize( new Dimension( 490, 20 ) );
		cyclePanel.add( cycleLabel );

		instructionArea = new JTextArea( 13, 43 );
		instructionArea.setEditable( false );
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
		registersPanel.setBackground( Color.MAGENTA );

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

	private void initPipelinePanel() {

		pipelinePanel.setPreferredSize( new Dimension( 490, 230 ) );
		pipelinePanel.setBackground( Color.LIGHT_GRAY );

		pipelineLabel = new JLabel( "PIPELINE" );
		pipelineLabel.setFont( new Font( "Dialog", Font.BOLD, 20 ) );
		pipelineLabel.setPreferredSize( new Dimension( 490, 20 ) );
		pipelinePanel.add( pipelineLabel );

		pipelineArea = new JTextArea( 12, 43 );
		pipelineArea.setEditable( false );
		pipelinePanel.add( pipelineArea );

		centerPanel.add( pipelinePanel );
	}

	private void initCodePanel() {

		codePanel.setPreferredSize( new Dimension( 490, 230 ) );
		codePanel.setBackground( Color.CYAN );

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
		clockCycle = new String[lineCtr];

		String[] temp = instructionArea.getText().split( "\n" );

		for(int i=0; i<temp.length; i++)
			instArray[i] = temp[i].split( "," );
		
		for(int i=0; i<(instArray.length); i++) {
			updateCodes( instArray[i][0], instArray[i][1], instArray[i][2] );
			instructionSet.doOperation( instArray[i][0], instArray[i][1], instArray[i][2] );
			updateRegisters();
		}

		startClockCycle();
		updatePipeline();
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

	private void startClockCycle() {

		for(int i=0; i<clockCycle.length; i++)
			clockCycle[i] = "";
	}

	private void updatePipeline() {

	}

	private void updateRegisters() {

		registersArea.setText( "" );

		for(int i=18; i<instructionSet.map.length; i++) {
			registersArea.append( instructionSet.map[i][0] + " = " + instructionSet.map[i][1] + "\n");
		}
	}

	public void actionPerformed( ActionEvent e ) {

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
				startPipeline();
			}
		}

		if( e.getSource()==reset ) {
			instructionArea.setText( "" );
			pipelineArea.setText( "" );
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

//