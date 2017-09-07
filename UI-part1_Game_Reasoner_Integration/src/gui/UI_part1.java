/**
 * @author Xiaofei Zhou
 * She is a java beginner. So some parts of the code may be not very smart.
 * Have any problem with the code? Feel free to contact with zhouxf14@163.com
 */

package gui;

import edu.usc.ict.vhmsg.MessageEvent;
import edu.usc.ict.vhmsg.MessageListener;
import edu.usc.ict.vhmsg.VHMsg;
import edu.usc.ict.vhmsg.Config;
import edu.usc.ict.vhmsg.main.VhmsgSender;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import java.io.File;
import jxl.Sheet;
import jxl.Workbook;

public class UI_part1 extends JFrame implements MessageListener{
	static final int WIDTH=1600;
	static final int HEIGHT=900;
	JFrame jf;

	static final int YES = 1;
	static final int MAYBE = 2;
	static final int NO = 3;
	static final int UNKNOWN = 0;

	static final int USE = 1;
	static final int NOT_USE = 2;

	static final int VP = 0;
	static final int PLAYER_D = 1;
	static final int PLAYER_W = 2;
	static final int PLAYER_A = 3;

	/**
	 * Variables deal with message sending and receiving
	 */
	static VHMsg vhmsgSubscriber;
	static VhmsgSender vrValueOfDieSender;
	static VhmsgSender vrAllGearCardsSender;
	static VhmsgSender vrCurrentPlayerSender;
	static VhmsgSender vrChoicesForThreatSender;
	static VhmsgSender vrChosenThreatSender;
	static VhmsgSender vrVPQuestionTokenSender;
	static VhmsgSender vrGamePhaseSender;
	static VhmsgSender vrQAWithSkillDecisionSender;
	static VhmsgSender vrSuggestedSkillSender;
	static VhmsgSender vrSuggestedCardSender;
	static VhmsgSender vrFinalChosenCardsSender;
	static VhmsgSender vrRoomResultSender;
	static VhmsgSender vrAntidoteDieSender;
	static VhmsgSender vrOthersAgreementSender;

	/**
	 * Variables for message sending
	 */
	static String[] gamePhaseNames = {"Initialization", "GameMove", "RoomSelection", "QuestionAndAnswer", "Discussion", "ResultAnnouncing"};
	static String[] skillNames = {"fight", "friend", "see", "loves animals", "block", "hack", "fix", "fast"};
	static String[] cardNames = {"", "abby", "april", "amy", "cd", "scientists", "name tag", "ben", "chris", "cloaking device", "flux helmet", "grace", "good shoes", "hammer", "helmet", "lab mannaul", "liz", "luke", "magaphone", "mike", "nico", "noah", "pocket computer", "ray", "sam", "scanner", "scott", "tara", "vera"};

	/**
	 * 	variables for game representations
	 */
	static String formerGamePhase; //to record the last game phase
	static String currentGamePhase;//to record the current game phase
	static String currentPlayer;//to record the current player  
	static int dieValue;//to record the result of rolling a die
	static String roomOptions;//to record the result of three threat rooms for players to choose from
	static String roomSelected;//to record the threat room players decide on
	static String finalDecision1;//to record one of the gear cards player finally decide on
	static String finalDecision2;//to record one of the gear cards player finally decide on
	static String finalDecision3;//to record one of the gear cards player finally decide on
	static boolean allAgree;
	static String currentToken;//to record the token which is currently used in question asking 
	static String currentFilling;// to record the filling which is currently generated in question asking 
	static String currentAnswer; //to record the answer to the current question asked by players
	static String currentSpeaker;//to record who is the current speaker        
	static String gearCard1A;//to record player A's gear cards
	static String gearCard2A;
	static String gearCard3A;
	static String gearCard4A;
	static String gearCard1W;//to record player W's gear cards
	static String gearCard2W;
	static String gearCard3W;
	static String gearCard4W;
	static String gearCard1D;//to record player D's gear cards
	static String gearCard2D;
	static String gearCard3D;
	static String gearCard4D;
	static String gearCard1VP;//to record player VP's gear cards
	static String gearCard2VP;
	static String gearCard3VP;
	static String gearCard4VP;
	static String token1VP;//to record virtual peer's battery tokens
	static String token2VP;
	static String token3VP;
	//the following variables to record other players' battery tokens are commented because WoZ don't need to input them at this stage
	//	static String token1A;
	//	static String token2A;
	//	static String token3A;
	//	static String token1W;
	//	static String token2W;
	//	static String token3W;
	//	static String token1D;
	//	static String token2D;
	//	static String token3D;
	static String filling1VP;//to record fillings used by virtual player
	static String filling2VP;
	static String filling3VP;
	static String filling1A;//to record fillings used by player A
	static String filling2A;
	static String filling3A;
	static String filling1W;//to record fillings used by player W
	static String filling2W;
	static String filling3W;
	static String filling1D;//to record fillings used by player D
	static String filling2D;
	static String filling3D;
	static String answer1VP;//to record answers virtual player get
	static String answer2VP;
	static String answer3VP;
	static String answer1A;//to record answers player A get
	static String answer2A;
	static String answer3A;
	static String answer1W;//to record answers player W get
	static String answer2W;
	static String answer3W;
	static String answer1D;//to record answers player D get
	static String answer2D;
	static String answer3D;
	static int potentialSkillsNum;//the number of skills automatically linked with keywords in a question
	static String contentToVP;//what other players directly say to virtual player
	static boolean roomResult;//whether the players win the room or not
	static boolean antidoteResult;//whether the players get the antidote or not	
	static String decisionSkillsVP;//one of the virtual player's decision on skills 
	static String decisionSkillsA;//one of the player A's decision on skills
	static String decisionSkillsW;//one of the player W's decision on skills
	static String decisionSkillsD;//one of the player D's decision on skills
	static String decisionCardsVP;//one of the virtual player's decision on cards
	static String decisionCardsA;//one of the player A's decision on cards
	static String decisionCardsW;//one of the player W's decision on cards
	static String decisionCardsD;//one of the player D's decision on cards

	/**
	 * UI components in the panel to select game phase and check which is the current game phase 
	 */
	static JPanel panelGamePhase;
	static JRadioButton radioButtonInitialization;
	static JRadioButton radioButtonGameMove;
	static JRadioButton radioButtonRoomSelection;
	static JRadioButton radioButtonQuestionAndAnswer;
	static JRadioButton radioButtonDiscussion;
	static JRadioButton radioButtonResultAnnouncing;
	/**
	 * UI components in the panel to select current player
	 */
	static JPanel panelCurrentPlayer;	
	static JRadioButton radioButtonPlayerA;
	static JRadioButton radioButtonPlayerW;
	static JRadioButton radioButtonPlayerD;
	static JRadioButton radioButtonPlayerVP;
	/**
	 * Navigation input text field used to jump to corresponding player's panel
	 */
	static JTextField textFieldNavigation;
	/**
	 * UI components in each player's own panel which act as the second way to select current player
	 */
	static JRadioButton radioButtonPlayerA1;
	static JRadioButton radioButtonPlayerW1;
	static JRadioButton radioButtonPlayerD1;
	static JRadioButton radioButtonPlayerVP1;
	/**
	 * UI components to select a die value
	 */
	static JPanel panelDieValue;//panel to select a die value
	static JRadioButton radioButtonDieValue1;
	static JRadioButton radioButtonDieValue2;
	static JRadioButton radioButtonDieValue3;
	static JRadioButton radioButtonDieValue4;
	static JRadioButton radioButtonDieValue5;
	static JRadioButton radioButtonDieValue6;
	/**
	 * UI components in the panel to input threat room options and threat room selected
	 */
	static JPanel panelRoomSelection;
	static JTextField textFieldRoomOptions;
	static JTextField textFieldRoomSelected;
	/**
	 * UI components in the panel to input other players' verbal behaviors directly to virtual player
	 */
	static JPanel panelBehaviorsToVP;
	static JTextField textFieldSpeaker;
	static String behaviorsType;
	/**
	 * UI components in the panel to input final decision
	 */
	static JPanel panelFinalDecision;
	static JTextField textFieldFinal1;
	static JTextField textFieldFinal2;
	static JTextField textFieldFinal3;
	static UI_skills panelSkillsDecisionGenerationVP=new UI_skills();

	/**
	 * UI components to update the results of room and antidote
	 */
	static JPanel panelResult;
	static JCheckBox checkRoomWin;
	static JCheckBox checkRoomLose;
	static JCheckBox checkAntidoteWin;
	static JCheckBox checkAntidoteLose;
	/**
	 * UI components in the panel of player A
	 */
	static JPanel panelPlayerA;
	//	static JTextField textFieldTokenCode1A;
	//	static JTextField textFieldTokenCode2A;
	//	static JTextField textFieldTokenCode3A;
	static JTextField textFieldFilling1A;
	static JTextField textFieldFilling2A;
	static JTextField textFieldFilling3A;
	static JTextField textFieldAnswer1A;
	static JTextField textFieldAnswer2A;
	static JTextField textFieldAnswer3A;
	static JTextField textFieldGearCardCode1A;
	static JTextField textFieldGearCardCode2A;
	static JTextField textFieldGearCardCode3A;
	static JTextField textFieldGearCardCode4A;
	/**
	 * UI components in the panel of player W
	 */
	static JPanel panelPlayerW;
	//	static JTextField textFieldTokenCode1W;
	//	static JTextField textFieldTokenCode2W;
	//	static JTextField textFieldTokenCode3W;
	static JTextField textFieldFilling1W;
	static JTextField textFieldFilling2W;
	static JTextField textFieldFilling3W;
	static JTextField textFieldAnswer1W;
	static JTextField textFieldAnswer2W;
	static JTextField textFieldAnswer3W;
	static JTextField textFieldGearCardCode1W;
	static JTextField textFieldGearCardCode2W;
	static JTextField textFieldGearCardCode3W;
	static JTextField textFieldGearCardCode4W;
	/**
	 * UI components in the panel of player D
	 */
	static JPanel panelPlayerD;
	//	static JTextField textFieldTokenCode1D;
	//	static JTextField textFieldTokenCode2D;
	//	static JTextField textFieldTokenCode3D;
	static JTextField textFieldFilling1D;
	static JTextField textFieldFilling2D;
	static JTextField textFieldFilling3D;
	static JTextField textFieldAnswer1D;
	static JTextField textFieldAnswer2D;
	static JTextField textFieldAnswer3D;
	static JTextField textFieldGearCardCode1D;
	static JTextField textFieldGearCardCode2D;
	static JTextField textFieldGearCardCode3D;
	static JTextField textFieldGearCardCode4D;
	/**
	 * UI components in the panel of player VP
	 */
	static JPanel panelPlayerVP;
	static JTextField textFieldTokenCode1VP;
	static JTextField textFieldTokenCode2VP;
	static JTextField textFieldTokenCode3VP;
	static JLabel labelToken1VP;
	static JLabel labelToken2VP;
	static JLabel labelToken3VP;
	static JTextField textFieldFilling1VP;
	static JTextField textFieldFilling2VP;
	static JTextField textFieldFilling3VP;
	static JTextField textFieldAnswer1VP;
	static JTextField textFieldAnswer2VP;
	static JTextField textFieldAnswer3VP;
	static JTextField textFieldGearCardCode1VP;
	static JTextField textFieldGearCardCode2VP;
	static JTextField textFieldGearCardCode3VP;
	static JTextField textFieldGearCardCode4VP;

	static CommonNavi commonNavi=new CommonNavi();//common navigation
	static CheckBatteryToken checkBatteryToken=new CheckBatteryToken();//check whether all the battery tokens are used up or not
	static CheckBatteryToken2 checkBatteryToken2=new CheckBatteryToken2();//check whether all the battery tokens are used up or not
	static ClearBorder clearBorder=new ClearBorder();//Used to change panel border's color in order to highlight a certain panel when a radio button is selected
	static ClearBorder2 clearBorder2=new ClearBorder2();//Used to change panel border's color in order to highlight a certain panel when the text field is focused
	static SkillsDecisionGenerationVP skillsDecisionGenerationVP=new SkillsDecisionGenerationVP();//keylistener used to generate virtual player's decision on skills
	static SkillsDecisionGenerationVP2 skillsDecisionGenerationVP2=new SkillsDecisionGenerationVP2();//actionlistener used to generate virtual player's decision on skills
	static String sourceFile1="batteryToken_db.xls";//dataset of the mapping between battery tokens and corresponding codes
	static String sourceFile2="gearCard_db.xls";//dataset of the mapping between gear cards and corresponding codes
	static String sourceFile3="potentialSkills_db.xls";//dataset of the mapping between keywords and skills
	static String sourceFile4="final_decision.xls";//dataset of the mapping between gear cards and corresponding codes
	/**
	 * Timer to start the flashing timer to remind WoZer to select discussion phase when 2-minute question asking phase is up
	 */
	static Timer timer0=new Timer(120000, new ActionListener(){
		public void actionPerformed(ActionEvent e){
			timer1.start();
		}
	});
	/**
	 *  A timer which can make the radio button of discussion phase flash: flashing timer
	 */ 
	static Timer timer1=new Timer(500,new ActionListener(){
		public void actionPerformed(ActionEvent e){
			timer0.stop();
			//    		radioButtonDiscussion.setText("");
			if(radioButtonDiscussion.getText()=="Discussion"){
				radioButtonDiscussion.setText("Click Here");
				panelGamePhase.setBackground(Color.white);
			}
			else{
				radioButtonDiscussion.setText("Discussion");
				panelGamePhase.setBackground(Color.CYAN);
			}

		}
	});

