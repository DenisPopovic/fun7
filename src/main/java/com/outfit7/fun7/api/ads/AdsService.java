package com.outfit7.fun7.api.ads;

import com.outfit7.fun7.model.dto.AdsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

@Service
public class AdsService {
  Logger logger = LoggerFactory.getLogger(AdsService.class);

  private final WebClient webClient;
  private final String ADS_API_URL = "https://us-central1-o7tools.cloudfunctions.net";
  private final String ADS_ENABLED_STRING = "sure, why not!";

  public AdsService(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl(ADS_API_URL).build();
  }

  public boolean adsEnabled(int countryCode) {
    try {
      AdsDto response =
          this.webClient
              .get()
              .uri("/fun7-ad-partner?countryCode={countryCode}", countryCode)
              .headers(headers -> headers.setBasicAuth("fun7user", "fun7pass"))
              .retrieve()
              .bodyToMono(AdsDto.class)
              .block();

      if (response == null) {
        return false;
      }

      return response.getAds().equals(ADS_ENABLED_STRING);
    } catch (WebClientException e) {
      logger.warn("Exception: %s%n", e);
    }
    return false;
  }
}
