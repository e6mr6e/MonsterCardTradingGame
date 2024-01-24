package at.fhtw.sampleapp.service;

import at.fhtw.sampleapp.persistence.repository.TransactionRepository;
import at.fhtw.sampleapp.persistence.repository.TransactionRepositoryImpl;
import at.fhtw.sampleapp.persistence.UnitOfWork;

public class TransactionService {

    private TransactionRepository transactionRepository;

    public TransactionService(UnitOfWork unitOfWork) {
        this.transactionRepository = new TransactionRepositoryImpl(unitOfWork);
    }

    public void acquirePackages(String username) {
        transactionRepository.paketeErwerben(username);
    }

    public boolean enoughCoins(String username) {
        return transactionRepository.hatGenugMuenzen(username);
    }

    public void deductCoins(String username) {
        transactionRepository.muenzenAbziehen(username);
    }

    public void insertCards(String username) {
        transactionRepository.kartenZuordnen(username);
    }

    public void deletePackage() {
        transactionRepository.deletePackage();
    }

    public void updateCardsIntoDeck(String username) {
        transactionRepository.kartenImDeckGeben(username);
    }
    public int countAcquiredPackages(String username) {
        return transactionRepository.countAcquiredPackages(username);
    }
}