	/**
	 * common navigation for all the text fields
	 * ctrl+a:jump to the panel of player A, which is the left player
	 * ctrl+w:jump to the panel of player W, which is the top player
	 * ctrl+d:jump to the panel of player D, which is the right player
	 * ctrl+s:jump to the panel of player S, which is virtual player
	 * ctrl+f:jump to the panel of final decision on gear cards
	 * shift+ctrl:jump to the panel of utterance said by other players directly to virtual player
	 * @author Xiaofei Zhou
	 *
	 */
	static class CommonNavi implements KeyListener
	{
		public void keyPressed(KeyEvent e)
		{
			if((e.getKeyCode()==KeyEvent.VK_A)&&(e.isControlDown()))
			{
				textFieldFilling1A.requestFocusInWindow();
			}
			if((e.getKeyCode()==KeyEvent.VK_W)&&(e.isControlDown()))
			{
				textFieldFilling1W.requestFocusInWindow();
			}
			if((e.getKeyCode()==KeyEvent.VK_D)&&(e.isControlDown()))
			{
				textFieldFilling1D.requestFocusInWindow();
			}
			if((e.getKeyCode()==KeyEvent.VK_S)&&(e.isControlDown()))
			{
				textFieldFilling1VP.requestFocusInWindow();
			}
			if((e.getKeyCode()==KeyEvent.VK_F)&&(e.isControlDown()))
			{
				textFieldFinal1.requestFocusInWindow();
			}
			if((e.isShiftDown())&&(e.isControlDown())){
				textFieldSpeaker.requestFocusInWindow();
			}
			if(e.isAltDown()){
				textFieldNavigation.requestFocusInWindow();
			}
		}
		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
		}
		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
		}
	}

	/**
	 * This is a key listener which is used to check up whether all the battery tokens of all four players are used up or not.
	 */
	static class CheckBatteryToken implements KeyListener{
		public void keyPressed(KeyEvent e){
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				if((answer1A.isEmpty()==false)&&(answer2A.isEmpty()==false)&&(answer3A.isEmpty()==false)&&(answer1W.isEmpty()==false)&&(answer2W.isEmpty()==false)&&(answer3W.isEmpty()==false)&&(answer1D.isEmpty()==false)&&(answer1D.isEmpty()==false)&&(answer1D.isEmpty()==false)&&(answer2D.isEmpty()==false)&&(answer3D.isEmpty()==false)&&(answer1VP.isEmpty()==false)&&(answer2VP.isEmpty()==false)&&(answer3VP.isEmpty()==false)){
					timer1.start();
				}
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}
	}
	/**
	 * This is an action listener which is used to check up whether all the battery tokens of all four players are used up or not.
	 */
	static class CheckBatteryToken2 implements ActionListener{
		public void actionPerformed(ActionEvent e){
				if((answer1A.isEmpty()==false)&&(answer2A.isEmpty()==false)&&(answer3A.isEmpty()==false)&&(answer1W.isEmpty()==false)&&(answer2W.isEmpty()==false)&&(answer3W.isEmpty()==false)&&(answer1D.isEmpty()==false)&&(answer1D.isEmpty()==false)&&(answer1D.isEmpty()==false)&&(answer2D.isEmpty()==false)&&(answer3D.isEmpty()==false)&&(answer1VP.isEmpty()==false)&&(answer2VP.isEmpty()==false)&&(answer3VP.isEmpty()==false)){
					timer1.start();
				}
		}
	}

	/**
	 * keylistener to generate virtual peer's decision on skills based on the keywords in questions.
	 * First scan the keywords which are input by WoZer to check whether they exist in the sourcefile1 and if exist, then output skills linked with existing keywords.
	 * @author Xiaofei
	 *
	 */
	static class SkillsDecisionGenerationVP implements KeyListener
	{
		public void keyPressed(KeyEvent e)
		{
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				// check keyword
				try{
					UI_skills.friend.setSelected(false);
					UI_skills.fast.setSelected(false);
					UI_skills.fight.setSelected(false);
					UI_skills.see.setSelected(false);
					UI_skills.block.setSelected(false);
					UI_skills.lovesAnimals.setSelected(false);
					UI_skills.hack.setSelected(false);
					UI_skills.fix.setSelected(false);
					UI_skills.friendN.setSelected(false);
					UI_skills.fastN.setSelected(false);
					UI_skills.fightN.setSelected(false);
					UI_skills.seeN.setSelected(false);
					UI_skills.blockN.setSelected(false);
					UI_skills.lovesAnimalsN.setSelected(false);
					UI_skills.hackN.setSelected(false);
					UI_skills.fixN.setSelected(false);
					Workbook book=Workbook.getWorkbook(new File(sourceFile3));
					Sheet sheet=book.getSheet(0);
					int rows=sheet.getRows();
					int cols=sheet.getColumns();
					for (int z=1;z<rows;z++){
						String key;
						key=sheet.getCell(0, z).getContents().trim();
						if (currentFilling.contains(key)){
							for (int i=1;i<cols;i++){
								if(sheet.getCell(i, z).getContents().trim().equals("Friend"))
								{
									UI_skills.friend.setSelected(true);
								}
								if(sheet.getCell(i, z).getContents().trim().equals("Fight"))
								{
									UI_skills.fight.setSelected(true);
								}
								if(sheet.getCell(i, z).getContents().trim().equals("See"))
								{
									UI_skills.see.setSelected(true);
								}
								if(sheet.getCell(i, z).getContents().trim().equals("Loves Animals"))
								{
									UI_skills.lovesAnimals.setSelected(true);
								}
								if(sheet.getCell(i, z).getContents().trim().equals("Block"))
								{
									UI_skills.block.setSelected(true);
								}
								if(sheet.getCell(i, z).getContents().trim().equals("Hack"))
								{
									UI_skills.hack.setSelected(true);
								}
								if(sheet.getCell(i, z).getContents().trim().equals("Fix"))
								{
									UI_skills.fix.setSelected(true);
								}
								if(sheet.getCell(i, z).getContents().trim().equals("Fast"))
								{
									UI_skills.fast.setSelected(true);
								}
							}
						}										
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}
	}

	/**
	 * actionlistener to generate virtual peer's decision on skills based on the keywords in questions.
	 * First scan the keywords which are input by WoZer to check whether they exist in the sourcefile1 and if exist, then output skills linked with existing keywords.
	 * @author Xiaofei
	 *
	 */
	static class SkillsDecisionGenerationVP2 implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			// check keyword
			try{
				UI_skills.friend.setSelected(false);
				UI_skills.fast.setSelected(false);
				UI_skills.fight.setSelected(false);
				UI_skills.see.setSelected(false);
				UI_skills.block.setSelected(false);
				UI_skills.lovesAnimals.setSelected(false);
				UI_skills.hack.setSelected(false);
				UI_skills.fix.setSelected(false);
				UI_skills.friendN.setSelected(false);
				UI_skills.fastN.setSelected(false);
				UI_skills.fightN.setSelected(false);
				UI_skills.seeN.setSelected(false);
				UI_skills.blockN.setSelected(false);
				UI_skills.lovesAnimalsN.setSelected(false);
				UI_skills.hackN.setSelected(false);
				UI_skills.fixN.setSelected(false);
				Workbook book=Workbook.getWorkbook(new File(sourceFile3));
				Sheet sheet=book.getSheet(0);
				int rows=sheet.getRows();
				int cols=sheet.getColumns();
				for (int z=1;z<rows;z++){
					String key;
					key=sheet.getCell(0, z).getContents().trim();
					if (currentFilling.contains(key)){
						for (int i=1;i<cols;i++){
							if(sheet.getCell(i, z).getContents().trim().equals("Friend"))
							{
								UI_skills.friend.setSelected(true);
							}
							if(sheet.getCell(i, z).getContents().trim().equals("Fight"))
							{
								UI_skills.fight.setSelected(true);
							}
							if(sheet.getCell(i, z).getContents().trim().equals("See"))
							{
								UI_skills.see.setSelected(true);
							}
							if(sheet.getCell(i, z).getContents().trim().equals("Loves Animals"))
							{
								UI_skills.lovesAnimals.setSelected(true);
							}
							if(sheet.getCell(i, z).getContents().trim().equals("Block"))
							{
								UI_skills.block.setSelected(true);
							}
							if(sheet.getCell(i, z).getContents().trim().equals("Hack"))
							{
								UI_skills.hack.setSelected(true);
							}
							if(sheet.getCell(i, z).getContents().trim().equals("Fix"))
							{
								UI_skills.fix.setSelected(true);
							}
							if(sheet.getCell(i, z).getContents().trim().equals("Fast"))
							{
								UI_skills.fast.setSelected(true);
							}
						}
//						return;
					}										
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}

	/**
	 * actionlistener used to clear the former highlight border of panel and also used to stop timer0 if there is an error in the operation
	 * @author Xiaofei Zhou
	 *
	 */
	static class ClearBorder implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(formerGamePhase.equals("GameMove")){
				panelCurrentPlayer.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			}
			if(formerGamePhase.equals("RoomSelection")){
				panelRoomSelection.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			}
			if(formerGamePhase.equals("FinalDecision")){
				panelFinalDecision.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			}
			if(formerGamePhase.equals("QuestionAndAnswer")){
				panelPlayerA.setBorder(BorderFactory.createLineBorder(Color.WHITE));
				panelPlayerW.setBorder(BorderFactory.createLineBorder(Color.WHITE));
				panelPlayerD.setBorder(BorderFactory.createLineBorder(Color.WHITE));
				panelPlayerVP.setBorder(BorderFactory.createLineBorder(Color.WHITE));
				timer0.stop();
			}
			if(formerGamePhase.equals("ResultAnnouncing")){
				panelResult.setBorder(BorderFactory.createLineBorder(Color.white));
			}
		}
	}

	/**
	 * focuslistener used to clear the former highlight border of panel and also used to stop timer0 if there is an error in the operation
	 * @author Xiaofei Zhou
	 */
	static class ClearBorder2 implements FocusListener{
		public void focusGained(FocusEvent e){
			if(formerGamePhase.equals("Initialization")){
				panelCurrentPlayer.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			}
			if(formerGamePhase.equals("GameMove")){
				panelDieValue.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			}
			if(formerGamePhase.equals("RoomSelection")){
				panelRoomSelection.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			}
			if(formerGamePhase.equals("FinalDecision")){
				panelFinalDecision.setBorder(BorderFactory.createLineBorder(Color.WHITE));
			}
			if(formerGamePhase.equals("QustionAsking")){
				panelPlayerA.setBorder(BorderFactory.createLineBorder(Color.WHITE));
				panelPlayerW.setBorder(BorderFactory.createLineBorder(Color.WHITE));
				panelPlayerD.setBorder(BorderFactory.createLineBorder(Color.WHITE));
				panelPlayerVP.setBorder(BorderFactory.createLineBorder(Color.WHITE));
				timer0.stop();
			}
			if(formerGamePhase.equals("ResultAnnouncing")){
				panelResult.setBorder(BorderFactory.createLineBorder(Color.white));
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub

		}
	}



	/**
	 * auto complete combobox for skills
	 * @return
	 */
	public static DefaultComboBoxModel getComboBoxModelSkill(){
		DefaultComboBoxModel model=new DefaultComboBoxModel();
		model.addElement("block");
		model.addElement("fast");
		model.addElement("fight");
		model.addElement("fix");
		model.addElement("friend");
		model.addElement("hack");
		model.addElement("loves animals");
		model.addElement("see");
		return model;
	}

	/**
	 * auto complete combobox for gear cards
	 * @return
	 */
	public static DefaultComboBoxModel getComboBoxModelGearCard(){
		DefaultComboBoxModel model=new DefaultComboBoxModel();
		model.addElement("abby");
		model.addElement("april");
		model.addElement("amy");
		model.addElement("ben");
		model.addElement("cd");
		model.addElement("chris");
		model.addElement("cloaking device");
		model.addElement("flux helmet");
		model.addElement("grace");
		//		model.addElement("grace");
		model.addElement("good shoes");
		model.addElement("hammer");
		model.addElement("helmet");
		model.addElement("lab mannaul");
		model.addElement("liz");
		model.addElement("luke");
		model.addElement("magaphone");
		model.addElement("mike");
		model.addElement("name tag");
		model.addElement("nico");
		model.addElement("noah");
		model.addElement("pocket computer");
		model.addElement("ray");
		model.addElement("sam");
		model.addElement("scanner");
		model.addElement("scientists");
		model.addElement("scott");
		model.addElement("tara");
		model.addElement("vera");
		return model;
	}

	/**
	 * panel for selecting game phase
	 * @return
	 */
	private static JPanel panelGamePhase(){
		JPanel p=new JPanel();
		p.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		p.setBackground(Color.WHITE);
		ButtonGroup bg=new ButtonGroup();
		radioButtonInitialization=new JRadioButton("Initialization");
		radioButtonInitialization.addActionListener(clearBorder);
		radioButtonInitialization.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				formerGamePhase=currentGamePhase;
				if(!currentGamePhase.equals("Initialization")) {
					currentGamePhase="Initialization";
					//vrGamePhase yuhan Initialization
					sendGamePhase();
				}

			}
		});
		radioButtonGameMove=new JRadioButton("Game Move");
		radioButtonGameMove.addActionListener(clearBorder);
		radioButtonGameMove.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				formerGamePhase=currentGamePhase;
				currentGamePhase="GameMove";
				//vrGamePhase yuhan GameMove
				sendGamePhase();
				panelCurrentPlayer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			}
		});
		radioButtonRoomSelection=new JRadioButton("Room Selection");
		radioButtonRoomSelection.addActionListener(clearBorder);
		radioButtonRoomSelection.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				formerGamePhase=currentGamePhase;
				currentGamePhase="RoomSelection";
				//vrGamePhase yuhan RoomSelection
				sendGamePhase();
				panelRoomSelection.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			}
		});
		radioButtonQuestionAndAnswer=new JRadioButton("Q&A");
		radioButtonQuestionAndAnswer.addActionListener(clearBorder);
		radioButtonQuestionAndAnswer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				formerGamePhase=currentGamePhase;
				if(currentGamePhase.equals("QuestionAndAnswer")==false){
					currentGamePhase="QuestionAndAnswer";
					//vrGamePhase yuhan QA
					sendGamePhase();
					timer0.start();
				}
				panelPlayerA.setBorder(BorderFactory.createLineBorder(Color.black));
				panelPlayerW.setBorder(BorderFactory.createLineBorder(Color.black));
				panelPlayerD.setBorder(BorderFactory.createLineBorder(Color.black));
				panelPlayerVP.setBorder(BorderFactory.createLineBorder(Color.black));
			}
		});
		radioButtonDiscussion=new JRadioButton("Discussion");
		radioButtonDiscussion.addActionListener(clearBorder);
		radioButtonDiscussion.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				timer1.stop();
				panelGamePhase.setBackground(Color.WHITE);
				radioButtonDiscussion.setText("Discussion");
				formerGamePhase=currentGamePhase;
				currentGamePhase="Discussion";
				//vrGamePhase yuhan Discussion
				sendGamePhase();
			}
		});
		radioButtonResultAnnouncing=new JRadioButton("Result");
		radioButtonResultAnnouncing.addActionListener(clearBorder);
		radioButtonResultAnnouncing.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				formerGamePhase=currentGamePhase;
				currentGamePhase="ResultAnnouncing";
				//vrGamePhase yuhan ResultAnnouncing
				sendGamePhase();
				panelResult.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			}
		});
		bg.add(radioButtonInitialization);
		bg.add(radioButtonGameMove);
		bg.add(radioButtonRoomSelection);
		bg.add(radioButtonQuestionAndAnswer);
		bg.add(radioButtonDiscussion);
		bg.add(radioButtonResultAnnouncing);
		p.setLayout(new FlowLayout());
		p.add(radioButtonInitialization);
		p.add(radioButtonGameMove);
		p.add(radioButtonRoomSelection);
		p.add(radioButtonQuestionAndAnswer);
		p.add(radioButtonDiscussion);
		p.add(radioButtonResultAnnouncing);
		return p;
	}

	/**
	 * panel for selecting current player
	 * @return
	 */
	private static JPanel panelCurrentPlayer(){
		JPanel p=new JPanel();
		p.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		JLabel labelCurrentPlayer=new JLabel("Current");
		ButtonGroup bg=new ButtonGroup();
		radioButtonPlayerA=new JRadioButton("Left: A");
		radioButtonPlayerA.addActionListener(clearBorder);
		radioButtonPlayerA.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentPlayer="a";
				//vrCurrentPlayer yuhan
				sendCurrentPlayer();
				formerGamePhase=currentGamePhase;
				if(!currentGamePhase.equals("GameMove")) {
					currentGamePhase="GameMove";
					//vrGamePhase yuhan GameMove
					sendGamePhase();
				}
				radioButtonGameMove.setSelected(true);
				radioButtonPlayerA1.setSelected(true);
			}
		});
		radioButtonPlayerW=new JRadioButton("Top: W");
		radioButtonPlayerW.addActionListener(clearBorder);
		radioButtonPlayerW.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentPlayer="w";
				//vrCurrentPlayer yuhan
				sendCurrentPlayer();
				formerGamePhase=currentGamePhase;
				if(!currentGamePhase.equals("GameMove")) {
					currentGamePhase="GameMove";
					//vrGamePhase yuhan GameMove
					sendGamePhase();
				}
				radioButtonGameMove.setSelected(true);
				radioButtonPlayerW1.setSelected(true);
			}
		});
		radioButtonPlayerD=new JRadioButton("Right: D");
		radioButtonPlayerD.addActionListener(clearBorder);
		radioButtonPlayerD.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentPlayer="d";
				//vrCurrentPlayer yuhan
				sendCurrentPlayer();
				formerGamePhase=currentGamePhase;
				if(!currentGamePhase.equals("GameMove")) {
					currentGamePhase="GameMove";
					//vrGamePhase yuhan GameMove
					sendGamePhase();
				}
				radioButtonGameMove.setSelected(true);
				radioButtonPlayerD1.setSelected(true);
			}
		});
		radioButtonPlayerVP=new JRadioButton("VP");
		radioButtonPlayerVP.addActionListener(clearBorder);
		radioButtonPlayerVP.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentPlayer="v";
				//vrCurrentPlayer yuhan
				sendCurrentPlayer();
				formerGamePhase=currentGamePhase;
				if(!currentGamePhase.equals("GameMove")) {
					currentGamePhase="GameMove";
					//vrGamePhase yuhan GameMove
					sendGamePhase();
				}
				radioButtonGameMove.setSelected(true);
				radioButtonPlayerVP1.setSelected(true);
			}
		});
		bg.add(radioButtonPlayerA);
		bg.add(radioButtonPlayerW);
		bg.add(radioButtonPlayerD);
		bg.add(radioButtonPlayerVP);

		p.setLayout(new FlowLayout());
		p.add(labelCurrentPlayer);
		p.add(radioButtonPlayerA);
		p.add(radioButtonPlayerW);
		p.add(radioButtonPlayerD);
		p.add(radioButtonPlayerVP);
		return p;
	}

	/**
	 * panel for rolling the die, which belongs to the game move phase
	 * @return
	 */
	public static JPanel panelDieValue(){
		JPanel p=new JPanel();
		p.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		JLabel labelDieValue=new JLabel("Die");
		ButtonGroup bg=new ButtonGroup();
		radioButtonDieValue1=new JRadioButton("1");
		radioButtonDieValue1.addActionListener(clearBorder);
		radioButtonDieValue1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dieValue=1;
				//vrValueOfDie yuhan
				sendValueOfDie();
				System.out.println(dieValue);
			}
		});
		radioButtonDieValue2=new JRadioButton("2");
		radioButtonDieValue2.addActionListener(clearBorder);
		radioButtonDieValue2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dieValue=2;
				//vrValueOfDie yuhan
				sendValueOfDie();
				System.out.println(dieValue);
			}
		});
		radioButtonDieValue3=new JRadioButton("3");
		radioButtonDieValue3.addActionListener(clearBorder);
		radioButtonDieValue3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dieValue=3;
				//vrValueOfDie yuhan
				sendValueOfDie();
				System.out.println(dieValue);
			}
		});
		radioButtonDieValue4=new JRadioButton("4");
		radioButtonDieValue4.addActionListener(clearBorder);
		radioButtonDieValue4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dieValue=4;
				//vrValueOfDie yuhan
				sendValueOfDie();
				System.out.println(dieValue);
			}
		});
		radioButtonDieValue5=new JRadioButton("5");
		radioButtonDieValue5.addActionListener(clearBorder);
		radioButtonDieValue5.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dieValue=5;
				//vrValueOfDie yuhan
				sendValueOfDie();
				System.out.println(dieValue);
			}
		});
		radioButtonDieValue6=new JRadioButton("6");
		radioButtonDieValue6.addActionListener(clearBorder);
		radioButtonDieValue6.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dieValue=6;
				//vrValueOfDie yuhan
				sendValueOfDie();
				System.out.println(dieValue);
			}
		});
		bg.add(radioButtonDieValue1);
		bg.add(radioButtonDieValue2);
		bg.add(radioButtonDieValue3);
		bg.add(radioButtonDieValue4);
		bg.add(radioButtonDieValue5);
		bg.add(radioButtonDieValue6);

		p.setLayout(new FlowLayout());
		p.add(labelDieValue);
		p.add(radioButtonDieValue1);
		p.add(radioButtonDieValue2);
		p.add(radioButtonDieValue3);
		p.add(radioButtonDieValue4);
		p.add(radioButtonDieValue5);
		p.add(radioButtonDieValue6);
		return p;
	}

	/**
	 * panel for room selection
	 * @return
	 */
	private static JPanel panelRoomSelection(){
		JPanel p=new JPanel();
		p.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		JLabel labelRoomOptions=new JLabel("Options");
		JLabel labelRoomSelected=new JLabel("Selected");
		textFieldRoomOptions=new JTextField(3);
		textFieldRoomOptions.setForeground(Color.black);
		textFieldRoomSelected=new JTextField(3);
		textFieldRoomSelected.setForeground(Color.black);
		textFieldRoomOptions.addKeyListener(commonNavi);
		textFieldRoomOptions.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){
				formerGamePhase=currentGamePhase;
				if(!currentGamePhase.equals("RoomSelection")) {
					currentGamePhase="RoomSelection";
					//vrGamePhase yuhan RoomSelection
					sendGamePhase();
				}
				radioButtonRoomSelection.setSelected(true);				
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldRoomOptions.addFocusListener(clearBorder2);
		textFieldRoomOptions.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					roomOptions=textFieldRoomOptions.getText();
					//vrChoiceForThreat yuhan
					sendChoicesForThreat();
					textFieldRoomOptions.setForeground(Color.BLUE);
					textFieldRoomSelected.requestFocusInWindow();
					System.out.println(roomOptions);
				}
			}
		});
		textFieldRoomSelected.addKeyListener(commonNavi);
		textFieldRoomSelected.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					roomSelected=textFieldRoomSelected.getText();
					//vrChosenThreat yuhan
					sendChosenThreat();
					System.out.println(roomSelected);
					textFieldRoomOptions.setText("");
					//					textFieldRoomSelected.setText("");
					textFieldRoomSelected.setForeground(Color.BLUE);
					//					textFieldTokenCode1A.requestFocusInWindow();
					textFieldTokenCode1VP.requestFocusInWindow();
				}
			}
		});
		p.setLayout(new FlowLayout());
		p.add(labelRoomOptions);
		p.add(textFieldRoomOptions);
		p.add(labelRoomSelected);
		p.add(textFieldRoomSelected);
		return p;
	}

	/**
	 * panel for inputting the Behaviors to virtual peer
	 * @return
	 */
	private static JPanel panelBehaviorsToVP(){
		JPanel p=new JPanel();
		p.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		JLabel labelSpeaker=new JLabel("Speaker: ");
		textFieldSpeaker=new JTextField(2);
		textFieldSpeaker.setForeground(Color.black);
		JTextField textFieldContent=new JTextField(15);
		textFieldContent.setForeground(Color.black);
		JButton buttonAskForOpinion=new JButton("Opinion");
		JButton buttonAskForAvailableCardsSkills= new JButton("Available Cards/Skills");
		JButton buttonAgree=new JButton("Agree");
		JButton buttonChallenge=new JButton("Challenge");
		JButton buttonSuggestQuestionToAsk=new JButton("Suggest Question");

		buttonAskForOpinion.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				behaviorsType="buttonAskForOpinion";
				textFieldContent.setText("What do you think, virtual peer?");
				textFieldContent.setForeground(Color.blue);
			}
		});
		buttonAskForAvailableCardsSkills.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				behaviorsType="AskForAvailableCardsSkills";
				textFieldContent.setText("Do you have (cards/skills), virtual peer?");
				textFieldContent.setForeground(Color.blue);
			}
		});
		buttonAgree.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				behaviorsType="Agree";
				textFieldContent.setText("You are right, virtual peer.");
				textFieldContent.setForeground(Color.blue);
			}
		});
		buttonChallenge.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				behaviorsType="Challenge";
				textFieldContent.setText("I don't think so, virtual peer.");
				textFieldContent.setForeground(Color.blue);
			}
		});
		buttonSuggestQuestionToAsk.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				behaviorsType="SuggestQuestionToAskn";
				textFieldContent.setText("Can you ask about (question), virtual peer?");
				textFieldContent.setForeground(Color.blue);
			}
		});
		textFieldSpeaker.addKeyListener(commonNavi);
		textFieldSpeaker.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					currentSpeaker=textFieldSpeaker.getText();
					textFieldSpeaker.setForeground(Color.BLUE);
					textFieldContent.requestFocusInWindow();
				}
			}
		});
		textFieldContent.addKeyListener(commonNavi);
		textFieldContent.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					contentToVP=textFieldContent.getText();
					textFieldSpeaker.setText("");
					textFieldContent.setText("");
				}
			}
		});
		Box box0=Box.createVerticalBox();
		Box box1=Box.createHorizontalBox();
		Box box2=Box.createHorizontalBox();
		Box box3=Box.createHorizontalBox();
		box1.add(labelSpeaker);
		box1.add(textFieldSpeaker);
		box1.add(new JLabel("  "));
		box1.add(textFieldContent);
		box2.add(buttonAgree);
		box2.add(new JLabel(" "));
		box2.add(buttonChallenge);
		box2.add(new JLabel(" "));
		box2.add(buttonAskForOpinion);
		box3.add(buttonAskForAvailableCardsSkills);
		box3.add(new JLabel(" "));
		box3.add(buttonSuggestQuestionToAsk);
		box0.add(box1);
		box0.add(new JLabel("  "));
		box0.add(box2);
		box0.add(new JLabel("  "));
		box0.add(box3);
		p.add(box0);
		return p;
	}

	/**
	 * panel for final decision
	 * @return
	 */
	private static JPanel panelFinalDecision(){
		JPanel p=new JPanel();
		p.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		p.setLayout(new FlowLayout());
		//		JLabel labelFinalDecision=new JLabel("Final:");
		textFieldFinal1=new JTextField(3);
		textFieldFinal1.setForeground(Color.BLACK);
		textFieldFinal2=new JTextField(3);
		textFieldFinal2.setForeground(Color.BLACK);
		textFieldFinal3=new JTextField(3);
		textFieldFinal3.setForeground(Color.BLACK);
		JButton buttonCall=new JButton("Call it");
		JButton buttonAllAgree=new JButton("All Agree");
		buttonCall.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					textFieldFinal1.setForeground(Color.BLUE);
					textFieldFinal2.setForeground(Color.BLUE);
					textFieldFinal3.setForeground(Color.BLUE);
				}
			}
		});
		buttonAllAgree.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				allAgree=true;
				//vrOthersAgreement yuhan
				sendOthersAgreement();
				textFieldFinal1.setForeground(Color.BLUE);
				textFieldFinal2.setForeground(Color.BLUE);
				textFieldFinal3.setForeground(Color.BLUE);
			}
		});
		textFieldFinal1.addKeyListener(commonNavi);
		textFieldFinal1.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					String gearCardCode=textFieldFinal1.getText().trim();
					//vrFinalChosenCards yuhan
					//need textFieldFinal2 and textFieldFinal3 update
					sendFinalChosenCards();
					try
					{
						Workbook book=Workbook.getWorkbook(new File(sourceFile4));
						Sheet sheet=book.getSheet(0);
						int rows=sheet.getRows();
						int cols=sheet.getColumns();
						for (int z=1;z<rows;z++)
						{
							String code;
							code=sheet.getCell(0, z).getContents().trim();
							if (gearCardCode.equals(code))
							{
								finalDecision1=sheet.getCell(1,z).getContents().trim();
								textFieldFinal1.setText(finalDecision1);
							}										
						}
						textFieldFinal2.requestFocusInWindow();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}

				if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					textFieldFinal2.requestFocusInWindow();
				}
			}
		});
		textFieldFinal2.addKeyListener(commonNavi);
		textFieldFinal2.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					String gearCardCode=textFieldFinal2.getText().trim();
					//vrFinalChosenCards yuhan
					//need textFieldFinal2 and textFieldFinal3 update
					sendFinalChosenCards();
					try
					{
						Workbook book=Workbook.getWorkbook(new File(sourceFile4));
						Sheet sheet=book.getSheet(0);
						int rows=sheet.getRows();
						int cols=sheet.getColumns();
						for (int z=1;z<rows;z++)
						{
							String code;
							code=sheet.getCell(0, z).getContents().trim();
							if (gearCardCode.equals(code))
							{
								finalDecision2=sheet.getCell(1,z).getContents().trim();
								textFieldFinal2.setText(finalDecision2);
							}										
						}
						textFieldFinal3.requestFocusInWindow();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT){
					textFieldFinal1.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					textFieldFinal3.requestFocusInWindow();
				}
			}
		});
		textFieldFinal3.addKeyListener(commonNavi);
		textFieldFinal3.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					String gearCardCode=textFieldFinal3.getText().trim();
					//vrFinalChosenCards yuhan
					//need textFieldFinal2 and textFieldFinal3 update
					sendFinalChosenCards();
					try
					{
						Workbook book=Workbook.getWorkbook(new File(sourceFile4));
						Sheet sheet=book.getSheet(0);
						int rows=sheet.getRows();
						int cols=sheet.getColumns();
						for (int z=1;z<rows;z++)
						{
							String code;
							code=sheet.getCell(0, z).getContents().trim();
							if (gearCardCode.equals(code))
							{
								finalDecision3=sheet.getCell(1,z).getContents().trim();
								textFieldFinal3.setText(finalDecision3);
							}										
						}
						buttonCall.requestFocusInWindow();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT){
					textFieldFinal2.requestFocusInWindow();
				}
			}
		});
		//		p.add(labelFinalDecision);
		p.add(textFieldFinal1);
		p.add(textFieldFinal2);
		p.add(textFieldFinal3);
		p.add(buttonCall);
		p.add(buttonAllAgree);
		return p;
	}
	/**
	 * panel of cheatsheet which is for WoZer's convenience to input virtual player's decisions on skills
	 * @return
	 */
	private static JPanel panelCheatSheet(){
		JPanel p=new JPanel();
		p.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		Object[][] content={
				{"fight","beat","big","bad"},
				{"friend","help","people","lonely"},
				{"see","dark","notice","hide"},
				{"loves","animals","good","with","animals"},
				{"block","keep","from","danger"},
				{"hack","good","with","computers"},
				{"fix","broken","unwork","machine"},
				{"fast","run","danger","no time"},	
		};
		String[] title={"Skill","Keyword1","Keyword2","Keyword3"};
		JTable tableCheatSheet=new JTable(content,title);
		p.add(tableCheatSheet);
		return p;
	}

	/**
	 * panel for player A
	 * @return
	 */
	private static JPanel panelPlayerA(){
		JPanel p=new JPanel();		
		//		JLabel labelPlayer=new JLabel("Left Player(A)");
		radioButtonPlayerA1=new JRadioButton("Left Player(A)");
		radioButtonPlayerA1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentPlayer="a";
				//vrCurrentPlayer yuhan
				sendCurrentPlayer();
				formerGamePhase=currentGamePhase;
				if(!currentGamePhase.equals("GameMove")) {
					currentGamePhase="GameMove";
					//vrGamePhase yuhan GameMove
					sendGamePhase();
				}
				radioButtonGameMove.setSelected(true);
				radioButtonPlayerA.setSelected(true);
			}
		});
		radioButtonPlayerA1.addActionListener(clearBorder);
		JLabel labelGearCard=new JLabel("Gear");
		JLabel labelGearCard1A=new JLabel("_______________");
		labelGearCard1A.setFont(new Font(Font.DIALOG, Font.BOLD,10));
		JLabel labelGearCard2A=new JLabel("_______________");
		labelGearCard2A.setFont(new Font(Font.DIALOG, Font.BOLD,10));
		JLabel labelGearCard3A=new JLabel("_______________");
		labelGearCard3A.setFont(new Font(Font.DIALOG, Font.BOLD,10));
		JLabel labelGearCard4A=new JLabel("_______________");
		labelGearCard4A.setFont(new Font(Font.DIALOG, Font.BOLD,10));
		JLabel labelTokenCode=new JLabel("Code");
		JLabel labelToken=new JLabel("Token");
		JLabel labelFilling=new JLabel("Filling");
		JLabel labelAnswer=new JLabel("Answer");
		//		JLabel labelToken1A=new JLabel("Token1");
		//		JLabel labelToken2A=new JLabel("Token2");
		//		JLabel labelToken3A=new JLabel("Token3");
		JLabel labelSkill=new JLabel("   Skill   ");
		JLabel labelCard=new JLabel("   Card   ");
		textFieldGearCardCode1A=new JTextField(1);
		textFieldGearCardCode2A=new JTextField(1);
		textFieldGearCardCode3A=new JTextField(1);
		textFieldGearCardCode4A=new JTextField(1);
		//		textFieldTokenCode1A=new JTextField(1);
		//		textFieldTokenCode2A=new JTextField(1);
		//		textFieldTokenCode3A=new JTextField(1);
		textFieldFilling1A=new JTextField(3);
		textFieldFilling1A.setForeground(Color.black);
		textFieldFilling2A=new JTextField(3);
		textFieldFilling2A.setForeground(Color.black);
		textFieldFilling3A=new JTextField(3);
		textFieldFilling3A.setForeground(Color.black);
		textFieldAnswer1A=new JTextField(3);
		textFieldAnswer1A.setForeground(Color.black);
		textFieldAnswer2A=new JTextField(3);
		textFieldAnswer2A.setForeground(Color.black);
		textFieldAnswer3A=new JTextField(3);
		textFieldAnswer3A.setForeground(Color.black);
		
		Object[] items1=new Object[]
				{"block","fast","fight","fix","friend","hack","loves animals","see"};
		DefaultComboBoxModel model1=getComboBoxModelSkill();
		JComboBox cmbDecisionSkillsA=new JAutoCompleteComboBox(model1);
		DefaultComboBoxModel model=getComboBoxModelGearCard();
		JComboBox cmbDecisionCardsA=new JAutoCompleteComboBox(model);
		((JTextField) cmbDecisionSkillsA.getEditor().getEditorComponent()).setText("");
		cmbDecisionSkillsA.getEditor().getEditorComponent().addKeyListener(commonNavi);
		cmbDecisionSkillsA.getEditor().getEditorComponent().addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					decisionSkillsA=((JTextField) cmbDecisionSkillsA.getEditor().getEditorComponent()).getText();
					currentSpeaker="a";
					//vrSuggestedSkill yuhan
					sendSuggestedSkill(PLAYER_A, decisionSkillsA.trim(), USE);
					System.out.println(currentSpeaker+": "+decisionSkillsA);
					((JTextField) cmbDecisionSkillsA.getEditor().getEditorComponent()).setText("");
				}
			}
		});
		Object[] items=new Object[]
				{"abby","april","amy","ben","cd","chris","cloaking device","flux helmet","grace","grace","good shoes","hammer","helmet","lab mannaul","liz","luke","magaphone","mike","name tag","nico","noah","pocket computer","ray","sam","scanner","scientists","scott","tara","vera"};
		((JTextField) cmbDecisionCardsA.getEditor().getEditorComponent()).setText("");
		cmbDecisionCardsA.getEditor().getEditorComponent().addKeyListener(commonNavi);
		cmbDecisionCardsA.getEditor().getEditorComponent().addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					decisionCardsA=((JTextField) cmbDecisionCardsA.getEditor().getEditorComponent()).getText();
					//vrSuggestedCard yuhan
					sendSuggestedCard(PLAYER_A, decisionCardsA.trim(), USE);
					currentSpeaker="a";
					System.out.println(currentSpeaker+": "+decisionCardsA);
					((JTextField) cmbDecisionCardsA.getEditor().getEditorComponent()).setText("");
				}
			}
		});
		
		JComboBox cmbDecisionNoSkillsA=new JAutoCompleteComboBox(model1);
		JComboBox cmbDecisionNoCardsA=new JAutoCompleteComboBox(model);
		((JTextField) cmbDecisionNoSkillsA.getEditor().getEditorComponent()).setText("");
		cmbDecisionNoSkillsA.getEditor().getEditorComponent().addKeyListener(commonNavi);
		cmbDecisionNoSkillsA.getEditor().getEditorComponent().addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					decisionSkillsA=((JTextField) cmbDecisionNoSkillsA.getEditor().getEditorComponent()).getText();
					currentSpeaker="a";
					//vrSuggestedSkill yuhan
					sendSuggestedSkill(PLAYER_A, decisionSkillsA.trim(), NOT_USE);
					System.out.println(currentSpeaker+": no "+decisionSkillsA);
					((JTextField) cmbDecisionNoSkillsA.getEditor().getEditorComponent()).setText("");
				}
			}
		});
		((JTextField) cmbDecisionNoCardsA.getEditor().getEditorComponent()).setText("");
		cmbDecisionNoCardsA.getEditor().getEditorComponent().addKeyListener(commonNavi);
		cmbDecisionNoCardsA.getEditor().getEditorComponent().addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					decisionCardsA=((JTextField) cmbDecisionNoCardsA.getEditor().getEditorComponent()).getText();
					currentSpeaker="a";
					//vrSuggestedCard yuhan
					sendSuggestedCard(PLAYER_A, decisionCardsA.trim(), NOT_USE);
					System.out.println(currentSpeaker+": no "+decisionCardsA);
					((JTextField) cmbDecisionNoCardsA.getEditor().getEditorComponent()).setText("");
				}
			}
		});
		
		textFieldGearCardCode1A.addKeyListener(commonNavi);
		textFieldGearCardCode1A.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent arg0){
				textFieldGearCardCode1A.selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldGearCardCode1A.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					String gearCardCode=textFieldGearCardCode1A.getText().trim();
					//vrAllGearCards yuhan need get all 16 card code
					sendAllGearCards();
					try
					{
						Workbook book=Workbook.getWorkbook(new File(sourceFile2));
						Sheet sheet=book.getSheet(0);
						int rows=sheet.getRows();
						int cols=sheet.getColumns();
						for (int z=1;z<rows;z++)
						{
							String code;
							code=sheet.getCell(0, z).getContents().trim();
							if (gearCardCode.equals(code))
							{
								gearCard1A=sheet.getCell(1,z).getContents().trim();
								labelGearCard1A.setText(gearCard1A);
								//								textFieldGearCardCode1A.setText("");
							}										
						}
						textFieldGearCardCode2A.requestFocusInWindow();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
				{
					textFieldGearCardCode2A.requestFocusInWindow();
				}
			}
		});
		textFieldGearCardCode2A.addKeyListener(commonNavi);
		textFieldGearCardCode2A.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent arg0){
				textFieldGearCardCode2A.selectAll();
				//				  this.getUIClassID().selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldGearCardCode2A.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent arg0){
				textFieldGearCardCode2A.selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldGearCardCode2A.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					String gearCardCode=textFieldGearCardCode2A.getText().trim();
					//vrAllGearCards yuhan
					sendAllGearCards();
					try
					{
						Workbook book=Workbook.getWorkbook(new File(sourceFile2));
						Sheet sheet=book.getSheet(0);
						int rows=sheet.getRows();
						int cols=sheet.getColumns();
						for (int z=1;z<rows;z++)
						{
							String code;
							code=sheet.getCell(0, z).getContents().trim();
							if (gearCardCode.equals(code))
							{
								gearCard2A=sheet.getCell(1,z).getContents().trim();
								labelGearCard2A.setText(gearCard2A);
								//								textFieldGearCardCode2A.setText("");
							}										
						}
						textFieldGearCardCode3A.requestFocusInWindow();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
				{
					textFieldGearCardCode3A.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT)
				{
					textFieldGearCardCode1A.requestFocusInWindow();
				}
			}
		});
		textFieldGearCardCode3A.addKeyListener(commonNavi);
		textFieldGearCardCode3A.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent arg0){
				textFieldGearCardCode3A.selectAll();
				//				  this.getUIClassID().selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldGearCardCode3A.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent arg0){
				textFieldGearCardCode3A.selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});

		textFieldGearCardCode3A.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					String gearCardCode=textFieldGearCardCode3A.getText().trim();
					//vrAllGearCards yuhan
					sendAllGearCards();
					try
					{
						Workbook book=Workbook.getWorkbook(new File(sourceFile2));
						Sheet sheet=book.getSheet(0);
						int rows=sheet.getRows();
						int cols=sheet.getColumns();
						for (int z=1;z<rows;z++)
						{
							String code;
							code=sheet.getCell(0, z).getContents().trim();
							if (gearCardCode.equals(code))
							{
								gearCard3A=sheet.getCell(1,z).getContents().trim();
								labelGearCard3A.setText(gearCard3A);
								//								textFieldGearCardCode3A.setText("");
							}										
						}
						textFieldGearCardCode4A.requestFocusInWindow();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
				{
					textFieldGearCardCode4A.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT)
				{
					textFieldGearCardCode2A.requestFocusInWindow();
				}
			}
		});
		textFieldGearCardCode4A.addKeyListener(commonNavi);
		textFieldGearCardCode4A.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent arg0){
				textFieldGearCardCode4A.selectAll();
				//				  this.getUIClassID().selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});

		textFieldGearCardCode4A.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					String gearCardCode=textFieldGearCardCode4A.getText().trim();
					//vrAllGearCards yuhan
					sendAllGearCards();
					try
					{
						Workbook book=Workbook.getWorkbook(new File(sourceFile2));
						Sheet sheet=book.getSheet(0);
						int rows=sheet.getRows();
						int cols=sheet.getColumns();
						for (int z=1;z<rows;z++)
						{
							String code;
							code=sheet.getCell(0, z).getContents().trim();
							if (gearCardCode.equals(code))
							{
								gearCard4A=sheet.getCell(1,z).getContents().trim();
								labelGearCard4A.setText(gearCard4A);
								//								textFieldGearCardCode4A.setText("");
							}										
						}
						textFieldGearCardCode1W.requestFocusInWindow();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT)
				{
					textFieldGearCardCode3A.requestFocusInWindow();
				}
			}
		});
		//		textFieldTokenCode1A.addKeyListener(commonNavi);
		//		textFieldTokenCode1A.addFocusListener(new FocusListener(){
		//			  public void focusGained(FocusEvent arg0){
		//				  textFieldTokenCode1A.selectAll();
		//			  }
		//
		//			@Override
		//			public void focusLost(FocusEvent e) {
		//				// TODO Auto-generated method stub
		//				
		//			}
		//		});
		//		textFieldTokenCode1A.addKeyListener(new KeyAdapter(){
		//			public void keyPressed(KeyEvent e)
		//			{
		//				if(e.getKeyCode()==KeyEvent.VK_ENTER)
		//				{
		//					String tokenCode=textFieldTokenCode1A.getText().trim();
		//					try
		//					{
		//						Workbook book=Workbook.getWorkbook(new File(sourceFile1));
		//						Sheet sheet=book.getSheet(0);
		//						int rows=sheet.getRows();
		//						int cols=sheet.getColumns();
		//						for (int z=1;z<rows;z++)
		//						{
		//							String code;
		//							code=sheet.getCell(0, z).getContents().trim();
		//							if (tokenCode.equals(code))
		//							{
		////								token1A=sheet.getCell(1,z).getContents().trim();
		////								labelToken1A.setText(token1A);
		//								//								textFieldTokenCode1A.setText("");
		//							}										
		//						}
		//						textFieldTokenCode2A.requestFocusInWindow();
		//					}
		//					catch(Exception ex)
		//					{
		//						ex.printStackTrace();
		//					}
		//				}
		//				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		//				{
		//					textFieldFilling1A.requestFocusInWindow();
		//				}
		//				if(e.getKeyCode()==KeyEvent.VK_DOWN)
		//				{
		//					textFieldTokenCode2A.requestFocusInWindow();
		//				}
		//			}
		//		});
		//		textFieldTokenCode2A.addKeyListener(commonNavi);
		//		textFieldTokenCode2A.addFocusListener(new FocusListener(){
		//			  public void focusGained(FocusEvent arg0){
		//				  textFieldTokenCode2A.selectAll();
		//			  }
		//
		//			@Override
		//			public void focusLost(FocusEvent e) {
		//				// TODO Auto-generated method stub
		//				
		//			}
		//		});
		//		textFieldTokenCode2A.addKeyListener(new KeyAdapter(){
		//			public void keyPressed(KeyEvent e)
		//			{
		//				if(e.getKeyCode()==KeyEvent.VK_ENTER)
		//				{
		//					String tokenCode=textFieldTokenCode2A.getText().trim();
		//					try
		//					{
		//						Workbook book=Workbook.getWorkbook(new File(sourceFile1));
		//						Sheet sheet=book.getSheet(0);
		//						int rows=sheet.getRows();
		//						int cols=sheet.getColumns();
		//						for (int z=1;z<rows;z++)
		//						{
		//							String code;
		//							code=sheet.getCell(0, z).getContents().trim();
		//							if (tokenCode.equals(code))
		//							{
		////								token2A=sheet.getCell(1,z).getContents().trim();
		////								labelToken2A.setText(token2A);
		//								//								textFieldTokenCode2A.setText("");
		//							}										
		//						}
		//						textFieldTokenCode3A.requestFocusInWindow();
		//					}
		//					catch(Exception ex)
		//					{
		//						ex.printStackTrace();
		//					}
		//				}
		//				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		//				{
		//					textFieldFilling2A.requestFocusInWindow();
		//				}
		//				if(e.getKeyCode()==KeyEvent.VK_DOWN)
		//				{
		//					textFieldTokenCode3A.requestFocusInWindow();
		//				}
		//				if(e.getKeyCode()==KeyEvent.VK_UP){
		//					textFieldTokenCode1A.requestFocusInWindow();
		//				}
		//			}
		//		});
		//		textFieldTokenCode3A.addKeyListener(commonNavi);
		//		textFieldTokenCode3A.addFocusListener(new FocusListener(){
		//			  public void focusGained(FocusEvent arg0){
		//				  textFieldTokenCode3A.selectAll();
		//			  }
		//
		//			@Override
		//			public void focusLost(FocusEvent e) {
		//				// TODO Auto-generated method stub
		//				
		//			}
		//		});
		//		textFieldTokenCode3A.addKeyListener(new KeyAdapter(){
		//			public void keyPressed(KeyEvent e)
		//			{
		//				if(e.getKeyCode()==KeyEvent.VK_ENTER)
		//				{
		//					String tokenCode=textFieldTokenCode3A.getText().trim();
		//					try
		//					{
		//						Workbook book=Workbook.getWorkbook(new File(sourceFile1));
		//						Sheet sheet=book.getSheet(0);
		//						int rows=sheet.getRows();
		//						int cols=sheet.getColumns();
		//						for (int z=1;z<rows;z++)
		//						{
		//							String code;
		//							code=sheet.getCell(0, z).getContents().trim();
		//							if (tokenCode.equals(code))
		//							{
		////								token3A=sheet.getCell(1,z).getContents().trim();
		////								labelToken3A.setText(token3A);
		//								//								textFieldTokenCode3A.setText("");
		//							}										
		//						}
		//					}
		//					catch(Exception ex)
		//					{
		//						ex.printStackTrace();
		//					}
		//				}
		//				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		//				{
		//					textFieldFilling3A.requestFocusInWindow();
		//				}
		//				if(e.getKeyCode()==KeyEvent.VK_UP){
		//					textFieldTokenCode2A.requestFocusInWindow();
		//				}
		//				if(e.getKeyCode()==KeyEvent.VK_DOWN){
		//					cmbDecisionSkillsA.requestFocusInWindow();
		//				}
		//			}
		//		});
		textFieldFilling1A.addKeyListener(commonNavi);
		textFieldFilling1A.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){
				formerGamePhase=currentGamePhase;
				if(currentGamePhase.equals("QuestionAndAnswer")==false){
					currentGamePhase="QuestionAndAnswer";
					//vrGamePhase yuhan QA
					sendGamePhase();
					timer0.start();
				}
				radioButtonQuestionAndAnswer.setSelected(true);
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldFilling1A.addFocusListener(clearBorder2);
		textFieldFilling1A.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					filling1A=textFieldFilling1A.getText();
					currentFilling=filling1A;
					currentSpeaker="a";
					System.out.println(currentSpeaker+" "+currentFilling);
					textFieldFilling1A.setForeground(Color.BLUE);
					UI_skills.textFieldKeyword.setText(currentFilling);
					textFieldAnswer1A.requestFocusInWindow();
				}
				//				if(e.getKeyCode()==KeyEvent.VK_LEFT){
				//					textFieldTokenCode1A.requestFocusInWindow();
				//				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					textFieldAnswer1A.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					textFieldFilling2A.requestFocusInWindow();
				}
			}
		});
		textFieldFilling2A.addKeyListener(commonNavi);
		textFieldFilling2A.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){
				formerGamePhase=currentGamePhase;
				if(currentGamePhase.equals("QuestionAndAnswer")==false){
					currentGamePhase="QuestionAndAnswer";
					//vrGamePhase yuhan QA
					sendGamePhase();
					timer0.start();
				}
				radioButtonQuestionAndAnswer.setSelected(true);
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldFilling2A.addFocusListener(clearBorder2);
		textFieldFilling2A.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					filling2A=textFieldFilling2A.getText();
					currentFilling=filling2A;
					currentSpeaker="a";
					System.out.println(currentSpeaker+" "+currentFilling);
					UI_skills.textFieldKeyword.setText(currentFilling);
					textFieldFilling2A.setForeground(Color.BLUE);
					textFieldAnswer2A.requestFocusInWindow();
				}
				//				if(e.getKeyCode()==KeyEvent.VK_LEFT){
				//					textFieldTokenCode2A.requestFocusInWindow();
				//				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					textFieldAnswer2A.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					textFieldFilling3A.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_UP){
					textFieldFilling1A.requestFocusInWindow();
				}
			}
		});
		textFieldFilling3A.addKeyListener(commonNavi);
		textFieldFilling3A.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){
				formerGamePhase=currentGamePhase;
				if(currentGamePhase.equals("QuestionAndAnswer")==false){
					currentGamePhase="QuestionAndAnswer";
					//vrGamePhase yuhan QA
					sendGamePhase();
					timer0.start();
				}
				radioButtonQuestionAndAnswer.setSelected(true);
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldFilling3A.addFocusListener(clearBorder2);
		textFieldFilling3A.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					filling3A=textFieldFilling3A.getText();
					currentFilling=filling3A;
					currentSpeaker="a";
					System.out.println(currentSpeaker+" "+currentFilling);
					UI_skills.textFieldKeyword.setText(currentFilling);
					textFieldFilling3A.setForeground(Color.BLUE);
					textFieldAnswer3A.requestFocusInWindow();
				}
				//				if(e.getKeyCode()==KeyEvent.VK_LEFT){
				//					textFieldTokenCode3A.requestFocusInWindow();
				//				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					textFieldAnswer3A.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_UP){
					textFieldFilling2A.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					cmbDecisionSkillsA.requestFocusInWindow();
				}
			}
		});
		textFieldAnswer1A.addKeyListener(commonNavi);
		textFieldAnswer1A.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					answer1A=textFieldAnswer1A.getText();
					currentAnswer=answer1A;
					System.out.println(currentSpeaker+" "+currentAnswer);
					//					currentToken=token1A;
					textFieldAnswer1A.setForeground(Color.BLUE);
					currentToken="";
					UI_skills.buttonSkill.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT){
					textFieldFilling1A.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					textFieldAnswer2A.requestFocusInWindow();
				}
			}
		});
		textFieldAnswer1A.addKeyListener(skillsDecisionGenerationVP);
		textFieldAnswer1A.addKeyListener(checkBatteryToken);
		textFieldAnswer2A.addKeyListener(commonNavi);
		textFieldAnswer2A.addKeyListener(skillsDecisionGenerationVP);
		textFieldAnswer2A.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					answer2A=textFieldAnswer2A.getText();
					currentAnswer=answer2A;
					System.out.println(currentSpeaker+" "+currentAnswer);
					//					currentToken=token2A;
					currentToken="";
					textFieldAnswer2A.setForeground(Color.BLUE);
					UI_skills.buttonSkill.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT){
					textFieldFilling2A.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					textFieldAnswer3A.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_UP){
					textFieldAnswer1A.requestFocusInWindow();
				}
			}
		});
		textFieldAnswer2A.addKeyListener(checkBatteryToken);
		textFieldAnswer3A.addKeyListener(commonNavi);
		textFieldAnswer3A.addKeyListener(skillsDecisionGenerationVP);
		textFieldAnswer3A.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					answer3A=textFieldAnswer3A.getText();
					currentAnswer=answer3A;
					System.out.println(currentSpeaker+" "+currentAnswer);
					//					currentToken=token3A;
					currentToken="";
					textFieldAnswer3A.setForeground(Color.BLUE);
					UI_skills.buttonSkill.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT){
					textFieldFilling3A.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_UP){
					textFieldAnswer2A.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					cmbDecisionSkillsA.requestFocusInWindow();
				}
			}
		});
		textFieldAnswer3A.addKeyListener(checkBatteryToken);
		JButton buttonYes1A=new JButton("Yes");
		buttonYes1A.addActionListener(skillsDecisionGenerationVP2);
		buttonYes1A.addActionListener(checkBatteryToken2);
		buttonYes1A.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling1A.getText();
				filling1A=currentFilling;
				textFieldFilling1A.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
				
				currentToken="";
				answer1A="yes";
				currentAnswer="yes";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer1A.setText(currentAnswer);
				textFieldAnswer1A.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonNo1A=new JButton("No");
		buttonNo1A.addActionListener(skillsDecisionGenerationVP2);
		buttonNo1A.addActionListener(checkBatteryToken2);
		buttonNo1A.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling1A.getText();
				filling1A=currentFilling;
				textFieldFilling1A.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
				
				currentToken="";
				answer1A="no";
				currentAnswer="no";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer1A.setText(currentAnswer);
				textFieldAnswer1A.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonNotMatter1A=new JButton("NM");
