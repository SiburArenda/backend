package com.publicvm.siburarenda.dto;

import com.publicvm.siburarenda.model.Dates;
import com.publicvm.siburarenda.model.Status;
import lombok.Data;

import java.sql.Date;

@Data
public class DatesDto {

    private Long id;
    private Date from;
    private Date to;
    private String status;
    private Date created;
    private Date updated;

    public static DatesDto toDto(Dates dates) {
        DatesDto dto = new DatesDto();
        dto.setId(dates.getId());
        dto.setCreated(dates.getCreated());
        dto.setUpdated(dates.getUpdated());
        dto.setFrom(dates.getFrom());
        dto.setTo(dates.getTo());
        dto.setStatus(String.valueOf(dates.getStatus()));
        return dto;
    }

    public static Dates toDates(DatesDto datesDto) {
        Dates dates = new Dates();
        dates.setId(datesDto.getId());
        dates.setCreated(datesDto.getCreated());
        dates.setUpdated(datesDto.getUpdated());
        dates.setFrom(datesDto.getFrom());
        dates.setTo(datesDto.getTo());
        dates.setStatus(Status.valueOf(datesDto.getStatus()));
        return dates;
    }

}
