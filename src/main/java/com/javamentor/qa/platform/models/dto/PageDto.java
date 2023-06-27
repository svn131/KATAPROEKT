package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Пагинация наших DTO")
public class PageDto<T> {
    @Schema(description = "Текущий номер страницы")
    private int currentPageNumber;
    @Schema(description = "Общее колличество страниц")
    private int totalPageCount;
    @Schema(description = "Общее количество результатов")
    private int totalResultCount;
    @Schema(description = "Список элементов типо Т на странице")
    private List<T> items;
    @Schema(description = "Количество элементов на странице")
    private int itemsOnPage;

}
