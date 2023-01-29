import java.util.*;
import java.lang.Math;

class Scheduling 
{
	int n, head;
	int size, seek;
	int seq[];
	int str[];

	Scheduling(int n)
	{
		this.n = n;
		str = new int[n];
		seq = new int[n+2];
	}

	void input()
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("Head position : ");
        head = sc.nextInt();
        System.out.print("Total number of cylinders : ");
        size = sc.nextInt();
        System.out.print("Enter the string of cylinder numbers : ");
        for (int i = 0; i < n; i++)
            str[i] = sc.nextInt();
	} 

	void sort(int[] arr, int n)
	{
		for(int i=0; i<n-1; i++)
		{
			for(int j=0; j<n-i-1; j++)
			{
				if(arr[j]>arr[j+1])
				{
					int temp = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = temp;
				}
			}
		}
	}

	int findMin(int[] dist, int[] v)
	{
		int minI = -1, min = 9999;
		for (int i = 0; i < n; i++) 
        {
        	if(v[i]==0 && min>dist[i])
        	{
        		min = dist[i];
        		minI = i;
        	}
        }
        return minI;
	}

	void calcDist(int[] dist, int[] v, int h)
	{
		for (int i = 0; i < n; i++) 
        {
        	dist[i] = Math.abs(h - str[i]);
        }
	}

	void SSTF()
	{
		System.out.println("\n------ SSTF SCHEDULINNG ------");
		int h = head;
		int []dist = new int[n];
        int []v = new int[n];
        for (int i = 0; i < n; i++) 
        {
            dist[i] = 0;
            v[i] = 0;
        }

        int index = 0;
        int min;

		seek = 0;
		for(int i=0; i<n; i++)
			seq[i] = -1;

		for(int i=0; i<n; i++)
		{
			seq[index++] = h;
			calcDist(dist, v, h);
			min = findMin(dist, v);
			v[min] = 1;
			seek += dist[min];
			h = str[min];
		}

		seq[index++] = h;

		System.out.println("\nTotal number of seek operation : "+seek);
		System.out.println("Seek Sequence :");
		for(int i=0; i<index; i++)
			System.out.print(seq[i]+"  ");
	}

	void CSCAN()
	{
		System.out.println("\n------ C-SCAN SCHEDULINNG ------");
		int h = head;
		int []left = new int[n+1];
		int []right = new int[n+1];
		int index=0;

		seek = 0;
		for(int i=0; i<n; i++)
			seq[i] = -1;

		int l=0,r=0;
		left[l++] = 0;
		right[r++] = size - 1;
		for(int i=0; i<n; i++)
		{
			if(str[i]<h)
				left[l++] = str[i];
			else if(str[i]>h)
				right[r++] = str[i];
		}

		sort(left, l);
		sort(right, r);

		for(int i=0; i<r; i++)
		{
			seq[index++] = right[i];
			seek += Math.abs(right[i] - h);
			h = right[i];
		}

		h = 0;
		seek += size-1;

		for(int i=0; i<l; i++)
		{
			seq[index++] = left[i];
			seek += Math.abs(left[i] - h);
			h = left[i];
		}		

		System.out.println("\nTotal number of seek operation : "+seek);
		System.out.println("Seek Sequence :");
		for(int i=0; i<index; i++)
			System.out.print(seq[i]+"  ");
	}

	void CLOOK()
	{
		System.out.println("\n------ C-LOOK SCHEDULINNG ------");
		int h = head;
		int []left = new int[n];
		int []right = new int[n];
		int index=0;

		seek = 0;
		for(int i=0; i<n; i++)
			seq[i] = -1;

		int l=0,r=0;
		for(int i=0; i<n; i++)
		{
			if(str[i]<h)
				left[l++] = str[i];
			else if(str[i]>h)
				right[r++] = str[i];
		}

		sort(left, l);
		sort(right, r);

		for(int i=0; i<r; i++)
		{
			seq[index++] = right[i];
			seek += Math.abs(right[i] - h);
			h = right[i];
		}

		seek += Math.abs(h - left[0]);
		h = left[0];

		for(int i=0; i<l; i++)
		{
			seq[index++] = left[i];
			seek += Math.abs(left[i] - h);
			h = left[i];
8
		}		

		System.out.println("\nTotal number of seek operation : "+seek);
		System.out.println("Seek Sequence :");
		for(int i=0; i<index; i++)
			System.out.print(seq[i]+"  ");
	}
} 

class DScheduling
{
	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);
		int n;
		System.out.print("Total number of cylinder numbers : ");
		n = sc.nextInt();
		Scheduling S = new Scheduling(n);
		S.input();
		int ch;
		int again = 1;
		do
		{
			System.out.print("\n 1. SSTF\n 2. C-SCAN\n 3. C-LOOK\n 4. Exit\nEnter your choice : ");
			ch = sc.nextInt();
			switch(ch)
			{
				case 4: again=0;	break;
				case 1:	S.SSTF();	break;
				case 2:	S.CSCAN();	break;
				case 3:	S.CLOOK();	break;
				default: System.out.println("\n Invalid choice!");
			}
		}while(again==1);
	}
}