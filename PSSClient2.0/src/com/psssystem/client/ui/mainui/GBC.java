package com.psssystem.client.ui.mainui;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GBC extends GridBagConstraints{
	/*gridx和gridy分别表示x和y轴坐标*/
	public GBC(int gridx,int gridy){
		this.gridx=gridx;
		this.gridy=gridy;
	}
	
	public GBC(int gridx,int gridy,int gridwidth,int gridheight){
		this.gridx=gridx;
		this.gridy=gridy;
		this.gridheight=gridheight;
		this.gridwidth=gridwidth;
	}
	
	public GBC setAnchor(int anchor){
		this.anchor=anchor;
		return this;
	}
	
	public GBC setFill(int fill){
		this.fill=fill;
		return this;
	}
	
	public GBC setWeight(double weightx,double weighty){
		this.weightx=weightx;
		this.weighty=weighty;
		return this;
	}
	public GBC setInsets(int distance){
		this.insets=new Insets(distance,distance,distance,distance);
		return this;
	}
	public GBC tipicalRightInsets(){
		this.insets=new Insets(10,0,10,10);
		return this;
	}
	public GBC tipicalLeftInsets(){
		this.insets=new Insets(10,10,10,0);
		return this;
	}
	public GBC setInsets(int top,int left,int bottom,int right){
		this.insets=new Insets(top,left,bottom,right);
		return this;
	}
	
	public GBC setIpad(int ipadx,int ipady){
		this.ipadx=ipadx;
		this.ipady=ipady;
		return this;
	}
	
}
