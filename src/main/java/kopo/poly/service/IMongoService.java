package kopo.poly.service;

import kopo.poly.dto.ChatDTO;

public interface IMongoService {
    int insertChat(ChatDTO pDTO) throws Exception;
}
