package API;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import models.LinksBase;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

/**
 *
 * Created by Maksim Rylkin on 09.10.2015.
 */
public class RssParser extends Thread{

    private String param;
    private final LinksBase LINKS = LinksBase.getINSTANCE();

    public RssParser(String param) {
        this.param = param;
    }

    @Override
    public void run() {
        URL url;
        try {
            url = new URL("https://blogs.yandex.ru/search.rss?text="+param);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(connection));
            List list = feed.getEntries();
            Iterator itEntries = list.iterator();


            while (itEntries.hasNext()) {
                SyndEntry syndEntry = (SyndEntry)itEntries.next();
                LINKS.add(syndEntry.getLink());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (FeedException e) {
            e.printStackTrace();
        }

    }
}
