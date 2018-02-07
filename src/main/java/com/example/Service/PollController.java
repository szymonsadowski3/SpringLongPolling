package com.example.Service;

import com.example.Controller.StudentController;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@RestController
@RequestMapping("/poll")
public class PollController {
    private final Queue<DeferredResult<Quote>> responseBodyQueue = new ConcurrentLinkedQueue<>();

    class Quote {
        String symb;

        public Quote(String symb) {
            this.symb = symb;
        }

        public String getSymb() {
            return symb;
        }

        public void setSymb(String symb) {
            this.symb = symb;
        }
    }

    static class DeferredQuote extends DeferredResult<Quote> {
        private final String symbol;
        public DeferredQuote(String symbol) {
            this.symbol = symbol;
        }
    }

    @RequestMapping(value = "/{symbol}", method = RequestMethod.GET)
    public @ResponseBody
    DeferredQuote deferredResult(@PathVariable("symbol") String symbol) {
        DeferredQuote result = new DeferredQuote(symbol);
        responseBodyQueue.add(result);
        return result;
    }

    @Scheduled(fixedRate = 2000)
    public void processQueues() {
        for (DeferredResult<Quote> result : responseBodyQueue) {
//            Quote quote = jpaStockQuoteRepository.findStock(result.symbol);
            result.setResult(new Quote("ABC"));
            responseBodyQueue.remove(result);
        }
    }
}
