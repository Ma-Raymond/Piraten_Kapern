package pk;


import java.util.*;

// Abstract Class as a drawer
abstract class Drawer{
    public abstract Object draw();
}
interface Source{
    boolean isEmpty();
    Object draw();
}
public class CardDrawer extends Drawer{
    public static CardPile currentPile = new CardPile();

    public Cards draw(){
        return currentPile.draw();
    }
}

