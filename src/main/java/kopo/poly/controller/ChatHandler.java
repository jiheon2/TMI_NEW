package kopo.poly.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.dto.ChatDTO;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatHandler extends TextWebSocketHandler {
    private static Set<WebSocketSession> clients = Collections.synchronizedSet(new LinkedHashSet<>());
    public static Map<String, String> roomInfo = Collections.synchronizedMap(new LinkedHashMap<>());

    /* websocket 연결 성공 시 */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info(this.getClass().getName() + ".afterConnectionEstablished Start!");

        String roomName = CmmUtil.nvl((String) session.getAttributes().get("roomName"));
        String customerId = CmmUtil.nvl((String) session.getAttributes().get("customerId"));
        String roomNameHash = CmmUtil.nvl((String) session.getAttributes().get("roomNameHash"));

        log.info("roomName : " + roomName);
        log.info("customerId : " + customerId);
        log.info("roomNameHash : " + roomNameHash);

        clients.forEach(s -> {
            if (roomNameHash.equals(s.getAttributes().get("roomNameHash"))) {
                try {
                    ChatDTO cDTO = new ChatDTO();
                    cDTO.setName("홍길동");
                    cDTO.setMsg(customerId + "님이 " + roomName + " 채팅방에 입장하셨습니다.");
                    cDTO.setDate(DateUtil.getDateTime("yyyyMMdd hh:mm:ss"));

                    String json = new ObjectMapper().writeValueAsString(cDTO);
                    log.info("json : " + json);

                    TextMessage chatMsg = new TextMessage(json);
                    s.sendMessage(chatMsg);

                    cDTO = null;

                } catch (IOException e) {
                    log.info("Error : " + e);
                }
            }
        });

        if (!clients.contains(session)) {
            clients.add(session);
            roomInfo.put(roomName, roomNameHash);
            log.info("session open : " + session);
        }

        session.getAttributes().put("roomName", roomName);
        session.getAttributes().put("customerId", customerId);
        session.getAttributes().put("roomNameHash", roomNameHash);

        log.info(this.getClass().getName() + ".afterConnectionEstablished End!");
    }

    /* websocket 연결 종료시 */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info(this.getClass().getName() + ".afterConnectionClosed Start!");

        String roomName = CmmUtil.nvl((String) session.getAttributes().get("roomName"));
        String customerId = CmmUtil.nvl((String) session.getAttributes().get("customerId"));
        String roomNameHash = CmmUtil.nvl((String) session.getAttributes().get("roomNameHash"));

        log.info("roomName : " + roomName);
        log.info("customerId : " + customerId);
        log.info("roomNameHash : " + roomNameHash);

        clients.remove(session);

        clients.forEach(s -> {
            if (roomNameHash.equals(s.getAttributes().get("roomNameHash"))) {
                try {
                    ChatDTO cDTO = new ChatDTO();
                    cDTO.setName("관리자");
                    cDTO.setMsg(customerId + " 님이" + roomName + " 채팅방에 입장하셨습니다.");
                    cDTO.setDate(DateUtil.getDateTime("yyyy-MM-dd hh:mm:ss"));

                    String json = new ObjectMapper().writeValueAsString(cDTO);
                    log.info("json : " + json);

                    TextMessage chatMsg = new TextMessage(json);
                    s.sendMessage(chatMsg);

                    cDTO = null;
                } catch (IOException e) {
                    log.info("Error : " + e);
                }
            }
        });

        log.info(this.getClass().getName() + ".afterConnectionClosed End!");
    }

    /* websocket 메세지 수신 및 송신 */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info(this.getClass().getName() + ".handleTextMessage Start!");

        String payload = message.getPayload();
        log.info("payload : " + payload);

        String roomName = CmmUtil.nvl((String) session.getAttributes().get("roomName"));
        String customerId = CmmUtil.nvl((String) session.getAttributes().get("customerId"));
        String roomNameHash = CmmUtil.nvl((String) session.getAttributes().get("roomNameHash"));

        log.info("roomName : " + roomName);
        log.info("customerId : " + customerId);
        log.info("roomNameHash : " + roomNameHash);

        String msg = CmmUtil.nvl(message.getPayload());
        log.info("msg : " + msg);

        ChatDTO cDTO = new ObjectMapper().readValue(msg,ChatDTO.class);

        cDTO.setDate(DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss"));

        String json = new ObjectMapper().writeValueAsString(cDTO);

        log.info("json : " + json);

        clients.forEach(s -> {
            if(roomNameHash.equals(s.getAttributes().get("roomNameHash"))) {
                try {
                    TextMessage chatMsg = new TextMessage(json);
                    s.sendMessage(chatMsg);
                } catch (IOException e) {
                    log.info("Error : " + e);
                }
            }
        });

        log.info(this.getClass().getName() + ".handleTextMessage End!");
    }
}
