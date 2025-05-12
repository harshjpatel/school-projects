package pkg0;

public class make_deposit_Account1 extends makedeposit {
	//Make deposit for ATM1
	@Override
	void make_deposit(DataStore ds){
	ds.setbalancedepositAccount1(ds.getbalanceAccount1_temp()+ds.getdepositAccount1());
	}
}
