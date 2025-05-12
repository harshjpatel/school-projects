package pkg0;

public class display_balance_Account1 extends displaybalance {
	@Override
	void display_balance(DataStore ds){
	System.out.println("Balance: $"+ds.getbalanceAccount1_temp());
	}
}
