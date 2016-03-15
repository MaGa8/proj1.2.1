

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GaugePanelTest extends JFrame 
{
	
	public static void main (String[] args)
	{
		new GaugePanelTest();
	}
	
	public GaugePanelTest()
	{
		class ButtonListen implements ActionListener
		{
			public ButtonListen (int steps, GaugePanel panel)
			{
				mSteps = steps;
				mPanel = panel;
			}
			
			public void actionPerformed (ActionEvent e)
			{
				double amount = mPanel.getIncrementSize (Math.abs (mSteps));
				if (mSteps > 0)
					mPanel.increase (amount);
				else
					mPanel.decrease (amount);
				mPanel.repaint();
			}
			
			private GaugePanel mPanel;
			private int mSteps;
		}
		
		
		setSize (250, 500);
		setLayout (new GridBagLayout());
		setDefaultCloseOperation (EXIT_ON_CLOSE);
		
		mPanel = new GaugePanel (1, 9);
		
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 2;
		add (mPanel, c);
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 0;
		
		JButton add, sub;
		add = new JButton ("add");
		sub = new JButton ("sub");
		add.addActionListener (new ButtonListen (10, mPanel));
		sub.addActionListener (new ButtonListen (-10, mPanel));
		c.gridx = 1;
		add (add, c);
		c.gridy = 1;
		add (sub, c);
		
		setVisible (true);
		System.out.println (mPanel.getSize());
	}
	
	private GaugePanel mPanel;
}
