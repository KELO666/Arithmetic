package main;
import java.util.Scanner;

import pojo.Question;
import util.FractionUtil;
public class Main {
	
	public static void welcome(){
		System.out.println("*************************************");
		System.out.println("*              Myapp.exe            *");
		System.out.println("*Only for learning and communication*");
		System.out.println("*         author：KELO、LQK         *");
		System.out.println("*************************************");
		
	}
	
	public static void main(String[] args) {
//		int scope=20;
//		Question q=new Question(scope);
//		System.out.println(q);
//		System.out.println(FractionUtil.calculate(q.numbers,q.operators));
		System.out.println("请输入生成的题目个数");
		Scanner input1 = new Scanner(System.in);
		int scope = input1.nextInt();
		System.out.println("请输入生成数值的最大值（不包括最大值）");
		Scanner input2 = new Scanner(System.in);
		int num = input2.nextInt();
		
		for (int i = 0; i < num; i++) {
			Question q=new Question(scope);
			System.out.println(q);
		}
	}
}