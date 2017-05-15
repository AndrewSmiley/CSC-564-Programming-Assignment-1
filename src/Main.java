import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import static java.util.Comparator.comparingDouble;
import static java.util.Comparator.comparingInt;

/**
 * Created by Andrew on 5/8/17
 * Just a simple class to demonstrate dynamic programming.
 * You are going on a long trip. You start on the road at mile post 0. Along the way there are n
 hotels, at mile posts a1 < a2 < … < an, where each ai is measured from the starting point. The
 only places you are allowed to stop are at these hotels, but you can choose which of the hotels
 you stop at. You must stop at the final hotel (at distance an), which is your destination.
 You'd ideally like to travel 200 miles a day, but this may not be possible (depending on the spacing
 of the hotels). If you travel x miles during a day, the penalty for that day is (200 - x)2
 . You want
 to plan your trip so as to minimize the total penalty - that is, the sum, over all travel days, of the
 daily penalties.
 Give an efficient algorithm that determines the optimal sequence of hotels at which to stop.

 Input files give a list of 100 perspective stops, we need to calculate our the most efficient method for going about the stops.

 */

public class Main {


    public static void main(String[] args) {
        //create something to store our mile markers, our penalities and our previous
        //bests, store 0 as the first element in hte mile markers and penalties, -1 in prvious best to represent 0
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

            br.close(); //close the file when all values are read
            //handle exceptions
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        //this is our outer loop, we start at i=1 because i=0 is our base case, and we already know
        //the previous best for this one, which is -1 (there's no previous best)
        for (int i = 1; i < mileMarkers.size(); i++) {
            int minIndex = 0; //this is our best previous index (i.e. the starting point before i)
            ArrayList<Integer> mins = new ArrayList<>(); //store all of our starting point scores through
            for (int j = 0; j < i; j++) {
                //here we do the reccurance relation: f(n)=min(f(j) +[200-(a[i]-a[j]]^2 : 0 <= j < i;)
                mins.add(penalities.get(j) + (int) Math.pow(200 - (mileMarkers.get(i) - mileMarkers.get(j)), 2));
            }
            //here just get our Math.min(), this is a tweak because we've stored our mins in an arraylist
            minIndex = IntStream.range(0, mins.size()).boxed()
                    .min(comparingInt(mins::get))
                    .get();
            //now update our previous best
            previousBest.add(minIndex);
            //update our penalty scores with our previous best index value
            penalities.add(mins.get(minIndex));
        }
        //now just iterate over our previous bests to get our indexes of mile markers
        int i = previousBest.size()-1;
        //store the indeexs
        ArrayList<Integer> indexes = new ArrayList<>();
        //add our indexes in reverse order (i.e. farthest mile marker first) till we get to -1 i.e. the starting point
        while(i != -1){
            indexes.add(i);
            i = previousBest.get(i);
        }
        //finally, print in decrementing order as to show from mile marker 0 to the last mile marker
        for(int j = indexes.size()-1; j >= 0; j--){
            System.out.println("Distance: "+mileMarkers.get(indexes.get(j))+ " Total Penalty: "+penalities.get(indexes.get(j)));
        }


    }



}
