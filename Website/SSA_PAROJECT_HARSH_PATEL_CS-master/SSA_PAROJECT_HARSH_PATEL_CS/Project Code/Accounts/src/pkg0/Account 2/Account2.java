package pkg0;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
public class Account2 
{
	DataStore ds;
	@SuppressWarnings("rawtypes")
	mda_efsm_account md;
	public void initialize(@SuppressWarnings("rawtypes") mda_efsm_account md2) throws IOException
	{
		concrete_Account2 catm=new concrete_Account2(); //create the object of the concrete class
		catm.initialize(catm); // and intializing it
		ds=new Account2_DataStore();
		md=md2;
		menu();
	}
	
	public void print()
	{
		System.out.println("This is Account-2");
	}
	public void menu() throws NumberFormatException, IOException
	{
		int n1=0;
		int p=0;
		int y=0;
		int a=0;
		int d=0;
		int w=0;
		
		do
		{
			System.out.println("\n");
			System.out.println("Select the Operation you would like to perform in Account1");
			System.out.println("1. open(int p, int y,int a)");
			System.out.println("2. Login(String x)");
			System.out.println("3. pin(int x)");
			System.out.println("4. deposit(int d)");
			System.out.println("5. withdraw (int w)");
			System.out.println("6. balance()");
			System.out.println("7. Logout(String x):");
			System.out.println("8. Suspend(String x)");
			System.out.println("9. activate(String x)");
			System.out.println("10. exit()");
			Scanner in1 = new Scanner(System.in);
			n1=in1.nextInt();
			if(n1<=10)
			{
				BufferedReader in2 = new BufferedReader(new InputStreamReader(System.in));
				BufferedReader in3 = new BufferedReader(new InputStreamReader(System.in));
				BufferedReader in4 = new BufferedReader(new InputStreamReader(System.in));
				switch (n1) 
				{
				case 1: 
					System.out.println("\nEnter the value of pin");
					p=(int) Float.parseFloat(in2.readLine());
					System.out.println("\nEnter the value of user-id");
					y=(int) Float.parseFloat(in2.readLine());
					System.out.println("\nEnter the value of balance");
					a=(int) Float.parseFloat(in2.readLine());;
					open(p,y,a);
					break;
				
				case 2:
					System.out.println("Enter the value of User-id \n:");
					y=(int) Float.parseFloat(in2.readLine());
					login(y);
					break;
					
				case 3: 
					System.out.println("Enter the value of pin\n");
					y=((int) Float.parseFloat(in2.readLine()));;
					pin(p);
					break;
			
				case 4: 
					System.out.println("Enter the value of deposit");
					d=((int) Float.parseFloat(in2.readLine()));;
					//System.out.println("Deposit in ATM1 "+x);
					deposit(d);
					break;
					
				case 5:
					System.out.println("Enter the value of withdrawal");
					w=p=((int) Float.parseFloat(in2.readLine()));;
					withdraw(w);
					break;
					
				case 6:
					balance();
					break;
					
				case 7: 
					logout();
					break;
					
				case 8: 
					suspend();
					break;
					
				case 9:
					activate();
					break;
		
				case 10:
					exit();
					break;
				
				default: System.out.println("Operation not permitted in this state");
				break;
			}
		}
		else
		{
			menu();
		}
		}
		while(n1<=10);
	}

	


		//to st0re data of account-2
		public void open(int p, int y,float a)
		{
			 //store the data in the temporary variables
			ds.setpinAccount2_temp(p);
			ds.setidAccount2_temp(y);
			ds.setbalanceAccount2_temp(a);
			md.open(); //call the function in the o/p
		}
	
		//to check pin of account-2

		public void login(int y) 
		{
			if(y==(ds.getidAccount2_temp()))
			{
				md.login();
			}
			else
			{
				md.IncorrectPin(1);
				System.out.println("Message:Incorrect Pin");
			}
			// TODO Auto-generated method stub
		
	}
		
		public void pin(int p)
		{
			if(p==(ds.getpinAccount2_temp()))
			{
				if(ds.getbalanceAccount2_temp()>500)
				{
					md.CorrectPinAboveMin();
				}
				else if(ds.getbalanceAccount2_temp()<500)
				{
					md.CorrectPinBelowMin();
				}
			}
			else
			{
				md.IncorrectPin(1);
				System.out.println("Message:Incorrect Pin");
			}
		}
		//to make deposite of account-2
		public void deposit (int d)
		{
			ds.setdepositAccount2_temp(d);
			//System.out.println("Deposit value in deposit function " +d);
			md.deposit();
		}
		
		//to make withdrawal from account-2
		public void withdraw(int w)
		{
			ds.setwithdrawAccount2_temp(w);
			if((ds.getbalanceAccount2()-w)>0)
			{
				md.withdraw();
			}
			else
			md.NoFunds();
		}
		
		//to display balance from account-2
		public void balance()
		{
			md.balance();
		}
		
		//to login into account-2
	

		//to logout from account-2
		public void logout()
		{
			md.login();
		}
		
		//to suspend the account
		public void suspend()
		{
			md.suspended();
		}
		
		//to activate the account
	
		
		public void activate()
		{
			md.activate();
		}
		
		public void suspnd()
		{
			md.suspended();
		}
		
		public void exit()
		{
			md.exit();
		}

		
}
	

