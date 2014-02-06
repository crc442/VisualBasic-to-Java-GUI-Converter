
import java.util.*;

// This class is used to map keys with their corresponding integer value for creating shortcut
// It is used to create shortcut for menus
class keyTable
{


    public Hashtable key;
    
    // Initiates a hashtable object with the keys and their corresponding integer value.
    public keyTable()
    {
    	key = new Hashtable();
     	key.put("A", new Integer(java.awt.event.KeyEvent.VK_A));   
     	key.put("B", new Integer(java.awt.event.KeyEvent.VK_B));
     	key.put("C", new Integer(java.awt.event.KeyEvent.VK_C));
     	key.put("D", new Integer(java.awt.event.KeyEvent.VK_D));
     	key.put("E", new Integer(java.awt.event.KeyEvent.VK_E));
     	key.put("F", new Integer(java.awt.event.KeyEvent.VK_F));
     	key.put("G", new Integer(java.awt.event.KeyEvent.VK_G));
     	key.put("H", new Integer(java.awt.event.KeyEvent.VK_H));
     	key.put("I", new Integer(java.awt.event.KeyEvent.VK_I));
     	key.put("J", new Integer(java.awt.event.KeyEvent.VK_J));
     	key.put("K", new Integer(java.awt.event.KeyEvent.VK_K));
     	key.put("L", new Integer(java.awt.event.KeyEvent.VK_L));
     	key.put("M", new Integer(java.awt.event.KeyEvent.VK_M));
     	key.put("N", new Integer(java.awt.event.KeyEvent.VK_N));
     	key.put("O", new Integer(java.awt.event.KeyEvent.VK_O));
     	key.put("P", new Integer(java.awt.event.KeyEvent.VK_P));
     	key.put("Q", new Integer(java.awt.event.KeyEvent.VK_Q));
     	key.put("R", new Integer(java.awt.event.KeyEvent.VK_R));
     	key.put("S", new Integer(java.awt.event.KeyEvent.VK_S));
     	key.put("T", new Integer(java.awt.event.KeyEvent.VK_T));
     	key.put("U", new Integer(java.awt.event.KeyEvent.VK_U));
     	key.put("V", new Integer(java.awt.event.KeyEvent.VK_V));
     	key.put("W", new Integer(java.awt.event.KeyEvent.VK_W));
     	key.put("X", new Integer(java.awt.event.KeyEvent.VK_X));
     	key.put("Y", new Integer(java.awt.event.KeyEvent.VK_Y));
     	key.put("Z", new Integer(java.awt.event.KeyEvent.VK_Z));
     	key.put("F1", new Integer(java.awt.event.KeyEvent.VK_F1));
     	key.put("F2", new Integer(java.awt.event.KeyEvent.VK_F2));
     	key.put("F3", new Integer(java.awt.event.KeyEvent.VK_F3));
     	key.put("F4", new Integer(java.awt.event.KeyEvent.VK_F4));
     	key.put("F5", new Integer(java.awt.event.KeyEvent.VK_F5));
     	key.put("F6", new Integer(java.awt.event.KeyEvent.VK_F6));
     	key.put("F7", new Integer(java.awt.event.KeyEvent.VK_F7));
     	key.put("F8", new Integer(java.awt.event.KeyEvent.VK_F8));
     	key.put("F9", new Integer(java.awt.event.KeyEvent.VK_F9));
     	key.put("F10", new Integer(java.awt.event.KeyEvent.VK_F10));
     	key.put("F11", new Integer(java.awt.event.KeyEvent.VK_F11));
     	key.put("F12", new Integer(java.awt.event.KeyEvent.VK_F12));
     	key.put("INSERT", new Integer(java.awt.event.KeyEvent.VK_INSERT));
     	key.put("DEL", new Integer(java.awt.event.KeyEvent.VK_DELETE));
     	key.put("BKSP", new Integer(java.awt.event.KeyEvent.VK_BACK_SPACE));
     	
    }
   
}

