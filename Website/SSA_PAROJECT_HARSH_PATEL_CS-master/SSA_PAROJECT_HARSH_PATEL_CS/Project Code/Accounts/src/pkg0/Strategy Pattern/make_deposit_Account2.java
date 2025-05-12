package pkg0;

public class make_deposit_Account2 extends makewithdraw{
	//Make withdraw for ATM1
	@Override
	void make_withdraw(DataStore ds){
	ds.setbalanceATM2(ds.getbalanceAccount2()-ds.getwithdrawAccount2_temp());
	}
}

