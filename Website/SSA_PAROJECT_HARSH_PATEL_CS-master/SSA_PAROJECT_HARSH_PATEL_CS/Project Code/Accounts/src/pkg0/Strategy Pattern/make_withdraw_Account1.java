package pkg0;

public class make_withdraw_Account1 extends makewithdraw{
	//Make withdraw for 
	@Override
	void make_withdraw(DataStore ds){
		ds.setbalancwithdrawAccount1(ds.getbalanceAccount1_temp()-ds.getwithdrawAccount1());
	}
}
