import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner; 
public class Main {
	//Preemptive Scheduling FUNCTIONS
	    //put functions in here
	
	
	
	
    //Non-Preemptive FUNCTIONS
	
	static void FCFS(int[] arrT,int[] burT) {
		

		
		int temp=0,temp2=0;
	
        for(int i=0; i < arrT.length; i++)
        {
        	
        
            for(int j=1; j < (arrT.length-i); j++)
            {  
                     if(arrT[j-1]> arrT[j]){  
                            //swap elements 
                    	 
                    	    //Arrival times
                            temp = arrT[j-1];  
                            arrT[j-1] = arrT[j];  
                            arrT[j] = temp;
                            
                            //burst times
                            temp2 = burT[j-1];  
                            burT[j-1] = burT[j];  
                            burT[j] = temp2;
                    }  
            }
                     
       } 
        

        //
        double burstAdd= arrT[0]+burT[0];
        
        //Waiting Time
        double waitTSum=0;
        double[] waitT= new double[arrT.length];
        
       
        for(int i=0; i < arrT.length; i++)
        {
        	if(i==0)
        	{
        		//will always result in zero
        	waitT[i]=arrT[i] - arrT[i];
        	waitTSum=arrT[i] - arrT[i];
        	}
        	
        	else if (i==1)
        	{
        	
        	waitT[i]=burstAdd-arrT[i];
        	waitTSum=burstAdd-arrT[i];
        	}
        	
        	else 
        	{
        		waitT[i]=burstAdd+burT[i-1]-arrT[i];
        		waitTSum=waitTSum+(burstAdd+burT[i-1]-arrT[i]);
        		burstAdd= burstAdd+burT[i-1];
        	}
        	
        }
        
        //Turn around Time
        double[] turnT= new double[arrT.length];
        burstAdd= arrT[0]+burT[0];
        double turnTSum=0;
        for(int i=0; i < arrT.length; i++)
        {
        	if(i==0)
        	{
        
        	turnT[i]=burstAdd - arrT[i];
        	turnTSum=turnT[i];
        	}
        	
        	else 
        	{
        
        	turnT[i]=burstAdd+burT[i]-arrT[i];
        	turnTSum=turnTSum+(burstAdd+burT[i]-arrT[i]);
        	burstAdd=burstAdd+burT[i];
        	}
        }
        
        System.out.println("Waiting Time:     Turnaround time:");
        
        for(int i=0; i < arrT.length; i++)
        {
        	
        	System.out.println("P"+i+": "+ waitT[i] +"               "+"P"+i+": "+turnT[i]);
        	
        }
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        System.out.println("Ave "+df.format(waitTSum/arrT.length)+ "            Ave: "+df.format(turnTSum/arrT.length));
        

		
	}
	
	//Disk Scheduling FUNCTIONS
	
	    //put functions in here
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);  
	
		System.out.println(" 1. Preemptive Scheduling \n 2. Non-Preemptive Scheduling \n 3. Disk Scheduling  ");
		System.out.println("Enter the number: ");
		int option1= input.nextInt();
		
		
		
		if (option1==1) 
		
		{
			
			System.out.println("Preemptive Scheduling");
			
			
			
		}
		
		else if (option1==2) 
			
		{
			
			System.out.println("Non-Preemptive Scheduling");
			
			System.out.println("Input no. of processes [2-9]: ");
			
			int numP=input.nextInt();
			
			List<Integer> arrT = new ArrayList<Integer>();
			List<Integer> burT = new ArrayList<Integer>();
			
			System.out.println("Input Arrival Times: ");
			
			for(int i=0;i<numP;i++) {
				
				System.out.print("AT "+(i+1)+": " );
				
				int inp=input.nextInt();
				
				arrT.add(inp);
				
			}
			
			System.out.println("Input Burst Times: ");
			
			for(int i=0;i<numP;i++) {
				
				System.out.print("BT "+(i+1)+": " );
				
				int inp=input.nextInt();
				
				burT.add(inp);
				
			}
			
			System.out.println(" [A] First Come First Serve (FCFS) \n [B] Shortest Job First (SJF) \n [C] Priority (Prio) \n [D] Exit ");
			
			System.out.println("Enter the letter of your choice: ");
			
			String option2= input.next();
			
			if (option2.equals("A") ||option2.equals("a") ) {
				
				System.out.println("[A] First Come First Serve");
				
				//calling of function
				FCFS(arrT.stream().mapToInt(i -> i).toArray(),burT.stream().mapToInt(i -> i).toArray());
			}
		
		}
		
		else if (option1==3) 
			
		{
			
			System.out.println("Disk Scheduling");
			
			
			
		}
		
		
	}

}
