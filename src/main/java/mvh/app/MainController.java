package mvh.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mvh.enums.WeaponType;
import mvh.util.Reader;
import mvh.world.*;

import java.io.File;
import java.io.IOException;

public class MainController {

    //Store the data of editor
    private World world_1;

    private WeaponType weaponType;

    /**
     * this function checks if the type of input is castable to integer or not
     * @param input
     * @return a boolean
     */
    public Boolean check_symbol( String input){
        try{
            Integer.parseInt(input);
            return true;
        }catch (Exception e){
            return false;
        }
    }


    /**
     * Setup the window state
     */


    /**
     * we have defined the identities of our GUI layout
     */
    @FXML
    private TextField ArmorHero;

    @FXML
    private MenuItem Axe;

    @FXML
    private MenuItem Club;

    @FXML
    private TextField ColumnEn;

    @FXML
    private TextField ColumnsWorld;

    @FXML
    private Button CreateWorld;

    @FXML
    private Button DelEnt;

    @FXML
    private Label DetailsEnOutput;

    @FXML
    private TextField HealthHero;

    @FXML
    private TextField HealthMonster;

    @FXML
    private TextField RowEn;

    @FXML
    private TextField RowsWorld;

    @FXML
    private MenuItem Sword;

    @FXML
    private TextField SymbolHero;

    @FXML
    private TextField SymbolMonster;

    @FXML
    private Button ViewEntity;

    @FXML
    private Button ViewWorld;

    @FXML
    private Label ViewWorldOutput;

    @FXML
    private TextField WeaponHero;

    @FXML
    private SplitMenuButton WeaponMonster;

    @FXML
    private Button addHero;

    @FXML
    private Button addMonster;

    @FXML
    private Label leftIdentity;

    @FXML
    private Label rightIdentity;

    @FXML
    private TextField rowHeroAdd;

    @FXML
    private TextField rowMonsterAdd;

    @FXML
    private TextField columnHeroAdd;

    @FXML
    private TextField columnMonsterAdd;

    @FXML
    private Font x1;

    @FXML
    private Font x3;

    @FXML
    private Color x4;

    /**
     * i created an alert with my info in it
     * @param event
     */
    @FXML
    void aboutAction(ActionEvent event) {
        System.out.printf("aboutAction");
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("INFO");
        alert.setTitle("About");
        alert.setContentText("Author: Aminreza Tavakoli\nEmail: aminreza.tavakolikho@ucalgary.ca\nThis is a world editor for Monsters vs Heros");
        alert.show();
    }

    /**
     * -------------------------------------------------------------------------------------------------
     * we have defined on actions for every menu item and created a WeaponType object
     * to store the state of the WeaponType
     * @param event
     */
    @FXML
    void axeAction(ActionEvent event) {
        System.out.printf("axeAction");
        weaponType = WeaponType.AXE;

    }

    @FXML
    void swordAction(ActionEvent event) {
        System.out.printf("swordAction");
        weaponType = WeaponType.SWORD;
    }

    @FXML
    void clubAction(ActionEvent event) {
        System.out.printf("clubAction");
        weaponType = WeaponType.CLUB;
    }
    /**
     * -------------------------------------------------------------------------------------------------
     */

    /**
     * by using the World class we are creating a world getting rows and columns in the text fields
     * @param event
     */
    @FXML
    void creatWorld(ActionEvent event) {
        leftIdentity.setText("");
        rightIdentity.setText("");
        System.out.printf("creatWorld%n");
        try{
            int rows = Integer.parseInt(RowsWorld.getText());
            int columns =Integer.parseInt(ColumnsWorld.getText());
            world_1 = new World(rows, columns);
            rightIdentity.setText("world created successfully");
        }
        catch (IllegalArgumentException e) {
            leftIdentity.setText("can't parse integer from what you entered");
            e.getStackTrace();
        }
    }

    /**
     * by using the delete function in the World class and having rows and columns we delete the
     * desired entity
     * @param event
     */
    @FXML
    void deleteEnt(ActionEvent event) {
        leftIdentity.setText("");
        rightIdentity.setText("");
        System.out.printf("deleteEnt%n");
        if(world_1 != null) {
            try {
                int row = Integer.parseInt(RowEn.getText());
                int column = Integer.parseInt(ColumnEn.getText());
                world_1.deleteEntity(row, column);
                rightIdentity.setText("Entity deleted successfully");
            } catch (IllegalArgumentException e) {      // if typecasting doesn't occur the user has entered a wrong input
                leftIdentity.setText("can't parse integer from what you entered");
                e.getStackTrace();
            }
        }
        else {
            leftIdentity.setText("world is not set properly");
        }
    }

