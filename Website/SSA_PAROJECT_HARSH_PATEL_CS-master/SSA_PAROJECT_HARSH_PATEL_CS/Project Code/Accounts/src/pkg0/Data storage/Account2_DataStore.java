package pkg0;

public class Account2_DataStore extends DataStore 
{
	//These variables are used to store data in the datastore
	public static int balance;
	public static int pin;
	public static int deposit;
	public static int withdraw;
	//These variables are used to store data in the temporary datastore
	public static int balancetemp;
	public static int pintemp;
	public static int deposittemp;
	public static int withdrawtemp;
	@Override
	public void setbalanceAccount2(int d) { //Set the value of the balance in the datastore
	balance=d;
	}
	@Override
	public int getbalanceAccount2() //get the value of balance from the datastore
	{return (balance);}
	@Override
	public void setpinAccount2(int p) //Set the value of the pin in the datastore
	{pin=p;}
	@Override
	public int getpinAccount2() //get the value of pin from the datastore
	{return (pin);}
	@Override
	public void setdepositAccount2(int d) //Set the value of the deposit in the datastore
	{deposit=d;}
	@Override
	public int getdepositAccount2() //get the value of deposit from the datastore
	{return (deposit);}
	public void setwithdrawAccount2(int w) //Set the value of the deposit in the datastore
	{withdraw=w;}
	@Override
	public int getwithdrawAccount2() //get the value of withdraw from the datastore
	{return(withdraw);}
	@Override
	public void setbalanceAccount2_temp(int a) { //Set the value of the balance in the temporary datastore
	balancetemp=a;
	}
	@Override
	public int getbalanceAccount2_temp() //get the value of balance from the temporary datastore
	{return (balancetemp);}
	@Override
	public void setpinAccount2_temp(int p) //Set the value of the pin in the temporary datastore
	{pintemp=p;}
	@Override
	public int getpinAccount2_temp() //get the value of pin from the temporary datastore
	{return (pintemp);}
	@Override
	public void setdepositAccount2_temp(int d) //Set the value of the deposit in the temporary datastore
	{deposittemp=d;}
	@Override
	public int getdepositAccount2_temp() //get the value of deposit from the temporary datastore
	{return (deposittemp);}
	@Override
	public int getwithdrawAccount2_temp() //get the value of withdraw from the temporary datastore
	{return(withdrawtemp);}
	public void setidAccount2_temp(int y) //Set the value of the deposit in the temporary datastore
	{deposittemp=y;}}
	
	

