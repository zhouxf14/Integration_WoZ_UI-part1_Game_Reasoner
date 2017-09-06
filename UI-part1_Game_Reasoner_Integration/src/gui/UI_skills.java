package gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import jxl.Sheet;
import jxl.Workbook;
import   java.util.List;   
import   java.util.Vector;
import   javax.swing.*;

public class UI_skills extends JPanel{
	static final int WIDTH=500;
	static final int HEIGHT=500;
	JFrame jf;
	static JTextField textFieldKeyword;
	static JTextField textFieldNum;
	static JCheckBox friend;
	static JCheckBox fight;
	static JCheckBox see;
	static JCheckBox lovesAnimals;
	static JCheckBox block;
	static JCheckBox hack;
	static JCheckBox fix;
	static JCheckBox fastN;
	static JCheckBox friendN;
	static JCheckBox fightN;
	static JCheckBox seeN;
	static JCheckBox lovesAnimalsN;
	static JCheckBox blockN;
	static JCheckBox hackN;
	static JCheckBox fixN;
	static JCheckBox fast;
	static JButton buttonSkill;

	static String keyword;
	static int skillNum;
	static ShiftSkills shiftSkills=new ShiftSkills();
	static CommonNavi2 commonNavi2=new CommonNavi2();

	//common navigation for all the text fields in the text field of skills number
	static class CommonNavi2 implements KeyListener
	{
		public void keyPressed(KeyEvent e)
		{
			if((e.getKeyCode()==KeyEvent.VK_A)&&(e.isControlDown()))
			{
				UI_part1.textFieldFilling1A.requestFocusInWindow();
			}
			if((e.getKeyCode()==KeyEvent.VK_W)&&(e.isControlDown()))
			{
				UI_part1.textFieldFilling1W.requestFocusInWindow();
			}
			if((e.getKeyCode()==KeyEvent.VK_D)&&(e.isControlDown()))
			{
				UI_part1.textFieldFilling1D.requestFocusInWindow();
			}
			if((e.getKeyCode()==KeyEvent.VK_S)&&(e.isControlDown()))
			{
				UI_part1.textFieldFilling1VP.requestFocusInWindow();
			}
			if((e.getKeyCode()==KeyEvent.VK_F)&&(e.isControlDown()))
			{
				UI_part1.textFieldFinal1.requestFocusInWindow();
			}
			if((e.isShiftDown())&&(e.isControlDown())){
				UI_part1.textFieldSpeaker.requestFocusInWindow();
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
	static class ShiftSkills implements KeyListener{
		public void keyPressed(KeyEvent e){
			if((e.getKeyCode()==KeyEvent.VK_RIGHT)&&(e.isShiftDown()))
			{
				if(friend.isSelected()){
					friendN.setSelected(true);
					friend.setSelected(false);
				}
				else if(friendN.isSelected()){
					friendN.setSelected(false);
				}
				if(fight.isSelected()){
					fightN.setSelected(true);
					fight.setSelected(false);
				}
				else if(fightN.isSelected()){
					fightN.setSelected(false);
				}
				if(fix.isSelected()){
					fixN.setSelected(true);
					fix.setSelected(false);
				}
				else if(fixN.isSelected()){
					fixN.setSelected(false);
				}
				if(fast.isSelected()){
					fastN.setSelected(true);
					fast.setSelected(false);
				}
				else if(fastN.isSelected()){
					fastN.setSelected(false);
				}
				if(block.isSelected()){
					blockN.setSelected(true);
					block.setSelected(false);
				}
				else if(blockN.isSelected()){
					blockN.setSelected(false);
				}
				if(see.isSelected()){
					seeN.setSelected(true);
					see.setSelected(false);
				}
				else if(seeN.isSelected()){
					seeN.setSelected(false);
				}
				if(lovesAnimals.isSelected()){
					lovesAnimalsN.setSelected(true);
					lovesAnimals.setSelected(false);
				}
				else if(lovesAnimalsN.isSelected()){
					lovesAnimalsN.setSelected(false);
				}
				if(hack.isSelected()){
					hackN.setSelected(true);
					hack.setSelected(false);
				}
				else if(hackN.isSelected()){
					hackN.setSelected(false);
				}
			}
			if((e.getKeyCode()==KeyEvent.VK_LEFT)&&(e.isShiftDown()))
			{
				if(friend.isSelected()){
					friend.setSelected(false);
				}
				else if(friendN.isSelected()){
					friendN.setSelected(false);
					friend.setSelected(true);
				}
				if(fight.isSelected()){
					fight.setSelected(false);
				}
				else if(fightN.isSelected()){
					fightN.setSelected(false);
					fight.setSelected(true);
				}
				if(fix.isSelected()){
					fix.setSelected(false);
				}
				else if(fixN.isSelected()){
					fixN.setSelected(false);
					fix.setSelected(true);
				}
				if(fast.isSelected()){
					fast.setSelected(false);
				}
				else if(fastN.isSelected()){
					fastN.setSelected(false);
					fast.setSelected(true);
				}
				if(block.isSelected()){
					block.setSelected(false);
				}
				else if(blockN.isSelected()){
					blockN.setSelected(false);
					block.setSelected(true);
				}
				if(see.isSelected()){
					see.setSelected(false);
				}
				else if(seeN.isSelected()){
					seeN.setSelected(false);
					see.setSelected(true);
				}
				if(lovesAnimals.isSelected()){
					lovesAnimals.setSelected(false);
				}
				else if(lovesAnimalsN.isSelected()){
					lovesAnimalsN.setSelected(false);
					lovesAnimals.setSelected(true);
				}
				if(hack.isSelected()){
					hack.setSelected(false);
				}
				else if(hackN.isSelected()){
					hack.setSelected(true);
					hackN.setSelected(false);
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

	public static JPanel panelSkill(){
		JPanel p=new JPanel();
		p.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		JLabel labelKeyword=new JLabel("Keyword");
		textFieldKeyword=new JTextField(10);
		textFieldKeyword.addKeyListener(shiftSkills);
		textFieldKeyword.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
				{
					textFieldNum.requestFocusInWindow();
				}
			}
		});
		textFieldNum=new JTextField(3);
		textFieldNum.addKeyListener(commonNavi2);
		textFieldNum.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					textFieldNum.setText("");
				}
			}
		});
		JLabel labelSkill=new JLabel("kill");
		JLabel labelFriend=new JLabel("____Friend____");
		JLabel labelFight=new JLabel("____Fight_____");
		JLabel labelSee=new JLabel("_____See______");
		JLabel labelLovesAnimals=new JLabel("_LovesAnimals_");
		JLabel labelBlock=new JLabel("____Block_____");
		JLabel labelHack=new JLabel("_____Hack_____");
		JLabel labelFix=new JLabel("_____Fix______");
		JLabel labelFast=new JLabel("_____Fast_____");
		friend=new JCheckBox("Y");
		friend.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				friendN.setSelected(false);
			}
		});
		fight=new JCheckBox("Y");
		fight.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				fightN.setSelected(false);
			}
		});
		see=new JCheckBox("Y");
		see.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				seeN.setSelected(false);
			}
		});
		lovesAnimals=new JCheckBox("Y");
		lovesAnimals.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				lovesAnimalsN.setSelected(false);
			}
		});
		block=new JCheckBox("Y");
		block.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				blockN.setSelected(false);
			}
		});
		hack=new JCheckBox("Y");
		hack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				hackN.setSelected(false);
			}
		});
		fix=new JCheckBox("Y");
		fix.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				fixN.setSelected(false);
			}
		});
		fast=new JCheckBox("Y");
		fast.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				fastN.setSelected(false);
			}
		});
		friendN=new JCheckBox("N");
		friendN.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				friend.setSelected(false);
			}
		});
		fightN=new JCheckBox("N");
		fightN.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				fight.setSelected(false);
			}
		});
		seeN=new JCheckBox("N");
		seeN.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				see.setSelected(false);
			}
		});
		lovesAnimalsN=new JCheckBox("N");
		lovesAnimalsN.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				lovesAnimals.setSelected(false);
			}
		});
		blockN=new JCheckBox("N");
		blockN.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				block.setSelected(false);
			}
		});
		hackN=new JCheckBox("N");
		hackN.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				hack.setSelected(false);
			}
		});
		fixN=new JCheckBox("N");
		fixN.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				fix.setSelected(false);
			}
		});
		fastN=new JCheckBox("N");
		fastN.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				fast.setSelected(false);
			}
		});
		buttonSkill=new JButton("OK");
		buttonSkill.addKeyListener(shiftSkills);
		buttonSkill.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					skillNum=0;
					textFieldKeyword.setText("");
					String potentialSkills="";
					if(friend.isSelected()){
						skillNum=skillNum+1;
						potentialSkills=potentialSkills+"Friend"+"\r\n";
						friend.setSelected(false);
					}
					if(friendN.isSelected()){
						skillNum=skillNum+1;
						potentialSkills=potentialSkills+"no Friend"+"\r\n";
						friendN.setSelected(false);
					}
					if(fight.isSelected()){
						skillNum=skillNum+1;
						potentialSkills=potentialSkills+"Fight"+"\r\n";
						fight.setSelected(false);
					}
					if(fightN.isSelected()){
						skillNum=skillNum+1;
						potentialSkills=potentialSkills+"no Fight"+"\r\n";
						fightN.setSelected(false);
					}
					if(see.isSelected()){
						skillNum=skillNum+1;
						potentialSkills=potentialSkills+"See"+"\r\n";
						see.setSelected(false);
					}
					if(seeN.isSelected()){
						skillNum=skillNum+1;
						potentialSkills=potentialSkills+"no see"+"\r\n";
						seeN.setSelected(false);
					}
					if(block.isSelected()){
						skillNum=skillNum+1;
						potentialSkills=potentialSkills+"Block"+"\r\n";
						block.setSelected(false);
					}
					if(blockN.isSelected()){
						skillNum=skillNum+1;
						potentialSkills=potentialSkills+"no Block"+"\r\n";
						blockN.setSelected(false);
					}
					if(lovesAnimals.isSelected()){
						skillNum=skillNum+1;
						potentialSkills=potentialSkills+"Loves Animals"+"\r\n";
						lovesAnimals.setSelected(false);
					}
					if(lovesAnimalsN.isSelected()){
						skillNum=skillNum+1;
						potentialSkills=potentialSkills+"no Loves Animals"+"\r\n";
						lovesAnimalsN.setSelected(false);
					}				
					if(hack.isSelected()){
						skillNum=skillNum+1;
						potentialSkills=potentialSkills+"Hack"+"\r\n";
						hack.setSelected(false);
					}
					if(hackN.isSelected()){
						skillNum=skillNum+1;
						potentialSkills=potentialSkills+"no Hack"+"\r\n";
						hackN.setSelected(false);
					}
					if(fix.isSelected()){
						skillNum=skillNum+1;
						potentialSkills=potentialSkills+"Fix"+"\r\n";
						fix.setSelected(false);
					}
					if(fixN.isSelected()){
						skillNum=skillNum+1;
						potentialSkills=potentialSkills+"no Fix"+"\r\n";
						fixN.setSelected(false);
					}
					if(fast.isSelected()){
						skillNum=skillNum+1;
						potentialSkills=potentialSkills+"Fast"+"\r\n";
						fast.setSelected(false);
					}
					if(fastN.isSelected()){
						skillNum=skillNum+1;
						potentialSkills=potentialSkills+"no Fast"+"\r\n";
						fastN.setSelected(false);
					}
					String num=String.valueOf(skillNum);
					textFieldNum.setText(num);
