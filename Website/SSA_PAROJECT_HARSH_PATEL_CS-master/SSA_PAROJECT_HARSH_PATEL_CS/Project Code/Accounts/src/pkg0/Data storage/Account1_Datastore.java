package pkg0;

public class Account1_Datastore extends DataStore 
{
	//These variables are used to store data in the datastore
	public static int balance;
	public static String pin;
	public static int deposit;
	public static int withdraw;
	//These variables are used to store data in the temporary datastore
	public static int balancetemp;
	public static String pintemp;
	public static int deposittemp;
	public static int withdrawtemp;
	@Override
	public void setbalanceAccount1_temp(int a) { //Set the value of the balance in the datastore
	balance=a;
	}
	@Override
	public int getbalanceAccount1_temp() //get the value of balance from the datastore
	{return (balance);}
	@Override
	public void setpinAccount1_temp(String p) //Set the value of the pin in the datastore
	{pin=p;}
	@Override
	public String getpinAccount1_temp() //get the value of pin from the datastore
	{return (pin);}
	@Override
	public void setdepositAccount1(int d) //Set the value of the deposit in the datastore
	{deposit=d;}
	@Override
	public int getdepositAccount1() //get the value of deposit from the datastore
	{return (deposit);}
	@Override
	public void setwithdrawAccount1(int w) //Set the value of the deposit in the datastore
	{withdraw=w;}
	@Override
	public int getwithdrawAccount1() //get the value of withdraw from the datastore
	{return(withdraw);}
	@Override
	public void setdepositAccount1_temp(int d) //Set the value of the deposit in the temporary datastore
	{deposittemp=d;}
	@Override
	public int getdepositAccount1_temp() //get the value of deposit from the temporary datastore
	{return (deposittemp);}
	@Override
	public void setwithdrawAccount1_temp(int w) //Set the value of the withdraw in the temporary datastore
	{withdrawtemp=w;}
	@Override
	public int getwithdrawAccount1_temp() //get the value of withdraw from the temporary datastore
	{return(withdrawtemp);}
	}

