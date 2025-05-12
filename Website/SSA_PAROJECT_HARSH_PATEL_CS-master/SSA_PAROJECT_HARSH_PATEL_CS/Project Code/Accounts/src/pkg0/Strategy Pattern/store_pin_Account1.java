package pkg0;

public class store_pin_Account1 extends storepin{
	//stores pins for Account1-- Copies the pin from the temporary pin and saves it in the pin in the corresponding datastore
	@Override
	void store_pin(DataStore ds){
	ds.setpinAccount1(ds.getpinAccount1_temp());
}
}