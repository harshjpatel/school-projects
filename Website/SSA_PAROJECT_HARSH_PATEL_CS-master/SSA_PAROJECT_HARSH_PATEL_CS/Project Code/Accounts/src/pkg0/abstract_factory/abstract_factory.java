//abstractfactory class

package pkg0;

public abstract class abstract_factory 
{
	//This is the class that is responsible for creating the corresponding objects for each ATMs
	abstract DataStore get_datastore();
	/*
	Functions listed below are abstract. The implementation of these functions are given in the corresponding concrete implementations.
	*/
	abstract storepin get_storepin();
	abstract storebalance get_storebalance();
	abstract promptforpin get_promptpin();
	abstract display_menu get_displaymenu();
	abstract incorrect_pin_msg get_inpinmsg();
	abstract tooMany_attempts_msg get_attempts();
	abstract makedeposit get_makedeposit();
	abstract makewithdraw get_makewithdraw();
	abstract penalty get_penalty();
	abstract displaybalance get_displaybalance();
	abstract void initialize(abstract_factory con);
	
}
