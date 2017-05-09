import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import static java.util.Comparator.comparingDouble;
import static java.util.Comparator.comparingInt;

/**
 * Created by Andrew on 5/8/17.
 */

public class Main {


    public static void main(String[] args) {
        //create something to store our mile markers
        ArrayList<Integer> mileMarkers = new ArrayList<>(Arrays.asList(new Integer(0)));
        ArrayList<Integer> penalities = new ArrayList<>(Arrays.asList(new Integer(0)));
        ArrayList<Integer> previousBest = new ArrayList<>(Arrays.asList(new Integer(-1)));

        //attempt to open the file
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("hwdata1.txt")));
            //reference for our line
            String line;
            while ((line = br.readLine()) != null) {
                mileMarkers.add(Integer.parseInt(line));
            }

            br.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        for (int i : mileMarkers) {
            System.out.println(i);

        }

        for (int i = 1; i < mileMarkers.size(); i++) {
            int minIndex = 0; //this is our best previous index (i.e. the starting point before i)
            ArrayList<Integer> mins = new ArrayList<>(); //store all of our starting point scores through
            for (int j = 0; j < i; j++) {
                mins.add(penalities.get(j) + (int) Math.pow(200 - (mileMarkers.get(i) - mileMarkers.get(j)), 2));
            }
            //here just get our Math.min()
            minIndex = IntStream.range(0, mins.size()).boxed()
                    .min(comparingInt(mins::get))
                    .get();
            //now update our previous best
            previousBest.add(minIndex);
            penalities.add(mins.get(minIndex));
        }
        int i = previousBest.size()-1;
        ArrayList<Integer> indexes = new ArrayList<>();
        while(i != 0){
            indexes.add(i);
            System.out.println("Distance: "+mileMarkers.get(i));
            i = previousBest.get(i);
        }

        for(int j = indexes.size()-1; j > 0; j--){
            System.out.println("Distance"+mileMarkers.get(indexes.get(j))+ " Total Penalty: "+(int) Math.pow((200-mileMarkers.get(indexes.get(j))),2));
        }

//        for(int i = previousBest.size()-1; i >0; previousBest.get(previousBest.get(i))){
//            System.out.println("Distance: "+mileMarkers.get(i));
//        }

    }



}
