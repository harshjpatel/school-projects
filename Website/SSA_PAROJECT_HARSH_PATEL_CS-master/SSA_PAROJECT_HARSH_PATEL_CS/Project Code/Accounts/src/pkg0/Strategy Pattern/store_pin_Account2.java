package pkg0;

public class store_pin_Account2 extends storepin
{
	//stores pins for Account-- Copies the pin from the temporary pin and saves it in the pin in the corresponding datastore
	@Override
	void store_pin(DataStore ds){
	ds.setpinAccount2(ds.getpinAccount2_temp());
}
}