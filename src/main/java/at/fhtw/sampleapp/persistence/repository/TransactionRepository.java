package at.fhtw.sampleapp.persistence.repository;

import java.util.List;
import java.util.Map;

public interface TransactionRepository {
        void paketeErwerben(String username);
        List<Map<String, Object>> selectCards();
        boolean hatGenugMuenzen(String username);
        void muenzenAbziehen(String username);
        void kartenZuordnen(String username);
        void deletePackage();
        List<String> vierKartenAuswaehlen(String username);
        void kartenImDeckGeben(String username);
        int countAcquiredPackages(String username);
}