package pkg0;

public class check_pin extends account_states
{
	@Override
	public void open()
	{
	//Do Nothing
	}
	
	@Override
	public void IncorrectPin(int max)
	{
	create_output().incorrect_pin_msg(); //displays the incorrect pin message
	if(max==create().get_count()) //if the value of count is equal to the max value,
	{
	create_output().too_many_attempts_msg(); //print the too many attempts message;
	}
	else
	{
	create().set_count((create().get_count()+1));
	}
	}
	
	@Override
	public void CorrectPinBelowMin()
	{
	create_output().display_menu();
	}
	
	@Override
	public void CorrectPinAboveMin()
	{
	create_output().display_menu();
	}
	@Override
	
	public void deposit()
	{
	//Do Nothing
	}
	
	@Override
	public void BelowMinBalance()
	{
	//Do Nothing
	}
	
	@Override
	public void AboveMinBalance()
	{
	//Do Nothing
	}
	
	@Override
	public void exit()
	{
	//Do Nothing
	}
	
	@Override
	public void balance()
	{
	//Do Nothing
	}
	
	@Override
	public void withdraw()
	{
	//Do Nothing
	}
	
	@Override
	public void lock()
	{
	//Do Nothing
	}
	@Override
	public void unlock()
	{
	//Do Nothing
	}
	
	@Override
	public int get_id()
	{
	return(1);
	}
	
	public void login()
	{
		//Do nothing
	}
	
	public void logout()
	{
		
	}

	@Override
	void WithdrawBelowMinBalance() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void incorrectlogin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void incorrectlock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void incorrectunlock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void overdrawn() {
		// TODO Auto-generated method stub
		
	}
}
