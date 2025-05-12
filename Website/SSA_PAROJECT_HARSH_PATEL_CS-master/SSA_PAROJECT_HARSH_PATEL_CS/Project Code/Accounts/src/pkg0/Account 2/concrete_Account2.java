package pkg0;

public class concrete_Account2 extends abstract_factory {
	@Override
	void initialize(abstract_factory con)
	{
	// This function is responsible for creating the object of the outprocessor
	//which calls the initialize function,
		//that creates the
		//corresponding object
		output_processor op1=new output_processor();
		op1.initialize(this);
		}
		//// Create and return the object of the datastore1 and returns it.
		@Override
		public DataStore get_datastore()
		{
		return (new Account2_DataStore());
		}
		//// Create and return the object of the actions in the output processor.
		@Override
		public storepin get_storepin()
		{
		return (new store_pin_Account2());
		}
		@Override
		public promptforpin get_promptpin()
		{
		return (new prompt_pin_Account2());
		}
		@Override
		public display_menu get_displaymenu()
		{
		return (new display_menu_account2());
		}
		@Override
		public incorrect_pin_msg get_inpinmsg()
		{
		return (new incorrect_pin_Account2());
		}
		@Override
		public tooMany_attempts_msg get_attempts(){
			return (new tooMany_attempts_Account2());
		}
		@Override
		public makewithdraw get_makewithdraw()
		{
		return (new make_withdraw_Account2());
		}
		@Override
		public displaybalance get_displaybalance()
		{
		return (new display_balance_Account2());
		}
		@Override
		penalty get_penalty() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		storebalance get_storebalance() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		makedeposit get_makedeposit() {
			// TODO Auto-generated method stub
			return null;
		}
		}