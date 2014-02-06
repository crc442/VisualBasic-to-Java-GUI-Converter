
import java.util.*;
class HashTable
{
    public Hashtable hs;
    public HashTable()
    {
     hs = new Hashtable();
     hs.put("Begin", new Integer(1));   
    hs.put("End", new Integer(2));   
    hs.put("VB.Form",new Integer(3));
    hs.put("Caption", new Integer(4));
    hs.put("ClientHeight", new Integer(5));
    hs.put("ClientLeft", new Integer(6));    
    hs.put("ClientTop", new Integer(7));
    hs.put("ClientWidth", new Integer(8));    
    hs.put("LinkTopic", new Integer(9));
    hs.put("VB.CheckBox", new Integer(10));    
    hs.put("Height", new Integer(11));
    hs.put("Left", new Integer(12));        
    hs.put("Top", new Integer(13));    
    hs.put("Width", new Integer(14)); 
    hs.put("VB.PictureBox", new Integer(15));
    hs.put("TabIndex", new Integer(16));
    hs.put("VB.ListBox", new Integer(20));
    hs.put("VB.ComboBox", new Integer(21));
    hs.put("Index", new Integer(22));
    hs.put("VB.OptionButton", new Integer(23));
    hs.put("VB.CommandButton", new Integer(24));
    hs.put("VB.TextBox", new Integer(25));
    hs.put("VB.Image", new Integer(26));
    hs.put("VB.Line", new Integer(27));
    hs.put("X1", new Integer(28));
    hs.put("Y1", new Integer(29));
    hs.put("X2", new Integer(30));
    hs.put("Y2", new Integer(31));
    hs.put("VB.Shape", new Integer(32));
    hs.put("VB.Label", new Integer(33));
    hs.put("VB.VScrollBar", new Integer(34));
    hs.put("VB.HScrollBar", new Integer(35));
    hs.put("BeginProperty", new Integer(37));
    hs.put("EndProperty", new Integer(38));
    hs.put("Font", new Integer(39));
    hs.put("Name", new Integer(40));
    hs.put("Size", new Integer(41));
    hs.put("Charset", new Integer(42));
    hs.put("Underline", new Integer(43));
    hs.put("Weight", new Integer(44));
    hs.put("Italic", new Integer(45));
    hs.put("Strikethrough", new Integer(46));
    hs.put("Appearance", new Integer(47));
    hs.put("BackColor", new Integer(48));
    hs.put("Default", new Integer(49));
    hs.put("Enabled", new Integer(50));
    hs.put("MaskColor", new Integer(51));
    hs.put("MousePointer", new Integer(52));
    hs.put("Style", new Integer(53));
    hs.put("TabStop", new Integer(54));
    hs.put("ToolTipText", new Integer(55));
    hs.put("UseMaskColor", new Integer(56));
    hs.put("Visible", new Integer(57));
    hs.put("List", new Integer(58));
    hs.put("ForeColor", new Integer(59));
    hs.put("class label", new Integer(60));
    hs.put("class checkbox", new Integer(61));
    hs.put("class cmdbutton", new Integer(62));
   	hs.put("class javax.swing.JTextField", new Integer(65));
    hs.put("class textarea", new Integer(63));
    hs.put("class combobox", new Integer(64));
    hs.put("BorderStyle", new Integer(65));
    hs.put("Locked", new Integer(66));
    hs.put("DragMode", new Integer(67));
    hs.put("class javax.swing.JRadioButton", new Integer(68));
    hs.put("VB.Frame",new Integer(69));
    hs.put("class frame",new Integer(70));
    hs.put("class java.lang.String",new Integer(71));
    hs.put("FillColor",new Integer(72));
    hs.put("FillStyle",new Integer(73));
    hs.put("Shape",new Integer(74));
    hs.put("class shape",new Integer(75));
    hs.put("class javax.swing.JMenu", new Integer(76));
    hs.put("class list", new Integer(77));
    hs.put("class scrollbar", new Integer(78));
    hs.put("class java.util.Vector", new Integer(79));
    hs.put("class picturebox", new Integer(80));
    hs.put("Picture", new Integer(81));
    hs.put("ItemData",new Integer(82));
    hs.put("class javax.swing.JMenuBar",new Integer(83));
    hs.put("VB.Menu",new Integer(85));
    hs.put("TabDlg.SSTab",new Integer(84));//78
    hs.put("Tabs",new Integer(86));//79
    hs.put("TabHeight",new Integer(87));//80
    hs.put("TabsPerRow",new Integer(88));//81
    hs.put("class tabbedpane",new Integer(89));//82    
    hs.put("Alignment",new Integer(90));//Rqd Checkbox   
    hs.put("Value",new Integer(91));//Rqd Checkbox and ScrollBar 
    hs.put("BackStyle",new Integer(92));//Rqd Shape
    hs.put("BorderColor",new Integer(93));//Rqd Shape   
    hs.put("class line",new Integer(95));//Rqd Shape   
    hs.put("TabOrientation",new Integer(96));
    hs.put("UseMnemonic",new Integer(97));
    hs.put("WindowState",new Integer(98));
    hs.put("Tab",new Integer(99));   
    hs.put("DisabledPicture",new Integer(100));
    hs.put("DownPicture",new Integer(101));
	hs.put("Text", new Integer(102));//96
    hs.put("MultiLine", new Integer(103));//97
    hs.put("class imagebox",new Integer(104));//98
    hs.put("class radiobutton",new Integer(105));//99
    
    hs.put("LargeChange",new Integer(106));//100 Scrollbar value change when clicked in middle
    hs.put("SmallChange",new Integer(107));//101 Scrollbar value change when clicked at arrow
    hs.put("Max",new Integer(108));//102 Scrollbar value change when clicked in middle
    hs.put("Min",new Integer(109));//103 Scrollbar value change when clicked at arrow
    hs.put("MultiSelect",new Integer(110));//104 List selection type
    
   }
}

