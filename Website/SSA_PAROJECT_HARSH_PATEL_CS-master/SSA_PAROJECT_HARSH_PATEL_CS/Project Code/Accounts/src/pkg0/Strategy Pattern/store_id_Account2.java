package pkg0;

public class store_id_Account2 extends storelogin{
	//stores pins for Account1-- Copies the pin from the temporary pin and saves it in the pin in the corresponding datastore
	@Override
	void store_pin(DataStore ds){
	ds.setpinAccount2(ds.getpinAccount2_temp());
}

	@Override
	void store_login(DataStore ds) {
		// TODO Auto-generated method stub
		
	}
}