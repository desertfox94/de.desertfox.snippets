package fhl.mib.seminar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App {

	public static void main(String[] args) throws IOException {
		Element table = Jsoup.connect("https://de.wikipedia.org/wiki/Liste_deutscher_Zeitungen").execute().parse().getElementById("tageszeitungen");
		Element tBody = table.child(0);
		Elements rows = tBody.children();
		List<Newspaper> newspapers = new ArrayList<Newspaper>(rows.size());
		Element row;
		for (int i = 1; i < rows.size(); i++) {
			row = rows.get(i);
			Newspaper newspaper = new Newspaper();
			Element col = row.child(1);
			Element name;
			if (col.children().size() > 1) {
				name = col.child(1);
			} else {
				name = col.child(0);
				
			}
			newspaper.name = name.text();
			newspaper.wikiLink = "https://de.wikipedia.org" + name.attr("href");
			newspapers.add(newspaper);
		}
		for (Newspaper newspaper : newspapers) {
			Elements elementsByClass = Jsoup.connect(newspaper.wikiLink).timeout(10 * 1000).execute().parse().getElementsByClass("infobox toptextcells float-right");
			if (elementsByClass.isEmpty()) {
				continue;
			}
			Element infobox = elementsByClass.first();
			Elements weblinks = infobox.getElementsByClass("external text");
			if (!weblinks.isEmpty()) {
				for (Element weblink : weblinks) {
					newspaper.homepage.add(weblink.attr("href"));
					
				}
			}
			System.out.println(newspaper.name + " - " + newspaper.homepage);
		}
	}
	
	
}
