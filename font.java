// The font class is used by all the classes like checkbox,combobox,label etc;
// for storing and retreiving the font information for the particular classes object

class font
{
	// This variable is used to store the font name.
	String name;
	
	// This variable is used to store the font size in pixels.
	double size;
	
	// This variable is used to store the font style information such as Italics,Bold.
	int style;
	

//constructor
	public font()
	{
		name="MS Sans Serif";
		name="\""+name+"\"";
		size=8*1.33;//Multiplication by 1.33 is necessary for converting pts to pixels.
		style=java.awt.Font.PLAIN;
	}
	
	// This method reads the font information for the control object for which it is being called.
	protected void setFontProperty(java.util.StringTokenizer s)
	{
		String curToken=new String("Font");
		HashTable h=new HashTable();
		Integer val1;
		while(curToken.compareTo("EndProperty")!=0)
		{
				curToken=s.nextToken();
				val1=(Integer)h.hs.get(curToken);
				if(val1==null)
				continue;
				switch(val1.intValue())
				{
					case 40:
							curToken=getCaption(s);
							name=curToken;
							break;
					case 41:
							curToken=s.nextToken();	
							size=Math.ceil(Double.parseDouble(curToken)*1.33);
							break;	
					case  44:
							curToken=s.nextToken();
							style=java.awt.Font.PLAIN;
							if(Integer.parseInt(curToken)>450)
							style=style|java.awt.Font.BOLD;	
							break;
					case 45:
							curToken=s.nextToken();
							if(Integer.parseInt(curToken)==-1)
							style=style|java.awt.Font.ITALIC;
							break;
					}
			}	
	}


	// This method returns the Font Code for each Control object such as combobox,cmdbutton etc;
	protected String getFontCode()
	{
		return("new Font("+name+","+style+","+Math.round(size)+")");		
	}
	
  	// This method replaces every occurence of single backward slash by double backward slash.
	private StringBuffer replaceSlashByDoubleSlash(StringBuffer text)
	{
		int slpos=-2;
		while(true)
		{
				slpos=text.indexOf("\\",slpos+2);
				if(slpos==-1)
				break;
				text.insert(slpos+1,'\\');
		}	
		return(text);
	}	



	private String getCaption(java.util.StringTokenizer s)
	{
		StringBuffer token=new StringBuffer(s.nextToken());
		int oldpos,newpos;
		oldpos=0;newpos=0;
		char charNxtToQuotes;
		int quotesCounter=0;
		if(new String(token).startsWith("\"")==false)
		return(new String(token));
		else
		{
			quotesCounter++;
			token=replaceSlashByDoubleSlash(token);
			StringBuffer newToken;
			while(true)
			{
				newpos=token.indexOf("\"",newpos+1);//Find occurence of " after the pair of Quotes that has been found
				if(newpos==-1)
				{
					if(quotesCounter%2==0)
					break;
					token=token.append(' ');
					newToken=new StringBuffer(s.nextToken());
					newToken=replaceSlashByDoubleSlash(newToken);
					token=token.append(newToken);
					newpos=oldpos+1;//oldpos is reqd bcoz newpos gets overwritten when Quotes is not present
				}
				else
				{
					oldpos=newpos;
					quotesCounter++;
					if(newpos+1!=token.length())//it implies that the ending quotes has been found
					charNxtToQuotes=token.charAt(newpos+1);
					else
					{
						break;
					}
					if(charNxtToQuotes==' ')
					{
						break;
					}
					else if(charNxtToQuotes=='\"')
					{
						token.setCharAt(newpos,'\\');
						newpos+=1;
					}
				}
			}
		}
		return(new String(token));
	}
}
	