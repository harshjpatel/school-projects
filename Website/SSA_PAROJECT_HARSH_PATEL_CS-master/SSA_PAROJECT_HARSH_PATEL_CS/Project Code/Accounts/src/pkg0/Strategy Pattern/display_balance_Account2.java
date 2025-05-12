package pkg0;

public class display_balance_Account2 extends displaybalance {
	@Override
	void display_balance(DataStore ds)
	{
		System.out.println("Balance: $"+ds.getbalanceAccount2());
	}
}
