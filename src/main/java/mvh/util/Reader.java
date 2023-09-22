/**
 * Name: Aminreza Tavakol
 * TUT: 04
 * Date: 04/02/22
 */
package mvh.util;

import mvh.enums.WeaponType;
import mvh.world.Hero;
import mvh.world.Monster;
import mvh.world.World;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class to assist reading in world file
 *
 * @author Jonathan Hudson
 * @version 1.0
 */
public final class Reader {
    /**
     * we get a file and the loop through it for each row and column and using the information build hero or monster
     * objects add them to a new World object and return it
     * @param fileWorld the input file
     * @return a World object based on the information of the file
     * @throws IOException
     */

    public static World loadWorld(File fileWorld) throws IOException {
        World world = null; // create the world outside of try and catch , so we can return it
        try (BufferedReader file_b_reader = new BufferedReader(new FileReader((fileWorld)))){
            String line_rows = file_b_reader.readLine();
            int line_rows_Int = Integer.parseInt(line_rows);
            String line_columns = file_b_reader.readLine();
            int line_columns_Int = Integer.parseInt(line_columns);
            world = new World(line_rows_Int, line_columns_Int);

            for(int i = 0 ; i < line_rows_Int ; i++) // the number of rows in the file
            {
                for(int j = 0 ; j < line_columns_Int ; j++) // the number of columns in the file
                {
                    String line = file_b_reader.readLine();
                    String[] line_parts = line.split(",");
                    if(line_parts.length > 2)
                    {
                        if(line_parts[2].equals("HERO")) // checks if our object is a Hero
                        {
                            int index_health = Integer.parseInt(line_parts[4]);
                            int index_weapon_s = Integer.parseInt(line_parts[5]);
                            int index_armor_s = Integer.parseInt(line_parts[6]);
                            char index_symbol = line_parts[3].charAt(0);
                            Hero hero = new Hero(index_health, index_symbol, index_weapon_s, index_armor_s);
                            world.addEntity(i, j, hero);
                        }
                        else if(line_parts[2].equals("MONSTER")) // checks if our object is a Monster
                        {
                            int index_health = Integer.parseInt(line_parts[4]);
                            char index_symbol = line_parts[3].charAt(0);
                            WeaponType weaponType = null;
                            if(line_parts[5].equals("S")){
                                weaponType = WeaponType.SWORD;
                            }
                            if(line_parts[5].equals("A")){
                                weaponType = WeaponType.AXE;
                            }
                            if(line_parts[5].equals("C")){
                                weaponType = WeaponType.CLUB;
                            }
                            Monster monster = new Monster(index_health, index_symbol, weaponType);
                            world.addEntity(i, j, monster);
                        }
                    }
                }
            }
        }

        return world;
    }

    /**
     * we start by checking if the file exists or not if not then we make one
     * then using the buffered reader and the information of each entity we write to the file
     * @param world the world that we want to write to the file
     * @param file the file that we want to output the world to
     */
    public static void write(World world, File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (file.exists() && file.isFile() && file.canWrite()) {
                try {
                    BufferedWriter buffered_writer = new BufferedWriter(new FileWriter(file));
                    for (int i = 0; i < world.getRows(); i++) {
                        for (int j = 0 ; j < world.getColumns(); j++) {
                            if (world.isHero(i, j)) {
                                buffered_writer.write(String.format("%s,%s,HERO,H,%s,%s,%s", Integer.toString(i), Integer.toString(j), world.getEntity(i, j).getHealth(), world.getEntity(i, j).weaponStrength(), world.getEntity(i, j).armorStrength()) + "\n");
                            } else if (world.isMonster(i, j)){
                                if( ((Monster)world.getEntity(i, j)).getWeaponType().getWeaponStrength() == 4) {
                                    buffered_writer.write(String.format("%s,%s,MONSTER,M,%s,S", i, j, world.getEntity(i, j).getHealth(), world.getEntity(i, j).weaponStrength()) + "\n");
                                }
                                if( ((Monster)world.getEntity(i, j)).getWeaponType().getWeaponStrength() == 3) {
                                    buffered_writer.write(String.format("%s,%s,MONSTER,M,%s,A", i, j, world.getEntity(i, j).getHealth(), world.getEntity(i, j).weaponStrength()) + "\n");
                                }
                                if( ((Monster)world.getEntity(i, j)).getWeaponType().getWeaponStrength() == 2) {
                                    buffered_writer.write(String.format("%s,%s,MONSTER,M,%s,C", i, j, world.getEntity(i, j).getHealth(), world.getEntity(i, j).weaponStrength()) + "\n");
                                }

                            } else {
                                buffered_writer.write(String.format("%s,%s", i, j) + "\n"); // if the entity was null
                            }
                        }
                    }
                    buffered_writer.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