//		buttonNotMatter1A.setFont(new Font(Font.DIALOG, Font.BOLD,7));
		buttonNotMatter1A.addActionListener(skillsDecisionGenerationVP2);
		buttonNotMatter1A.addActionListener(checkBatteryToken2);
		buttonNotMatter1A.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling1A.getText();
				filling1A=currentFilling;
				textFieldFilling1A.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
				
				currentToken="";
				answer1A="not matter";
				currentAnswer="not matter";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer1A.setText(currentAnswer);
				textFieldAnswer1A.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonYes2A=new JButton("Yes");
		buttonYes2A.addActionListener(skillsDecisionGenerationVP2);
		buttonYes2A.addActionListener(checkBatteryToken2);
		buttonYes2A.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling2A.getText();
				filling2A=currentFilling;
				textFieldFilling2A.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
				
				currentToken="";
				answer2A="yes";
				currentAnswer="yes";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer2A.setText(currentAnswer);
				textFieldAnswer2A.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonNo2A=new JButton("No");
		buttonNo2A.addActionListener(skillsDecisionGenerationVP2);
		buttonNo2A.addActionListener(checkBatteryToken2);
		buttonNo2A.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling2A.getText();
				filling2A=currentFilling;
				textFieldFilling2A.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
						
				currentToken="";
				answer2A="no";
				currentAnswer="no";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer2A.setText(currentAnswer);
				textFieldAnswer2A.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonNotMatter2A=new JButton("NM");
