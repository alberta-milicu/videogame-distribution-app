package com.example.videogame_distribution_app.controller;

import com.example.videogame_distribution_app.entity.Game;
import com.example.videogame_distribution_app.entity.User;
import com.example.videogame_distribution_app.repository.UserRepository;
import com.example.videogame_distribution_app.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;
    private final UserRepository userRepository;

    @Autowired
    public GameController(GameService gameService, UserRepository userRepository) {
        this.gameService = gameService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }

    @GetMapping("/{id}")
    public Optional<Game> getGameById(@PathVariable Long id) {
        return gameService.getGameById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Game createGame(@RequestBody Game game) {
        // Check if the user ID is provided and valid
        if (game.getUser() == null || game.getUser().getId() == 0) {
            throw new IllegalArgumentException("User ID must be provided.");
        }

        // Fetch the user from the database
        User user = userRepository.findById(game.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Associate the user with the game
        game.setUser(user);

        // Save the game
        return gameService.createGame(game);
    }

    @PutMapping("/{id}")
    public Game updateGame(@PathVariable Long id, @RequestBody Game game) {
        return gameService.updateGame(id, game);
    }

    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
    }
}
