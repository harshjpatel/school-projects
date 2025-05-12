package pkg0;

public class make_withdraw_Account2 extends makewithdraw{
	//Make withdraw for 
	@Override
	void make_withdraw(DataStore ds){
	ds.setbalanceAccount2(ds.getbalanceAccount2()-ds.getwithdrawAccount2_temp());
	}
}
