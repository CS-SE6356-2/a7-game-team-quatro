
/* Original Author: Shannen Barrameda
 * Author: Jacob Villarreal */

import java.util.*;

/* Represents a single Uno playing card */
public class UnoCard
{
	/* Data members */
	private String type;
	private String color;
	
	/* Special Cards */
	public static final ArrayList<String> cardTypes
		= new ArrayList<>(
				Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
							  "skip", "reverse", "draw2", "wild", "wild4"));
	
	public static final ArrayList<String> cardColors
		= new ArrayList<>(
				Arrays.asList("green", "red", "yellow", "blue", "wild"));
	
	/* Constructors */
	public UnoCard(String cType, String cColor)
	{
		if (UnoCard.cardTypes.contains(cType)
			&& UnoCard.cardColors.contains(cColor))
		{
			this.type = cType;
			this.color = cColor;
		}
		else
		{
			this.type = "ERROR";
			this.color = "ERROR";
		}
	}
	
	@Override
	public boolean equals(Object object)
	{
		if (this == object)
		{
			return true;
		}
		if (object == null || this.getClass() != object.getClass())
		{
			return false;
		}
		UnoCard card = (UnoCard) object;
		return card.type == this.type
			&& card.color == this.color;
	}
			
	/* Compares with another card to see if they match. Matching means
	 * that one card can be put on top of another in the discard pile. */
	public boolean matches(UnoCard otherCard)
	{
		/* Anything matches with a wild card.
		 * Otherwise, cards match if color or type are the same. */
		return this.getColor() == "wild" 
		    || otherCard.getColor() == "wild" 
		    || this.getColor() == otherCard.getColor()
		    || this.getType() == otherCard.getType();
	}

	/* Getters */
	public String getType()
	{
		return this.type;
	}

	public String getColor()
	{
		return this.color;
	}
}