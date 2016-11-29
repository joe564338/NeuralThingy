import java.util.*;
public class MyBasicRobot{
	public static double sigmoid(double x, boolean deriv){
		if(deriv == true){
			return (x *(1-x));
		}
		else{
			return (1/(1+Math.pow(Math.E,(-1*x))));
		}
	}
	public static double[][] dot(double arr1[][], double arr2[][]){
		double sum = 0;
		double[][] product = new double[arr1.length][arr2[0].length];
		for (int i = 0; i < arr1.length; i++) { 
			for (int j = 0; j < arr2[0].length; j++) { 
				for (int k = 0; k < arr1[0].length; k++) { 
					product[i][j] += arr1[i][k] * arr2[k][j];
				}
			}
		}
		return product;
	}
	public static double[][] transpose(double[][] arr){
		double[][] transposedArr = new double[arr[0].length][arr.length];
		for(int i = 0; i < transposedArr.length; i++){
			for(int k = 0; k < transposedArr[0].length;k++){
				transposedArr[i][k] = arr[k][i];
			}
		}
		return transposedArr;
	}
	public static void main(String[] args){
	
		double[][] inputData = new double[][]{
			{0,0,1},
			{0,1,1},
			{1,0,1},
			{1,1,1}
		};
		double[][] outputData = new double[][]{
			{0},
			{1},
			{1},
			{0}
		};
		Random simple = new Random(1);
		double[][] syn0 = new double[3][4];
		for(int i = 0; i <syn0.length; i++){
			for(int k = 0; k < syn0[0].length; k++){
				
				syn0[i][k] =2 * simple.nextDouble() - 1;
			}
		}
		double[][] syn1 = new double[4][1];
		for(int i = 0; i <syn1.length; i++){
			for(int k = 0; k < syn1[0].length; k++){
				syn1[i][k] =2 * simple.nextDouble() - 1;
			}
		}
		double[][] l2 = null;
		for (int j = 0; j < 60000; j++){
			double[][] l0 = inputData;
			double[][] product1 = dot(l0,syn0);
			double[][] l1 = new double[product1.length][product1[0].length];
			for (int i = 0; i < product1.length; i++){
				for(int k = 0; k < product1[0].length;k++){
					l1[i][k]=sigmoid(product1[i][k],false);
				}
			}
			double[][] product2 = dot(l1,syn1);
			
			l2 = new double[product2.length][product2[0].length];
			for (int i = 0; i < product2.length; i++){
				for(int k = 0; k < product2[0].length;k++){
					l2[i][k]=sigmoid(product2[i][k],false);
				}
			}
			double[][] l2Error = new double[outputData.length][outputData[0].length];
			for(int i = 0; i < outputData.length;i++){
				for(int k = 0; k < outputData[0].length; k++){
					l2Error[i][k] = outputData[i][k] - l2[i][k];
				}
			}
			if((j % 10000) == 0){
				
				double mean = 0;
				int counter = 0;
				for(int i = 0; i < l2Error.length; i++){
					for(int k = 0; k < l2Error[0].length; k++){
						
						mean += Math.abs(l2Error[i][k]);
						counter++;
					}
				}
				mean = mean/counter;
				System.out.println(mean);
			}
			double[][] l2Delta = new double[l2Error.length][l2Error[0].length];
			for(int i = 0; i < l2Error.length; i++){
				for(int k = 0; k < l2Error[0].length;k++){
					l2Delta[i][k] = l2Error[i][k] * sigmoid(l2[i][k], true);
				}
			}
			double[][] l1Error = dot(l2Delta, transpose(syn1));
			double[][] l1Delta = new double[l1Error.length][l1Error[0].length];
			for(int i = 0; i < l1Error.length; i++){
				for(int k = 0; k < l1Error[0].length;k++){
					l1Delta[i][k] = l1Error[i][k] * sigmoid(l1[i][k], true);
				}
			}
			double[][] temp1 = dot(transpose(l1),l2Delta);
			double[][] temp2 = dot(transpose(l0), l1Delta);
			for(int i = 0; i <syn1.length; i++){
				for(int k = 0; k < syn1[0].length; k++){
					syn1[i][k] += temp1[i][k];
				}
			}
			for(int i = 0; i <syn0.length; i++){
				for(int k = 0; k < syn0[0].length; k++){
					syn0[i][k] += temp2[i][k];
				}
			}
		}
		System.out.println("Output after training");
		for (int i = 0; i < l2.length; i++){
			for(int k = 0; k < l2[0].length;k++){
				System.out.println(l2[i][k]);
			}
		}
	}

}