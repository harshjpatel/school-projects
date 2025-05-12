package pkg0;

public class output_processor {
	public static DataStore ds;
	public static storepin sp;
	public static storebalance sb;
	public static promptforpin pp;
	public static display_menu dm;
	public static incorrect_pin_msg ipm;
	public static tooMany_attempts_msg tam;
	public static makedeposit md;
	public static makewithdraw mw;
	public static penalty p;
	public static displaybalance db;
	void initialize(abstract_factory ob)
	{
	// ob is the object of the concrete factory and the intialize function uses it to create the objects of the datastore
	// and other functions in the output processor
	ds=ob.get_datastore();
	sp=ob.get_storepin();
	sb=ob.get_storebalance();
	pp=ob.get_promptpin();
	dm=ob.get_displaymenu();
	ipm=ob.get_inpinmsg();
	tam=ob.get_attempts();
	md=ob.get_makedeposit();
	mw=ob.get_makewithdraw();
	p=ob.get_penalty();
	db=ob.get_displaybalance();
	}
	DataStore get_datastrote()
	{// This functions returns the datastore object to the ATM as it needs to access the temporary variables.
	return(ds);
	}
	//calls the corresponding functions
	void store_pin()
	{
	sp.store_pin(ds);
	}
	void store_balance()
	{
	sb.store_balance(ds);
	}
	void prompt_for_PIN()
	{
		pp.promptpin();
	}
	void display_menu()
	{
	dm.displaymenu();
	}
	void incorrect_pin_msg()
	{
	ipm.inpinmsg();
	}
	void too_many_attempts_msg()
	{
	tam.tooManyAttempts();
	}
	void MakeDeposit()
	{
	md.make_deposit(ds);
	}
	void MakeWithdraw()
	{
	mw.make_withdraw(ds);
	}
	void penalty()
	{
	p.in_penalty(ds);
	}
	
	void Display_Balance()
	{
	db.display_balance(ds);
	
	}
	void store_userid()
	{
		ds.store_userid();
	}
	
}
