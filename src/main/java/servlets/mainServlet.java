package servlets;

import API.RssParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import models.LinksBase;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by Maksim Rylkin on 09.10.2015.
 */
public class mainServlet extends HttpServlet {

    private final LinksBase LINKS = LinksBase.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LINKS.clear();
        resp.setContentType("text/html; charset=UTF-8");

        List<Thread> threads = new ArrayList<Thread>();
        String[] asd = req.getParameterValues("query");
        for (String x : asd){
            RssParser parser = new RssParser(x);
            parser.start();
            threads.add(parser);
        }

        for (Thread thread : threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        Map<String,Integer> domains = new HashMap<>();
        for (String link : LINKS.values()){
            String host = new URL(link).getHost();
            if (domains.containsKey(host))
                domains.put(host,domains.get(host)+1);
            else
                domains.put(host,1);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject jsonObject = new JsonObject();

        for (Map.Entry<String, Integer> entry : domains.entrySet()){
            jsonObject.addProperty(entry.getKey(),entry.getValue());
        }

        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter printout = resp.getWriter();
        printout.print(gson.toJson(jsonObject));
        printout.flush();
    }
}
