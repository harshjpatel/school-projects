package pkg0;

public class store_id_Account1 extends storelogin{
	//stores pins for ATM1-- Copies the pin from the temporary pin and saves it in the pin in the corresponding datastore
	@Override
	void store_pin(DataStore ds){
	ds.setpinAccount1(ds.getpinAccount1_temp());
}

	@Override
	void store_login(DataStore ds) {
		// TODO Auto-generated method stub
		
	}
}