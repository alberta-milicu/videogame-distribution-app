package com.example.videogame_distribution_app.service;

import com.example.videogame_distribution_app.entity.Game;
import com.example.videogame_distribution_app.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final NotificationService notificationService;

    @Autowired
    public GameService(GameRepository gameRepository, NotificationService notificationService) {
        this.gameRepository = gameRepository;
        this.notificationService = notificationService;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Optional<Game> getGameById(Long id) {
        return gameRepository.findById(id);
    }

    public Game createGame(Game game) {
        Game savedGame = gameRepository.save(game);
        notificationService.sendGameNotification(savedGame.getName()); // Async call
        return savedGame;
    }

    public Game updateGame(Long id, Game game) {
        if (gameRepository.existsById(id)) {
            return gameRepository.save(game);
        }
        return null;
    }

    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }
}
