package uz.pdp.currancy_bot.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyResponse {

    private Long id;

    @JsonProperty("Code")
    private String code;

    @JsonProperty("Ccy")
    private String name;

    @JsonProperty("CcyNm_UZC")
    private String nameUzc;

    @JsonProperty("CcyNm_UZ")
    private String nameUz;

    @JsonProperty("CcyNm_EN")
    private String nameEn;

    @JsonProperty("CcyNm_RU")
    private String nameRu;

    @JsonProperty("Rate")
    private String rate;

    @JsonProperty("Date")
    private String date;
}