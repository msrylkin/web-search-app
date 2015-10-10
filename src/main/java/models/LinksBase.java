package models;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * Created by Maksim Rylkin on 09.10.2015.
 */
public class LinksBase {
    private static LinksBase INSTANCE;
    private volatile Set<String> links = new HashSet<String>();

    private LinksBase() {

    }

    public static LinksBase getINSTANCE(){
        if (INSTANCE==null)
            INSTANCE = new LinksBase();
        return INSTANCE;
    }

    public void add(String str){
        this.links.add(str);
    }

    public void clear(){
        links.clear();
    }

    public Set<String> values(){
        return links;
    }
}
