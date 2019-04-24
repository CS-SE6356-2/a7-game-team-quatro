/* Original Author: Shannen Barrameda
 * Author: Jacob Villarreal */

/* Represents a single Uno playing card */
public class UnoCard
{
	/* Special Cards */
	public final static int skip = 10;
	public final static int reverse = 11;
	public final static int drawTwo = 12;
	public final static int wildCard = 13;
	public final static int wildDrawFour = 14;

	/* Colors */
	public final static int green = 1;
	public final static int red = 2;
	public final static int yellow = 3;
	public final static int blue = 4;
	public final static int wildColor = 5;

	/* Data members */
	private int type;
	private int color;

	/* Constructor */
	public UnoCard(int cType, int cColor)
	{
		/* Validate parameters. Card is made a wild card
		 * if parameters are invalid. */
		if (cType >= 0 && cType <= UnoCard.wildCard
		    && cColor >= UnoCard.green && cColor <= UnoCard.wildColor)
		{
			this.type = cType;
			this.type = cColor;	
		}
		else
		{
			this.type = UnoCard.wildCard;
			this.color = UnoCard.wildColor;
		}
	}

	/* Getters */
	public int getType()
	{
		return this.type;
	}

	public int getColor()
	{
		return this.color;
	}
}