//		buttonNotMatter2A.setFont(new Font(Font.DIALOG, Font.BOLD,7));
		buttonNotMatter2A.addActionListener(skillsDecisionGenerationVP2);
		buttonNotMatter2A.addActionListener(checkBatteryToken2);
		buttonNotMatter2A.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling2A.getText();
				filling2A=currentFilling;
				textFieldFilling2A.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);			
				
				currentToken="";
				answer2A="not matter";
				currentAnswer="not matter";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer2A.setText(currentAnswer);
				textFieldAnswer2A.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonYes3A=new JButton("Yes");
		buttonYes3A.addActionListener(skillsDecisionGenerationVP2);
		buttonYes3A.addActionListener(checkBatteryToken2);
		buttonYes3A.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling3A.getText();
				filling3A=currentFilling;
				textFieldFilling3A.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
							
				currentToken="";
				answer3A="yes";
				currentAnswer="yes";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer3A.setText(currentAnswer);
				textFieldAnswer3A.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonNo3A=new JButton("No");
		buttonNo3A.addActionListener(skillsDecisionGenerationVP2);
		buttonNo3A.addActionListener(checkBatteryToken2);
		buttonNo3A.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling3A.getText();
				filling3A=currentFilling;
				textFieldFilling3A.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
				
				currentToken="";
				answer3A="no";
				currentAnswer="no";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer3A.setText(currentAnswer);
				textFieldAnswer3A.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonNotMatter3A=new JButton("NM");
//		buttonNotMatter3A.setFont(new Font(Font.DIALOG, Font.BOLD,7));
		buttonNotMatter3A.addActionListener(skillsDecisionGenerationVP2);
		buttonNotMatter3A.addActionListener(checkBatteryToken2);
		buttonNotMatter3A.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling3A.getText();
				filling3A=currentFilling;
				textFieldFilling3A.setForeground(Color.blue);
				
				currentToken="";
				answer3A="not matter";
				currentAnswer="not matter";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer3A.setText(currentAnswer);
				textFieldAnswer3A.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});

		Box box0=Box.createVerticalBox();
		box0.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		Box box1=Box.createHorizontalBox();
		box1.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		Box box11=Box.createVerticalBox();
		Box box12=Box.createVerticalBox();
		Box box13=Box.createVerticalBox();
		Box box14=Box.createVerticalBox();
		Box box2=Box.createVerticalBox();
		box2.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		Box box21=Box.createHorizontalBox();
		Box box22=Box.createHorizontalBox();
		Box box23=Box.createHorizontalBox();
		Box box3=Box.createHorizontalBox();
		box3.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		Box box4=Box.createHorizontalBox();
		box4.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		box11.add(textFieldGearCardCode1A);
		box11.add(labelGearCard1A);
		box12.add(textFieldGearCardCode2A);
		box12.add(labelGearCard2A);
		box13.add(textFieldGearCardCode3A);
		box13.add(labelGearCard3A);
		box14.add(textFieldGearCardCode4A);
		box14.add(labelGearCard4A);
		box1.add(labelGearCard);
		box1.add(box11);
		box1.add(box12);
		box1.add(box13);
		box1.add(box14);
		box21.add(textFieldFilling1A);
		box21.add(buttonYes1A);
		box21.add(buttonNo1A);
		box21.add(buttonNotMatter1A);
		box21.add(textFieldAnswer1A);
		box22.add(textFieldFilling2A);
		box22.add(buttonYes2A);
		box22.add(buttonNo2A);
		box22.add(buttonNotMatter2A);
		box22.add(textFieldAnswer2A);
		box23.add(textFieldFilling3A);
		box23.add(buttonYes3A);
		box23.add(buttonNo3A);
		box23.add(buttonNotMatter3A);
		box23.add(textFieldAnswer3A);
		box2.add(box21);
		box2.add(box22);
		box2.add(box23);
		box3.add(labelSkill);
		box3.add(new JLabel("  "));
		box3.add(cmbDecisionSkillsA);
		box3.add(new JLabel("  "));
		box3.add(labelCard);
		box3.add(new JLabel("  "));
		box3.add(cmbDecisionCardsA);
		box4.add(new JLabel("No Skill"));
		box4.add(new JLabel("  "));
		box4.add(cmbDecisionNoSkillsA);
		box4.add(new JLabel("  "));
		box4.add(new JLabel("No Card"));
		box4.add(new JLabel("  "));
		box4.add(cmbDecisionNoCardsA);
		//		box0.add(labelPlayer);
		box0.add(radioButtonPlayerA1);
		box0.add(box1);
		box0.add(box2);
		box0.add(box3);
		box0.add(box4);
		p.add(box0);
		return p;
	}

	/**
	 * panel for player D
	 * @return
	 */
	private static JPanel panelPlayerD(){
		JPanel p=new JPanel();		
		//		JLabel labelPlayer=new JLabel("Right Player(D)");
		radioButtonPlayerD1=new JRadioButton("Right Player(D)");
		radioButtonPlayerD1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentPlayer="d";
				//vrCurrentPlayer yuhan
				sendCurrentPlayer();
				formerGamePhase=currentGamePhase;
				if(!currentGamePhase.equals("GameMove")) {
					currentGamePhase="GameMove";
					//vrGamePhase yuhan GameMove
					sendGamePhase();
				}
				radioButtonGameMove.setSelected(true);
				radioButtonPlayerD.setSelected(true);
			}
		});
		radioButtonPlayerD1.addActionListener(clearBorder);
		JLabel labelGearCard=new JLabel("Gear");
		JLabel labelGearCard1D=new JLabel("_______________");
		labelGearCard1D.setFont(new Font(Font.DIALOG, Font.BOLD,10));
		JLabel labelGearCard2D=new JLabel("_______________");
		labelGearCard2D.setFont(new Font(Font.DIALOG, Font.BOLD,10));
		JLabel labelGearCard3D=new JLabel("_______________");
		labelGearCard3D.setFont(new Font(Font.DIALOG, Font.BOLD,10));
		JLabel labelGearCard4D=new JLabel("_______________");
		labelGearCard4D.setFont(new Font(Font.DIALOG, Font.BOLD,10));
		JLabel labelTokenCode=new JLabel("Code");
		JLabel labelToken=new JLabel("Token");
		JLabel labelFilling=new JLabel("Filling");
		JLabel labelAnswer=new JLabel("Answer");
		//		JLabel labelToken1D=new JLabel("Token1");
		//		JLabel labelToken2D=new JLabel("Token2");
		//		JLabel labelToken3D=new JLabel("Token3");
		JLabel labelSkill=new JLabel("   Skill   ");
		JLabel labelCard=new JLabel("   Card   ");
		textFieldGearCardCode1D=new JTextField(1);
		textFieldGearCardCode2D=new JTextField(1);
		textFieldGearCardCode3D=new JTextField(1);
		textFieldGearCardCode4D=new JTextField(1);
		//		textFieldTokenCode1D=new JTextField(1);
		//		textFieldTokenCode2D=new JTextField(1);
		//		textFieldTokenCode3D=new JTextField(1);
		textFieldFilling1D=new JTextField(3);
		textFieldFilling1D.setForeground(Color.BLACK);
		textFieldFilling2D=new JTextField(3);
		textFieldFilling2D.setForeground(Color.BLACK);
		textFieldFilling3D=new JTextField(3);
		textFieldFilling3D.setForeground(Color.BLACK);
		textFieldAnswer1D=new JTextField(3);
		textFieldAnswer1D.setForeground(Color.BLACK);
		textFieldAnswer2D=new JTextField(3);
		textFieldAnswer2D.setForeground(Color.BLACK);
		textFieldAnswer3D=new JTextField(3);
		textFieldAnswer3D.setForeground(Color.BLACK);
		
		Object[] items1=new Object[]
				{"block","fast","fight","fix","friend","hack","loves animals","see"};
		DefaultComboBoxModel model1=getComboBoxModelSkill();
		JComboBox cmbDecisionSkillsD=new JAutoCompleteComboBox(model1);
		DefaultComboBoxModel model=getComboBoxModelGearCard();
		JComboBox cmbDecisionCardsD=new JAutoCompleteComboBox(model);
		((JTextField) cmbDecisionSkillsD.getEditor().getEditorComponent()).setText("");
		cmbDecisionSkillsD.getEditor().getEditorComponent().addKeyListener(commonNavi);
		cmbDecisionSkillsD.getEditor().getEditorComponent().addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					decisionSkillsD=((JTextField) cmbDecisionSkillsD.getEditor().getEditorComponent()).getText();
					currentSpeaker="d";
					//vrSuggestedSkill yuhan
					sendSuggestedSkill(PLAYER_D, decisionSkillsD.trim(), USE);
					System.out.println(currentSpeaker+": "+decisionSkillsD);
					((JTextField) cmbDecisionSkillsD.getEditor().getEditorComponent()).setText("");
				}
			}
		});
		Object[] items=new Object[]
				{"abby","april","amy","ben","cd","chris","cloaking device","flux helmet","grace","grace","good shoes","hammer","helmet","lab mannaul","liz","luke","magaphone","mike","name tag","nico","noah","pocket computer","ray","sam","scanner","scientists","scott","tara","vera"};
		((JTextField) cmbDecisionCardsD.getEditor().getEditorComponent()).setText("");
		cmbDecisionCardsD.getEditor().getEditorComponent().addKeyListener(commonNavi);
		cmbDecisionCardsD.getEditor().getEditorComponent().addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					decisionCardsD=((JTextField) cmbDecisionCardsD.getEditor().getEditorComponent()).getText();
					currentSpeaker="d";
					//vrSuggestedCard yuhan
					sendSuggestedCard(PLAYER_D, decisionCardsD.trim(), USE);
					System.out.println(currentSpeaker+": "+decisionCardsD);
					((JTextField) cmbDecisionCardsD.getEditor().getEditorComponent()).setText("");
				}
			}
		});
		
		JComboBox cmbDecisionNoSkillsD=new JAutoCompleteComboBox(model1);
		JComboBox cmbDecisionNoCardsD=new JAutoCompleteComboBox(model);
		((JTextField) cmbDecisionNoSkillsD.getEditor().getEditorComponent()).setText("");
		cmbDecisionNoSkillsD.getEditor().getEditorComponent().addKeyListener(commonNavi);
		cmbDecisionNoSkillsD.getEditor().getEditorComponent().addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					decisionSkillsD=((JTextField) cmbDecisionNoSkillsD.getEditor().getEditorComponent()).getText();
					currentSpeaker="d";
					//vrSuggestedSkill yuhan
					sendSuggestedSkill(PLAYER_D, decisionSkillsD.trim(), NOT_USE);
					System.out.println(currentSpeaker+": "+decisionSkillsD);
					((JTextField) cmbDecisionNoSkillsD.getEditor().getEditorComponent()).setText("");
				}
			}
		});
		((JTextField) cmbDecisionNoCardsD.getEditor().getEditorComponent()).setText("");
		cmbDecisionNoCardsD.getEditor().getEditorComponent().addKeyListener(commonNavi);
		cmbDecisionNoCardsD.getEditor().getEditorComponent().addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					decisionCardsD=((JTextField) cmbDecisionNoCardsD.getEditor().getEditorComponent()).getText();
					currentSpeaker="d";
					//vrSuggestedCard yuhan
					sendSuggestedCard(PLAYER_D, decisionCardsD.trim(), NOT_USE);
					System.out.println(currentSpeaker+": "+decisionCardsD);
					((JTextField) cmbDecisionNoCardsD.getEditor().getEditorComponent()).setText("");
				}
			}
		});
		
		textFieldGearCardCode1D.addKeyListener(commonNavi);
		textFieldGearCardCode1D.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					String gearCardCode=textFieldGearCardCode1D.getText().trim();
					//vrAllGearCards yuhan
					sendAllGearCards();
					try
					{
						Workbook book=Workbook.getWorkbook(new File(sourceFile2));
						Sheet sheet=book.getSheet(0);
						int rows=sheet.getRows();
						int cols=sheet.getColumns();
						for (int z=1;z<rows;z++)
						{
							String code;
							code=sheet.getCell(0, z).getContents().trim();
							if (gearCardCode.equals(code))
							{
								gearCard1D=sheet.getCell(1,z).getContents().trim();
								labelGearCard1D.setText(gearCard1D);
								//								textFieldGearCardCode1D.setText("");
							}										
						}
						textFieldGearCardCode2D.requestFocusInWindow();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
				{
					textFieldGearCardCode2D.requestFocusInWindow();
				}
			}
		});
		textFieldGearCardCode2D.addKeyListener(commonNavi);
		textFieldGearCardCode2D.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					String gearCardCode=textFieldGearCardCode2D.getText().trim();
					//vrAllGearCards yuhan
					sendAllGearCards();
					try
					{
						Workbook book=Workbook.getWorkbook(new File(sourceFile2));
						Sheet sheet=book.getSheet(0);
						int rows=sheet.getRows();
						int cols=sheet.getColumns();
						for (int z=1;z<rows;z++)
						{
							String code;
							code=sheet.getCell(0, z).getContents().trim();
							if (gearCardCode.equals(code))
							{
								gearCard2D=sheet.getCell(1,z).getContents().trim();
								labelGearCard2D.setText(gearCard2D);
								//								textFieldGearCardCode2D.setText("");
							}										
						}
						textFieldGearCardCode3D.requestFocusInWindow();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
				{
					textFieldGearCardCode3D.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT)
				{
					textFieldGearCardCode1D.requestFocusInWindow();
				}
			}
		});
		textFieldGearCardCode3D.addKeyListener(commonNavi);
		textFieldGearCardCode3D.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					String gearCardCode=textFieldGearCardCode3D.getText().trim();
					//vrAllGearCards yuhan
					sendAllGearCards();
					try
					{
						Workbook book=Workbook.getWorkbook(new File(sourceFile2));
						Sheet sheet=book.getSheet(0);
						int rows=sheet.getRows();
						int cols=sheet.getColumns();
						for (int z=1;z<rows;z++)
						{
							String code;
							code=sheet.getCell(0, z).getContents().trim();
							if (gearCardCode.equals(code))
							{
								gearCard3D=sheet.getCell(1,z).getContents().trim();
								labelGearCard3D.setText(gearCard3D);
								//								textFieldGearCardCode3D.setText("");
							}										
						}
						textFieldGearCardCode4D.requestFocusInWindow();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
				{
					textFieldGearCardCode4D.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT)
				{
					textFieldGearCardCode2D.requestFocusInWindow();
				}
			}
		});
		textFieldGearCardCode4D.addKeyListener(commonNavi);
		textFieldGearCardCode4D.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					String gearCardCode=textFieldGearCardCode4D.getText().trim();
					//vrAllGearCards yuhan
					sendAllGearCards();
					try
					{
						Workbook book=Workbook.getWorkbook(new File(sourceFile2));
						Sheet sheet=book.getSheet(0);
						int rows=sheet.getRows();
						int cols=sheet.getColumns();
						for (int z=1;z<rows;z++)
						{
							String code;
							code=sheet.getCell(0, z).getContents().trim();
							if (gearCardCode.equals(code))
							{
								gearCard4D=sheet.getCell(1,z).getContents().trim();
								labelGearCard4D.setText(gearCard4D);
							}										
						}
						textFieldGearCardCode1VP.requestFocusInWindow();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT)
				{
					textFieldGearCardCode3D.requestFocusInWindow();
				}
			}
		});
		textFieldGearCardCode1D.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent arg0){
				textFieldGearCardCode1D.selectAll();
				//				  this.getUIClassID().selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldGearCardCode2D.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent arg0){
				textFieldGearCardCode2D.selectAll();
				//				  this.getUIClassID().selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldGearCardCode3D.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent arg0){
				textFieldGearCardCode3D.selectAll();
				//				  this.getUIClassID().selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldGearCardCode4D.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent arg0){
				textFieldGearCardCode4D.selectAll();
				//				  this.getUIClassID().selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		//		textFieldTokenCode1D.addKeyListener(commonNavi);
		//		textFieldTokenCode1D.addFocusListener(new FocusListener(){
		//			  public void focusGained(FocusEvent arg0){
		//				  textFieldTokenCode1D.selectAll();
		//			  }
		//
		//			@Override
		//			public void focusLost(FocusEvent e) {
		//				// TODO Auto-generated method stub
		//				
		//			}
		//		});
		//		textFieldTokenCode1D.addKeyListener(new KeyAdapter(){
		//			public void keyPressed(KeyEvent e)
		//			{
		//				if(e.getKeyCode()==KeyEvent.VK_ENTER)
		//				{
		//					String tokenCode=textFieldTokenCode1D.getText().trim();
		//					try
		//					{
		//						Workbook book=Workbook.getWorkbook(new File(sourceFile1));
		//						Sheet sheet=book.getSheet(0);
		//						int rows=sheet.getRows();
		//						int cols=sheet.getColumns();
		//						for (int z=1;z<rows;z++)
		//						{
		//							String code;
		//							code=sheet.getCell(0, z).getContents().trim();
		//							if (tokenCode.equals(code))
		//							{
		////								token1D=sheet.getCell(1,z).getContents().trim();
		////								labelToken1D.setText(token1D);
		//								//								textFieldTokenCode1D.setText("");
		//							}										
		//						}
		//						textFieldTokenCode2D.requestFocusInWindow();
		//					}
		//					catch(Exception ex)
		//					{
		//						ex.printStackTrace();
		//					}
		//				}
		//				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		//				{
		//					textFieldFilling1D.requestFocusInWindow();
		//				}
		//				if(e.getKeyCode()==KeyEvent.VK_DOWN)
		//				{
		//					textFieldTokenCode2D.requestFocusInWindow();
		//				}
		//			}
		//		});
		//		textFieldTokenCode2D.addKeyListener(commonNavi);
		//		textFieldTokenCode2D.addFocusListener(new FocusListener(){
		//			  public void focusGained(FocusEvent arg0){
		//				  textFieldTokenCode2D.selectAll();
		//			  }
		//
		//			@Override
		//			public void focusLost(FocusEvent e) {
		//				// TODO Auto-generated method stub
		//				
		//			}
		//		});
		//		textFieldTokenCode2D.addKeyListener(new KeyAdapter(){
		//			public void keyPressed(KeyEvent e)
		//			{
		//				if(e.getKeyCode()==KeyEvent.VK_ENTER)
		//				{
		//					String tokenCode=textFieldTokenCode2D.getText().trim();
		//					try
		//					{
		//						Workbook book=Workbook.getWorkbook(new File(sourceFile1));
		//						Sheet sheet=book.getSheet(0);
		//						int rows=sheet.getRows();
		//						int cols=sheet.getColumns();
		//						for (int z=1;z<rows;z++)
		//						{
		//							String code;
		//							code=sheet.getCell(0, z).getContents().trim();
		//							if (tokenCode.equals(code))
		//							{
		//								token2D=sheet.getCell(1,z).getContents().trim();
		//								labelToken2D.setText(token2D);
		//								//								textFieldTokenCode2D.setText("");
		//							}										
		//						}
		//						textFieldTokenCode3D.requestFocusInWindow();
		//					}
		//					catch(Exception ex)
		//					{
		//						ex.printStackTrace();
		//					}
		//				}
		//				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		//				{
		//					textFieldFilling2D.requestFocusInWindow();
		//				}
		//				if(e.getKeyCode()==KeyEvent.VK_DOWN)
		//				{
		//					textFieldTokenCode3D.requestFocusInWindow();
		//				}
		//				if(e.getKeyCode()==KeyEvent.VK_UP){
		//					textFieldTokenCode1D.requestFocusInWindow();
		//				}
		//			}
		//		});
		//		textFieldTokenCode3D.addKeyListener(commonNavi);
		//		textFieldTokenCode3D.addFocusListener(new FocusListener(){
		//			  public void focusGained(FocusEvent arg0){
		//				  textFieldTokenCode3D.selectAll();
		//			  }
		//
		//			@Override
		//			public void focusLost(FocusEvent e) {
		//				// TODO Auto-generated method stub
		//				
		//			}
		//		});
		//		textFieldTokenCode3D.addKeyListener(new KeyAdapter(){
		//			public void keyPressed(KeyEvent e)
		//			{
		//				if(e.getKeyCode()==KeyEvent.VK_ENTER)
		//				{
		//					String tokenCode=textFieldTokenCode3D.getText().trim();
		//					try
		//					{
		//						Workbook book=Workbook.getWorkbook(new File(sourceFile1));
		//						Sheet sheet=book.getSheet(0);
		//						int rows=sheet.getRows();
		//						int cols=sheet.getColumns();
		//						for (int z=1;z<rows;z++)
		//						{
		//							String code;
		//							code=sheet.getCell(0, z).getContents().trim();
		//							if (tokenCode.equals(code))
		//							{
		//								token3D=sheet.getCell(1,z).getContents().trim();
		//								labelToken3D.setText(token3D);
		//								//								textFieldTokenCode3D.setText("");
		//							}										
		//						}
		//					}
		//					catch(Exception ex)
		//					{
		//						ex.printStackTrace();
		//					}
		//				}
		//				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		//				{
		//					textFieldFilling3D.requestFocusInWindow();
		//				}
		//				if(e.getKeyCode()==KeyEvent.VK_UP){
		//					textFieldTokenCode2D.requestFocusInWindow();
		//				}
		//				if(e.getKeyCode()==KeyEvent.VK_DOWN){
		//					cmbDecisionSkillsD.requestFocusInWindow();
		//				}
		//			}
		//		});
		textFieldFilling1D.addKeyListener(commonNavi);
		textFieldFilling1D.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){
				formerGamePhase=currentGamePhase;
				if(currentGamePhase.equals("QuestionAndAnswer")==false){
					currentGamePhase="QuestionAndAnswer";
					//vrGamePhase yuhan QA
					sendGamePhase();
					timer0.start();
				}
				radioButtonQuestionAndAnswer.setSelected(true);
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldFilling1D.addFocusListener(clearBorder2);
		textFieldFilling1D.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					filling1D=textFieldFilling1D.getText();
					currentFilling=filling1D;
					currentSpeaker="d";
					System.out.println(currentSpeaker+" "+currentFilling);
					UI_skills.textFieldKeyword.setText(currentFilling);
					textFieldFilling1D.setForeground(Color.BLUE);
					textFieldAnswer1D.requestFocusInWindow();
				}
				//				if(e.getKeyCode()==KeyEvent.VK_LEFT){
				//					textFieldTokenCode1D.requestFocusInWindow();
				//				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					textFieldAnswer1D.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					textFieldFilling2D.requestFocusInWindow();
				}
			}
		});
		textFieldFilling2D.addKeyListener(commonNavi);
		textFieldFilling2D.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){
				formerGamePhase=currentGamePhase;
				if(currentGamePhase.equals("QuestionAndAnswer")==false){
					currentGamePhase="QuestionAndAnswer";
					//vrGamePhase yuhan QA
					sendGamePhase();
					timer0.start();
				}
				radioButtonQuestionAndAnswer.setSelected(true);
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldFilling2D.addFocusListener(clearBorder2);
		textFieldFilling2D.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					filling2D=textFieldFilling2D.getText();
					currentFilling=filling2D;
					currentSpeaker="d";
					System.out.println(currentSpeaker+" "+currentFilling);
					UI_skills.textFieldKeyword.setText(currentFilling);
					textFieldFilling2D.setForeground(Color.BLUE);
					textFieldAnswer2D.requestFocusInWindow();
				}
				//				if(e.getKeyCode()==KeyEvent.VK_LEFT){
				//					textFieldTokenCode2D.requestFocusInWindow();
				//				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					textFieldAnswer2D.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					textFieldFilling3D.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_UP){
					textFieldFilling1D.requestFocusInWindow();
				}
			}
		});
		textFieldFilling3D.addKeyListener(commonNavi);
		textFieldFilling3D.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){
				formerGamePhase=currentGamePhase;
				if(currentGamePhase.equals("QuestionAndAnswer")==false){
					currentGamePhase="QuestionAndAnswer";
					//vrGamePhase yuhan QA
					sendGamePhase();
					timer0.start();
				}
				radioButtonQuestionAndAnswer.setSelected(true);
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldFilling3D.addFocusListener(clearBorder2);
		textFieldFilling3D.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					filling3D=textFieldFilling3D.getText();
					currentFilling=filling3D;
					currentSpeaker="d";
					System.out.println(currentSpeaker+" "+currentFilling);
					UI_skills.textFieldKeyword.setText(currentFilling);
					textFieldFilling3D.setForeground(Color.BLUE);
					textFieldAnswer3D.requestFocusInWindow();
				}
				//				if(e.getKeyCode()==KeyEvent.VK_LEFT){
				//					textFieldTokenCode3D.requestFocusInWindow();
				//				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					textFieldAnswer3D.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_UP){
					textFieldFilling2D.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					cmbDecisionSkillsD.requestFocusInWindow();
				}
			}
		});
		textFieldAnswer1D.addKeyListener(commonNavi);
		textFieldAnswer1D.addKeyListener(skillsDecisionGenerationVP);
		textFieldAnswer1D.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					answer1D=textFieldAnswer1D.getText();
					currentAnswer=answer1D;
					System.out.println(currentSpeaker+" "+currentAnswer);
					//					currentToken=token1D;
					currentToken="";
					textFieldAnswer1D.setForeground(Color.BLUE);
					UI_skills.buttonSkill.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT){
					textFieldFilling1D.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					textFieldAnswer2D.requestFocusInWindow();
				}
			}
		});
		textFieldAnswer1D.addKeyListener(checkBatteryToken);
		textFieldAnswer2D.addKeyListener(commonNavi);
		textFieldAnswer2D.addKeyListener(skillsDecisionGenerationVP);
		textFieldAnswer2D.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					answer2D=textFieldAnswer2D.getText();
					currentAnswer=answer2D;
					System.out.println(currentSpeaker+" "+currentAnswer);
					//					currentToken=token2D;
					currentToken="";
					textFieldAnswer2D.setForeground(Color.BLUE);
					UI_skills.buttonSkill.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT){
					textFieldFilling2D.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					textFieldAnswer3D.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_UP){
					textFieldAnswer1D.requestFocusInWindow();
				}
			}
		});
		textFieldAnswer2D.addKeyListener(checkBatteryToken);
		textFieldAnswer3D.addKeyListener(commonNavi);
		textFieldAnswer3D.addKeyListener(skillsDecisionGenerationVP);
		textFieldAnswer3D.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					answer3D=textFieldAnswer3D.getText();
					currentAnswer=answer3D;
					System.out.println(currentSpeaker+" "+currentAnswer);
					//					currentToken=token3D;
					currentToken="";
					textFieldAnswer3D.setForeground(Color.BLUE);
					UI_skills.buttonSkill.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT){
					textFieldFilling3D.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_UP){
					textFieldAnswer2D.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					cmbDecisionSkillsD.requestFocusInWindow();
				}
			}
		});
		textFieldAnswer3D.addKeyListener(checkBatteryToken);
		
		JButton buttonYes1D=new JButton("Yes");
		buttonYes1D.addActionListener(skillsDecisionGenerationVP2);
		buttonYes1D.addActionListener(checkBatteryToken2);
		buttonYes1D.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling1D.getText();
				filling1D=currentFilling;
				textFieldFilling1D.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
		
				currentToken="";
				answer1D="yes";
				currentAnswer="yes";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer1D.setText(currentAnswer);
				textFieldAnswer1D.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonNo1D=new JButton("No");
		buttonNo1D.addActionListener(skillsDecisionGenerationVP2);
		buttonNo1D.addActionListener(checkBatteryToken2);
		buttonNo1D.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling1D.getText();
				filling1D=currentFilling;
				textFieldFilling1D.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
		
				currentToken="";
				answer1D="no";
				currentAnswer="no";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer1D.setText(currentAnswer);
				textFieldAnswer1D.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonNotMatter1D=new JButton("NM");
