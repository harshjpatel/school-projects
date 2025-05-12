package pkg0;

public class mda_efsm_account 
{
	//count is responsible for storing the value of the number of attempts.
	public static int count=0;
	//list of states
	private final account_states lst[] = {new idle(), new check_pin(), new ready(),new s1(),new overdrawn(),new locked(), new suspended()};
	//account_states
	public account_states as=lst[0];
	//object of different states are created and the object is initialized with the idle state.
	
	//open the account function
	public void open()
	{
		as.open(); //calling the action in the states
		switch (as.get_id()) 
		{ 	//based on the current state, new new states are assigned to the current state.
			case 0: as=lst[1]; //State change to idle
			//System.out.println("State changed to Idle");
		break;
		default: System.out.println("Operation not permitted in this state");
		break;
		}
	}
	
	//login user-id function
	public void login() 
	{
		as.login();
		switch (as.get_id()) 
		{
			case 0: as=lst[1];
			System.out.println("State changed to check-pin state");
			break;
			default: System.out.println("Operation not permitted in this state");
			break;
		}
	}
	
	//IncorrectPin function
	public void IncorrectPin(int max)
	{
	max=2;
	as.IncorrectPin(max);
	switch (as.get_id()) {
	case 1: if(count==max+1)
	{
	as=lst[0];
	//System.out.println("State changed to IDLE");
	}else if(count<max+1)
	{
	as=lst[1];
	//System.out.println("State changed to CHECKPIN");
	}
	break;
	default: System.out.println("Operation not permitted in this state");
	break;
	}
	}
	public void CorrectPinAboveMin()
	{
	as.CorrectPinAboveMin();
	switch (as.get_id()) {
	case 1: as=lst[2];
	//System.out.println("State changed to READY");
	break;
	default: System.out.println("Operation not permitted in this state");
	break;
	}
	}
	public void CorrectPinBelowMin()
	{
	as.CorrectPinBelowMin();
	switch (as.get_id()) {
	case 1: as=lst[5];
	//System.out.println("State changed to OVERDRAWN");
	break;
	default: System.out.println("Operation not permitted in this state");
	break;
	}
	}
	public void deposit()
	{
	as.deposit();
	switch (as.get_id()) {
	case 2: as=lst[2];
	//System.out.println("State changed to READY");
	break;
	case 5: as=lst[4];
	//System.out.println("State changed to S1");
	break;
	default: System.out.println("Operation not permitted in this state");
	break;
	}
	}
	public void BelowMinBalance()
	{
	as.BelowMinBalance();
	switch (as.get_id()) {
	case 3: as=lst[4];
	//System.out.println("State changed to OVERDRAWN");
	break;
	case 5: as=lst[4];
	//System.out.println("State changed to OVERDRAWN");
	break;
	default: // System.out.println("Operation not permitted in this state BelowMinBal");
	break;
	}
	}
	public void AboveMinBalance()
	{
	as.AboveMinBalance();
	switch (as.get_id()) {
	case 3: as=lst[2];
	//System.out.println("State changed to READY");
	break;
	case 5: as=lst[2];
	//System.out.println("State changed to READY");
	break;
	default: //System.out.println("Operation not permitted in this state AbowMinBal");
	break;
	}
	}
	public void exit()
	{
	as.exit();
	switch (as.get_id()) {
	case 2: as=lst[0];
	System.out.println("State changed to IDLE");
	break;
	case 4: as=lst[0];
	//System.out.println("State changed to IDLE");
	break;
	default: System.out.println("Operation not permitted in this state");
	break;
	}
	}
	public void balance()
	{
	as.balance();
	switch (as.get_id()) {
	case 2: as=lst[2];
	//System.out.println("State changed to READY");
	break;
	case 4: as=lst[4];
	//System.out.println("State changed to OVERDRAWN");
	break;
	default: System.out.println("Operation not permitted in this state");
	break;
	}
	}
	public void withdraw()
	{
	as.withdraw();
	switch (as.get_id()) {
	case 2: as=lst[3];
	//System.out.println("State changed to S1");
	break;
	default: System.out.println("Operation not permitted in this state");
	break;
	}
	}
	public void lock()
	{
	as.lock();
	switch (as.get_id()) {
	case 4: as=lst[5];
	//System.out.println("State changed to LOCKED");
	break;
	case 2: as=lst[5];
	//System.out.println("State changed to LOCKED");
	break;
	default: System.out.println("Operation not permitted in this state");
	break;
	}
	}
	public void unlock()
	{
	as.unlock();
	switch (as.get_id()) {
	case 5: as=lst[3];
	//System.out.println("State changed to S1");
	break;
	default: System.out.println("Operation not permitted in this state");
	break;
	}
	}
	public void set_count(int x)
	{
	count=x;
	}
	public int get_count()
	{
	return(count);
	}
	public void suspended() 
	{
		as.activate();
		switch (as.get_id()) {
		case 2: as=lst[6];
		//System.out.println("State changed to ready state");
		break;
		default: System.
		out.println("Operation not permitted in this state");
		break;}
	}
	public void incorrectlogin() 
	{
		as.incorrectlogin();
		switch (as.get_id()) {
		case 1: as=lst[1];
		//System.out.println("State changed to Idle");
		break;
		case 5: as=lst[2];
		//System.out.println("State changed to ready state");
		break;
		default: System.out.println("Operation not permitted in this state");
		break;
		}
	
	}
	public void activate() 
	{
		as.activate();
		switch (as.get_id()) {
		case 6: as=lst[2];
		//System.out.println("State changed to ready state");
		break;
		default: System.out.println("Operation not permitted in this state");
		break;}
	}
	
	public void NoFunds() 
	{
		// TODO Auto-generated method stub
		
	}


	
	}


