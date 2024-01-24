package at.fhtw;

import at.fhtw.httpserver.server.Server;
import at.fhtw.httpserver.utils.Router;
import at.fhtw.sampleapp.controller.*;
import at.fhtw.sampleapp.persistence.repository.*;
import at.fhtw.sampleapp.service.CardService;
import at.fhtw.sampleapp.service.DeckService;
import at.fhtw.sampleapp.service.TransactionService;
import at.fhtw.sampleapp.persistence.UnitOfWork;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(10001, configureRouter());
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static Router configureRouter()
    {
        Router router = new Router();

        // Create an instance of UnitOfWork
        UnitOfWork unitOfWork = new UnitOfWork();

        // Create an instance of TransactionService
        TransactionService transactionService = new TransactionService(unitOfWork);


        // Create an instance of CardService
        CardRepository cardRepository = new CardRepositoryImpl(unitOfWork);

        // Create an instance of CardService
        CardService cardService = new CardService(cardRepository);

        DeckRepository deckRepository = new DeckRepositoryImpl(unitOfWork);

        DeckService deckService = new DeckService(deckRepository);


        router.addService("/weather", new WeatherController());
        router.addService("/echo", new EchoController());
        router.addService("/users", new UserController());
        router.addService("/sessions", new SessionController());
        router.addService("/packages", new PackageController());
        router.addService("/transactions", new TransactionController(transactionService));
        router.addService("/transactions/packages", new TransactionController(transactionService));
        router.addService("/cards", new CardController(cardService));
        router.addService("/deck", new DeckController(deckService));
        router.addService("/stats", new StatsController());
        router.addService("/scoreboard", new ScoreboardController());

        return router;
    }
}