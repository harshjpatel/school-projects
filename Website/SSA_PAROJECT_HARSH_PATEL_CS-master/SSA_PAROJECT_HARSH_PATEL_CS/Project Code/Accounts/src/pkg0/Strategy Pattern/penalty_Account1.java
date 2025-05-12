package pkg0;

public class penalty_Account1 extends penalty{
	//Impose penalty for 
	@Override
	void in_penalty(DataStore ds){
	ds.setbalanceAccount1(ds.getbalanceAccount1()-20);
	}
}
