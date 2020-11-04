package io.geekbang.moby.gateway.router;

import java.util.List;
import java.util.Random;

public class HttpEndpointRouterImpl implements HttpEndpointRouter {
    private Random random = new Random();

    @Override
    public String route(List<String> endpoints) {
        int size = endpoints.size();
        int nextInt = random.nextInt(size);
        int index = nextInt % size;
        return endpoints.get(index);
    }
}
