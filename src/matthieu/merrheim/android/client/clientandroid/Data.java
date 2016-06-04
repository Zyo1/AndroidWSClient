package matthieu.merrheim.android.client.clientandroid;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;


public class Data {
    static final String[] DATA = {

    };



    public static ArrayList<String> randomList(int count) {
        Random random = new Random();
        HashSet<String> items = new HashSet<String>();


        count = Math.min(count, DATA.length);

        while (items.size() < count) {
            items.add(DATA[random.nextInt(DATA.length)]);
        }

        return new ArrayList<String>(items);
    }

    public static ArrayList<String> asList() {
        ArrayList<String> items = new ArrayList<String>();
        for (int i = 0, z = DATA.length ; i < z ; i++) {
            items.add(DATA[i]);
        }
        return items;
    }

}
