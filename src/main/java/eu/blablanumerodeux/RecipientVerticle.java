package eu.blablanumerodeux;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RecipientVerticle extends AbstractVerticle {

    private final ObjectMapper mapper = Json.mapper;
    @Autowired
    private MovieService articleService;

    @Override
    public void start() throws Exception {
        super.start();
        vertx.eventBus().<String>consumer("stringAdresse")
                .handler(getAllArticleService(articleService));
    }

    private Handler<Message<String>> getAllArticleService(MovieService service) {
        return msg -> vertx.<String>executeBlocking(future -> {
            try {
                future.complete(mapper.writeValueAsString(service.getAllArticle()));
            } catch (JsonProcessingException e) {
                future.fail(e);
            }
        }, result -> {
            if (result.succeeded()) {
                msg.reply(result.result());
            } else {
                msg.reply(result.cause().toString());
            }
        });
    }
}
