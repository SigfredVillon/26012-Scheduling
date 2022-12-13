import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner; 
public class Main {
	
	//Preemptive Scheduling FUNCTIONS
	    //put functions in here
	static void SRTF(int n, int [] at,int [] f,int [] bt, int [] k ) {
		int st=0, t=0;
		
		float aveWT=0, aveTA=0;

		int ct[] = new int[n];

		int ta[] = new int[n];
		
		int wt[] = new int[n]; 
		
		while(true){
			int mininum=99,c=n;
			
			if (t==n)
			
			break;
		   
			for (int i=0;i<n;i++){
			   if ((at[i]<=st) && (f[i]==0) && (bt[i]<mininum)){
				mininum=bt[i];
				c=i;
				   }
			   }
		   
			if (c==n)
				st++;
			else{
			   bt[c]--;
			   st++;
			   if (bt[c]==0){
				   ct[c]= st;
				   f[c]=1;
				   t++;
					   }
				   }
			   }
		   for(int i=0;i<n;i++){
				ta[i] = ct[i] - at[i];
				wt[i] = ta[i] - k[i];
				aveWT+= wt[i];
				aveTA+= ta[i];
		   }
		   System.out.println("Waiting time\t\t\t\tTurnaround time");
		   for(int i=0;i<n;i++){
			   System.out.println(wt[i]+"\t\t\t\t\t"+ ta[i]);
		   }
		   System.out.println("Average Waiting Time: "+ (float)(aveWT/n)+"\t\tAverage Turnaround Time: "+ (float)(aveTA/n));
		}
	
	static void PP(int n, int [] at, int [] bt) {
		Scanner input = new Scanner(System.in);
		int twt = 0;
		int ttat = 0;
		float aveWT = 0;
		float aveTT = 0;
		int ct = 0;
		int complete = 0;
		int[] prioNumArr = new int[n];
   
		System.out.println("Input individual priority number: ");
		for (int i=0;i<n;i++){
			System.out.print("Prio"+(i+ 1)+": ");
			int prioNum = input.nextInt();
			prioNumArr[i] = prioNum;
		}
		
		Priority prioArr[] = new Priority[n];
		for (int i = 0; i < n; i++){
			prioArr[i] = new Priority(i+1, at[i], bt[i], prioNumArr[i], 0, 0, 0, 0, bt[i], false);
		}
   
		ArrayList<Integer> seekQ = new ArrayList<>();
		boolean processing = false;
		int prev = -1;

		while (complete != n){
			int in = -1;
			int min = 999;
			for (int i=0; i< n; i++){
				if ((prioArr[i].at <= ct) && prioArr[i].isCompleted == false){
					if (prioArr[i].prioNum < min) {
						min = prioArr[i].prioNum;
						in = i;
					}
					if (prioArr[i].prioNum == min) {
						if (prioArr[i].at < prioArr[in].at) {
							min = prioArr[i].prioNum;
							in = i;
						}
					}
				}
			}
			if (in != -1) {
				if (prioArr[in].rb == prioArr[in].bt) {
					prioArr[in].startT = ct;
				}
			}
			seekQ.add(in);
			
			if(in == -1)
			{
				ct++;
				continue;
			}
			
			prioArr[in].rb -= 1;
			ct++;

			if (prioArr[in].rb == 0) {
				prioArr[in].ct = ct;
				prioArr[in].tat = prioArr[in].ct - prioArr[in].at;
				prioArr[in].wt = prioArr[in].tat - prioArr[in].bt;
				
				twt += prioArr[in].wt;
				ttat += prioArr[in].tat;
				
				prioArr[in].isCompleted = true;
				complete++;
			}
		}
		aveWT = (float)twt/n;
		aveTT = (float)ttat/n;

    System.out.println("Waiting time: \t\t\t Turnaround time: ");
    for (int i=0;i<prioArr.length;i++){
        System.out.println("P"+(i + 1)+": "+prioArr[i].wt+"\t\t\t\t"+" P"+(i + 1)+": "+prioArr[i].tat);
    }
    System.out.println("Average Waiting Time: " + aveWT + "\t Average Turnaround Time: " + aveTT);    
}

