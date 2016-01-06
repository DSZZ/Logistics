package edu.nju.logistics.bl.impl.operationmanagement;

import java.util.ArrayList;

public class Combination {
	
	private Combination() {}
	
	public static ArrayList<int[]> list=new ArrayList<int[]>();
	
	public static void comb(int m,int n, int r,int cities[]){
		if(r==0){
			int temp[]={cities[0],cities[1]};
			list.add(temp);
		}
		else{
			for(int i=m-n;i<m-r+1;i++) {
				cities[2-r]=i+1;
				comb(m,m-i-1,r-1,cities);
			}
		}
	}
	//测试
	public static void main(String[] args) {
		int a[]={1};
		comb(1,1,2,a);
		for (int i = 0; i <list.size(); i++) {
			System.out.println(list.get(i)[0]+" "+list.get(i)[1]);
		}
	}
}
