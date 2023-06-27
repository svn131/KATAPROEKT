package com.javamentor.qa.platform.service.abstracts.dto;

import com.javamentor.qa.platform.models.dto.PageDto;

import java.util.List;
import java.util.Map;

public abstract class PaginationDtoServiceAbstract<T, P> {
    // Два абстрактных метода, которые необходимо реализовать разработчику
    // Реализуй этот метод, чтобы получить конкретный список дто по объекту параметра
    protected abstract List<T> getItems(P param);

    // Реализуй этот метод, чтобы получить общее колличество данных в бд по объекту параметра
    protected abstract int getTotalResultCount(P param);

    //Сборка нашего PageDto
    public PageDto<T> getPageDto(Map<String, Object> parameters) {
        // Извлечение параметров пагинации из переданных параметров
        int currentPage = (int) parameters.get("currentPage");
        int itemsOnPage = (int) parameters.get("itemsOnPage");
        P param = (P) parameters.get("workPagination");
        // Получение списка наших ДТО по параметру пагинации
        List<T> items = getItems(param);
        // Получение общего колличества данных в бд по параметру пагинации
        int totalResultCount = getTotalResultCount(param);
        //Получение общего колличества страниц
        int totalPageCount = (int) Math.ceil((double) totalResultCount / (double) itemsOnPage);
        //Итоговая сборка нашего PageDto
        PageDto<T> pageDto = new PageDto<>();
        pageDto.setCurrentPageNumber(currentPage);
        pageDto.setTotalPageCount(totalPageCount);
        pageDto.setTotalResultCount(totalResultCount);
        pageDto.setItems(items);
        pageDto.setItemsOnPage(itemsOnPage);

        return pageDto;
    }

}