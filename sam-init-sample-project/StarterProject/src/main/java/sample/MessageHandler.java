package sample;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Handler for requests to Lambda function.
 */
@Slf4j
public class MessageHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Custom-Header", "application/json");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent().withHeaders(headers);
        try {
            log.info("input: {}", new ObjectMapper().writeValueAsString(input.getBody()));
            String output = String.format("{ \"message\": \"%s\"}", UUID.randomUUID().toString());

            return response
                    .withStatusCode(200)
                    .withBody(output);
        } catch (Exception e) {
            return response
                    .withBody(e.getMessage())
                    .withStatusCode(500);
        }
    }

}
