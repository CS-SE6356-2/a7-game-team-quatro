//Shannen Barrameda sib170130 CS3354.HON Team 3
// Second Author: Jacob Villarreal
public class Card{
    /* SPECIAL CARDS */
    public final static int SKIP = 10;
    public final static int REVERSE = 11;
    public final static int DRAWTWO = 12;
    public final static int WILDCARD = 13;
    public final static int WILDDRAW4 = 14; 
    
    /* COLORS */
    public final static int GREEN = 1;
    public final static int RED = 2;
    public final static int YELLOW = 3;
    public final static int BLUE = 4;
    public final static int WILD = 5;
    
    int value, color;
    
    public Card(){
       value = WILDCARD;
       color = WILD;
    }
    
    public Card(int value, int color){
        if(value >= 0 && value <= 14){
            this.value = value;
        }
        else{
            throw new IllegalArgumentException("Invalid Card Value");
        }
        
        if(color != GREEN && color != RED && color != YELLOW && color != BLUE 
            && color != WILD){
            throw new IllegalArgumentException("Invalid Card Color");
        }
        else{
            this.color = color;
        }
    }
    
    public int getValue(){
        return value;
    }
    
    public int getColor(){
        return color;
    }
    public String valueAsString(){
        switch(value){
            case 10: return "Skip";
            case 11: return "Reverse";
            case 12: return "DrawTwo";
            case 13: return "WildCard";
	    case 14: return "WildDraw4";
            default: return Integer.toString(value);                
        }
    }
    
    public String colorAsString(){
        switch(color){
            case 1: return "Green";
            case 2: return "Red";
            case 3: return "Yellow";
            case 4: return "Blue";
	    case 5: return "Wild";
            default: return "Unknown";
        }
    }
    public String toString(){
        if(color == WILD)
            return valueAsString();
        else
            return colorAsString() + " " + valueAsString();
    }
}