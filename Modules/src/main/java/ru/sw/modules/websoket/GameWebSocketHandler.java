package ru.sw.modules.websoket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.sw.modules.game.Game;
import ru.sw.modules.game.GameRepository;
import ru.sw.modules.game.player.Player;
import ru.sw.modules.settings.Settings;
import ru.sw.modules.settings.SettingsRepository;
import ru.sw.platform.core.exceptions.PlatofrmExecption;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class GameWebSocketHandler extends TextWebSocketHandler {

    public  Object monitor = new Object();
    public  Game activeGame = new Game();
    public  List<WebSocketSession> sessionList = new ArrayList<>();

    Logger logger = LoggerFactory.getLogger(GameWebSocketHandler.class);
    private  ObjectMapper objectMapper = new ObjectMapper();
    private  Calendar startGame;


    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private SettingsRepository settingsRepository;

    @Override
    public synchronized void afterConnectionEstablished(WebSocketSession session) throws Exception {
        try {
            if (activeGame.getId() == null) {
                gameRepository.create(activeGame);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        sessionList.add(session);
        activeGame.setCountVisitors(sessionList.size());
        if(startGame != null) {
            long seconds = (Calendar.getInstance().getTime().getTime()-startGame.getTime().getTime())/1000;
            activeGame.setSecondsBeforeRoundOver(new Integer((int) seconds));
        }
        updateSession();
        System.err.println("Session open");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.err.println("------------------");
        System.err.println(message.getPayload().toString());
    }

    @Override
    public synchronized void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionList.remove(session);
        activeGame.setCountVisitors(sessionList.size());
        updateSession();
    }

    public void startGame(){
        activeGame.setState(Game.GameState.Active);
        startGame = Calendar.getInstance();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(40000);
                    synchronized (monitor) {
                        activeGame.setState(Game.GameState.Finished);
                        startGame = null;

                        Integer min = 0;
                        Integer max = 100;
                        Double generatedValue = min + Math.random() * (max - min);
                        System.err.println("Generated value"  + generatedValue);
                        Player winner = null;
                        double sum = 0;
                        for(Player player : activeGame.getPlayers()) {
                            if(player.isWinner()) {
                                winner = player;
                                break;
                            }
                        }
                        if(winner == null) {
                            List<Settings> settings = settingsRepository.list(Settings.class);
                            if(settings.isEmpty()) {
                                throw new PlatofrmExecption("settings not set" , PlatofrmExecption.Type.ActionError);
                            }
                            for (Player player : activeGame.getPlayers()) {
                                sum += player.getProbability();
                                if(player.getNickName().contains(settings.get(0).getSiteName())){
                                    sum += settings.get(0).getBonus();
                                }
                                if (generatedValue < sum) {
                                    winner = player;
                                    break;
                                }
                            }
                        }
                        System.err.println("winner:" + winner.getNickName());
                        gameRepository.update(activeGame);
                        activeGame = new Game();
                        activeGame.setLastWinnerNickName(winner.getNickName());
                        activeGame.setLastWinnerImageUrl(winner.getImageUrl());
                        gameRepository.create(activeGame);
                        updateSession();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        updateSession();
    }


    public void updateSession() {
        synchronized (monitor) {
            for (WebSocketSession session : sessionList) {
                try {
                    session.sendMessage(new TextMessage(objectMapper.writeValueAsString(activeGame)));
                } catch (IOException e) {
                    throw new PlatofrmExecption(e.getMessage(), PlatofrmExecption.Type.ActionError);
                }
            }
        }
    }
}