//					textFieldNum.requestFocusInWindow(); if game reasoner does not need to know the skills' number in total, then this variable is not in need
					UI_part1.textFieldNavigation.requestFocusInWindow();
				}
			}
		});
		buttonSkill.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				skillNum=0;
				String potentialSkills="";
				if(friend.isSelected()){
					skillNum=skillNum+1;
					potentialSkills=potentialSkills+"Friend"+"\r\n";
					friend.setSelected(false);
				}
				if(friendN.isSelected()){
					skillNum=skillNum+1;
					potentialSkills=potentialSkills+"no Friend"+"\r\n";
					friendN.setSelected(false);
				}
				if(fight.isSelected()){
					skillNum=skillNum+1;
					potentialSkills=potentialSkills+"Fight"+"\r\n";
					fight.setSelected(false);
				}
				if(fightN.isSelected()){
					skillNum=skillNum+1;
					potentialSkills=potentialSkills+"no Fight"+"\r\n";
					fightN.setSelected(false);
				}
				if(see.isSelected()){
					skillNum=skillNum+1;
					potentialSkills=potentialSkills+"See"+"\r\n";
					see.setSelected(false);
				}
				if(seeN.isSelected()){
					skillNum=skillNum+1;
					potentialSkills=potentialSkills+"no see"+"\r\n";
					seeN.setSelected(false);
				}
				if(block.isSelected()){
					skillNum=skillNum+1;
					potentialSkills=potentialSkills+"Block"+"\r\n";
					block.setSelected(false);
				}
				if(blockN.isSelected()){
					skillNum=skillNum+1;
					potentialSkills=potentialSkills+"no Block"+"\r\n";
					blockN.setSelected(false);
				}
				if(lovesAnimals.isSelected()){
					skillNum=skillNum+1;
					potentialSkills=potentialSkills+"Loves Animals"+"\r\n";
					lovesAnimals.setSelected(false);
				}
				if(lovesAnimalsN.isSelected()){
					skillNum=skillNum+1;
					potentialSkills=potentialSkills+"no Loves Animals"+"\r\n";
					lovesAnimalsN.setSelected(false);
				}				
				if(hack.isSelected()){
					skillNum=skillNum+1;
					potentialSkills=potentialSkills+"Hack"+"\r\n";
					hack.setSelected(false);
				}
				if(hackN.isSelected()){
					skillNum=skillNum+1;
					potentialSkills=potentialSkills+"no Hack"+"\r\n";
					hackN.setSelected(false);
				}
				if(fix.isSelected()){
					skillNum=skillNum+1;
					potentialSkills=potentialSkills+"Fix"+"\r\n";
					fix.setSelected(false);
				}
				if(fixN.isSelected()){
					skillNum=skillNum+1;
					potentialSkills=potentialSkills+"no Fix"+"\r\n";
					fixN.setSelected(false);
				}
				if(fast.isSelected()){
					skillNum=skillNum+1;
					potentialSkills=potentialSkills+"Fast"+"\r\n";
					fast.setSelected(false);
				}
				if(fastN.isSelected()){
					skillNum=skillNum+1;
					potentialSkills=potentialSkills+"no Fast"+"\r\n";
					fastN.setSelected(false);
				}
				String num=String.valueOf(skillNum);
				textFieldNum.setText(num);
				textFieldNum.requestFocusInWindow();
			}
		});
		Box box=Box.createVerticalBox();
		Box box1=Box.createHorizontalBox();
		Box box2=Box.createVerticalBox();
		Box box21=Box.createHorizontalBox();
		box21.setBorder(BorderFactory.createLineBorder(Color.white));
		Box box22=Box.createHorizontalBox();
		box22.setBorder(BorderFactory.createLineBorder(Color.white));
		Box box23=Box.createHorizontalBox();
		box23.setBorder(BorderFactory.createLineBorder(Color.white));
		Box box24=Box.createHorizontalBox();
		box24.setBorder(BorderFactory.createLineBorder(Color.white));
		Box box25=Box.createHorizontalBox();
		box25.setBorder(BorderFactory.createLineBorder(Color.white));
		Box box26=Box.createHorizontalBox();
		box26.setBorder(BorderFactory.createLineBorder(Color.white));
		Box box27=Box.createHorizontalBox();
		box27.setBorder(BorderFactory.createLineBorder(Color.white));
		Box box28=Box.createHorizontalBox();
		box28.setBorder(BorderFactory.createLineBorder(Color.white));
		box21.add(labelFriend);
		box21.add(friend);
		box21.add(friendN);
		box22.add(labelFight);
		box22.add(fight);
		box22.add(fightN);
		box23.add(labelSee);
		box23.add(see);
		box23.add(seeN);
		box24.add(labelLovesAnimals);
		box24.add(lovesAnimals);
		box24.add(lovesAnimalsN);
		box25.add(labelBlock);
		box25.add(block);
		box25.add(blockN);
		box26.add(labelHack);
		box26.add(hack);
		box26.add(hackN);
		box27.add(labelFix);
		box27.add(fix);
		box27.add(fixN);
		box28.add(labelFast);
		box28.add(fast);
		box28.add(fastN);
		box2.add(labelSkill);
		box2.add(box21);
		box2.add(box22);
		box2.add(box23);
		box2.add(box24);
		box2.add(box25);
		box2.add(box26);
		box2.add(box27);
		box2.add(box28);
		box1.add(labelKeyword);
		box1.add(textFieldKeyword);
		box1.add(textFieldNum);
		box.add(box1);
		box.add(box2);
		box.add(buttonSkill);	
		p.add(box);
		return p;
	}

	UI_skills(){
		JPanel panelSkill=panelSkill();
		this.add(panelSkill);
	}	
}

