package movie.suggestions;

public class ImdbScraper {
    
}
//        String url = "http://www.imdb.com/search/title/?title_type=feature&release_date=%d".formatted(1990);
//        Document doc = Jsoup.connect(url).get();
//        for(Element movie : doc.select("div.lister-item.mode-advanced")) {
//            
//            List<Element> h3s = movie.select("h3").stream()
//                    .collect(Collectors.toList());
//            for(Element h3 : h3s) {
//                Elements links = h3.select("a[href]");
//                System.out.println(links.text());
//            }
//        }