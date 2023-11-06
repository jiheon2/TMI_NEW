package kopo.poly.service;

import kopo.poly.dto.MarketDTO;

import java.util.List;

public interface IMarketService {
    List<MarketDTO> getList(String nm) throws Exception;
}
