package com.psssystem.client.startup;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import com.psssystem.client.ui.mainui.LoginFrame;



public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable(){

			@Override
			public void run() {
				//beautyeye
		        try{ 
		              org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		              UIManager.put("RootPane.setupButtonVisible", false);
		        }catch(Exception e){
		        	e.printStackTrace();
		        }
				JFrame log=new LoginFrame();
			}
			
		});
		
	}

}
