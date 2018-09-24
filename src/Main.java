import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
public class Main {
	public static void main(String[] args) {
		try {
			boolean flag=true;
			for(String s:args){
				if(s.equals("-n")||s.equals("-r")) break;   //生成题目和答案文档
				else{   //对错题数量统计
					flag=false;
					break;
				}
			}

			//生成题目和答案文档
			if(flag){
				List<Question> questionBase=new ArrayList<Question>();   //题库
				int n=1;      //题目个数（默认为1）
				int r=-1;     //数值范围
				for(int i=0;i<args.length;i++){
					if(args[i].equals("-n")) n=Integer.valueOf(args[i+1]);
					if(args[i].equals("-r")) r=Integer.valueOf(args[i+1]);
				}
				//没有给出数值范围
				if(r==-1){
					System.err.println("Warning: The scope of value has not been given!");
					return;
				}
				Question q=new Question(r);
				questionBase.add(q);
				while(questionBase.size()<n||Question.isRepeated(questionBase)){
					q=new Question(r);
					questionBase.add(q);
				}
				//生成题目文件Questions.txt
				BufferedWriter bw1=new BufferedWriter(new FileWriter("C:\\Users\\lenovo\\Desktop\\四则运算\\Questions.txt"));
				for(int i=0;i<questionBase.size();i++){
					String s=i+1+". "+questionBase.get(i)+"\r\n";
					bw1.write(s);
				}
				bw1.flush();
				bw1.close();
				//生成答案文件Answers.txt
				BufferedWriter bw2=new BufferedWriter(new FileWriter("C:\\Users\\lenovo\\Desktop\\四则运算\\Answers.txt"));
				for(int i=0;i<questionBase.size();i++){
					String s=i+1+". "+questionBase.get(i).result+"\r\n";
					bw2.write(s);
				}
				bw2.flush();
				bw2.close();
				//生成答题文件Exercises.txt
				BufferedWriter bw3=new BufferedWriter(new FileWriter("C:\\Users\\lenovo\\Desktop\\四则运算\\Exercises.txt"));
				for(int i=0;i<questionBase.size();i++){
					String s=i+1+". \r\n";
					bw3.write(s);
				}
				bw3.flush();
				bw3.close();
				return;
			}

			//对错题数量统计
			String e="",a="";
			for(int i=0;i<args.length;i++){
				if(args[i].equals("-e")) e=args[i+1];
				if(args[i].equals("-a")) a=args[i+1];
			}
			//没有给出答题文件路径
			if(e.equals("")){
				System.err.println("404: The exercises file is not found!");
				return;
			}
			//没有给出答案文件路径
			if(a.equals("")){
				System.err.println("404: The answers file is not found!");
				return;
			}
			List<String> exercises=new ArrayList<String>();
			List<String> answers=new ArrayList<String>();
			String str="";
			//读取答题文件
			BufferedReader br1=new BufferedReader(new FileReader(e));
			for(int i=1;null!=(str=br1.readLine());i++)
				exercises.add(str.replace(i+". ",""));
			br1.close();
			//读取答案文件
			BufferedReader br2=new BufferedReader(new FileReader(a));
			for(int i=1;null!=(str=br2.readLine());i++)
				answers.add(str.replace(i+". ",""));
			br2.close();
			String correct="",wrong="";
			List<Integer> corrects=new ArrayList<Integer>();
			List<Integer> wrongs=new ArrayList<Integer>();
			for(int i=0;i<answers.size();i++){
				if(exercises.get(i).equals(answers.get(i))) corrects.add(i+1);
				else wrongs.add(i+1);
			}
			for(int i=0;i<corrects.size();i++) correct+=corrects.get(i)+",";
			for(int i=0;i<wrongs.size();i++) wrong+=wrongs.get(i)+",";
			if(corrects.size()>0) correct=correct.substring(0,correct.length()-1);
			if(wrongs.size()>0) wrong=wrong.substring(0,wrong.length()-1);
			//生成分数文件Grade.txt
			BufferedWriter bw=new BufferedWriter(new FileWriter("C:\\Users\\lenovo\\Desktop\\四则运算\\Grade.txt"));
			bw.write("Correct:"+corrects.size()+"("+correct+")\r\nWrong:"+wrongs.size()+"("+wrong+")");
			bw.flush();
			bw.close();
		} catch (Exception e) {
			//-a C:\Users\lenovo\Desktop\四则运算\Answers.txt -e C:\Users\lenovo\Desktop\四则运算\Exercises.txt
			e.printStackTrace();
		}
	}
}