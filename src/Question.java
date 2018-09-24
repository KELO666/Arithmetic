import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Question {
	int n=new Random().nextInt(3)+1;        //随机生成题目运算符的个数（1~3）
	String question="";            //题目
	List<Fraction> fractions=new ArrayList<Fraction>();   //生成的所有运算数组成的列表（用于计算方法calculate()）
	List<Operator> operators=new ArrayList<Operator>();   //生成的所有运算符组成的列表（用于计算方法calculate()）
	List<Fraction> results=new ArrayList<Fraction>();     //题目每一步运算得出的结果组成的列表
	Fraction[] fTemp=new Fraction[n+1];     //生成的所有运算数组成的数组（用于生成题目）
	Operator[] oTemp=new Operator[n];       //生成的所有运算符组成的数组（用于生成题目）
	Fraction result;              //题目得出的最终结果

	/**
	 * 带参构造方法，用于随机生成题目
	 * @param scope   给定数值的范围
	 */
	public Question(int scope) {
		do{
			question="";
			fractions.clear();
			operators.clear();
			results.clear();
			Fraction f;
			Operator o;
			f=new Fraction(scope);
			fractions.add(f);
			fTemp[0]=f;
			for(int i=0;i<n;i++){
				do{
					o=new Operator(n);
					operators.add(o);
					if(i==0) break;
				}while(Operator.isRepeated(operators));   //用于生成优先级不重复的运算符
				oTemp[i]=o;
				f=new Fraction(scope);
				fractions.add(f);
				fTemp[i+1]=f;
			}
			result=calculate();     //计算
		}while(result.n==-1);      //如果结果为-1，证明题目不合法,需重新设置题目
	}

	/**
	 * 带参构造方法，用于指定题目
	 * @param fs   运算数数组
	 * @param os   运算符数组
	 */
	public Question(Fraction[] fs,Operator[] os) {
		fTemp=fs;
		oTemp=os;
		for(Fraction f:fs) fractions.add(f);
		for(Operator o:os) operators.add(o);
		result=calculate();
	}

	/**
	 * 计算
	 * @return   返回最终结果，如果结果为-1，则证明题目非法
	 * 题目非法的原因有二：
	 * 1.被减数小于减数
	 * 2.除数为0
	 */
	public Fraction calculate() {
		int priority=1;
		do{
			for(int i=0;i<operators.size();i++)
				if(operators.get(i).priority==priority){
					switch(operators.get(i).operator){
					case '+':
						fractions.set(i,Util.add(fractions.get(i),fractions.get(i+1)));
						break;
					case '-':
						//如果减数大于被减数，则返回-1
						if(!Util.compare(fractions.get(i),fractions.get(i+1))) return new Fraction(-1,0,1);
						fractions.set(i,Util.minus(fractions.get(i),fractions.get(i+1)));
						break;
					case '×':
						fractions.set(i,Util.multiply(fractions.get(i),fractions.get(i+1)));
						break;
					case '÷':
						//如果除数为0，则返回-1
						if(fractions.get(i+1).isZero()) return new Fraction(-1,0,1);
						fractions.set(i,Util.divice(fractions.get(i),fractions.get(i+1)));
						break;
					}
					results.add(fractions.get(i));
					fractions.remove(i+1);
					operators.remove(i);
					break;
				}
			priority++;
		}while(operators.size()>0);
		return fractions.get(0);
 	}

	/**
	 * 判断题目是否重复
	 * 题目列表的最后一项与前面所有项进行比较，如果出现题目重复，则删掉最后一项
	 * @param list   题目列表
	 * @return       重复返回true，否则返回false
	 * 判断题目重复的依据有二：
	 * 1.运算数相同，运算符相同
	 * 2.运算顺序相同，即结果列表一一对应
	 */
	public static boolean isRepeated(List<Question> list) {
		for(int i=0;i<list.size()-1;i++){
			//最终结果不同
			if(!Util.isEqual(list.get(list.size()-1).result,list.get(i).result)) return false;
			//结果列表大小不同
			if(list.get(list.size()-1).results.size()!=list.get(i).results.size()) return false;
			//结果列表一一对应存在不同元素
			for(int j=0;j<list.get(list.size()-1).results.size();j++)
				if(!Util.isEqual(list.get(list.size()-1).results.get(j),list.get(i).results.get(j))) return false;
			boolean bool=false;
			//数值列表存在不同元素
			for(Fraction f1:list.get(list.size()-1).fTemp){
				for(Fraction f2:list.get(i).fTemp){
					if(!Util.isEqual(f1,f2)) bool=false;
					else{
						bool=true;
						break;
					}
				}
				if(bool==false) return false;
			}
			//符号列表存在不同元素
			for(Operator o1:list.get(list.size()-1).oTemp){
				for(Operator o2:list.get(i).oTemp){
					if(!Util.isEqual(o1,o2)) bool=false;
					else{
						bool=true;
						break;
					}
				}
				if(bool==false) return false;
			}
		}
		list.remove(list.size()-1);
		return true;
	}

	@Override
	public String toString() {
		int i1=0,i2=0,i3=0;
		for(;i1<oTemp.length;i1++)
			if(oTemp[i1].priority==1){
				question+=fTemp[i1]+" "+oTemp[i1].operator+" "+fTemp[i1+1];
				break;
			}
		if(oTemp.length>1){
			for(;i2<oTemp.length;i2++)
				if(oTemp[i2].priority==2){
					if(i2==i1-1) question=fTemp[i2]+" "+oTemp[i2].operator+" ( "+question+" )";
					if(i2==i1+1) question="( "+question+" ) "+oTemp[i2].operator+" "+fTemp[i2+1];
					if(oTemp.length==3){
						for(;i3<oTemp.length;i3++)
							if(oTemp[i3].priority==3){
								if(i3==0) question=fTemp[i3]+" "+oTemp[i3].operator+" [ "+question+" ]";
								if(i3==1){
									if(i1==1) question="( "+fTemp[i1]+" "+oTemp[i1].operator+" ) "+oTemp[i3].operator+" ( "+fTemp[i2]+" "+oTemp[i2].operator+" )";
									if(i2==1) question="( "+fTemp[i2]+" "+oTemp[i2].operator+" ) "+oTemp[i3].operator+" ( "+fTemp[i1]+" "+oTemp[i1].operator+" )";
								}
								if(i3==2) question="[ "+question+" ] "+oTemp[i3].operator+" "+fTemp[i3+1];
								break;
							}
					}
					break;
				}
		}
		question+=" = ";
		return question;
	}
}