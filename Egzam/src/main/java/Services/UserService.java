package Services;

import Exceptions.KaraokeNotFoundException;
import Repositories.KaraokeRepository;
import Resonse.KaraokeModel;
import Resonse.KaraokeResponseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.net.URI;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final RestClient client = RestClient.create();
    private final KaraokeRepository karaokeRepository;
    public String getKaraokeKey(String karaokeName) throws KaraokeNotFoundException {
        KaraokeModel karaoke = karaokeRepository.findByNameIgnoreCase(processKaraokeName(karaokeName));
        if (karaoke != null && karaoke.id() != null && !karaoke.id().isEmpty) {
            return karaoke.id();
        }


        URI path = new DefaultUriBuilderFactory().builder()
                .scheme("https")
                .host("//karaoke-api-service-prod.stingray.com")
                .path("application/json")
                .queryParam("q", karaokeName)
                .build();
        KaraokeResponseModel result = client.get()
                .uri(path)
                .retrieve()
                .body(KaraokeResponseModel.class);

        KaraokeModel karaokeModel = result.getDocs().getFirst();
        karaokeModel.name(processKaraokeName(karaokeModel.name()));
        karaokeRepository.save(karaokeModel);
        return karaokeModel.id();
    }
        private String processKaraokeName(String originalName){
            return originalName.trim().toLowerCase();
        }
}
