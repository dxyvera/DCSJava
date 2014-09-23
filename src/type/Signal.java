package type;

public enum Signal {
	UNKNOWN,HIGH,LOW;
	public Signal not()
	{
		switch(this)
		{
		case HIGH:
			return LOW;
		case LOW:
			return HIGH;
		default:
			return UNKNOWN;
		}
	}
	public static Signal and(Signal s1,Signal s2)
	{
		if(s1==HIGH&&s2==HIGH)return HIGH;
		else if(s1==LOW||s2==LOW)return LOW;
		else return UNKNOWN;
	}
	public static Signal or(Signal s1,Signal s2)
	{
		if(s1==LOW&&s2==LOW)return LOW;
		else if(s1==HIGH||s2==HIGH)return HIGH;
		else return UNKNOWN;
	}
	public static Signal xor(Signal s1,Signal s2)
	{
		if(s1==null||s2==null)return UNKNOWN;
		else if(s1==UNKNOWN||s2==UNKNOWN)return UNKNOWN;
		else if(s1==s2)return LOW;
		else return HIGH;
	}
	
	public static Signal resolve(Signal s1,Signal s2)
	{
		if(s1==UNKNOWN||s2==UNKNOWN)return UNKNOWN;
		else if(s1==null)return s2;
		else if(s2==null)return s1;
		else if(s1==s2)return s2;
		else return UNKNOWN;
	}
	
	public static Signal valueOf(Boolean val)
	{
		if(val==null)return null;
		else if(val)return HIGH;
		else return LOW;
	}
}
