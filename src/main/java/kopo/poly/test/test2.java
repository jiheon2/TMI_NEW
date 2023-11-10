package kopo.poly.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class test2 {
    private static final String url = "https://map.naver.com/v5/search";

    public static void main(String[] args) {
        try {
            Document doc = Jsoup.connect(url).get();

            // 일정 시간 동안 웹 페이지 요소를 기다리기 위한 반복
            for (int i = 0; i < 10; i++) {
                doc = Jsoup.connect(url).get();
                // (1) 검색결과 iframe으로 frame을 바꾼다.
                Element searchIframe = doc.select("iframe#searchIframe").first();
                String iframeUrl = searchIframe.attr("src");
                Document iframeDoc = Jsoup.connect(iframeUrl).get();

                // (2) 검색 결과 장소 목록을 elements에 담는다.
                Elements elements = iframeDoc.select(".C6RjW .place_bluelink");

                System.out.println("TestTest**********************************");
                System.out.println("elements.size() = " + elements.size());

                // (3) 첫번째 검색결과를 클릭한다.
                Element firstElement = elements.first();
                String detailUrl = firstElement.attr("href");
                Document detailDoc = Jsoup.connect(detailUrl).get();

                // (4) 상세정보가 나오는 프레임으로 이동한다.
                Element entryIframe = detailDoc.select("iframe#entryIframe").first();
                String entryIframeUrl = entryIframe.attr("src");
                Document entryIframeDoc = Jsoup.connect(entryIframeUrl).get();

                // (5) 상세정보 프레임에서 주소 정보가 들어있는 곳으로 이동한다.
                Elements placeSectionContents = entryIframeDoc.select(".place_section_content");
                Element addressElement = placeSectionContents.get(1);

                // (6) "주소" 버튼 요소를 찾아 클릭한다.
                Element addressButton = addressElement.select(".LDgIH").first();
                String addressUrl = addressButton.attr("href");
                Document addressDoc = Jsoup.connect(addressUrl).get();

                // (7) "도로명"과 "지번" 정보가 들어있는 div 요소를 찾아서, 해당 정보를 가져온다.
                Elements addressInfos = addressDoc.select(".nQ7Lh");

                if (elements.size() > 0) {
                    // 원하는 요소를 찾았을 때 데이터 추출
                    for (Element addressInfo : addressInfos) {
                        Element addressType = addressInfo.select("span").first();
                        String address = addressInfo.ownText().trim();
                        System.out.println(addressType.text() + " : " + address);
                    }
                    break; // 원하는 데이터를 찾았으므로 반복 중지
                }

                try {
                    // 스레드 대기
                    Thread.sleep(1000); // 1초 대기
                } catch (InterruptedException e) {
                    // 인터럽트 예외 처리
                    System.out.println("스레드가 인터럽트되었습니다.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}