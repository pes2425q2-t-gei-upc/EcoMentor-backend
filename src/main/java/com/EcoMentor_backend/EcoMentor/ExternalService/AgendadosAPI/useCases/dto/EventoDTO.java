package com.EcoMentor_backend.EcoMentor.ExternalService.AgendadosAPI.useCases.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventoDTO {
    private int id;
    private String title;
    private String description;
    @SuppressWarnings("checkstyle:MemberName")
    private String date_ini;
    @SuppressWarnings("checkstyle:MemberName")
    private String date_end;
    @SuppressWarnings("checkstyle:MemberName")
    private String info_tickets;
    private String schedule;
    private List<CategoriaDTO> categories;
    private List<ImagenDTO> images;
    private UbicacionDTO location;
    private List<LinkDTO> links;
}










