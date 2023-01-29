#include<iostream>
#include<string>
#include<vector>
using namespace std;

class LCS
{
	int** mat;
	int m, n;

public:

	void init(string s1, string s2)
	{
		m = s1.size()+1;
		n = s2.size()+1;
		mat = new int*[m];
		for(int i=0; i<m; i++)
		{
			mat[i] = new int[n];
			for(int j=0; j<n; j++)
				mat[i][j] = 0;
		}
		
	}

	void printMat()
	{
		for(int i=0; i<m; i++)
		{
			for(int j=0; j<n; j++)
				cout<<mat[i][j]<<"\t";
			cout<<endl;
		}
	}

	void printLCS(string s1, string s2)
	{
		string lcs;
		int i=m, j=n;
		while(i>0 && j>0)
		{
			if(s1[i-1]==s2[j-1])
			{
				lcs+=s1[i-1];
				--i; --j;
			}
			else if(mat[i-1][j]>mat[i][j-1])
				--i;
			else
				--j;
		}
		cout<<" LCS : ";
		for(int i=lcs.size()-1; i>=0; i--)
			cout<<lcs[i];
	}

	void findLCS(string s1, string s2)
	{
		init(s1, s2);
		for(int i=0; i<m; i++)
		{
			for(int j=0; j<n; j++)
			{
				if(i==0 || j==0)
					mat[i][j] = 0;
				else if(s1[i-1] == s2[j-1])
					mat[i][j] = 1 + mat[i-1][j-1];
				else if(mat[i-1][j] > mat[i][j-1])
					mat[i][j] = mat[i-1][j];
				else
					mat[i][j] = mat[i][j-1];
			}
			cout<<"\n Pass "<<i+1<<endl;
			printMat();
		}
		cout<<"\n Length of LCS : "<<mat[m-1][n-1]<<endl;
		printLCS(s1, s2);
	}
};

int main()
{
	string s1, s2;
	cout<<"String 1 :\t";	cin>>s1;
	cout<<"String 2 :\t";	cin>>s2;
	LCS str;
	str.findLCS(s1, s2);
	return 0;
}