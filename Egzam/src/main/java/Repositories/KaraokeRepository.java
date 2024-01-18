package Repositories;

import Resonse.KaraokeModel;
import org.springframework.stereotype.Repository;

@Repository
public interface KaraokeRepository {
    KaraokeModel findByNameIgnoreCase(String name);
}
