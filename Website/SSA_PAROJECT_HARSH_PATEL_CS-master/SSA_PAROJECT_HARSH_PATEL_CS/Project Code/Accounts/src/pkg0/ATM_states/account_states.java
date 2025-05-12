package pkg0;

public abstract class account_states 
{
	mda_efsm_account create()
	{
		return(new mda_efsm_account());
	}
	void overdrawdraw() {
		// TODO Auto-generated method stub
		
	}
	public output_processor output;
	output_processor create_output()
	{
		return(new output_processor());
	}
	abstract void open();
	abstract void IncorrectPin(int max);
	abstract void CorrectPinBelowMin();
	abstract void CorrectPinAboveMin();
	abstract void deposit();
	abstract void BelowMinBalance();
	abstract void AboveMinBalance();
	abstract void exit();
	abstract void balance();
	abstract void withdraw();
	abstract void WithdrawBelowMinBalance();
	abstract void lock();
	abstract void login();
	abstract void incorrectlogin();
	abstract void incorrectlock();
	abstract void incorrectunlock();
	abstract void unlock();
	abstract int get_id();
	public void activate(){};
	abstract void overdrawn();
	public void Incorrectlogin(int max){}
	
}
	
class idle extends account_states
{
	public int ID;
	@Override
	public void open()
	{
	create_output().prompt_for_PIN();
	create_output().store_balance();
	create().set_count(0);
	}
	@Override
	public void Incorrectlogin(int max){
		
	}
	
	public void IncorrectPin(int max)
	{
	// Do nothing
	}
	@Override
	
	public void CorrectPinBelowMin()
	{
	// Do nothing
	}
	@Override
	public void overdrawn()
	{
		
	}
	public void CorrectPinAboveMin()
	{
	// Do nothing
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
	
	public void WithdrawBelowMinBalance()
	{
		//Do nothing
	}
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
	
	//to getid
	public int get_id()
	{
		return(0);
	}
	
	//to do login
	public void login()
	{
		//Do nothing
	}
	
	public void incorrectlogin()
	{
		//Do nothing
	}

	public void incorrectlock()
	{
		//Do nothing
	}
	
	public void incorrectunlock()
	{
		//Do nothing
	}
	
	public void logout()
	{
		//Do nothing
	}
	
	public void suspend()
	{
		//Do nothing
	}
	
	public void activate()
	{
		//do nothing
	}
	@Override
	void overdrawdraw() {
		// TODO Auto-generated method stub
		
	}
}