//		buttonNotMatter1D.setFont(new Font(Font.DIALOG, Font.BOLD,7));
		buttonNotMatter1D.addActionListener(skillsDecisionGenerationVP2);
		buttonNotMatter1D.addActionListener(checkBatteryToken2);
		buttonNotMatter1D.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling1D.getText();
				filling1D=currentFilling;
				textFieldFilling1D.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
		
				currentToken="";
				answer1D="not matter";
				currentAnswer="not matter";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer1D.setText(currentAnswer);
				textFieldAnswer1D.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonYes2D=new JButton("Yes");
		buttonYes2D.addActionListener(skillsDecisionGenerationVP2);
		buttonYes2D.addActionListener(checkBatteryToken2);
		buttonYes2D.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling2D.getText();
				filling2D=currentFilling;
				textFieldFilling2D.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
		
				currentToken="";
				answer2D="yes";
				currentAnswer="yes";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer2D.setText(currentAnswer);
				textFieldAnswer2D.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonNo2D=new JButton("No");
		buttonNo2D.addActionListener(skillsDecisionGenerationVP2);
		buttonNo2D.addActionListener(checkBatteryToken2);
		buttonNo2D.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling2D.getText();
				filling2D=currentFilling;
				textFieldFilling2D.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
		
				currentToken="";
				answer2D="no";
				currentAnswer="no";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer2D.setText(currentAnswer);
				textFieldAnswer2D.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonNotMatter2D=new JButton("NM");
//		buttonNotMatter2D.setFont(new Font(Font.DIALOG, Font.BOLD,7));
		buttonNotMatter2D.addActionListener(skillsDecisionGenerationVP2);
		buttonNotMatter2D.addActionListener(checkBatteryToken2);
		buttonNotMatter2D.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling2D.getText();
				filling2D=currentFilling;
				textFieldFilling2D.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
		
				currentToken="";
				answer2D="not matter";
				currentAnswer="not matter";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer2D.setText(currentAnswer);
				textFieldAnswer2D.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonYes3D=new JButton("Yes");
		buttonYes3D.addActionListener(skillsDecisionGenerationVP2);
		buttonYes3D.addActionListener(checkBatteryToken2);
		buttonYes3D.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling3D.getText();
				filling3D=currentFilling;
				textFieldFilling3D.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
		
				currentToken="";
				answer3D="yes";
				currentAnswer="yes";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer3D.setText(currentAnswer);
				textFieldAnswer3D.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonNo3D=new JButton("No");
		buttonNo3D.addActionListener(skillsDecisionGenerationVP2);
		buttonNo3D.addActionListener(checkBatteryToken2);
		buttonNo3D.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling3D.getText();
				filling3D=currentFilling;
				textFieldFilling3D.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
		
				currentToken="";
				answer3D="no";
				currentAnswer="no";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer3D.setText(currentAnswer);
				textFieldAnswer3D.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonNotMatter3D=new JButton("NM");
//		buttonNotMatter3D.setFont(new Font(Font.DIALOG, Font.BOLD,7));
		buttonNotMatter3D.addActionListener(skillsDecisionGenerationVP2);
		buttonNotMatter3D.addActionListener(checkBatteryToken2);
		buttonNotMatter3D.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling3D.getText();
				filling3D=currentFilling;
				textFieldFilling3D.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
		
				currentToken="";
				answer3D="not matter";
				currentAnswer="not matter";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer3D.setText(currentAnswer);
				textFieldAnswer3D.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		
		Box box0=Box.createVerticalBox();
		box0.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		Box box1=Box.createHorizontalBox();
		box1.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		Box box11=Box.createVerticalBox();
		Box box12=Box.createVerticalBox();
		Box box13=Box.createVerticalBox();
		Box box14=Box.createVerticalBox();
		Box box2=Box.createVerticalBox();
		box2.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		Box box21=Box.createHorizontalBox();
		Box box22=Box.createHorizontalBox();
		Box box23=Box.createHorizontalBox();
		Box box3=Box.createHorizontalBox();
		box3.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		Box box4=Box.createHorizontalBox();
		box4.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		box11.add(textFieldGearCardCode1D);
		box11.add(labelGearCard1D);
		box12.add(textFieldGearCardCode2D);
		box12.add(labelGearCard2D);
		box13.add(textFieldGearCardCode3D);
		box13.add(labelGearCard3D);
		box14.add(textFieldGearCardCode4D);
		box14.add(labelGearCard4D);
		box1.add(labelGearCard);
		box1.add(box11);
		box1.add(box12);
		box1.add(box13);
		box1.add(box14);
		box21.add(textFieldFilling1D);
		box21.add(buttonYes1D);
		box21.add(buttonNo1D);
		box21.add(buttonNotMatter1D);
		box21.add(textFieldAnswer1D);
		box22.add(textFieldFilling2D);
		box22.add(buttonYes2D);
		box22.add(buttonNo2D);
		box22.add(buttonNotMatter2D);
		box22.add(textFieldAnswer2D);
		box23.add(textFieldFilling3D);
		box23.add(buttonYes3D);
		box23.add(buttonNo3D);
		box23.add(buttonNotMatter3D);
		box23.add(textFieldAnswer3D);
		box2.add(box21);
		box2.add(box22);
		box2.add(box23);
		box3.add(labelSkill);
		box3.add(new JLabel("  "));
		box3.add(cmbDecisionSkillsD);
		box3.add(new JLabel("  "));
		box3.add(labelCard);
		box3.add(new JLabel("  "));
		box3.add(cmbDecisionCardsD);
		box4.add(new JLabel("No Skill"));
		box4.add(new JLabel("  "));
		box4.add(cmbDecisionNoSkillsD);
		box4.add(new JLabel("  "));
		box4.add(new JLabel("No Card"));
		box4.add(new JLabel("  "));
		box4.add(cmbDecisionNoCardsD);
		//		box0.add(labelPlayer);
		box0.add(radioButtonPlayerD1);
		box0.add(box1);
		box0.add(box2);
		box0.add(box3);
		box0.add(box4);
		p.add(box0);
		return p;
	}

	/**
	 * panel for player W
	 * @return
	 */
	private static JPanel panelPlayerW(){
		JPanel p=new JPanel();		
		//		JLabel labelPlayer=new JLabel("Top Player(W)");
		radioButtonPlayerW1=new JRadioButton("Top Player(W)");
		radioButtonPlayerW1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentPlayer="w";
				//vrCurrentPlayer yuhan
				sendCurrentPlayer();
				formerGamePhase=currentGamePhase;
				if(!currentGamePhase.equals("GameMove")) {
					currentGamePhase="GameMove";
					//vrGamePhase yuhan GameMove
					sendGamePhase();
				}
				radioButtonGameMove.setSelected(true);
				radioButtonPlayerW.setSelected(true);
			}
		});
		radioButtonPlayerW1.addActionListener(clearBorder);
		JLabel labelGearCard=new JLabel("Gear");
		JLabel labelGearCard1W=new JLabel("_______________");
		labelGearCard1W.setFont(new Font(Font.DIALOG, Font.BOLD,10));
		JLabel labelGearCard2W=new JLabel("_______________");
		labelGearCard2W.setFont(new Font(Font.DIALOG, Font.BOLD,10));
		JLabel labelGearCard3W=new JLabel("_______________");
		labelGearCard3W.setFont(new Font(Font.DIALOG, Font.BOLD,10));
		JLabel labelGearCard4W=new JLabel("_______________");
		labelGearCard4W.setFont(new Font(Font.DIALOG, Font.BOLD,10));
		JLabel labelTokenCode=new JLabel("Code");
		JLabel labelToken=new JLabel("Token");
		JLabel labelFilling=new JLabel("Filling");
		JLabel labelAnswer=new JLabel("Answer");
		//		JLabel labelToken1W=new JLabel("Token1");
		//		JLabel labelToken2W=new JLabel("Token2");
		//		JLabel labelToken3W=new JLabel("Token3");
		JLabel labelSkill=new JLabel("   Skill   ");
		JLabel labelCard=new JLabel("   Card   ");
		textFieldGearCardCode1W=new JTextField(1);
		textFieldGearCardCode2W=new JTextField(1);
		textFieldGearCardCode3W=new JTextField(1);
		textFieldGearCardCode4W=new JTextField(1);
		//		textFieldTokenCode1W=new JTextField(1);
		//		textFieldTokenCode2W=new JTextField(1);
		//		textFieldTokenCode3W=new JTextField(1);
		textFieldFilling1W=new JTextField(3);
		textFieldFilling1W.setForeground(Color.BLACK);
		textFieldFilling2W=new JTextField(3);
		textFieldFilling2W.setForeground(Color.BLACK);
		textFieldFilling3W=new JTextField(3);
		textFieldFilling3W.setForeground(Color.BLACK);
		textFieldAnswer1W=new JTextField(3);
		textFieldAnswer1W.setForeground(Color.BLACK);
		textFieldAnswer2W=new JTextField(3);
		textFieldAnswer2W.setForeground(Color.BLACK);
		textFieldAnswer3W=new JTextField(3);
		textFieldAnswer3W.setForeground(Color.BLACK);
		
		Object[] items1=new Object[]
				{"block","fast","fight","fix","friend","hack","loves animals","see"};
		DefaultComboBoxModel model1=getComboBoxModelSkill();
		JComboBox cmbDecisionSkillsW=new JAutoCompleteComboBox(model1);
		DefaultComboBoxModel model=getComboBoxModelGearCard();
		JComboBox cmbDecisionCardsW=new JAutoCompleteComboBox(model);
		((JTextField) cmbDecisionSkillsW.getEditor().getEditorComponent()).setText("");
		cmbDecisionSkillsW.getEditor().getEditorComponent().addKeyListener(commonNavi);
		cmbDecisionSkillsW.getEditor().getEditorComponent().addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					decisionSkillsW=((JTextField) cmbDecisionSkillsW.getEditor().getEditorComponent()).getText();
					currentSpeaker="w";
					//vrSuggestedSkill yuhan
					sendSuggestedSkill(PLAYER_W, decisionSkillsW.trim(), USE);
					System.out.println(currentSpeaker+": "+decisionSkillsW);
					((JTextField) cmbDecisionSkillsW.getEditor().getEditorComponent()).setText("");
				}
			}
		});
		Object[] items=new Object[]
				{"abby","april","amy","ben","cd","chris","cloaking device","flux helmet","grace","grace","good shoes","hammer","helmet","lab mannaul","liz","luke","magaphone","mike","name tag","nico","noah","pocket computer","ray","sam","scanner","scientists","scott","tara","vera"};
		((JTextField) cmbDecisionCardsW.getEditor().getEditorComponent()).setText("");
		cmbDecisionCardsW.getEditor().getEditorComponent().addKeyListener(commonNavi);
		cmbDecisionCardsW.getEditor().getEditorComponent().addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					decisionCardsW=((JTextField) cmbDecisionCardsW.getEditor().getEditorComponent()).getText();
					currentSpeaker="w";
					//vrSuggestedCard yuhan
					sendSuggestedCard(PLAYER_W, decisionCardsW.trim(), USE);
					System.out.println(currentSpeaker+": "+decisionCardsW);
					((JTextField) cmbDecisionCardsW.getEditor().getEditorComponent()).setText("");
				}
			}
		});
		
		JComboBox cmbDecisionNoSkillsW=new JAutoCompleteComboBox(model1);
		JComboBox cmbDecisionNoCardsW=new JAutoCompleteComboBox(model);
		((JTextField) cmbDecisionNoSkillsW.getEditor().getEditorComponent()).setText("");
		cmbDecisionNoSkillsW.getEditor().getEditorComponent().addKeyListener(commonNavi);
		cmbDecisionNoSkillsW.getEditor().getEditorComponent().addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					decisionSkillsW=((JTextField) cmbDecisionNoSkillsW.getEditor().getEditorComponent()).getText();
					currentSpeaker="w";
					//vrSuggestedSkill yuhan
					sendSuggestedSkill(PLAYER_W, decisionSkillsW.trim(), NOT_USE);
					System.out.println(currentSpeaker+": no "+decisionSkillsW);
					((JTextField) cmbDecisionNoSkillsW.getEditor().getEditorComponent()).setText("");
				}
			}
		});
		((JTextField) cmbDecisionNoCardsW.getEditor().getEditorComponent()).setText("");
		cmbDecisionNoCardsW.getEditor().getEditorComponent().addKeyListener(commonNavi);
		cmbDecisionNoCardsW.getEditor().getEditorComponent().addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					decisionCardsW=((JTextField) cmbDecisionNoCardsW.getEditor().getEditorComponent()).getText();
					currentSpeaker="w";
					//vrSuggestedCard yuhan
					sendSuggestedCard(PLAYER_W, decisionCardsW.trim(), NOT_USE);
					System.out.println(currentSpeaker+": "+decisionCardsW);
					((JTextField) cmbDecisionNoCardsW.getEditor().getEditorComponent()).setText("");
				}
			}
		});
		
		textFieldGearCardCode1W.addKeyListener(commonNavi);
		textFieldGearCardCode1W.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					String gearCardCode=textFieldGearCardCode1W.getText().trim();
					//vrAllGearCards yuhan
					sendAllGearCards();
					try
					{
						Workbook book=Workbook.getWorkbook(new File(sourceFile2));
						Sheet sheet=book.getSheet(0);
						int rows=sheet.getRows();
						int cols=sheet.getColumns();
						for (int z=1;z<rows;z++)
						{
							String code;
							code=sheet.getCell(0, z).getContents().trim();
							if (gearCardCode.equals(code))
							{
								gearCard1W=sheet.getCell(1,z).getContents().trim();
								labelGearCard1W.setText(gearCard1W);
								//								textFieldGearCardCode1W.setText("");
							}										
						}
						textFieldGearCardCode2W.requestFocusInWindow();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
				{
					textFieldGearCardCode2W.requestFocusInWindow();
				}
			}
		});
		textFieldGearCardCode2W.addKeyListener(commonNavi);
		textFieldGearCardCode2W.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					String gearCardCode=textFieldGearCardCode2W.getText().trim();
					//vrAllGearCards yuhan
					sendAllGearCards();
					try
					{
						Workbook book=Workbook.getWorkbook(new File(sourceFile2));
						Sheet sheet=book.getSheet(0);
						int rows=sheet.getRows();
						int cols=sheet.getColumns();
						for (int z=1;z<rows;z++)
						{
							String code;
							code=sheet.getCell(0, z).getContents().trim();
							if (gearCardCode.equals(code))
							{
								gearCard2W=sheet.getCell(1,z).getContents().trim();
								labelGearCard2W.setText(gearCard2W);
								//								textFieldGearCardCode2W.setText("");
							}										
						}
						textFieldGearCardCode3W.requestFocusInWindow();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
				{
					textFieldGearCardCode3W.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT)
				{
					textFieldGearCardCode1W.requestFocusInWindow();
				}
			}
		});
		textFieldGearCardCode3W.addKeyListener(commonNavi);
		textFieldGearCardCode3W.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					String gearCardCode=textFieldGearCardCode3W.getText().trim();
					//vrAllGearCards yuhan
					sendAllGearCards();
					try
					{
						Workbook book=Workbook.getWorkbook(new File(sourceFile2));
						Sheet sheet=book.getSheet(0);
						int rows=sheet.getRows();
						int cols=sheet.getColumns();
						for (int z=1;z<rows;z++)
						{
							String code;
							code=sheet.getCell(0, z).getContents().trim();
							if (gearCardCode.equals(code))
							{
								gearCard3W=sheet.getCell(1,z).getContents().trim();
								labelGearCard3W.setText(gearCard3W);
								//								textFieldGearCardCode3W.setText("");
							}										
						}
						textFieldGearCardCode4W.requestFocusInWindow();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
				{
					textFieldGearCardCode4W.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT)
				{
					textFieldGearCardCode2W.requestFocusInWindow();
				}
			}
		});
		textFieldGearCardCode4W.addKeyListener(commonNavi);
		textFieldGearCardCode4W.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					String gearCardCode=textFieldGearCardCode4W.getText().trim();
					//vrAllGearCards yuhan
					sendAllGearCards();
					try
					{
						Workbook book=Workbook.getWorkbook(new File(sourceFile2));
						Sheet sheet=book.getSheet(0);
						int rows=sheet.getRows();
						int cols=sheet.getColumns();
						for (int z=1;z<rows;z++)
						{
							String code;
							code=sheet.getCell(0, z).getContents().trim();
							if (gearCardCode.equals(code))
							{
								gearCard4W=sheet.getCell(1,z).getContents().trim();
								labelGearCard4W.setText(gearCard4W);
								//								textFieldGearCardCode4W.setText("");
							}										
						}
						textFieldGearCardCode1D.requestFocusInWindow();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT)
				{
					textFieldGearCardCode3W.requestFocusInWindow();
				}
			}
		});
		textFieldGearCardCode1W.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent arg0){
				textFieldGearCardCode1W.selectAll();
				//				  this.getUIClassID().selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldGearCardCode2W.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent arg0){
				textFieldGearCardCode2W.selectAll();
				//				  this.getUIClassID().selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldGearCardCode3W.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent arg0){
				textFieldGearCardCode3W.selectAll();
				//				  this.getUIClassID().selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldGearCardCode4W.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent arg0){
				textFieldGearCardCode4W.selectAll();
				//				  this.getUIClassID().selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		//		textFieldTokenCode1W.addKeyListener(commonNavi);
		//		textFieldTokenCode1W.addFocusListener(new FocusListener(){
		//			  public void focusGained(FocusEvent arg0){
		//				  textFieldTokenCode1W.selectAll();
		//			  }
		//
		//			@Override
		//			public void focusLost(FocusEvent e) {
		//				// TODO Auto-generated method stub
		//				
		//			}
		//		});
		//		textFieldTokenCode1W.addKeyListener(new KeyAdapter(){
		//			public void keyPressed(KeyEvent e)
		//			{
		//				if(e.getKeyCode()==KeyEvent.VK_ENTER)
		//				{
		//					String tokenCode=textFieldTokenCode1W.getText().trim();
		//					try
		//					{
		//						Workbook book=Workbook.getWorkbook(new File(sourceFile1));
		//						Sheet sheet=book.getSheet(0);
		//						int rows=sheet.getRows();
		//						int cols=sheet.getColumns();
		//						for (int z=1;z<rows;z++)
		//						{
		//							String code;
		//							code=sheet.getCell(0, z).getContents().trim();
		//							if (tokenCode.equals(code))
		//							{
		//								token1W=sheet.getCell(1,z).getContents().trim();
		//								labelToken1W.setText(token1W);
		//								//								textFieldTokenCode1W.setText("");
		//							}										
		//						}
		//						textFieldTokenCode2W.requestFocusInWindow();
		//					}
		//					catch(Exception ex)
		//					{
		//						ex.printStackTrace();
		//					}
		//				}
		//				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		//				{
		//					textFieldFilling1W.requestFocusInWindow();
		//				}
		//				if(e.getKeyCode()==KeyEvent.VK_DOWN)
		//				{
		//					textFieldTokenCode2W.requestFocusInWindow();
		//				}
		//			}
		//		});
		//		textFieldTokenCode2W.addKeyListener(commonNavi);
		//		textFieldTokenCode2W.addFocusListener(new FocusListener(){
		//			  public void focusGained(FocusEvent arg0){
		//				  textFieldTokenCode2W.selectAll();
		//			  }
		//
		//			@Override
		//			public void focusLost(FocusEvent e) {
		//				// TODO Auto-generated method stub
		//				
		//			}
		//		});
		//		textFieldTokenCode2W.addKeyListener(new KeyAdapter(){
		//			public void keyPressed(KeyEvent e)
		//			{
		//				if(e.getKeyCode()==KeyEvent.VK_ENTER)
		//				{
		//					String tokenCode=textFieldTokenCode2W.getText().trim();
		//					try
		//					{
		//						Workbook book=Workbook.getWorkbook(new File(sourceFile1));
		//						Sheet sheet=book.getSheet(0);
		//						int rows=sheet.getRows();
		//						int cols=sheet.getColumns();
		//						for (int z=1;z<rows;z++)
		//						{
		//							String code;
		//							code=sheet.getCell(0, z).getContents().trim();
		//							if (tokenCode.equals(code))
		//							{
		//								token2W=sheet.getCell(1,z).getContents().trim();
		//								labelToken2W.setText(token2W);
		//								//								textFieldTokenCode2W.setText("");
		//							}										
		//						}
		//						textFieldTokenCode3W.requestFocusInWindow();
		//					}
		//					catch(Exception ex)
		//					{
		//						ex.printStackTrace();
		//					}
		//				}
		//				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		//				{
		//					textFieldFilling2W.requestFocusInWindow();
		//				}
		//				if(e.getKeyCode()==KeyEvent.VK_DOWN)
		//				{
		//					textFieldTokenCode3W.requestFocusInWindow();
		//				}
		//				if(e.getKeyCode()==KeyEvent.VK_UP){
		//					textFieldTokenCode1W.requestFocusInWindow();
		//				}
		//			}
		//		});
		//		textFieldTokenCode3W.addKeyListener(commonNavi);
		//		textFieldTokenCode3W.addFocusListener(new FocusListener(){
		//			  public void focusGained(FocusEvent arg0){
		//				  textFieldTokenCode3W.selectAll();
		//			  }
		//
		//			@Override
		//			public void focusLost(FocusEvent e) {
		//				// TODO Auto-generated method stub
		//				
		//			}
		//		});
		//		textFieldTokenCode3W.addKeyListener(new KeyAdapter(){
		//			public void keyPressed(KeyEvent e)
		//			{
		//				if(e.getKeyCode()==KeyEvent.VK_ENTER)
		//				{
		//					String tokenCode=textFieldTokenCode3W.getText().trim();
		//					try
		//					{
		//						Workbook book=Workbook.getWorkbook(new File(sourceFile1));
		//						Sheet sheet=book.getSheet(0);
		//						int rows=sheet.getRows();
		//						int cols=sheet.getColumns();
		//						for (int z=1;z<rows;z++)
		//						{
		//							String code;
		//							code=sheet.getCell(0, z).getContents().trim();
		//							if (tokenCode.equals(code))
		//							{
		//								token3W=sheet.getCell(1,z).getContents().trim();
		//								labelToken3W.setText(token3W);
		//								//								textFieldTokenCode3W.setText("");
		//							}										
		//						}
		//					}
		//					catch(Exception ex)
		//					{
		//						ex.printStackTrace();
		//					}
		//				}
		//				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		//				{
		//					textFieldFilling3W.requestFocusInWindow();
		//				}
		//				if(e.getKeyCode()==KeyEvent.VK_UP){
		//					textFieldTokenCode2W.requestFocusInWindow();
		//				}
		//				if(e.getKeyCode()==KeyEvent.VK_DOWN){
		//					cmbDecisionSkillsW.requestFocusInWindow();
		//				}
		//			}
		//		});
		textFieldFilling1W.addKeyListener(commonNavi);
		textFieldFilling1W.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){
				formerGamePhase=currentGamePhase;
				if(currentGamePhase.equals("QuestionAndAnswer")==false){
					currentGamePhase="QuestionAndAnswer";
					//vrGamePhase yuhan QA
					sendGamePhase();
					timer0.start();
				}
				radioButtonQuestionAndAnswer.setSelected(true);
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldFilling1W.addFocusListener(clearBorder2);
		textFieldFilling1W.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					filling1W=textFieldFilling1W.getText();
					currentFilling=filling1W;
					currentSpeaker="w";
					System.out.println(currentSpeaker+" "+currentFilling);
					UI_skills.textFieldKeyword.setText(currentFilling);
					textFieldFilling1W.setForeground(Color.BLUE);
					textFieldAnswer1W.requestFocusInWindow();
				}
				//				if(e.getKeyCode()==KeyEvent.VK_LEFT){
				//					textFieldTokenCode1W.requestFocusInWindow();
				//				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					textFieldAnswer1W.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					textFieldFilling2W.requestFocusInWindow();
				}
			}
		});
		textFieldFilling2W.addKeyListener(commonNavi);
		textFieldFilling2W.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){
				formerGamePhase=currentGamePhase;
				if(currentGamePhase.equals("QuestionAndAnswer")==false){
					currentGamePhase="QuestionAndAnswer";
					//vrGamePhase yuhan QA
					sendGamePhase();
					timer0.start();
				}
				radioButtonQuestionAndAnswer.setSelected(true);
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldFilling2W.addFocusListener(clearBorder2);
		textFieldFilling2W.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					filling2W=textFieldFilling2W.getText();
					currentFilling=filling2W;
					currentSpeaker="w";
					System.out.println(currentSpeaker+" "+currentFilling);
					UI_skills.textFieldKeyword.setText(currentFilling);
					textFieldFilling2W.setForeground(Color.BLUE);
					textFieldAnswer2W.requestFocusInWindow();
				}
				//				if(e.getKeyCode()==KeyEvent.VK_LEFT){
				//					textFieldTokenCode2W.requestFocusInWindow();
				//				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					textFieldAnswer2W.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					textFieldFilling3W.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_UP){
					textFieldFilling1W.requestFocusInWindow();
				}
			}
		});
		textFieldFilling3W.addKeyListener(commonNavi);
		textFieldFilling3W.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){
				formerGamePhase=currentGamePhase;
				if(currentGamePhase.equals("QuestionAndAnswer")==false){
					currentGamePhase="QuestionAndAnswer";
					//vrGamePhase yuhan QA
					sendGamePhase();
					timer0.start();
				}
				radioButtonQuestionAndAnswer.setSelected(true);
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldFilling3W.addFocusListener(clearBorder2);
		textFieldFilling3W.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					filling3W=textFieldFilling3W.getText();
					currentFilling=filling3W;
					currentSpeaker="w";
					System.out.println(currentSpeaker+" "+currentFilling);
					UI_skills.textFieldKeyword.setText(currentFilling);
					textFieldFilling3W.setForeground(Color.BLUE);
					textFieldAnswer3W.requestFocusInWindow();
				}
				//				if(e.getKeyCode()==KeyEvent.VK_LEFT){
				//					textFieldTokenCode3W.requestFocusInWindow();
				//				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					textFieldAnswer3W.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_UP){
					textFieldFilling2W.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					cmbDecisionSkillsW.requestFocusInWindow();
				}
			}
		});
		textFieldAnswer1W.addKeyListener(commonNavi);
		textFieldAnswer1W.addKeyListener(skillsDecisionGenerationVP);
		textFieldAnswer1W.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					answer1W=textFieldAnswer1W.getText();
					currentAnswer=answer1W;
					System.out.println(currentSpeaker+" "+currentAnswer);
					//					currentToken=token1W;
					currentToken="";
					textFieldAnswer1W.setForeground(Color.BLUE);
					UI_skills.buttonSkill.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT){
					textFieldFilling1W.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					textFieldAnswer2W.requestFocusInWindow();
				}
			}
		});
		textFieldAnswer1W.addKeyListener(checkBatteryToken);
		textFieldAnswer2W.addKeyListener(commonNavi);
		textFieldAnswer2W.addKeyListener(skillsDecisionGenerationVP);
		textFieldAnswer2W.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					answer2W=textFieldAnswer2W.getText();
					currentAnswer=answer2W;
					System.out.println(currentSpeaker+" "+currentAnswer);
					//					currentToken=token2W;
					currentToken="";
					textFieldAnswer2W.setForeground(Color.BLUE);
					UI_skills.buttonSkill.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT){
					textFieldFilling2W.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					textFieldAnswer3W.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_UP){
					textFieldAnswer1W.requestFocusInWindow();
				}
			}
		});
		textFieldAnswer2W.addKeyListener(checkBatteryToken);
		textFieldAnswer3W.addKeyListener(commonNavi);
		textFieldAnswer3W.addKeyListener(skillsDecisionGenerationVP);
		textFieldAnswer3W.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					answer3W=textFieldAnswer3W.getText();
					currentAnswer=answer3W;
					System.out.println(currentSpeaker+" "+currentAnswer);
					//					currentToken=token3W;
					currentToken="";
					textFieldAnswer3W.setForeground(Color.BLUE);
					UI_skills.buttonSkill.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT){
					textFieldFilling3W.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_UP){
					textFieldAnswer2W.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					cmbDecisionSkillsW.requestFocusInWindow();
				}
			}
		});
		textFieldAnswer3W.addKeyListener(checkBatteryToken);

		JButton buttonYes1W=new JButton("Yes");
		buttonYes1W.addActionListener(skillsDecisionGenerationVP2);
		buttonYes1W.addActionListener(checkBatteryToken2);
		buttonYes1W.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling1W.getText();
				filling1W=currentFilling;
				textFieldFilling1W.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
				
				currentToken="";
				answer1W="yes";
				currentAnswer="yes";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer1W.setText(currentAnswer);
				textFieldAnswer1W.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonNo1W=new JButton("No");
		buttonNo1W.addActionListener(skillsDecisionGenerationVP2);
		buttonNo1W.addActionListener(checkBatteryToken2);
		buttonNo1W.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling1W.getText();
				filling1W=currentFilling;
				textFieldFilling1W.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
		
				currentToken="";
				answer1W="no";
				currentAnswer="no";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer1W.setText(currentAnswer);
				textFieldAnswer1W.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonNotMatter1W=new JButton("NM");
