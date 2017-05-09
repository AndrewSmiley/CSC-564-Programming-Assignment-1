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
        //create something to store our mile markers, our penalities and our previous
        //bests
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
        //
        while(i != -1){
            indexes.add(i);
            i = previousBest.get(i);
        }

        for(int j = indexes.size()-1; j >= 0; j--){
            System.out.println("Distance: "+mileMarkers.get(indexes.get(j))+ " Total Penalty: "+penalities.get(indexes.get(j)));
        }


    }



}
