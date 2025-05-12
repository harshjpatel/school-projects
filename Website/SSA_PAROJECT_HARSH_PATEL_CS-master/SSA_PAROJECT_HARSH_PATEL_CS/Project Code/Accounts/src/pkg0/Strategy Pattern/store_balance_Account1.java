package pkg0;

public class store_balance_Account1 extends storebalance {

	@Override
	void store_balance(DataStore ds){
		ds.setbalanceAccount1(ds.getbalanceAccount1_temp());
	}
}