//		buttonNotMatter1W.setFont(new Font(Font.DIALOG, Font.BOLD,7));
		buttonNotMatter1W.addActionListener(skillsDecisionGenerationVP2);
		buttonNotMatter1W.addActionListener(checkBatteryToken2);
		buttonNotMatter1W.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling1W.getText();
				filling1W=currentFilling;
				textFieldFilling1W.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
		
				currentToken="";
				answer1W="not matter";
				currentAnswer="not matter";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer1W.setText(currentAnswer);
				textFieldAnswer1W.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonYes2W=new JButton("Yes");
		buttonYes2W.addActionListener(skillsDecisionGenerationVP2);
		buttonYes2W.addActionListener(checkBatteryToken2);
		buttonYes2W.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling2W.getText();
				filling2W=currentFilling;
				textFieldFilling2W.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
		
				currentToken="";
				answer2W="yes";
				currentAnswer="yes";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer2W.setText(currentAnswer);
				textFieldAnswer2W.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonNo2W=new JButton("No");
		buttonNo2W.addActionListener(skillsDecisionGenerationVP2);
		buttonNo2W.addActionListener(checkBatteryToken2);
		buttonNo2W.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling2W.getText();
				filling2W=currentFilling;
				textFieldFilling2W.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
		
				currentToken="";
				answer2W="no";
				currentAnswer="no";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer2W.setText(currentAnswer);
				textFieldAnswer2W.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonNotMatter2W=new JButton("NM");
//		buttonNotMatter2W.setFont(new Font(Font.DIALOG, Font.BOLD,7));
		buttonNotMatter2W.addActionListener(skillsDecisionGenerationVP2);
		buttonNotMatter2W.addActionListener(checkBatteryToken2);
		buttonNotMatter2W.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling2W.getText();
				filling2W=currentFilling;
				textFieldFilling2W.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
		
				currentToken="";
				answer2W="not matter";
				currentAnswer="not matter";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer2W.setText(currentAnswer);
				textFieldAnswer2W.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonYes3W=new JButton("Yes");
		buttonYes3W.addActionListener(skillsDecisionGenerationVP2);
		buttonYes3W.addActionListener(checkBatteryToken2);
		buttonYes3W.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling3W.getText();
				filling3W=currentFilling;
				textFieldFilling3W.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
		
				currentToken="";
				answer3W="yes";
				currentAnswer="yes";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer3W.setText(currentAnswer);
				textFieldAnswer3W.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonNo3W=new JButton("No");
		buttonNo3W.addActionListener(skillsDecisionGenerationVP2);
		buttonNo3W.addActionListener(checkBatteryToken2);
		buttonNo3W.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling3W.getText();
				filling3W=currentFilling;
				textFieldFilling3W.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
		
				currentToken="";
				answer3W="no";
				currentAnswer="no";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer3W.setText(currentAnswer);
				textFieldAnswer3W.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonNotMatter3W=new JButton("NM");
