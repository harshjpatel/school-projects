package pkg0;

public class ready extends account_states
{
	@Override
	public void open()
	{
	//Do Nothing
	}
	@Override
	public void IncorrectPin(int max)
	{
	//Do Nothing
	}
	@Override
	public void CorrectPinBelowMin()
	{
	//Do Nothing
	}
	@Override
	public void CorrectPinAboveMin()
	{
	//Do Nothing
	}
	@Override
	public void deposit()
	{
	create_output().MakeDeposit();
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
	public void balance()
	{
	create_output().Display_Balance();
	}
	@Override
	public void withdraw()
	{
	create_output().MakeWithdraw();
	}
	@Override
	public void lock()
	{
		//Do nothing only state change
	}
	@Override
	public void unlock()
	{
	//Do Nothing
	}
	@Override
	public int get_id()
	{
	return(2);
	}
	@Override
	void WithdrawBelowMinBalance() {
		// TODO Auto-generated method stub
		
	}
	@Override
	void login() {
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
	void exit() {
		// TODO Auto-generated method stub
		
	}
	@Override
	void overdrawn() {
		// TODO Auto-generated method stub
		
	}
	
	
}
