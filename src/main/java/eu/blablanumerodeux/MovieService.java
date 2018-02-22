package eu.blablanumerodeux;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieService {

    public List<String> getAllArticle() {
        List<String> result = new ArrayList<>();
        result.add("test");
        result.add("test2");
        return result;
    }
}