//		buttonNotMatter3W.setFont(new Font(Font.DIALOG, Font.BOLD,7));
		buttonNotMatter3W.addActionListener(skillsDecisionGenerationVP2);
		buttonNotMatter3W.addActionListener(checkBatteryToken2);
		buttonNotMatter3W.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling3W.getText();
				filling3W=currentFilling;
				textFieldFilling3W.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
		
				currentToken="";
				answer3W="not matter";
				currentAnswer="not matter";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer3W.setText(currentAnswer);
				textFieldAnswer3W.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		
		Box box0=Box.createVerticalBox();
		box0.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		Box box1=Box.createHorizontalBox();
		box1.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		Box box11=Box.createVerticalBox();
		Box box12=Box.createVerticalBox();
		Box box13=Box.createVerticalBox();
		Box box14=Box.createVerticalBox();
		Box box2=Box.createVerticalBox();
		box2.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		Box box21=Box.createHorizontalBox();
		Box box22=Box.createHorizontalBox();
		Box box23=Box.createHorizontalBox();
		Box box3=Box.createHorizontalBox();
		box3.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		Box box4=Box.createHorizontalBox();
		box4.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		box11.add(textFieldGearCardCode1W);
		box11.add(labelGearCard1W);
		box12.add(textFieldGearCardCode2W);
		box12.add(labelGearCard2W);
		box13.add(textFieldGearCardCode3W);
		box13.add(labelGearCard3W);
		box14.add(textFieldGearCardCode4W);
		box14.add(labelGearCard4W);
		box1.add(labelGearCard);
		box1.add(box11);
		box1.add(box12);
		box1.add(box13);
		box1.add(box14);
		box21.add(textFieldFilling1W);
		box21.add(buttonYes1W);
		box21.add(buttonNo1W);
		box21.add(buttonNotMatter1W);
		box21.add(textFieldAnswer1W);
		box22.add(textFieldFilling2W);
		box22.add(buttonYes2W);
		box22.add(buttonNo2W);
		box22.add(buttonNotMatter2W);
		box22.add(textFieldAnswer2W);
		box23.add(textFieldFilling3W);
		box23.add(buttonYes3W);
		box23.add(buttonNo3W);
		box23.add(buttonNotMatter3W);
		box23.add(textFieldAnswer3W);
		box2.add(box21);
		box2.add(box22);
		box2.add(box23);
		box3.add(labelSkill);
		box3.add(new JLabel("  "));
		box3.add(cmbDecisionSkillsW);
		box3.add(new JLabel("  "));
		box3.add(labelCard);
		box3.add(new JLabel("  "));
		box3.add(cmbDecisionCardsW);
		box4.add(new JLabel("No Skill"));
		box4.add(new JLabel("  "));
		box4.add(cmbDecisionNoSkillsW);
		box4.add(new JLabel("  "));
		box4.add(new JLabel("No Card"));
		box4.add(new JLabel("  "));
		box4.add(cmbDecisionNoCardsW);
		//		box0.add(labelPlayer);
		box0.add(radioButtonPlayerW1);
		box0.add(box1);
		box0.add(box2);
		box0.add(box3);
		box0.add(box4);
		p.add(box0);
		return p;
	}

	/**
	 * panel for virtual peer
	 * @return
	 */
	private static JPanel panelPlayerVP(){
		JPanel p=new JPanel();		
		//		JLabel labelPlayer=new JLabel("Player VP");
		radioButtonPlayerVP1=new JRadioButton("Player(VP)");
		radioButtonPlayerVP1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentPlayer="v";
				//crCurrentPlayer yuhan
				sendCurrentPlayer();
				formerGamePhase=currentGamePhase;
				if(!currentGamePhase.equals("GameMove")) {
					currentGamePhase="GameMove";
					//vrGamePhase yuhan GameMove
					sendGamePhase();
				}
				radioButtonGameMove.setSelected(true);
				radioButtonPlayerVP.setSelected(true);
			}
		});
		radioButtonPlayerVP1.addActionListener(clearBorder);
		JLabel labelGearCard=new JLabel("Gear");
		JLabel labelGearCard1VP=new JLabel("_______________");
		labelGearCard1VP.setFont(new Font(Font.DIALOG, Font.BOLD,10));
		JLabel labelGearCard2VP=new JLabel("_______________");
		labelGearCard2VP.setFont(new Font(Font.DIALOG, Font.BOLD,10));
		JLabel labelGearCard3VP=new JLabel("_______________");
		labelGearCard3VP.setFont(new Font(Font.DIALOG, Font.BOLD,10));
		JLabel labelGearCard4VP=new JLabel("_______________");
		labelGearCard4VP.setFont(new Font(Font.DIALOG, Font.BOLD,10));
		JLabel labelTokenCode=new JLabel("Code");
		JLabel labelToken=new JLabel("Token");
		JLabel labelFilling=new JLabel("Filling");
		JLabel labelAnswer=new JLabel("Answer");
		labelToken1VP=new JLabel("--------Token1--------");
		labelToken1VP.setFont(new Font(Font.DIALOG,Font.BOLD,9));
		labelToken2VP=new JLabel("--------Token2--------");
		labelToken2VP.setFont(new Font(Font.DIALOG,Font.BOLD,9));
		labelToken3VP=new JLabel("--------Token3--------");
		labelToken3VP.setFont(new Font(Font.DIALOG,Font.BOLD,9));
		JLabel labelSkill=new JLabel("   Skill   ");
		JLabel labelCard=new JLabel("   Card   ");
		textFieldGearCardCode1VP=new JTextField(1);
		textFieldGearCardCode2VP=new JTextField(1);
		textFieldGearCardCode3VP=new JTextField(1);
		textFieldGearCardCode4VP=new JTextField(1);
		textFieldTokenCode1VP=new JTextField(1);
		textFieldTokenCode2VP=new JTextField(1);
		textFieldTokenCode3VP=new JTextField(1);
		textFieldFilling1VP=new JTextField(3);
		textFieldFilling1VP.setForeground(Color.BLACK);
		textFieldFilling2VP=new JTextField(3);
		textFieldFilling2VP.setForeground(Color.BLACK);
		textFieldFilling3VP=new JTextField(3);
		textFieldFilling3VP.setForeground(Color.BLACK);
		textFieldAnswer1VP=new JTextField(3);
		textFieldAnswer1VP.setForeground(Color.BLACK);
		textFieldAnswer2VP=new JTextField(3);
		textFieldAnswer2VP.setForeground(Color.BLACK);
		textFieldAnswer3VP=new JTextField(3);
		textFieldAnswer3VP.setForeground(Color.BLACK);
		JTextField textFieldDecisionSkillVP=new JTextField(3);
		JTextField textFieldDecisionCardVP=new JTextField(3);
		textFieldDecisionSkillVP.addKeyListener(commonNavi);
		textFieldDecisionSkillVP.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					decisionSkillsVP=textFieldDecisionSkillVP.getText();
					currentSpeaker="v";
					System.out.println(currentSpeaker+": "+decisionSkillsVP);
					textFieldDecisionSkillVP.setText("");
				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					textFieldDecisionCardVP.requestFocusInWindow();
				}
			}
		});
		textFieldDecisionCardVP.addKeyListener(commonNavi);
		textFieldDecisionCardVP.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					decisionCardsVP=textFieldDecisionCardVP.getText();
					currentSpeaker="v";
					System.out.println(currentSpeaker+": "+decisionCardsVP);
					textFieldDecisionCardVP.setText("");
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT){
					textFieldDecisionSkillVP.requestFocusInWindow();
				}
			}
		});
		textFieldGearCardCode1VP.addKeyListener(commonNavi);
		textFieldGearCardCode1VP.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					String gearCardCode=textFieldGearCardCode1VP.getText().trim();
					//vrAllGearCards yuhan
					sendAllGearCards();
					try
					{
						Workbook book=Workbook.getWorkbook(new File(sourceFile2));
						Sheet sheet=book.getSheet(0);
						int rows=sheet.getRows();
						int cols=sheet.getColumns();
						for (int z=1;z<rows;z++)
						{
							String code;
							code=sheet.getCell(0, z).getContents().trim();
							if (gearCardCode.equals(code))
							{
								gearCard1VP=sheet.getCell(1,z).getContents().trim();
								labelGearCard1VP.setText(gearCard1VP);
								//								textFieldGearCardCode1VP.setText("");
							}										
						}
						textFieldGearCardCode2VP.requestFocusInWindow();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
				{
					textFieldGearCardCode2VP.requestFocusInWindow();
				}
			}
		});
		textFieldGearCardCode2VP.addKeyListener(commonNavi);
		textFieldGearCardCode2VP.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					String gearCardCode=textFieldGearCardCode2VP.getText().trim();
					//vrAllGearCards yuhan
					sendAllGearCards();
					try
					{
						Workbook book=Workbook.getWorkbook(new File(sourceFile2));
						Sheet sheet=book.getSheet(0);
						int rows=sheet.getRows();
						int cols=sheet.getColumns();
						for (int z=1;z<rows;z++)
						{
							String code;
							code=sheet.getCell(0, z).getContents().trim();
							if (gearCardCode.equals(code))
							{
								gearCard2VP=sheet.getCell(1,z).getContents().trim();
								labelGearCard2VP.setText(gearCard2VP);
								//								textFieldGearCardCode2VP.setText("");
							}										
						}
						textFieldGearCardCode3VP.requestFocusInWindow();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
				{
					textFieldGearCardCode3VP.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT)
				{
					textFieldGearCardCode1VP.requestFocusInWindow();
				}
			}
		});
		textFieldGearCardCode3VP.addKeyListener(commonNavi);
		textFieldGearCardCode3VP.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					String gearCardCode=textFieldGearCardCode3VP.getText().trim();
					//vrAllGearCards yuhan
					sendAllGearCards();
					try
					{
						Workbook book=Workbook.getWorkbook(new File(sourceFile2));
						Sheet sheet=book.getSheet(0);
						int rows=sheet.getRows();
						int cols=sheet.getColumns();
						for (int z=1;z<rows;z++)
						{
							String code;
							code=sheet.getCell(0, z).getContents().trim();
							if (gearCardCode.equals(code))
							{
								gearCard3VP=sheet.getCell(1,z).getContents().trim();
								labelGearCard3VP.setText(gearCard3VP);
								//								textFieldGearCardCode3VP.setText("");
							}										
						}
						textFieldGearCardCode4VP.requestFocusInWindow();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
				{
					textFieldGearCardCode4VP.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT)
				{
					textFieldGearCardCode2VP.requestFocusInWindow();
				}
			}
		});
		textFieldGearCardCode4VP.addKeyListener(commonNavi);
		textFieldGearCardCode4VP.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					String gearCardCode=textFieldGearCardCode4VP.getText().trim();
					//vrAllGearCards yuhan
					sendAllGearCards();
					try
					{
						Workbook book=Workbook.getWorkbook(new File(sourceFile2));
						Sheet sheet=book.getSheet(0);
						int rows=sheet.getRows();
						int cols=sheet.getColumns();
						for (int z=1;z<rows;z++)
						{
							String code;
							code=sheet.getCell(0, z).getContents().trim();
							if (gearCardCode.equals(code))
							{
								gearCard4VP=sheet.getCell(1,z).getContents().trim();
								labelGearCard4VP.setText(gearCard4VP);
								//								textFieldGearCardCode4VP.setText("");
							}										
						}
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT)
				{
					textFieldGearCardCode3VP.requestFocusInWindow();
				}
			}
		});
		textFieldGearCardCode1VP.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent arg0){
				textFieldGearCardCode1VP.selectAll();
				//				  this.getUIClassID().selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldGearCardCode2VP.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent arg0){
				textFieldGearCardCode2VP.selectAll();
				//				  this.getUIClassID().selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldGearCardCode3VP.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent arg0){
				textFieldGearCardCode3VP.selectAll();
				//				  this.getUIClassID().selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldGearCardCode4VP.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent arg0){
				textFieldGearCardCode4VP.selectAll();
				//				  this.getUIClassID().selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldTokenCode1VP.addKeyListener(commonNavi);
		textFieldTokenCode1VP.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent arg0){
				textFieldTokenCode1VP.selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldTokenCode1VP.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					String tokenCode=textFieldTokenCode1VP.getText().trim();
					try
					{
						Workbook book=Workbook.getWorkbook(new File(sourceFile1));
						Sheet sheet=book.getSheet(0);
						int rows=sheet.getRows();
						int cols=sheet.getColumns();
						for (int z=1;z<rows;z++)
						{
							String code;
							code=sheet.getCell(0, z).getContents().trim();
							if (tokenCode.equals(code))
							{
								token1VP=sheet.getCell(1,z).getContents().trim();
								labelToken1VP.setText(token1VP);
								//								textFieldTokenCode1VP.setText("");
							}										
						}
						textFieldTokenCode2VP.requestFocusInWindow();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
				{
					textFieldFilling1VP.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN)
				{
					textFieldTokenCode2VP.requestFocusInWindow();
				}
			}
		});
		textFieldTokenCode2VP.addKeyListener(commonNavi);
		textFieldTokenCode2VP.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent arg0){
				textFieldTokenCode2VP.selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldTokenCode2VP.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					String tokenCode=textFieldTokenCode2VP.getText().trim();
					try
					{
						Workbook book=Workbook.getWorkbook(new File(sourceFile1));
						Sheet sheet=book.getSheet(0);
						int rows=sheet.getRows();
						int cols=sheet.getColumns();
						for (int z=1;z<rows;z++)
						{
							String code;
							code=sheet.getCell(0, z).getContents().trim();
							if (tokenCode.equals(code))
							{
								token2VP=sheet.getCell(1,z).getContents().trim();
								labelToken2VP.setText(token2VP);
								//								textFieldTokenCode2VP.setText("");
							}										
						}
						textFieldTokenCode3VP.requestFocusInWindow();
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
				{
					textFieldFilling2VP.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN)
				{
					textFieldTokenCode3VP.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_UP){
					textFieldTokenCode1VP.requestFocusInWindow();
				}
			}
		});
		textFieldTokenCode3VP.addKeyListener(commonNavi);
		textFieldTokenCode3VP.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent arg0){
				textFieldTokenCode3VP.selectAll();
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});

		textFieldTokenCode3VP.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					String tokenCode=textFieldTokenCode3VP.getText().trim();
					//vrVPQuestionToken yuhan
					sendVPQuestionToken();
					try
					{
						Workbook book=Workbook.getWorkbook(new File(sourceFile1));
						Sheet sheet=book.getSheet(0);
						int rows=sheet.getRows();
						int cols=sheet.getColumns();
						for (int z=1;z<rows;z++)
						{
							String code;
							code=sheet.getCell(0, z).getContents().trim();
							if (tokenCode.equals(code))
							{
								token3VP=sheet.getCell(1,z).getContents().trim();
								labelToken3VP.setText(token3VP);
								//								textFieldTokenCode3VP.setText("");
							}										
						}
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
				{
					textFieldFilling3VP.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_UP){
					textFieldTokenCode2VP.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					textFieldDecisionSkillVP.requestFocusInWindow();
				}
			}
		});
		textFieldFilling1VP.addKeyListener(commonNavi);
		textFieldFilling1VP.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){
				formerGamePhase=currentGamePhase;
				if(currentGamePhase.equals("QuestionAndAnswer")==false){
					currentGamePhase="QuestionAndAnswer";
					//vrGamePhase yuhan QA
					sendGamePhase();
					timer0.start();
				}
				radioButtonQuestionAndAnswer.setSelected(true);
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldFilling1VP.addFocusListener(clearBorder2);
		textFieldFilling1VP.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					filling1VP=textFieldFilling1VP.getText();
					currentFilling=filling1VP;
					currentSpeaker="v";
					System.out.println(currentSpeaker+" "+currentFilling);
					UI_skills.textFieldKeyword.setText(currentFilling);
					textFieldFilling1VP.setForeground(Color.BLUE);
					textFieldAnswer1VP.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT){
					textFieldTokenCode1VP.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					textFieldAnswer1VP.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					textFieldFilling2VP.requestFocusInWindow();
				}
			}
		});
		textFieldFilling2VP.addKeyListener(commonNavi);
		textFieldFilling2VP.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){
				formerGamePhase=currentGamePhase;
				if(currentGamePhase.equals("QuestionAndAnswer")==false){
					currentGamePhase="QuestionAndAnswer";
					//vrGamePhase yuhan QA
					sendGamePhase();
					timer0.start();
				}
				radioButtonQuestionAndAnswer.setSelected(true);
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldFilling2VP.addFocusListener(clearBorder2);
		textFieldFilling2VP.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					filling2VP=textFieldFilling2VP.getText();
					currentFilling=filling2VP;
					currentSpeaker="v";
					System.out.println(currentSpeaker+" "+currentFilling);
					UI_skills.textFieldKeyword.setText(currentFilling);
					textFieldFilling2VP.setForeground(Color.BLUE);
					textFieldAnswer2VP.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT){
					textFieldTokenCode2VP.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					textFieldAnswer2VP.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					textFieldFilling3VP.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_UP){
					textFieldFilling1VP.requestFocusInWindow();
				}
			}
		});
		textFieldFilling3VP.addKeyListener(commonNavi);
		textFieldFilling3VP.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e){
				formerGamePhase=currentGamePhase;
				if(currentGamePhase.equals("QuestionAndAnswer")==false){
					currentGamePhase="QuestionAndAnswer";
					//vrGamePhase yuhan QA
					sendGamePhase();
					timer0.start();
				}
				radioButtonQuestionAndAnswer.setSelected(true);
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub

			}
		});
		textFieldFilling3VP.addFocusListener(clearBorder2);
		textFieldFilling3VP.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					filling3VP=textFieldFilling3VP.getText();
					currentFilling=filling3VP;
					currentSpeaker="v";
					System.out.println(currentSpeaker+" "+currentFilling);
					UI_skills.textFieldKeyword.setText(currentFilling);
					textFieldFilling3VP.setForeground(Color.BLUE);
					textFieldAnswer3VP.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT){
					textFieldTokenCode3VP.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					textFieldAnswer3VP.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_UP){
					textFieldFilling2VP.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					textFieldDecisionSkillVP.requestFocusInWindow();
				}
			}
		});
		textFieldAnswer1VP.addKeyListener(commonNavi);
		textFieldAnswer1VP.addKeyListener(skillsDecisionGenerationVP);
		textFieldAnswer1VP.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					answer1VP=textFieldAnswer1VP.getText();
					currentAnswer=answer1VP;
					System.out.println(currentSpeaker+" "+currentAnswer);
					currentToken=token1VP;
					textFieldAnswer1VP.setForeground(Color.BLUE);
					UI_skills.buttonSkill.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT){
					textFieldFilling1VP.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					textFieldAnswer2VP.requestFocusInWindow();
				}
			}
		});
		textFieldAnswer1VP.addKeyListener(checkBatteryToken);
		textFieldAnswer2VP.addKeyListener(commonNavi);
		textFieldAnswer2VP.addKeyListener(skillsDecisionGenerationVP);
		textFieldAnswer2VP.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					answer2VP=textFieldAnswer2VP.getText();
					currentAnswer=answer2VP;
					System.out.println(currentSpeaker+" "+currentAnswer);
					currentToken=token2VP;
					textFieldAnswer2VP.setForeground(Color.BLUE);
					UI_skills.buttonSkill.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT){
					textFieldFilling2VP.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					textFieldAnswer3VP.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_UP){
					textFieldAnswer1VP.requestFocusInWindow();
				}
			}
		});
		textFieldAnswer2VP.addKeyListener(checkBatteryToken);
		textFieldAnswer3VP.addKeyListener(commonNavi);
		textFieldAnswer3VP.addKeyListener(skillsDecisionGenerationVP);
		textFieldAnswer3VP.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					answer3VP=textFieldAnswer3VP.getText();
					currentAnswer=answer3VP;
					System.out.println(currentSpeaker+" "+currentAnswer);
					currentToken=token3VP;
					textFieldAnswer3VP.setForeground(Color.BLUE);
					UI_skills.buttonSkill.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT){
					textFieldFilling3VP.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_UP){
					textFieldAnswer2VP.requestFocusInWindow();
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					textFieldDecisionSkillVP.requestFocusInWindow();
				}
			}
		});
		textFieldAnswer3VP.addKeyListener(checkBatteryToken);

		JButton buttonYes1VP=new JButton("Yes");
		buttonYes1VP.addActionListener(skillsDecisionGenerationVP2);
		buttonYes1VP.addActionListener(checkBatteryToken2);
		buttonYes1VP.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling1VP.getText();
				filling1VP=currentFilling;
				textFieldFilling1VP.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
		
				currentToken="";
				answer1VP="yes";
				currentAnswer="yes";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer1VP.setText(currentAnswer);
				textFieldAnswer1VP.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonNo1VP=new JButton("No");
		buttonNo1VP.addActionListener(skillsDecisionGenerationVP2);
		buttonNo1VP.addActionListener(checkBatteryToken2);
		buttonNo1VP.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling1VP.getText();
				filling1VP=currentFilling;
				textFieldFilling1VP.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
	
				currentToken="";
				answer1VP="no";
				currentAnswer="no";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer1VP.setText(currentAnswer);
				textFieldAnswer1VP.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonNotMatter1VP=new JButton("NM");
//		buttonNotMatter1VP.setFont(new Font(Font.DIALOG, Font.BOLD,7));
		buttonNotMatter1VP.addActionListener(skillsDecisionGenerationVP2);
		buttonNotMatter1VP.addActionListener(checkBatteryToken2);
		buttonNotMatter1VP.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling1VP.getText();
				filling1VP=currentFilling;
				textFieldFilling1VP.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
	
				currentToken="";
				answer1VP="not matter";
				currentAnswer="not matter";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer1VP.setText(currentAnswer);
				textFieldAnswer1VP.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonYes2VP=new JButton("Yes");
		buttonYes2VP.addActionListener(skillsDecisionGenerationVP2);
		buttonYes2VP.addActionListener(checkBatteryToken2);
		buttonYes2VP.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling2VP.getText();
				filling2VP=currentFilling;
				textFieldFilling2VP.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
	
				currentToken="";
				answer2VP="yes";
				currentAnswer="yes";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer2VP.setText(currentAnswer);
				textFieldAnswer2VP.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonNo2VP=new JButton("No");
		buttonNo2VP.addActionListener(skillsDecisionGenerationVP2);
		buttonNo2VP.addActionListener(checkBatteryToken2);
		buttonNo2VP.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling2VP.getText();
				filling2VP=currentFilling;
				textFieldFilling2VP.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
	
				currentToken="";
				answer2VP="no";
				currentAnswer="no";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer2VP.setText(currentAnswer);
				textFieldAnswer2VP.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonNotMatter2VP=new JButton("NM");
//		buttonNotMatter2VP.setFont(new Font(Font.DIALOG, Font.BOLD,7));
		buttonNotMatter2VP.addActionListener(skillsDecisionGenerationVP2);
		buttonNotMatter2VP.addActionListener(checkBatteryToken2);
		buttonNotMatter2VP.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling2VP.getText();
				filling2VP=currentFilling;
				textFieldFilling2VP.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
	
				currentToken="";
				answer2VP="not matter";
				currentAnswer="not matter";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer2VP.setText(currentAnswer);
				textFieldAnswer2VP.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonYes3VP=new JButton("Yes");
		buttonYes3VP.addActionListener(skillsDecisionGenerationVP2);
		buttonYes3VP.addActionListener(checkBatteryToken2);
		buttonYes3VP.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling3VP.getText();
				filling3VP=currentFilling;
				textFieldFilling3VP.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
	
				currentToken="";
				answer3VP="yes";
				currentAnswer="yes";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer3VP.setText(currentAnswer);
				textFieldAnswer3VP.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonNo3VP=new JButton("No");
		buttonNo3VP.addActionListener(skillsDecisionGenerationVP2);
		buttonNo3VP.addActionListener(checkBatteryToken2);
		buttonNo3VP.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling3VP.getText();
				filling3VP=currentFilling;
				textFieldFilling3VP.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
	
				currentToken="";
				answer3VP="no";
				currentAnswer="no";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer3VP.setText(currentAnswer);
				textFieldAnswer3VP.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		JButton buttonNotMatter3VP=new JButton("NM");
//		buttonNotMatter3VP.setFont(new Font(Font.DIALOG, Font.BOLD,7));
		buttonNotMatter3VP.addActionListener(skillsDecisionGenerationVP2);
		buttonNotMatter3VP.addActionListener(checkBatteryToken2);
		buttonNotMatter3VP.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				currentFilling=textFieldFilling3VP.getText();
				filling3VP=currentFilling;
				textFieldFilling3VP.setForeground(Color.blue);
				System.out.println(currentSpeaker+" "+currentFilling);
	
				currentToken="";
				answer3VP="not matter";
				currentAnswer="not matter";
				System.out.println(currentSpeaker+" "+currentAnswer);
				textFieldAnswer3VP.setText(currentAnswer);
				textFieldAnswer3VP.setForeground(Color.blue);
				UI_skills.buttonSkill.requestFocusInWindow();
			}
		});
		
		Box box0=Box.createVerticalBox();
		box0.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		Box box1=Box.createHorizontalBox();
		box1.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		Box box11=Box.createVerticalBox();
		Box box12=Box.createVerticalBox();
		Box box13=Box.createVerticalBox();
		Box box14=Box.createVerticalBox();
		Box box2=Box.createVerticalBox();
		box2.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		Box box21=Box.createHorizontalBox();
		Box box22=Box.createHorizontalBox();
		Box box23=Box.createHorizontalBox();
		Box box3=Box.createHorizontalBox();
		box3.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		box11.add(textFieldGearCardCode1VP);
		box11.add(labelGearCard1VP);
		box12.add(textFieldGearCardCode2VP);
		box12.add(labelGearCard2VP);
		box13.add(textFieldGearCardCode3VP);
		box13.add(labelGearCard3VP);
		box14.add(textFieldGearCardCode4VP);
		box14.add(labelGearCard4VP);
		box1.add(labelGearCard);
		box1.add(box11);
		box1.add(box12);
		box1.add(box13);
		box1.add(box14);
		box21.add(textFieldTokenCode1VP);
		box21.add(labelToken1VP);
		box21.add(textFieldFilling1VP);
		box21.add(buttonYes1VP);
		box21.add(buttonNo1VP);
		box21.add(buttonNotMatter1VP);
		box21.add(textFieldAnswer1VP);
		box22.add(textFieldTokenCode2VP);
		box22.add(labelToken2VP);
		box22.add(textFieldFilling2VP);
		box22.add(buttonYes2VP);
		box22.add(buttonNo2VP);
		box22.add(buttonNotMatter2VP);
		box22.add(textFieldAnswer2VP);
		box23.add(textFieldTokenCode3VP);
		box23.add(labelToken3VP);
		box23.add(textFieldFilling3VP);
		box23.add(buttonYes3VP);
		box23.add(buttonNo3VP);
		box23.add(buttonNotMatter3VP);
		box23.add(textFieldAnswer3VP);
		box2.add(box21);
		box2.add(box22);
		box2.add(box23);
		box3.add(labelSkill);
		box3.add(textFieldDecisionSkillVP);
		box3.add(labelCard);
		box3.add(textFieldDecisionCardVP);
		//		box0.add(labelPlayer);
		box0.add(radioButtonPlayerVP1);
		box0.add(box1);
//		box0.add(panelTokens);
		box0.add(box2);
//		box0.add(box3);
		p.add(box0);
		return p;
	}

	/**
	 * panel for the live video from webcam
	 * @return
	 */
	private static JPanel getVideo()
	{
		JPanel p=new JPanel();
		Webcam webcam = Webcam.getDefault();
		webcam.setViewSize(WebcamResolution.VGA.getSize());
		WebcamPanel panel = new WebcamPanel(webcam);
		panel.setFPSDisplayed(true);
		panel.setDisplayDebugInfo(true);
		panel.setImageSizeDisplayed(true);
		panel.setMirrored(true);// because the webcam is set up below the desktop
//		panel.setMirrored(false); //if the webcam is set up above the desktop, then we should setMirrored(false)
		p.add(panel);
		return p;
	}