	public static void RR(int n, int at[],int bt[], int tempb[]){ 

		Scanner input = new Scanner(System.in);
		int tq, timer = 0, maxProIn = 0;
		boolean complete[] = new boolean[n];
		float avgWait = 0, avgTT = 0;
		int waitT[] = new int[n];
		int turnT[] = new int[n];
		int queue[] = new int[n];

		System.out.print("Input time slice: ");
		tq = input.nextInt();
		for(int i = 0; i < n; i++){ 
			complete[i] = false;
			queue[i] = 0;
		}
		while(timer < at[0])
			timer++;
		queue[0] = 1;
		
		while(true){
			boolean flag = true;
			for(int i = 0; i < n; i++){
				if(tempb[i] != 0){
					flag = false;
					break;
				}
			}
			if(flag)
				break;

			for(int i = 0; (i < n) && (queue[i] != 0); i++){
				int ctr = 0;
				while((ctr < tq) && (tempb[queue[0]-1] > 0)){
					tempb[queue[0]-1] -= 1;
					timer += 1;
					ctr++;
					checkNewArrival(timer, at, n, maxProIn, queue);
				}
					if((tempb[queue[0]-1] == 0) && (complete[queue[0]-1] == false)){
						turnT[queue[0]-1] = timer;
						complete[queue[0]-1] = true;
				}
				boolean idle = true;
				if(queue[n-1] == 0){
					for(int k = 0; k < n && queue[k] != 0; k++){
						if(complete[queue[k]-1] == false){
							idle = false;
						}
					}
				}
				else
					idle = false;

				if(idle){
					timer++;
					checkNewArrival(timer, at, n, maxProIn, queue);
				}
				queueMaintainence(queue,n);
			}
		}

		for(int i = 0; i < n; i++){
			turnT[i] = turnT[i] - at[i];
			waitT[i] = turnT[i] - bt[i];
		}

		System.out.print("Waiting time\t\t\t\t\tTurnaround time"
                     + "\n");
		for(int i = 0; i < n; i++){
			System.out.print("P"+(i+1)+": "+waitT[i]+"\t\t\t\t\t\t"+"P"+(i+1)+": "+turnT[i]+ "\n");
    }
		for(int i =0; i< n; i++){
			avgWait += waitT[i];
			avgTT += turnT[i];
		}
		System.out.print("Average Waiting Time: "+(avgWait/n)+"\t\t\tAverage Turnaround Time : "+(avgTT/n));
	}
	public static void queueUpdation(int queue[],int timer,int arrival[],int n, int maxProccessIndex){
		int zeroIndex = -1;
		for(int i = 0; i < n; i++){
			if(queue[i] == 0){
				zeroIndex = i;
				break;
			}
		}
		if(zeroIndex == -1)
			return;
		queue[zeroIndex] = maxProccessIndex + 1;
	}

	public static void checkNewArrival(int timer, int arrival[], int n, int maxProccessIndex,int queue[]){
		if(timer <= arrival[n-1]){
			boolean newArrival = false;
			for(int j = (maxProccessIndex+1); j < n; j++){
				if(arrival[j] <= timer){
					if(maxProccessIndex < j){
						maxProccessIndex = j;
						newArrival = true;
					}
				}
			}
			if(newArrival)
				queueUpdation(queue,timer,arrival,n, maxProccessIndex);       
		}
	}

