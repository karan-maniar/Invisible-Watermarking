import java.io.*;

public class Cip_Test
{
	public static void main(String args[])
	{
		String s="Karan";
		String f="";
		System.out.print("\nString : " + s);
		char a[] = s.toCharArray();
		int len = s.length();
		for(int i=0;i<len;i++)
		{
			System.out.print("\nChar " + i + " : " + a[i]);
		}
		for(int i=0;i<len;i++)
		{
			f = f+ (31+ (int)a[i])+"-";
		}
		System.out.print("\n" + f);
		int fg=0;
		int sp=0;
		int ep = f.indexOf("-");
		String ft="";
		while(fg==0)
		{
			String tt = f.substring(sp,ep);
			int tc = Integer.parseInt(tt);
			tc = tc-31;
			ft = ft + (char)tc;
			f=f.substring(ep+1);
			ep=f.indexOf("-");
			if (f.length()<2)
			{
				fg=1;
			}
		}
		System.out.print("\n\n\n" + ft);
	}
}