/**
 * this is the panel for result announcement. room result and antidote result can be updated here
 * @return
 */
	private static JPanel panelResult(){
		JPanel p=new JPanel();
		p.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		JLabel labelRoom=new JLabel("Room");
		JLabel labelAntidote=new JLabel("Antidote");
		checkRoomWin=new JCheckBox("Win");
		checkRoomLose=new JCheckBox("Lose");
		checkAntidoteWin=new JCheckBox("Win");
		checkAntidoteLose=new JCheckBox("Lose");
		checkRoomWin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				checkRoomLose.setSelected(false);
				if((currentGamePhase.equals("ResultAnnouncing"))==false){
					formerGamePhase=currentGamePhase;
					currentGamePhase="ResultAnnouncing";
					//vrGamePhase yuhan ResultAnnouncing
					sendGamePhase();
				}
				radioButtonResultAnnouncing.setSelected(true);
				roomResult=true;
				//vrRoomResult yuhan
				sendRoomResult();
			}
		});
		checkRoomLose.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				checkRoomWin.setSelected(false);
				if((currentGamePhase.equals("ResultAnnouncing"))==false){
					formerGamePhase=currentGamePhase;
					currentGamePhase="ResultAnnouncing";
					//vrGamePhase yuhan ResultAnnouncing
					sendGamePhase();
				}
				radioButtonResultAnnouncing.setSelected(true);
				roomResult=false;
				//vrRoomResult yuhan
				sendRoomResult();
			}
		});
		checkAntidoteWin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				checkAntidoteLose.setSelected(false);
				if((currentGamePhase.equals("ResultAnnouncing"))==false){
					formerGamePhase=currentGamePhase;
					currentGamePhase="ResultAnnouncing";
					//vrGamePhase yuhan ResultAnnouncing
					sendGamePhase();
				}
				radioButtonResultAnnouncing.setSelected(true);
				antidoteResult=true;
				//vrAntidoteDie yuhan
				sendAntidoteDie();
			}
		});
		checkAntidoteLose.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				checkAntidoteWin.setSelected(false);
				if((currentGamePhase.equals("ResultAnnouncing"))==false){
					formerGamePhase=currentGamePhase;
					currentGamePhase="ResultAnnouncing";
					//vrGamePhase yuhan ResultAnnouncing
					sendGamePhase();
				}
				radioButtonResultAnnouncing.setSelected(true);
				antidoteResult=false;
				//vrAntidoteDie yuhan
				sendAntidoteDie();
			}
		});
		Box box0=Box.createHorizontalBox();
		Box box1=Box.createHorizontalBox();
		box1.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		Box box2=Box.createHorizontalBox();
		box2.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		box1.add(labelRoom);
		box1.add(checkRoomWin);
		box1.add(checkRoomLose);
		box2.add(labelAntidote);
		box2.add(checkAntidoteWin);
		box2.add(checkAntidoteLose);
		box0.add(box1);
		box0.add(new JLabel("    "));
		box0.add(box2);
		p.add(box0);
		return p;
	}

	UI_part1()
	{
		this.setTitle("SCIPR WoZ UI-part1");
		JPanel p=new JPanel();
		JScrollPane scrollPane=new JScrollPane();
		panelGamePhase=panelGamePhase();
		panelCurrentPlayer=panelCurrentPlayer();
		panelDieValue=panelDieValue();
		panelRoomSelection=panelRoomSelection();
		panelFinalDecision=panelFinalDecision();
		panelBehaviorsToVP=panelBehaviorsToVP();
		panelPlayerA=panelPlayerA();
		panelPlayerW=panelPlayerW();
		panelPlayerD=panelPlayerD();
		panelPlayerVP=panelPlayerVP();
		panelResult=panelResult();
		ButtonGroup playerGroup=new ButtonGroup();
		playerGroup.add(radioButtonPlayerA1);
		playerGroup.add(radioButtonPlayerD1);
		playerGroup.add(radioButtonPlayerVP1);
		playerGroup.add(radioButtonPlayerW1);
		JPanel panelCheatSheet=panelCheatSheet();
		JButton buttonClear=new JButton("Clear All");//button to renew all the information except each player's gear cards left
		buttonClear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				timer0.stop();
				currentGamePhase="";
				formerGamePhase="";
				currentPlayer="";
				dieValue=0;
				roomOptions="";
				roomSelected="";
				finalDecision1="";
				finalDecision2="";
				finalDecision3="";
				allAgree=false;
				currentToken="";
				currentFilling="";
				currentAnswer="";
				currentSpeaker="";      
				gearCard1A="";
				gearCard2A="";
				gearCard3A="";
				gearCard4A="";
				gearCard1W="";
				gearCard2W="";
				gearCard3W="";
				gearCard4W="";
				gearCard1D="";
				gearCard2D="";
				gearCard3D="";
				gearCard4D="";
				gearCard1VP="";
				gearCard2VP="";
				gearCard3VP="";
				gearCard4VP="";
				token1VP="";
				token2VP="";
				token3VP="";
				filling1VP="";
				filling2VP="";
				filling3VP="";
				filling1A="";
				filling2A="";
				filling3A="";
				filling1W="";
				filling2W="";
				filling3W="";
				filling1D="";
				filling2D="";
				filling3D="";
				answer1VP="";
				answer2VP="";
				answer3VP="";
				answer1A="";
				answer2A="";
				answer3A="";
				answer1W="";
				answer2W="";
				answer3W="";
				answer1D="";
				answer2D="";
				answer3D="";
				potentialSkillsNum=0;
				contentToVP="";
				decisionSkillsVP="";
				decisionSkillsA="";
				decisionSkillsW="";
				decisionSkillsD="";
				decisionCardsVP="";
				decisionCardsA="";
				decisionCardsW="";
				decisionCardsD="";
				panelResult.setBorder(BorderFactory.createLineBorder(Color.white));
				checkRoomWin.setSelected(false);
				checkAntidoteWin.setSelected(false);
				checkRoomLose.setSelected(false);
				checkAntidoteLose.setSelected(false);
				panelGamePhase.setBackground(Color.white);
				textFieldRoomOptions.setForeground(Color.black);
				textFieldRoomSelected.setText("");
				textFieldRoomSelected.setForeground(Color.black);
				textFieldFilling1A.setText("");
				textFieldFilling1A.setForeground(Color.black);
				textFieldFilling2A.setText("");
				textFieldFilling2A.setForeground(Color.black);
				textFieldFilling3A.setText("");
				textFieldFilling3A.setForeground(Color.black);
				textFieldAnswer1A.setText("");
				textFieldAnswer1A.setForeground(Color.BLACK);
				textFieldAnswer2A.setText("");
				textFieldAnswer2A.setForeground(Color.BLACK);
				textFieldAnswer3A.setText("");
				textFieldAnswer3A.setForeground(Color.BLACK);
				textFieldFilling1W.setText("");
				textFieldFilling1W.setForeground(Color.black);
				textFieldFilling2W.setText("");
				textFieldFilling2W.setForeground(Color.black);
				textFieldFilling3W.setText("");
				textFieldFilling3W.setForeground(Color.black);
				textFieldAnswer1W.setText("");
				textFieldAnswer1W.setForeground(Color.BLACK);
				textFieldAnswer2W.setText("");
				textFieldAnswer2W.setForeground(Color.BLACK);
				textFieldAnswer3W.setText("");
				textFieldAnswer3W.setForeground(Color.BLACK);
				textFieldFilling1D.setText("");
				textFieldFilling1D.setForeground(Color.black);
				textFieldFilling2D.setText("");
				textFieldFilling2D.setForeground(Color.black);
				textFieldFilling3D.setText("");
				textFieldFilling3D.setForeground(Color.black);
				textFieldAnswer1D.setText("");
				textFieldAnswer1D.setForeground(Color.BLACK);
				textFieldAnswer2D.setText("");
				textFieldAnswer2D.setForeground(Color.BLACK);
				textFieldAnswer3D.setText("");
				textFieldAnswer3D.setForeground(Color.BLACK);
				textFieldFilling1VP.setText("");
				textFieldFilling2VP.setText("");
				textFieldFilling3VP.setText("");
				textFieldAnswer1VP.setText("");
				textFieldAnswer1VP.setForeground(Color.BLACK);
				textFieldAnswer2VP.setText("");
				textFieldAnswer2VP.setForeground(Color.BLACK);
				textFieldAnswer3VP.setText("");
				textFieldAnswer3VP.setForeground(Color.BLACK);
				textFieldTokenCode1VP.setText("");
				textFieldTokenCode2VP.setText("");
				textFieldTokenCode3VP.setText("");
				textFieldFinal1.setText("");
				textFieldFinal2.setText("");
				textFieldFinal3.setText("");
				textFieldFinal1.setForeground(Color.black);
				textFieldFinal3.setForeground(Color.black);
				textFieldFinal2.setForeground(Color.black);
				labelToken1VP.setText("--------Token1--------");
				labelToken1VP.setFont(new Font(Font.DIALOG,Font.BOLD,9));
				labelToken2VP.setText("--------Token2--------");
				labelToken2VP.setFont(new Font(Font.DIALOG,Font.BOLD,9));
				labelToken3VP.setText("--------Token3--------");
				labelToken3VP.setFont(new Font(Font.DIALOG,Font.BOLD,9));
				radioButtonInitialization.setSelected(true);
				//				areaQuestionAnswer.setText("");
				//				areaSkillsDecisionVP.setText("");
				//				areaDecision.setText("");

			}
		});
		/**
		 * The function of text field used to navigate to different players' information panel
		 */
		textFieldNavigation=new JTextField(1);
		textFieldNavigation.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_A){
					textFieldFilling1A.requestFocusInWindow();
					textFieldNavigation.setText("");
				}
				if(e.getKeyCode()==KeyEvent.VK_W){
					textFieldFilling1W.requestFocusInWindow();
					textFieldNavigation.setText("");
				}
				if(e.getKeyCode()==KeyEvent.VK_D){
					textFieldFilling1D.requestFocusInWindow();
					textFieldNavigation.setText("");
				}
				if(e.getKeyCode()==KeyEvent.VK_S){
					textFieldFilling1VP.requestFocusInWindow();
					textFieldNavigation.setText("");
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		//Set main layout_v1
		//		Box box0=Box.createVerticalBox();
		//		Box box1=Box.createHorizontalBox();
		//		Box box2=Box.createHorizontalBox();
		//		Box box21=Box.createVerticalBox();
		//		Box box22=Box.createVerticalBox();
		//		Box box23=Box.createVerticalBox();
		//		box1.add(buttonClear);	
		//		box1.add(panelCurrentPlayer);
		//		box1.add(panelDieValue);
		//		box1.add(panelRoomSelection);
		//		box1.add(panelFinalDecision);
		//		box21.add(getVideo());
		//		box21.add(panelDisplay);
		//		box22.add(panelPlayerA);
		//		box22.add(panelPlayerW);
		//		box22.add(panelPlayerD);
		//		box22.add(panelPlayerVP);
		//		box23.add(panelSkillsDecisionGenerationVP);
		//		box23.add(panelCheatSheet);
		//		box23.add(panelBehaviorsToVP);
		//		box2.add(box21);
		//		box2.add(box22);
		//		box2.add(box23);
		//		box0.add(panelGamePhase);
		//		box0.add(box1);
		//		box0.add(box2);
		//		p.add(box0);
		//		this.add(p);
		
		//set main layout_v2
		Box box0=Box.createVerticalBox();
		Box box1=Box.createHorizontalBox();
		Box box2=Box.createHorizontalBox();
		Box box3=Box.createHorizontalBox();
		Box box31=Box.createVerticalBox();
		Box box32=Box.createVerticalBox();
		Box box33=Box.createVerticalBox();
		box1.add(buttonClear);	
		box1.add(panelGamePhase);
//		box1.add(panelResult);
		box2.add(textFieldNavigation);
		box2.add(panelCurrentPlayer);
		box2.add(panelDieValue);
		box2.add(panelRoomSelection);
		box2.add(panelFinalDecision);
		box2.add(panelResult);
		box31.add(panelPlayerA);
		//		box31.add(panelDisplay);
		box31.add(new JPanel());
		box31.add(panelPlayerVP);
		box31.add(panelBehaviorsToVP);
//		box31.add(panelResult);
		box32.add(panelPlayerW);
		box32.add(getVideo());
		//		box32.add(panelPlayerVP);
		box33.add(panelPlayerD);
		box33.add(panelSkillsDecisionGenerationVP);
		box33.add(panelCheatSheet);
		//		box33.add(panelBehaviorsToVP);
		box3.add(box31);
		box3.add(box32);
		box3.add(box33);

		box0.add(box1);
		box0.add(box2);
		box0.add(box3);
		p.add(box0);
		scrollPane.setViewportView(p);
		this.add(scrollPane);

//		//set main layout_v3
//				Box box0=Box.createVerticalBox();
//				Box box1=Box.createHorizontalBox();
//				Box box2=Box.createHorizontalBox();
//				Box box3=Box.createHorizontalBox();
//				Box box4=Box.createHorizontalBox();
//				Box box41=Box.createVerticalBox();
//				Box box42=Box.createVerticalBox();
//				box1.add(buttonClear);	
//				box1.add(panelGamePhase);
//				box1.add(panelResult);
//				box2.add(panelCurrentPlayer);
//				box2.add(panelDieValue);
//				box2.add(panelRoomSelection);
//				box2.add(panelFinalDecision);
//				box3.add(panelPlayerA);
//				box3.add(panelPlayerW);
//				box3.add(panelPlayerD);
//				box41.add(panelPlayerVP);
//				box41.add(panelBehaviorsToVP);
//				box42.add(panelSkillsDecisionGenerationVP);
//				box42.add(panelCheatSheet);
//				box4.add(box41);
//				box4.add(getVideo());
//				box4.add(box42);
//				box0.add(box1);
//				box0.add(box2);
//				box0.add(box3);
//				box0.add(box4);
//				p.add(box0);
//				scrollPane.setViewportView(p);
//				this.add(scrollPane);

		/**
		 * Create the menu bar to update the room result and the antidote result
		 */
		JMenuBar jmb1=new JMenuBar();
		JMenu jm1=new JMenu("Curiosity Reasoner");
		JMenuItem item11=new JMenuItem("On");
		JMenuItem item12=new JMenuItem("Off");
		jm1.add(item11);
		jm1.addSeparator();
		jm1.add(item12);

		JMenu jm2=new JMenu("Room Result");
		JMenuItem item21=new JMenuItem("Win");
		item21.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				roomResult=true;
			}
		});
		JMenuItem item22=new JMenuItem("Lose");
		item22.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				roomResult=false;
			}
		});
		jm2.add(item21);
		jm2.addSeparator();
		jm2.add(item22);

		JMenu jm3=new JMenu("Antidote Result");
		JMenuItem item31=new JMenuItem("Win");
		item31.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				if(roomResult==true)
				{
					antidoteResult=true;
				}
			}
		});
		JMenuItem item32=new JMenuItem("Lose");
		item32.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				if(roomResult==true)
				{
					antidoteResult=false;
				}
			}
		});
		jm3.add(item31);
		jm3.addSeparator();
		jm3.add(item32);
		jmb1.add(jm1);
		jmb1.add(jm2);
		jmb1.add(jm3);
		this.setJMenuBar(jmb1);	
		this.setSize(WIDTH,HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * Initialize the senders and subscribers of VHMsg
	 */
	public void initializeVHMsg() {
		System.setProperty("VHMSG_SERVER", Config.VHMSG_SERVER_URL);
		vhmsgSubscriber = new VHMsg();
		vhmsgSubscriber.openConnection();
		vhmsgSubscriber.enableImmediateMethod();
		vhmsgSubscriber.addMessageListener( this );

		vrValueOfDieSender = new VhmsgSender("vrValueOfDie");
		vrAllGearCardsSender = new VhmsgSender("vrAllGearCards");
		vrCurrentPlayerSender = new VhmsgSender("vrCurrentPlayer");
		vrChoicesForThreatSender = new VhmsgSender("vrChoicesForThreat");
		vrChosenThreatSender = new VhmsgSender("vrChosenThreat");
		vrVPQuestionTokenSender = new VhmsgSender("vrVPQuestionToken");
		vrGamePhaseSender = new VhmsgSender("vrGamePhase");
		vrQAWithSkillDecisionSender = new VhmsgSender("vrQAWithSkillDecision");
		vrSuggestedSkillSender = new VhmsgSender("vrSuggestedSkill");
		vrSuggestedCardSender = new VhmsgSender("vrSuggestedCard");
		vrFinalChosenCardsSender = new VhmsgSender("vrFinalChosenCards");
		vrRoomResultSender = new VhmsgSender("vrRoomResult");
		vrAntidoteDieSender = new VhmsgSender("vrAntidoteDie");
		vrOthersAgreementSender = new VhmsgSender("vrOthersAgreement");
	}

	/**
	 * Listen to the message events
	 * @param e MessageEvent
	 */
	@Override
	public void messageAction(MessageEvent e) {
		System.out.println(e.toString());
		String[] tokens = e.toString().split(" ");
	}

	public static void sendValueOfDie() {
		vrValueOfDieSender.sendMessage(Integer.toString(dieValue));
	}

	public static void sendAllGearCards() {
		String allGearCards = "";
		if (!textFieldGearCardCode1VP.getText().trim().equals("")) {
			allGearCards = allGearCards + textFieldGearCardCode1VP.getText().trim() + " ";
		} else {
			allGearCards = allGearCards + "0 ";
		}
		if (!textFieldGearCardCode2VP.getText().trim().equals("")) {
			allGearCards = allGearCards + textFieldGearCardCode2VP.getText().trim() + " ";
		} else {
			allGearCards = allGearCards + "0 ";
		}
		if (!textFieldGearCardCode3VP.getText().trim().equals("")) {
			allGearCards = allGearCards + textFieldGearCardCode3VP.getText().trim() + " ";
		} else {
			allGearCards = allGearCards + "0 ";
		}
		if (!textFieldGearCardCode4VP.getText().trim().equals("")) {
			allGearCards = allGearCards + textFieldGearCardCode4VP.getText().trim() + " ";
		} else {
			allGearCards = allGearCards + "0 ";
		}
		if (!textFieldGearCardCode1D.getText().trim().equals("")) {
			allGearCards = allGearCards + textFieldGearCardCode1D.getText().trim() + " ";
		} else {
			allGearCards = allGearCards + "0 ";
		}
		if (!textFieldGearCardCode2D.getText().trim().equals("")) {
			allGearCards = allGearCards + textFieldGearCardCode2D.getText().trim() + " ";
		} else {
			allGearCards = allGearCards + "0 ";
		}
		if (!textFieldGearCardCode3D.getText().trim().equals("")) {
			allGearCards = allGearCards + textFieldGearCardCode3D.getText().trim() + " ";
		} else {
			allGearCards = allGearCards + "0 ";
		}
		if (!textFieldGearCardCode4D.getText().trim().equals("")) {
			allGearCards = allGearCards + textFieldGearCardCode4D.getText().trim() + " ";
		} else {
			allGearCards = allGearCards + "0 ";
		}
		if (!textFieldGearCardCode1W.getText().trim().equals("")) {
			allGearCards = allGearCards + textFieldGearCardCode1W.getText().trim() + " ";
		} else {
			allGearCards = allGearCards + "0 ";
		}
		if (!textFieldGearCardCode2W.getText().trim().equals("")) {
			allGearCards = allGearCards + textFieldGearCardCode2W.getText().trim() + " ";
		} else {
			allGearCards = allGearCards + "0 ";
		}
		if (!textFieldGearCardCode3W.getText().trim().equals("")) {
			allGearCards = allGearCards + textFieldGearCardCode3W.getText().trim() + " ";
		} else {
			allGearCards = allGearCards + "0 ";
		}
		if (!textFieldGearCardCode4W.getText().trim().equals("")) {
			allGearCards = allGearCards + textFieldGearCardCode4W.getText().trim() + " ";
		} else {
			allGearCards = allGearCards + "0 ";
		}
		if (!textFieldGearCardCode1A.getText().trim().equals("")) {
			allGearCards = allGearCards + textFieldGearCardCode1A.getText().trim() + " ";
		} else {
			allGearCards = allGearCards + "0 ";
		}
		if (!textFieldGearCardCode2A.getText().trim().equals("")) {
			allGearCards = allGearCards + textFieldGearCardCode2A.getText().trim() + " ";
		} else {
			allGearCards = allGearCards + "0 ";
		}
		if (!textFieldGearCardCode3A.getText().trim().equals("")) {
			allGearCards = allGearCards + textFieldGearCardCode3A.getText().trim() + " ";
		} else {
			allGearCards = allGearCards + "0 ";
		}
		if (!textFieldGearCardCode4A.getText().trim().equals("")) {
			allGearCards = allGearCards + textFieldGearCardCode4A.getText().trim();
		} else {
			allGearCards = allGearCards + "0";
		}
		vrAllGearCardsSender.sendMessage(allGearCards);
	}

	public static void sendCurrentPlayer() {
		String currentPlayerNumber = "";
		if (currentPlayer.equals("v")) {
			currentPlayerNumber = "0";
		}
		if (currentPlayer.equals("d")) {
			currentPlayerNumber = "1";
		}
		if (currentPlayer.equals("w")) {
			currentPlayerNumber = "2";
		}
		if (currentPlayer.equals("a")) {
			currentPlayerNumber = "3";
		}
		if (!currentPlayerNumber.equals("")) {
			vrCurrentPlayerSender.sendMessage(currentPlayerNumber);
		}
	}

	public static void sendChoicesForThreat() {
		String roomOptionsWithoutSpace = roomOptions.trim();
		String choicesForThreat = "";
		char[] roomOptionsInChars = roomOptionsWithoutSpace.toCharArray();
		choicesForThreat = roomOptionsInChars[0] + " " + roomOptionsInChars[1] + " " +roomOptionsInChars[2];
		vrChoicesForThreatSender.sendMessage(choicesForThreat);
	}

	public static void sendChosenThreat() {
		vrChosenThreatSender.sendMessage(roomSelected);
	}

	public static void sendVPQuestionToken() {
		vrVPQuestionTokenSender.sendMessage(textFieldTokenCode1VP.getText().trim() + " " + textFieldTokenCode2VP.getText().trim() + " " +textFieldTokenCode3VP.getText().trim());
	}

	public static void sendGamePhase() {
		for (int i = 0; i < 6; i++) {
			if (currentGamePhase.equals(gamePhaseNames[i])) {
				vrGamePhaseSender.sendMessage(Integer.toString(i + 1));
			}
		}
	}

	public static void sendQAWithSkillDecision() {
		String qaWithSkillDecision = "";
		String currentSpeakerNumber = "";
		if (currentSpeaker.equals("v")) {
			currentSpeakerNumber = "0";
		}
		if (currentSpeaker.equals("d")) {
			currentSpeakerNumber = "1";
		}
		if (currentSpeaker.equals("w")) {
			currentSpeakerNumber = "2";
		}
		if (currentSpeaker.equals("a")) {
			currentSpeakerNumber = "3";
		}
		qaWithSkillDecision += (currentSpeakerNumber +" ");
		String filling = currentFilling.trim().replace(" ","/");
		qaWithSkillDecision += (filling + " ");
		String answer = currentAnswer.trim().replace(" ", "/");
		qaWithSkillDecision += (answer + " ");
		if (UI_skills.fight.isSelected()) {
			qaWithSkillDecision += (YES + " ");
		} else if (UI_skills.fightN.isSelected()) {
			qaWithSkillDecision += (NO + " ");
		} else {
			qaWithSkillDecision += (UNKNOWN + " ");
		}
		if (UI_skills.friend.isSelected()) {
			qaWithSkillDecision += (YES + " ");
		} else if (UI_skills.friendN.isSelected()) {
			qaWithSkillDecision += (NO + " ");
		} else {
			qaWithSkillDecision += (UNKNOWN + " ");
		}
		if (UI_skills.see.isSelected()) {
			qaWithSkillDecision += (YES + " ");
		} else if (UI_skills.seeN.isSelected()) {
			qaWithSkillDecision += (NO + " ");
		} else {
			qaWithSkillDecision += (UNKNOWN + " ");
		}
		if (UI_skills.lovesAnimals.isSelected()) {
			qaWithSkillDecision += (YES + " ");
		} else if (UI_skills.lovesAnimalsN.isSelected()) {
			qaWithSkillDecision += (NO + " ");
		} else {
			qaWithSkillDecision += (UNKNOWN + " ");
		}
		if (UI_skills.block.isSelected()) {
			qaWithSkillDecision += (YES + " ");
		} else if (UI_skills.blockN.isSelected()) {
			qaWithSkillDecision += (NO + " ");
		} else {
			qaWithSkillDecision += (UNKNOWN + " ");
		}
		if (UI_skills.hack.isSelected()) {
			qaWithSkillDecision += (YES + " ");
		} else if (UI_skills.hackN.isSelected()) {
			qaWithSkillDecision += (NO + " ");
		} else {
			qaWithSkillDecision += (UNKNOWN + " ");
		}
		if (UI_skills.fix.isSelected()) {
			qaWithSkillDecision += (YES + " ");
		} else if (UI_skills.fixN.isSelected()) {
			qaWithSkillDecision += (NO + " ");
		} else {
			qaWithSkillDecision += (UNKNOWN + " ");
		}
		if (UI_skills.fast.isSelected()) {
			qaWithSkillDecision += (YES);
		} else if (UI_skills.fastN.isSelected()) {
			qaWithSkillDecision += (NO);
		} else {
			qaWithSkillDecision += (UNKNOWN);
		}
	}

	public static void sendSuggestedSkill(int player, String skill, int usage) {
		String skillNumber = "";
		for (int i = 0; i < 8; i++) {
			if (skill.equals(skillNames[i])) {
				skillNumber = Integer.toString(i);
			}
		}
		vrSuggestedSkillSender.sendMessage(player + " " + skillNumber + " " + usage);
	}

	public static void sendSuggestedCard(int player, String card, int usage) {
		String cardNumber = "";
		for (int i = 1; i <= 28; i++) {
			if (card.equals(cardNames[i])) {
				cardNumber = Integer.toString(i);
			}
		}
		vrSuggestedCardSender.sendMessage(player + " " + cardNumber + " " + usage);
	}

	public static void sendFinalChosenCards() {
		String gearCardCode1=textFieldFinal1.getText().trim();
		String gearCardCode2=textFieldFinal2.getText().trim();
		String gearCardCode3=textFieldFinal3.getText().trim();
		vrFinalChosenCardsSender.sendMessage(gearCardCode1 + " " + gearCardCode2 + " " + gearCardCode3);
	}

	public static void sendRoomResult() {
		if (roomResult) {
			vrRoomResultSender.sendMessage("1");
		} else {
			vrRoomResultSender.sendMessage("2");
		}
	}

	public static void sendAntidoteDie() {
		if (antidoteResult) {
			vrAntidoteDieSender.sendMessage("1");
		} else {
			vrAntidoteDieSender.sendMessage("2");
		}
	}

	public static void sendOthersAgreement() {
		if (allAgree) {
			vrOthersAgreementSender.sendMessage("1");
		} else {
			vrOthersAgreementSender.sendMessage("2");
		}
	}

	public static void main(String[] args)
	{
		UI_part1 ui_part1 = new UI_part1();
		ui_part1.initializeVHMsg();
		// to-do yuhan
		//panelSkillsDecisionGenerationVP.initializeVHMsg();
		currentGamePhase="Initialization";
		//vrGamePhase yuhan Initialization
		sendGamePhase();
		formerGamePhase="";
		currentSpeaker="";
		currentFilling="";
		currentAnswer="";
		currentToken="";
		dieValue=0;
		answer1A="";
		answer2A="";
		answer3A="";
		answer1W="";
		answer2W="";
		answer3W="";
		answer1D="";
		answer2D="";
		answer3D="";
		answer1VP="";
		answer2VP="";
		answer3VP="";
		timer0.stop();
		radioButtonInitialization.setSelected(true);
		UI_skills.buttonSkill.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					//vrQAWithSkillDecision yuhan
					sendQAWithSkillDecision();
				}
			}
		});
		UI_skills.buttonSkill.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//vrQAWithSkillDecision yuhan
				sendQAWithSkillDecision();
			}
		});

		// Belows are tests
		//		if (!textFieldGearCardCode1VP.getText().trim().equals("")) {
//			System.out.println("textFieldFilling1VP " + "start" + textFieldFilling1VP.getText() + "end");
//		} else {
//			System.out.println("textFieldFilling1VP is not set");
//		}
	}	
}