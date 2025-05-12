package pkg0;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
public class Account1<concrete_Account1>
{
	DataStore ds;
	@SuppressWarnings("rawtypes")
	mda_efsm_account md;
	public void initialize(@SuppressWarnings("rawtypes") mda_efsm_account md1) throws IOException
	{
		pkg0.concrete_Account1 catm=new pkg0.concrete_Account1(); //create the object of the concrete class
		catm.initialize(catm); // and intializing it
		ds=new Account1_Datastore();
		md=md1;
		menu();
	}
	
	public void print()
	{
		System.out.println("This is Account-1");
	}
	public void menu() throws IOException
	{
		int n1=0;
		String p = null;
		String y;
		float a;
		float d;
		float w;
		String x = null;
		do
		{
			System.out.println("\n");
			System.out.println("Select the Operation you would like to perform in Account1");
			System.out.println("1. open(String p, String y, float a)");
			System.out.println("2. Login(String x)");
			System.out.println("3. pin(string x)");
			System.out.println("4. deposit(float d)");
			System.out.println("5. withdraw (float w)");
			System.out.println("6. balance()");
			System.out.println("8. lock(string x)");
			System.out.println("9. unlock(string x)");
			System.out.println("10. exit");
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
					System.out.println("\nEnter the value of pin\n");
					p=in2.readLine();
					System.out.println("\nEnter the value of user-id\n");
					y=in2.readLine();
					System.out.println("\nEnter the value of balance\n");
				    a=Float.parseFloat(in2.readLine());
					open(p,y,a);
					break;
				case 2:
					System.out.println("Enter the value of User-id \n:");
					y=in2.readLine();
					login(y);
					break;
				case 3:
					System.out.println("Enter the value of pin\n");
					y=in2.readLine();
					pin(p);
					break;
				case 4: 
					System.out.println("Enter the value of deposit\n");
					d=Float.parseFloat(in2.readLine());
					//System.out.println("Deposit in Account1 "+x);
					deposit(d);
					break;
				case 5:
					System.out.println("Enter the value of withdrawal");
					w=Float.parseFloat(in2.readLine());
					withdraw(w);
					break;
				case 6:
					balance();
					break;
				case 7: 
					logout();
					break;
				case 8:
					System.out.println("Enter the value of pin\n");
					x=in2.readLine();
					lock(x);
					break;
				case 9:
					System.out.println("Enter the value of pin\n");
					x=in2.readLine();
					unlock(x);
					break;
				case 10:
					exit();
					break;
				default: System.out.println("Operation not permitted in this state\n");
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
		

		public void open(String p, String y,float a)
		{
			
			ds.setpinAccount1_temp(p);
			ds.setidAccount1_temp(y);
			ds.setbalanceAccount1_temp(a); //store the data in the temporary variables
			md.open(); //call the function in the o/p
		}
		
		public void login(String y) 
		{
			if(y.equals(ds.getidAccount1_temp()))
			{
				md.login();
			}
			else
			{
				md.IncorrectPin(1);
			}
		
	}

		public void pin(String x)
		{
			if(x.equals(ds.getpinAccount1_temp()))
			{
				if(ds.getbalanceAccount1_temp()>500)
				{
					md.CorrectPinAboveMin();
				}
				else if(ds.getbalanceAccount1_temp()<500)
				{
					md.CorrectPinBelowMin();
				}
			}
			else
			{
				md.IncorrectPin(2);
				System.out.println("Message:Incorrect Pin");
			}
		}
		
		public void deposit(float d)
		{
			ds.setdepositAccount1(d);
			//System.out.println("Deposit value in deposit function " +d);
			md.deposit();
			if(ds.getbalanceAccount1_temp()+ds.getdepositAccount1()<500)
			{
				md.BelowMinBalance();
			}
			else
			{
				md.AboveMinBalance();
			}
		}
		public void withdraw(float w)
		{
			ds.setwithdrawAccount1(w);
			if((ds.getbalanceAccount1_temp()>500))
			{
				md.withdraw();
			}
			if(ds.getbalanceAccount1_temp()-ds.getwithdrawAccount1()<500)
			{
				md.BelowMinBalance();
			}
			else
			md.AboveMinBalance();
		}
		
		public void balance()
		{
			ds.getbalanceAccount1();
			md.balance();
		}
		

		public void logout()
		{
			md.open();
		}
		public void lock(String x)
		{
			if(x.equals(ds.getpinAccount1_temp()))
			{
				md.lock();
			}
		}
	
		public void unlock(String x)
		{
			if(x.equals(ds.getpinAccount1_temp()))
			{
				md.unlock();
				if(ds.getbalanceAccount1_temp()<500)
				{
					md.BelowMinBalance();
				}
				else
				{
					md.AboveMinBalance();
				}
			}
		}
			
		public void exit()
		{
			md.exit();
		}
}
	

