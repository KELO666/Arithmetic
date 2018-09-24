import java.util.Random;
public class Fraction {
	int n=0;             //整数部分
	int molecular=0;     //分子
	int denominator=1;   //分母

	/**
	 * 带参构造方法，用于随机生成分数
	 * @param scope   给定分子分母的范围
	 */
	public Fraction(int scope) {
		Random r=new Random();
		molecular=r.nextInt(scope)+1;
		denominator=r.nextInt(scope)+1;
		rationalize();
	}

	/**
	 * 带参构造方法，用于赋值
	 * @param n             整数部分
	 * @param molecular     分子
	 * @param denominator   分母
	 */
	public Fraction(int n,int molecular,int denominator) {
		this.molecular=molecular;
		this.denominator=denominator;
		this.n=n;
		rationalize();
	}

	/**
	 * 无参构造方法，生成一个数（默认值为0，即0'0/1）
	 */
	public Fraction() {
	}

	/**
	 * 分数有理化
	 */
	public void rationalize() {
		//假分数化为带分数
		if(molecular>=denominator){
			n+=molecular/denominator;
			molecular%=denominator;
		}
		//约分
		int gcd=Util.gCommonDivisor(molecular,denominator);
		if(gcd!=1) {
			molecular/=gcd;
			denominator/=gcd;
		}
	}

	/**
	 * 通分
	 * @param lcm   最小公倍数
	 */
	public void commonReduction(int lcm) {
		molecular*=lcm/denominator;
		denominator=lcm;
	}

	/**
	 * 带分数化为假分数，用于计算
	 */
	public void changeToImproperFraction() {
		if(n==0) return;
		molecular+=n*denominator;
		n=0;
	}

	/**
	 * 判断数值是否为0
	 * @return     为0返回true，否则返回false
	 */
	public boolean isZero() {
		if(n==0&&molecular==0) return true;
		return false;
	}

	@Override
	public String toString() {
		rationalize();
		if(molecular%denominator==0) return ""+n;
		String fraction="";
		if(n>0) fraction+=n+"\'";
		fraction+=molecular;
		if(denominator>1) fraction+="/"+denominator;
		return fraction;
	}
}