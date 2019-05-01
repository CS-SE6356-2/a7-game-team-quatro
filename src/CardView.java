import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;

public class CardView extends ImageView implements EventHandler<MouseEvent>{
    private ArrayList<CardView> list;
    private UnoCard card;

    //image with mouse listener
    public CardView(String type, String color, ArrayList<CardView> list){
        super(CardImageLoader.LoadCardImage(new UnoCard(type, color)));
        card = new UnoCard(type, color);
        this.list = list;
        list.add(this);
        setEffect(null);
    }

    //image without mouse listener
    public CardView(String type, String color){
        super(CardImageLoader.LoadCardImage(new UnoCard(type, color)));
        card = new UnoCard(type, color);
        this.list = null;
        setEffect(null);
    }

    //static images
    public CardView(String alignment){
        super(CardImageLoader.LoadCardImage(alignment));;
        this.list = null;
        setEffect(null);
    }

    public UnoCard getCard(){
        return card;
    }
    
    public void setCard(UnoCard card){
        this.card = card;
    }

    public void changeBorder(){
        for(int i = 0; i<list.size(); i++){
            list.get(i).setEffect(null);
        }
        InnerShadow s = new InnerShadow(20, Color.BLACK);
        setEffect(s);
    }

    public UnoCard getSelectedCard(){
        CardView card = null;
        for(int i = 0; i<list.size(); i++){
            if(list.get(i).getEffect() != null){
                card = list.get(i);
            }
        }
        if(card == null) return null;
        return card.getCard();
    }

    @Override
    public void handle(MouseEvent event){
        if(event.getEventType() == MouseEvent.MOUSE_PRESSED){
            changeBorder();
        }
    }
}