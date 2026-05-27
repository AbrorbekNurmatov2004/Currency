package uz.pdp.currancy_bot.model.dto;

import lombok.*;

@Getter
@Builder
public class AppErrorDto {
    private String path;
    private int error;
    private String message;
    private String timestamp;
}