	public static void queueMaintainence(int queue[], int n){
		for(int i = 0; (i < n-1) && (queue[i+1] != 0) ; i++){
			int temp = queue[i];
			queue[i] = queue[i+1];
			queue[i+1] = temp;
		}
	}
	
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
	
	
	static void SJF(int[] at,int[] bt) {
		int n = at.length;
		int pid[] = new int[n];
		int ct[] = new int[n]; // ct means complete time
		int ta[] = new int[n]; // ta means turn around time
		int wt[] = new int[n];  //wt means waiting time
		int f[] = new int[n];  // f means it is flag it checks process is completed or not
		int st=0, tot=0;
		float avgwt=0, avgta=0;
		 
		for(int i=0;i<n;i++)
		{
			pid[i] = i+1;
		}
		
		boolean a = true;
		while(true)
		{
		int c=n, min=999;
		if (tot == n) // total no of process = completed process loop will be terminated
		break;
		for (int i=0; i<n; i++)
		{
		/*
		* If i'th process arrival time <= system time and its flag=0 and burst<min
		* That process will be executed first
		*/
		if ((at[i] <= st) && (f[i] == 0) && (bt[i]<min))
		{
		min=bt[i];
		c=i;
		}
		}
		/* If c==n means c value can not updated because no process arrival time< system time so we increase the system time */
		if (c==n)
		st++;
		else
		{
		ct[c]=st+bt[c];
		st+=bt[c];
		ta[c]=ct[c]-at[c];
		wt[c]=ta[c]-bt[c];
		f[c]=1;
		tot++;
		}
		}
		
        System.out.println("Waiting Time:     Turnaround time:");
        
        for(int i=0; i < n; i++)
        {
        	avgwt+= wt[i];
    		avgta+= ta[i];
        	System.out.println("P"+pid[i]+": "+ wt[i] +"               "+"P"+pid[i]+": "+ta[i]);
        	
        }
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        System.out.println("Ave "+df.format(avgwt/n)+ "            Ave: "+df.format(avgta/n));
	}

	
	static void PRIO(int[] at,int[] bt) {	
		

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
			
			System.out.println("Input no. of processes [2-9]: ");
			
			int numP=input.nextInt();
			int pid[] = new int[numP];
			int f[] = new int[numP]; 
			int k[]= new int[numP]; 
			int at[] = new int[numP];
			int bt[] = new int[numP]; 
			int tempb[]= new int[numP];
			
			System.out.println("Input Arrival Times: ");
			
			for(int i=0;i<numP;i++) {
				
				System.out.print("AT "+(i+1)+": " );
				
				at[i]= input.nextInt();
				
			}
			
			System.out.println("Input Burst Times: ");
			
			for(int i=0;i<numP;i++) {
				
				System.out.print("BT "+(i+1)+": " );
				bt[i]= input.nextInt();
				
				pid[i]= i+1;

				k[i]= bt[i];
				
				f[i]= 0;

				tempb[i] = bt[i];
				
			}
			boolean rep=false;
			while (rep==false) {
			System.out.println(" [A] Shortest Remaining Time First (SRTF) \n [B] Round Robin (RR) \n [C] Preemptive Priority (P-Prio) \n [D] Multi-level Queue Scheduling (MQS) \n [E] Exit ");
			
			System.out.println("Enter choice: ");
			
			String option2= input.next();
			switch(option2) {
				case "A" : 
				case "a":
					//[A] Shortest Remaining Time First
					SRTF(numP, at, f, bt, k);
				  	break;
				case "B":
				case "b":
					//[B] Round Robin (RR)
					RR(numP, at,bt, tempb);
					break;
				case "C":
				case "c":
					//[C] Preemptive Priority (P-Prio)
					PP(numP, at, bt);
					break;
				case "E":
				case "e":
					rep=true;
					break;
				default:			
					System.out.println("Option is not available choose again");
			  }
			
			}
		}
		
		else if (option1==2) 
			
		{
			// copy start
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
			boolean rep=false;
			while (rep==false) {
				
			System.out.println(" [A] First Come First Serve (FCFS) \n [B] Shortest Job First (SJF) \n [C] Priority (Prio) \n [D] Exit ");
			
			System.out.println("Enter the letter of your choice: ");
			//copy end
			String option2= input.next();
			
			if (option2.equals("A") ||option2.equals("a") ) {
				
				System.out.println("[A] First Come First Serve");
				
				//calling of function
				FCFS(arrT.stream().mapToInt(i -> i).toArray(),burT.stream().mapToInt(i -> i).toArray());
			}
			
			else if (option2.equals("B") ||option2.equals("b") ) {
				
				System.out.println("[B] Shortest Job First (SJF) ");
				
				//calling of function
				SJF(arrT.stream().mapToInt(i -> i).toArray(),burT.stream().mapToInt(i -> i).toArray());
			}
			
			else if (option2.equals("C") ||option2.equals("c") ) {
				
				System.out.println("[C] Priority (Prio)");
				
				//calling of function
				PRIO(arrT.stream().mapToInt(i -> i).toArray(),burT.stream().mapToInt(i -> i).toArray());
			}
			
			else if (option2.equals("D") ||option2.equals("d") ) {
				rep=true;
				System.out.println("End");
				
			}
			
			else {
				System.out.println("Wrong input try again");
				
			}
			
			}
			
		
		}
		
		else if (option1==3) 
			
		{
			
			System.out.println("Disk Scheduling");
			
			
			
		}
		
		
	}

}


class Priority {
    int n, at, bt, prioNum, startT, ct, tat, wt, rb;
    boolean isCompleted;
    Priority(int n, int at, int bt, int prioNum, int startT, int ct, int tat, int wt, int rb, boolean isCompleted) {
        this.n = n;
        this.at = at;
        this.bt = bt;
        this.prioNum = prioNum;
        this.startT = startT;
        this.ct = ct;
        this.tat = tat;
        this.wt = wt;
        this.rb = rb;
        this.isCompleted = isCompleted;
    }
}
