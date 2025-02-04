package com.example.videogame_distribution_app.service;

import com.example.videogame_distribution_app.entity.Game;
import com.example.videogame_distribution_app.repository.GameRepository;
import com.example.videogame_distribution_app.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private GameService gameService;

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
        game.setId(1L);
        game.setName("Test Game");
    }


    @Test
    public void testGetAllGames() {
        Game game1 = new Game();
        game1.setName("Game 1");
        Game game2 = new Game();
        game2.setName("Game 2");
        when(gameRepository.findAll()).thenReturn(Arrays.asList(game1, game2));

        List<Game> games = gameService.getAllGames();

        assertNotNull(games);
        assertEquals(2, games.size());
        verify(gameRepository, times(1)).findAll();
    }

    @Test
    public void testGetGameById() {
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));

        Optional<Game> result = gameService.getGameById(1L);

        assertTrue(result.isPresent());
        assertEquals(game, result.get());
        verify(gameRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateGame() {
        when(gameRepository.save(any(Game.class))).thenReturn(game);

        Game createdGame = gameService.createGame(game);

        assertNotNull(createdGame);
        assertEquals(game.getName(), createdGame.getName());
        verify(gameRepository, times(1)).save(game);
        verify(notificationService, times(1)).sendGameNotification(game.getName());
    }

    @Test
    public void testUpdateGame() {
        Game updatedGame = new Game();
        updatedGame.setId(1L);
        updatedGame.setName("Updated Game");

        when(gameRepository.existsById(1L)).thenReturn(true);
        when(gameRepository.save(updatedGame)).thenReturn(updatedGame);

        Game result = gameService.updateGame(1L, updatedGame);

        assertNotNull(result);
        assertEquals("Updated Game", result.getName());
        verify(gameRepository, times(1)).save(updatedGame);
    }

    @Test
    public void testUpdateGame_GameNotFound() {
        Game updatedGame = new Game();
        updatedGame.setId(1L);
        updatedGame.setName("Updated Game");

        when(gameRepository.existsById(1L)).thenReturn(false);

        Game result = gameService.updateGame(1L, updatedGame);

        assertNull(result);
        verify(gameRepository, times(0)).save(updatedGame);
    }

    @Test
    public void testDeleteGame() {
        doNothing().when(gameRepository).deleteById(1L);

        gameService.deleteGame(1L);

        verify(gameRepository, times(1)).deleteById(1L);
    }
}
