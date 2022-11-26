import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SettingsMenu here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SettingsMenu extends World
{
    //declaring variables for the background
    private GreenfootImage background;
    public static final Color titleColor = MainMenu.titleColor;
    public static final Font titleFont = MainMenu.titleFont;
    private String title = "Settings";
    public static final Color settingsColor = MainMenu.scoreColor;
    public static final Font settingsFont = MainMenu.scoreFont;
    private String moveUp = "Move Up:";
    private String moveDown = "Move Down:";
    private String moveLeft = "Move Left:";
    private String moveRight = "Move Right:";
    private String boost = "Boost:";
    private String shootLaser = "Shoot Laser:";
    private String launchTorp = "Launch Torpedo: ";
    private String upgdBoost = "Upgrade Boost:";
    private String upgdOverheat = "Upgrade Overheat:";
    private String upgdTorp = "Upgrade Torpedo:";
    private String mouseFixed = "You Cannot Change \"Shoot Laser\" Keybind";
    
    //declaring variable to store user information
    private UserInfo user;
    
    //declaring actors
    private Button backButton, upButton, downButton, leftButton, rightButton, boostButton, laserButton, torpButton, upgd1Button, upgd2Button, upgd3Button;
    
    //declaring instance variables
    private GreenfootSound clickSound = new GreenfootSound("Menu Click.wav");
    public static String upStr, downStr, leftStr, rightStr, boostStr, torpStr, upgd1Str, upgd2Str, upgd3Str;
    private boolean keyChange = false;
    
    public SettingsMenu()
    {    
        //Create a new world with the specified sizes and set its background
        super(GameWorld.WORLD_WIDTH, GameWorld.WORLD_HEIGHT, 1);
        background = new GreenfootImage(GameWorld.drawBackground(GameWorld.WORLD_WIDTH, GameWorld.WORLD_HEIGHT, Color.WHITE));
        background.setColor(titleColor);
        background.setFont(titleFont);
        background.drawString(title, (getWidth() - (int)(title.length() * titleFont.getSize() * 0.58)) / 2, getHeight() / 7);
        background.setColor(settingsColor);
        background.setFont(settingsFont);
        background.drawString(moveUp, getWidth() / 10, getHeight() / 4);
        background.drawString(moveDown, getWidth() / 10, getHeight() * 11 / 28);
        background.drawString(moveLeft, getWidth() / 10, getHeight() * 15 / 28);
        background.drawString(moveRight, getWidth() / 10, getHeight() * 19 / 28);
        background.drawString(boost, getWidth() / 10, getHeight() * 23 / 28);
        background.drawString(shootLaser, getWidth() / 2, getHeight() / 4);
        background.drawString(launchTorp, getWidth() / 2, getHeight() * 11 / 28);
        background.drawString(upgdBoost, getWidth() / 2, getHeight() * 15 / 28);
        background.drawString(upgdOverheat, getWidth() / 2, getHeight() * 19 / 28);
        background.drawString(upgdTorp, getWidth() / 2, getHeight() * 23 / 28);
        background.drawString(mouseFixed, (getWidth() - (int)(mouseFixed.length() * settingsFont.getSize() * 0.58)) / 2, getHeight() * 27 / 28);
        setBackground(background);
        
        if(UserInfo.isStorageAvailable()){ //check if the user has information
            user = UserInfo.getMyInfo();
            setKeybinds();
        }
        
        //add buttons
        backButton = new Button("Back");
        addObject(backButton, getWidth() / 10, getHeight() * 13 / 14);
        
        upButton = new Button(upStr);
        addObject(upButton, getWidth() * 11 / 30, getHeight() / 4);
        downButton = new Button(downStr);
        addObject(downButton, getWidth() * 11 / 30, getHeight() * 11 / 28);
        leftButton = new Button(leftStr);
        addObject(leftButton, getWidth() * 11 / 30, getHeight() * 15 / 28);
        rightButton = new Button(rightStr);
        addObject(rightButton, getWidth() * 11 / 30, getHeight() * 19 / 28);
        boostButton = new Button(boostStr);
        addObject(boostButton, getWidth() * 11 / 30, getHeight() * 23 / 28);
        laserButton = new Button("mouse");
        addObject(laserButton, getWidth() * 13 / 15, getHeight() / 4);
        torpButton = new Button(torpStr);
        addObject(torpButton, getWidth() * 13 / 15, getHeight() * 11 / 28);
        upgd1Button = new Button(upgd1Str);
        addObject(upgd1Button, getWidth() * 13 / 15, getHeight() * 15 / 28);
        upgd2Button = new Button(upgd2Str);
        addObject(upgd2Button, getWidth() * 13 / 15, getHeight() * 19 / 28);
        upgd3Button = new Button(upgd3Str);
        addObject(upgd3Button, getWidth() * 13 / 15, getHeight() * 23 / 28);
    }
    
    public void started(){
        MainMenu.playMusic();
    }
    
    public void act(){
        //changes the user's keybinds if they click on the keybind
        if(Greenfoot.mouseClicked(upButton) && !keyChange){
            changeKey(upButton, upStr, 0);
        }
        else if(Greenfoot.mouseClicked(downButton) && !keyChange){
            changeKey(downButton, downStr, 1);
        }
        else if(Greenfoot.mouseClicked(leftButton) && !keyChange){
            changeKey(leftButton, leftStr, 2);
        }
        else if(Greenfoot.mouseClicked(rightButton) && !keyChange){
            changeKey(rightButton, rightStr, 3);
        }
        else if(Greenfoot.mouseClicked(boostButton) && !keyChange){
            changeKey(boostButton, boostStr, 4);
        }
        else if(Greenfoot.mouseClicked(torpButton) && !keyChange){
            changeKey(torpButton, torpStr, 5);
        }
        else if(Greenfoot.mouseClicked(upgd1Button) && !keyChange){
            changeKey(upgd1Button, upgd1Str, 6);
        }
        else if(Greenfoot.mouseClicked(upgd2Button) && !keyChange){
            changeKey(upgd2Button, upgd2Str, 7);
        }
        else if(Greenfoot.mouseClicked(upgd3Button) && !keyChange){
            changeKey(upgd3Button, upgd3Str, 8);
        }
        if(Greenfoot.mouseClicked(backButton)){ //takes the user back to the main menu
            clickSound.play();
            Greenfoot.setWorld(new MainMenu());
        }
    }
    
    public void stopped(){
        MainMenu.pauseMusic();
    }
    
    //changes the user's keybind and saves it
    private void changeKey(Button button, String buttonStr, int userIndex){
        clickSound.play();
        keyChange = true;
        String key = Greenfoot.getKey();
        while(key == null){
            key = Greenfoot.getKey();
        }
        buttonStr = key;
        button.update(buttonStr); //update the keybind shown
        int keyNum = getKeyNum(buttonStr);
        user.setInt(userIndex, keyNum);
        user.store(); //save their new keybind
        keyChange = false;
    }
    
    //method to set the saved keybinds for the user
    private void setKeybinds(){
        if(user != null){
            if(user.getInt(0) != 0){
                upStr = getKeybind(user.getInt(0));
            }
            else{
                upStr = "w";
            }
            if(user.getInt(1) != 0){
                downStr = getKeybind(user.getInt(1));
            }
            else{
                downStr = "s";
            }
            if(user.getInt(2) != 0){
                leftStr = getKeybind(user.getInt(2));
            }
            else{
                leftStr = "a";
            }
            if(user.getInt(3) != 0){
                rightStr = getKeybind(user.getInt(3));
            }
            else{
                rightStr = "d";
            }
            if(user.getInt(4) != 0){
                boostStr = getKeybind(user.getInt(4));
            }
            else{
                boostStr = "shift";
            }
            if(user.getInt(5) != 0){
                torpStr = getKeybind(user.getInt(5));
            }
            else{
                torpStr = "space";
            }
            if(user.getInt(6) != 0){
                upgd1Str = getKeybind(user.getInt(6));
            }
            else{
                upgd1Str = "1";
            }
            if(user.getInt(7) != 0){
                upgd2Str = getKeybind(user.getInt(7));
            }
            else{
                upgd2Str = "2";
            }
            if(user.getInt(8) != 0){
                upgd3Str = getKeybind(user.getInt(8));
            }
            else{
                upgd3Str = "3";
            }
        }
    }
    
    //method to get the number representing the key, stores all values from the 64K Keyboard except "function", "caps lock", and "\"
    private int getKeyNum(String key){
        if(key.equals("1")){
            return 1;
        }
        else if(key.equals("2")){
            return 2;
        }
        else if(key.equals("3")){
            return 3;
        }
        else if(key.equals("4")){
            return 4;
        }
        else if(key.equals("5")){
            return 5;
        }
        else if(key.equals("6")){
            return 6;
        }
        else if(key.equals("7")){
            return 7;
        }
        else if(key.equals("8")){
            return 8;
        }
        else if(key.equals("9")){
            return 9;
        }
        else if(key.equals("0")){
            return 10;
        }
        else if(key.equals("a")){
            return 11;
        }
        else if(key.equals("b")){
            return 12;
        }
        else if(key.equals("c")){
            return 13;
        }
        else if(key.equals("d")){
            return 14;
        }
        else if(key.equals("e")){
            return 15;
        }
        else if(key.equals("f")){
            return 16;
        }
        else if(key.equals("g")){
            return 17;
        }
        else if(key.equals("h")){
            return 18;
        }
        else if(key.equals("i")){
            return 19;
        }
        else if(key.equals("j")){
            return 20;
        }
        else if(key.equals("k")){
            return 21;
        }
        else if(key.equals("l")){
            return 22;
        }
        else if(key.equals("m")){
            return 23;
        }
        else if(key.equals("n")){
            return 24;
        }
        else if(key.equals("o")){
            return 25;
        }
        else if(key.equals("p")){
            return 26;
        }
        else if(key.equals("q")){
            return 27;
        }
        else if(key.equals("r")){
            return 28;
        }
        else if(key.equals("s")){
            return 29;
        }
        else if(key.equals("t")){
            return 30;
        }
        else if(key.equals("u")){
            return 31;
        }
        else if(key.equals("v")){
            return 32;
        }
        else if(key.equals("w")){
            return 33;
        }
        else if(key.equals("x")){
            return 34;
        }
        else if(key.equals("y")){
            return 35;
        }
        else if(key.equals("z")){
            return 36;
        }
        else if(key.equals("`")){
            return 37;
        }
        else if(key.equals("-")){
            return 38;
        }
        else if(key.equals("=")){
            return 39;
        }
        else if(key.equals("[")){
            return 40;
        }
        else if(key.equals("]")){
            return 41;
        }
        else if(key.equals(";")){
            return 42;
        }
        else if(key.equals("'")){
            return 43;
        }
        else if(key.equals(",")){
            return 44;
        }
        else if(key.equals(".")){
            return 45;
        }
        else if(key.equals("/")){
            return 46;
        }
        else if(key.equals("up")){
            return 47;
        }
        else if(key.equals("down")){
            return 48;
        }
        else if(key.equals("left")){
            return 49;
        }
        else if(key.equals("right")){
            return 50;
        }
        else if(key.equals("escape")){
            return 51;
        }
        else if(key.equals("delete")){
            return 52;
        }
        else if(key.equals("backspace")){
            return 53;
        }
        else if(key.equals("tab")){
            return 54;
        }
        else if(key.equals("enter")){
            return 55;
        }
        else if(key.equals("shift")){
            return 56;
        }
        else if(key.equals("control")){
            return 57;
        }
        else if(key.equals("windows")){
            return 58;
        }
        else if(key.equals("alt")){
            return 59;
        }
        else if(key.equals("space")){
            return 60;
        }
        else if(key.equals("alt graph")){
            return 61;
        }
        else if(key.equals("context menu")){
            return 62;
        }
        return 0;
    }
    
    //method to get the key that the number represents
    public static String getKeybind(int keyNum){
        if(keyNum == 1){
            return "1";
        }
        else if(keyNum == 2){
            return "2";
        }
        else if(keyNum == 3){
            return "3";
        }
        else if(keyNum == 4){
            return "4";
        }
        else if(keyNum == 5){
            return "5";
        }
        else if(keyNum == 6){
            return "6";
        }
        else if(keyNum == 7){
            return "7";
        }
        else if(keyNum == 8){
            return "8";
        }
        else if(keyNum == 9){
            return "9";
        }
        else if(keyNum == 10){
            return "0";
        }
        else if(keyNum == 11){
            return "a";
        }
        else if(keyNum == 12){
            return "b";
        }
        else if(keyNum == 13){
            return "c";
        }
        else if(keyNum == 14){
            return "d";
        }
        else if(keyNum == 15){
            return "e";
        }
        else if(keyNum == 16){
            return "f";
        }
        else if(keyNum == 17){
            return "g";
        }
        else if(keyNum == 18){
            return "h";
        }
        else if(keyNum == 19){
            return "i";
        }
        else if(keyNum == 20){
            return "j";
        }
        else if(keyNum == 21){
            return "k";
        }
        else if(keyNum == 22){
            return "l";
        }
        else if(keyNum == 23){
            return "m";
        }
        else if(keyNum == 24){
            return "n";
        }
        else if(keyNum == 25){
            return "o";
        }
        else if(keyNum == 26){
            return "p";
        }
        else if(keyNum == 27){
            return "q";
        }
        else if(keyNum == 28){
            return "r";
        }
        else if(keyNum == 29){
            return "s";
        }
        else if(keyNum == 30){
            return "t";
        }
        else if(keyNum == 31){
            return "u";
        }
        else if(keyNum == 32){
            return "v";
        }
        else if(keyNum == 33){
            return "w";
        }
        else if(keyNum == 34){
            return "x";
        }
        else if(keyNum == 35){
            return "y";
        }
        else if(keyNum == 36){
            return "z";
        }
        else if(keyNum == 37){
            return "`";
        }
        else if(keyNum == 38){
            return "-";
        }
        else if(keyNum == 39){
            return "=";
        }
        else if(keyNum == 40){
            return "[";
        }
        else if(keyNum == 41){
            return "]";
        }
        else if(keyNum == 42){
            return ";";
        }
        else if(keyNum == 43){
            return "'";
        }
        else if(keyNum == 44){
            return ",";
        }
        else if(keyNum == 45){
            return ".";
        }
        else if(keyNum == 46){
            return "/";
        }
        else if(keyNum == 47){
            return "up";
        }
        else if(keyNum == 48){
            return "down";
        }
        else if(keyNum == 49){
            return "left";
        }
        else if(keyNum == 50){
            return "right";
        }
        else if(keyNum == 51){
            return "escape";
        }
        else if(keyNum == 52){
            return "delete";
        }
        else if(keyNum == 53){
            return "backspace";
        }
        else if(keyNum == 54){
            return "tab";
        }
        else if(keyNum == 55){
            return "enter";
        }
        else if(keyNum == 56){
            return "shift";
        }
        else if(keyNum == 57){
            return "control";
        }
        else if(keyNum == 58){
            return "windows";
        }
        else if(keyNum == 59){
            return "alt";
        }
        else if(keyNum == 60){
            return "space";
        }
        else if(keyNum == 61){
            return "alt graph";
        }
        else if(keyNum == 62){
            return "context menu";
        }
        return null;
    }
}
