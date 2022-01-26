package Core.Habr;
import Core.ParserSettings;
public class HabrSettings extends ParserSettings {

    public HabrSettings(int start, int end) {
        startPoint = start;
        endPoint = end;
        BASE_URL = "https://habr.com/ru/news";
        PREFIX = "page{CurrentId}";
        savePath = "C:\\Users\\Kensein\\OneDrive\\Рабочий стол\\Java-3-main";
    }
}