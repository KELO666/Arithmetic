public class Util {
	/**
	 * 最大公因数，用于约分
	 * 递归实现辗转相除法
	 * @param a
	 * @param b
	 * @return   返回a、b的最大公因数
	 */
	public static int gCommonDivisor(int a,int b) {
		if(b==0) return a;
		else return gCommonDivisor(b,a%b);
	}

	/**
	 * 最小公倍数，用于通分
	 * [a,b]=a*b/(a,b)
	 * @param a
	 * @param b
	 * @return   返回a、b的最小公倍数
	 */
	public static int lCommonMultiple(int a,int b) {
		return a*b/gCommonDivisor(a,b);
	}

	/**
	 * 判断a、b的大小，用于判断两数相减时被减数是否不小于减数
	 * @param a
	 * @param b
	 * @return   a>=b返回true，否则返回false
	 */
	public static boolean compare(Fraction a,Fraction b) {
		a.changeToImproperFraction();
		b.changeToImproperFraction();
		if(a.denominator!=b.denominator){
			int lcm=lCommonMultiple(a.denominator,b.denominator);
			a.commonReduction(lcm);
			b.commonReduction(lcm);
		}
		if(a.molecular>=b.molecular) return true;
		else return false;
	}

	/**
	 * a+b
	 * @param a
	 * @param b
	 * @return   返回a+b的结果
	 */
	public static Fraction add(Fraction a,Fraction b) {
		if(a.denominator!=b.denominator){
			int lcm=lCommonMultiple(a.denominator,b.denominator);
			a.commonReduction(lcm);
			b.commonReduction(lcm);
		}
		Fraction c=new Fraction(a.n+b.n,a.molecular+b.molecular,a.denominator);
		return c;
	}

	/**
	 * a-b
	 * @param a
	 * @param b
	 * @return   返回a-b的结果
	 */
	public static Fraction minus(Fraction a,Fraction b) {
		a.changeToImproperFraction();
		b.changeToImproperFraction();
		if(a.denominator!=b.denominator){
			int lcm=lCommonMultiple(a.denominator,b.denominator);
			a.commonReduction(lcm);
			b.commonReduction(lcm);
		}
		Fraction c=new Fraction(0,a.molecular-b.molecular,a.denominator);
		return c;
	}

	/**
	 * a×b
	 * @param a
	 * @param b
	 * @return   返回a×b的结果
	 */
	public static Fraction multiply(Fraction a,Fraction b) {
		a.changeToImproperFraction();
		b.changeToImproperFraction();
		Fraction c=new Fraction(0,a.molecular*b.molecular,a.denominator*b.denominator);
		return c;
	}

	/**
	 * a÷b
	 * @param a
	 * @param b
	 * @return   返回a÷b的结果
	 */
	public static Fraction divice(Fraction a,Fraction b) {
		a.changeToImproperFraction();
		b.changeToImproperFraction();
		Fraction c=new Fraction(0,a.molecular*b.denominator,a.denominator*b.molecular);
		return c;
	}

	/**
	 * 判断a、b是否相等，用于判断题目是否重复
	 * @param a
	 * @param b
	 * @return   a=b返回true，否则返回false
	 */
	public static boolean isEqual(Fraction a,Fraction b) {
		a.changeToImproperFraction();
		b.changeToImproperFraction();
		if(a.denominator!=b.denominator){
			int lcm=lCommonMultiple(a.denominator,b.denominator);
			a.commonReduction(lcm);
			b.commonReduction(lcm);
		}
		if(a.n==b.n&&a.molecular==b.molecular&&a.denominator==b.denominator) return true;
		return false;
	}

	/**
	 * 判断运算符a、b是否相同，用于判断题目是否重复
	 * @param a
	 * @param b
	 * @return   a、b相同返回true，否则返回false
	 */
	public static boolean isEqual(Operator a,Operator b) {
		if(a.operator==b.operator) return true;
		return false;
	}
}