    /**
     * by using the FileChooser and showOpenDialog we get the file, and use the loadWorld function in
     * our Reade class to read the file and return the world object
     * @param event
     * @throws IOException
     */
    @FXML
    void loadAction(ActionEvent event) throws IOException {
        leftIdentity.setText("");
        rightIdentity.setText("");
        FileChooser fc_1 = new FileChooser();
        fc_1.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".txt", "*.txt"));

        try {
            File file_1 = fc_1.showOpenDialog(new Stage());
            world_1 = Reader.loadWorld(file_1);
            rightIdentity.setText("File loaded successfully");
        }catch (Exception e){
            leftIdentity.setText("there is something wrong with the file ");
            e.getStackTrace();
        }
    }

    /**
     * just quits the program using System.exit
     * @param event
     */
    @FXML
    void quitAction(ActionEvent event) {
        System.out.printf("quitAction%n");
        System.out.println("you ended the program");
        System.exit(0);
    }

    /**
     * by using the FileChooser and showSaveDialog we get the file, and use the Write function in
     * our Reader class to write to the file
     * @param event
     */
    @FXML
    void saveAction(ActionEvent event) {
        leftIdentity.setText("");
        rightIdentity.setText("");
        try {
            System.out.printf("saveAction%n");
            FileChooser fc_2 = new FileChooser();
            File file_2 = fc_2.showSaveDialog(new Stage());
            Reader.write(world_1, file_2);
            rightIdentity.setText("Saved to file successfully");
        }catch (Exception e){
            leftIdentity.setText("there is something wrong with the file ");
            e.getStackTrace();
        }

    }

    /**
     * if the world is not null we check for the entity of the entered row and column,
     * and then we get the information of the entity using internal functions of their classes
     * and output it to our label
     * @param event
     */
    @FXML
    void viewEn(ActionEvent event) {
        leftIdentity.setText("");
        rightIdentity.setText("");
        System.out.printf("viewEn%n");

        if(world_1 != null) {
            try {
                int row = Integer.parseInt(RowEn.getText());
                int column = Integer.parseInt(ColumnEn.getText());

                if(world_1.isHero(row, column)){
                    Hero hero = (Hero)world_1.getEntity(row, column);
                    char symbol = hero.getSymbol();
                    int health = hero.getHealth();
                    int weapon_damage = hero.weaponStrength();
                    int armor_health = hero.armorStrength();
                    String detail = String.format("Type: Hero\nSymbol: %s\nHealth: %d\nWeapon Damage: %d\nArmor Health: %d", symbol, health, weapon_damage, armor_health);
                    DetailsEnOutput.setText(detail);
                    rightIdentity.setText("Hero's detail showed successfully");
                }
                if(world_1.isMonster(row, column)){
                    Monster monster = (Monster)world_1.getEntity(row, column);
                    char symbol = monster.getSymbol();
                    int health = monster.getHealth();
                    int weapon_damage = monster.weaponStrength();
                    String weapon = String.valueOf(monster.getWeaponType());
                    String detail = String.format("Type: Monster\nSymbol: %s\nHealth: %s\nWeapon: %s\nWeapon Strength: %s", symbol, health, weapon, weapon_damage);
                    DetailsEnOutput.setText(detail);
                    rightIdentity.setText("Monster's detail showed successfully");
                }
                if(!(world_1.isMonster(row, column))&&!(world_1.isHero(row, column))){
                    DetailsEnOutput.setText("Neither monster nor hero\nNull");
                }

            } catch(ArrayIndexOutOfBoundsException e){
                leftIdentity.setText("the index is out of bound"); // if the row and column are out of bound
                e.getStackTrace();
            } catch (IllegalArgumentException e) {  // if typecasting doesn't occur the user has entered a wrong input
                leftIdentity.setText("can't parse integer from what you entered");
                e.getStackTrace();
            }
        }
        else {
            leftIdentity.setText("world is not set properly");
        }
    }

    /**
     * we use the worldString function in the World class and put in a string and output it to our label
     * @param event
     */
    @FXML
    void viewWorld(ActionEvent event) {
        leftIdentity.setText("");
        rightIdentity.setText("");
        System.out.printf("viewWorld%n");
        if(world_1 != null){
            String world_out = world_1.worldString();
            ViewWorldOutput.setText(world_out);
            rightIdentity.setText("World showed successfully");
        }
    }

    /**
     * we check if we have all the information needed to create a hero and then create our hero
     * and add it to our world using addEntity function
     * @param event
     */
    @FXML
    void addHeroAction(ActionEvent event) {
        leftIdentity.setText("");
        rightIdentity.setText("");
        System.out.printf("addHeroAction%n");
        if(HealthHero.getText()==""){
            leftIdentity.setText("Enter Health for hero");
        }
        else if(SymbolHero.getText()==""){
            leftIdentity.setText("Enter Symbol for hero");
        }
        else if( check_symbol(SymbolHero.getText())){
            leftIdentity.setText("the Symbol for hero should be a character");
        }
        else if(rowHeroAdd.getText()==""){
            leftIdentity.setText("Enter Row for hero");
        }
        else if(columnHeroAdd.getText()==""){
            leftIdentity.setText("Enter Column for hero");
        }
        else if(world_1 != null) {
            try {
                int health_hero = Integer.parseInt(HealthHero.getText());
                char symbol_hero = SymbolHero.getText().charAt(0);
                int weapon_hero = Integer.parseInt(WeaponHero.getText());
                int armor_hero = Integer.parseInt(ArmorHero.getText());
                Hero hero_add = new Hero(health_hero, symbol_hero, weapon_hero, armor_hero);
                int row_hero_add = Integer.parseInt(rowHeroAdd.getText());
                int column_hero_add = Integer.parseInt(columnHeroAdd.getText());
                world_1.addEntity(row_hero_add, column_hero_add, hero_add);
                rightIdentity.setText("Hero added successfully");
            } catch (IllegalArgumentException e) {  // if typecasting doesn't occur the user has entered a wrong input
                leftIdentity.setText("can't parse integer from what you entered");
                e.getStackTrace();
            } catch (ArrayIndexOutOfBoundsException e) {   // if the row and column are out of bound
                leftIdentity.setText("the index is out of bound");
                e.getStackTrace();
            }
        }
        else{
            leftIdentity.setText("world is not set properly");
        }
    }

    /**
     * we check if we have all the information needed to create a monster and then create our monster
     * and add it to our world using addEntity function
     * @param event
     */
    @FXML
    void addMonsterAction(ActionEvent event) {
        leftIdentity.setText("");
        rightIdentity.setText("");
        System.out.printf("addMonsterAction%n");
        if(HealthMonster.getText()==""){
            leftIdentity.setText("Enter Health for monster");
        }
        else if(SymbolMonster.getText()==""){
            leftIdentity.setText("Enter Symbol for monster");
        }
        else if( check_symbol(SymbolMonster.getText())){
            leftIdentity.setText("the Symbol for monster should be a character");
        }
        else if(weaponType == null){
            leftIdentity.setText("Choose Weapon Type");
        }
        else if(rowMonsterAdd.getText()==""){
            leftIdentity.setText("Enter Row for monster");
        }
        else if(columnMonsterAdd.getText()==""){
            leftIdentity.setText("Enter Column for monster");
        }
        else if(world_1 != null && weaponType != null) {
            try {
                int health_monster = Integer.parseInt(HealthMonster.getText());
                char symbol_monster = SymbolMonster.getText().charAt(0);
                Monster monster_add = new Monster(health_monster, symbol_monster, weaponType);
                int row_monster_add = Integer.parseInt(rowMonsterAdd.getText());
                int column_monster_add = Integer.parseInt(columnMonsterAdd.getText());
                world_1.addEntity(row_monster_add, column_monster_add, monster_add);
                rightIdentity.setText("Monster added successfully");
            } catch (ArrayIndexOutOfBoundsException e) {    // if the row and column are out of bound
                leftIdentity.setText("the index is out of bound");
                e.getStackTrace();
            }catch (IllegalArgumentException e) {   // if typecasting doesn't occur the user has entered a wrong input
                leftIdentity.setText("can't parse integer from what you entered");
                e.getStackTrace();
            }
        }
        else{
            leftIdentity.setText("world is not set properly");
        }
    }
}
