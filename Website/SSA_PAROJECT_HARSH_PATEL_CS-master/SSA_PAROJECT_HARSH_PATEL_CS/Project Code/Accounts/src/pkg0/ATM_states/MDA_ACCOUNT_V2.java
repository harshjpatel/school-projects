package pkg0;

import java.io.IOException;
import java.util.Scanner;

public class MDA_ACCOUNT_V2{/**
* @param args the command line arguments
*/
static DataStore ds;
public static void main(String[] args) {////Driver for the ATM SYSTEM
int s;
int n=0;
mda_efsm_account md=new mda_efsm_account(); // creating the object of the mda EFSM
// TODO code application logic here

do{
Scanner in = new Scanner(System.in);
System.out.println("Select the Account");
System.out.println("1. Account-1");
System.out.println("2. Account-2");
s = in.nextInt();
if(s==1)
{
	System.out.println("You selected Account "+s);
	Account1 a1=new Account1();
	try {
		a1.initialize(md);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
else if(s==2)
{
	System.out.println("You selected Account "+s);
	Account2 a2 = new Account2();
	try {
		a2.initialize(md);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
else
{
	System.out.println("Please select a valid Account");
}
}while((s>0)&&(s<3));
}
}
